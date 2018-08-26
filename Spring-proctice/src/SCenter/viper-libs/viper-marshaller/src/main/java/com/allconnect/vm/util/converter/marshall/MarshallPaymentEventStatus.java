package com.A.vm.util.converter.marshall;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.A.V.beans.entity.CustomerPaymentEventStatus;
import com.A.xml.v4.PaymentEventType;
import com.A.xml.v4.PaymentStatusWithTypeType;

/**
 * @author ebthomas
 * 
 */
public final class MarshallPaymentEventStatus {
	/**
	 * @author ebthomas
	 * 
	 */

	private static final Logger logger = Logger
			.getLogger(MarshallPaymentEventStatus.class);

	public static final class Builder {

		/**
		 * Builder for Sales Order Bean Status.
		 */
		private Builder() {
			super();
		}

		public static void copyPaymentEventStatus(
				final PaymentEventType paymentEventType,
				final List<CustomerPaymentEventStatus> eventStatusList) {

			logger.debug("copy PaymentEventStatus");

			for (CustomerPaymentEventStatus customerPaymentEventStatus : eventStatusList) {

				PaymentStatusWithTypeType paymentStatusWithTypeType = paymentEventType.addNewPaymentStatus();
				copyPaymentEventStatus(customerPaymentEventStatus,paymentStatusWithTypeType);
			}

		}

		/**
		 * @param eventStatus
		 *            source
		 * @return destination
		 */
		public static void copyPaymentEventStatus(
				final CustomerPaymentEventStatus eventStatus,
				final PaymentStatusWithTypeType paymentStatusWithTypeType) {

			if ((eventStatus != null) && (eventStatus.getReasons() != null)) {
				for (String value : eventStatus.getReasons()) {
					paymentStatusWithTypeType
							.addReason(Integer.parseInt(value));
				}

				if (eventStatus.getStatus() != null) {
					paymentStatusWithTypeType.setStatus(Integer
							.valueOf(eventStatus.getStatus()));
				}
				
			 	
				if (eventStatus.getExternalId() != null) {
					paymentStatusWithTypeType.setExternalId( Long.valueOf(eventStatus
							.getExternalId()));
				}

				if (eventStatus.getDateTimeStamp() != null) {
					paymentStatusWithTypeType.setDateTimeStamp(eventStatus
							.getDateTimeStamp());
				}
				

			}

			 
		}

	}
}
