package abc.xyz.pts.bcs.common.dao.support.query.arg;


import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.FlightRouteQueryArg;

public class FlightRouteQueryArgTest extends TestCase {

	@Test
	public void testFlightRouteQueryArg() {
		
		FlightRouteQueryArg arg = new FlightRouteQueryArg(Table.FLIGHT_SEGMENTS, "ABC");
		
		assertTrue(arg.getWhereClause().contains("AND (fltr1.dep_airport_code = ? OR fltr1.arr_airport_code = ?"));
		
		arg = new FlightRouteQueryArg(Table.FLIGHT_SEGMENTS, "ABC-DEF");
		assertTrue(arg.getWhereClause().contains("AND fltr1.dep_airport_code = ?"));
		
		arg = new FlightRouteQueryArg(Table.FLIGHT_SEGMENTS, "ABC-DEF-GHI");
		assertTrue(arg.getWhereClause().contains("AND fltr2.dep_airport_code = ?"));
		
		assertEquals("ABC", arg.getCriteriaValues().get(0));
		assertEquals("DEF", arg.getCriteriaValues().get(1));
		assertEquals("DEF", arg.getCriteriaValues().get(2));
		assertEquals("GHI", arg.getCriteriaValues().get(3));
		
	}
	
	@Test
	public void testNullFlightRouteQueryArg() {
		
		FlightRouteQueryArg arg = new FlightRouteQueryArg(Table.FLIGHT_SEGMENTS, "");
		assertNull(arg.getCriteriaValues());
	}
}
