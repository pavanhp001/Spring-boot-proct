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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import abc.xyz.pts.bcs.common.dao.support.AbstractDAO;
import abc.xyz.pts.bcs.wi.dao.PrSeverityReasonDao;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;

@Repository("prSeverityReasonDao")
public class PrSeverityReasonDaoImpl extends AbstractDAO implements PrSeverityReasonDao{
    private static final String query = "select code, description,  SEVERITY_LEVEL from reason_codes where DELETED_IND is NULL order by upper(description)";
    private static final String query_intl = "select code, description_lang description, SEVERITY_LEVEL from reason_codes where DELETED_IND is NULL order by description";

    @Override
    public List <SeverityReason> findAllSeverityReason(final Locale locale) {
        if(locale == null || "en".equals(locale.getLanguage())) {
             return getJdbcTemplate().query(query, new PrSeverityReasonMapper());
        } else {
             return getJdbcTemplate().query(query_intl, new PrSeverityReasonMapper());
        }
    }

    private static final class PrSeverityReasonMapper implements RowMapper<SeverityReason> {
        @Override
        public SeverityReason mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
            final SeverityReason severityReason = new SeverityReason();
            severityReason.setCode(resultSet.getString("code"));
            severityReason.setDescription(resultSet.getString("description"));
            severityReason.setSeverityLevel(resultSet.getLong("SEVERITY_LEVEL"));
        return severityReason;
        }
    }

    @Override
    public Map<String, SeverityReason> findAllSeverityReasonMap(final Locale locale) {
        final List<SeverityReason> reasonList = findAllSeverityReason(locale);
        final Map<String, SeverityReason> reasonMap = new HashMap<String, SeverityReason>(reasonList.size());
        for (final SeverityReason reason : reasonList)
        {
            reasonMap.put(reason.getCode(), reason);
        }
        return reasonMap;
    }

}
