package com.ychp.cache;

import com.ychp.cache.aop.CacheAdvice;
import com.ychp.cache.aop.CacheInvalidAdvice;
import com.ychp.cache.ext.DataExtService;
import com.ychp.cache.ext.impl.DefaultDataExtServiceImpl;
import com.ychp.redis.manager.RedisManager;
import com.ychp.cache.properties.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yingchengpeng
 * @date 2018/8/15
 */
@Configuration
@ConditionalOnProperty(name = "cache.type", havingValue = "redis")
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration {

	@Bean
	public RedisManager cacheManager() {
		return new RedisManager();
	}

	@Bean
	public CacheAdvice cacheAdvice() {
		return new CacheAdvice();
	}

	@Bean
	public CacheInvalidAdvice cacheInvalidAdvice() {
		return new CacheInvalidAdvice();
	}

	@Bean
	@ConditionalOnMissingBean(DataExtService.class)
	public DataExtService dataExtService() {
		return new DefaultDataExtServiceImpl();
	}
}
