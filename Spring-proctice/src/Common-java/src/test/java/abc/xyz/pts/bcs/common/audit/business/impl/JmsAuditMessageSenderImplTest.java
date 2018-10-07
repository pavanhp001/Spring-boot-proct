package abc.xyz.pts.bcs.common.audit.business.impl;

import java.util.Date;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;

import abc.xyz.pts.bcs.common.audit.business.AuditException;
import abc.xyz.pts.bcs.common.audit.business.AuditMessageCreator;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.jmx.impl.MessageSenderMXBean;

public class JmsAuditMessageSenderImplTest {
    Mockery mockContext;
    JmsAuditMessageSenderImpl auditMessageSender;
    MessageSenderMXBean messageSenderMXBean;
    javax.jms.Destination mockDestination;
    MessageCreator auditMessageCreator;
    JmsOperations mockJmsTemplate;
    AuditEvent auditEvent;
    @Before
    public void setup(){
        mockContext = new Mockery();
        auditMessageSender = new JmsAuditMessageSenderImpl();
        messageSenderMXBean = new MessageSenderMXBean(); 
        mockDestination = mockContext.mock(javax.jms.Destination.class);
        auditMessageSender.setDestination(mockDestination);
        mockJmsTemplate = mockContext.mock(JmsOperations.class);
        auditMessageSender.setJmsOperations(mockJmsTemplate);
        
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
        mockContext.checking(new Expectations() {{
            one(mockJmsTemplate).send(with(any(javax.jms.Destination.class)), with(any(AuditMessageCreator.class)));
        }});
        auditMessageSender.setMessageSenderMXBean(messageSenderMXBean);
        auditMessageSender.send(auditEvent);
        
    }
    
    @Test
    public void testSendException() throws Exception{
        mockContext.checking(new Expectations() {{
            one(mockJmsTemplate).send(with(any(javax.jms.Destination.class)), with(any(AuditMessageCreator.class)));
            will(throwException(new Exception("test exception")));
        }});
        try{
        	auditMessageSender.send(auditEvent);
        	Assert.fail();
        }
        catch(AuditException e){
        	
        }
        
    }
}
