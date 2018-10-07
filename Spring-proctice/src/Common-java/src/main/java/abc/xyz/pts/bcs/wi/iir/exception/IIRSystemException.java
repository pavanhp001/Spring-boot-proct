/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 * @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir.exception;

import ssa.ssautil.SSAException;

public class IIRSystemException extends IIRException
{
    private static final long serialVersionUID = 783275408L;

    public IIRSystemException(final String func, final SSAException ssae, final String iirErrMsg)
    {
        super(func, ssae, iirErrMsg);
    }
}
