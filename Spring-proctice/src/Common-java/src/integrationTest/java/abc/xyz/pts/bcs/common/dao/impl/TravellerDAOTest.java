/**
 * 
 */
package abc.xyz.pts.bcs.common.dao.impl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;

/**
 * @author John Templer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = true)
public class TravellerDAOTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	TravellerDAO travellerDAO;
	
    @Test
    public void testChangeStatus(){
    	Long travellerId;
    	List<Traveller> travellers = travellerDAO.getTravellers();
    	if (travellers.size() > 0) {
    		travellerId = travellers.get(0).getId();

    		travellerDAO.updateRiskAssessmentToCompleted(travellerId);
    		Traveller t = travellerDAO.findTravellersById(travellerId);
    		Assert.assertEquals(t.getRiskAssessmentStatus(), TravellerDAO.RISK_ASSESSMENT_STATUS_COMPLETED);
    		resetTravellerStatus(travellerId);
    		
    		travellerDAO.updateRiskAssessmentToFailed(travellerId);
    		t = travellerDAO.findTravellersById(travellerId);
    		Assert.assertEquals(t.getRiskAssessmentStatus(), TravellerDAO.RISK_ASSESSMENT_STATUS_FAILED);
    		resetTravellerStatus(travellerId);

    		travellerDAO.updateRiskAssessmentToInProgress(travellerId);
    		t = travellerDAO.findTravellersById(travellerId);
    		Assert.assertEquals(t.getRiskAssessmentStatus(), TravellerDAO.RISK_ASSESSMENT_STATUS_INPROGRESS);
    		resetTravellerStatus(travellerId);    		
    		
    	} else {
    		Assert.fail("Missing traveller data for test");
    	}
    }
    
    private void resetTravellerStatus(final Long travellerId) {
		// restore status
		travellerDAO.updateRiskAssessmentToNotStarted(travellerId);
		Traveller t = travellerDAO.findTravellersById(travellerId);
		Assert.assertEquals(t.getRiskAssessmentStatus(), TravellerDAO.RISK_ASSESSMENT_STATUS_NOTSTARTED);
    	
    }
}
