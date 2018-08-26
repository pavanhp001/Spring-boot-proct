package com.AL.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Fieldset;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.builder.DialogueVOBuilder;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.builder.LineItemBuilder;
import com.AL.ui.builder.LineItemSelectionBuilder;
import com.AL.ui.common.util.PageConstantsEnum;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ErrorConstants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.exception.InvalidDataException;
import com.AL.ui.exception.InvalidFormatException;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.factory.HtmlFactory;
import com.AL.ui.factory.PrepopulateDialogue;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.ESEService;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.RequestUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.ViewUtil;
import com.AL.ui.validation.CustomizationValidator;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.DialogueVO;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.AL.xml.se.v4.ServiceabilityResponse2;
import com.AL.xml.v4.AccountHolderType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SchedulingInfoType;
import com.AL.ui.service.V.VOperation;

import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.RoleType;

@Controller
public class UpdateDialogueController extends BaseController {

	private static final Logger logger = Logger.getLogger(UpdateDialogueController.class);

	public static final ObjectFactory oFactory = new ObjectFactory();

	@RequestMapping(value = "/updateDialogue")
	public ModelAndView updateDialogue(HttpServletRequest request) throws Exception {
    
		logger.info("start_updateDialogue"); 
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		if(!Utils.isValidSession(sessionVO, SessionKeys.initiator.name())){
			ModelAndView sessionTimedoutView = new ModelAndView("session_timed_out");
			return sessionTimedoutView;
		}

		if(request.getSession().getAttribute("previouslyGivenDataId")!=null){
			request.getSession().removeAttribute("previouslyGivenDataId");
		}
		if(request.getSession().getAttribute("mandatoryDisclosureCheckboxes")!=null){
			request.getSession().removeAttribute("mandatoryDisclosureCheckboxes");
		}
		Boolean ishybris = false;
		if (request.getSession().getAttribute("ishybris") != null) {
			logger.info("hybris true");
			ishybris = (Boolean) request.getSession().getAttribute("ishybris");
		}
		Boolean frontierAsIs = false;
		if (request.getSession().getAttribute("frontierAsIs") != null) {
			logger.info("frontierAsIs true");
			frontierAsIs = (Boolean) request.getSession().getAttribute("frontierAsIs");
		}
		//selectedFeaturesJSONHiddenValue
		//sentFrom=exitOnClick


		Map<String,String> resultsMap = new HashMap<String, String>();

		boolean isDataCompleted = true;

		Map<String, String> requestParamMap = RequestUtil.INSTANCE.dumpRequestScope(request);

		String page_event = "";

		ModelAndView mav = new ModelAndView();
		String formData = "";

		CKOInitialVo CKOVo = (CKOInitialVo)sessionVO.get(SessionKeys.initiator.name());		

		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());

		Map<String, Map<String, List<String>>> enableMap = orderQualVO.getEnableDependencyMap();
		
		Map<String, String> declineExtIdAndValue = new HashMap<String, String>();

		for(Entry<String, Map<String, List<String>>> enableDependencyEntry : enableMap.entrySet()){
			for(Entry<String, List<String>> entrySetValue : enableDependencyEntry.getValue().entrySet()){
				for(String listValue : entrySetValue.getValue()){
					if(listValue.indexOf("PAGE_EVENT:") >= 0){
						declineExtIdAndValue.put(enableDependencyEntry.getKey(), entrySetValue.getKey());
					}
				}
			}
		}
		
		logger.info("Decline Authorization Ext IDs And Values ::::: "+declineExtIdAndValue);
		
		for(Entry<String, String> reqEntry : requestParamMap.entrySet()){
			for(String declineAuthExternalID : declineExtIdAndValue.keySet()){
				if(declineAuthExternalID.indexOf(reqEntry.getKey()) >=0 && reqEntry.getValue().equalsIgnoreCase(declineExtIdAndValue.get(declineAuthExternalID))){
					page_event = "DECLINE_AUTHORIZATION"; 
				}
			}
		}

		logger.info("page_event="+page_event);
		
		String providerID = orderQualVO.getProviderExternalId();

		DialogueVO dialogueVO = orderQualVO.getDialogueVO();

		Map<String, FeatureType> availableFeatureMap = orderQualVO.getDialogueFeatureMap();

		Map<String, FeatureGroupType> availableFeatureGroupMap = orderQualVO.getDialogueFeatureGroup();

		List<DataField> availableDataFieldList = new ArrayList<DataField>();

		String orderID = orderQualVO.getOrderId();

		String extSelectedValues = requestParamMap.get("extIDSelectedValues");

		String isUtilityOffer = requestParamMap.get("isUtilityOffer"); 
		
		logger.info("Is_UtilityOffer="+isUtilityOffer);
		
		long lineItemExternalID = orderQualVO.getLineItemExternalId();

		if(orderQualVO.getAvailableDataField() != null){
			availableDataFieldList = orderQualVO.getAvailableDataField();
		}
		else{
			if(dialogueVO != null && dialogueVO.getDataFieldMap() != null){
				for (Entry<String, Map<String, List<DataField>>>  dialogue : dialogueVO.getDataFieldMap().entrySet()) {
					for (Entry<String, List<DataField>>  dfEntry1 : dialogue.getValue().entrySet()) {
						for(DataField fieldEntry : dfEntry1.getValue()) {
							availableDataFieldList.add(fieldEntry);
						}
					}
				}
			}
		}

		if(requestParamMap.get("formdata") != null && !(requestParamMap.get("formdata").trim().length() <= 0)){
			formData = requestParamMap.get("formdata");
		}

		if(availableDataFieldList != null && requestParamMap != null){
			for(DataField df : availableDataFieldList){

				for(Entry<String, String> reqEle : requestParamMap.entrySet()){
					if(df.getFeatureExternalId() != null){
						String fetExternalID = reqEle.getKey();

						if(df.getFeatureExternalId().equals(fetExternalID)){
							resultsMap.put(df.getFeatureExternalId(), reqEle.getValue());
						}
					}
					if(df.getExternalId().equals(reqEle.getKey())  && (reqEle.getValue() != null && !(reqEle.getValue().trim().length() <=0))){
						resultsMap.put(reqEle.getKey(), reqEle.getValue());
					}
				}

				if(formData.indexOf(df.getExternalId()+",") >= 0 && df.getDescription() != null && df.getDescription().equalsIgnoreCase("optional")){
					String replaceString = df.getExternalId() + ",";
					formData = formData.replace(replaceString, "");
				}
			}
		}
		if(formData != null && formData.trim().length() > 0){
			isDataCompleted = false;
		}
		if(!isDataCompleted){
			resultsMap.put("formData", formData);
			resultsMap.put("MonthDD", (String) request.getAttribute("MonthDD"));
			resultsMap.put("YearDD", (String) request.getAttribute("YearDD"));
			request.setAttribute("resultMap_FormData", resultsMap);
			request.getSession().setAttribute("previouslyGivenDataId", request.getParameter("previouslyGivenDataId"));

		}

		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(orderQualVO.getProductExternalId(),orderQualVO.getGUID(), orderQualVO.getProviderExternalId());

		LineItemSelectionsType lineItemSelections = orderQualVO.getLineItemSelections();

		if((lineItemSelections == null) || (lineItemSelections.getSelection().size() == 0)) {
			lineItemSelections = LineItemSelectionBuilder.getLineItemSelections(
					requestParamMap, productInfo.getProductDetails().getCustomization());
			orderQualVO.setLineItemSelections(lineItemSelections);
		}

		
		
		String isDominionPageExecuted = requestParamMap.get("isDominionPageExecuted");
		logger.info("isDominionPageExecuted :: "+isDominionPageExecuted);
		String dominionProductExtId = (String) request.getSession().getAttribute("dominionProductExtId");
		logger.info("dominionProductExtId========"+dominionProductExtId);
		
		LineItemType newLineItemType = LineItemBuilder.INSTANCE.getLineItemBySelectedDialogues(requestParamMap, availableDataFieldList, availableFeatureMap, availableFeatureGroupMap, orderQualVO.getNewLineItemType(), resultsMap,request);
		SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
		if(!Utils.isBlank(isUtilityOffer) && dominionProductExtId!= null && orderQualVO.getProductExternalId().contains(dominionProductExtId) && !("true".equals(isDominionPageExecuted))){
			logger.info("dominionProductExtId condition ========"+dominionProductExtId);
			OrderType order = OrderServiceUI.INSTANCE.updateLineItemBySelectedlineItem(Constants.CKO_READY, orderQualVO, lineItemExternalID, null,context, null, newLineItemType);
			//ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_COMPLETE);
			updateDPSPartnerMavData(mav,CKOVo, order, orderQualVO,requestParamMap);
			return mav;
		}
		OrderType updatedOrder = OrderUtil.INSTANCE.returnOrderType(orderID);
		
		mav.setViewName("dialogueUpdate");

		


		StringBuilder customerName = new StringBuilder();
		if(updatedOrder.getAccountHolder() != null && !updatedOrder.getAccountHolder().isEmpty() && orderQualVO.getLineItemExternalId() > 0 ){
			LineItemType lineItemType = LineItemService.INSTANCE.getLineItem(updatedOrder.getAgentId(),updatedOrder,orderQualVO.getLineItemExternalId(),null);
			if (lineItemType != null && lineItemType.getAccountHolderExternalId() != null){
				for(AccountHolderType accountHolder : updatedOrder.getAccountHolder())
				{
					logger.info("accountHolderExtID="+ accountHolder.getExternalId()+"_LineAcountHolderExtID="+lineItemType.getAccountHolderExternalId());
					if ( accountHolder.getExternalId() == lineItemType.getAccountHolderExternalId().longValue() )
					{
						customerName.append(accountHolder.getFirstName());
						customerName.append(" ");
						customerName.append(accountHolder.getLastName());
						break;
					}
				}
			}
		}
		if(customerName.length() == 0){
				customerName.append(updatedOrder.getCustomerInformation().getCustomer().getFirstName());
				customerName.append(" ");
				customerName.append(updatedOrder.getCustomerInformation().getCustomer().getLastName());
		}
		mav.addObject("customerName", customerName);
		if(updatedOrder.getOrderStatus() != null && updatedOrder.getOrderStatus().getStatus() != null && updatedOrder.getOrderStatus().getStatus().equals("FATAL")){
			List<String> params = new ArrayList<String>();
			params.add(Constants.PAGE_ID+"="+PageConstantsEnum.OQ_DEMO_CONTENTS.getId());
			params.add(Constants.PROVIDER_ID+"="+providerID);
			CKOVo.setParams(params);
			CKOVo.setErrorCode(PageConstantsEnum.ERROR_ST_CODE.getId());
			OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineItemExternalID, context);
			ViewUtil.processErrorViewByStatus(mav, CKOVo, "Error", orderQualVO, ErrorConstants.CKO_ERROR);
			return mav;
		}

		AttributeEntityType secondDateEntity = oFactory.createAttributeEntityType();
		AttributeEntityType firstTimeEntity = oFactory.createAttributeEntityType();

		SchedulingInfoType schedulingInfo = oFactory.createSchedulingInfoType();

		updatedOrder = PrepopulateDialogue.INSTANCE.updateValuesToCustomer(updatedOrder, availableDataFieldList, resultsMap, orderQualVO, 
				lineItemExternalID, secondDateEntity, firstTimeEntity, context, requestParamMap, extSelectedValues, schedulingInfo);

		if(updatedOrder.getOrderStatus() != null && updatedOrder.getOrderStatus().getStatus() != null && 
				updatedOrder.getOrderStatus().getStatus().equals("FATAL")){

			List<String> params = new ArrayList<String>();
			params.add(Constants.PAGE_ID+"="+PageConstantsEnum.OQ_DEMO_CONTENTS.getId());
			params.add(Constants.PROVIDER_ID+"="+providerID);
			CKOVo.setParams(params);
			CKOVo.setErrorCode(PageConstantsEnum.ERROR_ST_CODE.getId());
			OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineItemExternalID, context);
			ViewUtil.processErrorViewByStatus(mav, CKOVo, "Error", orderQualVO, ErrorConstants.CKO_ERROR);
			return mav;
		}
		StringBuilder events = new StringBuilder();

		List<Fieldset> fieldsetList = HtmlFactory.INSTANCE.updateDialogueFieldSet(events, dialogueVO);
		for (Fieldset fieldset : fieldsetList) {
			String element = HtmlBuilder.INSTANCE.toString(fieldset);
			events.append(element);
		}
		orderQualVO.setDataField(events.toString());

		mav.addObject("dataField", events.toString());

		mav.addObject("orderId", updatedOrder.getExternalId());
		
		if(updatedOrder.getCustomerInformation() != null){
			mav.addObject("customerId", updatedOrder.getCustomerInformation().getCustomer().getExternalId());
		}
		else{
			logger.debug("FATAL error while updating order ");
			List<String> params = new ArrayList<String>();
			params.add(Constants.PAGE_ID+"="+PageConstantsEnum.OQ_DEMO_CONTENTS.getId());
			params.add(Constants.PROVIDER_ID+"="+providerID);
			CKOVo.setParams(params);
			CKOVo.setErrorCode(PageConstantsEnum.ERROR_ST_CODE.getId());
			OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineItemExternalID, context);
			ViewUtil.processErrorViewByStatus(mav, CKOVo, "Error", orderQualVO, ErrorConstants.CKO_ERROR);
			return mav;
		}

		String isUtilityOfferAvailOnOrder = orderQualVO.getHasUtitliOfferLineitem();
		List<String> params = new ArrayList<String>();
		params.add(Constants.PAGE_ID+"="+PageConstantsEnum.OQ_DEMO_CONTENTS.getId());
		params.add(Constants.PROVIDER_ID+"="+providerID);
		CKOVo.setParams(params);
		CKOVo.setErrorCode(PageConstantsEnum.SUCCESS_ST_CODE.getId());

		logger.info("Getting_second_date+"+secondDateEntity);

		newLineItemType.setSchedulingInfo(schedulingInfo);

		OrderType responseOrder = null;
		if((secondDateEntity != null && secondDateEntity.getSource() != null && secondDateEntity.getSource().trim().equals("SECOND_DESIRED_DATE")) ||
				(firstTimeEntity != null && firstTimeEntity.getSource() != null && firstTimeEntity.getSource().trim().equals("FIRST_INSTALLATION_TIME"))){

			logger.info("Inside_second_entity="+secondDateEntity);
			if(!Utils.isBlank(page_event) && "DECLINE_AUTHORIZATION".equals(page_event)){
				responseOrder = OrderServiceUI.INSTANCE.updateLineItemBySelectedlineItem(Constants.REMOVED, orderQualVO, lineItemExternalID, secondDateEntity,context, firstTimeEntity, newLineItemType);
			}
			else{
				responseOrder = OrderServiceUI.INSTANCE.updateLineItemBySelectedlineItem(Constants.CKO_COMPLETE, orderQualVO, lineItemExternalID, secondDateEntity,context, firstTimeEntity, newLineItemType);
				if(isUtilityOffer == null || !("true").equalsIgnoreCase(isUtilityOffer))
				{
					if (!ishybris && !frontierAsIs) {
					responseOrder = OrderServiceUI.INSTANCE.updateLineItemBySelectedlineItem(Constants.CKO_INCOMPLETE, orderQualVO, lineItemExternalID, secondDateEntity,context, firstTimeEntity, newLineItemType);
					createOrderRecapScreenView(mav,responseOrder,lineItemExternalID, context, request);
					mav.addObject("selectedFeatureJSONValue", request.getParameter("selectedFeatureJSONValue") ) ;
					mav.addObject("selectedFeaturesJSONHiddenValue", request.getParameter("selectedFeaturesJSONHiddenValue") ) ;
					mav.setViewName("submitLineItem");
					}
				}
			}

			logger.info("After updating line item attributes ");
		}
		else{
			logger.info("INSIDE First and second installltion date null");
			
			if(!Utils.isBlank(page_event) && "DECLINE_AUTHORIZATION".equals(page_event)){
				
				if(isUtilityOffer != null && ("true").equalsIgnoreCase(isUtilityOffer)){
					LineItemType utilityOfferlineItem = CKOLineItemFactory.INSTANCE.getUtilityOfferLineItem(updatedOrder,orderQualVO.getProductExternalId());
					if(utilityOfferlineItem != null){
						newLineItemType.setLineItemNumber(utilityOfferlineItem.getLineItemNumber());
						newLineItemType.setExternalId(utilityOfferlineItem.getExternalId());
					}else{
						logger.info("no_utitlityOffer_lineItem_on_order");
					}
				}
				logger.info("lineItemExternalID="+newLineItemType.getExternalId());
				if( newLineItemType.getExternalId() > 0){
					responseOrder = OrderServiceUI.INSTANCE.updateLineItemBySelectedlineItem(Constants.REMOVED, orderQualVO, lineItemExternalID, null,context, null, newLineItemType);
				}
			}
			else{
				if(isUtilityOffer != null && ("true").equalsIgnoreCase(isUtilityOffer))
				{
					LineItemType utilityOfferlineItem = CKOLineItemFactory.INSTANCE.getUtilityOfferLineItem(updatedOrder,orderQualVO.getProductExternalId());
					if(utilityOfferlineItem != null){
						newLineItemType.setLineItemNumber(utilityOfferlineItem.getLineItemNumber());
						newLineItemType.setExternalId(utilityOfferlineItem.getExternalId());
					}if(!Utils.isBlank(requestParamMap.get("DominionStatus")) && requestParamMap.get("DominionStatus").trim().length() > 0){
						logger.info("ProductOfferId:: "+requestParamMap.get("ProductOfferId"));
						logger.info("DominionStatus:: "+requestParamMap.get("DominionStatus"));
						ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_COMPLETE);
						if(Constants.SUCCESS.equals(requestParamMap.get("DominionStatus"))){
							responseOrder = OrderServiceUI.INSTANCE.updateLineItemAttributesBySelectedlineItem(orderQualVO, lineItemExternalID, context, newLineItemType, requestParamMap);
						}
					}else{
						responseOrder = OrderServiceUI.INSTANCE.updateLineItemBySelectedlineItem(Constants.CKO_COMPLETE, orderQualVO, lineItemExternalID, null,context, null, newLineItemType);
					}

				} else {
					responseOrder = OrderServiceUI.INSTANCE.updateLineItemBySelectedlineItem(Constants.CKO_INCOMPLETE, orderQualVO, lineItemExternalID, null,context, null, newLineItemType);
				}
				
							
				if(isUtilityOffer == null || !("true").equalsIgnoreCase(isUtilityOffer))
				{
					if (!ishybris && !frontierAsIs) {
					createOrderRecapScreenView(mav,responseOrder, lineItemExternalID, context, request);
					mav.addObject("selectedFeatureJSONValue", request.getParameter("selectedFeatureJSONValue") ) ;
					mav.addObject("oqSelectedFeaturesHIDDENValue", request.getParameter("oqSelectedFeaturesHIDDENValue") ) ;
					mav.setViewName("submitLineItem");
					}
				}
			}

			logger.info("After updating First and second installltion date null");
		}

		/*
		 *	Scenario 1
		 *	if line item is in CKO incomplete status and if its an utility product, do not update the line item status
		 *	else if line item is in CKO incomplete status and if it is a static product, update the line item status to cancel removed
		 *
		 *	Scenario 2
		 *	if the product is static product and line item is in CKO complete or in CKO canceled do not do anything
		 *	else if the product is a utility product and line item status in CKO complete and CKO canceled, update line item status to
		 *	provision ready 
		 */

		if(isUtilityOffer != null && ("true").equalsIgnoreCase(isUtilityOffer)){
			if(Utils.isBlank(page_event)){
				LineItemType utilityOfferlineItem = CKOLineItemFactory.INSTANCE.getUtilityOfferLineItem(updatedOrder,orderQualVO.getProductExternalId());
				if(utilityOfferlineItem != null){
					OrderServiceUI.INSTANCE.submitUtiltyOffer(responseOrder, utilityOfferlineItem.getExternalId(), orderQualVO, context);
					if(CKOVo.getLineItems() == null){
						List<Long> list  =  new ArrayList<Long>();
						CKOVo.setLineItems(list);
					}
					CKOVo.getLineItems().add(0, utilityOfferlineItem.getExternalId());
				}
				
			}
		}
		else{
			if(!Utils.isBlank(page_event)){
				updateLineItemStatusToCancel(responseOrder, orderQualVO, request.getSession());
			}
		}

		if("submitLineItem".equalsIgnoreCase(mav.getViewName()))
		{
			ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_SUCCESS);
		}
		else
		{
			ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_COMPLETE);
			Utils.clearCachedData(orderQualVO);
			MDC.clear();
			CKOCacheService.INSTANCE.clear(sessionVO.getSessionId());
			Utils.clearSessionData(request.getSession());
		}
			

			if (ishybris || frontierAsIs) {
				logger.info("Hybris shell plan/frontier as-is plan skipping order recap");
				mav.setViewName("dialogueUpdate");
				List<String> lineitemIdList = new ArrayList<String>();
				lineitemIdList.add(Long.toString(lineItemExternalID));
				LineItemService.INSTANCE.submitMultipleLineItem( orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineitemIdList,context);
				OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_COMPLETE, orderQualVO, lineItemExternalID, context);
			}
		
		logger.info("end_updateDialogue");
		
		return mav;
	}

	private void createOrderRecapScreenView(ModelAndView mav, OrderType orderType, long lineItemExternalID, SalesContextType context, HttpServletRequest request) throws RecoverableException, UnRecoverableException
	{
		LineItemType orderRecapLineItemType = null;
		if ((orderType != null) && (orderType.getLineItems() != null))
		{
			if(orderType.getLineItems()!=null && !orderType.getLineItems().getLineItem().isEmpty())
			{
				for(LineItemType lineItemType : orderType.getLineItems().getLineItem())
				{
					if( lineItemType.getExternalId()==lineItemExternalID )
					{
						orderRecapLineItemType = lineItemType;
						break;
					}
				}
			}
			
			Double baseRecurringPrice = (Double)request.getSession().getAttribute("baseRecurringPrice");
			Double baseNonRecurringPrice = (Double)request.getSession().getAttribute("baseNonRecurringPrice");
			
			orderType = CKOLineItemFactory.INSTANCE.validateStaticPrices(orderType, orderRecapLineItemType, context, baseRecurringPrice, baseNonRecurringPrice);
			
			//Getting order recap line item to display after updating the line item prices.
			if(orderType.getLineItems()!=null && !orderType.getLineItems().getLineItem().isEmpty())
			{
				for(LineItemType lineItemType : orderType.getLineItems().getLineItem())
				{
					if( lineItemType.getExternalId()==lineItemExternalID )
					{
						orderRecapLineItemType = lineItemType;
						break;
					}
				}
			}
			
			Map<String, Map<String, String>> dynamicFlowContext = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			if(dynamicFlowContext!=null)
			{
				Map<String, String> dynamicFlow = dynamicFlowContext.get("dynamicFlow");
				dynamicFlow.put("dynamicFlow.page", "OrderRecap");
				dynamicFlow.put("dynamicFlow.enabled", "true");
				
				Map<String, String> orderSource = new HashMap<String, String>();
				orderSource.put("orderSource.referrer", orderType.getReferrerId());
				dynamicFlowContext.put("orderSource", orderSource);
				
				DialogueResponseType dialogueResponseType = DialogServiceUI.INSTANCE.getDialoguesByContext(dynamicFlowContext);
				DataFieldList dataFieldList = DialogueVOBuilder.buildDataFieldList(dialogueResponseType);
				
				mav.addObject("dataFieldList", dataFieldList.getDataField());
			}
			
			String baseRecPrice = request.getParameter("lineItemMonthlyPrice");
			String baseNonRecPrice = request.getParameter("lineItemInstallationPrice");
			mav.addObject("productMonthlyPrice", baseRecPrice);
			mav.addObject("productInstallationPrice", baseNonRecPrice);
			
			mav.addObject("businessPartyName", orderType.getCustomerInformation().getCustomer().getReferrerGeneralName());
			mav.addObject("lineItemType", orderRecapLineItemType);
			mav.addObject("baseMonthlyPrice", CKOLineItemFactory.INSTANCE.extractBaseMontlyPrice(orderRecapLineItemType));
			mav.addObject("hasFeatures", CKOLineItemFactory.INSTANCE.hasFeatures(orderRecapLineItemType));
			mav.addObject("LineItemVo", CKOLineItemFactory.INSTANCE.lineItemPromotions(orderType, orderRecapLineItemType));
		}
	}
	

	public static List<String> validateProduct(String validationType, 
			Map<String, String> requestParamMap, String externalID, 
			List<DataField> availableDataFieldList) throws InvalidDataException, ParseException, InvalidFormatException{
		if(validationType.equalsIgnoreCase("5CAL") || validationType.equalsIgnoreCase("6CAL") || validationType.equalsIgnoreCase("7CAL") || (validationType.indexOf("CAL:") > 0)){
			if(validationType.equalsIgnoreCase("5CAL")){
				validationType = "cal5";
			}
			else if(validationType.equalsIgnoreCase("6CAL")){
				validationType = "cal6";
			}
			else if(validationType.equalsIgnoreCase("7CAL")){
				validationType = "cal7";
			}
			else if((validationType.indexOf("CAL:") > 0)){
				validationType = "cal_varies";
			}
		}

		List<String> errorList = new ArrayList<String>();

		if(!Character.isDigit(validationType.charAt(0))){

			switch (basicValidationTypes.valueOf(validationType)){

			case SSN :	errorList = CustomizationValidator.INSTANCE.validateSocialSecurityNumber(requestParamMap, externalID); 
			break;

			case Routing_Number :	 errorList = CustomizationValidator.INSTANCE.validateRoutingNumber(requestParamMap, externalID, errorList);
			break;

			case Phone :	errorList = CustomizationValidator.INSTANCE.validatePhoneNumber(requestParamMap, externalID, errorList);
			break;

			case phone :	errorList = CustomizationValidator.INSTANCE.validatePhoneNumber(requestParamMap, externalID, errorList);
			break;			

			case Email :	errorList = CustomizationValidator.INSTANCE.validateEmail(requestParamMap, externalID, errorList);
			break;

			case credit_card_number :	errorList = CustomizationValidator.INSTANCE.validateCreditCardDetails(requestParamMap, externalID, availableDataFieldList);
			break;

			case cal5 :	errorList = CustomizationValidator.INSTANCE.validateCalDetails(requestParamMap, externalID);
			break;

			case cal6 :	errorList = CustomizationValidator.INSTANCE.validateCalDetails(requestParamMap, externalID);
			break;

			case cal7 :	errorList = CustomizationValidator.INSTANCE.validateCalDetails(requestParamMap, externalID);
			break;

			case cal_varies :	errorList = CustomizationValidator.INSTANCE.validateCalDetails(requestParamMap, externalID);
			break;

			case datepicker :	errorList = CustomizationValidator.INSTANCE.validateCalDetails(requestParamMap, externalID);
			break;
			}
		}
		return errorList;
	}

	public enum basicValidationTypes
	{
		SSN, Routing_Number, Phone, phone, Email, Date, date, credit_card_number, credit_card_cvv, Check_Number, Checking_Account_Number, credit_card_date, cal5, cal6, cal7, cal_varies, datepicker;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	@RequestMapping(value="updateDialogue/validateEmail")
	protected @ResponseBody String validateEmail(HttpServletRequest request,
			HttpServletResponse response) throws InvalidDataException, InvalidFormatException, JSONException{
		logger.info("INSIDE VALIDATE EMAIL AJAX CALL ");
		JSONObject errorJson = new JSONObject();

		String emailStr = request.getParameter("selectedValue");
		logger.info("SELECTED EMAIL ::::::::: "+emailStr);
		int atIndex = emailStr.indexOf( "@" );
		String url = null;
		if ( !emailStr.equals("") ) {

			if ( atIndex <= 0 || (atIndex + 1) >= emailStr.length() ){
				errorJson.put("error","E-mail not valid, missing or missplaced \"@\" symbol.");
			}
			else{
				url = emailStr.substring( atIndex + 1 );
				String address = emailStr.substring( 0, atIndex );
				if (!CustomizationValidator.INSTANCE.isProperAddress(url, true) || !CustomizationValidator.INSTANCE.isProperAddress(address, false)){
					errorJson.put("error","E-mail not valid.");
				}
				if (!CustomizationValidator.INSTANCE.isValidDNS(url)) {
					errorJson.put("error","Domain (" + url + ") does not have E-mail server.");
				}
			}
		}
		return errorJson.toString();
	}

	@RequestMapping(value="updateDialogue/populateCityAndState")
	protected @ResponseBody String populateCityAndState(HttpServletRequest request,
			HttpServletResponse response) throws InvalidDataException, InvalidFormatException, JSONException{
		logger.info("INSIDE POPULATE CITY AND STATE AJAX CALL ");

		String zipCode = request.getParameter("zipCode");
		logger.info("ENTERED ZIP CODE ::::::::: "+zipCode);

		String dwellingType = "House";
		ServiceabilityEnterpriseResponse sarRes;

		sarRes = ESEService.INSTANCE.getServiceabilityResponse(zipCode, dwellingType);

		ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
		com.AL.xml.se.v4.AddressType sreAddress = sre.getCorrectedAddress();
		String city = sreAddress.getCity();
		String state = sreAddress.getStateOrProvince();
		JSONObject obj = new JSONObject();
		try {
			if(city != null && city.trim().length() > 0 && state != null && state.trim().length() > 0){
				obj.put("city", formatString(city));
				obj.put("state", state);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();

	}

	private String formatString(String toBeFormatted){
		String formattedString = "";
		if(!Utils.isBlank(toBeFormatted)){
			String [] sp = toBeFormatted.split(" ");
			for(int i=0;i < sp.length;i++){
				formattedString = formattedString + sp[i].substring(0,1).toUpperCase()+sp[i].substring(1).toLowerCase()+" ";
			}	
		}
		return formattedString;
	}

	/*LUHAN Algorithm
	 * Checks whether the given credit card number is valid or not using check digit.
	 * STEPS:
	 * 		1. let the last digit of the given credit card number be the check digit.
	 * 		2. multiply each alternate digit with 2. If the result value length is greater than 1(i.e, the result is >9),
	 * 		add the digits of this value.
	 * 		3. sum up all the digits along with the result value.
	 * 		4. multiply the result with 9.
	 * 		5. the last digit of the resulting number is the check digit.
	 */ 
	@RequestMapping(value="updateDialogue/isValidCCNumber")
	protected @ResponseBody String isValidCCNumber(HttpServletRequest request,
			HttpServletResponse response) throws InvalidDataException, InvalidFormatException, JSONException{
		String ccNumber = request.getParameter("ccNumber");
		String ccWithoutCheckDigit = ccNumber.substring(0, (ccNumber.length()-1));
		int checkDigitInitial = Integer.valueOf(ccNumber.substring(ccNumber.length()-1));
		String reverseString = new StringBuilder(ccWithoutCheckDigit).reverse().toString();
		int ccLength = reverseString.length();
		JSONObject errorJson = new JSONObject();
		int sum = 0;
		int specificChar = 0;
		for (int i = 1; i <= ccLength; i++){
			specificChar = Integer.valueOf(reverseString.substring(0, 1));
			reverseString = reverseString.substring(1);
			if(i == 1 || (i%2 != 0)){
				if(specificChar != 0){
					specificChar = specificChar*2;
					String specificCharVal = String.valueOf(specificChar);
					int finalSum = 0;
					int j = 1;
					if(specificCharVal.length() >= 2){
						while(j <= specificCharVal.length()){
							finalSum += Integer.valueOf(specificCharVal.substring(0, 1));
							specificCharVal = specificCharVal.substring(1);
						}
						specificChar = finalSum;
					}
				}
			}
			sum += specificChar;
		}
		int finalSum = sum*9;
		int checkDigit = (finalSum % 10);

		if(checkDigitInitial == checkDigit){

		}
		else{
			errorJson.put("error", "Credit Card Number invalid");
		}

		return errorJson.toString();
	}

	/*
	 * if the person selects no for credit check, then the questions must not be pitched and the order must be cancelled,
	 * for this when ever the customer selects no for credit check question, then the order must be updated to CKO_incomplete and 
	 * he should be redirected to orderSummary page
	 */

	public void updateLineItemStatusToCancel(OrderType order, OrderQualVO orderQualVO, HttpSession session) throws Exception {

		List<Integer> reasons = new ArrayList<Integer>(); 
		reasons.add(Constants.REASON_CODE);
		SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(session);
		List<String> lineItemIdList = new ArrayList<String>();
		lineItemIdList.add(String.valueOf(orderQualVO.getLineItemExternalId()));

		LineItemService.INSTANCE.updateLineItemStatus(orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineItemIdList, 
				LineItemStatusCodesType.CANCELLED_REMOVED.value(), LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, context, new ErrorList());

	}
	@RequestMapping(value="/showErrorPage")
	protected ModelAndView showErorPage(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("message") String message) throws Exception{
		logger.info("Error_message="+message);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dominionErrMsg", message);
		mav.setViewName("dominionErrorPage");
		return mav;
	}
	private void updateDPSPartnerMavData(ModelAndView mav, CKOInitialVo CKOVo, OrderType order, OrderQualVO orderQualVO,Map<String,String> requestParamMap) {
		  logger.info("updateDPSPartnerMavData");
		  ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_SUCCESS);
		  mav.setViewName("oq_DominionPortal");
		  mav.addObject("isUtilityOffer", true);
		  if(CKOVo != null){
		   mav.addObject("PartnerURL", CKOVo.getParentUrl());
		  }
		  if(orderQualVO != null ){
		   mav.addObject("agentId", orderQualVO.getPhoneId());
		   mav.addObject("inumVal", orderQualVO.getInumVal());
		   mav.addObject("lineItemExternalId", orderQualVO.getLineItemExternalId());
		   //mav.addObject("PhoneId", orderQualVO.getPhoneId());
		  }
		  if (order != null && order.getCustomerInformation() != null 
		    && order.getCustomerInformation().getCustomer() != null
		    && order.getCustomerInformation().getCustomer().getAddressList() != null ){
			  
			  mav.addObject("FirstName", order.getCustomerInformation().getCustomer().getFirstName());
			  mav.addObject("LastName", order.getCustomerInformation().getCustomer().getLastName());
			   
		   List<CustAddress> hybrisAddressList = order.getCustomerInformation().getCustomer().getAddressList().getCustomerAddress();
		   for(CustAddress custAddr : hybrisAddressList){
		    for(RoleType role : custAddr.getAddressRoles().getRole()){
		     if(role.value().equals("ServiceAddress")){
		      mav.addObject("AddressLine1", custAddr.getAddress().getStreetNumber()+" "+custAddr.getAddress().getStreetName()+" "+custAddr.getAddress().getStreetType());
		      mav.addObject("AddressLine2", custAddr.getAddress().getLine2());
		      mav.addObject("City", custAddr.getAddress().getCity());
		      mav.addObject("State", custAddr.getAddress().getStateOrProvince());
		      mav.addObject("ZipCode", custAddr.getAddress().getPostalCode().split("-")[0]);
		      
		     }
		    }
		   }  
		   String offerId = (String)requestParamMap.get("DOMINION-SURGEHELP");
	       if(!Utils.isBlank(offerId)){
	    	   mav.addObject("ProductOfferId", offerId.split("\\|")[0]);
			   mav.addObject("UDCCode", offerId.split("\\|")[1]);
	       }
		   mav.addObject("PhoneNum",requestParamMap.get("DisabledHomePhoneNum").replaceAll("-", ""));
		   mav.addObject("Email", requestParamMap.get("Email"));
		  }
		 }
}