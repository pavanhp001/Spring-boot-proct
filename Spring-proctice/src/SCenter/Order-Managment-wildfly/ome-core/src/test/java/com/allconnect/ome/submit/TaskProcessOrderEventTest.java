package com.AL.ome.submit;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.util.OmeSpringUtil;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NotificationEventCollectionType;
import com.AL.xml.v4.NotificationEventType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextNoArbiter.xml" })
public class TaskProcessOrderEventTest extends BaseALTestX {

	@Autowired
	private ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Before
	public void setUp() {

		OmeSpringUtil.INSTANCE.setApplicationContext(applicationContext);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testSubmitOrder() {
		String inputXml1 = getXMLFromFile("src\\test\\resources\\xml\\ome-createOrder-2.xml");

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
	public void doTask(final OrderManagementRequestResponseDocument doc) {

		// create response container
		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();
		Response response = requestResponse.addNewResponse();

		OrderType orderType = doc.getOrderManagementRequestResponse()
				.getRequest().getOrderInfo();
		response.getOrderInfoList().add(orderType);
		orderType.getLineItems().getLineItemList().get(0)
				.unsetNotificationEvents();

		Request request = doc.getOrderManagementRequestResponse().getRequest();

		Long orderId = 1L;// Long.valueOf( request.getOrderId() );

		// Ensure that request is in the response container
		Map<String, NotificationEventCollectionType> notifEventsMap = loadRequestToResponse(
				container, request, orderId);

		addNotificationEventsFromRequest(notifEventsMap, response);

		System.out.println(container.toString());

	}

	/**
	 * @param taskResponse
	 *            Load Request information to response
	 * @param container
	 *            Response Object
	 */
	public Map<String, NotificationEventCollectionType> loadRequestToResponse(
			final OrderManagementRequestResponseDocument container,
			final Request request, Long orderId) {

		Map<String, NotificationEventCollectionType> events = new HashMap<String, NotificationEventCollectionType>();

		if (request != null) {
			if (orderId != null) {
				request.setOrderId(String.valueOf(orderId.longValue()));
			}

			container.getOrderManagementRequestResponse().setRequest(request);
			if ((request.getOrderInfo() != null)
					&& (request.getOrderInfo().getLineItems() != null)
					&& (request.getOrderInfo().getLineItems().getLineItemList() != null)) {
				List<LineItemType> litList = request.getOrderInfo()
						.getLineItems().getLineItemList();
				for (LineItemType lit : litList) {

					try {
						String externalId = String.valueOf(lit.getExternalId());

						if (lit.getNotificationEvents() != null) {
							events.put(externalId, lit.getNotificationEvents());
						}
					} catch (Exception e) {

					}
				}
			}
		}

		return events;
	}

	public void addNotificationEventsFromRequest(
			final Map<String, NotificationEventCollectionType> notifEventsMap,
			final Response response) {

		if ((notifEventsMap != null) && (!notifEventsMap.isEmpty())
				&& (response != null) && (response.getOrderInfoList() != null)
				&& (response.getOrderInfoList().size() > 0)) {

			LineItemCollectionType litCollection = response.getOrderInfoList()
					.get(0).getLineItems();
			if ((litCollection != null)
					&& (litCollection.getLineItemList() != null)) {
				List<LineItemType> listOfLineItems = litCollection
						.getLineItemList();

				for (LineItemType lit : listOfLineItems) {

					String responseLineItemExternalId = String.valueOf(lit
							.getExternalId());
					NotificationEventCollectionType notEventCollType = notifEventsMap
							.get(responseLineItemExternalId);
					NotificationEventCollectionType newNotifEventCollType = lit
							.getNotificationEvents();
					if (newNotifEventCollType == null) {
						newNotifEventCollType = lit.addNewNotificationEvents();
					}

					if ((notEventCollType != null)
							&& (notEventCollType.getEventList() != null)
							&& (notEventCollType.getEventList().size() > 0)) {

						for (NotificationEventType eventFromRequest : notEventCollType
								.getEventList()) {
							newNotifEventCollType.getEventList().add(
									eventFromRequest);
						}

					}
				}

			}
		}

	}

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

		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\main\\resources\\xml\\products_createOrder_refCustomer.xml");
		inputXml1 = inputXml1.replaceAll("ZZZZZZZZ", "EXT-" + unique);
		inputXml1 = inputXml1.replaceAll("CCCCCCCC", "CC-" + unique);
		inputXml1 = inputXml1.replaceAll("AAAAAAAA", "CON-" + unique);
		inputXml1 = inputXml1.replaceAll("BBBBBBBB", "ACT-" + unique);

		inputXml1 = inputXml1.replaceAll("CCCCCCCCNNNNNN", "CONFIRM-" + unique);
		inputXml1 = inputXml1.replaceAll("CCCCCAAAAANNNNNN", "CUST-" + unique);

		assertNotNull(inputXml1);

		try {
			OrderManagementRequestResponseDocument order;
			order = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			doTaskCreate(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doTaskCreate(
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
