package com.AL.ui.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.builder.JSONBuilder;
import com.AL.ui.common.util.PageConstantsEnum;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ErrorConstants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.factory.ModelViewFactory;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.service.workflow.ActionStatus;
import com.AL.ui.service.workflow.Intent;
import com.AL.ui.service.workflow.ViewFlow;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.JsonUtil;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.PriceDisplayUtil;
import com.AL.ui.util.RequestUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.ViewUtil;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.DialogueVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.PriceDisplayVO;
import com.AL.ui.vo.SessionVO;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.v4.AccountHolderType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

public enum ActionStoreInitialRequest {

	INSTANCE;
	public static final ObjectFactory oFactory = new ObjectFactory();
	private static final Logger logger = Logger.getLogger(ActionStoreInitialRequest.class);

	/**
	 * logic to display features
	 * 
	 * @param intent
	 * @param request
	 * @param order2 
	 * @param orderQualObj 
	 * @return
	 * @throws Exception
	 */
	public ViewFlow execute(Intent intent, HttpServletRequest request, OrderQualVO orderQualVO) throws Exception {
		final JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
		ModelAndView mav = new ModelAndView();
		ViewFlow viewFlow = null;
		/*
		 * checks whether we landed over this page clicking on the back button
		 */
		if(!Utils.isBlank(request.getParameter("previouslyGivenDataId"))){
			//logger.info("Customer Qualification Page entered Dialogues ACTION PAGE :::::::: "+request.getParameter("previouslyGivenDataId"));
			request.getSession().setAttribute("previouslyGivenDataId", request.getParameter("previouslyGivenDataId"));
			mav.addObject("previouslyGivenDataId", request.getParameter("previouslyGivenDataId"));
		}
		
		/*
		 * converts the HttpRequest to a map with key as names of selectedFields and values as the corresponding values
		 */
		Map<String, String> requestParamMap = RequestUtil.INSTANCE.dumpRequestScope(request);
		
		String CKOInput = null;
		OrderType order = (OrderType) request.getAttribute("orderType");
		
		/*
		 * retrieve the CKOInput(hidden JSON sent from the sales center) from the intent
		 */
		if(intent != null){
			CKOInput = intent.getAsString("CKOInput");
		}
		else{
			Intent intentFinal = (Intent) request.getSession().getAttribute("intent");
			CKOInput = intentFinal.getAsString("CKOInput");
		}
		
		/*
		 * convert CKOInput String to JSON Object
		 */
		JSONObject mainJson = null;
		try {
			mainJson = new JSONObject(CKOInput);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		long lineItemExternalID = orderQualVO.getLineItemExternalId();
		logger.info("LineItem_ExternalID+"+lineItemExternalID);
		
		CKOInitialVo CKOVo = util.convert(CKOInput, "CKO", CKOInitialVo.class);
		
		List<String> paramValues =  CKOVo.getParams();
		if(paramValues.size()>6)
		{
			Map<String, Map<String, String>> dynamicFlowContextMap = new HashMap<String, Map<String, String>>();
			Map<String, String> dynamicFlow = new HashMap<String, String>();
			
			for(int i=5; i<paramValues.size(); i++)
			{
				String paramArray[] = paramValues.get(i).split("=");
				if("GUID".equalsIgnoreCase(paramArray[0]))
				{
					dynamicFlow.put("GUID", paramArray[1]);
				}
				else if(paramArray[0].indexOf("dynamicFlow") != -1)
				{
					dynamicFlow.put(paramArray[0], paramArray[1]);
				}
			}
			dynamicFlowContextMap.put("dynamicFlow", dynamicFlow);
			request.getSession().setAttribute("dynamicFlowContextMap", dynamicFlowContextMap);
		}
		/*receiversList get from CKOVO param*/
		if(paramValues != null && !paramValues.isEmpty()){
			for(String param:paramValues){
				if(param.indexOf("receiversList") != -1){
					request.getSession().setAttribute("receiversList", param.split("=")[1]);
				}
			}
		}
		
		/*
			-------------------------------------------------------------------------------------------------------------------------
				Start Of Logic to get the features and featureGroups selected during previous CKO
			-------------------------------------------------------------------------------------------------------------------------
		 */
		
		/*
		 * if we get to this page by removing product that is already in CKO complete status, we need to prepopulte 
		 * the selected features and featureGroups that are selected during the previous CKO
		 */
		List<FeatureValueType> previouslySelectedFeatures = new ArrayList<FeatureValueType>();
		List<FeatureGroup> previouslySelectedFeatureGroups = new ArrayList<FeatureGroup>();
		LineItemType lineItem = null;
		
		/*
		 * retrieve the current lineItem from the Order based on the lineItemExternalId
		 * and getting all the promotion status
		 */
		logger.info("returning current lineItem from the order");
		JSONObject promoJSONObject = new JSONObject();
		for(LineItemType liType : order.getLineItems().getLineItem()){
			if(liType.getExternalId() == lineItemExternalID){
				lineItem = liType;
			}

			if(liType.getLineItemDetail().getDetailType().equals("productPromotion")){
				String externalID = liType.getLineItemDetail().getDetail().getPromotionLineItem().getPromotion().getExternalId();
				LineItemStatusCodesType lineItemStatus = liType.getLineItemStatus().getStatusCode();
				if(liType.getLineItemStatus() != null && liType.getLineItemStatus().getStatusCode() != null){
					try {
						promoJSONObject.put(externalID, lineItemStatus);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				logger.info("EXTERNAL_ID"+externalID);
			}
		}

		String hybrisAttributes = "";
		request.getSession().setAttribute("ishybris", false);
		request.getSession().setAttribute("hybrisAttributes", null);
		if(lineItem != null) {
			if(lineItem.getLineItemAttributes() != null && lineItem.getLineItemAttributes().getEntity() != null){
				for(AttributeEntityType entityType : lineItem.getLineItemAttributes().getEntity()){
					if (entityType.getSource().equals("HYBRIS_ATTRIBUTES")) {
						hybrisAttributes = entityType.getAttribute().get(0).getValue();
						logger.info("hybrisAttributes=" + hybrisAttributes);
						request.getSession().setAttribute("hybrisAttributes", hybrisAttributes);
						request.getSession().setAttribute("ishybris", true);
					}
				}
			}		
		}
		
		orderQualVO.setPromotionStatusJSON(promoJSONObject);
		
		/*
		 * builds a new LineItemObject(with item number and lineItemExternalID only) from the existing LineItem to avoid included features being added
		 * and setting the lineItem to OrderQualVO object
		 */
		if(lineItem != null){
			orderQualVO.setNewLineItemType(buildNewLineItem(lineItem));
			orderQualVO.setLineItemType(lineItem);
			orderQualVO.setLineItemNumber(lineItem.getLineItemNumber());
			/*
			 * Checks whether lineItem contains previously SelectedFeatures and adds them to features and featuresGroup List
			 */
			returnPreviouslySelectedFeatures(lineItem, previouslySelectedFeatures, previouslySelectedFeatureGroups);
		}
		
		logger.info("Previous_CKO_Selected_Features="+previouslySelectedFeatures);
		logger.info("Previous_CKO_Selected_Feature_Groups="+previouslySelectedFeatureGroups);
		
		/*
			-------------------------------------------------------------------------------------------------------------------------
					End Of Logic to get the features and featureGroups selected during previous CKO
			-------------------------------------------------------------------------------------------------------------------------
		*/
		
		logger.info("GUID --> "+orderQualVO.getGUID());
		logger.info("Product External Id --> "+orderQualVO.getProductExternalId());
		logger.info("Provider External Id --> "+orderQualVO.getProviderExternalId());
		
		/*
		 *  check whether the product info object is present in the cache, if present, return the same
		 *  else make a getProductDetails Service Call return the productInfo Object
		 */
		//ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(orderQualVO.getProductExternalId(), orderQualVO.getGUID(), orderQualVO.getProviderExternalId());
		ProductInfoType productInfo = (ProductInfoType) request.getAttribute("productInfo");
		logger.info("After Calling productService");
		
		if(productInfo == null) {
			
			/*
			 * check whether the sales context is present in the session, if present, use the same or 
			 * perform a getSalesContext Call and use the salesContext
			 */
			SalesContextType context = OrderUtil.INSTANCE.returnSalesContext(orderQualVO.getOrderId(), orderQualVO.getAgentId(), request.getSession());
			
			logger.debug("FATAL error while updating order ");		
			List<String> params = new ArrayList<String>();
			params.add(Constants.PAGE_ID+"="+PageConstantsEnum.PRODUCT_INFO.getId());
			params.add(Constants.PROVIDER_ID+"="+orderQualVO.getProviderExternalId());
			params.add(Constants.AGENT_ID+"="+orderQualVO.getAgentId());
			params.add(Constants.PRODUCT_EXTERNALID+"="+orderQualVO.getProductExternalId());
			params.add(Constants.GUID.toLowerCase()+"="+orderQualVO.getGUID());
			CKOVo.setParams(params);
			CKOVo.setErrorCode(PageConstantsEnum.ERROR_ST_CODE.getId());
			OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineItemExternalID, context);
			return ViewUtil.resolveErrorView(CKOVo, Constants.CKO_ERROR, orderQualVO, ErrorConstants.REQUEST_TN_FAIL);
		}
		
		//META data
		boolean receiverValidation = false;
		boolean frontierAsIs = false;
		String hybrisCategoryName = "";
		if(productInfo.getProductDetails() != null && productInfo.getProductDetails().getMetaData() != null 
				&& productInfo.getProductDetails().getMetaData().getMetaData() != null){
			for(String metaData : productInfo.getProductDetails().getMetaData().getMetaData()){
				if(metaData.equalsIgnoreCase("EQUIPMENT_REQUIRED=1")){
					receiverValidation = true;
				}
				if(metaData.contains("CATEGORY=")) {
					if (!Utils.isBlank(metaData.substring(metaData.lastIndexOf("=") + 1))) {
					hybrisCategoryName = metaData.substring(metaData.lastIndexOf("=") + 1);
					}
				}
				if(metaData.contains("ASIS_PLAN")) {
					if(orderQualVO != null) {
						if(!Utils.isBlank(orderQualVO.getProviderExternalId()) && orderQualVO.getProviderExternalId().equalsIgnoreCase("14490")) {
							frontierAsIs = true;
						}						
					}					
				}
				
			}
		}
		request.getSession().setAttribute("frontierAsIs", frontierAsIs);
		request.getSession().setAttribute("hybrisCategoryName", hybrisCategoryName);
		request.getSession().setAttribute("receiverValidation", receiverValidation);		
		
		List<FeatureType> features =  new ArrayList<FeatureType>();
		List<FeatureGroupType> featureGroup =  new ArrayList<FeatureGroupType>();
		List<ProductPromotionType> promotions  =  new ArrayList<ProductPromotionType>();
		
		if(productInfo.getProductDetails() != null){
			request.getSession().setAttribute("baseRecurringPrice", productInfo.getProduct().getPriceInfo().getBaseRecurringPrice());
			request.getSession().setAttribute("baseNonRecurringPrice", productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice());
			if(productInfo.getProductDetails().getFeature() != null){
				features =  productInfo.getProductDetails().getFeature();
			}
			if(productInfo.getProductDetails().getFeatureGroup() != null){
				featureGroup = productInfo.getProductDetails().getFeatureGroup();
			}
			if(productInfo.getProductDetails().getPromotion() != null){
				promotions = productInfo.getProductDetails().getPromotion();
			}
		}
		
		/*
		 * generate sessionVO Object from sessionID from intent
		 */
		
		SessionVO sessionVO = SessionVO.create(intent.getAsString("sessionId"));
		
		/*
		 * check whether the product is under ASIS plan or not
		 */
		boolean isASISPlan = isProductOfASISPlan(productInfo);
		
		orderQualVO.setAsisPlan(isASISPlan);
		if(CKOVo != null 
				&& CKOVo.getFeedbackMap() != null 
				   && CKOVo.getFeedbackMap().get("PriorEnrollSurge") != null){
			if(orderQualVO.getNameValue()!= null){
				orderQualVO.getNameValue().put("PriorEnrollSurge", CKOVo.getFeedbackMap().get("PriorEnrollSurge"));
			}else{
				orderQualVO.setNameValue(new HashMap<String,String>());
				orderQualVO.getNameValue().put("PriorEnrollSurge", CKOVo.getFeedbackMap().get("PriorEnrollSurge"));
			}
		}
		/*
		 * provisioning call to retrieve all the dialogue related data
		 */
		DialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByProductId(productInfo, isASISPlan, orderQualVO);
		
		/*
			-------------------------------------------------------------------------------------------------------------------------
				Start Of Logic to get the active dialogues selected during previous CKO
			-------------------------------------------------------------------------------------------------------------------------
		*/
		
		/*
		 * selects all the active dialogues that selected during the previous CKO and creating a JSONObject 
		 * from the selected dialogues and setting them to HttpSession Object as 'previouslyGivenDataId' 
		 * this json is used to display all the selected dialogues during customer Qualification page
		 */

		SelectedDialogsType previouslySelectedDialogues = null;
		JSONObject previousSelectedDialoguesJSON = null;
		if(lineItem != null && lineItem.getActiveDialogs() != null && lineItem.getActiveDialogs().getDialogs() != null && lineItem.getActiveDialogs().getDialogs().getDialog() != null){
			previouslySelectedDialogues = lineItem.getActiveDialogs();
			
			/*
			 * creating a JSONObject from the dialogues selected during previous CKO
			 */
			previousSelectedDialoguesJSON = JSONBuilder.INSTANCE.buildActiveDialoguesJSON(previouslySelectedDialogues.getDialogs().getDialog(), dialogueVO.getDialogueNameList());
			
			/*
			 * setting the previously created JSON into HTTPSession Object as 'previouslyGivenDataId' 
			 * this value is used to display all the selected values that are selected during the 
			 * previous CKO
			 */
			if(previousSelectedDialoguesJSON != null ){
				request.getSession().setAttribute("previouslyGivenDataId", previousSelectedDialoguesJSON.toString());
				request.getSession().setAttribute("isSessionpreviouslyGivenDataId", "");
			}else{
				request.getSession().setAttribute("isSessionpreviouslyGivenDataId", "true");
			}
		}else{
			request.getSession().setAttribute("isSessionpreviouslyGivenDataId", "true");
		}
		/*
			-------------------------------------------------------------------------------------------------------------------------
					End Of Logic to get the active dialogues selected during previous CKO and setting them to session
			-------------------------------------------------------------------------------------------------------------------------
		 */
		
		/*
		 * setting dialogueVO into orderQual, this dialogueVO is used rather than making a service call during the customer Qualification phase
		 */
		 
		logger.info("Finished Calling dialogueService");

		List<String> dialogueFeatureExternalIDList = new ArrayList<String>();
		
		/*
		 * iterate over the dialogueNameList and create a new List of externalID with feature type dialogue
		 */
		if(dialogueVO != null){
			dialogueFeatureExternalIDList = DialogueUtil.returnDialogueFeatures(dialogueVO);
		}
		
		logger.info("Feature List --> "+dialogueFeatureExternalIDList);
		
		logger.info("Line Item External ID --> "+orderQualVO.getLineItemExternalId());
		
		/*
			-------------------------------------------------------------------------------------------------------------------------
				Start Of Logic to remove features that are present in the dialogue response from feature list
			-------------------------------------------------------------------------------------------------------------------------
		*/
		
		/*
		 * FeatureList and FeatureGroupList after removing the features that are present dialogue 
		 */
		List<FeatureType> finalFeaturesList = new ArrayList<FeatureType>();
		List<FeatureGroupType> finalFeatureGroupList = new ArrayList<FeatureGroupType>();
		
		/*
		 * FeatureMap and FeatureGroupMap to store the features and featuregroups that are present in the dialogues 
		 */
		Map<String, FeatureType> dialogueFeatureMap = new HashMap<String, FeatureType>();
		Map<String, FeatureGroupType> dialogueFeatureGroupMap = new HashMap<String, FeatureGroupType>();
		
		/*
		 * List containing the features that are selected during the previous CKO and corresponding values seperated by '::'
		 */
		List<String> previousCKOSelFeatureList = new ArrayList<String>();
		
		logger.info("before removing features that are present in dialogues");
		finalFeaturesList.addAll(features);
		finalFeatureGroupList.addAll(featureGroup);
		logger.info("Final Features List Size BEFORE ::::: "+finalFeaturesList.size());
		removeFeatures(dialogueFeatureExternalIDList, features, finalFeaturesList, dialogueFeatureMap);
		logger.info("Final Features List Size AFTER ::::: "+finalFeaturesList.size());
		removeFeatureGroups(dialogueFeatureExternalIDList, featureGroup, finalFeatureGroupList, dialogueFeatureGroupMap);
		
		/*
		 * creating a Map with key as feature (or) featureGroup (or) promotion ExternalID and Value as List<PriceDisplayVO> 
		 */
		Map<String, List<PriceDisplayVO>> priceDisplayVOMap = PriceDisplayUtil.INSTANCE.buildPriceDetails(features, null, null);
		priceDisplayVOMap.putAll(PriceDisplayUtil.INSTANCE.buildPriceDetails(null, featureGroup, null));
		priceDisplayVOMap.putAll(PriceDisplayUtil.INSTANCE.buildPriceDetails(null, null, promotions));
		
		logger.info("Price Display VO Map :::::: "+priceDisplayVOMap.toString());
		
		generatePreviouslySelectedFeatureList(previouslySelectedFeatures, previousCKOSelFeatureList);
		/*
			-------------------------------------------------------------------------------------------------------------------------
				End Of Logic to remove features that are present in the dialogue response from feature list
			-------------------------------------------------------------------------------------------------------------------------
		 */
		
		logger.info("after removing features that are present in dialogues");
		
		/*
		 * setting base recurring price and base non recurring price to orderQualVO object,
		 * this is used when we are setting base recurring and base non recurring price to model
		 */
		orderQualVO.setBaseRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseRecurringPrice()));
		orderQualVO.setBaseNonRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice()));
		
		/*
			-------------------------------------------------------------------------------------------------------------------------
				Start Of Logic to check whether the product should be processed as a static product or as a utility product
				1. product processed as static product if there is atleast one of the following:
					a. features b. featureGroups c. promotions
				2. product is processed as utility product if no one of the above is present
			-------------------------------------------------------------------------------------------------------------------------
		*/
		
		if(!Utils.isEmpty(features) || !Utils.isEmpty(featureGroup) || !Utils.isEmpty(promotions)){
			//Preparing feature map to display feature description in product authentication screen by feature external id.
			Map<String,String> fetureMap = new HashMap<String, String>();
			if(features != null){
				for (FeatureType feature : features){
					if (feature != null && feature.getExternalId() != null && feature.getExternalId().startsWith("RTS:DISH:SSA")){
						fetureMap.put(feature.getExternalId(),feature.getDescription());	
					}else{
						fetureMap.put(feature.getExternalId(),feature.getType());
					}
				}
			}
			if(featureGroup != null){
				for (FeatureGroupType feature :featureGroup){
					if (feature != null && feature.getFeature() != null){
						for (FeatureType featuregrp : feature.getFeature()){
							fetureMap.put(featuregrp.getExternalId(),featuregrp.getType());
						}	   
					}
				}
			}
			if(promotions!=null){
				for (ProductPromotionType productPromotionType : promotions) {
					fetureMap.put(productPromotionType.getExternalId(),productPromotionType.getDescription());
				}
			}
			
			request.getSession().setAttribute("productFeatureMap",fetureMap);
			
			mav.addObject("iData", mainJson);
			mav.addObject("productMonthlyPrice", productInfo.getProduct().getPriceInfo().getBaseRecurringPrice());
			mav.addObject("productInstallationPrice", productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice());
			mav.addObject("baseRecPrice", orderQualVO.getBaseRecurringPrice());
			mav.addObject("baseNonRecPrice", orderQualVO.getBaseNonRecurringPrice());
			
			mav.addObject("parentExternalID", orderQualVO.getParentExternalId());
			mav.addObject("providerExternalID", orderQualVO.getProviderExternalId());
			mav.setViewName("product_info");
			String productDetailType =  "";
			
			if(lineItem!=null && lineItem.getLineItemDetail()!=null && lineItem.getLineItemDetail().getDetail()!=null 
					&& lineItem.getLineItemDetail().getDetail().getProductLineItem()!=null){
				productDetailType = lineItem.getLineItemDetail().getDetail().getProductLineItem().getName();
			}
			mav.addObject("productName", productDetailType);
			orderQualVO.setProductDetailType(productDetailType);
			
			orderQualVO.setProviderBaseType(productInfo.getProduct().getProvider().getSource().getValue());
			orderQualVO.setPriceDisplayVOMap(priceDisplayVOMap);
			/*
			 * check if the current status is success or not and creates a new view flow if the current status is success
			 */
			viewFlow = resolveNextView(finalFeaturesList, finalFeatureGroupList, intent, previousCKOSelFeatureList, 
					previouslySelectedFeatureGroups, mav, orderQualVO, request, promotions);
			List<String> params = new ArrayList<String>();
		    params.add("page_id="+PageConstantsEnum.PRODUCT_INFO.getId());
		    params.add(Constants.PROVIDER_ID+"="+orderQualVO.getProviderExternalId());
		    params.add(Constants.AGENT_ID+"="+orderQualVO.getAgentId());
		    params.add(Constants.PRODUCT_EXTERNALID+"="+orderQualVO.getProductExternalId());
			params.add(Constants.GUID.toLowerCase()+"="+orderQualVO.getGUID());
		    CKOVo.setParams(params);
		    
		}else{
			logger.info("Before entering OrderUtil to build utility offer and products without features dialogues");
			mav = OrderUtil.INSTANCE.buildUtilityProductDialoues(order, orderQualVO, requestParamMap, mav, CKOVo, request);
			mav.setViewName("oq_demo");
			Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.YEAR, -18);
		    String hiddenYear = (cal.get(Calendar.MONTH)+1)+"/"+(cal.get(Calendar.DATE)-1)+"/"+cal.get(Calendar.YEAR);
		    mav.addObject("hiddenYear", hiddenYear);
		    viewFlow = ViewFlow.create(mav, intent.getCurrentStatus());
		    
		    List<String> params = new ArrayList<String>();
		    params.add("page_id="+PageConstantsEnum.OQ_DEMO_CONTENTS.getId());
		    params.add(Constants.PROVIDER_ID+"="+orderQualVO.getProviderExternalId());
		    params.add(Constants.AGENT_ID+"="+orderQualVO.getAgentId());
		    params.add(Constants.PRODUCT_EXTERNALID+"="+orderQualVO.getProductExternalId());
			params.add(Constants.GUID.toLowerCase()+"="+orderQualVO.getGUID());
		    CKOVo.setParams(params);
		}
		
		logger.info("setting dialogue related features and featureGroup to orderQualVO object");
		
		/*
		 * setting dialogue related features and featureGroup to orderQualVO object
		 */
		orderQualVO.setDialogueFeatureMap(dialogueFeatureMap);
		orderQualVO.setDialogueFeatureGroup(dialogueFeatureGroupMap);
		
		logger.info("setting previously selected features to orderQualVO Object");
		
		/*
		 * setting previously selected features to orderQualVO Object
		 */
		orderQualVO.setViewDetailsSelFeatureList(previousCKOSelFeatureList);
		orderQualVO.setViewDetailsSelFeatureGroupList(previouslySelectedFeatureGroups);
		
		logger.info("setting the orderQualVO Object to session");
		/*
		 * setting the orderQualVO Object to session
		 */
		sessionVO.put(SessionKeys.orderQualVo.name(), orderQualVO);

		sessionVO.put(SessionKeys.initiator.name(), CKOVo);

		CKOCacheService.INSTANCE.store(sessionVO);

		ModelAndView modProcessedView = viewFlow.getModelAndView();

		List<String> params = new ArrayList<String>();
		params.add(Constants.CERTIFICATE+"=ABC123");
		params.add(Constants.PAGE_ID+"="+PageConstantsEnum.PRODUCT_INFO.getId());
		params.add(Constants.PROVIDER_ID+"="+orderQualVO.getProviderExternalId());
		params.add(Constants.AGENT_ID+"="+orderQualVO.getAgentId());
		params.add(Constants.PRODUCT_EXTERNALID+"="+orderQualVO.getProductExternalId());
		params.add(Constants.GUID.toLowerCase()+"="+orderQualVO.getGUID());
		CKOVo.setParams(params);
		CKOVo.setErrorCode(PageConstantsEnum.SUCCESS_ST_CODE.getId());	

		ViewUtil.processViewByStatus(modProcessedView, CKOVo, "Success", Constants.CKO_SUCCESS);		
		return viewFlow;
	}

	/**
	 * returns true if the selected product is under ASIS Plan
	 * @param productInfo
	 * @return
	 */
	private boolean isProductOfASISPlan(ProductInfoType productInfo) {
		boolean isASISPlan = false;
		if(productInfo.getProductDetails() != null && productInfo.getProductDetails().getMetaData() != null 
				&& productInfo.getProductDetails().getMetaData().getMetaData() != null){
			for(String metaData : productInfo.getProductDetails().getMetaData().getMetaData()){
				if(metaData.equalsIgnoreCase("ASIS_PLAN")){
					isASISPlan = true;
				}
			}
		}
		return isASISPlan;
	}

	/**
	 * Checks whether lineItem contains SelectedFeatures and adds them to features 
	 * and featuresGroup List
	 * @param lineItem
	 * @param features
	 * @param featureGroups
	 */
	private void returnPreviouslySelectedFeatures(LineItemType lineItem, 
			List<FeatureValueType> features, List<FeatureGroup> featureGroups) {
		if(lineItem.getSelectedFeatures() != null){
			SelectedFeaturesType selectedFeatures = lineItem.getSelectedFeatures();
			if(selectedFeatures.getFeatures() != null){
				if(selectedFeatures.getFeatures().getFeatureValue() != null){
					features.addAll(selectedFeatures.getFeatures().getFeatureValue());
				}
			}
			if(selectedFeatures.getFeatureGroup() != null){
				featureGroups.addAll(selectedFeatures.getFeatureGroup());
			}
		}
	}

	/**
	 * checks whether the current status in the intent is success or not and creates view Flow accordingly
	 * 
	 * @param features
	 * @param featureGroup
	 * @param intent
	 * @param viewDetailsSelFeaturesList
	 * @param viewDetailsSelFeatureGroupsList
	 * @param mav
	 * @param orderQualVO
	 * @param request
	 * @param promotions
	 * @param priceDisplayVOMap 
	 * 
	 * @return
	 * @throws Exception
	 */
	public ViewFlow resolveNextView(List<FeatureType> features, List<FeatureGroupType> featureGroup, Intent intent, 
			List<String> previousCKOSelFeatureList, List<FeatureGroup> previouslySelectedFeatureGroups, 
			ModelAndView mav, OrderQualVO orderQualVO, HttpServletRequest request, 
			List<ProductPromotionType> promotions)throws Exception{
		
		/*
		 * obtaining current status from intent
		 */
		ActionStatus currentStatus = intent.getCurrentStatus();

		switch (currentStatus) {
		case success:
			
			/*
			 * creates viewFlow based on the status and ModelAndView object
			 */
			ViewFlow vf = ViewFlow.create(ModelViewFactory.INSTANCE.createProductInfoView(features, featureGroup, previousCKOSelFeatureList, 
					previouslySelectedFeatureGroups, mav, orderQualVO, request, promotions), currentStatus);
			return vf;

		case fail:
			return null;

		}

		return null;
	}

	/**
	 * builds a new LineItemObject(contains item number and lineItemExternal ID) from the existing LineItem  
	 * to avoid included features being added
	 * @param lineItem
	 * @return
	 */
	private LineItemType buildNewLineItem(LineItemType lineItem) {
		
		LineItemType newLineItem = oFactory.createLineItemType();
		
		newLineItem.setLineItemNumber(lineItem.getLineItemNumber());
		newLineItem.setExternalId(lineItem.getExternalId());

		return newLineItem;
	}
	
	/**
	 * perform two functions:
	 * 1. remove the features that are present in the dialogues 
	 * 2. build a map of features that are present in the dialogue
	 * 
	 * @param dialogueFeatureExternalIDList
	 * @param featuresList
	 * @param listOfFinalFeatures 
	 * @param dialogueFeatureMap 
	 */
	private static void removeFeatures(List<String> dialogueFeatureExternalIDList, List<FeatureType> featuresList, List<FeatureType> listOfFinalFeatures, Map<String, FeatureType> dialogueFeatureMap){
		
		if(!Utils.isEmpty(dialogueFeatureExternalIDList)){
			for(FeatureType features : featuresList){
				if(Utils.listContainsString(dialogueFeatureExternalIDList, features.getExternalId())){
					listOfFinalFeatures.remove(features);
					dialogueFeatureMap.put(features.getExternalId(), features);
				}
			}
		}
	}
	
	/**
	 * perform two functions:
	 * 1. remove the featureGroups that are present in the dialogues 
	 * 2. build a map of featureGroups that are present in the dialogue
	 * @param dialogueFeatureExternalIDList
	 * @param dialogueFeatureGroupMap 
	 * @param featuresList
	 * @param listOfFinalFeatures
	 */
	private static void removeFeatureGroups(List<String> dialogueFeatureExternalIDList, List<FeatureGroupType> featureGroupList, List<FeatureGroupType> listOfFinalFeaturesGroups, Map<String, FeatureGroupType> dialogueFeatureGroupMap){
		if(!Utils.isEmpty(dialogueFeatureExternalIDList)){
			for(FeatureGroupType featureGroup : featureGroupList){
				if(Utils.listContainsString(dialogueFeatureExternalIDList, featureGroup.getExternalId())){
					listOfFinalFeaturesGroups.remove(featureGroup);
					dialogueFeatureGroupMap.put(featureGroup.getExternalId(), featureGroup);
				}
			}
		}
	}
	
	/**
	 * iterate over the previouslySelectedFeaturesList and generate a List containing 
	 * feature external ID and its Value seperated by '::'
	 * @param previouslySelectedFeatures
	 * @param previousCKOSelFeatureMap
	 */
	private void generatePreviouslySelectedFeatureList(List<FeatureValueType> previouslySelectedFeatures, List<String> previousCKOSelFeatureMap) {
		if(previouslySelectedFeatures != null){
			for(FeatureValueType featureValueType : previouslySelectedFeatures){
				if(featureValueType.getIncluded() == null){
					previousCKOSelFeatureMap.add(featureValueType.getExternalId()+"::"+featureValueType.getValue());
				}
			}
		}
	}
}