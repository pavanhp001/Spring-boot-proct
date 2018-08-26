/**
 *
 */
package com.AL.ome.builder;

import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskUpdateLineItemStatus;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContext.xml" })
public class TaskUpdateLineItemStatusTest extends BaseALTestX {
	@Autowired
	private EntityManagerFactory em;

	@Test
	public void testUpdateLineItemStatus() {
		String inputXml1 = getXMLFromFile("src\\test\\resources\\xml\\ome-updateLineItem-1.xml");

		assertNotNull(inputXml1);

		try {
			doTask(OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1));

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
		assertNotNull(orderDocument.getOrderManagementRequestResponse());
		assertNotNull(orderDocument.getOrderManagementRequestResponse()
				.getRequest());

		TaskUpdateLineItemStatus task = new TaskUpdateLineItemStatus();

		task.execute(orderDocument);
	}

	public EntityManagerFactory getEm() {
		return em;
	}

	public void setEm(EntityManagerFactory em) {
		this.em = em;
	}

}
