/**
 * This JUNIT is to test orderSearch service
 */
package com.AL.ome.functional;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.BaseALTest;
import com.AL.task.impl.TaskSearchOrder;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author mnagineni
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class SearchOrderTestF {

	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\search-order.xml");

		assertNotNull(inputXml1);

		try {
			OrderManagementRequestResponseDocument orderDocument1;
			orderDocument1 = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			OrderManagementRequestResponseDocument doc = doTask(orderDocument1);
			assertNotNull(doc);
			assertNotNull(doc.getOrderManagementRequestResponse());
			assertNotNull(doc.getOrderManagementRequestResponse().getResponse());
			assertNotNull(doc.getOrderManagementRequestResponse().getResponse().getOrderSearchResult());
			assertTrue(doc.getOrderManagementRequestResponse().getResponse().getOrderSearchResult().getTotalRows() > 0);
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

		TaskSearchOrder task = (TaskSearchOrder) SpringUtilTest
				.getBean(TaskSearchOrder.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		return doc;
	}

}
