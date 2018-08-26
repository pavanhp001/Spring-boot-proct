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
public final class TaskAddLineItemE1P1AP1Test {

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
	public void testTop() {

		final String insertAfterLineItemNumber = "-999";
		final LineItem lineItemBean = null;

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				orderId, insertAfterLineItemNumber, lineItemBean);

		orderChangeValueObject.setLiList(AddLineItemMotherData.getLineItemList(0,1));

		LineItemCollectionType lineItemCollectionType = AddLineItemMotherData
				.getLineItemCollectionType();
		
		AddLineItemMotherData.createPromotion(  lineItemCollectionType, 1,1001, Boolean.FALSE, 0);
		 	
		
		
				
		orderChangeValueObject
				.setLineItemCollectionType(lineItemCollectionType);
		LineItemManagementStrategy.updateLineItemList(
				orderChangeValueObject, unmarshallLineItem);
		
		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		AddLineItemMotherData.printLineItems("FIRST-BY-NEGATIVE",updatedList);
 
//		assertEquals(5, updatedList.size());
//		assertEquals(Long.valueOf("1000"), updatedList.get(0).getExternalId());
//		assertEquals(Long.valueOf("3000"), updatedList.get(1).getExternalId()); 
//		assertEquals(Long.valueOf("1001"), updatedList.get(2).getExternalId()); 
//		assertEquals(Long.valueOf("3001"), updatedList.get(3).getExternalId()); 
//		assertEquals(Long.valueOf("1"), updatedList.get(4).getExternalId());
//		assertEquals(0, updatedList.get(0).getLineItemNumber());
//		assertEquals(1, updatedList.get(1).getLineItemNumber());
//		assertEquals(2, updatedList.get(2).getLineItemNumber());
//		assertEquals(3, updatedList.get(3).getLineItemNumber());
//		assertEquals(4, updatedList.get(4).getLineItemNumber());
	}
	 
 
}
