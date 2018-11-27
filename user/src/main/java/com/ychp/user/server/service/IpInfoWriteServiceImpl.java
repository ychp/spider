package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.server.repository.IpInfoRepository;
import com.ychp.user.model.IpInfo;
import com.ychp.user.api.service.IpInfoWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class IpInfoWriteServiceImpl implements IpInfoWriteService {

    @Autowired
    private IpInfoRepository ipInfoRepository;

    @Override
    public Long create(IpInfo ipInfo) {
        try {
            ipInfoRepository.create(ipInfo);
            return ipInfo.getId();
        } catch (Exception e) {
            throw new ResponseException("ip.info.create.fail", e.getMessage(), e.getCause());
        }
    }
}
