/**
 * 
 */
package com.AL.ui.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataFieldMatrix;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.vo.DialogueVO;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueListType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.di.v4.DialogueType.DataGroupList;


public class DialogueVOBuilder {
	
	/**
	 * converts the List<Dialogues> to dialogueVO object
	 * @param dialogueList
	 * @param dialogueVO
	 * @param productId
	 * @return DialogueVO
	 */
	public static DialogueVO buildDialogues(List<Dialogue> dialogueList, DialogueVO dialogueVO,String productId){
		
		Map<String, Map<String,List<DataField>>> dialogueFieldMap = new HashMap<String, Map<String,List<DataField>>>();
		
		Map<String, Map<String, List<DataFieldMatrix>>> dialogueFieldMatrixMap = new HashMap <String, Map<String, List<DataFieldMatrix>>>();
		
		if(dialogueList != null){
			for(Dialogue dialogue : dialogueList){
				
				/**
				 * creates Map with DialogueExternalID as Key and Map<String, List<DataField>> as Value
				 * based on this map, we build the dialogues display
				 */
				
				dialogueFieldMap.put(dialogue.getExternalId(), buildDataGroupFieldMap(dialogueFieldMap,dialogue));
				
				/**
				 * creates Map with DialogueExternalID as Key and Map<String, List<DataFieldMatrixList>> as Value
				 * based on this map, we create the dependencies of dialogues over other
				 */
				
				dialogueFieldMatrixMap.put(dialogue.getExternalId(), buildDataGroupFieldMatrixMap(dialogueFieldMatrixMap,dialogue));
			}
		}
		dialogueVO.setProductId(productId);
		dialogueVO.setDataFieldMap(dialogueFieldMap);
		dialogueVO.setDataFieldMatrixMap(dialogueFieldMatrixMap);

		return dialogueVO;
	}
	
	/**
	 * create a Map<String, List<DataFieldMatrix>> with DataGroupName as key and List<DataFieldMatrix> as Value
	 * @param dialogueFieldMatrixMap
	 * @param dialogue
	 * @return Map<String, List<DataFieldMatrix>>
	 */
	private static Map<String, List<DataFieldMatrix>> buildDataGroupFieldMatrixMap(Map<String, Map<String, 
				List<DataFieldMatrix>>> dialogueFieldMatrixMap, Dialogue dialogue) {
		Map<String, List<DataFieldMatrix>> matrixMap = new HashMap<String, List<DataFieldMatrix>>();
		
		/**
		 * iterate over the dataGroupList on the dialogue and 
		 * create a map with dataGroupName as Key and DataFieldMatrixList as Value
		 */
		
		for(DataGroup dataGroup : dialogue.getDataGroupList()){
			
			matrixMap.put(dataGroup.getName(), dataGroup.getDataFieldMatrixList());
		}
		return matrixMap;
	}

	/**
	 * create a Map<String, List<DataField>> with dataGroup Name as key and List<DataField> as Value
	 * @param dialogueMap
	 * @param dialogue
	 * @return Map<String, List<DataField>>
	 */
	private static Map<String, List<DataField>> buildDataGroupFieldMap(Map<String, Map<String, 
				List<DataField>>> dialogueMap, Dialogue dialogue) {
		Map<String, List<DataField>> fieldMap = new HashMap<String, List<DataField>>();
		
		/**
		 * iterate over the dataGroupList on the dialogue and 
		 * create a map with dataGroupName as Key and DataFieldList as Value
		 */
		
		for(DataGroup dataGroup : dialogue.getDataGroupList()){
			fieldMap.put(dataGroup.getName(), dataGroup.getDataFieldList());
		}
		return fieldMap;
	}
	
	/**
	 * Creates the DataFieldList
	 * 
	 * 
	 * @param dialogueResponseType
	 * @return
	 */
	public static DataFieldList buildDataFieldList(
			DialogueResponseType dialogueResponseType) {

		DataFieldList dataFieldList = new DataFieldList();

		if(dialogueResponseType !=null && dialogueResponseType.getResults()!=null){
			DialogueListType dialogueListType = dialogueResponseType.getResults().getDialogueList();
			for (DialogueType dialogueType : dialogueListType.getDialogue()){
				DataGroupList dataGroupList = dialogueType.getDataGroupList();
				for (DataGroupType dataGroupType :dataGroupList.getDataGroup()){
					dataFieldList.getDataField().addAll(dataGroupType.getDataFieldList().getDataField());
				}
			}
			return dataFieldList;
		}
		return null;
	}

	
}
