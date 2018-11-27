package com.ychp.session.impl;

import com.ychp.session.constant.SessionConstants;
import com.ychp.session.manager.SessionManager;
import com.ychp.session.utils.CookieUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
@Slf4j
public class SkySessionFilter implements Filter {

	@Getter
	@Setter
	private int cookieMaxAge;

	@Getter
	@Setter
	private String cookieDomain;

	@Getter
	@Setter
	private int maxInactiveInterval;

	@Getter
	@Setter
	private SessionManager sessionManager;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("init session filter");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (servletRequest instanceof SkyHttpServletRequestWrapper) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		SkyHttpServletRequestWrapper sessionRequest =
				new SkyHttpServletRequestWrapper(request, response, sessionManager);
		sessionRequest.setCookieDomain(cookieDomain);
		sessionRequest.setCookieMaxAge(cookieMaxAge);
		sessionRequest.setMaxInactiveInterval(maxInactiveInterval);
		filterChain.doFilter(sessionRequest, response);

		SkySession skySession = (SkySession) sessionRequest.getSession();
		if(!skySession.isValid()) {
			sessionManager.remove(skySession.getId());
			CookieUtils.del(request, SessionConstants.SESSION_COOKIE_KEY);
			return;
		}

		if (skySession.isChange()) {
			Map<String, Object> snapshot = skySession.snapshot();
			sessionManager.save(skySession.getId(), snapshot, maxInactiveInterval);
		} else {
			sessionManager.refresh(skySession.getId(), maxInactiveInterval);
		}
		CookieUtils.refresh(request, SessionConstants.SESSION_COOKIE_KEY, maxInactiveInterval);

	}

	@Override
	public void destroy() {
		log.info("destroy session filter");
	}
}
