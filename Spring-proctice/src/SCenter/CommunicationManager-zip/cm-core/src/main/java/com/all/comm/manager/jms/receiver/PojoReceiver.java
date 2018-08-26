package com.AL.comm.manager.jms.receiver;

 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.log4j.Logger;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;

/**
 * @author ebthomas
 * 
 */
public final class PojoReceiver
{

    private ExecutorService SINGLE_THREAD = Executors.newSingleThreadExecutor();
    private String namespace = "default";
    private CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE.createCommunicationManager( namespace );
            

    private static Logger log = Logger.getLogger( PojoReceiver.class );

    public PojoReceiver()
    {
        super();
    }
    
    public PojoReceiver( final String queue, final Class<?> clazz, final String methodName )
    {
        super();
        try
        {
            listen( queue, clazz, methodName );
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void listen( final String queue, final Class<?> clazz, final String methodName ) throws Exception
    {

        final Runnable command = new Runnable()
        {
            public void run()
            {
                MDBReceiverSender clazzToProcessMessage = new MDBReceiverSender( clazz, methodName );
                try
                {
                    communicationManager.listen( queue, clazzToProcessMessage );
                }
                catch ( Exception e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        
        SINGLE_THREAD.execute( command );
    }

    public void shutdown()
    {
        SINGLE_THREAD.shutdown();
    }
}
