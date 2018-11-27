package com.ychp.user.server.repository;

import com.google.common.collect.ImmutableMap;
import com.ychp.mybatis.repository.BaseRepository;
import com.ychp.user.model.DeviceInfo;
import org.springframework.stereotype.Repository;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
@Repository
public class DeviceInfoRepository extends BaseRepository<DeviceInfo, Long> {

	public DeviceInfo findByUniqueInfo(DeviceInfo deviceInfo) {
		return getSqlSession().selectOne(sqlId("findByUniqueInfo"),
				ImmutableMap.of("os", deviceInfo.getOs(), "browser", deviceInfo.getBrowser(),
						"browserVersion", deviceInfo.getBrowserVersion(), "device", deviceInfo.getDevice()));
	}
}