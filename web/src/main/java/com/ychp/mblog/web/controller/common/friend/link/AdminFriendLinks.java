package com.ychp.spider.web.controller.common.friend.link;

import com.ychp.common.model.paging.Paging;
import com.ychp.cache.annontation.DataInvalidCache;
import com.ychp.user.api.bean.query.FriendLinkCriteria;
import com.ychp.user.model.FriendLink;
import com.ychp.user.api.service.FriendLinkReadService;
import com.ychp.user.api.service.FriendLinkWriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Api(description = "友情链接")
@RestController
@RequestMapping("/api/admin/friend-link")
public class AdminFriendLinks {

	@Autowired
	private FriendLinkReadService friendLinkReadService;

	@Autowired
	private FriendLinkWriteService friendLinkWriteService;

	@ApiOperation(value = "友情链接创建接口", httpMethod = "POST")
	@PostMapping
	@DataInvalidCache("friend-links")
	public Long create(@RequestBody FriendLink friendLink) {
		return friendLinkWriteService.create(friendLink);
	}

	@ApiOperation(value = "友情链接更新接口", httpMethod = "PUT")
	@PutMapping
	@DataInvalidCache("friend-links")
	public Boolean update(@RequestBody FriendLink friendLink) {
		return friendLinkWriteService.update(friendLink);
	}

	@ApiOperation("友情链接分页接口")
	@GetMapping("paging")
	public Paging<FriendLink> paging(FriendLinkCriteria criteria) {
		return friendLinkReadService.paging(criteria);
	}


}
