package com.ychp.user.server.repository;

import com.ychp.user.model.UserSummary;
import com.ychp.mybatis.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
* @author yingchengpeng
* @date 2018/10/02
*/
@Repository
public class UserSummaryRepository extends BaseRepository<UserSummary, Long> {

    public Boolean increase(UserSummary summary) {
        return getSqlSession().update(sqlId("increase"), summary) == 1;
    }

    public UserSummary findByUserId(Long userId) {
        return getSqlSession().selectOne(sqlId("findByUserId"), userId);
    }
}