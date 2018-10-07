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

/**
 * For Advance SQL
 *
 * @author MMotlib-Siddiqui
 *
 */
public final class QueryBuilderAdvance
    extends QueryBuilder
{
    private final List<QueryBuilder> queryBuilderList;
    private WithSelectQueryBuilder withQb;          // optional
    private final List<String> staticOrderBy;
    private boolean ignoreRowNum;

    public QueryBuilderAdvance
        ( final TableActionCommand actionCmd
        , final Field orderByField
        )
    {
        super(actionCmd, orderByField);
        this.queryBuilderList = new ArrayList<QueryBuilder>(2);
        this.staticOrderBy = new ArrayList<String>();
    }

    @Override
    public String getPagedQuerySql()
    {
        return getQuerySql(true, true);
    }

    @Override
    public String getQuerySql(final boolean multipleResults, final boolean page)
    {
        final StringBuilder sql = new StringBuilder();

        // *******
        // * options With Statement
        // *******
        if (withQb != null) {
            sql.append(withQb.getPrefixWithSQL());
        }

        if (multipleResults && page)
        {
            sql.append(" SELECT * FROM ");
            sql.append(" ( SELECT ROWNUM r ");
            sql.append("        , query.* ");
            sql.append("     FROM ( ");
        }

        // *****
        // * This is where we join up all the QueryBuilders
        // *****
        boolean firstQuery = true;
        for (final QueryBuilder qb : queryBuilderList)
        {
            if (firstQuery == false) {
                sql.append(" UNION ALL ");
            }
            firstQuery = false;

            sql.append(qb.getQuerySql(false, false));
        }

        //
        if (multipleResults)
        {
            sql.append(" ORDER BY ");
            sql.append(getOrderbyClause(tableCommand.getSortByColumns(), tableCommand.getSortDirection()));

            // do we have any static order by?
            for (final String orderByCol : this.staticOrderBy)
            {
                sql.append(",");
                sql.append(orderByCol);
            }

            if (page && this.ignoreRowNum == false)
            {
                 sql.append(" ) query WHERE r <= ");
                 sql.append(tableCommand.getEndIndex() + ") WHERE r >= " + tableCommand.getStartIndex());
            }
            else if (page)
            {
                // since its already being narrowed
                sql.append(" ) query ");
                sql.append(" ) ");
            }
        }

        return sql.toString();
    }

    public List<Object> getCriteria()
    {
        return this.criteriaValues;
    }


    public void setEstimationSql(final QueryBuilder qb)
    {
        estimationSql = qb.getInnerSql();
        estimationCriteriaValues = qb.criteriaValues;
    }

    public WithSelectQueryBuilder getWithQb()
    {
        return withQb;
    }

    public void setWithQb(final WithSelectQueryBuilder withQb)
    {
        this.withQb = withQb;
    }

    public List<QueryBuilder> getQueryBuilderList()
    {
        return queryBuilderList;
    }

    public List<String> getStaticOrderBy()
    {
        return staticOrderBy;
    }

    public boolean isIgnoreRowNum()
    {
        return ignoreRowNum;
    }

    public void setIgnoreRowNum(final boolean ignoreRowNum)
    {
        this.ignoreRowNum = ignoreRowNum;
    }

    @Override
    public Object[] getEstimationCriteriaValues()
    {
        return estimationCriteriaValues.toArray();
    }

}
