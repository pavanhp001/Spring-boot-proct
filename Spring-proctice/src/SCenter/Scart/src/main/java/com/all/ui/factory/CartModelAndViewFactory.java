package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.util.Utils;
import com.AL.ui.builder.CartDialogueBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.vo.LineItemTypeVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author 
 *
 */
public enum CartModelAndViewFactory {

	INSTANCE;
	private static final Logger logger = Logger.getLogger(CartModelAndViewFactory.class);
	private static final String bulletPoint = "<b style='font-size:20px;font-weight:bold;margin-left:20px;'>&#149;&nbsp;</b>";
	private static final String VERIZON_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_Verizon";
	private static final String COMCAST_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_Comcast";
	private static final String ATT_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_ATT";
	private static final String ATTV6_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_ATTV6";
	private static final String DISH_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_Dish";
	private static final String FRONTIER_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_Frontier";
	private static final String QWEST_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_Qwest";
	private static final String CENTURYLINK_DATAFIELD_EXTERNAL_ID = "CloseConvertedCall_CenturyLink";
	private static final String QWEST_FALLOUT_DATAFIELD_EXTERNAL_ID ="CloseConvertedCall_Qwest_Fallout";
	private static final String CENTURYLINK_FALLOUT_DATAFIELD_EXTERNAL_ID="CloseConvertedCall_CenturyLink_Fallout";
	private static final String COX_DATAFIELD_EXTERNAL_ID="CloseConvertedCall_Cox";
	private static final String HUGHESNET_DATAFIELD_EXTERNAL_ID="CloseConvertedCall_HughesNet";
	private static final String CENTURYLINK = "CenturyLink";
	private static final String QWEST = "Qwest";
	
	public ModelAndView craeteCKOView(Map<String,Object> modelAndViewMap, String viewName)
	{
		logger.info("craeteCKOView_begin_in_CartModelAndViewFactory");
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
		mav.addObject("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(
														(OrderType)modelAndViewMap.get(ControllerConstants.ORDER)) );
		mav.setViewName(viewName);
		logger.info("craeteCKOView_end_in_CartModelAndViewFactory");
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
	public ModelAndView createCloseCallSaleView(HttpSession session,
			OrderType order, HttpServletRequest request) throws Exception {
		
		logger.info("createCloseCallSaleView_begin_in_CartModelAndViewFactory");
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		/**	
		 * Getting SalesContext from the session				 
		 */
		SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
		String GUID = CartUtil.INSTANCE.getGuid(salesContext);
		SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute(ControllerConstants.SALESCONTEXT);
		
		/*
		 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
		 */
		Map<String, Map<String, String>> context = CartDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType(ControllerConstants.CLOSE_CALL_SALE,salesCenterVo,true);
		
		String email = order.getCustomerInformation().getCustomer().getBestEmailContact();
		if(!Utils.isBlank(email)){
			if(Utils.isValidEmail(email)){
				Map<String, String> salesFlow = context.get("salesFlow");
			    salesFlow.put("salesFlow.requiresCSAT", "true");
			    session.setAttribute("requiresCSAT", true);
			}
		}
		else{
			session.setAttribute("requiresCSAT", false);
		}
		
		//Dialogue Service Call Based on Value
		DialogueResponseType dialogueResponseType = CartDialogueFactory.INSTANCE.getDialoguesByContext(context);
		DataFieldList dataFieldList = CartDialogueBuilder.INSTANCE.buildDataFieldList(dialogueResponseType);
		
		List<DataFieldType> dataFields = updateRTIMOrderDialogues(order, dataFieldList.getDataField());
		Map<String, String> scMap = new HashMap<String, String>();
		if(salesCenterVo != null )
		{
			 scMap = salesCenterVo.getScMap();
		}
		
		//TODO:Get the actual values for referrer.businessParty.callbackNumber
		CartDialogueBuilder.INSTANCE.scMapForCloseCallPage(scMap, order);

		//Replacing the Dynamic Values
		if(dataFields != null)
		{
			for(DataFieldType dataFieldType : dataFields)
			{
				CartDialogueBuilder.INSTANCE.replaceNamesWithValues(dataFieldType, scMap);
			}
		}

		ModelAndView mav = createOrderView(
				agentId, order, ControllerConstants.CONCLUSION_VIEW,ControllerConstants.CONCLUSION_TITLE, request);
		
		mav.addObject(ControllerConstants.DATAFIELDLIST, dataFields);
		logger.info("createCloseCallSaleView_end_in_CartModelAndViewFactory");
		return mav;
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
	public ModelAndView createOrderView(final String agentId,
			final OrderType orderType, String view,String title, HttpServletRequest request) throws UnRecoverableException{
		logger.info("createOrderView_begin_in_CartModelAndViewFactory");
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName(view);
		mav.addObject(ControllerConstants.TITLE, title);

		if (orderType != null) 
		{
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			mav.addObject(ControllerConstants.ORDER, orderType);
			createOrderScreensView(mav, orderType,productResultManager);
			mav.addObject("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(orderType));
		}
		if(title.equals(ControllerConstants.ORDER_SUMMARY_TITLE))
		{
			mav.addObject(ControllerConstants.UTILITYOFFER, "false");
			mav.addObject("isPlaceOrder", CartLineItemFactory.INSTANCE.allowPlaceOrder(orderType));
		}
		else if(title.equals(ControllerConstants.CONCLUSION_TITLE))
		{
			mav.addObject("isWarmTransferEnabled", WarmTransferFactory.INSTANCE.isWarmTransferEnabled(orderType, request));
			mav.addObject("CKOCompletedLineItems" , "");
		}
		
		logger.info("createOrderView_end_in_CartModelAndViewFactory");
		return mav;
	}

	/**
	 * @param mav
	 * @param orderType
	 * @param lineItemId
	 */
	public void createOrderScreensView(final ModelAndView mav, final OrderType orderType,ProductResultsManager productResultManager)throws UnRecoverableException {
		logger.info("createOrderScreensView_begin_in_CartModelAndViewFactory");
		if ((orderType != null) && (orderType.getLineItems() != null))
		{
			List<LineItemType> lineItemTypes = CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(orderType);
			List<LineItemTypeVO> sortedLineItems = CartLineItemFactory.INSTANCE.sortLineItemFeaturesAndSelections(lineItemTypes, orderType.getAccountHolder(),productResultManager);
			mav.addObject("lineItemList", lineItemTypes);
			mav.addObject("sortedLineItems", sortedLineItems);
			mav.addObject("baseMonthlyPriceMap", CartLineItemUtil.extractBaseMontlyPrice(orderType));
			mav.addObject("hasFeatures", CartLineItemUtil.hasFeatures(orderType));
		}

		Customer customer = null;

		if ((orderType != null) && (orderType.getCustomerInformation() != null))
		{
			customer = orderType.getCustomerInformation().getCustomer();
			mav.addObject("customer", customer);
			mav.addObject(ControllerConstants.ADDRESS, CartLineItemUtil.getAddress(customer,
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			String customerName = customer.getFirstName()+" "+customer.getLastName();
			mav.addObject("customerName", customerName);
		}

		if ((orderType != null) && (orderType.getLineItems() != null)
				&& (orderType.getLineItems().getLineItem() != null)) 
		{
			mav.addObject("lineitems", orderType.getLineItems().getLineItem());
		}
		mav.addObject("LineItemVo", CartPromotionFactory.INSTANCE.lineItemPromotions(orderType));
		mav.addObject("promoMap", CartLineItemFactory.INSTANCE.hasLineItemPromtions(orderType));
		logger.info("createOrderScreensView_end_in_CartModelAndViewFactory");
	}
	
	
	/**
	 * 
	 * Adding RTIM providers related dialogues (if Order contains RTIM related products) and common dialogues into dataFieldList .
	 * 
	 * @param order
	 * @param dataFieldList
	 * @return dataFieldList
	 * @throws UnRecoverableException
	 */
	public List<DataFieldType> updateRTIMOrderDialogues(OrderType order, 
			List<DataFieldType> dataFieldList) throws Exception
	{
		logger.info("updateRTIMOrderDialogues_begin_in_CartModelAndViewFactory");
		Map<String, Boolean> rtimProviderMap =  new HashMap<String, Boolean>();
		List<DataFieldType> resultedDataFieldList = new ArrayList<DataFieldType>();
		
		//Preparing rtimProviderMap if Order contains RTIM products.
		prepareRTIMProviderMapUsingOrder(order,rtimProviderMap);
		
		// RTIM provider related dataField(Which are available on Order) and common dataField into the list
		prepareDataFieldList(resultedDataFieldList,dataFieldList,rtimProviderMap);
		logger.info("updateRTIMOrderDialogues_end_in_CartModelAndViewFactory");
		return resultedDataFieldList;
	}
	

	/**
	 * Preparing rtimProviderMap if Order contains RTIM products.
	 * If order contains RTIM product then key is that product 'Provider ExternalId' and value is 'true' .
	 * 
	 * @param order
	 * @param rtimProviderMap
	 */
	public void prepareRTIMProviderMapUsingOrder(OrderType order,
			Map<String, Boolean> rtimProviderMap) {
		logger.info("prepareRTIMProviderMapUsingOrder_begin_in_CartModelAndViewFactory");
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		if(lineItems != null)
		{
			for(LineItemType lineItem : lineItems)
			{
				if(LineItemUtil.isSubmittedProduct(lineItem))
				{
					if( lineItem.getLineItemDetail().getDetail()!= null 
							&& lineItem.getLineItemDetail().getDetail().getProductLineItem()!=null 
							&& lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider()!=null )
					{
						if( lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.COMCAST) )
						{
							rtimProviderMap.put(Constants.COMCAST, true);
						}
						
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.DISH))
						{
							rtimProviderMap.put(Constants.DISH, true);
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.FRONTIER))
						{
							rtimProviderMap.put(Constants.FRONTIER, true);
						}						
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.VERIZON))
						{
							rtimProviderMap.put(Constants.VERIZON, true);
							
							// Checking whether the Verizon product on the order is fall out order or not. 
							if(isFalloutOrder(lineItem,rtimProviderMap))
							{
								rtimProviderMap.put(Constants.VERIZON, false);
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.VERIZON2))
						{
							rtimProviderMap.put(Constants.VERIZON2, true);
							
							// Checking whether the Verizon product on the order is fall out order or not. 
							if(isFalloutOrder(lineItem,rtimProviderMap))
							{
								rtimProviderMap.put(Constants.VERIZON2, false);
							}
						}
						
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.ATT1))
						{
							rtimProviderMap.put(Constants.ATT1, true);
						}if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.ATTV6))
						{
							rtimProviderMap.put(Constants.ATTV6, true);
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.CENTURY_LINK))
						{
							int isCenturyLinkOrQwest = CartLineItemFactory.INSTANCE.isCenturyLinkOrQwestProduct(lineItem);
							if( isCenturyLinkOrQwest == 1 )
							{
								rtimProviderMap.put(Constants.CENTURY_LINK, true);
								if(isFalloutOrder(lineItem,rtimProviderMap))
								{
									rtimProviderMap.put(Constants.CENTURY_LINK, false);
								}
							}
							else if( isCenturyLinkOrQwest == 2 )
							{
								rtimProviderMap.put(QWEST, true);
								if(isFalloutOrder(lineItem,rtimProviderMap))
								{
									rtimProviderMap.put(QWEST, false);
								}
							}
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.COX_RTS_PROVIDER_ID))
						{
							rtimProviderMap.put(Constants.COX_RTS_PROVIDER_ID, true);
							
							// Checking whether the Cox product on the order is fall out order or not. 
							if(isFalloutOrder(lineItem,rtimProviderMap))
							{
								rtimProviderMap.put(Constants.COX_RTS_PROVIDER_ID, false);
							}
						}
						if( lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.TWC_RTS) )
						{
							rtimProviderMap.put(Constants.TWC_RTS, true);
						}
						if(lineItem.getLineItemDetail().getDetail()
								.getProductLineItem().getProvider().getExternalId().equals(Constants.HUGHESNET_RTS_PROVIDER_ID))
						{
							rtimProviderMap.put(Constants.HUGHESNET_RTS_PROVIDER_ID, true);
						}
					}
				}
			}
		}
		logger.info("prepareRTIMProviderMapUsingOrder_end_in_CartModelAndViewFactory"+rtimProviderMap);
	}
	
	
	/**
	 * Checking whether the Verizon product on the order is fall out order or not. 
	 * If it is fall out order then not adding the Verizon provider related dialogues to dataFieldList.
	 * 
	 * @param lineItem
	 * @param rtimProviderMap
	 * 
	 */
	private boolean isFalloutOrder(LineItemType lineItem, Map<String, Boolean> rtimProviderMap){
		logger.info("isFalloutOrder_begin_in_CartModelAndViewFactory");
		boolean isFallOut = false;
		LineItemAttributeType lineItemAttributeType = lineItem.getLineItemAttributes();
		if( lineItemAttributeType!=null && lineItemAttributeType.getEntity()!=null 
				&& !lineItemAttributeType.getEntity().isEmpty() )
		{
			for( AttributeEntityType attributeEntityType : lineItemAttributeType.getEntity() )
			{
				if( !Utils.isBlank(attributeEntityType.getSource()) 
						&& attributeEntityType.getSource().equalsIgnoreCase(Constants.CKO) 
						&& attributeEntityType.getAttribute() != null 
						&& !attributeEntityType.getAttribute().isEmpty() )
				{
					for(AttributeDetailType attributeDetailType : attributeEntityType.getAttribute())
					{
						if( !Utils.isBlank(attributeDetailType.getName()) 
								&& !Utils.isBlank(attributeDetailType.getValue())
								&& attributeDetailType.getName().equalsIgnoreCase(Constants.IS_FALLOUT_ORDER) 
								&& attributeDetailType.getValue().equalsIgnoreCase(Constants.TRUE) )
						{
							isFallOut =  true;
							break;
						}
					}
					break;
				}
			}
		}
		logger.info("isFalloutOrder_end_in_CartModelAndViewFactory");
		return isFallOut;
		
	}
	
	
	/**
	 * 
	 * RTIM provider related data fields(Which are available on Order) and common data fields are adding into the List.
	 * 
	 * @param resultedDataFieldList
	 * @param dataFieldList
	 * @param rtimProviderMap
	 */
	private void prepareDataFieldList(
			List<DataFieldType> resultedDataFieldList,
			List<DataFieldType> dataFieldList, Map<String, Boolean> rtimProviderMap) throws UnRecoverableException 
	{
		logger.info("prepareDataFieldList_begin_in_CartModelAndViewFactory");
		for(DataFieldType dataFieldType : dataFieldList)
		{
			if(dataFieldType.getExternalId().equals(VERIZON_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.VERIZON)!=null && rtimProviderMap.get(Constants.VERIZON).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(COMCAST_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.COMCAST)!=null && rtimProviderMap.get(Constants.COMCAST).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(ATT_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.ATT1)!=null && rtimProviderMap.get(Constants.ATT1).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(ATTV6_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.ATTV6)!=null && rtimProviderMap.get(Constants.ATTV6).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(DISH_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.DISH)!=null && rtimProviderMap.get(Constants.DISH).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(FRONTIER_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.FRONTIER)!=null && rtimProviderMap.get(Constants.FRONTIER).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}			
			else if(dataFieldType.getExternalId().equals(QWEST_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(QWEST)!=null && rtimProviderMap.get(QWEST).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(CENTURYLINK_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.CENTURY_LINK)!=null && rtimProviderMap.get(Constants.CENTURY_LINK).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(CENTURYLINK_FALLOUT_DATAFIELD_EXTERNAL_ID))
			{
				if( (rtimProviderMap.get(Constants.CENTURY_LINK)!=null && !(rtimProviderMap.get(Constants.CENTURY_LINK).booleanValue())))
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(QWEST_FALLOUT_DATAFIELD_EXTERNAL_ID))
			{
				if(rtimProviderMap.get(QWEST)!=null && !(rtimProviderMap.get(QWEST).booleanValue()))
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(COX_DATAFIELD_EXTERNAL_ID))
			{
				if(rtimProviderMap.get(Constants.COX_RTS_PROVIDER_ID)!=null && (rtimProviderMap.get(Constants.COX_RTS_PROVIDER_ID).booleanValue()))
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else if(dataFieldType.getExternalId().equals(HUGHESNET_DATAFIELD_EXTERNAL_ID))
			{
				if( rtimProviderMap.get(Constants.HUGHESNET_RTS_PROVIDER_ID)!=null && rtimProviderMap.get(Constants.HUGHESNET_RTS_PROVIDER_ID).booleanValue() )
				{
					StringBuilder builder = new StringBuilder();
					if(!dataFieldType.getText().contains(bulletPoint)) {
						builder.append(bulletPoint);
					}
					builder.append(dataFieldType.getText());
					dataFieldType.setText(builder.toString());
					resultedDataFieldList.add(dataFieldType);
				}
			}
			else
			{
				resultedDataFieldList.add(dataFieldType);
			}
			
		}
		logger.info("prepareDataFieldList_end_in_CartModelAndViewFactory");
	}
}
