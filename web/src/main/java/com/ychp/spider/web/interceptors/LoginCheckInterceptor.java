package com.ychp.spider.web.interceptors;

import com.ychp.common.model.SkyUser;
import com.ychp.common.util.SessionContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
@Slf4j
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if(uri.trim().startsWith("/api") || "/login".equalsIgnoreCase(uri.trim()) || "login".equalsIgnoreCase(uri.trim())) {
            return true;
        }

        SkyUser skyUser = SessionContextUtils.currentUser();
        if(skyUser != null) {
            if (!"admin".equals(skyUser.getName()) && "/users".equals(uri.trim())) {
                response.sendRedirect("/login");
            }
            return true;
        }

        response.sendRedirect("/login");
        return true;
    }

}
