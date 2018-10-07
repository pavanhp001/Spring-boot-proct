/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

public class AuditEventTypeCodeArg extends SimpleInQueryArg {


    public AuditEventTypeCodeArg(final List<String> eventTypeCodes) {

        if(CollectionUtils.isNotEmpty(eventTypeCodes)) {
            addToWhereClause(" (");
            for (int index = 0; index<eventTypeCodes.size(); index++) {
                addToWhereClause(Table.V_AUDIT_LOGS + "." + Field.EVENT_TYPE_CODE + "='" + eventTypeCodes.get(index) +"'");
                if (index != eventTypeCodes.size() - 1){
                    addToWhereClause(" OR ");
                }
            }
            addToWhereClause(" )");
        }
    }

}
