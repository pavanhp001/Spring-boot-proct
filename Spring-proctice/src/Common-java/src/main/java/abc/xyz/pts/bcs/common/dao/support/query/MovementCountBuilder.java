/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * For fetching the counts not exceeding the configured value
 * 
 * @author Sathishbabu C
 * 
 */
public class MovementCountBuilder extends CountBuilder {

    public MovementCountBuilder(final TableActionCommand tableCommand) {
        super(tableCommand);
    }
    
    public MovementCountBuilder(final TableActionCommand tableCommand,
            final Field primaryKey, final Table table) {
        super(tableCommand,primaryKey,table);
    }

    public String getQueryForCountSql() {
        final StringBuilder sql = new StringBuilder(526);
        sql.append(" SELECT count(*) FROM  ( SELECT ROWNUM r, query.* FROM (  SELECT ");
        sql.append(selectList);
        sql.append(" FROM ");
        sql.append(tableList);
        if (whereClause.length() > 0) {
            sql.append(" WHERE ");
            sql.append(whereClause);
        }
        sql.append(" ORDER BY ");
        sql.append(getOrderbyClause(tableCommand.getSortByColumns(), tableCommand.getSortDirection()));
        sql.append(") query WHERE rownum <= ");
        sql.append(tableCommand.getConfiguredRowCount() + 1);
        sql.append(" )");
        return sql.toString();
    }
}
