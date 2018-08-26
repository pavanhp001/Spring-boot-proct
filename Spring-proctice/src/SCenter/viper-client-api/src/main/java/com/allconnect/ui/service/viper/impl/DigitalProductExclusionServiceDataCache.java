package com.A.ui.service.V.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.A.comm.manager.jms.util.StringUtil;
import com.A.ui.service.config.ConfigRepo;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum DigitalProductExclusionServiceDataCache  {

	INSTANCE;

	private static final String PRODUCTS_EXCLUSION_CACHE = "web_product_exclusion_cache";

	// Cache expiry set to 24 hrs
	private static final long CACHE_TIMEOUT = 0;
	
	private static final String FALSE = "false";

	private static final Logger logger = Logger.getLogger(DigitalProductExclusionServiceDataCache.class);

	public boolean clearAllProductCache(){
		logger.info("Clearing_all_product_exclusion_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(PRODUCTS_EXCLUSION_CACHE);
			c.clear();
			logger.info("Clearing_all_product_exclusion_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_product_exclusion_cache", e);
		}
		return Boolean.FALSE;
	}


	public void store(Integer key, List<String> productExclList, String cacheTimeout){		
		try {
			// we are storing products in cache when "isCacheEnabled" flag value is "true" or null.
			// Otherwise("false") not storing value in cache.
			String isCacheEnabled = ConfigRepo.getString("*.cache_enable") != null ? ConfigRepo.getString("*.cache_enable") : "true";
			if( !FALSE.equalsIgnoreCase(isCacheEnabled) )
			{
				Cache c = CacheManager.getInstance().getCache(PRODUCTS_EXCLUSION_CACHE);
				if (!StringUtil.isEmpty(cacheTimeout)){
					c.store(key, productExclList, TimeUnit.HOURS.toMillis(Long.valueOf(cacheTimeout)));
				}else{
					c.store(key, productExclList, CACHE_TIMEOUT);
				}
				
			} else {
				logger.info("No product caching exclusion for key=" + key);
			}
		} catch (CacheException e) {
			logger.error("Exception_while_storing_product_exclusion_in_cache_for_key=" + key, e);
		}
	}


	public Object get(Integer key){
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(PRODUCTS_EXCLUSION_CACHE);
			o = c.retrieve(key);
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_product_exclusion_from_cache_for_key=" + key, e);
		}
		return o;
	}
	
	public boolean clearCacheOnKey(final String key) {
		logger.info("Clearing_product_exclusion_cache_started_for_key=" +key);
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(PRODUCTS_EXCLUSION_CACHE);
			c.remove(key);
			logger.info("Product_exclusion_cache_size="+ c.size());
			logger.info("Clearing_all_product_exclusion_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_product_exclusion_in_cache_with_key=" + key, e);
		}
		return Boolean.FALSE;
	}
}
