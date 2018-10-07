package abc.xyz.pts.bcs.common.dao.support.query.arg;

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;


public class FuzzyTravellerQueryArgTest extends TestCase {

	
	@Test
	public void testFuzzyTravellerFirstAndLastNameQueryArg() {
		
		FuzzyTravellerQueryArg arg = new FuzzyTravellerQueryArg(Field.FORENAME, 
				Field.LAST_NAME,
				Field.FORENAME.name(),
				Field.LAST_NAME.name());
		
		assertEquals(" CONTAINS(FULL_NAME,'NDATA(FORENAME,FORENAME) AND NDATA(LAST_NAME,LAST_NAME)',1)>0", 
				arg.getWhereClause());

	}

	@Test
	public void testFuzzyTravellerFirstNameQueryArg() {		

		FuzzyTravellerQueryArg arg1 = new FuzzyTravellerQueryArg(Field.FORENAME, 
				Field.LAST_NAME,
				Field.FORENAME.name(),
				"");		

		assertEquals(" CONTAINS(FULL_NAME,'NDATA(FORENAME,FORENAME)',1)>0", 
				arg1.getWhereClause());

	}
	
	@Test
	public void testFuzzyTravellerLastNameQueryArg() {		
		
		FuzzyTravellerQueryArg arg2 = new FuzzyTravellerQueryArg(Field.FORENAME, 
				Field.LAST_NAME,
				"",
				Field.LAST_NAME.name());	

		assertEquals(" CONTAINS(FULL_NAME,'NDATA(LAST_NAME,LAST_NAME)',1)>0", 
				arg2.getWhereClause());
	}
}
