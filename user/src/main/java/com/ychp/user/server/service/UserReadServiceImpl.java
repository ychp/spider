package com.ychp.user.server.service;

import com.google.common.collect.Lists;
import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.Encryption;
import com.ychp.user.api.bean.query.UserCriteria;
import com.ychp.user.api.bean.response.UserVO;
import com.ychp.user.enums.UserStatusEnum;
import com.ychp.user.api.converter.UserConverter;
import com.ychp.user.server.repository.UserProfileRepository;
import com.ychp.user.server.repository.UserRepository;
import com.ychp.user.model.User;
import com.ychp.user.model.UserProfile;
import com.ychp.user.api.service.UserReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Service
public class UserReadServiceImpl implements UserReadService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public User findById(Long id) {
        if(id == null) {
            throw new InvalidException("user.id.empty", "id", id);
        }
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseException("user.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> findByIds(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return Lists.newArrayListWithCapacity(0);
        }
        try {
            return userRepository.findByIds(ids);
        } catch (Exception e) {
            throw new ResponseException("user.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public UserVO findDetailById(Long id) {
        if(id == null) {
            throw new InvalidException("user.id.empty", "id", id);
        }
        try {
            User user = userRepository.findById(id);
            UserProfile profile = userProfileRepository.findByUserId(id);
            return UserConverter.convertToDetail(user, profile);
        } catch (Exception e) {
            throw new ResponseException("user.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public User login(String name, String password) {
        try {
            User user = userRepository.findByName(name);
            if(user == null) {
                throw new ResponseException("user.login.fail");
            }

            if(!Objects.equals(user.getStatus(), UserStatusEnum.NORMAL.getValue())) {
                throw new ResponseException("user.has.frozen");
            }

            if(Encryption.checkPassword(password, user.getSalt(), user.getPassword())) {
                return user;
            }
            return null;
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseException("user.login.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public Paging<User> paging(UserCriteria criteria) {
        try {
            return userRepository.paging(criteria.toMap());
        } catch (Exception e) {
            throw new ResponseException("user.paging.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public User findByName(String name) {
        try {
            return userRepository.findByName(name);
        } catch (Exception e) {
            throw new ResponseException("user.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<UserProfile> findProfileByIds(List<Long> userIds) {
        if(CollectionUtils.isEmpty(userIds)) {
            return Lists.newArrayListWithCapacity(0);
        }
        try {
            return userProfileRepository.findByUserIds(userIds);
        } catch (Exception e) {
            throw new ResponseException("user.profile.find.fail", e.getMessage(), e.getCause());
        }
    }
}
