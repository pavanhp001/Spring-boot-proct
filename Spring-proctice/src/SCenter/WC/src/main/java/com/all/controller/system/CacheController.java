package com.A.controller.system;

 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.A.ui.service.V.impl.CacheService;

@Controller
public class CacheController extends AbstractController {

	@RequestMapping(value = "/cache/{operation}")
	public ModelAndView paymentMethodCCEditCustomer(
			@PathVariable String operation,HttpServletRequest request) {
		
		//TODO: 
		
		if ("clear".equals(operation)) {
			CacheService.INSTANCE.clear();
		}

		return null;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("message", "Hello World From Phuong!");
		return mav;
	}

}
