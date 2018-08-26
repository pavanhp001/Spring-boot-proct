package com.AL.beans;
import javax.ejb.Local;

@Local
public interface MessageProducerLocal {
	public void sendMessage();
}
