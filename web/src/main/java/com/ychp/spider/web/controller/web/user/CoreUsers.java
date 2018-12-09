package com.ychp.spider.web.controller.web.user;

import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.SkyUser;
import com.ychp.common.util.Encryption;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.web.component.user.LoginChecker;
import com.ychp.spider.web.util.SkyUserMaker;
import com.ychp.user.api.service.UserReadService;
import com.ychp.user.api.service.UserWriteService;
import com.ychp.user.cache.UserCacher;
import com.ychp.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yingchengpeng
 * @date 2018-08-27
 */
@Api(description = "公共用户接口")
@RestController
@RequestMapping("/api/user")
public class CoreUsers {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserWriteService userWriteService;

    @Autowired
    private LoginChecker loginChecker;

    @Autowired
    private UserCacher userCacher;

    @ApiOperation(value = "获取用户信息", httpMethod = "GET")
    @GetMapping("{id}")
    public SkyUser detail(@ApiParam(example = "1") @PathVariable Long id) {
        return SkyUserMaker.make(userCacher.findById(id));
    }

    @ApiOperation(value = "登录", httpMethod = "POST")
    @PostMapping("login")
    public SkyUser login(@ApiParam("用户名") @RequestParam String name,
                         @ApiParam("密码") @RequestParam String password,
                         HttpServletRequest request) {
        if (loginChecker.needCheckCaptcha(name)) {
            throw new ResponseException("login.fail.too.many");
        }

        User user = userReadService.login(name, password);

        if (user == null) {
            loginChecker.incrErrorTimes(name);
            throw new ResponseException("user.login.fail");
        }

        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("online", SkyUserMaker.make(user));
        session.setAttribute("isAdmin", "admin".equalsIgnoreCase(user.getName()));

        return SkyUserMaker.make(user);
    }

    @ApiOperation(value = "注销", httpMethod = "POST")
    @PostMapping("logout")
    public Boolean logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return true;
    }

    @ApiOperation(value = "修改密码", httpMethod = "PUT")
    @PutMapping("change-password")
    public Boolean changePassword(String oldPassword, String newPassword) {
        Long userId = SessionContextUtils.getUserId();
        User user = userReadService.findById(userId);
        if (!Encryption.checkPassword(oldPassword, user.getSalt(), user.getPassword())) {
            throw new ResponseException("user.password.mismatch");
        }

        User toUpdate = new User();
        toUpdate.setId(userId);
        toUpdate.setPassword(Encryption.encrypt3DES(newPassword, user.getSalt()));

        return userWriteService.update(toUpdate);
    }

}
