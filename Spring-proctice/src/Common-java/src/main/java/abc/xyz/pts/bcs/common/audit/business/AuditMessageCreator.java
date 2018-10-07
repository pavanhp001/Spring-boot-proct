/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */

package abc.xyz.pts.bcs.common.audit.business;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.MessageCreator;

import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.util.JaxbHelper;

public class AuditMessageCreator implements MessageCreator {
    private final AuditEvent ae;
    private final Logger log = Logger.getLogger(AuditMessageCreator.class);

    public AuditMessageCreator(final AuditEvent ae) {
        this.ae = ae;
    }

    @Override
    public Message createMessage(final Session session) throws JMSException {
        TextMessage message = null;
        final String messageXML = JaxbHelper.serialise(ae);
        if (messageXML != null) {
            if(log.isDebugEnabled()){
                log.debug("System Audit Message sent = "+ messageXML);
            }
            message = session.createTextMessage(messageXML);
        } else {
            final String error = "Serialisation of audit JMS message failed";
            log.error(error);
            throw new JMSException(error);
        }
        return message;
    }
}
