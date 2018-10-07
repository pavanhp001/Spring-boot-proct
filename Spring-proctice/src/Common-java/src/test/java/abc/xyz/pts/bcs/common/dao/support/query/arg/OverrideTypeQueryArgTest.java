package abc.xyz.pts.bcs.common.dao.support.query.arg;

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.OverrideTypeQueryArg;


public class OverrideTypeQueryArgTest extends TestCase {


	@Test
	public void testOverrideTypeWithOverrideQueryArg() {
		
		OverrideTypeQueryArg arg = new OverrideTypeQueryArg(Table.FLIGHT_MANIFESTS, Field.OVERRIDE_TYPE, "N", "A", "G");		
		assertEquals(null, arg.getWhereClause());
		
		arg = new OverrideTypeQueryArg(Table.FLIGHT_MANIFESTS, Field.OVERRIDE_TYPE, "");		
		assertEquals(null, arg.getWhereClause());	
				
	}
	
	@Test
	public void testOverrideTypeNoOverrideQueryArg() {
		
		OverrideTypeQueryArg arg = new OverrideTypeQueryArg(Table.FLIGHT_MANIFESTS, Field.OVERRIDE_TYPE, null, "A", "G");
		
		assertEquals(" ( " + Table.FLIGHT_MANIFESTS.getValue() + "." + Field.OVERRIDE_TYPE + " IN (?,?) ) ", arg.getWhereClause());
		assertEquals("G", arg.getCriteriaValues().get(1));
		assertEquals("A", arg.getCriteriaValues().get(0));
		
		arg = new OverrideTypeQueryArg(Table.FLIGHT_MANIFESTS, Field.OVERRIDE_TYPE, null, null, "G");
		assertEquals(" ( " + Table.FLIGHT_MANIFESTS.getValue() + "." + Field.OVERRIDE_TYPE + " IN (?) ) ", arg.getWhereClause());
		assertEquals("G", arg.getCriteriaValues().get(0));	
		
	}
	
	@Test
	public void testOverrideTypeBlankQueryArg() {		
		OverrideTypeQueryArg arg = new OverrideTypeQueryArg(Table.FLIGHT_MANIFESTS, Field.OVERRIDE_TYPE, null);		
		assertEquals( " (  1=2  ) " , arg.getWhereClause());
	}
	
}
