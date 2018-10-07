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
 * Container for all Application level exception (non fatal).
 *
 * @author mmotlib-siddiqui
 *
 */
public abstract class ApplicationException
    extends Exception
{
    private static final long serialVersionUID = 7902408856356988699L;
    private final String errorCode;

    public ApplicationException(final String message, final String errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(final Exception e, final String errorCode)
    {
        super(e);
        this.errorCode = errorCode;
    }

    public ApplicationException(final String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorCode()
    {
        return errorCode;
    }
}
