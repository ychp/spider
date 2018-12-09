package com.ychp.spider.manager;

import com.ychp.spider.repository.SpiderDataRepository;
import com.ychp.spider.repository.TaskRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
@Component
public class TaskManager {

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private SpiderDataRepository spiderDataRepository;

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        taskRepository.delete(id);
        spiderDataRepository.deleteBy(id);
    }
}
