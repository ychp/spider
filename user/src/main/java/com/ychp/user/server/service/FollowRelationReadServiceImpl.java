package com.ychp.user.server.service;

import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.FollowRelationCriteria;
import com.ychp.user.api.service.FollowRelationReadService;
import com.ychp.user.model.FollowRelation;
import com.ychp.user.server.repository.FollowRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class FollowRelationReadServiceImpl implements FollowRelationReadService {

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @Override
    public FollowRelation findById(Long id) {
        if(id == null) {
            throw new InvalidException("flowRelation.id.empty", "id", id);
       }
        try {
            return followRelationRepository.findById(id);
       } catch (Exception e) {
            throw new ResponseException("flowRelation.find.fail", e.getMessage(), e.getCause());
       }
   }

    @Override
    public Paging<FollowRelation> paging(FollowRelationCriteria criteria) {
        try {
            return followRelationRepository.paging(criteria.toMap());
       } catch (Exception e) {
            throw new ResponseException("flowRelation.paging.fail", e.getMessage(), e.getCause());
       }
   }

}