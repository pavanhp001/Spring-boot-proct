package com.AL.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import edu.emory.mathcs.backport.java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.service.ClosingOfferService;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.transport.TransportConfig;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.Attributes;
import com.AL.xml.cm.v4.CustomerAttributeEntityType;
import com.AL.xml.cm.v4.CustomerAttributeType;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.EMailAddressType;
import com.AL.xml.cm.v4.NotificationEventCollectionType;
import com.AL.xml.cm.v4.NotificationEventType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.LinkableType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.ProviderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.ServiceType;


@Controller("OfferController")
public class OfferController {
	private static final Logger logger = Logger.getLogger(OfferController.class);

	@Autowired
	ClosingOfferService closingOfferService;

	/**
	 * Displaying the savers offer.
	 * 
	 * @param request
	 * @throws UnRecoverableException
	 */
	public void showSaversOffer(RequestContext request) throws UnRecoverableException {
		HttpServletRequest httpRequest = (HttpServletRequest) request
		.getExternalContext().getNativeRequest();
		long startTimer=0;
		StopWatch timer = new StopWatch();
		timer.start();
		try {
			logger.info("showOffer_begin_in_OfferController");
			HttpSession session = httpRequest.getSession();
			
			SalesCenterVO salesCenterVo = (SalesCenterVO)session.getAttribute("salescontext");
			OrderType order = (OrderType)session.getAttribute("order");
			
			boolean isFromRecommendation = false;
			if( session.getAttribute("isFromRecommendation")!=null 
				&& String.valueOf(session.getAttribute("isFromRecommendation")).equalsIgnoreCase("yes") )
			{
				isFromRecommendation = true;
			}

			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "OpeningOffer");
			dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
			
			startTimer=timer.getTime();
			List<ProductSummaryVO> details = new ArrayList<ProductSummaryVO>();
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			HashMap<String, List<ProductSummaryVO>> saversMap = (HashMap<String, List<ProductSummaryVO>>) productResultManager.getSaversOfferMap();
			for (Entry<String, List<ProductSummaryVO>> entry : saversMap.entrySet()) {
				details.addAll(entry.getValue());
			}
			if(!Utils.isBlank(ProductResultsManager.getSiftFileVersion())){
				httpRequest.getSession().setAttribute("siftFileVersion", ProductResultsManager.getSiftFileVersion());
			}
			String offers = "";
			if (details != null) {
				offers = getOffersData(details, session);
			}
			logger.info("saversOffers=" + offers.toString());
			if (salesCenterVo.getValueByName("drupalContentUrl") != null 
					&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
				String dialoguesFromDrupal = SalesUtil.getDialoguesFormDrupalContent(contextMap,salesCenterVo);
				if (Utils.isBlank(dialoguesFromDrupal)){
					generateDialoguesFromService(contextMap,salesCenterVo,request,offers);
				}else{
					if (dialoguesFromDrupal != null && dialoguesFromDrupal.contains("includes:")) {
						dialoguesFromDrupal = dialoguesFromDrupal.replace("includes:", "includes: " + offers);
					}
					request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));	
					request.getFlowScope().put("dialogue" , dialoguesFromDrupal);	
				}
			}else{
				generateDialoguesFromService(contextMap,salesCenterVo,request,offers);
			}

			Long orderId = order.getExternalId();
			if (details != null && orderId  != null) {
				request.getFlowScope().put("detailsOfferJson", createOfferJSON(getOffersProductList(details), orderId)
								.toString());
			}
			boolean isUtilityOfferExist = false;
			if( !isFromRecommendation )
			{
				if(session.getAttribute("isUtilityOfferExist")!= null){
				    isUtilityOfferExist = (Boolean) session.getAttribute("isUtilityOfferExist");
				}
				if(! isUtilityOfferExist){
					boolean isConfirmReferrerForUtility = false;
					if(session.getAttribute("isConfirmReferrerForUtility")!= null){
				        isConfirmReferrerForUtility = (Boolean) session.getAttribute("isConfirmReferrerForUtility");
					}
				    isUtilityOfferExist = SalesUtilsFactory.INSTANCE.confirmUtilityOffer(salesCenterVo, productResultManager, httpRequest, isConfirmReferrerForUtility);
				}
				/*if(dynamicFlow != null 
					&& dynamicFlow.get("dynamicFlow.flowType") != null 
						&& dynamicFlow.get("dynamicFlow.flowType").contains("consumersInteractions")){
					isUtilityOfferExist = false;
				}*/
		        session.setAttribute("isUtilityOfferExist" , isUtilityOfferExist);
			}
			com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(), com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
			request.getFlowScope().put("isUtilityOfferExist", isUtilityOfferExist);
			request.getFlowScope().put("address", getAddress(custAdr));
			request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
			if (Utils.isBlank(order.getCustomerInformation().getCustomer().getBestEmailContact())) {
				request.getFlowScope().put("isEmailEmpty", "true");
			} else {
				request.getFlowScope().put("isEmailEmpty", "false");
			}
			if(dynamicFlow != null 
					&& dynamicFlow.get("dynamicFlow.flowType") != null 
					     && (dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice"))){
				List<ProductSummaryVO>	closingOfferList  = closingOfferService.buildClosingOfferList(order, productResultManager, httpRequest);
				if(closingOfferList != null && !closingOfferList.isEmpty()){
					request.getFlowScope().put("closingOfferFlag" , "Next Page ClosingOffer"+" - "+ closingOfferList.get(0).getName());
					httpRequest.getSession().setAttribute("closingOfferName" ,closingOfferList.get(0).getName());
					httpRequest.getSession().setAttribute("closingOfferPoints" ,closingOfferList.get(0).getPoints());
				}else{
					request.getFlowScope().put("closingOfferFlag" , null);
				}
			}else{
					List<ProductSummaryVO>	closingOfferList  = closingOfferService.buildClosingOfferList(order, productResultManager, httpRequest);
					if(closingOfferList != null && !closingOfferList.isEmpty()){
						httpRequest.getSession().setAttribute("closingOfferName" ,closingOfferList.get(0).getName());
						httpRequest.getSession().setAttribute("closingOfferPoints" ,closingOfferList.get(0).getPoints());
					}else{
						request.getFlowScope().put("closingOfferFlag" , null);
					}
			}
			logger.info("timeTakenForShowSaversOffer="+timer.getTime());
			logger.info("showOffer_end_in_OfferController");
		} catch (Exception e) {
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error("error_in_OfferController",e);
			throw new UnRecoverableException(e.getMessage());
		}
		finally{
			timer.stop();
		}

	}

	/**
	 * Adding Savers offer as line item to the order
	 * 
	 * @param requestContext
	 * @return Event
	 * @throws BaseException
	 */
	public Event saveSaversOfferOnOrder(RequestContext requestContext) throws BaseException {
		StopWatch timer = new StopWatch();
		long startTimer=0;
		timer.start();
		try {
			logger.info("saveSaversOfferOnOrder_begin_OfferController");
			HttpServletRequest request = (HttpServletRequest) requestContext.getExternalContext().getNativeRequest();
			boolean isSaversLineItemCreated = false;
			HttpSession session = request.getSession();

			String submit = request.getParameter("Opening_Offer_NonRR_2");
			String email = request.getParameter("email");

			logger.info("is_saversoffer_accepted=" + submit);
			logger.info("email=" + email);
			if( requestContext.getConversationScope().get("isSaversLineItemCreated")!=null )
			{
				isSaversLineItemCreated = true;
			}

			logger.info("isSaversLineItemCreated="+isSaversLineItemCreated);

			if (!Utils.isBlank(submit) && submit.equalsIgnoreCase("NO"))
			{
				session.setAttribute("displaySaversButton",true);
			}else {
				session.setAttribute("displaySaversButton",false);
				requestContext.getConversationScope().put("isSaversLineItemCreated",true);
			}

			ErrorList errorList = new ErrorList();
			boolean isSimpleChoiceFlow = false;
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");

			//Simplechoice savers offer sync vs async call fix
			if(dynamicFlow.get("dynamicFlow.flowType").toLowerCase().contains("simplechoice"))
			{
				isSimpleChoiceFlow = true;
			}
			logger.info("isSimpleChoiceFlow="+isSimpleChoiceFlow);
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");

			if( session.getAttribute("isFromRecommendation")!=null )
			{
				session.removeAttribute("isFromRecommendation");
				requestContext.getFlowScope().put("event", "recommendationsEvent");
			}

			if( !isSaversLineItemCreated )
			{
				Long customerID = (Long) request.getSession().getAttribute("customerID");
				String agentId = salesCenterVo.getValueByName("Agent");
				OrderType order2 = (OrderType) request.getSession().getAttribute("order");

				com.AL.xml.cm.v4.ObjectFactory oFactory1 = new com.AL.xml.cm.v4.ObjectFactory();
				CustomerType customer = oFactory1.createCustomerType();
				customer.setExternalId(customerID);

				if (order2.getCustomerInformation().getCustomer().getBestPhoneContact() != null) {
					customer.setBestPhoneContact(order2.getCustomerInformation().getCustomer().getBestPhoneContact());
				}

				EMailAddressType emailObj = new EMailAddressType();
				boolean isEmailAddressChanged = false;
				if (!Utils.isBlank(email))
				{
					String dtCreated = salesCenterVo.getValueByName("dtCreated");
					if( !Utils.isBlank( dtCreated ) )
					{
						isEmailAddressChanged = WarmTransferFactory.INSTANCE.isEmailAddressUpdated(email, salesCenterVo);
					}
					customer.setBestEmailContact(email);
					String hemail = "";
					if (!Utils.isBlank(email)) {
						hemail = email;
						emailObj.setValue(hemail);
						customer.setHomeEMail(emailObj);
					}
				} else {
					customer.setBestEmailContact(order2.getCustomerInformation().getCustomer().getBestEmailContact());
					if (!Utils.isBlank(order2.getCustomerInformation().getCustomer().getBestEmailContact())) {
						email = order2.getCustomerInformation().getCustomer().getBestEmailContact();
						emailObj.setValue(email);
						customer.setHomeEMail(emailObj);
					}

				}
				customer.setEMailOptIn(order2.getCustomerInformation().getCustomer().isEMailOptIn());
				if ((!Utils.isBlank(submit)) && (submit.equalsIgnoreCase("Yes"))) {
					customer.setMarketingOptIn(true);
				} else {
					customer.setMarketingOptIn(false);
				}
				if (session.getAttribute("primaryLanguage") != null) {
					customer.setPrimaryLanguage(Integer.valueOf(session.getAttribute("primaryLanguage").toString()));
				}

				if(request.getSession().getAttribute("saversProductExtIds")!=null){
					String productExtIds = (String) request.getSession().getAttribute("saversProductExtIds");
					CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
					Attributes attributes = new com.AL.xml.cm.v4.ObjectFactory().createAttributes();
					List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
					customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("SAVERS", productExtIds, "", "SALESCENTER"));
					attributes.getAttribute().add(customerAttributeType);
					customer.setAttributes(attributes);
				}

				String GUID = (String) request.getSession().getAttribute("GUID");
				Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
				Map<String, String> sourceEntity = new HashMap<String, String>();
				sourceEntity.put("source", "salescenter");
				sourceEntity.put("GUID", GUID);
				data.put("source", sourceEntity);
				CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
				if(isEmailAddressChanged  && dynamicFlow.get("dynamicFlow.flowType")!= null 
						&& !dynamicFlow.get("dynamicFlow.flowType").contains("customerLookup")){
					NotificationEventType notifEventType = oFactory1.createNotificationEventType();
					notifEventType.getReason().add(50);
					notifEventType.setCode(100);
					NotificationEventCollectionType notifEventColl = oFactory1.createNotificationEventCollectionType();
					DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
					GregorianCalendar gc = new GregorianCalendar();
					XMLGregorianCalendar dateTime = datatypeFactory.newXMLGregorianCalendar(gc);
					notifEventType.setDateTimeStamp(dateTime);
					notifEventType.setComment("Customer Info Updated");
					notifEventColl.getEvent().add(notifEventType);
					startTimer=timer.getTime();
					if(isSimpleChoiceFlow){
						customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order2.getCustomerInformation().getCustomer().getExternalId()), "updateCustomer", customer, notifEventColl,customerContext, errorList);
						logger.info("TimeTakenforCustomerServicecall="+(timer.getTime()-startTimer));
					}else{
						CustomerService.INSTANCE.asyncSubmitCustomerType(agentId, String.valueOf(order2.getCustomerInformation().getCustomer().getExternalId()), "updateCustomer", customer, notifEventColl,customerContext,GUID);
						logger.info("TimeTakenforCustomerServicecall="+(timer.getTime()-startTimer));
						//Updating the order in session for changed Email
						order2.getCustomerInformation().getCustomer().setBestEmailContact(email);
						order2.getCustomerInformation().getCustomer().getHomeEMail().setValue(email);	
					}
				}
				else if(customer.isMarketingOptIn()){
					startTimer=timer.getTime();
					if(isSimpleChoiceFlow){
						customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order2.getCustomerInformation().getCustomer().getExternalId()),"updateCustomer", customer, null, customerContext, errorList);
						logger.info("TimeTakenforCustomerServicecall="+(timer.getTime()-startTimer));
					}else{
						CustomerService.INSTANCE.asyncSubmitCustomerType(agentId, String.valueOf(order2.getCustomerInformation().getCustomer().getExternalId()),"updateCustomer", customer, null, customerContext,GUID);
						logger.info("TimeTakenforCustomerServicecall="+(timer.getTime()-startTimer));
						//Updating the order in session for changed Email
						order2.getCustomerInformation().getCustomer().setBestEmailContact(email);
						order2.getCustomerInformation().getCustomer().getHomeEMail().setValue(email);
					}
				}
				else{
					startTimer=timer.getTime();
					if(isSimpleChoiceFlow){
						customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order2.getCustomerInformation().getCustomer().getExternalId()),"updateCustomer", customer, null, customerContext, errorList);
						logger.info("TimeTakenforCustomerServicecall="+(timer.getTime()-startTimer));
					}else{
						CustomerService.INSTANCE.asyncSubmitCustomerType(agentId, String.valueOf(order2.getCustomerInformation().getCustomer().getExternalId()),"updateCustomer", customer, null, customerContext,GUID);
						logger.info("TimeTakenforCustomerServicecall="+(timer.getTime()-startTimer));
					}
				}

				if (errorList != null && errorList.size() > 0) {
					for (CartError cartError : errorList) {
						logger.info("UpdateCustomer_Error_Code=" + cartError.getCode());
						logger.info("UpdateCustomer_Error_Message="	+ cartError.getMessage());
						logger.info("UpdateCustomer_Error_Description="	+ cartError.getDescription());
					}
					throw new UnRecoverableException(errorList.get(0).getMessage());
				}

				logger.info("customer_submitted_successfully");

				order2.getCustomerInformation().getCustomer().setMarketingOptIn(customer.isMarketingOptIn());

				if (!Utils.isBlank(submit)) {
					request.getSession().setAttribute("submitvalue", submit);
				}
				String offerJson = request.getParameter("offerJson");
				logger.info("offerJson=" + offerJson);
				logger.info("is_offer_accepted=" + submit);

				if (submit != null && submit.equalsIgnoreCase("Yes")) {
					String productData = offerJson;
					com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
					SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
					String orderExtId = null;
					OrderType order =  null;
					if (!productData.equals("")) {
						JSONObject index = new JSONObject(productData);
						JSONArray selectedArray = index.getJSONArray("index");

						for (int i = 0; i < selectedArray.length(); i++) {
							LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
							String jsonString = selectedArray.getString(i);
							JSONObject feedback = new JSONObject(jsonString);
							String productExernalId = feedback.getString("productExernalId");
							String partnerExternalId = feedback.getString("partnerExternalId");
							String providerName = feedback.getString("providerName");
							String productname = feedback.getString("productname");
							orderExtId = feedback.getString("orderId");
							String providerSourceBaseType = feedback.getString("providerSourceBaseType");

							LineItemType lineItemType = oFactory.createLineItemType();
							lineItemType.setPartnerName(providerName);
							lineItemType.setLineItemNumber(0);
							lineItemType.setExternalId(0L);
							lineItemType.setService(ServiceType.BUSINESS);
							if (order2 != null) {
								com.AL.xml.v4.CustAddress cust = SalesUtil.INSTANCE.getAddress(order2.getCustomerInformation().getCustomer(), com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
								String billingInfo = String.valueOf(order2.getCustomerInformation().getCustomer().getBillingInfoList().getBillingInfo().get(0).getExternalId());
								String svcAddressExtId = String.valueOf(cust.getAddress().getExternalId());
								lineItemType.setBillingInfoExtId(billingInfo);
								lineItemType.setSvcAddressExtId(svcAddressExtId);
							}

							LineItemAttributeType lineItemAttributeType = oFactory.createLineItemAttributeType();

							lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("TYPE", "SaversOffer", "", "PRODUCT_TYPE"));
							lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("Display", "false", "", "provider_feedback"));
							lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("STATUS",	"CKOComplete",	"CKO COMPLETED", "CKO"));
							lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("name", providerName, "", "PROVIDER_NAME"));
							lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("name", productname, "", "PRODUCT_NAME"));

							lineItemType.setLineItemAttributes(lineItemAttributeType);

							LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
							lineItemDetailType.setDetailType("product");
							lineItemDetailType.setProductUniqueId(null);

							ProviderSourceType pstValue = oFactory.createProviderSourceType();
							pstValue.setDatasource(providerName);
							pstValue.setValue(CartLineItemFactory.INSTANCE.getProviderSourceBaseType(providerSourceBaseType));
							lineItemType.setProductDatasource(pstValue);

							OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
							LinkableType productType = oFactory.createLinkableType();
							productType.setExternalId(productExernalId);
							productType.setName(productname);
							ProviderType providerType = oFactory.createProviderType();
							providerType.setExternalId(partnerExternalId);
							providerType.setName(providerName);
							productType.setProvider(providerType);
							orderLineItemDetailTypeType.setProductLineItem(productType);
							lineItemDetailType.setDetail(orderLineItemDetailTypeType);
							lineItemType.setLineItemDetail(lineItemDetailType);

							liCollection.getLineItem().add(lineItemType);
							startTimer=timer.getTime();
							order = LineItemService.INSTANCE.addLineItem(agentId, orderExtId, liCollection, salesContext);
							logger.info("TimeTakenforLineItemServicecall="+(timer.getTime()-startTimer));
						}
						/*OrderType order =  null;

						 * Getting SalesContext from the session



							LineItemType offerLineItem = LineItemUtil.getLineItemBasedOnProductType(order2,"SaversOffer");
							logger.info("offerLineItem="+offerLineItem);
							if(offerLineItem == null){

						 * ServiceCall for adding LineItem to Order

								startTimer=timer.getTime();
								order = LineItemService.INSTANCE.addLineItem(agentId, orderExtId, liCollection, salesContext);
								logger.info("TimeTakenforLineItemServicecall="+(timer.getTime()-startTimer));
							}*/
						if (order != null) {
							List<String> listOflineItemIds = LineItemUtil.containsSaversOffers(order);
							if (!listOflineItemIds.isEmpty()) {
								order = LineItemService.INSTANCE.submitMultipleLineItem(agentId, orderExtId, listOflineItemIds, salesContext);
							} else {
								logger.info("line_item_added_savers_error_for= "+orderExtId+"_order");
							}
							logger.info("line_item_added_savers_successfully_for="+orderExtId+"_order");
						}
						if(order!= null){
							session.setAttribute("order",order);								
						}
					}

				}
			}

			logger.info("timeTakenForSaveSaversOffer="+timer.getTime());
			logger.info("eventId="+requestContext.getFlowScope().getString("event"));		
			if(requestContext.getFlowScope().get("event")!=null){
				return new Event(this,requestContext.getFlowScope().getString("event"));
			}
			else
			{
				return new Event(this,"closeCallNoSaleEvent");
			}

		} catch (Exception e) {

			logger.error("error_while_adding_savers_offer_as_line_item_in_OfferController",e);
			throw new UnRecoverableException(e.toString());
		}
		finally{
			timer.stop();
		}
	}
	
	public JSONObject createOfferJSON(List<ProductSummaryVO> details,
			Long orderId) throws Exception {

		JSONObject mainJson = new JSONObject();

		try {
//			JSONObject prodJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			for (ProductSummaryVO detailsVO : details) {
				JSONObject prodJson = new JSONObject();

				prodJson.put("orderId", orderId);
				prodJson.put("partnerExternalId",
						escapeSpecialCharacters(detailsVO
								.getProviderExternalId()));
				prodJson.put("productExernalId",
						escapeSpecialCharacters(detailsVO.getExternalId()));
				prodJson.put("productname", escapeSpecialCharacters(detailsVO
						.getName()));
				prodJson.put("isPromotion", false);
				prodJson.put("appliesTo", 0L);
				prodJson.put("providerName", escapeSpecialCharacters(detailsVO
						.getProviderName()));
				prodJson.put("providerSourceBaseType", detailsVO.getSource());
				jsonArray.put(prodJson);
			}

			mainJson.put("index", jsonArray);
			logger.info("OfferJSON=" + mainJson.toString());

		} catch (JSONException e) {
			logger.warn("Error_in_OfferController",e);
			throw new Exception("JSON Exception: " + e.getMessage());
		}
		return mainJson;
	}

	public String escapeSpecialCharacters(String str) {
		if (str != null) {
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");

			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			// this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			// this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");

			// this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			// this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");

			// this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			// this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			// this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			// this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");

			// this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			// this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			// this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			// this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
		}
		return str;
	}

	private String getOffersData(List<ProductSummaryVO> details, HttpSession session) {
		List<String> suppressSaversCustAttrProdExtIdsList  = Utils.isBlank(ConfigRepo.getString("*.suppress_savers_cust_attr_prod_ext_ids")) ? null : Arrays.asList(ConfigRepo.getString("*.suppress_savers_cust_attr_prod_ext_ids").split("\\|"));
		StringBuilder offers = new StringBuilder();
		StringBuilder productExtIds = new StringBuilder();
		int i = 0;
		for (ProductSummaryVO mdo : details) {
			if (i == 2) {
				break;
			}
			if (mdo.getPromotionMetaDataList() != null
					&& mdo.getPromotionMetaDataList().size() > 0) {
				for (String metaData : mdo.getPromotionMetaDataList()) {
					if (metaData != null
							&& metaData
									.equalsIgnoreCase("OFFER_PHASE=OpeningOffer")) {
						if (mdo != null
								&& mdo.getMarketingHighlightsList() != null
								&& mdo.getMarketingHighlightsList().size() > 0) {
							offers
									.append("<li style=\"font-size: 16px; font-weight:bold; margin-left: 20px;\">"
											+ mdo.getMarketingHighlightsList()
													.get(0) + "</li> ");
							i++;
						}
					}
				}
				if(suppressSaversCustAttrProdExtIdsList == null || suppressSaversCustAttrProdExtIdsList.size()==0 || !suppressSaversCustAttrProdExtIdsList.contains(mdo.getExternalId())){
					if(productExtIds.length()==0){
						productExtIds.append(escapeSpecialCharacters(mdo.getExternalId()));
					}
					else{
						productExtIds.append(",").append(escapeSpecialCharacters(mdo.getExternalId()));
					}
				}
			}
		}
		if(productExtIds.length()!=0){
			session.setAttribute("saversProductExtIds", productExtIds.toString());
		}
		return offers.toString();
	}


	public Address getAddress(com.AL.xml.v4.CustAddress custAdr) {
		Address add = new Address();
		add.setPostfixDirectional(custAdr.getAddress().getPostfixDirectional());
		add.setPrefixDirectional(custAdr.getAddress().getPrefixDirectional());
		add.setStreetName(custAdr.getAddress().getStreetName());
		add.setStreetNumber(custAdr.getAddress().getStreetNumber());
		add.setStreetType(custAdr.getAddress().getStreetType());
		add.setLine2(custAdr.getAddress().getLine2());
		add.setCity(custAdr.getAddress().getCity());
		add.setStateOrProvince(custAdr.getAddress().getStateOrProvince());
		add.setPostalCode(custAdr.getAddress().getPostalCode());

		return add;

	}

	private List<ProductSummaryVO> getOffersProductList(
			List<ProductSummaryVO> details) {
		List<ProductSummaryVO> detailsOffer = new ArrayList<ProductSummaryVO>();
		int i = 0;
		for (ProductSummaryVO mdo : details) {
			if (i == 2) {
				break;
			}
			if (mdo.getPromotionMetaDataList() != null
					&& mdo.getPromotionMetaDataList().size() > 0) {
				for (String metaData : mdo.getPromotionMetaDataList()) {
					if (metaData != null
							&& metaData
									.equalsIgnoreCase("OFFER_PHASE=OpeningOffer")) {
						if (mdo != null
								&& mdo.getMarketingHighlightsList() != null
								&& mdo.getMarketingHighlightsList().size() > 0) {
							detailsOffer.add(mdo);
							i++;
						}
					}
				}
			}
		}
		return detailsOffer;
	}
	
	private void generateDialoguesFromService(Map<String, Map<String, String>> contextMap, 
			SalesCenterVO salesCenterVo, RequestContext request, String offers) throws UnRecoverableException {

		try {
			SalesDialogueVO	dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);
			StringBuilder events = new StringBuilder();
			List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
			List<DataGroup> dgList = null;

			for (Dialogue dialogue : dialogueVO.getDialogueList()) {
				dgList = dialogue.getDataGroupList();

				for (DataGroup dGroup : dgList) {
					fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(	events, dialogueVO.getDataFieldMap().get(dialogue.getExternalId()).get(dGroup.getName()), null, false);
					for (Fieldset fieldset : fieldsetList) {
						String element = HtmlBuilder.INSTANCE.toString(fieldset);
						element = salesCenterVo.replaceNamesWithValues(element);
						events.append(element);
					}
				}
			}
			String result = salesCenterVo.replaceNamesWithValues(events.toString());
			logger.info("dialogue_for_OfferController=" + result);

			if (result != null && result.contains("includes:")) {
				result = result	.replaceFirst("includes:", "includes: " + offers);
			}
			request.getFlowScope().put("dialogue", result);
		} catch (BaseException e) {
			logger.error("Exception_in_OfferController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}

	}
	
}
