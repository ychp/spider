package com.ychp.spider.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.ychp.common.util.HtmlUtils;
import com.ychp.spider.dto.SpiderRule;
import com.ychp.spider.dto.SpiderWebData;
import com.ychp.spider.enums.DataType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Component
public class DefaultParser extends BaseParser {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String getType() {
        return "default";
    }

    @Override
    protected boolean skipCheck(DataType type) {
        return false;
    }

    @Override
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

    @Override
    protected SpiderRule parseRule(String ruleJson) {
        try {
            return objectMapper.readValue(ruleJson, SpiderRule.class);
        } catch (IOException e) {
            log.error("fail parse rule = {}, case {}", ruleJson, Throwables.getStackTraceAsString(e));
            throw new RuntimeException("rule.parse.fail");
        }
    }

}
