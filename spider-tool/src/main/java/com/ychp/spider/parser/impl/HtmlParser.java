package com.ychp.spider.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ychp.common.util.HtmlUtils;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.parser.BaseParser;
import com.ychp.spider.parser.data.SpiderWebData;
import com.ychp.spider.parser.enums.ParserTypeEnums;
import com.ychp.spider.parser.rule.BaseRule;
import com.ychp.spider.parser.rule.HtmlRule;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Component
public class HtmlParser extends BaseParser {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String getType() {
        return ParserTypeEnums.HTML.getValue();
    }

    @Override
    public List<SpiderWebData> spider(String url, BaseRule rule) {
        HtmlRule htmlRule = (HtmlRule) rule;
        String pagingKey = htmlRule.getPagingKey();
        String offsetKey = htmlRule.getOffsetKey();
        int limit = htmlRule.getLimit() == null ? 20 : htmlRule.getLimit();
        String firstKey = htmlRule.getFirstPageKey();
        boolean isPaging = !StringUtils.isEmpty(pagingKey) && url.contains(pagingKey);
        boolean isOffset = !StringUtils.isEmpty(offsetKey) && url.contains(offsetKey);
        boolean hasFirstKey = !StringUtils.isEmpty(firstKey);
        Set<SpiderWebData> datas = Sets.newHashSet();

        if (!isOffset && !isPaging) {
            return getDatas(url, htmlRule);
        }

        if (isPaging && hasFirstKey) {
            int pageNo = 1;
            while (true) {
                String finalUrl = url.replace(pagingKey, pageNo + "");
                Document document = getWebContextDocument(finalUrl);
                if (document == null) {
                    return Lists.newArrayListWithCapacity(0);
                }
                datas.addAll(spiderContext(document, htmlRule, finalUrl));
                if (document.html().contains(firstKey)) {
                    break;
                }
                pageNo++;
            }
        }

        if (isOffset && hasFirstKey) {
            int offset = 0;
            while (true) {
                String finalUrl = url.replace(offsetKey, offset + "");
                Document document = getWebContextDocument(finalUrl);
                if (document == null) {
                    return Lists.newArrayListWithCapacity(0);
                }
                if (offset > 0 && document.html().contains(firstKey)) {
                    break;
                }
                datas.addAll(spiderContext(document, htmlRule, finalUrl));
                offset += limit;
            }
        }

        return Lists.newArrayList(datas);
    }

    private List<SpiderWebData> getDatas(String url, HtmlRule rule) {
        Document document = getWebContextDocument(url);
        if (document == null) {
            return Lists.newArrayListWithCapacity(0);
        }
        return spiderContext(document, rule, url);
    }

    private Document getWebContextDocument(String url) {
        String html = getWebContext(url, null);
        html = removeUselessContent(html);
        return Jsoup.parse(html);
    }

    private final static String HTML_PRE = "<html";
    private final static String HTML_UP_PRE = "<HTML";

    private String removeUselessContent(String html) {
        if (!html.startsWith(HTML_PRE) || !html.startsWith(HTML_UP_PRE)) {
            if (html.contains(HTML_PRE)) {
                html = html.substring(html.indexOf(HTML_PRE));
            }
            if (html.contains(HTML_UP_PRE)) {
                html = html.substring(html.indexOf(HTML_UP_PRE));
            }
        }
        return html;
    }

    private List<SpiderWebData> spiderContext(Document document, HtmlRule rule, String url) {
        long pre = System.currentTimeMillis();
        List<SpiderWebData> spiderWebDatas = Lists.newArrayList();
        spiderWebDatas.addAll(spiderOneTag(document, rule.getTextTag(), rule.getKeyWord(), DataType.TEXT));
        spiderWebDatas.addAll(spiderOneTag(document, rule.getImageTag(), rule.getKeyWord(), DataType.IMAGE));
        spiderWebDatas.addAll(spiderOneTag(document, rule.getVideoTag(), rule.getKeyWord(), DataType.VIDEO));
        spiderWebDatas.addAll(spiderOneTag(document, rule.getSubTag(), rule.getKeyWord(), DataType.TAG));
        String domain = url.split("\\?")[0];
        String[] domainArr = domain.split("/");
        spiderWebDatas = spiderWebDatas.stream()
                .filter(Objects::nonNull)
                .filter(spiderWebData -> !spiderWebData.getUrl().startsWith("/"))
                .peek(spiderWebData -> {
                    spiderWebData.setSource(url);
                    if (spiderWebData.getUrl().startsWith("/")) {
                        spiderWebData.setUrl(domainArr[0] + "//" + domainArr[2] + spiderWebData.getUrl());
                    }
                })
                .collect(Collectors.toList());

        log.info("finish spider task[url={}], datas = {}, cost {} ms",
                url, spiderWebDatas.size(), System.currentTimeMillis() - pre);
        return spiderWebDatas;
    }

    private List<SpiderWebData> spiderOneTag(Document document,
                                             List<String> tags,
                                             List<String> keyWords,
                                             DataType type) {
        List<SpiderWebData> spiderWebDatas = Lists.newArrayList();
        for (String tag : tags) {
            Elements elements = document.getElementsByTag(tag);
            elements.forEach(element -> spiderWebDatas.add(checkData(element, keyWords, type)));
        }
        return spiderWebDatas;
    }

    private SpiderWebData checkData(Element element, List<String> keyWords, DataType type) {
        String context = element.html();
        if (skipCheck(type)) {
            return parseData(element, type);
        }

        for (String keyword : keyWords) {
            if (context.toLowerCase().contains(keyword)) {
                return parseData(element, type);
            }
        }
        return null;
    }

    @Override
    protected boolean skipCheck(DataType type) {
        return false;
    }

    @Override
    protected HtmlRule parseRule(String ruleJson) {
        try {
            return objectMapper.readValue(ruleJson, HtmlRule.class);
        } catch (IOException e) {
            log.error("fail parse rule = {}, case {}", ruleJson, Throwables.getStackTraceAsString(e));
            throw new RuntimeException("rule.parse.fail");
        }
    }

    /**
     * 解析数据
     *
     * @param element 源数据
     * @param type    类型
     * @return 数据
     */
    protected SpiderWebData parseData(Element element, DataType type) {
        SpiderWebData data;
        switch (type) {
            case TEXT:
                data = getText(element);
                break;
            case VIDEO:
                data = getVideos(element);
                break;
            case IMAGE:
                data = getImages(element);
                break;
            case TAG:
                data = getSubTags(element);
                break;
            default:
                data = null;
                break;
        }
        if (data != null) {
            data.setType(type.getValue());
        }
        return data;
    }

    protected SpiderWebData getVideos(Element element) {
        SpiderWebData data = new SpiderWebData();
        data.setUrl(element.attr("source"));
        data.setContent(element.attr("source"));
        return data;
    }

    protected SpiderWebData getImages(Element element) {
        SpiderWebData data = new SpiderWebData();
        data.setUrl(element.attr("src"));
        data.setContent(element.attr("src"));
        return data;
    }

    protected SpiderWebData getText(Element element) {
        SpiderWebData data = new SpiderWebData();
        String textContent = element.outerHtml();
        data.setContent(HtmlUtils.splitAndFilterString(textContent, textContent.length()));
        if (element.hasAttr("href")) {
            data.setUrl(element.attr("href"));
        }
        if (data.getUrl().matches("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?")
                || data.getUrl().matches("(/[a-zA-Z0-9\\&%_\\./-~-]*)[?]{0,1}")) {
            return data;
        }
        return null;
    }

    protected SpiderWebData getSubTags(Element element) {
        SpiderWebData data = new SpiderWebData();
        String textContent = element.outerHtml();
        data.setUrl(element.attr("src"));
        data.setContent(textContent);
        return data;
    }

}
