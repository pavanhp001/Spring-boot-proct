package com.A.util.converter.mask;

import java.util.Calendar;
import java.util.List;

import com.A.comm.manager.jms.util.StringUtil;
import com.A.V.SelectedDialogueUtil;
import com.A.xml.v4.AccountHolderType;
import com.A.xml.v4.CustBillingInfoType;
import com.A.xml.v4.CustomerDocument.Customer;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.DialogValueType;

public enum SecurityMask {

	INSTANCE;

	final static Calendar cal = Calendar.getInstance();
	
	public static final String MASKED_SSN = "****-**-****";
	public static final String MASKED_PIN = "****";
	public static final String MASKED_DRIVER_LICENSE_NUMBER = "*****";
	public static final String MASKED_SECURITY_ANSWER = "****";

	public void execute(final Customer customer) {

		if (!StringUtil.isEmpty(customer.getSsn())) {
			customer.setSsn("****-**-****");
		}


		if (customer.getDob() != null) {
			customer.setDob(Calendar.getInstance());
			customer.getDob().set(Calendar.YEAR, 2000);
			customer.getDob().set(Calendar.MONTH, 1);
			customer.getDob().set(Calendar.DATE, 1);
		}


		if (!StringUtil.isEmpty(customer.getPartnerSSN())) {
			customer.setPartnerSSN("****-**-****");
		}

		if (customer.getDriverLicense() != null) {

			if (customer.getDriverLicense().getLicenseNumber() != null) {
				customer.getDriverLicense().setLicenseNumber("*****");
			}

			if (customer.getDriverLicense().getLicenseExpirationDate() != null) {

				customer.getDriverLicense().setLicenseExpirationDate(cal);
			}

		}

		if (customer.getSecurityVerificationInfo() != null) {
			if (customer.getSecurityVerificationInfo() != null) {
				customer.getSecurityVerificationInfo().setPin("****");
			}
		}

		if (customer.getSecurityVerificationInfo() != null) {
			if (customer.getSecurityVerificationInfo() != null) {
				customer.getSecurityVerificationInfo()
						.setSecurityAnswer("****");
			}
		}

		if ((customer.getBillingInfoList() != null)
				&& (customer.getBillingInfoList().getBillingInfoList() != null)) {

			for (CustBillingInfoType bi : customer.getBillingInfoList()
					.getBillingInfoList()) {

				if (bi.getCardHolderName() != null) {
					bi.setCardHolderName("*****");
				}
				if (bi.getCreditCardNumber() != null) {
					bi.setCreditCardNumber("*****");
				}
				if (bi.getExpirationYearMonth() != null) {
					bi.setExpirationYearMonth(cal);
				}
				if (bi.getCheckingAccountNumber() != null) {
					bi.setCheckingAccountNumber("*****");
				}
				if (bi.getRoutingNumber() != null) {
					bi.setRoutingNumber("*****");
				}
			}

		}

	}

	public void execute(final DialogValueType dvType) {
	    List<String> idList = SelectedDialogueUtil.getSecureDialogueList();
	    if(idList.contains(dvType.getExternalId().trim().replace("/", "")) && dvType.getValueList() != null && !dvType.getValueList().isEmpty()){
		dvType.getValueList().get(0).setStringValue("*****");
	    }
	}
	public void execute(final CustomerType customer) {

		if (!StringUtil.isEmpty(customer.getSsn())) {
			customer.setSsn("****-**-****");
		}

		if (customer.getDob() != null) {
			customer.setDob(Calendar.getInstance());
			customer.getDob().set(Calendar.YEAR, 2000);
			customer.getDob().set(Calendar.MONTH, 1);
			customer.getDob().set(Calendar.DATE, 1);
		}


		if (!StringUtil.isEmpty(customer.getPartnerSSN())) {
			customer.setPartnerSSN("****-**-****");
		}

		if (customer.getDriverLicense() != null) {

			if (customer.getDriverLicense().getLicenseNumber() != null) {
				customer.getDriverLicense().setLicenseNumber("*****");
			}

			if (customer.getDriverLicense().getLicenseExpirationDate() != null) {

				customer.getDriverLicense().setLicenseExpirationDate(cal);
			}

		}

		if (customer.getSecurityVerificationInfo() != null) {
			if (customer.getSecurityVerificationInfo() != null) {
				customer.getSecurityVerificationInfo().setPin("****");
			}
		}

		if (customer.getSecurityVerificationInfo() != null) {
			if (customer.getSecurityVerificationInfo() != null) {
				customer.getSecurityVerificationInfo()
						.setSecurityAnswer("****");
			}
		}

		if ((customer.getBillingInfoList() != null)
				&& (customer.getBillingInfoList().getBillingInfoList() != null)) {

			for (CustBillingInfoType bi : customer.getBillingInfoList()
					.getBillingInfoList()) {

				if (bi.getCardHolderName() != null) {
					bi.setCardHolderName("*****");
				}
				if (bi.getCreditCardNumber() != null) {
					bi.setCreditCardNumber("*****");
				}
				if (bi.getExpirationYearMonth() != null) {
					bi.setExpirationYearMonth(cal);
				}
				if (bi.getCheckingAccountNumber() != null) {
					bi.setCheckingAccountNumber("*****");
				}
				if (bi.getRoutingNumber() != null) {
					bi.setRoutingNumber("*****");
				}
			}

		}

	}
	
	public void execute(List<AccountHolderType> accountHolders) {
		if(accountHolders != null) {
			for(AccountHolderType accountHolder : accountHolders) {
				if(accountHolder.getSsn() != null) {
					accountHolder.setSsn(MASKED_SSN);
				}
				if(accountHolder.getDriversLicense() != null) {
					if(accountHolder.getDriversLicense().getLicenseNumber() != null) {
						accountHolder.getDriversLicense().setLicenseNumber(MASKED_DRIVER_LICENSE_NUMBER);
					}
					if(accountHolder.getDriversLicense().getLicenseExpirationDate() != null) {
						accountHolder.getDriversLicense().setLicenseExpirationDate(cal);
					}
				}
				if(accountHolder.getSecurityVerificationInfo() != null) {
					if(accountHolder.getSecurityVerificationInfo().getPin() != null) {
						accountHolder.getSecurityVerificationInfo().setPin(MASKED_PIN);
					}
					if(accountHolder.getSecurityVerificationInfo().getSecurityAnswer() != null) {
						accountHolder.getSecurityVerificationInfo().setSecurityAnswer(MASKED_SECURITY_ANSWER);
					}
				}
				if(accountHolder.getDob() != null) {
					accountHolder.setDob(cal);
				}
			}
		}
	}
	
	
}
