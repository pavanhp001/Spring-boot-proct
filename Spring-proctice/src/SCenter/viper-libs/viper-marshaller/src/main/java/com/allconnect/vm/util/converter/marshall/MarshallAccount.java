package com.A.vm.util.converter.marshall;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlAnySimpleType;
import com.A.V.beans.entity.Account;
import com.A.V.beans.entity.Consumer;
import com.A.xml.v4.AccountType;
import com.A.xml.v4.Accounts;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.SecurityCreditCardType;
import com.A.xml.v4.SecurityVerificationType;

/**
 * @author ebthomas
 * 
 */
public final class MarshallAccount {

	private static final Logger logger = Logger
			.getLogger(MarshallAccount.class);

	/**
	 * Marshall Account.
	 */
	private MarshallAccount() {

	}

	/**
	 * @author ebthomas.
	 * 
	 */
	public static final class Builder {

		private Builder() {
			super();
		}

		private static Boolean isValid() {
			return Boolean.TRUE;
		}

		/**
		 * @param billingInfo
		 *            Entity
		 * @return XMLBean
		 */
		public static void build(final Consumer src, final CustomerType dest) {
			if (isValid()) {
				buildAccountType(src, dest);
			} else {
				throw new IllegalArgumentException(
						"invalid Account Information.  unable to build");
			}
		}

		private static void buildAccountType(final Consumer src,
				final CustomerType dest) {

			try {

				if ((src.getAccounts() != null)
						&& (src.getAccounts().size() > 0)) {

					Accounts accounts = dest.addNewAccounts();

					for (Account acct : src.getAccounts()) {

						buildAccountType(acct, accounts);

					}
				}
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}

		}

		private static void buildAccountType(final Account acct,
				final Accounts accounts) {

			AccountType aType = accounts.addNewAccount();

			XmlAnySimpleType anySimpleType = aType.addNewAccountTypeIndicator();
			SecurityVerificationType securityVerfType = aType
					.addNewAuthentication();
			securityVerfType.setPin(acct.getPin());
			securityVerfType.setSecurityAnswer(acct.getSecurityAnswer());
			securityVerfType.setSecurityQuestion(acct.getSecurityQuestion());

			SecurityCreditCardType securityCCType = aType
					.addNewSecurityCreditCard();
			securityCCType.setCreditCardRef(acct.getCreditCardRef());
			securityCCType.setIsAuthorized(String.valueOf(acct
					.getIsAuthorized()));
			securityCCType.setSecurityTCAccepted(acct.getSecurityTCAccepted());

			anySimpleType.setStringValue(acct.getAccountTypeIndicator());

			aType.setAccountStatus(acct.getAccountStatus());
			aType.setAccountType(acct.getAccountType());
			aType.setAddressExtId(acct.getAddressExtId());
			aType.setBillingAccountNumber(acct.getBillingAccountNumber());
			aType.setBillingAccountTelephoneNumber(acct.getBillingPhone());
			aType.setConsentToCC(acct.getConsentToCC());
			aType.setConsumerExtId(acct.getConsumerExtId());
			aType.setCreditAlert(acct.getCreditAlert());
			aType.setCreditCheck(acct.getCreditCheck());
			aType.setExistingLast4SSN(acct.getSsn());
			
			
			Long externalIdAsLong = acct.getExternalId();
			String externalId = "0";
			 if (externalIdAsLong != null) {
 				try {
					externalId = String.valueOf(externalIdAsLong);
				} catch (Exception e) {
					logger.warn("invalid accounts external id");
				}
 				
			 }
			 
			aType.setExternalId(externalId);
			
			boolean prevAddressIndicator = false;

			if (acct.getPreviousAddressIndicator() != null) {
				prevAddressIndicator = "1".equals(acct
						.getPreviousAddressIndicator());
			}
			aType.setPrevAddressIndicator(prevAddressIndicator);
			aType.setProviderType(acct.getProviderType());
			aType.setSecurityCreditCard(securityCCType);
			aType.setSuppressionIndicator(acct.getSuppressionIndicator());
			aType.setAuthentication(securityVerfType);
			aType.setAccountTypeIndicator(anySimpleType);

		}
	}

}
