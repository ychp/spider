package com.ychp.spider.web.controller.blog;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.blog.bean.query.CategoryCriteria;
import com.ychp.blog.model.Category;
import com.ychp.blog.service.CategoryReadService;
import com.ychp.blog.service.CategoryWriteService;
import com.ychp.cache.annontation.DataCache;
import com.ychp.cache.annontation.DataInvalidCache;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.web.async.article.CategoryUpdateEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-08-10
 */
@Api(description = "文章类目管理")
@RestController
@RequestMapping("/api/bloger/category")
public class BlogerCategories {

    @Autowired
    private CategoryReadService categoryReadService;

    @Autowired
    private CategoryWriteService categoryWriteService;

    @Autowired
    private AsyncPublisher publisher;

    @ApiOperation(value = "类目创建接口", httpMethod = "POST")
    @PostMapping
    public Long create(@RequestBody Category category) {
        category.setUserId(SessionContextUtils.getUserId());
        return  categoryWriteService.create(category);
    }

    @ApiOperation(value = "类目更新接口", httpMethod = "PUT")
    @PutMapping
    @DataInvalidCache("article:categories:{{currentUser.id}}")
    public Boolean update(@ApiParam(example = "1") Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        Boolean result = categoryWriteService.update(category);
        publisher.post(new CategoryUpdateEvent(id, name));
        return result;
    }

    @ApiOperation("类目分页接口")
    @GetMapping("paging")
    public Paging<Category> paging(CategoryCriteria criteria) {
        criteria.setUserId(SessionContextUtils.getUserId());
        return categoryReadService.paging(criteria);
    }

    @ApiOperation("类目")
    @GetMapping("find-by-user")
    @DataCache("article:categories:{{currentUser.id}}")
    public List<Category> userCategories() {
        CategoryCriteria criteria = new CategoryCriteria();
        criteria.setUserId(SessionContextUtils.getUserId());
        return categoryReadService.list(criteria);
    }
}
