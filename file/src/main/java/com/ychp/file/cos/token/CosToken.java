package com.ychp.file.cos.token;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Data
@ConfigurationProperties(prefix = "file.cos")
public class CosToken {

	/**
	 * 用户编号
	 */
	private String secretId;

	/**
	 * 用户密钥
	 */
	private String secretKey;

	private String appId;

	private String bucketName;

	private String region;

}
