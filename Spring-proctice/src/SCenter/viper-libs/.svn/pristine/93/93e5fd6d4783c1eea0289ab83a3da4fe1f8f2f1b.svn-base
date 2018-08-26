package com.A.vm.util.converter.marshall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerAddressAssociation;
import com.A.xml.v4.CustBillingInfoType;

/**
 * @author ebthomas
 *
 */
public final class MarshallBillingInformation {
	/**
	 * Marshall Address.
	 */

	private static final Logger logger = Logger
			.getLogger(MarshallBillingInformation.class);

	private MarshallBillingInformation() {

	}

	/**
	 * @author ebthomas.
	 *
	 */
	public static final class Builder {

		/**
		 * Marshall Address.
		 */
		private Builder() {
			super();
		}

		private static Boolean isValid() {
			return Boolean.TRUE;
		}

		public static AddressBean getAddressByExtId(Consumer dest,
				long addressExtId) {

			AddressBean address = null;

			List<CustomerAddressAssociation> addrList = new ArrayList<CustomerAddressAssociation>(
					dest.getAddresses());

			for (CustomerAddressAssociation caa : addrList) {
				if ((caa != null) && (caa.getAddress() != null)) {
					if (addressExtId == caa.getAddress().getExternalId()) {
						return caa.getAddress();
					}
				}
			}

			return address;
		}

		/**
		 * @param billingInfo
		 *            Entity
		 * @return XMLBean
		 */
		public static CustBillingInfoType build(final Consumer src,
				final BillingInformation billingInfo,
				CustBillingInfoType billingInfoType, boolean includeAccountHolders) {
			if (isValid()) {
				return buildBillingInfo(src, billingInfo, billingInfoType, includeAccountHolders);
			}
			throw new IllegalArgumentException(
					"invalid document.  unable to build");
		}
		

		/**
		 * @param billingInfo
		 *            Entity Bean
		 * @return BillingInfoType
		 */
		@SuppressWarnings("static-access")
		private static CustBillingInfoType buildBillingInfo(final Consumer src,
				final BillingInformation billingInfo,
				CustBillingInfoType billingInfoType, boolean includeAccountHolders) {

			if (billingInfo != null) {

				if (billingInfo.getAddressRefId() != null) {
					billingInfoType
							.setAddressRef(billingInfo.getAddressRefId());
				} else {
					AddressBean addr = getAddressByExtId(src,
							billingInfo.getAddressExternalId());

					if (addr != null) {
						String uniqueId = addr.getAddressUniqueId();

						billingInfoType.setAddressRef(uniqueId);
					}
				}

				billingInfoType
						.setBillingMethod(billingInfo.getBillingMethod());
				billingInfoType.setCreditCardNumber(billingInfo
						.getCreditCardNumber());
				billingInfoType.setVerificationCode(billingInfo
						.getVerificationCode());
				billingInfoType.setCheckingAccountNumber(billingInfo
						.getCheckingAccountNumber());
				billingInfoType
						.setRoutingNumber(billingInfo.getRoutingNumber());
				billingInfoType.setExternalId(billingInfo.getExternalId()
						.longValue());
				billingInfoType.setBillingUniqueId(billingInfo
						.getBillingUniqueId());
				billingInfoType.setCardHolderName(billingInfo
						.getCardHolderName());
				
				billingInfoType.setStatus(billingInfo.getStatus());
				billingInfoType.setIsChecking(billingInfo.getIsChecking());

				//Update Billing Month and Year
				if ((billingInfo.getExpireMonth() != null)
						&& (billingInfo.getExpireYear() != null)) {
					try {
						int month = Integer.valueOf(billingInfo
								.getExpireMonth());
						int year = Integer.valueOf(billingInfo.getExpireYear());
						logger.trace("Marshalling Year :" + year +"   Month : " + month);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String dt = year+"-"+month+"-"+"01";
						Calendar expCal = Calendar.getInstance();
						expCal.setTime(sdf.parse(dt));
						billingInfoType.setExpirationYearMonth(expCal);

					} catch (Exception e) {
						logger.warn(e.getMessage()
								+ " unable to marshall billing date", e);
					}
				}
				billingInfoType.setCreditCardType(billingInfoType
						.getCreditCardType().forString(
								billingInfo.getCardType()));
				logger.debug("Billing Info entity account holder unique id" +billingInfo.getAccountHolderUniqueId());
				if(includeAccountHolders) {
					billingInfoType.setAccountHolderUniqueId(billingInfo.getAccountHolderUniqueId());
				}
			}

			return billingInfoType;
		}
	}
}
