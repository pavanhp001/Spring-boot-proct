package com.A.ui.service.V.impl;

import com.A.xml.cm.v4.CustomerType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum CustomerCacheService {

	INSTANCE;
	
	public static final String CUSTOMER_PREFIX = "customer-";
	private CustomerType DEFAULT_NULL_CUSTOMER_TYPE = null;
	private static final long TIME_IN_CACHE = 180000;
	
	
	public void store(final CustomerType customer, final String id) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(CUSTOMER_PREFIX + id, customer, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clear(final String id) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(CUSTOMER_PREFIX + id);

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public CustomerType get(final String id) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(CUSTOMER_PREFIX + id);

			if ((o != null) && (o instanceof CustomerType)) {
				return (CustomerType) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_CUSTOMER_TYPE;

	}
}
