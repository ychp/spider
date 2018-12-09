package com.ychp.spider.service;


import com.ychp.spider.model.Task;

/**
 * @author yingchengpeng
 * @date 2018-12-09
 */
public interface TaskWriteService {

    Boolean create(Task task);

    Boolean updateStatus(Long id, Integer status);

    Boolean update(Task task);

    Boolean delete(Long id);
}
