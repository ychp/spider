package com.ychp.spider.web.async.article.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.blog.service.ArticleWriteService;
import com.ychp.spider.web.async.article.CategoryUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
@Slf4j
@AsyncBean
public class CategoryUpdateAsync {

	@Autowired
	private ArticleWriteService articleWriteService;

	@AsyncSubscriber
	public void syncName(CategoryUpdateEvent event) {
		articleWriteService.updateCategoryName(event.getId(), event.getName());
	}

}
