package com.A.ui.service.V.impl;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum MDUCacheService {

	INSTANCE;

	private static final String MDU_CACHE = "mdu_properties_cache";

	// Cache expiry set to 24 hrs
	private static final long CACHE_TIMEOUT = 0;
	
	private static final String FALSE = "false";

	private static final Logger logger = Logger.getLogger(MDUCacheService.class);

	public boolean clearAllMDUCache(){
		logger.info("Clearing_all_mdu_properties_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(MDU_CACHE);
			c.clear();
			logger.info("Clearing_all_mdu_properties_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_mdu_properties_cache", e);
		}
		return Boolean.FALSE;
	}


	public void store(String programExternalId, String mduProperties){		
			// we are storing products in cache when "isCacheEnabled" flag value is "true" or null.
			// Otherwise("false") not storing value in cache.
			try {
				Cache c = CacheManager.getInstance().getCache(MDU_CACHE);
				c.store(programExternalId, mduProperties);
			} catch (CacheException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public Object get(String key){
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(MDU_CACHE);
			o = c.retrieve(key);
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_mdu_properties_cache_for_key=" + key, e);
		}
		return o;
	}
	
	public boolean clearCacheOnKey(final String key) {
		logger.info("Clearing_mdu_properties_cache_started_for_key=" +key);
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(MDU_CACHE);
			c.remove(key);
			logger.info("Clearing_mdu_properties_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_mdu_properties_cache_with_key=" + key, e);
		}
		return Boolean.FALSE;
	}
	
	public void store(Object stateMDUMap){		
		// we are storing products in cache when "isCacheEnabled" flag value is "true" or null.
		// Otherwise("false") not storing value in cache.
		try {
			Cache c = CacheManager.getInstance().getCache(MDU_CACHE);
			c.store("mduProperties", stateMDUMap);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
}
	public void storeATTV6Points(Object attV6PointsMap, long attV6PointsCacheTimeout){  
		  // we are storing products in cache when "isCacheEnabled" flag value is "true" or null.
		  // Otherwise("false") not storing value in cache.
		  try {
			   Cache c = CacheManager.getInstance().getCache(MDU_CACHE);
			   c.store("attV6Points", attV6PointsMap, attV6PointsCacheTimeout);
		  } catch (CacheException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		  }
	}
}
