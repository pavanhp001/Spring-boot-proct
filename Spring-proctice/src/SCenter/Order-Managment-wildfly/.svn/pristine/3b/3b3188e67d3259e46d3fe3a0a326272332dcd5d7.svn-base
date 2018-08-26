package com.AL.op.service.impl;

import static com.AL.op.ProvisionConstants.AUTH_CUSTOMER_SERVICE;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.AL.op.service.OpService;
import com.AL.op.strategy.ProvisioningStrategy;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument.OrderProvisioningResponse;

@Component(AUTH_CUSTOMER_SERVICE)
public class AuthenticateCustomerService implements OpService {
	
private static final Logger logger = Logger.getLogger(CreditQualService.class);
	
	public String getName() {
		return AUTH_CUSTOMER_SERVICE;
	}

	public OrderProvisioningResponseDocument execute(OrderProvisioningRequestDocument opRequestDoc) throws Exception {
		logger.info("Authenticate customer started with GUID: "+opRequestDoc.getOrderProvisioningRequest().getCorrelationId());
		OrderProvisioningResponse opResponse = ProvisioningStrategy.INSTANCE.sendRtimMessage(opRequestDoc.getOrderProvisioningRequest());
		OrderProvisioningResponseDocument opResponseDoc = OrderProvisioningResponseDocument.Factory.newInstance();
		opResponseDoc.setOrderProvisioningResponse(opResponse);
		logger.info("Authenticate customer finished with GUID: "+opRequestDoc.getOrderProvisioningRequest().getCorrelationId());
		return opResponseDoc;
	}	

}
