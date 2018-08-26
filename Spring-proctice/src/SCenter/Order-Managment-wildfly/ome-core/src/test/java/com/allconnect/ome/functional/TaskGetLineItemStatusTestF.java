/**
 *
 */
package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.BaseALTest;
import com.AL.task.impl.TaskGetLineItemStatus;
import com.AL.task.impl.TaskUpdateLineItem;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public final class TaskGetLineItemStatusTestF {

	@Before
	public void setUp() {

	}

	@Test
	public void testUpdateLineItem() {
		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\updateLineitem.xml");
		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\symph_update_lineitem.xml");
		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\harmony_update_dish_lineitem_features_sample.xml");
		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\harmony_dish_updatelineitem.xml");
		String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\getLineItemStatus.xml");
		//String inputXml1 = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\80267_updatelienitem_issue.xml");

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

	/**
	 * @param orderDocument
	 *            Order Management Request Response Document
	 */
	public void doTask(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);

		assertNotNull(orderDocument);
		assertNotNull(orderDocument.getOrderManagementRequestResponse());
		assertNotNull(orderDocument.getOrderManagementRequestResponse()
				.getRequest());
		// assertNotNull(
		// orderDocument.getOrderManagementRequestResponse().getRequest().getNewLineItem()
		// );
		// LineItemType lineItemType =
		// orderDocument.getOrderManagementRequestResponse().getRequest().getNewLineItem();

		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();
		assertNotNull(request);

		TaskGetLineItemStatus task = (TaskGetLineItemStatus) SpringUtilTest
				.getBean(TaskGetLineItemStatus.class);

		task.execute(orderDocument);
	}
}
