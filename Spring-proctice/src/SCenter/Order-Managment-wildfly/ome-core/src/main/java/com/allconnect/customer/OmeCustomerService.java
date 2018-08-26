package com.AL.customer;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.ome.OmeCustomerCommunicator;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.util.XmlUtil;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.CustomerInteraction;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.CustomerInteractionDao;
import com.AL.xml.v4.AddressListType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.CustBillingInfoType;
import com.AL.xml.v4.CustomerContextEntityType;
import com.AL.xml.v4.CustomerContextType;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.TransactionType;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

@Component
public class OmeCustomerService {

	private static final String UPDATE_CUST_ACTION = "updateCustomer";

	private static final String ADD_ADDRESS_ACTION = "addAddress";

	private static final String CREATE_CUST_ACTION = "createCustomer";

	private static final Logger logger = Logger
			.getLogger(OmeCustomerService.class);

	private static final String EXTERNAL_ID = "externalId";
	private static final String INVALID_CUSTOMER = "INVALID_CUSTOMER";

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerInteractionDao customerInteractionDao;

	/**
	 * Helper method to prepare the request for Customer Service, submit the
	 * request to CM service and return the response received from CM.
	 *
	 * @param params
	 * @param customerSrc
	 * @param customerAction
	 * @return
	 */
	public CustomerManagementRequestResponseDocument processCustomer(
			OrchestrationContext params, CustomerType customerSrc,
			String customerAction) {
		logger.info("Sending request to customer mgmt.");;
		CustomerManagementRequestResponseDocument customerRequest;
		customerRequest = prepareCustomer(customerSrc, customerAction,params);
		CustomerManagementRequestResponseDocument customerResponse = null;
		if(params.get(INVALID_CUSTOMER) == null){
			customerResponse = submitCustomerRequest(customerRequest);
		}

		Long customerExternalId = Long.valueOf(-1);

		if ((customerResponse == null) || (customerResponse.getCustomerManagementRequestResponse() == null) || (customerResponse
						.getCustomerManagementRequestResponse().getResponse() == null) || (customerResponse
								.getCustomerManagementRequestResponse().getResponse()
								.getCustomerInfoArray() == null) || (customerResponse
										.getCustomerManagementRequestResponse().getResponse()
										.getCustomerInfoArray().length == 0)|| (customerResponse
										.getCustomerManagementRequestResponse().getResponse()
										.getCustomerInfoArray()[0] == null) ) {

			throw new IllegalArgumentException("Customer operation unsuccessful");
		}

		if (customerResponse != null){
			customerExternalId = Long.valueOf(customerResponse
					.getCustomerManagementRequestResponse().getResponse()
					.getCustomerInfoArray()[0].getExternalId());
		}
		ResponseItemOme omeParams = (ResponseItemOme) params;
		omeParams.add("customerExternalId", customerExternalId);
		omeParams.add("customerResponse", customerResponse);

		return customerResponse;
	}

	/**
	 * Helper method to send the request to Customer Service
	 *
	 * @param customerRequest
	 * @return
	 */
	private CustomerManagementRequestResponseDocument submitCustomerRequest(
			CustomerManagementRequestResponseDocument customerRequest) {

		CustomerManagementRequestResponseDocument response = OmeCustomerCommunicator
				.submitCustomer(customerRequest.toString());
		return response;
	}

	/**
	 * Preparing CustomerRequestResponseDocument with proper transaction type
	 *
	 * @param customerSrc
	 * @param customerAction
	 * @return
	 */
	public CustomerManagementRequestResponseDocument prepareCustomer(
			final CustomerType customerSrc, String customerAction,OrchestrationContext params) {
		CustomerManagementRequestResponseDocument custReqResDoc = CustomerManagementRequestResponseDocument.Factory
				.newInstance();
		CustomerManagementRequestResponse reqRes = custReqResDoc
				.addNewCustomerManagementRequestResponse();
		CustomerManagementRequestResponse.Request req = reqRes.addNewRequest();

		if (customerAction.equalsIgnoreCase(CREATE_CUST_ACTION)) {
			if(params.get(TaskContextParamEnum.salesContext.name()) != null){
				populateSaleContextToCustomerContext(params, req);


			}
			reqRes.setTransactionType(TransactionType.CREATE_CUSTOMER);
		} else if (customerAction.equalsIgnoreCase(UPDATE_CUST_ACTION)) {
			reqRes.setTransactionType(TransactionType.UPDATE_CUSTOMER);
			reqRes.getRequest().setCustomerId(String.valueOf(customerSrc.getExternalId()));
			//Check provided customer exist or not
			Consumer consumer = getExistingConsumer(customerSrc);
			if(consumer == null){
				params.add(INVALID_CUSTOMER, "Customer does not exist");
			}
		} else if (customerAction.equalsIgnoreCase(ADD_ADDRESS_ACTION)) {
			reqRes.setTransactionType(TransactionType.ADD_ADDRESS);
			reqRes.getRequest().setCustomerId(String.valueOf(customerSrc.getExternalId()));
			//Check provided customer exist or not
			Consumer consumer = getExistingConsumer(customerSrc);
			if(consumer == null){
				params.add(INVALID_CUSTOMER, "Customer does not exist");
			}
		}


		req.setCustomerInfo(customerSrc);
		return custReqResDoc;
	}

	private void populateSaleContextToCustomerContext(
			OrchestrationContext params,
			CustomerManagementRequestResponse.Request req) {
		SalesContextType scType = (SalesContextType)params.get(TaskContextParamEnum.salesContext.name());
		List<SalesContextEntityType> entityList = scType.getEntityList();
		CustomerContextType custCtxType = req.addNewCustomerContext();
		for(SalesContextEntityType scEntity : entityList){
			CustomerContextEntityType ccEntityType = custCtxType.addNewEntity();
			ccEntityType.setName(scEntity.getName());
			ccEntityType.setAttributeArray(scEntity.getAttributeArray());
		}
	}

	/**
	 * Helper method to update Address Ext Id and Billing Ext ID from Customer Response to Order Document at Lineitem level
	 */
	public void updateLineitemInfo(
			CustomerManagementRequestResponseDocument customerResponse,
			OrderManagementRequestResponseDocument orderDocument) {
		logger.info("Updating lineitem references with generated ext. ids");
		if (customerResponse != null && customerResponse.getCustomerManagementRequestResponse().getResponse() != null ) {
			OrderType destOrderType = orderDocument.getOrderManagementRequestResponse().getRequest().getOrderInfo();
			if (destOrderType.getLineItems() != null) {
				List<LineItemType> destLineitemList = destOrderType
						.getLineItems().getLineItemList();
				CustomerType srcCustomerType = customerResponse
						.getCustomerManagementRequestResponse().getResponse()
						.getCustomerInfoList().get(0);
				AddressListType srcAddressListType = srcCustomerType
						.getAddressList();
				List<CustAddress> srcCustAddressList = srcAddressListType
						.getCustomerAddressList();
				List<CustBillingInfoType> srcBillingList = srcCustomerType
						.getBillingInfoList().getBillingInfoList();
				if (srcCustAddressList != null && destLineitemList != null
						&& srcBillingList != null) {
					for (LineItemType destLineItem : destLineitemList) {
						//Updating Service address External id for referenced Service Address in Customer
						for (CustAddress srcCustAddress : srcCustAddressList) {
							String addressUniqueId = srcCustAddress
									.getAddressUniqueId();
							String addressExtId = String.valueOf(srcCustAddress
									.getAddress().getExternalId());
							if (destLineItem.getSvcAddressRef()
									.equalsIgnoreCase(addressUniqueId)) {
								destLineItem.setSvcAddressExtId(addressExtId);
								break;
							}
						}
						//Updating Billing External id for referenced Billing information in Customer
						for (CustBillingInfoType srcBillingInfo : srcBillingList) {
							String billingUniqueId = srcBillingInfo
									.getBillingUniqueId();
							String billingExtId = String.valueOf(srcBillingInfo
									.getExternalId());
							if (destLineItem.getBillingInfoRef()
									.equalsIgnoreCase(billingUniqueId)) {
								destLineItem.setBillingInfoExtId(billingExtId);
								break;
							}
						}
					}
				}
			}
		}

	}

	public Consumer getExistingConsumer(CustomerType src){
		logger.info("Locating existing customer");
		Consumer consumer = null;
		boolean isExtIDEmpty = XmlUtil.isElementNil(src.newCursor(),EXTERNAL_ID);
		long externalId = 0;
		if(!isExtIDEmpty ){
			externalId = src.getExternalId();
			if(externalId > 0){
				logger.info("Searching customer by ext. id : " + externalId);
				consumer = customerDao.findCustomerByExternalId(externalId);
			}
		}
		return consumer;
	}

	/**
	 * Method to add consumer interaction information. For eg. Who created the customer , when it was created, adding a special notes
	 * when Customer contact Agent etc
	 * @param ci
	 */
	public void addConsumerInteraction(String agentId, SalesOrder so, LineItem li, String notes){
		logger.info("Preparing Customer Interaction");
		addConsumerInteraction(agentId, so, so.getSource(), li, notes);
	}
	
	/**
	 * Method to add consumer interaction information. For eg. Who created the customer , when it was created, adding a special notes
	 * when Customer contact Agent etc
	 * @param ci
	 */
	public void addConsumerInteraction(String agentId, SalesOrder so, String source, LineItem li, String notes){
		logger.info("Preparing Customer Interaction");
		CustomerInteraction ci = new CustomerInteraction();
		ci.setAgentExternalId(agentId);
		ci.setSource(source);
		ci.setOrderExternalId(so.getExternalId());
		ci.setNotes(notes);
		ci.setCustomerExternalId(so.getConsumerExternalId());
		ci.setDateOfInteraction(Calendar.getInstance());

		long lineItemExternalId = li.getExternalId();
		ci.setLineItemExternalId(lineItemExternalId);

		// Use this method to get the product category display name, rather to fire DB
		ci.setServiceType(getProductCategoryDisplayName(li));
		
		if(li.getProviderExternalId() != null){
			Long providerId = Long.valueOf(li.getProviderExternalId());
			ci.setProviderId(providerId);
		}
		customerInteractionDao.saveConsumerInteraction(ci);
	}


	public void addConsumerInteraction(String agentId, SalesOrder so, String notes){
		addConsumerInteraction(agentId, so, so.getSource(), notes);
	}

	public void addConsumerInteraction(String agentId, SalesOrder so, String source, String notes){
		CustomerInteraction ci = new CustomerInteraction();
		ci.setAgentExternalId(agentId);
		ci.setSource(source);
		ci.setOrderExternalId(so.getExternalId());
		ci.setNotes(notes);
		ci.setCustomerExternalId(so.getConsumerExternalId());
		ci.setDateOfInteraction(Calendar.getInstance());

		if ((so.getLineItems() != null) && (so.getLineItems().size() > 0)) {
			long lineItemExternalId = so.getLineItems().get(0).getExternalId();
			ci.setLineItemExternalId(lineItemExternalId);
			
			// Use this method to get the product category display name, rather to fire DB
			ci.setServiceType(getProductCategoryDisplayName(so.getLineItems().get(0)));
			
			if(so.getLineItems().get(0).getProviderExternalId() != null){
				Long providerId = Long.valueOf(so.getLineItems().get(0).getProviderExternalId());
				ci.setProviderId(providerId);
			}
		}
		customerInteractionDao.saveConsumerInteraction(ci);
	}
	
	/**
	 * Adding Customer Interaction
	 * 
	 * @param agentId
	 * @param so
	 * @param lineItemId
	 * @param source
	 * @param notes
	 */
	public void addConsumerInteraction(String agentId, SalesOrder so, Long lineItemId, String source, String notes){
		CustomerInteraction ci = new CustomerInteraction();
		ci.setAgentExternalId(agentId);
		ci.setSource(source);
		ci.setOrderExternalId(so.getExternalId());
		ci.setNotes(notes);
		ci.setCustomerExternalId(so.getConsumerExternalId());
		ci.setDateOfInteraction(Calendar.getInstance());

		if ((so.getLineItems() != null) && (so.getLineItems().size() > 0)) {
			for(LineItem li: so.getLineItems()){
				if(li.getExternalId().equals(lineItemId)){
					ci.setLineItemExternalId(li.getExternalId());
					
					// Use this method to get the product category display name, rather to fire DB
					ci.setServiceType(getProductCategoryDisplayName(li));
					
					if(li.getProviderExternalId() != null){
						Long providerId = Long.valueOf(li.getProviderExternalId());
						ci.setProviderId(providerId);
					}
				}
			}
		}
		customerInteractionDao.saveConsumerInteraction(ci);
	}

	public Consumer getCustomer(Long externalId)
	{
		return  customerDao.findCustomerByExternalId(externalId);
	}
	
	/**
	 * Use this method to get the product category display name, rather to fire DB
	 * @param lineitem
	 * @return productCategoryDisplayName
	 */
	private String getProductCategoryDisplayName(LineItem lineitem){
		String productCategoryDisplayName = null;
		if(null != lineitem.getLineItemDetail() && null != lineitem.getLineItemDetail().getCategory()){
			productCategoryDisplayName = lineitem.getLineItemDetail().getCategory();
		}
		return productCategoryDisplayName;
	}
}
