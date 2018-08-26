package com.A.ui.service.V.impl;

import org.apache.log4j.Logger;

import com.A.xml.dtl.v4.DetailsRequestResponse;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum DetailsCacheService {

	INSTANCE;
	
//	private static final long TIME_IN_CACHE = 60 * 60 * 1000; // 60 MINUTES
	public static final String DETAILS_PREFIX = "details-";
	private DetailsRequestResponse DEFAULT_NULL_DETAIL_RESPONSE = null;
	private static final Logger logger = Logger.getLogger(DetailsCacheService.class);

	public void store(final DetailsRequestResponse details, final String correlationId, Long TIME_IN_CACHE) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(DETAILS_PREFIX + "-" + correlationId, details, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DetailsRequestResponse clear(final String correlationId) {

		DetailsRequestResponse result = null;

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(DETAILS_PREFIX + "-"+ correlationId);

			result = (DetailsRequestResponse) o;

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public DetailsRequestResponse get( final String correlationId) {

		try {
			Cache c = CacheManager.getInstance().getCache();
			Object o = c.retrieve(DETAILS_PREFIX + "-"+ correlationId);

			if ((o != null) && (o instanceof DetailsRequestResponse)) {
				logger.info("Found Details in cache. Detail correlationId: "+correlationId);
				return (DetailsRequestResponse) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_DETAIL_RESPONSE;

	}
}
