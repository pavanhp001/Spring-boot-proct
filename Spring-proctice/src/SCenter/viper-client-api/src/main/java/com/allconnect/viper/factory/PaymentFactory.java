package com.A.V.factory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.A.ui.service.V.CustomerService;
import com.A.util.DateUtil;
import com.A.xml.cm.v4.BillingAccountTypeType;
import com.A.xml.cm.v4.BillingInfoList;
import com.A.xml.cm.v4.CreditCardTypeType;
import com.A.xml.cm.v4.CustBillingInfoType;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.PaymentEventType;
import com.A.xml.cm.v4.PaymentEventTypeType;
import com.A.xml.cm.v4.PaymentStatusWithTypeType;

public enum PaymentFactory {

	INSTANCE;
	
	public static final Logger logger = Logger.getLogger(PaymentFactory.class);
	
	public PaymentStatusWithTypeType createPaymentStatus(final int providerId, final int statusId) {
		
		ObjectFactory oFactory = new ObjectFactory();
		
		PaymentStatusWithTypeType statusType = oFactory
				.createPaymentStatusWithTypeType();
		statusType.setProviderId(providerId);
		statusType.setStatus(statusId);
		statusType.getReason().add(1);
		statusType.setExternalId(0);
		statusType.setDateTimeStamp(DateUtil.getCurrentXMLDate());
		
		return statusType;
	}
	
	public PaymentEventType createPaymentStatus(PaymentEventTypeType eventType, BigDecimal amount, final String orderId, final String lineItemId, final String billingInfo, final String confirmNumber, final String cvv) {
		
		ObjectFactory oFactory = new ObjectFactory();
		
		
		PaymentEventType paymentEvent = oFactory.createPaymentEventType();
		paymentEvent.setExternalId("0");
		paymentEvent.setEvent(eventType);
		paymentEvent.setAmount(amount);
		paymentEvent.setLineItemId(lineItemId);
		paymentEvent.setBillingInfoId(billingInfo);
		paymentEvent.setConfirmNumber(confirmNumber);
		paymentEvent.setOrderId(orderId);
		paymentEvent.setCVV(cvv);
		
		
		PaymentStatusWithTypeType statusType = PaymentFactory.INSTANCE
				.createPaymentStatus(1111, 1);

		paymentEvent.getPaymentStatus().add(statusType);
		
		
		
		return paymentEvent;
	}
	
	public PaymentEventType create(String amount, String billingInfoId,
			String cvv, String paymentEventType, String lineitemId,
			String orderId, String confirmNumber, int providerId, int status) {

		ObjectFactory oFactory = new ObjectFactory();
		PaymentEventType payEventType = oFactory.createPaymentEventType();

		payEventType.setAmount(new BigDecimal(amount));
		payEventType.setBillingInfoId(billingInfoId);
		payEventType.setConfirmNumber(confirmNumber);
		payEventType.setCVV(cvv);
		payEventType.setEvent(PaymentEventTypeType.valueOf(paymentEventType));
		payEventType.setLineItemId(lineitemId);
		payEventType.setOrderId(orderId);

		PaymentStatusWithTypeType statusType = oFactory
				.createPaymentStatusWithTypeType();
		statusType.setProviderId(providerId);
		statusType.setStatus(status);

		// payEventType.setPaymentStatus(statusType);
		payEventType.setTransactionDate(null);

		return payEventType;

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

	public BillingInfoList updateBillingInfoType(String cust_id,
			String billing_id, int year, int month, String cardHolderName,
			String addressRef,String ccType,String cardNum) {
		ObjectFactory oFactory = new ObjectFactory();
		BillingInfoList billingInfoList = oFactory.createBillingInfoList();

		CustomerType customer = CustomerService.INSTANCE
				.getCustomerType(cust_id);
		List<CustBillingInfoType> billingList = customer.getBillingInfoList()
				.getBillingInfo();
		for (CustBillingInfoType billingInfoType : billingList) {
			String externalId = billingInfoType.getExternalId() + "";
			if (externalId.equals(billing_id)) {
				CustBillingInfoType updatedBillingInfoType = billingInfoType;
				updatedBillingInfoType.setAddressRef(addressRef);
				try {
					XMLGregorianCalendar x = DatatypeFactory.newInstance()
							.newXMLGregorianCalendar();
					x.setMonth(month);
					x.setYear(year);
					updatedBillingInfoType.setExpirationYearMonth(x);
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				updatedBillingInfoType.setExternalId(billingInfoType
						.getExternalId());
				updatedBillingInfoType.setCardHolderName(cardHolderName);
				updatedBillingInfoType.setCreditCardNumber(cardNum);
				CreditCardTypeType ccTypeEnum = CreditCardTypeType.valueOf(ccType);
				updatedBillingInfoType.setCreditCardType(ccTypeEnum);
				
				billingInfoList.getBillingInfo().add(updatedBillingInfoType);
				break;
			}
		}
		return billingInfoList;
	}
	
	public BillingInfoList updateBillingInfoTypeCK(String cust_id,String billing_id,String addressRef, String accountName, String routingNumber, String checkingAccountNumber, 
			 String licenseState, String licenseNumber, String account){
		ObjectFactory oFactory = new ObjectFactory();
		BillingInfoList billingInfoList = oFactory.createBillingInfoList();

		CustomerType customer = CustomerService.INSTANCE
				.getCustomerType(cust_id);
		List<CustBillingInfoType> billingList = customer.getBillingInfoList()
				.getBillingInfo();
		for (CustBillingInfoType billingInfoType : billingList) {
			String externalId = billingInfoType.getExternalId() + "";
			if (externalId.equals(billing_id)) {
				CustBillingInfoType updatedBillingInfoType = billingInfoType;
				updatedBillingInfoType.setAddressRef(addressRef);
				if(routingNumber != null && !routingNumber.trim().equals("")){
					updatedBillingInfoType.setRoutingNumber(routingNumber);
				}
				
				updatedBillingInfoType.setBillingAccountType(BillingAccountTypeType.ACH);
				updatedBillingInfoType.setExternalId(billingInfoType.getExternalId());
				updatedBillingInfoType.setCardHolderName(accountName);
				
				if(checkingAccountNumber != null && !checkingAccountNumber.trim().equals("")){
					updatedBillingInfoType.setCheckingAccountNumber(checkingAccountNumber);
				}
				
				if(account.equals("checking")){
					updatedBillingInfoType.setIsChecking(1);
				} else {
					updatedBillingInfoType.setIsChecking(0);
				}
				billingInfoList.getBillingInfo().add(updatedBillingInfoType);
				break;
			}
			
		}
		return billingInfoList;
	}

	public CustBillingInfoType newBillingAccountTypeCK(String addressRef, String accountName, String routingNumber, String checkingAccountNumber, 
			String status, String billingUniqueId, String licenseState, String licenseNumber, String account, String method) {
		ObjectFactory oFactory = new ObjectFactory();
		CustBillingInfoType cBI = oFactory.createCustBillingInfoType();

		cBI.setBillingAccountType(BillingAccountTypeType.ACH);
		cBI.setExternalId(0L);
		cBI.setBillingMethod(method);
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

	public String getBillingUniqueId() {
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());

		int random = generator.nextInt(99999) + 99999;
		if (random < 100000 || random > 999999) {
			random = generator.nextInt(99999) + 99999;
			if (random < 100000) {
				logger.info("Unable to generate random billingUniqueId at this time..");
				random = random + 100000;
			} else if (random > 999999) {
				logger.info("Unable to generate random billingUniqueId at this time..");
				random = 123456;
			}
		}

		String billingUniqueId = String.valueOf(random);
		logger.debug("Random UniqueId ::::: " + billingUniqueId);
		return billingUniqueId;
	}
	
}
