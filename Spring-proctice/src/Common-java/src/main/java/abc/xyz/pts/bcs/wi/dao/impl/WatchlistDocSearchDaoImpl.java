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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.enums.WeightingType;
import abc.xyz.pts.bcs.common.irisk.enums.AlertMatchTypes;
import abc.xyz.pts.bcs.wi.dao.WatchlistDocSearchDao;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchlistWeightings;

/**
 * @author ryattapu
 *
 */
public class WatchlistDocSearchDaoImpl implements WatchlistDocSearchDao
{

    private JdbcDaoSupport daoSupport;
    private final CustomRowMapper<TargetItem> rowMapper = new CustomRowMapper<TargetItem>(TargetItem.class);
    private LookupDataService lookupDataService;

    private static final Logger log = Logger.getLogger(WatchlistDocSearchDaoImpl.class);

    private static final String LANG = "_lang";

    private String getQuery(final boolean isInternational) {

        return " SELECT twl.id, twl.forename, twl.last_name, twl.watl_name, twl.gender "
                  + "      , TRUNC(twl.birth_date) birth_date "
                  + "      , TRUNC(twl.birth_date_from) birth_date_from "
                  + "      , TRUNC(twl.birth_date_to) birth_date_to "
                  + "      , twl.birth_place, twl.birth_cntry_code country_of_birth, twl.nationality, twl.doc_no "
                  + "       , twl.doc_type, twl.valid_until_date, twl.protocol_number, twl.created_by "
                  + "      , NVL(modified_datetime, created_datetime) date_of_last_change "
                  +    "       , twl.update_version_no "
                  + "      , wl.description" + (isInternational ? LANG : "") + " watl_desc "
                  + "        , ac.code actc_code "
                  + "        , ac.description" + (isInternational ? LANG : "") + " actc_code_desc "
                  + "        , ac.app_action_code app_action_code "
                  + "      , ac.auto_qualify_ind auto_qualify "
                  + "      , rc.code resc_code "
                  + "      , rc.description" + (isInternational ? LANG : "") + " resc_code_desc "
                  + "      , rc.severity_level "
                  + "      , TRUNC(twl.valid_until_date) valid_until_date "
                  + "   FROM target_watch_lists twl "
                  + "      , action_codes ac "
                  + "      , reason_codes rc "
                  + "      , watch_lists wl "
                  + "  WHERE twl.actc_code = ac.code "
                  + "    AND twl.resc_code = rc.code "
                  +    "    AND twl.watl_name = wl.name "
                  + "    AND twl.nationality = ? "
                  + "    AND twl.doc_no = ? "
                  + "    AND twl.id != NVL(?, 0) "
                  ;
    }


    private static final String WL_ENABLED_SQL = " AND wl.enabled_ind = 'Y' "  ;


    @Override
    public List<TargetItem> getMatches(final TargetItem target, final Locale locale) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("nat='" + target.getNationality()
                    + "' docNum='" + target.getDocNo()
                    + "' targetId=" + (target.getId() == null ? "NULL" : target.getId())
                    );
        }

        // ** Use Issue Counttry (if available)
        final String nationality = !StringUtils.isEmpty(target.getDocumentIssueCountry())
            ? target.getDocumentIssueCountry() : target.getNationality();

        StringBuffer sql;

        if(locale == null || "en".equals(locale.getLanguage())) {
           sql = new StringBuffer(getQuery(false));

        } else {
           sql = new StringBuffer(getQuery(true));

        }
            sql.append(WL_ENABLED_SQL) ;
        final ArrayList<Object> params = new ArrayList<Object>();
        params.add(nationality);
        params.add(target.getDocNo());
        params.add(target.getId());

        // ** Are we only looking at targets that fall within a valid end date?
        if (target.getValidUntilDate() != null)
        {
            sql.append("    AND TRUNC(?) <= TRUNC(twl.valid_until_date) ");
            params.add(target.getValidUntilDate());
        }


        final List<TargetItem> matchItemsList =
            daoSupport.getJdbcTemplate().query( sql.toString(), params.toArray(), rowMapper);

        if (log.isDebugEnabled()) {
            log.debug("doc search found " + matchItemsList.size() + " rows");
        }

        // ** Score the matches
        for (final TargetItem matchItem : matchItemsList)
        {
            matchItem.setMatchScore(calculateTotalScore(target, matchItem));
            matchItem.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
        }

        return matchItemsList;
    }

    private Double calculateTotalScore
        ( final TargetItem target
        , final TargetItem match
        ) throws Exception
    {
        WatchlistWeightings watchlistWeightings = null;

        Double baseScore = 0.0;
        watchlistWeightings = lookupDataService.getWatchlistWeightings(WeightingType.DOCUMENT);

        if (watchlistWeightings != null)
        {
            // ** Nationality
            baseScore += watchlistWeightings.getNationality();

            // ** Doc Number
            baseScore += watchlistWeightings.getDocNumber();

            // ** Country Of Birth
            if (!StringUtils.isEmpty(target.getCountryOfBirth()) && target.getCountryOfBirth().equals(match.getCountryOfBirth())) {
                baseScore += watchlistWeightings.getBirthCountry();
            }

            // ** Gender
            if (!StringUtils.isEmpty(target.getGender())&& target.getGender().equals(match.getGender())) {
                baseScore += watchlistWeightings.getGender();
            }

            // ** Doc Type
            if (!StringUtils.isEmpty(target.getDocType())&& target.getDocType().equals(match.getDocType())) {
                baseScore += watchlistWeightings.getDocType();
            }

            // ** Full name
            if ((!StringUtils.isEmpty(target.getForename()) && target.getForename().equals(match.getForename())) &&
                (!StringUtils.isEmpty(target.getLastName()) && target.getLastName().equals(match.getLastName())))
            {
                baseScore += watchlistWeightings.getFullName();
            }

            // ** BirthDate
            if (target.getBirthDate() != null)
            {
                final long tBirthDate = target.getBirthDate().getTimeInMillis();
                if (match.getBirthDate() != null)
                {
                    final long mBirthDate = match.getBirthDate().getTimeInMillis();

                    if (tBirthDate == mBirthDate) {
                        baseScore += watchlistWeightings.getBirthDate();
                    }
                }
                else if (match.getBirthDateFrom() != null && match.getBirthDateTo() != null)
                {
                    final long mBirthDateFrom = match.getBirthDateFrom().getTimeInMillis();
                    final long mBirthDateTo = match.getBirthDateTo().getTimeInMillis();

                    if (tBirthDate >= mBirthDateFrom && tBirthDate <= mBirthDateTo) {
                        baseScore += watchlistWeightings.getBirthDate();
                    }
                }
            }
        }

        return baseScore;
    }

    public void setDaoSupport(final JdbcDaoSupport daoSupport)
    {
        this.daoSupport = daoSupport;
    }

    public void setLookupDataService(final LookupDataService lookupDataService)
    {
        this.lookupDataService = lookupDataService;
    }



}