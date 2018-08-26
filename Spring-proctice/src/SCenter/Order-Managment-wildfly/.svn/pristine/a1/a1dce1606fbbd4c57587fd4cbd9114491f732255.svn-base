package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;
import javax.persistence.EntityManagerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskGetOrder;
import com.AL.task.impl.TaskGetOrderByCustomer;
import com.AL.util.SpringUtilBase;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class GetOrderCustomerTestF {

	@Before
	public void setUp() {

	}

	@Test
	public void testGetOrderByCustomerId() {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\main\\resources\\xml\\ome-getOrderCust.xml");
		inputXml1 = inputXml1.replaceAll("IIIIIIII", "AC12");

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

		TaskGetOrderByCustomer task = (TaskGetOrderByCustomer) SpringUtilTest
				.getBean(TaskGetOrderByCustomer.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		System.out.println(doc.toString());

	}

}
