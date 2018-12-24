package com.ychp.spider.parser;

import com.google.common.collect.Maps;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.util.HttpClientUtil;
import com.ychp.spider.bean.response.TaskDetailInfo;
import com.ychp.spider.enums.DataStatus;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.parser.data.SpiderWebData;
import com.ychp.spider.parser.rule.BaseRule;
import com.ychp.spider.service.SpiderDataWriteService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
public abstract class BaseParser {

    @Resource
    private SpiderDataWriteService spiderDataWriteService;

    /**
     * 获取解析类型
     *
     * @return 类型
     */
    public abstract String getType();

    public void spider(TaskDetailInfo taskInfo) {
        BaseRule rule = parseRule(taskInfo.getSpiderRule());
        spider(taskInfo, rule);
    }

    /**
     * 数据抓取
     *
     * @param taskInfo 任务信息
     * @param rule     规则
     */
    public abstract void spider(TaskDetailInfo taskInfo, BaseRule rule);

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

    protected void save(List<SpiderWebData> spiderDatas, TaskDetailInfo taskInfo) {
        List<SpiderData> datas = convert(spiderDatas, taskInfo);
        if (!spiderDataWriteService.creates(datas)) {
            throw new ResponseException("data.save.fail");
        }
        log.info("save task[id={}, node={}], datas = {}",
                taskInfo.getId(), taskInfo.getParserId(), datas.size());
    }

    private List<SpiderData> convert(List<SpiderWebData> webDatas, TaskDetailInfo taskDetailInfo) {
        return webDatas.stream().map(webData -> {
            SpiderData spiderData = new SpiderData();
            spiderData.setUserId(taskDetailInfo.getUserId());
            spiderData.setParserId(taskDetailInfo.getParserId());
            spiderData.setTaskId(taskDetailInfo.getId());
            spiderData.setContent(webData.getContent());
            spiderData.setUrl(webData.getUrl());
            spiderData.setSource(webData.getSource());
            spiderData.setType(webData.getType());
            spiderData.setPath(webData.getPath());
            spiderData.setLevel(webData.getLevel());
            spiderData.setStatus(DataStatus.INIT.getValue());
            spiderData.setUniqueCode(spiderData.generateUniqueCode());
            return spiderData;
        }).collect(Collectors.toList());
    }

}
