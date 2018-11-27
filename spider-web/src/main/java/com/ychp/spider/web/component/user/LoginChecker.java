package com.ychp.spider.web.component.user;

import com.ychp.spider.web.constant.SessionConstants;
import com.ychp.redis.dao.JedisTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/8/22
 */
@Component
public class LoginChecker {

	private final static String KEY_PREFIX = "user:login-error:";
	private final static Integer MAX_TIMES = 5;
	private final static Integer EXPIRE_TIME = 900;
	@Autowired
	private JedisTemplate jedisTemplate;

	public boolean needCheckCaptcha(String name) {
		Long errorTimes = jedisTemplate.excute(jedis -> {
			String key = "user:login-error:" + name;
			String times = jedis.get(key);
			if(StringUtils.isEmpty(times)) {
				return 0L;
			}
			return Long.valueOf(times);
		});
		return errorTimes >= MAX_TIMES;
	}

	public void incrErrorTimes(String name) {
		jedisTemplate.excute(jedis -> {
			String key = KEY_PREFIX + name;
			Long times = jedis.incr(key);
			jedis.expire(key, EXPIRE_TIME);
			return times;
		});
	}

	public boolean checkCaptcha(HttpSession session, String captcha) {
		String token = (String) session.getAttribute(SessionConstants.CAPTCHA_TOKEN);
		if(Objects.equals(captcha, token)) {
			session.removeAttribute(SessionConstants.CAPTCHA_TOKEN);
			return true;
		}
		return false;
	}

}
