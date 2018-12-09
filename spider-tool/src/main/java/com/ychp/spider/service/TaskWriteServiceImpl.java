package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.spider.manager.TaskManager;
import com.ychp.spider.model.Parser;
import com.ychp.spider.model.Task;
import com.ychp.spider.repository.ParserRepository;
import com.ychp.spider.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
@Slf4j
@Service
public class TaskWriteServiceImpl implements TaskWriteService {

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private ParserRepository parserRepository;

    @Resource
    private TaskManager taskManager;

    @Override
    public Boolean create(Task task) {
        try {
            Parser parser = parserRepository.findById(task.getParserId());
            task.setUrl(parser.getUrl());
            task.setSpiderRule(parser.getSpiderRule());
            return taskRepository.create(task);
        } catch (IllegalArgumentException e) {
            log.error("create task fail, task = {}, error = {}", task, Throwables.getStackTraceAsString(e));
            throw new ResponseException("task.create.fail");
        }
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        Task task = new Task();
        task.setId(id);
        task.setStatus(status);
        return update(task);
    }

    @Override
    public Boolean update(Task task) {
        try {
            return taskRepository.update(task);
        } catch (IllegalArgumentException e) {
            log.error("update task fail, task = {}, error = {}", task, Throwables.getStackTraceAsString(e));
            throw new ResponseException("task.update.fail");
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            checkArgument(id != null, "task.id.not.empty");

            taskManager.delete(id);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("delete task fail, id = {}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        } catch (Exception e) {
            log.error("delete task fail, id = {}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("task.delete.fail");
        }
    }
}
