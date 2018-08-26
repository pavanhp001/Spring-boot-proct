package com.A.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.A.xml.cm.v4.AddressType;
import com.A.xml.cm.v4.BillingAccountTypeType;
import com.A.xml.cm.v4.CustAddress;
import com.A.xml.cm.v4.CustBillingInfoType;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.PaymentEventType;
import com.A.xml.v4.AddressListType;
import com.A.xml.v4.BillingInfoList;
import com.A.xml.v4.CustAddress.AddressRoles;
import com.A.xml.v4.Customer;

public class CopyCmCustomerToOmeUtil {
	
	public static Map<com.A.xml.cm.v4.RoleType, com.A.xml.v4.RoleType> ADDR_ROLE_MAP = 
			new HashMap<com.A.xml.cm.v4.RoleType, com.A.xml.v4.RoleType>();
	static {
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.BILLING_ADDRESS, com.A.xml.v4.RoleType.BILLING_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.SHIPPING_ADDRESS, com.A.xml.v4.RoleType.SHIPPING_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.MAILING_ADDRESS, com.A.xml.v4.RoleType.MAILING_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.SERVICE_ADDRESS, com.A.xml.v4.RoleType.SERVICE_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.CORRECTED_ADDRESS, com.A.xml.v4.RoleType.CORRECTED_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.DT_ADDRESS, com.A.xml.v4.RoleType.DT_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.HOME_ADDRESS, com.A.xml.v4.RoleType.HOME_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.CURRENT_ADDRESS, com.A.xml.v4.RoleType.CURRENT_ADDRESS);
		ADDR_ROLE_MAP.put(com.A.xml.cm.v4.RoleType.PREVIOUS_ADDRESS, com.A.xml.v4.RoleType.PREVIOUS_ADDRESS);
	}
	
	public static Map<com.A.xml.cm.v4.CreditCardTypeType, com.A.xml.v4.CreditCardTypeType> CARD_TYPE_MAP = 
			new HashMap<com.A.xml.cm.v4.CreditCardTypeType, com.A.xml.v4.CreditCardTypeType>();
	static {
		CARD_TYPE_MAP.put(com.A.xml.cm.v4.CreditCardTypeType.VISA, com.A.xml.v4.CreditCardTypeType.VISA);
		CARD_TYPE_MAP.put(com.A.xml.cm.v4.CreditCardTypeType.MASTER_CARD, com.A.xml.v4.CreditCardTypeType.MASTER_CARD);
		CARD_TYPE_MAP.put(com.A.xml.cm.v4.CreditCardTypeType.DISCOVER, com.A.xml.v4.CreditCardTypeType.DISCOVER);
		CARD_TYPE_MAP.put(com.A.xml.cm.v4.CreditCardTypeType.AMERICAN_EXPRESS, com.A.xml.v4.CreditCardTypeType.AMERICAN_EXPRESS);
		CARD_TYPE_MAP.put(com.A.xml.cm.v4.CreditCardTypeType.OPTIMA, com.A.xml.v4.CreditCardTypeType.OPTIMA);
	}

	public static com.A.xml.v4.Customer copyCmCustomerToOmeCustomer(CustomerType cmCustomer) {
		com.A.xml.v4.Customer omeCustomer = new Customer();
		omeCustomer.setPayments(copyPaymentsType(cmCustomer.getPayments()));
		omeCustomer.setAddressList(copyAddressList(cmCustomer.getAddressList()));
		omeCustomer.setAgentId(cmCustomer.getAgentId());
		omeCustomer.setAgentName(cmCustomer.getAgentName());
		omeCustomer.setBestEmailContact(cmCustomer.getBestEmailContact());
		omeCustomer.setDob(cmCustomer.getDob());
		omeCustomer.setFirstName(cmCustomer.getFirstName());
		omeCustomer.setLastName(cmCustomer.getLastName());
		omeCustomer.setExternalId(cmCustomer.getExternalId());
		omeCustomer.setMiddleName(cmCustomer.getMiddleName());
		omeCustomer.setNameSuffix(cmCustomer.getNameSuffix());
		omeCustomer.setBillingInfoList(copyBillingInfoList(cmCustomer.getBillingInfoList()));
		omeCustomer.setSsn(cmCustomer.getSsn());
		omeCustomer.setTitle(cmCustomer.getTitle());
		omeCustomer.setBestPhoneContact(cmCustomer.getBestPhoneContact());
		omeCustomer.setSecondPhone(cmCustomer.getSecondPhone());
		com.A.xml.v4.PhoneNumberType homePhoneNumberType = new com.A.xml.v4.PhoneNumberType();
		omeCustomer.setHomePhoneNumber(homePhoneNumberType);
		return omeCustomer;
	}
	
	private static com.A.xml.v4.PaymentsType copyPaymentsType(com.A.xml.cm.v4.PaymentsType cmPayments) {
		com.A.xml.v4.PaymentsType payments = new com.A.xml.v4.PaymentsType();
		List<com.A.xml.v4.PaymentEventType> paymentList = payments.getPaymentEvent();
		for(com.A.xml.cm.v4.PaymentEventType event : cmPayments.getPaymentEvent()) {
			paymentList.add(copyPaymentEvent(event));
		}
		return payments;
	}
	
	private static com.A.xml.v4.PaymentEventType copyPaymentEvent(PaymentEventType cmPaymentEvent) {
		com.A.xml.v4.PaymentEventType omeEvent = new com.A.xml.v4.PaymentEventType();
		omeEvent.setAmount(cmPaymentEvent.getAmount());
		omeEvent.setBillingInfoId(cmPaymentEvent.getBillingInfoId());
		omeEvent.setConfirmNumber(cmPaymentEvent.getConfirmNumber());
		omeEvent.setCustAgreedCCDisclosure(cmPaymentEvent.getCustAgreedCCDisclosure());
		omeEvent.setCVV(cmPaymentEvent.getCVV());
		omeEvent.setEvent(com.A.xml.v4.PaymentEventTypeType.valueOf(cmPaymentEvent.getEvent().name()));
		omeEvent.setExternalId(cmPaymentEvent.getExternalId());
		omeEvent.setLineItemId(cmPaymentEvent.getLineItemId());
		omeEvent.setOrderId(cmPaymentEvent.getOrderId());
		omeEvent.setTransactionDate(cmPaymentEvent.getTransactionDate());
		return omeEvent;
	}
	
	private static com.A.xml.v4.AddressListType copyAddressList(com.A.xml.cm.v4.AddressListType cmAddressList) {
		com.A.xml.v4.AddressListType addressList = new AddressListType();
		List<com.A.xml.v4.CustAddress> resultList = addressList.getCustomerAddress();
		for(CustAddress address : cmAddressList.getCustomerAddress()) {
			resultList.add(copyAddress(address));
		}
		return addressList;
	}
	
	private static com.A.xml.v4.CustAddress copyAddress(CustAddress cmAddress) {
		com.A.xml.v4.CustAddress omeAddress = new com.A.xml.v4.CustAddress();
		omeAddress.setAddress(createAddress(cmAddress.getAddress()));
		List<com.A.xml.v4.RoleType> omeRoles = new ArrayList<com.A.xml.v4.RoleType>();
		for(com.A.xml.cm.v4.RoleType cmRole : cmAddress.getAddressRoles().getRole()) {
			omeRoles.add(ADDR_ROLE_MAP.get(cmRole));
		}
		AddressRoles addressRoles = new AddressRoles();
		addressRoles.getRole().addAll(omeRoles);
		omeAddress.setAddressRoles(addressRoles);
		omeAddress.setAddressUniqueId(cmAddress.getAddressUniqueId());
		return omeAddress;
	}
	
	private static com.A.xml.v4.AddressType createAddress(AddressType cmAddress) {
		com.A.xml.v4.AddressType address = new com.A.xml.v4.AddressType();
		address.setAddressBlock(cmAddress.getAddressBlock());
		address.setCity(cmAddress.getCity());
		address.setExternalId(cmAddress.getExternalId());
		address.setLine2(cmAddress.getLine2());
		address.setPostalCode(cmAddress.getPostalCode());
		address.setPostfixDirectional(cmAddress.getPostfixDirectional());
		address.setPrefixDirectional(cmAddress.getPrefixDirectional());
		address.setStateOrProvince(cmAddress.getStateOrProvince());
		address.setStreetName(cmAddress.getStreetName());
		address.setStreetNumber(cmAddress.getStreetNumber());
		address.setStreetType(cmAddress.getStreetType());
		return address;
	}
	
	private static com.A.xml.v4.CustBillingInfoType copyBillingInfo(CustBillingInfoType cmBillingInfo) {
        com.A.xml.v4.CustBillingInfoType orderBillingInfo = new com.A.xml.v4.CustBillingInfoType();
        orderBillingInfo.setAddressRef(cmBillingInfo.getAddressRef());
        orderBillingInfo.setCardHolderName(cmBillingInfo.getCardHolderName());
        orderBillingInfo.setExpirationYearMonth(cmBillingInfo.getExpirationYearMonth());
        orderBillingInfo.setExternalId(cmBillingInfo.getExternalId());
        orderBillingInfo.setCreditCardNumber(cmBillingInfo.getCreditCardNumber());
        orderBillingInfo.setVerificationCode(cmBillingInfo.getVerificationCode());
        orderBillingInfo.setBillingUniqueId(cmBillingInfo.getBillingUniqueId());
        orderBillingInfo.setStatus(cmBillingInfo.getStatus());
        orderBillingInfo.setCreditCardType(CARD_TYPE_MAP.get(cmBillingInfo.getCreditCardType()));
        orderBillingInfo.setBillingMethod(BillingAccountTypeType.CREDIT_CARD.value());
        return orderBillingInfo;
	}

	private static BillingInfoList copyBillingInfoList(com.A.xml.cm.v4.BillingInfoList billingInfoList) {
		BillingInfoList billingInfo = new BillingInfoList();
		List<com.A.xml.v4.CustBillingInfoType> billInfoList = billingInfo.getBillingInfo();
		for(com.A.xml.cm.v4.CustBillingInfoType billInfo : billingInfoList.getBillingInfo()) {
			billInfoList.add(copyBillingInfo(billInfo));
		}
		return billingInfo;
	}
}
