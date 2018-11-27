package com.ychp.user;

import com.ychp.user.cache.AddressCacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yingchengpeng
 * @date 2018/8/10
 */
@ComponentScan
@Configuration
public class UserApiAutoConfig {

	@Bean
	public AddressCacher addressCacher() {
		return new AddressCacher();
	}
}
