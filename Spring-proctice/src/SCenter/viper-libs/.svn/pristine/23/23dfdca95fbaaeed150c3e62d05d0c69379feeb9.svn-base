package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.A.util.XmlUtil;
import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.vm.util.converter.DynamicBuilder;
import com.A.vm.util.converter.mapper.BillingInfoMapper;
import com.A.xml.v4.CustBillingInfoType;
import com.A.xml.v4.CustomerType;

/**
 * @author ebthomas
 *
 */
public final class UnmarshallBillingInformation {

	private static final String BILLING_INFO = "billingInfoList";

	private static final Logger logger = Logger
			.getLogger(UnmarshallBillingInformation.class);

	/**
	 * convert Address Bean.
	 */
	private UnmarshallBillingInformation() {
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
		private BillingInformation doBuildBillingInfo() {
			return null;
		}

		/**
		 * @return Billing Information Bean
		 */
		public BillingInformation build() {
			return doBuildBillingInfo();
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
		public static BillingInformation buildBillingInformation(
				BillingInformation billingInformationBean,
				final CustBillingInfoType billingInfoType,
				boolean isUpdateRequest) {
			/*
			 * if(!isUpdateRequest) billingInformationBean = new
			 * BillingInformation();
			 */

			return copy(billingInformationBean, billingInfoType,
					isUpdateRequest);
		}

		/**
		 * @param src
		 *            source
		 * @return destination
		 */
		public static BillingInformation copy(
				final BillingInformation billingInfoBean,
				final CustBillingInfoType src, boolean isUpdateRequest) {
			if (src == null) {
				return null;
			}

			DynamicBuilder<CustBillingInfoType, BillingInformation> builder = new DynamicBuilder<CustBillingInfoType, BillingInformation>(
					UnmarshallValidationEnum.constrained);

			try {
				builder.copyInstanceAttributes(src, billingInfoBean,
						BillingInfoMapper.billingInformationFields,
						isUpdateRequest);
				copyCustom(billingInfoBean, src, isUpdateRequest);

			} catch (Exception e) {
				logger.error(
						"Exception thrown while copying Billing Information from XML to BillingInformation...",
						e);
				throw new IllegalArgumentException(
						"unable.to.unmarshall.billing.information");
			}

			return billingInfoBean;
		}

	}

	public static void copy(CustomerType src, Consumer dest,
			boolean isUpdateRequest) {
		if (src != null) {
			if (src.getBillingInfoList() != null) {
				BillingInformation billingInformationBean = null;

				List<CustBillingInfoType> custBillingInfoList = src
						.getBillingInfoList().getBillingInfoList();
				List<BillingInformation> billingBeanList = null;
				if (custBillingInfoList != null) {
					for (CustBillingInfoType billingInfoType : custBillingInfoList) {
						if (billingInfoType.getExternalId() == 0) {
							billingInformationBean = createNewBillingInformation(
									dest, billingInfoType, isUpdateRequest);

							if(dest.getBillingInfoList() == null){
								dest.setBillingInfoList(new HashSet<BillingInformation>());
							}
							dest.getBillingInfoList().add(
									billingInformationBean);

						} else {
							if ((src.getBillingInfoList() != null) && (src.getBillingInfoList().getBillingInfoList() != null)) {

								billingBeanList =   new ArrayList<BillingInformation>(dest.getBillingInfoList());
							}

							if (billingBeanList != null) {
								for (BillingInformation billingBean : billingBeanList) {
									// Find the matching bean from the list and
									// update it
									if (billingBean.getExternalId() == billingInfoType
											.getExternalId()) {
										billingInformationBean = copyBillingDetails(
												isUpdateRequest, billingBean,
												billingInfoType);
										syncBillingInfoOnChange(
												billingInformationBean, dest,
												billingInfoType);
									}
								}
							}
						}

					}

				}
			}
		}
	}

	public static BillingInformation createNewBillingInformation(Consumer dest,
			CustBillingInfoType billingInfoType, boolean isUpdateRequest) {

		BillingInformation billingInformationBean = new BillingInformation();
		billingInformationBean = copyBillingDetails(isUpdateRequest,
				billingInformationBean, billingInfoType);
		syncBillingInfoOnChange(billingInformationBean, dest, billingInfoType);

		return billingInformationBean;
	}

	public static void syncBillingInfoOnChange(
			BillingInformation billingInformationBean, Consumer dest,
			CustBillingInfoType billingInfoType) {

		billingInformationBean.setBillingUniqueId(billingInfoType
				.getBillingUniqueId());

		String addressRef = billingInfoType.getAddressRef();

		AddressBean address = UnmarshallConsumer.getAddressByRef(dest,
				addressRef);

		if (address == null) {
			throw new IllegalArgumentException("invalid address ref:"+addressRef);
		}
			billingInformationBean
					.setAddressExternalId(address.getExternalId());

		billingInformationBean.setConsumer(dest);

	}

	public static BillingInformation copyBillingDetails(
			boolean isUpdateRequest, BillingInformation billingInformationBean,
			CustBillingInfoType billingInfoType) {

		String addrRef = billingInfoType.getAddressRef();

		if (addrRef != null) {
			billingInformationBean.setAddressRefId(addrRef);
		}
		billingInformationBean = UnmarshallBillingInformation.Builder
				.buildBillingInformation(billingInformationBean,
						billingInfoType, isUpdateRequest);
		return billingInformationBean;
	}

	public static void copyCustom(
			final BillingInformation billingInfoDestination,
			final CustBillingInfoType billingInfoSource, boolean isUpdateRequest) {
	    	//TimeZone timeZone = TimeZone.getTimeZone("GMT");
		Calendar cal = billingInfoSource.getExpirationYearMonth();

		if (cal != null) {
			billingInfoDestination.setExpirationDate(cal.getTime());
		}
		if (billingInfoSource.getCreditCardType() != null) {
			billingInfoDestination.setCardType(billingInfoSource
					.getCreditCardType().toString());
		}

		if (billingInfoSource.getExpirationYearMonth() != null) {
			Calendar expDate = billingInfoSource.getExpirationYearMonth();
			int month = expDate.get(Calendar.MONTH);
			int year = expDate.get(Calendar.YEAR);
			logger.trace("Year :" + year +"   Month : " + month);
			billingInfoDestination.setExpireMonth(String.valueOf(month+1));
			billingInfoDestination.setExpireYear(String.valueOf(year));
		}
		billingInfoDestination.setIsChecking(billingInfoSource.getIsChecking());
		logger.info("Billing Info type account holder unique id: "+billingInfoSource.getAccountHolderUniqueId());
		if(billingInfoSource.getAccountHolderUniqueId() != null) {
			billingInfoDestination.setAccountHolderUniqueId(billingInfoSource.getAccountHolderUniqueId());
		}

	}
}
