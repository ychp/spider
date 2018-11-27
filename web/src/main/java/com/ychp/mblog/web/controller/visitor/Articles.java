package com.ychp.spider.web.controller.visitor;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.blog.bean.query.ArticleCriteria;
import com.ychp.blog.bean.response.ArticleBaseInfoVO;
import com.ychp.blog.bean.response.ArticleDetailVO;
import com.ychp.blog.cache.ArticleCacher;
import com.ychp.blog.enums.AimTypeEnum;
import com.ychp.blog.enums.ArticleStatusEnum;
import com.ychp.blog.model.ArticleSummary;
import com.ychp.blog.model.LikeLog;
import com.ychp.blog.service.ArticleReadService;
import com.ychp.blog.service.LikeLogReadService;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.model.paging.PagingCriteria;
import com.ychp.ip.component.IPServer;
import com.ychp.spider.web.async.article.ArticleVisitEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-08-10
 */
@Api(description = "文章-针对所有用户")
@RestController
@RequestMapping("/api/article")
public class Articles {

    @Autowired
    private ArticleReadService articleReadService;

    @Autowired
    private AsyncPublisher publisher;

    @Autowired
    private IPServer ipServer;

    @Autowired
    private LikeLogReadService likeLogReadService;

    @Autowired
    private ArticleCacher articleCacher;

    @ApiOperation("文章详情接口")
    @GetMapping("{id}/detail")
    public ArticleDetailVO detail(@ApiParam(example = "1") @PathVariable Long id, HttpServletRequest request) {
        ArticleDetailVO detailVO = articleCacher.findDetail(id);
        ArticleSummary summary = articleReadService.findSummaryById(id);
        detailVO.setSummary(summary);
        String ip = ipServer.getIp(request);
        LikeLog likeLog = likeLogReadService.findByAimAndIp(id, AimTypeEnum.ARTICLE.getValue(), ip);
        detailVO.setHasLiked(likeLog != null);
        publisher.post(new ArticleVisitEvent(id));
        return detailVO;
    }

    @ApiOperation("文章分页接口")
    @GetMapping("paging")
    public Paging<ArticleBaseInfoVO> paging(ArticleCriteria criteria) {
        criteria.setStatus(ArticleStatusEnum.PUBLIC.getValue());
        return articleReadService.paging(criteria);
    }

    @ApiOperation("文章日期分页接口")
    @GetMapping("paging-publish-date")
    public Paging<String> paging(PagingCriteria criteria) {
        return articleReadService.pagingPublishDates(criteria);
    }

    @ApiOperation("热门文章接口")
    @GetMapping("popular")
    public List<ArticleBaseInfoVO> popular(@RequestParam(defaultValue = "6") Integer size) {
        return articleReadService.popular(size);
    }
}
