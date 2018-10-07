package abc.xyz.pts.bcs.common.dao.support.query.arg;

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.TravelTypeQueryArg;


public class TravelTypeQueryArgTest extends TestCase {

	
	@Test
	public void testTravelTypeQueryArg() {
		
		TravelTypeQueryArg arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, Table.FLIGHT_MANIFESTS , null, null, "TRANSIT", "NORMAL");
		assertTrue(arg.getWhereClause().contains("FLIGHT_MANIFESTS.TYPE_OF_TRAVEL IN (?,?)"));
		assertEquals("TRANSIT", arg.getCriteriaValues().get(0));
		
		arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, Table.FLIGHT_MANIFESTS, "UNMATCHED", "TRANSFER", null, null, null);
		assertTrue(arg.getWhereClause().contains(Field.TYPE_OF_TRAVEL + " = 'TRANSFER'"));
		assertTrue(arg.getWhereClause().contains("1=2"));
		assertFalse(arg.getWhereClause().contains(Field.CONNECTING_FLIGHT_TRA_ID.name()));
		
		arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, Table.FLIGHT_MANIFESTS, "TRANSFER", null, null, null);
		assertTrue(arg.getWhereClause().contains(Field.TYPE_OF_TRAVEL + " = 'TRANSFER'"));
		assertTrue(arg.getWhereClause().contains("1=2"));
		assertTrue(arg.getWhereClause().contains(Field.CONNECTING_FLIGHT_TRA_ID + " IS NOT NULL"));
//		assertEquals("none", arg.getCriteriaValues().get(0));
		
		
		arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, Table.FLIGHT_MANIFESTS, null, "UNMATCHED", null, null);
		assertTrue(arg.getWhereClause().contains(Field.TYPE_OF_TRAVEL + " = 'TRANSFER'"));
		assertTrue(arg.getWhereClause().contains("1=2"));
		assertTrue(arg.getWhereClause().contains(Field.CONNECTING_FLIGHT_TRA_ID + " IS NULL"));
	}
	
	@Test
	public void testNoTravelTypeQueryArg() {
		
		TravelTypeQueryArg arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, Table.FLIGHT_MANIFESTS, null, null, null, null);
		assertTrue(arg.getWhereClause().contains("1=2"));
	}
	
	// QAT-1327 - have overloaded the TravelTypeQueryArg constructor to just
	// take in the TRAVELLERS table, as we're copying the travel type column
	// over to TRAVELLERS. We can remove the above test that refers to Table.FLIGHT_MANIFESTS
	// once all projects are updated to reflect the change in structure on
	// TRAVELLERS.
	
	@Test
	public void testTravelTypeQueryArgTravellerOnly() {
		
		TravelTypeQueryArg arg = new TravelTypeQueryArg(Table.TRAVELLERS, 
				Field.TYPE_OF_TRAVEL,
				"UNMATCHED", 
				"TRANSFER",
				"TRANSIT", 
				"NORMAL");
		assertNull(arg.getWhereClause());
		
		arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, "UNMATCHED", "TRANSFER", null, null);
		assertTrue(arg.getWhereClause().contains(Field.TYPE_OF_TRAVEL + " = 'TRANSFER'"));
		assertTrue(arg.getWhereClause().contains("1=2"));
		assertFalse(arg.getWhereClause().contains(Field.CONNECTING_FLIGHT_TRA_ID.name()));
		
		arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL,  "TRANSFER", null, null, null);
		assertTrue(arg.getWhereClause().contains(Field.TYPE_OF_TRAVEL + " = 'TRANSFER'"));
		assertTrue(arg.getWhereClause().contains("1=2"));
		assertTrue(arg.getWhereClause().contains(Field.CONNECTING_FLIGHT_TRA_ID + " IS NOT NULL"));
		
		
		arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, (String)null, "UNMATCHED", (String)null, (String)null);
		assertTrue(arg.getWhereClause().contains(Field.TYPE_OF_TRAVEL + " = 'TRANSFER'"));
		assertTrue(arg.getWhereClause().contains("1=2"));
		assertTrue(arg.getWhereClause().contains(Field.CONNECTING_FLIGHT_TRA_ID + " IS NULL"));
	}
	
	@Test
	public void testNoTravelTypeQueryArgTravellerOnly() {
		
		TravelTypeQueryArg arg = new TravelTypeQueryArg(Table.TRAVELLERS, Field.TYPE_OF_TRAVEL, (String)null, (String)null, (String)null, (String)null);
		assertTrue(arg.getWhereClause().contains("1=2"));
	}	
	
}
