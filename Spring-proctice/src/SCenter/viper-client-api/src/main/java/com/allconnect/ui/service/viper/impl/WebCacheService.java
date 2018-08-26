package com.A.ui.service.V.impl;

import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductResponseType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum WebCacheService {

	INSTANCE;

	private static final long TIME_IN_CACHE = 120 * 60 * 1000; // 120 MINUTES
	public static final String WEB_PREFIX = "web-";
	private ProductInfoType DEFAULT_NULL_WEB_TYPE = null;

	public void store(final ProductInfoType product, final String providerId,
			final String productId) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(WEB_PREFIX + "-" + providerId + "-" + productId, product,
					TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ProductInfoType clear(final String providerId, final String productId) {

		ProductInfoType result = null;

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c
					.remove(WEB_PREFIX + "-" + providerId + "-" + productId);

			result = (ProductInfoType) o;

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public ProductInfoType get(final String providerId, final String productId) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(WEB_PREFIX + "-" + providerId + "-"
					+ productId);

			if ((o != null) && (o instanceof ProductResponseType)) {
				return (ProductInfoType) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_WEB_TYPE;

	}
}
