package com.ychp.spider.web.util;

import com.ychp.user.model.User;
import com.ychp.common.model.SkyUser;
import org.dozer.DozerBeanMapper;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
public class SkyUserMaker {

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    public static SkyUser make(User user) {
        SkyUser skyUser = new SkyUser();
        dozer.map(user, skyUser);
        return skyUser;
    }
}
