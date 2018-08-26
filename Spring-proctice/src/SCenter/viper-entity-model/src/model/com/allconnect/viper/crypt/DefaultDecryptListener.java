package com.A.V.crypt;

import java.util.Calendar;
import java.util.logging.Logger;

import com.A.V.beans.entity.AccountHolder;
import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.SelectedDialogue;
import com.A.V.crypt.service.DecryptService;
import com.A.V.utility.StringUtils;

public enum DefaultDecryptListener {

	INSTANCE;

	private final static Logger logger = Logger.getLogger(DefaultDecryptListener.class .getName());


	public void decrypt(Consumer consumer) {

		try {
			consumer.setSsn(decryptToPlainText(consumer.getSsn()));
			consumer.setPartnerSSN(decryptToPlainText(consumer.getPartnerSSN()));
			consumer.setDriverLicenseNo(decryptToPlainText(consumer
					.getDriverLicenseNo()));
			consumer.setPin(decryptToPlainText(consumer.getPin()));
			consumer.setSecurityAnswer(decryptToPlainText(consumer
					.getSecurityAnswer()));

			decryptBirthInfo(    consumer);


			for (BillingInformation bi : consumer.getBillingInfoList()) {
				decrypt(bi);
			}
		} catch (Exception e) {
			logger.warning (String.format("%s error decrypting.  unable to decrypt consumer %s:",
					e.getMessage(), consumer.getExternalId()));
		}

	}
	
	public void decrypt(AccountHolder accountHolder) {
		try {
			accountHolder.setSsn(decryptToPlainText(accountHolder.getSsn()));
			accountHolder.setDriverLicenseNumber(decryptToPlainText(accountHolder.getDriverLicenseNumber()));
			accountHolder.setSecurityPin(decryptToPlainText(accountHolder.getSecurityPin()));
			accountHolder.setSecurityAnswer(decryptToPlainText(accountHolder.getSecurityAnswer()));
			accountHolder.setDob(decryptToPlainText(accountHolder.getDob()));
		} catch (Exception e) {
			logger.warning (String.format("%s error decrypting.  unable to decrypt account holder %s:",
					e.getMessage(), accountHolder.getAccountHolderUniqueId()));
		}
	}

	public void decrypt(SelectedDialogue dialogue) {
	    dialogue.setValue(decryptToPlainText(dialogue.getValue()));
	}

	public void decryptBirthInfo(final Consumer consumer) {

		if (consumer == null) {
			return;
		}
		final String birthInfo = consumer.getBirthInfo();

		if ((birthInfo == null)|| (birthInfo.length() == 0)) {
			return;
		}

		String decryptedBirthInfo = decryptToPlainText(birthInfo);

		String[] dateInfo = decryptedBirthInfo.split("-");

		if (dateInfo.length < 3) {
			return;
		}
		consumer.setBirthInfo(decryptedBirthInfo);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(dateInfo[0]));
		cal.set(Calendar.MONTH, Integer.valueOf(dateInfo[1]));
		cal.set(Calendar.DATE, Integer.valueOf(dateInfo[2]));

		consumer.setDob(cal);

	}

	public void decrypt(BillingInformation bi) {

		bi.setCardHolderName(decryptToPlainText(bi.getCardHolderName()));
		bi.setCreditCardNumber(decryptToPlainText(bi.getCreditCardNumber()));
		bi.setRoutingNumber(decryptToPlainText(bi.getRoutingNumber()));
		bi.setCheckingAccountNumber(decryptToPlainText(bi
				.getCheckingAccountNumber()));

		// Expiration date
		if ((bi.getExpireMonth() != null) && (bi.getExpireMonth().length() > 1)
				&& (bi.getExpireYear() != null)
				&& (bi.getExpireYear().length() > 3)) {
			String plainMonth = decryptToPlainText(bi.getExpireMonth());
			String plainYear = decryptToPlainText(bi.getExpireYear());

			bi.setExpireMonth(plainMonth);
			bi.setExpireYear(plainYear);
			int month = convertInteger(plainMonth);
			int year = convertInteger(plainYear);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.DAY_OF_MONTH,
					cal.getActualMaximum(Calendar.DAY_OF_MONTH));

			bi.setExpirationDate(cal.getTime());
		}

	}

	private String decryptToPlainText(Object hexFormat) {

		if (hexFormat == null) {
			return null;
		}

		String plainText = "";

		if (hexFormat instanceof String) {

			plainText = (String) hexFormat;

			if (plainText.length() == 0) {
				return "";
			}

			try {
				plainText = DecryptService.INSTANCE.decryptHex(plainText);

			} catch (Exception e) {
				//logger.error("%s error decrypting trying again", e.getMessage());
				try {
					plainText = DecryptService.INSTANCE.decryptHex(plainText);
				} catch (Exception e1) {
					plainText = "DECRYPT ERROR";
					logger.warning(String.format("%s error decrypting.  unable to decrypt",
							e1.getMessage()));
				}
			}

		}

		return plainText;
	}

	public static int convertInteger(final String value) {

		if (value != null) {

			try {

				Integer result = (Integer.parseInt(value.trim()));

				return result;

			} catch (NumberFormatException nfe) {

			}
		}

		throw new IllegalArgumentException("conversion not supported:" + value);
	}

}
