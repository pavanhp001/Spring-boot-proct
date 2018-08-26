package com.AL.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.customer.OmeCustomerService;
import com.AL.enums.LineItemStatus;
import com.AL.enums.OrderStatus;
import com.AL.factory.StatusFactory;
import com.AL.service.OrderManagementRedefinedService;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.strategy.StatusChangeStrategy;
import com.AL.task.util.AccountHolderUtil;
import com.AL.util.service.OrderManagementServiceUtil;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SalesOrderContext;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.V.beans.job.Job;
import com.AL.Vdao.dao.JobDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.util.converter.unmarshall.UnmarshallSalesOrderContext;
import com.AL.xml.v4.SalesContextType;

@Component
public class OrderManagementRedefinedServiceImpl implements OrderManagementRedefinedService{

	public static final Logger LOGGER = LoggerFactory.getLogger(OrderManagementRedefinedServiceImpl.class);
	
	private static final String LI_TYPE_PRODUCT = "product";
	private static final String YES = "yes";
	private static final String NO = "no";
	
	@Autowired
	private UnmarshallSalesOrderContext unmarshallSOContext;
	
	@Autowired
	private OmeCustomerService omeCustomerService;
	
	@Autowired
	private OrderManagementDao orderManagementDao;
	
	@Autowired
	private JobDao jobDao;
	
	/* (non-Javadoc)
	 * @see com.AL.service.OrderManagementRedefinedService#updateLineitemStatus(com.AL.task.context.impl.OrchestrationContext)
	 */
	public Boolean updateLineitemStatus(OrchestrationContext params) {

		LOGGER.info("method updateLineitemStatus START");
		long startTime = System.currentTimeMillis();
		Boolean isSuccess = Boolean.FALSE;
		
		// Get the request from the order management document request parameter
		String orderExtId = (String)params.get(TaskContextParamEnum.orderId.name());
		if(null != orderExtId){
			
			// Check for the user/agent validity
			if(null != params.get(TaskContextParamEnum.agent.name())){
				
				User user = (User)params.get(TaskContextParamEnum.agent.name());
				String transType = (String)params.get(TaskContextParamEnum.transactionType.name());
							
				// Retrieve the order from DB
				LOGGER.debug("retrieve sales order from the database:" + orderExtId);
				
				boolean includeAccountHolders = false;
                if(params.get(AccountHolderUtil.includeAccountHoldersKey) != null) {
                    includeAccountHolders = (Boolean)params.get(AccountHolderUtil.includeAccountHoldersKey); 
                }
                SalesOrder salesOrder = orderManagementDao.findById(Long.valueOf(orderExtId), includeAccountHolders);

				if(null != salesOrder){
					
					String liExtId = (String)params.get(TaskContextParamEnum.lineItemExtId.name());
					String newStatus = (String)params.get(TaskContextParamEnum.status.name());
					String source = (String)params.get(TaskContextParamEnum.source.name());
					
					long lineItemExtId = Long.valueOf(liExtId);
					// Check for the line item status change validity (transition from to To)
					LineItem lineItem = OrderManagementServiceUtil.getLineitemOnValidStatusChange(lineItemExtId, newStatus, salesOrder);
					if(null != lineItem){
						
						// Change the status of the requested line item
						LineItemStatus requestedLineItemStatus = LineItemStatus.valueOf(newStatus);
						StatusRecordBean statusRecord = new StatusRecordBean();
						statusRecord.setStatus(requestedLineItemStatus.name());
						if(null != params.get(TaskContextParamEnum.reason.name())){
							List<String> reason = new ArrayList<String>();
							reason.add((String)params.get(TaskContextParamEnum.reason.name()));
							statusRecord.setReasons(reason);
						}
						statusRecord.setAgentExternalId(user.getUserLogin());
						statusRecord.setAgent(user);
						statusRecord.setDateTimeStamp(Calendar.getInstance());
						
						lineItem.setCurrentStatus(statusRecord);
						lineItem.getHistoricStatus().add(statusRecord);
						
						// Collect all order status and 
						Map<String, String> lineItemStatus = new HashMap<String, String>();
						for (LineItem li : salesOrder.getLineItems()) {
							if ((li.getCurrentStatus() != null)	&& (li.getCurrentStatus().getStatus() != null)) {
								if(li.getLineItemDetail().getType().equalsIgnoreCase(LI_TYPE_PRODUCT)){
									lineItemStatus.put(lineItem.getCurrentStatus().getStatus(), "TOKEN");
								}
							}
							// Update the line item attribute to notify DWME that this line item has changed its status
							if(li.getExternalId()==lineItemExtId){
								OrderManagementServiceUtil.updateLineItemAttributes(YES, li.getLineItemAttribute());
							}
							else{
								OrderManagementServiceUtil.updateLineItemAttributes(NO, li.getLineItemAttribute());
							}
						}
						
						// Change the order status
						OrderStatus calculatedOrderStatus = OrderStatus.sales;
						if ((lineItemStatus != null) && (lineItemStatus.size() > 0)) {
							calculatedOrderStatus = OrderStatus.precedence(lineItemStatus);
						}
						StatusRecordBean newSalesOrderStatus = StatusFactory.INSTANCE.create(user,calculatedOrderStatus);
						
						StatusRecordBean currentStatus = salesOrder.getCurrentStatus();
						StatusChangeStrategy.INSTANCE.validateStatus(currentStatus);
						StatusChangeStrategy.INSTANCE.resolveNewStatusAndReason(salesOrder,newSalesOrderStatus);
						StatusChangeStrategy.INSTANCE.addCurrentStatusToHistory(salesOrder,newSalesOrderStatus);
						
						LOGGER.debug("update current order status:" + salesOrder.getExternalId());
						salesOrder.setCurrentStatus(newSalesOrderStatus);
						
						// TODO Update the total order price if the status modified from sales to cancelled vis-versa
						
						// Set the correlation id to the param object & request. 
						if(null == params.get(TaskContextParamEnum.correlationId.name())){
							params.add(TaskContextParamEnum.correlationId.name(), salesOrder.getGuid());
						}
						
						// Update sales order context
						SalesContextType salesContextType = (SalesContextType)params.get(TaskContextParamEnum.salesContext.name());
						if(null != salesContextType){
							Set<SalesOrderContext> socList = unmarshallSOContext.build(salesContextType);
							OrderManagementServiceUtil.buildSalesOrderContextForExtPorcess(salesOrder,socList);
						}
						
						// Save the information --- One DB call
						isSuccess = orderManagementDao.updateSalesOrderForStatusUpdate(salesOrder);
						
						// Update customer information
						String message = isSuccess ? " Success " : " Failed ";
						omeCustomerService.addConsumerInteraction(user.getUserLogin(), salesOrder, lineItem.getExternalId(),
								source, transType + " [lineitem : "+lineItem.getExternalId()+"]"+message);
						
						// Broadcast that message to BA
						params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
					}				
				}
				else{
					throw new IllegalArgumentException("Unable to locate sales order : "+orderExtId);	
				}
			}
			else{
				throw new IllegalArgumentException("Unable to locate agent id for order : "+orderExtId);
			}
		}
		else{
			throw new IllegalArgumentException("Unable to locate sales order id in the request "+orderExtId);
		}
		LOGGER.info("method updateLineitemStatus completed in "+(System.currentTimeMillis() - startTime)+" ms");
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see com.AL.service.OrderManagementRedefinedService#retrieveJobsByTransactionId(java.lang.String)
	 */
	public List<Job> retrieveJobsByTransactionId(String pTransactionId){
		
		return jobDao.retrieveJobsByTransactionId(pTransactionId);
	}
}
