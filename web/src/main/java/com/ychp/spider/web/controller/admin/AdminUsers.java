package com.ychp.spider.web.controller.admin;

import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.UserCriteria;
import com.ychp.user.api.service.UserReadService;
import com.ychp.user.api.service.UserWriteService;
import com.ychp.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Api(description = "运营管理账户用的数据接口")
@RestController
@RequestMapping("/api/admin/user")
public class AdminUsers {

    @Autowired
    private UserReadService userReadService;

    @ApiOperation("用户详情接口")
    @GetMapping("{id}/detail")
    public User detail(@ApiParam(example = "1") @PathVariable Long id) {
        return userReadService.findDetailById(id);
    }

    @ApiOperation("用户分页接口")
    @GetMapping("paging")
    public Paging<User> paging(UserCriteria criteria) {
        return userReadService.paging(criteria);
    }

}
