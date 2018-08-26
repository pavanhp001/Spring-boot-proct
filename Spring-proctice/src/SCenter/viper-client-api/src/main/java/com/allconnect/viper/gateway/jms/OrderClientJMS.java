package com.A.V.gateway.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.util.JMSConfigManager;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.ClientService;
import com.A.V.gateway.OrderClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.v4.OrderManagementRequestResponse;

public class OrderClientJMS extends
		BaseClientJMS<OrderManagementRequestResponse> implements
		ClientService<OrderManagementRequestResponse>, OrderClient {

	private static final Logger logger = Logger.getLogger(OrderClientJMS.class);
	private static final int TIMEOUT = 120000;
	private static final String ORDER_NAMESPACE = "jms";
	private static final String END_POINT_NAME = "endpoint.ome.in";
	private static final int NUMBER_OF_RETRY = 3;
	private static final String PAYLOAD_START = "<ac:payload>";
	private static final int PAYLOAD_START_LENGTH = PAYLOAD_START.length();
	private final String GU_ID = "GUID";

	private static final JaxbUtil<OrderManagementRequestResponse> util = new JaxbUtil<OrderManagementRequestResponse>();

	private static final CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(ORDER_NAMESPACE);

	@Override
	public OrderManagementRequestResponse send(
			OrderManagementRequestResponse omrr) {

		//TODO:Remove
		if ((omrr != null) && (omrr.getRequest() != null) && ((omrr.getRequest().getAgentId() == null) ||(omrr.getRequest().getAgentId().length() == 0) )) {
			omrr.getRequest().setAgentId("default");
		}

		TextMessage responseFromRTIM = null;
		OrderManagementRequestResponse response = null;

		if (omrr == null) {
			return null;
		}

		try {
			
			Map<String,String> headers = new HashMap<String,String>();
			if(omrr.getRequest().getSalesContext() != null){
				String guid = SalesContextFactory.INSTANCE.getAttribute(omrr.getRequest().getSalesContext(), GU_ID);
				headers.put(GU_ID, guid);
			}
			
			if(StringUtils.isBlank(headers.get(GU_ID))) {
				logger.info("GUID is empty in SalesContext, sending OrderId as GUID to Comm Manager ........ ");
				headers.put(GU_ID, omrr.getRequest().getOrderId());
			}
			
			String orderRequestResponseAsString = util.toString(omrr,
					OrderManagementRequestResponse.class);

			logger.info("OMRR JMS Request[ID: "+omrr.getCorrelationId()+"] :: "+orderRequestResponseAsString);
			
			long currentTime = System.currentTimeMillis();
			responseFromRTIM = (TextMessage) commManager.sendSync(
					END_POINT_NAME, orderRequestResponseAsString, TIMEOUT, headers);

			logger.info("OMRR JMS Response[ID: "+omrr.getCorrelationId()+"][Time: "+(System.currentTimeMillis() - currentTime) + "ms] :: "+responseFromRTIM.getText());

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (responseFromRTIM != null) {
			try {
				response = util.toObject(
						extract(responseFromRTIM.getText()),
						OrderManagementRequestResponse.class);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		return response;
	}

	public String extract(String orderRR) {
		int indexStart = orderRR.indexOf(PAYLOAD_START) + PAYLOAD_START_LENGTH;
		int indexEnd = orderRR.indexOf("</ac:payload>");

		if ((indexStart != -1) && (indexEnd != -1)) {
			return orderRR.substring(indexStart, indexEnd);
		}
		return orderRR;

	}
	
	
	/**
     * This method will send OME request asynchronously. So use this method only if you dont need res back from
     * OME. This method can be used in Salescenter to send aynch request like at the end of the call to update
     * session status completed flag. Or adding home depot lineitem(Savers offers) etc.
     *
     *
     */
     @Override
     public void sendAsync(OrderManagementRequestResponse req) {

           if( null == req)
                throw new IllegalArgumentException("Request cannot be null!!!");


           try {

                Map<String,String> headers = new HashMap<String,String>();
                if(req.getRequest().getSalesContext() != null){
                     String guid = SalesContextFactory.INSTANCE.getAttribute(req.getRequest().getSalesContext(), GU_ID);
                     headers.put(GU_ID, guid);
                }

                if(StringUtils.isBlank(headers.get(GU_ID))) {
                     String uuid = UUID.randomUUID().toString();
                     logger.info("GUID is empty in SalesContext, creating new one.");
                     headers.put(GU_ID, uuid);
                     req.setCorrelationId(uuid);
                }

                String orderRequestResponseAsString = util.toString(req,
                           OrderManagementRequestResponse.class);

                logger.info("Sending async request to OME");

                commManager.send(END_POINT_NAME, orderRequestResponseAsString,headers);

                logger.info("Async request to OME completed");
           } catch (Exception e) {
                logger.error("Exception occured while sending async request to OME.", e);
           }


     }

	
}