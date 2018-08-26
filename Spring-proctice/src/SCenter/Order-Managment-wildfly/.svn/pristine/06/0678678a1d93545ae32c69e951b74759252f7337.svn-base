package com.AL.op.service;

import static com.AL.op.ProvisionConstants.*;

import java.util.HashMap;
import java.util.Map;

import com.AL.xml.common.TransactionType;

public class OpServiceLocator {
	
	private static Map<TransactionType.Enum, String> serviceMap = new HashMap<TransactionType.Enum, String>();
	
	static {
		serviceMap.put(TransactionType.CREDIT_QUALIFICATION, CREDIT_QUAL_SERVICE);
		serviceMap.put(TransactionType.AUTHENTICATE_CUSTOMER, AUTH_CUSTOMER_SERVICE);
		serviceMap.put(TransactionType.ORDER_QUALIFICATION, ORDER_QUAL_SERVICE);
		serviceMap.put(TransactionType.VALIDATE_PAYMENT, VALIDATE_PAYMENT_SERVICE);
	}
	
	public static String getServiceName(TransactionType.Enum transactionType) {
		return serviceMap.get(transactionType);
	}
	
	public static OpService getService(String serviceName) {
		return (OpService)OpServiceLookupContext.INSTANCE.lookup(serviceName);
	}
	
	public static OpService getService(TransactionType.Enum transactionType) {
		String serviceName = serviceMap.get(transactionType);
		return (OpService)OpServiceLookupContext.INSTANCE.lookup(serviceName);
	}
	
}
