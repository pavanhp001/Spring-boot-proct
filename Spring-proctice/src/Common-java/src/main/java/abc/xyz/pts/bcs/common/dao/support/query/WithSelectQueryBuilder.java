/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

import java.util.List;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * Allow With to be included for an SQL Statement.
 *  i.e.  WITH ....
 *        SELECT .....
 *
 * @author MMotlib-Siddiqui
 *
 */
public class WithSelectQueryBuilder
    extends QueryBuilder
{
    private String withName;

    public WithSelectQueryBuilder
        ( final String withName
        , final TableActionCommand actionCmd
        , final Field orderByField
        )
    {
        super(actionCmd, orderByField);

        this.withName = withName;
    }


    protected String getPrefixWithSQL()
    {
        StringBuilder buf = new StringBuilder();

        // With SQL
        buf.append(" WITH  " + withName + " AS ( ");
        buf.append(getPagedQuerySql());
        buf.append(" ) ");

        return buf.toString();
    }


    public List<Object> getCriteria()
    {
        return this.criteriaValues;
    }

}
