/**
 * 
 */
package abc.xyz.pts.bcs.common.jmx.impl;

import junit.framework.TestCase;

import org.junit.Test;
/**
 * @author ryattapu
 *
 */
public final class AuditMessageMXBeanTest extends TestCase{
	
	private AuditMessageListenerMXBean messageMXBean;
	
	@Override
	protected void setUp() throws Exception {
		messageMXBean = new AuditMessageListenerMXBean();
	}
	@Test
	public void testCalculateAvgMessagesProcessingTime() {
		long totalProcessingTime = 100L;
		long totalMessagesCount = 10L;
		long avgMessagesProcessingTime = messageMXBean.calculateAvgMessagesProcessingTime(totalProcessingTime, totalMessagesCount);
		assertEquals(10L, avgMessagesProcessingTime);
	}	
	
	@Test
	public void testCalculateMaxMessagesProcessingTime() {
		long maxProcessingTime = 10L;
		long currentProcessingTime = 10L;
		long maxMessagesProcessingTime = messageMXBean.calculateMaxProcessingTime(maxProcessingTime, currentProcessingTime);
		assertEquals(10L, maxMessagesProcessingTime);
		
		currentProcessingTime = 11L;
		maxMessagesProcessingTime = messageMXBean.calculateMaxProcessingTime(maxProcessingTime, currentProcessingTime);
		assertFalse((maxMessagesProcessingTime <= maxProcessingTime));
		assertEquals(11L, maxMessagesProcessingTime);
	}
	
	@Test
	public void testCalculateMinMessagesProcessingTime() {
		long minProcessingTime = 10L;
		long currentProcessingTime = 10L;
		long minMessagesProcessingTime = messageMXBean.calculateMinProcessingTime(minProcessingTime, currentProcessingTime);
		assertEquals(10L, minMessagesProcessingTime);
		
		currentProcessingTime = 9L;
		minMessagesProcessingTime = messageMXBean.calculateMinProcessingTime(minProcessingTime, currentProcessingTime);
		assertFalse((minMessagesProcessingTime >= minProcessingTime));
		assertEquals(9L, minMessagesProcessingTime);
	}
	
}
