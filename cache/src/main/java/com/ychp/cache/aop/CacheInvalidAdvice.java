package com.ychp.cache.aop;

import com.ychp.cache.annontation.DataInvalidCache;
import com.ychp.cache.ext.DataExtService;
import com.ychp.redis.manager.RedisManager;
import com.ychp.cache.utils.CacheAdviceUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018/8/14
 */
@Aspect
public class CacheInvalidAdvice {

	@Autowired
	private RedisManager redisManager;

	@Autowired
	private DataExtService dataExtService;

	@Pointcut(value = "@annotation(com.ychp.cache.annontation.DataInvalidCache)")
	public void pointCut() {
	}

	@Around(value = "pointCut()")
	public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnObject = joinPoint.proceed();

		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		DataInvalidCache dataInvalidCache = method.getAnnotation(DataInvalidCache.class);

		Map<String, Object> datas = CacheAdviceUtils.getRequestDatas(joinPoint);
		dataExtService.fillExtInfo(datas);
		datas.put("return", returnObject);
		String key = CacheAdviceUtils.getCacheKey(dataInvalidCache.value(), datas);

		String cache = redisManager.getJson(key);

		if(!StringUtils.isEmpty(cache)) {
			redisManager.invalid(key);
		}
		return returnObject;
	}

}
