package com.A.ui.service.V.impl;

import com.A.ui.vo.SessionVO;
import org.springframework.web.servlet.ModelAndView;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum CKOCacheService {

	INSTANCE;
	
	private SessionVO DEFAULT_NULL_SESSION_VO;
	private ModelAndView DEFAULT_NULL_MAV;
	private static final long SESSION_VO_TTL =  1800000;
	
	public static final String CKO_PREFIX = "CKO-";
	public static final String CKO_PREFIX_MAV = "CKOMAV-";
	public static final String CKO_PREFIX_TOKEN = "CKOToken-";
	
	public void store(final SessionVO sessionVO) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(CKO_PREFIX + sessionVO.getSessionId(), sessionVO, SESSION_VO_TTL);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void storeMAV(final ModelAndView mav, final SessionVO sessionVO, final String name) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(CKO_PREFIX_MAV + sessionVO.getSessionId() + name, mav, SESSION_VO_TTL);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void storeCSFToken(final String token, String id) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(CKO_PREFIX_TOKEN + id, token, SESSION_VO_TTL);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public SessionVO clear(final String id) {

		SessionVO vo = null;
		try {
			Cache c = CacheManager.getInstance().getCache();

			  vo = (SessionVO)c.remove(CKO_PREFIX + id);
			
			

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}
	
	public ModelAndView clearMAV(final String id, final String name) {

		ModelAndView vo = null;
		try {
			Cache c = CacheManager.getInstance().getCache();

			  vo = (ModelAndView)c.remove(CKO_PREFIX_MAV + id + name);
			
			

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}	
	
	public String clearCSFToken(final String id) {

		String vo = null;
		try {
			Cache c = CacheManager.getInstance().getCache();

			  vo = (String)c.remove(CKO_PREFIX_TOKEN + id);
			
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}

	
	
	public SessionVO get(final String id) {

		SessionVO vo = null;
		
		try {
			Cache c = CacheManager.getInstance().getCache();
			vo = (SessionVO)c.retrieve(CKO_PREFIX + id);

			if ((vo != null) && (vo instanceof SessionVO)) {
				return (SessionVO) vo;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DEFAULT_NULL_SESSION_VO;

	}
	
	public ModelAndView getMAV(final String id, final String name) {

		ModelAndView vo = new ModelAndView();
		
		try {
			Cache c = CacheManager.getInstance().getCache();
			vo = (ModelAndView)c.retrieve(CKO_PREFIX_MAV + id + name);

			if ((vo != null) && (vo instanceof ModelAndView)) {
				return (ModelAndView) vo;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DEFAULT_NULL_MAV;

	}	
	
	public String getToken(final String id) {

		String vo = null;
		
		try {
			Cache c = CacheManager.getInstance().getCache();
			vo = (String)c.retrieve(CKO_PREFIX_TOKEN + id);

			if ((vo != null) && (vo instanceof String)) {
				return (String) vo;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}	
}
