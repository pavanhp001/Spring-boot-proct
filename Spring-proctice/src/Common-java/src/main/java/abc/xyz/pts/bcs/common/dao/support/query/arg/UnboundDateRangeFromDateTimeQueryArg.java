/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.utils.Constants;

/**
 * Can be used if the date time can be used directly without the need of a bind
 * variable eg : date_time between 'to_date('15-12-2011 10:30:00','dd-mm-yyyy hh24:mi:ss') and
 * 'to_date('16-12-2011 13:45:00','dd-mm-yyyy hh24:mi:ss')
 *
 * Should deal elegantly with either date set to NULL
 *
 * rather than date_time between ? and ?
 *
 */
public class UnboundDateRangeFromDateTimeQueryArg extends
        DateRangeQueryArg {

    /**
     * Overloaded Constructor that takes Calendar objects
     *
     * @param from
     * @param to
     * @param field
     * @param table
     */
    public  UnboundDateRangeFromDateTimeQueryArg(final Calendar  from, final Calendar to,  final Field field, final Table table)
    {
        final SimpleDateFormat displayFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
        final StringBuilder builder = new StringBuilder();

        if (from != null) {
            builder.append(table.getValue())
                    .append(".")
                    .append(field);
            if (to != null) {
                builder.append(" BETWEEN to_date('")
                        .append(displayFormat.format( from.getTime() ))
                        .append("','")
                        .append(Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi)
                        .append("') AND to_date('")
                        .append(displayFormat.format(to.getTime()))
                        .append("','")
                        .append(Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi)
                        .append("') ");

            } else {
                builder.append(" = to_date('")
                        .append(displayFormat.format( from.getTime() ))
                        .append("','")
                        .append(Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi)
                        .append("') ");
            }
        } else if (to != null) {
            builder.append(table.getValue())
                    .append(".")
                    .append(field)
                    .append(" <= to_date('")
                    .append(displayFormat.format(to.getTime()))
                    .append("','")
                    .append(Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi)
                    .append("') ");
        }
        addToWhereClause(builder.toString());
    }
}