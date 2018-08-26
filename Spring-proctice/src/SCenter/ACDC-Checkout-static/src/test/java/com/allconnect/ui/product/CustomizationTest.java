package com.AL.ui.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.AL.html.Fieldset;
import com.AL.ui.builder.LineItemSelectionBuilder;
import com.AL.ui.builder.OrderQualVOBuilder;
import com.AL.ui.vo.OrderQualVO;
import com.AL.V.gateway.util.JaxbUtil;
import com.AL.xml.v4.LineItemSelectionType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.SelectionChoiceType;


public class CustomizationTest {
	
	@Test
	public void testListCustomization() {
		Map<String, String> customizations = new HashMap<String, String>();
		customizations.put("SSN", "1234");
		customizations.put("ATT-VID-PG-11", "38-630-FREE3,ATT-HBO,ATT-ENCORE,ATT-SHOWUNL,37-647");
		//customizations.put("VOIP-SEC-LINE", "VOIPSECLINE-Y");
		customizations.put("VOIP-DIR-LIST2", "CHO_08");
		customizations.put("VOIP-DIR-LIST", "V-UNLIST");
		customizations.put("ATT-VID-HW-1", "ATT9-5");
		//customizations.put("VOIP-PORT-1", "VOIP-PORT-1-Y");
		customizations.put("VOIP-PORT-4-ZIP", "30310");
		customizations.put("VOIP-PORT-4-STATE", "GA");
		customizations.put("VOIP-PORT-4-CITY", "Atlanta");
		customizations.put("VOIP-PORT-4-LINE1", "2770 drew valley");
		customizations.put("VOIP-PORT-3-NAME", "Pankaj");
		customizations.put("VOIP-PORT-2-NUMBER", "1234556789");
		LineItemSelectionsType lineItemSelections = LineItemSelectionBuilder.getLineItemSelections(customizations, null);
		for(LineItemSelectionType selection : lineItemSelections.getSelection()) {
			System.out.println("customizationId = "+selection.getExternalId());
			System.out.println("  parentChoiceId = "+selection.getParentChoiceExternalId());
			for(SelectionChoiceType choice : selection.getSelectionChoice()) {
				System.out.println("     choiceId = "+choice.getExternalId());
			}
		}
		//String s = util.toCleanString(lineItemSelections, "ns2", LineItemSelectionsType.class);
		//System.out.println(s);
	}
	
	@Test
	public void testCustomerInfo() {
		Map<String, String> customizations = new HashMap<String, String>();
		customizations.put("ssn", "1234");
		customizations.put("ATT-VID-PG-11", "38-630-FREE3,ATT-HBO,ATT-ENCORE,ATT-SHOWUNL,37-647");
		customizations.put("dob", "09/12/1981");
		customizations.put("securityPin", "1234");
		OrderQualVO vo = OrderQualVOBuilder.buildOrderQualVO(customizations, null);
		//String s = util.toCleanString(lineItemSelections, "ns2", LineItemSelectionsType.class);
		System.out.println(vo.getSsn());
	}
	
	public static LineItemSelectionsType getPreselectedCustomizations() {
		LineItemSelectionsType selectedCustomizations = new LineItemSelectionsType();
		LineItemSelectionType lineItemSelection = new LineItemSelectionType();
		lineItemSelection.setExternalId("ATT-VID-PG-11");
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
		/*lineItemSelection = new LineItemSelectionType();
		lineItemSelection.setExternalId("VOIP-PORT-2");
		selectionType = new SelectionChoiceType();
		selectionType.setExternalId("VOIP-PORT-2-NUMBER");
		selectionType.setDetail("404-000-0000");
		lineItemSelection.getSelectionChoice().add(selectionType);
		selectedCustomizations.getSelection().add(lineItemSelection);*/
		StringBuilder sb = new StringBuilder();
		for(LineItemSelectionType selection : selectedCustomizations.getSelection()) {
			sb.append("\ncustomizationId = "+selection.getExternalId());
			sb.append("\n  parentChoiceId = "+selection.getParentChoiceExternalId());
			for(SelectionChoiceType choice : selection.getSelectionChoice()) {
				sb.append("\n     choiceId = "+choice.getExternalId());
			}
		}
		System.out.println(sb.toString());
		return selectedCustomizations;
	}


}
