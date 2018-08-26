package com.A.ui.service.V;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.A.thread.JobUpdateThread;
import com.A.ui.constants.Constants;
import com.A.ui.service.V.impl.CustomerCacheService;
import com.A.ui.service.V.impl.CustomerContextCacheService;
import com.A.ui.service.V.impl.CustomerInteractionCacheService;
import com.A.ui.transport.TransportConfig;
import com.A.ui.vo.ErrorList;
import com.A.V.factory.CustomerFactory;
import com.A.xml.cm.v4.AccountHolderSearch;
import com.A.xml.cm.v4.Accounts;
import com.A.xml.cm.v4.AddressListType;
import com.A.xml.cm.v4.AddressType;
import com.A.xml.cm.v4.BillingInfoList;
import com.A.xml.cm.v4.CustBillingInfoType;
import com.A.xml.cm.v4.CustomerContextType;
import com.A.xml.cm.v4.CustomerInteractionList;
import com.A.xml.cm.v4.CustomerInteractionType;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerSearch;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.DriverLicenseType;
import com.A.xml.cm.v4.NotificationEventCollectionType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.PaymentEventType;

public enum CustomerService {
	
	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(CustomerService.class);	

	public CustomerInteractionList getCustomerInteractionList(
			final ObjectFactory oFactory,
			final CustomerManagementRequestResponse cmrr) {

		if (cmrr.getRequest().getCustomerInfo() == null) {
			cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
		}

		if (cmrr.getRequest().getCustomerInfo().getCustomerInteractionList() == null) {
			cmrr.getRequest()
					.getCustomerInfo()
					.setCustomerInteractionList(
							oFactory.createCustomerInteractionList());
		}

		return cmrr.getRequest().getCustomerInfo().getCustomerInteractionList();
	}

	public List<CustomerType> locateCustomer(Map<String, String> map) {

		Set<String> keys = map.keySet();

		for (String key : keys) {
			String data = map.get(key);

			if ((data == null) || (data.length() == 0)) {
				map.put(key, "");
			}
		}

		List<CustomerType> customers = TransportConfig.INSTANCE
				.getCustomerClient().locateCustomer(map);

		return customers;

	}
	
	public CustomerSearch searchCustomer(Map<String, String> map, int offset) {

		Set<String> keys = map.keySet();

		for (String key : keys) {
			String data = map.get(key);
			if ((data == null) || (data.length() == 0)) {
				map.put(key, "");
			}
		}

		CustomerSearch custSearchResult = TransportConfig.INSTANCE.getCustomerClient().searchCustomer(map, offset);
		
		return custSearchResult;
	}

	/**
	 * Get Address with Customer externalId
	 * 	and Address externalId
	 * 
	 * @param id
	 * @param addressId
	 * @return
	 */
	public AddressType getAddressById(final String id, final String addressId) {

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("getAddressById");

		if (id != null) {
			cmrr.getRequest().setCustomerId(id);
		}

		if (addressId != null) {
			cmrr.getRequest().setAddressId(addressId);
		}

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getCustomerClient().send(cmrr);

		List<AddressType> addressTypeList = cmrrR.getResponse()
				.getAddressInfo();
		AddressType address = null;

		if (addressTypeList.size() > 0) {
			address = addressTypeList.get(0);
		}

		return CustomerFactory.INSTANCE.checkAddressCaseForCustomer(address);
	}
	
	/**
	 * Add Provider Account to Customer
	 * 
	 * @param agentId
	 * @param customerId
	 * @param accounts
	 * @return
	 */
	@Deprecated
	public CustomerType addAccount(final String agentId,
			final String customerId, final Accounts accounts){
		return addAccount(agentId, customerId, accounts, null);
	}

	/**
	 * Add Provider Account to Customer
	 * 
	 * @param agentId
	 * @param customerId
	 * @param accounts
	 * @param customerContext
	 * @return
	 */
	public CustomerType addAccount(final String agentId,
			final String customerId, final Accounts accounts, CustomerContextType customerContext) {

		ObjectFactory oFactory = new ObjectFactory();
		CustomerType customer = oFactory.createCustomerType();
		customer.setAgentId(agentId);
		customer.setAccounts(accounts);
		
		return submitCustomerType(agentId, customerId, "updateCustomer", customer, null, customerContext);
	}
	
	/**
	 * Create Customer
	 * 
	 * @param custInfo
	 * @param addressList
	 * @param billingList
	 * @return
	 */
	@Deprecated
	public CustomerType createCustomer(final CustomerType custInfo,
			final AddressListType addressList, final BillingInfoList billingList){
		return createCustomer(custInfo, addressList, billingList, null);
	}

	/**
	 * Create Customer
	 * 
	 * @param custInfo
	 * @param addressList
	 * @param billingList
	 * @param customerContext
	 * @return
	 */
	public CustomerType createCustomer(final CustomerType custInfo,
			final AddressListType addressList, final BillingInfoList billingList, CustomerContextType customerContext) {

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("createCustomer");

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		cmrr.getRequest().setCustomerId("0");
		cmrr.getRequest().setCustomerNumber("0");
		cmrr.getRequest().setCustomerInfo(custInfo);
		custInfo.setBillingInfoList(billingList);
		custInfo.setAddressList(addressList);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse()
				.getCustomerInfo();
		CustomerType customer = null;

		if (customerTypeList.size() > 0) {
			customer = customerTypeList.get(0);
		}
		
		customer = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customer);
		CustomerCacheService.INSTANCE.store(customer, customer.getExternalId()
				+ "");
		return customer;
	}

	public CustomerType getCustomerTypeByCustomerNumber(
			final String customerNumber) {

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("getCustomerByCustomerNum");

		if (customerNumber != null) {
			cmrr.getRequest().setCustomerNumber(customerNumber);
		}

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse()
				.getCustomerInfo();
		CustomerType customer = null;

		if (customerTypeList.size() > 0) {
			customer = customerTypeList.get(0);
			//Filtering deleted billing info's and associated payment events
			List<String> inActiveBillingIdList = filterInActiveBillingInfo(customer);
			filterInActivePayments(customer, inActiveBillingIdList);
		}

		return CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customer);
	}

	/**
	 * To get CustomerContextType using Customer externalId
	 * 
	 * @param id
	 * @return
	 */
	public CustomerContextType getCustomerContextType(final String id) {
		CustomerContextType customerContext = CustomerContextCacheService.INSTANCE.get(id);
		if(customerContext == null){
			getCustomerType(id);
			customerContext = CustomerContextCacheService.INSTANCE.get(id);
		}
		
		return customerContext;
	}
	
	/**
	 * Get Customer with externalId
	 * 
	 * @param id
	 * @return
	 */
	public CustomerType getCustomerType(final String id) {

		long currentTime = System.currentTimeMillis();
		logger.info("GetCustomerType: fetching customer : "+id);
		CustomerType ct = CustomerCacheService.INSTANCE.get(id);
		if (ct == null) {
			ObjectFactory oFactory = new ObjectFactory();
			CustomerManagementRequestResponse cmrr = oFactory
					.createCustomerManagementRequestResponse();
			cmrr.setRequest(oFactory
					.createCustomerManagementRequestResponseRequest());
			cmrr.setCorrelationId(UUID.randomUUID().toString());
			cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
			cmrr.setTransactionType("getCustomerById");

			if (id != null) {
				cmrr.getRequest().setCustomerId(id);
			}

			CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
					.getCustomerClient().send(cmrr);
			
			if ((cmrrR != null) && (cmrrR.getResponse() != null)
					&& (cmrrR.getResponse().getCustomerInfo() != null)) {
				/* Caching not needed, so commented
				 * if(cmrrR.getResponse().getCustomerContext() != null){
					CustomerContextCacheService.INSTANCE.store(cmrrR.getResponse().getCustomerContext(), id);
				} else {
					CustomerContextCacheService.INSTANCE.store(oFactory.createCustomerContextType(), id);
				}*/

				List<CustomerType> customerTypeList = cmrrR.getResponse()
						.getCustomerInfo();

				if (customerTypeList.size() > 0) {
					ct = customerTypeList.get(0);
					
					//Filtering deleted billing info's and associated payment events
					List<String> inActiveBillingIdList = filterInActiveBillingInfo(ct);
					filterInActivePayments(ct, inActiveBillingIdList);
				}
			}
			
			ct = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(ct);
			CustomerCacheService.INSTANCE.store(ct, id);
		}
		logger.info("GetCustomerType: returnedCustomer="+id+" totalTime="+(System.currentTimeMillis() - currentTime) + " ms");
		return ct;
	}

	@Deprecated
	public void saveCustomerId(String id, String occupation,
			String businessName, String ssn, String dob, String stateIdNumber,
			String driverLicenseState, String licenseNumber) {
		saveCustomerId("default", id, occupation, businessName, ssn, 
				dob, stateIdNumber, driverLicenseState, licenseNumber);
	}
	
	@Deprecated
	public void saveCustomerId(String agentId, String id, String occupation,
			String businessName, String ssn, String dob, String stateIdNumber,
			String driverLicenseState, String licenseNumber){
		saveCustomerId(agentId, id, occupation, businessName, ssn, 
				dob, stateIdNumber, driverLicenseState, licenseNumber, null);
	}

	/**
	 * Save Customer Identification Info
	 * 
	 * @param agentId
	 * @param id
	 * @param occupation
	 * @param businessName
	 * @param ssn
	 * @param dob
	 * @param stateIdNumber
	 * @param driverLicenseState
	 * @param licenseNumber
	 * @param customerContext
	 */
	public void saveCustomerId(String agentId, String id, String occupation,
			String businessName, String ssn, String dob, String stateIdNumber,
			String driverLicenseState, String licenseNumber, CustomerContextType customerContext) {

		CustomerType customerType = CustomerCacheService.INSTANCE.get(id);

		if (customerType == null) {
			customerType = CustomerService.INSTANCE.getCustomerType(id);
		}
		
		customerType.setAgentId(agentId);

		CustomerCacheService.INSTANCE.clear(id);
		CustomerInteractionCacheService.INSTANCE
				.clearCachedCustomerInteraction(id);

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("updateCustomer");

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		cmrr.getRequest().setCustomerInfo(customerType);
		cmrr.getRequest().setCustomerId(id);

		DriverLicenseType dlType = oFactory.createDriverLicenseType();
		dlType.setLicenseNumber(licenseNumber);
		dlType.setState(driverLicenseState);

		cmrr.getRequest().getCustomerInfo().setDriverLicense(dlType);
	}
	
	/**
	 * Get Customer Interaction using
	 * 	Customer exernalId
	 * 
	 * @param id
	 * @return List<CustomerInteractionType>
	 */
	public List<CustomerInteractionType> getCustomerInteraction(String id) {
		return getCustomerInteraction(id, "default");
	}
	
	/**
	 * Get Customer Interaction using
	 * 	Customer exernalId
	 * 
	 * @param id
	 * @return List<CustomerInteractionType>
	 */
	public List<CustomerInteractionType> getCustomerInteraction(String id, String agentId) {

		List<CustomerInteractionType> list = CustomerInteractionCacheService.INSTANCE
				.getCustomerInteractionFromCache(id);

		if (list != null) {
			return list;
		}

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId(1);
		cmrr.setTransactionType("getInteractionByCustomerId");

		if (id != null) {
			cmrr.getRequest().setCustomerId(id);
		}
		
		if (agentId != null) {
			cmrr.getRequest().setAgentId(agentId);
		}

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse()
				.getCustomerInfo();

		if ((customerTypeList != null) && (customerTypeList.size() > 0)) {
			CustomerType c = customerTypeList.get(0);
			if(c.getCustomerInteractionList() != null){
				list = c.getCustomerInteractionList().getCustomerInteraction();
			}
		}

		CustomerInteractionCacheService.INSTANCE
				.storeCustomerInteraction(
						list,
						id,
						CustomerInteractionCacheService.CUSTOMER_INTERACTION_PREFIX);

		return list;
	}

	/**
	 * Save Interaction notes to Customer
	 * 
	 * @param id
	 * @param agentId
	 * @param orderId
	 * @param source
	 * @param notes
	 * @param customerFullName
	 * @return
	 */
	@Deprecated
	public List<CustomerType> saveCustomerInteraction(String id,
			String agentId, String orderId, String source, String notes,
			String customerFullName) {

		return saveCustomerInteraction(id, agentId, orderId, source, notes,
					customerFullName, null, "-1", null, null);
	}
	
	/**
	 * Save Interaction notes to Customer
	 * 
	 * @param id
	 * @param agentId
	 * @param orderId
	 * @param source
	 * @param notes
	 * @param customerFullName
	 * @param providerId
	 * @param lineitemId
	 * @param reason
	 * @return
	 */
	@Deprecated
	public List<CustomerType> saveCustomerInteraction(String id,
			String agentId, String orderId, String source, String notes,
			String customerFullName, String providerId, String lineitemId,
			String reason){
		return saveCustomerInteraction(id, agentId, orderId, source, notes,
				customerFullName, providerId, lineitemId, reason, new ErrorList());
	}

	/**
	 * Save Interaction notes to Customer
	 * 
	 * @param id
	 * @param agentId
	 * @param orderId
	 * @param source
	 * @param notes
	 * @param customerFullName
	 * @param providerId
	 * @param lineitemId
	 * @param reason
	 * @return
	 */
	public List<CustomerType> saveCustomerInteraction(String id,
			String agentId, String orderId, String source, String notes,
			String customerFullName, String providerId, String lineitemId,
			String reason, ErrorList errorList) {

		//CustomerCacheService.INSTANCE.clear(id);
		CustomerInteractionCacheService.INSTANCE
				.clearCachedCustomerInteraction(id);

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("addCustomerInteraction");

		CustomerInteractionType customerInteractionType = CustomerFactory.INSTANCE
				.createCustomerInteractionType(agentId, customerFullName,
						Long.valueOf(orderId), Long.valueOf(id), notes, source);
		customerInteractionType.setLineItemId(Long.valueOf(lineitemId));

		if(reason != null){
			customerInteractionType.setReasons(reason);
		}
		
		if(providerId != null){
			customerInteractionType.setProviderId(Long.valueOf(providerId));
		}
		
		CustomerInteractionList cInteractionList = CustomerService.INSTANCE
				.getCustomerInteractionList(oFactory, cmrr);
		cInteractionList.getCustomerInteraction().add(customerInteractionType);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getCustomerClient().send(cmrr);
		CustomerFactory.INSTANCE.validateCMRR(cmrrR, errorList);

		List<CustomerType> customerTypeList = null;
		
		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getCustomerInfo() != null)) {
			customerTypeList = cmrrR.getResponse().getCustomerInfo();
		}

		return customerTypeList;
	}
	
	public List<CustomerType> saveCustomerInteraction(String id, CustomerInteractionType customerInteractionType) {

		CustomerCacheService.INSTANCE.clear(id);
		CustomerInteractionCacheService.INSTANCE
				.clearCachedCustomerInteraction(id);

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("addCustomerInteraction");

		CustomerInteractionList cInteractionList = CustomerService.INSTANCE
				.getCustomerInteractionList(oFactory, cmrr);
		cInteractionList.getCustomerInteraction().add(customerInteractionType);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse()
				.getCustomerInfo();

		return customerTypeList;

	}

	public CustomerType submitCustomerType(final String agentId,
			final String id, final String transactionType) {

		CustomerType customer = null;

		return CustomerService.INSTANCE.submitCustomerType(agentId, id,
				transactionType, customer);
	}
	
	public CustomerType submitCustomerType(final String agentId,
			final String id, final String transactionType,
			final CustomerType customer,
			final NotificationEventCollectionType notifEventColl){
		return submitCustomerType(agentId, id, transactionType, customer,
				notifEventColl, null);
	}
	
	@Deprecated
	public CustomerType submitCustomerType(final String agentId,
			final String id, final String transactionType,
			final CustomerType customer) {
		CustomerContextType customerContext = null;
		return submitCustomerType(agentId, id, transactionType, customer, customerContext);
	}

	/**
	 * Update Customer
	 * 
	 * @param agentId
	 * @param id
	 * @param transactionType
	 * @param customer
	 * @param customerContext
	 * @return
	 */
	public CustomerType submitCustomerType(final String agentId,
			final String id, final String transactionType,
			final CustomerType customer, CustomerContextType customerContext) {

		return submitCustomerType(agentId, id, transactionType,	customer, null, customerContext);
	}
	
	/**
	 * Update Customer
	 * 
	 * @param agentId
	 * @param id
	 * @param transactionType
	 * @param customer
	 * @param notifEventColl
	 * @param customerContext
	 * @return
	 */
	@Deprecated
	public CustomerType submitCustomerType(final String agentId,
			final String id, final String transactionType,
			final CustomerType customer,
			final NotificationEventCollectionType notifEventColl, CustomerContextType customerContext){
		return submitCustomerType(agentId, id, transactionType, customer, notifEventColl, 
				customerContext, new ErrorList());
	}

	/**
	 * Update Customer
	 * 
	 * @param agentId
	 * @param id
	 * @param transactionType
	 * @param customer
	 * @param notifEventColl
	 * @param customerContext
	 * @return
	 */
	public CustomerType submitCustomerType(final String agentId, final String id, final String transactionType,
			final CustomerType customer, final NotificationEventCollectionType notifEventColl, 
			CustomerContextType customerContext, ErrorList errorList) {

		long currentTime = System.currentTimeMillis();
		logger.info("SubmitCustomerType: updating customer : "+id);
		
		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType(transactionType);

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		
		if (id != null) {
			cmrr.getRequest().setCustomerId(id);
			cmrr.getRequest().setAgentId(agentId);
		}

		if (customer != null) {
			customer.setAgentId(agentId);
			cmrr.getRequest().setCustomerInfo(customer);
		}
		
		if (notifEventColl != null) {
			cmrr.getRequest().setNotificationEventCollection(notifEventColl);
		}
		
		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getCustomerClient().send(cmrr);
		CustomerFactory.INSTANCE.validateCMRR(cmrrR, errorList);

		CustomerType customerR = null;
		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getCustomerInfo() != null)) {
			
			List<CustomerType> customerTypeList = cmrrR.getResponse()
					.getCustomerInfo();
			if (customerTypeList.size() > 0) {
				customerR = customerTypeList.get(0);
				
				//Filtering deleted billing info's and associated payment events
				List<String> inActiveBillingIdList = filterInActiveBillingInfo(customerR);
				filterInActivePayments(customerR, inActiveBillingIdList);
				
				customerR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customerR);
				
				//Cache will be reset only if updateCustomer is successful
				if(errorList != null && errorList.size() == 0){
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(id);
					CustomerCacheService.INSTANCE.clear(id);
					CustomerCacheService.INSTANCE.store(customerR, id);
				}
			}
		}
		
		logger.info("SubmitCustomerType: returning customer :"+id+" ... Total time: "+(System.currentTimeMillis() - currentTime) + "ms");
		return customerR;
	}
	
	/**
	 * This is to lock all the orders of this customer 
	 * 	
	 * @param agentId
	 * @param customerId
	 * @return
	 */
	public boolean lockCustomer(String agentId, String customerId){
		logger.info("lockCustomer: agentId --> "+agentId+", customerId --> "+customerId);
		
		return JobService.INSTANCE.lockCustomer(agentId, customerId, null);
	}
	
	/**
	 * This is to unlock all the orders of this customer 
	 * 
	 * @param agentId
	 * @param customerId
	 * @return
	 */
	public void unLockCustomer(String agentId, String customerId){
		logger.info("unLockCustomer: agentId --> "+agentId+", customerId --> "+customerId);
		
		JobUpdateThread jut = new JobUpdateThread(agentId, null, customerId, null, 
				Boolean.FALSE, Constants.CUST_LOCK_TYPE, null);
		new Thread(jut).start();
	}
	
	/**
	 * Remove Payments associated to deleted 
	 * 	BillingInfo from Customer
	 * 
	 * @param customer
	 * @param inActiveBillingIdList
	 */
	public void filterInActivePayments(CustomerType customer, List<String> inActiveBillingIdList) {
		logger.debug("filterInActivePayments, Id : "+customer.getExternalId());
		Iterator<PaymentEventType> it = customer.getPayments().getPaymentEvent().iterator();
		while(it.hasNext()){
			String billingInfoId = it.next().getBillingInfoId();
			if(inActiveBillingIdList.contains(billingInfoId)){
				it.remove();
			}
		}
	}

	/**
	 * Remove deleted BillingInfo from Customer
	 * 
	 * @param customer
	 * @return
	 */
	public List<String> filterInActiveBillingInfo(CustomerType customer) {
		logger.debug("filterInActiveBillingInfo, Id : "+customer.getExternalId());
		
		List<String> inActiveBillingIdList = new ArrayList<String>();
		Iterator<CustBillingInfoType> it = customer.getBillingInfoList().getBillingInfo().iterator();
		while(it.hasNext()) {
			CustBillingInfoType billingInfo = it.next();
			String status = billingInfo.getStatus();
			if(status != null && status.equalsIgnoreCase("inactive")){
				inActiveBillingIdList.add(billingInfo.getBillingUniqueId());
				it.remove();
			}
		}
		return inActiveBillingIdList;
	}
	
	/**
     * Asynchronous updateCustomer call. Be careful about using this service call as it does not return
     * response.
     *
     * @param agentId
     * @param customerExtID
     * @param transactionType
     * @param customer
     * @param notifEventColl
     * @param customerContext
     * @param guid
     * @return Nothing.
     */
	
	
	public void asyncSubmitCustomerType(final String agentId, final String customerExtID, final String transactionType,
			final CustomerType customer, final NotificationEventCollectionType notifEventColl, 
			CustomerContextType customerContext,final String guid){

		logger.info("Adding SubmitCustomerType asynchronously"+customerExtID);
		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(guid);
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType(transactionType);

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		
		if (customerExtID != null) {
			cmrr.getRequest().setCustomerId(customerExtID);
			cmrr.getRequest().setAgentId(agentId);
		}

		if (customer != null) {
			customer.setAgentId(agentId);
			cmrr.getRequest().setCustomerInfo(customer);
		}
		
		if (notifEventColl != null) {
			cmrr.getRequest().setNotificationEventCollection(notifEventColl);
		}
		logger.info("CM Async request : [TransactionType="+transactionType+", AgentId="+agentId+", CustomerExtID="+customerExtID+", GUID="+guid+"]");
		long start = System.currentTimeMillis();
		TransportConfig.INSTANCE.getCustomerClient().sendAsync(cmrr);
		logger.info("CM Async request completed: [TransactionType="+transactionType+", AgentId="+agentId+", CustomerExtID="+customerExtID+", GUID="+guid+" , Time="+(System.currentTimeMillis()-start)+"]");
		
	}

	public AccountHolderSearch searchAccountHolderSearch(Map<String, String> map, int offset) {

		Set<String> keys = map.keySet();

		for (String key : keys) {
			String data = map.get(key);
			if ((data == null) || (data.length() == 0)) {
				map.put(key, "");
			}
		}

		AccountHolderSearch accntHolderSearchResult = TransportConfig.INSTANCE.getCustomerClient().searchAccntCustomerInfo(map, offset);
		
		return accntHolderSearchResult;
	}
}
