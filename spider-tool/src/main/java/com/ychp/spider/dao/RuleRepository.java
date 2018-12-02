package com.ychp.spider.dao;

import com.google.common.collect.ImmutableMap;
import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.spider.model.Rule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Repository
public class RuleRepository extends BaseRepository<Rule, Long> {

    public void updateStatus(@Param("id") Long id, @Param("status") Integer status) {
        getSqlSession().update(sqlId("updateStatus"), ImmutableMap.of("id", id, "status", status));
    }

    public void updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") Integer status) {
        getSqlSession().update(sqlId("updateStatusByIds"), ImmutableMap.of("ids", ids, "status", status));
    }

    public void updateCount(Long id, Integer image, Integer video, Integer text) {
        getSqlSession().update(sqlId("updateCount"), ImmutableMap.of("id", id,
                "imageCount", image, "videoCount", video,
                "textCount", text));
    }

    public Rule findByCode(String code) {
        return getSqlSession().selectOne(sqlId("findByCode"), code);
    }

}
