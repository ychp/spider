package com.ychp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Data
@ConfigurationProperties("ip")
public class IPProperties implements Serializable {

	private static final long serialVersionUID = 2561557517994020604L;

	private BaiduIpProperties baidu;
}
