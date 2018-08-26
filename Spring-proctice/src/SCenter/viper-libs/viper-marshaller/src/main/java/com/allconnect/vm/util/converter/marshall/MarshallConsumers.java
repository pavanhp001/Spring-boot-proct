package com.A.vm.util.converter.marshall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.beans.entity.CustomerSurvey;
import com.A.xml.v4.AddressType;
import com.A.xml.v4.Attributes;
import com.A.xml.v4.BillingInfoList;
import com.A.xml.v4.CsatSurveyType;
import com.A.xml.v4.CsatSurveysType;
import com.A.xml.v4.CustAddress;
import com.A.xml.v4.CustAddress.AddressRoles;
import com.A.xml.v4.CustBillingInfoType;
import com.A.xml.v4.CustomerFinancialInfoType;
import com.A.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.Response;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.CustomerType.BillingDeliveryPreference;
import com.A.xml.v4.GenderDocument;
import com.A.xml.v4.PaymentEventType;
import com.A.xml.v4.PaymentsType;
import com.A.xml.v4.RoleType;

/**
 * @author ebthomas
 *
 */
@Component
public final class MarshallConsumers {

	private static final Logger logger = Logger
			.getLogger(MarshallConsumers.class);

	private static final String PAPER = "paper";
	private static final String ELECTRONIC = "electronic";

	private Boolean isValid() {

		return Boolean.TRUE;
	}

	/**
	 * @param dest
	 *            destination for copy operation
	 * @param src
	 *            source of copy operation
	 */
	private static void copyConsumerAddress(final CustomerType dest,
			final Consumer src) {
		logger.debug("Copying address information in the CustomerResponse document...");

		try {
			if (src.getConsumerAddressList() != null) {
				List<AddressBean> addressList = src.getConsumerAddressList();
				logger.trace("MarshallConsumers:copyConsumerAddress():addressList:size: " + addressList.size());
				dest.addNewAddressList();
				int i = 0;
				long prevAddrId = 0;
				for (AddressBean address : addressList) {
					if (0 == i++) {
						logger.trace("BEFORE:MarshallConsumers:copyConsumerAddress():0 == i++: " + address.getAddressUniqueId());
						prevAddrId = addAddressToDest(dest, address);
						logger.trace("AFTER: MarshallConsumers:copyConsumerAddress():0 == i++: " + address.getAddressUniqueId() +
								" :prevAddrId: " + prevAddrId);
					} else if (prevAddrId != address.getId()) {
						logger.trace("BEFORE:MarshallConsumers:copyConsumerAddress():prevAddrId != address.getId(): " + address.getAddressUniqueId()
								+ " :prevAddrId: " + prevAddrId + " :address.getId(): " + address.getId());
						prevAddrId = addAddressToDest(dest, address);
						logger.trace("AFTER:MarshallConsumers:copyConsumerAddress():prevAddrId != address.getId(): " + address.getAddressUniqueId()
								+ " :prevAddrId: " + prevAddrId + " :address.getId(): " + address.getId());
					}
				}
			}
		} catch (Exception e) {
			logger.warn("warning unable to marshall address fully");
		}
	}

	private static long addAddressToDest(final CustomerType dest,
			AddressBean src) {
		long prevAddrId;
		List<String> srcRoles = src.getAddressRoles();
		CustAddress destCustAddress = dest.getAddressList()
				.addNewCustomerAddress();
		AddressRoles destAddressRoles = destCustAddress.addNewAddressRoles();

		if (srcRoles != null) {

			for (String role : srcRoles) {

				if ((role != null) && (role.length() > 1)) {
					String firstLetter = role.substring(0, 1).toUpperCase();
					String rest = role.substring(1);
					RoleType.Enum rt = RoleType.Enum.forString(firstLetter
							+ rest);

					destAddressRoles.addRole(rt);
				}
			}
		}

		destCustAddress.setAddressRoles(destAddressRoles);
		destCustAddress.setAddressUniqueId(src.getAddressUniqueId());

		if (src.getStatus() != null) {
			destCustAddress.setStatus(destCustAddress.getStatus().forString(
					src.getStatus().toLowerCase()));
		}

		AddressType adrType = MarshallAddressBean.copyAddress(src);
		destCustAddress.setAddress(adrType);
		prevAddrId = src.getId();
		return prevAddrId;
	}

	/**
	 * @param dest
	 *            destination for copy operation
	 * @param src
	 *            source of copy operation
	 */
	private static void copyConsumerDemographicInfo(final CustomerType dest,
			final Consumer src) {

		if (src.getExternalId() != null) {
			dest.setExternalId(src.getExternalId().longValue());
		}

		if (src.getReferrerId() != null) {
			dest.setReferrerId(src.getReferrerId().longValue());
		} else {
			dest.setReferrerId(0);
		}

		if (src.getPartnerAccountId() != null) {
			dest.setPartnerAccountId(src.getPartnerAccountId());
		} else {
			dest.setPartnerAccountId("");
		}

		if (src.getDtAgentId() != null) {
			dest.setDtAgentId(src.getDtAgentId());
		} else {
			dest.setDtAgentId("");
		}
		
		if (null != src.getContractAccountNumber()) {
            dest.setContractAccountNumber(src.getContractAccountNumber());
		} else {
            dest.setContractAccountNumber("");
		}

		if (src.getDtCreated() != null) {
			dest.setDtCreated(src.getDtCreated());
		}

		if(src.getCustomerCreateDate() != null){
			dest.setCustomerCreateDate(src.getCustomerCreateDate());
		}

		if(src.getPartnerId() != null){
			dest.setPartnerId(src.getPartnerId());
		}

		if(src.getReferrerGeneralName() != null){
			dest.setReferrerGeneralName(src.getReferrerGeneralName());
		}else{
			dest.setReferrerGeneralName("");
		}

		dest.setTitle(src.getTitle());
		dest.setFirstName(src.getFirstName());
		dest.setLastName(src.getLastName());
		dest.setMiddleName(src.getMiddleName());
		dest.setNameSuffix(src.getNameSuffix());
		dest.setAgentId(src.getAgentId());
		dest.setPrimaryLanguage(src.getPrimaryLanguage());

		if (src.getCustomerType() != null) {
			CustomerType.ProviderCustomerType.Enum providerCustomerType = CustomerType.ProviderCustomerType.Enum
					.forString(src.getCustomerType());

			dest.setProviderCustomerType(providerCustomerType);
		}

		if ((src.getGender() != null)
				&& (src.getGender().toString().startsWith("F"))) {
			dest.setGender(GenderDocument.Gender.F);
		} else {
			dest.setGender(GenderDocument.Gender.M);
		}

		if(src.getOffersPresented() !=null) {
		    dest.setOffersPresented(src.getOffersPresented());
		} else {
		    dest.setOffersPresented("");
		}

		if (src.isPhoneContactOptIn()) {
			dest.setPhoneContactOptIn(true);
		} else {
			dest.setPhoneContactOptIn(false);
		}

		if (src.iseMailProductUpdatesOptIn()) {
			dest.setEMailProductUpdatesOptIn(true);
		} else {
			dest.setEMailProductUpdatesOptIn(false);
		}

		if ((src.getMarketingOptIn() != null) && (src.getMarketingOptIn())) {
			dest.setMarketingOptIn(true);
		} else {
			dest.setMarketingOptIn(false);
		}

		if ((src.getDirectMailOptIn() != null) && (src.getDirectMailOptIn())) {
			dest.setDirectMailOptIn(true);
		} else {
			dest.setDirectMailOptIn(false);
		}

		if ((src.getNonBuyerWebOptIn() != null) && (src.getNonBuyerWebOptIn())) {
			dest.setNonBuyerWebOptIn(true);
		} else {
			dest.setNonBuyerWebOptIn(false);
		}

		if (src.getEMailOptIn()) {
			dest.setEMailOptIn(true);
		} else {
			dest.setEMailOptIn(false);
		}

		// Billing delivery option
		if (src.getBillingDeliveryPreference() != null) {
			if (src.getBillingDeliveryPreference().trim().startsWith(PAPER))

				dest.setBillingDeliveryPreference(BillingDeliveryPreference.PAPER);
			else if (src.getBillingDeliveryPreference().trim()
					.startsWith(ELECTRONIC))
				dest.setBillingDeliveryPreference(BillingDeliveryPreference.ELECTRONIC);
		}

		// Driver license option

		dest.addNewDriverLicense();
		if (src.getDriverLicenseNo() != null) {
			dest.getDriverLicense().setLicenseNumber(src.getDriverLicenseNo());
		}
		if (src.getLicenseExpirationDate() != null) {
			dest.getDriverLicense().setLicenseExpirationDate(
					src.getLicenseExpirationDate());
		}
		if (src.getLicenseState() != null) {
			dest.getDriverLicense().setState(src.getLicenseState());
		}

		// Landlord info

		dest.addNewLandlordInfo();
		if (src.getLandlordName() != null) {
			dest.getLandlordInfo().setLandlordName(src.getLandlordName());
		}
		if (src.getLandlordPhoneNumber() != null) {
			dest.getLandlordInfo().setLandlordPhoneNumber(
					src.getLandlordPhoneNumber());
		}

		// State ID info
		dest.addNewStateId();
		if (src.getStateIdNo() != null) {
			dest.getStateId().setIdNumber(src.getStateIdNo());
		}
		if (src.getIdState() != null) {
			dest.getStateId().setState(src.getIdState());
		}

		// Best contact and time info
		if (src.getBestTimeToCall1() != null) {
			// Calendar cal = Calendar.getInstance();
			// cal.setTime();
			dest.setBestTimeToCall1(src.getBestTimeToCall1());
		} else {
			dest.setBestTimeToCall1(null);
		}

		if (src.getBestTimeToCall2() != null) {
			// Calendar cal = Calendar.getInstance();
			// cal.setTime(src.getBestTimeToCall2());
			dest.setBestTimeToCall2(src.getBestTimeToCall2());
		} else {
			dest.setBestTimeToCall2(null);
		}

		dest.setBestTimeToCallPhone(src.getBestTimeToCallPhone());
		dest.setBestEmailContact(src.getBestEmailContact());
		// Employer info
		dest.setDob(src.getDob());
		dest.setSsn(src.getSsn());
		dest.setACustomerNumber(src.getACustomerNumber());
		dest.addNewSecurityVerificationInfo();
		dest.getSecurityVerificationInfo().setPin(src.getPin());
		dest.getSecurityVerificationInfo().setSecurityQuestion(
				src.getSecurityQuestion());
		dest.getSecurityVerificationInfo().setSecurityAnswer(
				src.getSecurityAnswer());

		dest.setBestPhoneContact(src.getBestPhoneContact());
		dest.setPartnerName(src.getPartnerName());
		dest.setPartnerSSN(src.getPartnerSSN());
		dest.setSecondPhone(src.getSecondPhone());

		//Customer Survey
		if(src.getCustomerCsatSurveys() != null) {
		    CsatSurveysType csatSrvType = dest.addNewCsatSurveys();
		    if(src.getCustomerCsatSurveys().size() > 0) {
			Set<CustomerSurvey> surveySet = src.getCustomerCsatSurveys();
			for(CustomerSurvey survey : surveySet) {
			    CsatSurveyType srvType = csatSrvType.addNewCsatSurvey();
			    srvType.setId(survey.getSurveyId());
			    //srvType.setName(survey.getName());
			}
		    }
		}
	}

	/**
	 * @param date
	 *            date to be converted to calendar
	 * @return Calendar value that represents the input Date
	 */
	public Calendar getDateAsCalendar(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal;
	}

	/**
	 * @param consumerBean
	 *            source consumer bean
	 * @param dest
	 *            destination
	 */
	public static void copyFinancialInfo(final Consumer consumerBean,
			final CustomerFinancialInfoType dest) {

		if (consumerBean != null) {

			if (consumerBean.isEmployed()) {
				dest.addNewEmployed();

				dest.getEmployed().setBusinessName(
						consumerBean.getEmployerBusinessName());
				dest.getEmployed().setBusinessPhoneNum(
						consumerBean.getEmployerPhoneNumber());
				dest.getEmployed().setOccupation(consumerBean.getOccupation());
			} else {
				//dest.addNewUnemployed();
			}
			dest.setBankOrMortgageInstitution(consumerBean
					.getBankOrMortgageInstitution());
			dest.setOtherIncomeSource(consumerBean.getOtherIncomeSource());

			if (consumerBean.isRetired()) {
				dest.addNewRetired();
			}

			if (consumerBean.isStudent()) {
				dest.addNewStudent();
			}

		}
	}

	/**
	 * @param consumerBean
	 *            bean
	 * @param dest
	 *            destination
	 */
	private static void copyConsumerFinancialInfo(final Consumer consumerBean,
			final CustomerType dest) {
		if ((consumerBean != null) && (dest != null)) {
			dest.addNewFinancialInfo();
			copyFinancialInfo(consumerBean, dest.getFinancialInfo());
		}
	}

	/**
	 * @param src
	 *            source to be copied
	 * @param dest
	 *            destination of the copy operation
	 */
	private static void copyConsumerContactInfo(final Consumer src,
			final CustomerType dest) {
		dest.setHomePhoneNumber(MarshallContactItem.getPhoneValue(src
				.getHomePhone()));
		dest.setCellPhoneNumber(MarshallContactItem.getPhoneValue(src
				.getCellPhone()));
		dest.setWorkPhoneNumber(MarshallContactItem.getPhoneValue(src
				.getWorkPhone()));
		if(src.getWorkPhoneExtn() != null){
			dest.setWorkPhoneNumberExtn(src.getWorkPhoneExtn());
		}

		dest.setHomeEMail(MarshallContactItem.getEmailValue(src.getHomeEMail()));
		dest.setWorkEMail(MarshallContactItem.getEmailValue(src.getWorkEMail()));
	}

	private static void copyBillingInfo(Consumer src, CustomerType destination, boolean includeAccountHolders) {

		if ((src == null) || (src.getBillingInfoList() == null)
				|| (src.getBillingInfoList().size() == 0)) {
			return;
		}
		List<BillingInformation> billingList = new ArrayList<BillingInformation>(
				src.getBillingInfoList());
		if (billingList != null) {
			BillingInfoList billingInfoTypeList = destination
					.addNewBillingInfoList();
			for (BillingInformation billingBean : billingList) {
				CustBillingInfoType billingInfoType = billingInfoTypeList
						.addNewBillingInfo();
				MarshallBillingInformation.Builder.build(src, billingBean,
						billingInfoType, includeAccountHolders);
			}
		}

	}

	private static void copyAccount(Consumer src, CustomerType destination) {

		MarshallAccount.Builder.build(src, destination);
	}

	/**
	 * @param destination
	 *            source to be copied
	 * @param src
	 *            destination of the copy operation
	 */
	private static void copyConsumer(final CustomerType destination,
			final Consumer src, boolean includeAccountHolders) {
		copyConsumerDemographicInfo(destination, src);
		copyConsumerAddress(destination, src);
		copyConsumerFinancialInfo(src, destination);
		copyConsumerContactInfo(src, destination);
		copyAccount(src, destination);
		copyBillingInfo(src, destination, includeAccountHolders);
		copyPayments(src, destination);
		copyCustomerAttributes(src, destination);
	}
	
	

	private static void copyCustomerAttributes(Consumer src,
			CustomerType destination) {
		if (src.getCustomerAttributes() != null) {
			Attributes attributes = destination.addNewAttributes();
			MarshallCustomerAttribute.Builder.buildCustAttribute(src,
					attributes);
		}
	}

	private static void copyPayments(Consumer src, CustomerType destination) {
		Set<CustomerPaymentEvent> paymentEventList = src.getPaymentEvents();
		if (paymentEventList != null) {
			PaymentsType paymentsTypeList = destination.addNewPayments();

			try {
				for (CustomerPaymentEvent customerPaymentEvent : paymentEventList) {
					PaymentEventType paymentEventType = paymentsTypeList
							.addNewPaymentEvent();

					MarshallPaymentEvent.Builder.build(customerPaymentEvent,
							paymentEventType);
				}
			} catch (Exception e) {
				logger.error("unable to unmarshall payment events");
			}
		}

	}

	/**
	 * @param consumerBean
	 *            source Bean
	 * @return destination Consumer XMLBean Object
	 */
	public static CustomerType buildConsumer(final Consumer consumerBean) {
		return buildConsumer(consumerBean, false);
	}

	public static CustomerType buildConsumer(final Consumer consumerBean, boolean includeAccountHolders) {
		CustomerType customerType = CustomerType.Factory.newInstance();
		copyConsumer(customerType, consumerBean, false);
		return customerType;
	}
	
	public void buildCustomerContext(Response response, Consumer consumer) {
		MarshallCustomerContext.buildCustomerContext(response, consumer);
	}

	public CustomerType build(final Consumer consumerBean,
			final CustomerType customerType) {
		return build(consumerBean, customerType, false);
	}
	
	public CustomerType build(final Consumer consumerBean,
			final CustomerType customerType, boolean includeAccountHolders) {
		if (isValid()) {
			return doBuildConsumer(consumerBean, customerType, includeAccountHolders);
		}
		throw new IllegalArgumentException("invalid document.  unable to build");
	}

	private CustomerType doBuildConsumer(Consumer consumerBean,
			CustomerType customerType, boolean includeAccountHolders) {
		if (consumerBean != null) {
			copyConsumer(customerType, consumerBean, includeAccountHolders);
		}
		return customerType;
	}
}
