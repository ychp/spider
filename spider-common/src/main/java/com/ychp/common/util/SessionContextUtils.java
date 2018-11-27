package com.ychp.common.util;

import com.ychp.common.model.SkyUser;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
public class SessionContextUtils {

	private static final ThreadLocal<SkyUser> USER_THREAD_LOCAL = new ThreadLocal<>();

	public static void put(SkyUser user) {
		USER_THREAD_LOCAL.set(user);
	}

	public static SkyUser currentUser() {
		return USER_THREAD_LOCAL.get();
	}

	public static Long getUserId() {
		SkyUser user = currentUser();
		return user == null ? null : user.getId();
	}
}
