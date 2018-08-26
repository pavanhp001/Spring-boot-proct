package com.A.vm.util.converter.marshall;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.beans.entity.CustomerPaymentEventStatus;
import com.A.xml.v4.PaymentEventType;
import com.A.xml.v4.PaymentEventTypeType;

/**
 * @author ebthomas
 * 
 */
public final class MarshallPaymentEvent {
	/**
	 * Marshall Address.
	 */
	private MarshallPaymentEvent() {

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

		/**
		 * @param billingInfo
		 *            Entity
		 * @return XMLBean
		 */
		public static PaymentEventType build(final CustomerPaymentEvent domain,
				PaymentEventType paymentEventType) {
			if (isValid()) {
				return buildPaymentEvent(domain, paymentEventType);
			}
			throw new IllegalArgumentException(
					"invalid document.  unable to build");
		}

		/**
		 * @param billingInfo
		 *            Entity Bean
		 * @return BillingInfoType
		 */
		private static PaymentEventType buildPaymentEvent(
				final CustomerPaymentEvent domain,
				PaymentEventType paymentEventType) {
			paymentEventType.setAmount(BigDecimal.valueOf(domain.getAmount()));
			paymentEventType.setBillingInfoId( domain.getBillingInfoId() );
			paymentEventType.setConfirmNumber(domain.getConfirmNum());
			
			if (domain.getCustAgreedCCDisclosure() == null) {
				domain.setCustAgreedCCDisclosure(Boolean.FALSE);
			}
			String disclosed = domain.getCustAgreedCCDisclosure() ? "true" : "false";
			paymentEventType.setCustAgreedCCDisclosure(disclosed);
			paymentEventType.setCVV(domain.getCvv());
			paymentEventType.setExternalId(domain.getExternalId());
			paymentEventType.setLineItemId(domain.getLineItemId());
			paymentEventType.setOrderId(domain.getOrderId());

			if (domain.getEventType() != null) {
				PaymentEventTypeType.Enum paymentEventEnum = PaymentEventTypeType.Enum
						.forString(domain.getEventType());
				paymentEventType.setEvent(paymentEventEnum);
			}
			List<CustomerPaymentEventStatus> domainPaymentStatusHistoryList = domain
					.getPaymentStatusHistory();
			MarshallPaymentEventStatus.Builder.copyPaymentEventStatus(
			paymentEventType, domainPaymentStatusHistoryList);
			if (domain.getTransDate() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(domain.getTransDate());
			paymentEventType.setTransactionDate(cal);
			} else {
				domain.setTransDate(null) ;
			}
			return paymentEventType;
		}
	}
}
