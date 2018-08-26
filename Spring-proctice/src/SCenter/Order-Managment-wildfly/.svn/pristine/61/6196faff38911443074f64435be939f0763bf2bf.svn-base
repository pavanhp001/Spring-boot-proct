package com.AL.op.service.impl;

import static com.AL.op.ProvisionConstants.CREDIT_QUAL_SERVICE;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.xml.common.OpMessage;
import com.AL.ome.OmeCustomerCommunicator;
import com.AL.op.mapper.CustomerInfoMapper;
import com.AL.op.service.OpService;
import com.AL.op.strategy.ProvisioningStrategy;
import com.AL.op.util.ProvisionHandlerUtil;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.xml.common.OpResponseStatus;
import com.AL.xml.common.OpStatusType;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument;
import com.AL.xml.v4.orderProvisioning.CreditQualificationRequest;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument.OrderProvisioningResponse;

@Component(CREDIT_QUAL_SERVICE)
public class CreditQualService implements OpService {

	private static final Logger logger = Logger.getLogger(CreditQualService.class);
	
	public String getName() {
		return CREDIT_QUAL_SERVICE;
	}
	
	@Autowired
	CustomerDao customerDao;

	public OrderProvisioningResponseDocument execute(OrderProvisioningRequestDocument opRequestDoc) throws Exception {
		logger.info("CreditQualification started with GUID: "+opRequestDoc.getOrderProvisioningRequest().getCorrelationId());
		OrderProvisioningResponse opResponse = ProvisioningStrategy.INSTANCE.sendRtimMessage(opRequestDoc.getOrderProvisioningRequest());
		OpResponseStatus responseStatus = opResponse.getResponseStatus();
		if(responseStatus.getStatus() != OpStatusType.ERROR && 
				opRequestDoc.getOrderProvisioningRequest().getPersistCustomerInfo()) {
			logger.info("Updating customer information");
			CreditQualificationRequest cqRequest = (CreditQualificationRequest)opRequestDoc.getOrderProvisioningRequest().getRequest();
			CustomerManagementRequestResponseDocument cmRequestDoc = CustomerInfoMapper.INSTANCE.createCMRequestDoc(
					opRequestDoc.getOrderProvisioningRequest(), customerDao, cqRequest.getCustomerInfo());
			logger.debug("CM request: "+cmRequestDoc.xmlText());
			try {
				CustomerManagementRequestResponseDocument cmResponseDoc = OmeCustomerCommunicator.submitCustomer(cmRequestDoc.xmlText());
				com.AL.xml.v4.StatusType cmStatus = cmResponseDoc.getCustomerManagementRequestResponse().getStatus();
				OpResponseStatus cmResponseStatus = ProvisionHandlerUtil.getOpResponseStatus(cmStatus);
				logger.info("CM response status: "+cmStatus.getStatusMsg());
				responseStatus.getOpMessageList().addAll(cmResponseStatus.getOpMessageList());
				if(cmResponseStatus.getStatus() != OpStatusType.INFO && cmResponseStatus.getStatus() != OpStatusType.SUCCESS) {
					responseStatus.setStatus(cmResponseStatus.getStatus());
					responseStatus.getOpMessageList().addAll(cmResponseStatus.getOpMessageList());
				}
			} catch(Exception e) {
				logger.error("Error occured in updating customer information in CM. "+ e);
				responseStatus.setStatus(OpStatusType.ERROR);
				OpMessage opMsg = responseStatus.addNewOpMessage();
				opMsg.setText(e.getMessage());
				opMsg.setCode("1");
				responseStatus.getOpMessageList().add(opMsg);
			}
		}
		OrderProvisioningResponseDocument opResponseDoc = OrderProvisioningResponseDocument.Factory.newInstance();
		opResponseDoc.setOrderProvisioningResponse(opResponse);
		return opResponseDoc;
	}
}