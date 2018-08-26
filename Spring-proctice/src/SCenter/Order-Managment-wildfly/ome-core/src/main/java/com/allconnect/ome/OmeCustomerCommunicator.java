package com.AL.ome;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument;

public class OmeCustomerCommunicator {

	private static Logger logger = Logger
			.getLogger(OmeCustomerCommunicator.class);
	// private static GsonBuilder gb = new GsonBuilder();

	private static final String CUSTOMER_REQUEST_RESPONSE_TYPE = "CustomerResponseDocument";
	private static String namespace = "ome/customer";
	private static CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(namespace);
	private static int TIME_OUT = 10000;

	private static String logicalQueueName = "endpoint.customer.in";

	private static String startElement = "<ac:acMessage xmlns:ac=\"http://xml.AL.com/v4\""
			+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
			+ " xsi:schemaLocation=\"http://xml.AL.com/v4/acMessageWrapper.xsd \""
			+ ">"
			+ " <ac:source>source</ac:source><ac:msgType>request</ac:msgType><ac:actionType>query</ac:actionType>"
			+ " <ac:payloadType>CustomerManagementRequestResponseDocument</ac:payloadType><ac:payload>";

	private static String endElement = "</ac:payload></ac:acMessage>";


	/**
	 * Preparing a customer request and sumitting to Customer service. Parsing the response received from Customer Service
	 */
	public static CustomerManagementRequestResponseDocument submitCustomer(String inputXml){
		logger.debug("preparing customer for service");
		inputXml = startElement + inputXml + endElement;
		logger.debug("submitting customer to service");
		String responseXml = submitConsumerToCM(inputXml);

		 CustomerManagementRequestResponseDocument custDoc = null;
	        try
	        {

	            logger.debug("Parsing response from Customer Service...");
	            AcMessageDocument requestDocument = AcMessageDocument.Factory.parse( responseXml );

	            // Then pull out the Request/Response doc...
	            AcMessageType message = requestDocument.getAcMessage();
	            String payloadType = message.getPayloadType();

	            if ( payloadType != null && payloadType.equalsIgnoreCase( CUSTOMER_REQUEST_RESPONSE_TYPE ) )
	            {
	                AcMessageType.Payload msgPayload = message.getPayload();
	                String temp = msgPayload.xmlText();

	                // Extract the incoming response as CustomerManagementRequestResponseDocument
	                custDoc = CustomerManagementRequestResponseDocument.Factory.parse( temp );
	            }

	        }
	        catch ( XmlException e )
	        {
	            logger.error( "Exception thrown while parsing response from Customer Service: "+e.getError(),e);

	        }
	        catch ( NullPointerException npe )
	        {
	            logger.error( "Exception thrown while parsing response from Customer Service : ", npe );
	        }

	        return custDoc;

	}

	/**
	 * Method to prepare payload to send over JMS queue for Customer Service and
	 * return the response back to client
	 *
	 * @param inputXml
	 * @return
	 */
	private static String submitConsumerToCM(String inputXml) {
		logger.debug("preparing payload...");
		// inputXml = startElement + inputXml + endElement;
		String responseXml = "";
		if (inputXml != null) {
			Message message;
			try {
				logger.debug("Sending message to Customer Service.");
				long startTime = System.currentTimeMillis();
				TIME_OUT = SystemPropertiesRepo.getSystemPropertyValue("OME.customer.timeout");
				logger.info("customer.timeout value from db : " + TIME_OUT);
				message = communicationManager.sendSync(logicalQueueName,
						inputXml, TIME_OUT);
				if ((message != null) && (message instanceof TextMessage)) {
					long totalTime = System.currentTimeMillis() - startTime;
					logger.debug("Request completed in : " + totalTime + "ms");
					responseXml = ((TextMessage) message).getText();
					logger.debug("Response from Customer Service : " + responseXml);
				}
			} catch (Exception e) {
				logger.error(
						"Got exception while sending request to Customer service",
						e);
			}
		}
		return responseXml;
	}

}
