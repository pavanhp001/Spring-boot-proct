/**
 *
 */
package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.AL.BaseALTest;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;


@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public final class CreateOrderTestF {

	@Before
	public void setUp() {

		System.out.println("Data");
	}

	@Test
	public void testCreateOrderTest() {
		Calendar now = Calendar.getInstance();
		int milli = now.get(Calendar.MILLISECOND);
		int second = now.get(Calendar.SECOND);
		int minute = now.get(Calendar.MINUTE);
		int hour = now.get(Calendar.HOUR);
		int day = now.get(Calendar.DATE);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String unique = year + month + day + "-" + hour + minute + second + "-"
				+ milli + "";

		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\att_issue.xml");
		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\harmony_directstar_create_order_refcust.xml");
		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\93673.xml");
		String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\concert\\CreateOrder.xml");

		assertNotNull(inputXml1);

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

		TaskCreateOrder task = (TaskCreateOrder) SpringUtilTest
				.getBean(TaskCreateOrder.class);

		OrderManagementRequestResponseDocument result = task
				.execute(orderDocument);
		Response response = result.getOrderManagementRequestResponse()
				.getResponse();
		assertNotNull(response);

		OrderType responseOrder = response.getOrderInfoArray()[0];

		assertNotNull(responseOrder.getALConfirmationNumber());
		assertNotNull(responseOrder.getAssociatedWithMove());

		// assertNotNull( response.getNewOrderInfo().getDirectMailOptIn() );
		assertNotNull(responseOrder.getSource());
		assertNotNull(responseOrder.getAgentId());
		assertNotNull(responseOrder.getExternalId());
		assertNotNull(responseOrder.getCampaignId());
		assertNotNull(response.getOrderId());
		assertNotNull(response.getOrderInfoList());
		assertNotNull(response.getOrderInfoList().get(0));
		assertNotNull(response.getOrderInfoList().get(0).getLineItems());
		assertNotNull(response.getOrderInfoList().get(0).getLineItems().getLineItemList());
	}

}
