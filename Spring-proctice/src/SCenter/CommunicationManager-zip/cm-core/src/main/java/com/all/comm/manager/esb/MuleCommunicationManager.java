/**
 *
 */
package com.AL.comm.manager.esb;

import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.AL.comm.manager.CommunicationManager;


/**
 * @author ebthomas
 * 
 */
public class MuleCommunicationManager implements CommunicationManager</*MuleMessage*/Message, MessageListener>
{

	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public void listen(String target, MessageListener callback)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Message listenSync(String target) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Message listenSync(String target, int timeout) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(String target, String msg) {
		// TODO Auto-generated method stub
		
	}

	public Message sendSync(Object msg) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Message sendSync(String target, String msg, int timeout)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Message sendSync(String target, String responseName, String msg,
			int timeout) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.AL.comm.manager.CommunicationManager#sendSync(java.lang.String, javax.jms.TextMessage, int)
	 */
	public Message sendSync(String target, TextMessage message, int timeout)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(String target, Object msg) {
		// TODO Auto-generated method stub
		
	}

    public void send(String target, Object msg, Map<String, String> map) {
        // TODO Auto-generated method stub
        
    }	
    
	public void sendBroadcast(String target, Object msg) {
		// TODO Auto-generated method stub
		
	}

	public void setParams(Map<String, String> map) {
		// TODO Auto-generated method stub
		
	}

	public void sendBroadcast(String target, Object msg, Map<String, String> map) {
		// TODO Auto-generated method stub
		
	}

	public Message sendSync(Object msg, Map<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Message sendSync(String target, String msg, int timeout,
			Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Message sendSync(String target, String responseName, String msg,
			int timeout, Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Message sendSync(String target, TextMessage message, int timeout,
			Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(String target, Map<String, Object> msg,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		
	}

	
	public void send(String target, Object msg, boolean useTargetQueueName) {
		// TODO Auto-generated method stub
		
	}

	public Message sendSync(String destName, String msg, int timeout, boolean useTargetQueueName)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//    private MuleClient muleClient;
//
//    /**
//     * Dispatches an event asynchronously to a endpointUri via a mule server. the Url determines where to dispathc the event to,
//     * this can be in the form of
//     * 
//     * @param url
//     *            the Mule url used to determine the destination and transport of the message
//     * @param message
//     *            the message to send
//     * @throws org.mule.api.MuleException
//     */
//   
//
//    /**
//     * {@inheritDoc}
//     */
//    public void send( String url, Serializable component ) throws Exception
//    {
//        final int DEFAULT_TIMEOUT = 5000;
//
//        Map messageProperties = new HashMap();
//
//        if ( messageProperties.get( MuleProperties.MULE_REMOTE_SYNC_PROPERTY ) == null )
//        {
//            messageProperties.put( MuleProperties.MULE_REMOTE_SYNC_PROPERTY, "true" );
//        }
//
//        // MuleMessage msg = new DefaultMuleMessage( payload );
//
//        // sendSync( url, msg, DEFAULT_TIMEOUT );
//    }
//
//    
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage sendSync( String url, MuleMessage msg, int timeout ) throws Exception
//    {
//        Map messageProperties = new HashMap();
//
//        if ( messageProperties == null )
//        {
//            messageProperties = new HashMap();
//        }
//        if ( messageProperties.get( MuleProperties.MULE_REMOTE_SYNC_PROPERTY ) == null )
//        {
//            messageProperties.put( MuleProperties.MULE_REMOTE_SYNC_PROPERTY, "true" );
//        }
//        // MuleMessage message = new DefaultMuleMessage(payload,
//        // messageProperties);
//        return muleClient.send( url, msg, timeout );
//
//    }
//
//    /**
//     * Sends an event synchronously to a endpointUri via a mule server and a resulting message is returned.
//     */
//    public MuleMessage sendSync( String url, String responseName, MuleMessage msg, int timeout ) throws Exception
//    {
//        return sendSync( url, msg, timeout );
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage createMessage( Object messageBody ) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage listen( String destName ) throws Exception
//    {
//        throw new UnsupportedOperationException( "Mule Client is not a listener" );
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public void listen( String destName, MessageListener callback ) throws Exception
//    {
//        throw new UnsupportedOperationException( "Mule Client is not a listener" );
//
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public void listenAsync( TargetDestination dest, MessageListener callback ) throws Exception
//    {
//        throw new UnsupportedOperationException( "Mule Client is not a listener" );
//
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage listenSync( String destName ) throws Exception
//    {
//        throw new UnsupportedOperationException( "Mule Client is not a listener" );
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage listenSync( String destName, int timeout ) throws Exception
//    {
//        throw new UnsupportedOperationException( "Mule Client is not a listener" );
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage listenSync( TargetDestination dest ) throws Exception
//    {
//        throw new UnsupportedOperationException( "Mule Client is not a listener" );
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public void send( TargetDestination dest, MuleMessage msg ) throws Exception
//    {
//        // TODO Auto-generated method stub
//
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage sendSync( String destName, Object msg, int timeout ) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage sendSync( String destName, String responseName, Object msg, int timeout ) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//     
//    /**
//     * {@inheritDoc}
//     */
//    public MuleMessage sendSync( Object msg ) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public void reply( String destName, String obj ) throws Exception
//    {
//        // TODO Auto-generated method stub
//        
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public Session getSession()
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public Message sendSync2( String destName, String msg, int timeout ) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public Message send( String destName, Object obj ) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//     
//    
//
//	public MuleMessage send(String destName, MuleMessage obj) throws Exception {
//		 muleClient.dispatch( destName, obj );
//	        
//	        return null;
//	}
//
//	public MuleMessage sendSync(String destName, String msg, int timeout)
//			throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void send(String subject, String createSession, int i) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//
//	public void send(String target, String msg) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//
//	public MuleMessage sendSync(String target, String responseName, String msg,
//			int timeout) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
