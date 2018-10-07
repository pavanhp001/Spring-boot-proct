/*
 * This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2001-2009
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral;

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.referral.exception.UnrecognisedReferralStatusException;
import abc.xyz.pts.bcs.common.referral.state.ClosedReferralState;
import abc.xyz.pts.bcs.common.referral.state.NewReferralState;
import abc.xyz.pts.bcs.common.referral.state.OpenReferralState;
import abc.xyz.pts.bcs.common.referral.state.ReferralState;
import abc.xyz.pts.bcs.common.referral.state.UnqualifiedReferralState;

/**
 * @author Irfan.Ansari
 *
 */
public class ReferralStateFactory {

    private static final Logger log = Logger.getLogger(ReferralStateFactory.class);

    /**
     * Get referralState base on the supplied data.
     *
     * @param referral
     * @returns the matched {@link ReferralState}instance and can also return
     *          null if not matched.
     */
    public static ReferralState getInstance(final Referral referral) {
        // no need to load the referral data
        ReferralState newReferralState = null;

        if (ReferralStatusType.CLOSED.name().equals(referral.getStatus())) {
            newReferralState = new ClosedReferralState(referral);
        } else if (ReferralStatusType.UNQ.name().equals(referral.getStatus())) {
            newReferralState = new UnqualifiedReferralState(referral);
        } else if (ReferralStatusType.OPEN.name().equals(referral.getStatus())) {
            newReferralState = new OpenReferralState(referral);
        } else if (ReferralStatusType.NEW.name().equals(referral.getStatus())) {
            newReferralState = new NewReferralState(referral);
        }
        if (newReferralState == null) {
            log.error("unknown referral status (" + referral.getStatus() + ")" );
            throw new UnrecognisedReferralStatusException(referral.getStatus());
        }
        // the referral has it's
        String previousStatus = null;
        if (referral.getReferralState()!= null) {
            previousStatus = referral.getReferralState().getReferralStatusType().name();
        }
        newReferralState.setPreviousStatus(previousStatus);
        return newReferralState;
    }
}
