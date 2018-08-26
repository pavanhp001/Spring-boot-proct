package com.AL.comm.manager.jms.receiver.impl;

import java.util.Map;

import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import com.AL.comm.manager.jms.receiver.Executable;

/**
 * @author ebthomas
 * 
 */
public class ExecutableDefault<T, U, V> implements Executable<T, U, V>
{
    Logger logger = Logger.getLogger( ExecutableDefault.class );

    public V execute( final T message, final Map<String, String> map )
    {
        logger.debug( System.nanoTime() + "> extracting data from message ....." );

        String name = null;
        try
        {
            if ( message instanceof TextMessage )
            {
                TextMessage objMessage = (TextMessage) message;
                Object obj = objMessage.getJMSCorrelationID();
                if ( obj instanceof String )
                {
                    name = ( (String) obj );
                    logger.debug( "received correlation id: " + name );
                }
            }
        }
        catch ( Throwable t )
        {
            t.printStackTrace();

        }

        return null;
    }
}
