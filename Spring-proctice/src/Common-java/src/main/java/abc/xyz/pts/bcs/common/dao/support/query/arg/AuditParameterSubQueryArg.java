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

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

/**
 * Travel Type arg object
 */
public class AuditParameterSubQueryArg extends QueryArg {

    public AuditParameterSubQueryArg
        ( final Table table
        , final Field field
        , final String parameterName
        , final String parameterValue
       )
    {


    final String subQueryWithParameterValue = " EXISTS ( SELECT 1 FROM  "+Table.V_AUDIT_LOG_PARAMETERS+" audlp, "+Table.EVENT_PARAMETERS+" evep, "
                                          + Table.PARAMETERS+" par WHERE audlp."+Field.AUDL_ID+" = "+Table.V_AUDIT_LOGS+"."+Field.ID+" AND audlp."+Field.EVEP_ID+" = evep."+Field.ID+" AND "
                                          + " par."+Field.ID+" = evep."+Field.PAR_ID+"  AND par."+Field.NAME+" = ? "
                                          + " AND UPPER(audlp."+Field.VALUE+") = UPPER(?) ) ";

        if (StringUtils.isNotEmpty(parameterName))
        {
            addToWhereClause(subQueryWithParameterValue);
            addSearchCriteria(parameterName);
            addSearchCriteria(parameterValue);
        }
    }

}
