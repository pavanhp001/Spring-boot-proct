/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.impl;

import abc.xyz.pts.bcs.common.dao.AirportValidationDAO;
import abc.xyz.pts.bcs.common.dao.support.AbstractDAO;

public class AirportValidationDAOImpl extends AbstractDAO implements AirportValidationDAO {

    @Override
    public boolean isAirportValid(final String airportCode) {

        final String sql = "SELECT count(*) FROM AIRPORT_DESIGNATIONS " +
            " WHERE (iata_airport_code = ? OR icao_airport_code = ?)";

        final int out = getJdbcTemplate().queryForInt(
                sql, new Object[]{airportCode, airportCode});
        return (out > 0);
    }
}
