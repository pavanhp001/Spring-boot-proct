package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.List;
import com.A.V.beans.entity.CustomerPaymentEventStatus;
import com.A.xml.v4.PaymentStatusWithTypeType;

/**
 * @author ebthomas
 * 
 */
public final class UnmarshallPaymentEventStatus {
	/**
	 * Unmarshall Status.
	 */
	private UnmarshallPaymentEventStatus() {
		super();
	}

	/**
	 * @param orderStatusSrc
	 *            Order Status
	 * @return Domain Status Record Bean
	 */
	public static List<CustomerPaymentEventStatus> copyCustomerPaymentEventStatus(
			final List<PaymentStatusWithTypeType> paymentStatusSrcList) {

		List<CustomerPaymentEventStatus> resultList = new ArrayList<CustomerPaymentEventStatus>();

		for (PaymentStatusWithTypeType status : paymentStatusSrcList) {
			resultList.add(copyCustomerPaymentEventStatus(status));
		}

		return resultList;

	}

	/**
	 * @param orderStatusSrc
	 *            Order Status
	 * @return Domain Status Record Bean
	 */
	public static CustomerPaymentEventStatus copyCustomerPaymentEventStatus(
			final PaymentStatusWithTypeType paymentStatusSrc) {

		CustomerPaymentEventStatus dest = new CustomerPaymentEventStatus();

		List<String> reasons = new ArrayList<String>();

		if ((paymentStatusSrc != null)
				&& (paymentStatusSrc.getReasonArray() != null)) {

			for (Integer value : paymentStatusSrc.getReasonArray()) {
				reasons.add(String.valueOf(value));
			}

			dest.setExternalId(String.valueOf(paymentStatusSrc.getExternalId()));
			dest.setReasons(reasons);
			dest.setStatus(String.valueOf(paymentStatusSrc.getStatus()));
			dest.setDateTimeStamp(paymentStatusSrc.getDateTimeStamp());
		}
		return dest;
	}

}
