package com.ychp.session.utils;


import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
@Slf4j
public class SessionIdGenerator {

	/**
	 * 暂时为uuid，后续优化
	 * @return
	 */
	public static String getId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
