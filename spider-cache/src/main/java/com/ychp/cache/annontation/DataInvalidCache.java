package com.ychp.cache.annontation;

import java.lang.annotation.*;

/**
 * @author yingchengpeng
 * @date 2018/8/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DataInvalidCache {

	String value() default "";

	int expireTime() default 3600;
}
