package com.ychp.spider.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ychp.common.util.HttpClientUtil;
import com.ychp.spider.dto.SpiderRule;
import com.ychp.spider.dto.SpiderWebData;
import com.ychp.spider.enums.DataType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
public abstract class BaseParser {

    /**
     * 获取解析类型
     *
     * @return 类型
     */
    public abstract String getType();

    public List<SpiderWebData> spider(String url, String ruleJson) {
        SpiderRule rule = parseRule(ruleJson);

        Document document = getWebContext(url);
        if (document == null) {
            return Lists.newArrayListWithCapacity(0);
        }

        return spiderContext(document, rule, url);
    }

    private Document getWebContext(String url) {
        String html;
        try {
            html = HttpClientUtil.get(url, Maps.newHashMap(), false, true, null);
        } catch (Exception e) {
            log.error("parse url = {} fail, case {}", url, e.getMessage());
            return null;
        }
        html = removeUselessContent(html);
        return Jsoup.parse(html);
    }

    private String removeUselessContent(String html) {
        if (!html.startsWith("<html") || !html.startsWith("<HTML")) {
            if (html.contains("<html")) {
                html = html.substring(html.indexOf("<html"));
            }
            if (html.contains("<HTML")) {
                html = html.substring(html.indexOf("<HTML"));
            }
        }
        return html;
    }

    protected List<SpiderWebData> spiderContext(Document document, SpiderRule rule, String url) {
        List<SpiderWebData> spiderWebDatas = Lists.newArrayList();
        spiderWebDatas.addAll(spiderOneTag(document, rule.getTextTag(), rule.getKeyWord(), DataType.TEXT));
        spiderWebDatas.addAll(spiderOneTag(document, rule.getImageTag(), rule.getKeyWord(), DataType.IMAGE));
        spiderWebDatas.addAll(spiderOneTag(document, rule.getVideoTag(), rule.getKeyWord(), DataType.VIDEO));
        spiderWebDatas.addAll(spiderOneTag(document, rule.getSubTag(), rule.getKeyWord(), DataType.TAG));
        String domain = url.split("\\?")[0];
        String[] domainArr = domain.split("/");
        spiderWebDatas = spiderWebDatas.stream()
                .filter(Objects::nonNull)
                .peek(spiderWebData -> {
                    spiderWebData.setSource(url);
                    if (spiderWebData.getUrl().startsWith("/")) {
                        spiderWebData.setUrl(domainArr[0] + "//" + domainArr[2] + spiderWebData.getUrl());
                    }
                })
                .collect(Collectors.toList());
        return spiderWebDatas;
    }

    protected List<SpiderWebData> spiderOneTag(Document document,
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

    /**
     * 是否跳过校验
     *
     * @param type 类型
     * @return 是否跳过
     */
    protected abstract boolean skipCheck(DataType type);

    /**
     * 解析数据
     *
     * @param element 源数据
     * @param type    类型
     * @return 数据
     */
    protected abstract SpiderWebData parseData(Element element, DataType type);

    /**
     * 解析规则类型
     *
     * @param ruleJson 规则字符串
     * @return 规则
     */
    protected abstract SpiderRule parseRule(String ruleJson);
}
