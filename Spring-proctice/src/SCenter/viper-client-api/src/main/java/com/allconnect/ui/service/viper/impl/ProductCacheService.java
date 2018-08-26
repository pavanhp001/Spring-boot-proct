package com.A.ui.service.V.impl;

import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductResponseType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum ProductCacheService {

	INSTANCE;
	
	private static final long TIME_IN_CACHE = 60 * 60 * 1000; // 60 MINUTES
	public static final String PRODUCT_PREFIX = "product-";
	private ProductInfoType DEFAULT_NULL_PRODUCT_TYPE = null;

	public void store(final ProductInfoType product, final String providerId, final String productId) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(PRODUCT_PREFIX + "-" + providerId + "-" +productId, product, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ProductInfoType clear(final String providerId, final String productId) {

		ProductInfoType result = null;

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(PRODUCT_PREFIX + "-"+ providerId + "-" + productId);

			result = (ProductInfoType) o;

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public ProductInfoType get( final String providerId, final String productId) {

		try {
			Cache c = CacheManager.getInstance().getCache();
			Object o = c.retrieve(PRODUCT_PREFIX + "-"+ providerId + "-" +productId);

			if ((o != null) && (o instanceof ProductInfoType)) {
				System.out.println("Found product in cache. Product extenalId: "+productId);
				return (ProductInfoType) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_PRODUCT_TYPE;

	}
}

