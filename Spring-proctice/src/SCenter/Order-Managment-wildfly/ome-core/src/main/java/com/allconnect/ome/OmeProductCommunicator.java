package com.AL.ome;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.springframework.stereotype.Component;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.ProductEnterpriseResponseDocument;

public class OmeProductCommunicator
{

	private static Logger logger = Logger.getLogger( OmeProductCommunicator.class );
	private static String namespace = "ome/product";
	private static CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE
			.createCommunicationManager( namespace );
	private static int TIME_OUT = 10000;
	private static String logicalQueueName = "endpoint.product.in";
	private static String startElement = "<ac:acMessage xmlns:ac=\"http://xml.AL.com/v4\""
			+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
			+ " xsi:schemaLocation=\"http://xml.AL.com/v4/acMessageWrapper.xsd \"" + ">"
			+ " <ac:source>source</ac:source><ac:msgType>request</ac:msgType><ac:actionType>query</ac:actionType>"
			+ " <ac:payloadType>ProductRequestDocument</ac:payloadType><ac:payload>";

	private static String endElement = "</ac:payload></ac:acMessage>";

	public static ProductEnterpriseResponseDocument getProducts( String inputXml )
	{
		logger.debug( "Sending request to Product service" );
		inputXml = startElement + inputXml + endElement;
		String response = sendRequestToProductService( inputXml );

		ProductEnterpriseResponseDocument productResponse = null;
		try
		{
			logger.info("Processing product service response");
			// Parse the incoming XML into the doc...
			AcMessageDocument requestDocument = AcMessageDocument.Factory.parse( response );

			// Then pull out the ServiceabiltyRequest/Response doc...
			AcMessageType message = requestDocument.getAcMessage();
			String payloadType = message.getPayloadType();

			if ( payloadType != null && payloadType.equalsIgnoreCase( "ProductResponseDocument" ) )
			{
				AcMessageType.Payload msgPayload = message.getPayload();
				String temp = msgPayload.xmlText();

				productResponse = ProductEnterpriseResponseDocument.Factory.parse( temp );
			}

		}
		catch ( XmlException e )
		{
			logger.error( "Exception thrown while parsing response from Products : ", e );

		}
		catch ( NullPointerException npe )
		{
			logger.error( "Exception thrown while parsing response from Products : ", npe );
		}
		return productResponse;
	}

	private static String sendRequestToProductService( String inputXml )
	{
		String responseXml = "";
		if ( inputXml != null )
		{
			Message message;
			try
			{
				long startTime = System.currentTimeMillis();
				TIME_OUT = SystemPropertiesRepo.getSystemPropertyValue("OME.product.timeout");
				logger.info("product.timeout value from db : " + TIME_OUT);
				logger.debug( "Request : " + inputXml );
				message = communicationManager.sendSync( logicalQueueName, inputXml, TIME_OUT );

				if ( ( message != null ) && ( message instanceof TextMessage ) )
				{

					long totalTime = System.currentTimeMillis() - startTime;
					logger.info( "Product service request completed in : " + totalTime +"ms");
					responseXml = ( (TextMessage) message ).getText();
					logger.debug( "Response from Products Service : " + responseXml );
				}else{
					logger.error("Did not received response from product service!!!");
				}
			}
			catch ( Exception e )
			{
				logger.error( "Got exception while sending request to Products Service", e );
			}
		}
		return responseXml;
	}

}
