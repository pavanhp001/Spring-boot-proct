package com.AL.ie.task;

 
import java.util.List;

import org.apache.log4j.Logger;

import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.OrchestrationParamName;
import com.AL.validation.Message;
import com.AL.validation.ValidationReport;
import com.AL.validation.impl.DefaultValidationReport;
import com.AL.validation.impl.OmeValidationHelper;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.StatusType;
 

/**
 * @author ebthomas
 * 
 * @param <T>
 *            Request
 */

public abstract class IeTaskBase<T> implements IeTask<T> {

	Logger logger = Logger.getLogger(IeTaskBase.class);
	public boolean isUpdateRequest = Boolean.FALSE;
	
//	@Autowired
//	private MarshallOrder<SalesOrderBean> marshallOrder;
//	
 

	/**
	 * @param orderDocument
	 *            request element
	 * @return response element
	 */
	public abstract OrchestrationContext processRequest(T orderDocument);

	/**
	 * The main action that will be performed.
	 * 
	 * @param requestResult
	 *            request element
	 * @return response element
	 */
	public abstract OrchestrationContext processAgenda(
			OrchestrationContext requestResult);

	/**
	 * @param requestResult
	 *            input request
	 * @return Response
	 */
	public abstract T processResponse(OrchestrationContext requestResult);

	/**
	 * @param e
	 *            Exception
	 * @param taskResponse
	 *            response
	 * @param orderDocument
	 *            input document
	 * @return Response
	 */
	public abstract T handleFault(Exception e,
			OrchestrationContext taskResponse, T orderDocument);

	/**
	 * @param strToBroadcastOriginal
	 *            message to broadcast
	 */
	public abstract void broadcast(final String strToBroadcastOriginal);

	private String result;

	 

	/**
	 * {@inheritDoc}
	 */
	public T execute(final T xmlDocument) {
		logger.debug(  "starting task process");
		OrchestrationContext parameterContainer = null;
		T response = null;

		try {
			logger.debug(  "start processing request");
			parameterContainer = processRequest(xmlDocument);
			logger.debug(  "completed processing request");

			if (!parameterContainer.getValidationReport().hasFatal()) {
				logger.debug(  "start processing agenda");
				parameterContainer = processAgenda(parameterContainer);
				logger.debug(  "completed processing agenda");
			} else {
				logger.debug(  "error processing request bypassing agenda");
			}

			logger.debug(  "start preparing response");
			response = processResponse(parameterContainer);
			logger.debug(  "completed preparing response");

		} catch (Exception e) {
			e.printStackTrace();
			 logger.error(e);
			addExceptionError(parameterContainer, e.getClass()
					.getCanonicalName() + e.getMessage(), e);

			try {
				if (response == null) {
					logger.debug(  "building response");
					response = processResponse(parameterContainer);
				}

				if ((response != null)
						&& (!parameterContainer.getValidationReport()
								.hasMessages(Message.Type.FATAL))) {
					logger.debug(  "broadcasting response ");
					broadcast(response.toString());
					logger.debug(  "broadcast of response complete.");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				logger.error(  "unable to build response:" + e2.getMessage());
				
			}

			response = handleFault(e, parameterContainer, response);
		}

		updateProcessingStatus(parameterContainer, response);
		broadcast(response.toString());
		
		logger.debug(  "task completed");

		return response;
	}

	/**
	 * @param parameterContainer
	 *            parameter container
	 * @param message
	 *            message
	 * @param e
	 *            Exception
	 */
	protected void addExceptionError(
			final OrchestrationContext parameterContainer, final String message,
			final Exception e) {
		if (parameterContainer == null) {
			
			throw new IllegalArgumentException( 
					"unable.to.build.proper.exception."+e.getMessage(),e);
		}
		ValidationReport report = (ValidationReport) parameterContainer
				.get(OrchestrationParamName.statusReport.name());

		if (report == null) {
			report = new DefaultValidationReport();
		}

		parameterContainer.add(OrchestrationParamName.statusReport.name(),
				report);
		report.addMessage(Message.Type.FATAL,
				Long.valueOf(Message.Type.FATAL.getCode()), message, e);

	}

	/**
	 * @param parameterContainer
	 *            Parameter Container
	 * @param xmlDocument
	 *            XML Document
	 */
	private void updateProcessingStatus(
			final OrchestrationContext parameterContainer, final T xmlDocument) {

		if ((parameterContainer == null) || (xmlDocument == null)) {
			throw new IllegalArgumentException(" parameterContainer:"
					+ parameterContainer + " xmlDocument:" + xmlDocument);
		}

		ValidationReport report = (ValidationReport) parameterContainer
				.get(OrchestrationParamName.statusReport.name());

		if (report == null) {
			report = new DefaultValidationReport();
		}

		// Add a default message indicating SUCCESS or FAILURE
		if (!report.hasMessages()) {
			parameterContainer.add(
					OrchestrationParamName.statusReport.name(), report);
			report.addMessage(Message.Type.INFO,
					Long.valueOf(Message.Type.INFO.getCode()),
					"request completed");
		}

		// Use message PRIORITY to determine status of task based on message
		// types [INFO, FATAL, etc..]
		StatusType status = OmeValidationHelper.determineStatus(xmlDocument,
				report);

		// Update report with processing status
		if (status != null) {
			OmeValidationHelper.populateProcessingStatus(status, report);
		}

		if (xmlDocument != null) {
			logger.debug(   xmlDocument.toString());
		}

	}

	/**
	 * @param taskResponse
	 *            Load Request information to response
	 * @param container
	 *            Response Object
	 */
	public void loadRequestToResponse(
			final OrderManagementRequestResponseDocument container,
			final OrchestrationContext taskResponse) {
		Request request = (Request) taskResponse.get(OrchestrationParamName.request
				.name());
		String orderId = (String) taskResponse.get(TaskContextParamEnum.salesOrderId
				.name());

		if (request != null) {
			if (orderId != null) {
				request.setOrderId(orderId);
			}

			container.getOrderManagementRequestResponse().setRequest(request);
		}

	}
	
	
	protected void loadSalesOrderToResponse(OrderManagementRequestResponse requestResponse, List<SalesOrder> salesOrderBeanList)
	{
		
      
	}

	/**
	 * @return getter
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            setter
	 */
	public void setResult(final String result) {
		this.result = result;
	}

	/**
	 * @param bean
	 *            SalesOrderBean weakly typed
	 * @return Strongly typed version of SalesOrderBean
	 */
	public SalesOrder getSalesOrderBean(final Object bean) {
		try {
			return (SalesOrder) bean;
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			return null;
		}

	}
	 

	/**
	 * @param externalId
	 *            Id of item cast to Long
	 * @return Long value of externalId
	 */
	public Long getSalesOrderId(final Object externalId) {
		try {
			return (Long) externalId;
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			return null;
		}

	} 
	  
	public boolean isUpdateRequest() {
		return isUpdateRequest;
	}

	public void setUpdateRequest(boolean isUpdateRequest) {
		this.isUpdateRequest = isUpdateRequest;
	}
 
	
	protected OrchestrationContext createLoadContext(final OrderManagementRequestResponseDocument orderDocument) {
    	
    	OrchestrationContext params = OrchestrationContext.Factory.createOme();
    	 
        params.add("correlationId", orderDocument.getOrderManagementRequestResponse().getCorrelationId());
        params.add("transactionId", orderDocument.getOrderManagementRequestResponse().getTransactionId());
        params.add("timeStamp", orderDocument.getOrderManagementRequestResponse().getTransactionTimeStamp());
        
        return params;
        
    }

}
