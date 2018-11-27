package com.ychp.user.server.repository;

import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.user.model.User;
import org.springframework.stereotype.Repository;

/**
* @author yingchengpeng
* @date 2018/08/08
*/
@Repository
public class UserRepository extends BaseRepository<User, Long> {

    public User findByName(String name) {
        return getSqlSession().selectOne(sqlId("findByName"), name);
    }
}