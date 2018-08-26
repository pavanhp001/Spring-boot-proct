package com.A.ui.service.V;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.ui.transport.TransportConfig;
import com.A.V.factory.SalesContextFactory;
import com.A.xml.v4.CcpCommunicationEventType;
import com.A.xml.v4.CcpRequestResponse;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProcessingMessage;
import com.A.xml.v4.SalesContextType;
import com.A.xml.v4.CcpRequestResponse.Request;

public enum CCPService {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(CCPService.class);

	/**
	 * Method to resend email through CCP
	 * 
	 * @param agentId
	 * @param orderId
	 * @param providerId
	 * @param transactionType
	 * @param salesContext
	 * @return
	 */
	public void sendEmail(String agentId, String orderId, String providerId, String transactionType, 
			OrderType order, SalesContextType salesContext, Map<String, Object> ccpMap){
		String guid = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		
		CcpRequestResponse ccprr =  new CcpRequestResponse();
		if(StringUtils.isNotBlank(guid)){
			ccprr.setGUID(guid);
		} else {
			ccprr.setGUID(UUID.randomUUID().toString());	
		}
		
		ccprr.setTransactionType(transactionType);
		ccprr.setSalesContext(salesContext);

		Request request = new CcpRequestResponse.Request();
		request.setOrderManagementRequestResponse(order);
		ccprr.setRequest(request);

		logger.debug("sendEmail, sending ccp request, id: "+orderId);
		CcpRequestResponse ccpRR = TransportConfig.INSTANCE.getCCPClient().send(ccprr);
		List<CcpCommunicationEventType> ccpEventList = new ArrayList<CcpCommunicationEventType>();	
		List<ProcessingMessage> ccpMessagesList = new ArrayList<ProcessingMessage>();	
		
		if(ccpRR != null && ccpRR.getResponse() != null && 
				ccpRR.getResponse().getCommunicationEvents() != null){
				ccpEventList =  ccpRR.getResponse().getCommunicationEvents().getCommunicationEvent();
				
			if( ccpEventList != null && ccpEventList.size() > 0 ){
				ccpMap.put("ccpEventList", ccpEventList);
			} else {
				ccpMessagesList = ccpRR.getStatus().getProcessingMessages().getMessage();
				ccpMap.put("ccpMessagesList", ccpMessagesList);
			}
		}
	}
	
	/**
	 * Method to get Communication Events for a giver order id
	 *  
	 * @param agentId
	 * @param orderId
	 * @param providerId
	 * @param transactionType
	 * @param salesContext
	 * @return
	 */
	public List<CcpCommunicationEventType> getCommunicationEvents(String agentId, String orderId, 
			String providerId, String transactionType, SalesContextType salesContext){
		String guid = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");

		CcpRequestResponse ccprr =  new CcpRequestResponse();
		if(StringUtils.isNotBlank(guid)){
			ccprr.setGUID(guid);
		} else {
			ccprr.setGUID(UUID.randomUUID().toString());	
		}
		ccprr.setTransactionType(transactionType);
		ccprr.setSalesContext(salesContext);

		Request request = new CcpRequestResponse.Request();
		ccprr.setRequest(request);

		logger.debug("getCommunicationEvents, sending ccp request, id: "+orderId);
		CcpRequestResponse ccpRR = TransportConfig.INSTANCE.getCCPClient().send(ccprr);
		
		List<CcpCommunicationEventType> ccpEventList = new ArrayList<CcpCommunicationEventType>();
		if(ccpRR != null && ccpRR.getResponse() != null && 
				ccpRR.getResponse().getCommunicationEvents() != null){
			ccpEventList =  ccpRR.getResponse().getCommunicationEvents().getCommunicationEvent();
		}
		
		return ccpEventList;
	}
	
	/**
	 * Method to resend email through CCP
	 * 
	 * @param id
	 * @return
	 */
	public void sendEmail(Long emailContentId, String transactionType, 
			OrderType order, SalesContextType salesContext, Map<String, Object> ccpMap){
		String guid = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		
		CcpRequestResponse ccprr =  new CcpRequestResponse();
		if(StringUtils.isNotBlank(guid)){
			ccprr.setGUID(guid);
		} else {
			ccprr.setGUID(UUID.randomUUID().toString());	
		}
		
		ccprr.setTransactionType(transactionType);
		ccprr.setSalesContext(salesContext);

		Request request = new CcpRequestResponse.Request();
		request.setOrderManagementRequestResponse(order);
		request.setEmailContentId(emailContentId);
		ccprr.setRequest(request);

		logger.debug("sendEmail, sending ccp request, Email Content Id: "+emailContentId);
		CcpRequestResponse ccpRR = TransportConfig.INSTANCE.getCCPClient().send(ccprr);
		List<CcpCommunicationEventType> ccpEventList = new ArrayList<CcpCommunicationEventType>();	
		List<ProcessingMessage> ccpMessagesList = new ArrayList<ProcessingMessage>();	
		
		if(ccpRR != null && ccpRR.getResponse() != null && 
				ccpRR.getResponse().getCommunicationEvents() != null){
				ccpEventList =  ccpRR.getResponse().getCommunicationEvents().getCommunicationEvent();
				
			if( ccpEventList != null && ccpEventList.size() > 0 ){
				ccpMap.put("ccpEventList", ccpEventList);
			} else {
				ccpMessagesList = ccpRR.getStatus().getProcessingMessages().getMessage();
				ccpMap.put("ccpMessagesList", ccpMessagesList);
			}
		}
	}
}
