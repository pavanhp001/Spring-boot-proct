package com.AL.ie.service.strategy;

import java.util.HashMap;
import java.util.Map;

import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.ie.service.ResponseLibrary;
import com.AL.util.OmeSpringUtil;
import com.AL.Vdao.util.ProviderConfigCache;
import com.AL.Vdao.util.ProviderConfigVO;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequest;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequestDocument;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponseDocument;

public enum SendStrategy {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(SendStrategy.class);
	private static int TIMEOUT = 120000;
	private final String RTIM_NAMESPACE = "ome/integration";


	CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(RTIM_NAMESPACE);

	private static Map<String, ProviderConfigVO> providerConfigMap = new HashMap<String, ProviderConfigVO>();

	static {
		ProviderConfigCache configCache = (ProviderConfigCache)OmeSpringUtil.INSTANCE.
				getBean("providerConfigCache");
		providerConfigMap = configCache.getProviderConfigMap();
	}

	public Message<?> onMessage(Message<?> m) {

		Object obj = m.getPayload();

		if (obj == null) {
			return m;
		}

		if (obj instanceof String) {
			return onStringMessage(m);
		}

		else if (obj instanceof OrderFulfillmentRequestDocument) {
			return onOrderMessage(m);
		}

		else
			throw new IllegalArgumentException("invalid data type:"
					+ obj.getClass().getCanonicalName());
	}

	public Message<?> onOrderMessage(final Message<?> message) {

		long start = System.currentTimeMillis();
		Object obj = message.getPayload();
		OrderFulfillmentRequestDocument payload = (OrderFulfillmentRequestDocument) obj;
		OrderFulfillmentRequest doc = payload.getOrderFulfillmentRequest();

		org.springframework.integration.MessageHeaders headers = message
				.getHeaders();
		Message<?> resultMessage = null;
		TextMessage responseFromRTIM = null;
		try {


			if (doc != null && commManager != null) {

				String orderFulfillmentRequestAsString = payload
						.xmlText(new XmlOptions().setSaveOuter());

				Map<String, String> mapOfData = createInputDataInMap(doc);
				String queueEndpoint = resolveQueueEndpoint(doc);
				logger.info("Resolved queue name for RTIM : " + queueEndpoint);

				orderFulfillmentRequestAsString = TemplateStrategy.INSTANCE
						.getATTOrderQualificationTemplate(mapOfData);
				// *************************************

				TIMEOUT = SystemPropertiesRepo.getSystemPropertyValue("OME.rtim.timeout");
				if(TIMEOUT == 0){
					logger.debug("RTIM timeout is zero in db so falling back to 120000ms");
					TIMEOUT = 120000;
				}
				logger.info("RTIM Timeout : " + TIMEOUT);
				logger.info("Send request to rtim queue : " + queueEndpoint);
				responseFromRTIM = (TextMessage) commManager.sendSync(queueEndpoint, orderFulfillmentRequestAsString,TIMEOUT, true);
			}

			if (responseFromRTIM != null) {
			    logger.info("Received response from RTIM");
				RtimRequestResponseDocument rtimRequestResponseDocument = RtimRequestResponseDocument.Factory
						.parse(responseFromRTIM.getText());

				resultMessage = MessageBuilder
						.withPayload(rtimRequestResponseDocument)
						.copyHeaders(headers).build();

			} else {
				logger.warn("ERROR:unable to get a response from the provider.  possible TIMEOUT, JMS DOWN...:"
						+ message.toString());
				RtimRequestResponseDocument rtimRequestResponseDocument  = ResponseLibrary.INSTANCE.getNoResponseTemplate(doc);

				resultMessage = MessageBuilder
						.withPayload(rtimRequestResponseDocument)
						.copyHeaders(headers).build();

			}
			logger.info("RTIM communication completed in " + (System.currentTimeMillis() - start) + "ms");
			return resultMessage;
		} catch (Exception e) {
			logger.warn(e);
		}

		return resultMessage;

	}

	/**
	 * Based on provider external id, returns provider queue name.
	 * @param doc
	 * @return
	 */
	private String resolveQueueEndpoint(OrderFulfillmentRequest doc) {
		String providerExtId = ResolveProviderName.resolveProvider(doc);
		//ProOmeSpringUtil.INSTANCE.getBean("providerConfigCache");
		//return providersQueueMap.get(providerId);
		return providerConfigMap.get(providerExtId).getQueueName();
	}




	final Map<String, String> createInputDataInMap(
			final OrderFulfillmentRequest doc) {

		Map<String, String> data = new HashMap<String, String>();

		String correlationId = doc.getOrderManagementRequestResponse()
				.getCorrelationId();
		String sessionId = doc.getOrderManagementRequestResponse()
				.getSessionId();
		String orderNumber = doc.getOrderManagementRequestResponse()
				.getRequest().getOrderId();

		String salesCode = doc.getOrderManagementRequestResponse()
				.getSalesCode();

		String affiliateName = doc.getOrderManagementRequestResponse()
				.getAffiliateName();

		String region = doc.getOrderManagementRequestResponse().getRegion();
		String orderRRDocument = doc.getOrderManagementRequestResponse()
				.xmlText(new XmlOptions().setSaveOuter());

		String methodName = ResolveProviderMethodStrategy
				.resolveInvokeMethodName(doc);
		//String providerName = ResolveProviderName.resolveInvokeMethodName(doc);
		String providerExtId = ResolveProviderName.resolveProvider(doc);
		String providerName = providerConfigMap.get(providerExtId).getProviderName();

		if (sessionId == null) {
			sessionId = String.valueOf(System.currentTimeMillis());
		}

		data.put("methodName", methodName);
		data.put("providerId", providerName);
		data.put("correlationId", correlationId);
		data.put("sessionId", sessionId);
		data.put("orderNumber", orderNumber);
		data.put("region", region);
		data.put("orderRRDocument", orderRRDocument);
		data.put("salesCode", salesCode);
		data.put("affiliateName", affiliateName);

		return data;

	}

	public Message<?> onStringMessage(final Message<?> message) {

		return message;
	}

}
