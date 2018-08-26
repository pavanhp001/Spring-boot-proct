/**
 *
 */
package com.AL.comm.manager.jms.strategy;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.comm.manager.jms.util.TargetDestination;

/**
 * @author ebthomas
 *
 */
public class ReceiveStrategy
{
    public static void listenAsync( TargetDestination jmsDest, MessageListener callback ) throws Exception
    {
        setupAsynchConsumer( jmsDest, callback );

      //  log( "listen() - Asynchronous listen on destination " + jmsDest.toString() );
    }

    //
    // This is an asynchronous listen. The caller provides a JMS callback
    // interface reference, and any messages received for the given destination
    // are provided through the onMessage() callback method
    //
    public static  void listen( TargetDestination jmsDest, String destName, MessageListener callback ) throws Exception
    {
        //log( "JMSManager.listen(" + destName + ", " + callback + ")" );

        //TargetDestination jmsDest = getJMSDestination( destName );

        // Set the caller as a topic subcriber or queue receiver as appropriate
        //
        setupAsynchConsumer( jmsDest, callback );

        //log( "listen() - Asynchronous listen on destination " + destName );

    }

    public static  Message listen( TargetDestination jmsDest, String destName ) throws Exception
    {
        return listenSync(jmsDest,  destName );
    }

    //
    // This is a synchronous listen. The caller will block until
    // a message is received for the given destination
    //
    public static  Message listenSync(TargetDestination jmsDest, String destName ) throws Exception
    {
        System.out.println( "listen() - Synchronous listen on destination " + destName );

        //TargetDestination jmsDest = getJMSDestination( destName );

        // Setup the consumer and block until a
        // message arrives for this destination

        System.out.println( "listen() -  destination:" + jmsDest );
        //
        return setupSynchConsumer( jmsDest, 0 );
    }

    //
    // This is a synchronous listen. The caller will block until
    // a message is received for the given destination OR the
    // timeout value (in milliseconds) has been reached
    //
    public static  Message listenSync(TargetDestination jmsDest, String destName, int timeout ) throws Exception
    {
        //log( "listen() - Synchronous listen on destination " + destName );

        //TargetDestination jmsDest = getJMSDestination( destName );

        // Setup the consumer and block until a
        // message arrives for this destination
        //
        return setupSynchConsumer( jmsDest, timeout );
    }

    public static  void listenAsync( String namespace, Destination dest, MessageListener callback ) throws Exception
    {
        Connection connection = JMSConfigManager.INSTANCE.getIndependentConnection( namespace );
        Session s = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
        MessageConsumer c = s.createConsumer( dest );
        c.setMessageListener( callback );
    }

    public static  Message listenSync( String namespace, Destination dest ) throws Exception
    {
        Connection connection = JMSConfigManager.INSTANCE.getIndependentConnection(namespace );

        Message msg = null;
        Session s = null;
        try
        {
            s = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
            MessageConsumer c = s.createConsumer( dest );
            msg = c.receive();
        }
        finally
        {
            if ( s != null )
            {
                s.close();
            }
        }
        return msg;
    }

    /**
     * {@inheritDoc}
     */
    public static  Message listenSync( TargetDestination dest ) throws Exception
    {
        return null;
    }
    
    public static void setupAsynchConsumer( TargetDestination jmsDest, MessageListener callback ) throws Exception
    {
        if ( jmsDest.getConsumer() == null )
        {
            jmsDest.setConsumer( jmsDest.getSession().createConsumer( jmsDest.getDestination() ) );
        }

        jmsDest.getConsumer().setMessageListener( callback );
    }

    public static Message setupSynchConsumer( TargetDestination jmsDest, int timeout ) throws Exception
    {
        if ( jmsDest.getConsumer() == null )
        {
            jmsDest.setConsumer( jmsDest.getSession().createConsumer( jmsDest.getDestination() ) );
        }

        if ( timeout > 0 )
            return jmsDest.getConsumer().receive( timeout );
        else
            return jmsDest.getConsumer().receive();
    }
}
