package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.cache.annontation.DataCache;
import com.ychp.user.server.repository.IpInfoRepository;
import com.ychp.user.model.IpInfo;
import com.ychp.user.api.service.IpInfoReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class IpInfoReadServiceImpl implements IpInfoReadService {

    @Autowired
    private IpInfoRepository ipInfoRepository;

    @Override
    @DataCache("ip-info:{{ip}}")
    public IpInfo findByIp(String ip) {
        try {
            return ipInfoRepository.findByIp(ip);
        } catch (Exception e) {
            throw new ResponseException("ip.info.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<IpInfo> findByIps(List<String> ips) {
        try {
            return ipInfoRepository.findByIps(ips);
        } catch (Exception e) {
            throw new ResponseException("ip.info.find.fail", e.getMessage(), e.getCause());
        }
    }
}