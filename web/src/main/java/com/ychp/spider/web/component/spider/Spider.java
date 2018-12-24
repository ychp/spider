package com.ychp.spider.web.component.spider;

import com.ychp.common.exception.ResponseException;
import com.ychp.spider.bean.response.ParserDetailInfo;
import com.ychp.spider.bean.response.TaskDetailInfo;
import com.ychp.spider.parser.data.SpiderWebData;
import com.ychp.spider.enums.DataStatus;
import com.ychp.spider.enums.TaskStatus;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.parser.BaseParser;
import com.ychp.spider.parser.core.ParserRegistry;
import com.ychp.spider.service.ParserReadService;
import com.ychp.spider.service.SpiderDataWriteService;
import com.ychp.spider.service.TaskReadService;
import com.ychp.spider.service.TaskWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Component
public class Spider {

    @Resource
    private ParserRegistry parserRegistry;

    @Resource
    private ParserReadService parserReadService;

    @Resource
    private TaskReadService taskReadService;

    @Resource
    private TaskWriteService taskWriteService;

    @Resource
    private SpiderDataWriteService spiderDataWriteService;

    public void spider(Long taskId) {
        TaskDetailInfo taskInfo = taskReadService.findOneById(taskId);
        log.info("start spider task[id={}, node={}]", taskId, taskInfo.getParserId());
        long pre = System.currentTimeMillis();

        ParserDetailInfo parserDetailInfo = parserReadService.findOneById(taskInfo.getParserId());

        BaseParser parser = parserRegistry.getParser(parserDetailInfo.getParserTypeKey());

        spider(taskInfo, parser);

        taskWriteService.updateStatus(taskId, TaskStatus.FINISH.getValue());
        log.info("finish spider task[id={}, node={}], cost {} ms",
                taskId, taskInfo.getParserId(), System.currentTimeMillis() - pre);

    }

    private void spider(TaskDetailInfo taskInfo, BaseParser parser) {
        long pre = System.currentTimeMillis();

        List<SpiderWebData> webDatas = parser.spider(taskInfo.getUrl(), taskInfo.getSpiderRule());
        List<SpiderData> datas = convert(webDatas, taskInfo);
        if (!spiderDataWriteService.creates(datas)) {
            throw new ResponseException("data.save.fail");
        }
        log.info("finish spider task[id={}, node={}], datas = {}, cost {} ms",
                taskInfo.getId(), taskInfo.getParserId(), datas.size(), System.currentTimeMillis() - pre);
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
        })
                .collect(Collectors.toList());
    }

}
