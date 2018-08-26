package com.A.ui.activity;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum ActivitySafeRedirect {

	INSTANCE;

	public static final String LOGIN_URI = "http://host:portcontext/salescenter/login.html";
	public static final String HOME_URI = "http://host:portcontext/salescenter/home.html";
	public static final String CONTEXT_NAME = "salescenter";
	public static final String CONTEXT_PATH = "/salescenter";

	public void execute(HttpServletRequest httpReq, HttpServletResponse httpRes) {

		execute(LOGIN_URI, httpReq, httpRes);
	}

	public String getHomeUrl(HttpServletRequest httpReq,
			HttpServletResponse httpRes) {

		return getUrl(HOME_URI, httpReq, httpRes);
	}

	public String getLoginUrl(HttpServletRequest httpReq,
			HttpServletResponse httpRes) {

		return getUrl(LOGIN_URI, httpReq, httpRes);
	}

	public String getUrl(String uri, HttpServletRequest httpReq,
			HttpServletResponse httpRes) {

		String port = String.valueOf(httpReq.getServerPort());
		String host = httpReq.getServerName();
		String contextPath = cleanContextPath( httpReq.getContextPath());

		String loginUri = uri.replace("port", port).replace("host", host)
				.replace("context", contextPath);

		return loginUri;
	}

	public void execute(final String uri, HttpServletRequest httpReq,
			HttpServletResponse httpRes) {

		String port = String.valueOf(httpReq.getServerPort());
		String host = httpReq.getServerName();
		String contextPath = cleanContextPath( httpReq.getContextPath());

		String loginUri = uri.replace("port", port).replace("host", host)
				.replace("context", contextPath); 

		try {
			httpRes.sendRedirect(loginUri);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String cleanContextPath(String contextPath) {
		if ((contextPath == null) || (contextPath.length() == 0)) {
			contextPath = "";
		} else {
			//the only allowed context path is fulfillment
			//TODO:should be in the database
			contextPath = CONTEXT_PATH;
		}

		return contextPath;
	}
}
