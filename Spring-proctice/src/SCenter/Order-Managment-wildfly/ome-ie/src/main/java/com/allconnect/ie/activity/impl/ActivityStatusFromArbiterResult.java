package com.AL.ie.activity.impl;

import org.apache.log4j.Logger;

import com.AL.enums.LineItemStatus;
import com.AL.enums.OrderStatus;
import com.AL.ie.factory.ArbiterStatusFactory;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.xml.v4.ProviderLineItemStatusType;

public enum ActivityStatusFromArbiterResult {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(ActivityStatusFromArbiterResult.class);

	public StatusRecordBean resolveLineItemStatus(
			ProviderLineItemStatusType status, final OrchestrationContext params, final User agent) {

		logger.info("Resolving lineitem status from providerLineItemStatusType : " + status.getLineItemStatusCode()
				.toString());

		StatusRecordBean priorStatusRecordBean = (StatusRecordBean) params
				.get(TaskContextParamEnum.priorStatus.name());

		logger.info("Previous LineItemStatus is : " + priorStatusRecordBean.getStatus());

		if ((status != null) && (status.getLineItemStatusCode() != null)
				&& (status.getLineItemStatusCode().toString() != null)) {

			String lineItemStatusCode = status.getLineItemStatusCode().toString();
			logger.info("Evaluating lineitem status based on provider status : " + lineItemStatusCode);

			if (("provision_ready".equalsIgnoreCase(lineItemStatusCode)) || ("order_submitted".equalsIgnoreCase(lineItemStatusCode))) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.provision_ready.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.provision_ready);

			}else if ("processing_schedule_pending".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.processing_schedule_pending.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.processing_schedule_pending);

			} else if ("processing_schedule_confirmed"
					.equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.processing_schedule_confirmed.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.processing_schedule_confirmed);

			} else if ("processing_connected".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.processing_connected.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.processing_connected);

			} else if ("processing_disconnected".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.processing_disconnected.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.processing_disconnected);

			} else if ("processing_cancelled".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.processing_cancelled.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.processing_cancelled);

			} else if ("hold_order_pending_problem".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.hold_order_pending_problem.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.hold_order_pending_problem);

			} else if ("hold_authorization_pending".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.hold_authorization_pending.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.hold_authorization_pending);

			} else if ("hold_provider".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.hold_provider.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.hold_provider);

			} else if ("order_info_required".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.submit_failed.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.submit_failed);

			} else if ("order_failed".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.submit_failed.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.submit_failed);

			} else if ("failed".equalsIgnoreCase(lineItemStatusCode)) {

				logger.info("Resolved LineItemStatus is : " + LineItemStatus.submit_failed.name() );
				return ArbiterStatusFactory.INSTANCE.create(agent,LineItemStatus.submit_failed);
			}

		} else if (priorStatusRecordBean != null) {
			logger.info("Returning previous lineitem status :" + priorStatusRecordBean.getStatus());
			priorStatusRecordBean.setId(0);
			return priorStatusRecordBean;

		}
		logger.info("Returning LineItemStatus : sales");
		return ArbiterStatusFactory.INSTANCE
				.create(agent,OrderStatus.sales);

	}
}
