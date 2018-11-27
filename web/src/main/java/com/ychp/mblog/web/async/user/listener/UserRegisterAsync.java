package com.ychp.spider.web.async.user.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.common.model.SkyUser;
import com.ychp.spider.web.async.user.UserRegisterEvent;
import com.ychp.user.api.service.UserSummaryWriteService;
import com.ychp.user.model.UserSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Slf4j
@AsyncBean
public class UserRegisterAsync {

	@Autowired
	private UserSummaryWriteService userSummaryWriteService;

	@AsyncSubscriber
	public void register(UserRegisterEvent userRegisterEvent) {
		SkyUser skyUser = userRegisterEvent.getSkyUser();
		if(skyUser == null) {
			return;
		}

		UserSummary summary = new UserSummary();
		summary.setUserId(skyUser.getId());
		summary.setFollowerNum(0L);
		summary.setFansNum(0L);
		userSummaryWriteService.create(summary);
	}

}
