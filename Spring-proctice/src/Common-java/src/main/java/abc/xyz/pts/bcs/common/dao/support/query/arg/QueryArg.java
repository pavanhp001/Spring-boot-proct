/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.ArrayList;
import java.util.List;

import abc.xyz.pts.bcs.common.dao.support.query.Table;

/**
 * Abstract class to extend for custom query args
 * A QueryArg houses a criteria statement with place holder(s) (e.g. flight_number = ?),
 *     criteria value(s) and any extra tables required for the WHERE clause
 */
public abstract class QueryArg {

    protected static final String EMPTY_STRING="";
    private StringBuilder whereClause;
    private List<Table> tableList;
    private List<Object> criteriaValues;

    public String getWhereClause() {
        if (whereClause != null){
            return whereClause.toString();
        } else {
            return null;
        }
    }

    public List<Object> getCriteriaValues() {
        return criteriaValues;
    }

    public List<Table> getTables() {
        return tableList;
    }


    /**
     * Add WHERE clause part
     * @param clause
     */
    protected void addToWhereClause(final String clause) {
        if (whereClause == null) {
            whereClause = new StringBuilder();
        }
        whereClause.append(clause);
    }
    /**
     * Add search criteria value
     * @param value
     */
    protected void addSearchCriteria(final Object value) {
        if (criteriaValues == null) {
            criteriaValues = new ArrayList<Object>();
        }
        criteriaValues.add(value);
    }
    /**
     * Add table to FROM part of query
     * @param table
     */
    protected void addTable(final Table table) {
        if (tableList == null) {
            tableList = new ArrayList<Table>();
        }
        if (!tableList.contains(table)) {
            tableList.add(table);
        }
    }
}
