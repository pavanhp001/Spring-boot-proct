package com.AL.ome.submit;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskSubmit;
import com.AL.task.impl.TaskUpdateLineItemStatus;
import com.AL.util.OmeSpringUtil;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextNoArbiter.xml" })
public class NoEventSingleLineItemTest extends BaseALTestX {

	@Autowired
	private ApplicationContext applicationContext;

	@Before
	public void setUp() {

		OmeSpringUtil.INSTANCE.setApplicationContext(applicationContext);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testSubmitOrder() {
		//String inputXml1 = getXMLFromFile("/Users/ethomas/work/OrderManagement/ome-core/src/test/resources/xml/ome-order-submit.xml");
		String inputXml1 = getXMLFromFile("/Users/ethomas/work/OrderManagement/ome-core/src/test/resources/xml/ome-order-status-change.xml");
	 
		 

		try {
			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "hold_order")));
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "sales_new_order"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "sales_defer")));  
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "sales_pre_order")));  
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "provision_problem")));  
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "processing_schedule_confirmed"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "processing_connected"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "processing_disconnected"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "processing_aged"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "processing_schedule_pending"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "hold_order_pending_problem"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "processing_problem_ordered_vru_failed"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "hold_customer_action"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "hold_authorization_pending"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "hold_provider"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "hold_order")));  
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "processing_cancelled"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "failed"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "cancelled_removed"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "failed_test"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "reverse_schedule_pending"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "reverse_schedule_confirmed"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "reverse_connected"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "reverse_disconnected"))); 
//			doTask(OrderManagementRequestResponseDocument.Factory.parse(StringUtils.replace(inputXml1, "ZZZZZZZZZ", "reverse_aged")));
			
			
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

		//TaskSubmit task = (TaskSubmit) SpringUtilTest.getBean(TaskSubmit.class);
		TaskUpdateLineItemStatus task = (TaskUpdateLineItemStatus) SpringUtilTest.getBean(TaskUpdateLineItemStatus.class);
		
		OrderManagementRequestResponseDocument result = task
				.execute(orderDocument);
		Response response = result.getOrderManagementRequestResponse()
				.getResponse();
		assertNotNull(response);
	}

	 
	public void doTaskCreate(
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

		assertNotNull(responseOrder.getALConfirmationNumber());
		assertNotNull(responseOrder.getALCustomerAccountNumber());
		assertNotNull(responseOrder.getAssociatedWithMove());

		// assertNotNull( response.getNewOrderInfo().getDirectMailOptIn() );
		assertNotNull(responseOrder.getSource());
		assertNotNull(responseOrder.getAgentId());
		assertNotNull(responseOrder.getExternalId());
		assertNotNull(responseOrder.getCampaignId());
		assertNotNull(response.getOrderId());
	}
}
