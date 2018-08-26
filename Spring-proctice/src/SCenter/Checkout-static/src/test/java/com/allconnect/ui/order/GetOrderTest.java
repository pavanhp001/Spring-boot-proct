package com.AL.ui.order;

import static org.junit.Assert.assertNotNull;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;

import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.xml.v4.CustomSelectionsType;
import com.AL.xml.v4.DateTimeOrTimeRangeType;
import com.AL.xml.v4.LineItemSelectionType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SchedulingInfoType;
import com.AL.xml.v4.SelectionChoiceType;

public class GetOrderTest {

	@Test
	public void testGetOrderJMS() throws Exception {

		OrderType order = OrderService.INSTANCE.getOrderByOrderNumber("17602");

		assertNotNull(order);

	}
	
	private static DatatypeFactory df = null;
	static {
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException dce) {
			throw new IllegalStateException(
					"Exception while obtaining DatatypeFactory instance", dce);
		}
	}
	
	@Test
	public void testUpdateScheduleInfo() {
		LineItemType lineItem = new LineItemType();
		lineItem.setExternalId(17761L);
		lineItem.setLeadId(999L);
		SchedulingInfoType info = new SchedulingInfoType();
		DateTimeOrTimeRangeType dateTime = new DateTimeOrTimeRangeType();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(System.currentTimeMillis());
		dateTime.setDate(df.newXMLGregorianCalendar(gc));
		dateTime.setStartTime(df.newXMLGregorianCalendar(gc));
		dateTime.setEndTime(df.newXMLGregorianCalendar(gc));
		dateTime.setTime(df.newXMLGregorianCalendar(gc));
		info.setDesiredStartDate(dateTime);
		lineItem.setSchedulingInfo(info);
		OrderServiceUI.INSTANCE.updateLineItem("17197", lineItem);
	}
	
	@Test
	public void testUpdateLineItemSelections() {
		LineItemType lineItem = new LineItemType();
		lineItem.setExternalId(18171L);
		lineItem.setLeadId(999L);
		CustomSelectionsType selections = new CustomSelectionsType();
		selections.setSelections(getLinieItemSelections());
		lineItem.setCustomSelections(selections);
		OrderServiceUI.INSTANCE.updateLineItem("17602", lineItem);
	}
	
	public static LineItemSelectionsType getLinieItemSelections() {
		LineItemSelectionsType selectedCustomizations = new LineItemSelectionsType();
		LineItemSelectionType lineItemSelection = new LineItemSelectionType();
		/*lineItemSelection.setExternalId("ATT-VID-PG-11");
		SelectionChoiceType selectionType = new SelectionChoiceType();
		selectionType.setExternalId("ATT-SHOWUNL");
		lineItemSelection.getSelectionChoice().add(selectionType);
		selectionType = new SelectionChoiceType();
		selectionType.setExternalId("ATT-SOCCER");
		lineItemSelection.getSelectionChoice().add(selectionType);
		selectedCustomizations.getSelection().add(lineItemSelection);
		lineItemSelection = new LineItemSelectionType();
		lineItemSelection.setExternalId("VOIP-SEC-LINE");
		selectionType = new SelectionChoiceType();
		selectionType.setExternalId("VOIPSECLINE-Y");
		lineItemSelection.getSelectionChoice().add(selectionType);
		selectedCustomizations.getSelection().add(lineItemSelection);
		lineItemSelection = new LineItemSelectionType();
		lineItemSelection.setExternalId("VOIP-DIR-LIST2");
		selectionType = new SelectionChoiceType();
		selectionType.setExternalId("V-UNPUB");
		lineItemSelection.getSelectionChoice().add(selectionType);
		selectedCustomizations.getSelection().add(lineItemSelection);
		lineItemSelection = new LineItemSelectionType();
		lineItemSelection.setExternalId("VOIP-PORT-1");
		selectionType = new SelectionChoiceType();
		selectionType.setExternalId("VOIP-PORT-1-ATT");
		lineItemSelection.getSelectionChoice().add(selectionType);
		selectedCustomizations.getSelection().add(lineItemSelection);
		//Create line Item and choice
		lineItemSelection = new LineItemSelectionType();
		lineItemSelection.setExternalId("VOIP-PORT-2");
		selectionType = new SelectionChoiceType();
		selectionType.setExternalId("VOIP-PORT-2-NUMBER");
		selectionType.setDetail("404-000-0000");
		lineItemSelection.getSelectionChoice().add(selectionType);
		selectedCustomizations.getSelection().add(lineItemSelection);
		StringBuilder sb = new StringBuilder();
		for(LineItemSelectionType selection : selectedCustomizations.getSelection()) {
			sb.append("\ncustomizationId = "+selection.getExternalId());
			sb.append("\n  parentChoiceId = "+selection.getParentChoiceExternalId());
			for(SelectionChoiceType choice : selection.getSelectionChoice()) {
				sb.append("\n     choiceId = "+choice.getExternalId());
			}
		}
		System.out.println(sb.toString());*/
		return selectedCustomizations;
	}
}
