package com.AL.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AL.service.InstallationService;
import com.AL.service.UpdateDigtialDialogueService;
import com.AL.ui.OrderRecapVO;
import com.AL.ui.common.util.PageConstantsEnum;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.DigitalCacheKeys;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.service.V.impl.StaticCKOCacheManager;
import com.AL.ui.util.JsonUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.ViewUtil;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author Nanda Kishore Palapala
 *
 */
@Controller
public class SubmitLineItemController  extends BaseController
{
	private static final Logger logger = Logger.getLogger(SubmitLineItemController.class);
	
	@Autowired
	private UpdateDigtialDialogueService updateDialogueService;

	@Autowired
	private InstallationService installationService;
	@RequestMapping(value = "/submitLineItem")
	public ModelAndView submitLineItemOnOrder(HttpServletRequest request) throws Exception 
	{
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		if(!Utils.isValidSession(sessionVO, SessionKeys.initiator.name()))
		{
			ModelAndView sessionTimedoutView = null;
			String idata = request.getParameter("iData");
			if(idata!=null && idata.length()>0)
			{
				JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
				CKOInitialVo CKOVo = util.convert(idata, "CKO", CKOInitialVo.class);
				if( !CKOVo.getParams().isEmpty() && CKOVo.getParams().contains("channelType=web"))
				{
					sessionTimedoutView = new ModelAndView("acdc_session_timed_out");
				}
			}
			if(sessionTimedoutView==null)
			{
				sessionTimedoutView = new ModelAndView("session_timed_out");
			}
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

		List<String> params = CKOVo.getParams();
		logger.info("Before_modify_paramsList="+params);
		if(params !=  null){
			for(String parmsValue : params){
				if((!Utils.isBlank(parmsValue)) && parmsValue.contains(Constants.PAGE_ID)){
					int pageIndex = params.indexOf(parmsValue);
					params.set(pageIndex,"page_id="+PageConstantsEnum.OQ_DEMO_CONTENTS.getId());
					break;
				}
			} 
			logger.info("After_modify_paramsList="+params);
			CKOVo.setParams(params);
		}
		CKOVo.setErrorCode(PageConstantsEnum.SUCCESS_ST_CODE.getId());
		ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_COMPLETE);

		//Clearing all data
		Utils.clearCachedData(orderQualVO);
		CKOCacheService.INSTANCE.clear(sessionVO.getSessionId());
		Utils.clearSessionData(request.getSession());
		logger.info("submitLineItemOnOrder_end");
		return mav;
	}
	
	@RequestMapping(value = "/submitLineItemForAppliance")
	public ModelAndView submitLineItemOnOrderForAppliance(HttpServletRequest request,final RedirectAttributes redirectAttrs) throws Exception 
	{	
		logger.info("start_submitLineItemForAppliance");
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
		ModelAndView modelAndView = new ModelAndView();
		if(!Utils.isValidSession(sessionVO, SessionKeys.initiator.name()))
		{
			CKOLineItemFactory.INSTANCE.prepareNewSession(sessionVO, request, util);	
		}
		
		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());
		CKOInitialVo CKOVo = (CKOInitialVo)sessionVO.get(SessionKeys.initiator.name());
			updateDialogueService.excuteUpdateDailog(request,orderQualVO);
		try{
			OrderType orderType = null;
			OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderQualVO.getOrderId(), Boolean.TRUE);
			
			if(orderManagementRequestResponse.getResponse().getOrderInfo() != null){
				if ((orderManagementRequestResponse.getResponse().getOrderInfo() != null) && (orderManagementRequestResponse.getResponse().getOrderInfo().size() > 0)) {
					orderType = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
				}
			}
			OrderRecapVO  orderRecapVO = installationService.buidlOrderRecapVO(request,orderType,orderQualVO,sessionVO);
			
			String monthlyTotal = String.valueOf(orderRecapVO.getMonthly());
			String installationTotal = String.valueOf(orderRecapVO.getInstallationTotal());
			Double baseRecurringPrice = Utils.isBlank(monthlyTotal)?0.00:Double.valueOf(monthlyTotal);
			Double baseNonRecurringPrice = Utils.isBlank(installationTotal)?0.00:Double.valueOf(installationTotal);
			SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
			List<String> lineitemIdList = new ArrayList<String>();
			lineitemIdList.add(Long.toString(orderQualVO.getLineItemExternalId()));
			ObjectFactory oFactory = new ObjectFactory();
			LineItemType lineItem = oFactory.createLineItemType();
			lineItem.setExternalId(orderQualVO.getLineItemExternalId());
			lineItem.setLineItemNumber(orderQualVO.getLineItemNumber());
			AttributeEntityType entity = oFactory.createAttributeEntityType();
			entity.setSource("CKO");
			AttributeDetailType attr = oFactory.createAttributeDetailType();
			attr.setName("STATUS");
			attr.setValue(Constants.CKO_COMPLETE);
			entity.getAttribute().add(attr);
			LineItemAttributeType attribute = oFactory.createLineItemAttributeType();
			attribute.getEntity().add(entity);
			lineItem.setLineItemAttributes(attribute);
			lineItem.setLineItemPriceInfo(CKOLineItemFactory.INSTANCE.buildLineItemPricInfoType(baseRecurringPrice,baseNonRecurringPrice));
			 OrderServiceUI.INSTANCE.updateLineItem(orderQualVO,context,lineItem);
			CKOVo.setStatus(Constants.CKO_COMPLETE);
			LineItemService.INSTANCE.submitMultipleLineItem( orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineitemIdList,context);
			String stateAsJSONString = util.convert(CKOVo, "CKO", CKOInitialVo.class);
			logger.info("redirectSuccessful");
			modelAndView.addObject("iData", stateAsJSONString);
			modelAndView.setViewName("dialogueUpdate");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("submitLineItemForAppliance",e);
			throw new Exception(e);
		}
		finally{
			if(orderQualVO != null){
				//Clearing all data
				StaticCKOCacheManager.INSTANCE.clearCache(DigitalCacheKeys.ProductVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
				StaticCKOCacheManager.INSTANCE.clearCache(DigitalCacheKeys.QualificationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
				StaticCKOCacheManager.INSTANCE.clearCache(DigitalCacheKeys.InstallationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
				Utils.clearCachedData(orderQualVO);
				CKOCacheService.INSTANCE.clear(sessionVO.getSessionId());
				Utils.clearSessionData(request.getSession());
			}
		}
		logger.info("end_submitLineItemForAppliance");
		return modelAndView;
		
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitDigitalLineItem", method = RequestMethod.POST)
	public ModelAndView submitDigitalLineItem(HttpServletRequest request) throws Exception{
		logger.info("start_submit_Digital_LineItem");
		JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
		ModelAndView modelAndView = new ModelAndView();
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		if(!Utils.isValidSession(sessionVO, SessionKeys.initiator.name()))
		{
			CKOLineItemFactory.INSTANCE.prepareNewSession(sessionVO, request, util);	
		}
		CKOInitialVo CKOVo = (CKOInitialVo)sessionVO.get(SessionKeys.initiator.name());	
		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());
		try{
			String monthlyTotal = request.getParameter("monthlyTotal");
			String installationTotal = request.getParameter("installationTotal");
			Double baseRecurringPrice = Utils.isBlank(monthlyTotal)?0.00:Double.valueOf(monthlyTotal);
			Double baseNonRecurringPrice = Utils.isBlank(installationTotal)?0.00:Double.valueOf(installationTotal);
			SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
			List<String> lineitemIdList = new ArrayList<String>();
			lineitemIdList.add(Long.toString(orderQualVO.getLineItemExternalId()));
			ObjectFactory oFactory = new ObjectFactory();
			LineItemType lineItem = oFactory.createLineItemType();
			lineItem.setExternalId(orderQualVO.getLineItemExternalId());
			lineItem.setLineItemNumber(orderQualVO.getLineItemNumber());
			AttributeEntityType entity = oFactory.createAttributeEntityType();
			entity.setSource("CKO");
			AttributeDetailType attr = oFactory.createAttributeDetailType();
			attr.setName("STATUS");
			attr.setValue(Constants.CKO_COMPLETE);
			entity.getAttribute().add(attr);
			LineItemAttributeType attribute = oFactory.createLineItemAttributeType();
			attribute.getEntity().add(entity);
			lineItem.setLineItemAttributes(attribute);
			lineItem.setLineItemPriceInfo(CKOLineItemFactory.INSTANCE.buildLineItemPricInfoType(baseRecurringPrice,baseNonRecurringPrice));
			 OrderServiceUI.INSTANCE.updateLineItem(orderQualVO,context,lineItem);
			CKOVo.setStatus(Constants.CKO_COMPLETE);
			LineItemService.INSTANCE.submitMultipleLineItem( orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineitemIdList,context);
			String stateAsJSONString = util.convert(CKOVo, "CKO", CKOInitialVo.class);
			logger.info("redirectSuccessful");
			modelAndView.addObject("iData", stateAsJSONString);
			modelAndView.setViewName("dialogueUpdate");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("submitDigitalLineItem_error",e);
			throw new Exception(e);
		}
		finally{
			if(orderQualVO != null){
				//Clearing all data
				StaticCKOCacheManager.INSTANCE.clearCache(DigitalCacheKeys.ProductVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
				StaticCKOCacheManager.INSTANCE.clearCache(DigitalCacheKeys.QualificationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
				StaticCKOCacheManager.INSTANCE.clearCache(DigitalCacheKeys.InstallationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
				Utils.clearCachedData(orderQualVO);
				CKOCacheService.INSTANCE.clear(sessionVO.getSessionId());
				Utils.clearSessionData(request.getSession());
			}
		}
		logger.info("end_submit_Digital_LineItem");
		return modelAndView;
	}
}
