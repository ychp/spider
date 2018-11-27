package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.model.UserSummary;
import com.ychp.user.impl.server.repository.UserSummaryRepository;
import com.ychp.user.service.UserSummaryWriteService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


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
    public Boolean update(UserSummary userSummary) {
        try {
            return userSummaryRepository.update(userSummary);
       } catch (Exception e) {
            throw new ResponseException("userSummary.update.fail", e.getMessage(), e.getCause());
       }
   }

    @Override
    public Boolean delete(Long id) {
        try {
            return userSummaryRepository.delete(id);
       } catch (Exception e) {
            throw new ResponseException("userSummary.delete.fail", e.getMessage(), e.getCause());
       }
   }

}
