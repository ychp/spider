package com.ychp.spider.web.controller.common.address;

import com.ychp.user.cache.AddressCacher;
import com.ychp.user.model.Address;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018/8/10
 */
@Api(description = "获取地址数据")
@RestController
@RequestMapping("/api/address")
public class Addresses {

	@Autowired
	private AddressCacher addressCacher;

	@ApiOperation(value = "获取指定地址的子节点数据", httpMethod = "GET")
	@GetMapping("{pid}/children")
	public List<Address> findByPid(@ApiParam(example = "0") @PathVariable Long pid) {
		return addressCacher.findChildrenByPid(pid);
	}

}
