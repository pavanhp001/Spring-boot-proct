/**
 *
 */
package com.AL.comm.manager.jms.strategy;

import java.io.Serializable;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import com.AL.comm.manager.jms.util.TargetDestination;

/**
 * @author ebthomas
 *
 */
public class SendStrategy extends AbstractSendStrategy
{
    private static Logger log = Logger.getLogger( SendStrategy.class );
    
    //
    // The following allows the caller to send a Serializable object to a destination
    //
    public static Message send( TargetDestination targetDestination, String destName, Object obj ) throws Exception
    {

        System.out.println( "send( String destName, Object obj )" );

        if ( obj instanceof Serializable )
        {
            System.out.println( "* obj instanceof Serializable***" );
            System.out.println( "* casting to serializable***" );
            Serializable serializable = (Serializable) obj;
            System.out.println( "* completed cast to serializable***" );
            System.out.println( serializable );
            //TargetDestination targetDestination = getJMSDestination( destName );
            System.out.println( targetDestination.toString() );
            // Make sure we have a message producer created for this destination
            setupProducer( targetDestination );
            System.out.println( "setupProducer at targetDestination" );
            // Send the message for this destination
            Message msg = createJMSMessage( serializable, targetDestination.getSession() );
            System.out.println( "created JMS Message" );
            addMuleCorrelationId( msg );
            System.out.println( "added Mule Correlation Id" );
            System.out.println( "sending message" );
            targetDestination.getProducer().send( msg );
            System.out.println( "message sent" );
            log.info( "send() - message sent" );
        }
        else
        {
            System.out.println( "* default ***" );

          //  TargetDestination targetDestination = getJMSDestination( destName );

            // Make sure we have a message producer created for this destination
            setupProducer( targetDestination );

            // Send the message for this destination
            Message msg = createJMSMessage( obj, targetDestination.getSession() );
            addMuleCorrelationId( msg );
            targetDestination.getProducer().send( msg );
            log.info( "send() - message sent" );
        }
        
        return (Message)obj;
    }

    
    public static Message createJMSMessage( Object obj, Session session ) throws Exception
    {
        if ( obj instanceof Message )
            return (Message) obj;

        if ( obj instanceof String )
        {
            TextMessage textMsg = session.createTextMessage();
            textMsg.setText( (String) obj );
            return textMsg;
        }
        else if ( obj instanceof Serializable )
        {
            ObjectMessage objMsg = session.createObjectMessage();
            objMsg.setObject( (Serializable) obj );
            return objMsg;
        }
        else
        {
            throw new IllegalArgumentException( "Unable to create a JMS message with object type "
                    + obj.getClass().getCanonicalName() );
        }
    }
    
}
