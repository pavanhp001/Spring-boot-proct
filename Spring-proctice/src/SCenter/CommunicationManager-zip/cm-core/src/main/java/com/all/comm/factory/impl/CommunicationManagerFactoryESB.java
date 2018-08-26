/**
 *
 */
package com.AL.comm.factory.impl;

import javax.jms.Message;
import javax.jms.MessageListener; 
import com.AL.comm.factory.CommunicationManagerFactory;
import com.AL.comm.manager.CommunicationManager;

/**
 * @author ebthomas
 *
 */
public class CommunicationManagerFactoryESB implements CommunicationManagerFactory</*MuleMessage*/Message, MessageListener>
{

	public CommunicationManager<Message, MessageListener> createCommunicationManager(
			String namespace) {
		// TODO Auto-generated method stub
		return null;
	}

//    /**
//     * {@inheritDoc}
//     */
//    public CommunicationManager<MuleMessage, MessageListener> createCommunicationManager()
//    {
//        // based on configuration
//       // return new MuleCommunicationManager();
//    	return null;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public CommunicationManager<MuleMessage, MessageListener> createCommunicationManager( String namespace )
//    {
//        // based on configuration
//       // return new MuleCommunicationManager();
//    	
//    	return null;
//    }

}
