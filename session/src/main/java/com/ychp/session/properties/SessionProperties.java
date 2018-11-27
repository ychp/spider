package com.ychp.session.properties;

import com.ychp.redis.properties.RedisProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
@Data
@ConfigurationProperties(prefix = "session")
public class SessionProperties {

	private String cookieDomain;

	private Integer cookieMaxAge;

	private RedisProperties redis;

}
