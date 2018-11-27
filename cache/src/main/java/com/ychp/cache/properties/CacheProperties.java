package com.ychp.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yingchengpeng
 * @date 2018/8/15
 */
@Data
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {

	private String type;

}
