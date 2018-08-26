/**
 *
 */
package com.AL.comm.manager.jms.strategy;

import javax.jms.Destination;
import javax.jms.Message;
import javax.naming.NamingException;
import com.AL.cm.util.Constants;
import com.AL.cm.util.UUIDGenerator;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.comm.manager.jms.util.TargetDestination;

/**
 * @author ebthomas
 *
 */
public abstract class AbstractSendStrategy
{
    public static void setupProducer( TargetDestination jmsDest ) throws Exception
    {
        if ( jmsDest.getProducer() != null )
            return;

        jmsDest.setProducer( jmsDest.getSession().createProducer( jmsDest.getDestination() ) );
    }
    
    public static void addMuleCorrelationId( final Message jmsMessage )
    {
        try
        {
            jmsMessage.setStringProperty( Constants.PROPS_MULE_CORRELATION_ID, UUIDGenerator.generate() );
            if (jmsMessage.getJMSCorrelationID() == null)
            {
            	jmsMessage.setJMSCorrelationID(UUIDGenerator.generate());
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    public static Destination getJmsDestination( String jmsDestinationJndiName ) throws Exception
    {
        Destination jmsDestination = null;
        try
        {
            jmsDestination = (Destination) JMSConfigManager.INSTANCE.getInitialContext().lookup( jmsDestinationJndiName );
        }
        catch ( ClassCastException cce )
        {
            throw new Exception( cce );
        }
        catch ( NamingException ne )
        {
            throw new Exception( ne );
        }
        return jmsDestination;
    }
    
}
