package com.AL.task.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.task.TaskBase;
import com.AL.util.messaging.Broadcastable;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.Vdao.dao.SearchOrderDao;
import com.AL.Vdao.dao.impl.SearchCriteria;
import com.AL.Vdao.util.OrderDataBean;
import com.AL.vm.util.converter.marshall.MarshallOrderSearch;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderSearch;

@Component("taskSearchOrder")
public class TaskSearchOrder extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		Broadcastable {
	
	private Logger logger = Logger.getLogger(TaskSearchOrder.class);

	@Autowired
	private SearchOrderDao searchOrderDao;

	@Override
	public OrchestrationContext processRequest(
			OrderManagementRequestResponseDocument orderDoc) {
		logger.info("Processing searchOrder request");

		OrchestrationContext params = OrchestrationContext.Factory
				.createOme();
		Request request = orderDoc.getOrderManagementRequestResponse()
				.getRequest();
		getPagingDetails(orderDoc, params);
		saveRequestContext(orderDoc.getOrderManagementRequestResponse(),
				params);
		SearchCriteria criteria = prepareSearchCriteria(request);
		params.add(TaskContextParamEnum.searchCriteria.name(), criteria);
		params.add(TaskContextParamEnum.request.name(), request);
		return params;
	}

	@Override
	public OrchestrationContext processAgenda(OrchestrationContext params) {
		SearchCriteria criteria = (SearchCriteria) params
				.get(TaskContextParamEnum.searchCriteria.name());
		logger.info("Searching Order with criteria : " + criteria.toString());
		int offSet = (Integer) params.get(TaskContextParamEnum.offSet.name());
		int totalRows = (Integer) params.get(TaskContextParamEnum.totalRows.name());
		if (criteria != null) {
			if(totalRows > 0 && totalRows < 50){
				TOTAL_ROWS = totalRows;
			}
				Map<String,Object> resultMap = searchOrderDao.searchOrder(criteria,offSet, TOTAL_ROWS);


			if (resultMap != null && !resultMap.isEmpty()) {
				List<OrderDataBean> orderList =(List<OrderDataBean>) resultMap.get("dataSets");
				Integer searchCount = (Integer)resultMap.get("totalCount");
				params.add(TaskContextParamEnum.searchList.name(), orderList);
				params.add(TaskContextParamEnum.searchCount.name(), searchCount);
			} else {
				((ResponseItemOme) params).addOrderNotFound("No Order Found!!");
			}
		}
		return params;
	}

	@Override
	public OrderManagementRequestResponseDocument processResponse(
			OrchestrationContext params) {
		logger.info("Preparing Order search response");
		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();
		Response response = requestResponse.addNewResponse();
		loadRequestToResponse(container, params);
		loadResponseContext(params, requestResponse);

		try {
			// add Response Information to response container
			Integer searchCount = (Integer) params.get(TaskContextParamEnum.searchCount.name());
			List<OrderDataBean> dataList = (List<OrderDataBean>) (params.get(TaskContextParamEnum.searchList.name()));
			OrderSearch search = response.addNewOrderSearchResult();
			search.setTotalRows(searchCount.intValue());
			if (dataList != null && !dataList.isEmpty()) {
				MarshallOrderSearch.marshallSearchResult(dataList, search);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while processing response ...", e);
		}
		return container;
	}

	@Override
	public OrderManagementRequestResponseDocument handleFault(Exception e,
			OrchestrationContext taskResponse,
			OrderManagementRequestResponseDocument orderDoc) {
		return null;
	}

	@Override
	public void broadcast(String strToBroadcastOriginal,
			Map<String, String> header) {
	}

	private SearchCriteria prepareSearchCriteria(Request request) {
		SearchCriteria sc = new SearchCriteria();
		sc.setLineItemStatus(request.getStatusArray(0) != null ? request.getStatusArray(0).trim() : "");
		sc.setProviderId(request.getProviderId() != null ? request.getProviderId().trim() : "");
		if (request.getProviderId() != null) {
			sc.setScheduledInstallDate(request.getScheduledStartDate());
		}
		if (request.getProviderId() == null) {
			sc.setOrderDate(request.getOrderDate());
			
			if (request.getOrderDate() == null) {
				sc.setLineItemCreateDate(request.getLineItemCreateDate());
			}
		}
		if(request.getChannelType() > 0) {
			sc.setChannelType(request.getChannelType());
		}

		return sc;
	}
}
