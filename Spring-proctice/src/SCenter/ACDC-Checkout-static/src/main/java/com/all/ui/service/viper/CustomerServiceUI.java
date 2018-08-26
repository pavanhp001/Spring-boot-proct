package com.AL.ui.service.V;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;

import com.AL.ui.service.V.impl.CustomerCacheService;
import com.AL.ui.vo.OrderQualVO;
import com.AL.util.DateUtil;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.SecurityVerificationType;

public enum CustomerServiceUI {
	INSTANCE;
	
	public static QName qname = new QName("dob");
	
	public CustomerType createCustomer(OrderQualVO orderQualVO, CustomerType customerType) {
		if(customerType == null) {
			customerType = new CustomerType();
		}
		if(!StringUtils.isEmpty(orderQualVO.getSsn())) {
			customerType.setSsn(orderQualVO.getSsn());
		}
		
		/*if(orderQualVO.getDob() != null) {
			customerType.setDob(orderQualVO.getDob());
		}*/
		
		if(orderQualVO.getWorkPhoneNumber() != null) {
			customerType.setWorkPhoneNumber(orderQualVO.getWorkPhoneNumber());
		}
		
		if(!StringUtils.isEmpty(orderQualVO.getSecurityAnswer())) {
			SecurityVerificationType verificationType = customerType.getSecurityVerificationInfo();
			if(verificationType == null) {
				verificationType = new SecurityVerificationType();
			}
			verificationType.setSecurityAnswer(orderQualVO.getSecurityAnswer());
			customerType.setSecurityVerificationInfo(verificationType);
		}
			
		if(!StringUtils.isEmpty(orderQualVO.getSecurityQuestion())) {
			SecurityVerificationType verificationType = customerType.getSecurityVerificationInfo();
			if(verificationType == null) {
				verificationType = new SecurityVerificationType();
			}
			verificationType.setSecurityQuestion(orderQualVO.getSecurityQuestion());
			customerType.setSecurityVerificationInfo(verificationType);
		}
		
		if(!StringUtils.isEmpty(orderQualVO.getSecurityPin())) {
			SecurityVerificationType verificationType = customerType.getSecurityVerificationInfo();
			if(verificationType == null) {
				verificationType = new SecurityVerificationType();
			}
			verificationType.setPin(orderQualVO.getSecurityPin());
			customerType.setSecurityVerificationInfo(verificationType);
		}
		return customerType;
	}
	
	public CustomerType updateCustomer(CustomerType customer, String externalId) {
		CustomerType updatedCustomer = CustomerService.INSTANCE.submitCustomerType(
					"default", externalId, "updateCustomer", customer);
		if(updatedCustomer != null) {
			CustomerCacheService.INSTANCE.store(updatedCustomer, externalId);
		}
	    return updatedCustomer;
	}
	
	public CustomerType getCustomer(String externalId) {
		CustomerType customer = CustomerService.INSTANCE.getCustomerType(externalId);
		return customer;
	}
	
	public CustomerType updateCustomer(CustomerType customer, String externalId,String agentId) {
		CustomerType updatedCustomer = CustomerService.INSTANCE.submitCustomerType(
				agentId, externalId, "updateCustomer", customer);
		if(updatedCustomer != null) {
			CustomerCacheService.INSTANCE.store(updatedCustomer, externalId);
		}
	    return updatedCustomer;
	}
}
