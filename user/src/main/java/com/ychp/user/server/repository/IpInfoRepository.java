package com.ychp.user.server.repository;

import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.user.model.IpInfo;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
@Repository
public class IpInfoRepository extends BaseRepository<IpInfo, Long> {

	/**
	 * 查询单个对象
	 * @param ip ip地址
	 * @return 对象
	 */
	public IpInfo findByIp(String ip){
		return getSqlSession().selectOne(sqlId("findByIp"), ip);
	}

	/**
	 * 批量查询
	 * @param ips ip列表
	 * @return
	 */
	public List<IpInfo> findByIps(List<String> ips){
		if (ips == null || ips.isEmpty()) {
			return Collections.emptyList();
		}
		return getSqlSession().selectList(sqlId("findByIps"), ips);
	}
}