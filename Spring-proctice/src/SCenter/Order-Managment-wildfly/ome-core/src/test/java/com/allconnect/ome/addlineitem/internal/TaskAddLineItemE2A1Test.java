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
public final class TaskAddLineItemE2A1Test {

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

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList(0,1));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList =orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Insert After Existing -->0<--",updatedList);
		assertEquals(3, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(1).getExternalId()); 
		assertEquals(Long.valueOf("2"), updatedList.get(2).getExternalId());
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
		
	}
	
	
	@Test
	public void testAddLineItemBeforeZERO() {

		final String insertAfterLineItemNumber = "-1";
		final LineItem lineItemBean = AddLineItemMotherData.getMockLineItem("1");

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList(0,1));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList =orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Insert Before First",updatedList);
		assertEquals(3, updatedList.size());
		assertEquals(Long.valueOf("1000"), updatedList.get(0).getExternalId()); 
		assertEquals(Long.valueOf("1"), updatedList.get(1).getExternalId()); 
		assertEquals(Long.valueOf("2"), updatedList.get(2).getExternalId());
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
		
	}
	
	@Test
	public void testAddLineItemLast() {

		final String insertAfterLineItemNumber = "2";
		final LineItem lineItemBean = AddLineItemMotherData.getMockLineItem("1");

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList(0,1));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList =orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Insert Last",updatedList);
		assertEquals(3, updatedList.size());
		
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId()); 
		assertEquals(Long.valueOf("2"), updatedList.get(1).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(2).getExternalId()); 
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
		
	}
 
}
