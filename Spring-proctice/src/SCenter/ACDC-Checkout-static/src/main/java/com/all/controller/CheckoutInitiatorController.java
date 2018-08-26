package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.DIALOGECACHETIME;
import static com.AL.ui.util.ConfigProperties.PROVIDER_LOGO_LOCATION;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AL.service.DigitalCKOInitiatorService;
import com.AL.ui.ProductVO;
import com.AL.ui.builder.JSONBuilder;
import com.AL.ui.client.ActionStoreInitialRequest;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.exception.InvalidTokenException;
import com.AL.ui.factory.CKOLineItemFactory;
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
import com.AL.ui.vo.SessionVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemStatusCodesType;
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
  
	 /*@Value("${dialogeCacheTime}")
	 private String dialogeCacheTime;
	 @Value("${provider_logo_location}")
	 private String providersImageLocation;*/
	@Autowired
	private DigitalCKOInitiatorService digitalCKOInitiatorService;

	private static final Logger logger = Logger.getLogger(CKOInitiatorController.class);


	@RequestMapping(value = "/redirectCKO", method = RequestMethod.GET)
	public ModelAndView handleGet(@ModelAttribute("mavProductInfoDemo") ModelAndView modelAndView, HttpServletRequest request) {
		logger.info("redirectSuccessful");
		modelAndView.setViewName("acdc_product_info");
		return modelAndView;
	}
	
	@RequestMapping(value = "/redirectQualification", method = RequestMethod.GET)
	public ModelAndView handleQualificationContent(@ModelAttribute("mavProductInfoDemo") ModelAndView modelAndView, HttpServletRequest request) {
		logger.info("redirectSuccessful");
		modelAndView.setViewName("acdc_oq_demo");
		return modelAndView;
	}
	@RequestMapping(value = "/redirectapplianceQualification", method = RequestMethod.GET)
	public ModelAndView handleApplianceQualificationContent(@ModelAttribute("mavProductInfoDemo") ModelAndView modelAndView, HttpServletRequest request) {
		logger.info("redirectapplianceQualification");
		modelAndView.setViewName("acdc_utility_view");
		return modelAndView;
	}

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/CKO")
	public String initiate(final HttpServletRequest request,final HttpServletResponse response, final RedirectAttributes redirectAttrs) throws Exception {
		if(!Utils.isBlank(DIALOGECACHETIME)){
			logger.info("dialogeCacheTime="+DIALOGECACHETIME);
			DialogCacheService.INSTANCE.setTimeInCache(Long.valueOf(DIALOGECACHETIME));
		}
		request.getSession().setAttribute("isACDC", false);
		String orderIdInSession = (String)request.getSession().getAttribute("orderID");
		final Intent intent = IntentInitial.INSTANCE.process(StaticIntentSteps.init, request);
		String CKOInput = intent.getAsString(Constants.CKO_INPUT);
		String channelType = null;
		String phoneNo = null;
		String channelCss = null;
		String guid = null;
		String dtmUrl = null;
		String applianceFlag=null;
		logger.info("CKOInput=" + CKOInput);
		JSONObject mainJson = new JSONObject(CKOInput);
		JSONObject CKO = mainJson.getJSONObject("CKO");
		if (CKOInput != null && !CKOInput.isEmpty()) {
			request.getSession().setAttribute(Constants.CKO_INPUT, CKOInput);
			JSONObject params = CKO.getJSONObject("params");
			JSONArray stringJsonArray = params.getJSONArray("string");
			for (int i = 1; i < stringJsonArray.length(); i++) {
				String typeObject = (String) stringJsonArray.get(i);
				logger.info("typeObject="+typeObject);
				if (typeObject.indexOf(Constants.CHANNEL_TYPE) == 0 
						|| typeObject.indexOf(Constants.PHONE_NO) == 0 
						|| typeObject.indexOf(Constants.CHANNEL_CSS) == 0 
						|| typeObject.indexOf(Constants.GUID.toLowerCase()) == 0 || typeObject.indexOf(Constants.DTMURL) == 0 || typeObject.indexOf(Constants.APPLIANCE_FLAG) == 0) {
					String[] typeStringArray = typeObject.split("=");
					if (Constants.CHANNEL_TYPE.equalsIgnoreCase(typeStringArray[0])) {
						channelType =  typeStringArray[1];
					} else if(Constants.CHANNEL_CSS.equalsIgnoreCase(typeStringArray[0])) {
						channelCss =  typeStringArray[1];
					} else if(Constants.PHONE_NO.equalsIgnoreCase(typeStringArray[0])) {
						phoneNo =  typeStringArray[1];
					}else if(Constants.GUID.equalsIgnoreCase(typeStringArray[0])) {
						guid = typeStringArray[1];
					}else if(Constants.DTMURL.equalsIgnoreCase(typeStringArray[0])) {
						dtmUrl = typeStringArray[1];
					}
					else if(Constants.APPLIANCE_FLAG.equalsIgnoreCase(typeStringArray[0])) {
						applianceFlag = typeStringArray[1];
					}
				}
			}
			logger.info("dtm:"+dtmUrl);
			request.getSession().setAttribute(Constants.CHANNEL_TYPE, channelType);
			request.getSession().setAttribute(Constants.CHANNEL_CSS, channelCss);
			request.getSession().setAttribute(Constants.PHONE_NO, phoneNo);
			request.getSession().setAttribute(Constants.DTMURL, dtmUrl);
			request.getSession().setAttribute(Constants.APPLIANCE_FLAG,applianceFlag );
			
		}
		final JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
		CKOInitialVo CKOVo = util.convert(CKOInput, "CKO", CKOInitialVo.class);
		String orderIDFromCKO = CKO.getString("orderId");

		// check "action" key exists in the JSONObject
		if(CKO.has("action")){
			String action = CKO.getString("action");
			request.setAttribute("action", action );
		}
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
		final OrderQualVO orderQualObj = JSONBuilder.INSTANCE.addValuesToOrderQualObject(intent.getAsString(Constants.CKO_INPUT), request);
		orderQualObj.setGUID(guid);
		MDC.put("orderId", orderQualObj.getOrderId());
		MDC.put("GUID", orderQualObj.getGUID());
		request.getSession().setAttribute("providerExternalID", orderQualObj.getProviderExternalId());
		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getProduct(orderQualObj.getProductExternalId(), orderQualObj.getGUID(), orderQualObj.getProviderExternalId());
		request.getSession().setAttribute("product_name",productInfo.getProduct().getName());
		request.getSession().setAttribute("provider_name",productInfo.getProduct().getProvider().getName());
		//set values in orderQualVO
		orderQualObj.setBaseRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseRecurringPrice()));
		orderQualObj.setBaseNonRecurringPrice(String.valueOf(productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice()));
		request.getSession().setAttribute("product_base_recurring_price",orderQualObj.getBaseRecurringPrice());
		logger.info("CKOInput_from_Intent"+intent.getAsString(Constants.CKO_INPUT));
		String redirectToPage="redirect:/static/redirectCKO";
		if(CKOVo!= null && CKOVo.getParams().contains(Constants.DIGTIAL_KEY)){
			ViewFlow viewFlow = null;
			SessionVO sessionVO = SessionVO.create(intent.getAsString("sessionId"));
			//ProductVO  productVO = digitalCKOInitiatorService.getCacheVOObjects(orderQualObj);
			//if(productVO == null){
			viewFlow = digitalCKOInitiatorService.digitalCKOInitiatService(orderQualObj, request, intent, CKOVo,sessionVO,productInfo);
			/*}else{
				ModelAndView mav = new ModelAndView("product_Json");
				digitalCKOInitiatorService.updateOrderQualVO(request,orderQualObj);
				mav.addObject("productVO", Utils.convertJSONString(productVO));
				viewFlow = ViewFlow.create(mav, null);
			}*/
			sessionVO.put(SessionKeys.initiator.name(),CKOVo);
			sessionVO.put(SessionKeys.orderQualVo.name(), orderQualObj);
			sessionVO.getData().put(Constants.APPLIANCE_FLAG, applianceFlag);
			CKOInput = intent.getAsString("CKOInput");
			viewFlow.getModelAndView().addObject("iData", mainJson);
			redirectAttrs.addFlashAttribute("mavProductInfoDemo", viewFlow.getModelAndView());
			logger.info("map"+viewFlow.getModelAndView().getModel().toString());
			logger.info("customerInformation flag"+viewFlow.getModelAndView().getModel().containsKey("customerInformation"));
			if(viewFlow.getModelAndView().getModel().containsKey("customerInformation")){ 
				redirectToPage= "redirect:/static/redirectQualification";
				if((applianceFlag!=null) && (applianceFlag.equalsIgnoreCase("true"))){
					redirectToPage= "redirect:/static/redirectapplianceQualification";
				}
			}
		}else{
			saveValuesToSession(orderQualObj, request, intent, CKOVo, productInfo);
			final ViewFlow viewFlow = ActionStoreInitialRequest.INSTANCE.execute(intent, request, orderQualObj, false);
			redirectAttrs.addFlashAttribute("mavProductInfoDemo", viewFlow.getModelAndView());
		}
		return redirectToPage;
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
	private void saveValuesToSession(final OrderQualVO orderQualVO, HttpServletRequest request, final Intent intent, final CKOInitialVo CKOVo, ProductInfoType productInfo) throws Exception {

		boolean isContainsPurOrder = Boolean.FALSE;
		if(request.getSession().getAttribute("previouslyGivenDataId")!=null){
			request.getSession().removeAttribute("previouslyGivenDataId");
		}
		 logger.info("providersImageLocation : "+PROVIDER_LOGO_LOCATION);
		   if(!Utils.isBlank(PROVIDER_LOGO_LOCATION)){
			request.getSession().setAttribute("providersImageLocation",PROVIDER_LOGO_LOCATION);
		          } 		           
		final JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
		String CKOInput = intent.getAsString(Constants.CKO_INPUT);
		List<String> paramValues =  CKOVo.getParams();
		String crfToken = null;
		for(String paramVal:paramValues){
			String paramArray[] = paramVal.split("=");
			if("csrfToken".equalsIgnoreCase(paramArray[0]))
			{
				crfToken =paramArray[1];
			}
		}
		if(Utils.isBlank(crfToken)) {
			crfToken = CKOCacheService.INSTANCE.getToken(request.getSession().getId());
		} else {
			CKOCacheService.INSTANCE.clearCSFToken(request.getSession().getId());
			CKOCacheService.INSTANCE.storeCSFToken(crfToken, request.getSession().getId());
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
		if(order.getLineItems().getLineItem()!= null && order.getLineItems().getLineItem().size() > 0){
			for(LineItemType liType : order.getLineItems().getLineItem()){
				if(liType.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.PROVISION_READY)) {
					isContainsPurOrder = Boolean.TRUE;
				}
				if(liType.getExternalId() == orderQualVO.getLineItemExternalId()){
					for(AttributeEntityType attrEntity : liType.getLineItemAttributes().getEntity()){
						if("CKO".equalsIgnoreCase(attrEntity.getSource())){
							for(AttributeDetailType attrType : attrEntity.getAttribute()){
								if("crf_token".equalsIgnoreCase(attrType.getName())){
									if(Utils.isBlank(crfToken) || !crfToken.equalsIgnoreCase(attrType.getValue())){
										throw new InvalidTokenException("The page you are trying to view cannot be shown because of the security issues");
									}
								}
							}
						}
					}
				}
			}
		}

		//the order object set into the request object is used throughout the CKO process
		request.setAttribute("orderType", order);

		final String productExternalId = orderQualVO.getProductExternalId();
		final String providerExternalID = orderQualVO.getProviderExternalId();
		String action = (String)request.getAttribute("action");
		logger.info("productExternalId="+productExternalId+"_providerExternalID="+providerExternalID);

		logger.info("productInfo="+productInfo);

		request.setAttribute("productInfo", productInfo);

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


				LineItemType lineItemType = buildLineItem(oFactory,productInfo,billingInfo,svcAddressExtId,CKOVo);

				LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
				liCollection.getLineItem().add(lineItemType);
				SalesContextType context = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());
				LineItemService.INSTANCE.asyncAddLineItem(order.getAgentId(), String.valueOf(order.getExternalId()), liCollection, context, true);
				orderQualVO.setNewLineItemType(lineItemType);
				orderQualVO.setHasUtitliOfferLineitem("No");
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

		request.getSession().setAttribute("guid", guid);
		request.getSession().setAttribute("providerExternalID", orderQualVO.getProviderExternalId());
		request.getSession().setAttribute("lineItemExternalID", orderQualVO.getLineItemExternalId());
		request.getSession().setAttribute("orderID", orderQualVO.getOrderId());
		request.getSession().setAttribute(Constants.CKO_INPUT, intent.getAsString(Constants.CKO_INPUT));
		request.getSession().setAttribute("product_name",productInfo.getProduct().getName());
		request.getSession().setAttribute("isContainsPurOrder", isContainsPurOrder);
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

		if(partnerMap!= null && partnerMap.size()>0){
			//PatnerSpecific Data add to on UtilityOffer LineItem as a lineItemAttributeType
			lineItemAttributeType.getEntity().add(CKOLineItemFactory.INSTANCE.setAttributeEntityType(partnerMap,"cust_ref_rate"));
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
