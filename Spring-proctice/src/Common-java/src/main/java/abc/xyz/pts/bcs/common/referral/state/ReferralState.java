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

public interface ReferralState
{
    /**
     * Acknowledge a referral.
     *
     * @throws ApplicationException
     */
    public void handleStateOnAcknoweledge() throws ApplicationException;

    /**
     * Save changes to a referral.
     *
     * @throws ApplicationException
     */
    public void handleStateOnSave() throws ApplicationException;

    /**
     * Close a referral.
     *
     * @throws ApplicationException
     */
    public void handleStateOnClose() throws ApplicationException;

    /**
     * Get referral data.
     *
     * @return
     */
    public Referral getReferral();

    /**
     * This processes hits that have been added to the referral
     * @throws ApplicationException
     */
    public void addHits() throws ApplicationException;

    public void setPreviousStatus(final String previousStatus);

    public boolean isStatusChanged();

    public ReferralStatusType getReferralStatusType();

}