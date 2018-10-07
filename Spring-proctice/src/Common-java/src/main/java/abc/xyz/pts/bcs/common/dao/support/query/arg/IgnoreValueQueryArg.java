/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;


/**
 *    Arg object for simple WHERE clause ignoring if valueToIgnore = value
 */
public class IgnoreValueQueryArg extends QueryArg {


    /**
     *
     * @param searchField
     * @param table
     * @param valueToIgnore If equal to value, do not query with this arg
     * @param value Search value
     */
    public IgnoreValueQueryArg(final Field searchField, final Table table, final Object valueToIgnore, final Object value) {

        if (value != null && !"".equals(value) && !valueToIgnore.equals(value)) {

            addToWhereClause(table.getValue() + "." + searchField + "=?");

            addSearchCriteria(value);
        }
    }



}
