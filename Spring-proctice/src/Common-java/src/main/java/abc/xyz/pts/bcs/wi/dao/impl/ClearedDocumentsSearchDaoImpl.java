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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.wi.dao.ClearedDocumentsSearchDao;
import abc.xyz.pts.bcs.wi.dto.GenericIDBean;
import abc.xyz.pts.bcs.wi.dto.TargetItem;

/**
 * @author JTempler
 * @author Kasi.Subramaniam - Add Cleared Document functionality - QAT 586.
 *
 */
public class ClearedDocumentsSearchDaoImpl implements ClearedDocumentsSearchDao
{
    private JdbcDaoSupport daoSupport;
    private final CustomRowMapper<GenericIDBean> rowMapper = new CustomRowMapper<GenericIDBean>(GenericIDBean.class);

    private static final Logger log = Logger.getLogger(ClearedDocumentsSearchDaoImpl.class);

    DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy");

    private static final String SELECT_QUERY =
        "SELECT cd.id " +
            " FROM CLEARED_DOCUMENTS cd" +
            " WHERE" +
            " cd.TARWL_ID = ?" +
            " AND cd.FORENAME = ?" +
            " AND cd.LAST_NAME = ?" +
            " AND cd.NATIONALITY = ?" +
            " AND TO_CHAR(cd.BIRTH_DATE,'dd-MM-YYYY') = ?" +
            " AND cd.DOC_NO = ?" +
            " AND cd.DOC_TYPE = ?" +
            " AND cd.GENDER = ?" +
            " AND TRUNC(cd.VALID_UNTIL_DATE) >= TRUNC(sysdate)";

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

    @Override
    public Long getClearedDocId(final Long targetId, final TargetItem traveller) {

        if (log.isDebugEnabled()) {
            final DateTime t = new DateTime(traveller.getBirthDate().getTime());
            final String birthDate = fmt.print(t);
            log.debug(    " Target WL Id : " + targetId +
                        " Forename : " + traveller.getForename() +
                        " Lastname : " + traveller.getLastName() +
                        " Nationality : " + traveller.getNationality() +
                        " Birth date : " + birthDate +
                        " Doc No : " + traveller.getDocNo() +
                        " Doc Type : " + traveller.getDocType() +
                        " Gender : " + traveller.getGender()
                    );
        }

        // If the birth date is null or any of the other fields, we don't need do a check against
        // cleared docs since it will always fail as birthdate is
        // a mandatory field in cleared_documents
        if (traveller.getBirthDate() == null || traveller.getNationality() == null || traveller.getForename() == null
                || traveller.getLastName() == null || traveller.getDocNo() == null || traveller.getDocType() == null
                || traveller.getGender() == null) {
            return null;
        }

        final List<GenericIDBean> beanList = daoSupport.getJdbcTemplate().query(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                    final PreparedStatement ps =
                    connection.prepareStatement(SELECT_QUERY, new String[] {"id"});
                    ps.setLong(1, targetId);
                    ps.setString(2, traveller.getForename());
                    ps.setString(3, traveller.getLastName());
                    ps.setString(4, traveller.getNationality());
                    final DateTime t = new DateTime(traveller.getBirthDate().getTime());
                    final String birthDate = fmt.print(t);
                    ps.setString(5, birthDate);
                    ps.setString(6, traveller.getDocNo());
                    ps.setString(7, traveller.getDocType());
                    ps.setString(8, traveller.getGender());
                    return ps;
                }
            }, rowMapper);

        if (beanList != null && beanList.size() >= 1) {
            return beanList.get(0).getId();
        }
        return null;
    }
}