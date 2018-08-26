package com.AL.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.constants.ErrorConstants;
import com.AL.ui.service.V.impl.OrderCacheService;
import com.AL.ui.service.workflow.ActionStatus;
import com.AL.ui.service.workflow.ViewFlow;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ViewUtil {
	private static final Logger logger = Logger.getLogger(ViewUtil.class);
	private static final JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();

	public static void processViewByStatus(ModelAndView currentView, 
			CKOInitialVo CKOVo, String statusMsg, String status) {
		logger.info("INSIDE Process view by status");
		CKOVo.setStatus(status);
		if( "UtilityOffer".equalsIgnoreCase( CKOVo.getAction() ) 
			&& CKOVo.getFeedbackMap()!=null && !CKOVo.getFeedbackMap().isEmpty() )
		{
			CKOVo.getFeedbackMap().clear();
		}
		logger.info("after setting status to CKO vo object");
		String stateAsJSONString = util.convert(CKOVo, "CKO", CKOInitialVo.class);
		logger.info("After converting CKO vo object to string=" + stateAsJSONString);
		currentView.addObject("iData",stateAsJSONString);
		logger.info("adding view name to IDATA");
	}

	public static void processViewByStatus(ModelAndView currentView, CKOInitialVo CKOVo,
			String statusMsg, String status, List<Errors> errList) {

		Map<String, String>feedbackMap = new HashMap<String, String>();
		List<String> errorStringList = new ArrayList<String>();
		for(Errors err : errList)
		{
			List<ObjectError> errObjectList = err.getAllErrors();
			for(ObjectError errObj : errObjectList)
			{
				String errCode = errObj.getCode();
				String dfltMsg = errObj.getDefaultMessage();
				if(errCode != null && dfltMsg != null)
				{
					errorStringList.add(dfltMsg);
					feedbackMap.put(errCode, dfltMsg);
				}
			}
		}

		//Create map to store application success/errors/events message		
		//feedbackMap.put("APP_MSG1", statusMsg);
		//feedbackMap.put("ERR_CODE1", "System is down");
		CKOVo.setFeedbackMap(feedbackMap);
		CKOVo.setStatus(status);

		String stateAsJSONString = util.convert(CKOVo, "CKOFeedback", CKOInitialVo.class);
		logger.info("CKOFeedbackJson:"+stateAsJSONString);

		currentView.addObject("errors",errorStringList);
		currentView.addObject("iData",stateAsJSONString);
	}

	public static ModelAndView processErrorViewByStatus(ModelAndView errorView, 
			CKOInitialVo CKOVo, String status, OrderQualVO orderQualVo, ErrorConstants... errors) {

		Map<String, String>feedbackMap = new HashMap<String, String>();		
		for(ErrorConstants error : errors) {
			feedbackMap.put(error.getErrorCode(), error.getErrorMessage());
		}
		CKOVo.setFeedbackMap(feedbackMap);
		CKOVo.setStatus(status);
		String stateAsJSONString = util.convert(CKOVo, "CKO", CKOInitialVo.class);
		logger.info("CKOFeedbackJson:"+stateAsJSONString);
		errorView.addObject("orderQualVo", orderQualVo);
		errorView.addObject("orderID", orderQualVo.getOrderId());
		errorView.addObject("lineItemID", orderQualVo.getLineItemExternalId());
		logger.info("orderQualVo.getProviderExternalId() ::: "+orderQualVo.getProviderExternalId());
		errorView.addObject("providerExternalID", orderQualVo.getProviderExternalId());
		errorView.addObject("iData",stateAsJSONString);
		errorView.addObject("errorMap", feedbackMap);
		OrderCacheService.INSTANCE.clear(orderQualVo.getOrderId());
		return errorView;
	}

	public static ViewFlow resolveErrorView(CKOInitialVo CKOVo, String status, OrderQualVO orderQualVo, ErrorConstants... errors) {
		ModelAndView errorView = new ModelAndView("dialogueUpdate");
		errorView = processErrorViewByStatus(errorView, CKOVo, status, orderQualVo, errors);
		ViewFlow vf = ViewFlow.create(errorView, ActionStatus.fail);
		OrderCacheService.INSTANCE.clear(orderQualVo.getOrderId());
		return vf;
	}

	public static void processViewByStatus(HttpServletRequest request, CKOInitialVo CKOVo,
			String statusMsg, String status, List<String> errList) {

		Map<String, String>feedbackMap = new HashMap<String, String>();
		List<String> errorStringList = new ArrayList<String>();
		for(String err : errList)
		{
			feedbackMap.put(err, err);
		}

		//Create map to store application success/errors/events message		

		CKOVo.setFeedbackMap(feedbackMap);
		CKOVo.setStatus(status);

		String stateAsJSONString = util.convert(CKOVo, "CKOFeedback", CKOInitialVo.class);
		logger.info("CKOFeedbackJson:"+stateAsJSONString);
		
		request.setAttribute("errors",errorStringList);
		request.setAttribute("iData",stateAsJSONString);
	}

	public static ModelAndView returnMAVObject(CKOInitialVo CKOVo, String status, 
			ModelAndView currentView, String errors) {
		CKOVo.setStatus(status);	
		String stateAsJSONString = util.convert(CKOVo, "CKO", CKOInitialVo.class);
		currentView.addObject("iData",stateAsJSONString);
		return currentView;
	}

	public static String returnIData(CKOInitialVo CKOVo, String status, 
			ErrorConstants... errors) {

		Map<String, String>feedbackMap = new HashMap<String, String>();		
		for(ErrorConstants error : errors) {
			feedbackMap.put(error.getErrorCode(), error.getErrorMessage());
		}
		CKOVo.setFeedbackMap(feedbackMap);
		CKOVo.setStatus(status);
		String stateAsJSONString = util.convert(CKOVo, "CKO", CKOInitialVo.class);
		logger.info("CKOFeedbackJson:"+stateAsJSONString);

		return stateAsJSONString;
	}
}
