package com.AL.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ui.service.V.VClient;
import com.AL.ui.vo.ErrorList;
import com.AL.V.gateway.util.JaxbUtil;
import com.AL.xml.cm.v4.AddressListType;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.BillingAccountTypeType;
import com.AL.xml.cm.v4.BillingInfoList;
import com.AL.xml.cm.v4.CreditCardTypeType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustBillingInfoType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.RoleType;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.OrderType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class VClientTest {

	@Autowired
	private VClient VClient;
	
	private static final Logger logger = Logger.getLogger(VClientTest.class);
	
	@Test
	public void getCustomer(){
		
		CustomerType customer = VClient.get("default","6419", new ArrayList<String>(), new ErrorList());
		
		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
	}
	
	public CustomerType buildCustomer(){
		
		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
		
		CustomerType customerType = oFactory.createCustomerType();
		customerType.setFirstName("Swetha");
		customerType.setLastName("Myakagudem");
		
		String addressUniqueId = customerType.getLastName()+"-addr";
		String billingUniqueId = customerType.getLastName();
		String agentId = "default";
		
		customerType.setReferrerId(123L);
		customerType.setAgentId(agentId);
		customerType.setExternalId(0L);
		
		AddressListType addressList = oFactory.createAddressListType();
		CustAddress custAddress = oFactory.createCustAddress();
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
		
		addrRoles.getRole().add(RoleType.BILLING_ADDRESS);
		addrRoles.getRole().add(RoleType.HOME_ADDRESS);
		addrRoles.getRole().add(RoleType.SERVICE_ADDRESS);
		custAddress.setAddressRoles(addrRoles);
		
		AddressType address = oFactory.createAddressType();
		address.setCountry("United States");
		address.setCity("city");
		address.setStateOrProvince("Alabama");
		custAddress.setStatus("active");
		custAddress.setAddressUniqueId(addressUniqueId);
		custAddress.setAddress(address);
		addressList.getCustomerAddress().add(custAddress);
		
		BillingInfoList billingInfoList = getCustomerBillingInfo(addressUniqueId, billingUniqueId);
		customerType.setBillingInfoList(billingInfoList);
		
		return customerType;
	}
	
	@Test
	public void newCustomer(){
		
		CustomerType customerType = buildCustomer();
		
		CustomerType customer = VClient.createCustomer("default",customerType, new ArrayList<String>(), new ErrorList());
		
		JaxbUtil<CustomerType> utilCust = new JaxbUtil<CustomerType>();

		logger.info("Customer Response ---->>>>>>"+ utilCust.toString(customer, CustomerType.class));
		
		logger.info("Customer ID:"+customer.getReferrerId());
		logger.info("External ID:"+customer.getExternalId());
	}

	private BillingInfoList getCustomerBillingInfo(String addressUniqueId, String billingUniqueId) {
		
		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
		
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
	
	public CustBillingInfoType newBillingAccountTypeCC(String addressRef,
			BillingAccountTypeType acountType, String method, String ccNumber,
			String ccType, int year, int month, String holderName,
			String verificationCode, String checkAcctNum, String routeNumber,
			String status, String id, String billingUniqueId) {

		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
		
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
		
		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
		
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
	
	@Test
	public void newOrderAndCustomer(){
		String agentId = "default";
		
		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		
		CustomerType customerType = buildCustomer();
		
		CustomerType customer_new = VClient.createCustomer("default",customerType, new ArrayList<String>(), new ErrorList());
		
		OrderType order = new OrderType();
		order.setExternalId(0L);
		order.setSource("Web");
		order.setReferrerId("108");
		order.setAgentId(agentId);
		
		Customer customer = new Customer();
		customer.setExternalId(customer_new.getExternalId());
		
		LineItemCollectionType lineItemsCollectionType = oFactory.createLineItemCollectionType();
		order.setLineItems(lineItemsCollectionType);
		
		OrderType orderType = VClient.createOrderAndCustomer(agentId, order, customer, new ArrayList<String>(), new ErrorList());
		
		JaxbUtil<OrderType> utilCust = new JaxbUtil<OrderType>();

		logger.info("Order Response ---->>>>>>"+ utilCust.toString(orderType, OrderType.class));
		
	}
	
	@Test
	public void newOrderExistingCustomer(){
		
		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		
		String agentId = "default";
		
		CustomerType customer_exist = VClient.get("default","6419", new ArrayList<String>(), new ErrorList());

		OrderType order = new OrderType();
		order.setExternalId(0L);
		order.setSource("Web");
		order.setReferrerId("108");
		order.setAgentId(agentId);
		
		Customer customer = new Customer();
		customer.setExternalId(customer_exist.getExternalId());
		
		LineItemCollectionType lineItemsCollectionType = oFactory.createLineItemCollectionType();
		order.setLineItems(lineItemsCollectionType);
		
		OrderType orderType = VClient.createOrderExistingCustomer(agentId, order, customer, new ArrayList<String>(), new ErrorList());
		
		JaxbUtil<OrderType> utilCust = new JaxbUtil<OrderType>();

		logger.info("Order Response ---->>>>>>"+ utilCust.toString(orderType, OrderType.class));
	}
	
	@Test
	public void getOrderByCustomer(){
		String agentId = "default";
		String customerId = "38039";
		
		List<OrderType> orderList = VClient.getOrderByCustomer(agentId, customerId, new ArrayList<String>(), new ErrorList());
		if(orderList != null && orderList.size() > 0 ){
			for(OrderType order : orderList){
				logger.info("Order Id :::::::: "+order.getExternalId()); 
			}
		} else {
			logger.info("No orders ........");
		}
		
	}
}
