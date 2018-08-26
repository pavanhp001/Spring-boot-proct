package com.AL.activity.impl;

import java.util.List;
import java.util.Set;

import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SalesOrderContext;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.SalesContextEntityType;

public enum ActivitySalesContextEvaluate {

	INSTANCE;
	 
	private static final String HARMONY = "harmony";
	private static final String ACCORD = "accord";
	private static final String SOURCE = "source";
	private static final String CKO = "CKO";
	private static final String GUID = "GUID";
	private static final String NULL_STRING = null;
	
	private static final String SYP = "SYP";
	private static final String FA = "fa";
	private static final String UPDATEDBY = "updatedBy";

	public String getGUID(final OrderManagementRequestResponseDocument orderDoc) {

		String guid = getValue(orderDoc, CKO, GUID);

		if ((guid == null)
				&& (orderDoc.getOrderManagementRequestResponse() != null)) {
			guid = orderDoc.getOrderManagementRequestResponse()
					.getCorrelationId();
		}

		return guid;
	}

	public String getValue(
			final OrderManagementRequestResponseDocument orderDoc,
			String entity, String name) {

		if ((orderDoc == null)
				|| (orderDoc.getOrderManagementRequestResponse() == null)
				|| (orderDoc.getOrderManagementRequestResponse().getRequest() == null)) {
			return NULL_STRING;
		}

		Request orderReq = orderDoc.getOrderManagementRequestResponse()
				.getRequest();

		if ((orderReq != null) && (orderReq.getSalesContext() != null)) {
			List<SalesContextEntityType> salesOrderContextList = orderReq
					.getSalesContext().getEntityList();

			if ((salesOrderContextList == null)
					|| (salesOrderContextList.size() == 0)) {
				return NULL_STRING;
			}

			for (SalesContextEntityType context : salesOrderContextList) {

				if ((context != null) && (entity.equals(context.getName()))) {
					for (NameValuePairType nvpt : context.getAttributeList()) {
						if (name.equals(nvpt.getName())) {
							return nvpt.getValue();
						}
					}

				}
			}

		}

		return NULL_STRING;

	}

	public String getGUID(final SalesOrder so) {

		if (so == null) {
			return NULL_STRING;
		}

		Set<SalesOrderContext> salesOrderContextList = so
				.getSalesOrderContexts();

		if ((salesOrderContextList == null)
				|| (salesOrderContextList.size() == 0)) {
			return NULL_STRING;
		}

		for (SalesOrderContext context : salesOrderContextList) {

			if ((context != null) && (CKO.equals(context.getEntityName()))

			&& (GUID.equals(context.getName()))) {
				return context.getValue();
			}
		}

		return NULL_STRING;

	}

	
	/**
	 * Fix - PR-30: When there is a submit call from Encore and do the following pricing calculation
	 * 
	 * @param so
	 * @return TRUE/FALSE
	 */
	public boolean isCallFromEncore(final SalesOrder so) {

		if (so == null) {
			return Boolean.FALSE;
		}

		Set<SalesOrderContext> salesOrderContextList = so
				.getSalesOrderContexts();

		if ((salesOrderContextList == null)
				|| (salesOrderContextList.size() == 0)) {
			return Boolean.FALSE;
		}

		for (SalesOrderContext context : salesOrderContextList) {

			if ((context != null) && (SYP.equals(context.getEntityName()))
					&& (FA.equals(context.getValue()))
					&& (UPDATEDBY.equals(context.getName()))) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;

	}
}
