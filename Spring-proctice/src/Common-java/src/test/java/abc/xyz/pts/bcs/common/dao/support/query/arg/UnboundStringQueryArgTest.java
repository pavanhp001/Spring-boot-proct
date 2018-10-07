/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class UnboundStringQueryArgTest {
	
	@Test
	public void shouldReturnValidWhereClause_AValidString() {
		UnboundStringQueryArg stringWithoutBindingVariableArg = new UnboundStringQueryArg(Field.GENDER, Table.TRAVELLERS, "M");
		assertThat(stringWithoutBindingVariableArg.getWhereClause(),equalTo(Table.TRAVELLERS+"."+Field.GENDER+"="+"'M' "));
	}

}
