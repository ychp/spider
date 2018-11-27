package com.ychp.async.annontation;

import java.lang.annotation.*;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AsyncSubscriber {

}
