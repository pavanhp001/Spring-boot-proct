package com.A.ui.service.V.impl;

import java.util.List;

import com.A.xml.cm.v4.CustomerInteractionType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum CustomerInteractionCacheService {

	INSTANCE;
	
	public static final String CUSTOMER_INTERACTION_PREFIX = "customer-int-";
	private List<CustomerInteractionType> DEFAULT_NULL_CUSTOMER_INT = null;
	private static final long TIME_IN_CACHE = 180000;
	
	public void storeCustomerInteraction(
			final List<CustomerInteractionType> customerInt, final String id,
			final String prefix) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(CUSTOMER_INTERACTION_PREFIX + id, customerInt,
					TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearCachedCustomerInteraction(final String id) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(CUSTOMER_INTERACTION_PREFIX + id);

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<CustomerInteractionType> getCustomerInteractionFromCache(
			final String id) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(CUSTOMER_INTERACTION_PREFIX + id);

			if ((o != null) && (o instanceof List)) {
				return (List<CustomerInteractionType>) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_CUSTOMER_INT;

	}
}
