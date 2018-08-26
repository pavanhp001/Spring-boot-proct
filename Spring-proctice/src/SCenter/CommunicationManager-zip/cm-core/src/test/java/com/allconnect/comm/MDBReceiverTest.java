package com.AL.comm;
 
import javax.jms.Message;
import javax.jms.MessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.receiver.MDBReceiver;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.comm.message.handler.HandlerImpl;
 
/**
 * @author ebthomas
 * 
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:spring/applicationContext.xml" } )
public class MDBReceiverTest
{ 
    private static String logicalQueueName = "endpoint.verizon.in";
    private MDBReceiver clazzToProcessMessage = new MDBReceiver( HandlerImpl.class, "execute" );
     
    @Test
    public void testMessageDeliverytest() throws Exception
    {   
        String namespace = "pricing";
        CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE.createCommunicationManager(namespace);
         
        communicationManager.listen( logicalQueueName, clazzToProcessMessage );
        
        loopForever(); 
    }

    public void loopForever() throws Exception
    {
        boolean go = true;
        while (go)
        {
            Thread.sleep( 1000 );
           // System.out.println( "wait...."   );
        }
    }
}
