package com.AL.ui.factory;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.AL.util.DateUtil;
import com.AL.xml.cm.v4.AddressListType;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.BillingAccountTypeType;
import com.AL.xml.cm.v4.BillingInfoList;
import com.AL.xml.cm.v4.CreditCardTypeType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustBillingInfoType;
import com.AL.xml.cm.v4.CustomerFinancialInfoType;
import com.AL.xml.cm.v4.CustomerInteractionType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.DriverLicenseType;
import com.AL.xml.cm.v4.EMailAddressType;
import com.AL.xml.cm.v4.LandlordInfoType;
import com.AL.xml.cm.v4.ObjectFactory;
import com.AL.xml.cm.v4.PaymentEventType;
import com.AL.xml.cm.v4.PhoneNumberType;
import com.AL.xml.cm.v4.StateIdType;

/**
 * @author 
 *
 */
public enum CartCustomerFactory {

	INSTANCE;

	private ObjectFactory oFactory = new ObjectFactory();

	/**
	 * @param request
	 * @param agentId
	 * @return CustomerType
	 */
	public CustomerType createNewCustomer( final HttpServletRequest request,final String agentId) {

		CustomerType customer = oFactory.createCustomerType();

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String homePhone = request.getParameter("homePhoneNumber");
		String workPhone = request.getParameter("workPhoneNumber");
		String homeEmail = request.getParameter("homeEMail");
		String workEmail = request.getParameter("workEMail");
		String dob = request.getParameter("dob");

		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer = updateCustomerDobDate(customer, dob);
		customer = updateContactInformation(customer, homePhone, workPhone, "0000", workEmail, "000", homeEmail);
		customer.setReferrerId(123L);
		customer.setAgentId(agentId);
		customer.setExternalId(0L);

		String addressUniqueId = customer.getLastName()+"-addr";
		String billingUniqueId = customer.getLastName();
		String status = "active";
		String[] roles = {"SERVICE_ADDRESS"};

		AddressListType addressList = oFactory.createAddressListType();
		AddressType address = CartAddressFactory.INSTANCE.addAddress(request);
		CustAddress custAddress = CartAddressFactory.INSTANCE.addCustAddress(status, addressUniqueId, roles, address);
		addressList.getCustomerAddress().add(custAddress);
		customer.setAddressList(addressList);

		BillingInfoList billingInfoList = getCustomerBillingInfo(addressUniqueId, billingUniqueId);
		customer.setBillingInfoList(billingInfoList);

		return customer;
	}

	/**
	 * @param customer
	 * @param homePhone
	 * @param workPhone
	 * @param cellPhone
	 * @param workEmail
	 * @param workExt
	 * @param homeEmail
	 * @return CustomerType
	 */
	public CustomerType updateContactInformation( CustomerType customer, final String homePhone,   
			final String workPhone,
			final String cellPhone,   
			final String workEmail, final String workExt,
			final String homeEmail) {

		if (customer == null) {
			customer = oFactory.createCustomerType();
		}

		customer.setHomeEMail(createHomeEMail(    homeEmail, 0));
		customer.setWorkEMail(createWorkEMail(    workEmail,1));
		//customer.setCellPhoneNumber(createCellPhoneNumber(    cellPhone,"",2));
		customer.setWorkPhoneNumber(createWorkPhoneNumber(    workPhone,workExt,3));
		customer.setHomePhoneNumber(createHomePhoneNumber(    homePhone,"",4));

		return customer;
	}

	/**
	 * @param customer
	 * @param dob
	 * @return CustomerType
	 */
	public CustomerType updateCustomerDobDate( CustomerType customer, final String dob) {

		if (customer == null) {
			customer = oFactory.createCustomerType();
		}

		Date dobDate = DateUtil.fromStringToDate(dob);

		if (dobDate != null) {
			customer.setDob(DateUtil.asXMLGregorianCalendar(dobDate));
		} else {
			customer.setDob(null);
		}

		return customer;
	}

	/**
	 * @param value
	 * @param extension
	 * @param order
	 * @return PhoneNumberType
	 */
	public PhoneNumberType createHomePhoneNumber(final String value,
			final String extension, final Integer order) {
		return createPhoneNumber("home", value, extension, order);
	}

	/**
	 * @param value
	 * @param extension
	 * @param order
	 * @return PhoneNumberType
	 */
	public PhoneNumberType createCellPhoneNumber(final String value,
			final String extension, final Integer order) {
		return createPhoneNumber("cell", value, extension, order);
	}

	/**
	 * @param value
	 * @param extension
	 * @param order
	 * @return PhoneNumberType
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
	 * @return PhoneNumberType
	 */
	public PhoneNumberType createPhoneNumber(final String desc,
			final String value, final String extension, final Integer order) {
		PhoneNumberType pnt = oFactory.createPhoneNumberType();
		pnt.setDesc(desc);
		//pnt.setExtension(extension);
		pnt.setOrder(order);
		pnt.setValue(value);

		return pnt;
	}

	/**
	 * @param value
	 * @param order
	 * @return EMailAddressType
	 */
	public EMailAddressType createHomeEMail(final String value,
			final Integer order) {

		return createEMail(value, "home", order);
	}

	/**
	 * @param value
	 * @param order
	 * @return EMailAddressType
	 */
	public EMailAddressType createWorkEMail(final String value,
			final Integer order) {

		return createEMail(value, "work", order);
	}

	/**
	 * @param value
	 * @param desc
	 * @param order
	 * @return EMailAddressType
	 */
	public EMailAddressType createEMail(final String value, final String desc,
			final Integer order) {
		EMailAddressType ema = oFactory.createEMailAddressType();
		ema.setDesc(desc);
		ema.setOrder(order);
		ema.setValue(value);

		return ema;
	}

	/**
	 * @param landlordName
	 * @param phoneNumber
	 * @return LandlordInfoType
	 */
	public LandlordInfoType createLandlordInfo(final String landlordName,
			final String phoneNumber) {

		LandlordInfoType llInfo = oFactory.createLandlordInfoType();
		llInfo.setLandlordName(landlordName);
		llInfo.setLandlordPhoneNumber(phoneNumber);

		return llInfo;
	}

	/**
	 * @param licenseNumber
	 * @param state
	 * @param lExpireDate
	 * @return DriverLicenseType
	 */
	public DriverLicenseType createDriverLicense(final String licenseNumber,
			final String state, final Date lExpireDate) {
		DriverLicenseType dlt = oFactory.createDriverLicenseType();
		dlt.setLicenseNumber(licenseNumber);
		dlt.setState(state);
		dlt.setLicenseExpirationDate(DateUtil
				.asXMLGregorianCalendar(lExpireDate));

		return dlt;

	}

	/**
	 * @param idNumber
	 * @param state
	 * @return StateIdType
	 */
	public StateIdType createStateId(final String idNumber, final String state) {
		StateIdType stateId = oFactory.createStateIdType();
		stateId.setIdNumber(idNumber);
		stateId.setState(state);

		return stateId;
	}

	/**
	 * @return PaymentEventType
	 */
	public PaymentEventType createPayments() {

		PaymentEventType pmt = oFactory.createPaymentEventType();
		// pmt.setAmount(value);
		// pmt.setBillingInfoId(value)
		// pmt.setConfirmNumber(value)
		// pmt.setCustAgreedCCDisclosure(value);
		// pmt.setCVV(value);
		// pmt.setEvent(value);
		// pmt.setLineItemId(v);
		// pmt.setOrderId(value);
		// pmt.setPaymentStatus(value);
		// pmt.setTransactionDate(value);

		return pmt;
	}

	/**
	 * @param agentId
	 * @param customerFullName
	 * @param orderId
	 * @param customerId
	 * @param notes
	 * @return CustomerInteractionType
	 */
	public CustomerInteractionType createCustomerInteractionType(
			final String agentId, final String customerFullName, final Long orderId,
			final Long customerId, final String notes) {

		CustomerInteractionType customerInteractionType = oFactory
		.createCustomerInteractionType();
		customerInteractionType.setAgentId(agentId);
		customerInteractionType.setSource("fulfillment");

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
	 * @return CustomerType
	 */
	public CustomerType updateCustomerName(final CustomerType customer,
			final String title, final String firstName,
			final String middleName, final String lastName,
			final String nameSuffix) {

		customer.setTitle(title);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setMiddleName(middleName);
		customer.setNameSuffix(nameSuffix);

		return customer;
	}

	/**
	 * @param title
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param nameSuffix
	 * @return CustomerType
	 */
	public CustomerType updateCustomerName(final String title,
			final String firstName, final String middleName,
			final String lastName, final String nameSuffix) {

		CustomerType customer = oFactory.createCustomerType();

		updateCustomerName(customer, title, firstName, middleName, lastName,
				nameSuffix);

		return customer;

	}

	protected boolean nonBuyerWebOptIn;
	protected boolean directMailOptIn;
	protected boolean eMailOptIn;
	protected boolean phoneContactOptIn;
	protected boolean eMailProductUpdatesOptIn;
	protected boolean marketingOptIn;

	/**
	 * @param customer
	 * @param gender
	 * @param dob
	 * @param ssn
	 * @param primaryLanguage
	 * @return CustomerType
	 */
	public CustomerType updateCustomer(CustomerType customer, String gender,
			Date dob, String ssn, Integer primaryLanguage) {

		if (customer == null) {
			customer = oFactory.createCustomerType();
		}

		customer.setGender(gender);
		// customer.setDob(CalendarUtil.asXMLGregorianCalendar(dob));
		customer.setSsn(ssn);
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
	 * @return CustomerFinancialInfoType
	 */
	public CustomerFinancialInfoType createCustomerFinancialInfoType(
			final String mortgageInstitution, final String otherIncome,
			final String businessName, final String businessPhoneNum,
			final String occupation, final boolean isStudent,
			final boolean isRetired,
			final boolean isUnemployed) {
		CustomerFinancialInfoType custFinInfo = oFactory
		.createCustomerFinancialInfoType();
		custFinInfo.setBankOrMortgageInstitution(mortgageInstitution);
		custFinInfo.setOtherIncomeSource(otherIncome);

		if (custFinInfo.getEmployed() == null) {
			custFinInfo.setEmployed(oFactory
					.createCustomerFinancialInfoTypeEmployed());
		}

		custFinInfo.getEmployed().setBusinessName(businessName);
		custFinInfo.getEmployed().setBusinessPhoneNum(businessPhoneNum);
		custFinInfo.getEmployed().setOccupation(occupation);


		if (isStudent) {
			custFinInfo.setStudent(oFactory.createEmptyElementType());
		}

		if (isRetired) {
			custFinInfo.setRetired(oFactory.createEmptyElementType());
		}

		if (isUnemployed) {
			custFinInfo.setUnemployed(oFactory.createEmptyElementType());
		}

		return custFinInfo;
	}

	/**
	 * @param addressUniqueId
	 * @param billingUniqueId
	 * @return BillingInfoList
	 */
	public BillingInfoList getCustomerBillingInfo(String addressUniqueId, String billingUniqueId) {

		ObjectFactory oFactory = new ObjectFactory();
		BillingInfoList billingInfoList = oFactory.createBillingInfoList();

		int expiryYear = 2015;
		int expiryMonth = 7;
		String cardHolderName = "Test CardHolder CC";
		String creditCardNumber = "9856365212547854";
		String cardType = "AMERICAN_EXPRESS";
		String verificationCode = "986";
		String status = "active";


		CustBillingInfoType custBillingInfoTypeCC = newBillingAccountTypeCC(addressUniqueId, BillingAccountTypeType.CREDIT_CARD, "Saving",
				creditCardNumber, cardType, expiryYear, expiryMonth, cardHolderName, verificationCode,
				"", "", status, null, billingUniqueId+"-ccPayinfo");
		billingInfoList.getBillingInfo().add(custBillingInfoTypeCC);

		cardHolderName = "Test CardHolder CK";
		String routingNumber = "56897412";
		String ckAccountNumber = "1254789652364785";
		CustBillingInfoType custBillingInfoTypeCheckin = newBillingAccountTypeCK(addressUniqueId, cardHolderName, routingNumber,
				ckAccountNumber, status, billingUniqueId+"-ckPayinfo", "", "", "checking");
		billingInfoList.getBillingInfo().add(custBillingInfoTypeCheckin);

		return billingInfoList;
	}

	/**
	 * @param addressRef
	 * @param acountType
	 * @param method
	 * @param ccNumber
	 * @param ccType
	 * @param year
	 * @param month
	 * @param holderName
	 * @param verificationCode
	 * @param checkAcctNum
	 * @param routeNumber
	 * @param status
	 * @param id
	 * @param billingUniqueId
	 * @return CustBillingInfoType
	 */
	private CustBillingInfoType newBillingAccountTypeCC(String addressRef,
			BillingAccountTypeType acountType, String method, String ccNumber,
			String ccType, int year, int month, String holderName,
			String verificationCode, String checkAcctNum, String routeNumber,
			String status, String id, String billingUniqueId) {

		ObjectFactory oFactory = new ObjectFactory();
		CustBillingInfoType cBI = oFactory.createCustBillingInfoType();

		cBI.setIsChecking(0);
		cBI.setAddressRef(addressRef);
		cBI.setBillingAccountType(acountType);
		cBI.setBillingMethod(method);
		cBI.setCreditCardNumber(ccNumber);
		CreditCardTypeType ccTypeEnum = CreditCardTypeType.valueOf(ccType);
		cBI.setCreditCardType(ccTypeEnum);
		try {
			XMLGregorianCalendar x = DatatypeFactory.newInstance()
			.newXMLGregorianCalendar();
			x.setMonth(month);
			x.setYear(year);
			cBI.setExpirationYearMonth(x);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		cBI.setCardHolderName(holderName);
		cBI.setVerificationCode(verificationCode);
		cBI.setCheckingAccountNumber(checkAcctNum);
		cBI.setRoutingNumber(routeNumber);
		cBI.setStatus(status);
		cBI.setExternalId(0L);
		cBI.setBillingUniqueId(billingUniqueId);

		return cBI;
	}


	/**
	 * @param addressRef
	 * @param accountName
	 * @param routingNumber
	 * @param checkingAccountNumber
	 * @param status
	 * @param billingUniqueId
	 * @param licenseState
	 * @param licenseNumber
	 * @param account
	 * @return CustBillingInfoType
	 */
	private CustBillingInfoType newBillingAccountTypeCK(String addressRef, String accountName, String routingNumber, String checkingAccountNumber,
			String status, String billingUniqueId, String licenseState, String licenseNumber, String account) {
		ObjectFactory oFactory = new ObjectFactory();
		CustBillingInfoType cBI = oFactory.createCustBillingInfoType();

		cBI.setBillingAccountType(BillingAccountTypeType.ACH);
		cBI.setExternalId(0L);
		cBI.setBillingUniqueId(billingUniqueId);
		cBI.setAddressRef(addressRef);

		cBI.setCardHolderName(accountName);
		cBI.setStatus(status);
		cBI.setCheckingAccountNumber(checkingAccountNumber);
		cBI.setRoutingNumber(routingNumber);
		if(account.equals("checking")){
			cBI.setIsChecking(1);
		} else {
			cBI.setIsChecking(0);
		}

		return cBI;
	}

}
