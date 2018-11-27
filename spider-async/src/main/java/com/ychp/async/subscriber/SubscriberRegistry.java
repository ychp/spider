package com.ychp.async.subscriber;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.async.model.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
public class SubscriberRegistry {

	private Map<String, List<Subscriber>> subscriberByClass = Maps.newConcurrentMap();

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	private void init() {
		Map<String, Object> subscribers = applicationContext.getBeansWithAnnotation(AsyncBean.class);

		for(Object subscriber : subscribers.values()) {
			Method[] methods = subscriber.getClass().getDeclaredMethods();
			checkMethod(methods, subscriber);
		}
	}

	private void checkMethod(Method[] methods, Object subscriber) {
		for(Method method : methods) {
			if(method.getDeclaredAnnotation(AsyncSubscriber.class) != null) {
				String paramName = method.getParameters()[0].getType().getSimpleName();
				List<Subscriber> methodList = subscriberByClass.get(paramName);
				if(methodList == null) {
					methodList = Lists.newArrayList();
				}
				methodList.add(new Subscriber(subscriber, method));
				subscriberByClass.put(paramName, methodList);
			}
		}
	}

	public List<Subscriber> getMethodByParamter(String paramName) {
		return subscriberByClass.get(paramName);
	}
}
