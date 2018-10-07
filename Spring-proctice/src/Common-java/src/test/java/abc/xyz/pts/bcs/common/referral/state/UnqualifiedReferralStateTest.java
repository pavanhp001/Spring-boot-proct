package abc.xyz.pts.bcs.common.referral.state;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.exception.ApplicationException;

public class UnqualifiedReferralStateTest {

    private ReferralState referralState;
    
       
    @Test
    public void testSaveLeadsToCloseState() throws ApplicationException {
        
        //Given
        Referral referral = createUnqualifiedReferralWithAllRuledOutHits();        
        referralState = new UnqualifiedReferralState(referral);
        
        //When
        referralState.handleStateOnSave();
        
        //Then        
       assertThat("Should fail if status not closed ",referral.getStatus(), equalTo(ReferralStatusType.CLOSED.name()));
       assertThat("Should fail if its not instance of ClosedReferralState class ",referral.getReferralState(), instanceOf(ClosedReferralState.class));
        
    }
    
    @Test
    public void testSaveLeadsToNewState() throws ApplicationException {
        
        //Given
        Referral referral = createUnqualifiedReferralWithAtLeastOneRuledInHit();        
        referralState = new UnqualifiedReferralState(referral);
        
        //When
        referralState.handleStateOnSave();
        
        //Then        
       assertThat("Should fail if status not NEW ",referral.getStatus(), equalTo(ReferralStatusType.NEW.name()));
       assertThat("Should fail if its not instance of NewReferralState class ",referral.getReferralState(), instanceOf(NewReferralState.class));
        
    }
    
    
    @Test
    public void testSaveLeadsToNoChangeInCurrentState() throws ApplicationException {
        
        //Given
        Referral referral = createUnqualifiedReferralWithNoHitRuled();        
        referralState = new UnqualifiedReferralState(referral);
        
        //When
        referralState.handleStateOnSave();
        
        //Then        
       assertThat("Should fail if status is not Unqualified ",referral.getStatus(), equalTo(ReferralStatusType.UNQ.name()));
       assertThat("Should fail if its not instance of UnqualifiedReferralState class ",referral.getReferralState(), instanceOf(UnqualifiedReferralState.class));
        
    }
    
private Referral createUnqualifiedReferralWithNoHitRuled(){
        
        Referral referral = new Referral();
        
        referral.setStatus(ReferralStatusType.UNQ.name());
       
        ReferralHit referralHit1 = createReferralHit("UNKNOWN");
        ReferralHit referralHit2 = createReferralHit("UNKNOWN");
        
        referral.getReferralHits().add(referralHit1);
        referral.getReferralHits().add(referralHit2);
        return referral;
    }
    
  private Referral createUnqualifiedReferralWithAtLeastOneRuledInHit(){
        
        Referral referral = new Referral();
        
        referral.setStatus(ReferralStatusType.UNQ.name());
       
        ReferralHit referralHit1 = createReferralHit("IN");
        ReferralHit referralHit2 = createReferralHit("OUT");
        ReferralHit referralHit3 = createReferralHit("UNKNOWN");
        
        referral.getReferralHits().add(referralHit1);
        referral.getReferralHits().add(referralHit2);
        referral.getReferralHits().add(referralHit3);
        return referral;
    }
  
    
    private Referral createUnqualifiedReferralWithAllRuledOutHits(){
        
        Referral referral = new Referral();
        
        referral.setStatus(ReferralStatusType.UNQ.name());
       
        ReferralHit referralHit1 = createReferralHit("OUT");
        ReferralHit referralHit2 = createReferralHit("OUT");
        referral.getReferralHits().add(referralHit1);
        referral.getReferralHits().add(referralHit2);
        return referral;
    }
    
    
    private ReferralHit createReferralHit(final String qualificationStatus){
       
        ReferralHit referralHit = new ReferralHit();  
        referralHit.setQualificationStatus(qualificationStatus);
        
        return referralHit;
    }

}
