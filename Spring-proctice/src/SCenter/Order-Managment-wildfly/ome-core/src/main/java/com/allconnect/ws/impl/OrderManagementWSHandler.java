package com.AL.ws.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.AL.ie.task.IeTask;
import com.AL.task.Task;
import com.AL.ws.WSHandler;
import com.AL.ws.activity.OrderManagementActivity;
import com.AL.ws.activity.RealTimeActivity;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;

/**
 * @author ebthomas
 *
 */

@Component("orderManagementWSHandler")
public final class OrderManagementWSHandler extends WSHandlerBase implements
		WSHandler<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> {
	private static Logger logger = Logger
			.getLogger(OrderManagementWSHandler.class);

	/**
	 * Private Constructor.
	 */
	public OrderManagementWSHandler() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument execute(
			final OrderManagementRequestResponseDocument doc) {

		checkNull(doc);

		try {
			OrderManagementRequestResponse omrr = doc
					.getOrderManagementRequestResponse();

			String currentTaskName = getTaskName(omrr);

			// ******************************
			// Process Order Management Tasks
			// ******************************
			Task<OrderManagementRequestResponseDocument> orderManagementCurrentTask = OrderManagementActivity.INSTANCE
					.getComponentOmeTask(currentTaskName);
			logger.trace("OrderManagementWSHandler:execute:orderManagementCurrentTask : " + currentTaskName);

			if (orderManagementCurrentTask != null) {
				OrderManagementRequestResponseDocument response = orderManagementCurrentTask
						.execute(doc);

				logger.trace("OrderManagementWSHandler:execute:response : " + response.xmlText());

				return response;
			}

			// ***********************************
			// Process Real Time Integration Tasks
			// ***********************************
			logger.debug("OME Real Time Integration task");

			IeTask<OrderManagementRequestResponseDocument> iRealTimeCurrentTask = RealTimeActivity.INSTANCE
					.getComponentOmeIE(currentTaskName);
			if (iRealTimeCurrentTask != null) {
				logger.debug("Executing task: " + currentTaskName);

				OrderManagementRequestResponseDocument response = iRealTimeCurrentTask
						.execute(doc);
				logger.trace("OME response : " + response.xmlText());

				return response;
			}

			// *********************************
			// Invalid Task. NOT FOUND
			// ********************************
			throw new IllegalArgumentException(
					"unable to execute functionality:" + currentTaskName);

		} catch (Exception e) {
			return WSExceptionHandler.INSTANCE.handleException(doc, e);
		}

	}

	private String getTaskName(final OrderManagementRequestResponse omrr) {

		String currentTaskName = "";

		logger.debug("TransactionType: "+omrr.getTransactionType());
		if (omrr != null) {
			OrderManagementRequestResponse.TransactionType.Enum tt = omrr
					.getTransactionType();
			

			if (tt == null) {

				throw new IllegalArgumentException("Invalid xml request!!!");
			}

			if (tt != null) {
				currentTaskName = tt.toString();
				logger.debug("Transaction Type : " + currentTaskName);

			}
		}

		if (currentTaskName == null) {
			throw new IllegalArgumentException("missing task name");
		}
		logger.debug("Current Task name: "+currentTaskName);
		return currentTaskName;
	}

}
