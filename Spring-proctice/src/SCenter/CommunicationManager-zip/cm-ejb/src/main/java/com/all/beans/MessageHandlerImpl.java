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
@LocalBinding(jndiBinding = "cm.handler.local")
public class MessageHandlerImpl implements Executable<String, String, String> {

    /**
     * Default constructor. 
     */
    public MessageHandlerImpl() {
    }

	public String execute(String msg, Map<String, String> map) {
		 
	    System.out.println("*****************MessageHandlerImpl.execute()****************************");
		System.out.println("MessageHandlerImpl.java execute(): Processing Message : " + msg);
	    System.out.println("*****************MessageHandlerImpl.execute()****************************");
		return msg;
	}

}
