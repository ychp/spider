package com.ychp.session.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ychp.redis.manager.RedisManager;
import com.ychp.session.constant.SessionConstants;

import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
public class SessionManager {

	private final RedisManager redisManager;

	public SessionManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	public void save(String id, Map<String, Object> attributes, int expireTime) {
		redisManager.save(getKey(id), attributes, expireTime);
	}

	public Map<String, Object> get(String id) {
		return redisManager.get(getKey(id), new TypeReference<Map<String, Object>>(){});
	}

	public void refresh(String id, int expireTime) {
		redisManager.refresh(getKey(id), expireTime);
	}

	public void remove(String id) {
		redisManager.invalid(getKey(id));
	}

	private String getKey(String id) {
		return SessionConstants.SESSION_PRE + id;
	}
}
