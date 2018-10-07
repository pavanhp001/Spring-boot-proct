package abc.xyz.pts.bcs.common.referral.workflows.notification;



import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;
import abc.xyz.pts.bcs.common.messaging.JMSSender;
import abc.xyz.pts.bcs.common.referral.workflows.notification.ReferralNotificationServiceImpl;

public class ReferralNotificationServiceImplTest {
	private JMSSender workflowsSender;
	private TravellerDAO travellerDAO;
	private Mockery context;
	private ReferralNotificationServiceImpl referralNotificationService;
	
	@Before
	public void setup() {
		context =  new Mockery(){
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		
		workflowsSender = context.mock(JMSSender.class);
		
		travellerDAO = context.mock(TravellerDAO.class);
		referralNotificationService = new ReferralNotificationServiceImpl();
		referralNotificationService.setTravellerDAO(travellerDAO);
		referralNotificationService.setReferralNotifcationRequestSender(workflowsSender);
		referralNotificationService.setReferralAddHitNotifcationRequestSender(workflowsSender);
		
	}
	
	@Test
	public void testSendHitAddedNotification() throws Exception {
		final long traId = 9L;
		final Traveller traveller = new Traveller();
		traveller.setFlightSegId(100L);
		context.checking(new Expectations(){
			{
				oneOf(travellerDAO).findTravellersById(traId);
				will(returnValue(traveller));
				
				oneOf(workflowsSender).sendMessage(with(any(String.class)));
			}
		});
		
		referralNotificationService.sendHitAddedNotification(traId);
	}
	
	@Test
	public void testSendNotification() throws Exception {
		final long traId = 9L;
		final Traveller traveller = new Traveller();
		traveller.setFlightSegId(100L);
		context.checking(new Expectations(){
			{
				oneOf(travellerDAO).findTravellersById(traId);
				will(returnValue(traveller));
				
				oneOf(workflowsSender).sendMessage(with(any(String.class)));
			}
		});
		
		referralNotificationService.sendNotification(traId);
	}
	
	@After
	public void verify() {
		context.assertIsSatisfied();
	}
}

