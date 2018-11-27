package com.ychp.session.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ychp.session.manager.SessionManager;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yingchengpeng
 * @date 2018/8/16
 */
public class SkySession implements HttpSession {

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String id;

	private long creationTime;

	private volatile long lastAccessedTime;

	/**
	 * 最大不活跃时间
	 */
	private int maxInactiveInterval;

	/**
	 * redis中的数据
	 */
	private ConcurrentMap<String, Object> dbAttributes = new ConcurrentHashMap<>();

	/**
	 * 本次请求中的新数据
	 */
	private ConcurrentMap<String, Object> newAttributes = new ConcurrentHashMap<>();

	/**
	 * 本次请求中需要删除的数据
	 */
	private List<String> deleteAttributes = Collections.synchronizedList(Lists.newArrayList());

	/**
	 * 是否发生变化
	 */
	@Getter
	private volatile boolean isChange = false;

	/**
	 * 是否有效
	 */
	@Getter
	private volatile boolean isValid = true;

	public SkySession(String id, HttpServletRequest request,
	                  HttpServletResponse response,
	                  SessionManager sessionManager,
	                  int maxInactiveInterval) {
		this.request = request;
		this.response = response;
		this.id = id;
		this.creationTime = System.currentTimeMillis();
		this.lastAccessedTime = creationTime;
		this.maxInactiveInterval = maxInactiveInterval;
		Map<String, Object> dbDatas = sessionManager.get(id);
		if (dbDatas != null) {
			dbAttributes.putAll(dbDatas);
		}
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	@Override
	public ServletContext getServletContext() {
		return this.request.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int i) {
		this.maxInactiveInterval = i;
	}

	@Override
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		if(deleteAttributes.contains(name)) {
			return null;
		} else if(newAttributes.containsKey(name)) {
			return newAttributes.get(name);
		}
		return dbAttributes.get(name);
	}

	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	@Override
	public Enumeration getAttributeNames() {
		Set<String> names = Sets.newHashSet();
		names.addAll(newAttributes.keySet());
		names.addAll(dbAttributes.keySet());
		return Collections.enumeration(names);
	}

	@Override
	public String[] getValueNames() {
		return getValueNames().clone();
	}

	@Override
	public void setAttribute(String name, Object value) {
		if(StringUtils.isEmpty(name)) {
			return;
		}

		if(value == null) {
			dbAttributes.remove(name);
			newAttributes.remove(name);
			deleteAttributes.add(name);
			isChange = true;
			return;
		}

		newAttributes.put(name, value);
		isChange = true;
	}

	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		if(StringUtils.isEmpty(name)) {
			return;
		}

		dbAttributes.remove(name);
		newAttributes.remove(name);
		deleteAttributes.add(name);
		isChange = true;
	}

	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	@Override
	public void invalidate() {
		this.isValid = false;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	public Map<String, Object> snapshot() {
		Map<String, Object> snapshot = Maps.newHashMap();
		snapshot.putAll(dbAttributes);
		snapshot.putAll(newAttributes);
		return snapshot;
	}

}
