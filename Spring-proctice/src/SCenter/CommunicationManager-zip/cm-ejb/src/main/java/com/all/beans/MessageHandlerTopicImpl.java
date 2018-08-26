package com.AL.beans;

import java.util.Map;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.jboss.ejb3.annotation.LocalBinding;
import com.AL.comm.manager.jms.receiver.Executable;

/**
 * Session Bean implementation class MessageHandlerImpl
 */
@Stateless 
@LocalBinding(jndiBinding = "cm.topic.handler.local")
public class MessageHandlerTopicImpl implements Executable<String, String, String> {

    /**
     * Default constructor. 
     */
    public MessageHandlerTopicImpl() {
    }

	public String execute(String msg, Map<String, String> map) {
		 
	    System.out.println("*****************MessageHandlerTopicImpl.execute()****************************");
		System.out.println("MessageHandlerTopicImpl.java execute(): Processing Message : " + msg);
	    System.out.println("*****************MessageHandlerTopicImpl.execute()****************************");
		return msg;
	}

}
