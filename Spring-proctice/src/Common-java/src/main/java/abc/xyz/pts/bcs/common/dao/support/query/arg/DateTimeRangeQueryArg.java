/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.Date;

import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

/**
 *
 * @author Sathishbabu C
 *
 */
public class DateTimeRangeQueryArg extends QueryArg {

    /**
     * Constructor to handle Date and Time Range
     *
     * @param from
     *            - The start date and time of the search.
     * @param to
     *            - The end date and time of the search.
     * @param field
     *            - The DB field from where the info should be read
     * @param table
     *            - The DB table from where the required info should be read
     */
    public DateTimeRangeQueryArg(final Date from, final Date to, final Field field, final Table table) {

        applyArg(from, to, table.getValue() + "." + field);

    }

    /**
     * Constructor to handle Date and Time Range
     *
     * @param from
     *            - The start date and time of the search.
     * @param to
     *            - The end date and time of the search.
     * @param field
     *            - The Advanced field from where the info should be read
     */
    public DateTimeRangeQueryArg(final Date from, final Date to, final AdvancedField field) {
        applyArg(from, to, field.getValue());
    }

    private void applyArg(final Date from, final Date to, final String nameToBeUsedForWhereClause) {
        if (from != null) {
            addSearchCriteria(from);
            addToWhereClause(nameToBeUsedForWhereClause);
            if (to != null) {

                addSearchCriteria(to);
                addToWhereClause(" BETWEEN ? AND ? ");

            } else {
                addToWhereClause(" = ? ");
            }
        } else if (to != null) {
            addToWhereClause(nameToBeUsedForWhereClause + " <= ? ");
            addSearchCriteria(to);
        }
    }

}
