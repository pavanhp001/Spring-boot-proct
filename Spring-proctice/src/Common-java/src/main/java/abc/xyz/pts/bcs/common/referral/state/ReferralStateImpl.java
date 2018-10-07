/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.state;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.common.referral.exception.IllegalReferralOperationException;

/**
 * Abstract state interface.
 *
 * @author cwalker
 */
public abstract class ReferralStateImpl implements ReferralState {
    protected Referral referral;
    protected String previousStatus;

    ReferralStateImpl(final Referral referral) {
        this.referral = referral;
    }

    @Override
    public void handleStateOnAcknoweledge() throws ApplicationException {
        throw new IllegalReferralOperationException();
    }

    @Override
    public void handleStateOnSave() throws ApplicationException {
        throw new IllegalReferralOperationException();
    }

    @Override
    public void handleStateOnClose() throws ApplicationException {
        referral.setStatus(ReferralStatusType.CLOSED.name());
    }

    @Override
    public Referral getReferral() {
        return this.referral;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    @Override
    public void setPreviousStatus(final String previousStatus) {
        this.previousStatus = previousStatus;
    }

    @Override
    public boolean isStatusChanged() {
        // if the previous status is not null then
        // check if the status has changed
        if (previousStatus != null) {
            if (referral.getStatus().equals(previousStatus)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isAtLeastOneHitRuledIn() {
        if (referral.isAtLeastOneHitRuledIn()) {
            return true;
        }
        return false;
    }

}
