package com.A.ui.service.V.impl;

import com.A.rules.groovy.directrules.DirectRuleImpl;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum GroovyCacheService {
	INSTANCE;

	private static final long TIME_IN_CACHE = 24 * 60 * 60 * 1000; // 1 day
	public static final String GROOVY_PREFIX = "groovy-";
	private DirectRuleImpl DEFAULT_NULL_GROOVY_TYPE = null;

	public void store(final DirectRuleImpl rule, final String context, final String providerId,
			final String productId, final Boolean accord, final String version) {

		//Use the cache manager to create the default cache
		try {
			
			String key = GROOVY_PREFIX +"-" +context+ "-" +  providerId + "-"
					+ productId + "-" + "-"+ version + accord;
			
			Cache c = CacheManager.getInstance().getCache();
			c.store( key, rule, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DirectRuleImpl clear(final String context, final String providerId,
			final String productId, final Boolean accord, final String version) {

		DirectRuleImpl result = null;
		
		String key = GROOVY_PREFIX +"-" +context+ "-" +  providerId + "-"
				+ productId + "-" + "-"+ version + accord;

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(key);

			result = (DirectRuleImpl) o;

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public DirectRuleImpl get(final String context, final String providerId, final String productId,
			final Boolean accord, final String version) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			String key = GROOVY_PREFIX +"-" +context+ "-" +  providerId + "-"
					+ productId + "-" + "-"+ version + accord;
			Object o = c.retrieve(key);

			if ((o != null) && (o instanceof DirectRuleImpl)) {
				return (DirectRuleImpl) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_GROOVY_TYPE;

	}

	public void clearAll() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			c.clear();

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
