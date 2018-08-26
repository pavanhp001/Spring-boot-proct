package com.A.V.jms;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.service.V.LineItemService;
import com.A.ui.service.V.OrderService;
import com.A.ui.service.V.VOperation;
import com.A.ui.service.V.impl.OrderCacheService;
import com.A.ui.transport.TransportConfig;
import com.A.util.DateUtil;
import com.A.V.factory.SalesContextFactory;
import com.A.xml.v4.DateTimeOrTimeRangeType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderSearch;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.SalesContextType;
import com.A.xml.v4.SchedulingInfoType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSOrderTest {
	
	private String ORDER_EXT_ID = "6524";
	private Long LINE_ITEM_EXT_ID = 5919L;

	@Test
	public void testGetOrderJMS() throws Exception {

		OrderType order = OrderService.INSTANCE.getOrderByOrderNumber("31215");

		assertNotNull(order);

	}
	
	@Test
	public void updateOrder() {
		ObjectFactory oFactory = new ObjectFactory();
		OrderType orderType = oFactory.createOrderType();
		Date dt = new Date();
		orderType.setMoveDate(DateUtil.asXMLGregorianCalendar(dt));
		
		orderType.setExternalId(Long.valueOf("27559"));
		
		String correlationId = UUID.randomUUID().toString();
		OrderType order = OrderService.INSTANCE.updateOrder("default", "27559", orderType, correlationId);

		assertNotNull(order);
	}
	
	@Test
	public void getByOrderDate() {
		Map<String, Object> roles = new HashMap<String, Object>();
		final String ACTIVE_PROVIDER = "*";
		
		String date = "02/05/2013";
		List<OrderType> orders = OrderService.INSTANCE.getByOrderDate(date, roles, ACTIVE_PROVIDER);

		assertNotNull(orders);
	}
	
	@Test
	public void getOrderByLineItemNumber() {
		OrderType order = OrderService.INSTANCE.getOrderByLineItemNumber("15957", "default");
		assertNotNull(order);
	}
	
	@Test
	public void getOrderByProvider() {
		String agentId = "mnagineni";
		String orderId = "10150";
		String providerId = "239228";
		SalesContextType salesContext = null;
		Boolean filtered = Boolean.TRUE;
		OrderType order = OrderService.INSTANCE.getOrderByProvider(agentId, orderId, providerId, salesContext, filtered);
		assertNotNull(order);
	}
	
	@Test
	public void testSearchOrder() throws Exception {
		//Below information works with VPRDEV001 envt
		XMLGregorianCalendar schdStDt = DateUtil.asXMLGregorianCalendarDate("05/11/2013", "MM/dd/yyyy");
		
		String status = "provision_ready";
		String agentId = "mnagineni";
		String providerId = "23928";
		
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		Map<String, String> salesEntity = new HashMap<String, String>();
		salesEntity.put("GUID", "");//TODO: set the actual GUID
		data.put("source", salesEntity);
		SalesContextType salesContext = SalesContextFactory.INSTANCE.getXMLV4SalesContext(data);
		
		OrderSearch orderSearchResults = OrderService.INSTANCE.searchOrders(agentId, status, providerId, schdStDt, 
				null, null, salesContext);
		assertNotNull(orderSearchResults);
		assertTrue(orderSearchResults.getTotalRows() > 0);
		assertNotNull(orderSearchResults.getSearchResult());
		assertTrue(orderSearchResults.getSearchResult().size() > 0);
	}
	
//	private static DatatypeFactory df = null;
//	static {
//		try {
//			df = DatatypeFactory.newInstance();
//		} catch (DatatypeConfigurationException dce) {
//			throw new IllegalStateException(
//					"Exception while obtaining DatatypeFactory instance", dce);
//		}
//	}
//	
//	@Test
//	public void testUpdateScheduleInfo() {
//		LineItemType lineItem = new LineItemType();
//		lineItem.setExternalId(17761L);
//		lineItem.setLeadId(999L);
//		SchedulingInfoType info = new SchedulingInfoType();
//		DateTimeOrTimeRangeType dateTime = new DateTimeOrTimeRangeType();
//		GregorianCalendar gc = new GregorianCalendar();
//		gc.setTimeInMillis(System.currentTimeMillis());
//		dateTime.setDate(df.newXMLGregorianCalendar(gc));
//		dateTime.setStartTime(df.newXMLGregorianCalendar(gc));
//		dateTime.setEndTime(df.newXMLGregorianCalendar(gc));
//		dateTime.setTime(df.newXMLGregorianCalendar(gc));
//		info.setDesiredStartDate(dateTime);
//		lineItem.setSchedulingInfo(info);
//		OrderType order = LineItemService.INSTANCE.lineItemUpdate("default", "17197", 
//				lineItem, lineItem.getExternalId());
//	}
//
//	@Test
//	public void testUpdateOrderJMS() throws Exception {
//		ObjectFactory oFactory = new ObjectFactory();
//
//		LineItemType lit = oFactory.createLineItemType();
//		lit.setExternalId(5919);
//		lit.setLeadId(999L);
//		OrderType order = LineItemService.INSTANCE.lineItemUpdate("default", ORDER_EXT_ID, lit, LINE_ITEM_EXT_ID);
//
//		assertNotNull(order);
//	}

}
