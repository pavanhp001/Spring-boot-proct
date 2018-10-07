package abc.xyz.pts.bcs.common.audit.business.impl;

import java.util.Date;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.audit.business.AuditMessageSender;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;

public class Log4jAuditMessageSenderImplTest {
    Mockery mockContext;
    AuditMessageSender auditMessageSender;
    AuditEvent auditEvent;
    @Before
    public void setup(){
        mockContext = new Mockery();
        auditMessageSender = new Log4jAuditMessageSenderImpl();
               
        auditEvent = new AuditEvent();
        auditEvent.setCreatedDate(new Date());
        auditEvent.setName("testType");
        auditEvent.setUserId("testUID");
        AuditEventParameter auditParam = new AuditEventParameter();
        auditParam.setName("testName");
        auditParam.setValue("testValue");
        auditEvent.getParameters().add(auditParam);
    }

    /**
     * This method must be in all mock based tests in order to check that the context has been fully satisfied.
     */
    @After
    public void checkMockContext(){
        mockContext.assertIsSatisfied();
    }
    
    @Test
    public void testSend() throws Exception{
        auditMessageSender.send(auditEvent);
        
    }
}
