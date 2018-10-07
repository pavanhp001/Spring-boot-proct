package abc.xyz.pts.bcs.common.dao.support.query.arg;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.WildCardQueryArg;

import junit.framework.TestCase;

public class LastNameQueryArgTest extends TestCase {

	@Test
	public void testLastNameQueryArg() {
		
		WildCardQueryArg arg = new WildCardQueryArg("Mike", Field.FORENAME, Table.TRAVELLERS);
		assertEquals(Table.TRAVELLERS.getValue() + ".FORENAME = ? ", arg.getWhereClause());
		
		arg = new WildCardQueryArg("M*", Field.FORENAME, Table.TRAVELLERS);
		assertEquals(Table.TRAVELLERS.getValue() + ".FORENAME LIKE ? ", arg.getWhereClause());
		
		arg = new WildCardQueryArg("Jones", Field.LAST_NAME, Table.TRAVELLERS);
		assertEquals(Table.TRAVELLERS.getValue() + ".LAST_NAME = ? ", arg.getWhereClause());
		
		arg = new WildCardQueryArg("Jon*", Field.LAST_NAME, Table.TRAVELLERS);
		assertEquals(Table.TRAVELLERS.getValue() + ".LAST_NAME LIKE ? ", arg.getWhereClause());
	}
	
}
