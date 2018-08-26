package com.A.ui.service.V.impl;

import java.util.List;
import com.A.ui.domain.WebLookupCollection;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum CacheService {

	INSTANCE;

	private static final long SEARCH_TIME_IN_CACHE = 10000;
	private static final long GUID_TIME_IN_CACHE = 120000;

	public static final String ADDRESS_PREFIX = "address-";
	public static final String SEARCH_PREFIX = "search-";

	public void storeLookup(final WebLookupCollection search, final String id) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store("Lookup-" + id, search, SEARCH_TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WebLookupCollection getLookup(final String context) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve("Lookup-" + context);

			if ((o != null) && (o instanceof List)) {
				return (WebLookupCollection) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void storeSearch(final List search, final String id,
			final String prefix) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(SEARCH_PREFIX + id, search, SEARCH_TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to store GUID to cache
	 * 
	 * @param id
	 * @param guid
	 */
	public void storeGUID(final String id, final String guid) {
		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(id, guid, GUID_TIME_IN_CACHE);
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to retrieve GUID from cache
	 * 
	 * @param id
	 * @return
	 */
	public String getGUID(final String id) {
		try {
			Cache c = CacheManager.getInstance().getCache();
			Object o = c.retrieve(id);
			if ((o != null) && (o instanceof String)) {
				return (String) o;
			}
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void clear() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			c.clear();

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
