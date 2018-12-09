package com.ychp.spider.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ychp.common.util.HtmlUtils;
import com.ychp.common.util.HttpClientUtil;
import com.ychp.spider.dto.SpiderRule;
import com.ychp.spider.dto.SpiderWebData;
import com.ychp.spider.enums.DataType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
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

        String pagingKey = rule.getPagingKey();
        String offsetKey = rule.getOffsetKey();
        int limit = rule.getLimit() == null ? 20 : rule.getLimit();
        String firstKey = rule.getFirstPageKey();
        boolean isPaging = false;
        boolean isOffset = false;
        boolean hasFirstKey = !StringUtils.isEmpty(firstKey);
        if (!StringUtils.isEmpty(pagingKey) && url.contains(pagingKey)) {
            isPaging = true;
        }
        if (!StringUtils.isEmpty(offsetKey) && url.contains(offsetKey)) {
            isOffset = true;
        }
        Set<SpiderWebData> datas = Sets.newHashSet();

        if ((!isOffset && !isPaging)) {
            Document document = getWebContext(url);
            if (document == null) {
                return Lists.newArrayListWithCapacity(0);
            }
            return spiderContext(document, rule, url);
        }

        if (isPaging && hasFirstKey) {
            int pageNo = 1;
            while (true) {
                String finalUrl = url.replace(pagingKey, pageNo + "");
                Document document = getWebContext(finalUrl);
                if (document == null) {
                    return Lists.newArrayListWithCapacity(0);
                }
                datas.addAll(spiderContext(document, rule, finalUrl));
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
                Document document = getWebContext(finalUrl);
                if (document == null) {
                    return Lists.newArrayListWithCapacity(0);
                }
                if (offset > 0 && document.html().contains(firstKey)) {
                    break;
                }
                datas.addAll(spiderContext(document, rule, finalUrl));
                offset += limit;
            }
        }

        return Lists.newArrayList(datas);
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
                .filter(spiderWebData -> !spiderWebData.getUrl().startsWith("/"))
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

    /**
     * 解析规则类型
     *
     * @param ruleJson 规则字符串
     * @return 规则
     */
    protected abstract SpiderRule parseRule(String ruleJson);
}
