package com.AL.op.strategy;

import java.util.HashMap;
import java.util.Map;

import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.op.util.RTIMProviderUtil;
import com.AL.Vdao.util.ProviderConfigVO;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.xml.common.OpMessage;
import com.AL.xml.common.RequestContext;
import com.AL.xml.common.OpResponseStatus;
import com.AL.xml.common.OpStatusType;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument.OrderProvisioningRequest;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningResponseDocument.OrderProvisioningResponse;
import com.AL.xml.v4.orderProvisioning.ProvisioningResponse;
import com.AL.xml.v4.orderProvisioning.RtimProvisioningRequest;
import com.AL.xml.v4.orderProvisioning.RtimProvisioningRequestDocument;
import com.AL.xml.v4.orderProvisioning.RtimProvisioningResponse;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponse;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponseDocument;

import static com.AL.op.ProvisionConstants.*;

public enum ProvisioningStrategy {
	
	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(ProvisioningStrategy.class);
	
	private static int TIMEOUT = 120000;
	private final String RTIM_NAMESPACE = "ome/integration";
	
	private static final XmlOptions XML_OPTIONS = new XmlOptions();
	
	static {
		XML_OPTIONS.setSavePrettyPrint();
		XML_OPTIONS.setSavePrettyPrintIndent(4);
		XML_OPTIONS.setSaveAggressiveNamespaces();
		Map<String, String> nsMap = new HashMap<String, String>();
		nsMap.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		XML_OPTIONS.setSaveSuggestedPrefixes(nsMap);
	}
	
	CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
		.createCommunicationManager(RTIM_NAMESPACE);

	static {
		TIMEOUT = SystemPropertiesRepo.getSystemPropertyValue("OME.rtim.timeout");
		if(TIMEOUT == 0){
			TIMEOUT = 120000;
		}
	}
	
	public OrderProvisioningResponse sendRtimMessage(OrderProvisioningRequest opRequest) throws Exception {
		logger.info("Sending message to rtim for GUID: "+opRequest.getCorrelationId());
		ProviderConfigVO providerConfig = RTIMProviderUtil.getRTIMProviderConfig(opRequest.getProviderExternalId());
		logger.info("Rtim queue: " + providerConfig.getQueueName());
		String rtimRequest = createRtimProvisiongRequest(opRequest, providerConfig.getProviderName());
		logger.debug(rtimRequest);
		TextMessage responseFromRTIM = (TextMessage) commManager.sendSync(providerConfig.getQueueName(), 
				rtimRequest, TIMEOUT, true);
		OrderProvisioningResponse oprResponse = OrderProvisioningResponse.Factory.newInstance();
		oprResponse.setCorrelationId(opRequest.getCorrelationId());
		if(responseFromRTIM != null) {
			logger.info("Rtim response received:");
			logger.debug(responseFromRTIM.getText());
			RtimRequestResponseDocument rtimRequestResponseDoc = RtimRequestResponseDocument.Factory
					.parse(responseFromRTIM.getText());
			RtimProvisioningResponse rtimProvisioningResponse = (RtimProvisioningResponse)
				rtimRequestResponseDoc.getRtimRequestResponse().getResponse();
			ProvisioningResponse prResponse = rtimProvisioningResponse.getProvisioningResponse();
			oprResponse.setResponse(prResponse);
			oprResponse.setResponseStatus(rtimProvisioningResponse.getResponseStatus());
		} else {
			logger.warn("ERROR:unable to get a response from the provider.  possible TIMEOUT, JMS DOWN...:");
			OpResponseStatus responseStatus = oprResponse.addNewResponseStatus();
			responseStatus.setStatusCode(ERROR_CODE);
			responseStatus.setStatus(OpStatusType.ERROR);
			OpMessage opMsg = responseStatus.addNewOpMessage();
			opMsg.setText("ERROR:unable to get a response from the provider.  possible TIMEOUT, JMS DOWN");
			opMsg.setCode("1");
		}
		return oprResponse;
	}

	public String createRtimProvisiongRequest(OrderProvisioningRequest opRequest, String providerName) {
		RtimProvisioningRequestDocument rtimProvisioningRequestDoc = RtimProvisioningRequestDocument.Factory.newInstance();
		RtimProvisioningRequest rtimProvisioningRequest = rtimProvisioningRequestDoc.addNewRtimProvisioningRequest();
		RequestContext context = rtimProvisioningRequest.addNewContext();
		context.setCorrelationId(opRequest.getCorrelationId());
		context.setTransactionType(opRequest.getTransactionType());
		context.setProviderId(providerName);
		context.setOrderNumber(opRequest.getOrderId());
		rtimProvisioningRequest.setProvisioningRequest(opRequest.getRequest());
		RtimRequestResponseDocument rtimReqRespDoc = RtimRequestResponseDocument.Factory.newInstance();
		RtimRequestResponse rtimReqResp = rtimReqRespDoc.addNewRtimRequestResponse();
		rtimReqResp.setRequest(rtimProvisioningRequest);
		return rtimReqRespDoc.xmlText(XML_OPTIONS);
	}

}
