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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.audit.util.PropertyFormatter;

/**
 * Creates and sends an audit message. Can be used as an alternative to the aspect if it is not suitable.
 *
 * @author tterry
 *
 */
public class Auditor {

    private static final String STATUS_FAILURE = "FAILURE";
    private static final String STATUS_SUCCESS = "SUCCESS";

    private AuditMessageSender messageSender;

    /**
     * @param messageSender
     *            the messageSender to set
     */
    public void setMessageSender(final AuditMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Creates and sends an audit message based apon the supplied parameters.
     *
     * @param event
     *            Type of event
     * @param parameters
     *            A map of event parameters
     * @param userId
     *            Id of user performing action
     * @param employeeName
     *            Name of user performing action
     * @param responseTime
     *            Time the action has taken to complete
     * @param error
     *            Has the user's action errored?
     * @throws AuditException
     *             If a message cannot be sent
     */
    public void audit(final Event event, final Map<Parameter, Object> parameters, final String userId, final String employeeName,
            final long responseTime, final boolean error) throws AuditException {
        AuditEvent ae = new AuditEvent();
        ae.setUserId(userId);
        ae.setEmployeeName(employeeName);
        ae.setName(event.value());
        ae.setResponseTime(responseTime);
        ae.setCreatedDate(new Date());
        if (error) {
            ae.setResponseStatus(STATUS_FAILURE);
        } else {
            ae.setResponseStatus(STATUS_SUCCESS);
        }
        List<AuditEventParameter> params = new ArrayList<AuditEventParameter>(parameters.size());
        for (Map.Entry<Parameter, Object> mapEntry : parameters.entrySet()) {
            AuditEventParameter aep = new AuditEventParameter();
            aep.setName(mapEntry.getKey().value());
            String strValue = PropertyFormatter.format(mapEntry.getValue());
            aep.setValue(strValue);
            params.add(aep);
        }
        ae.getParameters().addAll(params);
        messageSender.send(ae);
    }
}
