package com.AL.comm;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.comm.manager.jms.receiver.PojoReceiver;
import com.AL.comm.message.handler.HandlerImpl;
 
 
 
/**
 * @author ebthomas
 * 
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:spring/applicationContext.xml" } )
public class PojoReceiverTest
{ 
    private static String QUEUE = "endpoint.in.q";
    PojoReceiver pojoReceiver = null;
      
    @Test
    public void testMessageDeliverytest() throws Exception
    {   
        pojoReceiver = new PojoReceiver(QUEUE, HandlerImpl.class, "execute" );
        
        loopForever(); 
    }

    public void loopForever() throws Exception
    {
        boolean go = true;
        while (go)
        {
            Thread.sleep( 1000 );
            System.out.println( "wait...."   );
        }
    }
}
