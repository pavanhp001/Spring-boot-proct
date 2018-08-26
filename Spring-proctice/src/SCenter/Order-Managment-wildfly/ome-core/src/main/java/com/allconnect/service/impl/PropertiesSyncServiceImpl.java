package com.AL.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.AL.service.PropertiesSyncService;
import com.AL.Vdao.dao.SystemPropertiesDao;
@Component
public class PropertiesSyncServiceImpl implements PropertiesSyncService {

	private static final Logger logger = Logger.getLogger(PropertiesSyncServiceImpl.class);

	@Autowired
	private SystemPropertiesDao systemPropertiesDao;

	@Scheduled(fixedDelay=3600000)
	public void syncProperties() {
		logger.debug("Synced properties from db.");
		systemPropertiesDao.sync();
	}
}
