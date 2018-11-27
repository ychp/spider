package com.ychp.user.cache;

import com.ychp.cache.annontation.DataCache;
import com.ychp.user.api.service.UserReadService;
import com.ychp.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yingchengpeng
 * @date 2018/10/1
 */
@Component
public class UserCacher {

    @Autowired
    private UserReadService userReadService;

    @DataCache("user:{{id}}")
    public User findById(Long id) {
        return userReadService.findById(id);
    }

}
