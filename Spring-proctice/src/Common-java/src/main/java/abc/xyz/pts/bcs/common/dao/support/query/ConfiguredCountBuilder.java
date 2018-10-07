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
public class ConfiguredCountBuilder extends CountBuilder {

    public ConfiguredCountBuilder(final TableActionCommand tableCommand) {
        super(tableCommand);
    }

    public String getQueryForCountSql() {
        final StringBuilder sql = new StringBuilder(526);
        sql.append("SELECT count(0) FROM ( SELECT null FROM ");
        sql.append(tableList);
        sql.append(" WHERE ");
        if (whereClause.length() > 0) {
            sql.append(whereClause);
            sql.append(" AND ");
        }
        sql.append("rownum <= ");
        sql.append(tableCommand.getConfiguredRowCount() + 1);
        sql.append(" )");
        return sql.toString();
    }
}
