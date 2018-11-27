package com.ychp.async.subscriber;

import com.ychp.async.model.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
public class Dispatcher {

	private final SubscriberRegistry subscriberRegistry;

	@Autowired
	public Dispatcher(SubscriberRegistry subscriberRegistry) {
		this.subscriberRegistry = subscriberRegistry;
	}

	@Async
	public void dispatch(Object event) {
		String eventName = event.getClass().getSimpleName();
		List<Subscriber> methods = subscriberRegistry.getMethodByParamter(eventName);
		if(CollectionUtils.isEmpty(methods)) {
			return;
		}
		for(Subscriber method : methods) {
			method.invoke(event);
		}
	}
}
