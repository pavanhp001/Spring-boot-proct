package com.AL.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.AL.xml.pr.v4.CustomizationType;
import com.AL.xml.v4.LineItemSelectionType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.SelectionChoiceType;
import com.AL.xml.pr.v4.ChoiceType;


/**
 * The Class OrderQualRequestBuilder.
 */
public class LineItemSelectionBuilder {	
	/**
	 * This method maps selected customization and choices
	 * to LineItemSelectionsType
	 *
	 * @param customizationSelectionsMap the customization selections
	 * <"CustomizationId", "choiceId1, choiceId2"> or 
	 * <"choiceId", "Answer to choice">
	 * @param customizations, all the customization of the product
	 * @return the line item selections
	 */
	public static LineItemSelectionsType getLineItemSelections(Map<String, String> 
			customizationSelectionsMap, List<CustomizationType> customizations) {
		/*
		 * This map will contain <customizationId, parentChoiceId> 
		 * for all customizations
		 */
		Map<String, String> customizationMap = new HashMap<String, String>();
		/*
		 * This map will contain <choiceId, parentCustomizationId> for all choices
		 */
		Map<String, String> choiceMap = new HashMap<String, String>();
		toCustomizationAndChoiceMap(customizationMap, choiceMap, 
				customizations, null);
		LineItemSelectionsType lineItemSelections = new LineItemSelectionsType();
		for(Map.Entry<String, String> e: customizationSelectionsMap.entrySet()) {
			if(customizationMap.containsKey(e.getKey())) {
				String parentChoiceId = customizationMap.get(e.getKey());
				//Check is the parent choice is selected or not
				if(!StringUtils.isEmpty(parentChoiceId)) {
					if(!isValidSelection(customizationSelectionsMap, parentChoiceId))
						continue;
				}
				LineItemSelectionType lineItemSelection = new LineItemSelectionType();
				lineItemSelection.setExternalId(e.getKey());
				String choices = e.getValue();
				if(choices != null) {
					String[] choiceList = choices.split(",");
					for(String choiceId : choiceList) {
						if(!StringUtils.isEmpty(choiceId)){
							SelectionChoiceType selectionType = new SelectionChoiceType();
							selectionType.setExternalId(choiceId);
							lineItemSelection.getSelectionChoice().add(selectionType);
						}
					}
				}
				if(parentChoiceId != null) {
					lineItemSelection.setParentChoiceExternalId(parentChoiceId);
				}
				lineItemSelections.getSelection().add(lineItemSelection);
			} else if(choiceMap.containsKey(e.getKey())) {
				String selectionExternalId = choiceMap.get(e.getKey());
				String parentChoiceId = customizationMap.get(selectionExternalId);
				//Check is the parent choice is selected or not
				if(!StringUtils.isEmpty(parentChoiceId)) {
					if(!isValidSelection(customizationSelectionsMap, parentChoiceId))
						continue;
				}
				if(!StringUtils.isEmpty(e.getValue())) {
					LineItemSelectionType lineItemSelection = null;
					/*
					 * Check if the lineItemSelection is already created
					 * if yes then add choice to that
					 * if no then create lineItemSelection and add choice to it
					 */
					for(LineItemSelectionType selection : lineItemSelections.getSelection()) {
						if(selection.getExternalId().equalsIgnoreCase(selectionExternalId)) {
							lineItemSelection = selection;
						}
					}
					if(lineItemSelection == null) {
						lineItemSelection = new LineItemSelectionType();
					}
					lineItemSelection.setExternalId(selectionExternalId);
					if(parentChoiceId != null) {
						lineItemSelection.setParentChoiceExternalId(parentChoiceId);
					}
					SelectionChoiceType selectionType = new SelectionChoiceType();
					selectionType.setExternalId(e.getKey());
					selectionType.setDetail(e.getValue());
					lineItemSelection.getSelectionChoice().add(selectionType);
					lineItemSelections.getSelection().add(lineItemSelection);
				}
			}
		}
		return lineItemSelections;
	}
	
	/*
	 * This method checks if a nested selection is valid.
	 * returns true if parent choiceId is selected
	 * returns false if parent choiceId is not selected
	 */
	private static boolean isValidSelection(Map<String, String> customizationSelectionsMap, 
			String parentChoiceId) {
		boolean isValidSelection = false; 
		List<String> choices = new ArrayList<String>(customizationSelectionsMap.values());
		if(choices.contains(parentChoiceId)) {
			isValidSelection = true;
		}
		return isValidSelection;
	}
	/**
	 * This method creates map of all customizations  and choices
	 * @param customizationMap the customization map
	 * <CustomizationId, parentChoiceId>
	 * @param choiceMap the choice map
	 * <choiceId, parentCustomizationId>
	 * @param cusmomizations the cusmomizations
	 * @param parentChoiceId the parent choice id
	 */
	private static void toCustomizationAndChoiceMap(Map<String, String> customizationMap, 
			Map<String, String> choiceMap, List<CustomizationType> cusmomizations, String parentChoiceId) {
		for(CustomizationType customization : cusmomizations) {
			customizationMap.put(customization.getExternalId(), parentChoiceId);
			for(ChoiceType choice : customization.getOptions().getChoice()) {
				choiceMap.put(choice.getExternalId(), customization.getExternalId());
				if(choice.getCustomization() != null) {
					toCustomizationAndChoiceMap(customizationMap, choiceMap, choice.getCustomization(), 
							choice.getExternalId());
				}
			}
		}
	}
}
