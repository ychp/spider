package com.ychp.spider.web.controller.blog;

import com.google.common.base.MoreObjects;
import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.blog.bean.query.CommentCriteria;
import com.ychp.blog.bean.request.CommentRequest;
import com.ychp.blog.bean.response.CommentWithChildrenInfo;
import com.ychp.blog.enums.AimTypeEnum;
import com.ychp.blog.enums.CommentStatusEnum;
import com.ychp.blog.model.Article;
import com.ychp.blog.model.Comment;
import com.ychp.blog.service.ArticleReadService;
import com.ychp.blog.service.CommentReadService;
import com.ychp.blog.service.CommentWriteService;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.SkyUser;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.web.async.article.ArticleCommentEvent;
import com.ychp.spider.web.async.article.CommentDeleteEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-09-10
 */
@Slf4j
@Api("博客主 - 评价管理")
@RestController
@RequestMapping("/api/bloger/comment")
public class BlogerComments {

    @Autowired
    private ArticleReadService articleReadService;

    @Autowired
    private CommentWriteService commentWriteService;

    @Autowired
    private CommentReadService commentReadService;

    @Autowired
    private AsyncPublisher publisher;

    @ApiOperation("评价")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean comment(@RequestBody CommentRequest commentDto){
        Comment comment = makeComment(commentDto);
        Boolean result = commentWriteService.create(comment);

        if(!result){
            throw new ResponseException("comment.create.fail");
        }

        publisher.post(new ArticleCommentEvent(comment.getAimId(), comment.getPid()!= null, true));
        return true;
    }

    private Comment makeComment(CommentRequest commentRequest) {
        Comment comment = new Comment();

        if(commentRequest.getPid() != null){
            Comment parent = commentReadService.findById(commentRequest.getPid());
            comment.setOwnerId(parent.getOwnerId());
            comment.setPid(parent.getId());
            comment.setLevel(parent.getLevel() + 1);
            comment.setReceiver(parent.getReplier());
            comment.setReceiverName(parent.getReplierName());
            comment.setReplierAvatar(parent.getReplierName());
        } else {
            if(Objects.equals(commentRequest.getType(), AimTypeEnum.ARTICLE.getValue())) {
                Article article = articleReadService.findById(commentRequest.getAimId());
                comment.setOwnerId(article.getUserId());
            }
            comment.setLevel(1);
        }

        comment.setType(MoreObjects.firstNonNull(commentRequest.getType(), AimTypeEnum.ARTICLE.getValue()));
        comment.setAimId(commentRequest.getAimId());
        comment.setContent(commentRequest.getContent());

        SkyUser skyUser = SessionContextUtils.currentUser();
        comment.setReplier(skyUser.getId());
        comment.setReplierName(skyUser.getNickName());
        comment.setReplierAvatar(skyUser.getAvatar());
        comment.setStatus(CommentStatusEnum.SHOW.getValue());
        return comment;
    }

    @ApiOperation("评价分页记录")
    @GetMapping(value = "paging")
    public Paging<Comment> comments(CommentCriteria commentCriteria){
        commentCriteria.setOwnerId(SessionContextUtils.getUserId());
        return commentReadService.paging(commentCriteria);
    }

    @ApiOperation("显示评价")
    @PutMapping(value = "{id}/show", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean show(@PathVariable("id")Long id){
        Comment comment = commentWriteService.updateStatus(id, CommentStatusEnum.SHOW.getValue());
        if(!Objects.equals(comment.getOwnerId(), SessionContextUtils.getUserId())) {
            throw new ResponseException("comment.not.belong.to.user");
        }
        publisher.post(new ArticleCommentEvent(comment.getAimId(), comment.getPid()!= null, true));
        return true;
    }

    @ApiOperation("隐藏评价")
    @PutMapping(value = "{id}/hide", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean hide(@PathVariable("id") Long id){
        Comment comment = commentWriteService.updateStatus(id, CommentStatusEnum.HIDE.getValue());
        if(!Objects.equals(comment.getOwnerId(), SessionContextUtils.getUserId())) {
            throw new ResponseException("comment.not.belong.to.user");
        }
        publisher.post(new ArticleCommentEvent(comment.getAimId(), comment.getPid()!= null, false));
        return true;
    }

    @ApiOperation("删除评价")
    @PutMapping(value = "{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete(@PathVariable("id") Long id){
        Comment comment = commentWriteService.updateStatus(id, CommentStatusEnum.DELETED.getValue());
        if(!Objects.equals(comment.getOwnerId(), SessionContextUtils.getUserId())) {
            throw new ResponseException("comment.not.belong.to.user");
        }
        publisher.post(new ArticleCommentEvent(comment.getAimId(), comment.getPid()!= null, false));
        publisher.post(new CommentDeleteEvent(comment.getId()));
        return true;
    }

    @ApiOperation("评价详情")
    @GetMapping(value = "{id}/findById")
    public CommentWithChildrenInfo detail(@PathVariable Long id){
        Long userId = SessionContextUtils.getUserId();
        Comment parent = commentReadService.findById(id);

        List<Comment> comments = commentReadService.findByPid(id);
        if(Objects.equals(parent.getOwnerId(), userId)) {
            return new CommentWithChildrenInfo(parent, comments);
        }

        comments = comments.stream().filter(comment ->
                Objects.equals(comment.getStatus(), CommentStatusEnum.SHOW.getValue())
                || Objects.equals(comment.getReplier(), userId))
                .collect(Collectors.toList());
        return new CommentWithChildrenInfo(parent, comments);
    }

}

