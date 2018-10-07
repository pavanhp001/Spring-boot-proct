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
 *    Simple arg object for simple WHERE clauses (e.g. table1.field1 = ? )
 */
public class NotEqualsQueryArg extends QueryArg {


    /**
     * Constructor.
     * @param searchField Search field name
     * @param table Table to prepend field name
     * @param value Criteria value
     */
    public NotEqualsQueryArg(final Field searchField, final Table table, final Object value) {

        if (value != null && !"".equals(value)) {

            addToWhereClause(table.getValue() + "." + searchField + "!=?");

            addSearchCriteria(value);
        }
    }



}
