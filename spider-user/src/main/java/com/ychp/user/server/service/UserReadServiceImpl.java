package com.ychp.user.server.service;

import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.Encryption;
import com.ychp.user.api.bean.query.UserCriteria;
import com.ychp.user.api.service.UserReadService;
import com.ychp.user.enums.UserStatusEnum;
import com.ychp.user.model.User;
import com.ychp.user.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Service
public class UserReadServiceImpl implements UserReadService {

    @Autowired
    private UserRepository userRepository;

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
    public User findDetailById(Long id) {
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

}
