package com.AL.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class SYPMockController extends AbstractController {

	private static final Logger logger = Logger
			.getLogger(SYPMockController.class);

	@RequestMapping(value = "/SYP")
	public ModelAndView saveProductInfo(HttpServletRequest request)
			throws Exception {
		logger.debug("executing " + SYPMockController.class.getName());

		ModelAndView mav = new ModelAndView();
		mav.setViewName("SYP");
		return mav;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

}