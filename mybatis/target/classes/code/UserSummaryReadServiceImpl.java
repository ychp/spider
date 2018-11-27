package com.ychp.user.server.service;

import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.user.dto.query.UserSummaryCriteria;
import com.ychp.user.model.UserSummary;
import com.ychp.user.impl.server.repository.UserSummaryRepository;
import com.ychp.user.service.UserSummaryReadService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class UserSummaryReadServiceImpl implements UserSummaryReadService {

    @Autowired
    private UserSummaryRepository userSummaryRepository;

    @Override
    public UserSummary findById(Long id) {
        if(id == null) {
            throw new InvalidException("userSummary.id.empty", "id", id);
       }
        try {
            return userSummaryRepository.findById(id);
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