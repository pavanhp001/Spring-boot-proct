 package com.AL;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.task.strategy.LineItemManagementStrategy;
import com.AL.V.beans.entity.LineItem;
import com.AL.vm.util.converter.unmarshall.UnmarshallLineItem;
import com.AL.vm.vo.OrderChangeValueObject;
//import com.AL.xml.v4.ApplicableType;
//import com.AL.xml.v4.LineItemCollectionType;
//import com.AL.xml.v4.LineItemDetailType;
//import com.AL.xml.v4.LineItemDetailType.DetailType;
//import com.AL.xml.v4.LineItemType;
//import com.AL.xml.v4.MarketItemType;
import com.AL.xml.v4.LineItemDetailType;

/**
* @author ebthomas
* 
*/
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class LineItemAddStrategyTest  
{

	@Autowired
	UnmarshallLineItem unmarshallLineItem;
   
   @Before
   public void setUp() {
        
   }
   
   @Test
   public void testLineItemAdd( )
   {
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PRODUCT,"-1");
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PRODUCT,"0");
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PRODUCT,"1");
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PRODUCT,"2");
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PRODUCT,"3");
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PRODUCT,"4");
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PRODUCT,"99");
//	   
//	   testPlacementOfSingle(LineItemDetailType.DetailType.BUNDLE,"0");
//	   
//	   testPlacementOfSingle(LineItemDetailType.DetailType.PROMOTION,"0");
	   
//	   testPromotionSingle(  "0", 0, false);
//	   testPromotionSingle(  "0", 1, false);
//	   testPromotionSingle(  "1", 2, false);
//	   testPromotionSingle(  "1", 3, false);
//	   
//	   
//	   testPromotionDouble(  "0", 0, false);
//       testPromotionDouble(  "0", 1, false);
//       testPromotionDouble(  "1", 2, false);
//       testPromotionDouble(  "1", 3, false);
//       
//       testPromotionDouble(  "0", 1, true);
//       
//       testPromotionFive( "1" );
   }
   
 

   
//   public void testPromotionDouble(  String lineItemNumber, int appliesTo, boolean isInternal)
//   {
//       List<LineItem> existingListItemList = LineItemMotherObject.getLineItem(4); 
//       assertEquals(4, existingListItemList.size());
//       
//       
//       LineItemCollectionType newLineItemContainerType = LineItemCollectionType.Factory.newInstance();
//       
//       //Add Promotion
//       LineItemType newLineItemType = newLineItemContainerType.addNewLineItem();
//       newLineItemType.setExternalId(-1);
//       newLineItemType.setLineItemNumber( 0 );
//       LineItemDetailType lineItemDetailType = newLineItemType.addNewLineItemDetail();  
//       lineItemDetailType.setDetailType(LineItemDetailType.DetailType.PROMOTION);
//       ApplicableType newPromotion = lineItemDetailType.addNewDetail().addNewPromotionLineItem();
//     //  newPromotion.setAppliesTo(String.valueOf(appliesTo));
//       newPromotion.setIsAppliesToInternal(isInternal);
//       
//       //MarketItem
//       LineItemType newLineItemType1 = newLineItemContainerType.addNewLineItem();
//       newLineItemType1.setLineItemNumber( 1 );
//       newLineItemType1.setExternalId(-1);
//       LineItemDetailType lineItemDetailType1 = newLineItemType1.addNewLineItemDetail();  
//       lineItemDetailType1.setDetailType(LineItemDetailType.DetailType.MARKET_ITEM);
//       MarketItemType newMarketItem = lineItemDetailType1.addNewDetail().addNewMarketItemLineItem();
//       newMarketItem.setExternalId( "ExternalID-Of-The-New-Market-Item" );
//        
//       OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(existingListItemList,newLineItemContainerType, lineItemNumber);
//       LineItemManagementStrategy.updateLineItemList(  orderChangeValueObject,unmarshallLineItem);
//       List<LineItem> updatedList  = orderChangeValueObject.getLiList();
//       int expectedIndex = Integer.parseInt(lineItemNumber)+1;
//       
//       if (expectedIndex >= updatedList.size())
//       {
//           expectedIndex = updatedList.size() -1;
//       }
//       assertEquals("NEW-PROMO-NEW",updatedList.get(expectedIndex).getExternalId());
//       
//       System.out.println("add after--->"+lineItemNumber);
//       for (LineItem li:updatedList)
//       {
//           System.out.println("Line Number:"+li.getLineItemNumber()+":"+li.getExternalId());
//           
//       }
//       assertEquals(6, updatedList.size());
//   }
//   
//   public void testPromotionFive(  String afterLineItemNumber )
//   {
//       List<LineItem> existingListItemList = LineItemMotherObject.getLineItem(4); 
//       assertEquals(4, existingListItemList.size());
//       
//       
//       LineItemCollectionType newLineItemContainerType = LineItemCollectionType.Factory.newInstance();
//       
//       //Add Promotion
//       LineItemType newLineItemType = newLineItemContainerType.addNewLineItem();
//       newLineItemType.setExternalId(-1);
//       newLineItemType.setLineItemNumber( 0 );
//       LineItemDetailType lineItemDetailType = newLineItemType.addNewLineItemDetail();  
//       lineItemDetailType.setDetailType(LineItemDetailType.DetailType.PROMOTION);
//       ApplicableType newPromotion = lineItemDetailType.addNewDetail().addNewPromotionLineItem();
//       //newPromotion.setAppliesTo(String.valueOf("4"));
//       newPromotion.setIsAppliesToInternal(true);
//       
//         newLineItemType = newLineItemContainerType.addNewLineItem();
//       newLineItemType.setExternalId(-1);
//       newLineItemType.setLineItemNumber( 5 );
//         lineItemDetailType = newLineItemType.addNewLineItemDetail();  
//       lineItemDetailType.setDetailType(LineItemDetailType.DetailType.PROMOTION);
//         newPromotion = lineItemDetailType.addNewDetail().addNewPromotionLineItem();
//     //  newPromotion.setAppliesTo(String.valueOf("3"));
//       newPromotion.setIsAppliesToInternal(true);
//       
//       //MarketItem
//       LineItemType newLineItemType1 = newLineItemContainerType.addNewLineItem();
//       newLineItemType1.setLineItemNumber( 1 );
//       newLineItemType1.setExternalId(-1);
//       LineItemDetailType lineItemDetailType1 = newLineItemType1.addNewLineItemDetail();  
//       lineItemDetailType1.setDetailType(LineItemDetailType.DetailType.MARKET_ITEM);
//       MarketItemType newMarketItem = lineItemDetailType1.addNewDetail().addNewMarketItemLineItem();
//       newMarketItem.setExternalId( "ExternalID-Of-The-New-Market-Item" );
//       
//         newLineItemType1 = newLineItemContainerType.addNewLineItem();
//       newLineItemType1.setLineItemNumber( 2 );
//       newLineItemType1.setExternalId(-1);
//         lineItemDetailType1 = newLineItemType1.addNewLineItemDetail();  
//       lineItemDetailType1.setDetailType(LineItemDetailType.DetailType.MARKET_ITEM);
//         newMarketItem = lineItemDetailType1.addNewDetail().addNewMarketItemLineItem();
//       newMarketItem.setExternalId( "ExternalID-Of-The-New-Market-Item" );
//       
//       newLineItemType1 = newLineItemContainerType.addNewLineItem();
//       newLineItemType1.setLineItemNumber( 3 );
//       newLineItemType1.setExternalId(-1);
//         lineItemDetailType1 = newLineItemType1.addNewLineItemDetail();  
//       lineItemDetailType1.setDetailType(LineItemDetailType.DetailType.MARKET_ITEM);
//         newMarketItem = lineItemDetailType1.addNewDetail().addNewMarketItemLineItem();
//       newMarketItem.setExternalId( "ExternalID-Of-The-New-Market-Item" );
//       
//       
//       newLineItemType1 = newLineItemContainerType.addNewLineItem();
//       newLineItemType1.setLineItemNumber( 4 );
//       newLineItemType1.setExternalId(-1);
//         lineItemDetailType1 = newLineItemType1.addNewLineItemDetail();  
//       lineItemDetailType1.setDetailType(LineItemDetailType.DetailType.MARKET_ITEM);
//         newMarketItem = lineItemDetailType1.addNewDetail().addNewMarketItemLineItem();
//       newMarketItem.setExternalId( "ExternalID-Of-The-New-Market-Item" );
//       
//       
//        
//       OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(existingListItemList,newLineItemContainerType, afterLineItemNumber);
//       LineItemManagementStrategy.updateLineItemList(  orderChangeValueObject,unmarshallLineItem);
//       List<LineItem> updatedList  = orderChangeValueObject.getLiList();
//       
//       int expectedIndex = Integer.parseInt(afterLineItemNumber)+1;
//       
//       if (expectedIndex >= updatedList.size())
//       {
//           expectedIndex = updatedList.size() -1;
//       }
//      // assertEquals("NEW-PROMO-NEW",updatedList.get(expectedIndex).getExternalId());
//       
//       System.out.println("add after--->"+afterLineItemNumber);
//       for (LineItem li:updatedList)
//       {
//           System.out.println("Line Number:"+li.getLineItemNumber()+":"+li.getExternalId());
//           
//       }
//       assertEquals(10, updatedList.size());
//   }
//   
//   public void testPromotionSingle(  String lineItemNumber, int appliesTo, boolean isInternal)
//   {
//	   List<LineItem> existingListItemList = LineItemMotherObject.getLineItem(4); 
//	   assertEquals(4, existingListItemList.size());
//	   
//	   
//	   LineItemCollectionType newLineItemContainerType = LineItemCollectionType.Factory.newInstance();
//	   LineItemType newLineItemType = newLineItemContainerType.addNewLineItem();
//	   newLineItemType.setExternalId(-1);
//	   LineItemDetailType lineItemDetailType = newLineItemType.addNewLineItemDetail();  
//	   lineItemDetailType.setDetailType(LineItemDetailType.DetailType.PROMOTION);
//	   ApplicableType newPromotion = lineItemDetailType.addNewDetail().addNewPromotionLineItem();
//	  // newPromotion.setAppliesTo(String.valueOf(applies));
//	   newPromotion.setIsAppliesToInternal(isInternal);
//	    
//	   OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(existingListItemList,newLineItemContainerType, lineItemNumber);
//	   LineItemManagementStrategy.updateLineItemList(  orderChangeValueObject,unmarshallLineItem);
//       List<LineItem> updatedList  = orderChangeValueObject.getLiList();
//	   int expectedIndex = Integer.parseInt(lineItemNumber)+1;
//	   
//	   if (expectedIndex >= updatedList.size())
//	   {
//		   expectedIndex = updatedList.size() -1;
//	   }
//	   assertEquals("NEW-PROMO-NEW",updatedList.get(expectedIndex).getExternalId());
//	   
//	   System.out.println("add after--->"+lineItemNumber);
//	   for (LineItem li:updatedList)
//	   {
//		   System.out.println(li.getExternalId());
//		   
//	   }
//	   assertEquals(5, updatedList.size());
//   }
//   
//   public void testPlacementOfSingle(DetailType.Enum detailType, String lineItemNumber)
//   {
//	   List<LineItem> existingListItemList = LineItemMotherObject.getLineItem(4); 
//	   assertEquals(4, existingListItemList.size());
//	   
//	   
//	   LineItemCollectionType newLineItemContainerType = LineItemCollectionType.Factory.newInstance();
//	   LineItemType newLineItemType = newLineItemContainerType.addNewLineItem();
//	   newLineItemType.setExternalId(-1);
//	   LineItemDetailType lineItemDetailType = newLineItemType.addNewLineItemDetail();  
//	   lineItemDetailType.setDetailType(detailType);
//	    
//	   OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(existingListItemList,newLineItemContainerType, lineItemNumber);
//	   LineItemManagementStrategy.updateLineItemList(  orderChangeValueObject,unmarshallLineItem);
//       List<LineItem> updatedList  = orderChangeValueObject.getLiList();
//	   int expectedIndex = Integer.parseInt(lineItemNumber)+1;
//	   
//	   if (expectedIndex >= updatedList.size())
//	   {
//		   expectedIndex = updatedList.size() -1;
//	   }
//	   assertEquals("NEW",updatedList.get(expectedIndex).getExternalId());
//	   
//	   System.out.println("add after--->"+lineItemNumber);
//	   for (LineItem li:updatedList)
//	   {
//		   System.out.println(li.getExternalId());
//	   }
//	   assertEquals(5, updatedList.size());
//   }
   
//	<xs:enumeration value="promotion"/>
// <xs:enumeration value="marketItem"/>
// <xs:enumeration value="bundle"/>
   
   
    

}

