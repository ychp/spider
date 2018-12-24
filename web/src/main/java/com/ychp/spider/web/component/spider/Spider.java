package com.ychp.spider.web.component.spider;

import com.ychp.spider.bean.response.ParserDetailInfo;
import com.ychp.spider.bean.response.TaskDetailInfo;
import com.ychp.spider.enums.TaskStatus;
import com.ychp.spider.parser.BaseParser;
import com.ychp.spider.parser.core.ParserRegistry;
import com.ychp.spider.service.ParserReadService;
import com.ychp.spider.service.TaskReadService;
import com.ychp.spider.service.TaskWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    public void spider(Long taskId) {
        TaskDetailInfo taskInfo = taskReadService.findOneById(taskId);
        log.info("start spider task[id={}, node={}]", taskId, taskInfo.getParserId());
        long pre = System.currentTimeMillis();

        ParserDetailInfo parserDetailInfo = parserReadService.findOneById(taskInfo.getParserId());

        BaseParser parser = parserRegistry.getParser(parserDetailInfo.getParserTypeKey());

        parser.spider(taskInfo);

        taskWriteService.updateStatus(taskId, TaskStatus.FINISH.getValue());
        log.info("finish spider task[id={}, node={}], cost {} ms",
                taskId, taskInfo.getParserId(), System.currentTimeMillis() - pre);

    }

}
