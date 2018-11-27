package com.ychp.spider.web.controller.common.search;

import com.ychp.blog.search.ArticleSearchWriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yingchengpeng
 * @date 2018/8/19
 */
@Api(description = "搜索管理服务")
@RestController
@RequestMapping("/api/admin/search")
public class AdminSearchs {

	@Autowired
	private ArticleSearchWriteService articleSearchWriteService;

	@ApiOperation("全量同步文章")
	@GetMapping("article/full")
	public Boolean articleFullDump() {
		articleSearchWriteService.fullDump();
		return true;
	}

	@ApiOperation("全量同步文章")
	@GetMapping("article/delta")
	public Boolean articleDeltaDump(Integer deltaMin) {
		deltaMin = deltaMin == null ? 35 : deltaMin;
		articleSearchWriteService.deltaDump(deltaMin);
		return true;
	}

}
