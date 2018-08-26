package com.AL.ome.addlineitem;

import java.util.ArrayList;
import java.util.List;
import org.apache.xmlbeans.XmlException;
import com.AL.ome.system.BaseALTestX;
import com.AL.V.beans.entity.LineItem;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProductType;

public class AddLineItemMotherData {

	public static LineItem getMockLineItem(String externalIds) {

		LineItem lineItem = new LineItem();
		lineItem.setExternalId(Long.parseLong(externalIds));

		return lineItem;

	}

	 
	
	public static LineItemCollectionType getLineItemCollectionType(long startFrom,
			LineItemCollectionType liTypeCollection, Integer... externalIds) {

		int i = (int)startFrom;
		for (Integer newExternalId : externalIds) {
			LineItemType lit = liTypeCollection.addNewLineItem();
			lit.setLineItemNumber(i++);
			lit.setExternalId(newExternalId);

			LineItemDetailType lineItemDetail = lit.addNewLineItemDetail();
			OrderLineItemDetailTypeType detail = lineItemDetail.addNewDetail();

			ProductType product = detail.addNewProductLineItem();
		}

		return liTypeCollection;
	}
	
	public static LineItemCollectionType getLineItemCollectionType( ) {
		LineItemCollectionType liTypeCollection = LineItemCollectionType.Factory
				.newInstance();

		return liTypeCollection;

	}
	
	

	public static LineItemCollectionType getLineItemCollectionType(long startFrom,
			Integer... externalIds) {
		LineItemCollectionType liTypeCollection = LineItemCollectionType.Factory
				.newInstance();

		return getLineItemCollectionType(  startFrom,liTypeCollection, externalIds);

	}

	public static List<LineItem> getLineItemList() {
		return getLineItemList(0);
	}

	public static List<LineItem> getLineItemList(Integer... lineItemNumbers) {

		long startIndex = 1;
		List<LineItem> lineItemBeanList = new ArrayList<LineItem>();

		for (Integer lineItemNumber : lineItemNumbers) {
			LineItem lineItem1 = new LineItem();
			lineItem1.setExternalId(startIndex++);
			lineItem1.setLineItemNumber(lineItemNumber);
			lineItemBeanList.add(lineItem1);
		}

		return lineItemBeanList;
	}

	public static OrderType getSalesOrder() {

		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\main\\resources\\xml\\ome-addlineitem-1.xml");
	 
		 OrderManagementRequestResponseDocument orderDocument1 = null;
		try {
			 
	            orderDocument1 = OrderManagementRequestResponseDocument.Factory.parse( inputXml1 );
	            
			 
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orderDocument1.getOrderManagementRequestResponse().getRequest().getOrderInfo();
	}

	public static void createPromotion(LineItemCollectionType liTypeCollection,int lineItemNumber,
			int newExternalId, boolean appliesToInternal, Integer... appliesTo) {

		LineItemType lineItemType = liTypeCollection.addNewLineItem();
		lineItemType.setExternalId(newExternalId);
		lineItemType.setLineItemNumber(lineItemNumber);

		LineItemDetailType lineItemDetail = lineItemType.addNewLineItemDetail();
		OrderLineItemDetailTypeType detail = lineItemDetail.addNewDetail();

		ApplicableType promotion = detail.addNewPromotionLineItem();
		promotion.setIsAppliesToInternal(appliesToInternal);

		for (Integer applies : appliesTo) {
			promotion.addAppliesTo(applies);
		}

	}

	public static void printLineItems(String messageName,
			List<LineItem> updatedList) {
		for (int i = 0; i < 2; i++) {
			System.out.println("**********************" + messageName
					+ "********************************");
		}
		for (LineItem li : updatedList) {
			 
			System.out.println("LineItem Number:" + li.getLineItemNumber()
					+ " ExternalId:" + li.getExternalId());
		}
		for (int i = 0; i < 2; i++) {
			System.out.println("**********************" + messageName
					+ "********************************");
		}
	}
}
