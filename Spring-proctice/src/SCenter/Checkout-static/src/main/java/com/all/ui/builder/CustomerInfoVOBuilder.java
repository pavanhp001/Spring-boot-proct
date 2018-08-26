/**
 * 
 */
package com.AL.ui.builder;

import java.util.Date;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;
import com.AL.ui.service.V.CustomerServiceUI;
import com.AL.ui.vo.CustomerInfoVO;
import com.AL.util.DateUtil;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.DriverLicenseType;
import com.AL.xml.cm.v4.EMailAddressType;
import com.AL.xml.cm.v4.PhoneNumberType;
import com.AL.xml.v4.Customer;

public class CustomerInfoVOBuilder {

	private static final Logger logger = Logger.getLogger(CustomerInfoVOBuilder.class);

	/**
	 * Creates a new CustomerInfoVo Object
	 * @param parameterMap
	 * @param customerInfoVO
	 * @return
	 */
	public static CustomerInfoVO buildCustomerInfoVO(Map<String, String> parameterMap,CustomerInfoVO customerInfoVO){
		if(customerInfoVO == null) {
			customerInfoVO = new CustomerInfoVO();
		}
		return customerInfoVO;
	}

	/**
	 * Builds a new customer object from cutomerInfoVo Object
	 * @param customerInfoVO
	 * @param customerExternalID
	 * @param orderCustomer
	 * @return
	 */
	public static CustomerType buildCustomerType(CustomerInfoVO customerInfoVO, String customerExternalID, Customer orderCustomer) {

		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();

		CustomerType newCust = oFactory.createCustomerType();
		newCust.setExternalId(Long.parseLong(customerExternalID));

		newCust.setSsn(customerInfoVO.getSsn());

		if(customerInfoVO.getEmailAddress() != null){
			EMailAddressType email = oFactory.createEMailAddressType();
			email.setValue(customerInfoVO.getEmailAddress());
			logger.info("Email Address ::::::::::: "+customerInfoVO.getEmailAddress());
			newCust.setHomeEMail(email);
		}

		logger.info("Best Contact Number :::::::::::: "+customerInfoVO.getBtn());
		logger.info("CUSTOMER ORDER BEST CONTACT NUMBER :::::::: "+orderCustomer.getBestPhoneContact());

		if(customerInfoVO.getBtn() != null){
			newCust.setBestPhoneContact(customerInfoVO.getBtn());
		}
		else if(orderCustomer.getBestPhoneContact() != null){
			newCust.setBestPhoneContact(orderCustomer.getBestPhoneContact());
		}

		DriverLicenseType driverLisence = oFactory.createDriverLicenseType();

		if(customerInfoVO.getDriverLicense() != null && customerInfoVO.getState() != null){
			//logger.info("DRIVER LICENSE NUMBER :::::::::::: "+customerInfoVO.getDriverLicense());
			driverLisence.setLicenseNumber(customerInfoVO.getDriverLicense());

			logger.info("DRIVER LICENSE STATE ::::::::::::::: "+customerInfoVO.getState());
			driverLisence.setState(customerInfoVO.getState());
			newCust.setDriverLicense(driverLisence);
		}

		if(customerInfoVO.getDob() != null){
			String dob = customerInfoVO.getDob();
			//			if(dob.indexOf(""))
			//logger.info("DATE OF BIRTH :::::::::::::::::: "+dob);
			Date dobDate = DateUtil.fromStringToDate(customerInfoVO.getDob());

			if (dobDate != null) {
				XMLGregorianCalendar xmlGregorianCalendar = DateUtil.asXMLGregorianCalendar(dobDate);			
				newCust.setDob(xmlGregorianCalendar);
			} else {
				newCust.setDob(null);
			}
		}

		PhoneNumberType phoneType = null;
		if(customerInfoVO.getHomePhoneNumber() != null){
			phoneType = oFactory.createPhoneNumberType();

			logger.info("HOME PHONE NUMBER TYPE :::::::::::: "+customerInfoVO.getHomePhoneNumber());
			phoneType.setValue(customerInfoVO.getHomePhoneNumber());
			newCust.setHomePhoneNumber(phoneType);
		}

		if(customerInfoVO.getCellPhoneNumber() != null){
			phoneType = oFactory.createPhoneNumberType();
			logger.info("CELL PHONE TYPE :::::::::::: "+customerInfoVO.getCellPhoneNumber());
			phoneType.setValue(customerInfoVO.getCellPhoneNumber());
			newCust.setCellPhoneNumber(phoneType);
		}
		
		/*
		 * added code to save work phone number on the customer
		 */
		if(customerInfoVO.getWorkPhoneNumber() != null){
			phoneType = oFactory.createPhoneNumberType();
			logger.info("WORK PHONE TYPE :::::::::::: "+customerInfoVO.getWorkPhoneNumber());
			phoneType.setValue(customerInfoVO.getWorkPhoneNumber());
			newCust.setWorkPhoneNumber(phoneType);
		}
		return newCust;
	}

}
