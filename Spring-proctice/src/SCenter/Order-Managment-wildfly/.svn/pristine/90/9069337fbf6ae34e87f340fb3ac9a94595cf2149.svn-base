package com.AL.task.impl;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.task.LocalTaskGetOrderByCustomer;
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
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 *
 */


@Component("taskGetOrderByCustomer")
public class TaskGetOrderByCustomer extends TaskBase<OrderManagementRequestResponseDocument>
implements LocalTaskGetOrderByCustomer<OrderManagementRequestResponseDocument>
{
    private static final Logger logger = Logger.getLogger( TaskGetOrderByConfirmationNumber.class );
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

    /**
     * {@inheritDoc}
     */
    public OrchestrationContext processRequest( final OrderManagementRequestResponseDocument orderDocument )
    {
        Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
        /*boolean includeCustomerDetails = Boolean.FALSE;
        includeCustomerDetails = request.getIncludeCustomerDetailsInResponse();*/
        String customerId = unmarshallOrder.buildCustomerId( request );
        logger.debug("Search for Customer Account No : " + customerId );
        OrchestrationContext params =  createLoadContext(orderDocument);
        getPagingDetails( orderDocument, params );
        try
        {
            params.add( TaskContextParamEnum.customerNumber.name(), customerId.trim() );
            params.add( TaskContextParamEnum.request.name(), request );
            //params.add( TaskContextParamEnum.includeCustomerDetails.name(), includeCustomerDetails);
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
        String customerId = (String) params.get( TaskContextParamEnum.customerNumber.name() );
        Long custExtId = -1L;
        custExtId = Long.valueOf(customerId.trim());
        int offSet =(Integer) params.get( TaskContextParamEnum.offSet.name() );
		int totalRows = (Integer)params.get( TaskContextParamEnum.totalRows.name() );

        logger.debug("Searching order based on Customer No...." + custExtId );

        List<SalesOrder> orderList = orderManagementDao.findByCustomer( custExtId,offSet,totalRows );
        if ( orderList != null && orderList.size() > 0 )
        {
            logger.debug("Sales Order. found..." );
            params.addSuccessful( orderList );
        }
        else
        {
            logger.debug("Order does not exist for Customer no...." + customerId);
            ( (ResponseItemOme) params ).addOrderNotFound( "SalesOrder for Customer : " + customerId + " not found !!!" );
        }


        return params;
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument processResponse( final OrchestrationContext params )
    {
        logger.debug("processing response...." );
        //Boolean includeCustDetails = (Boolean)params.get(TaskContextParamEnum.includeCustomerDetails.name());
        OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory.newInstance();
        OrderManagementRequestResponse requestResponse = container.addNewOrderManagementRequestResponse();
        @SuppressWarnings("unchecked")
		List<SalesOrder> orderList =(List<SalesOrder>)params.get( TaskContextParamEnum.salesOrder.name() ) ;

        if ( orderList != null && !orderList.isEmpty())
        {
        	Response response = requestResponse.addNewResponse();
        	for(SalesOrder so : orderList){
        		logger.debug("Preparing response for found SalesOrder...." );
        		OrderType newOrderInfoResponse = response.addNewOrderInfo();
        		CustomerInformation custInfoType=newOrderInfoResponse.addNewCustomerInformation();
        		CustomerType custType = custInfoType.addNewCustomer();
        		marshallOrder.build( so, newOrderInfoResponse  );
        		//if(includeCustDetails){
        			Consumer consumerBean = customerDao.findCustomerByExternalId(so.getConsumerExternalId());
        			if(consumerBean != null){
        				custType = newOrderInfoResponse.getCustomerInformation().getCustomer();
                    	marshallConsumer.build(consumerBean, custType);
        			}
        		//}
        		response.setOrderId( String.valueOf(so.getExternalId() ));
        	}
        }
        else
        {
            logger.debug("No orders found for provided customer...." );
        }

		responseBuilder.loadResponseContext(params, requestResponse);
        // Ensure that request is in the response container
        loadRequestToResponse( container, params );

        return container;
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument handleFault( final Exception e, final OrchestrationContext params,
            final OrderManagementRequestResponseDocument orderDocument )
    {

        logger.error( e );
        return orderDocument;

    }

    /**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {
		//Broadcast is not required fro all the gets
		//Broadcastable broadcast = HttpBroadcastable.createDefault();
		//broadcast.broadcast(strToBroadcastOriginal,map);
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
