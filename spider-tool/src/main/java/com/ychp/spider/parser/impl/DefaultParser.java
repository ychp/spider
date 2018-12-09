package com.ychp.spider.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.ychp.spider.dto.SpiderRule;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.parser.BaseParser;
import lombok.extern.slf4j.Slf4j;
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
    protected SpiderRule parseRule(String ruleJson) {
        try {
            return objectMapper.readValue(ruleJson, SpiderRule.class);
        } catch (IOException e) {
            log.error("fail parse rule = {}, case {}", ruleJson, Throwables.getStackTraceAsString(e));
            throw new RuntimeException("rule.parse.fail");
        }
    }

}
