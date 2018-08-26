package com.A.V.gateway.jms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.util.JMSConfigManager;
import com.A.util.DateUtil;
import com.A.V.factory.CustomerFactory;
import com.A.V.gateway.ClientService;
import com.A.V.gateway.CustomerClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerManagementRequestResponse.PagingDetail;
import com.A.xml.cm.v4.AccountHolderSearch;
import com.A.xml.cm.v4.CustomerSearch;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.CustomerManagementRequestResponse.Request;

public class CustomerClientJMS extends
		BaseClientJMS<CustomerManagementRequestResponse> implements
		ClientService<CustomerManagementRequestResponse>, CustomerClient {

	private static final Logger logger = Logger
			.getLogger(CustomerClientJMS.class);
	private static final int TIMEOUT = 60000;
	//TODO: Place these into system properties
	private final String CUSTOMER_NAMESPACE = "jms";
	private final String GU_ID = "GUID";
	private final String END_POINT_NAME = "endpoint.customer.in";

	private final JaxbUtil<CustomerManagementRequestResponse> util = new JaxbUtil<CustomerManagementRequestResponse>();

	private final CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(CUSTOMER_NAMESPACE);

	//TODO: rename sendCMRR to send and remove the
	//below method after BETA, agent id should not be set to default
	public CustomerManagementRequestResponse send(
			CustomerManagementRequestResponse cmrr) {
		logger.debug("invoked CustomerClientJMS: send(CustomerManagementRequestResponse cmrr)...");
		if ((cmrr != null) && (cmrr.getRequest() != null) && ((cmrr.getRequest().getAgentId() == null) ||(cmrr.getRequest().getAgentId().length() == 0) )) {
			cmrr.getRequest().setAgentId("default");
		}

		return sendCMRR(cmrr);
	}
	
	private CustomerManagementRequestResponse sendCMRR(
			CustomerManagementRequestResponse cmrr) {
		TextMessage responseText = null;
		String cmrrAsString = util.toString(cmrr, CustomerManagementRequestResponse.class);

		CustomerManagementRequestResponse response =  null;

		try {
			logger.info("CMRR JMS Request[ID: "+cmrr.getCorrelationId()+"] :: "+cmrrAsString);
			
			Map<String,String> headers = new HashMap<String,String>();
			if(cmrr.getRequest().getCustomerContext() != null){
				String guid = CustomerFactory.INSTANCE.getAttribute(cmrr.getRequest().getCustomerContext(), GU_ID);
				headers.put(GU_ID, guid);
			}
			
			if(StringUtils.isBlank(headers.get(GU_ID))) {
				logger.info("GUID is empty in CustomerContext, sending customerId as GUID to Comm Manager ........ ");
				headers.put(GU_ID, cmrr.getRequest().getCustomerId());
			}

			long currentTime = System.currentTimeMillis();
			responseText = (TextMessage) commManager.sendSync(END_POINT_NAME, cmrrAsString, TIMEOUT,headers);
			
			logger.info("CMRR JMS Response[ID: "+cmrr.getCorrelationId()+"][Time: "+(System.currentTimeMillis() - currentTime) + "ms] :: "+responseText.getText());

			if (responseText != null) {
				response = util.toObject(extract(responseText.getText()), CustomerManagementRequestResponse.class);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return response;
	}

	public String extract(String custRR) {

		custRR = custRR
				.replace(
						"<customerManagementRequestResponse>",
						"<customerManagementRequestResponse xmlns=\"http://xml.A.com/v4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		int indexStart = custRR.indexOf("<customerManagementRequestResponse");
		int indexEnd = custRR.indexOf("</payload>");

		if (indexEnd == -1) {
			indexEnd = custRR.indexOf("</xml-fragment>");
		}

		if ((indexStart != -1) && (indexEnd != -1)) {

			return custRR.substring(indexStart, indexEnd);
		}
		logger.debug("CustomeClientJMS: send(): extract(responseFromRTIM.getText()....."+custRR);
		return custRR;
	}

	public List<CustomerType> locateCustomer(Map<String, String> map) {

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId(1);
		cmrr.setTransactionType("locateCustomer");

		Request cmRequest = cmrr.getRequest();
		String agentId = map.get("agentId");
		if(StringUtils.isNotEmpty(agentId)){
			cmRequest.setAgentId(agentId);
		} else {
			cmRequest.setAgentId("default");
		}
		
		cmRequest.setPhoneNumber(map.get("phone"));
		cmRequest.setFirstName(map.get("firstname"));
		cmRequest.setLastName(map.get("lastname"));
		cmRequest.setPartialSSN(map.get("partialSSN"));
		cmRequest.setState(map.get("state"));
		cmRequest.setCity(map.get("city"));
		cmRequest.setZipcode(map.get("zipcode"));
		cmRequest.setStreetAddress(map.get("streetName"));
		cmRequest.setCustomerNumber(map.get("customer_number"));
		cmRequest.setConfirmationNumber(map.get("confirmationNumber"));
		cmRequest.setEmailId(map.get("email"));

		// order
		// confirm
		CustomerManagementRequestResponse cmrrR = send(cmrr);
		List<CustomerType> customerTypeList = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null)) {
			customerTypeList = cmrrR.getResponse().getCustomerInfo();
		}

		return customerTypeList;
	}
	
	/**
	 * Search Customer
	 * 
	 * @param map
	 * @param offset
	 * @return
	 */
	public CustomerSearch searchCustomer(Map<String, String> map, int offset) {

		logger.info("SearchCustomer: invoked ..... ");
		
		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId(1);
		cmrr.setTransactionType("searchCustomer");
		
		if(offset > 0){
			PagingDetail pageDetail = oFactory.createCustomerManagementRequestResponsePagingDetail();
			pageDetail.setOffSet(offset);
			cmrr.setPagingDetail(pageDetail);
		}
		
		Request cmRequest = cmrr.getRequest();
		cmRequest.setPhoneNumber(map.get("phone"));
		cmRequest.setFirstName(map.get("firstname"));
		cmRequest.setLastName(map.get("lastname"));
		cmRequest.setPartialSSN(map.get("partialSSN"));
		cmRequest.setState(map.get("state"));
		cmRequest.setCity(map.get("city"));
		cmRequest.setZipcode(map.get("zipcode"));
		cmRequest.setStreetAddress(map.get("streetName"));
		cmRequest.setCustomerNumber(map.get("customer_number"));
		cmRequest.setConfirmationNumber(map.get("confirmationNumber"));
		cmRequest.setEmailId(map.get("email"));
		
		String provider = map.get("provider");
		if(StringUtils.isNotEmpty(provider)){
			cmRequest.setProviderExtId(provider);
		}
		
		String orderStartDate = map.get("orderStartDate");
		if(StringUtils.isNotBlank(orderStartDate)){
			cmRequest.setOrderStartDate(DateUtil.asXMLGregorianCalendarDate(orderStartDate, "MM/dd/yyyy"));
		}
		
		String orderEndDate = map.get("orderEndDate");
		if(StringUtils.isNotBlank(orderEndDate)){
			cmRequest.setOrderEndDate(DateUtil.asXMLGregorianCalendarDate(orderEndDate, "MM/dd/yyyy"));
		}
		
		cmRequest.setAgentId(map.get("agentId"));
		cmRequest.setReferrerId(map.get("referrer"));

		CustomerManagementRequestResponse cmrrR = sendCMRR(cmrr);
		CustomerSearch customerSearchResult = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null) && 
				cmrrR.getResponse().getCustomerSearchResult() != null) {
			customerSearchResult = cmrrR.getResponse().getCustomerSearchResult();
			logger.info("SearchCustomer: result size ..... "+customerSearchResult.getSearchResult().size());
		}

		return customerSearchResult;
	}


	/**
     * This method will send CM request asynchronously. So use this method only if you don't need res back from CM. 
     * This method can be used in Salescenter to send aynch request like updating customer etc.
     *
     *
     */
	
	@Override
	public void sendAsync(CustomerManagementRequestResponse cmrr) {
		// TODO Auto-generated method stub
		
		
		if( null == cmrr)
            throw new IllegalArgumentException("Request cannot be null!!!");
		

		try {
			
			String cmrrAsString = util.toString(cmrr, CustomerManagementRequestResponse.class);
			logger.info("CMRR JMS Request[ID: "+cmrr.getCorrelationId()+"] :: "+cmrrAsString);
			
			Map<String,String> headers = new HashMap<String,String>();
			if(cmrr.getRequest().getCustomerContext() != null){
				String guid = CustomerFactory.INSTANCE.getAttribute(cmrr.getRequest().getCustomerContext(), GU_ID);
				headers.put(GU_ID, guid);
			}
			
			if(StringUtils.isBlank(headers.get(GU_ID))) {
				logger.info("GUID is empty in CustomerContext, sending customerId as GUID to Comm Manager ........ ");
				headers.put(GU_ID, cmrr.getRequest().getCustomerId());
			}
			logger.info("Sending async request to CM");
			
			commManager.send(END_POINT_NAME, cmrrAsString, headers);
			
			logger.info("Async request to CM completed");
		} catch (Exception e) {
			 logger.error("Exception occured while sending async request to CM.", e);
		}
	}

	
	/**
	 * Search Customer and Account Holder based on flag isAccountHolderSearch
	 * 
	 * @param map
	 * @param offset
	 * @return
	 */
	public AccountHolderSearch searchAccntCustomerInfo(Map<String, String> map, int offset) {

		logger.info("SearchCustomer: invoked ..... ");
		
		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId(1);
		cmrr.setTransactionType("searchCustomer");
		
		if(offset > 0){
			PagingDetail pageDetail = oFactory.createCustomerManagementRequestResponsePagingDetail();
			pageDetail.setOffSet(offset);
			cmrr.setPagingDetail(pageDetail);
		}
		
		Request cmRequest = cmrr.getRequest();
		cmRequest.setPhoneNumber(map.get("phone"));
		cmRequest.setFirstName(map.get("firstname"));
		cmRequest.setLastName(map.get("lastname"));
		cmRequest.setPartialSSN(map.get("partialSSN"));
		cmRequest.setState(map.get("state"));
		cmRequest.setCity(map.get("city"));
		cmRequest.setZipcode(map.get("zipcode"));
		cmRequest.setStreetAddress(map.get("streetName"));
		cmRequest.setCustomerNumber(map.get("customer_number"));
		cmRequest.setConfirmationNumber(map.get("confirmationNumber"));
		cmRequest.setEmailId(map.get("email"));
		
		String provider = map.get("provider");
		if(StringUtils.isNotEmpty(provider)){
			cmRequest.setProviderExtId(provider);
		}
		
		String orderStartDate = map.get("orderStartDate");
		if(StringUtils.isNotBlank(orderStartDate)){
			cmRequest.setOrderStartDate(DateUtil.asXMLGregorianCalendarDate(orderStartDate, "MM/dd/yyyy"));
		}
		
		String orderEndDate = map.get("orderEndDate");
		if(StringUtils.isNotBlank(orderEndDate)){
			cmRequest.setOrderEndDate(DateUtil.asXMLGregorianCalendarDate(orderEndDate, "MM/dd/yyyy"));
		}
		
		String iSAccountHolderSearch = map.get("isAchSearch");
		if(StringUtils.isNotEmpty(iSAccountHolderSearch)){
			cmRequest.setIsAccountHolderSearch(iSAccountHolderSearch);
		}
		
		cmRequest.setAgentId(map.get("agentId"));
		cmRequest.setReferrerId(map.get("referrer"));

		CustomerManagementRequestResponse cmrrR = sendCMRR(cmrr);
		AccountHolderSearch accntHolderSearchResult = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null) && 
				cmrrR.getResponse().getAccountHolderSearchResult() != null) {
			accntHolderSearchResult = cmrrR.getResponse().getAccountHolderSearchResult();
			logger.info("SearchCustomer: result size ..... "+accntHolderSearchResult.getSearchResult().size());
		}

		return accntHolderSearchResult;
	}
}
