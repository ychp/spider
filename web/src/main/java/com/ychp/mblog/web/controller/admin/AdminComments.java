package com.ychp.spider.web.controller.admin;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.blog.bean.query.CommentCriteria;
import com.ychp.blog.bean.response.CommentWithChildrenInfo;
import com.ychp.blog.enums.CommentStatusEnum;
import com.ychp.blog.model.Comment;
import com.ychp.blog.service.CommentReadService;
import com.ychp.blog.service.CommentWriteService;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.web.async.article.ArticleCommentEvent;
import com.ychp.spider.web.async.article.CommentDeleteEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-09-10
 */
@Slf4j
@Api("管理员 - 评价管理")
@RestController
@RequestMapping("/api/admin/comment")
public class AdminComments {

    @Autowired
    private CommentWriteService commentWriteService;

    @Autowired
    private CommentReadService commentReadService;

    @Autowired
    private AsyncPublisher publisher;

    @ApiOperation("评价分页记录")
    @GetMapping(value = "paging")
    public Paging<Comment> comments(CommentCriteria commentCriteria){
        return commentReadService.paging(commentCriteria);
    }

    @ApiOperation("删除评价")
    @PutMapping(value = "{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete(@PathVariable("id") Long id){
        Comment comment = commentWriteService.updateStatus(id, CommentStatusEnum.DELETED.getValue());
        publisher.post(new ArticleCommentEvent(comment.getAimId(), comment.getPid()!= null, false));
        publisher.post(new CommentDeleteEvent(comment.getId()));
        return true;
    }

    @ApiOperation("评价详情")
    @GetMapping(value = "{id}/detail")
    public CommentWithChildrenInfo detail(@PathVariable Long id){
        Comment parent = commentReadService.findById(id);
        List<Comment> comments = commentReadService.findByPid(id);
        return new CommentWithChildrenInfo(parent, comments);
    }

}

