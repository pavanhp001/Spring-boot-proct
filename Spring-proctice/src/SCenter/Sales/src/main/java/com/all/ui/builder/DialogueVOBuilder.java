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
import com.AL.ui.vo.SalesDialogueVO;

/**
 * @author ganesh
 *
 */
public class DialogueVOBuilder {
	
	public static SalesDialogueVO buildDialogues(List<Dialogue> dialogueList, SalesDialogueVO dialogueVO,String productId){
		Map<String, Map<String,List<DataField>>> dialogueFieldMap = new HashMap<String, Map<String,List<DataField>>>();
		Map<String, Map<String, List<DataFieldMatrix>>> dialogueFieldMatrixMap = new HashMap
			<String, Map<String, List<DataFieldMatrix>>>();
		if(dialogueList != null){
			for(Dialogue dialogue : dialogueList){
				//DataField
				dialogueFieldMap.put(dialogue.getExternalId(), buildDataGroupFieldMap(dialogueFieldMap,dialogue));
				//DataFieldMatrix
				dialogueFieldMatrixMap.put(dialogue.getExternalId(), buildDataGroupFieldMatrixMap(dialogueFieldMatrixMap,dialogue));
			}
		}
		dialogueVO.setProductId(productId);
		dialogueVO.setDataFieldMap(dialogueFieldMap);
		dialogueVO.setDataFieldMatrixMap(dialogueFieldMatrixMap);

		return dialogueVO;
	}
	
	private static Map<String, List<DataFieldMatrix>> buildDataGroupFieldMatrixMap(Map<String, Map<String, 
				List<DataFieldMatrix>>> dialogueFieldMatrixMap, Dialogue dialogue) {
		Map<String, List<DataFieldMatrix>> matrixMap = new HashMap<String, List<DataFieldMatrix>>();
		for(DataGroup dataGroup : dialogue.getDataGroupList()){
			//for each data group build the map
			//map contains the group name and the list of data-field matrix
			matrixMap.put(dataGroup.getName(), dataGroup.getDataFieldMatrixList());
		}
		return matrixMap;
	}

	private static Map<String, List<DataField>> buildDataGroupFieldMap(Map<String, Map<String, 
				List<DataField>>> dialogueMap, Dialogue dialogue) {
		Map<String, List<DataField>> fieldMap = new HashMap<String, List<DataField>>();
		for(DataGroup dataGroup : dialogue.getDataGroupList()){
			//for each data group build the map
			//map contains the group name and the list of data-fields
			fieldMap.put(dataGroup.getName(), dataGroup.getDataFieldList());
		}
		return fieldMap;
	}

	public static SalesDialogueVO buildDialogues(List<Dialogue> dialogueList, SalesDialogueVO dialogueVO){
		Map<String, Map<String,List<DataField>>> dialogueFieldMap = new HashMap<String, Map<String,List<DataField>>>();
		Map<String, Map<String, List<DataFieldMatrix>>> dialogueFieldMatrixMap = new HashMap
			<String, Map<String, List<DataFieldMatrix>>>();
		if(dialogueList != null){
			for(Dialogue dialogue : dialogueList){
				//DataField
				dialogueFieldMap.put(dialogue.getExternalId(), buildDataGroupFieldMap(dialogueFieldMap,dialogue));
				//DataFieldMatrix
				dialogueFieldMatrixMap.put(dialogue.getExternalId(), buildDataGroupFieldMatrixMap(dialogueFieldMatrixMap,dialogue));
			}
		}
		dialogueVO.setDataFieldMap(dialogueFieldMap);
		dialogueVO.setDataFieldMatrixMap(dialogueFieldMatrixMap);
		dialogueVO.setDialogueList(dialogueList);
		return dialogueVO;
	}

}
