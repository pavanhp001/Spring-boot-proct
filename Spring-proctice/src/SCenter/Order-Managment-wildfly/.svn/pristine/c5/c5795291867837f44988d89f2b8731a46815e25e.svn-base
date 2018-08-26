package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskGetOrderByDate;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class GetOrderDateTestF {

	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		long stTime = System.currentTimeMillis();
		// String inputXml1 = BaseALTest.getXMLFromFile(
		// "src\\main\\resources\\xml\\ome-getOrder-1.xml" );
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\test\\resources\\xml\\getOrderByDate.xml");
		// inputXml1 = inputXml1.replaceAll( "ZZZZZZZZ", "1" );

		assertNotNull(inputXml1);

		try {
			OrderManagementRequestResponseDocument orderDocument1;
			orderDocument1 = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			doTask(orderDocument1);
			
			System.out.println("Total request time : " + (System.currentTimeMillis() - stTime) + "ms");

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

		TaskGetOrderByDate task = (TaskGetOrderByDate) SpringUtilTest
				.getBean(TaskGetOrderByDate.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		System.out.println(doc.toString());

	}

}
