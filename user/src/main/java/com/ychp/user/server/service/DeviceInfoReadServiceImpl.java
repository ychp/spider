package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.cache.annontation.DataCache;
import com.ychp.user.server.repository.DeviceInfoRepository;
import com.ychp.user.model.DeviceInfo;
import com.ychp.user.api.service.DeviceInfoReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class DeviceInfoReadServiceImpl implements DeviceInfoReadService {

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Override
    @DataCache("device-info:{{deviceInfo.os}}-{{deviceInfo.browser}}-{{deviceInfo.browserVersion}}-{{deviceInfo.device}}")
    public DeviceInfo findByUniqueInfo(DeviceInfo deviceInfo) {
        try {
            return deviceInfoRepository.findByUniqueInfo(deviceInfo);
        } catch (Exception e) {
            throw new ResponseException("device.info.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<DeviceInfo> findByIds(List<Long> ids) {
        try {
            return deviceInfoRepository.findByIds(ids);
        } catch (Exception e) {
            throw new ResponseException("device.info.find.fail", e.getMessage(), e.getCause());
        }
    }
}