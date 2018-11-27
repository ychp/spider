package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.api.service.UserSummaryWriteService;
import com.ychp.user.model.UserSummary;
import com.ychp.user.server.repository.UserSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class UserSummaryWriteServiceImpl implements UserSummaryWriteService {

    @Autowired
    private UserSummaryRepository userSummaryRepository;

    @Override
    public Long create(UserSummary userSummary) {
        try {
            userSummaryRepository.create(userSummary);
            return userSummary.getId();
       } catch (Exception e) {
            throw new ResponseException("userSummary.create.fail", e.getMessage(), e.getCause());
       }
   }

    @Override
    public Boolean increase(UserSummary userSummary) {
        try {
            return userSummaryRepository.increase(userSummary);
       } catch (Exception e) {
            throw new ResponseException("userSummary.increase.fail", e.getMessage(), e.getCause());
       }
   }

}
