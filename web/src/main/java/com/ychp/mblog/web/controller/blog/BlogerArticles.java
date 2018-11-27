package com.ychp.spider.web.controller.blog;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.blog.bean.query.ArticleCriteria;
import com.ychp.blog.bean.request.ArticleCreateRequest;
import com.ychp.blog.bean.request.ArticleUpdateRequest;
import com.ychp.blog.bean.response.ArticleBaseInfoVO;
import com.ychp.blog.bean.response.ArticleDetailVO;
import com.ychp.blog.enums.ArticleStatusEnum;
import com.ychp.blog.service.ArticleReadService;
import com.ychp.blog.service.ArticleWriteService;
import com.ychp.cache.annontation.DataInvalidCache;
import com.ychp.common.model.SkyUser;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.web.async.article.ArticleSyncSearchEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yingchengpeng
 * @date 2018-08-10
 */
@Api(description = "文章管理-用于管理")
@RestController
@RequestMapping("/api/bloger/article")
public class BlogerArticles {

    @Autowired
    private ArticleReadService articleReadService;

    @Autowired
    private ArticleWriteService articleWriteService;

    @Autowired
    private AsyncPublisher publisher;

    @ApiOperation(value = "文章创建接口", httpMethod = "POST")
    @PostMapping
    public Long create(@RequestBody ArticleCreateRequest request) {
        SkyUser user = SessionContextUtils.currentUser();
        request.getArticle().setUserId(user.getId());
        request.getArticle().setAuthor(user.getNickName());
        Long articleId = articleWriteService.create(request);
        publisher.post(new ArticleSyncSearchEvent(articleId));
        return articleId;
    }

    @ApiOperation("文章详情接口")
    @GetMapping("{id}/for-edit")
    public ArticleDetailVO detail(@ApiParam(example = "1") @PathVariable Long id) {
        return articleReadService.findEditById(id);
    }

    @ApiOperation(value = "文章编辑接口", httpMethod = "PUT")
    @PutMapping
    @DataInvalidCache("article:{{request.article.id}}")
    public Boolean update(@RequestBody ArticleUpdateRequest request) {
        Boolean result = articleWriteService.update(request);
        publisher.post(new ArticleSyncSearchEvent(request.getArticle().getId()));
        return result;
    }

    @ApiOperation("文章分页接口")
    @GetMapping("paging")
    public Paging<ArticleBaseInfoVO> paging(ArticleCriteria criteria) {
        criteria.setUserId(SessionContextUtils.getUserId());
        return articleReadService.paging(criteria);
    }

    @ApiOperation(value = "删除文章", httpMethod = "DELETE")
    @DeleteMapping
    @DataInvalidCache("article:{{id}}")
    public Boolean delete(@ApiParam(example = "1") @RequestParam Long id) {
        Boolean result = articleWriteService.delete(id);
        publisher.post(new ArticleSyncSearchEvent(id));
        return result;
    }


    @ApiOperation("设置为私有")
    @PutMapping("{id}/private")
    public Boolean setPrivate(@PathVariable Long id) {
        Boolean result = articleWriteService.updateStatus(id, ArticleStatusEnum.PRIVATE.getValue());
        publisher.post(new ArticleSyncSearchEvent(id));
        return result;
    }

    @ApiOperation("设置为公开")
    @PutMapping("{id}/public")
    public Boolean setPublic(@PathVariable Long id) {
        Boolean result = articleWriteService.updateStatus(id, ArticleStatusEnum.PUBLIC.getValue());
        publisher.post(new ArticleSyncSearchEvent(id));
        return result;
    }
}
