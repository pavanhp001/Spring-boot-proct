/**
 *
 */
package com.AL.comm.manager;

import java.util.Map;
import javax.jms.TextMessage;

/**
 * @author ebthomas
 * 
 */
public interface CommunicationManager<T, U> {
	
	String getNamespace();
 
	void listen(String target, U callback) throws Exception;

	T listenSync(String target) throws Exception;

	T listenSync(String target, int timeout) throws Exception;
	 
	void send(String target, Object msg);
	
	void send(String target, Object msg, boolean useTargetQueueName );

	void send(String target, Object msg, Map<String, String> map);
	
	void send(String target, Map<String,Object> msg, Map<String, String> map);
	 
	T sendSync(Object msg) throws Exception;

	T sendSync(String target, String msg, int timeout) throws Exception;
	
	T sendSync(String target, String msg, int timeout, boolean useTargetQueueName) throws Exception;

	T sendSync(String target, String responseName, String msg, int timeout) throws Exception;
	
	T sendSync(String target, TextMessage message, int timeout) throws Exception;
	
	T sendSync(Object msg, Map<String, String> map) throws Exception;

	T sendSync(String target, String msg, int timeout, Map<String, String> map) throws Exception;

	T sendSync(String target, String responseName, String msg, int timeout, Map<String, String> map) throws Exception;
	
	T sendSync(String target, TextMessage message, int timeout, Map<String, String> map) throws Exception;
	
	@Deprecated
	void sendBroadcast(String target, Object msg, Map<String, String> map);

}
