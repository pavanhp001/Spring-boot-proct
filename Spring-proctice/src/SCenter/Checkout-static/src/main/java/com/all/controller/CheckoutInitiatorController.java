package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.DIALOGECACHETIME;
import static com.AL.ui.util.ConfigProperties.PROVIDER_LOGO_LOCATION;
import static com.AL.ui.util.ConfigProperties.DOMINION_URL;
import static com.AL.ui.util.ConfigProperties.DOMINION_PRODUCT_EXTID;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.builder.JSONBuilder;
import com.AL.ui.client.ActionStoreInitialRequest;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.xml.pr.v4.EnergyPriceInfoType.Rate;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.service.V.impl.DialogCacheService;
import com.AL.ui.service.V.impl.OrderCacheService;
import com.AL.ui.service.workflow.Intent;
import com.AL.ui.service.workflow.ViewFlow;
import com.AL.ui.service.workflow.intent.IntentInitial;
import com.AL.ui.service.workflow.stat.StaticIntentSteps;
import com.AL.ui.util.JsonUtil;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.LinkableType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.ProviderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.ServiceType;


@Controller
public class CKOInitiatorController extends BaseController {


	private static final String CKO_INPUT = "CKOInput";

	private static final Logger logger = Logger.getLogger(CKOInitiatorController.class);

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/CKO")
	public ModelAndView initiate(final HttpServletRequest request) throws Exception {
		if(!Utils.isBlank(DIALOGECACHETIME)){
			logger.info("dialogeCacheTime="+DIALOGECACHETIME);
			DialogCacheService.INSTANCE.setTimeInCache(Long.valueOf(DIALOGECACHETIME));
		}

		String orderIdInSession = (String)request.getSession().getAttribute("orderID");
		String CKOInput = request.getParameter("CKOInput");
		logger.info("CKOInput=" + CKOInput);
		JSONObject mainJson = new JSONObject(request.getParameter("CKOInput"));
		JSONObject CKO = mainJson.getJSONObject("CKO");
		String orderIDFromCKO = CKO.getString("orderId");
		// check "action" key exists in the JSONObject
		if(CKO.has("action")){
			String action = CKO.getString("action");
			request.setAttribute("action", action );
		}
		Map<String,Map<String,String>> providerSessionData = (Map<String,Map<String,String>>)request.getSession().getAttribute("providerSessionData");
		if(orderIdInSession != null && !orderIDFromCKO.equals(orderIdInSession)){
			if(OrderCacheService.INSTANCE.get(orderIdInSession)!=null){
				OrderCacheService.INSTANCE.clear(orderIdInSession);
			}
			if(CKOCacheService.INSTANCE.get(request.getSession().getId())!=null){
				CKOCacheService.INSTANCE.clear(request.getSession().getId());
			}
			request.getSession().invalidate();
			MDC.clear();
			logger.info("Session_has_been_cleared");
		}
		final Intent intent = IntentInitial.INSTANCE.process(StaticIntentSteps.init, request);

		final OrderQualVO orderQualObj = JSONBuilder.INSTANCE.addValuesToOrderQualObject(intent.getAsString(CKO_INPUT), request);
		//saved provider data into session level for Pivoting Between Products
		if(providerSessionData != null && providerSessionData.get(orderQualObj.getProviderExternalId()) == null){
			providerSessionData.put(orderQualObj.getProviderExternalId(), new HashMap<String,String>());
		}else if(providerSessionData == null){
			providerSessionData = new HashMap<String,Map<String,String>>();
			providerSessionData.put(orderQualObj.getProviderExternalId(), new HashMap<String,String>());
		}
		request.getSession().setAttribute("providerSessionData",providerSessionData);
		logger.info("CKOInput_from_Intent"+intent.getAsString(CKO_INPUT));
		saveValuesToSession(orderQualObj, request, intent);
		final ViewFlow viewFlow = ActionStoreInitialRequest.INSTANCE.execute(intent, request, orderQualObj);
		return viewFlow.getModelAndView();
	}

	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest arg0, final HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("index");
		return mav;
	}

	/**
	 * Retrieves providerExternalID, lineItemExternalID, orderID, CKOInput, lineItemNumber, 
	 * product_base_recurring_price, product_base_nonrecurring_price from OrderQualVo object and save
	 * them into Session
	 * 
	 * @param orderQualVO
	 * @param request
	 * @param intent
	 * @return HttpServletRequest
	 */
	private void saveValuesToSession(final OrderQualVO orderQualVO, HttpServletRequest request, final Intent intent) throws Exception {

		if(request.getSession().getAttribute("previouslyGivenDataId")!=null){
			request.getSession().removeAttribute("previouslyGivenDataId");
		}
		logger.info("providersImageLocation : "+PROVIDER_LOGO_LOCATION);
		if(!Utils.isBlank(PROVIDER_LOGO_LOCATION)){
			request.getSession().setAttribute("providersImageLocation",PROVIDER_LOGO_LOCATION);
		}
		
		logger.info("dominionUrl : "+DOMINION_URL);
		if(!Utils.isBlank(DOMINION_URL)){
			request.getSession().setAttribute("dominionUrl",DOMINION_URL);
		}
		
		logger.info("dominionProductExtId :: "+DOMINION_PRODUCT_EXTID);
		if(!Utils.isBlank(DOMINION_PRODUCT_EXTID)){
			request.getSession().setAttribute("dominionProductExtId",DOMINION_PRODUCT_EXTID);
		}
		
		OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderQualVO.getOrderId(), Boolean.TRUE);

		OrderType order = null;

		String guid = null;
		if(orderManagementRequestResponse.getResponse().getSalesContext() != null){
			SalesContextFactory.INSTANCE.setContextInSession(request.getSession(), orderManagementRequestResponse.getResponse().getSalesContext());
			guid = OrderUtil.INSTANCE.getGUID(orderManagementRequestResponse.getResponse().getSalesContext());
		}
		if(orderManagementRequestResponse.getResponse().getOrderInfo() != null){
			if ((orderManagementRequestResponse.getResponse().getOrderInfo() != null) && (orderManagementRequestResponse.getResponse().getOrderInfo().size() > 0)) {
				order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
			}
		}
		//the order object set into the request object is used throughout the CKO process
		request.setAttribute("orderType", order);
		final String productExternalId = orderQualVO.getProductExternalId();
		final String providerExternalID = orderQualVO.getProviderExternalId();
		String action = (String)request.getAttribute("action");
		logger.info("productExternalId="+productExternalId+"_providerExternalID="+providerExternalID);
		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(productExternalId, guid, providerExternalID);
		logger.info("productInfo="+productInfo);
		request.setAttribute("productInfo", productInfo);
		if(productInfo.getProduct().getPriceInfo()!= null 
				&& productInfo.getProduct().getPriceInfo().getEnergyPriceInfo()!= null
				 && productInfo.getProduct().getPriceInfo().getEnergyPriceInfo().getRate() != null
				  && !productInfo.getProduct().getPriceInfo().getEnergyPriceInfo().getRate().isEmpty()){
			Map<String, Double> energyTier = new HashMap<String, Double>(); 
			for(Rate rate:productInfo.getProduct().getPriceInfo().getEnergyPriceInfo().getRate()){
				energyTier.put(rate.getTier(), rate.getValue());
			}
			if(energyTier.get(Constants.ENERGY_RATE) != null){
				request.getSession().setAttribute(SessionKeys.usageRate.name(), energyTier.get(Constants.ENERGY_RATE));
			}else{
				for(Entry<String,Double> map : energyTier.entrySet()){
					request.getSession().setAttribute(SessionKeys.usageRate.name(), map.getValue());
					break;
				}
			}
		}
		if(productInfo.getProduct().getPriceInfo().getEnergyPriceInfo() != null 
				&& productInfo.getProduct().getPriceInfo().getEnergyPriceInfo().getEnergyUnit() != null){
			request.getSession().setAttribute(SessionKeys.energyRateUnit.name(),productInfo.getProduct().getPriceInfo().getEnergyPriceInfo().getEnergyUnit().value());
		}
		if(productInfo.getProduct().getProvider() != null && productInfo.getProduct().getProvider().getParent() != null 
				&& productInfo.getProduct().getProvider().getParent().getExternalId() != null){
			orderQualVO.setParentExternalId(productInfo.getProduct().getProvider().getParent().getExternalId());
		}
		if( order != null && "UtilityOffer".equalsIgnoreCase(action)){
			LineItemType utilityOfferlineItem = CKOLineItemFactory.INSTANCE.getUtilityOfferLineItem(order,orderQualVO.getProductExternalId());
			if(utilityOfferlineItem != null){
				logger.info("Utility_Offer_LineItem_ExternalId="+utilityOfferlineItem.getExternalId());
				orderQualVO.setLineItemExternalId(utilityOfferlineItem.getExternalId());
				orderQualVO.setHasUtitliOfferLineitem("Yes");
			}else{
				com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
				com.AL.xml.v4.CustAddress cust = CKOLineItemFactory.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
				String billingInfo = String.valueOf(order.getCustomerInformation().getCustomer()
						.getBillingInfoList().getBillingInfo().get(0).getExternalId());
				String svcAddressExtId = String.valueOf(cust.getAddress().getExternalId());
				final JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
				String CKOInput = intent.getAsString("CKOInput");
				CKOInitialVo CKOVo = util.convert(CKOInput, "CKO", CKOInitialVo.class);
				LineItemType lineItemType = buildLineItem(oFactory,productInfo,billingInfo,svcAddressExtId,CKOVo);
				LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
				liCollection.getLineItem().add(lineItemType);
				SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
				//order = LineItemService.INSTANCE.addLineItem(order.getAgentId(), String.valueOf(order.getExternalId()), liCollection, context, true);
				//lineItemType = CKOLineItemFactory.INSTANCE.getUtilityOfferLineItem(order,orderQualVO.getProductExternalId());
				//orderQualVO.setLineItemExternalId(lineItemType.getExternalId());
				//orderQualVO.setLineItemNumber(lineItemType.getLineItemNumber());
				//orderQualVO.setLineItemType(lineItemType);
				//orderQualVO.setNewLineItemType(lineItemType);
				//orderQualVO.setHasUtitliOfferLineitem("No");
				//request.setAttribute("orderType", order);
			}
		}
		String agentId = order.getAgentId();
		MDC.put("agentId", agentId);
		MDC.put("orderId", orderQualVO.getOrderId());
		MDC.put("GUID", guid != null ? guid : "" );

		orderQualVO.setCustomerExternalId(String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()));

		orderQualVO.setAgentId(order.getAgentId());
		orderQualVO.setGUID(guid);

		for(LineItemType liType : order.getLineItems().getLineItem()){
			if(liType.getExternalId() == orderQualVO.getLineItemExternalId()){
				request.getSession().setAttribute("lineItemNumber", liType.getLineItemNumber());
				request.getSession().setAttribute("product_base_recurring_price",liType.getLineItemPriceInfo().getBaseRecurringPrice());
				request.getSession().setAttribute("product_base_nonrecurring_price",liType.getLineItemPriceInfo().getBaseNonRecurringPrice());
				orderQualVO.setProviderExternalId(liType.getPartnerExternalId());
			}
		}
		logger.info("orderQualVO_ProviderExternalId"+orderQualVO.getProviderExternalId());
		request.getSession().setAttribute("isReceiverMatch", Utils.isMetaDataHasReceiverMatchTrueOrNot(productInfo));
		request.getSession().setAttribute("guid", guid);
		request.getSession().setAttribute("providerExternalID", orderQualVO.getProviderExternalId());
		request.getSession().setAttribute("lineItemExternalID", orderQualVO.getLineItemExternalId());
		request.getSession().setAttribute("orderID", orderQualVO.getOrderId());
		request.getSession().setAttribute("CKOInput", intent.getAsString(CKO_INPUT));
		request.getSession().setAttribute("product_name",productInfo.getProduct().getName());

	}

	/**
	 * build LineItem type UtilityOffer
	 * 
	 * @param oFactory
	 * @param productInfo
	 * @param billingInfo
	 * @param svcAddressExtId
	 * @param CKOVo
	 * @return LineItemType
	 */
	private LineItemType buildLineItem(com.AL.xml.v4.ObjectFactory oFactory, ProductInfoType productInfo, String billingInfo, String svcAddressExtId,
			CKOInitialVo CKOVo) {
		logger.info("Start_buildLineItem_for_UtilityOfferlineItem");
		String partnerExternalId = productInfo.getProduct().getProvider().getExternalId();
		String partnerName = productInfo.getProduct().getProvider().getName();
		String productName = productInfo.getProduct().getName();
		String productExternalId  = productInfo.getProduct().getExternalId();
		String providerSourceBaseType = productInfo.getProduct().getProvider().getSource().getValue().value();
		logger.info("providerSourceBaseType"+providerSourceBaseType);
		Double baseNonRecurringPrice = productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice();
		Double baseRecurringPrice = productInfo.getProduct().getPriceInfo().getBaseRecurringPrice();
		Long productUniqueId = System.nanoTime();

		LineItemType lineItemType= oFactory.createLineItemType();

		lineItemType.setPartnerName(partnerName);
		lineItemType.setLineItemNumber(0);
		lineItemType.setExternalId(0L);
		lineItemType.setBillingInfoExtId(billingInfo);
		lineItemType.setSvcAddressExtId(svcAddressExtId);

		lineItemType.setService(ServiceType.BUSINESS);

		ProviderSourceType prvdrSrcType = oFactory.createProviderSourceType();
		prvdrSrcType.setValue(CKOLineItemFactory.INSTANCE.getProviderSourceBaseType(providerSourceBaseType));
		lineItemType.setProductDatasource(prvdrSrcType);

		LineItemDetailType lineItemDetailType = new LineItemDetailType();
		lineItemDetailType.setDetailType("product");
		lineItemDetailType.setProductUniqueId(productUniqueId);

		LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
		lineItemAttributeType.getEntity().add(CKOLineItemFactory.INSTANCE.setAttributeEntityType("TYPE", "UtilityOffer",
				"", "PRODUCT_TYPE"));
		lineItemAttributeType.getEntity().add(CKOLineItemFactory.INSTANCE.setAttributeEntityType("Display", "false",
				"", "provider_feedback"));
		lineItemAttributeType.getEntity().add(CKOLineItemFactory.INSTANCE.setAttributeEntityType("STATUS", Constants.CKO_READY,
				"CKO IS READY FOR CKO PROCESSING", "CKO"));
		lineItemAttributeType.getEntity().add(CKOLineItemFactory.INSTANCE.setAttributeEntityType("name", partnerName,
				"", "PROVIDER_NAME"));
		lineItemAttributeType.getEntity().add(CKOLineItemFactory.INSTANCE.setAttributeEntityType("name", productName,
				"", "PRODUCT_NAME"));		

		Map<String,String> partnerMap = CKOVo.getFeedbackMap();
		logger.info("partnerMap="+partnerMap);

		if(partnerMap!= null && partnerMap.size() > 0 && partnerMap.get("PriorEnrollSurge") == null){
			//PatnerSpecific Data add to on UtilityOffer LineItem as a lineItemAttributeType
			lineItemAttributeType.getEntity().add(CKOLineItemFactory.INSTANCE.setAttributeEntityType(partnerMap,"cust_ref_rate"));
		}
		// set event type to Closing Renters
		if (!Utils.isBlank(partnerExternalId) 
				&& Constants.CLOSING_RENTERS_PROVIDER_ID.equals(partnerExternalId) 
				&& productInfo.getProductDetails().getMetaData() != null 
				&& productInfo.getProductDetails().getMetaData().getMetaData() != null 
				&& !productInfo.getProductDetails().getMetaData().getMetaData().isEmpty()){
			for(String metaData:productInfo.getProductDetails().getMetaData().getMetaData()){
				if(Constants.OFFERTYPE_CLOSINGRENTERS.equalsIgnoreCase(metaData)){
					lineItemType.setEventType(2);
				}
			}
		}
		lineItemType.setLineItemAttributes(lineItemAttributeType);
		OrderLineItemDetailTypeType orderLineItemDetailTypeType = new OrderLineItemDetailTypeType();
		LinkableType productType = new LinkableType();
		productType.setExternalId(productExternalId);
		productType.setName(productName);
		ProviderType providerType = new ProviderType();
		providerType.setExternalId(partnerExternalId);
		providerType.setName(partnerName);
		productType.setProvider(providerType);
		orderLineItemDetailTypeType.setProductLineItem(productType);
		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);
		LineItemPriceInfoType liPriceInfo = oFactory.createLineItemPriceInfoType();
		liPriceInfo.setBaseNonRecurringPrice(baseNonRecurringPrice);
		liPriceInfo.setBaseRecurringPrice(baseRecurringPrice);
		lineItemType.setLineItemPriceInfo(liPriceInfo);
		logger.info("End_buildLineItem_for_UtilityOfferlineItem");
		return lineItemType;
	}

}
