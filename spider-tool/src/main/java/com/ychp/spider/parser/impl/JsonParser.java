package com.ychp.spider.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.parser.BaseParser;
import com.ychp.spider.parser.data.SpiderWebData;
import com.ychp.spider.parser.enums.ParserTypeEnums;
import com.ychp.spider.parser.rule.BaseRule;
import com.ychp.spider.parser.rule.JsonRule;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Component
public class JsonParser extends BaseParser {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String getType() {
        return ParserTypeEnums.JSON.getValue();
    }

    @Override
    public List<SpiderWebData> spider(String url, BaseRule rule) {
        JsonRule jsonRule = (JsonRule) rule;

        if(StringUtils.isEmpty(jsonRule.getDataType())) {
            jsonRule.setDataType(DataType.TEXT.getText());
        }

        if(!jsonRule.getIsPaging()) {
            return getDatas(url, jsonRule);
        }

        Set<SpiderWebData> datas = Sets.newHashSet();

        int pageNo = 1;
        while (true) {
            String finalUrl = url.replace(jsonRule.getPagingKey(), pageNo + "");
            List<SpiderWebData> results = getDatas(finalUrl, jsonRule);
            if(results.isEmpty()) {
                break;
            }
            datas.addAll(results);
            pageNo ++;
        }

        return Lists.newArrayList(datas);
    }

    private List<SpiderWebData> getDatas(String url, JsonRule rule) {
        String json = getWebContext(url,
                ImmutableMap.of("Accept", "application/json"));
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(rule.getDataKey());

        int size = jsonArray.size();
        List<SpiderWebData> datas = Lists.newArrayListWithCapacity(size);
        for(int i = 0; i < size; i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String content = getContent(item, rule.getContentKeys());

            if(!skipCheck(DataType.fromText(rule.getDataType())) && !containsKeyWords(content, rule.getKeyWord())) {
                continue;
            }

            SpiderWebData data = new SpiderWebData();
            data.setContent(content);
            data.setUrl(rule.getDetailPre() + item.getString(rule.getDetailKey()));
            datas.add(data);
        }

        return datas;
    }

    private boolean containsKeyWords(String content, List<String> keyWords) {
        if(CollectionUtils.isEmpty(keyWords)) {
            return true;
        }

        String lowContent = content.toLowerCase();
        for(String keyword : keyWords) {
            if(lowContent.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private String getContent(JSONObject item, List<String> keys) {
        StringBuilder sb = new StringBuilder();
        for(String key : keys) {
            sb.append(item.getString(key));
        }
        return sb.toString();
    }

    @Override
    protected boolean skipCheck(DataType type) {
        return true;
    }

    @Override
    protected JsonRule parseRule(String ruleJson) {
        try {
            return objectMapper.readValue(ruleJson, JsonRule.class);
        } catch (IOException e) {
            log.error("fail parse rule = {}, case {}", ruleJson, Throwables.getStackTraceAsString(e));
            throw new RuntimeException("rule.parse.fail");
        }
    }

}
