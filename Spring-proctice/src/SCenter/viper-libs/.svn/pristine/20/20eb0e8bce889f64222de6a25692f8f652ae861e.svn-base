package com.A.validation.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.A.validation.Message;
import com.A.validation.ValidationReport;
import com.A.V.utility.Status;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.PricingRequestResponseDocument;
import com.A.xml.v4.ProcessingMessage;
import com.A.xml.v4.StatusType;
import com.A.xml.v4.StatusType.ProcessingMessages;

public class PricingValidationHelper {

	private static final Logger logger = Logger.getLogger(PricingValidationHelper.class);

	private static final String FATAL_PREFIX = "FATAL";
	private static final String ERROR_PREFIX = "ERROR";

	/**
	 * Helper method to check pricing response had any fatal error
	 *
	 * @param pricingDoc
	 * @return
	 */
	public static boolean isFatalErrorExist(
			PricingRequestResponseDocument pricingDoc) {
		
		logger.info("Validating Pricing service response");
		Boolean exist = Boolean.FALSE;
		if ((pricingDoc != null)
				&& (pricingDoc.getPricingRequestResponse() != null)) {
			StatusType status = pricingDoc.getPricingRequestResponse()
					.getStatus();
			if ((status != null) && (status.getStatusMsg() != null)) {
				String msg = status.getStatusMsg();

				exist = (msg.startsWith(FATAL_PREFIX) || msg
						.startsWith(ERROR_PREFIX));

			}
		}
		return exist;
	}

	/**
	 * Helper method to check pricing response had any warning error
	 *
	 * @param pricingDoc
	 * @return
	 */
	public static boolean isWarningErrorExist(
			PricingRequestResponseDocument pricingDoc) {
		Boolean exist = Boolean.FALSE;
		if ((pricingDoc != null)
				&& (pricingDoc.getPricingRequestResponse() != null)) {
			StatusType status = pricingDoc.getPricingRequestResponse()
					.getStatus();
			if (status != null) {
				String msg = status.getStatusMsg();
				if (Status.Pricing.WARN_ORDER_NONE_PRICED.name()
						.equalsIgnoreCase(msg.trim())
						|| Status.Pricing.WARN_ORDER_PARTIALLY_PRICED.name()
								.equalsIgnoreCase(msg.trim())) {
					exist = Boolean.TRUE;
				}
			}
		}
		return exist;
	}

	/**
	 * Helper method to retrieve Status from Pricing response, which is being
	 * used in preparing current status for order.
	 *
	 * @param pricingDoc
	 * @return
	 */
	public static String getPricingStatus(
			PricingRequestResponseDocument pricingDoc) {
		String statusMsg = "";
		if ((pricingDoc != null)
				&& (pricingDoc.getPricingRequestResponse() != null)) {
			StatusType status = pricingDoc.getPricingRequestResponse()
					.getStatus();
			if (status != null) {
				statusMsg = status.getStatusMsg();
			}
		}
		return statusMsg;
	}

	/**
	 * Helper method to populate pricing status message from Pricing response to
	 * validation report so that OME response would display those message.
	 *
	 * @param pricingDoc
	 * @param validationReport
	 */
	public static void populateStatusMsg(
			PricingRequestResponseDocument pricingDoc,
			ValidationReport validationReport) {
		if ((pricingDoc != null)
				&& (pricingDoc.getPricingRequestResponse() != null)) {
			StatusType status = pricingDoc.getPricingRequestResponse()
					.getStatus();
			if (status != null) {
				populatedMessages(validationReport, status);
			}
		}

		// Populate LI item specific status
		populateLIPricingStatus(pricingDoc, validationReport);
	}

	private static void populatedMessages(ValidationReport validationReport,
			StatusType status) {

		if ((status == null) ||
			(status.getProcessingMessages() == null) ||
			(status.getStatusMsg() == null)) {
			return;
		}

		ProcessingMessages messages = status.getProcessingMessages();
		String pricingStatusMsg = status.getStatusMsg();




		if (((!pricingStatusMsg
				.equalsIgnoreCase("INFO_ORDER_PRICED_SUCCESSFULLY")) || (!pricingStatusMsg
				.equalsIgnoreCase("WARN_ORDER_NONE_PRICED")))
				&& messages != null) {
			for (ProcessingMessage msg : messages.getMessageList()) {
				if (!msg.isNil()) {
					Double code = msg.getCode();
					validationReport.addMessage(Message.Type.FATAL,
							code.longValue(), msg.getText(), "");
				}
			}
		}
	}

	public static void populateLIPricingStatus(
			PricingRequestResponseDocument pricingDoc,
			ValidationReport validationReport) {
		if (pricingDoc != null
				&& pricingDoc.getPricingRequestResponse() != null
				&& pricingDoc.getPricingRequestResponse().getResponse() != null) {
			List<OrderType> orderList = pricingDoc.getPricingRequestResponse()
					.getResponse().getPricingResponseOrderElementList();
			if (orderList != null && !orderList.isEmpty()) {
				OrderType orderType = orderList.get(0);
				List<LineItemType> liList = orderType.getLineItems()
						.getLineItemList();
				if (liList != null && !liList.isEmpty()) {
					for (LineItemType liType : liList) {
						StatusType piStatus = liType.getLineItemPriceInfo()
								.getPriceInfoStatus();
						if (piStatus != null) {
							populatedMessages(validationReport, piStatus);
						}
					}
				}
			}
		}
	}

}
