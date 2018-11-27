package com.ychp.user.server.service;

import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.FriendLinkCriteria;
import com.ychp.user.server.repository.FriendLinkRepository;
import com.ychp.user.model.FriendLink;
import com.ychp.user.api.service.FriendLinkReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class FriendLinkReadServiceImpl implements FriendLinkReadService {

    @Autowired
    private FriendLinkRepository friendLinkRepository;

    @Override
    public FriendLink findById(Long id) {
        if(id == null) {
            throw new InvalidException("friendLink.id.empty", "id", id);
        }
        try {
            return friendLinkRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseException("friendLink.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public Paging<FriendLink> paging(FriendLinkCriteria criteria) {
        try {
            return friendLinkRepository.paging(criteria.toMap());
        } catch (Exception e) {
            throw new ResponseException("friendLink.paging.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<FriendLink> listVisible() {
        try {
            FriendLinkCriteria criteria = new FriendLinkCriteria();
            criteria.setVisible(true);
            return friendLinkRepository.list(criteria.toMap());
        } catch (Exception e) {
            throw new ResponseException("friendLink.paging.fail", e.getMessage(), e.getCause());
        }
    }

}