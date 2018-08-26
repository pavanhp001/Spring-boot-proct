package com.AL.ui.activity.customer.impl;

import java.util.ArrayList;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.ui.activity.customer.CreateCustomerActivity;
import com.AL.ui.intent.Intent;
import com.AL.ui.service.V.UICartCustomerService;
import com.AL.ui.vo.ErrorList;

import com.AL.xml.cm.v4.AddressListType;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.BillingAccountTypeType;
import com.AL.xml.cm.v4.BillingInfoList;
import com.AL.xml.cm.v4.CreditCardTypeType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustBillingInfoType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.ObjectFactory;
import com.AL.xml.cm.v4.RoleType;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;

@Component
public class CreateCustomerActivityDefault implements CreateCustomerActivity {

	@Autowired
	private UICartCustomerService custService;
	
	public void startActivity(Intent intent) {

		String agentId =  intent.getStringExtra("agentId");
		String checkAllRoles = intent.getStringExtra("checkAllRoles");
		String[] roles =   intent.getStringArrayExtra("roles");
		AddressType address = (AddressType)intent.getExtras().get("address");
		CustomerType customerType = (CustomerType)intent.getExtras().get("customerType");

		ObjectFactory oFactory = new ObjectFactory();
		
		String addressUniqueId = customerType.getLastName()+"-addr";
		String billingUniqueId = customerType.getLastName();
		
		customerType.setReferrerId(123L);
		customerType.setAgentId(agentId);
		customerType.setExternalId(0L);

		AddressListType addressList = oFactory.createAddressListType();
		CustAddress custAddress = oFactory.createCustAddress();
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
		if(checkAllRoles == null){
			for (String role : roles) {
				addrRoles.getRole().add(RoleType.valueOf(role));
				custAddress.setAddressRoles(addrRoles);
			}
		} else {
			RoleType[] rolesArr = RoleType.values();
			for(RoleType role: rolesArr){
				addrRoles.getRole().add(role);
			}
			custAddress.setAddressRoles(addrRoles);
		}
		
		custAddress.setStatus("active");
		custAddress.setAddressUniqueId(addressUniqueId);
		custAddress.setAddress(address);
		addressList.getCustomerAddress().add(custAddress);
		
		customerType.setAddressList(addressList);

		BillingInfoList billingInfoList = getCustomerBillingInfo(addressUniqueId, billingUniqueId);
		customerType.setBillingInfoList(billingInfoList);

		CustomerType customer = custService.createCustomer(agentId,customerType, new ArrayList<String>(), new ErrorList());

		intent.getExtras().put("customer", customer);
		
	}
	
	private BillingInfoList getCustomerBillingInfo(String addressUniqueId, String billingUniqueId) {
		
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
	
	public CustBillingInfoType newBillingAccountTypeCC(String addressRef,
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
	
	public CustBillingInfoType newBillingAccountTypeCK(String addressRef, String accountName, String routingNumber, String checkingAccountNumber, 
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
