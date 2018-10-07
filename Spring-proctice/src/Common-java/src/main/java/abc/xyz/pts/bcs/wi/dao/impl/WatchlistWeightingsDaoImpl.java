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
package abc.xyz.pts.bcs.wi.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.wi.dao.WatchlistWeightingsDao;
import abc.xyz.pts.bcs.wi.dto.WatchlistWeightings;

/**
 * @author ryattapu
 *
 */
public class WatchlistWeightingsDaoImpl implements  WatchlistWeightingsDao {
    private JdbcDaoSupport daoSupport;
    private final CustomRowMapper<WatchlistWeightings> rowMapper = new CustomRowMapper<WatchlistWeightings>(WatchlistWeightings.class);
    private static final String QUERY
                    = " SELECT FULL_NAME, BIRTH_DATE, GENDER, NATIONALITY, BIRTH_COUNTRY, DOC_TYPE, DOC_NUMBER,WEIGHT_TYPE"
                    + " FROM   WATCHLIST_WEIGHTINGS";

    @Override
    public List<WatchlistWeightings> getWatchlistWeightingsFromDataSource() {
        final List<WatchlistWeightings>  watchlistWeightings = daoSupport.getJdbcTemplate().query(QUERY, rowMapper);
        return watchlistWeightings;
    }

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

}
