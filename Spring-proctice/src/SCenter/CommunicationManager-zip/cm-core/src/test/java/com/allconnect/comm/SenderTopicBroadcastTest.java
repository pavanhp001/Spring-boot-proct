package com.AL.comm;

 

 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
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
public class SenderTopicBroadcastTest  
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
        
        EMPTY_MAP.put( "providers", "#ATT#DTV#2314635#" );
        final String logicalQueueName = "endpoint.ome.out";
        
        CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE.createCommunicationManager( namespace );
         
        while (true)
        {
        	 System.out.println("sending..."+System.currentTimeMillis());
        	 String payload = getXMLFromFile("src\\main\\resources\\sample_order.xml"); 
             communicationManager.sendBroadcast( logicalQueueName,  payload, EMPTY_MAP );
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
    
    public static String getXMLFromFile( final String fileName )
    {
        
        
        File file = new File( fileName );
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader( new FileReader( file ) );
            String text = null;

            // repeat until all lines is read
            while ( ( text = reader.readLine() ) != null )
            {
                contents.append( text ).append( System.getProperty( "line.separator" ) );
            }

            return contents.toString();

        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( reader != null )
                {
                    reader.close();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        return null;
    }

}
