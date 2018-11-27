package com.ychp.spider.web.async.user.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.spider.web.async.user.CancelFollowEvent;
import com.ychp.spider.web.async.user.FollowEvent;
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
public class FollowAsync {

	@Autowired
	private UserSummaryWriteService userSummaryWriteService;

	@AsyncSubscriber
	public void follow(FollowEvent followEvent) {

		// 增加粉丝数量
		UserSummary summary = new UserSummary();
		summary.setUserId(followEvent.getFolloweeId());
		summary.setFollowerNum(0L);
		summary.setFansNum(1L);
		userSummaryWriteService.increase(summary);

		// 增加关注人数量
		summary = new UserSummary();
		summary.setUserId(followEvent.getFollowerId());
		summary.setFollowerNum(1L);
		summary.setFansNum(0L);
		userSummaryWriteService.increase(summary);
	}

	@AsyncSubscriber
	public void cancelFollow(CancelFollowEvent cancelFollowEvent) {

		// 增加粉丝数量
		UserSummary summary = new UserSummary();
		summary.setUserId(cancelFollowEvent.getFolloweeId());
		summary.setFollowerNum(0L);
		summary.setFansNum(- 1L);
		userSummaryWriteService.increase(summary);

		// 增加关注人数量
		summary = new UserSummary();
		summary.setUserId(cancelFollowEvent.getFollowerId());
		summary.setFollowerNum(- 1L);
		summary.setFansNum(0L);
		userSummaryWriteService.increase(summary);
	}
}
