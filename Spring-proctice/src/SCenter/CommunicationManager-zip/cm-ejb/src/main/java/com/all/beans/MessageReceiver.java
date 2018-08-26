package com.AL.beans;

import javax.jms.MessageListener;
import org.jboss.ejb3.annotation.ResourceAdapter;
import com.AL.comm.manager.jms.receiver.MDBReceiverSender;

/**
 * Message-Driven Bean implementation class for: MessageReceiver
 *
 */
@ResourceAdapter("activemq-rar-5.4.2.rar")
public class MessageReceiver extends MDBReceiverSender implements MessageListener {
 
	
	/**
     * Default constructor. 
     */
    public MessageReceiver() {
    	super();
    }
	
	 
}
