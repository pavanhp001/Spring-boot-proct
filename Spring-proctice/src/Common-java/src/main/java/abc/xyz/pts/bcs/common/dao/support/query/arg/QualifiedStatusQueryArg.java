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

public class QualifiedStatusQueryArg extends QueryArg {

    private static final String QUALIFY_STATUS_ALL = "all";

    public QualifiedStatusQueryArg(final Table tbl, final Field valueField, final String value) {

        if(value != null && !QUALIFY_STATUS_ALL.equalsIgnoreCase(value)){
            addToWhereClause("EXISTS (SELECT 1 FROM " + tbl + " WHERE " + valueField + " = ? AND REF_ID = RFRL.id )");
            addSearchCriteria(value);
        }
    }

}
