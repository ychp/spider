package com.ychp.redis.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.ychp.redis.dao.JedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018/8/14
 */
@Slf4j
public class RedisManager {

	@Autowired
	private JedisTemplate jedisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public void save(String key, Object t, int expireTime) {
		String json;
		try {
			json = form2Json(t);
		} catch (JsonProcessingException e) {
			log.error("fail to parse to json by {}, case {}", t, Throwables.getStackTraceAsString(e));
			return;
		}

		save(key, json, expireTime);
	}

	public void save(String key, String value, int expireTime) {
		jedisTemplate.excute(jedis -> jedis.setex(key, expireTime, value));
	}

	public void refresh(String key, int expireTime) {
		jedisTemplate.excute(jedis -> jedis.expire(key, expireTime));
	}

	public void invalid(String key) {
		jedisTemplate.excute(jedis -> jedis.del(key));
	}

	public <T> T get(String key, Class<T> clazz) {
		String json = getJson(key);
		try {
			return form2Object(json, clazz);
		} catch (IOException e) {
			log.error("fail to parse to json by {}, case {}", json, Throwables.getStackTraceAsString(e));
		}
		return null;
	}

	public <T> T get(String key, TypeReference type) {
		String json = getJson(key);
		try {
			return form2Object(json, type);
		} catch (IOException e) {
			log.error("fail to parse to json by {}, case {}", json, Throwables.getStackTraceAsString(e));
		}
		return null;
	}

	public String getJson(String key) {
		return jedisTemplate.excute(jedis -> jedis.get(key));
	}

	private String form2Json(Object t) throws JsonProcessingException {
		return objectMapper.writeValueAsString(t);
	}

	private <T> T form2Object(String json, Class<T> clazz) throws IOException {
		if(StringUtils.isEmpty(json)) {
			return null;
		}
		return objectMapper.readValue(json, clazz);
	}

	private <T> T form2Object(String json, TypeReference type) throws IOException {
		if(StringUtils.isEmpty(json)) {
			return null;
		}
		return objectMapper.readValue(json, type);
	}

}
