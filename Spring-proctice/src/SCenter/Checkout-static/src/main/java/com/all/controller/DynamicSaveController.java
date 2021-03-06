package com.AL.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Fieldset;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.builder.LineItemSelectionBuilder;
import com.AL.ui.common.util.PageConstantsEnum;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataFieldMatrix;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.HtmlFactory;
import com.AL.ui.factory.PrepopulateDialogue;
import com.AL.ui.factory.PromotionHandlerFactory;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.RequestUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.ViewUtil;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.DialogueVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;
import com.AL.ui.vo.StaticCKOVO;
import com.AL.util.DateUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.AccountHolderType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.RoleType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

/**
 * @author prabhu sekhar tripuraneni
 *
 */
@Controller
public class DynamicSaveController extends BaseController {

	private static final Logger logger = Logger
	.getLogger(DynamicSaveController.class);
	public static final ObjectFactory oFactory = new ObjectFactory();

	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request) throws Exception {

		/*
		 * setting the model name
		 */
		ModelAndView mav = new ModelAndView("oq_demo");	

		/*
		 * if this is the second time coming to this CKO, we have to show all the previously entered data
		 * we get the previously entered data from the session and setting it to mav, in js, we iterate over this
		 * value and default all the fields.
		 */
		if(!Utils.isBlank((String)request.getSession().getAttribute("previouslyGivenDataId"))){
			//logger.info("Customer Qualification Page entered Dialogues ACTION PAGE :::::::: "+(String) request.getSession().getAttribute("previouslyGivenDataId"));
			String prevEnteredData = (String)request.getSession().getAttribute("previouslyGivenDataId");
			mav.addObject("previouslyEnteredData",prevEnteredData);
		}
		if(request.getSession().getAttribute("mandatoryDisclosureCheckboxes") != null){
			mav.addObject("mandatoryDisclosureCheckboxes",(request.getSession().getAttribute("mandatoryDisclosureCheckboxes")).toString());
		}
		
		//saved provider data into session level for Pivoting Between Products
		if((String)request.getSession().getAttribute("isSessionpreviouslyGivenDataId") != null
				&& !Utils.isBlank((String)request.getSession().getAttribute("isSessionpreviouslyGivenDataId"))){
			Map<String,Map<String,String>> providerSessionData = (Map<String,Map<String,String>>)request.getSession().getAttribute("providerSessionData");
			String providerExternalID = (String)request.getSession().getAttribute("providerExternalID");
			if(!Utils.isBlank(providerExternalID) && providerSessionData != null && providerSessionData.get(providerExternalID) != null){
				String previouslyGivenDataId = providerSessionData.get(providerExternalID).get("previouslyGivenDataId");
				if(!Utils.isBlank(previouslyGivenDataId)){
					mav.addObject("previouslyEnteredData", previouslyGivenDataId);
				}
				logger.info("session_previouslyGivenDataId");
			}
		}
		/*
		 * getting the session object from CKO cache service
		 */
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());

		/*
		 * checking whether the session is a valid session or not.
		 * if the session is valid, normal process takes place,
		 * if the session is invalid, we redirect to session timedout page.
		 */
		if(!Utils.isValidSession(sessionVO, SessionKeys.initiator.name())){
			ModelAndView sessionTimedoutView = new ModelAndView("session_timed_out");
			return sessionTimedoutView;
		}

		/*
		 * getting CKO vo and orderQualVO objects from session
		 */
		CKOInitialVo CKOVo = (CKOInitialVo)sessionVO.get(SessionKeys.initiator.name());

		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());

		/*
		 * converting the request into map
		 */
		Map<String, String> reuestParamMap = RequestUtil.INSTANCE.dumpRequestScope(request);

		mav = buildStaticProductDialoues(request, reuestParamMap, orderQualVO, mav);

		logger.info("setting values to session object");
		sessionVO.put(SessionKeys.orderQualVo.name(), orderQualVO);

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

		ViewUtil.processViewByStatus(mav, CKOVo, "Success", Constants.CKO_SUCCESS);

		String baseRecPriceAfterCalc = request.getParameter("monthlyCostAmtFld");
		String baseNonRecPriceAfterCalc = request.getParameter("oneTimePriceFld");

		String baseRecPrice = request.getParameter("lineItemMonthlyPrice");
		String baseNonRecPrice = request.getParameter("lineItemInstallationPrice");

		String featureMonthlyPrice = request.getParameter("featureMonthlyPrice");
		String featureOneTimePrice = request.getParameter("featureOneTimePrice");

		mav.addObject("baseRecPrice", baseRecPriceAfterCalc);
		mav.addObject("baseNonRecPrice", baseNonRecPriceAfterCalc);

		mav.addObject("productMonthlyPrice", baseRecPrice);
		mav.addObject("productInstallationPrice", baseNonRecPrice);

		mav.addObject("featureMonthlyPrice", featureMonthlyPrice);
		mav.addObject("featureOneTimePrice", featureOneTimePrice);
		String  promotionSelectedFields = request.getParameter("promotionSelectedFields");
		String  featureCheckboxFields = request.getParameter("featureCheckboxFields");
		String  featureSelectBoxFields = request.getParameter("featureSelectBoxFields");
		String  featureGroupSelectedFields = request.getParameter("featureGroupSelectBoxFields");

		mav.addObject("promotionSelectedFields", promotionSelectedFields);
		mav.addObject("featureCheckboxFields", featureCheckboxFields);
		mav.addObject("featureSelectBoxFields", featureSelectBoxFields);
		mav.addObject("featureGroupSelectBoxFields", featureGroupSelectedFields);
		mav.addObject("parentExternalID", orderQualVO.getParentExternalId());
		return mav;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

	/**
	 * build customer qualification related dialogues 
	 * 
	 * @param request
	 * @param requestParamMap
	 * @param orderQualVO
	 * @param CKOVo
	 * @param order
	 * @param mav
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView buildStaticProductDialoues(HttpServletRequest request, 
			Map<String, String>requestParamMap, OrderQualVO orderQualVO, ModelAndView mav) throws Exception{

		StaticCKOVO stCkVO = new StaticCKOVO();

		List<String> errorStringList = new ArrayList<String>();

		List<String> errorLog = null;

		String jsonString = "";
		String providerExternalID = "";

		/*
		 * checking whether the order is already present in the cache, if present, we return the order
		 * if not present, we do a get order call
		 */
		OrderType order = OrderUtil.INSTANCE.returnOrderType(orderQualVO.getOrderId());

		/*
		 * checking whether the product info is already present in the cache
		 * and returning if present or making a getProductDetails if not present 
		 */
		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(orderQualVO.getProductExternalId(),orderQualVO.getGUID(), orderQualVO.getProviderExternalId());

		/*
		 * getting lineItemExternalID and providerExternalID from the orderQualVo Object
		 */
		long lineItemExternalID = orderQualVO.getLineItemExternalId();
		if(orderQualVO.getProviderExternalId() != null){
			providerExternalID = orderQualVO.getProviderExternalId();
		}else{
			providerExternalID = OrderUtil.INSTANCE.getProviderExternalId(order, lineItemExternalID);
		}

		String selectedValues = requestParamMap.get("extIDSelectedValues");

		/*
		 * getting selected and unselected promotions from the product cutomization page
		 */
		String unSelectedPromotions = requestParamMap.get("unSelectedPromotions");

		/*
		 * converting the list of features to features map
		 */
		List<FeatureType> features = productInfo.getProductDetails().getFeature(); 

		Map<String, FeatureType> availableFeatureMap = buildAvailableFeatureMap(features);

		logger.info("Available Feature Map --> "+availableFeatureMap);

		/*
		 * converting the list of featureGroups to featureGroup map
		 */
		List<FeatureGroupType> featureGroupList= productInfo.getProductDetails().getFeatureGroup();
		Map<String, FeatureGroupType> availableFeatureGroupMap = new HashMap<String, FeatureGroupType>();
		if(!Utils.isEmpty(featureGroupList)){
			availableFeatureGroupMap = buildAvailableFeatureGroupMap(featureGroupList);
		}
		logger.info("Available Feature Group Map --> "+availableFeatureGroupMap);


		/*
		 * getting all the features and feature groups that are present in the dialogues 
		 */
		Map<String, FeatureType> dialogueFeatureMap = orderQualVO.getDialogueFeatureMap();

		Map<String, FeatureGroupType> dialogueFeatureGroupMap = orderQualVO.getDialogueFeatureGroup();

		logger.info("Order ID ::::: "+orderQualVO.getOrderId());

		/*
		 * getting the features and feature groups that are entered during the previous CKO
		 */
		List<String> viewDetailsFeaturesList = orderQualVO.getViewDetailsSelFeatureList();

		List<FeatureGroup> viewDetailsFeatureGroupList = orderQualVO.getViewDetailsSelFeatureGroupList();

		logger.info("before prepopulate values ");

		/*
		 * defaulting the customer related data if the data is entered during the previous CKO
		 */
		Map<String, String> preSelectedMap = PrepopulateDialogue.INSTANCE.buildPreSelectedValues(order);
		logger.info("after prepopulate values ");

		LineItemType item = null;
		Map<String, Long> promoLineItemMap = new HashMap<String, Long>(); 

		/*
		 * retrieving all lineItems that are present on the order
		 */
		if(order.getLineItems() != null){
			int lineItemNumber = 0;
			/*
			 * iterating over all the lineItems to get current lineItem and Promotions that are present on 
			 * the order and that are not in cancelled removed status
			 */
			for(LineItemType lineItemTypes : order.getLineItems().getLineItem()){
				if(lineItemTypes.getExternalId() == orderQualVO.getLineItemExternalId()){
					item = lineItemTypes;

					/*
					 * getting the lineItemNumber of the current lineItem
					 */
					lineItemNumber = lineItemTypes.getLineItemNumber();
				}

				/*
				 * checking whether the lineItemDetail Type is of productPromotion and lineItemStatus not cancelled_removed
				 */
				if(lineItemTypes.getLineItemDetail().getDetailType().equals("productPromotion") && 
						!lineItemTypes.getLineItemStatus().getStatusCode().value().equalsIgnoreCase("CANCELLED_REMOVED")){
					OrderLineItemDetailTypeType detailType = lineItemTypes.getLineItemDetail().getDetail();
					boolean appliesToSameLine = false;

					/*
					 * appliesTo is a field that is present on every promotion and 
					 * this is same as the lineItem that the current promotion is acting
					 */
					// appliesTO same as lineItemNumber, it belongs to same lineItem
					if(lineItemNumber == lineItemTypes.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0)){
						appliesToSameLine = true;
					}

					/*
					 * adding only those promotions that are applied to current lineItem only
					 * (i.e, the appliesTo of promotion is same as current LineItem lineItemNumber)
					 */
					//if promo appliesTo current lineItem, put it in map
					if(appliesToSameLine){
						String lineItemPromoExternalID = detailType.getPromotionLineItem().getPromotion().getExternalId();

						/*
						 * this Map is used as a reference for adding or deleting the promotions that are 
						 * added or removed during the product customization page
						 */
						promoLineItemMap.put(lineItemPromoExternalID, lineItemTypes.getExternalId());
					}
				}
			}
		}

		logger.info("completed retrieving line item and promotions that are present on the order");

		String businessPartyName = null;		
		String hybrisAttributes = "";
		String productCategory = null;
		/*
		 * retrieving all the checkboxes and dropDowns selected during the product customization phase these values are
		 * used to default those checkboxes and select boxes when back button is pressed during the customer Qualification phase
		 */
		String selectedFeatureCheck = requestParamMap.get("featureCheckboxFields");
		String selectedFeatureSelect = requestParamMap.get("featureSelectBoxFields");
		mav.addObject("selectedCheckbox", selectedFeatureCheck);
		mav.addObject("selectedDropDown", selectedFeatureSelect);

		addPromotionToLineItem(item, promoLineItemMap, unSelectedPromotions, orderQualVO, request);

		/*
		 * adding all the selected features and featureGroups to LineItem, this line Item is updated along with 
		 * these features, active dialogues etc, finally while updating lineItem status
		 */

		mav.addObject("firstTimeDialogue", "firstTimeDialogue");
		logger.info("after adding features to line item");

		if(productInfo.getProduct() != null && productInfo.getProduct().getProvider() != null && productInfo.getProduct().getProvider().getName() != null){
			businessPartyName = productInfo.getProduct().getProvider().getName();
		}
		if(item.getLineItemAttributes() != null && item.getLineItemAttributes().getEntity() != null){
			for(AttributeEntityType entityType : item.getLineItemAttributes().getEntity()){
				if (Utils.isBlank(businessPartyName) && entityType.getSource().equals("PROVIDER_NAME")) {
					businessPartyName = entityType.getAttribute().get(0).getValue();
				}
				if (request.getSession().getAttribute("hybrisAttributes") != null) {
					hybrisAttributes = (String) request.getSession().getAttribute("hybrisAttributes");
				} else {
					if (entityType.getSource().equals("HYBRIS_ATTRIBUTES")) {
						hybrisAttributes = entityType.getAttribute().get(0).getValue();
						logger.info("hybrisAttributes=" + hybrisAttributes);
					}
				}
				if (entityType.getSource().equals("PRODUCT_CATEGORY")) {
					productCategory = entityType.getAttribute().get(0).getValue();
				}
			}
		}
		logger.info("productCategory="+productCategory);
		String hybrisUrl = "";
		String hybrisUsaaId = "";
		String hybrisProgramName = "";
		String agentExtId = "";
		String atgAffiliateId = "";
		String atgPhoneNumber = "";
		String hybrisCategoryName = (String) request.getSession().getAttribute("hybrisCategoryName");
		if (!Utils.isBlank(hybrisAttributes)) {
			String[] result = hybrisAttributes.split("\\|");
			hybrisUrl = (!Utils.isBlank(result[0]) ? result[0] : "");
			if(hybrisAttributes.contains(Constants.CHARTER)|| hybrisAttributes.contains(Constants.SPECTRUM)){
				atgAffiliateId = (!Utils.isBlank(result[1]) ? result[1] : "");
				atgPhoneNumber = (!Utils.isBlank(result[2]) ? result[2] : "");
			}
			else{
				hybrisUsaaId = (!Utils.isBlank(result[1]) ? result[1] : "");
				hybrisProgramName = (!Utils.isBlank(result[2]) ? result[2] : "");
				agentExtId = (!Utils.isBlank(result[3]) ? result[3] : "");
			}
		}
		String htmlHybrisUrl = "";
		if(!Utils.isBlank(hybrisUrl)) {
			logger.info("Building hybrisUrl Params for " + hybrisUrl);
			if(!Utils.isBlank(hybrisAttributes) && (hybrisAttributes.contains(Constants.CHARTER) || hybrisAttributes.contains(Constants.SPECTRUM))){
				htmlHybrisUrl = hybrisUrl + "?v="+atgAffiliateId;
				htmlHybrisUrl = htmlHybrisUrl + "&affpn=" + atgPhoneNumber;
				if (order != null) {
					if (order.getCustomerInformation() != null) {
						if (order.getCustomerInformation().getCustomer() != null) {
							if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getBestPhoneContact())) {
								htmlHybrisUrl = htmlHybrisUrl + "&phone=" + order.getCustomerInformation().getCustomer().getBestPhoneContact().replaceAll(" ", "%20");
							}
							if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getFirstName())) {
								htmlHybrisUrl = htmlHybrisUrl + "&fname=" + order.getCustomerInformation().getCustomer().getFirstName().replaceAll(" ", "%20");
							}
							if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getLastName())) {
								htmlHybrisUrl = htmlHybrisUrl + "&lname=" + order.getCustomerInformation().getCustomer().getLastName().replaceAll(" ", "%20");
							}
							if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getBestEmailContact())) {
								htmlHybrisUrl = htmlHybrisUrl + "&email=" + order.getCustomerInformation().getCustomer().getBestEmailContact().replaceAll(" ", "%20");
							}	
							//if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getAgentId())) {
							//	htmlHybrisUrl = htmlHybrisUrl + "&agentIdentifier=" + order.getCustomerInformation().getCustomer().getAgentId() + "_" + agentExtId;
							//}
						}
						if (order.getMoveDate() != null) {
							htmlHybrisUrl = htmlHybrisUrl + "&moving=" + "yes";
						}
						else{
							htmlHybrisUrl = htmlHybrisUrl + "&moving=" + "false";
						}
						if (order.getCustomerInformation().getCustomer().getAddressList() != null) {
							List<CustAddress> hybrisAddressList = order.getCustomerInformation().getCustomer().getAddressList().getCustomerAddress();
							for(CustAddress custAddr : hybrisAddressList){
								for(RoleType role : custAddr.getAddressRoles().getRole()){
									if(role.value().equals("ServiceAddress")){
										if (!Utils.isBlank(custAddr.getAddress().getStreetNumber()) &&  !Utils.isBlank(custAddr.getAddress().getStreetName())) {
											htmlHybrisUrl = htmlHybrisUrl + "&a=" + custAddr.getAddress().getStreetNumber();
											if (!Utils.isBlank(custAddr.getAddress().getPrefixDirectional())) {
												htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getPrefixDirectional();
											}
											htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getStreetName().replaceAll(" ", "%20");
											if (!Utils.isBlank(custAddr.getAddress().getStreetType())) {
												htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getStreetType();
											}
											if (!Utils.isBlank(custAddr.getAddress().getPostfixDirectional())) {
												htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getPostfixDirectional();
											}
										}									
										if (!Utils.isBlank(custAddr.getAddress().getPostalCode())) {
											if(custAddr.getAddress().getPostalCode().contains("-")) {
												htmlHybrisUrl = htmlHybrisUrl + "&z=" + custAddr.getAddress().getPostalCode().split("-")[0];
											} else {
												htmlHybrisUrl = htmlHybrisUrl + "&z=" + custAddr.getAddress().getPostalCode();
											}
										}
										if (!Utils.isBlank(custAddr.getAddress().getLine2())) {
											htmlHybrisUrl = htmlHybrisUrl + "&u=" + custAddr.getAddress().getLine2().replaceAll(" ", "%20");
										}
										if (!Utils.isBlank(custAddr.getAddress().getCity())) {
											htmlHybrisUrl = htmlHybrisUrl + "&addrCity=" + custAddr.getAddress().getCity().replaceAll(" ", "%20");
										}
										if (!Utils.isBlank(custAddr.getAddress().getStateOrProvince())) {
											htmlHybrisUrl = htmlHybrisUrl + "&addrState=" + custAddr.getAddress().getStateOrProvince().replaceAll(" ", "%20");
										}
									}
								}
							}																																				
						}
						if (!Utils.isBlank(hybrisCategoryName)) {
							htmlHybrisUrl = htmlHybrisUrl + "&offerType=" + request.getSession().getAttribute("hybrisCategoryName");
						}
						if (!Utils.isBlank(productCategory) && (productCategory.contains("VIDEO")||productCategory.contains("TRIPLE"))) {
							htmlHybrisUrl = htmlHybrisUrl + "&tvServiceFlag=" + "true";
						}
						else{
							htmlHybrisUrl = htmlHybrisUrl + "&tvServiceFlag=" + "false";
						}
						if (!Utils.isBlank(productCategory) && (productCategory.contains("PHONE")||productCategory.contains("TRIPLE"))) {
							htmlHybrisUrl = htmlHybrisUrl + "&voiceServiceFlag=" + "true";
						}
						else{
							htmlHybrisUrl = htmlHybrisUrl + "&voiceServiceFlag=" + "false";
						}
						if (!Utils.isBlank(productCategory) && (productCategory.contains("INTERNET")||productCategory.contains("TRIPLE"))) {
							htmlHybrisUrl = htmlHybrisUrl + "&dataServiceFlag=" + "true";
						}
						else{
							htmlHybrisUrl = htmlHybrisUrl + "&dataServiceFlag=" + "false";
						}
						htmlHybrisUrl = htmlHybrisUrl + "&TransID=" + String.valueOf(order.getExternalId()); 
					}
				}
			}
			else{
			htmlHybrisUrl = hybrisUrl + "?invalidateSession=true";
			if (order != null) {
				htmlHybrisUrl = htmlHybrisUrl + "&concertId=" + order.getExternalId();
				if (order.getCustomerInformation() != null) {
					if (order.getCustomerInformation().getCustomer() != null) {
						if (order.getCustomerInformation().getCustomer().getReferrerId() > 0L) {
							htmlHybrisUrl = htmlHybrisUrl + "&referrerId=" + order.getCustomerInformation().getCustomer().getReferrerId();
						}
						if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getBestPhoneContact())) {
							htmlHybrisUrl = htmlHybrisUrl + "&customerPhoneNumber=" + order.getCustomerInformation().getCustomer().getBestPhoneContact().replaceAll(" ", "%20");
						}
						if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getFirstName())) {
							htmlHybrisUrl = htmlHybrisUrl + "&firstName=" + order.getCustomerInformation().getCustomer().getFirstName().replaceAll(" ", "%20");
						}
						if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getLastName())) {
							htmlHybrisUrl = htmlHybrisUrl + "&lastName=" + order.getCustomerInformation().getCustomer().getLastName().replaceAll(" ", "%20");
						}
						if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getBestEmailContact())) {
							htmlHybrisUrl = htmlHybrisUrl + "&email=" + order.getCustomerInformation().getCustomer().getBestEmailContact().replaceAll(" ", "%20");
						}	
						if (!Utils.isBlank(order.getCustomerInformation().getCustomer().getAgentId())) {
							htmlHybrisUrl = htmlHybrisUrl + "&agentIdentifier=" + order.getCustomerInformation().getCustomer().getAgentId() + "_" + agentExtId;
						}
					}
				
					if (order.getCustomerInformation().getCustomer().getAddressList() != null) {
						List<CustAddress> hybrisAddressList = order.getCustomerInformation().getCustomer().getAddressList().getCustomerAddress();
						for(CustAddress custAddr : hybrisAddressList){
							for(RoleType role : custAddr.getAddressRoles().getRole()){
								if(role.value().equals("ServiceAddress")){
									if (!Utils.isBlank(custAddr.getAddress().getStreetNumber()) &&  !Utils.isBlank(custAddr.getAddress().getStreetName())) {
										htmlHybrisUrl = htmlHybrisUrl + "&addrStreet=" + custAddr.getAddress().getStreetNumber();
										if (!Utils.isBlank(custAddr.getAddress().getPrefixDirectional())) {
											htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getPrefixDirectional();
										}
										htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getStreetName().replaceAll(" ", "%20");
										if (!Utils.isBlank(custAddr.getAddress().getStreetType())) {
											htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getStreetType();
										}
										if (!Utils.isBlank(custAddr.getAddress().getPostfixDirectional())) {
											htmlHybrisUrl = htmlHybrisUrl + "%20" + custAddr.getAddress().getPostfixDirectional();
										}
									}									
									if (!Utils.isBlank(custAddr.getAddress().getPostalCode())) {
										if(custAddr.getAddress().getPostalCode().contains("-")) {
											htmlHybrisUrl = htmlHybrisUrl + "&addrZip5=" + custAddr.getAddress().getPostalCode().split("-")[0];
										} else {
											htmlHybrisUrl = htmlHybrisUrl + "&addrZip5=" + custAddr.getAddress().getPostalCode();
										}
									}
									if (!Utils.isBlank(custAddr.getAddress().getLine2())) {
										htmlHybrisUrl = htmlHybrisUrl + "&addrApt=" + custAddr.getAddress().getLine2().replaceAll(" ", "%20");
									}
									if (!Utils.isBlank(custAddr.getAddress().getCity())) {
										htmlHybrisUrl = htmlHybrisUrl + "&addrCity=" + custAddr.getAddress().getCity().replaceAll(" ", "%20");
									}
									if (!Utils.isBlank(custAddr.getAddress().getStateOrProvince())) {
										htmlHybrisUrl = htmlHybrisUrl + "&addrState=" + custAddr.getAddress().getStateOrProvince().replaceAll(" ", "%20");
									}
								}
							}
						}																																				
					}
					if (item != null) {
						if (item.getExternalId() > 0L) {
							htmlHybrisUrl = htmlHybrisUrl + "&concertLineItemID=" + item.getExternalId();
						}
					}
					if (!Utils.isBlank(hybrisCategoryName)) {
						htmlHybrisUrl = htmlHybrisUrl + "&redirectCategoryName=" + request.getSession().getAttribute("hybrisCategoryName");
					}
					if (!Utils.isBlank(hybrisUsaaId) && !hybrisUsaaId.equalsIgnoreCase("none")) {
						htmlHybrisUrl = htmlHybrisUrl + "&memberId=" + hybrisUsaaId.replaceAll(" ", "%20");
					}
					if (!Utils.isBlank(hybrisProgramName) && !hybrisProgramName.equalsIgnoreCase("none")) {
						htmlHybrisUrl = htmlHybrisUrl + "&programName=" + hybrisProgramName.replaceAll(" ", "%20");
					}					
				}

			}
		}
			logger.info("Completed Building hybrisUrl Params =" + htmlHybrisUrl);
			stCkVO.setValueByName("hybrisUrl", htmlHybrisUrl);
			stCkVO.setValueByName("referrer.program.url", hybrisUrl);
		} else {
			logger.info("Completed Building hybrisUrl Params not completed");
			stCkVO.setValueByName("referrer.program.url", "No URL for ordersource");			
		}


		/*
		 * setting lineItem to orderQualVO Object, this lineItem is used to get all the 
		 * features that are selected during product customization
		 */

		//orderQualVO.setNewLineItemType(LineItemBuilder.INSTANCE.getLineItemBySelectedFeatures(requestParamMap, availableFeatureMap, availableFeatureGroupMap, orderQualVO.getNewLineItemType(), null));

		LineItemSelectionsType lineItemSelections = orderQualVO.getLineItemSelections();

		if((lineItemSelections == null) || (lineItemSelections.getSelection().size() == 0)) {
			lineItemSelections = LineItemSelectionBuilder.getLineItemSelections(requestParamMap, productInfo.getProductDetails().getCustomization());
			orderQualVO.setLineItemSelections(lineItemSelections);
		}

		List<String> allDataFieldList = new ArrayList<String>();

		/*
		 * checks whether the provisioning response is already present in the cache service, if it is present, then the response is returned
		 * if the response is not present, then a provisioning call is made to retrieves all the dialogue related data
		 */
		DialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByProductId(productInfo, orderQualVO.isAsisPlan(), orderQualVO);

		StringBuilder events = new StringBuilder();

		List<DataField> dataFields = new ArrayList<DataField>();
		List<DataGroup> dataGroupList = new ArrayList<DataGroup>();

		/*
		 *	iterate over dialogueNamesList(List<Dialogue>) and create a list of DataGroups, dataFields, these lists are used to build dialogues display
		 */
		for (Dialogue dialogue : dialogueVO.getDialogueNameList()) {

			String externalId = dialogue.getExternalId();
			dataGroupList.addAll(dialogue.getDataGroupList());
			Map<String, List<DataField>> dataFieldMap = dialogueVO.getDataFieldMap().get(externalId);

			for(Map.Entry<String, List<DataField>> fieldEntry : dataFieldMap.entrySet()) {
				dataFields.addAll(fieldEntry.getValue());
			}
		}
		logger.info("DF External ID ::::: "+dataFields);
		logger.info("setting all values into orderqualVO object");

		orderQualVO.setAvailableDataField(dataFields);

		orderQualVO.setAvailFeatureMap(availableFeatureMap);

		orderQualVO.setAvailableFeatureGroup(availableFeatureGroupMap);

		List<DataFieldMatrix> dataFieldMatrixs = new ArrayList<DataFieldMatrix>();

		/*
			--------------------------------------------------------------------------------------------------------------------
				Start Of logic to build enableDependencyMap and disableDependencyMap, these maps are used to hide and display 
				elements which are depended on the selected element
			--------------------------------------------------------------------------------------------------------------------
		 */


		/*
		 * enable dependency map contains all the elements that are to be shown when an element is selected
		 */
		Map<String, Map<String, List<String>>> enableDependencies = new HashMap<String, Map<String, List<String>>> (); 

		enableDependencies.putAll(DialogueUtil.buildDataFieldMatrices(dataGroupList, availableFeatureGroupMap));

		/*
		 * disable dependency map contains all the elements that are to be hidden when an element is selected
		 */
		Map<String, Map<String, List<String>>> disableDependencies = new HashMap<String, Map<String, List<String>>>();
		disableDependencies.putAll(DialogueUtil.getDisableDialogueDependencies(dataGroupList, enableDependencies, availableFeatureMap, availableFeatureGroupMap));

		/*
			--------------------------------------------------------------------------------------------------------------------
								End Of logic to build enableDependencyMap and disableDependencyMap
			--------------------------------------------------------------------------------------------------------------------
		 */

		logger.info("adding all dependency elements to data field matrix map");
		for (Dialogue dialogue : dialogueVO.getDialogueNameList()) {
			String externalId = dialogue.getExternalId();
			Map<String, List<DataFieldMatrix>> dataFieldMatrix = dialogueVO.getDataFieldMatrixMap().get(externalId);
			for(Map.Entry<String, List<DataFieldMatrix>> fieldEntry : dataFieldMatrix.entrySet()) {
				dataFieldMatrixs.addAll(fieldEntry.getValue());
			}
		}
		logger.info("after setting all the values to data field matrix map");
		List<String> replayAllEnabledValuesList = new ArrayList<String>();

		/*
		 */
		if(selectedValues != null && selectedValues.length() > 0){
			String[] selectedValuesArray = selectedValues.split(",");
			for(String selectVal : selectedValuesArray){
				replayAllEnabledValuesList.addAll(OrderUtil.INSTANCE.generateReplayChildvalues(enableDependencies, selectVal));
			}
		}

		orderQualVO.setEnableDependencyMap(enableDependencies);	

		/*
		 * creating a List<String> allDataList by adding all the externalID's that are present in this provisioning call
		 * this list is used to check all the dataFields when we are displaying the dependent fields in js 
		 */
		for(Entry<String, Map<String, List<String>>> enableDependenciesEntry : enableDependencies.entrySet()){
			for(Entry<String, List<String>> enableDependenciesList : enableDependenciesEntry.getValue().entrySet()){
				for(String dependedEle : enableDependenciesList.getValue()){
					allDataFieldList.add(dependedEle);
				}
			}
		}

		/*
		 * checking if the dialogue contains 'electricService.startDate' or 'businessParty.name', if the dialogue contains them,
		 * we need to replace them with their corresponding values that are present on the order.
		 */
		Customer customer = order.getCustomerInformation().getCustomer();
		List<CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
		for(CustAddress custAddr : custAddressList){
			for(RoleType role : custAddr.getAddressRoles().getRole()){
				if(role.value().equals("ServiceAddress")){
					String electricStartDate = DateUtil.toTimeString(custAddr.getAddress().getElectricityStartAt());
					stCkVO.setValueByName("electricService.startDate", electricStartDate);
				}
			}
		}
		stCkVO.setValueByName("businessParty.name", businessPartyName);
		stCkVO.setValueByName("consumer.name.nameBlock", customer.getFirstName()+" "+ customer.getLastName());
		stCkVO.setValueByName("referrer-general-name", customer.getReferrerGeneralName());
		/*
			--------------------------------------------------------------------------------------------------------------------
				Start Of logic to build display of the dialogues that are to be shown during the customer qualification page
			--------------------------------------------------------------------------------------------------------------------
		 */

		logger.info("before sending to html factory");
		List<Fieldset> fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dialogueVO, enableDependencies, dialogueFeatureMap, 
				dialogueFeatureGroupMap, viewDetailsFeaturesList, viewDetailsFeatureGroupList, preSelectedMap, 
				request.getContextPath(), requestParamMap, stCkVO, orderQualVO.getPriceDisplayVOMap(),orderQualVO.getDominionProductExtIds(),request.getSession().getAttribute("mandatoryDisclosureCheckboxes"));

		logger.info("after html factory");
		for (Fieldset fieldset : fieldsetList) {

			String element = HtmlBuilder.INSTANCE.toString(fieldset);
			element = element.replaceAll("&lt;", "<");
			element = element.replaceAll("&gt;", ">");
			events.append(element);
			logger.info("====================================");
		}
		orderQualVO.setDataField(events.toString());
		logger.info("setting values to mav obkect");
		if(errorLog != null && errorLog.size() > 0){
			mav.addObject("errorLog", errorLog);
		}
		if(!errorStringList.isEmpty()){
			mav.addObject("errors",errorStringList);
		}
		if(jsonString != null && jsonString.trim().length() >0){
			mav.addObject("iData", jsonString);
		}

		StringBuilder customerName = new StringBuilder();

		if(order.getAccountHolder() != null && !order.getAccountHolder().isEmpty() && orderQualVO.getLineItemExternalId() > 0 )
		{
			LineItemType lineItemType = LineItemService.INSTANCE.getLineItem(order.getAgentId(),order,orderQualVO.getLineItemExternalId(),null);
			if (lineItemType != null && lineItemType.getAccountHolderExternalId() != null)
			{
				for(AccountHolderType accountHolder : order.getAccountHolder())
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
			customerName.append(order.getCustomerInformation().getCustomer().getFirstName());
			customerName.append(" ");
			customerName.append(order.getCustomerInformation().getCustomer().getLastName());
		}

		mav.addObject("customerName",customerName.toString());


		mav.addObject("dataField", escapeSpecialCharacters(events.toString()));
		mav.addObject("productInfo", productInfo);

		mav.addObject("allDataFieldList", allDataFieldList);
		mav.addObject("enableDialogueMap", enableDependencies);
		mav.addObject("disableDialogueMap", disableDependencies);
		mav.addObject("replayAllEnabledValuesList", replayAllEnabledValuesList);
		mav.addObject("providerExternalID",providerExternalID);
		mav.addObject("isUtilityOffer", false);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -18);
		String hiddenYear = (cal.get(Calendar.MONTH)+1)+"/"+(cal.get(Calendar.DATE)-1)+"/"+cal.get(Calendar.YEAR);
		mav.addObject("hiddenYear", hiddenYear);

		logger.info("after setting values to MAV object");
		return mav;
	}

	private static void addPromotionToLineItem(LineItemType item, Map<String, Long> promoLineItemMap, 
			String unSelectedPromotions, OrderQualVO orderQualVO, HttpServletRequest request) {

		String selectedFeatureJSON = request.getParameter("selectedFeaturesJSONHiddenValue");
		logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
		JSONArray jArray = null;
		if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
			try {
				jArray = new JSONArray(selectedFeatureJSON);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			logger.info("JSON ARRAY LENGTH ::::: "+jArray.length());
		}

		Set<String> lineItemPromoExternalIDs = promoLineItemMap.keySet();

		for(String promoExternalID : lineItemPromoExternalIDs){
			if(unSelectedPromotions.indexOf(promoExternalID) >= 0){
				List<Integer> reasons = new ArrayList<Integer>(); 
				reasons.add(Constants.REASON_CODE);
				SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
				List<String> lineItemIdList = new ArrayList<String>();
				lineItemIdList.add(promoLineItemMap.get(promoExternalID).toString());
				/*
				 * deleting the promotion from the order(i.e, changing the status to cancelled_removed)
				 */
				LineItemService.INSTANCE.updateLineItemStatus(orderQualVO.getAgentId(), orderQualVO.getOrderId(), lineItemIdList, 
						LineItemStatusCodesType.CANCELLED_REMOVED.value(), LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, context);
			}
		}

		if(jArray != null){

			for(int i = 0; i < jArray.length(); i++){
				JSONObject jObject;
				try {
					jObject = (JSONObject) jArray.get(i);

					String customization = (String)jObject.get("type");
					if(customization.equalsIgnoreCase("promotion")){
						String externalID = jObject.getString("featureExternalID");
						if(!lineItemPromoExternalIDs.contains(externalID)){
							String promotionJSON = request.getParameter(externalID+"_hiddenPromo");
							LineItemCollectionType liCollection = PromotionHandlerFactory.INSTANCE.updatePromotions(promotionJSON, request, 
									orderQualVO.getOrderId(), item);
							SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());

							LineItemService.INSTANCE.addLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), liCollection, context, false);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String escapeSpecialCharacters(String str){
		if(str!=null){

			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");

			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");

			//this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			//this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");

			//this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			//this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			//this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			//this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");

			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");


		}
		return str;
	}

	/**
	 * converts the list of features to map containing feature external id and feature
	 * @param features
	 * @return
	 */
	private static Map<String, FeatureType> buildAvailableFeatureMap(List<FeatureType> features){
		Map<String, FeatureType> featureMap = new HashMap<String, FeatureType>();
		for(FeatureType feature : features){
			featureMap.put(feature.getExternalId(), feature);
		}
		return featureMap;
	}

	/**
	 * converts the list of feature groups to map containing featureGroupExternalID and featureGroup
	 * @param featureGroups
	 * @return
	 */
	private static Map<String, FeatureGroupType> buildAvailableFeatureGroupMap(List<FeatureGroupType> featureGroups){
		Map<String, FeatureGroupType> featureMap = new HashMap<String, FeatureGroupType>();
		for(FeatureGroupType feature : featureGroups){
			featureMap.put(feature.getExternalId(), feature);
		}
		return featureMap;
	}

	@RequestMapping(value = "/storedSessionData")
	public @ResponseBody String storedSessionData(HttpServletRequest req){
		String  previouslyGivenDataId = req.getParameter("previouslyGivenDataId");
		String  prdouctPageselFetuJSONVal = req.getParameter("selectedFeaturesJSONHiddenValue");
		String  oqDemoFetJSONVal = req.getParameter("selectedDataFeaildFeaturesJSONHiddenValue");
		//logger.info("selectedFeaturesJSONHiddenValue="+prdouctPageselFetuJSONVal);
		//logger.info("previouslyGivenDataId="+previouslyGivenDataId);
		//logger.info("selectedDataFeaildFeaturesJSONHiddenValue="+oqDemoFetJSONVal);
		HttpSession session = req.getSession(true);
		if (session != null){
			Map<String,Map<String,String>> providerSessionData = (Map<String,Map<String,String>>)session.getAttribute("providerSessionData");
			String providerExternalID = (String)req.getSession().getAttribute("providerExternalID");
			if(providerSessionData != null && providerSessionData.get(providerExternalID) != null){
				if(!Utils.isBlank(previouslyGivenDataId)){
					providerSessionData.get(providerExternalID).put("previouslyGivenDataId", previouslyGivenDataId);	
				}
				if(!Utils.isBlank(prdouctPageselFetuJSONVal)){
					providerSessionData.get(providerExternalID).put("selectedFeaturesJSONHiddenValue", prdouctPageselFetuJSONVal);	
				}
				if(!Utils.isBlank(oqDemoFetJSONVal)){
					providerSessionData.get(providerExternalID).put("selectedDataFeaildFeaturesJSONHiddenValue", oqDemoFetJSONVal);	
				}
			}
		}
		return "success";
	}
}