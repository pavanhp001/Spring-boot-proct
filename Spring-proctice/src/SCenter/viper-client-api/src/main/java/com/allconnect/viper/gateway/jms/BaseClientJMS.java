package com.A.V.gateway.jms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.util.JMSConfigManager;
import com.A.V.gateway.BaseClientService;

public class BaseClientJMS <T> extends BaseClientService<T> {

protected static final Map<String, CommunicationManager<javax.jms.Message, MessageListener>> commManagerMap = new HashMap<String, CommunicationManager<javax.jms.Message, MessageListener>>(); 
	 
private static final Logger logger = Logger.getLogger(BaseClientJMS.class);

	
	public CommunicationManager<javax.jms.Message, MessageListener> getCommunicationManager(final String namespace) {
		
		CommunicationManager<javax.jms.Message, MessageListener> commManager = commManagerMap.get(namespace);
		
		if (commManager == null) {
			 commManagerMap.put(namespace, JMSConfigManager.INSTANCE
						.createCommunicationManager(namespace));
			 
			 commManager = commManagerMap.get(namespace);
		}
		
		return commManager;
	}
		
	public String send( String namespace, String endpointName, int timeout, String uamRequestAsString, Map<String,String> headers) {

		TextMessage responseFromRTIM = null;
		
		if (uamRequestAsString == null) {
			logger.info("RequestAsString is null ......................... ");
			return null;
		} else {
			uamRequestAsString.replaceFirst("<v4:credentialName>.+</v4:credentialName>", "<v4:credentialName>********</v4:credentialName>");
			logger.info("EndpointName.........................: " + endpointName);
		}
		String responseAsString = "";
		try {
			CommunicationManager<javax.jms.Message, MessageListener> commManager = getCommunicationManager(namespace);
			logger.debug("commManager.........................: " + commManager.toString());
			
			long currentTime = System.currentTimeMillis();
			responseFromRTIM = (TextMessage) commManager.sendSync(
					endpointName, uamRequestAsString, timeout, headers);
			logger.info("Time taken : "+(System.currentTimeMillis() - currentTime) + "ms");
			if ((responseFromRTIM != null) && (responseFromRTIM instanceof TextMessage)) {
				responseAsString = responseFromRTIM.getText();
				logger.info("ResponseAsString ..... "+responseAsString);
			}
			else{
				logger.info("Unable to send request to "+endpointName);
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}

		return responseAsString;
	}
	
	@Override
	public InputStream getInputStream(String objAsString) {
		InputStream is = null;

		try {
			is = new ByteArrayInputStream(objAsString.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return is;
	}

	@Override
	public OutputStream getOutputStream(String objAsString) {

		OutputStream f1 = null;

		try {
			f1 = new ByteArrayOutputStream();
			return f1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f1;
	}
}
