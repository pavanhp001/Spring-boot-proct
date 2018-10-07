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
 * Unchecked exception for throwing all fatal exception we encounter.
 *
 * @author mmotlib-siddiqui
 *
 */
public abstract class FatalException extends RuntimeException
{
    private static final long serialVersionUID = 8642630079421220103L;


    public FatalException(final Exception e)
    {
        super(e);
    }

    public FatalException(final String str)
    {
        super(str);
    }
}

