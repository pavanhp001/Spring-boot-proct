package abc.xyz.pts.bcs.common.audit.business;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;

public class AuditorTest {
    Mockery mockContext;
    Auditor auditor;
    AuditMessageSender mockSender;
    
    @Before
    public void setup(){
        mockContext = new Mockery();
        mockSender = mockContext.mock(AuditMessageSender.class);
        auditor = new Auditor();
        auditor.setMessageSender(mockSender);
    }
    
    @After
    public void checkMockContext(){
        mockContext.assertIsSatisfied();
    }
    
    @Test
    public void testAudit() throws Exception{
        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.ACTION_CODE, "test");
        mockContext.checking(new Expectations() {{
            one(mockSender).send(with(any(AuditEvent.class)));
        }});
        auditor.audit(Event.ADD_ACTION_CODE, params, "testId", "testName", 1234L, false);
    }
    
    @Test
    public void testAuditError() throws Exception{
        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.ACTION_CODE, "test");
        mockContext.checking(new Expectations() {{
            one(mockSender).send(with(any(AuditEvent.class)));
        }});
        auditor.audit(Event.ADD_ACTION_CODE, params, "testId", "testName", 1234L, true);
    }
}
