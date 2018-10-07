/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
/**
 * @author Deepesh.Rathore
 *
 */
public class UnboundDateRangeFromDateTimeQueryArgTest {


	@Test
	public void testWhereClause_WhenToDateIsNull() throws Exception {
		
		Calendar todaysDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
		String formattedDate = dateFormat.format( todaysDate.getTime() );
		
		UnboundDateRangeFromDateTimeQueryArg queryArg = new UnboundDateRangeFromDateTimeQueryArg(todaysDate, null, Field.ACTION_DATE, Table.ACTION_CODES);
		String whereClause = queryArg.getWhereClause();
		
		String expectedWhereClause = Table.ACTION_CODES + "." + Field.ACTION_DATE + " = to_date('"+formattedDate+"','"+Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi+"')";
		assertThat(whereClause.trim(), equalTo(expectedWhereClause));
	}
	
	@Test
	public void testWhereClause_WhenToDateIsNotNull() throws Exception {
		
		Calendar fromDate = Calendar.getInstance();

		fromDate.add(Calendar.DATE, -4 );
		Calendar toDate = Calendar.getInstance();
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
		String formattedFromDate = dateFormat.format( fromDate.getTime() );
		String formattedToDate = dateFormat.format( toDate.getTime() );
		
		UnboundDateRangeFromDateTimeQueryArg queryArg = new UnboundDateRangeFromDateTimeQueryArg(fromDate, toDate, Field.ACTION_DATE, Table.ACTION_CODES);
		String whereClause = queryArg.getWhereClause();
		
		String expectedWhereClause = Table.ACTION_CODES + "." + Field.ACTION_DATE + 
			" BETWEEN to_date('"+formattedFromDate+"','"+Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi+"')" +
			" AND to_date('"+formattedToDate+"','" +Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi+  "')";
		
		assertThat(whereClause.trim(), equalTo(expectedWhereClause));
	}
	
	
	@Test
	public void testWhereClause_WhenFromDateIsNull() throws Exception {
		
		Calendar todaysDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
		String formattedDate = dateFormat.format( todaysDate.getTime() );
		
		UnboundDateRangeFromDateTimeQueryArg queryArg = new UnboundDateRangeFromDateTimeQueryArg(null, todaysDate, Field.ACTION_DATE, Table.ACTION_CODES);
		String whereClause = queryArg.getWhereClause();
		
		String expectedWhereClause = Table.ACTION_CODES + "." + Field.ACTION_DATE + " <= to_date('"+formattedDate+"','"+Constants.ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi+"')";
		assertThat(whereClause.trim(), equalTo(expectedWhereClause));
	}
	
	
	@Test
	public void testWhenBothDatesNull_ThenWhereClauseIsEmpty() throws Exception {
		UnboundDateRangeFromDateTimeQueryArg queryArg = new UnboundDateRangeFromDateTimeQueryArg(null, null, Field.ACTION_DATE, Table.ACTION_CODES);
		String whereClause = queryArg.getWhereClause();
		assertThat(whereClause.trim(), is(StringUtils.EMPTY));
	}

}
