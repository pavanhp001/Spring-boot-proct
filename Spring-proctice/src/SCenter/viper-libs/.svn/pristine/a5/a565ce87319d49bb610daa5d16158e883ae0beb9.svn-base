package com.A.factory;

import java.util.Calendar;
import org.apache.log4j.Logger;
import com.A.xml.common.RtimError;
import com.A.xml.v4.ProviderLineItemStatusType;
import com.A.xml.v4.ProviderLineItemStatusType.LineItemStatusCode;
import com.A.xml.v4.ProviderLineItemStatusType.ProcessingStatusCode;
import com.A.xml.v4.TransientResponseType;

public enum ProviderLineItemStatusFactory {

	INSTANCE;

	private static Logger logger = Logger
			.getLogger(ProviderLineItemStatusFactory.class);

	public boolean hasErrorStatus(TransientResponseType tResponseType) {
		return ((tResponseType.getProviderLineItemStatus() != null) && (tResponseType
				.getProviderLineItemStatus().getLineItemStatusCode()
				.equals(ProcessingStatusCode.ERROR)));
	}

	public boolean hasInfoStatus(TransientResponseType tResponseType) {
		return ((tResponseType.getProviderLineItemStatus() != null) && (tResponseType
				.getProviderLineItemStatus().getLineItemStatusCode()
				.equals(ProcessingStatusCode.INFO)));
	}

	public boolean hasProviderLineItemStatusType(
			TransientResponseType tResponseType) {
		return (tResponseType.getProviderLineItemStatus() != null);
	}

	public ProviderLineItemStatusType createSystemDown(
			TransientResponseType tResponseType) {

		if ((tResponseType != null)
				&& (tResponseType.getProviderLineItemStatus() == null)) {

			ProviderLineItemStatusType pLineItemStatusType = tResponseType
					.addNewProviderLineItemStatus();
			pLineItemStatusType.setDateTimeStamp(Calendar.getInstance());
			pLineItemStatusType
					.setLineItemStatusCode(LineItemStatusCode.ORDER_FAILED);
			String[] reasons = { "unable to get valid response from provider.  possible provider SYSTEM down" };
			pLineItemStatusType.setReasonArray(reasons);
			pLineItemStatusType
					.setProcessingStatusCode(ProcessingStatusCode.ERROR);
			logger.debug("invalid instance type.  creating empty transient container with error");
		}

		return tResponseType.getProviderLineItemStatus();

	}

	public ProviderLineItemStatusType createSalesNewOrder(
			TransientResponseType tResponseType) {

		if ((tResponseType != null)
				&& (tResponseType.getProviderLineItemStatus() == null)) {

			tResponseType.addNewProviderLineItemStatus();

		}

		ProviderLineItemStatusType status = tResponseType
				.getProviderLineItemStatus();

		status.setDateTimeStamp(Calendar.getInstance());
		status.setProcessingStatusCode(ProviderLineItemStatusType.ProcessingStatusCode.INFO);
		status.setLineItemStatusCode(ProviderLineItemStatusType.LineItemStatusCode.SALES_NEW_ORDER);
		String[] reasons = { "processing completed",
				"no transient response from provider" };
		status.setReasonArray(reasons);

		return status;
	}

	public ProviderLineItemStatusType createRtimError(
			TransientResponseType tResponseType, RtimError error) {

		if ((tResponseType != null)
				&& (tResponseType.getProviderLineItemStatus() == null)) {

			tResponseType.addNewProviderLineItemStatus();

		}

		ProviderLineItemStatusType status = tResponseType
				.getProviderLineItemStatus();

		status.setProcessingStatusCode(ProcessingStatusCode.ERROR);
		status.setDateTimeStamp(Calendar.getInstance());
		String errorMsg = " code:" + error.getErrorCode() + //
				" type:" + error.getErrorType() + //
				" msg:" + error.getErrorMessage();//
		status.setLineItemStatusCode(ProviderLineItemStatusType.LineItemStatusCode.FAILED);
		status.addReason(errorMsg);

		return status;
	}
}
