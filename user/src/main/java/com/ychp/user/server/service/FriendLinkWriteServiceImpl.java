package com.ychp.user.server.service;

import com.ychp.common.exception.ResponseException;
import com.ychp.user.server.repository.FriendLinkRepository;
import com.ychp.user.model.FriendLink;
import com.ychp.user.api.service.FriendLinkWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class FriendLinkWriteServiceImpl implements FriendLinkWriteService {

    @Autowired
    private FriendLinkRepository friendLinkRepository;

    @Override
    public Long create(FriendLink friendLink) {
        try {
            friendLinkRepository.create(friendLink);
            return friendLink.getId();
        } catch (Exception e) {
            throw new ResponseException("friendLink.create.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public Boolean update(FriendLink friendLink) {
        try {
            return friendLinkRepository.update(friendLink);
        } catch (Exception e) {
            throw new ResponseException("friendLink.update.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            return friendLinkRepository.delete(id);
        } catch (Exception e) {
            throw new ResponseException("friendLink.delete.fail", e.getMessage(), e.getCause());
        }
    }

}
