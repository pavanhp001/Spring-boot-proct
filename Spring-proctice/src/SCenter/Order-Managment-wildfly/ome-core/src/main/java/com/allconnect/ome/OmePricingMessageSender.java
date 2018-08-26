package com.AL.ome;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.SystemProperties;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.PricingRequestResponseDocument;

public class OmePricingMessageSender
{

    private static Logger logger = Logger.getLogger( OmePricingMessageSender.class );
    private static final String PRICING_REQUEST_RESPONSE_TYPE = "PricingRequestResponseDocument";
    private static String namespace = "ome/pricing";
    private static CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE
            .createCommunicationManager( namespace );
    //private static final int TIME_OUT = 30000;
    private static int TIME_OUT = 10000;
    private static String logicalQueueName = "endpoint.pricing.in";

    private static String startElement = "<ac:acMessage xmlns:ac=\"http://xml.AL.com/v4\""
            + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:schemaLocation=\"http://xml.AL.com/v4/acMessageWrapper.xsd \"" + ">"
            + " <ac:source>source</ac:source><ac:msgType>request</ac:msgType><ac:actionType>query</ac:actionType>"
            + " <ac:payloadType>PricingRequestResponseDocument</ac:payloadType><ac:payload>";

    private static String endElement = "</ac:payload></ac:acMessage>";

    private static String submitOrderToPricing( String inputXml )
    {
        String responseXml = "";
        if ( inputXml != null )
        {
            Message message;
            try
            {
                long startTime = System.currentTimeMillis();
                TIME_OUT = SystemPropertiesRepo.getSystemPropertyValue("OME.pricing.timeout");
                logger.info("pricing.timeout value from db : " + TIME_OUT);
                logger.info( "Sending message to Pricing" );
                message = communicationManager.sendSync( logicalQueueName, inputXml, TIME_OUT );

                if ( ( message != null ) && ( message instanceof TextMessage ) )
                {

                    long totalTime = System.currentTimeMillis() - startTime;
                    logger.info( "Pricing service request completed in : " + totalTime +"ms" );
                    responseXml = ( (TextMessage) message ).getText();
                    logger.debug( "Response from Pricing : " + responseXml);
                }else{
                	logger.error("Did not received response from Pricing service!!!");
                }
            }
            catch ( Exception e )
            {
                logger.error("Got exception while sending request to Pricing Service" , e);
            }
        }
        return responseXml;
    }

	public static PricingRequestResponseDocument getPricedOrder( String inputXml)
    {
        inputXml = startElement + inputXml + endElement;
        logger.debug( "Prepared request for Pricing : "  );
        logger.debug( inputXml );
        String response = submitOrderToPricing( inputXml );

        PricingRequestResponseDocument pricedDoc = null;
        try
        {
        	logger.info("Parsing Pricing service response");
            // Parse the incoming XML into the doc...
            AcMessageDocument requestDocument = AcMessageDocument.Factory.parse( response );

            // Then pull out the ServiceabiltyRequest/Response doc...
            AcMessageType message = requestDocument.getAcMessage();
            String payloadType = message.getPayloadType();

            if ( payloadType != null && payloadType.equalsIgnoreCase( PRICING_REQUEST_RESPONSE_TYPE ) )
            {
                AcMessageType.Payload msgPayload = message.getPayload();
                String temp = msgPayload.xmlText();

                // Extract the incoming response as PricingRequestResponseDocument
                pricedDoc = PricingRequestResponseDocument.Factory.parse( temp );
            }

        }
        catch ( XmlException e )
        {
            logger.error( "Exception thrown while parsing response from Pricing : ",e);

        }
        catch ( NullPointerException npe )
        {
            logger.error( "Exception thrown while parsing response from Pricing : ",npe);
        }
        return pricedDoc;
    }


}
