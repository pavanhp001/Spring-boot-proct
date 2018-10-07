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
package abc.xyz.pts.bcs.common.dao;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.business.lookup.bean.CarrierTypeBean;
import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;

/**
 */
public class CarrierTypesDao  {

    private JdbcDaoSupport daoSupport;

    private final CustomRowMapper<CarrierTypeBean> rowMapper = new CustomRowMapper<CarrierTypeBean>(CarrierTypeBean.class);

    private static final String QUERY
                    = " SELECT " + Field.CODE
                    + " FROM " + Table.CARRIER_TYPES
                    + " WHERE " + Field.ENABLED_IND + " = 'Y'";

    public List<CarrierTypeBean> getCarrierTypes() {
        final List<CarrierTypeBean> carrierTypes = daoSupport.getJdbcTemplate().query(QUERY, rowMapper);
        return carrierTypes;
    }

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

}
