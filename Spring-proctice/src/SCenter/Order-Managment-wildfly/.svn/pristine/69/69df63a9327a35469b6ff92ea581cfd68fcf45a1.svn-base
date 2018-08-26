package com.AL.task.impl;
 

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.task.LocalTaskGetOrderByScheduleDate;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.util.DateUtil;
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

@Component("taskGetOrderByScheduleDate")
public class TaskGetOrderByScheduleDate extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskGetOrderByScheduleDate<OrderManagementRequestResponseDocument> {
 
	private Logger logger = Logger.getLogger(TaskGetOrderByDate.class);
	
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
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {
		OrchestrationContext params =  createLoadContext(orderDocument);
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();
		//boolean includeCustomerDetails = Boolean.FALSE;
        //includeCustomerDetails = request.getIncludeCustomerDetailsInResponse();
		Calendar scheduleDate = unmarshallOrder.buildOrderScheduleDate(request);
		
		if (scheduleDate == null) {
			scheduleDate = Calendar.getInstance();
		}
		
		
		String status = "";
		String reason = "";
		
		if ((request.getStatusList() != null)&& (request.getStatusList().size() > 0)) {
			status = request.getStatusList().get(0);
			
		}
		
		if ((request.getReasonList() != null)&& (request.getReasonList().size() > 0)) {
			reason = request.getReasonList().get(0);
			
		}
		
	 

		getPagingDetails( orderDocument,params );
		try {
			params.add(TaskContextParamEnum.date.name(), scheduleDate);
			params.add(TaskContextParamEnum.request.name(), request);
			params.add(TaskContextParamEnum.reason.name(), reason);
			params.add(TaskContextParamEnum.status.name(), status);
			
			//params.add( TaskContextParamEnum.includeCustomerDetails.name(), includeCustomerDetails);
		} catch (Exception e) {
			return OrchestrationContext.Factory.createOme(e);
		}

		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {
		Calendar scheduleDate = (Calendar) params.get(TaskContextParamEnum.date.name());
		
 
		int offSet =(Integer) params.get( TaskContextParamEnum.offSet.name() );
		int totalRows = (Integer)params.get( TaskContextParamEnum.totalRows.name() );
		
		String reason = (String)params.get(TaskContextParamEnum.reason.name());
		String status = (String)params.get(TaskContextParamEnum.status.name());
		
		
		List<SalesOrder> saleOrderBeanList = orderManagementDao.findByScheduleDate( 
				scheduleDate.getTime(),status, reason, offSet,totalRows);

		if ((saleOrderBeanList != null) && (saleOrderBeanList.size() > 0)) {
			params.addSuccessful(saleOrderBeanList);
		} else {
			Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = formatter.format(DateUtil.startOfDay( scheduleDate.getTime() ));
			((ResponseItemOme) params)
					.addOrderNotFound("SalesOrder not found with Order Date : " + s +" !!!");
		}

	 
		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {
		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();
		
		//Boolean includeCustDetails = (Boolean)params.get(TaskContextParamEnum.includeCustomerDetails.name());
		List<SalesOrder> salesOrderBeanList = getSalesOrderBeanList(params
				.get(TaskContextParamEnum.salesOrder.name()));

		//Load response
		//loadSalesOrderToResponse(  requestResponse,  salesOrderBeanList);
        if ( salesOrderBeanList != null )
        {
    		Response response = requestResponse.addNewResponse();
            for ( SalesOrder salesOrderBean : salesOrderBeanList )
            {
                OrderType orderType = response.addNewOrderInfo();
                CustomerInformation custInfoType=orderType.addNewCustomerInformation();
        		CustomerType custType = custInfoType.addNewCustomer();
                if ( salesOrderBean != null && orderType !=null )
                {
                	marshallOrder.build( salesOrderBean, orderType  );
                	//if(includeCustDetails)
                	//{
                		Consumer consumerBean = customerDao.findCustomerByExternalId(salesOrderBean.getConsumerExternalId());
                		if(consumerBean != null){
                			custType = orderType.getCustomerInformation().getCustomer();
                        	marshallConsumer.build(consumerBean, custType);
                		}
                	//}
                }

            }
        }
        else
        {
        	logger.debug( "Sales order not found for provided date." );
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

		logger.debug(e.getMessage());

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
