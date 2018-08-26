package com.A.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.A.ui.constants.ControllerConstants;
import com.A.V.exception.RecoverableException;
import com.A.V.exception.UnRecoverableException;


/**
 * @author Preetam
 *
 */
@Controller
public class BaseController  extends AbstractController implements ControllerConstants{

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(BaseController.class);

	/**
	 * Handles the Recoverable Exception in the Concert application
	 * 
	 * 
	 * @param RecoverableException
	 * @return ModelAndView
	 * 
	 */
	@ExceptionHandler(RecoverableException.class)
	public ModelAndView handleRecoverableException(RecoverableException re, HttpServletRequest request){

		//logger.error("RecoverableException: "+ExceptionUtils.getFullStackTrace(re));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("concert.recoverable");
		modelAndView.addObject("message", re.getMessage());
		modelAndView.addObject("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
		return modelAndView;
	}

	/**
	 * Handles the UnRecoverable Exception in the Concert application
	 * 
	 * 
	 * @param UnRecoverableException
	 * @return ModelAndView
	 */
	@ExceptionHandler(UnRecoverableException.class)
	public ModelAndView handleUnRecoverableException(UnRecoverableException ure, HttpServletRequest request){

		//logger.error("UnRecoverableException: "+ExceptionUtils.getFullStackTrace(ure));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("concert.Unrecoverable");
		modelAndView.addObject("message", ure.getMessage());
		modelAndView.addObject("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
		return modelAndView;
	}

	/**
	 * @param ure
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e,HttpServletRequest request){

		//logger.error("Exception: "+ExceptionUtils.getFullStackTrace(e));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("concert.error");
		modelAndView.addObject("message", e.getMessage());
		modelAndView.addObject("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
		return modelAndView;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}


}
