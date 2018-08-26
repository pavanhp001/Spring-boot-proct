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
public final class TaskAddLineItemE1A1Test {

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
	public void testAddLineItemAfterZERO() {

		final String insertAfterLineItemNumber = "0";
		final LineItem lineItemBean = AddLineItemMotherData.getMockLineItem("1");

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList());

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList =orderChangeValueObject.getLiList();
		 

		AddLineItemMotherData.printLineItems("Insert After Existing -->0<--",updatedList);
		assertEquals(2, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(1).getExternalId()); 
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		
	}

	
	@Test
	public void testAddLineItemAfterNegativeOne() {

		final String insertAfterLineItemNumber = "-1";
		final LineItem lineItemBean = AddLineItemMotherData.getMockLineItem("1");

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList());

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList =orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Insert Before Existing",updatedList);
		assertEquals(2, updatedList.size());
		assertEquals(Long.valueOf("1000"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1"), updatedList.get(1).getExternalId()); 
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
	}
	
	
	@Test
	public void testAddLineItemHighExcessiveNumber() {

		final String insertAfterLineItemNumber = "98765";
		final LineItem lineItemBean = AddLineItemMotherData.getMockLineItem("1");

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList());

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList =orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Insert After Excessive High",updatedList);
		assertEquals(2, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(1).getExternalId()); 
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
	}
	
	@Test
	public void testAddLineItemLowExcessiveNumber() {

		final String insertAfterLineItemNumber = "-98765";
		final LineItem lineItemBean = AddLineItemMotherData.getMockLineItem("1");

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList());

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList =orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Insert Before Excessive Low",updatedList);
		assertEquals(2, updatedList.size());
		assertEquals(Long.valueOf("1000"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1"), updatedList.get(1).getExternalId()); 
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
	}
	
}
