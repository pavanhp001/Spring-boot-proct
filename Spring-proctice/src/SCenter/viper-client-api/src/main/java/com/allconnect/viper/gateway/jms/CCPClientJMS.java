package com.A.V.gateway.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.receiver.Executable;
import com.A.comm.manager.jms.util.JMSConfigManager;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.CCPClient;
import com.A.V.gateway.ClientService;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.v4.CcpRequestResponse;

public class CCPClientJMS extends BaseClientJMS<CcpRequestResponse> implements
ClientService<CcpRequestResponse>, CCPClient {

	private static final Logger logger = Logger.getLogger(CCPClientJMS.class);
	private static final int TIMEOUT = 120000;
	private static final String CCP_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.ccp.in";
	private static final String NS = Executable.MSG_VER_V4;
	private static final String PAYLOAD_START = "<"+NS+":payload>";
	private static final int PAYLOAD_START_LENGTH = PAYLOAD_START.length();
	private final String GU_ID = "GUID";

	private static final JaxbUtil<CcpRequestResponse> util = new JaxbUtil<CcpRequestResponse>();

	private static final CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
	.createCommunicationManager(CCP_NAMESPACE);

	@Override
	public CcpRequestResponse send(CcpRequestResponse ccprr) {
		TextMessage responseText = null;
		CcpRequestResponse response = null;

		if (ccprr == null) {
			return null;
		}

		try {

			Map<String,String> headers = new HashMap<String,String>();
			if(ccprr.getSalesContext() != null){
				String guid = SalesContextFactory.INSTANCE.getAttribute(ccprr.getSalesContext(), GU_ID);
				headers.put(GU_ID, guid);
			}

			if(StringUtils.isBlank(headers.get(GU_ID))) {
				logger.info("GUID is empty in SalesContext, sending OrderId as GUID to Comm Manager ........ ");
				if(ccprr.getRequest().getOrderManagementRequestResponse() != null){
					headers.put(GU_ID, String.valueOf(ccprr.getRequest().getOrderManagementRequestResponse().getExternalId()));
				}
			}
			
			//Adding CCP message headers
			headers.put(Executable.MSG_TYPE_KEY, Executable.CCP_TYPE);
			logger.info("Message Key:"+Executable.MSG_TYPE_KEY+", CCP Type:"+Executable.CCP_TYPE);
			
			headers.put(Executable.MSG_VERSION_KEY, NS);
			logger.info("Message Version Key:"+Executable.MSG_VERSION_KEY+", NS:"+NS);
			
			String ccpRRAsString = util.toString(ccprr,	CcpRequestResponse.class);

			logger.info("CCP JMS Request[ID: "+ccprr.getGUID()+"] :: "+ccpRRAsString);

			long currentTime = System.currentTimeMillis();
			responseText = (TextMessage) commManager.sendSync(END_POINT_NAME, ccpRRAsString, TIMEOUT, headers);

			logger.info("CCP JMS Response[ID: "+ccprr.getGUID()+"][Time: "+(System.currentTimeMillis() - currentTime) + "ms] :: "+responseText.getText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (responseText != null) {
			try {
				response = util.toObject(extract(responseText.getText()), CcpRequestResponse.class);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		return response;
	}

	@Override
	public String extract(String ccpRR) {
		int indexStart = ccpRR.indexOf(PAYLOAD_START) + PAYLOAD_START_LENGTH;
		int indexEnd = ccpRR.indexOf("</"+NS+":payload>");

		if ((indexStart != -1) && (indexEnd != -1)) {
			return ccpRR.substring(indexStart, indexEnd);
		}
		return ccpRR;
	}

}
