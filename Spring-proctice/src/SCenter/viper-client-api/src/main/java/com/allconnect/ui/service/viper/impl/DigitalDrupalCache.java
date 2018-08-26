package com.A.ui.service.V.impl;

import org.apache.log4j.Logger;

import com.A.ui.service.config.ConfigRepo;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum DigitalDrupalCache  {

	INSTANCE;

	private static final String PRODUCTS_CACHE = "web_drupal_cache";

	// Cache expiry set to 24 hrs
	private static final long CACHE_TIMEOUT = 0;
	
	private static final String FALSE = "false";

	private static final Logger logger = Logger.getLogger(DigitalDrupalCache.class);

	public boolean clearAllProductCache(){
		logger.info("Clearing_all_product_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(PRODUCTS_CACHE);
			c.clear();
			logger.info("Clearing_all_product_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_product_cache", e);
		}
		return Boolean.FALSE;
	}


	public void store(String key, Object product){		
		try {
			// we are storing products in cache when "isCacheEnabled" flag value is "true" or null.
			// Otherwise("false") not storing value in cache.
			String isCacheEnabled = ConfigRepo.getString("*.cache_enable") != null ? ConfigRepo.getString("*.cache_enable") : "true";
			if( !FALSE.equalsIgnoreCase(isCacheEnabled) )
			{
				Cache c = CacheManager.getInstance().getCache(PRODUCTS_CACHE);
				c.store(key, product, CACHE_TIMEOUT);
			} else {
				logger.info("No product caching for key=" + key);
			}
		} catch (CacheException e) {
			logger.error("Exception_while_storing_product_in_cache_for_key=" + key, e);
		}
	}


	public Object get(String key){
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(PRODUCTS_CACHE);
			o = c.retrieve(key);
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_product_from_cache_for_key=" + key, e);
		}
		return o;
	}
	
	public boolean clearCacheOnKey(final String key) {
		logger.info("Clearing_product_cache_started_for_key=" +key);
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(PRODUCTS_CACHE);
			c.remove(key);
			logger.info("Product_cache_size="+ c.size());
			logger.info("Clearing_all_product_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_product_in_cache_with_key=" + key, e);
		}
		return Boolean.FALSE;
	}
}
