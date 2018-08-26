/**
 *
 */
package com.AL.ome.vo;

import java.util.Arrays;
import java.util.List;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 * 
 */
public class SalesOrderView {
	private Response response;
	private Request request;
	private SalesOrder responseSalesOrder;
	private SalesOrder requestSalesOrder;

	private String agentId;
	private String ALConfirmationNumber;
	private String ALCustomerAccountNumber;
	private String associatedWithMove;
	// private String directMailOptIn;
	// private String eMailOptIn;
	private String externalId;
	private String referrerId;
	private String source;
	private List<LineItemType> lineItems;

	/**
	 * @param salesOrderXmlBean
	 *            input sales order
	 */
	public SalesOrderView(
			final OrderManagementRequestResponseDocument salesOrderXmlBean) {
		request = salesOrderXmlBean.getOrderManagementRequestResponse()
				.getRequest();
		response = salesOrderXmlBean.getOrderManagementRequestResponse()
				.getResponse();

		if (response.getOrderInfoArray().length > 0) {
			OrderType responseOrder = response.getOrderInfoArray()[0];

			agentId = responseOrder.getAgentId();
			ALConfirmationNumber = responseOrder.getAgentId();
			ALCustomerAccountNumber = responseOrder
					.getALCustomerAccountNumber();
			associatedWithMove = String.valueOf(responseOrder
					.getAssociatedWithMove());
			// directMailOptIn = String.valueOf(
			// response.getNewOrderInfo().getDirectMailOptIn() );
			// eMailOptIn = String.valueOf(
			// response.getNewOrderInfo().getEMailOptIn() );
			externalId = String.valueOf(responseOrder.getExternalId());
			referrerId = responseOrder.getReferrerId();
			source = responseOrder.getSource();

			LineItemCollectionType lineItem = responseOrder.getLineItems();

			lineItems = Arrays.asList(lineItem.getLineItemArray());
		}

	}

	/**
	 * @return response
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            response
	 */
	public void setResponse(final Response response) {
		this.response = response;
	}

	/**
	 * @return request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            request
	 */
	public void setRequest(final Request request) {
		this.request = request;
	}

	public SalesOrder getResponseSalesOrder() {
		return responseSalesOrder;
	}

	public void setResponseSalesOrder(
			final SalesOrder responseSalesOrder) {
		this.responseSalesOrder = responseSalesOrder;
	}

	public SalesOrder getRequestSalesOrder() {
		return requestSalesOrder;
	}

	public void setRequestSalesOrder(
			final SalesOrder requestSalesOrder) {
		this.requestSalesOrder = requestSalesOrder;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(final String agentId) {
		this.agentId = agentId;
	}

	public String getALConfirmationNumber() {
		return ALConfirmationNumber;
	}

	public void setALConfirmationNumber(
			final String ALConfirmationNumber) {
		this.ALConfirmationNumber = ALConfirmationNumber;
	}

	public String getALCustomerAccountNumber() {
		return ALCustomerAccountNumber;
	}

	public void setALCustomerAccountNumber(
			final String ALCustomerAccountNumber) {
		this.ALCustomerAccountNumber = ALCustomerAccountNumber;
	}

	public String getAssociatedWithMove() {
		return associatedWithMove;
	}

	public void setAssociatedWithMove(final String associatedWithMove) {
		this.associatedWithMove = associatedWithMove;
	}

	/*
	 * public String getDirectMailOptIn() { return directMailOptIn; }
	 * 
	 * public void setDirectMailOptIn( final String directMailOptIn ) {
	 * this.directMailOptIn = directMailOptIn; }
	 * 
	 * public String getEMailOptIn() { return eMailOptIn; }
	 * 
	 * public void setEMailOptIn( final String mailOptIn ) { eMailOptIn =
	 * mailOptIn; }
	 */

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(final String externalId) {
		this.externalId = externalId;
	}

	public String getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(final String referrerId) {
		this.referrerId = referrerId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public List<LineItemType> getLineItems() {
		return lineItems;
	}

	public void setLineItems(final List<LineItemType> lineItems) {
		this.lineItems = lineItems;
	}

}
