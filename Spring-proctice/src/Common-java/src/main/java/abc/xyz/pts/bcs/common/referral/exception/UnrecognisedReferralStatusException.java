/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.exception;

import abc.xyz.pts.bcs.common.exception.FatalException;


public class UnrecognisedReferralStatusException
    extends FatalException
{
    private static final long serialVersionUID = 1879152390818084403L;


    public UnrecognisedReferralStatusException(final String e)
    {
        super(e);
    }

}
