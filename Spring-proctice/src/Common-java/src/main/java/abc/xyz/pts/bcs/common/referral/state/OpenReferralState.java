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

/**
 * @author cwalker
 *
 */
public final class OpenReferralState extends ReferralStateImpl
{
    public OpenReferralState(final Referral referral)
    {
        super(referral);
    }


    @Override
    public void handleStateOnSave() throws ApplicationException
    {
        if (referral.isAllHitRuledOut()) {
            referral.setStatus(ReferralStatusType.CLOSED.name());
        } else if (referral.isAtLeastOneHitRuledIn()) {
            referral.setStatus(ReferralStatusType.OPEN.name());
        }
    }


    @Override
    public void addHits() throws ApplicationException {
        final ReferralStatusType referralState = ReferralStatusType.OPEN;
        referral.setStatus(referralState.name());
        referral.setClosureCode(null);
    }

    @Override
    public ReferralStatusType getReferralStatusType() {
        return ReferralStatusType.OPEN;
    }

}
