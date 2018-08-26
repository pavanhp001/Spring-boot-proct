package com.A.ui.service.V.impl;

import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum StaticCKOCacheManager {
	INSTANCE;

	private long TIME_IN_CACHE = 0; 
	private static final String STATIC_CKO_CACHE = "digital_static_CKO_cache";

	public void setTimeInCache(long timeInCache) {
		this.TIME_IN_CACHE = timeInCache;
	}

	/**
	 * @param object
	 * @param key
	 */
	public void store(final Object object, final String key) {

		// Use the cache manager to create the default cache
		try {
			Cache cache = CacheManager.getInstance().getCache(STATIC_CKO_CACHE);
			cache.store(key, object, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public Object clearCache(final String key) {
		Object object = null;
		try {
			Cache cache = CacheManager.getInstance().getCache(STATIC_CKO_CACHE);
			object = cache.remove(key);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;

	}

	/**
	 * @param key
	 * @return
	 */
	public Object getObjectFromCache(final String key) {
		Object object = null;
		try {
			Cache cache = CacheManager.getInstance().getCache(STATIC_CKO_CACHE);
			object = cache.retrieve(key);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
}
