package com.AL.util.messaging.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.util.messaging.Broadcastable;

public class JMSBroadcastable implements Broadcastable
{
	private static Logger logger = Logger.getLogger( JMSBroadcastable.class );

	public static JMSBroadcastable createDefault()
	{
		return new JMSBroadcastable();
	}

	/**
	 * Method to broadcast message to JMS queue.
	 */
	public void broadcast( String objectToBroadcast,final Map<String, String> map )
	{
		logger.trace( "Broadcasting Message : " + objectToBroadcast );
		JMSBroadcastUtil.sendMessage( objectToBroadcast, map );
	}

}
