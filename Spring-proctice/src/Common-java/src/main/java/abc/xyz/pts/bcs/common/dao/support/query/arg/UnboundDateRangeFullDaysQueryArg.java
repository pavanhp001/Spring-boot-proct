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
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;

/**
 * Can be used if the date time can be used directly without the need of a bind
 * variable eg : date_time between 'to_date('14-12-2011','dd-mm-yyyy') and
 * 'to_date('15-12-2011 23:59:59','dd-mm-yyyy hh24:mi:ss')
 *
 * rather than date_time between ? and ?
 *
 * Note : The time component of 23:59:59 is added to the to date.
 */
public class UnboundDateRangeFullDaysQueryArg extends
        DateRangeQueryArg {

    /**
     * Overloaded Constructor that takes Calendar objects
     *
     * @param from
     * @param to
     * @param field
     * @param table
     */
    public  UnboundDateRangeFullDaysQueryArg(final Calendar  from, final Calendar to,  final Field field, final Table table)
    {
        final StringBuilder builder = new StringBuilder();
        final String dateFrom = DateStringUtils.getStringFromCalendar(from) ;
        String dateTo = null ;

        // If both dates supplied, add 1 day to 'to' date
        // If only 'from' date supplied, set 'to' date as 'from' date + 1 day
        if (dateFrom != null && to == null) {
            dateTo = DateStringUtils.getStringFromCalendar(from);
        } else {
            dateTo = DateStringUtils.getStringFromCalendar(to);
        }
        builder.append(table.getValue())
                        .append(".")
                        .append(field)
                        .append(" BETWEEN to_date('")
                        .append(dateFrom).append("','")
                        .append(Constants.DATE_FORMAT_dd_mm_yyyy).append("') AND to_date('")
                        .append(dateTo)
                        .append(" ")
                        .append(Constants.TIME_23_59)
                        .append("','")
                        .append(Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi)
                        .append("') ");
        addToWhereClause(builder.toString());
    }
}
