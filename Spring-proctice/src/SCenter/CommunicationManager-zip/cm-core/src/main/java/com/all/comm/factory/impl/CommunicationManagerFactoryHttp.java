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
public class CommunicationManagerFactoryHttp implements CommunicationManagerFactory<Message, MessageListener>
{

    /**
     * {@inheritDoc}
     */
    public CommunicationManager<Message, MessageListener> createCommunicationManager()
    {
        // based on configuration
        return null; //new RestEasyCommunicationManager();
    }

    /**
     * {@inheritDoc}
     */
    public CommunicationManager<Message, MessageListener> createCommunicationManager( String namespace )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
