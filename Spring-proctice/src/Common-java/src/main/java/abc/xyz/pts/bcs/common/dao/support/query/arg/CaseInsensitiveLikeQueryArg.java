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
 *
 * @author ryattapu
 *
 */
public class CaseInsensitiveLikeQueryArg extends QueryArg {
    private static final String PECENT = "%";

    /**
     * Constructor.
     * @param searchField Search field name
     * @param table Table to prepend field name
     * @param value Criteria value
     */
    public CaseInsensitiveLikeQueryArg(final Field searchField, final Table table, Object value) {

        if (value != null && !"".equals(value)) {
            value = PECENT + value + PECENT;
            addToWhereClause("UPPER(" + table.getValue() + "." + searchField + ") LIKE(UPPER(?))");
            addSearchCriteria(value);
        }
    }
}
