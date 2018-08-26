/**
 *
 */
package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskDelete;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public final class DeleteOrderTestF {

	private ApplicationContext context;

	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\main\\resources\\xml\\ome-delete-1.xml");

		System.out.println(inputXml1);

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

		TaskDelete task = (TaskDelete) SpringUtilTest.getBean(TaskDelete.class);

		orderDocument.getOrderManagementRequestResponse().getRequest();// .setOrderId("EXT-2028-92926-328");
		OrderManagementRequestResponseDocument result = task
				.execute(orderDocument);
		Response response = result.getOrderManagementRequestResponse()
				.getResponse();
		assertNotNull(response);

		doDeleteTask(task, orderDocument, String.valueOf(60));
		doDeleteTask(task, orderDocument, String.valueOf(62));
		doDeleteTask(task, orderDocument, String.valueOf(97));

	}

	public void doDeleteTask(TaskDelete task,
			final OrderManagementRequestResponseDocument orderDocument,
			String id) {
		orderDocument.getOrderManagementRequestResponse().getRequest()
				.setOrderId(id);
		task.execute(orderDocument);
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

}
