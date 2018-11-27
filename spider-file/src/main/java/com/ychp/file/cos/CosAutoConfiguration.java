package com.ychp.file.cos;

import com.ychp.file.FileServer;
import com.ychp.file.cos.impl.CosFileServerImpl;
import com.ychp.file.cos.token.CosToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Configuration
@ConditionalOnProperty(name = "file.type", havingValue = "cos")
@EnableConfigurationProperties(CosToken.class)
public class CosAutoConfiguration {

	@Autowired
	private CosToken cosToken;

	@Bean
	public FileServer fileServer() {
		return new CosFileServerImpl(cosToken);
	}
}
