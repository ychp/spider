package com.ychp.request.util;

import com.ychp.request.model.UserAgent;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Slf4j
public class RequestUtils {

    public static UserAgent getUaInfo(HttpServletRequest request){
        String ua = request.getHeader("User-Agent");
        UserAgent userAgent = UaUtils.parseUa(ua);
        userAgent = userAgent == null ? new UserAgent() : userAgent;
        return userAgent;
    }

    public static String getUrl(HttpServletRequest request){
        return request.getRequestURL().toString();
    }

}
