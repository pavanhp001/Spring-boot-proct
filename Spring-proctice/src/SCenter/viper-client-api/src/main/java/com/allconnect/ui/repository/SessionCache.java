package com.A.ui.repository;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.A.ui.service.config.ConfigRepo;
import com.A.vo.AuthorizationMap;
import com.A.vo.UserAuthorization;

public enum SessionCache {

	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(SessionCache.class);

	public static final AuthorizationMap<String, String> EMPTY_ROLES = new AuthorizationMap<String, String>();

	public static final String AUTHENTICATION_SUBJECT = "SUBJECT";
	public static final String DEFAULT_EMPTY_QUOTES = "";
	public static final String DEFAULT_DATE_FORMAT = "MMM/dd/yyyy";
	public static final String ACCESS_TYPE = "ACCESS_TYPE";

	public String getUploadWorkDirectory() {

		String config = ConfigRepo.getString("upload_dir");

		if ((config == null) || (config.length() == 0)) {
			return "." +File.separator + "work"
			+ File.separator;
		}

		return config;
	}

	public long getOfferTTL() {
		return ConfigRepo.getInt("*.offer_ttl");
	}

	public long getLockTTL() {
		return ConfigRepo.getInt("*.lock_ttl");
	}

	public long getUnLockTTL() {
		return ConfigRepo.getInt("*.unlock_ttl");
	}
	
	public long getLockCustTTL() {
		return ConfigRepo.getInt("*.lock_cust_ttl");
	}

	public String getResource(HttpServletRequest request) {

		boolean isUseCDN = ConfigRepo.getBoolean("*.use_cdn");

		if (isUseCDN) {
			String cdnLocation = ConfigRepo.getString("*.cdn_location");

			if ((cdnLocation != null) && (cdnLocation.length() > 0)) {
				return cdnLocation;
			}
		}

		return request.getContextPath();
	}

	public String getSource() {
		String source = ConfigRepo.getString("*.source");

		return source != null ? source : "";
	}

	public String getCurrentDateTime() {
		String dateFormat = ConfigRepo.getString("*.date_format");

		if ((dateFormat == null) || (dateFormat.length() == 0)) {
			dateFormat = DEFAULT_DATE_FORMAT;
		}

		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		return format.format(new Date());
	}

	public Calendar getDate() {

		return Calendar.getInstance();
	}

	/*
	 * ***********************************************************
	 * ************* User Specific Properties ***************************
	 * **********************************************************
	 */

	public void clear(HttpSession session) {
		logger.info("clear: invalidating the session ");
		session.setAttribute(AUTHENTICATION_SUBJECT, null);
		session.invalidate();
	}

	public void set(HttpSession session, UserAuthorization auth) {

		if ((session == null) || (auth == null)) {
			return;
		}

		auth.setSessionId(session.getId());
		session.setAttribute(AUTHENTICATION_SUBJECT, auth);

	}

	/**
	 * To set Provider to session
	 * 
	 * @param session
	 * @param activeProvider
	 * @param activeProviderName
	 * @param image
	 */
	public void setProvider(HttpSession session, String activeProvider,
			String activeProviderName, String image) {

		logger.debug("setProvider: set provider to session");
		
		UserAuthorization auth = getSubject(session);

		if (auth != null) {
			auth.setActiveProvider(activeProvider);
			auth.setActiveProviderName(activeProviderName);
			auth.setImage(image);
		}
	}
	
	/**
	 * To clear provider in session
	 * 
	 * @param session
	 */
	public void clearProvider(HttpSession session) {

		logger.debug("clearProvider: clearing provider from session");
		
		UserAuthorization auth = getSubject(session);

		if (auth != null) {
			auth.setActiveProvider("");
			auth.setActiveProviderName("");
			auth.setImage("");
		}
	}

	public UserAuthorization getSubject(HttpSession session) {

		if (session == null) {
			return null;
		}

		return (UserAuthorization) session.getAttribute(AUTHENTICATION_SUBJECT);

	}

	public String getActiveProvider(HttpSession session) {

		if (session == null) {
			return null;
		}


		UserAuthorization auth = getSubject(session);

		return (auth != null) ? auth.getActiveProvider() : DEFAULT_EMPTY_QUOTES;
	}

	public String getProviderImage(HttpSession session) {
		UserAuthorization auth = getSubject(session);

		return (auth != null) ? auth.getImage() : DEFAULT_EMPTY_QUOTES;
	}

	public String getActiveProviderName(HttpSession session) {
		UserAuthorization auth = getSubject(session);

		return (auth != null) ? auth.getActiveProviderName() : DEFAULT_EMPTY_QUOTES;
	}

	public String getUserName(HttpSession session) {
		UserAuthorization auth = getSubject(session);

		return (auth != null) ? auth.getUserName() : DEFAULT_EMPTY_QUOTES;
	}

	public String getAgentId(HttpSession session) {
		UserAuthorization auth = getSubject(session);

		return (auth != null) ? auth.getUserLogin() : DEFAULT_EMPTY_QUOTES;
	}

	public boolean isAuthenticated(HttpSession session) {
		UserAuthorization auth = getSubject(session);
		return (auth != null);
	}

	public AuthorizationMap<String, String> getRoles(HttpSession session) {
		UserAuthorization auth = getSubject(session);

		return (auth != null) ? auth.getRoles() : EMPTY_ROLES;
	}

	public boolean isAccord(){
		String source = ConfigRepo.getString("*.sales_context");
		if(source != null && source.trim().equals("SYP")){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @param session
	 * @param accessType
	 */
	public void setAccessType(HttpSession session, String accessType){
		if (session == null) {
			return;
		}

		session.setAttribute(ACCESS_TYPE, accessType);
	}
	
	/**
	 * @param session
	 * @return
	 */
	public String getAccessType(HttpSession session){
		if (session == null) {
			return DEFAULT_EMPTY_QUOTES;
		}

		return (String)session.getAttribute(ACCESS_TYPE);
	}

}
