package com.A.ui.service.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.A.ui.dao.ConfigDao;
 
public class ConfigSyncServiceDefault implements ConfigSyncService   {

	private static final Logger logger = Logger.getLogger(ConfigSyncServiceDefault.class);

	@Autowired
	protected ConfigDao systemPropertiesDao;

	@Scheduled(fixedDelay = 86400000) //86400 seconds = 24 Hours
	public void syncProperties() {
		logger.info("synchronize");
		systemPropertiesDao.sync();
	}
	 
}