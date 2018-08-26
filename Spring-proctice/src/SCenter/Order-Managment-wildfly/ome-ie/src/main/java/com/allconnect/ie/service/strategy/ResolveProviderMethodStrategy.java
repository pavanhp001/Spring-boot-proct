package com.AL.ie.service.strategy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequest;

public class ResolveProviderMethodStrategy {
	
	private static final Logger logger = Logger.getLogger(ResolveProviderMethodStrategy.class);

	private static final Map<String, Object> mapTransactionType = new HashMap<String, Object>();
	private static final Object TOKEN = new Object();
	static {

		mapTransactionType.put("orderQualification", TOKEN);
		mapTransactionType.put("orderSubmission", TOKEN);
		mapTransactionType.put("orderStatus", TOKEN);
		mapTransactionType.put("getProductCatalog", TOKEN);
		mapTransactionType.put("serviceQualification", TOKEN);
		mapTransactionType.put("creditQualification", TOKEN);
		mapTransactionType.put("updateOrder", TOKEN);
		mapTransactionType.put("orderDateQualification", TOKEN);
	}

	public static String resolveInvokeMethodName(OrderFulfillmentRequest doc) {

		if ((doc != null)
				&& (doc.getOrderManagementRequestResponse() != null)
				&& (doc.getOrderManagementRequestResponse()
						.getTransactionType() != null)) {

			String transactionType = doc.getOrderManagementRequestResponse()
					.getTransactionType().toString();

			if (mapTransactionType.containsKey(transactionType)) {

				logger.debug("transaction type key found:"+transactionType);
				return transactionType;
			} else 
			{
				logger.debug("transaction type key NOT found:"+transactionType);
				return transactionType;
			}

		}

		throw new IllegalArgumentException(
				"unable to determine the transaction type:"+doc.toString());

	}

}
