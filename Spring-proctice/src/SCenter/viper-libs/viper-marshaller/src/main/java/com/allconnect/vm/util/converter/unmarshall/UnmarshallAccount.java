package com.A.vm.util.converter.unmarshall;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.A.V.beans.entity.Account;
import com.A.V.beans.entity.Consumer;
import com.A.xml.v4.AccountType;
import com.A.xml.v4.Accounts;
import com.A.xml.v4.CustomerType;

/**
 * @author ebthomas
 * 
 */
public final class UnmarshallAccount {

	private static final Logger logger = Logger
			.getLogger(UnmarshallAccount.class);

	/**
	 * convert Address Bean.
	 */
	private UnmarshallAccount() {
		super();
	}

	/**
	 * @author ebthomas
	 * 
	 */
	public static final class Builder {

		/**
		 * Builder for Sales Order Bean Status.
		 */
		private Builder() {
			super();
		}

		/**
		 * @return billing information bean
		 */
		private Account doBuildAccountInfo() {
			return null;
		}

		/**
		 * @return Billing Information Bean
		 */
		public Account build() {
			return doBuildAccountInfo();
		}

		/**
		 * @param custBillingType
		 *            Order Type
		 * @return Billing Information Bean
		 */

		/**
		 * @param billingInfoType
		 *            Input source
		 * @return Billing Information Object
		 */
		public static Account buildAccount(Account account,
				final CustomerType customerType, boolean isUpdateRequest) {
			/*
			 * if(!isUpdateRequest) AccountBean = new Account();
			 */

			return copy(account, customerType, isUpdateRequest);
		}

		/**
		 * @param src
		 *            source
		 * @return destination
		 */
		public static Account copy(final Account account,
				final CustomerType src, boolean isUpdateRequest) {
			if (src == null) {
				return null;
			}

			return account;

		}

		public static void copy(CustomerType src, Consumer dest,
				boolean isUpdateRequest) {
			if (src != null) {
				 
					Accounts accounts = src.getAccounts();
					
					if ((accounts != null) && (accounts.getAccountList() != null) && (accounts.getAccountList().size() != 0)) {
					
					List<AccountType> srcAccountType =  accounts.getAccountList();
					 
					if (srcAccountType != null) {
						for (AccountType accountType : srcAccountType) {

							if ((accountType.getExternalId() == null)
									|| ("0".equals(accountType.getExternalId()))) {
								createNewAccount(dest, accountType,
										Boolean.FALSE);
							} else {
								updateAccount(dest, accountType, Boolean.TRUE);
							}

						}
					}
					 
				}
			}
		}

		public static Account createNewAccount(Consumer dest,
				AccountType accountType, boolean isUpdateRequest) {
			Account acct = new Account();
			copy(acct, accountType, isUpdateRequest);

			Set<Account> accounts = dest.getAccounts();

			if (accounts == null) {
				accounts = new HashSet<Account>();
				dest.setAccounts(accounts);
			}

			accounts.add(acct);

			return acct;
		}

		public static Account updateAccount(Consumer dest,
				AccountType accountType, boolean isUpdateRequest) {

			try {
				Long acctExternalId = Long.valueOf(accountType.getExternalId());
				Set<Account> accounts = dest.getAccounts();

				for (Account account : accounts) {

					if ((account.getExternalId() != null)
							&& (account.getExternalId().equals(acctExternalId))) {
						copy(account, accountType, isUpdateRequest);
						return account;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			return null;

		}

		public static void copy(final Account acct, final AccountType aType,
				final boolean isUpdateRequest) {

			if ((aType != null) && (acct != null)) {

				if (!isUpdateRequest) {
					String externalId = aType.getExternalId();
					Long externalIdAsLong = 0L;
					if ((externalId != null) && (externalId.length() > 0)) {
						try {
							externalIdAsLong = Long.valueOf(externalId);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}

					acct.setExternalId(externalIdAsLong);
				}

				acct.setAccountStatus(aType.getAccountStatus());
				acct.setAccountType(aType.getAccountType());
				acct.setAddressExtId(aType.getAddressExtId());
				acct.setBillingAccountNumber(aType.getBillingAccountNumber());
				acct.setBillingPhone(aType.getBillingAccountTelephoneNumber());
				acct.setConsentToCC(aType.getConsentToCC());
				acct.setConsumerExtId(aType.getConsumerExtId());
				acct.setCreditAlert(aType.getCreditAlert());
				acct.setCreditCheck(aType.getCreditCheck());
				acct.setSsn(aType.getExistingLast4SSN());

				if (aType.getSecurityCreditCard() != null) {

					acct.setCreditCardRef(aType.getSecurityCreditCard()
							.getCreditCardRef());
					acct.setIsAuthorized("1".equals(aType
							.getSecurityCreditCard().getIsAuthorized()) ? 1 : 0);
					acct.setSecurityTCAccepted(aType.getSecurityCreditCard()
							.getSecurityTCAccepted());

				}

				if (aType.getAccountTypeIndicator() != null) {
					acct.setAccountTypeIndicator(aType
							.getAccountTypeIndicator().getStringValue());
				}

				if (aType.getAuthentication() != null) {
					acct.setPin(aType.getAuthentication().getPin());
					acct.setSecurityAnswer(aType.getAuthentication()
							.getSecurityAnswer());
					acct.setSecurityQuestion(aType.getAuthentication()
							.getSecurityQuestion());
				}

				acct.setProviderType(aType.getProviderType());
				acct.setPreviousAddressIndicator(aType
						.getPrevAddressIndicator() ? "1" : "0");
				acct.setSuppressionIndicator(aType.getSuppressionIndicator());

			}

		}
	}
}
