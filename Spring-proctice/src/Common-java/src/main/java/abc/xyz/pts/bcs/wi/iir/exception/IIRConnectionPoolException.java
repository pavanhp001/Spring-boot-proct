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

public class IIRConnectionPoolException extends IIRConnectException
{
    private static final long serialVersionUID = 6301926428550863700L;

    public IIRConnectionPoolException(final String msg)
    {
        super(msg);
    }

    public IIRConnectionPoolException(final String msg, final Throwable e) {
        super(msg, e);
    }

}
