package abc.xyz.pts.bcs.common.audit.bean;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class AuditContextHolderTest {

	@Test
	public void testAddParameter(){
		
		AuditContextHolder.addParameter(Parameter.ACTION_CODE, "actionCode");
		List<AuditParameter> list = AuditContextHolder.getParametersMap().get(Event.ANNOTATED_EVENT);
		
		AuditParameter auditParameter = list.get(0);
		assertThat(auditParameter.getName(), is(Parameter.ACTION_CODE));
		assertThat(auditParameter.getValue(), is("actionCode"));
		
	}
	
	@Test
	public void testAddParameters(){
		
		AuditContextHolder.addParameterToDefaultEvent(Parameter.ACTION_CODE, "actionCode1", "actionCode2");
		List<AuditParameter> list = AuditContextHolder.getParametersMap().get(Event.ANNOTATED_EVENT);
		
		assertThat(list.size(), is(1));
		AuditParameter auditParameter = list.get(0);
		assertThat(auditParameter.getName(), is(Parameter.ACTION_CODE));
		assertThat(auditParameter.getValue(), nullValue());
		assertThat(auditParameter.getMultipleValues().size(), is(2));
		
	}
	
	@Test
	public void testClear(){
		AuditContextHolder.addParameterToDefaultEvent(Parameter.ACTION_CODE, "actionCode1", "actionCode2");
		
		assertThat(AuditContextHolder.getParametersMap().size(), is(1));
		
		AuditContextHolder.clear();
		
		assertThat(AuditContextHolder.getParametersMap().size(), is(0));
	}
	
	@Test
	public void testMessageRetryThresholdReached_WhenThresholdReached(){
		int thresholdCount = 5; 
		AuditContextHolder.setJmsXDeliverCount(""+(thresholdCount));
		System.setProperty("audit.message.retry.threshold", ""+thresholdCount);
		
		assertThat(AuditContextHolder.isMessageRetryThresholdReached(), is(true));
		
	}
	
	@Test
	public void testMessageRetryThresholdReached_WhenThresholdNotReached(){
		int thresholdCount = 5; 
		AuditContextHolder.setJmsXDeliverCount(""+(thresholdCount-2));
		System.setProperty("audit.message.retry.threshold", ""+thresholdCount);
		
		assertThat(AuditContextHolder.isMessageRetryThresholdReached(), is(false));
		
	}
	
	@Test
	public void testMessageRetryPassedThreshold(){
		int thresholdCount = 5; 
		AuditContextHolder.setJmsXDeliverCount(""+(thresholdCount+2));
		System.setProperty("audit.message.retry.threshold", ""+thresholdCount);
		
		assertThat(AuditContextHolder.isMessageRetryPassedThreshold(), is(true));
	}
	
	@Test
	public void testMessageRetryNotPassedThreshold(){
		int thresholdCount = 5; 
		AuditContextHolder.setJmsXDeliverCount(""+(2));
		System.setProperty("audit.message.retry.threshold", ""+thresholdCount);
		
		assertThat(AuditContextHolder.isMessageRetryPassedThreshold(), is(false));
	}
	
	@After
	public void clearDown(){
		AuditContextHolder.clear();
	}
}
