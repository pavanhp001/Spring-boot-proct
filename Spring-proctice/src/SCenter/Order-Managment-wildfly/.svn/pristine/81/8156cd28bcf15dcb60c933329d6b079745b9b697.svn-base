package com.AL;

 

import com.AL.V.beans.entity.Consumer;
import com.AL.V.crypt.service.EncryptService;
import com.AL.V.crypt.service.DecryptService;

public enum EncryptStrategy {

	INSTANCE;

	public void encrypt(Consumer consumer) {
		
		
		try {
		if (consumer.getSsn() != null) {
			consumer.setSsn(EncryptService.INSTANCE.encryptHex(consumer
					.getSsn()));
		}

		if (consumer.getPartnerSSN() != null) {
			consumer.setPartnerSSN(EncryptService.INSTANCE.encryptHex(consumer
					.getPartnerSSN()));
		}

		if (consumer.getDriverLicenseNo() != null) {
			consumer.setDriverLicenseNo(EncryptService.INSTANCE
					.encryptHex(consumer.getDriverLicenseNo()));
		}
		
		}catch(Exception e) {
			
		}

	}

}

