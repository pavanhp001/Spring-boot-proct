package abc.xyz.pts.bcs.common.audit.aspect;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.jms.TextMessage;

import org.aspectj.lang.ProceedingJoinPoint;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.audit.annotation.Auditable;
import abc.xyz.pts.bcs.common.audit.bean.AuditContextHolder;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.enums.AuditUserTypes;

public class SystemAuditAspectTest {
	
	private Mockery context = null;
    final String JMSXDeliveryCount = "5";

	
	@Before
	public void setup(){
		context = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
	}
	
	@Test
	public void testAudit_WhenSuccess_AnnotatedEventShouldNotBeAudited() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		final TextMessage textMsg = context.mock(TextMessage.class);
		
		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		
		
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");
		
		context.checking(new Expectations(){
			{
				oneOf(mockProceedingJoinPoint).proceed();
				will(returnValue(null));
	            one(textMsg).getStringProperty(with(any(String.class)));will(returnValue(JMSXDeliveryCount));

			}
		
		});
		
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
		}			
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(1));
		AuditEvent auditEvent = auditEvents.get(0);
		assertThat(auditEvent.getUserId(), is(AuditUserTypes.SYSTEM_USER.value()));
		assertThat(auditEvent.getResponseStatus(), is(AbstractAuditAspect.RESPONSE_STATUS_SUCCESS));
		assertThat(auditEvent.getName(), is(Event.INSERT_TRAVELLER.value()));
		
		List<AuditEventParameter> parameters = auditEvent.getParameters();
		assertThat(parameters.size(), is(1));
		AuditEventParameter auditEventParameter = parameters.get(0);
		assertThat(auditEventParameter.getName(), is(Parameter.TRAVELLER_NAME.value()));
		assertThat(auditEventParameter.getValue(), is("traveller"));
	}
	
	@Test
	public void testAudit_WhenSuccess_AndMultipleValuesForSingleParameter() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		final TextMessage textMsg = context.mock(TextMessage.class);
		
		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		
		
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"firstName", "lastName" );
		
		context.checking(new Expectations(){
			{
				oneOf(mockProceedingJoinPoint).proceed();
				will(returnValue(null));
	            one(textMsg).getStringProperty(with(any(String.class)));will(returnValue(JMSXDeliveryCount));

			}
		
		});
		
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
		}			
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(1));
		AuditEvent auditEvent = auditEvents.get(0);
		assertThat(auditEvent.getUserId(), is(AuditUserTypes.SYSTEM_USER.value()));
		assertThat(auditEvent.getResponseStatus(), is(AbstractAuditAspect.RESPONSE_STATUS_SUCCESS));
		assertThat(auditEvent.getName(), is(Event.INSERT_TRAVELLER.value()));
		
		List<AuditEventParameter> parameters = auditEvent.getParameters();
		assertThat(parameters.size(), is(1));
		AuditEventParameter auditEventParameter = parameters.get(0);
		assertThat(auditEventParameter.getName(), is(Parameter.TRAVELLER_NAME.value()));
		assertThat(auditEventParameter.getValue(), is("firstName,lastName"));
	}	
	
	
	@Test
	public void testAudit_OnNonMessageListeners_WhenSuccess() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		
		
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");
		
		context.checking(new Expectations(){
			{
				oneOf(mockProceedingJoinPoint).proceed();
				will(returnValue(null));

			}
		
		});
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, null);
		} catch (Throwable e) {
		}
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(1));
		AuditEvent auditEvent = auditEvents.get(0);
		assertThat(auditEvent.getUserId(), is(AuditUserTypes.SYSTEM_USER.value()));
		assertThat(auditEvent.getResponseStatus(), is(AbstractAuditAspect.RESPONSE_STATUS_SUCCESS));
		assertThat(auditEvent.getName(), is(Event.INSERT_TRAVELLER.value()));
		
		List<AuditEventParameter> parameters = auditEvent.getParameters();
		assertThat(parameters.size(), is(1));
		AuditEventParameter auditEventParameter = parameters.get(0);
		assertThat(auditEventParameter.getName(), is(Parameter.TRAVELLER_NAME.value()));
		assertThat(auditEventParameter.getValue(), is("traveller"));
	}
	
	@Test
	public void testAudit_AnnotatedEventShouldBeAudited_WhenNoExceptionThrown_ButTargetMethodStillDidNotCompleteSuccessfully() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		final TextMessage textMsg = context.mock(TextMessage.class);
		
		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		
		context.checking(new Expectations(){
			{
				oneOf(mockProceedingJoinPoint).proceed();
	            one(textMsg).getStringProperty(with(any(String.class)));will(returnValue(JMSXDeliveryCount));

			}
		
		});
		
		int message_retry_threshold = 5;
		System.setProperty("audit.message.retry.threshold", ""+message_retry_threshold);
		
		
		AuditContextHolder.setJmsXDeliverCount(""+(1));
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
			
		}
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(1));
		AuditEvent auditEvent = auditEvents.get(0);
		assertThat(auditEvent.getUserId(), is(AuditUserTypes.SYSTEM_USER.value()));
		assertThat(auditEvent.getResponseStatus(), is(AbstractAuditAspect.RESPONSE_STATUS_FAILURE));
		assertThat(auditEvent.getName(), is(annotation.value().value()));
		
		List<AuditEventParameter> parameters = auditEvent.getParameters();
		assertThat(parameters.size(), is(0));
	}
	
	
	@Test
	public void testAudit_WhenFailure_AndMessageRetryThresholdReached_AnnotatedEventShouldBeAudited() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		final TextMessage textMsg = context.mock(TextMessage.class);
		
		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		
		
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");
		
		context.checking(new Expectations(){
			{
				oneOf(mockProceedingJoinPoint).proceed();
				will(throwException(new Exception()));
	            one(textMsg).getStringProperty(with(any(String.class)));will(returnValue(JMSXDeliveryCount));
			}
		
		});
		
		int message_retry_threshold = 5;
		System.setProperty("audit.message.retry.threshold", ""+message_retry_threshold);
		
		
//		Set the Delivery Count of the message to one more than the threshold  
		AuditContextHolder.setJmsXDeliverCount(""+(message_retry_threshold));
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
			
		}
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(1));
		AuditEvent auditEvent = auditEvents.get(0);
		assertThat(auditEvent.getUserId(), is(AuditUserTypes.SYSTEM_USER.value()));
		assertThat(auditEvent.getResponseStatus(), is(AbstractAuditAspect.RESPONSE_STATUS_FAILURE));
		assertThat(auditEvent.getName(), is(annotation.value().value()));
		
		List<AuditEventParameter> parameters = auditEvent.getParameters();
		assertThat(parameters.size(), is(1));
		AuditEventParameter auditEventParameter = parameters.get(0);
		assertThat(auditEventParameter.getName(), is(Parameter.FLIGHT_NUMBER.value()));
		assertThat(auditEventParameter.getValue(), is("flight-100"));
	}
	
	
	@Test
	public void testAudit_WhenFailureMultipleTimes_AndMessageRetryThresholdReached_AnnotatedEventShouldBeAudited() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		final TextMessage textMsg = context.mock(TextMessage.class);
		
		
		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		context.checking(new Expectations(){
			{
				one(mockProceedingJoinPoint).proceed();
				will(throwException(new Exception()));
				one(textMsg).getStringProperty(with(any(String.class)));will(returnValue("0"));
			}
			
		});

		int message_retry_threshold = 2;
		System.setProperty("audit.message.retry.threshold", ""+message_retry_threshold);
		
		
		//First time Failure
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");

		//Set the Delivery Count of the message when tried the first time
//		AuditContextHolder.setJmsXDeliverCount(""+(message_retry_threshold-1));
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
		}
		
		context.checking(new Expectations(){
			{
				one(mockProceedingJoinPoint).proceed();
				will(throwException(new Exception()));
				one(textMsg).getStringProperty(with(any(String.class)));will(returnValue("1"));
			}
			
		});
		
		//Second time Failure
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");

		//Set the Delivery Count of the message when tried the second time
//		AuditContextHolder.setJmsXDeliverCount(""+(message_retry_threshold));
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
		}
		
		context.checking(new Expectations(){
			{
				one(mockProceedingJoinPoint).proceed();
				will(throwException(new Exception()));
				one(textMsg).getStringProperty(with(any(String.class)));will(returnValue("2"));
			}
			
		});
		
		//Final Failure
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");

		//Set the Delivery Count of the message to be 1 when tried the third time
//		AuditContextHolder.setJmsXDeliverCount(""+(message_retry_threshold+1));
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
		}
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(1));
		AuditEvent auditEvent = auditEvents.get(0);
		assertThat(auditEvent.getUserId(), is(AuditUserTypes.SYSTEM_USER.value()));
		assertThat(auditEvent.getResponseStatus(), is(AbstractAuditAspect.RESPONSE_STATUS_FAILURE));
		assertThat(auditEvent.getName(), is(annotation.value().value()));
		
		List<AuditEventParameter> parameters = auditEvent.getParameters();

		//check that parameters only added once even after message processed multiple times.
		assertThat(parameters.size(), is(1));
		
		AuditEventParameter auditEventParameter = parameters.get(0);
		assertThat(auditEventParameter.getName(), is(Parameter.FLIGHT_NUMBER.value()));
		assertThat(auditEventParameter.getValue(), is("flight-100"));
		
	}	
	
	@Test
	public void testAudit_WhenFailure_AndMessageRetryThresholdNotReached_NoEventShouldBeAudited() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		final TextMessage textMsg = context.mock(TextMessage.class);
		
		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		
		
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");
		
		context.checking(new Expectations(){
			{
				oneOf(mockProceedingJoinPoint).proceed();
				will(throwException(new Exception()));
	            one(textMsg).getStringProperty(with(any(String.class)));will(returnValue("0"));

			}
		
		});
		
		int message_retry_threshold = 5;
		System.setProperty("audit.message.retry.threshold", ""+message_retry_threshold);
		
//		Set the Delivery Count of the message to less than the threshold  
		AuditContextHolder.setJmsXDeliverCount(""+(message_retry_threshold-1));
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
			
		}
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(0));
	}
	
	@Test
	public void testAudit_WhenFailure_AndMessageRetryThresholdPassed_NoEventShouldBeAudited() throws Throwable{
		
		SystemAuditAspect aspect = new SystemAuditAspect();
		MockAuditSender auditMessageSender = new MockAuditSender();
		aspect.setMessageSender(auditMessageSender);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = context.mock(ProceedingJoinPoint.class);

		final TextMessage textMsg = context.mock(TextMessage.class);
		
		Auditable annotation = new MockAuditedService().getClass().getMethod("mockAuditedMethod").getAnnotation(Auditable.class);
		
		
		AuditContextHolder.addParameter(Event.ANNOTATED_EVENT, Parameter.FLIGHT_NUMBER, "flight-100");
		AuditContextHolder.addParameter(Event.INSERT_TRAVELLER, Parameter.TRAVELLER_NAME,"traveller");
		
		context.checking(new Expectations(){
			{
				oneOf(mockProceedingJoinPoint).proceed();
				will(throwException(new Exception()));
	            one(textMsg).getStringProperty(with(any(String.class)));will(returnValue("7"));

			}
		
		});
		
		int message_retry_threshold = 5;
		System.setProperty("audit.message.retry.threshold", ""+message_retry_threshold);
		
//		Set the Delivery Count of the message to more than the threshold  
		AuditContextHolder.setJmsXDeliverCount(""+(message_retry_threshold+5));
		
		try {
			aspect.auditSystemAction(mockProceedingJoinPoint, annotation, textMsg);
		} catch (Throwable e) {
			
		}
			
		List<AuditEvent> auditEvents = auditMessageSender.getAuditEvents();
		assertThat(auditEvents.size(), is(0));
			
	}
	
	@After
	public void verify(){
		context.assertIsSatisfied();
	}
}


class MockAuditedService {
	@Auditable(value = Event.DATA_LOADER, ignoreEventOnSuccess = true)
	public void mockAuditedMethod() {

	}
}
