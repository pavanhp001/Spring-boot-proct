package com.AL.task.impl;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.task.LocalTaskGetOrderByStatus;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.util.converter.marshall.MarshallConsumers;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.CustomerInformationDocument.CustomerInformation;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 *
 */

@Component("taskGetOrderByStatus")
public class TaskGetOrderByStatus extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskGetOrderByStatus<OrderManagementRequestResponseDocument> {
	private static final Logger logger = Logger
			.getLogger(TaskGetOrderByConfirmationNumber.class);
	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private MarshallConsumers marshallConsumer;

	@Autowired
	private ResponseBuilder responseBuilder;


	protected int getPagingOffset(final OrderManagementRequestResponseDocument orderDocument) {

		int offset = 0;

		if ((orderDocument != null) && (orderDocument.getOrderManagementRequestResponse() != null) && (orderDocument.getOrderManagementRequestResponse().getPagingDetail() != null))
		{
			  offset = orderDocument.getOrderManagementRequestResponse().getPagingDetail().getOffSet();
		}

		return offset;
	}


	protected int getTotalRows(final OrderManagementRequestResponseDocument orderDocument) {

		int totalRows = 100;

		if ((orderDocument != null) && (orderDocument.getOrderManagementRequestResponse() != null) && (orderDocument.getOrderManagementRequestResponse().getPagingDetail() != null))
		{
			totalRows = orderDocument.getOrderManagementRequestResponse().getPagingDetail().getTotalRows();
		}

		return totalRows;
	}

    /**
     * {@inheritDoc}
     */
    public OrchestrationContext processRequest( final OrderManagementRequestResponseDocument orderDocument )
    {

    	logger.info("Processing getOrderByStatus request");
        Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
        List<String> status = null;
        List<String> reason = null;

        int offSet = getPagingOffset(orderDocument);
        int totalRows =getTotalRows(orderDocument);



        if ((request.getStatusList() != null) && (request.getStatusList().size() > 0)) {
        	status = request.getStatusList();
        }

        if ((request.getReasonList() != null) && (request.getReasonList().size() > 0)) {
        	reason = request.getReasonList();
        }

        OrchestrationContext params =  createLoadContext(orderDocument);
        getPagingDetails( orderDocument, params );
        try
        {
            params.add( TaskContextParamEnum.reason.name(), reason );
            params.add( TaskContextParamEnum.status.name(), status );
            params.add( TaskContextParamEnum.offSet.name(), offSet  );
            params.add( TaskContextParamEnum.totalRows.name(), totalRows  );
        }
        catch ( Exception e )
        {
            return OrchestrationContext.Factory.createOme( e );
        }

        return params;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrchestrationContext processAgenda( final OrchestrationContext params )
    {
        logger.debug("processing agenda...." );



        List<String> reason = (List<String>)params.get( TaskContextParamEnum.reason.name()  );
        List<String> status = (List<String>)params.get( TaskContextParamEnum.status.name()  );

        int offSet =(Integer) params.get( TaskContextParamEnum.offSet.name() );
		int totalRows = (Integer)params.get( TaskContextParamEnum.totalRows.name() );



        logger.info("reason:" + reason + " Status:"+status );

        List<SalesOrder> orderList = orderManagementDao.findOrderByStatusReason(status, reason, offSet, totalRows);
        if ( orderList != null && orderList.size() > 0 )
        {
            logger.debug("Sales Order. found..." );
            params.addSuccessful( orderList );
        }
        else
        {

            ( (ResponseItemOme) params ).addOrderNotFound( "SalesOrder for Status: " + status + " Reason:"+reason+"  not found !!!" );
        }



		return params;


    }


	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {
		logger.info("Preparing order response");
		// Boolean includeCustDetails =
		// (Boolean)params.get(TaskContextParamEnum.includeCustomerDetails.name());
		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();
		@SuppressWarnings("unchecked")
		List<SalesOrder> orderList = (List<SalesOrder>) params
				.get(TaskContextParamEnum.salesOrder.name());

		if (orderList != null && !orderList.isEmpty()) {
			Response response = requestResponse.addNewResponse();
			for (SalesOrder so : orderList) {
				logger.debug("Preparing response for found SalesOrder....");
				OrderType newOrderInfoResponse = response.addNewOrderInfo();
				CustomerInformation custInfoType = newOrderInfoResponse
						.addNewCustomerInformation();
				CustomerType custType = custInfoType.addNewCustomer();
				marshallOrder.build(so, newOrderInfoResponse);
				// if(includeCustDetails){
				Consumer consumerBean = customerDao.findCustomerByExternalId(so
						.getConsumerExternalId());
				if (consumerBean != null) {
					custType = newOrderInfoResponse.getCustomerInformation()
							.getCustomer();
					marshallConsumer.build(consumerBean, custType);
				}
				// }
				response.setOrderId(String.valueOf(so.getExternalId()));
			}
		} else {
			logger.debug("No orders found for provided customer....");
		}

		responseBuilder.loadResponseContext(params, requestResponse);
		// Ensure that request is in the response container
		loadRequestToResponse(container, params);

		return container;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument handleFault(
			final Exception e, final OrchestrationContext params,
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.error(e);
		return orderDocument;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

	}

	public UnmarshallOrder getUnmarshallOrder() {
		return unmarshallOrder;
	}

	public void setUnmarshallOrder(UnmarshallOrder unmarshallOrder) {
		this.unmarshallOrder = unmarshallOrder;
	}

	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

	public MarshallOrder<SalesOrder> getMarshallOrder() {
		return marshallOrder;
	}

	public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
		this.marshallOrder = marshallOrder;
	}

}