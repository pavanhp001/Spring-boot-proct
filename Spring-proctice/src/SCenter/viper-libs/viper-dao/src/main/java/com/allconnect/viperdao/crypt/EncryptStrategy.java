package com.A.Vdao.crypt;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.crypt.service.EncryptService;

public enum EncryptStrategy {

    INSTANCE;
    private static final Logger logger = Logger.getLogger(EncryptStrategy.class);

    public void encrypt(Consumer consumer) {

	String ssnUnencrypted = consumer.getSsn();

	// At Rest Encryption fields
	logger.info("Encrypting ssn");
	consumer.setSsn(encryptToHex(consumer.getSsn()));
	logger.info("Encrypting partner ssn");
	consumer.setPartnerSSN(encryptToHex(consumer.getPartnerSSN()));
	logger.info("Encrypting Driver lic");
	consumer.setDriverLicenseNo(encryptToHex(consumer.getDriverLicenseNo()));
	logger.info("Encrypting pin");
	consumer.setPin(encryptToHex(consumer.getPin()));
	logger.info("Encrypting security answer");
	consumer.setSecurityAnswer(encryptToHex(consumer.getSecurityAnswer()));

	// Copy DOB Calendar to String for Encryption
	logger.info("Encrypting birth info");
	setBirthInfo(consumer);
	consumer.setBirthInfo(encryptToHex(consumer.getBirthInfo()));

	// Mask consumer date of birth
	consumer.setDob(Calendar.getInstance());

	// Ensure CVV is never persisted.
	for (CustomerPaymentEvent pe : consumer.getPaymentEvents()) {
	    pe.setCvv("");
	}

	// Synchronize ssn last four of SSN
	if ((ssnUnencrypted != null) && (ssnUnencrypted.length() > 4)) {

	    consumer.setSsnLastFour(ssnUnencrypted.substring(ssnUnencrypted.length() - 4));
	}

	// Encrypt Billing information

	if (consumer.getBillingInfoList() != null && !consumer.getBillingInfoList().isEmpty()) {
	    logger.info("Encrypting Billing info");
	    for (BillingInformation bi : consumer.getBillingInfoList()) {
		encrypt(bi);
	    }
	}

    }

    public void setBirthInfo(final Consumer consumer) {

	if ((consumer == null) || (consumer.getDob() == null)) {
	    return;
	}

	Calendar dob = consumer.getDob();

	int year = dob.get(Calendar.YEAR);
	int month = dob.get(Calendar.MONTH);
	int date = dob.get(Calendar.DATE);

	String birthInfo = String.format("%s-%s-%s", year, month, date);
	consumer.setBirthInfo(birthInfo);

    }

    public void encrypt(BillingInformation bi) {
	bi.setCardHolderName(encryptToHex(bi.getCardHolderName()));
	bi.setCreditCardNumber(encryptToHex(bi.getCreditCardNumber()));
	bi.setRoutingNumber(encryptToHex(bi.getRoutingNumber()));
	bi.setCheckingAccountNumber(encryptToHex(bi.getCheckingAccountNumber()));

	Date date = bi.getExpirationDate();

	if (date != null) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);

	    String expireMonth = bi.getExpireMonth();
	    String expireYear = bi.getExpireYear();
	    bi.setExpireMonth(encryptToHex(expireMonth));
	    bi.setExpireYear(encryptToHex(expireYear));
	}
    }

    private String encryptToHex(Object raw) {

	if (raw == null) {
	    return null;
	}

	String hexString = "";

	if (raw instanceof String) {

	    hexString = (String) raw;

	    if (hexString.length() == 0) {
		return "";
	    }

	    try {
		String crypt = EncryptService.INSTANCE.encryptHex(hexString);

		return crypt;

	    }
	    catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new IllegalArgumentException("Exception while encrypting data.");
	    }

	}

	return hexString;
    }

}
