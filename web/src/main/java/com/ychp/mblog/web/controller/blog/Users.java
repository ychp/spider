package com.ychp.spider.web.controller.blog;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.SkyUser;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.Encryption;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.ip.component.IPServer;
import com.ychp.spider.web.async.user.UserLoginEvent;
import com.ychp.spider.web.async.user.UserRegisterEvent;
import com.ychp.spider.web.constant.SessionConstants;
import com.ychp.spider.web.controller.bean.request.user.UserProfileRequest;
import com.ychp.spider.web.controller.bean.request.user.UserRegisterRequest;
import com.ychp.spider.web.util.SkyUserMaker;
import com.ychp.user.api.bean.query.UserLoginLogCriteria;
import com.ychp.user.api.bean.response.UserLoginLogVO;
import com.ychp.user.api.bean.response.UserVO;
import com.ychp.user.cache.AddressCacher;
import com.ychp.user.model.Address;
import com.ychp.user.model.User;
import com.ychp.user.model.UserProfile;
import com.ychp.user.api.service.UserLoginLogReadService;
import com.ychp.user.api.service.UserReadService;
import com.ychp.user.api.service.UserWriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
@Api(description = "面向用户的用户接口")
@RestController
@RequestMapping("/api/user")
public class Users {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserWriteService userWriteService;

    @Autowired
    private AddressCacher addressCacher;

    @Autowired
    private IPServer ipServer;

    @Autowired
    private AsyncPublisher publisher;

    @Autowired
    private UserLoginLogReadService userLoginLogReadService;

    @ApiOperation(value = "注册", httpMethod = "POST")
    @PostMapping("register")
    public SkyUser register(@RequestBody UserRegisterRequest registerRequest, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String token = (String) session.getAttribute(SessionConstants.CAPTCHA_TOKEN);
        if(!Objects.equals(registerRequest.getCaptcha(), token)) {
            throw new ResponseException("register.captcha.mismatch");
        }

        session.removeAttribute(SessionConstants.CAPTCHA_TOKEN);

        if(StringUtils.isEmpty(registerRequest.getName()) || StringUtils.isEmpty(registerRequest.getPassword())) {
            throw new ResponseException("user.register.info.empty");
        }

        User user = userReadService.findByName(registerRequest.getName());
        if(user != null) {
            throw new ResponseException("user.name.exist");
        }

        user = new User();
        user.setName(registerRequest.getName());
        user.setNickName(registerRequest.getName());
        user.setSalt(Encryption.getSalt());
        user.setPassword(Encryption.encrypt3DES(registerRequest.getPassword(), user.getSalt()));
        userWriteService.create(user);
        session.setAttribute("userId", user.getId());

        SkyUser skyUser = SkyUserMaker.make(user);
        skyUser.setIp(ipServer.getIp(request));

        publisher.post(new UserLoginEvent(skyUser));
        publisher.post(new UserRegisterEvent(skyUser));
        return skyUser;
    }

    @ApiOperation(value = "获取用户基础信息(用户详情)", httpMethod = "GET")
    @GetMapping("profile")
    public UserVO detail() {
        Long userId = SessionContextUtils.getUserId();
        return userReadService.findDetailById(userId);
    }

    @ApiOperation(value = "更新用户基础信息(用户详情)", httpMethod = "POST")
    @PostMapping("profile")
    public Boolean updateProfile(@RequestBody UserProfileRequest request) {
        Long userId = SessionContextUtils.getUserId();

        User toUpdate = generateUser(request, userId);
        if(toUpdate != null) {
            userWriteService.update(toUpdate);
        }

        UserProfile toUpdateProfile = generateUserProfile(request, userId);
        if(toUpdateProfile != null) {
            userWriteService.saveProfile(toUpdateProfile);
        }

        return true;
    }

    private User generateUser(UserProfileRequest request, Long userId) {
        if(StringUtils.isEmpty(request.getNickName())) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        user.setNickName(request.getNickName());
        return user;
    }

    private UserProfile generateUserProfile(UserProfileRequest request, Long userId) {
        if(StringUtils.isEmpty(request.getAvatar())
                && StringUtils.isEmpty(request.getHomePage())
                && StringUtils.isEmpty(request.getGender())
                && StringUtils.isEmpty(request.getNickName())
                && StringUtils.isEmpty(request.getProfile())
                && StringUtils.isEmpty(request.getRealName())
                && StringUtils.isEmpty(request.getSynopsis())
                && request.getCountryId() == null
                && request.getProvinceId() == null
                && request.getCityId() == null
                && request.getBirth() == null) {
            return null;
        }
        UserProfile profile = new UserProfile();
        profile.setUserId(userId);
        profile.setBirth(request.getBirth());
        profile.setHomePage(request.getHomePage());
        profile.setGender(request.getGender());
        profile.setAvatar(request.getAvatar());
        profile.setRealName(request.getRealName());
        profile.setSynopsis(request.getSynopsis());
        profile.setProfile(request.getProfile());

        if(request.getCountryId() != null) {
            profile.setCountryId(request.getCountryId());
            Address country = addressCacher.findById(request.getCountryId());
            if(country == null) {
                throw new ResponseException("country.not.exist");
            }
            profile.setCountry(country.getName());
        }

        if(request.getProvinceId() != null) {
            profile.setProvinceId(request.getProvinceId());
            Address province = addressCacher.findById(request.getProvinceId());
            if(province == null) {
                throw new ResponseException("province.not.exist");
            }
            profile.setProvince(province.getName());
        }

        if(request.getCityId() != null) {
            profile.setCityId(request.getCityId());
            Address city = addressCacher.findById(request.getCityId());
            if(city == null) {
                throw new ResponseException("city.not.exist");
            }
            profile.setCity(city.getName());
        }
        return profile;
    }

    @ApiOperation("用户登录日志分页接口")
    @GetMapping("login-log/paging")
    public Paging<UserLoginLogVO> pagingLoginLog(UserLoginLogCriteria criteria) {
        criteria.setUserId(SessionContextUtils.getUserId());
        return userLoginLogReadService.paging(criteria);
    }

}
