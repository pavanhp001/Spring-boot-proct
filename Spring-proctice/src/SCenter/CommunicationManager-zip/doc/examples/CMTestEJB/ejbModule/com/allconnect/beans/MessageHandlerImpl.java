package com.AL.beans;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import com.AL.comm.manager.jms.receiver.Executable;

/**
 * Session Bean implementation class MessageHandlerImpl
 */
@Stateless(name="MessageHandlerImpl", mappedName="MessageHandlerImpl")
public class MessageHandlerImpl implements Executable<Message, String, String> {

    /**
     * Default constructor. 
     */
    public MessageHandlerImpl() {
    }

	@Override
	public String execute(Message arg) {
		String msg="";
		if(arg instanceof TextMessage)
			try {
				msg = ((TextMessage)arg).getText();
			} catch (JMSException e) {
				System.out.println("Exception thrown......"+e.getMessage());
				e.printStackTrace();
			}
		System.out.println("MessageHandlerImpl.java execute(): Processing Message : " + msg);
		return msg;
	}

}
