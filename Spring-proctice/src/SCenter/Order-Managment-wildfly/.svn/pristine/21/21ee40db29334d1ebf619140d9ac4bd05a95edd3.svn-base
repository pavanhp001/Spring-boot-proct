package com.AL.ome.addlineitem.external;

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
public final class TaskAddLineItemE0A1 {

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
	public void testMiddleV3InternalAndExternal() {

		final String insertAfterLineItemNumber = "0";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList(1,2,3));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,2000,3000);
		
		AddLineItemMotherData.createPromotion(  lineItemCollectionType, lineItemCollectionType.sizeOfLineItemArray(),1001, Boolean.FALSE, 0);
		AddLineItemMotherData.createPromotion(  lineItemCollectionType, lineItemCollectionType.sizeOfLineItemArray(),2001, Boolean.TRUE, 0);
				
				
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Zero with two productsV2",updatedList);
 
		assertEquals(7, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("2000"), updatedList.get(1).getExternalId()); 
		assertEquals(Long.valueOf("3000"), updatedList.get(2).getExternalId()); 
		assertEquals(Long.valueOf("1001"), updatedList.get(3).getExternalId());
		assertEquals(Long.valueOf("2001"), updatedList.get(4).getExternalId());
		assertEquals(Long.valueOf("2"), updatedList.get(5).getExternalId());
		assertEquals(Long.valueOf("3"), updatedList.get(6).getExternalId());
		
  
		
		 
		
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
		assertEquals(3, updatedList.get(3).getLineItemNumber());
		assertEquals(4, updatedList.get(4).getLineItemNumber());
		assertEquals(5, updatedList.get(5).getLineItemNumber());
		assertEquals(6, updatedList.get(6).getLineItemNumber());
		 
	}
	
	
	@Test
	public void testMiddleV2() {

		final String insertAfterLineItemNumber = "0";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList(1,2));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		
		AddLineItemMotherData.createPromotion(  lineItemCollectionType, lineItemCollectionType.sizeOfLineItemArray(),2000, Boolean.FALSE, 1);
				
				
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Zero with two productsV2",updatedList);
 
		assertEquals(4, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(1).getExternalId()); 
		assertEquals(Long.valueOf("2000"), updatedList.get(2).getExternalId()); 
		assertEquals(Long.valueOf("2"), updatedList.get(3).getExternalId());
		
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
	}
	
	
	
	@Test
	public void testMiddle() {

		final String insertAfterLineItemNumber = "0";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList(1,2));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		
		AddLineItemMotherData.createPromotion(  lineItemCollectionType, lineItemCollectionType.sizeOfLineItemArray(),2000, Boolean.FALSE, 0);
				
				
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Zero with two products",updatedList);
 
		assertEquals(4, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(1).getExternalId()); 
		assertEquals(Long.valueOf("2000"), updatedList.get(2).getExternalId()); 
		assertEquals(Long.valueOf("2"), updatedList.get(3).getExternalId());
		
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

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList());

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		
		AddLineItemMotherData.createPromotion(  lineItemCollectionType, lineItemCollectionType.sizeOfLineItemArray(),2000, Boolean.FALSE, 0);
				
				
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Zero",updatedList);
 
		assertEquals(3, updatedList.size());
		assertEquals(Long.valueOf("1"), updatedList.get(0).getExternalId());
		assertEquals(Long.valueOf("1000"), updatedList.get(1).getExternalId()); 
		assertEquals(Long.valueOf("2000"), updatedList.get(2).getExternalId()); 
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
	}

	@Test
	public void testFirst() {

		final String insertAfterLineItemNumber = "-1";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList());

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType(0,1000);
		
		AddLineItemMotherData.createPromotion(  lineItemCollectionType, lineItemCollectionType.sizeOfLineItemArray(),2000, Boolean.FALSE, 0);
				
				
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("Negative-One",updatedList);
 
		assertEquals(3, updatedList.size());
		assertEquals(Long.valueOf("1000"), updatedList.get(0).getExternalId()); 
		assertEquals(Long.valueOf("2000"), updatedList.get(1).getExternalId()); 
		assertEquals(Long.valueOf("1"), updatedList.get(2).getExternalId());
		assertEquals(0, updatedList.get(0).getLineItemNumber());
		assertEquals(1, updatedList.get(1).getLineItemNumber());
		assertEquals(2, updatedList.get(2).getLineItemNumber());
	}
	 
	
}
