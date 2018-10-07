/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.exception;

/**
 * This is used to throw when no rows have been updated.
 * Mainly when updateVersionNo is different.
 *
 * @author mmotlib-siddiqui
 *
 */
public class DataNoLongerValid
    extends ApplicationException
{
    private static final long serialVersionUID = -5036214603617411211L;

    public DataNoLongerValid()
    {
        super("referral.update.outofdate");
    }
}
