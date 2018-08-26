package com.AL.controller;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.AL.ui.dao.ConfigDao;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;

@Controller
public class MDUPropertiesController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MDUPropertiesController.class);

	@Autowired
	protected ConfigDao systemPropertiesDao;

	@PostConstruct
	public void init() throws Exception{
		systemPropertiesDao.sync();
		String mduUrl = ConfigRepo.getString("*.mdu_url");
		logger.info("mdu_url="+mduUrl);
		try {
			if(!Utils.isBlank(mduUrl)){
				SalesUtil.INSTANCE.getMduProperties(mduUrl);
			}
		} 
		catch (Exception e) {
			logger.error("Error_in_Loading_MDU_Properties",e);
		}
	}
}
