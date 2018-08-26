package com.AL.comm;
 

import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;

/**
 *
 */

/**
 * @author ebthomas
 * 
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:spring/applicationContext.xml" } )
public class SenderHeaderUpdateTest  
{
 
	private static final Map<String, String> EMPTY_MAP = new HashMap<String, String>();

    @Test
    public void testMessageDeliverytest() throws Exception
    { 
        // Start sending messages
        loopForever();
        System.out.println( "completed sending all messages" );
    }

    public void loopForever() throws JMSException, Exception
    {
        final String namespace = "topic";
        
        final String logicalQueueName = "endpoint.ome.out";
        
        CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE.createCommunicationManager( namespace );
         
        while (true)
        {
        	 System.out.println("sending..."+System.currentTimeMillis());
        	  
             communicationManager.sendBroadcast( logicalQueueName,  getPayload(), EMPTY_MAP );
            Thread.sleep( 100 );
        }
    }

    
    public String getPayload()
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " );
        sb.append( " xmlns:ord=\"http://verizon.com/orderingServices/\">  " );
        sb.append( "<soapenv:Header/> " );
        sb.append( "<soapenv:Body> " );
        sb.append( "<ord:createSession> " );
        sb.append( "<ord:client> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:name>?</ord:name> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:clientId>?</ord:clientId> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:requestId>?</ord:requestId> " );
        sb.append( "</ord:client> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:applicationId>?</ord:applicationId> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:_accountType>?</ord:_accountType> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:clientId>?</ord:clientId> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:salesOffice>?</ord:salesOffice> " );
        sb.append( "<!--Optional:--> " );
        sb.append( "<ord:salesAgentId>?</ord:salesAgentId> " );
        sb.append( "</ord:createSession> " );
        sb.append( "</soapenv:Body> " );
        sb.append( "  </soapenv:Envelope> " );

        return sb.toString();
    }
}
