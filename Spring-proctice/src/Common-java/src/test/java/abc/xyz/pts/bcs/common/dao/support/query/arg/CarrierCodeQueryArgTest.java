package abc.xyz.pts.bcs.common.dao.support.query.arg;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.CarrierCodeQueryArg;
import junit.framework.TestCase;

public class CarrierCodeQueryArgTest extends TestCase {

	
	@Test
	public void testCarrierCodeQueryArg() {
		CarrierCodeQueryArg arg = new CarrierCodeQueryArg("ABC");
		assertTrue(arg.getWhereClause().contains(" AND " + Table.FLIGHT_CODE_SHARES.getValue() + ".carrier_code = ? "));
		
	}
	
	@Test
	public void testNullCarrierCodeQueryArg() {
		CarrierCodeQueryArg arg = new CarrierCodeQueryArg("");
		assertNull(arg.getCriteriaValues());
	}
}
