package com.A.ui.service.V.impl;

import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;
import org.apache.log4j.Logger;

public enum ThoughtspotCacheService {

	INSTANCE;

	private long TIME_IN_CACHE = 0L; // Cache Never expires
	private static final String DB_CACHE = "thoughtspot_reports_cache";
	private static final Logger logger = Logger.getLogger(ThoughtspotCacheService.class);

	public void setTimeInCache(long timeInCache) {
		this.TIME_IN_CACHE = timeInCache;
	}


	public boolean clearTsCache(){
		logger.info("Clearing_all_TS_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(DB_CACHE);
			c.clear();
			logger.info("Clearing_all_TS_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_TS", e);
		}
		return Boolean.FALSE;
	}


	public void store(String key, Object data){		
		try {
			Cache c = CacheManager.getInstance().getCache(DB_CACHE);
			c.store(key, data, TIME_IN_CACHE);
		} catch (CacheException e) {
			logger.error("Exception_while_storing_TS_in_cache_for_key=" + key, e);
		}
	}


	public Object get(String key){
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(DB_CACHE);
			o = c.retrieve(key);
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_TS_from_cache_for_key=" + key, e);
		}
		return o;
	}
}
