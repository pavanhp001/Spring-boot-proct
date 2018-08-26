/**
 *
 */
package com.AL.comm.factory.impl;

import javax.jms.Message;
import javax.jms.MessageListener;
import com.AL.comm.factory.CommunicationManagerFactory;
import com.AL.comm.manager.jms.ActiveMQCommunicationManager;
import com.AL.comm.manager.CommunicationManager;

/**
 * @author ebthomas
 *
 */
public class CommunicationManagerFactoryJMS implements CommunicationManagerFactory<Message, MessageListener>
{

    /**
     * {@inheritDoc}
     */
    public CommunicationManager<Message, MessageListener> createCommunicationManager(String namespace)
    {
        if (true) //via config
        {
            return createActiveMQCM(namespace);
        }
        
        return null;
    }
    
    public CommunicationManager<Message, MessageListener> createActiveMQCM(String namespace)
    {
        return new ActiveMQCommunicationManager(namespace);
    }

}
