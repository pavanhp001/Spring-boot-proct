package com.A.V.gateway.jms;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.util.JMSConfigManager;
import com.A.V.gateway.ClientService;
import com.A.V.gateway.CustomerClient;
import com.A.V.gateway.DetailClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.CustomerManagementRequestResponse.Request;
import com.A.xml.dtl.v4.DetailsRequestResponse;

public class DetailClientJMS extends
		BaseClientJMS<DetailsRequestResponse> implements
		ClientService<DetailsRequestResponse>, DetailClient {

	private static final Logger logger = Logger
			.getLogger(DetailClientJMS.class);
	private static final int TIMEOUT = 60000;
	//TODO: Place these into system properties
	private final String CUSTOMER_NAMESPACE = "jms";
	private final String END_POINT_NAME = "endpoint.detail.in";

	private final JaxbUtil<DetailsRequestResponse> util = new JaxbUtil<DetailsRequestResponse>();

	private final CommunicationManager<javax.jms.Message, MessageListener> commManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(CUSTOMER_NAMESPACE);

	public DetailsRequestResponse send(DetailsRequestResponse order) {
		
		TextMessage responseFromRTIM = null;

		String detailRequestResponseAsString = util.toString(order,DetailsRequestResponse.class);
		
		logger.info(detailRequestResponseAsString);
		
		DetailsRequestResponse response =  null;

		try {
			responseFromRTIM = (TextMessage) commManager.sendSync(
					END_POINT_NAME, detailRequestResponseAsString, TIMEOUT);
		

		if (responseFromRTIM == null) {
			return null;
		}
		  response = util.toObject(
				extract(responseFromRTIM.getText()),
				DetailsRequestResponse.class);
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return response;

	}

	public String extract(String orderRR) {

		orderRR = orderRR
				.replace(
						"<detailsRequestResponse>",
						"<detailsRequestResponse xmlns=\"http://xml.A.com/v4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		int indexStart = orderRR.indexOf("<detailsRequestResponse");
		int indexEnd = orderRR.indexOf("</payload>");

		if (indexEnd == -1) {
			indexEnd = orderRR.indexOf("</xml-fragment>");
		}

		if ((indexStart != -1) && (indexEnd != -1)) {

			return orderRR.substring(indexStart, indexEnd);
		}

		return orderRR;

	}

}
