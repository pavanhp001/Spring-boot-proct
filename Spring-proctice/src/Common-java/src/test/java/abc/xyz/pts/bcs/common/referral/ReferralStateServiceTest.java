package abc.xyz.pts.bcs.common.referral;

import java.util.Locale;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.enums.ReferralEventType;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;


public class ReferralStateServiceTest extends TestCase{

	private Mockery context;
	private ReferralService referralService;
	private ReferralStateService referralStateService;
	private Locale locale;
	
	@Before
	public void setUp() {
		
		context = new Mockery(){
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		referralService = context.mock(ReferralService.class);
		referralStateService = new ReferralStateService();
		referralStateService.setReferralService(referralService);
		locale = Locale.getDefault();
	}
	
	@Test
	public void testAddHitToOpenReferral() throws Exception {
		
		final Referral referral = new Referral();
		referral.setStatus(ReferralStatusType.OPEN.name());
		
		context.checking(new Expectations(){
			{
				oneOf(referralService).closeReferral(referral, "", ReferralEventType.REFCLOSEDM.name(), locale, false);
			}
		});
		
        referralStateService.close(referral, "", ReferralEventType.REFCLOSEDM.name(), locale, false);

		assertTrue(referral.getStatus().equals(ReferralStatusType.CLOSED.name()));
	
	}
	
	@After
	public void verify(){
		context.assertIsSatisfied();
	}
}
