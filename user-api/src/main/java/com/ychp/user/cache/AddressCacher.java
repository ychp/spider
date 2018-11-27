package com.ychp.user.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ychp.user.model.Address;
import com.ychp.user.api.service.AddressReadService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yingchengpeng
 * @date 2018/8/10
 */
public class AddressCacher {

	@Autowired
	private AddressReadService addressReadService;

	private LoadingCache<Long, Address> addressById;
	private LoadingCache<Long, List<Address>> addressByPid;

	public AddressCacher() {
		addressById = CacheBuilder.newBuilder()
				.expireAfterWrite(12, TimeUnit.HOURS)
				.initialCapacity(10)
				.maximumSize(10)
				.build(new CacheLoader<Long, Address>() {
					@Override
					public Address load(Long id) {
						return addressReadService.findById(id);
					}
				});

		addressByPid = CacheBuilder.newBuilder()
				.expireAfterWrite(12, TimeUnit.HOURS)
				.initialCapacity(10)
				.maximumSize(10)
				.build(new CacheLoader<Long, List<Address>>() {
					@Override
					public List<Address> load(Long pid) {
						return addressReadService.findByPid(pid);
					}
				});
	}

	public Address findById(Long id) {
		return addressById.getUnchecked(id);
	}

	public List<Address> findChildrenByPid(Long pid) {
		return addressByPid.getUnchecked(pid);
	}
}
