/**
 *
 */
package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskAddLineItem;
import com.AL.util.OmeSpringUtil;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

@RunWith( value = SpringJUnit4ClassRunner.class )
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration( locations = { "classpath:**/applicationContextTest.xml" } )
public final class TaskAddLineItemTestF {

	@Autowired
	private ApplicationContext applicationContext;

	private static final OmeSpringUtil omeSpringUtil =  OmeSpringUtil.INSTANCE;

	@Before
	public void setUp() {

		 OmeSpringUtil.INSTANCE.setApplicationContext(applicationContext);

	}

	@Test
	public void testUpdateLineItemStatus() {

		String inputXml1 = BaseALTestX
				.getXMLFromFile("src//test//resources//xml//92631_SYP.xml");
		assertNotNull(inputXml1);

		try {
			doTask(OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

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
		assertNotNull(orderDocument.getOrderManagementRequestResponse()
				.getRequest().getNewLineItems());

		LineItemCollectionType liColl = orderDocument
				.getOrderManagementRequestResponse().getRequest()
				.getNewLineItems();
		List<LineItemType> liList = liColl.getLineItemList();
		assertNotNull(liList);

		/*for (LineItemType lineItemType : liList) {
			assertNotNull(lineItemType.getExternalId());
			//assertNotNull(lineItemType.getSvcAddressExtId());
		}*/

		TaskAddLineItem task = (TaskAddLineItem) SpringUtilTest.getBean(TaskAddLineItem.class);

		task.execute(orderDocument);
	}
}
