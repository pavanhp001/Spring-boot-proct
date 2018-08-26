package com.AL.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.dao.ProviderDao;
import com.AL.ui.domain.Provider;

@Controller
public class CartSystemConfigController extends BaseController {

	@Autowired
	ProviderDao providerDao;

	private static final Logger logger = Logger.getLogger(CartSystemConfigController.class);

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/info", method = RequestMethod.GET)
	public ModelAndView showSystemInfoForm(HttpServletRequest request) throws Exception 
	{

		ModelAndView mav = new ModelAndView();
		try
		{
			List<Provider> providers = providerDao.getProviders();
			mav.addObject("providers", providers);
		} 
		catch (Exception e)
		{
			logger.warn("error_while_getting_Provider_conten",e);
		}
		mav.setViewName("sysinfo");
		return mav;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			{
		// TODO Auto-generated method stub
		return null;
			}

}
