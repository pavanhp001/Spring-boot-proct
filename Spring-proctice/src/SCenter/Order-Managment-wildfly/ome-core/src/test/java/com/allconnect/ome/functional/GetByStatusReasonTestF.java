package com.AL.ome.functional;

 

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import com.AL.BaseALTest;
import com.AL.task.impl.TaskJob;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;


@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public final class GetByStatusReasonTestF {

	@Before
	public void setUp() {

		System.out.println("Data");
	}

	@Test
	public void testJobStatusReason() {
		String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\get-by-status-reason.xml");
	  
		try {
			OrderManagementRequestResponseDocument order;
			order = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			doTask(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doTask(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);
		assertNotNull(orderDocument.getOrderManagementRequestResponse());
		assertNotNull(orderDocument.getOrderManagementRequestResponse()
				.getRequest());

		TaskJob task = (TaskJob) SpringUtilTest
				.getBean(TaskJob.class);

		OrderManagementRequestResponseDocument result = task
				.execute(orderDocument);
		Response response = result.getOrderManagementRequestResponse()
				.getResponse();
		
		System.out.println(response.toString());
		assertNotNull(response);
 
 
	}

}
