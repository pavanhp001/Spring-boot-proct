/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

public abstract class AbstractTimeRangeQueryArg extends QueryArg {

    public AbstractTimeRangeQueryArg(final String fromTimeString, final String toTimeString, final AdvancedField field) {
    applyArg(fromTimeString, toTimeString , field.getValue());
    }

    /**
     * Constructor
     * @param fromTimeString From time range (or = if no toTimeString) [hh:mm]
     * @param toTimeString To time range [hh:mm]
     * @param field [Field.DEP_TIME|Field.ARR_TIME]
     * @param table Table with time fields to be queried
     */
    public AbstractTimeRangeQueryArg(final String fromTimeString, final String toTimeString, final Field field, final Table table) {
        applyArg(fromTimeString, toTimeString , table.getValue() + "." + field );
    }

    private void applyArg(final String fromTimeString, final String toTimeString, final String nameToBeUsedForWhereClause) {
    // it is valid to have a blank from time.  In some searches, you do not specify a time.
    if (StringUtils.isNotBlank(fromTimeString)) {

            final int fromTime = getTime(new TimeHolder(fromTimeString));
            addSearchCriteria(fromTime);

            if (StringUtils.isBlank(toTimeString)) {
                addToWhereClause(nameToBeUsedForWhereClause +" = ? ");
            } else {

                final int toTime = getTime(new TimeHolder(toTimeString));
                addSearchCriteria(toTime);
                if (fromTime < toTime) {
                    addToWhereClause(nameToBeUsedForWhereClause + " BETWEEN ? AND ? ");
                } else {
                    addToWhereClause(" (" +nameToBeUsedForWhereClause + " >= ? OR "+ nameToBeUsedForWhereClause + " <= ? ) ");
                }
            }
        }
    }

    protected abstract int getTime(final TimeHolder timeToConvert);

    protected static class TimeHolder {

        private final int hours;
        private final int mins;

        public TimeHolder(final String time) {

            final String[] timeParts = time.split(":");
            hours = Integer.valueOf(timeParts[0]);
            mins = Integer.valueOf(timeParts[1]);
        }
        public int getHours() {
            return hours;
        }
        public int getMins() {
            return mins;
        }
    }

}
