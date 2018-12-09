package com.ychp.spider.service;


import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.TaskCriteria;
import com.ychp.spider.bean.response.TaskDetailInfo;
import com.ychp.spider.bean.response.TaskInfo;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface TaskReadService {

    /**
     * 查询爬虫
     *
     * @param id 编号
     * @return 爬虫
     */
    TaskDetailInfo findOneById(Long id);

    /**
     * 分页获取爬虫
     *
     * @param criteria 条件
     * @return 爬虫分页
     */
    Paging<TaskInfo> paging(TaskCriteria criteria);

}
