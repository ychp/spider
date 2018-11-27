package com.ychp.session.impl;

import com.ychp.session.constant.SessionConstants;
import com.ychp.session.manager.SessionManager;
import com.ychp.session.utils.CookieUtils;
import com.ychp.session.utils.SessionIdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
public class SkyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletRequest request;

	private HttpServletResponse response;

	@Getter
	@Setter
	private SkySession skySession;

	@Getter
	@Setter
	private int cookieMaxAge;

	@Getter
	@Setter
	private String cookieDomain;

	@Getter
	@Setter
	private int maxInactiveInterval;

	private final SessionManager sessionManager;

	@Autowired
	public SkyHttpServletRequestWrapper(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    SessionManager sessionManager) {
		super(request);
		this.request = request;
		this.response = response;
		this.sessionManager = sessionManager;
	}

	@Override
	public HttpSession getSession(boolean create) {
		return this.getSession();
	}

	@Override
	public HttpSession getSession() {
		if(skySession != null) {
			return skySession;
		}
		String sid = CookieUtils.getValue(this.request, SessionConstants.SESSION_COOKIE_KEY);
		if(StringUtils.isEmpty(sid)) {
			sid = SessionIdGenerator.getId();
			CookieUtils.set(response, SessionConstants.SESSION_COOKIE_KEY, sid, cookieMaxAge, cookieDomain);
		}

		skySession = new SkySession(sid, request, response, sessionManager, maxInactiveInterval);
		return skySession;
	}

}
