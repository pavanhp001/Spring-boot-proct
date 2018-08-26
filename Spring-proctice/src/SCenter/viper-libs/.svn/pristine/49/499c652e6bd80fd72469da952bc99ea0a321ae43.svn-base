package com.A.task.context.impl;

import java.util.Map;
import java.util.Set;
import com.A.validation.Message;
import com.A.validation.ValidationReport;
import com.A.V.beans.entity.SalesOrder;

/**
 * @author ebthomas
 *
 */
public class ResponseItemOme extends ResponseItemBase implements
		OrchestrationContext {

	/**
	 * @param orderId
	 *            sales order to add
	 * @return success of add process
	 */
	public Boolean addSalesOrderRemoved(final SalesOrder  salesOrder) {
		if (salesOrder == null) {
			throw new IllegalArgumentException("NULL Sales Order");
		}

		Long orderId = salesOrder.getExternalId();
		 
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.INFO, 1L, "Removed Sales Order #"
					+ orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	/**
	 * @param salesOrder
	 * @return
	 */
	public Boolean addSalesOrderCreated(final SalesOrder  salesOrder) {
		if (salesOrder == null) {
			throw new IllegalArgumentException("NULL Sales Order");
		}

		Long orderId = salesOrder.getExternalId();

		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.INFO, 2L, "Added Sales Order #"
					+ orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	/**
	 * @param orderId
	 *            sales order to add
	 * @return success of add process
	 */
	public Boolean addSalesOrderNotFound(final String orderId) {
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.ERROR, 1L, "Sales Order #" + orderId
					+ " is not found");
			putValidationReport(report);
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	/**
	 * @param orderId
	 *            sales order to add
	 * @return success of add process
	 */
	public Boolean addOrderNotFound(final String message) {
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.ERROR, 1L, message);
			putValidationReport(report);
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	public void addInvalidCustomerResponse(final String message) {
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.FATAL, 1L, message);
			this.putValidationReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCustomerNotFound(final long externalId) {
		try {
			ValidationReport report = getValidationReport();
			report.addMessage(Message.Type.ERROR, 1L, "Customer #" + externalId
					+ " is not found");
			this.putValidationReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method to add lineitem validation messages
	 * @param validationMsg
	 */
	public void addInvalidLineItems(Map<String,String> validationMsg){
		Set<String> keys = validationMsg.keySet();
		for(String key : keys){
			String msg = validationMsg.get(key);
			try {
				ValidationReport report = getValidationReport();
				report.addMessage(Message.Type.ERROR, 1L, msg);
				this.putValidationReport(report);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to add FATAL message received from Product service
	 * @param message
	 */
	public void addInvalidProduct(String message)
	{
		ValidationReport report = getValidationReport();
		report.addMessage( Message.Type.FATAL,1L,message );
		this.putValidationReport( report );
	}

	/**
	 * Method to add invalid response message from Product service
	 * @param message
	 */
	public void invalidProductResponse(String message, String context){
		ValidationReport report = getValidationReport();
		report.addMessage( Message.Type.FATAL,1L,message );
		this.putValidationReport( report );
	}

	/**
	 * Task Response Object. Contains the response result of the task
	 */
	public ResponseItemOme() {

		super();
	}

}
