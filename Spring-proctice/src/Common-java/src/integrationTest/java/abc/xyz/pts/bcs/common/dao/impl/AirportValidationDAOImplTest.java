/**
 * 
 */
package abc.xyz.pts.bcs.common.dao.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * @author Sudheendra.Singh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = true)
public class AirportValidationDAOImplTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	AirportValidationDAOImpl airportValidationDAO;
	
    @Test
    public void testIsAirportValid(){
    	boolean validAirport = airportValidationDAO.isAirportValid("SLA");
    	Assert.assertTrue(validAirport);
    }

}
