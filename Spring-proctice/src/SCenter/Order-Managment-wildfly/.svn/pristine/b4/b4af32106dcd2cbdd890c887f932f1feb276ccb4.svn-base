package com.AL.ome.addlineitem.internal;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.AL.ome.addlineitem.AddLineItemMotherData;
import com.AL.task.strategy.LineItemManagementStrategy;
import com.AL.util.OmeSpringUtil;
import com.AL.V.beans.entity.LineItem;
import com.AL.vm.util.converter.unmarshall.UnmarshallLineItem;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.LineItemCollectionType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public final class TaskAddLineItemExternalAssignTest {

	final static String orderId = "1";

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	UnmarshallLineItem unmarshallLineItem;

	@Before
	public void setUp() {

		OmeSpringUtil.INSTANCE.setApplicationContext(applicationContext);

	}
	
	@Test
	public void testFirst() {

		final String insertAfterLineItemNumber = "-1";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData
				.getLineItemList(0));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0, 1000);

		AddLineItemMotherData.createPromotion(lineItemCollectionType,
				lineItemCollectionType.sizeOfLineItemArray(), 1001,
				Boolean.FALSE, 0);

		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(orderChangeValueObject,
				unmarshallLineItem);

		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("ADD AT TOP", updatedList);

		assertEquals(3, updatedList.size());
		assertEquals(Long.valueOf("1000"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1001"), updatedList.get(1).getExternalId());
		assertEquals(Long.valueOf("1"), updatedList.get(2).getExternalId());
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
	}

	@Test
	public void testTop() {

		final String insertAfterLineItemNumber = "0";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData
				.getLineItemList(1));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0, 1000);

		AddLineItemMotherData.createPromotion(lineItemCollectionType,
				lineItemCollectionType.sizeOfLineItemArray(), 2000,
				Boolean.FALSE, 0);

		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(orderChangeValueObject,
				unmarshallLineItem);

		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("SingleAdd", updatedList);

		assertEquals(3, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(1).getExternalId());
		assertEquals(Long.valueOf("2000"), updatedList.get(2).getExternalId());
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
	}
	
	@Test
	public void testBottom() {

		final String insertAfterLineItemNumber = "99";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData
				.getLineItemList(1,2));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0, 1000);

		AddLineItemMotherData.createPromotion(lineItemCollectionType,
				lineItemCollectionType.sizeOfLineItemArray(), 2000,
				Boolean.FALSE, 0);

		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(orderChangeValueObject,
				unmarshallLineItem);

		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("SingleAdd", updatedList);

		assertEquals(4, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("2"), updatedList.get(1).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(2).getExternalId());
		assertEquals(Long.valueOf("2000"), updatedList.get(3).getExternalId());
		
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
		assertEquals(3, updatedList.get(3).getLineItemNumber());
	}

}
