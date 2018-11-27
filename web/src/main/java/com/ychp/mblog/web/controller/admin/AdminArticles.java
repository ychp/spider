package com.ychp.spider.web.controller.admin;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.blog.bean.query.ArticleCriteria;
import com.ychp.blog.bean.response.ArticleBaseInfoVO;
import com.ychp.blog.enums.ArticleStatusEnum;
import com.ychp.blog.service.ArticleReadService;
import com.ychp.blog.service.ArticleWriteService;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.web.async.article.ArticleSyncSearchEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yingchengpeng
 * @date 2018-08-10
 */
@Api(description = "文章管理-用于管理")
@RestController
@RequestMapping("/api/admin/article")
public class AdminArticles {

    @Autowired
    private ArticleReadService articleReadService;

    @Autowired
    private ArticleWriteService articleWriteService;

    @Autowired
    private AsyncPublisher publisher;

    @ApiOperation("文章分页接口")
    @GetMapping("paging")
    public Paging<ArticleBaseInfoVO> paging(ArticleCriteria criteria) {
        return articleReadService.paging(criteria);
    }

    @ApiOperation("撤下文章接口")
    @PutMapping("{id}/frozen")
    public Boolean frozen(@PathVariable Long id) {
        Boolean result = articleWriteService.updateStatus(id, ArticleStatusEnum.FROZEN.getValue());
        publisher.post(new ArticleSyncSearchEvent(id));
        return result;
    }

    @ApiOperation("恢复文章接口")
    @PutMapping("{id}/unfrozen")
    public Boolean unfrozen(@PathVariable Long id) {
        Boolean result = articleWriteService.updateStatus(id, ArticleStatusEnum.PUBLIC.getValue());
        publisher.post(new ArticleSyncSearchEvent(id));
        return result;
    }

}
