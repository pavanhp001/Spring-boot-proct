package com.AL.op;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import com.AL.op.beans.OpMessageHandlerLocal;
import com.AL.op.util.ProvisionHandlerUtil;
import com.AL.util.OmeSpringUtil;
import com.AL.ws.WSHandler;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument;

public class AbstractOpHandler implements OpMessageHandlerLocal {
	
	private static Logger logger = Logger.getLogger(AbstractOpHandler.class);
	
	@SuppressWarnings("unchecked")
	public static String baseProcessRequest(final String inputXml) {
		long currentTime = System.currentTimeMillis();
		String payload = ProvisionHandlerUtil.extractPayload(inputXml);
		OrderProvisioningRequestDocument opRequestDoc = getOrderProvisioningRequestDoc(payload);
		WSHandler<OrderProvisioningRequestDocument, OrderProvisioningResponseDocument> taskHandler = 
			(WSHandler<OrderProvisioningRequestDocument, OrderProvisioningResponseDocument>) OmeSpringUtil.INSTANCE.getBean("OpWSHandler");
		OrderProvisioningResponseDocument opResponseDoc = taskHandler.execute(opRequestDoc);
		String strResponse = opResponseDoc.xmlText();
		logger.info("OrderProvisioning Request Completed [GUID=" + opRequestDoc.getOrderProvisioningRequest().getCorrelationId() + ", "
				+ "TransactionType="+ opRequestDoc.getOrderProvisioningRequest().getTransactionType()+", "
				+ "Status=" +  opResponseDoc.getOrderProvisioningResponse().getResponseStatus().getStatus()+ ", "
				+ "Time = " + (System.currentTimeMillis() - currentTime) + " ms]");
		return strResponse;
		
	}
	
	/**
	 * @param inputXml
	 *            input xml received from the request
	 * @return RequestResponseDocument for OrderManagement
	 */
	public static OrderProvisioningRequestDocument getOrderProvisioningRequestDoc(
			final String inputXml) {
		logger.debug("Parsing payload to orderProvisioning document");
		OrderProvisioningRequestDocument opRequestDoc = null;
		try {
			opRequestDoc = OrderProvisioningRequestDocument.Factory
					.parse(inputXml);
		} catch (XmlException e) {
			logger.error("Error occured in parsing payload to orderProvisioning document");
			e.printStackTrace();
		}
		return opRequestDoc;
	}
}
