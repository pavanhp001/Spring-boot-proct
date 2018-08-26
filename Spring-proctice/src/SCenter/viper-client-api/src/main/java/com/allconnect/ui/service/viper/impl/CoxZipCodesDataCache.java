package com.A.ui.service.V.impl;

import org.apache.log4j.Logger;

import com.A.comm.manager.jms.util.StringUtil;
import com.A.ui.service.config.ConfigRepo;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum CoxZipCodesDataCache  {

	INSTANCE;

	private static final String ZIPCODE_CACHE = "cox_zip_cache";

	// Cache expiry set to 24 hrs
	private static final long CACHE_TIMEOUT = 6000 * 10 * 60;

	private static final Logger logger = Logger.getLogger(CoxZipCodesDataCache.class);

	public boolean clearZipCodesCache(){
		logger.info("Clearing_all_Zipcodes_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(ZIPCODE_CACHE);
			c.clear();
			logger.info("Clearing_all_Zipcodes_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_Zipcodes_cache", e);
		}
		return Boolean.FALSE;
	}


	public void store(String key, Object Zipcodes){		
		try {
			Cache c = CacheManager.getInstance().getCache(ZIPCODE_CACHE);
			c.store(key, Zipcodes, CACHE_TIMEOUT);
		} catch (CacheException e) {
			logger.error("Exception_while_storing_product_in_cache_for_key=" + key, e);
		}
	}


	public Object get(String key){
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(ZIPCODE_CACHE);
			o = c.retrieve(key);
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_product_from_cache_for_key=" + key, e);
		}
		return o;
	}

}
