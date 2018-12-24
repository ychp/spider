package com.ychp.spider.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ychp.spider.bean.response.TaskDetailInfo;
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
    public void spider(TaskDetailInfo taskInfo, BaseRule rule) {
        JsonRule jsonRule = (JsonRule) rule;

        if (StringUtils.isEmpty(jsonRule.getDataType())) {
            jsonRule.setDataType(DataType.TEXT.getText());
        }

        if (!jsonRule.getIsPaging()) {
            save(getDatas(taskInfo.getUrl(), jsonRule), taskInfo);
            return;
        }

        List<SpiderWebData> datas = Lists.newArrayList();
        String url = taskInfo.getUrl();
        int pageNo = 1;
        while (true) {
            String finalUrl = url.replace(jsonRule.getPagingKey(), pageNo + "");
            int size = datas.size();
            datas.addAll(getDatas(finalUrl, jsonRule));
            if (datas.isEmpty()) {
                break;
            }
            int afterSize = datas.size();

            if (afterSize == size || datas.size() >= 200) {
                save(datas, taskInfo);
                datas.clear();

                // 本次无数据获取，认定为结束
                if(afterSize == size) {
                    break;
                }
            }

            pageNo++;
        }

    }

    private List<SpiderWebData> getDatas(String url, JsonRule rule) {
        long pre = System.currentTimeMillis();

        String json = getWebContext(url,
                ImmutableMap.of("Accept", "application/json"));
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(rule.getDataKey());

        int size = jsonArray.size();
        List<SpiderWebData> datas = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String content = getContent(item, rule.getContentKeys());

            if (!skipCheck(DataType.fromText(rule.getDataType())) && !containsKeyWords(content, rule.getKeyWord())) {
                continue;
            }

            SpiderWebData data = new SpiderWebData();
            data.setContent(content);
            data.setUrl(rule.getDetailPre() + item.getString(rule.getDetailKey()));
            data.setType(DataType.fromText(rule.getDataType()).getValue());
            datas.add(data);
        }

        log.info("finish spider task[url={}], datas = {}, cost {} ms",
                url, datas.size(), System.currentTimeMillis() - pre);
        return datas;
    }

    private boolean containsKeyWords(String content, List<String> keyWords) {
        if (CollectionUtils.isEmpty(keyWords)) {
            return true;
        }

        String lowContent = content.toLowerCase();
        for (String keyword : keyWords) {
            if (lowContent.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private String getContent(JSONObject item, List<String> keys) {
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
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
