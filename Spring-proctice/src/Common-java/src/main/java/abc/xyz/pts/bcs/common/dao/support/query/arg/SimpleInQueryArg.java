/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;


/**
 *    Value list arg object for IN WHERE clauses (e.g. table1.field1 IN (?) )
 */
public class SimpleInQueryArg extends QueryArg {


    public SimpleInQueryArg() {

    }

    /**
     * Constructor.
     * @param searchField Search field name
     * @param table Table to prepend field name
     * @param value Criteria values
     */
    public SimpleInQueryArg(final Field searchField, final Table table, final Object... values) {

        setInQuery(searchField, table, values);

    }

    public SimpleInQueryArg(final Field searchField, final Table table, final List values) {

        if(!CollectionUtils.isEmpty(values)){
            setInQuery(searchField, table, values.toArray());
        }

    }

    public SimpleInQueryArg(final Field field, final Table table, final String subQuery) {
        setInSubQuery(field, table, subQuery);
    }

    public void setInQuery(final Field searchField, final Table table, final Object... values) {

        final StringBuilder placeHolders = new StringBuilder();

        for (final Object value : values) {
            if (value != null && !"".equals(value)) {
                if (placeHolders.length() > 0) {
                    placeHolders.append(",");
                }
                placeHolders.append("?");
                addSearchCriteria(value);
            }
        }
        if (placeHolders.length() == 0) {
            // return no results
            addToWhereClause(" 1=2 ");
        } else {
            addToWhereClause(table.getValue() + "." + searchField + " IN (" + placeHolders + ")");
        }
    }

    public void setInSubQuery(final Field field, final Table table, final String subQuery) {
        addToWhereClause(table.getValue() + "." + field + " IN (" + subQuery + ")");
    }

    /**
     * Return false if has null values
     * @param values
     * @param otherValues
     * @return no null values
     */
    public boolean hasNoNullValues(final String[] values, final String... otherValues) {
        if (hasNoNullValues(values) && hasNoNullValues(otherValues)) {
            return true;
        }
        return false;
    }

    public boolean hasNoNullValues(final String... values) {
        for (final String value : values) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }


}
