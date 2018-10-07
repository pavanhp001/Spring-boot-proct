package abc.xyz.pts.bcs.common.audit.aspect;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.audit.business.AuditMessageSender;
import abc.xyz.pts.bcs.common.audit.business.impl.JmsAuditMessageSenderImpl;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;

public class AbstractAuditAspectTest extends TestCase {
    private Mockery context;
    private AbstractAuditAspect auditAspect;
    private AuditMessageSender auditMessageSender;
    private HttpServletRequest request;
    private AuditTestCommand command;
    private AuditEvent auditEvent;

    @Before
    public void setUp(){
        context = new Mockery();

        auditAspect = new AbstractAuditAspect() { };
        auditMessageSender = context.mock(AuditMessageSender.class);
        auditAspect.setMessageSender(auditMessageSender);

        request = context.mock(HttpServletRequest.class);
        auditEvent = new AuditEvent();
        auditEvent.setName("ACTIVE.ALERTS");

        command = new AuditTestCommand();
        command.setNestedProperty1(new NestedBeanTestImpl());
        command.setNestedProperty2(new NestedBeanTestImpl());
        command.setProperty1("value 1");
        command.setProperty2("value 3");
        command.setProperty3("value 3");
    }

    public void testAudit() throws Exception {
    	context.checking(new Expectations() {
    		{
    			one(auditMessageSender).send(auditEvent);
    		}
    	});
    	auditAspect.audit(auditEvent);
    	context.assertIsSatisfied();
    }

    @Test
    public void testCreateAuditEvent() throws Exception{
        final Hashtable<String, String[]> parameterMap = new Hashtable<String, String[]>(3);
        parameterMap.put("property1",new String[]{"testValue1"});
        parameterMap.put("property2",new String[]{"testValue2"});
        parameterMap.put("property3",new String[]{"testValue3"});

        final Principal p = new Principal() {
			public String getName() {
				return "testName";
			}
        };
        context.checking(new Expectations() {
        	{
	            exactly(2).of(request).getUserPrincipal(); will(returnValue(p));
	            one(request).getRemoteAddr(); will(returnValue("127.0.0.1"));
	            one(request).getParameterMap(); will(returnValue(parameterMap));
        	}
        });
        AuditEvent result = auditAspect.createAuditEvent(request, command.getClass());
        assertNotNull(result);
        assertEquals("testName", result.getUserId());
        assertNotNull(result.getCreatedDate());
        assertEquals(1, result.getParameters().size());
        context.assertIsSatisfied();
    }

    @Test
    public void testCreateAuditEventNullPrincipal() throws Exception{
        final Hashtable<String, String[]> parameterMap = new Hashtable<String, String[]>(3);
        parameterMap.put("property1",new String[]{"testValue1"});
        parameterMap.put("property2",new String[]{"testValue2"});
        parameterMap.put("property3",new String[]{"testValue3"});
        context.checking(new Expectations() {
        	{
	            one(request).getUserPrincipal(); will(returnValue(null));
	            one(request).getRemoteAddr(); will(returnValue("127.0.0.1"));
	            one(request).getParameterMap(); will(returnValue(parameterMap));
        	}
        });
        AuditEvent result = auditAspect.createAuditEvent(request, command.getClass());
        assertNotNull(result);
        context.assertIsSatisfied();
    }

    public void testBuildParameters() {
        final HashMap<String, String[]> parameterMap = new HashMap<String, String[]>(3);
        parameterMap.put("property1",new String[]{"testValue1"});
        parameterMap.put("property2",new String[]{"testValue2"});
        parameterMap.put("property3",new String[]{"testValue3"});
        parameterMap.put("property5",new String[]{"testValue5"});
        context.checking(new Expectations() {
        	{
	            one(request).getParameterMap(); will(returnValue(parameterMap));
        	}
        });

    	List<AuditEventParameter> result = auditAspect.buildParameters(command.getClass(), request);
    	assertNotNull(result);
    	assertEquals(1, result.size());
        assertEquals("ACTION.CODE", result.get(0).getName());
        assertEquals("testValue1", result.get(0).getValue());
    	context.assertIsSatisfied();
    }

    public void testResolvePropertyName() {
    	assertEquals("test", auditAspect.resolvePropertyName("test"));
    }

    public void testCreateAdditionalParameters() {
    	List<AuditEventParameter> params = new ArrayList<AuditEventParameter>();
    	assertEquals(0, params.size());
    	auditAspect.createAdditionalParameters(command, auditEvent, null);
    	// Check that ignored properties are not in the event
    	for (AuditEventParameter auditEventParameter : auditEvent.getParameters()) {
			if ("ALARM_CODE".equals(auditEventParameter.getName())) {
				fail("Unexpected parameter for ALARM_CODE");
			}
		}
    }

    public void testApplicatioName() {
        auditAspect = new AbstractAuditAspect() { };
    	assertNull(auditAspect.getApplicationName());
    	auditAspect.setApplicationName("test");
    	assertEquals("test", auditAspect.getApplicationName());
    	auditAspect.setApplicationName(null);
    	assertNull(auditAspect.getApplicationName());
    }

    public void testMessageSender() {
        auditAspect = new AbstractAuditAspect() { };
    	assertNull(auditAspect.getMessageSender());
    	JmsAuditMessageSenderImpl ms = new JmsAuditMessageSenderImpl();
    	auditAspect.setMessageSender(ms);
    	assertEquals(ms, auditAspect.getMessageSender());
    	auditAspect.setMessageSender(null);
    	assertNull(auditAspect.getMessageSender());
    }
}
