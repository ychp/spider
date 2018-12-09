package com.ychp.spider.repository;

import com.google.common.collect.ImmutableMap;
import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.spider.model.SpiderData;
import org.springframework.stereotype.Repository;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Repository
public class SpiderDataRepository extends BaseRepository<SpiderData, Long> {

    public void updateStatus(Long id, Integer status) {
        getSqlSession().update(sqlId("updateStatus"), ImmutableMap.of("id", id, "status", status));
    }

    public void deleteBy(Long taskId) {
        getSqlSession().delete(sqlId("deleteBy"), taskId);
    }

}
