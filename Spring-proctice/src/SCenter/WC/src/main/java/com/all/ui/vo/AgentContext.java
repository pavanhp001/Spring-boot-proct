package com.A.ui.vo;

import java.util.Calendar;

import org.springframework.web.servlet.ModelAndView;

import com.A.vo.UserAuthorization;

public class AgentContext {

	private SalesCenterVO salescontext;
	public SalesCenterVO getSalescontext() {
		return salescontext;
	}
	public void setSalescontext(SalesCenterVO salescontext) {
		this.salescontext = salescontext;
	}
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public ModelAndView getMavFromSession() {
		return mavFromSession;
	}
	public void setMavFromSession(ModelAndView mavFromSession) {
		this.mavFromSession = mavFromSession;
	}
	public UserAuthorization getUserAuthorization() {
		return userAuthorization;
	}
	public void setUserAuthorization(UserAuthorization userAuthorization) {
		this.userAuthorization = userAuthorization;
	}
	public String getCometd_url() {
		return cometd_url;
	}
	public void setCometd_url(String cometdUrl) {
		cometd_url = cometdUrl;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public Calendar getIdlePageStartTime() {
		return idlePageStartTime;
	}
	public void setIdlePageStartTime(Calendar idlePageStartTime) {
		this.idlePageStartTime = idlePageStartTime;
	}
	public Double getWebMetricStartTime() {
		return webMetricStartTime;
	}
	public void setWebMetricStartTime(Double webMetricStartTime) {
		this.webMetricStartTime = webMetricStartTime;
	}
	private String password;
	private String phoneId;
	private ModelAndView mavFromSession;
	private UserAuthorization userAuthorization;
	private String cometd_url;
	private String urlPath;
	private Calendar idlePageStartTime;
	private Double webMetricStartTime; 
}
