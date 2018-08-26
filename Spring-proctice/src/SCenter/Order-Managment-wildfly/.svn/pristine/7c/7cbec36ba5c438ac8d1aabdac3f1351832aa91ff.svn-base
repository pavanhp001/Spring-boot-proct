package com.AL.task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.AL.comm.manager.jms.receiver.Executable;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.util.SalesContextUtil;
import com.AL.util.converter.mask.SecurityMask;
import com.AL.validation.Message;
import com.AL.validation.ValidationReport;
import com.AL.validation.impl.DefaultValidationReport;
import com.AL.validation.impl.OmeValidationHelper;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.AuditDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.PagingDetail;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedDialogsType.Dialogs;
import com.AL.xml.v4.StatusType;

/**
 * @author ebthomas
 *
 * @param <T>
 *            Request
 */

public abstract class TaskBase<T> implements Task<T> {

	Logger logger = Logger.getLogger(TaskBase.class);
	public static final String CUSTOMERSERVICE_FATAL_ERROR = "Customer Management service failed with FATAL error";

	private final int START_POSITION = 0;
	protected int TOTAL_ROWS = 50;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private OrderManagementDao orderDao;

	@Autowired
	private AuditDao<OrderAudit> auditDao;

	/**
	 * @param orderDocument
	 *            request element
	 * @return response element
	 */
	public abstract OrchestrationContext processRequest(T orderDocument);

	/**
	 * @param domain
	 *            object
	 *
	 * @return OrchestrationContext
	 */
	public OrchestrationContext processValidation(OrchestrationContext params,
			SalesOrder salesOrder) {
		return params;
	}

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
	public abstract void broadcast(final String strToBroadcastOriginal,
			final Map<String, String> header);

	private String result;

	/**
	 * {@inheritDoc}
	 */
	public T execute(final T xmlDocument) {
		logger.info("starting task process");
		OrchestrationContext parameterContainer = null;
		T response = null;

		try {
			logger.debug("start processing request");
			parameterContainer = processRequest(xmlDocument);
			logger.debug("completed processing request");

			logger.debug("start processing validation");
			SalesOrder salesOrder = (SalesOrder) parameterContainer
					.get(TaskContextParamEnum.salesOrder.name());
			if (salesOrder != null) {
				parameterContainer = processValidation(parameterContainer,
						salesOrder);
			} else {
				logger.debug("Validation on sales order skipped as it is not found in context.");
			}
			logger.debug("completed processing validation");

			if (!parameterContainer.getValidationReport().hasFatal()) {
				logger.debug("start processing agenda");
				parameterContainer = processAgenda(parameterContainer);
				logger.debug("completed processing agenda");
			} else {

				Set<Message> messages = parameterContainer
						.getValidationReport().getMessages();

				for (Message msg : messages) {
					logger.error(msg.toString());
				}

				logger.error("error processing request bypassing agenda");
			}

			logger.debug("start preparing.response ....");
			response = processResponse(parameterContainer);
			logger.debug("completed preparing response");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			addExceptionError(parameterContainer, e.getClass()
					.getCanonicalName() + e.getMessage(), e);

			try {
				if (response == null) {
					logger.debug("building response");
					response = processResponse(parameterContainer);
				}

				if ((response != null)
						&& (!parameterContainer.getValidationReport()
								.hasMessages(Message.Type.ERROR))
						&& (!parameterContainer.getValidationReport()
								.hasMessages(Message.Type.SYSTEM))
						&& (!parameterContainer.getValidationReport()
								.hasMessages(Message.Type.FATAL))) {
					logger.debug("broadcasting response");
					broadcast(response.toString(), null);
					logger.debug("broadcast of response complete");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				logger.debug("unable.to.build.response." + e2.getMessage());

			}

			response = handleFault(e, parameterContainer, response);
		}

		updateProcessingStatus(parameterContainer, response);

		if ((response != null)
				&& (!parameterContainer.getValidationReport().hasMessages(
						Message.Type.ERROR))
				&& (!parameterContainer.getValidationReport().hasMessages(
						Message.Type.SYSTEM))
				&& (!parameterContainer.getValidationReport().hasMessages(
						Message.Type.FATAL))) {
			logger.debug("broadcasting response");
			broadcastProcessing(parameterContainer, response);
			logger.debug("broadcast of response complete");
		}else{
			logger.warn("Response contains error meesage. Ignored broadcasting message!!!");
		}

		logger.info("task.completed");

		return response;
	}

	@SuppressWarnings("unchecked")
	private void broadcastProcessing(
			final OrchestrationContext parameterContainer, final T response) {
		logger.debug("getting broadcast header info from context");
		boolean isSessionStatusCompleted = false;
		if(parameterContainer.get(SalesContextUtil.SESSION_STATUS_KEY) != null) {
			isSessionStatusCompleted = (Boolean)parameterContainer.get(SalesContextUtil.SESSION_STATUS_KEY); 
		}
		Object headerInfo = parameterContainer
				.get(TaskContextParamEnum.headerInfo.name());

		Map<String, String> mapNameValue = null;
		if ((headerInfo != null) && (headerInfo instanceof Map)) {
			mapNameValue = (Map<String, String>) headerInfo;
			mapNameValue.put(Executable.MSG_TYPE_KEY,
					"OrderManagementRequestResponseDocument");
			mapNameValue.put(Executable.MSG_VERSION_KEY, Executable.MSG_VER_V4);

		}

		if (response != null) {
			if (response instanceof OrderManagementRequestResponseDocument) {

				boolean fatalErrorExist = isFatalErrorExist(response);
				if (!fatalErrorExist) {
					OrderManagementRequestResponseDocument copyMaskedVersionOfOrder = copyAndMask((OrderManagementRequestResponseDocument) response, isSessionStatusCompleted);
					broadcast(copyMaskedVersionOfOrder.toString(), mapNameValue);
				} else {
					logger.warn("Skipping broadcast as response contains fatal error");
				}

			}

		}
	}

	public OrderManagementRequestResponseDocument copyAndMask(
			OrderManagementRequestResponseDocument order, boolean includeAccountHolders) {
		OrderManagementRequestResponseDocument copyOfOrderDocument = (OrderManagementRequestResponseDocument) order
				.copy();

		if ((copyOfOrderDocument != null)
				&& (copyOfOrderDocument.getOrderManagementRequestResponse() != null)
				&& (copyOfOrderDocument.getOrderManagementRequestResponse()
						.getRequest() != null)
				&& (copyOfOrderDocument.getOrderManagementRequestResponse()
						.getRequest().getOrderInfo() != null)) {

			// Request Information
			OrderType requestOrder = copyOfOrderDocument
					.getOrderManagementRequestResponse().getRequest()
					.getOrderInfo();

			doMask(requestOrder, includeAccountHolders);

		}

		if ((copyOfOrderDocument != null)
				&& (copyOfOrderDocument.getOrderManagementRequestResponse() != null)
				&& (copyOfOrderDocument.getOrderManagementRequestResponse()
						.getResponse() != null)
				&& (copyOfOrderDocument.getOrderManagementRequestResponse()
						.getResponse().getOrderInfoList() != null)) {

			// Response Information
			for (OrderType responseOrder : copyOfOrderDocument
					.getOrderManagementRequestResponse().getResponse()
					.getOrderInfoList()) {
				doMask(responseOrder, includeAccountHolders);

			}

		}
		return copyOfOrderDocument;
	}

	private void doMask(OrderType order, boolean includeAccountHolders) {
		if ((order != null) && (order.getCustomerInformation() != null)
				&& (order.getCustomerInformation().getCustomer() != null)) {
			SecurityMask.INSTANCE.execute(order.getCustomerInformation()
					.getCustomer());

			//Mask LineItem Detail Customer Information
			if ((order.getLineItems() != null) && (order.getLineItems().getLineItemList() != null)) {
				List<LineItemType> lit = order.getLineItems().getLineItemList();
				for (LineItemType lineItem:lit) {

					if (lineItem.getCustomer() != null) {
						SecurityMask.INSTANCE.execute(lineItem.getCustomer());
					}

					if(lineItem.getActiveDialogs() != null) {
					    SelectedDialogsType selDialogsType = lineItem.getActiveDialogs();
					    if(selDialogsType.getDialogs() != null) {
						Dialogs dlgs = selDialogsType.getDialogs();
						if(dlgs.getDialogList()!= null) {
						    List<DialogValueType> dlgList = dlgs.getDialogList();
						    for(DialogValueType dvType : dlgList) {
							SecurityMask.INSTANCE.execute(dvType);
						    }
						}
					    }
					}
				}
			}
			
			if(includeAccountHolders && !order.getAccountHolderList().isEmpty()) {
				logger.debug("Adding accountHolder to the boardcast message.");
				SecurityMask.INSTANCE.execute(order.getAccountHolderList());
			} else if(!includeAccountHolders && !order.getAccountHolderList().isEmpty()) {
				logger.debug("Removing accountHolder from the boardcast message.");
				order.getAccountHolderList().clear();
			}
		}
	}

	/**
	 * A method to check fatal error message in response.
	 *
	 * @param response
	 * @return
	 */
	private boolean isFatalErrorExist(T response) {

		OrderManagementRequestResponseDocument orderDoc = (OrderManagementRequestResponseDocument) response;

		if (orderDoc.getOrderManagementRequestResponse() != null
				&& orderDoc.getOrderManagementRequestResponse().getStatus() != null) {
			StatusType status = orderDoc.getOrderManagementRequestResponse()
					.getStatus();
			String msg = status.getStatusMsg() == null ? "" : status
					.getStatusMsg();
			if (msg.equalsIgnoreCase("FATAL") || msg.equalsIgnoreCase("ERROR")
					|| msg.equalsIgnoreCase("SYSTEM")) {

				return true;
			}
		}
		return false;
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
			final OrchestrationContext parameterContainer,
			final String message, final Exception e) {
		if (parameterContainer == null) {

			throw new IllegalArgumentException(
					"unable.to.build.proper.exception." + e.getMessage(), e);
		}
		ValidationReport report = (ValidationReport) parameterContainer
				.get(TaskContextParamEnum.statusReport.name());

		if (report == null) {
			report = new DefaultValidationReport();
		}

		parameterContainer
				.add(TaskContextParamEnum.statusReport.name(), report);
		report.addMessage(Message.Type.FATAL, Long.valueOf(Message.Type.FATAL
				.getCode()), getRootcause(e), e);

	}

	/**
	 * Helper method to find the root cause exception and then use it to add
	 * proper message in the status
	 *
	 * @param th
	 * @return
	 */
	public static Throwable unwindException(Throwable th) {
		if (th instanceof SQLException) {
			SQLException sql = (SQLException) th;
			if (sql.getNextException() != null) {
				return unwindException(sql.getNextException());
			}
		} else if (th.getCause() != null) {
			return unwindException(th.getCause());
		}

		return th;
	}

	public static String getRootcause(Throwable t) {
	    String rootCause = "";
	    if(t != null) {
		rootCause = ExceptionUtils.getRootCauseMessage(t);
	    }
	    return rootCause;
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
				.get(TaskContextParamEnum.statusReport.name());

		if (report == null) {
			report = new DefaultValidationReport();
		}

		// Add a default message indicating SUCCESS or FAILURE
		if (!report.hasMessages()) {
			parameterContainer.add(TaskContextParamEnum.statusReport.name(),
					report);
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
		Request request = (Request) taskResponse
				.get(TaskContextParamEnum.request.name());

		Long orderId = (Long) taskResponse
				.get(TaskContextParamEnum.salesOrderId.name());

		if (request != null) {
			if (orderId != null) {
				request.setOrderId(String.valueOf(orderId.longValue()));
			}

			// If properties is set in db to true then only add req to response
			// xml
			Boolean addRequestToRes = SystemPropertiesRepo
					.getSystemPropertyValueAsBoolean("OME.add.req.to.response");
			if (addRequestToRes) {
				container.getOrderManagementRequestResponse().setRequest(
						request);
			}
		}

	}

	/*
	 * protected void loadSalesOrderToResponse(OrderManagementRequestResponse
	 * requestResponse, List<SalesOrderBean> salesOrderBeanList) {
	 *
	 * Response response = requestResponse.addNewResponse();
	 *
	 * if ( salesOrderBeanList != null ) { for ( SalesOrderBean salesOrderBean :
	 * salesOrderBeanList ) {
	 *
	 * OrderType orderType = requestResponse.getResponse().addNewOrderInfo();
	 *
	 * if ( salesOrderBean != null && orderType !=null ) { marshallOrder.build(
	 * salesOrderBean, orderType ); }
	 *
	 * } } else { response.setOrderId( "Order Not Found" ); } }
	 */

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
	public SalesOrder getSalesOrder(final Object bean) {
		try {
			return (SalesOrder) bean;
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			return null;
		}

	}

	/**
	 * @param bean
	 *            SalesOrderBean weakly typed
	 * @return Strongly typed version of SalesOrderBean
	 */
	@SuppressWarnings("unchecked")
	public List<SalesOrder> getSalesOrderBeanList(final Object beans) {
		try {

			if (beans != null) {

				if (beans instanceof SalesOrder) {
					List<SalesOrder> soList = new ArrayList<SalesOrder>();
					soList.add((SalesOrder) beans);

					return soList;
				}

				if (beans instanceof List) {

					return (List<SalesOrder>) beans;
				}

				return (List<SalesOrder>) beans;
			}
		} catch (ClassCastException cce) {
			cce.printStackTrace();

		}

		List<SalesOrder> soList = new ArrayList<SalesOrder>();
		return soList;

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

	/**
	 * Thsi method will populate context object with client supplied information
	 * and return it back in the response.
	 *
	 * @param orderDocument
	 * @return
	 */
	public static OrchestrationContext createLoadContext(
			final OrderManagementRequestResponseDocument orderDocument) {

		OrchestrationContext params = OrchestrationContext.Factory.createOme();

		if ((orderDocument != null)
				&& (orderDocument.getOrderManagementRequestResponse() != null)) {
			OrderManagementRequestResponse orderDoc = orderDocument
					.getOrderManagementRequestResponse();
			params.add(TaskContextParamEnum.correlationId.name(),
					orderDoc.getCorrelationId());
			params.add(TaskContextParamEnum.transactionId.name(),
					orderDoc.getTransactionId());
			params.add(TaskContextParamEnum.sessionId.name(),
					orderDoc.getSessionId());
			params.add(TaskContextParamEnum.region.name(), orderDoc.getRegion());
			params.add(TaskContextParamEnum.salesCode.name(),
					orderDoc.getSalesCode());
			params.add(TaskContextParamEnum.affiliateName.name(),
					orderDoc.getAffiliateName());
			params.add(TaskContextParamEnum.timeStamp.name(),
					orderDoc.getTransactionTimeStamp());

			if(orderDoc.getRequest() != null && orderDoc.getRequest().getNotificationEvents() != null) {
			    params.add(TaskContextParamEnum.notificationEvents.name(), orderDoc.getRequest().getNotificationEvents());
			}

			params.add(TaskContextParamEnum.transactionType.name(), orderDoc
					.getTransactionType().toString());

			if (orderDoc.getRequest() != null) {
				if (orderDoc.getRequest().getSalesContext() != null) {
					params.add(TaskContextParamEnum.salesContext.name(),
							orderDoc.getRequest().getSalesContext());
				}

				if (orderDoc.getRequest().getAgentId() != null) {
					params.add(TaskContextParamEnum.agent.name(), orderDoc
							.getRequest().getAgentId());
				} else {
					params.getValidationReport().addErrorMessage(735L,
							"agentId is required. request.agentId");

					throw new IllegalArgumentException("missing agent Id");
				}

			}

		}
		return params;

	}

	/**
	 * Helper method to retrieve paging details from
	 * OrderManagementRequestResponse Document and set it in instance variable
	 * to be used later on to control the no of records to be retrieved.
	 *
	 * @param orderDocument
	 */
	protected void getPagingDetails(
			final OrderManagementRequestResponseDocument orderDocument,
			OrchestrationContext params) {
		int offSet = 0;
		int totalRows = 0;
		PagingDetail pDetail = orderDocument
				.getOrderManagementRequestResponse().getPagingDetail();
		if (pDetail != null) {
			offSet = pDetail.getOffSet();
			totalRows = pDetail.getTotalRows();
		}
		offSet = offSet == 0 ? START_POSITION : offSet;
		totalRows = totalRows == 0 ? TOTAL_ROWS : totalRows;
		params.add(TaskContextParamEnum.offSet.name(), offSet);
		params.add(TaskContextParamEnum.totalRows.name(), totalRows);
	}

	/*
	 * public void populateViolation( OrchestrationContext params,
	 * List<ConstraintViolation> violations ) { for(ConstraintViolation
	 * violation : violations){ ValidationReport vReport =
	 * params.getValidationReport(); vReport.addMessage( Message.Type.FATAL, 1L,
	 * violation.getMessage()); } }
	 */

	public void populateViolation(OrchestrationContext params,
			Set<ConstraintViolation<SalesOrder>> violations) {
		for (ConstraintViolation<SalesOrder> violation : violations) {
			ValidationReport vReport = params.getValidationReport();
			vReport.addMessage(Message.Type.FATAL, 1L, violation.getMessage());
		}
	}

	public MarshallOrder<SalesOrder> getMarshallOrder() {
		return marshallOrder;
	}

	public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
		this.marshallOrder = marshallOrder;
	}

	/**
	 * Helper method to audit customer details
	 *
	 * @param salesOrder
	 * @param response
	 * @return
	 */
	public void auditOrder(SalesOrder salesOrder, String description,
			String agentId) {
//		logger.debug("Auditing Order information");
//		OrderManagementRequestResponseDocument orderDoc = OrderManagementRequestResponseDocument.Factory
//				.newInstance();
//		Response response = orderDoc.addNewOrderManagementRequestResponse()
//				.addNewResponse();
//		SalesOrder oldOrder = orderDao.findById(salesOrder.getExternalId());
//		OrderType orderType = response.addNewOrderInfo();
//		marshallOrder.build(oldOrder, orderType);
//		OrderAudit orderAudit = AuditBuilder.createOrderAudit(oldOrder,
//				orderType.toString(), description, agentId);
//		auditDao.audit(orderAudit);
	}

	public void populateContext(OrchestrationContext params,
			OrderManagementRequestResponse orderDoc) {
		if (orderDoc != null) {
			params.add(TaskContextParamEnum.transactionId.name(),
					orderDoc.getTransactionId());
			params.add(TaskContextParamEnum.correlationId.name(),
					orderDoc.getCorrelationId());
			params.add(TaskContextParamEnum.sessionId.name(),
					orderDoc.getSessionId());
			params.add(TaskContextParamEnum.region.name(), orderDoc.getRegion());
			params.add(TaskContextParamEnum.salesCode.name(),
					orderDoc.getSalesCode());
			params.add(TaskContextParamEnum.affiliateName.name(),
					orderDoc.getAffiliateName());
		}
	}

	/**
	 * Helper method to populate client supplied context information back in to
	 * the response
	 *
	 * @param params
	 * @param requestResponse
	 */
	public void loadResponseContext(final OrchestrationContext params,
			OrderManagementRequestResponse requestResponse) {

		if (params.get(TaskContextParamEnum.correlationId.name()) != null) {
			requestResponse.setCorrelationId((String) params
					.get(TaskContextParamEnum.correlationId.name()));
		}

		if (params.get(TaskContextParamEnum.transactionId.name()) != null) {
			requestResponse.setTransactionId((Integer) params
					.get(TaskContextParamEnum.transactionId.name()));
		}

	}

	/**
	 * Saving request context in context to be populated back in response later
	 * on
	 *
	 * @param reqRes
	 * @param params
	 */
	public void saveRequestContext(OrderManagementRequestResponse reqRes,
			OrchestrationContext params) {
		if (reqRes.getCorrelationId() != null) {
			params.add(TaskContextParamEnum.correlationId.name(),
					reqRes.getCorrelationId());
		}
		params.add(TaskContextParamEnum.transactionId.name(),
				reqRes.getTransactionId());
	}

}
