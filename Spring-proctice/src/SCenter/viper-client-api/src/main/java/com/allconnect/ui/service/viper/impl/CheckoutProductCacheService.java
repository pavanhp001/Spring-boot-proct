package com.A.ui.service.V.impl;

import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductResponseType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;
import org.apache.log4j.Logger;

public enum CKOProductCacheService {

	INSTANCE;
	
	private static final String CKO_PRODUCTS_CACHE = "CKO_products_cache";
	private static final long TIME_IN_CACHE = 24 * 60 * 60 * 1000; // 24 Hours
	public static final String PRODUCT_PREFIX = "CKO_product";
	private ProductInfoType DEFAULT_NULL_PRODUCT_TYPE = null;
	private static final Logger logger = Logger.getLogger(CKOProductCacheService.class);

	public void store(final ProductInfoType product, final String providerId, final String productId) {

		// Use the cache manager to create the default cache
		try {
			String key = PRODUCT_PREFIX + "_" + providerId + "_" +productId;
			Cache c = CacheManager.getInstance().getCache(CKO_PRODUCTS_CACHE);
			c.store(key, product, TIME_IN_CACHE);
		} catch (CacheException e) {
			logger.error("Exception_while_storing_product_in_cache_with_productId=" + productId, e);
		}
	}

	public boolean clearCKOProductCacheOnKey(String key) {
		
		ProductInfoType result = null;
		String keyVal = PRODUCT_PREFIX + "_" + key;
		try {
			logger.info("Clearing_product_cache_started_for_key=" +keyVal);
			Cache c = CacheManager.getInstance().getCache(CKO_PRODUCTS_CACHE);
			c.remove(keyVal);
			logger.info("Clearing_product_cache_on_key_completed_successfully");
			return Boolean.TRUE;
		} 
		catch (CacheException e) {
			logger.error("Exception_while_clearing_product_in_cache_with_key=" + keyVal, e);
		}

		return Boolean.FALSE;

	}
	
	public boolean clearAllCKOProductCache() {
		logger.info("Clearing_all_CKO_product_cache_started");
		ProductInfoType result = null;

		try {
			Cache c = CacheManager.getInstance().getCache(CKO_PRODUCTS_CACHE);
			c.clear();
			logger.info("Clearing_all_product_cache_completed_successfully");
			return Boolean.TRUE;
		} 
		catch (CacheException e) {
			logger.error("Exception_while_clearing_product_cache", e);
		}

		return Boolean.FALSE;

	}

	public ProductInfoType get(String key) {
		String keyVal = PRODUCT_PREFIX + "_" + key;
		try {
			Cache c = CacheManager.getInstance().getCache(CKO_PRODUCTS_CACHE);
			Object o = c.retrieve(keyVal);

			if ((o != null) && (o instanceof ProductInfoType)) {
				logger.info("Retrieving_product_from_cache_for_key="+keyVal);
				return (ProductInfoType) o;
			}
		}
		catch (CacheException e) {
			logger.error("Exception_while_retrieving_product_from_cache_for_key=" + keyVal, e);
		}

		return DEFAULT_NULL_PRODUCT_TYPE;

	}
}

