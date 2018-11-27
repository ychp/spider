package com.ychp.async.annontation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AsyncBean {

}
