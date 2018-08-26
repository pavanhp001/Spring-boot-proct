package com.AL.op.service.impl;

import static com.AL.op.ProvisionConstants.ORDER_QUAL_SERVICE;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.ome.OmeCustomerCommunicator;
import com.AL.op.mapper.CustomerInfoMapper;
import com.AL.op.mapper.OrderServiceMapper;
import com.AL.op.service.OpService;
import com.AL.op.strategy.ProvisioningStrategy;
import com.AL.op.util.ProvisionHandlerUtil;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.ws.WSHandler;
import com.AL.xml.common.OpResponseStatus;
import com.AL.xml.common.OpStatusType;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.StatusType;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument;
import com.AL.xml.v4.orderProvisioning.OrderQualificationRequest;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument.OrderProvisioningRequest;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument.OrderProvisioningResponse;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument;
import com.AL.util.OmeSpringUtil;
import static com.AL.op.ProvisionConstants.*;

@Component(ORDER_QUAL_SERVICE)
public class OrderQualService implements OpService{
	
private static final Logger logger = Logger.getLogger(OrderQualService.class);
	
	public String getName() {
		return ORDER_QUAL_SERVICE;
	}	
	
	@Autowired
	CustomerDao customerDao;
	
	public OrderProvisioningResponseDocument execute(OrderProvisioningRequestDocument opRequestDoc) throws Exception {
		logger.info("OrderQualification started with GUID: "+opRequestDoc.getOrderProvisioningRequest().getCorrelationId());
		OrderProvisioningResponse opResponse = ProvisioningStrategy.INSTANCE.sendRtimMessage(opRequestDoc.getOrderProvisioningRequest());
		OpResponseStatus responseStatus = opResponse.getResponseStatus();
		if(responseStatus.getStatus() != OpStatusType.ERROR) {
			boolean persistCustomerInfoSuccess = true;
			if(opRequestDoc.getOrderProvisioningRequest().getPersistCustomerInfo()) {
				logger.info("Updating customer information");
				OrderQualificationRequest oqRequest = (OrderQualificationRequest)opRequestDoc.getOrderProvisioningRequest().getRequest();
				CustomerManagementRequestResponseDocument cmRequestDoc = CustomerInfoMapper.INSTANCE.createCMRequestDoc(
						opRequestDoc.getOrderProvisioningRequest(), customerDao, oqRequest.getCustomerInfo());
				logger.info("CM request: "+cmRequestDoc.xmlText());
				CustomerManagementRequestResponseDocument cmResponseDoc = OmeCustomerCommunicator.submitCustomer(cmRequestDoc.xmlText());
				StatusType cmStatus = cmResponseDoc.getCustomerManagementRequestResponse().getStatus();
				logger.info("CM response status: "+cmStatus.getStatusMsg());
				OpResponseStatus cmResponseStatus = ProvisionHandlerUtil.getOpResponseStatus(cmStatus);
				responseStatus.getOpMessageList().addAll(cmResponseStatus.getOpMessageList());
				if(cmResponseStatus.getStatus() != OpStatusType.INFO && cmResponseStatus.getStatus() != OpStatusType.SUCCESS) {
					responseStatus.setStatus(cmResponseStatus.getStatus());
					responseStatus.getOpMessageList().addAll(cmResponseStatus.getOpMessageList());
					persistCustomerInfoSuccess = false;
				}
			}
			
			boolean persistProductInfoSuccess = true;
			if(opRequestDoc.getOrderProvisioningRequest().getPersistProductInfo() && persistCustomerInfoSuccess) {
				logger.info("Started updating product lineItem from orderProvisioning service");
				StatusType updateProductStatus = persistOmeEntity(opRequestDoc.getOrderProvisioningRequest(), PersistEntityType.PRODUCTLINEITEM);
				OpResponseStatus omePrResponseStatus = ProvisionHandlerUtil.getOpResponseStatus(updateProductStatus);
				responseStatus.getOpMessageList().addAll(omePrResponseStatus.getOpMessageList());
				if(omePrResponseStatus.getStatus() != OpStatusType.INFO && omePrResponseStatus.getStatus() != OpStatusType.SUCCESS) {
					responseStatus.setStatus(omePrResponseStatus.getStatus());
					responseStatus.getOpMessageList().addAll(omePrResponseStatus.getOpMessageList());
					persistProductInfoSuccess = false;
				}
			}	
			if(opRequestDoc.getOrderProvisioningRequest().getPersistPromoInfo() && 
					persistCustomerInfoSuccess && persistProductInfoSuccess) {
				//TODO persist promotion
			}
		}
		OrderProvisioningResponseDocument opResponseDoc = OrderProvisioningResponseDocument.Factory.newInstance();
		opResponseDoc.setOrderProvisioningResponse(opResponse);
		return opResponseDoc;
	}	
	
	@SuppressWarnings("unchecked")
	public StatusType persistOmeEntity(OrderProvisioningRequest opRequest, PersistEntityType entityType) {
		OrderManagementRequestResponseDocument ormRRDoc = OrderServiceMapper.INSTANCE.createOrmRequestResponseDoc(
				opRequest, entityType);
		logger.debug("Request xml for updating "+entityType.getName());
		logger.debug(ormRRDoc.xmlText());
		WSHandler<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> taskHandler = 
			(WSHandler<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument>) 
				OmeSpringUtil.INSTANCE.getBean("orderManagementWSHandler");
		if (taskHandler == null) {
			logger.debug("unable to create task handler orderManagementWSHandler:");
			throw new IllegalArgumentException(
					"unable to create task handler orderManagementWSHandler:");
		} else {
			logger.debug("created."
					+ taskHandler.getClass().getCanonicalName());
		}
		StatusType status = null;
		try {
			OrderManagementRequestResponseDocument response = taskHandler
					.execute(ormRRDoc);
			status = response.getOrderManagementRequestResponse().getStatus();
			logger.info("Status of updating " + entityType.getName() + status.getStatusMsg());
		} catch(Exception e) {
			logger.error("Error occured in updating "+ entityType.getName() +" from orderProvisiong service: "+e);
			status = StatusType.Factory.newInstance();
			status.setStatusMsg("ERROR");
			status.setStatusCode(1);
		}
		return status;
	}
}
