package com.AL.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.ui.service.V.impl.CoxZipCodesDataCache;
import com.AL.ui.service.V.impl.FrontierPricingGridCache;
import com.AL.ui.service.V.impl.MDUCacheService;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.V.exception.UnRecoverableException;


@Controller
public class CacheClearController {
	
	private static final Logger logger = Logger.getLogger(CacheClearController.class);

	@RequestMapping(value="/clearCache", method = RequestMethod.GET)
	public ModelAndView clearDialogueCache(@RequestParam(value="key") String id) throws UnRecoverableException {
		boolean isCleared = false;
		ModelAndView mav = new ModelAndView();
		String message = "";
		if(id != null && !Utils.isBlank(id)){
			if(id.equalsIgnoreCase("all")){
				isCleared = ConcertDialogCacheService.INSTANCE.clearAllDialogueCache();
				if(isCleared){
					message = "Cleared all dialogues from cache";
					logger.info("Cleared_all_dialogues_from_cache");
				}
			}
			else{
				if(ConcertDialogCacheService.INSTANCE.get(id) != null){
					isCleared = ConcertDialogCacheService.INSTANCE.clearCacheOnKey(id);
					if(isCleared){
						message = "Cleared dialogue with key "+ id +" from cache";
						logger.info("Cleared_dialogue_with_key "+ id +" from_cache");
					}
				}
				else{
					message = "Entered key "+ id +" is invalid ";
				}
			}
		}
		else{
			message = "ID must not be null";
		}
		
		mav.addObject("message", message);
		mav.setViewName("clearCacheResponseView");
		return mav;
	}
	
	@RequestMapping(value="/clearMDUCache", method = RequestMethod.GET)
	public ModelAndView clearMDUCache(@RequestParam(value="key") String id) throws UnRecoverableException {
		boolean isCleared = false;
		ModelAndView mav = new ModelAndView();
		String message = "";
		if(id != null && !Utils.isBlank(id)){
			if(id.equalsIgnoreCase("all")){
				isCleared = MDUCacheService.INSTANCE.clearAllMDUCache();
				if(isCleared){
					message = "Cleared all MDU properties from cache";
					logger.info("Cleared_all_MDU_properties_from_cache");
					String mduUrl= ConfigRepo.getString("*.mdu_url");
					logger.info("mdu_url "+mduUrl);
					try {
						if(!Utils.isBlank(mduUrl)){
							SalesUtil.INSTANCE.getMduProperties(mduUrl);
						}
					} catch (Exception e) {
						logger.warn("Error in Loading mdu properties");
					}
				}
			}
			else{
				if(MDUCacheService.INSTANCE.get(id) != null){
					isCleared = MDUCacheService.INSTANCE.clearCacheOnKey(id);
					if(isCleared){
						message = "Cleared MDU properties with key "+ id +" from cache";
						logger.info("Cleared_MDU_properties_with_key "+ id +" from_cache");
					}
				}
				else{
					message = "Entered key "+ id +" is invalid ";
				}
			}
		}
		else{
			message = "ID must not be null";
		}
		
		mav.addObject("message", message);
		mav.setViewName("clearCacheResponseView");
		return mav;
	}
	
	@RequestMapping(value="/clearQualificationPopUpRefDetailsCache", method = RequestMethod.GET)
	public ModelAndView clearQualificationPopUpRefDetailsCache(@RequestParam(value="key") String id) throws UnRecoverableException {
		boolean isCleared = false;
		ModelAndView mav = new ModelAndView();
		String message = "";
		if(id != null && !Utils.isBlank(id)){
			if(id.equalsIgnoreCase("all")){
				isCleared = CoxZipCodesDataCache.INSTANCE.clearZipCodesCache();
				if(isCleared){
					message = "Cleared all Qualification popup Ref Details from cache";
					logger.info("Cleared_all_QualificationPopUpRefDetails_from_cache");
				}
			}
		}
		else{
			message = "ID must not be null";
		}
		
		mav.addObject("message", message);
		mav.setViewName("clearCacheResponseView");
		return mav;
	}
	
	@RequestMapping(value="/clearFrontierPricingGridCache", method = RequestMethod.GET)
	public ModelAndView clearFrontierPricingGridCache(@RequestParam(value="key") String id) throws UnRecoverableException {
		boolean isCleared = false;
		ModelAndView mav = new ModelAndView();
		String message = "";

		isCleared = FrontierPricingGridCache.INSTANCE.clearFrontierPricingGridCache();
		if(isCleared){
			message = "Cleared all FrontierPricingGrid from cache";
			logger.info("Cleared_all_FrontierPricingGrid_from_cache");
		}

		mav.addObject("message", message);
		mav.setViewName("clearCacheResponseView");
		return mav;
	}

}
