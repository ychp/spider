package com.ychp.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Data
public class BaiduIpProperties implements Serializable {

	private static final long serialVersionUID = 2561557517994020604L;

	private String apiKey;
}
