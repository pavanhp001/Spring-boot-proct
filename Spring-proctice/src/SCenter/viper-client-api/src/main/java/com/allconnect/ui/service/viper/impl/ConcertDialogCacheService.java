package com.A.ui.service.V.impl;

import org.apache.log4j.Logger;

import com.A.ui.service.config.ConfigRepo;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public enum ConcertDialogCacheService {

	INSTANCE;

	private static final String DIALOGUES_CACHE = "dialogues_cache";

	// Cache expiry set to 24 hrs
	private static final long CACHE_TIMEOUT = 0;
	private static final String FALSE = "false";

	private static final Logger logger = Logger.getLogger(ConcertDialogCacheService.class);

	public boolean clearAllDialogueCache(){
		logger.info("Clearing_all_dialogue_cache_started");
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(DIALOGUES_CACHE);
			logger.info("Dialgoue_cache_size="+ c.size());
			c.clear();
			logger.info("Clearing_all_dialogue_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_dialogue_cache", e);
		}
		return Boolean.FALSE;
	}


	public void store(String key, Object dialogue){		
		try {
			// we are storing dialogues in cache when "isCacheEnabled" flag value is "true" or null.
			// Otherwise("false") not storing value in cache.
			String isCacheEnabled = ConfigRepo.getString("*.cache_enable") != null ? ConfigRepo.getString("*.cache_enable") : "true";
			logger.info("sys_config cache_enabled=" + (ConfigRepo.getString("*.cache_enable") != null ? isCacheEnabled : "null"));
			if( !FALSE.equalsIgnoreCase(isCacheEnabled) )
			{
				Cache c = CacheManager.getInstance().getCache(DIALOGUES_CACHE);
				c.store(key, dialogue, CACHE_TIMEOUT);
				logger.info("Storing_dialoue_object_in_cache_for_key=" + key);
				logger.info("Dialogue_cache_size="+ c.size());
			} else {
				logger.info("No Dialogue caching for key=" + key);
			}
		} catch (CacheException e) {
			logger.error("Exception_while_storing_dialogue_in_cache_for_key=" + key, e);
		}
	}


	public Object get(String key){
		logger.info("Retrieveing_dialogue_from_cache_for_key=" + key);
		Object o = null;
		try {
			Cache c = CacheManager.getInstance().getCache(DIALOGUES_CACHE);
			o = c.retrieve(key);
			logger.info("Dialgoue_cache_size="+ c.size());
		} catch (CacheException e) {
			logger.error("Exception_while_retrieving_dialogue_from_cache_for_key=" + key, e);
		}
		return o;
	}
	
	public boolean clearCacheOnKey(final String key) {
		logger.info("Clearing_dialogue_cache_started_for_key=" +key);
		Cache c;
		try {
			c = CacheManager.getInstance().getCache(DIALOGUES_CACHE);
			c.remove(key);
			logger.info("Dialgoue_cache_size="+ c.size());
			logger.info("Clearing_all_dialogue_cache_completed_successfully!!!");
			return Boolean.TRUE;
		} catch (CacheException e) {
			logger.error("Exception_while_clearing_dialogue_in_cache_with_key=" + key, e);
		}
		return Boolean.FALSE;
	}
}
