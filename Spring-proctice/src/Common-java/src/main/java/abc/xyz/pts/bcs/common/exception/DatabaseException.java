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
 * Fatal error occured during DB transactions
 *
 * @author mmotlib-siddiqui
 *
 */
public final class DatabaseException
    extends FatalException
{
    private static final long serialVersionUID = -3961042851717413616L;

    // Error Number from DB
    private String dbErrorNum;


    public DatabaseException(final String errorNum, final Exception e)
    {
        super(e);
        this.dbErrorNum = errorNum;
    }

    public DatabaseException(final String errorNum)
    {
        super(new Exception());    // dummy one
        this.dbErrorNum = errorNum;
    }

    public DatabaseException(final long errorNum)
    {
        super(new Exception());    // dummy one
        this.dbErrorNum = String.valueOf(errorNum);
    }

    public String getDbErrorCode()
    {
        return dbErrorNum;
    }

    public void setDbErrorNum(final String dbErrorNum)
    {
        this.dbErrorNum = dbErrorNum;
    }

}
