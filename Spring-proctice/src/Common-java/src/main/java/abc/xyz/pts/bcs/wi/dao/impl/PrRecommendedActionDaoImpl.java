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
import abc.xyz.pts.bcs.wi.dao.PrRecommendedActionDao;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;

@Repository("prRecommendedActionDao")
public class PrRecommendedActionDaoImpl extends AbstractDAO implements PrRecommendedActionDao
{
    private static final String ACTION_CODE_ALL = "select code, description, app_action_code, auto_qualify_ind from action_codes where deleted_ind is null order by upper(description)";
    private static final String ACTION_CODE_ALL_INTL  = "select code, description_lang description, app_action_code, auto_qualify_ind from action_codes where deleted_ind is null order by description";

    private static final String ACTION_CODE_EXCL_HIDDEN = "select code, description, app_action_code, auto_qualify_ind from action_codes where deleted_ind is null and NVL(hidden_ind, 'N') <> 'Y' order by upper(description)";
    private static final String ACTION_CODE_EXCL_HIDDEN_INTL = "select code, description_lang description, app_action_code, auto_qualify_ind from action_codes where deleted_ind is null and NVL(hidden_ind, 'N') <> 'Y' order by description";

    public List <RecommendedAction> findAllRecommendedAction() {
        return findAllRecommendedAction(Locale.ENGLISH);
    }

    @Override
    public List <RecommendedAction> findAllRecommendedAction(final Locale locale) {
        if(locale == null || Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
             return getJdbcTemplate().query(ACTION_CODE_ALL, new RecommendedActionMapper());
        } else {
                return getJdbcTemplate().query(ACTION_CODE_ALL_INTL, new RecommendedActionMapper());
        }
    }

    @Override
    public List<RecommendedAction> findVisibleRecommendedAction(final Locale locale)
    {
        if (locale == null || Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
            return getJdbcTemplate().query(ACTION_CODE_EXCL_HIDDEN, new RecommendedActionMapper());
        }
        else {
            return getJdbcTemplate().query(ACTION_CODE_EXCL_HIDDEN_INTL, new RecommendedActionMapper());
        }
    }

    private static final class RecommendedActionMapper implements RowMapper<RecommendedAction> {

        @Override
        public RecommendedAction mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
            final RecommendedAction recommendedAction = new RecommendedAction();
            recommendedAction.setCode(resultSet.getString("code"));
               recommendedAction.setDescription(resultSet.getString("description"));
            recommendedAction.setAppActionCode(resultSet.getString("app_action_code"));
            recommendedAction.setAutoQualifyInd(resultSet.getString("auto_qualify_ind"));
        return recommendedAction;
        }
    }

}
