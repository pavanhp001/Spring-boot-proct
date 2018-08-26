package com.A.V.factory;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.ui.service.V.impl.CacheService;
import com.A.ui.vo.CartError;
import com.A.ui.vo.ErrorList;
import com.A.util.DateUtil;
import com.A.util.StringCaseUtil;
import com.A.util.XMLGregorianCalendarConverter;
import com.A.xml.cm.v4.AddressType;
import com.A.xml.cm.v4.CustAddress;
import com.A.xml.cm.v4.CustomerContextEntityType;
import com.A.xml.cm.v4.CustomerContextType;
import com.A.xml.cm.v4.CustomerFinancialInfoType;
import com.A.xml.cm.v4.CustomerInteractionType;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.DriverLicenseType;
import com.A.xml.cm.v4.EMailAddressType;
import com.A.xml.cm.v4.LandlordInfoType;
import com.A.xml.cm.v4.NameValuePairType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.PaymentEventType;
import com.A.xml.cm.v4.PhoneNumberType;
import com.A.xml.cm.v4.ProcessingMessage;
import com.A.xml.cm.v4.StateIdType;
import com.A.xml.v4.OrderType;

public enum CustomerFactory {

	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(CustomerFactory.class);

	public static final String GUID = "GUID";
	
	private ObjectFactory oFactory = new ObjectFactory();
	
	protected boolean nonBuyerWebOptIn;
	protected boolean directMailOptIn;
	protected boolean eMailOptIn;
	protected boolean phoneContactOptIn;
	protected boolean eMailProductUpdatesOptIn;
	protected boolean marketingOptIn;
	
	/**
	 * @param customer
	 * @param homePhone
	 * @param workPhone
	 * @param cellPhone
	 * @param workEmail
	 * @param workExt
	 * @param homeEmail
	 * @return
	 */
	public CustomerType updateContactInformation(CustomerType customer,
			final String homePhone, final String workPhone,
			final String cellPhone, final String workEmail,
			final String workExt, final String homeEmail) {

		logger.info("updateContactInformation: setting to customer ...");
		
		if (customer == null) {
			customer = oFactory.createCustomerType();
		}
		
		if(!StringUtils.isEmpty(homeEmail)){
			customer.setHomeEMail(createHomeEMail(homeEmail, 0));
		}
		
		if(!StringUtils.isEmpty(workEmail)){
			customer.setWorkEMail(createWorkEMail(workEmail, 1));
		}
		
		if(!StringUtils.isEmpty(cellPhone)){
			customer.setCellPhoneNumber(createCellPhoneNumber(cellPhone, "", 2));
		}
		
		if(!StringUtils.isEmpty(workPhone)){
			customer.setWorkPhoneNumber(createWorkPhoneNumber(workPhone, workExt, 3));
		}
		
		if(!StringUtils.isEmpty(homePhone)){
			customer.setHomePhoneNumber(createHomePhoneNumber(homePhone, "", 4));
		}

		return customer;
	}

	/**
	 * @param value
	 * @param extension
	 * @param order
	 * @return
	 */
	public PhoneNumberType createHomePhoneNumber(final String value,
			final String extension, final Integer order) {
		return createPhoneNumber("home", value, extension, order);
	}

	/**
	 * @param value
	 * @param extension
	 * @param order
	 * @return
	 */
	public PhoneNumberType createCellPhoneNumber(final String value,
			final String extension, final Integer order) {
		return createPhoneNumber("cell", value, extension, order);
	}

	/**
	 * @param value
	 * @param extension
	 * @param order
	 * @return
	 */
	public PhoneNumberType createWorkPhoneNumber(final String value,
			final String extension, final Integer order) {
		return createPhoneNumber("work", value, extension, order);
	}

	/**
	 * @param desc
	 * @param value
	 * @param extension
	 * @param order
	 * @return
	 */
	public PhoneNumberType createPhoneNumber(final String desc,
			final String value, final String extension, final Integer order) {
		logger.info("createPhoneNumber: build phone number type : "+desc);
		
		PhoneNumberType pnt = oFactory.createPhoneNumberType();
		pnt.setDesc(desc);
		pnt.setExtension(extension);
		pnt.setOrder(order);
		//CM is not saving extension, so this fix
		if(StringUtils.isNotBlank(extension)){
			pnt.setValue(value+" x"+extension);
		} else {
			pnt.setValue(value);
		}
		

		return pnt;
	}

	/**
	 * @param value
	 * @param order
	 * @return
	 */
	public EMailAddressType createHomeEMail(final String value,
			final Integer order) {

		return createEMail(value, "home", order);
	}

	/**
	 * @param value
	 * @param order
	 * @return
	 */
	public EMailAddressType createWorkEMail(final String value,
			final Integer order) {

		return createEMail(value, "work", order);
	}

	/**
	 * @param value
	 * @param desc
	 * @param order
	 * @return
	 */
	public EMailAddressType createEMail(final String value, final String desc,
			final Integer order) {
		logger.info("createEMail: type --> "+desc);
		
		EMailAddressType ema = oFactory.createEMailAddressType();
		ema.setDesc(desc);
		ema.setOrder(order);
		ema.setValue(value);

		return ema;
	}

	/**
	 * @param landlordName
	 * @param phoneNumber
	 * @return
	 */
	public LandlordInfoType createLandlordInfo(final String landlordName,
			final String phoneNumber) {
		logger.info("createLandlordInfo: build LandlordInfoType object");

		LandlordInfoType llInfo = oFactory.createLandlordInfoType();
		if(!StringUtils.isEmpty(landlordName)){
			llInfo.setLandlordName(landlordName);
		}
		
		if(!StringUtils.isEmpty(phoneNumber)){
			llInfo.setLandlordPhoneNumber(phoneNumber);
		}

		return llInfo;
	}

	/**
	 * @param licenseNumber
	 * @param state
	 * @param lExpireDate
	 * @return
	 */
	public DriverLicenseType createDriverLicense(final String licenseNumber,
			final String state, final Date lExpireDate) {
		logger.info("createDriverLicense: build DriverLicenseType object");
		
		DriverLicenseType dlt = oFactory.createDriverLicenseType();
		if(!StringUtils.isEmpty(licenseNumber)){
			dlt.setLicenseNumber(licenseNumber);
		}
		
		if(!StringUtils.isEmpty(state)){
			dlt.setState(state);
		}
		
		dlt.setLicenseExpirationDate(DateUtil.asXMLGregorianCalendar(lExpireDate));

		return dlt;

	}

	/**
	 * @param idNumber
	 * @param state
	 * @return
	 */
	public StateIdType createStateId(final String idNumber, final String state) {
		logger.info("createStateId: build StateIdType object");
		
		StateIdType stateId = oFactory.createStateIdType();
		if(!StringUtils.isEmpty(idNumber)){
			stateId.setIdNumber(idNumber);
		}
		
		if(!StringUtils.isEmpty(state)){
			stateId.setState(state);
		}

		return stateId;
	}

	/**
	 * @return
	 */
	public PaymentEventType createPayments() {

		PaymentEventType pmt = oFactory.createPaymentEventType();
		return pmt;
	}
	
	/**
	 * @param agentId
	 * @param customerFullName
	 * @param orderId
	 * @param customerId
	 * @param notes
	 * @return
	 */
	public CustomerInteractionType createCustomerInteractionType(
			final String agentId, final String customerFullName,
			final Long orderId, final Long customerId, final String notes) {
		
		return createCustomerInteractionType(agentId, customerFullName, orderId, customerId, notes, "fulfillment");
	}

	/**
	 * @param agentId
	 * @param customerFullName
	 * @param orderId
	 * @param customerId
	 * @param notes
	 * @return
	 */
	public CustomerInteractionType createCustomerInteractionType(
			final String agentId, final String customerFullName,
			final Long orderId, final Long customerId, final String notes, final String source) {

		logger.info("createCustomerInteractionType: build CustomerInteractionType custId: "+customerId);
		
		CustomerInteractionType customerInteractionType = oFactory
				.createCustomerInteractionType();
		customerInteractionType.setAgentId(agentId);
		customerInteractionType.setSource(source);

		try {
			customerInteractionType.setOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		customerInteractionType.setCustomerFullName(customerFullName);

		try {
			customerInteractionType.setCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		customerInteractionType.setNotes(notes);

		customerInteractionType.setInteractionDate(DateUtil
				.asXMLGregorianCalendar(new Date()));

		return customerInteractionType;

	}

	/**
	 * @param customer
	 * @param title
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param nameSuffix
	 * @return
	 */
	public CustomerType updateCustomerName(final CustomerType customer,
			final String title, final String firstName,
			final String middleName, final String lastName,
			final String nameSuffix) {
		
		logger.info("updateCustomerName: Name: "+firstName+" "+middleName+" "+lastName);

		if(!StringUtils.isEmpty(title)){
			customer.setTitle(title);
		}else{
			customer.setTitle("");
		}
		
		if(!StringUtils.isEmpty(firstName)){
			customer.setFirstName(firstName);
		}
		
		if(!StringUtils.isEmpty(lastName)){
			customer.setLastName(lastName);
		}
		
		if(!StringUtils.isEmpty(middleName)){
			customer.setMiddleName(middleName);
		}else{
			customer.setMiddleName("");
		}
		
		if(!StringUtils.isEmpty(nameSuffix)){
			customer.setNameSuffix(nameSuffix);
		}else{
			customer.setNameSuffix("");
		}

		return customer;
	}

	/**
	 * @param title
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param nameSuffix
	 * @return
	 */
	public CustomerType updateCustomerName(final String title,
			final String firstName, final String middleName,
			final String lastName, final String nameSuffix) {

		CustomerType customer = oFactory.createCustomerType();

		updateCustomerName(customer, title, firstName, middleName, lastName,
				nameSuffix);

		return customer;

	}

	/**
	 * @param customer
	 * @param gender
	 * @param dob
	 * @param ssn
	 * @param primaryLanguage
	 * @return
	 */
	public CustomerType updateCustomer(CustomerType customer, String gender,
			Date dob, String ssn, Integer primaryLanguage) {
		
		logger.info("updateCustomer: setting dob, gender, ssn, lang ...");

		if (customer == null) {
			customer = oFactory.createCustomerType();
		}

		if (dob != null) {
			QName NAME = new QName("http://xml.A.com/v4","dob");
			JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>(
					NAME, XMLGregorianCalendar.class,
					XMLGregorianCalendarConverter.asXMLGregorianCalendar(dob));
			customer.setDob(DateUtil.asXMLGregorianCalendar(dob));
			
		} else {
			customer.setDob(null);
		}

		if(!StringUtils.isEmpty(ssn)){
			customer.setSsn(ssn);
		}
		
		customer.setGender(gender);
		customer.setPrimaryLanguage(primaryLanguage);

		return customer;

	}

	/**
	 * @param mortgageInstitution
	 * @param otherIncome
	 * @param businessName
	 * @param businessPhoneNum
	 * @param occupation
	 * @param isStudent
	 * @param isRetired
	 * @param isUnemployed
	 * @return
	 */
	public CustomerFinancialInfoType createCustomerFinancialInfoType(
			final String mortgageInstitution, final String otherIncome,
			final String businessName, final String businessPhoneNum,
			final String occupation, final boolean isStudent,
			final boolean isRetired, final boolean isEmployed) {
		logger.info("createCustomerFinancialInfoType: build CustomerFinancialInfoType object ");
		
		CustomerFinancialInfoType custFinInfo = oFactory
				.createCustomerFinancialInfoType();
		custFinInfo.setBankOrMortgageInstitution(mortgageInstitution);
		custFinInfo.setOtherIncomeSource(otherIncome);

		if (isEmployed) {
			if (custFinInfo.getEmployed() == null) {
				custFinInfo.setEmployed(oFactory.createCustomerFinancialInfoTypeEmployed());
			}

			if(!StringUtils.isEmpty(businessName)){
				custFinInfo.getEmployed().setBusinessName(businessName);
			}
			
			if(!StringUtils.isEmpty(businessPhoneNum)){
				custFinInfo.getEmployed().setBusinessPhoneNum(businessPhoneNum);
			}
			
			if(!StringUtils.isEmpty(occupation)){
				custFinInfo.getEmployed().setOccupation(occupation);
			}
		} 
//		else {
//			custFinInfo.setUnemployed(oFactory.createEmptyElementType());
//		}

		if (isStudent) {
			custFinInfo.setStudent(oFactory.createEmptyElementType());
		}

		if (isRetired) {
			custFinInfo.setRetired(oFactory.createEmptyElementType());
		}

		return custFinInfo;
	}
	
	/**
	 * Get attribute value
	 * 
	 * @param context
	 * @param entityName
	 * @param attrName
	 * @return
	 */
	public String getAttribute(CustomerContextType context, String entityName, String attrName){
		if(context != null){
			for(CustomerContextEntityType entityType : context.getEntity()){
				if(entityType.getName().equals(entityName)){
					for(NameValuePairType nameValuePairType:  entityType.getAttribute()){
						if(nameValuePairType.getName().equals(attrName)){
							return nameValuePairType.getValue();
						}
					}
				}
			}
		}
		
		return "";
	}
	
	/**
	 * Get attribute value
	 * 
	 * @param context
	 * @param attrName
	 * @return
	 */
	public String getAttribute(CustomerContextType context, String attrName){
		if(context != null){
			for(CustomerContextEntityType entityType : context.getEntity()){
				for(NameValuePairType nameValuePairType:  entityType.getAttribute()){
					if(nameValuePairType.getName().equals(attrName)){
						String value = nameValuePairType.getValue();
						if(StringUtils.isNotBlank(value)){
							return value;
						}
					}
				}
			}
		}

		return "";
	}
	
	/**
	 * Method to build CustomerContextType
	 * 
	 * @param entityName
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	public CustomerContextType buildCustomerContext(String entityName, String attrName, String attrValue){
		CustomerContextType context = new CustomerContextType();
		
		CustomerContextEntityType entityType = new CustomerContextEntityType();
		entityType.setName(entityName);
		
		NameValuePairType attribute = new NameValuePairType();
		attribute.setName(attrName);
		attribute.setValue(attrValue);
		
		entityType.getAttribute().add(attribute);
		context.getEntity().add(entityType);
		
		return context;
	}
	
	/**
	 * Method to build CustomerContextType from Map
	 * 
	 * @param Map<String, Map<String, String>> data
	 * @return CustomerContextType
	 */
	public CustomerContextType buildCustomerContext(
			Map<String, Map<String, String>> data) {
		CustomerContextType context = new CustomerContextType();
		if(data == null){
			return context;
		}

		for (String entityName : data.keySet()) {

			CustomerContextEntityType entity = new CustomerContextEntityType();
			entity.setName(entityName);

			List<NameValuePairType> nvPairList = entity.getAttribute();
			Map<String, String> attrMap = data.get(entityName);

			for (String keyName : attrMap.keySet()) {
				String value = attrMap.get(keyName);

				NameValuePairType nvPairType = new NameValuePairType();
				nvPairType.setName(keyName);
				nvPairType.setValue(value);
				nvPairList.add(nvPairType);
			}
			context.getEntity().add(entity);
		}

		return context;
	}
	
	public CustomerType checkTitleCaseForCustomer(CustomerType customer){
		if(customer != null){
			customer.setTitle(StringCaseUtil.toCamelCase(customer.getTitle(),true));
			customer.setFirstName(StringCaseUtil.toCamelCase(customer.getFirstName(),true));
			customer.setMiddleName(StringCaseUtil.toCamelCase(customer.getMiddleName(),true));
			customer.setLastName(StringCaseUtil.toCamelCase(customer.getLastName(),true));
			String nameSuffix = customer.getNameSuffix();
			if(nameSuffix!=null && (nameSuffix.equalsIgnoreCase("Jr")||nameSuffix.equalsIgnoreCase("Sr"))){
				customer.setNameSuffix(StringCaseUtil.toCamelCase(customer.getNameSuffix(),true));
			}
			nameSuffix = null;
			List<CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (CustAddress custAddress : custAddressList) {
					custAddress.setAddress(checkAddressCaseForCustomer(custAddress.getAddress()));
				}
			}
		}
		
		return customer;
	}
	
	public OrderType checkTitleCaseForCustomer(OrderType order){

		if(order != null && order.getCustomerInformation() != null && order.getCustomerInformation().getCustomer() != null)
		{
			com.A.xml.v4.CustomerType customer = order.getCustomerInformation().getCustomer();

			order.getCustomerInformation().getCustomer().setTitle(StringCaseUtil.toCamelCase(customer.getTitle(),true));
			order.getCustomerInformation().getCustomer().setFirstName(StringCaseUtil.toCamelCase(customer.getFirstName(),true));
			order.getCustomerInformation().getCustomer().setMiddleName(StringCaseUtil.toCamelCase(customer.getMiddleName(),true));
			order.getCustomerInformation().getCustomer().setLastName(StringCaseUtil.toCamelCase(customer.getLastName(),true));
			String nameSuffix = order.getCustomerInformation().getCustomer().getNameSuffix();
			if(nameSuffix!=null && (nameSuffix.equalsIgnoreCase("Jr")||nameSuffix.equalsIgnoreCase("Sr"))){
				order.getCustomerInformation().getCustomer().setNameSuffix(StringCaseUtil.toCamelCase(customer.getNameSuffix(),true));
			}
			nameSuffix = null;
			
			List<com.A.xml.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (com.A.xml.v4.CustAddress custAddress : custAddressList) {
					com.A.xml.v4.AddressType addressType = custAddress.getAddress();
					if (addressType != null) {
						addressType.setPrefixDirectional(StringUtils.upperCase(addressType.getPrefixDirectional()));
						addressType.setPostfixDirectional(StringUtils.upperCase(addressType.getPostfixDirectional()));
						addressType.setStreetName(StringCaseUtil.toCamelCase(addressType.getStreetName(),false));
						addressType.setStreetType(StringCaseUtil.toCamelCase(addressType.getStreetType(),false));
						addressType.setLine2(StringCaseUtil.toCamelCase(addressType.getLine2(),false));
						addressType.setCity(StringCaseUtil.toCamelCase(addressType.getCity(),false));
					}
				}
			}
		}
		
		return order;
	}

	public com.A.xml.me.v4.AddressType checkAddressCaseForCustomer(com.A.xml.me.v4.AddressType address) {
		address.setPrefixDirectional(StringUtils.upperCase(address.getPrefixDirectional()));
		address.setPostfixDirectional(StringUtils.upperCase(address.getPostfixDirectional()));
		address.setStreetName(StringCaseUtil.toCamelCase(address.getStreetName(),false));
		address.setStreetType(StringCaseUtil.toCamelCase(address.getStreetType(),false));
		address.setLine2(StringCaseUtil.toCamelCase(address.getLine2(),false));
		address.setCity(StringCaseUtil.toCamelCase(address.getCity(),false));
		return address;
	}
	
	public AddressType checkAddressCaseForCustomer(AddressType address) {
		address.setPrefixDirectional(StringUtils.upperCase(address.getPrefixDirectional()));
		address.setPostfixDirectional(StringUtils.upperCase(address.getPostfixDirectional()));
		address.setStreetName(StringCaseUtil.toCamelCase(address.getStreetName(),false));
		address.setStreetType(StringCaseUtil.toCamelCase(address.getStreetType(),false));
		address.setLine2(StringCaseUtil.toCamelCase(address.getLine2(),false));
		address.setCity(StringCaseUtil.toCamelCase(address.getCity(),false));
		return address;
	}
	
	/**
	 * Method to capture error/fatal messages
	 * 
	 * @param CustomerManagementRequestResponse cmrr
	 * @param ErrorList errorList
	 */
	public void validateCMRR(CustomerManagementRequestResponse cmrr, ErrorList errorList)
	{
		if(cmrr != null && cmrr.getStatus() != null && cmrr.getStatus().getStatusMsg() != null)
		{
			String statusMsg = cmrr.getStatus().getStatusMsg();
			if(statusMsg.equalsIgnoreCase("ERROR") || statusMsg.equalsIgnoreCase("FATAL"))
			{
				logger.info("validateCMRR: CMRR status "+statusMsg);
				
				if(cmrr.getStatus().getProcessingMessages() != null &&
						cmrr.getStatus().getProcessingMessages().getMessage() != null)
				{
					for(ProcessingMessage message : cmrr.getStatus().getProcessingMessages().getMessage())
					{
						errorList.add(new CartError(String.valueOf(message.getCode()), message.getText()));
					}
				}
			}
		}
	}

	/**
	 * Method to cache cust GUID
	 * 
	 * @param orderId
	 * @param salesContext
	 */
	public void cacheGUID(Long custId, CustomerContextType customerContext) {
		String guid = getAttribute(customerContext, GUID);
		CacheService.INSTANCE.storeGUID("CUST-GUID-"+custId, guid);
	}
	

	/**
	 * @param value
	 * @param extension
	 * @param order
	 * @return
	 */
	public PhoneNumberType createFAWorkPhoneNumber(final String value,
			final String extension, final Integer order) {
		return createFAPhoneNumber("work", value, extension, order);
	}

	/**
	 * @param desc
	 * @param value
	 * @param extension
	 * @param order
	 * @return
	 */
	public PhoneNumberType createFAPhoneNumber(final String desc,
			final String value, final String extension, final Integer order) {
		logger.info("createFAPhoneNumber: build phone number type : "+desc);
		
		PhoneNumberType pnt = oFactory.createPhoneNumberType();
		pnt.setDesc(desc);
		pnt.setExtension(extension);
		pnt.setOrder(order);
		pnt.setValue(value);
		//CM is not saving extension, so this fix
		/*if(StringUtils.isNotBlank(extension)){
			pnt.setValue(value+" x"+extension);
		} else {
			pnt.setValue(value);
		}*/
		

		return pnt;
	}

}
