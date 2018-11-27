package com.ychp.spider.web.async.log.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.blog.enums.AimTypeEnum;
import com.ychp.blog.service.ArticleWriteService;
import com.ychp.spider.web.async.log.DislikeEvent;
import com.ychp.spider.web.async.log.LikeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ychp.blog.enums.AimTypeEnum.fromValue;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Slf4j
@AsyncBean
public class LikeAsync {

	@Autowired
	private ArticleWriteService articleWriteService;

	@AsyncSubscriber
	public void increase(LikeEvent likeEvent) {
		AimTypeEnum type = fromValue(likeEvent.getType());
		switch (type) {
			case ARTICLE:
				articleWriteService.increaseLike(likeEvent.getAimId());
				break;
			case TALK:
			case PHOTO:
			default:
		}
	}

	@AsyncSubscriber
	public void decrease(DislikeEvent dislikeEvent) {
		AimTypeEnum type = fromValue(dislikeEvent.getType());
		switch (type) {
			case ARTICLE:
				articleWriteService.decreaseLike(dislikeEvent.getAimId());
				break;
			case TALK:
			case PHOTO:
			default:
		}
	}

}
