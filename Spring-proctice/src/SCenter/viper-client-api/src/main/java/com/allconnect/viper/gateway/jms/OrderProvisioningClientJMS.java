package com.A.V.gateway.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.util.JMSConfigManager;
import com.A.V.gateway.OrderProvisioningClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.v4.AuthenticateCustomerRequest;
import com.A.xml.v4.AuthenticateCustomerResponse;
import com.A.xml.v4.CreditQualificationRequest;
import com.A.xml.v4.CreditQualificationResponse;
import com.A.xml.v4.OrderProvisioningRequest;
import com.A.xml.v4.OrderProvisioningResponse;

public class OrderProvisioningClientJMS implements OrderProvisioningClient {
	private static final Logger logger = Logger.getLogger(OrderClientJMS.class);
	private static final String ORDER_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.op.in";
	private static final int TIMEOUT = 120000;
	private static final String GUID = "GUID";
	private static final String PAYLOAD_START = "<ac:payload>";
	private static final int PAYLOAD_START_LENGTH = PAYLOAD_START.length();

	private static final JaxbUtil<OrderProvisioningRequest> opRequestUtil = new JaxbUtil<OrderProvisioningRequest>();
	private static final JaxbUtil<OrderProvisioningResponse> opResponseUtil = new JaxbUtil<OrderProvisioningResponse>();

	private static final CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(ORDER_NAMESPACE);

	private static final Class[] reqClassesToBeBound = { AuthenticateCustomerRequest.class,
			CreditQualificationRequest.class };

	private static final Class[] respClassesToBeBound = { AuthenticateCustomerResponse.class,
			CreditQualificationResponse.class };

	@Override
	public String extract(String opRequest) {
		int indexStart = opRequest.indexOf(PAYLOAD_START) + PAYLOAD_START_LENGTH;
		int indexEnd = opRequest.indexOf("</ac:payload>");

		if ((indexStart != -1) && (indexEnd != -1)) {
			return opRequest.substring(indexStart, indexEnd);
		}
		return opRequest;

	}

	@Override
	public OrderProvisioningResponse send(OrderProvisioningRequest opRequest) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(GUID, opRequest.getCorrelationId());

		String opRequestAsString = opRequestUtil.toStringWithBoundClass(opRequest,
				OrderProvisioningRequest.class, reqClassesToBeBound);
		OrderProvisioningResponse opResponse = null;

		long currentTime = System.currentTimeMillis();
		TextMessage responseFromRTIM = null;
		try {
			System.out.println(opRequestAsString);
			responseFromRTIM = (TextMessage) commManager.sendSync(END_POINT_NAME, opRequestAsString, TIMEOUT,
					headers);
			System.out.println(responseFromRTIM);
			logger.info("OPR JMS Response[ID: " + opRequest.getCorrelationId() + "][Time: "
					+ (System.currentTimeMillis() - currentTime) + "ms] :: " + responseFromRTIM.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (responseFromRTIM != null) {
			try {
				opResponse = opResponseUtil.toObjectWithBoundClass(extract(responseFromRTIM.getText()),
						OrderProvisioningResponse.class, respClassesToBeBound);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return opResponse;
	}

}
