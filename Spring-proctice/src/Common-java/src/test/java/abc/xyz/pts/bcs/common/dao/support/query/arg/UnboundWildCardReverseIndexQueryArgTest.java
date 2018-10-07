/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;


/**
 * @author Deepesh.Rathore
 *
 */
public class UnboundWildCardReverseIndexQueryArgTest {

	@Test
	public void shouldReturnValidWhereClause_UsingReverseKeyword_WithAsteriskWildCard() {
		String expectedResult = "REVERSE("+Table.TRAVELLERS + "." +Field.PRI_DOC_NO+")"+ " LIKE REVERSE("+"'%1234')" ; 
		UnboundWildCardReverseIndexQueryArg arg = new UnboundWildCardReverseIndexQueryArg("*1234", Field.PRI_DOC_NO, Table.TRAVELLERS);
		assertThat(arg.getWhereClause(), equalTo(expectedResult) ) ;  
	}
	
	@Test
	public void shouldReturnValidWhereClause_UsingReverseKeyword_WithApostropheWildCardAtTheStart() {
		String expectedResult = "REVERSE("+Table.TRAVELLERS + "." +Field.FORENAME+")"+ " LIKE REVERSE("+ "'%AS''D')" ; 
		UnboundWildCardReverseIndexQueryArg arg = new UnboundWildCardReverseIndexQueryArg("*AS'D", Field.FORENAME, Table.TRAVELLERS);
		assertThat(arg.getWhereClause(), equalTo(expectedResult) ) ; 
	}
	
	
	
	@Test
	public void shouldReturnValidWhereClause_UsingReverseKeyword_WithApostropheWithoutWildCard() {
		String expectedResult = Table.TRAVELLERS + "." +Field.FORENAME + " = 'AS''D'" ; 
		UnboundWildCardReverseIndexQueryArg arg = new UnboundWildCardReverseIndexQueryArg("AS'D", Field.FORENAME, Table.TRAVELLERS);
		assertThat(arg.getWhereClause(), equalTo(expectedResult) ) ;
	}
	
	
	@Test
	public void shouldReturnValidWhereClause_UsingReverseKeyword_WithApostropheWithWildCardAtTheEnd() {
		String expectedResult = Table.TRAVELLERS + "." +Field.FORENAME + " LIKE ('AS''D%')" ; 
		UnboundWildCardReverseIndexQueryArg arg = new UnboundWildCardReverseIndexQueryArg("AS'D*", Field.FORENAME, Table.TRAVELLERS);
		assertThat(arg.getWhereClause(), equalTo(expectedResult) ) ; 
	}
	
}
