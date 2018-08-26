package com.AL.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.AL.controller.RESTClient;
import com.AL.managers.ApplicationContextProvider;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.Utils;
import com.AL.V.factory.CustomerFactory;
import com.AL.xml.cm.v4.Attributes;
import com.AL.xml.cm.v4.CustomerAttributeEntityType;
import com.AL.xml.cm.v4.CustomerAttributeType;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

public class ConcertSessionTimeOutListener implements HttpSessionListener {
	
	private static final Logger logger = Logger.getLogger(ConcertSessionTimeOutListener.class);

	public ConcertSessionTimeOutListener() {
	}

	public void sessionCreated(HttpSessionEvent sessionEvent) {
		// Get the session that was created
		HttpSession session = sessionEvent.getSession();
		// Store something in the session, and log a message
		logger.info(getTime() + " Session ID=" 
				+ session.getId());
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		// Get the session that was invalidated
		HttpSession session = sessionEvent.getSession();
		logger.info("Order_Id_Is="+ session.getAttribute("orderId"));
		logger.info("pauseAndResumeURL="+ session.getAttribute("pauseAndResumeURL"));
		logger.info("phoneId="+ session.getAttribute("phoneId"));
		// Log a message
		if (session.getAttribute("orderId") != null){
			OrderManagementRequestResponse requestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(String.valueOf(session.getAttribute("orderId")));
			OrderType order = requestResponse.getResponse().getOrderInfo().get(0);
			
			Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
			Map<String, String> sourceEntity = new HashMap<String, String>();
			sourceEntity.put("source", "salescenter");
			sourceEntity.put( "GUID", (String)session.getAttribute("GUID") );
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
			
			String typeOfService = (String)session.getAttribute("typeOfService");
			logger.info("typeOfService="+typeOfService);
			if( session.getAttribute("isMoveInDelta")!= null && !Utils.isBlank(typeOfService) && !"moveInDeltaService".equalsIgnoreCase(typeOfService) )
			{
				customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("VVD", "true", "", "VVD"));
			}
			else
			{
				customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("VVD", "false", "", "VVD"));
			}
			attributes.getAttribute().add(customerAttributeType);
			updateCustomer.setAttributes(attributes);

			
			com.AL.xml.v4.Customer orderCustomer = order.getCustomerInformation().getCustomer();
			
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
			
			updateCustomer = CustomerService.INSTANCE.submitCustomerType(order.getAgentId(), String.valueOf(orderCustomer.getExternalId()),"updateCustomer", updateCustomer, customerContext);
			
			SalesContextType updateSalesContext = OrderService.INSTANCE.getSalesContext(String.valueOf(order.getExternalId()),order.getAgentId());
			List<SalesContextEntityType> list = updateSalesContext.getEntity();
			boolean isSessionStatusFlag = false;
			for (SalesContextEntityType entry : list){
				if (entry.getName() != null && entry.getName().equalsIgnoreCase("SYP")){
					List<NameValuePairType> nvpList =  entry.getAttribute();
					for (NameValuePairType nvpData : nvpList){
						if (nvpData.getName() != null && nvpData.getName().equalsIgnoreCase("sessionStatus")){
							isSessionStatusFlag = true;
							break;
						}
					}
				}
			}
			if (!isSessionStatusFlag){
				UpdateInvalidOrdersFactory.INSTANCE.updateSalesContextBySession(session,requestResponse);
				String populatedCustomerTrackerOrderId = String.valueOf((Long)session.getAttribute("populatedCustomerTrackerOrderId"));
				String populatedVerizonSmartCartOrderId = (String)(session.getAttribute("populatedVerizonSmartCartOrderId")); 
				if(Utils.isBlank(populatedCustomerTrackerOrderId) || !populatedCustomerTrackerOrderId.equals(String.valueOf(order.getExternalId())))
				{
					insertAndUpdateCustomerTrackerData(session, order);
				}
				try{
					if(Utils.isBlank(populatedVerizonSmartCartOrderId) || !populatedVerizonSmartCartOrderId.equals(Long.toString(order.getExternalId()))){
						Utils.postVZSaveSmartCartProduct(order, session);
					}
				}catch(Exception e){
					logger.info("error occured while post VZ SaveSmartCartProduct "+e.getMessage());
				}

			}
			
		}
		if (session.getAttribute("pauseAndResumeURL")!= null && session.getAttribute("phoneId")!= null){
			RESTClient.INSTANCE.resumeCall(String.valueOf(session.getAttribute("phoneId")), String.valueOf(session.getAttribute("pauseAndResumeURL")));
		}
		
		logger.info(getTime() + " Session_invalidated_Destroyed:ID=" 
				+ session.getId());
	}
	private String getTime()
	{
		return new Date(System.currentTimeMillis()).toString();
	}
	
	private void insertAndUpdateCustomerTrackerData(HttpSession session, OrderType orderType) {
		try{
			CustomerTrackerDao customerTrackerDao = ApplicationContextProvider.getApplicationContext().getBean(CustomerTrackerDao.class);
			try{
					Utils.insertAndUpdateCustomerTrackerData(orderType, session, customerTrackerDao);
				}catch(Exception e){
					logger.info("error occured while inserting the data "+e.getMessage());
				}
		}
		catch(Exception e){
			logger.warn("Error_in_insertAndUpdateCustomerTrackerData"+e.getMessage());
		}
	}
}

