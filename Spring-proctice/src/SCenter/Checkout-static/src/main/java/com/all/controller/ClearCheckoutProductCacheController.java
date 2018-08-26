package com.AL.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.service.V.impl.CKOProductCacheService;
import com.AL.ui.util.Utils;
import com.AL.V.exception.UnRecoverableException;

@Controller
public class ClearCKOProductCacheController {

	@RequestMapping(value="/clearCKOProductCache", method = RequestMethod.GET)
	public ModelAndView clearCKOProductCache(@RequestParam(value="key") String id) throws UnRecoverableException {
		boolean isCleared = false;
		ModelAndView mav = new ModelAndView();
		String message = "";
		if(id != null && !Utils.isBlank(id)){
			if(id.equalsIgnoreCase("all")){
				isCleared = CKOProductCacheService.INSTANCE.clearAllCKOProductCache();
				if(isCleared){
					message = "Cleared all CKO products from cache";
				}
			}
			else{
				if(CKOProductCacheService.INSTANCE.get(id) != null){
					isCleared = CKOProductCacheService.INSTANCE.clearCKOProductCacheOnKey(id);
					if(isCleared){
						message = "Cleared CKO product with key "+ id +" from cache";
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
}
