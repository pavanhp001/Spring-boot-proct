/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

import java.util.ArrayList;
import java.util.List;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;


public class UpdateBuilder extends AbstractBuilder {

    // UPDATE item list
    private final StringBuilder updateList = new StringBuilder(128);
    private final List<Object> updateValues = new ArrayList<Object>();

    /**
     * Constructor
     * @param tableCommand
     */
    public UpdateBuilder(final TableActionCommand tableCommand) {
        super();
        this.tableCommand = tableCommand;
        this.ignoreEmptyField = false;
    }

    /**
     * Add Update item to query, prepending comma if not first
     *     update item
     * @param update Column
     */
    public void addUpdate(final Field update, final Object value) {
        checkAddSeparator(updateList, ",");
        updateList.append(update + "=?");
        updateValues.add(value);
    }

    public String getSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE " + tableList.toString());
        sql.append(" SET " + updateList.toString());
        if (whereClause.length() > 0) {
            sql.append(" WHERE ");
            sql.append(whereClause);
        }

        return sql.toString();
    }

    /**
     * Return update values and criteria.
     * @return
     */
    public Object[] getCriteriaValues() {
        final List<Object> allValues = new ArrayList<Object>();
        allValues.addAll(updateValues);
        allValues.addAll(criteriaValues);

        return allValues.toArray();
    }
}
