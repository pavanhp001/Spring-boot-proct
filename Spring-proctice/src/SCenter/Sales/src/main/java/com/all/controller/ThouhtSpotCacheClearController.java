package com.AL.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.ui.service.V.impl.MDUCacheService;
import com.AL.ui.service.V.impl.ThoughtspotCacheService;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.V.exception.UnRecoverableException;


@Controller
public class ThouhtSpotCacheClearController {
	
	private static final Logger logger = Logger.getLogger(ThouhtSpotCacheClearController.class);

	@RequestMapping(value="/clearThoughtSpotCache", method = RequestMethod.GET)
	public ModelAndView clearThoughtSpotCache() throws UnRecoverableException {
		boolean isCleared = false;
		ModelAndView mav = new ModelAndView();
		String message = "";
				isCleared = ThoughtspotCacheService.INSTANCE.clearTsCache();
				if(isCleared){
					message = "Cleared ThoughtSpot Data from cache";
					logger.info("Cleared_ThoughtSpotData_from_cache");
				}
		
		mav.addObject("message", message);
		mav.setViewName("clearCacheResponseView");
		return mav;
	}
}
