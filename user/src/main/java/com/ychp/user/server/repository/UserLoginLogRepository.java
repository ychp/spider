package com.ychp.user.server.repository;

import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.user.model.UserLoginLog;
import org.springframework.stereotype.Repository;

/**
* @author yingchengpeng
* @date 2018/08/09
*/
@Repository
public class UserLoginLogRepository extends BaseRepository<UserLoginLog, Long> {

	public Boolean logout(Long id) {
		return getSqlSession().update(sqlId("logout"), id) == 1;
	}
}