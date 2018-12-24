package com.ychp.spider.parser;

import com.google.common.collect.Maps;
import com.ychp.common.util.HttpClientUtil;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.parser.data.SpiderWebData;
import com.ychp.spider.parser.rule.BaseRule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

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
        BaseRule rule = parseRule(ruleJson);
        return spider(url, rule);
    }

    /**
     * 数据抓取
     * @param url 地址
     * @param rule 规则
     * @return 抓取数据
     */
    public abstract List<SpiderWebData> spider(String url, BaseRule rule);

    protected String getWebContext(String url, Map<String, String> headers) {
        String responseText;
        try {
            responseText = HttpClientUtil.get(url, Maps.newHashMap(), false,
                    true, null, headers);
        } catch (Exception e) {
            log.error("parse url = {} fail, case {}", url, e.getMessage());
            return null;
        }
        return responseText;
    }

    /**
     * 是否跳过校验
     *
     * @param type 类型
     * @return 是否跳过
     */
    protected abstract boolean skipCheck(DataType type);


    /**
     * 解析规则类型
     *
     * @param ruleJson 规则字符串
     * @return 规则
     */
    protected abstract BaseRule parseRule(String ruleJson);

}
