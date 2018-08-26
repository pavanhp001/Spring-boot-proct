/**
 *
 */
package com.AL.ome.functional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.BaseALTest;
import com.AL.task.impl.TaskJob;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.JobCollectionType;
import com.AL.xml.v4.JobType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class TaskJobTestF {

	// @Autowired
	// private EntityManagerFactory entityManagerFactory;

	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		String inputXml = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\findByContextStatus_request.xml");
		assertNotNull(inputXml);

		try {
			OrderManagementRequestResponseDocument orderDocument = OrderManagementRequestResponseDocument.Factory.parse(inputXml);
			OrderManagementRequestResponseDocument doc = doTask(orderDocument);
			
			assertNotNull(doc);
			assertNotNull(doc.getOrderManagementRequestResponse());
			assertNotNull(doc.getOrderManagementRequestResponse().getResponse());
			List<JobType> jobList = doc.getOrderManagementRequestResponse().getResponse().getJobs().getJobList();
			assertNotNull(jobList);
			System.out.println(jobList.size());
			assertTrue(jobList.size() == 1);
			System.out.println(doc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param orderDocument
	 *            Order Document
	 */
	public OrderManagementRequestResponseDocument doTask(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);

		assertNotNull(orderDocument);
		assertNotNull(orderDocument.getOrderManagementRequestResponse());
		assertNotNull(orderDocument.getOrderManagementRequestResponse()
				.getRequest());

		TaskJob task = (TaskJob) SpringUtilTest
				.getBean(TaskJob.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		return doc;
	}

}
