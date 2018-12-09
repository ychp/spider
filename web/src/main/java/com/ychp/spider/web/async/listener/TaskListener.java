package com.ychp.spider.web.async.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.spider.web.async.TaskEvent;
import com.ychp.spider.web.component.spider.Spider;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Slf4j
@AsyncBean
public class TaskListener {

    @Resource
    private Spider spider;

    @AsyncSubscriber
    public void spider(TaskEvent taskEvent) {
        Long taskId = taskEvent.getTaskId();
        spider.spider(taskId);
    }

}
