package com.AL.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.AL.service.BackButtonService;
import com.AL.ui.InstallationVO;
import com.AL.ui.ProductVO;
import com.AL.ui.QualificationVO;
import com.AL.ui.util.Utils;

@Controller
public class BackButtonController {
	
	private static final Logger logger = Logger.getLogger(BackButtonController.class); 
	
	@Autowired
	private BackButtonService backButtonService;
	
	/**
	 * Used to get ProductVO from service when back button is clicked from UI.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/backButtonProductVO", method = RequestMethod.POST)
	public ModelAndView backButtonProdFunction(HttpServletRequest request) throws Exception{
		ProductVO productVO = backButtonService.getProductVO(request);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("acdc_product_info");
		modelAndView.addObject("productVO",Utils.convertJSONString(productVO));
		modelAndView.addObject("iData",request.getParameter("iData"));
		logger.info("End_backButtonProdFunction");
		return modelAndView;
	}
	
	/**
	 * Used to get QualificationVO from service when back button is clicked from UI.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/backButtonQualificationVO", method = RequestMethod.POST)
	public ModelAndView backButtonQualFunction(HttpServletRequest request) throws Exception{
		QualificationVO qualificationVO = backButtonService.getQualificationVO(request);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("acdc_oq_demo");
		modelAndView.addObject("qualificationVO",Utils.convertJSONString(qualificationVO));
		modelAndView.addObject("iData",request.getParameter("iData"));
		logger.info("End_backButtonQualFunction");
		return modelAndView;
	}
	
	/**
	 * Used to get InstallationVO from service when back button is clicked from UI.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/backButtonInstallationVO", method = RequestMethod.POST)
	public ModelAndView backButtonInstalFunction(HttpServletRequest request) throws Exception{
		InstallationVO installationVO = backButtonService.getInstallationVO(request);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("acdc_installation_info");
		modelAndView.addObject("installationVO",Utils.convertJSONString(installationVO));
		modelAndView.addObject("iData",request.getParameter("iData"));
		logger.info("End_backButtonInstalFunction");
		return modelAndView;
	}

}
