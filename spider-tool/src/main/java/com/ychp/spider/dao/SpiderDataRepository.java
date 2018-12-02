package com.ychp.spider.dao;

import com.google.common.collect.ImmutableMap;
import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.spider.model.SpiderData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Repository
public class SpiderDataRepository extends BaseRepository<SpiderData, Long> {

    public List<Long> findFirstIds() {
        return getSqlSession().selectList(sqlId("findFirstIds"));
    }

    public Map<Long, Map<String, Object>> countByRule() {
        return getSqlSession().selectMap(sqlId("countByRule"), "rule_id");
    }

    public Integer countByContent(String content) {
        return getSqlSession().selectOne(sqlId("countByContent"), content);
    }

    public void updateStatus(Long id, Integer status) {
        getSqlSession().update(sqlId("updateStatus"), ImmutableMap.of("id", id, "status", status));
    }

    public void deleteBy(Long ruleId) {
        getSqlSession().delete(sqlId("deleteBy"), ruleId);
    }

    public SpiderData findBy(String content, String url, Long ruleId) {
        return getSqlSession().selectOne(sqlId("findBy"),
                ImmutableMap.of("content", content, "url", url, "ruleId", ruleId));
    }

    public List<SpiderData> findByRuleId(Long ruleId) {
        return getSqlSession().selectList(sqlId("findByRuleId"), ruleId);
    }

    public Integer count(Map<String, Object> criteria) {
        return getSqlSession().selectOne(sqlId("count"), criteria);
    }
}
