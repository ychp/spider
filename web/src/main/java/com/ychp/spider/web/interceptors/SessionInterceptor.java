package com.ychp.spider.web.interceptors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.SkyUser;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.web.util.SkyUserMaker;
import com.ychp.user.cache.UserCacher;
import com.ychp.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserCacher userCacher;

    @Value("${cache.expire.time:60}")
    private Long expiredTime;
    private LoadingCache<String, List<String>> white;

    @PostConstruct
    public void init() {

        white = CacheBuilder.newBuilder()
                .expireAfterWrite(expiredTime, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(1000)
                .build(new CacheLoader<String, List<String>>() {
                    @Override
                    public List<String> load(String s) {
                        return Lists.newArrayList(
                                "/api/address/.*",
                                "/api/article/.*",
                                "/api/comment/.*",
                                "/api/category/.*",
                                "/api/label/.*",
                                "/api/friend-link/visible",
                                "/api/see-log",
                                "/api/file/.*",
                                "/api/user/captcha",
                                "/api/user/register",
                                "/api/user/login",
                                "/api/search/.*",
                                "/api/v2/api-docs");
                    }
                });

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        String uri = request.getRequestURI();

        if(userId != null) {
            User user = userCacher.findById(Long.valueOf(userId.toString()));
            SkyUser skyUser = SkyUserMaker.make(user);
            SessionContextUtils.put(skyUser);
            log.info("check user = {}", skyUser);
            return true;
        }

        if(contains(white.get("all"), uri) || !uri.trim().startsWith("/api")) {
            return true;
        }

        throw new ResponseException(401, "user.not.login");
    }

    private boolean contains(List<String> uris, String uri) {
        for(String uriReg : uris) {
            if(uri.matches(uriReg)) {
                return true;
            }
        }
        return false;
    }

}
