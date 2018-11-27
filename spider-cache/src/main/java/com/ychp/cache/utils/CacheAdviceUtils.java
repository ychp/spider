package com.ychp.cache.utils;

import com.github.jknack.handlebars.Handlebars;
import com.google.common.collect.Maps;
import com.ychp.handlebars.HandlebarsFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.IOException;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018/8/15
 */
public class CacheAdviceUtils {

	private static final String CACHE_PRE = "cache:";

	public static Map<String, Object> getRequestDatas(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		String[] params = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

		Map<String, Object> datas = Maps.newHashMap();
		for(int i = 0; i < params.length; i++) {
			datas.put(params[i], args[i]);
		}
		return datas;
	}

	public static String getCacheKey(String keyTemplate, Map<String, Object> datas) throws IOException {
		Handlebars handlebars = HandlebarsFactory.getInstance();
		return CACHE_PRE + handlebars.compileInline(keyTemplate).apply(datas);
	}
}
