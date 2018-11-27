package com.ychp.user.server.repository;

import com.ychp.user.model.Address;
import com.ychp.mybatis.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author yingchengpeng
* @date 2018/08/10
*/
@Repository
public class AddressRepository extends BaseRepository<Address, Long> {

	public List<Address> findByPid(Long pid) {
		return getSqlSession().selectList(sqlId("findByPid"), pid);
	}
}