package com.ychp.user.server.service;

import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.UserSummaryCriteria;
import com.ychp.user.api.service.UserSummaryReadService;
import com.ychp.user.model.UserSummary;
import com.ychp.user.server.repository.UserSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class UserSummaryReadServiceImpl implements UserSummaryReadService {

    @Autowired
    private UserSummaryRepository userSummaryRepository;

    @Override
    public UserSummary findByUserId(Long userId) {
        if(userId == null) {
            throw new InvalidException("userSummary.userId.empty", "userId", userId);
       }
        try {
            return userSummaryRepository.findByUserId(userId);
       } catch (Exception e) {
            throw new ResponseException("userSummary.find.fail", e.getMessage(), e.getCause());
       }
   }

    @Override
    public Paging<UserSummary> paging(UserSummaryCriteria criteria) {
        try {
            return userSummaryRepository.paging(criteria.toMap());
       } catch (Exception e) {
            throw new ResponseException("userSummary.paging.fail", e.getMessage(), e.getCause());
       }
   }

}