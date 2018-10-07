/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2011
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.web.command;

import java.io.Serializable;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;

/**
 * @author andy taylor
 */

@AuditableCommand(Event.REFERRAL_NOTE)
public class ReferralAddNoteCommand implements Serializable {

    private static final long serialVersionUID = 3291908881390006837L;

    @AuditableBeanProperty(key = Parameter.REFERRAL_NUMBER)
    private Long refId;

    private String noteText;

    public Long getRefId() {
        return refId;
    }

    public void setRefId(final Long refId) {
        this.refId = refId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(final String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return "ReferralAddNoteCommand [refId=" + refId + ", noteText=" + noteText + "]";
    }

}
