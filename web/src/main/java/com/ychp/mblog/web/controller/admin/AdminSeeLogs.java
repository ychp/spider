package com.ychp.spider.web.controller.admin;

import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.SeeLogCriteria;
import com.ychp.user.api.bean.response.SeeLogVO;
import com.ychp.user.api.service.SeeLogReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Api(description = "浏览记录")
@RestController
@RequestMapping("/api/admin/see-log")
public class AdminSeeLogs {

	@Autowired
	private SeeLogReadService seeLogReadService;

	@ApiOperation("浏览记录分页接口")
	@GetMapping("paging")
	public Paging<SeeLogVO> paging(SeeLogCriteria criteria) {
		return seeLogReadService.paging(criteria);
	}

}
