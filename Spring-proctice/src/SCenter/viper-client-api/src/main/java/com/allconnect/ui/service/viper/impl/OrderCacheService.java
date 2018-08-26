package com.A.ui.service.V.impl;

import org.apache.log4j.Logger;

import com.A.xml.v4.OrderType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum OrderCacheService {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(OrderCacheService.class);

	private static final long TIME_IN_CACHE = 120000;
	private OrderType DEFAULT_NULL_ORDER_TYPE = null;
	public static final String ORDER_PREFIX = "order-";

	public void store(final OrderType order, final String id) {
		logger.info("Storing order in cache for OrderId=" + id);
		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(ORDER_PREFIX + id, order, TIME_IN_CACHE);
			logger.info("Order Cache Size : " + c.size());
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clear(final String id) {
		logger.info("Removing order from cache for OrderId=" + id);
		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(ORDER_PREFIX + id);
			logger.info("Removed order from cache.");
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public OrderType get(final String id) {
		logger.info("Retrieving order from cache for OrderId=" + id);
		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(ORDER_PREFIX + id);

			if ((o != null) && (o instanceof OrderType)) {
				logger.info("Found order in cache.");
				return (OrderType) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_ORDER_TYPE;

	}
}
