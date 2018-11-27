package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.api.bean.request.SeeLogCreateRequest;
import com.ychp.user.server.manager.SeeLogManager;
import com.ychp.user.api.service.SeeLogWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class SeeLogWriteServiceImpl implements SeeLogWriteService {

    @Autowired
    private SeeLogManager seeLogManager;

    @Override
    public Long create(SeeLogCreateRequest request) {
        try {
            return seeLogManager.create(request.getSeeLog(), request.getIpInfo(), request.getDeviceInfo());
        } catch (Exception e) {
            throw new ResponseException("seeLog.create.fail", e.getMessage(), e.getCause());
        }
    }
}
