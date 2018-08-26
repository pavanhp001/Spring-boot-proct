package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SelectedDialogue;
import com.AL.vm.util.converter.marshall.MarshallSelectedDialogue;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * JUnit testcase to test marshalling functionality of Active Dialogue
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class MarshallActiveDialoueTestF
{
	private LineItem lineItem;
	
	OrderManagementRequestResponseDocument order = null;
	Request request = null;
	OrderType orderInfoType = null;
	LineItemType destLineItemType = null;
	
	@Before
	public void setUp() {
		lineItem = new LineItem();
		prepareDialogueData();
		
		order = OrderManagementRequestResponseDocument.Factory.newInstance();
		request = order.addNewOrderManagementRequestResponse().addNewRequest();
		orderInfoType = request.addNewOrderInfo();
		destLineItemType = orderInfoType.addNewLineItems().addNewLineItem();
		
	}

	private void prepareDialogueData()
	{
		Set<SelectedDialogue> dlgs = new HashSet<SelectedDialogue>();
		
		SelectedDialogue dlg1 = new SelectedDialogue();
		dlg1.setExternalId( "DLG101" );
		dlg1.setSelected( true );
		dlg1.setType( "string" );
		dlg1.setValue( "Answer1" );
		dlg1.setDialogueDate( getDate() );
		dlgs.add( dlg1 );
		
		SelectedDialogue dlg2 = new SelectedDialogue();
		dlg2.setExternalId( "DLG102" );
		dlg2.setSelected( true );
		dlg2.setType( "string" );
		dlg2.setValue( "Answer2" );
		dlg2.setDialogueDate( getDate() );
		dlgs.add( dlg2 );
		
		SelectedDialogue dlg3 = new SelectedDialogue();
		dlg3.setExternalId( "DLG101" );
		dlg3.setSelected( true );
		dlg3.setType( "string" );
		dlg3.setValue( "Answer2" );
		dlg3.setDialogueDate( getDate() );
		dlgs.add( dlg3 );
		
		/*SelectedDialogue dlg3 = new SelectedDialogue();
		dlg3.setExternalId( "DLG102" );
		dlg3.setSelected( true );
		dlg3.setType( "string" );
		dlg3.setValue( "Dialogue2Answer1" );
		dlgs.add( dlg3 );
		
		SelectedDialogue dlg4 = new SelectedDialogue();
		dlg4.setExternalId( "DLG103" );
		dlg4.setSelected( true );
		dlg4.setType( "integer" );
		dlg4.setValue( "Dialogue3Answer1" );
		dlgs.add( dlg4 );
		
		SelectedDialogue dlg5 = new SelectedDialogue();
		dlg5.setExternalId( "DLG103" );
		dlg5.setSelected( false );
		dlg5.setType( "integer" );
		dlg5.setValue( "Dialogue3Answer2" );
		dlgs.add( dlg5 );*/
		
		
		lineItem.setDialogues( dlgs );
	}
	
	private Calendar getDate() {
		Date d = new Date();
		//System.out.println("Date : " + d.getTime());
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(d.getTime());
		System.out.println("Time : " + cal.getTimeInMillis()+1);
		return cal;
	}
	@Test
	public void testMarshallDialogue(){
		assertNotNull(lineItem);
		assertNotNull(lineItem.getDialogues());
		assertNotNull( destLineItemType );
		MarshallSelectedDialogue.buildSelectedDialogue( lineItem, destLineItemType );
		System.out.println(destLineItemType.toString());
	}
}
