/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
/**
 * @author Deepesh.Rathore
 *
 */
public class ObjectUtilsTest {

	@Test
	public void testGivenSourceObjectHasNullCalendarProperty_WhenCopyPropertiesCalled_ThenNullCalendarPropertyDoesntThrowException() {

		Command dest = new Command();
		Command orig = new Command();
		orig.setNumber(100);
		orig.setBigDecimal(new BigDecimal("100.00"));
		
		new ObjectUtils().copyProperties(dest, orig);
			
		assertThat(dest.getNumber(), is(orig.getNumber()));
		
		assertThat(dest.getCalendar(), is(orig.getCalendar()));
	}
	
	@Test(expected = CopyFailedException.class)
	public void testGivenSourceObjectHasNullBigDecimalProperty_WhenCopyPropertiesCalled_ThenNullBigDecimalPropertyThrowsException() {

		Command dest = new Command();
		Command orig = new Command();
		orig.setNumber(100);
		
		new ObjectUtils().copyProperties(dest, orig);
			
		assertThat(dest.getNumber(), is(orig.getNumber()));
		
		assertThat(dest.getCalendar(), is(orig.getCalendar()));
	}
	
}


