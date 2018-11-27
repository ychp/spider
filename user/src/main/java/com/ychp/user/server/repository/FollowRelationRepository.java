package com.ychp.user.server.repository;

import com.google.common.collect.ImmutableMap;
import com.ychp.user.model.FollowRelation;
import com.ychp.mybatis.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
* @author yingchengpeng
* @date 2018/10/01
*/
@Repository
public class FollowRelationRepository extends BaseRepository<FollowRelation, Long> {

    public Boolean delete(Long followeeId, Long followerId) {
        return getSqlSession().delete(sqlId("delete"),
                ImmutableMap.of("followeeId", followeeId, "followerId", followerId)) == 1;
    }
}