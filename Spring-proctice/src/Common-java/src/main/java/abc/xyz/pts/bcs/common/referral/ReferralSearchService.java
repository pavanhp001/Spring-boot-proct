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
package abc.xyz.pts.bcs.common.referral;

import java.util.List;
import java.util.Locale;

import abc.xyz.pts.bcs.common.exception.FatalException;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;
import abc.xyz.pts.bcs.common.referral.web.command.ReferralSearchCommand;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * @author cwalker
 */
public interface ReferralSearchService
{
    List<ReferralRec> search(ReferralSearchCommand cmd, TableActionCommand actionCmd, Locale locale) throws FatalException;

    ReferralHitTargetRec getReferralHitTarget(Long referralId);

    ReferralTravellerRec getReferralHitTraveller(Long referralId);
    /**
     * <code>getReferralSearchCount</code> is used to get the total number of referrals for the given
     * search criteria.
     * @param search
     * @param tableCommand
     * @return
     */
    int getReferralSearchCount(ReferralSearchCommand search, TableActionCommand tableCommand);
}
