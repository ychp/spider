package com.ychp.session;

import com.google.common.collect.Lists;
import com.ychp.redis.RedisAutoConfiguration;
import com.ychp.redis.manager.RedisManager;
import com.ychp.session.impl.SkySessionFilter;
import com.ychp.session.manager.SessionManager;
import com.ychp.session.properties.SessionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(SessionProperties.class)
public class SkySessionAutoConfiguration {

	@Autowired
	private SessionProperties sessionProperties;

	@Autowired
	private RedisManager redisManager;

	@Bean
	public SessionManager sessionManager() {
		return new SessionManager(redisManager);
	}

	@Bean
	public FilterRegistrationBean sessionFilterBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		SkySessionFilter skySessionFilter = new SkySessionFilter();
		skySessionFilter.setCookieDomain(sessionProperties.getCookieDomain());
		skySessionFilter.setCookieMaxAge(sessionProperties.getCookieMaxAge() == null ? 1800: sessionProperties.getCookieMaxAge());
		skySessionFilter.setMaxInactiveInterval(skySessionFilter.getCookieMaxAge());
		skySessionFilter.setSessionManager(this.sessionManager());
		registrationBean.setFilter(skySessionFilter);
		registrationBean.setUrlPatterns(Lists.newArrayList("/*"));
		registrationBean.setName("sessionFilter");
		registrationBean.setOrder(- 1024);
		return registrationBean;
	}
}
