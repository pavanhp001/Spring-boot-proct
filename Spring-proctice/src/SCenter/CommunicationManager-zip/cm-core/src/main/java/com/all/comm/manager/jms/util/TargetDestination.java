/**
 *
 */
package com.AL.comm.manager.jms.util;

import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import org.apache.log4j.Logger;

 

 

/**
 * @author ebthomas
 * 
 */
public class TargetDestination
{
	private static Logger logger = Logger
	.getLogger(TargetDestination.class);
	
    private Destination destination = null;
    private Session session = null;
    private MessageProducer producer = null;
    private MessageConsumer consumer = null;
    private TemporaryQueue tempQueue = null;
    
    private String sendTo;
    private String replyTo;
    

    public TargetDestination( Destination destination, Session session, MessageProducer producer, MessageConsumer consumer )
    {
        this.destination = destination;
        this.session = session;
        this.producer = producer;
        this.consumer = consumer;
    }

    public void stop()
    {
        try
        {
            logger.debug("closing connections");
           
            
            if ( producer != null )
                producer.close();
            if ( consumer != null )
                consumer.close();
            if ( session != null )
                session.close();
            
            if (tempQueue != null)
            	tempQueue.delete();
            
            
            
            logger.debug("connections closed");

            destination = null;
            session = null;
            producer = null;
            consumer = null;
            tempQueue = null;
        }
        catch ( Exception e )
        {
        }
    }

    public Destination getDestination()
    {
        return destination;
    }

    public void setDestination( Destination destination )
    {
        this.destination = destination;
    }

    public Session getSession()
    {
        return session;
    }

    public void setSession( Session session )
    {
        this.session = session;
    }

    public MessageProducer getProducer()
    {
        return producer;
    }

    public void setProducer( MessageProducer producer )
    {
        this.producer = producer;
    }

    public MessageConsumer getConsumer()
    {
        return consumer;
    }

    public void setConsumer( MessageConsumer consumer )
    {
        this.consumer = consumer;
    }

    public String getSendTo()
    {
        return sendTo;
    }

    public void setSendTo( String sendTo )
    {
        this.sendTo = sendTo;
    }

    public String getReplyTo()
    {
        return replyTo;
    }

    public void setReplyTo( String replyTo )
    {
        this.replyTo = replyTo;
    }

	public TemporaryQueue getTempQueue() {
		return tempQueue;
	}

	public void setTempQueue(TemporaryQueue tempQueue) {
		this.tempQueue = tempQueue;
	}

     
    
    

}
