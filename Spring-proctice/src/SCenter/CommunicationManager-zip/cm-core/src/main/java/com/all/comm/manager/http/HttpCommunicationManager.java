/**
 *
 */
package com.AL.comm.manager.http;

import java.util.Map;
import java.io.Serializable;
import javax.jms.MessageListener;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.TargetDestination;

/**
 * 
 * @author ebthomas
 * 
 */
public abstract class HttpCommunicationManager implements
		CommunicationManager<Object, MessageListener> {

	public HttpCommunicationManager() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public void send(String destName, Serializable obj) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public void send(TargetDestination dest, Object msg) throws Exception {
		// TODO Auto-generated method stub

	}
	
    /**
     * {@inheritDoc}
     */
    public void send(String target, Object msg, Map<String, String> map) {
        // TODO Auto-generated method stub
        
    }   
    

	/**
	 * {@inheritDoc}
	 */
	public Object sendSync(String destName, String responseName, Object msg,
			int timeout) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String listen(String destName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void listen(String destName, MessageListener callback)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public void listenAsync(TargetDestination dest, MessageListener callback)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public String listenSync(String destName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String listenSync(String destName, int timeout) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String listenSync(TargetDestination dest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
