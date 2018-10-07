package abc.xyz.pts.bcs.common.audit.business;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.MessageCreator;

import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.audit.messages.ObjectFactory;

public class AuditMessageCreatorTest {
    Mockery mockContext;
    AuditEvent auditEvent;
    javax.jms.Session mockJmsSession;
    MessageCreator mc;
    
    @Before
    public void setup(){
        mockContext = new Mockery();
        auditEvent = new ObjectFactory().createAuditEvent();
        auditEvent.setCreatedDate(new Date());
        auditEvent.setName("testType");
        auditEvent.setUserId("testUID");
        AuditEventParameter auditParam = new ObjectFactory().createAuditEventParameter();
        auditParam.setName("testName");
        auditParam.setValue("testValue");
        auditEvent.getParameters().add(auditParam);
        mc = new AuditMessageCreator(auditEvent);
        mockJmsSession = mockContext.mock(javax.jms.Session.class);
    }

    /**
     * This method must be in all mock based tests in order to check that the context has been fully satisfied.
     */
    @After
    public void checkMockContext(){
        mockContext.assertIsSatisfied();
    }
    
    @Test
    public void testCreateMessage() throws Exception{
        mockContext.checking(new Expectations() {{
            one(mockJmsSession).createTextMessage(with(any(String.class)));
        }});
        mc.createMessage(mockJmsSession);
        
    }
}
