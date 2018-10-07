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

public class AuditEventParametersExpiryDateCheckArg extends SimpleInQueryArg {

    public AuditEventParametersExpiryDateCheckArg() {
        addToWhereClause(" ( ");
        addToWhereClause(Table.EVENT_PARAMETERS + "." + Field.EXPIRY_DATE + " is null ");
        addToWhereClause(" OR ");
        addToWhereClause(Table.EVENT_PARAMETERS + "." + Field.EXPIRY_DATE + " > sysdate ) ");
    }
}
