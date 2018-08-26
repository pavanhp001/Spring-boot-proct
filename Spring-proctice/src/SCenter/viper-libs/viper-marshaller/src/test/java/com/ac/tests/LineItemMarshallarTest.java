package com.ac.tests;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.Test;

import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.vm.util.converter.marshall.MarshallFeatureValue;
import com.A.vm.util.converter.marshall.MarshallLineItem;
import com.A.vm.util.converter.unmarshall.UnmarshallLineItem;
import com.A.vm.util.converter.unmarshall.UnmarshallSelectedFeatureValue;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderManagementRequestResponseDocument;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
public class LineItemMarshallarTest{

	private String xml;

	private UnmarshallLineItem unmarshallLineItem;

	private MarshallLineItem marshallLineItem;

	private UnmarshallSelectedFeatureValue unmarshallSelFeature;


	private LineItem li = new LineItem();
	@Before
	public void setUp() throws Exception {
		unmarshallLineItem = new UnmarshallLineItem();
		marshallLineItem = new MarshallLineItem();

		//xml = BaseTest.getXMLFromFile("src\\test\\resources\\xml\\order.xml");
		xml = BaseTest.getXMLFromFile("src\\test\\resources\\xml\\88001.xml");
		assertNotNull(xml);

	}

	/**
	 * Validating lineitem attributes
	 * @throws XmlException
	 */
	@Test
	public void testLineItemAttributes() throws XmlException {
//		OrderManagementRequestResponseDocument oDoc = OrderManagementRequestResponseDocument.Factory.parse(xml) ;
//
//		assertNotNull(oDoc);
//		Request req = oDoc.getOrderManagementRequestResponse().getRequest();
//		assertNotNull(req);
//		LineItemType liType = req.getOrderInfo().getLineItems().getLineItemList().get(0);
//		assertNotNull(liType);
//		assertNotNull(liType.getLineItemAttributes());
//
//		//Unmrshall from xml to lineitem attribute
//
//		unmarshallLineItem.copyLineItemAttributes(li, liType, false);
//		assertNotNull(li.getLineItemAttribute());
//		assert(li.getLineItemAttribute().size() > 0);
//		for(LineItemAttribute liAttrib : li.getLineItemAttribute()){
//			System.out.println(liAttrib.toString());
//		}
//
//
//		// Marshall back to xml
//		LineItemType destLIType = LineItemType.Factory.newInstance();
//		marshallLineItem.copyLineItemAttributes(li, destLIType);
//		System.out.println("Marshalled info : \n" + destLIType);
	}


	@Test
	public void testSelFeatures() throws XmlException {
		OrderManagementRequestResponseDocument oDoc = OrderManagementRequestResponseDocument.Factory.parse(xml) ;

		assertNotNull(oDoc);
		Request req = oDoc.getOrderManagementRequestResponse().getRequest();
		assertNotNull(req);
		LineItemType liType = req.getOrderInfo().getLineItems().getLineItemList().get(0);
		assertNotNull(liType);
		assertNotNull(liType.getSelectedFeatures());

		//Unmrshall from xml to lineitem attribute

		List<SelectedFeatureValue> features = UnmarshallSelectedFeatureValue.copySelectedFeatureValues(li, liType);
		assertNotNull(features);
		assert(features.size() > 0);
		Set<SelectedFeatureValue> featureSet = new HashSet<SelectedFeatureValue>();
		for(SelectedFeatureValue feature : features){
			featureSet.add(feature);
		}
		li.setSelectedFeatureValues(featureSet);

		// Marshall back to xml
		LineItemType destLIType = LineItemType.Factory.newInstance();
		MarshallFeatureValue.INSTANCE.copySelectedFeatureValue(li,destLIType,null);
		System.out.println(destLIType);
	}

}
