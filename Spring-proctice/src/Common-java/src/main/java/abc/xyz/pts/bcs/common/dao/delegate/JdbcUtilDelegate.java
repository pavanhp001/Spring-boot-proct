/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.delegate;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import abc.xyz.pts.bcs.common.dao.support.query.CountBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.utils.JdbcUtil;

/**
 * <p>This is a delegator to delegate all the static calls to {@link JdbcUtil} to facilitate ease of mocking/testing.</p>
 * @author Sai / Soheil
 * @see JdbcUtil
 */
public class JdbcUtilDelegate {

    /**
     * <p>Delegates to {@link JdbcUtil#executeQuery(JdbcTemplate, RowMapper, QueryBuilder)}</p>
     */
    public <T> List<T> executeQuery(final JdbcTemplate jdbcTemplate, final RowMapper<T> rowMapper, final QueryBuilder queryBuilder) {
        return  JdbcUtil.executeQuery(jdbcTemplate, rowMapper, queryBuilder);
    }

    /**
     * <p>Delegates to {@link JdbcUtil#executeQuery(JdbcTemplate, RowMapper, QueryBuilder)}</p>
     */
    public <T> List<T> executeAllQuery(final JdbcTemplate template, final RowMapper<T> rowMapper, final QueryBuilder builder) {
        return  JdbcUtil.executeAllQuery(template, rowMapper, builder);
    }

    /**
     * <p>Delegates to {@link JdbcUtil#executeQuery(JdbcTemplate, RowMapper, QueryBuilder)}</p>
     */
    public int executeQueryForCount(final JdbcTemplate template, final CountBuilder builder) {
        return JdbcUtil.executeQueryForCount(template, builder);
    }
}
