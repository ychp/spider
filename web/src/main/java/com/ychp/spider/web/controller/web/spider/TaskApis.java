package com.ychp.spider.web.controller.web.spider;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.enums.TaskStatus;
import com.ychp.spider.model.Task;
import com.ychp.spider.service.TaskWriteService;
import com.ychp.spider.web.async.TaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Slf4j
@RestController
@RequestMapping("/api/task")
public class TaskApis {

    @Resource
    private TaskWriteService taskWriteService;

    @Resource
    private AsyncPublisher asyncPublisher;

    /**
     * 创建
     */
    @PostMapping
    public Boolean create(@RequestBody Task task) {
        task.setUserId(SessionContextUtils.getUserId());
        task.setStatus(TaskStatus.INIT.getValue());
        return taskWriteService.create(task);
    }

    /**
     * 抓取
     */
    @PutMapping("/{id}")
    public Boolean process(@PathVariable("id") Long id) {
        Boolean result = taskWriteService.updateStatus(id, TaskStatus.PROCESS.getValue());
        asyncPublisher.post(new TaskEvent(id));
        return result;
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return taskWriteService.delete(id);
    }

}
