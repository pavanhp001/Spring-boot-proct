package com.AL.comm;
 
import javax.jms.Message;
import javax.jms.MessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.receiver.MDBReceiverSender;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.comm.message.handler.HandlerImpl;
 
/**
 * @author ebthomas
 * 
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:spring/applicationContext.xml" } )
public class MDBReceiverSenderTest
{ 
    private static String QUEUE = "endpoint.verizon.in";
    private MDBReceiverSender clazzToProcessMessage = new MDBReceiverSender( HandlerImpl.class, "execute" );
     
    @Test
    public void testMessageDeliverytest() throws Exception
    {   
        String namespace = "pricing";
        CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE.createCommunicationManager(namespace);
         
        communicationManager.listen( QUEUE, clazzToProcessMessage );
        
        loopForever(); 
    }

    public void loopForever() throws Exception
    {
        boolean go = true;
        while (go)
        {
            Thread.sleep( 500 );
            System.out.println( "wait...."   );
        }
    }
}
