/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.cpr.dao.impl;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.business.lookup.bean.NationalityBean;
import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.cpr.dao.CountryDao;

/**
 * @author Deepesh.Rathore
 *
 */
public class CountryDaoImpl implements CountryDao {

    private JdbcDaoSupport daoSupport;
    private final CustomRowMapper<NationalityBean> rowMapper = new CustomRowMapper<NationalityBean>(NationalityBean.class);

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }


    /* (non-Javadoc)
     * @see abc.xyz.pts.bcs.cpr.dao.CountryDao#getCountries()
     */
    @Override
    public List<NationalityBean> getCountries() {

        final Locale locale = LocaleContextHolder.getLocale();
        String localeShort;
        if (locale != null  && StringUtils.isNotEmpty(locale.toString())){
            localeShort = locale.toString().substring(0,2);
        }else{
            // EN(English) is the default locale
            localeShort = "EN";
        }

        final StringBuilder sbQuery = new StringBuilder();
        sbQuery.append(" SELECT CODE, COUNTRY_NAME_SHORT_");
        sbQuery.append(localeShort);
        sbQuery.append(" || ' (' || CODE || ')' DESCRIPTION FROM COUNTRY ORDER BY COUNTRY_NAME_SHORT_");
        sbQuery.append(localeShort);
        final String query = sbQuery.toString();

        //List<NationalityBean> nationalityBeansList = daoSupport.getJdbcTemplate().query(QUERY, rowMapper);
        final List<NationalityBean> nationalityBeansList = daoSupport.getJdbcTemplate().query(query, rowMapper);
        return nationalityBeansList;
    }

}
