/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import abc.xyz.das.dascore.model.generated.output.idetectdb.FlightType;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.irisk.mvo.ap.TypeOfTravelType;


/**
 *    Travel Type arg object
 */
public class AppTravelTypeQueryArg extends SimpleInQueryArg {


    public AppTravelTypeQueryArg
        ( final Table segmentsTable
        , final Field flightType
        , final Table manifestTable
        , final Field travellerType)
    {
        addToWhereClause(" ( ");
            addToWhereClause(" ( ");
                addToWhereClause(segmentsTable +  "." + flightType + " = '" + FlightType.I + "'");
                  addToWhereClause(" AND ");
                      addToWhereClause(" ( ");
                        addToWhereClause(manifestTable.getValue() + "." + travellerType + " = '" + TypeOfTravelType.TRANSFER.name() + "'");
                          addToWhereClause(" OR ");
                        addToWhereClause(manifestTable.getValue() + "." + travellerType + " = '" + TypeOfTravelType.TRANSIT.name() + "'");
                    addToWhereClause(" ) ");
                      addToWhereClause(" OR ");
                  addToWhereClause(manifestTable.getValue() + "." + travellerType + " = '" + TypeOfTravelType.NORMAL.name() + "'");
                  addToWhereClause(" ) ");
         addToWhereClause(" ) ");
    }


}
