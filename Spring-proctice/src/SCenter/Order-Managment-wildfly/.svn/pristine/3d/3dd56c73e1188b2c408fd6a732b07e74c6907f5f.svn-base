package com.AL.op.service.impl;

import static com.AL.op.ProvisionConstants.VALIDATE_PAYMENT_SERVICE;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.AL.op.service.OpService;
import com.AL.op.strategy.ProvisioningStrategy;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument.OrderProvisioningResponse;

@Component(VALIDATE_PAYMENT_SERVICE)
public class ValidatePaymentService implements OpService {
	
	private static final Logger logger = Logger.getLogger(ValidatePaymentService.class);
	
	public String getName() {
		return VALIDATE_PAYMENT_SERVICE;
	}

	public OrderProvisioningResponseDocument execute(OrderProvisioningRequestDocument opRequestDoc) throws Exception {
		logger.info("ValidatePayment started with GUID: "+opRequestDoc.getOrderProvisioningRequest().getCorrelationId());
		OrderProvisioningResponse opResponse = ProvisioningStrategy.INSTANCE.sendRtimMessage(opRequestDoc.getOrderProvisioningRequest());
		OrderProvisioningResponseDocument opResponseDoc = OrderProvisioningResponseDocument.Factory.newInstance();
		opResponseDoc.setOrderProvisioningResponse(opResponse);
		logger.info("ValidatePayment finished with GUID: "+opRequestDoc.getOrderProvisioningRequest().getCorrelationId());
		return opResponseDoc;
	}	

}
