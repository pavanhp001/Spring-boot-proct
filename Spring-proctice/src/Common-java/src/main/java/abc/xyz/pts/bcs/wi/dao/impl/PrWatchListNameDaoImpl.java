/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import abc.xyz.pts.bcs.common.dao.support.AbstractDAO;
import abc.xyz.pts.bcs.wi.dao.PrWatchListNameDao;
import abc.xyz.pts.bcs.wi.dto.WatchListName;

@Repository("prWatchListNameDao")
public class PrWatchListNameDaoImpl extends AbstractDAO implements PrWatchListNameDao {
    private static final String EN_LANGUAGE = "en";

    private static final String query = "select name, description, enabled_ind enabled from watch_lists " +
            "    where enabled_ind= 'Y' and deleted_ind is null order by upper(description)";

    private static final String query_intl = "select name, description_lang description, enabled_ind enabled " +
            "    from watch_lists " +
            "    where enabled_ind= 'Y' and deleted_ind is null order by description";

    private static final String query_include_disabled = "select name, description || decode(enabled_ind,'Y','',' #') description, " +
            "    enabled_ind enabled from watch_lists where deleted_ind is null order by upper(description)";

    private static final String query_intl_include_disabled = "select name, description_lang || decode(enabled_ind,'Y','',' #') description," +
            " enabled_ind enabled from watch_lists where deleted_ind is null order by description";

    @Override
    public List<WatchListName> findEnabledWatchListNames(final Locale locale) {

        if (locale == null || EN_LANGUAGE.equals(locale.getLanguage())) {
            return getJdbcTemplate().query(query, new WatchListNameMapper());
        } else {
            return getJdbcTemplate().query(query_intl, new WatchListNameMapper());
        }
    }

    @Override
    public List<WatchListName> findAllWatchListNames(final Locale locale) {

        if (locale == null || EN_LANGUAGE.equals(locale.getLanguage())) {
            return getJdbcTemplate().query(query_include_disabled, new WatchListNameMapper());
        } else {
            return getJdbcTemplate().query(query_intl_include_disabled, new WatchListNameMapper());
        }

    }

    private static final class WatchListNameMapper implements RowMapper<WatchListName> {

        @Override
        public WatchListName mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
            final WatchListName watchListName = new WatchListName();
            watchListName.setCode(resultSet.getString("name"));
            watchListName.setDescription(resultSet.getString("description"));
            return watchListName;
        }
    }

}
