package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskGetOrderByConfirmationNumber;
import com.AL.util.OmeSpringUtil;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class GetOrderConfirmTestF {
	private static final OmeSpringUtil omeSpringUtil =  OmeSpringUtil.INSTANCE;

	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\main\\resources\\xml\\ome-getOrderConfirm.xml");
		inputXml1 = inputXml1.replaceAll("ZZZZZZZZ", "AC-3");

		assertNotNull(inputXml1);

		try {
			OrderManagementRequestResponseDocument orderDocument1;
			orderDocument1 = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			doTask(orderDocument1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param orderDocument
	 *            Order Document
	 */
	public void doTask(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);

		assertNotNull(orderDocument);
		assertNotNull(orderDocument.getOrderManagementRequestResponse());
		assertNotNull(orderDocument.getOrderManagementRequestResponse()
				.getRequest());

		TaskGetOrderByConfirmationNumber task = (TaskGetOrderByConfirmationNumber) SpringUtilTest
				.getBean(TaskGetOrderByConfirmationNumber.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		System.out.println(doc.toString());

	}

}
