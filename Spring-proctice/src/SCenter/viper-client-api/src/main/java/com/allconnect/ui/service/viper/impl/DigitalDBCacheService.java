package com.A.ui.service.V.impl;

import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;
import org.apache.log4j.Logger;

public enum DigitalDBCacheService {

	INSTANCE;

	private long TIME_IN_CACHE = 120 * 60 * 1000; // 120 MINUTES
	private static final String DB_CACHE = "db_cache";
	private static final Logger logger = Logger.getLogger(DigitalDBCacheService.class);

	public void setTimeInCache(long timeInCache) {
		this.TIME_IN_CACHE = timeInCache;
	}


	public boolean clearAllDBCache(){
		logger.info("Clearing_all_DB_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(DB_CACHE);
			c.clear();
			logger.info("Clearing_all_db_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_db", e);
		}
		return Boolean.FALSE;
	}


	public void store(String key, Object data){		
		try {
			Cache c = CacheManager.getInstance().getCache(DB_CACHE);
			c.store(key, data, TIME_IN_CACHE);
		} catch (CacheException e) {
			logger.error("Exception_while_storing_product_in_cache_for_key=" + key, e);
		}
	}


	public Object get(String key){
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(DB_CACHE);
			o = c.retrieve(key);
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_db_from_cache_for_key=" + key, e);
		}
		return o;
	}

	public boolean clearCacheOnKey(final String key) {
		logger.info("Clearing_db_started_for_key=" +key);
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(DB_CACHE);
			c.remove(key);
			logger.info("db_size="+ c.size());
			logger.info("Clearing_all_db_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_product_in_cache_with_key=" + key, e);
		}
		return Boolean.FALSE;
	}

}
