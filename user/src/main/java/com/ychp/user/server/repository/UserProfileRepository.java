package com.ychp.user.server.repository;

import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.user.model.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author yingchengpeng
* @date 2018/08/09
*/
@Repository
public class UserProfileRepository extends BaseRepository<UserProfile, Long> {

    public UserProfile findByUserId(Long userId) {
        return getSqlSession().selectOne(sqlId("findByUserId"), userId);
    }

    public List<UserProfile> findByUserIds(List<Long> userIds) {
        return getSqlSession().selectList(sqlId("findByUserIds"), userIds);
    }
}