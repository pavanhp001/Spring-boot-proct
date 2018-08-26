package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import com.A.util.XmlUtil;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.beans.entity.CustomerPaymentEventStatus;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.PaymentEventType;
import com.A.xml.v4.PaymentStatusWithTypeType;

public class UnmarshallPaymentEvent {

	private static final Logger logger = Logger
			.getLogger(UnmarshallPaymentEvent.class);

	/**
	 * convert PaymentEvent Bean.
	 */
	private UnmarshallPaymentEvent() {
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
		private CustomerPaymentEvent doBuildUnmarshallPaymentEvent() {
			return null;
		}

		/**
		 * @param billingInfoType
		 *            Input source
		 * @return Billing Information Object
		 */
		public static CustomerPaymentEvent buildCustomerPaymentEvent(
				Consumer domainCustomerdest,
				CustomerPaymentEvent domainPaymentEvent,
				final PaymentEventType xmlPaymentEvent, boolean isUpdateRequest) {

			return copy(domainCustomerdest, domainPaymentEvent,
					xmlPaymentEvent, isUpdateRequest);
		}
		
		public static void copy(CustomerType xmlCustomerSrc, Consumer domainCustomerdest,
				boolean isUpdateRequest) {
			if (domainCustomerdest.getPaymentEvents() == null) {
				domainCustomerdest
						.setPaymentEvents(new HashSet<CustomerPaymentEvent>());
			}

			if ((xmlCustomerSrc.getPayments() != null)
					&& (xmlCustomerSrc.getPayments().getPaymentEventList() != null)) {
				for (PaymentEventType paymentEventType : xmlCustomerSrc
						.getPayments().getPaymentEventList()) {

					CustomerPaymentEvent customerPaymentEvent = UnmarshallPaymentEvent.Builder
							.copy(domainCustomerdest, paymentEventType,
									isUpdateRequest);

					if ((customerPaymentEvent.getExternalId() == null) || ("0".equals(customerPaymentEvent.getExternalId()))) {
						domainCustomerdest.getPaymentEvents().add(
								customerPaymentEvent);
					}
				}
			}
			
		}

		public static CustomerPaymentEvent copy(Consumer domainCustomerdest,
				final PaymentEventType src, boolean isUpdateRequest) {

			CustomerPaymentEvent domainPaymentEvent = null;

			for (CustomerPaymentEvent existingEvent : domainCustomerdest
					.getPaymentEvents()) {

				if ((existingEvent.getExternalId() != null) && (src.getExternalId() != null)) {
					if (existingEvent.getExternalId().equals(
							src.getExternalId())) {
						domainPaymentEvent = existingEvent;
					}
				}
			}

			//did not find existing event for update.  create new
			if (domainPaymentEvent == null) {
				domainPaymentEvent = new CustomerPaymentEvent();
			}

			return copy(domainCustomerdest, domainPaymentEvent, src,
					isUpdateRequest);

		}

		private static final String PAYMENTS = "payments";

		public static void copy(Consumer domainCustomerdest,
				CustomerType xmlSrcCustomerType,
				Consumer domainSrcCustomerType, boolean isUpdateRequest) {

			if (xmlSrcCustomerType != null) {
				if (!XmlUtil.isElementNull(xmlSrcCustomerType.newCursor(),
						PAYMENTS)) {
					CustomerPaymentEvent domainCustomerPaymentEvent = null;
					Set<CustomerPaymentEvent> updatedDomainCustomerPaymentEventList = new HashSet<CustomerPaymentEvent>();

					if ((xmlSrcCustomerType != null)
							&& (xmlSrcCustomerType.getPayments() != null)
							&& (xmlSrcCustomerType.getPayments()
									.getPaymentEventList() != null)) {
						List<PaymentEventType> xmlPaymentEventList = xmlSrcCustomerType
								.getPayments().getPaymentEventList();
						Set<CustomerPaymentEvent> currentCustomerPaymentEventList = null;

						if (xmlPaymentEventList != null) {
							for (PaymentEventType xmlPaymentEvent : xmlPaymentEventList) {
								if ("0".equals(xmlPaymentEvent.getExternalId())) {
									domainCustomerPaymentEvent = createNewCustomerPaymentEvent(
											domainCustomerdest,
											domainSrcCustomerType,
											xmlPaymentEvent, isUpdateRequest,
											updatedDomainCustomerPaymentEventList);

								} else {
									currentCustomerPaymentEventList = domainSrcCustomerType
											.getPaymentEvents();
									if (currentCustomerPaymentEventList != null) {
										for (CustomerPaymentEvent currentCustomerPaymentEvent : currentCustomerPaymentEventList) {

											if (currentCustomerPaymentEvent
													.getExternalId() == xmlPaymentEvent
													.getExternalId()) {
												domainCustomerPaymentEvent = copy(
														domainCustomerdest,
														domainCustomerPaymentEvent,
														xmlPaymentEvent,
														isUpdateRequest);
												syncPaymentEventOnChange(
														domainCustomerPaymentEvent,
														domainSrcCustomerType,
														xmlPaymentEvent);
											}
										}
									}
								}

								if (domainCustomerPaymentEvent != null) {
									updatedDomainCustomerPaymentEventList
											.add(domainCustomerPaymentEvent);
								}
							}
							domainSrcCustomerType
									.setPaymentEvents(updatedDomainCustomerPaymentEventList);
						}
					}
				}
			}
		}

		public static CustomerPaymentEvent createNewCustomerPaymentEvent(
				Consumer domainCustomerdest, Consumer dest,
				PaymentEventType paymentEventType, boolean isUpdateRequest,
				Set<CustomerPaymentEvent> updatedBillingList) {

			CustomerPaymentEvent customerPaymentEvent = new CustomerPaymentEvent();
			customerPaymentEvent = copy(domainCustomerdest,
					customerPaymentEvent, paymentEventType, isUpdateRequest);
			syncPaymentEventOnChange(customerPaymentEvent, dest,
					paymentEventType);

			return customerPaymentEvent;
		}

		public static void syncPaymentEventOnChange(
				CustomerPaymentEvent domainPaymentEvent, Consumer destConsumer,
				PaymentEventType xmlPaymentEventType) {

		}

		/**
		 * @param src
		 *            source
		 * @return destination
		 */
		public static CustomerPaymentEvent copy(Consumer domainCustomerdest,
				CustomerPaymentEvent domainPaymentEvent,
				final PaymentEventType src, boolean isUpdateRequest) {
			if (src == null) {
				return null;
			}

			if (domainPaymentEvent == null) {
				domainPaymentEvent = new CustomerPaymentEvent();

			}

			 
			if (src.getAmount() != null) {
				domainPaymentEvent.setAmount(src.getAmount().doubleValue());
			}

			if (src.getBillingInfoId() != null) {
				domainPaymentEvent.setBillingInfoId(src.getBillingInfoId());
			}

			if (src.getConfirmNumber() != null) {
				domainPaymentEvent.setConfirmNum(src.getConfirmNumber());
			}

			if (src.getCustAgreedCCDisclosure() != null) {
				boolean disclosed = ("true"
						.equals(src.getCustAgreedCCDisclosure())) || ("1"
						.equals(src.getCustAgreedCCDisclosure()));
				domainPaymentEvent.setCustAgreedCCDisclosure(disclosed);
			} else {
				domainPaymentEvent.setCustAgreedCCDisclosure(Boolean.FALSE);
			}

			if (src.getCVV() != null) {
				domainPaymentEvent.setCvv(src.getCVV());
			}

			if (src.getEvent() != null) {
				domainPaymentEvent.setEventType(src.getEvent().toString());
			}

			if (src.getExternalId() != null) {
				domainPaymentEvent.setExternalId(src.getExternalId());
			}

			if (src.getLineItemId() != null) {
				domainPaymentEvent.setLineItemId(src.getLineItemId());
			}

			if (src.getOrderId() != null) {
				domainPaymentEvent.setOrderId(src.getOrderId());
			}

			if (src.getPaymentStatusList() != null) {

				List<PaymentStatusWithTypeType> paymentStatusTypeList = src
						.getPaymentStatusList();

				List<CustomerPaymentEventStatus> paymentStatusHistory = UnmarshallPaymentEventStatus
						.copyCustomerPaymentEventStatus(paymentStatusTypeList);

				domainPaymentEvent.getPaymentStatusHistory().addAll(
						paymentStatusHistory);
			}

			if (src.getTransactionDate() != null) {
				domainPaymentEvent.setTransDate(src.getTransactionDate()
						.getTime());
			}
			return domainPaymentEvent;
		}

	}
	
	
	public static CustomerPaymentEvent createPaymentEvent(final PaymentEventType paymentEventType) {
		CustomerPaymentEvent domainPaymentEvent = new CustomerPaymentEvent();
		if (paymentEventType == null) {
			return null;
		}

		if (paymentEventType.getAmount() != null) {
			domainPaymentEvent.setAmount(paymentEventType.getAmount().doubleValue());
		}

		if (paymentEventType.getBillingInfoId() != null) {
			domainPaymentEvent.setBillingInfoId(paymentEventType.getBillingInfoId());
		}

		if (paymentEventType.getConfirmNumber() != null) {
			domainPaymentEvent.setConfirmNum(paymentEventType.getConfirmNumber());
		}

		if (paymentEventType.getCustAgreedCCDisclosure() != null) {
			boolean disclosed = ("true"
					.equals(paymentEventType.getCustAgreedCCDisclosure())) || ("1"
					.equals(paymentEventType.getCustAgreedCCDisclosure()));
			domainPaymentEvent.setCustAgreedCCDisclosure(disclosed);
		} else {
			domainPaymentEvent.setCustAgreedCCDisclosure(Boolean.FALSE);
		}

		if (paymentEventType.getCVV() != null) {
			domainPaymentEvent.setCvv(paymentEventType.getCVV());
		}

		if (paymentEventType.getEvent() != null) {
			domainPaymentEvent.setEventType(paymentEventType.getEvent().toString());
		}

		if (paymentEventType.getExternalId() != null) {
			domainPaymentEvent.setExternalId(paymentEventType.getExternalId());
		}

		if (paymentEventType.getLineItemId() != null) {
			domainPaymentEvent.setLineItemId(paymentEventType.getLineItemId());
		}

		if (paymentEventType.getOrderId() != null) {
			domainPaymentEvent.setOrderId(paymentEventType.getOrderId());
		}

		if (paymentEventType.getPaymentStatusList() != null) {

			List<PaymentStatusWithTypeType> paymentStatusTypeList = paymentEventType
					.getPaymentStatusList();

			List<CustomerPaymentEventStatus> paymentStatusHistory = UnmarshallPaymentEventStatus
					.copyCustomerPaymentEventStatus(paymentStatusTypeList);

			domainPaymentEvent.getPaymentStatusHistory().addAll(
					paymentStatusHistory);
		}

		if (paymentEventType.getTransactionDate() != null) {
			domainPaymentEvent.setTransDate(paymentEventType.getTransactionDate()
					.getTime());
		}
		return domainPaymentEvent;
	}

	public static Set<CustomerPaymentEvent> buildPaymentEvents(final List<PaymentEventType> paymentEventTypes) {
		Set<CustomerPaymentEvent> paymentEvents = new HashSet<CustomerPaymentEvent>();
		for(PaymentEventType paymentEventType : paymentEventTypes) {
			CustomerPaymentEvent paymentEvent = createPaymentEvent(paymentEventType);
			if(paymentEvent != null) {
				paymentEvents.add(paymentEvent);
			}
		}
		return paymentEvents;
	}

	public static void copyCustom(CustomerPaymentEvent domainPaymentEvent,
			final PaymentEventType src, boolean isUpdateRequest) {

	}
}
