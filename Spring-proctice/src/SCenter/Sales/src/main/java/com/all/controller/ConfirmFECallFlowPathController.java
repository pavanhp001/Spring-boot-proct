package com.AL.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.RequestContext;

import com.AL.ui.util.Utils;
import com.AL.V.exception.UnRecoverableException;

@Component
public class ConfirmFECallFlowPathController extends BaseController {

	private static final Logger logger = Logger.getLogger(ConfirmFECallFlowPathController.class);
	
	public String confirmFEFlowPath(HttpServletRequest request,
			HttpServletResponse response, RequestContext context)
			throws UnRecoverableException {
		
		if(!Utils.isBlank(request.getParameter("callStartTimeInGreeting"))){
			request.getSession().setAttribute("callStartTime", request.getParameter("callStartTimeInGreeting"));
		}
		
		String customerId = request.getParameter("customerId");
		request.getSession().setAttribute("selectedCustomerId", customerId);
		String webflowPath = (String)request.getSession().getAttribute("webflowPath");
		
		if (!Utils.isBlank(customerId)){
			webflowPath = webflowPath+"/data";
		}else{
			webflowPath = webflowPath+"/nodata";
		}
		
		request.getSession().removeAttribute("webflowPath");
		
		logger.info("webflowPath ::::: "+webflowPath);
		
		return webflowPath;
	}

}
