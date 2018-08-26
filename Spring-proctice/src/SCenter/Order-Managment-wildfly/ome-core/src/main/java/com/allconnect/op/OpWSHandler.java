package com.AL.op;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.AL.op.service.OpService;
import com.AL.op.service.OpServiceLocator;
import com.AL.ws.WSHandler;

import com.AL.xml.common.OpMessage;
import com.AL.xml.common.OpResponseStatus;
import com.AL.xml.common.OpStatusType;
import com.AL.xml.common.TransactionType;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument.OrderProvisioningResponse;

import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument;
import static com.AL.op.ProvisionConstants.*;

@Component("OpWSHandler")
public class OpWSHandler implements WSHandler<OrderProvisioningRequestDocument, OrderProvisioningResponseDocument> {

	private static Logger logger = Logger.getLogger(OpWSHandler.class);
	
	public OrderProvisioningResponseDocument execute(OrderProvisioningRequestDocument opRequestDoc) {
		TransactionType.Enum transactionType = opRequestDoc.getOrderProvisioningRequest().getTransactionType();
		logger.info("TransactionType: "+transactionType);
		OpService service = OpServiceLocator.getService(transactionType);
		logger.info("ServiceName: "+service.getName());
		OrderProvisioningResponseDocument opResponseDoc = null;
		try {
			opResponseDoc = (OrderProvisioningResponseDocument)service.execute(opRequestDoc);
		} catch (Exception e) {
			logger.error("Error occured in execution of "+service.getName());
			logger.error(e);
			opResponseDoc = OrderProvisioningResponseDocument.Factory.newInstance();
			OrderProvisioningResponse oprResponse = opResponseDoc.addNewOrderProvisioningResponse();
			OpResponseStatus responseStatus = oprResponse.addNewResponseStatus();
			responseStatus.setStatusCode(ERROR_CODE);
			responseStatus.setStatus(OpStatusType.ERROR);
			OpMessage opMsg = responseStatus.addNewOpMessage();
			opMsg.setText(e.getMessage());
			opMsg.setCode("1");
		}
		return opResponseDoc;
	}

}
