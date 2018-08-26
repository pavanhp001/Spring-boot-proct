package com.AL.comm.manager.jms.receiver;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;

public class MDBReceiver extends MDBReceiverAbstract implements MessageListener {

    private static Logger logger = Logger.getLogger(MDBReceiver.class);
    
    

    public MDBReceiver(Class<?> clazz, String method) {
        super(clazz, method);
    }

    public void onMessage(final Message message) {

        if ((getClazzname() != null) && (getClazzname().length() > 0)
                && (getMethodName() != null) && (getMethodName().length() > 0)) {

            if (message instanceof TextMessage) {
                try {
                    String payload = ((TextMessage) message).getText();
                    processDynamicInvocation(payload);
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else {

            if (message instanceof TextMessage) {
                try {
                    String payload = ((TextMessage) message).getText();
                    
                    Map<String, String> mapHeaderProps = extractHeaderInfo( message );
                    processInstanceInvocation(payload, mapHeaderProps);
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    

}
