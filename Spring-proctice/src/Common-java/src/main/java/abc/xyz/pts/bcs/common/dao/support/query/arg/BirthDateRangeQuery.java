/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.Calendar;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

public class BirthDateRangeQuery extends QueryArg
{

    /**
     * The folowing SQL is constructed.
     *
     * SQL:
     *      ( table.field = dt
     *      OR (table.field IS NULL AND ? BETWEEN table.fieldFrom AND table.fieldTo)
     *      )
     *
     * @param dt
     * @param field
     * @param fieldFrom
     * @param fieldTo
     * @param table
     */
    public BirthDateRangeQuery
        ( final Calendar dt
        , final Field field
        , final Field fieldFrom
        , final Field fieldTo
        , final Table table
        )
    {
        if (dt == null) {
            return;
        }

        String tblField = "TRUNC( " + table.getValue() + "." + field + ")";
        String tblFieldFrom = "TRUNC( " + table.getValue() + "." + fieldFrom + ")";
        String tblFieldTo = "TRUNC( " + table.getValue() + "." + fieldTo + ")";

        // ( BIRTH_DATE = ?
        //  OR (birth_date IS NULL AND ? BETWEEN birth_date_from AND birth_date_to)
        //  )
        String sql =  " ( " + tblField + " = ? "
                    + " OR (" + tblField + " IS NULL AND ? BETWEEN " + tblFieldFrom + " AND " + tblFieldTo + ") "
                    + " ) "
                    ;
        addToWhereClause(sql);

        addSearchCriteria(dt);
        addSearchCriteria(dt);
    }

}

