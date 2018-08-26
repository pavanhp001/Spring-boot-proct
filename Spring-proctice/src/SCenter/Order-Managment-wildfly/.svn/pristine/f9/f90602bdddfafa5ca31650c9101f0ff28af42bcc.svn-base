package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.AL.BaseALTest;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.task.impl.TaskSubmit;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class TaskSubmitTestF {

    @Before
    public void setUp() {

	System.out.println("Data");
    }


    @Test
    public void testSubmit() {

	String inputXml = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\95469.xml");
	assertNotNull(inputXml);

	try {
		OrderManagementRequestResponseDocument order;
		order = OrderManagementRequestResponseDocument.Factory
				.parse(inputXml);

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

	TaskSubmit task = (TaskSubmit) SpringUtilTest
			.getBean(TaskSubmit.class);

	OrderManagementRequestResponseDocument result = task
			.execute(orderDocument);
	Response response = result.getOrderManagementRequestResponse()
			.getResponse();
	assertNotNull(response);

	OrderType responseOrder = response.getOrderInfoArray()[0];

}
}
