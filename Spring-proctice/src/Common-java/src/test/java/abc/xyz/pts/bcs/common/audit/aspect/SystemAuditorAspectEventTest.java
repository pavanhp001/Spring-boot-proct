/**
 * 
 */
package abc.xyz.pts.bcs.common.audit.aspect;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import abc.xyz.pts.bcs.common.audit.business.SystemAuditor;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Match;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Response;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.TravelDocument;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Traveller;

import com. .pts.bcs.irisk.common.FlightSegmentSummary;

/**
 * @author John Templer and Kasi Subramaniam
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath*:Audit-test.xml"})  

public class SystemAuditorAspectEventTest { 
	
	@Autowired
	SystemAuditor systemAuditor;
	
	@Autowired
	MockAuditSender auditSender;

	private Response response;
	private Match match;
	
	@Before
	public void setup(){
		response = new Response();
		response.setFlightSegmentSummary(getFlightSegmentSummary());
		response.getRequest().add(getTraveller());
		
		match = new Match();
		match.setClearedDocumentsId(1L);
		auditSender.getAuditEvents().clear();

	}
	
	@Test
	public void testAutoClearHit() {
		
		// Given
		long referralId = 0;
		
		// When
		systemAuditor.auditClearedDocumentHit(response, match, referralId);
		
		// Then
		AuditEvent ae = auditSender.getAuditEvents().get(0);
		Assert.assertNotNull(ae);
		Assert.assertThat(ae.getName(), is("AUTO.CLEAR.HIT"));
		Assert.assertEquals(ae.getParameters().size(), 14);
		auditSender.getAuditEvents().clear();
		
	}
	
	private Traveller getTraveller() {
		Traveller t = new Traveller();
		t.setTraId(101);
		t.setSurname("LASTNAME");
		t.setForeName("FORENAME");
		TravelDocument doc = new TravelDocument();
		doc.setDocumentNo("DOC1");
		t.getTravelDocuments().add(doc);
		return t;
	}
	
	private FlightSegmentSummary getFlightSegmentSummary() {
		FlightSegmentSummary fs = new FlightSegmentSummary();
		fs.setArrAirportCode("DOH");
		fs.setDepAirportCode("LHR");
		fs.setFlightSegId(1000);
		fs.setOperCarrierCode("BA");
		fs.setOperFlightNo("1782");
		fs.setScheduledArrDateTime("2011-09-06");
		fs.setScheduledDepDateTime("2011-09-05");
		return fs;
	}
	
  
}
