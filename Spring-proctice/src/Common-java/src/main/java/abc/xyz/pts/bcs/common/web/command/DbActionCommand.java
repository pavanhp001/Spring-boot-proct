/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.command;

import abc.xyz.pts.bcs.common.exception.DatabaseException;

/**
 * This should be used purely to get the error code from the JdbcUtil call.
 *
 * Not great but it will do for the moment...until the JdbcUtil starts throwing exceptions.
 *
 * @author mmotlib-siddiqui
 *
 */
public class DbActionCommand extends TableActionCommand
{
    private static final long serialVersionUID = -1151966576383942893L;

    @Override
    public void setErrorNumber(final long errorNumber)
    {
        throw new DatabaseException(errorNumber);
    }
}
