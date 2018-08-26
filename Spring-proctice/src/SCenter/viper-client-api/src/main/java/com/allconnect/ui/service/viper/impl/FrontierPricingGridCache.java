package com.A.ui.service.V.impl;

import org.apache.log4j.Logger;

import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum FrontierPricingGridCache  {

	INSTANCE;

	private static final String FRONTIER_PRICING_GRID_CACHE = "frontier_pricing_grid_cache";

	// Cache expiry set to 24 hrs
	private static final long CACHE_TIMEOUT = 6000 * 10 * 60;

	private static final Logger logger = Logger.getLogger(FrontierPricingGridCache.class);

	public boolean clearFrontierPricingGridCache(){
		logger.info("Clearing_all_FrontierPricingGrid_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(FRONTIER_PRICING_GRID_CACHE);
			c.clear();
			logger.info("Clearing_all_FrontierPricingGrid_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_FrontierPricingGrid_cache", e);
		}
		return Boolean.FALSE;
	}


	public void store(String key, Object frontierPricingGridMap){		
		try {
			Cache c = CacheManager.getInstance().getCache(FRONTIER_PRICING_GRID_CACHE);
			c.store(key, frontierPricingGridMap, CACHE_TIMEOUT);
		} catch (CacheException e) {
			logger.error("Exception_while_storing_FrontierPricingGrid_in_cache_for_key=" + key, e);
		}
	}


	public Object get(String key){
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(FRONTIER_PRICING_GRID_CACHE);
			o = c.retrieve(key);
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_FrontierPricingGridMap_from_cache_for_key=" + key, e);
		}
		return o;
	}

}
