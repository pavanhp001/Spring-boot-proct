/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;


import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

public class TimeRangeQueryArg extends AbstractTimeRangeQueryArg {

    public TimeRangeQueryArg(final String fromTimeString, final String toTimeString, final AdvancedField field) {
    super(fromTimeString, toTimeString, field);
    }

    public TimeRangeQueryArg(final String fromTimeString, final String toTimeString, final Field field, final Table table) {
        super(fromTimeString, toTimeString, field, table);
    }

    @Override
    protected int getTime(final TimeHolder timeHolder) {
        return (timeHolder.getHours() * 3600) + (timeHolder.getMins() * 60);
    }
}
