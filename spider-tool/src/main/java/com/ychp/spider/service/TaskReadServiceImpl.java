package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.TaskCriteria;
import com.ychp.spider.bean.response.TaskDetailInfo;
import com.ychp.spider.bean.response.TaskInfo;
import com.ychp.spider.converter.TaskConverter;
import com.ychp.spider.model.Parser;
import com.ychp.spider.model.Task;
import com.ychp.spider.repository.ParserRepository;
import com.ychp.spider.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
@Slf4j
@Service
public class TaskReadServiceImpl implements TaskReadService {

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private ParserRepository parserRepository;

    @Override
    public TaskDetailInfo findOneById(Long id) {
        try {
            Task task = taskRepository.findById(id);
            if (task == null) {
                return null;
            }
            Parser parser = parserRepository.findById(task.getParserId());

            return TaskConverter.convertDetail(task, parser);
        } catch (Exception e) {
            log.error("find task fail by id={}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("task.find.by.id.fail");
        }
    }

    @Override
    public Paging<TaskInfo> paging(TaskCriteria criteria) {
        try {
            Paging<Task> taskPaging = taskRepository.paging(criteria.toMap());
            if (taskPaging.isEmpty()) {
                return Paging.empty();
            }
            List<Long> parserIds = taskPaging.getDatas().stream()
                    .map(Task::getParserId).collect(Collectors.toList());
            List<Parser> parsers = parserRepository.findByIds(parserIds);
            Map<Long, Parser> parserById = parsers.stream()
                    .collect(Collectors.toMap(Parser::getId, parser -> parser));

            List<TaskInfo> taskInfos = taskPaging.getDatas().stream()
                    .map(task -> TaskConverter.convert(task, parserById.get(task.getParserId())))
                    .collect(Collectors.toList());

            return new Paging<>(taskPaging.getTotal(), taskInfos);
        } catch (Exception e) {
            log.error("paging task fail, params = {}, error = {}", criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("task.paging.fail");
        }
    }
}
