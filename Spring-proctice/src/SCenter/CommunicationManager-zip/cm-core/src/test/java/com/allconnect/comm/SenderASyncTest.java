package  com.AL.comm;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.comm.AbstractCommTest;
/**
 *
 */

/**
 * @author ebthomas
 * 
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:spring/applicationContext.xml" } )
public class SenderASyncTest extends AbstractCommTest
{
 

    @Test
    public void testMessageDeliverytest() throws Exception
    { 
        // Start sending messages
        loopForever();
        System.out.println( "completed sending all messages" );
    }

    public void loopForever() throws JMSException, Exception
    {
        //final String namespace = "ome";
        final String namespace = "pricing";
        
        final String logicalQueueName = "endpoint.verizon.in";
        
        CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE.createCommunicationManager( namespace );
         
        while (true)
        {
        	 
             communicationManager.send( logicalQueueName,  getPayload() );
            Thread.sleep( 10 );
        }
    }

    

}
