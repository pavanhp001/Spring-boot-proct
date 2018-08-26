package com.AL.ie.service.strategy;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProviderType;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequest;

public class ResolveProviderName {

	private static final Logger logger = Logger
			.getLogger(ResolveProviderName.class);

//	public static String resolveInvokeMethodName(OrderFulfillmentRequest doc) {
//
//		String methodName = "ATTSTI";
//
//		return methodName;
//
//	}

	/**
	 * Find provider based on lineitem provider
	 * @param doc
	 * @return
	 */
	public static String resolveProvider(OrderFulfillmentRequest doc){
		logger.debug("Resolving provider for lineitem with notification event only.");
		String providerExtId = "";
		Request req = doc.getOrderManagementRequestResponse().getRequest();
		OrderType orderType = req.getOrderInfo();
		List<LineItemType> liTypeList = orderType.getLineItems().getLineItemList();
		for(LineItemType liType : liTypeList){
			logger.debug("Checking notification event present for given lineitem.");
			if(liType.getNotificationEvents() != null &&  liType.getNotificationEvents().getEventList() != null){

				if( liType.getLineItemDetail() != null && liType.getLineItemDetail().getDetail() != null){
					ProviderType providerType = liType.getLineItemDetail().getDetail().getProductLineItem().getProvider();
					if(providerType != null){
						providerExtId = providerType.getExternalId();
						logger.debug("Resolved provider external id : " + providerExtId);
						break;
					}
				}
			}
		}
		return providerExtId;
	}
}
