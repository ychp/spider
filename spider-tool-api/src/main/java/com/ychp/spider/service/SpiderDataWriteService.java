package com.ychp.spider.service;


import com.ychp.spider.model.SpiderData;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface SpiderDataWriteService {

    /**
     * 创建数据
     * @param spiderData 爬取的数据
     * @return 操作结果
     */
    Boolean create(SpiderData spiderData);

    /**
     * 批量创建数据
     * @param spiderDatas 爬取的数据
     * @return 操作结果
     */
    Boolean creates(List<SpiderData> spiderDatas);

    /**
     * 更新数据
     * @param spiderData 需要更新的数据
     * @return 操作结果
     */
    Boolean update(SpiderData spiderData);

    /**
     * 更新状态
     * @param id id
     * @param status 状态
     * @return 操作结果
     */
    Boolean updateStatus(Long id, Integer status);

    /**
     * 删除
     * @param id id
     * @return 操作结果
     */
    Boolean delete(Long id);

    /**
     * 删除
     * @param taskId 任务ID
     * @return 操作结果
     */
    Boolean deleteBy(Long taskId);
}
