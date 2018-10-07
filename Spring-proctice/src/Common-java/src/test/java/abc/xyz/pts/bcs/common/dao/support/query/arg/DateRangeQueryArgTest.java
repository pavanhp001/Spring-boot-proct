package abc.xyz.pts.bcs.common.dao.support.query.arg;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.DateRangeQueryArg;
import junit.framework.TestCase;


public class DateRangeQueryArgTest extends TestCase {

	
	@Test
	public void testDateRangeQueryArg() {
		
		DateRangeQueryArg arg = new DateRangeQueryArg("01-12-2030", "10-12-2030", Field.BIRTH_DATE, Table.TRAVELLERS);
		assertEquals("trunc(" + Table.TRAVELLERS.getValue() + "." + Field.BIRTH_DATE + ")" + " BETWEEN TO_DATE('01-12-2030', 'dd-MM-yyyy') AND TO_DATE('10-12-2030', 'dd-MM-yyyy') ", arg.getWhereClause());
	
		arg = new DateRangeQueryArg("01-12-2030", null, Field.BIRTH_DATE, Table.TRAVELLERS);
		assertEquals("trunc(" + Table.TRAVELLERS.getValue() + "." + Field.BIRTH_DATE + ")" + " = TO_DATE('01-12-2030', 'dd-MM-yyyy') ", arg.getWhereClause());
		
	}
	
}
