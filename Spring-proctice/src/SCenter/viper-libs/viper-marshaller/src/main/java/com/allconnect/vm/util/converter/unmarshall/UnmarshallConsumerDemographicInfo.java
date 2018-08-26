package com.A.vm.util.converter.unmarshall;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.enums.GenderEnum;
import com.A.util.XmlUtil;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerSurvey;
import com.A.vm.util.converter.DynamicBuilder;
import com.A.vm.util.converter.mapper.ConsumerDemographicInfoMapper;
import com.A.xml.v4.CsatSurveyType;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.SecurityVerificationType;

/**
 * @author ebthomas
 *
 */
public final class UnmarshallConsumerDemographicInfo {

	private static final String PRIMARY_LANGUAGE = "primaryLanguage";

	private static final Logger logger = Logger
			.getLogger(UnmarshallConsumerDemographicInfo.class);

	private static final String PIN = "pin";
	private static final String SECURITY_QUESTION = "securityQuestion";
	private static final String SECURITY_ANSWER = "securityAnswer";
	private static final String LANDLORD_NAME = "landlordName";
	private static final String LANDLORD_PHONE = "landlordPhoneNumber";
	private static final String STATE_ID_NO = "IdNumber";
	private static final String ID_STATE = "State";
	private static final String LICENSE_NO = "LicenseNumber";
	private static final String LICENSE_STATE = "State";
	private static final String LIC_EXP_DATE = "LicenseExpirationDate";

	/**
	 * @param src
	 *            Source
	 * @param dest
	 *            Destination
	 * @param isUpdateRequest
	 *            TODO
	 */
	private static void customCopy(
			final DynamicBuilder<CustomerType, Consumer> builder,
			final CustomerType src, final Consumer dest,
			final UnmarshallValidationEnum level, boolean isUpdateRequest) {
		if (src == null) {
			return;
		}
		logger.debug("Copying custom demographic information....");

		//Offers presented
		if(src.getOffersPresented() != null) {
		    dest.setOffersPresented(src.getOffersPresented());
		}

		// Gender
		if (src.getGender() != null) {
			dest.setGender(GenderEnum.get(src.getGender().toString()));
		} else {
			dest.setGender(null);
		}

		if (src.getProviderCustomerType() != null) {
			String providerCustomerType = src.getProviderCustomerType()
					.toString();

			dest.setCustomerType(providerCustomerType);
		}

		String dateDob = UnmarshallUtil.INSTANCE.getValue(src.newCursor(),
				"dob");
		if (dateDob != null) {
			Calendar calDob = UnmarshallUtil.INSTANCE.getDate(src, dateDob);
			dest.setDob(calDob);
		}

		// Security Verification Info
		if (src.getSecurityVerificationInfo() != null) {
			SecurityVerificationType secType = src
					.getSecurityVerificationInfo();
			if (!XmlUtil.isElementNil(secType.newCursor(), PIN)) {
				dest.setPin(secType.getPin());
			}
			if (!XmlUtil.isElementNil(secType.newCursor(), SECURITY_QUESTION)) {
				dest.setSecurityQuestion(secType.getSecurityQuestion());
			}
			if (!XmlUtil.isElementNil(secType.newCursor(), SECURITY_ANSWER)) {
				dest.setSecurityAnswer(secType.getSecurityAnswer());
			}
		}

		if (src.getBillingDeliveryPreference() != null) {
			dest.setBillingDeliveryPreference(src
					.getBillingDeliveryPreference().toString());
		}

		if(src.getEMailOptIn()){
			dest.setEMailOptIn(Boolean.TRUE);
		}else{
			dest.setEMailOptIn(Boolean.FALSE);
		}

		if(src.getMarketingOptIn()){
			dest.setMarketingOptIn(Boolean.TRUE);
		}else{
			dest.setMarketingOptIn(Boolean.FALSE);
		}

		if(src.getNonBuyerWebOptIn()){
			dest.setNonBuyerWebOptIn(Boolean.TRUE);
		}else{
			dest.setNonBuyerWebOptIn(Boolean.FALSE);
		}

		if (src.getEMailProductUpdatesOptIn()) {
			dest.seteMailProductUpdatesOptIn(Boolean.TRUE);
		} else {
			dest.seteMailProductUpdatesOptIn(Boolean.FALSE);
		}

		if (src.getDirectMailOptIn()) {
			dest.setDirectMailOptIn(Boolean.TRUE);
		} else {
			dest.setDirectMailOptIn(Boolean.FALSE);
		}

		if (src.getPhoneContactOptIn()) {
			dest.setPhoneContactOptIn(Boolean.TRUE);
		} else {
			dest.setPhoneContactOptIn(Boolean.FALSE);
		}

		Set<CustomerSurvey> cstSet = null;
		if(src.getCsatSurveys() != null && src.getCsatSurveys().getCsatSurveyList() != null) {
		    cstSet = new HashSet<CustomerSurvey>();
		    List<CsatSurveyType> srvTypeList = src.getCsatSurveys().getCsatSurveyList();
		    for(CsatSurveyType cst : srvTypeList) {
			CustomerSurvey csatEntity = new CustomerSurvey();
			csatEntity.setSurveyId(cst.getId());
			//csatEntity.setName(cst.getName());
			cstSet.add(csatEntity);
		    }
		}

		if(cstSet != null) {
		    dest.setCustomerCsatSurveys(cstSet);
		}
		// Primary language
		if(!XmlUtil.isElementNull(src.newCursor(), PRIMARY_LANGUAGE)){

			if (src.getPrimaryLanguage() >= 0 && src.getPrimaryLanguage() < 2) {
				dest.setPrimaryLanguage(src.getPrimaryLanguage());
			} else {
				throw new IllegalArgumentException("Primary language value must be either 0(English) or 1(Spanish)") ;
			}
		}else{
			logger.info("Ignoring primary language update");
		}


		// Region
		if (src.getRegion() != null) {
			dest.setRegion(src.getRegion().toString());
		}
		// Landlord info
		if (src.getLandlordInfo() != null) {
			if (!XmlUtil.isElementNil(src.getLandlordInfo().newCursor(),
					LANDLORD_NAME)) {
				dest.setLandlordName(src.getLandlordInfo().getLandlordName());
			}
			if (!XmlUtil.isElementNil(src.getLandlordInfo().newCursor(),
					LANDLORD_PHONE)) {
				dest.setLandlordPhoneNumber(src.getLandlordInfo()
						.getLandlordPhoneNumber());
			}
		}

		// State ID info

		if (src.getStateId() != null) {
			if (!XmlUtil
					.isElementNil(src.getStateId().newCursor(), STATE_ID_NO)) {
				dest.setStateIdNo(src.getStateId().getIdNumber());
			}
			if (!XmlUtil.isElementNil(src.getStateId().newCursor(), ID_STATE)) {
				dest.setIdState(src.getStateId().getState());
			}
		}

		// License Info

		if (src.getDriverLicense() != null) {
			if (!XmlUtil.isElementNil(src.getDriverLicense().newCursor(),
					LICENSE_NO)) {
				dest.setDriverLicenseNo(src.getDriverLicense()
						.getLicenseNumber());
			}
			if (!XmlUtil.isElementNil(src.getDriverLicense().newCursor(),
					LIC_EXP_DATE)) {
				Calendar cal = src.getDriverLicense()
						.getLicenseExpirationDate();
				dest.setLicenseExpirationDate(cal);
			}
			if (!XmlUtil.isElementNil(src.getDriverLicense().newCursor(),
					LICENSE_STATE)) {
				dest.setLicenseState(src.getDriverLicense().getState());
			}
		}

		if (!XmlUtil.isElementNil(src.newCursor(), "bestPhoneContact")) {
			if (src.getBestPhoneContact() != null) {
				dest.setBestPhoneContact(src.getBestPhoneContact());
			}
		}

		if (!XmlUtil.isElementNil(src.newCursor(), "partnerName")) {
			if (src.getPartnerName() != null) {
				dest.setPartnerName(src.getPartnerName());
			}
		}

		if (src.getPartnerSSN() != null) {
			dest.setPartnerSSN(src.getPartnerSSN());
		}

		//TODO: What happens if we need to clear these fields??? we should use isElementNil
		// best Contact and time info
		if (src.getBestTimeToCall1() != null) {
			Date date = src.getBestTimeToCall1().getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			dest.setBestTimeToCall1(cal);
		}

		if (src.getBestTimeToCall2() != null) {
			Date date = src.getBestTimeToCall2().getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			dest.setBestTimeToCall2(cal);
		}

		if (src.getBestTimeToCallPhone() != null) {
			dest.setBestTimeToCallPhone(src.getBestTimeToCallPhone());
		}
		if (src.getBestEmailContact() != null) {
			dest.setBestEmailContact(src.getBestEmailContact());
		}

		// Added for the requirements per ticket #108245 & 111915
		// Commenting this functionality for later release
		//dest.setWorkPhoneExtnValue(src.getWorkPhoneNumberExtn());
	}

	/*
	 * public static ConsumerBean copy( final CustomerType consumerTypeSource,
	 * final UnmarshallValidationEnum level, boolean isUpdateRequest ) { final
	 * ConsumerBean consumerBean = new ConsumerBean();
	 *
	 * copy( consumerTypeSource, consumerBean, isUpdateRequest );
	 *
	 * return consumerBean; }
	 */

	public static void copy(final CustomerType consumerTypeSource,
			final Consumer consumerBean, boolean isUpdateRequest) {
		logger.debug("Copying demographic information....");
		if (consumerBean == null) {
			return;
		}

		DynamicBuilder<CustomerType, Consumer> builder = new DynamicBuilder<CustomerType, Consumer>(
				null);

		try {
			builder.copyInstanceAttributes(consumerTypeSource, consumerBean,
					ConsumerDemographicInfoMapper.contact, isUpdateRequest);
			customCopy(builder, consumerTypeSource, consumerBean, null,
					isUpdateRequest);

			changeCase(consumerBean);

		} catch (Exception e) {
			logger.error(
					"Exception thrown while copying demographic info from XML to consumer bean...",
					e);
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private static void changeCase(Consumer consumerBean) {
		if(consumerBean.getFirstName() != null){
			consumerBean.setFirstName(consumerBean.getFirstName().toUpperCase());
		}

		if(consumerBean.getLastName() != null){
			consumerBean.setLastName(consumerBean.getLastName().toUpperCase());
		}
	}

}
