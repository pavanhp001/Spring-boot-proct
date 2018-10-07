/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;


import java.util.Arrays;
import java.util.List;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * Use to build database queries
 */
public class QueryBuilder extends AbstractBuilder {

    // SELECT item list
    protected final StringBuilder selectList = new StringBuilder(128);


    private String primaryKeySort;

    private OrderBy[] orderBys;

    /**
     * Constructor
     * @param tableCommand
     */
    public QueryBuilder(final TableActionCommand tableCommand, final boolean isDistinct) {
        this.tableCommand = tableCommand;
        this.ignoreEmptyField = true;
        this.isDistinct = isDistinct;
    }

    /**
     * Constructor
     * @param tableCommand
     */
    public QueryBuilder(final TableActionCommand tableCommand) {
        this(tableCommand, false);
    }
    /**
     * This Constructor adds Primary key to Order By clause to work correctly order by for Non Unique Columns
     * @param tableCommand
     * @param primaryKey
     * @param table
     */
    public QueryBuilder(final TableActionCommand tableCommand,
            final Field primaryKey, final Table table) {
        primaryKeySort = table.getValue() + "." + primaryKey;
        this.tableCommand = tableCommand;
        this.ignoreEmptyField = true;
        this.isDistinct = false;
    }
    
    /**
     * <p>Use this if you want to order by multiple columns in our SQL.</p>
     * @param tableCommand
     * @param orderbyColumns
     * @param table
     */
    public QueryBuilder(final TableActionCommand tableCommand, final OrderBy[] orderBys, final Table table) {
        this.orderBys = orderBys;
        this.tableCommand = tableCommand;
        this.ignoreEmptyField = true;
        this.isDistinct = false;
    }

    /**
     * Somtime we don't neeed to have a table name prefixed to the order by columns.
     *
     * @param tableCommand
     * @param primaryKey
     */
    public QueryBuilder(final TableActionCommand tableCommand,
            final Field primaryKey) {
        primaryKeySort = primaryKey.name();
        this.tableCommand = tableCommand;
        this.ignoreEmptyField = true;
        this.isDistinct = false;
    }


    /**
     * Add Select item to query, prepending comma if not first
     *     select item
     * @param select Column
     * @param table Table prepended to column
     */
    public void addSelect(final Field select, final Table table) {
        checkAddSeparator(selectList, ",");
        selectList.append(table.getValue() + "." + select);
    }


    /**
     * Select field + alias
     * @param select
     * @param alias
     * @param table
     */
    public void addSelect(final Field select, final Field alias,
            final Table table) {
        checkAddSeparator(selectList, ",");
        selectList.append(table.getValue() + "." + select + " " + alias);
    }


    /**
     * Add AdvancedField item to query, prepending comma if not first
     *     select item - no Table required
     * @param select
     */
    public void addSelect(final AdvancedField select) {
        checkAddSeparator(selectList, ",");
        selectList.append(select.getValue());
    }

    /**
     * Allow alias for advance field.
     *
     * @param select
     * @param alias
     */
    public void addSelect(final AdvancedField select, final Field alias) {
        checkAddSeparator(selectList, ",");
        selectList.append(select.getValue() + " " + alias);
    }

    public void addSelect(final NullField select) {
        checkAddSeparator(selectList, ",");
        selectList.append(select.getVal());
    }
    
    /**
     * Adds the hints to the query to take the advantage of oracle hints
     * @param hint
     */
    public void addIgnoreOrderBy(final boolean ignoreOrderBy){
        this.ignoreOrderBy = ignoreOrderBy;
    }
    
    /**
     * Adds the hints to the query to take the advantage of oracle hints
     * @param hint
     */
    public void addHint(final String hint){
        this.hint = hint;
    }
    /**
     * Join on table1.field1 = table2.field2
     * @param table1
     * @param field1
     * @param table2
     * @param field2
     */
    public void addTableJoin(final Table table1, final Field field1,
            final Table table2, final Field field2) {
        checkAddSeparator(whereClause, " AND ");
        whereClause.append(table1.getValue() + "." + field1 + " = "
                + table2.getValue() + "." + field2);
    }

    public void addTableJoin(final Table table1, final Field field1,
            final Table table2, final AdvancedField field2) {
        checkAddSeparator(whereClause, " AND ");
        whereClause.append(table1.getValue() + "." + field1 + " = "
                + table2.getValue() + "." + field2.getValue());
    }

    public void addOuterTableJoin(final Table table1, final Field field1,
            final Table table2, final Field field2, final Table outerJoinTable,
            final Field outerJoinField) {
        checkAddSeparator(whereClause, " AND ");
        whereClause.append(table1.getValue());
        whereClause.append(".");
        whereClause.append(field1);
        if (field1 == outerJoinField && table1 == outerJoinTable) {
            whereClause.append("(+)");
        }

        whereClause.append(" = ");
        whereClause.append(table2.getValue());
        whereClause.append(".");
        whereClause.append(field2);
        if (field2 == outerJoinField && table2 == outerJoinTable) {
            whereClause.append("(+)");
        }
    }

    /**
     * Add GROUP BY to query, prepending comma if not first grouping
     * @param table
     */
    public void addGroupBy(final Field group) {
        checkAddSeparator(groupList, ",");
        groupList.append(group);
    }

    /**
     * Add GROUP BY to query, prepending comma if not first grouping
     * @param table
     */
    public void addGroupBy(final AdvancedField group) {
        checkAddSeparator(groupList, ",");
        groupList.append(group.getValue());
    }


    /**
     * Return paged query sql with ? place holders for criteria
     * @return
     */
    public String getPagedQuerySql() {
        return getQuerySql(true, true);
    }

    /**
     * Return all results (non-paged)
     * @return
     */
    @Override
    public String getSql() {
        return getQuerySql(true, false);
    }

    public String getSingleResultQuerySql() {
        return getQuerySql(false, false);
    }

    /**
     * This method check for Order By based on multiple column names
     * @param orderBy orderBy
     * @param direction direction
     * @return
     */
    protected String getOrderbyClause(final List<String> sortByColumns,
            final String direction) {
        // TODO refactor. We are adding this logic into this existing method to have a minimum impact and changes to the API.
        final StringBuilder columns = new StringBuilder();
        if(orderBys != null) {
            userSuppliedOrderBy(columns);
        } else{
        if(sortByColumns != null){
            for(final String column: sortByColumns){
                checkAddSeparator(columns, ",");
                columns.append(column).append(" ").append(direction);
            }
        }
        if (primaryKeySort != null) {
            checkAddSeparator(columns, ",");
            columns.append(primaryKeySort).append(" ").append(direction);
        }
        }
        return columns.toString();
    }

    private void userSuppliedOrderBy(final StringBuilder columns) {
        for(final OrderBy orderBy : orderBys){
            checkAddSeparator(columns, ",");
            columns.append(orderBy.toString()).append(" ");
        }
    }


    /**
     *
     * @param multipleResults Multiple results
     * @param page Page results
     * @return
     */

    protected String getQuerySql(final boolean multipleResults,
            final boolean page) {
        final StringBuilder sql = new StringBuilder(526);
        if (multipleResults && page) {
            sql.append(" SELECT * FROM ");
            sql.append(" ( SELECT ROWNUM r ");
            sql.append("        , query.* ");
            sql.append("     FROM ( ");
        }

        sql.append(" SELECT ");

        if(hint != null) {
            sql.append(hint);
        }

        if (isDistinct) {
            sql.append(" DISTINCT ");
        }

        sql.append(selectList);
        sql.append(getInnerSql());
        if (multipleResults) {
        	if(!ignoreOrderBy){
        		sql.append(" ORDER BY ");
        		sql.append(getOrderbyClause(tableCommand.getSortByColumns(), tableCommand.getSortDirection()));
        	}

            if (page) {
                sql.append(" ) query WHERE ROWNUM <= ");
                sql.append(tableCommand.getEndIndex() + " ) WHERE r >= " + tableCommand.getStartIndex());
            }
        }

        return sql.toString();
    }

    public String getQueryForCountSql() {
        final StringBuilder sql = new StringBuilder(526);
        sql.append("SELECT count(0) FROM ");
        sql.append(tableList);
        sql.append(" WHERE ");
        sql.append(whereClause);
        return sql.toString();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Query - ");
        return stringBuilder.append(getPagedQuerySql()).append("\n Param values - ").append(
                Arrays.toString(getCriteriaValues())).toString();
    }
    
    
    @Override
    public int hashCode() {
        return getSql().hashCode();
    }

    /**
     * The equals is based on the generated SQL (String) as the business key.
     * The criteria values are irrelevant.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.getSql() == null){
            return false;
        }
        if(this.getSql().equals(((QueryBuilder)obj).getSql())) {
            return true;
        }
        return false;
    }
}
