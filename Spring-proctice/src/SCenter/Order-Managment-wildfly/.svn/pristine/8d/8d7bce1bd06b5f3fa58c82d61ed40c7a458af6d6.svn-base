package com.AL.task.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.task.LocalTaskGetOrder;
import com.AL.task.TaskBase;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.AccountHolder;
import com.AL.Vdao.dao.AccountHolderDao;
import com.AL.xml.v4.AccountHolderType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.vm.util.converter.mapper.AccountHolderMapper;

@Component("taskGetAllAccountHolders")
public class TaskGetAllAccountHolders extends TaskBase<OrderManagementRequestResponseDocument> 
		implements LocalTaskGetOrder<OrderManagementRequestResponseDocument> {

	private Logger logger = Logger.getLogger(TaskGetAllAccountHolders.class);
	
	@Autowired
	private AccountHolderDao accountHolderDao;
	
	@Override
	public OrchestrationContext processRequest(
			OrderManagementRequestResponseDocument orderDocument) {
		logger.info("TaskGetAllAccountHolders.processRequest() started");
		long orderId = Long.parseLong(orderDocument.getOrderManagementRequestResponse().getRequest().getOrderId());
		OrchestrationContext params = OrchestrationContext.Factory.createOme();
		logger.info("OrderId: "+orderId);
		params.add("orderId", orderId);
		return params;
	}

	@Override
	public OrchestrationContext processAgenda(OrchestrationContext params) {
		long orderId = (Long)params.get("orderId");
		List<AccountHolder> accountHolders = accountHolderDao.getAllAccountHoldersByOrderExternalId(orderId);
		logger.info("AccountHolder size: "+accountHolders.size());
		params.add("accountHolders", accountHolders);
		return params;
	}

	@Override
	public OrderManagementRequestResponseDocument processResponse(
			OrchestrationContext params) {
		logger.info("Preparing response");
		OrderManagementRequestResponseDocument omrrDoc = OrderManagementRequestResponseDocument.Factory
		.newInstance();
		OrderManagementRequestResponse requestResponse = omrrDoc
				.addNewOrderManagementRequestResponse();
		Response response = requestResponse.addNewResponse();
		@SuppressWarnings("unchecked")
		List<AccountHolder> accountHolders = (List<AccountHolder>)params.get("accountHolders");
		for(AccountHolder accountHolder : accountHolders) {
			AccountHolderType linAccHolder = response.addNewAccountHolder();
			AccountHolderMapper.copyAccountHolder(accountHolder, linAccHolder);
		}
		return omrrDoc;
	}

	@Override
	public OrderManagementRequestResponseDocument handleFault(Exception e,
			OrchestrationContext taskResponse,
			OrderManagementRequestResponseDocument orderDocument) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void broadcast(String strToBroadcastOriginal,
			Map<String, String> header) {
		// TODO Auto-generated method stub
		
	}
	
}
