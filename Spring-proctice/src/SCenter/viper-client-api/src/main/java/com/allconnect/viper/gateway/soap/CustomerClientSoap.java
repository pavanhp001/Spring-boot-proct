package com.A.V.gateway.soap;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.util.DateUtil;
import com.A.V.gateway.ClientService;
import com.A.V.gateway.CustomerClient;
import com.A.V.gateway.soap.client.CustomerWebService;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.cm.v4.AccountHolderSearch;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerManagementWS;
import com.A.xml.cm.v4.CustomerSearch;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.CustomerManagementRequestResponse.PagingDetail;
import com.A.xml.cm.v4.CustomerManagementRequestResponse.Request;

public class CustomerClientSoap extends
		BaseClientSoap<CustomerManagementRequestResponse> implements
		ClientService<CustomerManagementRequestResponse>, CustomerClient {
	
	public static final Logger logger = Logger.getLogger(CustomerClientSoap.class);

	public JaxbUtil<CustomerManagementRequestResponse> jaxbUtil = new JaxbUtil<CustomerManagementRequestResponse>();

	public CustomerManagementRequestResponse send(
			CustomerManagementRequestResponse order) {

		CustomerWebService srv = new CustomerWebService();
		CustomerManagementWS port = srv.getCustomerManagementWSPort();

		Holder<CustomerManagementRequestResponse> holder = new Holder<CustomerManagementRequestResponse>();
		holder.value = order;

		String response = port.processRequest(jaxbUtil.toString(holder.value,
				CustomerManagementRequestResponse.class));

		logger.info(response);

		String filteredResponse = extract(response);
		return jaxbUtil.toObject(filteredResponse,
				CustomerManagementRequestResponse.class);

	}

	public String extract(String orderRR) {

		orderRR = orderRR
				.replace(
						"<customerManagementRequestResponse>",
						"<customerManagementRequestResponse xmlns=\"http://xml.A.com/v4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		int indexStart = orderRR.indexOf("<customerManagementRequestResponse");
		int indexEnd = orderRR.indexOf("</payload>");

		if (indexEnd == -1) {
			indexEnd = orderRR.indexOf("</xml-fragment>");
		}

		if ((indexStart != -1) && (indexEnd != -1)) {

			return orderRR.substring(indexStart, indexEnd);
		}

		throw new IllegalArgumentException("invalid AC Response");

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

		cmRequest.setPhoneNumber(map.get("phone"));
		cmRequest.setFirstName(map.get("firstname"));
		cmRequest.setLastName(map.get("lastname"));
		cmRequest.setPartialSSN(map.get("partialSSN"));
		cmRequest.setState(map.get("state"));
		cmRequest.setCity(map.get("city"));
		cmRequest.setZipcode(map.get("zipcode"));
		cmRequest.setCustomerNumber(map.get("customer_number"));
		cmRequest.setConfirmationNumber(map.get("confirmationNumber"));

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

		CustomerManagementRequestResponse cmrrR = send(cmrr);
		CustomerSearch customerSearchResult = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null)) {
			customerSearchResult = cmrrR.getResponse().getCustomerSearchResult();
		}

		return customerSearchResult;
	}

	@Override
	public void sendAsync(CustomerManagementRequestResponse cmrr) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public AccountHolderSearch searchAccntCustomerInfo(Map<String, String> map,
			int offset) {

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

		CustomerManagementRequestResponse cmrrR = send(cmrr);
		AccountHolderSearch accntHolderSearchResult = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null) && 
				cmrrR.getResponse().getCustomerSearchResult() != null) {
			accntHolderSearchResult = cmrrR.getResponse().getAccountHolderSearchResult();
			logger.info("SearchCustomer: result size ..... "+accntHolderSearchResult.getSearchResult().size());
		}

		return accntHolderSearchResult;
	}
}
