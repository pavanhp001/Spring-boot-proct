/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.business.lookup.bean.CPRDataSourceBean;
import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

public class CPRDataSourceDao {

    private JdbcDaoSupport daoSupport;

    private final CustomRowMapper<CPRDataSourceBean> rowMapper = new CustomRowMapper<CPRDataSourceBean>(CPRDataSourceBean.class);

    private static final String QUERY
                    = " SELECT " + Field.DATA_SOURCE_NAME
                    + " FROM " + Table.DATA_SOURCES
                    + " WHERE " + Field.ENABLED_IND + " = 'Y'";

    public List<CPRDataSourceBean> getCPRDataSource() {
        final List<CPRDataSourceBean> dataSourceList = daoSupport.getJdbcTemplate().query(QUERY, rowMapper);
        return dataSourceList;
    }

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

}
