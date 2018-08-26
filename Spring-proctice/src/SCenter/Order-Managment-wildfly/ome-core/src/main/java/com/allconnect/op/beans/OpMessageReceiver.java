package com.AL.op.beans;

import javax.jms.MessageListener;

import org.jboss.ejb3.annotation.ResourceAdapter;

import com.AL.comm.manager.jms.receiver.MDBReceiverSender;

@ResourceAdapter("activemq-rar.rar")
public class OpMessageReceiver extends MDBReceiverSender implements MessageListener {
	
	/**
     * Default constructor. 
     */
	public OpMessageReceiver() {
		
	}

}
