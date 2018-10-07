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

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

public class DateTimeRangeQueryArgTest extends TestCase {

    @Test
    public void testDateTimeRangeQueryArg() {

        DateTimeRangeQueryArg arg = new DateTimeRangeQueryArg(new Date(), new Date(), Field.SCHEDULED_DATETIME,
                Table.SCHEDULES_MASTER);
        assertEquals(Table.SCHEDULES_MASTER.getValue() + "." + Field.SCHEDULED_DATETIME + " BETWEEN ? AND ? ",
                arg.getWhereClause());

    }
}
