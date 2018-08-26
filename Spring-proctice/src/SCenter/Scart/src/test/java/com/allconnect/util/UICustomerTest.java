package com.AL.util;

import java.util.*;

import javax.xml.datatype.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.controller.ajax.CartFeedbackOnStartController;
import com.AL.ui.service.V.UICartCustomerService;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.factory.CartCustomerFactory;
import com.AL.V.gateway.util.JaxbUtil;
import com.AL.xml.cm.v4.*;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class UICustomerTest {

	@Autowired
	private UICartCustomerService custService;

	private ObjectFactory oFactory = new ObjectFactory();
	
	private static final Logger logger = Logger.getLogger(UICustomerTest.class);

	@Test
	public void getCustomer() {

		CustomerType customer = custService.get("default","6419", new ArrayList<String>(), new ErrorList());

		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
	}

	//@Test
	public void createCustomer(){
		CustomerType customerType = oFactory.createCustomerType();
		customerType.setFirstName("Test");
		customerType.setLastName("Test");
		AddressType address=oFactory.createAddressType();
		address.setAddressBlock("test block");
		address.setCountry("USA");
		address.setCity("South Holland");
		address.setStateOrProvince("IL");
		address.setPostalCode("60473");
		String addressUniqueId = customerType.getLastName()+"-addr";
		String billingUniqueId = customerType.getLastName();
		String agentId = "default";

		customerType.setReferrerId(123L);
		customerType.setAgentId(agentId);
		customerType.setExternalId(0L);

		AddressListType addressList = oFactory.createAddressListType();
		CustAddress custAddress = oFactory.createCustAddress();
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();

		RoleType[] rolesArr = RoleType.values();
		for(RoleType role: rolesArr){
			addrRoles.getRole().add(role);
		}
		custAddress.setAddressRoles(addrRoles);
		custAddress.setStatus("active");
		custAddress.setAddressUniqueId(addressUniqueId);
		custAddress.setAddress(address);
		addressList.getCustomerAddress().add(custAddress);

		BillingInfoList billingInfoList = getCustomerBillingInfo(addressUniqueId, billingUniqueId);
		customerType.setBillingInfoList(billingInfoList);
		customerType.setAddressList(addressList);
		CustomerType customer = custService.createCustomer("default",customerType, new ArrayList<String>(), new ErrorList());

		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
	}

	//@Test
	public void saveName(){

		CustomerType cust= oFactory.createCustomerType();
		cust=CartCustomerFactory.INSTANCE.updateCustomerName("U", "AdrianU", "MostlyU", "PU", "M");
		CustomerType customer = custService.saveName("default", "6419", cust, new ArrayList<String>(), new ErrorList());

		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));

	}

	//@Test
	public void saveAddress(){

		AddressType address=oFactory.createAddressType();
		address.setAddressBlock("test block");
		address.setCountry("USA");
		address.setCity("South Holland");
		address.setStateOrProvince("IL");
		address.setPostalCode("60473");
		String agentId = "default";
		String addressUniqueId = agentId + System.nanoTime();

		CustAddress custAddress = oFactory.createCustAddress();
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
		RoleType[] rolesArr = RoleType.values();
		for(RoleType role: rolesArr){
			addrRoles.getRole().add(role);
		}
		custAddress.setAddressRoles(addrRoles);
		custAddress.setStatus("active");
		custAddress.setAddressUniqueId(addressUniqueId);
		custAddress.setAddress(address);


		CustomerType customer = custService.saveAddress(agentId, "6419", custAddress, new ArrayList<String>(), new ErrorList());

		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));

	}

	//@Test
	public void saveBillingInfo(){

		CustomerType customerType = oFactory.createCustomerType();
		String agentId = "default";
		String addressUniqueId = agentId + System.nanoTime()+"-addr";
		String billingUniqueId = agentId + System.nanoTime();
		BillingInfoList billingInfoList = getCustomerBillingInfo(addressUniqueId, billingUniqueId);
		customerType.setBillingInfoList(billingInfoList);
		CustomerType customer = custService.saveBillingInfo(agentId, "6419", customerType,new ArrayList<String>(),new ErrorList());

		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
	}

	//@Test
	public void savePaymentEvent(){

		CustomerType customerType = oFactory.createCustomerType();

		String agentId ="default";

		String addressRef = "";
		String accountName = "";
		String licenseState = "";
		String licenseNumber = "";
		String checkingAccountNumber = "";
		String routingNumber = "";
		String account = "";
		BillingInfoList billingInfoList = oFactory.createBillingInfoList();

		CustBillingInfoType billingInfoType = newBillingAccountTypeCK(addressRef, accountName,	routingNumber, checkingAccountNumber, 
				"active", getBillingUniqueId(), licenseState, licenseNumber, account);

		billingInfoList.getBillingInfo().add(billingInfoType);

		customerType.setBillingInfoList(billingInfoList);

		CustomerType customer = custService.savePaymentEvent(agentId, "6419", customerType,new ArrayList<String>(),new ErrorList());
		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));

	}
	//@Test
	public void saveIdentification(){

		CustomerType customerType = oFactory.createCustomerType();
		Date driverLicenseExpireDate = new Date();

		Date dobDate = DateUtil.fromStringToDate("05/01/1990");
		Integer primaryLanguageCode = Integer.valueOf("1");

		customerType = CartCustomerFactory.INSTANCE.updateCustomer(customerType, "M",
				dobDate, "678-12-1234", primaryLanguageCode);


		DriverLicenseType dlType = CartCustomerFactory.INSTANCE
		.createDriverLicense("023456421", "GeorgiaU",
				driverLicenseExpireDate);

		StateIdType stType = CartCustomerFactory.INSTANCE.createStateId(
				"896929446", "GeorgiaU");

		customerType.setDriverLicense(dlType);
		customerType.setStateId(stType);
		CustomerType customer = custService.saveIdentification("default", "6419", customerType,new ArrayList<String>(),new ErrorList());
		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
	}

	//@Test
	public void saveContactInfo(){

		CustomerType customerType = oFactory.createCustomerType();
		CartCustomerFactory.INSTANCE.updateContactInformation(customerType,"404-567-6789", "678-123-6789", "404-567-6789", "ptestUPDATED@work.com", "","ptestUPDATED@home.com");
		CustomerType customer = custService.saveContactInfo("default", "6419", customerType,new ArrayList<String>(),new ErrorList());
		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));

	}
	
	//@Test
	public void saveFinancialInfo(){
		CustomerType customerType = oFactory.createCustomerType();
		CustomerFinancialInfoType finInfoType = CartCustomerFactory.INSTANCE.createCustomerFinancialInfoType("","","", "", "Job",false, false,true);
		customerType.setFinancialInfo(finInfoType);
		CustomerType customer = custService.saveFinancialInfo("default", "6419", customerType,new ArrayList<String>(),new ErrorList());
		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
	}
	
	//@Test
	public void saveOptions(){
		
		CustomerType customerType = oFactory.createCustomerType();
		customerType.setNonBuyerWebOptIn(true);
		customerType.setDirectMailOptIn(true);
		customerType.setEMailOptIn(true);
		customerType.setPhoneContactOptIn(true);
		customerType.setEMailProductUpdatesOptIn(true);
		customerType.setMarketingOptIn(true);
		CustomerType customer = custService.saveOptions("default", "6419", customerType,new ArrayList<String>(),new ErrorList());
		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
	}
	
	//@Test
	public void updateCustomer(){
		
	}
	
	private String getBillingUniqueId() {
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());

		int random = generator.nextInt(99999) + 99999;
		if (random < 100000 || random > 999999) {
			random = generator.nextInt(99999) + 99999;
			if (random < 100000) {
				System.out
				.println("Unable to generate random billingUniqueId at this time..");
				random = random + 100000;
			} else if (random > 999999) {
				System.out
				.println("Unable to generate random billingUniqueId at this time..");
				random = 123456;
			}
		}

		String billingUniqueId = String.valueOf(random);
		logger.info("Random UniqueId ::::: " + billingUniqueId);
		return billingUniqueId;
	}
	private BillingInfoList getCustomerBillingInfo(String addressUniqueId, String billingUniqueId) {

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
				ckAccountNumber, status, billingUniqueId+"-ckPayinfo");
		billingInfoList.getBillingInfo().add(custBillingInfoTypeCheckin);

		return billingInfoList;
	}

	public CustBillingInfoType newBillingAccountTypeCK(String addressRef,
			String name, String routingNumber, String accountNumber,
			String status, String billingUniqueId) {

		CustBillingInfoType cBI = oFactory.createCustBillingInfoType();

		cBI.setBillingAccountType(BillingAccountTypeType.ACH);
		cBI.setIsChecking(1);
		cBI.setExternalId(0L);
		cBI.setBillingUniqueId(billingUniqueId);
		cBI.setAddressRef(addressRef);

		cBI.setCardHolderName(name);
		cBI.setStatus(status);
		cBI.setCheckingAccountNumber(accountNumber);
		cBI.setRoutingNumber(routingNumber);

		return cBI;
	}

	public CustBillingInfoType newBillingAccountTypeCC(String addressRef,
			BillingAccountTypeType acountType, String method, String ccNumber,
			String ccType, int year, int month, String holderName,
			String verificationCode, String checkAcctNum, String routeNumber,
			String status, String id, String billingUniqueId) {

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
	public CustBillingInfoType newBillingAccountTypeCK(String addressRef, String accountName, String routingNumber, String checkingAccountNumber, 
			String status, String billingUniqueId, String licenseState, String licenseNumber, String account) {

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
