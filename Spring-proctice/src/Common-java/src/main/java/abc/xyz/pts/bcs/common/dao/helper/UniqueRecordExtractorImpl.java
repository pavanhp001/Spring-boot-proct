/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
/**
 * @author Deepesh.Rathore
 *
 */
public class UniqueRecordExtractorImpl<T> implements UniqueRecordExtractor<T>{
	
	private static final Logger LOG = Logger.getLogger(UniqueRecordExtractorImpl.class);
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 */
	public UniqueRecordExtractorImpl(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public T getUniqueRecord(final String query, final CustomRowMapper<T> rowMapper,  final Object[] paramValues){
		
		
        List<T> dataList = jdbcTemplate.query(query, paramValues, rowMapper);
        if (noRecordsExist(dataList)) {
            return null;
        }
        
        // In case we get more than we expected
        if (moreThanOneRecord(dataList)) {
            LOG.warn("Expected 1 row got " + dataList.size() + " rows...returning 1st row");
        }
        
		return  dataList.get(0);
	}

	private boolean moreThanOneRecord(final List<T> dataList) {
		return dataList.size() > 1;
	}

	private boolean noRecordsExist(final List<T> dataList) {
		return dataList == null || dataList.isEmpty();
	}
}