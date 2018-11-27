package com.ychp.user.server.service;

import com.google.common.collect.Lists;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.SeeLogCriteria;
import com.ychp.user.api.bean.response.SeeLogVO;
import com.ychp.user.server.repository.DeviceInfoRepository;
import com.ychp.user.server.repository.IpInfoRepository;
import com.ychp.user.server.repository.SeeLogRepository;
import com.ychp.user.model.DeviceInfo;
import com.ychp.user.model.IpInfo;
import com.ychp.user.model.SeeLog;
import com.ychp.user.api.service.SeeLogReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class SeeLogReadServiceImpl implements SeeLogReadService {

    @Autowired
    private SeeLogRepository seeLogRepository;

    @Autowired
    private IpInfoRepository ipInfoRepository;

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Override
    public Paging<SeeLogVO> paging(SeeLogCriteria criteria) {
        Paging<SeeLog> seeLogPaging;
        try {
            seeLogPaging = seeLogRepository.paging(criteria.toMap());
        } catch (Exception e) {
            throw new ResponseException("see.log.paging.fail", e.getMessage(), e.getCause());
        }

        if(seeLogPaging.isEmpty()) {
            return Paging.empty();
        }

        List<SeeLog> seeLogs = seeLogPaging.getDatas();

        List<String> ips = seeLogs.stream().map(SeeLog::getIp).collect(Collectors.toList());

        List<IpInfo> ipInfos;

        try {
            ipInfos = ipInfoRepository.findByIps(ips);
        } catch (Exception e) {
            throw new ResponseException("ip.info.find.fail", e.getMessage(), e.getCause());
        }

        Map<String, IpInfo> ipInfoByIp = ipInfos.stream()
                .collect(Collectors.toMap(IpInfo::getIp, ipInfo -> ipInfo));

        List<Long> deviceIds = seeLogs.stream().map(SeeLog::getDeviceId).collect(Collectors.toList());

        List<DeviceInfo> deviceInfos;

        try {
            deviceInfos = deviceInfoRepository.findByIds(deviceIds);
        } catch (Exception e) {
            throw new ResponseException("device.info.find.fail", e.getMessage(), e.getCause());
        }

        Map<Long, DeviceInfo> deviceInfoById = deviceInfos.stream()
                .collect(Collectors.toMap(DeviceInfo::getId, deviceInfo -> deviceInfo));


        List<SeeLogVO> result = Lists.newArrayListWithCapacity(seeLogs.size());

        for (SeeLog seeLog : seeLogs) {
            result.add(new SeeLogVO(seeLog, ipInfoByIp.get(seeLog.getIp()), deviceInfoById.get(seeLog.getDeviceId())));
        }
        return new Paging<>(seeLogPaging.getTotal(), result);
    }
}