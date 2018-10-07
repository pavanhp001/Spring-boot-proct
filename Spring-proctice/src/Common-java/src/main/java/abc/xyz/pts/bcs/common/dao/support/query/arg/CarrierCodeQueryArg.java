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

import abc.xyz.pts.bcs.common.dao.support.query.Table;


public class CarrierCodeQueryArg extends QueryArg {

    /**
     * Constructor
     * @param carrierCode
     */
    public CarrierCodeQueryArg(final String carrierCode) {

        if (StringUtils.isNotBlank(carrierCode)) {
            addTable(Table.FLIGHT_CODE_SHARES);
            addSearchCriteria(carrierCode);
            addToWhereClause(
                 Table.FLIGHT_CODE_SHARES.getValue() + ".flts_flight_seg_id = "
                     + Table.FLIGHT_SEGMENTS.getValue() + ".flight_seg_id "
                     + " AND " + Table.FLIGHT_CODE_SHARES.getValue() + ".carrier_code = ? ");
        }
    }

}
