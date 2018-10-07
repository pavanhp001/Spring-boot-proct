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

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

/**
 * Specialised query arg for handling hit types within the referral search DAO.
 *
 * @author cwalker
 */
public class HitTypeQueryArg extends QueryArg {


    public HitTypeQueryArg(final Field field, final Table tbl, final List<String> hitTypes) {

        if (hitTypes != null && hitTypes.size() > 0) {

            final StringBuilder builder = new StringBuilder();
            String type;
            for (int i = 0, j = hitTypes.size(); i < j; i++) {

                if (i > 0) {
                    builder.append(", ");
                }

                type = hitTypes.get(i);
                builder.append("?");
                addSearchCriteria(type);

            }

            addToWhereClause("EXISTS (SELECT 1 FROM " + Table.REFERRAL_HITS +
                    " WHERE " + tbl + "." + field + " = " + Field.REF_ID +
                    " AND HIT_TYPE IN (" + builder.toString() + "))");
        }
    }
}
