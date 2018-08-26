package com.AL.beans;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.ejb3.annotation.ResourceAdapter;

import com.AL.comm.manager.jms.receiver.MDBReceiver;

/**
 * Message-Driven Bean implementation class for: MessageReceiver
 *
 */
@MessageDriven(name = "MessageReceiver",activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue.outbound") })
@ResourceAdapter("activemq-ra-5.4.2.rar")
public class MessageReceiver extends MDBReceiver implements MessageListener {

	
	@Resource(name="JNDI_NAME")
    private String jndiName ;
	
	
	/**
     * Default constructor. 
     */
    public MessageReceiver() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    /*public void onMessage(Message message) {
    	System.out.println("MessageReceiver.java : Enter onMessage() method....");
    	super.onMessage(message);
    	System.out.println("MessageReceiver.java : Exit onMessage() method....");        
    }*/
}
