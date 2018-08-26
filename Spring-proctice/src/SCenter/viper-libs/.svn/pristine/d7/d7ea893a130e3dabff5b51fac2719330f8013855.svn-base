package com.A.vm.util.converter.mapper;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.util.DateUtil;
import com.A.V.beans.entity.AccountHolder;
import com.A.xml.common.ContactPhoneType;
import com.A.xml.v4.AccountHolderType;
import com.A.xml.v4.DriverLicenseType;
import com.A.xml.v4.SecurityVerificationType;

public class AccountHolderMapper {
	
	private final static Logger logger = Logger.getLogger(AccountHolderMapper.class);
	
	public static final int SSN_LENGTH = 9;
	public static final int SSN_LAST_FOUR_INDEX = 5;
	public static final String EMPTY = "";
	public static final String NON_DIGIT_REGEX = "\\D";
	
	public static void copyAccountHolder(AccountHolder accHolder, 
    		AccountHolderType accountHolderType) {
    	logger.debug("Started copying account holder to account holder type");
    	accountHolderType.setBestContact(accHolder.getBestContact());
    	accountHolderType.setBestEmail(accHolder.getBestEmail());
    	accountHolderType.setFirstName(accHolder.getFirstName());
    	accountHolderType.setLastName(accHolder.getLastName());
    	if(accHolder.getMiddleName() != null) {
    		accountHolderType.setMiddleName(accHolder.getMiddleName());
    	}
    	if(accHolder.getNameSuffix() != null) {
    		accountHolderType.setNameSuffix(accHolder.getNameSuffix());
    	}
    	accountHolderType.setExternalId(accHolder.getId());
    	accountHolderType.setSsn(accHolder.getSsn());
    	accountHolderType.setDtCustomer(accHolder.isDtCustomer());
    	accountHolderType.setSecondContact(accHolder.getSecondContact());
    	accountHolderType.setAccountHolderUniqueId(accHolder.getAccountHolderUniqueId());
    	if(accHolder.getBestContactPhoneType() != null) {
    		accountHolderType.setBestContactPhoneType(ContactPhoneType.Enum.forString(accHolder.getBestContactPhoneType()));
    	}
    	if(accHolder.getSecondContactPhoneType() != null) {
    		accountHolderType.setSecondContactPhoneType(ContactPhoneType.Enum.forString(accHolder.getSecondContactPhoneType()));
    	}
    	if(StringUtils.isNotEmpty(accHolder.getDob())) {
    		accountHolderType.setDob(DateUtil.convertStringToDate(accHolder.getDob()));
    	}
    	if(accHolder.getDriverLicenseNumber() != null) {
    		DriverLicenseType driverLicenseType = accountHolderType.addNewDriversLicense();
    		driverLicenseType.setLicenseExpirationDate(accHolder.getDriverLicenseExpDate());
    		driverLicenseType.setLicenseNumber(accHolder.getDriverLicenseNumber());
    		driverLicenseType.setState(accHolder.getDriverLicenseState());
    	}
    	if(accHolder.getSecurityPin() != null) {
    		SecurityVerificationType securityInfo = accountHolderType.addNewSecurityVerificationInfo();
    		securityInfo.setPin(accHolder.getSecurityPin());
    		securityInfo.setSecurityAnswer(accHolder.getSecurityAnswer());
    		securityInfo.setSecurityQuestion(accHolder.getSecurityQuestion());
    	}
    	logger.debug("Finished copying account holder to account holder type");
    }

    public static  AccountHolder copyAccountHolderType(AccountHolderType accountHolderType, AccountHolder accHolder) {
    	logger.debug("Started copying account holder type to account holder");
    	if(accountHolderType.getExternalId() != 0) {
    		accHolder.setId(accountHolderType.getExternalId());
    	}
    	if(accountHolderType.getFirstName() != null) {
    		accHolder.setFirstName(accountHolderType.getFirstName());
    	}
    	if(accountHolderType.getLastName() != null) {
    		accHolder.setLastName(accountHolderType.getLastName());
    	}
    	if(accountHolderType.getMiddleName() != null) {
    		accHolder.setMiddleName(accountHolderType.getMiddleName());
    	}
    	if(accountHolderType.getNameSuffix() != null) {
    		accHolder.setNameSuffix(accountHolderType.getNameSuffix());
    	}
    	if(accountHolderType.getSsn() != null) {
    		accHolder.setSsn(accountHolderType.getSsn());
    		String ssn = accountHolderType.getSsn();
    		ssn = ssn.replaceAll(NON_DIGIT_REGEX, EMPTY);
    		if(ssn.length() == SSN_LENGTH) {
    			accHolder.setSsnLastFour(ssn.substring(SSN_LAST_FOUR_INDEX));
    		}
    	}
    	if(accountHolderType.getBestEmail() != null) {
    		accHolder.setBestEmail(accountHolderType.getBestEmail());
    	}
    	if(accountHolderType.getBestContact() != null) {
    		accHolder.setBestContact(accountHolderType.getBestContact());
    	}
    	if(accountHolderType.getBestContactPhoneType() != null) {
    		accHolder.setBestContactPhoneType(accountHolderType.getBestContactPhoneType().toString());
    	}
    	
    	if(accountHolderType.getSecondContact() != null) {
    		accHolder.setSecondContact(accountHolderType.getSecondContact());
    	}
    	
    	if(accountHolderType.getSecondContactPhoneType() != null) {
    		accHolder.setSecondContactPhoneType(accountHolderType.getSecondContactPhoneType().toString());
    	}
    	
    	accHolder.setDtCustomer(accountHolderType.getDtCustomer());
    	
    	if(accountHolderType.getAccountHolderUniqueId() != null) {
    		accHolder.setAccountHolderUniqueId(accountHolderType.getAccountHolderUniqueId());
    	}
    	
    	DriverLicenseType drLicenseType = accountHolderType.getDriversLicense();
    	if(drLicenseType != null) {
    		if(drLicenseType.getLicenseNumber() != null) {
    			accHolder.setDriverLicenseNumber(drLicenseType.getLicenseNumber());
    		}
    		if(drLicenseType.getState() != null) {
    			accHolder.setDriverLicenseState(drLicenseType.getState());
    		}
    		if(drLicenseType.getLicenseExpirationDate() != null) {
    			accHolder.setDriverLicenseExpDate(drLicenseType.getLicenseExpirationDate());
    		}
    	}
    	if(accountHolderType.getDob() != null) {
    		accHolder.setDob(DateUtil.convertDateToString(accountHolderType.getDob()));
    	}
    	SecurityVerificationType secVerfyType = accountHolderType.getSecurityVerificationInfo();
    	if(secVerfyType != null) {
    		if(secVerfyType.getSecurityAnswer() != null) {
    			accHolder.setSecurityAnswer(secVerfyType.getSecurityAnswer());
    		}
    		if(secVerfyType.getSecurityQuestion() != null) {
    			accHolder.setSecurityQuestion(secVerfyType.getSecurityQuestion());
    		}
    		if(secVerfyType.getPin() != null) {
    			accHolder.setSecurityPin(secVerfyType.getPin());
    		}
    	}
    	logger.debug("Started copying account holder type to account holder");
    	return accHolder;
    }


}
