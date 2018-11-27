package com.ychp.spider.web.controller.common.search;

import com.ychp.blog.search.ArticleSearchReadService;
import com.ychp.blog.search.bean.query.ArticleSearchCriteria;
import com.ychp.blog.search.bean.response.SearchWithAggsVO;
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
@Api(description = "文章搜索服务")
@RestController
@RequestMapping("/api/search")
public class Searchs {

	@Autowired
	private ArticleSearchReadService articleSearchReadService;

	@ApiOperation("文章搜索")
	@GetMapping("article")
	public SearchWithAggsVO search(ArticleSearchCriteria criteria) {
		return articleSearchReadService.search(criteria);
	}

}
