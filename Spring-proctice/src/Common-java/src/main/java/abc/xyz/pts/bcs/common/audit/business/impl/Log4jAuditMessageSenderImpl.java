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

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.audit.business.AuditException;
import abc.xyz.pts.bcs.common.audit.business.AuditMessageSender;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.util.JaxbHelper;

public class Log4jAuditMessageSenderImpl implements AuditMessageSender {
    private final Logger logger = Logger.getLogger(Log4jAuditMessageSenderImpl.class);

    public void send(final AuditEvent ae) throws AuditException {
        String messageXML = JaxbHelper.serialise(ae);
        if (messageXML != null) {
            logger.info("Audit Event Logged. Details: \n" + messageXML);
        } else {
            String errorMessage = "Unable to log audit event. Cannot serialise audit event.";
            logger.error(errorMessage);
            throw new AuditException(errorMessage);
        }
    }
}
