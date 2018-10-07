/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.exception;

public final class DBUpdateFailedException
    extends ApplicationException
{
    private static final long serialVersionUID = 3259455973061996003L;

    public DBUpdateFailedException(final String message)
    {
        super(message, "error.update.failed");
    }

}
