package com.AL.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.common.util.PageConstantsEnum;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.util.Utils;
import com.AL.ui.util.ViewUtil;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.SalesContextType;

/**
 * @author Nanda Kishore Palapala
 *
 */
@Controller
public class SubmitLineItemController 
{
	private static final Logger logger = Logger.getLogger(SubmitLineItemController.class);


	@RequestMapping(value = "/submitLineItem")
	public ModelAndView submitLineItemOnOrder(HttpServletRequest request) throws Exception 
	{
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		Map<String,Map<String,String>> providerSessionData = (Map<String,Map<String,String>>)request.getSession().getAttribute("providerSessionData");
		if(!Utils.isValidSession(sessionVO, SessionKeys.initiator.name())){
			ModelAndView sessionTimedoutView = new ModelAndView("session_timed_out");
			sessionTimedoutView.addObject("step", "3");
			return sessionTimedoutView;
		}
		StopWatch timer=new StopWatch();
		long startTimer = 0;
		timer.start();
		logger.info("submitLineItemOnOrder_begin");
		String lineItemExternalId = request.getParameter("lineItemExternalId");
		String authorizeOrder = request.getParameter("authorizeOrder");
		logger.info("authorizeOrder="+authorizeOrder);
		List<String> lineitemIdList = new ArrayList<String>();
		lineitemIdList.add(lineItemExternalId);
		
		CKOInitialVo CKOVo = (CKOInitialVo)sessionVO.get(SessionKeys.initiator.name());	
		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());
		
		SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
		if( "yes".equalsIgnoreCase(authorizeOrder))
		{
			logger.info("Submitting_the_lineItem_with_lineItemExternalIdList="+lineitemIdList);
			startTimer = timer.getTime();
			LineItemService.INSTANCE.submitMultipleLineItem( orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineitemIdList,context);
			logger.info("TimeTakenForSubmitMultipleLineItemServiceCall="+(timer.getTime()-startTimer));
			OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_COMPLETE, orderQualVO, Long.valueOf(lineItemExternalId), context);
			
		}
		else
		{
			logger.info("Updating_lineItem_attribute_as_CKOInComplete_status");
			startTimer = timer.getTime();
			OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_INCOMPLETE, orderQualVO, Long.valueOf(lineItemExternalId), context);
			logger.info("TimeTakenForupdateLineItemAttributeServiceCall="+(timer.getTime()-startTimer));
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dialogueUpdate");
		
		List<String> params = new ArrayList<String>();
		params.add(Constants.PAGE_ID+"="+PageConstantsEnum.OQ_DEMO_CONTENTS.getId());
		params.add(Constants.PROVIDER_ID+"="+orderQualVO.getProviderExternalId());
		CKOVo.setParams(params);
		CKOVo.setErrorCode(PageConstantsEnum.SUCCESS_ST_CODE.getId());
		ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_COMPLETE);
		
		//Clearing all data
		if(providerSessionData != null && providerSessionData.get(orderQualVO.getProviderExternalId()) != null){
			providerSessionData.remove(orderQualVO.getProviderExternalId());
		}
		Utils.clearCachedData(orderQualVO);
		CKOCacheService.INSTANCE.clear(sessionVO.getSessionId());
		Utils.clearSessionData(request.getSession());
		
		logger.info("submitLineItemOnOrder_end");
		return mav;
	}
	
}
