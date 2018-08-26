package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskGetOrderByProvider;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContextTest.xml" })
public class GetOrderByProviderTestF {
	private static final Logger logger = Logger
			.getLogger(GetOrderByLineItemTestF.class);
	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\test\\resources\\xml\\GetOrderByProvider.xml");
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

		TaskGetOrderByProvider task = (TaskGetOrderByProvider) SpringUtilTest
				.getBean(TaskGetOrderByProvider.class);

		OrderManagementRequestResponseDocument responseDoc = task
				.execute(orderDocument);
		logger.info(responseDoc.toString());
		assertNotNull(responseDoc);
		assertNotNull(responseDoc.getOrderManagementRequestResponse());
		assertNotNull(responseDoc.getOrderManagementRequestResponse().getResponse());
		Response response = responseDoc.getOrderManagementRequestResponse().getResponse();
		assertNotNull(response.getOrderInfoList());
		/*assertEquals("Requested OrderId should match with response Order external id.",String.valueOf(response.getOrderInfoList().get(0).getExternalId()),orderDocument.getOrderManagementRequestResponse().getRequest().getOrderId());
		assertTrue(response.getOrderInfoList().get(0).getLineItems().getLineItemList().size() == 1);
		assertEquals("Requested LineItem should match with response LineItem external id.",String.valueOf(response.getOrderInfoList().get(0).getLineItems().getLineItemList().get(0).getExternalId()),orderDocument.getOrderManagementRequestResponse().getRequest().getLineItemId());*/
	}
}
