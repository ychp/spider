package com.ychp.spider.web.controller.visitor;

import com.ychp.blog.bean.query.CommentCriteria;
import com.ychp.blog.bean.response.CommentWithChildrenInfo;
import com.ychp.blog.enums.CommentStatusEnum;
import com.ychp.blog.service.CommentReadService;
import com.ychp.common.model.paging.Paging;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-09-10
 */
@Slf4j
@Api("文章详情-评价")
@RestController
@RequestMapping("/api/comment")
public class Comments {

    @Autowired
    private CommentReadService commentReadService;

    @ApiOperation("文章详情-评价列表(不分页)")
    @GetMapping("list")
    public List<CommentWithChildrenInfo> findCommentByAimId(@ApiParam(value = "类型", defaultValue = "1")
                                                                @RequestParam(defaultValue = "1") Integer type,
                                                            @ApiParam("id") @RequestParam Long aimId) {
        return commentReadService.list(type, aimId);
    }

    @ApiOperation("文章详情-评价列表(分页)")
    @GetMapping(value = "paging")
    public Paging<CommentWithChildrenInfo> paging(@ApiParam(value = "类型", defaultValue = "1")
                                        @RequestParam(defaultValue = "1") Integer type,
                                    @ApiParam("id") @RequestParam Long aimId){
        CommentCriteria criteria = new CommentCriteria();
        criteria.setAimId(aimId);
        criteria.setType(type);
        criteria.setStatus(CommentStatusEnum.SHOW.getValue());
        return commentReadService.pagingCommentWithChildren(criteria);
    }

}
