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

public class DeleteBuilder extends AbstractBuilder {



    /**
     * Constructor
     * @param tableCommand
     */
    public DeleteBuilder(final TableActionCommand tableCommand) {
        super();
        this.tableCommand = tableCommand;
    }


    @Override
    public String getSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" DELETE FROM " + tableList.toString());

        if (whereClause.length() > 0) {
            sql.append(" WHERE ");
            sql.append(whereClause);
        }

        return sql.toString();
    }


}
