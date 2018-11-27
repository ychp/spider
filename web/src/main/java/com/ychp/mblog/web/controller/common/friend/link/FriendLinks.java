package com.ychp.spider.web.controller.common.friend.link;

import com.ychp.cache.annontation.DataCache;
import com.ychp.user.model.FriendLink;
import com.ychp.user.api.service.FriendLinkReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Api(description = "友情链接")
@RestController
@RequestMapping("/api/friend-link")
public class FriendLinks {

	@Autowired
	private FriendLinkReadService friendLinkReadService;

	@ApiOperation("友情链接接口")
	@GetMapping("visible")
	@DataCache("friend-links")
	public List<FriendLink> listVisible() {
		return friendLinkReadService.listVisible();
	}


}
