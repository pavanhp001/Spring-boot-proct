package com.A.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class VersionController extends AbstractController {

	private static final Logger logger = Logger
			.getLogger(VersionController.class);

	@RequestMapping(value = "/version")
	public ModelAndView version(HttpServletRequest request) {
		
		logger.info("logging_version");

		ModelAndView mv = new ModelAndView("version");
		mv.setViewName("version");
		return mv;
	}

	 

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

}
