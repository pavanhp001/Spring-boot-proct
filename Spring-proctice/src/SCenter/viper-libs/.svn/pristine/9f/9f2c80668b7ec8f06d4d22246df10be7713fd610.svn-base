package com.A.vm.util.converter.marshall;

import java.util.List;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.StatusRecordBean;
import com.A.xml.v4.OrderStatusHistoryType;
import com.A.xml.v4.OrderStatusWithTypeType;

/**
 * @author ebthomas
 *
 */
public final class MarshallStatus {
	/**
	 * @author ebthomas
	 *
	 */

	private static final Logger logger = Logger.getLogger(MarshallStatus.class);

	public static final class Builder {

		/**
		 * Builder for Sales Order Bean Status.
		 */
		private Builder() {
			super();
		}

		/**
		 * @param orderStatusSrc
		 *            source
		 * @return destination
		 */
		public static OrderStatusWithTypeType copyStatusRecordBean(
				final StatusRecordBean orderStatusSrc) {
			OrderStatusWithTypeType orderStatusWithTypeType = OrderStatusWithTypeType.Factory
					.newInstance();

			if ((orderStatusSrc != null)
					&& (orderStatusSrc.getReasons() != null)) {
				for (String value : orderStatusSrc.getReasons()) {
					orderStatusWithTypeType.addReason(Integer.parseInt(value));
				}

				if (orderStatusSrc.getStatus() != null) {
					orderStatusWithTypeType.setStatus(orderStatusSrc
							.getStatus());
				}

				if (orderStatusSrc.getDateTimeStamp() != null) {
					orderStatusWithTypeType.setDateTimeStamp(orderStatusSrc
							.getDateTimeStamp());
				}

				if ((orderStatusSrc != null)
						&& (orderStatusSrc.getAgentExternalId() != null)) {
					orderStatusWithTypeType.setAgentId(orderStatusSrc.getAgentExternalId());

				}

			}

			return orderStatusWithTypeType;
		}

		/**
		 * @param orderStatusSrc
		 *            source
		 * @param orderStatusWithTypeType
		 *            source
		 * @return destination
		 */
		public static OrderStatusWithTypeType copyStatusRecordBean(
				final StatusRecordBean orderStatusSrc,
				final OrderStatusWithTypeType orderStatusWithTypeType) {
			if (orderStatusSrc != null) {

				List<String> reasons = orderStatusSrc.getReasons();

				for (String reason : reasons) {
					orderStatusWithTypeType.addReason(Integer.parseInt(reason));
				}

				orderStatusWithTypeType.setStatus(orderStatusSrc.getStatus());
				orderStatusWithTypeType.setDateTimeStamp(orderStatusSrc
						.getDateTimeStamp());

				if ((orderStatusSrc != null)
						&& (orderStatusSrc.getAgentExternalId() != null)) {
					try {
						orderStatusWithTypeType.setAgentId(orderStatusSrc.getAgentExternalId());
					} catch (Exception e) {
						logger.warn(e.getMessage()
								+ " invalid order status agent id"
								+ orderStatusSrc.getId());
					}
				}

			}

			return orderStatusWithTypeType;
		}

		/**
		 * @param statusRecordBeanList
		 *            source
		 * @return destination
		 */
		public static OrderStatusHistoryType copyStatusRecordBeanList(
				final List<StatusRecordBean> statusRecordBeanList) {
			OrderStatusHistoryType historyContainer = OrderStatusHistoryType.Factory
					.newInstance();

			if (statusRecordBeanList != null) {

				for (StatusRecordBean orderStatus : statusRecordBeanList) {
					OrderStatusWithTypeType newPrevStatus = historyContainer
							.addNewPreviousStatus();
					copyStatusRecordBean(orderStatus, newPrevStatus);
				}
			}

			return historyContainer;
		}

	}
}
