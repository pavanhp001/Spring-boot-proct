package com.A.util;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.A.xml.v4.AddressType;
import com.A.xml.v4.CustAddress;
import com.A.xml.v4.CustBillingInfoType;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.PaymentEventType;
import com.A.xml.v4.PaymentsType;
import com.A.xml.v4.AddressListType;
import com.A.xml.v4.BillingInfoList;
import com.A.xml.cm.v4.CustAddress.AddressRoles;
import com.A.xml.v4.PhoneNumberType;
import com.A.xml.v4.EMailAddressType;
import com.A.xml.v4.SecurityVerificationType;
import com.A.xml.v4.DriverLicenseType;
import com.A.xml.cm.v4.DwellingType;
import com.A.xml.cm.v4.OwnershipType;


public class CustomerUtil {

	public static com.A.xml.cm.v4.CustomerType copyCustomerInfo(CustomerType omeCustomer) {
		com.A.xml.cm.v4.CustomerType cmCustomer = new com.A.xml.cm.v4.CustomerType();
		cmCustomer.setExternalId(omeCustomer.getExternalId());
		cmCustomer.setReferrerId(omeCustomer.getReferrerId());
		cmCustomer.setReferrerGeneralName(omeCustomer.getReferrerGeneralName());
		cmCustomer.setPartnerAccountId(omeCustomer.getPartnerAccountId());
		cmCustomer.setDtAgentId(omeCustomer.getDtAgentId());
		cmCustomer.setDtCreated(omeCustomer.getDtCreated());
		cmCustomer.setAgentId(omeCustomer.getAgentId());
		cmCustomer.setAgentName(omeCustomer.getAgentName());
		cmCustomer.setTitle(omeCustomer.getTitle());
		cmCustomer.setFirstName(omeCustomer.getFirstName());
		cmCustomer.setLastName(omeCustomer.getLastName());
		cmCustomer.setMiddleName(omeCustomer.getMiddleName());
		cmCustomer.setNameSuffix(omeCustomer.getNameSuffix());
		cmCustomer.setDob(omeCustomer.getDob());		
		cmCustomer.setSsn(omeCustomer.getSsn());
		cmCustomer.setGender(omeCustomer.getGender());
		cmCustomer.setNonBuyerWebOptIn(omeCustomer.isNonBuyerWebOptIn());
		cmCustomer.setDirectMailOptIn(omeCustomer.isDirectMailOptIn());
		cmCustomer.setEMailOptIn(omeCustomer.isEMailOptIn());
		cmCustomer.setPhoneContactOptIn(omeCustomer.isPhoneContactOptIn());
		cmCustomer.setEMailProductUpdatesOptIn(omeCustomer.isEMailProductUpdatesOptIn());
		cmCustomer.setMarketingOptIn(omeCustomer.isMarketingOptIn());
		cmCustomer.setOffersPresented(omeCustomer.getOffersPresented());
		cmCustomer.setHowLongAtPreviousAddress(omeCustomer.getHowLongAtPreviousAddress());
		cmCustomer.setBestTimeToCall1(omeCustomer.getBestTimeToCall1());
		cmCustomer.setBestTimeToCall2(omeCustomer.getBestTimeToCall2());
		cmCustomer.setBestTimeToCallPhone(omeCustomer.getBestTimeToCallPhone());
		cmCustomer.setBestEmailContact(omeCustomer.getBestEmailContact());
		cmCustomer.setBestPhoneContact(omeCustomer.getBestPhoneContact());
		cmCustomer.setPartnerName(omeCustomer.getPartnerName());
		cmCustomer.setPartnerSSN(omeCustomer.getPartnerSSN());
		cmCustomer.setSecondPhone(omeCustomer.getSecondPhone());
		cmCustomer.setACustomerNumber(omeCustomer.getACustomerNumber());
		cmCustomer.setHomePhoneNumber(copyPhoneNumberType(omeCustomer.getHomePhoneNumber()));
		cmCustomer.setCellPhoneNumber(copyPhoneNumberType(omeCustomer.getCellPhoneNumber()));
		cmCustomer.setWorkPhoneNumber(copyPhoneNumberType(omeCustomer.getWorkPhoneNumber()));
		cmCustomer.setWorkPhoneNumberExtn(omeCustomer.getWorkPhoneNumberExtn());
		cmCustomer.setHomeEMail(copyEmailAddressType(omeCustomer.getHomeEMail()));
		cmCustomer.setWorkEMail(copyEmailAddressType(omeCustomer.getWorkEMail()));
		cmCustomer.setDriverLicense(copyDriverLicense(omeCustomer.getDriverLicense()));
		cmCustomer.setPayments(copyPaymentsType(omeCustomer.getPayments()));
		cmCustomer.setProviderCustomerType(omeCustomer.getProviderCustomerType());
		cmCustomer.setSecurityVerificationInfo(copySecurityVerificationType(omeCustomer.getSecurityVerificationInfo()));
		cmCustomer.setBillingInfoList(copyBillingInfoList(omeCustomer.getBillingInfoList()));
		cmCustomer.setAddressList(copyAddressList(omeCustomer.getAddressList()));
		cmCustomer.setBillingDeliveryPreference(omeCustomer.getBillingDeliveryPreference());
		return cmCustomer;
	}

	
	public static com.A.xml.cm.v4.EMailAddressType copyEmailAddressType(EMailAddressType omeEmailAddress) {
		if(omeEmailAddress == null) {
			return null;
		}
		com.A.xml.cm.v4.EMailAddressType cmEmailAddress = new com.A.xml.cm.v4.EMailAddressType();
		try {
			BeanUtils.copyProperties(cmEmailAddress, omeEmailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cmEmailAddress;
	}
	
	public static com.A.xml.cm.v4.SecurityVerificationType copySecurityVerificationType(SecurityVerificationType omeSecurityVerification) {
		if(omeSecurityVerification == null) {
			return null;
		}
		com.A.xml.cm.v4.SecurityVerificationType cmSecurityVerification = new com.A.xml.cm.v4.SecurityVerificationType();
		try {
			BeanUtils.copyProperties(cmSecurityVerification, omeSecurityVerification);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cmSecurityVerification;
	}
	
	public static com.A.xml.cm.v4.PhoneNumberType copyPhoneNumberType(PhoneNumberType omePhonenumber) {
		if(omePhonenumber == null) {
			return null;
		}
		com.A.xml.cm.v4.PhoneNumberType cmPhoneNumber = new com.A.xml.cm.v4.PhoneNumberType();
		try {
			BeanUtils.copyProperties(cmPhoneNumber, omePhonenumber);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cmPhoneNumber;
	}
	
	public static com.A.xml.cm.v4.DriverLicenseType copyDriverLicense(DriverLicenseType driverLicense) {
		if(driverLicense == null) {
			return null;
		}
		com.A.xml.cm.v4.DriverLicenseType cmDriverLicense = new com.A.xml.cm.v4.DriverLicenseType();
		try {
			BeanUtils.copyProperties(cmDriverLicense, driverLicense);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cmDriverLicense;
	}
	
	private static com.A.xml.cm.v4.PaymentsType copyPaymentsType(PaymentsType omPayments) {
		if(omPayments == null) {
			return null;
		}
		com.A.xml.cm.v4.PaymentsType cmPayments = new com.A.xml.cm.v4.PaymentsType();
		List<com.A.xml.cm.v4.PaymentEventType> paymentList = cmPayments
				.getPaymentEvent();
		for (com.A.xml.v4.PaymentEventType event : omPayments
				.getPaymentEvent()) {
			paymentList.add(copyPaymentEvent(event));
		}
		return cmPayments;
	}

	private static com.A.xml.cm.v4.PaymentEventType copyPaymentEvent(
			PaymentEventType omePaymentEvent) {
		com.A.xml.cm.v4.PaymentEventType cmPaymentEvent = new com.A.xml.cm.v4.PaymentEventType();
		cmPaymentEvent.setAmount(omePaymentEvent.getAmount());
		cmPaymentEvent.setBillingInfoId(omePaymentEvent.getBillingInfoId());
		cmPaymentEvent.setConfirmNumber(omePaymentEvent.getConfirmNumber());
		cmPaymentEvent.setCustAgreedCCDisclosure(omePaymentEvent
				.getCustAgreedCCDisclosure());
		cmPaymentEvent.setCVV(omePaymentEvent.getCVV());
		if(omePaymentEvent.getEvent() != null) {
			cmPaymentEvent.setEvent(com.A.xml.cm.v4.PaymentEventTypeType
				.valueOf(omePaymentEvent.getEvent().name()));
		}
		cmPaymentEvent.setExternalId(omePaymentEvent.getExternalId());
		cmPaymentEvent.setLineItemId(omePaymentEvent.getLineItemId());
		cmPaymentEvent.setOrderId(omePaymentEvent.getOrderId());
		cmPaymentEvent.setTransactionDate(omePaymentEvent.getTransactionDate());
		return cmPaymentEvent;
	}

	private static com.A.xml.cm.v4.AddressListType copyAddressList(AddressListType omeAddressList) {
		if(omeAddressList == null) {
			return null;
		}
		com.A.xml.cm.v4.AddressListType addressList = new com.A.xml.cm.v4.AddressListType();
		List<com.A.xml.cm.v4.CustAddress> resultList = addressList.getCustomerAddress();
		for (CustAddress address : omeAddressList.getCustomerAddress()) {
			resultList.add(copyAddress(address));
		}
		return addressList;
	}

	private static com.A.xml.cm.v4.CustAddress copyAddress(
			CustAddress omeAddress) {
		com.A.xml.cm.v4.CustAddress cmAddress = new com.A.xml.cm.v4.CustAddress();
		cmAddress.setAddress(createAddress(omeAddress.getAddress()));
		AddressRoles addressRoles = new AddressRoles();
		for (com.A.xml.v4.RoleType cmRole : omeAddress
				.getAddressRoles().getRole()) {
			addressRoles.getRole().add(com.A.xml.cm.v4.RoleType.fromValue(cmRole.value()));
		}
		cmAddress.setAddressRoles(addressRoles);
		cmAddress.setStatus(omeAddress.getStatus());
		cmAddress.setAddressUniqueId(omeAddress.getAddressUniqueId());
		return cmAddress;
	}

	private static com.A.xml.cm.v4.AddressType createAddress(
			AddressType omeAddress) {
		com.A.xml.cm.v4.AddressType address = new com.A.xml.cm.v4.AddressType();
		address.setAddressBlock(omeAddress.getAddressBlock());
		address.setCity(omeAddress.getCity());
		address.setExternalId(omeAddress.getExternalId());
		address.setLine2(omeAddress.getLine2());
		address.setPostalCode(omeAddress.getPostalCode());
		address.setPostfixDirectional(omeAddress.getPostfixDirectional());
		address.setPrefixDirectional(omeAddress.getPrefixDirectional());
		address.setStateOrProvince(omeAddress.getStateOrProvince());
		address.setStreetName(omeAddress.getStreetName());
		address.setStreetNumber(omeAddress.getStreetNumber());
		address.setStreetType(omeAddress.getStreetType());
		if(omeAddress.getDwellingType() != null) {
			address.setDwellingType(DwellingType.fromValue(omeAddress.getDwellingType().value()));
		}
		address.setElectricityStartAt(omeAddress.getElectricityStartAt());
		address.setGasStartAt(omeAddress.getGasStartAt());
		address.setInEffect(omeAddress.getInEffect());
		address.setHasHadServicePreviously(omeAddress.isHasHadServicePreviously());
		address.setExpiration(omeAddress.getExpiration());
		address.setHowLongAtAddress(omeAddress.getHowLongAtAddress());
		address.setChangeType(omeAddress.getChangeType());
		if(omeAddress.getProviderExternalId() != null) {
		   address.setProviderExternalId(omeAddress.getProviderExternalId());
		}
		if(omeAddress.getAddressOwnership() != null) {
			address.setAddressOwnership(OwnershipType.fromValue(omeAddress.getAddressOwnership().value()));
		}
		return address;
	}

	private static com.A.xml.cm.v4.CustBillingInfoType copyBillingInfo(
			CustBillingInfoType cmBillingInfo) {
		com.A.xml.cm.v4.CustBillingInfoType orderBillingInfo = new com.A.xml.cm.v4.CustBillingInfoType();
		orderBillingInfo.setAddressRef(cmBillingInfo.getAddressRef());
		orderBillingInfo.setCardHolderName(cmBillingInfo.getCardHolderName());
		orderBillingInfo.setExpirationYearMonth(cmBillingInfo
				.getExpirationYearMonth());
		orderBillingInfo.setExternalId(cmBillingInfo.getExternalId());
		orderBillingInfo.setCreditCardNumber(cmBillingInfo
				.getCreditCardNumber());
		orderBillingInfo.setVerificationCode(cmBillingInfo
				.getVerificationCode());
		orderBillingInfo.setBillingUniqueId(cmBillingInfo.getBillingUniqueId());
		if(cmBillingInfo.getCreditCardType() != null) {
			orderBillingInfo.setCreditCardType(com.A.xml.cm.v4.CreditCardTypeType.fromValue(
					cmBillingInfo.getCreditCardType().value()));
		}
		if(cmBillingInfo.getBillingAccountType() != null) {
			orderBillingInfo.setBillingAccountType(com.A.xml.cm.v4.BillingAccountTypeType.fromValue(
					cmBillingInfo.getBillingAccountType().value()));
		}
		orderBillingInfo.setBillingMethod(cmBillingInfo.getBillingMethod());
		orderBillingInfo.setStatus(cmBillingInfo.getStatus());
		orderBillingInfo.setCheckingAccountNumber(cmBillingInfo.getCheckingAccountNumber());
		orderBillingInfo.setRoutingNumber(cmBillingInfo.getRoutingNumber());
		orderBillingInfo.setIsChecking(cmBillingInfo.getIsChecking());
		return orderBillingInfo;
	}

	private static com.A.xml.cm.v4.BillingInfoList copyBillingInfoList(
			BillingInfoList omeBillingInfoList) {
		if(omeBillingInfoList == null) {
			return null;
		}
		com.A.xml.cm.v4.BillingInfoList cmBillingInfoList = new com.A.xml.cm.v4.BillingInfoList();
		for (CustBillingInfoType billInfo : omeBillingInfoList
				.getBillingInfo()) {
			cmBillingInfoList.getBillingInfo().add(copyBillingInfo(billInfo));
		}
		return cmBillingInfoList;
	}

}
