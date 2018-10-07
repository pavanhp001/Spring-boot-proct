package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleInQueryArg;


public class SimpleInQueryArgTest extends TestCase {

	
	@Test
	public void testInQueryArg() {
		
		SimpleInQueryArg arg = new SimpleInQueryArg(Field.MOVEMENT_STATUS, Table.TRAVELLERS, "X", "Y");
		
		assertEquals(Table.TRAVELLERS.getValue() + "." + Field.MOVEMENT_STATUS + " IN (?,?)", arg.getWhereClause());
		assertEquals("X", arg.getCriteriaValues().get(0));
	}
	
	@Test
	public void testNoInQueryArg() {
		
		SimpleInQueryArg arg = new SimpleInQueryArg(Field.MOVEMENT_STATUS, Table.TRAVELLERS);
		assertEquals(" 1=2 ", arg.getWhereClause());
	}
	
	
	@Test
	public void testInQueryArgWithArgsInList() {
		List<String> list = new ArrayList<String>();
		list.add("X");
		list.add("Y");
		
		SimpleInQueryArg arg = new SimpleInQueryArg(Field.MOVEMENT_STATUS, Table.TRAVELLERS, list);

		assertEquals(Table.TRAVELLERS.getValue() + "." + Field.MOVEMENT_STATUS + " IN (?,?)", arg.getWhereClause());
		assertEquals("X", arg.getCriteriaValues().get(0));
	}
}
