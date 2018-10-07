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
import java.util.Iterator;
import java.util.List;

import abc.xyz.pts.bcs.common.dao.support.query.arg.QueryArg;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

public abstract class AbstractBuilder {

    protected TableActionCommand tableCommand;
    // FROM table list
    protected StringBuilder tableList = new StringBuilder(32);
    // WHERE clause
    protected StringBuilder whereClause = new StringBuilder(64);
    // WHERE criteria
    protected List<Object> criteriaValues = new ArrayList<Object>();
    protected List<Object> estimationCriteriaValues = new ArrayList<Object>();;

    // GROUP BY list
    protected StringBuilder groupList = new StringBuilder();


    protected String innerSql;

    // SQL used for estimation the rows
    protected String estimationSql;

    protected boolean ignoreEmptyField;

    protected boolean ignoreOrderBy;
    
    protected boolean isDistinct;

    protected String hint;

    /**
     * Add TABLE to query, prepending comma if not first Table
     * @param table
     */
    public void addTable(final Table table) {
        checkAddSeparator(tableList, ",");
        tableList.append(table.getValue());
    }
    
    /**
     * Add TABLE to query, prepending comma if not first Table
     * @param table
     */
    public void addInLineView(final String view) {
        checkAddSeparator(tableList, ",");
        tableList.append(view);
    }

    public void addTable(final Table table, final Table alias) {
        checkAddSeparator(tableList, ",");
        tableList.append(table.getValue() + " " + alias);
    }

    /**
     * Add QueryArg to query
     * @param arg
     */
    public void addWhereClause(final QueryArg arg) {

        if (arg.getWhereClause() != null || this.ignoreEmptyField == false) {
            checkAddSeparator(whereClause, " AND ");
            whereClause.append(arg.getWhereClause());
            if (arg.getCriteriaValues() != null){
                criteriaValues.addAll(arg.getCriteriaValues());
            }
        }
        if (arg.getTables() != null) {
            for (Iterator<Table> tables = arg.getTables().iterator(); tables.hasNext();) {
                addTable(tables.next());
            }
        }
    }

    /**
     * Return query SQL without paging or ordering
     * @return
     */
    public String getInnerSql() {
        if (innerSql == null) {
            StringBuilder sql = new StringBuilder();

            sql.append(" FROM ");
            sql.append(tableList);
            if (whereClause.length() > 0) {
                sql.append(" WHERE ");
                sql.append(whereClause);
            }
            if (groupList.length() > 0) {
                sql.append(" GROUP BY ");
                sql.append(groupList);
            }
            innerSql = sql.toString();
        }
        return innerSql;
    }

    /**
     * Get Query for estimation.
     *
     * @return
     */
    public String getEstimationSql() {
        if (estimationSql == null) {
            estimationSql = getInnerSql();
        }

        return estimationSql;
    }

    /**
     * Query criteria values to replace place holder(s)
     * @return
     */
    public Object[] getCriteriaValues() {
        return criteriaValues.toArray();
    }

    public Object[] getEstimationCriteriaValues() {
        return getCriteriaValues();
    }

    /**
     *
     * @param builder
     * @param separator
     */
    protected void checkAddSeparator(final StringBuilder builder,
            final String separator) {
        if (builder.length() > 0) {
            builder.append(separator);
        }
    }


    public TableActionCommand getTableCommand() {
        return tableCommand;
    }

    public void setTableCommand(final TableActionCommand tableCommand) {
        this.tableCommand = tableCommand;
    }

    /**
     * Return final execution SQL
     * @return
     */
    public abstract String getSql();

    /**
     * Keep data for a single page.
     *
     * @param dataList
     */
    public void keepPageData(final List<?> dataList) {
        tableCommand.setPagesAvailable(false);
        if (dataList != null && dataList.size() > tableCommand.getPageSize()) {
            tableCommand.setPagesAvailable(true);
            if(tableCommand.isPrintAll() == false) {
                dataList.subList(tableCommand.getPageSize(), dataList.size()).clear();
            }
        }
    }
}
