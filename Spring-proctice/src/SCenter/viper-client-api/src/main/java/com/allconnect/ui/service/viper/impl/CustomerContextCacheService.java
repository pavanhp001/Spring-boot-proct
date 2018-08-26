package com.A.ui.service.V.impl;

import com.A.xml.cm.v4.CustomerContextType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum CustomerContextCacheService {

	INSTANCE;
	
	public static final String CUSTOMER_CONTEXT_PREFIX = "customer-context-";
	private CustomerContextType DEFAULT_NULL_CUSTOMER_CONTEXT_TYPE = null;
	private static final long TIME_IN_CACHE = 180000;
	
	
	public void store(final CustomerContextType customerContext, final String id) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(CUSTOMER_CONTEXT_PREFIX + id, customerContext, TIME_IN_CACHE);
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	public void clear(final String id) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(CUSTOMER_CONTEXT_PREFIX + id);

		} catch (CacheException e) {
			e.printStackTrace();
		}

	}

	public CustomerContextType get(final String id) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(CUSTOMER_CONTEXT_PREFIX + id);

			if ((o != null) && (o instanceof CustomerContextType)) {
				return (CustomerContextType) o;
			}
		} catch (CacheException e) {
			e.printStackTrace();
		}

		return DEFAULT_NULL_CUSTOMER_CONTEXT_TYPE;

	}
}
