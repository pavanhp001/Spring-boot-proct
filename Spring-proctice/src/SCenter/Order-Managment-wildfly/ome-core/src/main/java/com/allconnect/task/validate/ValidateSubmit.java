package com.AL.task.validate;

import org.apache.log4j.Logger;

import com.AL.task.context.impl.OrchestrationContext;
import com.AL.factory.StatusFactory;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.vo.OrderChangeValueObject;

public enum ValidateSubmit {

	INSTANCE;

	private static final String SALES_ORDER_MISSING = "sales order missing";
	private static final String AGENT_ID_MISSING = "agent Id missing";
	private static final String ORDER_CHANGE_VALUE_OBJECT_MISSING = "orderChangeValueObject missing";
	private static final String MISSING_OBJECT_ID = "orderChangeValueObject missing";

	private static final Logger logger = Logger.getLogger(ValidateSubmit.class);

	public boolean validateCurrentStatusIsSubmitted(final OrchestrationContext params ,
			SalesOrder salesOrder ) {

		if ((salesOrder == null) || (salesOrder.getCurrentStatus() == null)) {
			logger.error("unable to find sales order in the context:");
			return Boolean.FALSE;
		}

		if (params.getValidationReport().hasErrors()) {
			logger.debug("errors exist.  unable to submit order:"
					+ salesOrder.getExternalId());
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}

	public boolean validate(final OrderChangeValueObject changeValue) {

		if (changeValue == null) {
			throw new IllegalArgumentException(
					ORDER_CHANGE_VALUE_OBJECT_MISSING);
		}

		return Boolean.TRUE;

	}

	public boolean validate( final OrderManagementDao orderManagementDao,
			final OrderChangeValueObject orderChangeValueObject,
			SalesOrder salesOrder, String agentId) {
		logger.info("Validate submit process");

		if (salesOrder == null) {
			throw new IllegalArgumentException(SALES_ORDER_MISSING);
		}

		if (agentId == null) {
			throw new IllegalArgumentException(AGENT_ID_MISSING);
		}

		if (orderChangeValueObject == null) {
			throw new IllegalArgumentException(
					ORDER_CHANGE_VALUE_OBJECT_MISSING);
		}

		if (orderChangeValueObject.getOrderId() == null) {
			throw new IllegalArgumentException(MISSING_OBJECT_ID);
		}

		ValidateSubmit.INSTANCE.ensureCurrentStatusNotNull(salesOrder, agentId,
				orderManagementDao);

		return Boolean.TRUE;

	}

	public void ensureCurrentStatusNotNull(final SalesOrder salesOrder,
			final String agentId, final OrderManagementDao orderManagementDao) {

		logger.debug("Validating current status");
		if (salesOrder.getCurrentStatus() == null) {

			StatusRecordBean statusRecordBean = StatusFactory
					.createNewSalesStatus(salesOrder, agentId);

			StatusFactory.INSTANCE.persistCurrentStatusRecord(
					orderManagementDao, salesOrder, statusRecordBean);
		}

	}
}
