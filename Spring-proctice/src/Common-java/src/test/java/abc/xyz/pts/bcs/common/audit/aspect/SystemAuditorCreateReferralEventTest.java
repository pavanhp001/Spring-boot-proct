package abc.xyz.pts.bcs.common.audit.aspect;

import static org.hamcrest.CoreMatchers.is;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import abc.xyz.pts.bcs.common.audit.business.SystemAuditor;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.dto.FlightSegment;
import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Match;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Response;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.TravelDocument;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Traveller;

import com. .pts.bcs.irisk.common.FlightSegmentSummary;

/**
 * 
 * Unit Tests for the System Audit Event - REFERRAL.CREATED
 *  
 * @author John Templer  
 * @author Kasi Subramaniam
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath*:Audit-test.xml"})  

public class SystemAuditorCreateReferralEventTest {

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
		match.setId(2L);
		auditSender.getAuditEvents().clear();
		
	}
	
	@Test
	public void testAutoQualifyHit() {
		
		// When
		systemAuditor.auditAutoQualifiedHit(response,  getNewReferral().getId(), getReferralHit().getId());
		
		// Then
		AuditEvent ae = auditSender.getAuditEvents().get(0);
		Assert.assertNotNull(ae);
		Assert.assertThat(ae.getName(), is("AUTO.QUALIFIED.HIT"));
		Assert.assertEquals(ae.getParameters().size(), 14);
		auditSender.getAuditEvents().clear();
		
	}	
	
	@Test
	public void testAuditAddHitCreatedWithResponse()
	{

		systemAuditor.auditAddReferralHit(response, getReferralHit()) ; 
		
		AuditEvent ae = auditSender.getAuditEvents().get(0);
		Assert.assertNotNull(ae);
		
		Assert.assertEquals(auditSender.getAuditEvents().size(), 1);
		
		Assert.assertEquals(ae.getParameters().size(), 14);
		Assert.assertThat(ae.getName(), is("REFERRAL.ADD.HIT"));
		auditSender.getAuditEvents().clear();
		
	}	

	@Test
	public void testAuditAddHitCreatedWithTravellerDTO()
	{

		systemAuditor.auditAddReferralHit(getTravellerDTO(), getReferralHit(), getFlightSegment()); 
		
		AuditEvent ae = auditSender.getAuditEvents().get(0);
		Assert.assertNotNull(ae);
		
		Assert.assertEquals(auditSender.getAuditEvents().size(), 1);
		
		Assert.assertEquals(ae.getParameters().size(), 12);
		Assert.assertThat(ae.getName(), is("REFERRAL.ADD.HIT"));
		auditSender.getAuditEvents().clear();
		
	}	
	
	@Test
	public void testAuditReferralCreatedWithResponse()
	{
		long referralId = 0;
		long noOfHits = 100 ; 
		
		systemAuditor.auditReferralCreated(response, referralId, noOfHits) ; 
		
		AuditEvent ae = auditSender.getAuditEvents().get(0);
		Assert.assertNotNull(ae);
		
		Assert.assertEquals(auditSender.getAuditEvents().size(), 1);
		
		Assert.assertEquals(ae.getParameters().size(), 14);
		Assert.assertThat(ae.getName(), is("REFERRAL.CREATED"));
		auditSender.getAuditEvents().clear();
		
	}
	
	@Test
	public void testAuditReferralCreatedWithFlightSegment()
	{
		long noOfHits = 100 ; 
		
		systemAuditor.auditReferralCreated(getTravellerDTO(), getNewReferral() , getFlightSegment() , noOfHits ) ;   
		
		AuditEvent ae = auditSender.getAuditEvents().get(0);
		Assert.assertNotNull(ae);
		
		Assert.assertEquals(auditSender.getAuditEvents().size(), 1);
		
		Assert.assertEquals(ae.getParameters().size(), 13);
		Assert.assertThat(ae.getName(), is("REFERRAL.CREATED"));
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
	
	
	private abc.xyz.pts.bcs.common.dto.Traveller getTravellerDTO()
	{
		abc.xyz.pts.bcs.common.dto.Traveller t = new abc.xyz.pts.bcs.common.dto.Traveller() ; 
		t.setId(100L);
		t.setActiveTravStatusDatetime( Calendar.getInstance() ) ; 
		t.setForeName("kasi"); 
		t.setLastName("subram"); 
		t.setPriDocNo("SD"); 
		t.setAddrArea("ADDR"); 
		t.setAddrCity("city"); 
		t.setAddrCntry("Cntry"); 
		t.setAddrName("Name"); 
		t.setPnrId(1L) ; 
		t.setApiDataStatus("S"); 
		t.setAuthDatetime( Calendar.getInstance() ) ; 
		t.setBirthDateString("12-12-2000"); 
		t.setBirthProvinceCode("BDC"); 
		
		return t ; 
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
	
	private Referral getNewReferral() 
	{
		Referral referral = new Referral();
		referral.setId(100L);
		referral.setPriority(1L);
		referral.setCreatedBy("kasi"); 
		referral.setSecActioningAirportCode("AC"); 
		referral.setSeverityLevel(1L); 
		
		return referral ; 
		
	}

	private ReferralHit getReferralHit() 
	{
		ReferralHit hit = new ReferralHit();
		hit.setId(101L);
		return hit; 
	}	
	
	private FlightSegment getFlightSegment()
	{
		FlightSegment fs = new FlightSegment(); 
		fs.setActualArrDatetime( Calendar.getInstance()); 
		fs.setActualDepDatetime( Calendar.getInstance() ); 
		fs.setArrAirportCode("DOH"); 
		fs.setOperCarrierCode("SC"); 
		fs.setOperFlightNo("SC"); 
		fs.setDepAirportCode("DOH"); 
		fs.setArrAirportCode("LHR"); 
		fs.setScheduledArrDatetime(Calendar.getInstance()) ; 
		fs.setScheduledDepDatetime(Calendar.getInstance())  ; 
		fs.setArrDatetime( Calendar.getInstance() ) ; 
		fs.setCreatedDatetime( Calendar.getInstance()) ; 
		fs.setDepAirportCode("DOH"); 
		fs.setDepDatetime( Calendar.getInstance() ) ; 
		fs.setDestAirportCode("LHR"); 
		fs.setEstimatedArrDatetime(Calendar.getInstance() ) ; 
		fs.setEstimatedDepDatetime( Calendar.getInstance() ) ; 
		fs.setFlightId(10L) ;  
		fs.setFlightStatus("S") ; 
	
		return fs ; 
	}
}
