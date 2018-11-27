package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.api.service.FollowRelationWriteService;
import com.ychp.user.model.FollowRelation;
import com.ychp.user.server.repository.FollowRelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class FollowRelationWriteServiceImpl implements FollowRelationWriteService {

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @Override
    public Long create(FollowRelation followRelation) {
        try {
            followRelationRepository.create(followRelation);
            return followRelation.getId();
       } catch (Exception e) {
            throw new ResponseException("followRelation.create.fail", e.getMessage(), e.getCause());
       }
   }

    @Override
    public Boolean update(FollowRelation followRelation) {
        try {
            return followRelationRepository.update(followRelation);
       } catch (Exception e) {
            throw new ResponseException("followRelation.update.fail", e.getMessage(), e.getCause());
       }
   }

    @Override
    public Boolean delete(Long followeeId, Long followerId) {
        try {
            return followRelationRepository.delete(followeeId, followerId);
       } catch (Exception e) {
            throw new ResponseException("flowRelation.delete.fail", e.getMessage(), e.getCause());
       }
   }

}
