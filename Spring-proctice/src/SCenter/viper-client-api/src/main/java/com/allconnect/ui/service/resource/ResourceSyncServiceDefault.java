package com.A.ui.service.resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.A.ui.dao.UserResourceEntityDao;

public class ResourceSyncServiceDefault implements ResourceSyncService {

	private static final Logger logger = Logger.getLogger(ResourceSyncServiceDefault.class);
	
	@Autowired
	protected UserResourceEntityDao userResource;
	
	@Scheduled(fixedDelay = 86400000) //86400 seconds = 24 Hours
	public void syncResources() {
		logger.debug("synchronizing resorces");
		userResource.sync();
	}

}
