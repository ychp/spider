package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.server.repository.DeviceInfoRepository;
import com.ychp.user.model.DeviceInfo;
import com.ychp.user.api.service.DeviceInfoWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class DeviceInfoWriteServiceImpl implements DeviceInfoWriteService {

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Override
    public Long create(DeviceInfo deviceInfo) {
        try {
            deviceInfoRepository.create(deviceInfo);
            return deviceInfo.getId();
        } catch (Exception e) {
            throw new ResponseException("device.info.create.fail", e.getMessage(), e.getCause());
        }
    }
}
