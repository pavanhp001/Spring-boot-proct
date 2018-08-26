package com.AL.managers;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.json.JSONArray;

import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.domain.SalesSession;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartSalesContextFactory;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ErrorList;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.Attributes;
import com.AL.xml.cm.v4.CustomerAttributeEntityType;
import com.AL.xml.cm.v4.CustomerAttributeType;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

public class SalesStatusUpdateManager implements Runnable{
	
	private long salesCallId = 0L;
	private boolean isPlaceOrder ;
	private boolean hasSubmittedRtProducts;
	private boolean hasAllProductsSubmitted;
	private String ambDisconnectDateTime;
	private String orderId;
	private String agentId;
	private String GUID;
	private String sessionId;
	private String vvdFlag;
	private int zipOnlySearch;

	private JSONArray lineItemIds;
	
	private SalesSessionDao salesSessionDao;
	private SalesCallDao salesCallDao;

	private static Logger logger = Logger.getLogger(SalesStatusUpdateManager.class );
	
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getGUID() {
		return GUID;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public SalesSessionDao getSalesSessionDao() {
		return salesSessionDao;
	}

	public void setSalesSessionDao(SalesSessionDao salesSessionDao) {
		this.salesSessionDao = salesSessionDao;
	}
	
	public SalesCallDao getSalesCallDao() {
		return salesCallDao;
	}

	public void setSalesCallDao(SalesCallDao salesCallDao) {
		this.salesCallDao = salesCallDao;
	}

	public long getSalesCallId() {
		return salesCallId;
	}

	public void setSalesCallId(long salesCallId) {
		this.salesCallId = salesCallId;
	}

	public String getAmbDisconnectDateTime() {
		return ambDisconnectDateTime;
	}

	public void setAmbDisconnectDateTime(String ambDisconnectDateTime) {
		this.ambDisconnectDateTime = ambDisconnectDateTime;
	}
	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setPlaceOrder(boolean isPlaceOrder) {
		this.isPlaceOrder = isPlaceOrder;
	}
	
	public boolean isPlaceOrder() {
		return isPlaceOrder;
	}
	
	public void setLineItemIds(JSONArray lineItemIds) {
		this.lineItemIds = lineItemIds;
	}
	
	public JSONArray getLineItemIds() {
		return lineItemIds;
	}
	
	public void setHasSubmittedRtProducts(boolean hasSubmittedRtProducts) {
		this.hasSubmittedRtProducts = hasSubmittedRtProducts;
	}
	
	public boolean hasSubmittedRtProducts() {
		return hasSubmittedRtProducts;
	}
	
	public boolean hasAllProductsSubmitted() {
		return hasAllProductsSubmitted;
	}

	public void setHasAllProductsSubmitted(boolean hasAllProductsSubmitted) {
		this.hasAllProductsSubmitted = hasAllProductsSubmitted;
	}

	public void setVvdFlag(String vvdFlag) {
		this.vvdFlag = vvdFlag;
	}
	
	public String getVvdFlag() {
		return vvdFlag;
	}
	
	public int getZipOnlySearch() {
		return zipOnlySearch;
	}

	public void setZipOnlySearch(int zipOnlySearch) {
		this.zipOnlySearch = zipOnlySearch;
	}

	public void run() {

		try {
			logger.info("SalesStatusUpdateManager_run_begin");
			MDC.put("sessionId", getSessionId() != null ? getSessionId() : "");
			MDC.put("agentId", getAgentId() != null ? getAgentId() : "");
			MDC.put("orderId", getOrderId() != null ? getOrderId() : "");
			MDC.put("GUID", getGUID() != null ? getGUID() : "");
			
			OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(getOrderId());
			if(orderManagementRequestResponse!=null)
			{
				SalesContextType salesContext = orderManagementRequestResponse.getResponse().getSalesContext();
				boolean isSalesSessionCompleted = isSessionStatusCompleted(salesContext);
				OrderType orderType = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
				
				
				Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
				Map<String, String> sourceEntity = new HashMap<String, String>();
				sourceEntity.put("source", "salescenter");
				sourceEntity.put( "GUID", getGUID());
				data.put( "source", sourceEntity );
				CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);

				com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
				CustomerType updateCustomer = oFactory.createCustomerType();
				CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
				Attributes attributes = updateCustomer.getAttributes();
				if(attributes == null)
				{
					attributes = new com.AL.xml.cm.v4.ObjectFactory().createAttributes();
				}
				List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
				customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("VVD", getVvdFlag(), "", "VVD"));
				attributes.getAttribute().add(customerAttributeType);
				updateCustomer.setAttributes(attributes);

				
				com.AL.xml.v4.Customer orderCustomer = orderType.getCustomerInformation().getCustomer();
				
				if( !Utils.isBlank ( orderCustomer.getBestEmailContact() ) )
				{
					updateCustomer.setExternalId( orderCustomer.getExternalId() );
					updateCustomer.setEMailOptIn( orderCustomer.isEMailOptIn() );
					updateCustomer.setEMailProductUpdatesOptIn(orderCustomer.isEMailProductUpdatesOptIn());
					updateCustomer.setMarketingOptIn( orderCustomer.isMarketingOptIn() );
					updateCustomer.setNonBuyerWebOptIn( orderCustomer.isNonBuyerWebOptIn() );
					updateCustomer.setDirectMailOptIn( orderCustomer.isDirectMailOptIn() );
					updateCustomer.setPhoneContactOptIn( orderCustomer.isPhoneContactOptIn() );

				}
				updateCustomer = CustomerService.INSTANCE.submitCustomerType(orderType.getAgentId(), String.valueOf(orderCustomer.getExternalId()),"updateCustomer", updateCustomer, customerContext);
				
				if( !isSalesSessionCompleted )
				{
					
					updateDispositionForCallDrop(orderType);
					List<String> listOflineItemIds = LineItemUtil.containsUtilityOffers(orderType);
					logger.info("lineItemIdsForUtilityOffer=" + listOflineItemIds);
					if (!listOflineItemIds.isEmpty())
					{
						List<Integer> reasons = new ArrayList<Integer>();
						reasons.add(ControllerConstants.REASON_CODE);

						LineItemService.INSTANCE.updateLineItemStatus(orderType.getAgentId(), String.valueOf(orderType.getExternalId()),
								listOflineItemIds, LineItemStatusCodesType.CANCELLED_REMOVED.value(),
								LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, salesContext,new ErrorList());
						logger.info("UpdateLineItemStatus_completed");
					}
					
					
					List<String> lineItemsListToSubmit = LineItemUtil.containsSaversOffers(orderType);
					
					if( isPlaceOrder())
					{
						for(int i=0;i<getLineItemIds().length();i++)
						{
							lineItemsListToSubmit.add(String.valueOf(getLineItemIds().get(i)));
						}
					}
					
					logger.info("lineItemIdsListToSubmitOrder=" + lineItemsListToSubmit);
					/*
					 * Submitting line items which line items are CKO complete status. 
					 */
					salesContext = CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext);
					if(!lineItemsListToSubmit.isEmpty())
					{
						LineItemService.INSTANCE.submitMultipleLineItem( orderType.getAgentId(), String.valueOf(orderType.getExternalId()), 
								lineItemsListToSubmit, salesContext,new ErrorList(), zipOnlySearch);
					}
					else
					{
						OrderService.INSTANCE.updateSalesContext(orderType.getAgentId(), String.valueOf(orderType.getExternalId()), 
								salesContext, SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch);
					}
					
				}
				else{
					if(hasAllProductsSubmitted())
					{
						updateDispositionForCallDrop(orderType);
					}
				}
			}
		} 
		catch (Exception e) {
			logger.error("Error_in_SalesStatusUpdateManager"  , e);
		}
	}
	

	/**
	 * 
	 * @param orderManagementRequestResponse
	 */
	private void updateDispositionForCallDrop(OrderType orderType)
	{
		SalesSession salesSession = new SalesSession();
		Long orderId = orderType.getExternalId();
		Long customerId = orderType.getCustomerInformation().getCustomer().getExternalId();

		if (customerId != null && orderId != null) 
		{
			salesSession = salesSessionDao.getSalesSession(orderId,	customerId);
		}
		Long dispositionId = 11295L;
		logger.info("salesSessionDispositionId="+dispositionId);
		
		salesSession.setDispositionId(dispositionId);

		salesSession.setEndTime(Calendar.getInstance());
		salesSession.setNormalClose("true");

		if (customerId != null)
		{
			salesSessionDao.updateSalesSession(salesSession);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z (Z)");
		try {
			if (!Utils.isBlank(ambDisconnectDateTime)) 
			{
				Calendar disconnectCal = Calendar.getInstance();
				String ambDisconnectDateTime2 = ambDisconnectDateTime.substring(0, 31);
				String ambDisconnectDateTime3 = ambDisconnectDateTime.substring(31, ambDisconnectDateTime.length());
				ambDisconnectDateTime = ambDisconnectDateTime2 + ":"+ ambDisconnectDateTime3;
				disconnectCal.setTime(sdf.parse(ambDisconnectDateTime));
				salesCallDao.updateSalesCallWithAmbDisconnectDateTime(salesCallId, disconnectCal);
			}
		} catch (Exception e) {
			logger.warn("Error_in_SaveDispositionDetails",e);
		}
		logger.info("Saved_DispositionDetails_For_CallDrop");
	}
	
	
	/**
	 * Verifying session status is completed or not. Returns "true" if and only if session status is completed and is updated by concert otherwise returns "false" 
	 * @param salesContext
	 * @return
	 */
	private boolean isSessionStatusCompleted(SalesContextType salesContext)
	{
		boolean isUpdateByConcert = false;
		boolean isSessionStatusCompleted = false;
		for (SalesContextEntityType salesContextEntityType : salesContext.getEntity())
		{
			if (salesContextEntityType.getName().equalsIgnoreCase(Constants.SYP))
			{
				for (NameValuePairType nameValuePairType : salesContextEntityType.getAttribute())
				{
					if ((nameValuePairType.getName().equalsIgnoreCase("updatedBy")))
					{
						if ( nameValuePairType.getValue().equalsIgnoreCase(Constants.CONCERT))
						{
							isUpdateByConcert = true;
						}
					}
					if ((nameValuePairType.getName().equalsIgnoreCase("sessionStatus")))
					{
						if (!nameValuePairType.getValue().equalsIgnoreCase("completed")){
							isSessionStatusCompleted = true;
						}
					}
				}
			}
		}
		
		if (isSessionStatusCompleted && isUpdateByConcert)
		{
			return true;
		}
		return false;
	}

}
