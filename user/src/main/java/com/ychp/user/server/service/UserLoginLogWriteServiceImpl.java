package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.server.repository.UserLoginLogRepository;
import com.ychp.user.model.UserLoginLog;
import com.ychp.user.api.service.UserLoginLogWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class UserLoginLogWriteServiceImpl implements UserLoginLogWriteService {

    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Override
    public Long create(UserLoginLog userLoginLog) {
        try {
            userLoginLogRepository.create(userLoginLog);
            return userLoginLog.getId();
        } catch (Exception e) {
            throw new ResponseException("userLoginLog.create.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public Boolean logout(Long id) {
        try {
            return userLoginLogRepository.logout(id);
        } catch (Exception e) {
            throw new ResponseException("userLoginLog.logout.fail", e.getMessage(), e.getCause());
        }
    }

}
