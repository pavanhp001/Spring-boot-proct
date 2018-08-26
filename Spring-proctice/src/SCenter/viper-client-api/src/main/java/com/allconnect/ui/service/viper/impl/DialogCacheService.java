package com.A.ui.service.V.impl;

import com.A.xml.di.v4.DialogueResponseType;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum DialogCacheService {

	INSTANCE;

	private long TIME_IN_CACHE = 120 * 60 * 1000; // 120 MINUTES
	public static final String DIALOG_PREFIX = "dialog-";
	private DialogueResponseType DEFAULT_NULL_DIALOG_TYPE = null;

	
	public void setTimeInCache(long timeInCache) {
		this.TIME_IN_CACHE = timeInCache;
	}
	
	public void store(final DialogueResponseType order, final String id) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(DIALOG_PREFIX + id, order, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DialogueResponseType clearCached(final String id) {

		DialogueResponseType result = null;

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(DIALOG_PREFIX + id);

			result = (DialogueResponseType) o;

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public DialogueResponseType getFromCache(final String id) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(DIALOG_PREFIX + id);

			if ((o != null) && (o instanceof DialogueResponseType)) {
				return (DialogueResponseType) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DEFAULT_NULL_DIALOG_TYPE;

	}
}
