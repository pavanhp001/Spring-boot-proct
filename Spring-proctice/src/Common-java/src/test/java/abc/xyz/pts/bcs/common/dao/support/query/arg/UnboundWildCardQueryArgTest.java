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
package abc.xyz.pts.bcs.common.dao.support.query.arg;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.UnboundWildCardQueryArg;

public class UnboundWildCardQueryArgTest {

	@Test
	public void shouldReturnValidWhereClause_GivenAsteriskWildcardCharacter() {
		String expectedWhereClause = Table.TRAVELLERS+"."+Field.PRI_DOC_NO+" LIKE '%1234'";
		UnboundWildCardQueryArg arg = new UnboundWildCardQueryArg("*1234", Field.PRI_DOC_NO, Table.TRAVELLERS);
		assertThat(arg.getWhereClause(),equalTo(expectedWhereClause));
	}
	
	@Test
	public void shouldReturnValidWhereClause_GivenPercentageWildcardCharacter() {
		String expectedWhereClause = Table.TRAVELLERS+"."+Field.PRI_DOC_NO+" LIKE '%1234'";
		UnboundWildCardQueryArg arg = new UnboundWildCardQueryArg("%1234", Field.PRI_DOC_NO, Table.TRAVELLERS);
		assertThat(arg.getWhereClause(),equalTo(expectedWhereClause));
	}
	
	@Test
	public void shouldReturnValidWhereClause_GivenNOWildcardCharacter() {
		String expectedWhereClause = Table.TRAVELLERS+"."+Field.PRI_DOC_NO+" = ? ";
		UnboundWildCardQueryArg arg = new UnboundWildCardQueryArg("1234", Field.PRI_DOC_NO, Table.TRAVELLERS);
		List<String> actualCriteriaValues = Arrays.asList(arg.getCriteriaValues().toArray(new String[0]));
		List<String> expectedCriteriaValues = Arrays.asList("1234");
		assertThat(arg.getWhereClause(),equalTo(expectedWhereClause));
		assertThat(actualCriteriaValues,equalTo(expectedCriteriaValues));
	}
}
