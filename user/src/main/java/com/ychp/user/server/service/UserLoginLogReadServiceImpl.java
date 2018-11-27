package com.ychp.user.server.service;

import com.google.common.collect.Lists;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.response.UserLoginLogVO;
import com.ychp.user.api.bean.query.UserLoginLogCriteria;
import com.ychp.user.model.User;
import com.ychp.user.model.UserLoginLog;
import com.ychp.user.server.repository.UserLoginLogRepository;
import com.ychp.user.server.repository.UserRepository;
import com.ychp.user.api.service.UserLoginLogReadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class UserLoginLogReadServiceImpl implements UserLoginLogReadService {

    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Paging<UserLoginLogVO> paging(UserLoginLogCriteria criteria) {
        try {
            Paging<UserLoginLog> paging = userLoginLogRepository.paging(criteria.toMap());
            if(paging.isEmpty()) {
                return Paging.empty();
            }
            List<UserLoginLog> logs = paging.getDatas();

            List<Long> userIds = logs.stream().map(UserLoginLog::getUserId).collect(Collectors.toSet())
                    .stream().collect(Collectors.toList());

            List<User> users = userRepository.findByIds(userIds);
            Map<Long, User> userById = users.stream().collect(Collectors.toMap(User::getId, user -> user));

            List<UserLoginLogVO> finalLogs = Lists.newArrayListWithCapacity(logs.size());

            for(UserLoginLog log : logs) {
                UserLoginLogVO logVO = new UserLoginLogVO();
                BeanUtils.copyProperties(log, logVO);
                User user = userById.get(log.getUserId());
                logVO.setName(user.getName());
                logVO.setNickName(user.getNickName());
                finalLogs.add(logVO);
            }
            return new Paging<>(paging.getTotal(), finalLogs);
        } catch (Exception e) {
            throw new ResponseException("userLoginLog.paging.fail", e.getMessage(), e.getCause());
        }
    }

}