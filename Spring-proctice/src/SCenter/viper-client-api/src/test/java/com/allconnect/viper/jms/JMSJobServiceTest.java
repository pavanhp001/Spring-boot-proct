package com.A.V.jms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.service.V.JobService;
import com.A.xml.v4.JobType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSJobServiceTest {

	/**
	 * Test for Get Job using Order, Context and LineItem
	 * Below hardcoded information works with VPRDEV001 envt
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetOrderJMS() throws Exception {

		String agentId = "mnagineni";
		String orderId = "4700";
		String lineItemId = "4229";
		String context = "20470058";
		
		JobType job = JobService.INSTANCE.getJobByOrderContextLineItem(agentId, orderId, lineItemId, context);
		assertNotNull(job);
	}
	
}
