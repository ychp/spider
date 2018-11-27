package com.ychp.ip;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ychp.ip.component.IPServer;
import com.ychp.properties.IPProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Configuration
@EnableConfigurationProperties(IPProperties.class)
public class IPResolveAutoConfiguration {

	@Autowired
	private IPProperties ipProperties;

	@Bean
	public IPServer ipServer(ObjectMapper objectMapper) {
		return new IPServer(ipProperties.getBaidu() == null ? "" : ipProperties.getBaidu().getApiKey(),
				objectMapper);
	}

}
