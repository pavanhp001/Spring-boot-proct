package com.A.V.crypt;

import com.A.V.utility.StringUtils;

import com.A.V.beans.entity.AccountHolder;
import com.A.V.crypt.service.EncryptService;

import java.util.logging.Logger;


public enum DefaultEncryptListener {
	
	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(DefaultEncryptListener.class.getName());
	 
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
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException(
						"Exception while encrypting data.");
			}
		}
		return hexString;
	}
	
	public void encrypt(AccountHolder accountHolder) {
		try {
			accountHolder.setSsn(encryptToHex(accountHolder.getSsn()));
			accountHolder.setDob(encryptToHex(accountHolder.getDob()));
			accountHolder.setSecurityPin(encryptToHex(accountHolder
					.getSecurityPin()));
			accountHolder.setSecurityAnswer(encryptToHex(accountHolder
					.getSecurityAnswer()));
			accountHolder.setDriverLicenseNumber(encryptToHex(accountHolder
					.getDriverLicenseNumber()));
		} catch (Exception e) {
			logger.warning(String
					.format("%s error encrypting.  unable to encrypt account holder %s:",
							e.getMessage(),
							accountHolder.getAccountHolderUniqueId()));
		}
	}

}
