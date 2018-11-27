package com.ychp.session.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
public class CookieUtils {

	public static String getValue(HttpServletRequest request, String name) {
		Cookie cookie = get(request, name);
		return cookie == null ? null : cookie.getValue();
	}

	public static Cookie get(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if(Objects.equals(cookie.getName(), name)) {
				return cookie;
			}
		}
		return null;
	}

	public static void set(HttpServletResponse response,
	                       String name, String value,
	                       int maxAge, String domain) {
		Cookie newCookie = new Cookie(name, value);
		newCookie.setMaxAge(maxAge);
		newCookie.setDomain(domain);
		newCookie.setPath("/");
		response.addCookie(newCookie);
	}

	public static void del(HttpServletRequest request, String name) {
		refresh(request, name, 0);
	}

	public static void refresh(HttpServletRequest request, String name, int maxAge) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return;
		}
		for (Cookie cookie : cookies) {
			if(Objects.equals(cookie.getName(), name)) {
				cookie.setMaxAge(maxAge);
			}
		};
	}

}
