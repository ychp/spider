package com.ychp.async.publisher;

import com.ychp.async.subscriber.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
public class AsyncPublisher {

	@Autowired
	private Dispatcher dispatcher;

	public void post(Object event) {
		dispatcher.dispatch(event);
	}

}
