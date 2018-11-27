package com.ychp.spider.web.schedule;

import com.ychp.blog.search.ArticleSearchWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author yingchengpeng
 * @date 2018/8/19
 */
@Component
public class SearchSchedules {

	@Autowired
	private ArticleSearchWriteService articleSearchWriteService;

	@Scheduled(cron = "0 0 1 * * ?")
	public void articleFullDump() {
		articleSearchWriteService.fullDump();
	}

	@Scheduled(cron = "0 0/30 * * * ?")
	public void articleDeltaDump() {
		articleSearchWriteService.deltaDump(35);
	}

}
