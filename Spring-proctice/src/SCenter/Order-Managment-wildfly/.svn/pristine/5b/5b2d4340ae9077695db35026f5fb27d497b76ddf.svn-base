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
import com.AL.task.impl.TaskGetOrder;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class GetOrderTestF {

	// @Autowired
	// private EntityManagerFactory entityManagerFactory;

	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\ome-getOrder-1.xml");
		inputXml1 = inputXml1.replaceAll("ZZZZZZZZ", "22");

		assertNotNull(inputXml1);

		try {
			OrderManagementRequestResponseDocument orderDocument1;
			orderDocument1 = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			OrderManagementRequestResponseDocument doc = doTask(orderDocument1);
			System.out.println(doc.xmlText());
			assertNotNull(doc);
			assertNotNull(doc.getOrderManagementRequestResponse());
			assertNotNull(doc.getOrderManagementRequestResponse().getRequest());
			assertNotNull(doc.getOrderManagementRequestResponse().getResponse());
			List<OrderType> ordList = doc.getOrderManagementRequestResponse().getResponse().getOrderInfoList();
			assertNotNull(ordList);
			for(OrderType ordType : ordList){
				assertNotNull(ordType.getLineItems());
				assertNotNull(ordType.getCustomerInformation().getCustomer());
			}
			System.out.println(ordList.size());
			assertTrue(ordList.size() == 1);
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

		TaskGetOrder task = (TaskGetOrder) SpringUtilTest
				.getBean(TaskGetOrder.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		return doc;
	}

}
