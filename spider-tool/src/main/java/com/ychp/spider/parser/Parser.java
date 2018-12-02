package com.ychp.spider.parser;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ychp.common.util.HttpClientUtil;
import com.ychp.spider.model.Rule;
import com.ychp.spider.dto.SpiderItem;
import com.ychp.spider.dto.SpiderRule;
import com.ychp.spider.dto.SpiderWebData;
import com.ychp.spider.enums.ScanType;
import com.ychp.spider.utils.ParserUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
public abstract class Parser<T extends SpiderWebData> {

    @Getter
    @Setter
    protected String configPre;

    private Map<String, Integer> levelByTag;

    private static final String IMAGE = "image";
    private static final String TAG = "tag";
    private static final String VIDEO = "video";
    private static final String TEXT = "text";

    protected static final String IFRAME = "iframe";

    public Parser(){
        initConfigPre();
    }

    protected abstract void initConfigPre();

    private SpiderRule initRule(Map<String, String> ruleValues){
        if(ruleValues == null){
            ruleValues = Maps.newHashMap();
        }
        if(ruleValues.isEmpty()) {
            Properties prop = new Properties();
            InputStream in = Object.class.getResourceAsStream("/rules.properties");
            try {
                if (in != null) {
                    prop.load(in);
                    ruleValues.put("url", prop.getProperty("spider.rule.url-" + configPre).trim());
                    ruleValues.put("keywords", prop.getProperty("spider.rule.keyword-" + configPre).trim());
                    ruleValues.put("videoTag", prop.getProperty("spider.rule.video-" + configPre).trim());
                    ruleValues.put("imageTag", prop.getProperty("spider.rule.image-" + configPre).trim());
                    ruleValues.put("textTag", prop.getProperty("spider.rule.text-" + configPre).trim());
                    ruleValues.put("subTag", prop.getProperty("spider.rule.subTag-" + configPre).trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ParserUtils.getRule(ruleValues);
    }

    private Map<String, Elements> generateTag(String html, SpiderRule spiderRule){
        Map<String, Elements> elementsByType = Maps.newHashMap();
        Document document = Jsoup.parse(html);
        fillTags(document, spiderRule.getTextTag(), TEXT, elementsByType);
        fillTags(document, spiderRule.getVideoTag(), VIDEO, elementsByType);
        fillTags(document, spiderRule.getImageTag(), IMAGE, elementsByType);
        fillTags(document, spiderRule.getSubTag(), TAG, elementsByType);
        return elementsByType;
    }

    private void fillTags(Document document, List<String> tags,
                         String key,
                         Map<String, Elements> elementsByType){
        Elements elements = new Elements();
        for(String tag : tags) {
            Elements elementsByTag = document.getElementsByTag(tag);
            elements.addAll(elementsByTag);
            elementsByTag = document.getElementsByClass(tag);
            elements.addAll(elementsByTag);
        }
        elementsByType.put(key, elements);
    }

    private List<Map<String, Set<SpiderItem>>> getDatas(String html, SpiderRule spiderRule){
        Map<String, Elements> tagsByType = generateTag(html, spiderRule);
        List<Map<String, Set<SpiderItem>>> results = Lists.newArrayList();
        for(String type : tagsByType.keySet()){
            results.add(getResult(type, tagsByType.get(type), spiderRule));
        }
        return results;
    }

    private Map<String, Set<SpiderItem>> getResult(String type, Elements elements, SpiderRule spiderRule){
        Map<String, Set<SpiderItem>> result = Maps.newHashMap();
        elements.forEach(element -> {
            List<SpiderItem> list = getOneResult(element, spiderRule, type);
            if(result.get(type) == null) {
                result.put(type, Sets.newHashSet(list));
            } else {
                Set<SpiderItem> item = result.get(type);
                item.addAll(list);
                result.put(type, item);
            }
        });
        return result;
    }

    private List<SpiderItem> getOneResult(Element element,
                                             SpiderRule spiderRule,
                                             String type){
        List<SpiderItem> list = Lists.newArrayList();
        switch (type) {
            case TEXT:
                list = getText(element, spiderRule);
                break;
            case VIDEO:
                list = getVideos(element, spiderRule);
                break;
            case IMAGE:
                list = getImages(element, spiderRule);
                break;
            case TAG:
                list = getSubTags(element, spiderRule);
                break;
            default:
                break;
        }
        list.forEach(data -> data.setLevel(getLevel(type)));
        return list;
    }

    protected List<SpiderItem> getVideos(Element element, SpiderRule spiderRule){
        SpiderItem spiderItem = new SpiderItem();
        spiderItem.setUrl(element.attr("source"));
        spiderItem.setContent(element.attr("source"));
        return Lists.newArrayList(spiderItem);
    }

    protected List<SpiderItem> getImages(Element element, SpiderRule spiderRule){
        SpiderItem spiderItem = new SpiderItem();
        spiderItem.setUrl(element.attr("src"));
        spiderItem.setContent(element.attr("src"));
        return Lists.newArrayList(spiderItem);
    }

    protected List<SpiderItem> getText(Element element, SpiderRule spiderRule){
        List<SpiderItem> result = Lists.newArrayList();
        String textContent = element.outerHtml();
        if(element.childNodeSize() > 0) {
            textContent = element.childNode(0).outerHtml();
        }
        SpiderItem spiderItem = new SpiderItem();
        if(containKeyWord(spiderRule.getKeyWord(), textContent)){
            spiderItem.setContent(textContent);
            if (element.hasAttr("href")) {
                spiderItem.setUrl(element.attr("href"));
            }
            if(spiderItem.getUrl().matches("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?")
                    || spiderItem.getUrl().matches("(/[a-zA-Z0-9\\&%_\\./-~-]*)[?]{0,1}")) {
                result.add(spiderItem);
            }
        }
        return result;
    }

    protected boolean containKeyWord(List<String> keyWord, String textContent){
        if(keyWord.isEmpty()){
            return true;
        }

        for(String key : keyWord){
            if(textContent.contains(key)){
                return true;
            }
        }
        return false;
    }

    protected List<SpiderItem> getSubTags(Element element, SpiderRule spiderRule){
        List<SpiderItem> result = Lists.newArrayList();
        String textContent = element.outerHtml();
        if(containKeyWord(spiderRule.getKeyWord(), textContent)) {
            SpiderItem spiderItem = new SpiderItem();
            spiderItem.setUrl(element.attr("src"));
            spiderItem.setContent(textContent);
            return Lists.newArrayList(spiderItem);
        }
        return result;
    }

    private List<T> makeResult(List<Map<String, Set<SpiderItem>>> datas, SpiderRule spiderRule){
        List<T> result = Lists.newArrayList();
        for(Map<String, Set<SpiderItem>> item : datas) {
            result.addAll(makeResult(item.get(VIDEO), spiderRule, ScanType.VIDEO.getValue()));
            result.addAll(makeResult(item.get(IMAGE), spiderRule, ScanType.IMAGE.getValue()));
            result.addAll(makeResult(item.get(TEXT), spiderRule, ScanType.TEXT.getValue()));
            result.addAll(makeResult(item.get(TAG), spiderRule, ScanType.TAG.getValue()));
            String dataRef = spiderRule.getUrlRegex();
            setDataRef(result, dataRef);
        }
        return result;
    }

    private void setDataRef(List<T> result, String dataRef) {
        for(T t : result){
            t.setDataRef(dataRef);
        }
    }

    private List<T> makeResult(Set<SpiderItem> datas, SpiderRule spiderRule, String type){
        List<T> result = Lists.newArrayList();
        if(datas != null) {
            T data;
            for(SpiderItem item : datas) {
                data = (T) new SpiderWebData();
                data.setKeyword(spiderRule.getKeyWords());
                data.setType(type);
                data.setContent(item.getContent());
                data.setUrl(item.getUrl());
                data.setSource(spiderRule.getUrlRegex());
                data.setLevel(item.getLevel());
                result.add(data);
            }
        }
        return result;
    }


    List<T> parseContext(Map<String, String> ruleValues){
        SpiderRule spiderRule = initRule(ruleValues);
        String url = spiderRule.getUrlRegex();
        String html;
        try {
            html = HttpClientUtil.get(url, Maps.newHashMap(), false, true, null);
        } catch (Exception e) {
            log.error("parse url = {} fail, case {}", url, e.getMessage());
            return Lists.newArrayList();
        }
        html = removeUselessContent(html);
        List<Map<String, Set<SpiderItem>>> datas = getDatas(html, spiderRule);
        return makeResult(datas, spiderRule);
    }

    public List<T> parseContext(){
        return parseContext(null);
    }

    private String removeUselessContent(String html){
        if(!html.startsWith("<html") || !html.startsWith("<HTML")){
            if(html.contains("<html")) {
                html = html.substring(html.indexOf("<html"));
            }
            if(html.contains("<HTML")) {
                html = html.substring(html.indexOf("<HTML"));
            }
        }
        return html;
    }

    public List<T>  spider(Map<String, String> ruleValues, Rule rule) {
        initLevel();
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<T> datas = parseContext(ruleValues);

        String url = ruleValues.get("url");
        if(datas.isEmpty()){
            log.info("finish spider by url[{}], sourceDatas[size={}]", url, datas.size());
            return Lists.newArrayList();
        }

        List<T> spiderDatas = Lists.newArrayList();

        stopwatch.reset();
        stopwatch.start();

        putDatas(spiderDatas, datas);

        stopwatch.stop();
        log.info("url = {} parse, cost {} ms", url, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        List<Map<String, String>> subDatas = Lists.newArrayList();
        datas.stream().filter(item -> Objects.equals(item.getType(), ScanType.TAG.getValue())
                && item.getContent().contains(IFRAME)).collect(Collectors.toList())
                .forEach(item -> {
                    if (!StringUtils.isEmpty(item.getUrl())) {
                        Map<String, String> subData = Maps.newHashMap(ruleValues);
                        subData.put("url", item.getUrl());
                        subDatas.add(subData);
                    }
                });

        stopwatch.reset();
        stopwatch.start();
        for (Map<String, String> ruleValue : subDatas) {
            putDatas(spiderDatas, parseContext(ruleValue));
        }

        stopwatch.stop();
        log.info("url = {} parse sub, cost {} ms", url, stopwatch.elapsed(TimeUnit.MILLISECONDS));

        log.info("finish spider by url[{}], spider datas[size={}], sourceDatas[size={}]", url, spiderDatas.size(), datas.size());
        if(spiderDatas.size() == 1){
            log.info("finish spider by url[{}], sourceDatas[size={}]", url, datas);
        }

        if(spiderDatas.size() == 0){
            log.info("finish spider by url[{}], sourceDatas[size={}]", url, datas.size());
        }

        return dealDatas(spiderDatas, rule);
    }

    protected List<T> dealDatas(List<T> spiderDatas, Rule rule) {
        return spiderDatas;
    }

    private void initLevel() {
        levelByTag = Maps.newHashMap();
        levelByTag.put(IMAGE, 1);
        levelByTag.put(VIDEO, 1);
        levelByTag.put(TAG, 1);
        levelByTag.put(TEXT, 1);
    }

    private Integer getLevel(String key) {
        Integer currentLevel = levelByTag.get(key);
        levelByTag.put(key, currentLevel + 1);
        return currentLevel;
    }

    protected void putDatas(List<T> spiderDatas, List<T> datas) {
        for (T item : datas) {
            if (Objects.equals(item.getType(), ScanType.TAG.getValue())
                    && item.getContent().contains(IFRAME)) {
                continue;
            }
            spiderDatas.add(item);
        }
    }

}
