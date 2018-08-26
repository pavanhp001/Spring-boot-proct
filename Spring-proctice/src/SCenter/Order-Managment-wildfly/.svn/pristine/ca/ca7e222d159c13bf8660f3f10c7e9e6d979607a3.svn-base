package com.AL.ome.marshall;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.V.beans.entity.CustomSelection;
import com.AL.V.beans.entity.LineItem;
import com.AL.vm.util.converter.marshall.MarshallCustomSelection;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class MarshallCustomSelectionTest
{
private LineItem lineItem;
	
	OrderManagementRequestResponseDocument order = null;
	Request request = null;
	OrderType orderInfoType = null;
	LineItemType destLineItemType = null;
	
	@Before
	public void setUp() {
		lineItem = new LineItem();
		prepareCustomSelectionData();
		
		order = OrderManagementRequestResponseDocument.Factory.newInstance();
		request = order.addNewOrderManagementRequestResponse().addNewRequest();
		orderInfoType = request.addNewOrderInfo();
		destLineItemType = orderInfoType.addNewLineItems().addNewLineItem();
		
	}

	private void prepareCustomSelectionData()
	{
		Set<CustomSelection> selections = new HashSet<CustomSelection>();
		
		CustomSelection s1 = new CustomSelection();
		s1.setSelectionExtId( "OptionalCablePackages" );
		s1.setParentChoiceExtId( null );
		s1.setChoiceExtId( "HBO" );
		//sel1.setChoiceDetail( "HBO Channel" );

		CustomSelection s2 = new CustomSelection();
		s2.setSelectionExtId( "OptionalCablePackages" );
		s2.setParentChoiceExtId( null );
		s2.setChoiceExtId( "Cinemax" );
		//sel2.setChoiceDetail( "Cinemax Channel" );
		
		CustomSelection s3 = new CustomSelection();
		s3.setSelectionExtId( "OptionalCablePackages" );
		s3.setParentChoiceExtId( null );
		s3.setChoiceExtId( "Starz" );
		//sel3.setChoiceDetail( "Starz Channel" );
		
		selections.add( s1 );
		selections.add( s2 );
		selections.add( s3 );
		
		CustomSelection sel1 = new CustomSelection();
		sel1.setSelectionExtId( "PortYourNumber" );
		sel1.setParentChoiceExtId( null );
		sel1.setChoiceExtId( "KeepMyCurrentNumber" );
		sel1.setChoiceDetail( "" );

		CustomSelection sel2 = new CustomSelection();
		sel2.setSelectionExtId( "NameOnAccount" );
		sel2.setParentChoiceExtId( "KeepMyCurrentNumber" );
		sel2.setChoiceExtId( "name" );
		sel2.setChoiceDetail( "Pritesh Patel" );
		
		CustomSelection sel3 = new CustomSelection();
		sel3.setSelectionExtId( "AddressLineOne" );
		sel3.setParentChoiceExtId( "KeepMyCurrentNumber" );
		sel3.setChoiceExtId( "lineOne" );
		sel3.setChoiceDetail( "30 E Lawn Way" );
		
		CustomSelection sel4 = new CustomSelection();
		sel4.setSelectionExtId( "AddressCity" );
		sel4.setParentChoiceExtId( "KeepMyCurrentNumber" );
		sel4.setChoiceExtId( "city" );
		sel4.setChoiceDetail( "Covington" );
		
		CustomSelection sel5 = new CustomSelection();
		sel5.setSelectionExtId( "AddressState" );
		sel5.setParentChoiceExtId( "KeepMyCurrentNumber" );
		sel5.setChoiceExtId( "state" );
		sel5.setChoiceDetail( "GA" );
		
		
		CustomSelection sel6 = new CustomSelection();
		sel6.setSelectionExtId( "ModemProfessionalOrSelfInstall" );
		sel6.setParentChoiceExtId( null );
		sel6.setChoiceExtId( "SelfInstall" );
		sel6.setChoiceDetail( "Opted for self installation" );
		
		
		selections.add( sel1 );
		selections.add( sel2 );
		selections.add( sel3 );
		selections.add( sel4 );
		selections.add( sel5 );
		selections.add( sel6 );
		
		lineItem.setSelections( selections );
	}
	
	@Test
	public void testSelections(){
		assertNotNull(lineItem);
		assertNotNull(lineItem.getSelections());
		assertNotNull(destLineItemType);
		MarshallCustomSelection.buildCustomSelection( lineItem, destLineItemType );
		System.out.println(destLineItemType.toString());
	}
}
