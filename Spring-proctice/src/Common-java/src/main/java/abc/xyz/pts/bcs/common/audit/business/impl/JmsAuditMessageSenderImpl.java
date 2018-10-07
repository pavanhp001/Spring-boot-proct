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

package abc.xyz.pts.bcs.common.audit.business.impl;

import java.util.Calendar;

import javax.jms.Destination;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsOperations;

import abc.xyz.pts.bcs.common.audit.business.AuditException;
import abc.xyz.pts.bcs.common.audit.business.AuditMessageCreator;
import abc.xyz.pts.bcs.common.audit.business.AuditMessageSender;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.jmx.impl.MessageSenderMXBean;

public class JmsAuditMessageSenderImpl implements AuditMessageSender {
    private Destination destination;
    private JmsOperations jmsOperations;
    private final Logger log = Logger.getLogger(JmsAuditMessageSenderImpl.class);
    private MessageSenderMXBean messageSenderMXBean;

    public JmsAuditMessageSenderImpl() {

    }

    public void send(final AuditEvent ae) throws AuditException {
        try {
            jmsOperations.send(getDestination(), new AuditMessageCreator(ae));
            // update JMX attribute Date of Last Message Sent
            Calendar cal = Calendar.getInstance();
            messageSenderMXBean.setLastMessageSentDate(cal);
            messageSenderMXBean.updateMessagesSentCount();
        } catch (Exception e) {
            log.error("Unable to send jms message", e);
            throw new AuditException("Unable to send jms message", e);
        }
    }

    /**
     * @return the jmsTemplate
     */
    public JmsOperations getJmsOperations() {
        return jmsOperations;
    }

    /**
     * @param jmsTemplate
     *            the jmsTemplate to set
     */
    public void setJmsOperations(final JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    /**
     * @return the destination
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(final Destination destination) {
        this.destination = destination;
    }

    public void setMessageSenderMXBean(final MessageSenderMXBean messageSenderMXBean) {
        this.messageSenderMXBean = messageSenderMXBean;
    }

}
