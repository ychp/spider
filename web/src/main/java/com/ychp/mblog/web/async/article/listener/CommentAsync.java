package com.ychp.spider.web.async.article.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.blog.enums.CommentStatusEnum;
import com.ychp.blog.model.Comment;
import com.ychp.blog.service.CommentReadService;
import com.ychp.blog.service.CommentWriteService;
import com.ychp.spider.web.async.article.CommentDeleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Slf4j
@AsyncBean
public class CommentAsync {

	@Autowired
	private CommentReadService commentReadService;

	@Autowired
	private CommentWriteService commentWriteService;

	@AsyncSubscriber
	public void comment(CommentDeleteEvent commentDeleteEvent) {
		List<Comment> comments = commentReadService.findByPid(commentDeleteEvent.getId());
		for(Comment comment : comments) {
			commentWriteService.updateStatus(comment.getId(), CommentStatusEnum.DELETED.getValue());
		}
	}
}
