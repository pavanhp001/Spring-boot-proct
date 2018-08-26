package com.AL.ui.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.util.Utils;
import com.AL.ui.builder.CartDialogueBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.DataFieldVO;
import com.AL.ui.vo.LineItemTypeVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.util.DateUtil;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.v4.AccountHolderType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;



/**
 * @author 
 *
 */
public enum CartEventFactory {

	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(CartEventFactory.class);
	
	public ModelAndView craeteCKOView(Map<String,Object> modelAndViewMap, String viewName)
	{
		logger.info("craeteCKOView_begin_in_CartEventFactory");
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(ControllerConstants.ORDERID, modelAndViewMap.get(ControllerConstants.ORDERID));
		mav.addObject(ControllerConstants.CUSTOMERID, modelAndViewMap.get(ControllerConstants.CUSTOMERID));
		mav.addObject(ControllerConstants.LINEITEMID, modelAndViewMap.get(ControllerConstants.LINEITEMID));
		mav.addObject(ControllerConstants.PROVIDERID, modelAndViewMap.get(ControllerConstants.PROVIDERID));
		mav.addObject(ControllerConstants.PROVIDERNAMES, modelAndViewMap.get(ControllerConstants.PROVIDERNAMES));
		mav.addObject(ControllerConstants.UTILITYOFFER, modelAndViewMap.get(ControllerConstants.UTILITYOFFER));
		mav.addObject(ControllerConstants.KEY, modelAndViewMap.get(ControllerConstants.KEY).toString());
		mav.addObject(ControllerConstants.URL, modelAndViewMap.get(ControllerConstants.URL).toString());
		mav.addObject(ControllerConstants.ORDER, modelAndViewMap.get(ControllerConstants.ORDER));
		mav.addObject(ControllerConstants.LINEITEMLIST, modelAndViewMap.get(ControllerConstants.LINEITEMLIST));
		mav.addObject(ControllerConstants.ADDRESS, modelAndViewMap.get(ControllerConstants.ADDRESS));
		
		if( modelAndViewMap.get(ControllerConstants.UTILITYOFFER).equals("true"))
		{
			mav.addObject(ControllerConstants.TITLE, ControllerConstants.UTILITY_TITLE);
		}
		else
		{
			mav.addObject(ControllerConstants.TITLE, ControllerConstants.CKO_TITLE);
		}
		
		if(modelAndViewMap.get(ControllerConstants.ISRECOMMENDATION) != null)
		{
			mav.addObject(ControllerConstants.ISRECOMMENDATION, modelAndViewMap.get(ControllerConstants.ISRECOMMENDATION));
		}
		else
		{
			mav.addObject(ControllerConstants.ISRECOMMENDATION, "false");
		}
		logger.info("craeteCKOView_end_in_CartEventFactory");
		mav.setViewName(viewName);

		return mav;
	}

	/**
	 * Builds the view for Close Call Sale
	 * 
	 * @param session
	 * @param order
	 * @param dataFieldList
	 * @return ModelAndView
	 * @throws Exception 
	 */
	public Event createCloseCallSaleView(HttpSession session,
			OrderType order, RequestContext req) throws Exception {
		logger.info("createCloseCallSaleView_begin_in_CartEventFactory");
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		/**	
		 * Getting SalesContext from the session				 
		 */
		SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
		String GUID = CartUtil.INSTANCE.getGuid(salesContext);
		SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute(ControllerConstants.SALESCONTEXT);
		
		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();
		
		Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
		Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
		Map<String, String> salesFlow = new HashMap<String, String>();
		String email = order.getCustomerInformation().getCustomer().getBestEmailContact();
		if(!Utils.isBlank(email)){
			if(Utils.isValidEmail(email)){
			    salesFlow.put("salesFlow.requiresCSAT", "true");
			    session.setAttribute("requiresCSAT", true);
			}
		}
		else{
			session.setAttribute("requiresCSAT", false);
		}
		
		contextMap.put("salesFlow", salesFlow);
		dynamicFlow.put("dynamicFlow.page", ControllerConstants.CLOSE_CALL_SALE);
		
		Map<String, String> scMap = new HashMap<String, String>();
		if(salesCenterVo != null )
		{
			 scMap = salesCenterVo.getScMap();
		}
		
		//Get the actual values for referrer.businessParty.callbackNumber
		CartDialogueBuilder.INSTANCE.scMapForCloseCallPage(scMap, order);
		CustAddress custAddress  = CartLineItemUtil.getAddress(order.getCustomerInformation().getCustomer(),com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
		req.getFlowScope().put("address", getAddress(custAddress));
		Event event = createOrderView(agentId, order, ControllerConstants.CONCLUSION_VIEW,ControllerConstants.CONCLUSION_TITLE, req);
		if (salesCenterVo.getValueByName("drupalContentUrl") != null 
				&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
			String dialoguesFromDrupal = getDialoguesFormDrupalContent(contextMap,salesCenterVo,null);
			if (Utils.isBlank(dialoguesFromDrupal)){
				generateDialoguesFromService(contextMap,salesCenterVo,req,order, scMap);
			}else{
				Map<String, Boolean> rtimProviderMap =  new HashMap<String, Boolean>();
				CartModelAndViewFactory.INSTANCE.prepareRTIMProviderMapUsingOrder(order, rtimProviderMap);
				StringBuilder str = new StringBuilder();
				for (Entry<String,Boolean> provider :rtimProviderMap.entrySet()){
					if(provider.getValue()){
						str.append(getDialoguesFormDrupalContent(contextMap,salesCenterVo,provider.getKey()));
					}
				}
				if(!Utils.isBlank(dialoguesFromDrupal) && !Utils.isBlank(str.toString()) && rtimProviderMap != null && rtimProviderMap.size() == 1 &&
						(rtimProviderMap.get(Constants.VERIZON) != null || rtimProviderMap.get(Constants.VERIZON2) != null)){
					dialoguesFromDrupal = removeVZDialogues(dialoguesFromDrupal);
				}
				dialoguesFromDrupal = dialoguesFromDrupal.replace("REPLACERTIMDIALOGUES", str.toString());
				dialoguesFromDrupal = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(CartUtil.escapeSpecialCharacters(dialoguesFromDrupal)));
				req.getFlashScope().put("referrerFlow", (String) session.getAttribute("referrerFlowAgentGroup"));	
				req.getFlowScope().put("isDialoguesFromDrupal", true);
				req.getFlowScope().put("dialoguesFromDrupal", dialoguesFromDrupal);
			}
		}
		else{
			generateDialoguesFromService(contextMap,salesCenterVo,req,order, scMap);
		}
		request.getSession().setAttribute("bestEmailContactValue", email);
		request.getSession().setAttribute("bestPhoneContactValue", order.getCustomerInformation().getCustomer().getBestPhoneContact());
		if(order.getMoveDate() != null 
				&& order.getMoveDate().toGregorianCalendar() != null 
				  && order.getMoveDate().toGregorianCalendar().getTime() != null){
			request.getSession().setAttribute("moveInDateValue", DateUtil.toDateString(order.getMoveDate()));
		}else{
			request.getSession().setAttribute("moveInDateValue", "");
		}
		//Here we setting OfferLineItemExtID in request level for Score Capturing
		LineItemType offerLineItem = LineItemUtil.getLineItemBasedOnProductType(order,"SaversOffer");
		if(offerLineItem !=  null){
			req.getFlashScope().put("offerLineItemExtID", offerLineItem.getExternalId());
		}
		logger.info("OfferLineItemExtID="+offerLineItem);
		//Suppress Sling TV dialogue when we have isSlingTvPurchased true
		if(LineItemUtil.isSlingTVPurchased(order)){
			if(req.getFlowScope().get("dialoguesFromDrupal") != null 
					&& !Utils.isBlank(req.getFlowScope().getString("dialoguesFromDrupal"))){
				Document doc = Jsoup.parse(req.getFlowScope().getString("dialoguesFromDrupal"));
				if(doc != null){
					Element  element = doc.getElementById(Constants.SLING_FS_DATA_FIELD_ID);
					if(element != null){
						element.remove();
						req.getFlowScope().put("dialoguesFromDrupal" , doc.toString());
					}	
				}
				if(req.getFlowScope().get(ControllerConstants.DATAFIELDLIST) != null
						&& !Utils.isBlank(req.getFlowScope().getString(ControllerConstants.DATAFIELDLIST))){
					Document docDataField = Jsoup.parse(req.getFlowScope().getString(ControllerConstants.DATAFIELDLIST));
					if(docDataField != null){
						Element  element = docDataField.getElementById(Constants.SLING_FS_DATA_FIELD_ID);
						if(element != null){
							element.remove();
							req.getFlowScope().put(ControllerConstants.DATAFIELDLIST , doc.toString());
						}	
					}
				}
			}
		}		
		logger.info("createCloseCallSaleView_end_in_CartEventFactory");
		return event;
	}
	
	
	private void generateDialoguesFromService(
			Map<String, Map<String, String>> contextMap,
			SalesCenterVO salesCenterVo, RequestContext req, OrderType order, Map<String, String> scMap) throws UnRecoverableException {
		req.getFlowScope().put("isDialoguesFromDrupal", false);
		//Dialogue Service Call Based on Value
		DialogueResponseType dialogueResponseType = null;
		try {
			dialogueResponseType = CartDialogueFactory.INSTANCE.getDialoguesByContext(contextMap);
			DataFieldList dataFieldList = CartDialogueBuilder.INSTANCE.buildDataFieldList(dialogueResponseType);
			List<DataFieldType> dataFields;
			dataFields = CartModelAndViewFactory.INSTANCE.updateRTIMOrderDialogues(order, dataFieldList.getDataField());

			List<DataFieldVO> dataFieldVOs =  CartDialogueBuilder.INSTANCE.prepareDataFiledVOList(dataFields);

			//Replacing the Dynamic Values
			if( dataFieldVOs != null && !dataFieldVOs.isEmpty() )
			{
				for(DataFieldVO dataFieldVO : dataFieldVOs)
				{
					CartDialogueBuilder.INSTANCE.replaceNamesWithValues(dataFieldVO, scMap);
				}
			}
			req.getFlowScope().put(ControllerConstants.DATAFIELDLIST, dataFieldVOs);
		}
		catch (Exception e) {
			logger.error("Exception_in_CartEventFactory_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}

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
	
	
	/**
	 * Builds the view for Order Summary,Order Recap and Close Call Sale
	 * 
	 * 
	 * @param agentId
	 * @param orderType
	 * @param view
	 * @return ModelAndView
	 */
	public Event createOrderView(final String agentId,
			final OrderType orderType, String view,String title, RequestContext req) throws UnRecoverableException{
		logger.info("createOrderView_begin_in_CartEventFactory");
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		HttpSession session = request.getSession();
		req.getFlowScope().put("name" , view);
		req.getFlowScope().put(ControllerConstants.TITLE, title);

		if (orderType != null) 
		{
			session.setAttribute(ControllerConstants.ORDER, orderType);
			createOrderScreensView(req, orderType);
			req.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(orderType));
		}
		if(title.equals(ControllerConstants.ORDER_SUMMARY_TITLE))
		{
			req.getFlowScope().put(ControllerConstants.UTILITYOFFER, "false");
			req.getFlowScope().put("isPlaceOrder", CartLineItemFactory.INSTANCE.allowPlaceOrder(orderType));
		}
		else if(title.equals(ControllerConstants.CONCLUSION_TITLE))
		{
			logger.info("isWarmTransferEnabledInSession="+request.getSession().getAttribute("isWarmTransferEnabled"));
			if (request.getSession().getAttribute("isWarmTransferEnabled") == null) {
				StringBuffer accountHolderName = new StringBuffer();
				List<LineItemType> lineItems = CartLineItemFactory.INSTANCE.sortLineItemProducts(orderType, request);
				if( lineItems != null && !lineItems.isEmpty() && lineItems.get(lineItems.size()-1).getAccountHolderExternalId() != null )
				{
					for(AccountHolderType accountHolder : orderType.getAccountHolder())
					{
						logger.info("accountHolderExtID="+ accountHolder.getExternalId()+"***********LineAcountHolderExtID="+lineItems.get(lineItems.size()-1).getAccountHolderExternalId());

						if ( accountHolder.getExternalId() == lineItems.get(lineItems.size()-1).getAccountHolderExternalId().longValue() )
							
						{
							accountHolderName.append(accountHolder.getFirstName());
							accountHolderName.append(" ");
							accountHolderName.append(accountHolder.getLastName());
							break;
						}
					}
				}
				logger.info("accountHolderNameLength="+ accountHolderName.length());
				if(accountHolderName.length() == 0)
				{
					accountHolderName.append(orderType.getCustomerInformation().getCustomer().getFirstName());
					accountHolderName.append(" ");
					accountHolderName.append(orderType.getCustomerInformation().getCustomer().getLastName());
				}
				req.getFlowScope().put("accountHolderName", accountHolderName.toString());
				req.getFlowScope().put("isWarmTransferEnabled", WarmTransferFactory.INSTANCE.isWarmTransferEnabled(orderType, request));
				logger.info("accountHolderName="+ accountHolderName.toString());
				logger.info("isWarmTransferEnabled="+req.getFlowScope().get("isWarmTransferEnabled"));
			}
		}
		logger.info("createOrderView_end_in_CartEventFactory");
		return new Event(this, "orderSummaryEvent");
	}

	/**
	 * @param mav
	 * @param orderType
	 * @param lineItemId
	 * @throws UnRecoverableException 
	 */
	public void createOrderScreensView(RequestContext req, final OrderType orderType) throws UnRecoverableException {
		logger.info("createOrderScreensView_begin_in_CartEventFactory");
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		HttpSession session = request.getSession();
		if ((orderType != null) && (orderType.getLineItems() != null))
		{
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			List<LineItemType> lineItemTypes = CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(orderType);
			List<LineItemTypeVO> sortedLineItems = CartLineItemFactory.INSTANCE.sortLineItemFeaturesAndSelections(lineItemTypes,orderType.getAccountHolder(),productResultManager);
			request.getSession().setAttribute("lineItemList", lineItemTypes);
			req.getFlowScope().put("sortedLineItems", sortedLineItems);
			req.getFlowScope().put("baseMonthlyPriceMap", CartLineItemUtil.extractBaseMontlyPrice(orderType));
			req.getFlowScope().put("hasFeatures", CartLineItemUtil.hasFeatures(orderType));
		}

		Customer customer = null;

		if ((orderType != null) && (orderType.getCustomerInformation() != null))
		{
			customer = orderType.getCustomerInformation().getCustomer();
			session.setAttribute("customer", customer);
		/*	req.getFlowScope().put(ControllerConstants.ADDRESS, CartLineItemUtil.getAddress(customer,
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));*/
			String customerName = customer.getFirstName()+" "+customer.getLastName();
			req.getFlowScope().put("customerName", customerName);
		}

		if ((orderType != null) && (orderType.getLineItems() != null)
				&& (orderType.getLineItems().getLineItem() != null)) 
		{
			session.setAttribute("lineitems", orderType.getLineItems().getLineItem());
		}
		session.setAttribute("LineItemVo", CartPromotionFactory.INSTANCE.lineItemPromotions(orderType));
		session.setAttribute("promoMap", CartLineItemFactory.INSTANCE.hasLineItemPromtions(orderType));
		logger.info("createOrderScreensView_end_in_CartEventFactory");
	}
	
	public static String getDialoguesFormDrupalContent(
			Map<String, Map<String, String>> contextMap, SalesCenterVO salesCenterVo, String providerId) {
		String dialoguesFromDrupal = null;
		try {
			dialoguesFromDrupal = CartUtil.dialoguesByContextForDrupal(contextMap,providerId);
			salesCenterVo.setValueByName("drupalDialogueCacheKey", CartUtil.generateDialogueCacheKey(contextMap,providerId,salesCenterVo.getValueByName("ordersource.programId")));
			if (Utils.isBlank(dialoguesFromDrupal)){
 				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(CartUtil.generateDialogueCacheKey(contextMap,providerId,salesCenterVo.getValueByName("ordersource.programId"))
 						, salesCenterVo.getValueByName("drupalContentUrl"));
 				if(Utils.isBlank(dialoguesFromDrupal)) {
 	 				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(CartUtil.generateDialogueBackupDefaultUserGroupDrupal(contextMap,providerId,salesCenterVo.getValueByName("ordersource.programId"))
 	 						, salesCenterVo.getValueByName("drupalContentUrl"));
 	 				CartUtil.storeDialoguesByContextForDrupalDefaultUserGroup(contextMap,dialoguesFromDrupal,providerId);
 	 				logger.info("dialogues--"+dialoguesFromDrupal);
 				} else {
 	 				CartUtil.storeDialoguesByContextForDrupal(contextMap,dialoguesFromDrupal,providerId);
 	 				logger.info("dialogues--"+dialoguesFromDrupal);
 				}
 			}
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialoguesFromDrupal = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(CartUtil.escapeSpecialCharacters(dialoguesFromDrupal)));
		return dialoguesFromDrupal;
	}
	
	public String removeVZDialogues(String response){
		StringBuilder sb  = new StringBuilder();
		try{
			Document doc = Jsoup.parse(response);
			for(Element e : doc.getAllElements()){
				if(e.tag().toString() == "fieldset" && (e.getElementById("CloseConvertedCall_3_FS") != null || e.getElementById("CloseConvertedCall_5_FS") != null)){
					e.remove();
				}else{
					if(e.tag().toString() == "fieldset" ){
						
						sb.append(e);
					}
				}
			}
		}catch (Exception e) {
			logger.error("Error while removingVZDialogues"+e.getMessage());
			return response;
		}
		return sb.toString();
	}
	
}
