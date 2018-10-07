package abc.xyz.pts.bcs.common.referral;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.impl.FlightSegmentsDAO;
import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.enums.QualificationStatusType;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralLogDAO;
import abc.xyz.pts.bcs.common.referral.workflows.notification.ReferralNotificationService;


public class ReferralServiceImplTest {

	private Mockery context;
	private ReferralDAO referralDao;
	private ReferralLogDAO referralLogDAO;
	private TravellerDAO travellerDAO;
	private ReferralNotificationService referralNotificationService;
	private ReferralServiceImpl referralServiceImpl;
	private FlightSegmentsDAO flightSegmentsDAO;
	@Before
	public void setup(){
		
		context = new Mockery(){
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		
		referralDao = context.mock(ReferralDAO.class);
		referralLogDAO = context.mock(ReferralLogDAO.class);
		referralNotificationService =  context.mock(ReferralNotificationService.class);
		
		travellerDAO = context.mock(TravellerDAO.class);
		flightSegmentsDAO = context.mock(FlightSegmentsDAO.class);
		
		referralServiceImpl  = new ReferralServiceImpl();
		referralServiceImpl.setFlightSegmentsDAO(flightSegmentsDAO);
		referralServiceImpl.setTravellerDAO(travellerDAO);
		referralServiceImpl.setReferralDAO(referralDao);
		referralServiceImpl.setReferralLogDAO(referralLogDAO);
		referralServiceImpl.setReferralNotificationService(referralNotificationService);
	}
	
	private Referral testCommonAddReferral(final Referral referral) {

		final Traveller traveller = new Traveller();
		final Long longVal = Long.valueOf(1);		
		
		context.checking(new Expectations(){
			{
				oneOf(referralDao).insert(referral);
				will(returnValue(longVal));

				oneOf(referralLogDAO).insert(with(any(ReferralLog.class)));
				oneOf(travellerDAO).updateAtRiskFlag(referral.getTraId());
				oneOf(travellerDAO).findTravellersById(referral.getTraId());
				will(returnValue(traveller));
				oneOf(flightSegmentsDAO).updateAtRiskFlag(traveller.getFlightSegId());
				if (referral.isOpen()){
					oneOf(referralNotificationService).sendHitAddedNotification(referral.getTraId());
				} else if(referral.isNew()) {
					oneOf(referralNotificationService).sendNotification(referral.getTraId());
				}

			}
		});
		
		Referral insertedReferral = referralServiceImpl.addReferralAndLog(referral);
		assertThat(insertedReferral, is(referral));	
		return insertedReferral;
	}
	
	@Test
	public void testHitAddedToNewReferral() throws Exception {
		final Referral referral = new Referral();
		referral.setId(1L);
		referral.setTraId(100L);
		referral.setStatus(ReferralStatusType.NEW.name());
		referral.setPriority(100l);
		testCommonAddReferral(referral);
	}
	
	@Test
	public void testAddHitToOpenReferral() throws Exception {
		final Referral referral = new Referral();
		referral.setId(1L);
		referral.setTraId(100L);
		referral.setStatus(ReferralStatusType.OPEN.name());
		referral.setPriority(100l);		
		testCommonAddReferral(referral);
	}
	
	@Test
	public void testAddHitToReferral_ResultsInChangeOfPriority() throws Exception {
				
		final Referral referral = new Referral();
		referral.setId(1L);
		referral.setTraId(100L);
		referral.setStatus(ReferralStatusType.UNQ.name());
		referral.setPriority(100l);		
		testCommonAddReferral(referral);
		
		Assert.assertTrue(referral.getPriority().longValue() == 100l);
	
		long newPriority = 1000l;
		
		referral.setPriority(newPriority);
		testCommonAddReferral(referral);
		
		Assert.assertTrue(referral.getPriority() == newPriority);
	}

	
	@Test
	public void testAddHitToUnqualifiedReferral_NoNotificationSent() throws Exception {
		final Referral referral = new Referral();
		final Traveller traveller = new Traveller();
		final Long longVal = Long.valueOf(1);
				
		referral.setId(1L);
		referral.setTraId(100L);
		referral.setStatus(ReferralStatusType.UNQ.name());
		referral.setPriority(100l);
		
		context.checking(new Expectations(){
			{
				oneOf(referralDao).insert(referral);
				will(returnValue(longVal));

				oneOf(referralLogDAO).insert(with(any(ReferralLog.class)));
				
				
				oneOf(travellerDAO).updateAtRiskFlag(referral.getTraId());
				
				oneOf(travellerDAO).findTravellersById(referral.getTraId());
				will(returnValue(traveller));
				oneOf(flightSegmentsDAO).updateAtRiskFlag(traveller.getFlightSegId());

			}
		});
		
		referralServiceImpl.addReferralAndLog(referral);
	}
	
	@Test
	public void testAddHitToClosedReferral_AndRuledInHit() throws Exception {
		final Referral referral = new Referral();
		referral.setId(1L);
		referral.setTraId(100L);
		referral.setSeverityLevel(1l);
		referral.setStatus(ReferralStatusType.CLOSED.name());
		referral.setPriority(100l);
		
		ReferralHit referralHit = new ReferralHit();
		referralHit.setQualificationStatus(QualificationStatusType.IN.name());
		List<ReferralHit> referralHits = new ArrayList<ReferralHit>();
		referralHits.add(referralHit);
		
		referral.addReferralHits(referralHits);
		testCommonAddReferral(referral);
		assertThat(referral.getStatus(), is(ReferralStatusType.NEW.name()));
		
		Assert.assertTrue(referral.getReferralState().isStatusChanged() == true);
	}
	
	@Test
	public void testAddHitToClosedReferral_AndRuledOutHit() throws Exception {
		
		final Referral referral = new Referral();
		referral.setId(1L);
		referral.setTraId(100L);
		referral.setSeverityLevel(1l);
		referral.setStatus(ReferralStatusType.CLOSED.name());
		referral.setPriority(100l);
		
		ReferralHit referralHit = new ReferralHit();
		referralHit.setQualificationStatus(QualificationStatusType.OUT.name());
		List<ReferralHit> referralHits = new ArrayList<ReferralHit>();
		referralHits.add(referralHit);
		
		referral.addReferralHits(referralHits);
		testCommonAddReferral(referral);
		assertThat(referral.getStatus(), is(ReferralStatusType.UNQ.name()));
		
		Assert.assertTrue(referral.getReferralState().isStatusChanged() == true);
		
	}


	@After
	public void verify(){
		context.assertIsSatisfied();
	}
}
