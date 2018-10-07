package abc.xyz.pts.bcs.common.dao.support.query.arg;

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.DateRangeFromDateTimeArg;

public class DateRangeFromDateTimeArgTest extends TestCase {

	
	@Test
	public void testDateRangeQueryArg() {
		
		DateRangeFromDateTimeArg arg = new DateRangeFromDateTimeArg("01-12-2030", "10-12-2030", Field.DEP_DATE, Table.TRAVELLERS);
		assertEquals(Table.TRAVELLERS.getValue() + "." + Field.DEP_DATE + " BETWEEN TO_DATE('01-12-2030', 'dd-MM-yyyy') AND TO_DATE('11-12-2030', 'dd-MM-yyyy') ", arg.getWhereClause());
	
		arg = new DateRangeFromDateTimeArg("01-12-2030", null, Field.DEP_DATE, Table.TRAVELLERS);
		assertEquals(Table.TRAVELLERS.getValue() + "." + Field.DEP_DATE + " BETWEEN TO_DATE('01-12-2030', 'dd-MM-yyyy') AND TO_DATE('02-12-2030', 'dd-MM-yyyy') ", arg.getWhereClause());
		
		arg = new DateRangeFromDateTimeArg(null, null, Field.DEP_DATE, Table.TRAVELLERS);
		assertEquals(null, arg.getWhereClause());
		
		
	}
	
}
