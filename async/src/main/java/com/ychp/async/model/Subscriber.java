package com.ychp.async.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber implements Serializable {

	private static final long serialVersionUID = 4180993523834778571L;

	private Object bean;

	private Method method;

	public void invoke(Object event) {
		try {
			method.invoke(bean, event);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
