package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.vm.vo.AgentUtil;
import com.A.vm.vo.OrderChangeValueObject;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.A.xml.v4.LineItemStatusType;

/**
 * @author ebthomas
 *
 */
@Component
public class UnmarshallLineItemOrderStatus {

	private static final Logger logger = Logger.getLogger(UnmarshallLineItemOrderStatus.class);
	/**
	 * Unmarshall Line Item Order Status.
	 */
	public UnmarshallLineItemOrderStatus() {
		super();
	}

	/**
	 * @param originalMessage
	 *            Source
	 * @return Boolean indicating valid message
	 */
	private Boolean isValid(final Request originalMessage) {
		if (originalMessage == null) {
			throw new IllegalArgumentException("null value not permitted.");
		}

		return Boolean.TRUE;
	}

	/**
	 * @param originalRequest
	 *            Source
	 * @param entityManagerReference
	 *            Repository Reference
	 * @return Order Status Domain Object
	 */
	private OrderChangeValueObject doBuildOrderStatus(
			final Request originalRequest) {
		OrderChangeValueObject orderChangeValueObject = null;

		if (originalRequest == null) {
			return null;
		}
		
		String orderId = originalRequest.getOrderId();
		LineItemStatusType newLineItemStatus = originalRequest
				.getNewLineItemStatus();
		
		List<String> lineItemIdList = originalRequest.getLineitemIdList();
		
		// Tweaking the code related to lineItemId or lineitemId list
		if (originalRequest.getLineItemId() != null && !originalRequest.getLineItemId().equals("")) {
			if (lineItemIdList != null) {
				lineItemIdList.add(originalRequest.getLineItemId());
			} else {
				lineItemIdList = new ArrayList<String>();
				lineItemIdList.add(originalRequest.getLineItemId());
			}
		}
		
		if (lineItemIdList != null && lineItemIdList.size() > 0) {
			for (String lieId : lineItemIdList) {
				logger.info("UnmarshallLineItemOrderStatus:doBuildOrderStatus():orderId: " + orderId 
						+ " :lineItemExternalIds: " + lieId 
						+ " :originalRequest LineItemIds Size: "  + originalRequest.sizeOfLineitemIdArray());
			}
		}
		
		String agentId = AgentUtil.getAgentId(originalRequest);
		if ((orderId != null) && (newLineItemStatus != null)	&& 
				(lineItemIdList != null && lineItemIdList.size() > 0 ))
		{
			logger.info("Requested status : " + newLineItemStatus.getStatusCode().toString() 
					+" for lineitems : " + lineItemIdList.toString());
			orderChangeValueObject = new OrderChangeValueObject(orderId, lineItemIdList, newLineItemStatus,agentId);
		}
		return orderChangeValueObject;
	}

	/**
	 * @param request
	 *            Input Source
	 * @param entityManagerReference
	 *            Datasource Reference
	 * @return Order Status Domain Object
	 */
	public OrderChangeValueObject build(final Request request) {
		if (request == null) {
			throw new IllegalArgumentException(
					"null value not allowed in builder");
		}

		if (isValid(request)) {
			return doBuildOrderStatus(request);
		}

		throw new IllegalArgumentException("invalid document.  unable to build");

	}

}
