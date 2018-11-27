package com.ychp.spider.web.async.user.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.common.model.SkyUser;
import com.ychp.spider.web.async.user.UserLoginEvent;
import com.ychp.user.model.UserLoginLog;
import com.ychp.user.api.service.UserLoginLogWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Slf4j
@AsyncBean
public class LoginLogAsync {

	@Autowired
	private UserLoginLogWriteService userLoginLogWriteService;

	@AsyncSubscriber
	public void log(UserLoginEvent userLoginEvent) {
		SkyUser skyUser = userLoginEvent.getSkyUser();
		if(skyUser == null) {
			return;
		}

		UserLoginLog loginLog = makeLog(skyUser);
		userLoginLogWriteService.create(loginLog);
	}

	private UserLoginLog makeLog(SkyUser skyUser) {
		UserLoginLog loginLog = new UserLoginLog();
		loginLog.setUserId(skyUser.getId());
		loginLog.setIp(skyUser.getIp());
		return loginLog;
	}
}
