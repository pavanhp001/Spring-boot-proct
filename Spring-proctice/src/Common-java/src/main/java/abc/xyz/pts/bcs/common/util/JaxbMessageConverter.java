/* **************************************************************************
 *                              - CONFIDENTIAL                           *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * Converts xml messages to/from objects using the <code>JaxbHelper</code> class.
 *
 * @version $Id: JaxbMessageConverter.java 530 2008-07-08 14:04:16Z wgriffiths $
 */
public class JaxbMessageConverter implements MessageConverter {

    private Class<?> classToBeBound;
     private static Logger logger = Logger.getLogger( JaxbMessageConverter.class );

    /**
     * Deserializes specified message into an object.
     *
     * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
     */
    @Override
    public Object fromMessage(final Message message) throws JMSException, MessageConversionException {

        String source = null;
        Object result = null;

        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            source = textMessage.getText();
       } else if (message instanceof BytesMessage) {
           final BytesMessage bytesMessage = (BytesMessage) message;
           byte[] msgContents;
           msgContents = new byte[ ( int )bytesMessage.getBodyLength() ];
           bytesMessage.readBytes(msgContents);
           source = new String(msgContents);
       }
        if (source != null) {
            result = JaxbHelper.deserialise(source, this.classToBeBound);
             if (logger.isDebugEnabled()) {
                 logger.debug("Deserilising instance of class(" + classToBeBound.getName() + ")from message: " + result);
             }
        } else {
            throw new MessageConversionException("Failed to convert the message, message type is not supported");
        }

        return result;
    }

    /**
     * Sets the class to reference during message conversion.
     */
    public void setClassToBeBound(final Class<?> arg0) {
        this.classToBeBound = arg0;
    }

    /**
     * Serializes the given object into a text message.
     *
     * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
     */
    @Override
    public Message toMessage(final Object object, final Session session) throws JMSException {

        // create TextMessage result
        return session.createTextMessage(JaxbHelper.serialise(object));
    }
}
