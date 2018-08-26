package com.AL.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.AL.ui.repository.SessionCache;
import com.AL.ui.service.V.UserAccessService;

import com.AL.V.domain.User;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.service.auth.AuthenticationService;
import com.AL.vo.AuthorizationMap;
import com.AL.vo.UserAuthorization;

/**
 * @author ctadikonda
 *
 */

@Controller
public class AuthenticationController
{
	private static final Logger logger = Logger.getLogger(AuthenticationController.class);

	/*	
	 * Login Functionality		 
	 */
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		SessionCache.INSTANCE.clear(request.getSession());
		return mav;
	}
	
	/*	
	 * Logout Functionality		 
	 */	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		SessionCache.INSTANCE.clear(request.getSession());
		return mav;
	}
	
	@RequestMapping(value = "/checkSession")
	public @ResponseBody boolean checkSession(HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("in checkSession method");
		return true;
	}
	
	@RequestMapping(value = "/session_timeout")
	public ModelAndView sessionTimeOut(HttpServletRequest request) throws Exception{
		logger.info("in sessionTimeOut method............");
		
		request.setAttribute("isSessionTimeOut", true);
		request.setAttribute("isSessionTimeOutText", "Session Session Time Out");
		String absoluteURL = (String) request.getSession().getAttribute(
				"urlPath");
		ModelAndView mav = new ModelAndView("redirect:" + absoluteURL
				+ "/srt/login");
		mav.setViewName("login");
		logger.info("redirecting to login page............");
		return mav;
	}

	@RequestMapping(value = "/login_process", method = RequestMethod.GET)
	public ModelAndView login_process(HttpServletRequest request) throws Exception{
		
		logger.info("login_process :: begin");
		if(request.getSession().getAttribute("mavFromSession") == null){
			String absoluteURL = (String) request.getSession().getAttribute("urlPath");
			ModelAndView mav = new ModelAndView("redirect:" + absoluteURL+ "/srt/login");
			mav.setViewName("login");
			return mav;
		}
		ModelAndView mav = (ModelAndView)request.getSession().getAttribute("mavFromSession");
		logger.info("login_process :: end");
		return mav;
	}
	
	@RequestMapping(value = "/login_process/orderId/{orderId}", method = RequestMethod.GET)
	public ModelAndView loginProcessForEncore(@PathVariable String orderId, HttpServletRequest request) throws Exception{
		
		logger.info("From Encore login_process :: begin");
		if(request.getSession().getAttribute("mavFromSession") == null){
			String absoluteURL = (String) request.getSession().getAttribute("urlPath");
			ModelAndView mav = new ModelAndView("redirect:" + absoluteURL+ "/srt/login");
			mav.setViewName("login");
			request.getSession().setAttribute("faOrderId", orderId);
			return mav;
		}
		ModelAndView mav = (ModelAndView)request.getSession().getAttribute("mavFromSession");
		mav.addObject("faOrderId", orderId);
		logger.info("From Encore login_process :: end");
		return mav;
	}

	/**
	 * Authenticating the user	   
	 * 
	 * @param request
	 * @param userid
	 * @param password
	 * @return mav
	 */

	@RequestMapping(value = "/login_process", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpServletRequest request,
			@RequestParam("chatForm:j_username") String userid,
			@RequestParam("chatForm:j_password") String password) throws UnRecoverableException{
		try{
		ModelAndView mav = new ModelAndView();
		logger.info("login_process");
		if ((userid != null) && (userid.length() > 0) && (password != null)
				&& (password.length() > 0)) {

			logger.info("userid::::::::"+userid);

			// Verify Valid User with Access to srt Application
			User user = AuthenticationService.INSTANCE.getUser(userid);

			// V Authentication and Authorization
			if (user != null) {	

				logger.info("user.getname is::::::::::::"+user.getName());
				logger.info("when user not equals null");

				UserAuthorization userAuthorization = UserAccessService.INSTANCE.authenticate(userid, password);
				logger.info("userAuthorization.getUserName()::::::::::::"+userAuthorization.getUserName());
				userAuthorization.setUser(user);
				logger.info("userAuthorization.getUser: after setting user::::::"+userAuthorization.getUser());
				
				if (userAuthorization.authenticated()) {
					//checking for permission to login to CONCERT
					AuthorizationMap<String, Map<String, List<String>>> authMap = userAuthorization.getPermissions();
					Set<String> resourceSet = authMap.getContext("SCORE").keySet();
					if(!resourceSet.contains("LOGIN")){
						mav.setViewName("login");
						mav.addObject("loginFailed", true);
						mav.addObject("permissionDenied", true);
						return mav;
					}
					logger.info("userAuthorization.authenticated()::::::"+userAuthorization.authenticated());
					SessionCache.INSTANCE.set(request.getSession(),	userAuthorization);

					if (SessionCache.INSTANCE.isAuthenticated(request.getSession())) {
						logger.info("isAuthenticated");
						logger.info("Authenticated::::::::::::::;;");
						
						request.getSession().setAttribute("username", userid);
						request.getSession().setAttribute("password", password);

						mav.addObject("userFromDB", user);
						mav.setViewName("sales.ordercapture");
						
						//Putting Auth in Session after Successful login
						request.getSession().setAttribute("mavFromSession", mav);
						
						String faOrderId = (String) request.getSession().getAttribute("faOrderId");
						logger.debug("faOrderId in POST: "+faOrderId);
						if(faOrderId != null)
							mav.addObject("faOrderId", faOrderId);
					}
				}else{
					mav.setViewName("login");
					mav.addObject("loginFailed", true);
					String failure_text = userAuthorization.getFailureText();
					if(failure_text.equals("WRONG_PWD")){
						mav.addObject("wrongPwd", true);
						mav.addObject("userName",userid);
						mav.addObject("pswd", "");
					} else {
						mav.addObject("wrongUserName", true);
					}
				}
			}
		}
		logger.info("login_process :: end");
		return mav;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
	}
}