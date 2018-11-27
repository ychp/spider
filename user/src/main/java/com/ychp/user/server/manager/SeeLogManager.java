package com.ychp.user.server.manager;

import com.ychp.user.server.repository.DeviceInfoRepository;
import com.ychp.user.server.repository.IpInfoRepository;
import com.ychp.user.server.repository.SeeLogRepository;
import com.ychp.user.model.DeviceInfo;
import com.ychp.user.model.IpInfo;
import com.ychp.user.model.SeeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
@Component
public class SeeLogManager {

	@Autowired
	private SeeLogRepository seeLogRepository;

	@Autowired
	private IpInfoRepository ipInfoRepository;

	@Autowired
	private DeviceInfoRepository deviceInfoRepository;

	@Transactional(rollbackFor = Exception.class)
	public Long create(SeeLog seeLog, IpInfo ipInfo, DeviceInfo deviceInfo) {
		if(ipInfo != null) {
			ipInfoRepository.create(ipInfo);
		}

		if(deviceInfo != null) {
			deviceInfoRepository.create(deviceInfo);
			seeLog.setDeviceId(deviceInfo.getId());
		}

		seeLogRepository.create(seeLog);
		return seeLog.getId();
	}
}
