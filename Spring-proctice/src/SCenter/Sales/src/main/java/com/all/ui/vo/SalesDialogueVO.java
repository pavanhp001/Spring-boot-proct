/**
 * 
 */
package com.AL.ui.vo;

import java.util.List;
import java.util.Map;

import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataFieldMatrix;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;


/**
 * @author ganesh
 *
 */
public class SalesDialogueVO {
	
	private String productId = null;
	
	private Map<String,Map<String, List<DataField>>> dataFieldMap = null;
	
	private Map<String, Map<String, List<DataFieldMatrix>>> dataFieldMatrixMap = null;
	
	private List<Dialogue> dialogueList = null;
	
	private List<DataGroup> dataGroupList = null;

	
	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the dataFieldMap
	 */
	public Map<String,Map<String, List<DataField>>> getDataFieldMap() {
		return dataFieldMap;
	}

	/**
	 * @param dataFieldMap the dataFieldMap to set
	 */
	public void setDataFieldMap(Map<String,Map<String, List<DataField>>> dataFieldMap) {
		this.dataFieldMap = dataFieldMap;
	}

	/**
	 * @return the dataFieldMatrixMap
	 */
	public Map<String, Map<String, List<DataFieldMatrix>>> getDataFieldMatrixMap() {
		return dataFieldMatrixMap;
	}

	/**
	 * @param dataFieldMatrixMap the dataFieldMatrixMap to set
	 */
	public void setDataFieldMatrixMap(Map<String, Map<String, List<DataFieldMatrix>>> dataFieldMatrixMap) {
		this.dataFieldMatrixMap = dataFieldMatrixMap;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("DialogueVO [");
		buff.append("productId = "+productId).append("\n");
		buff.append("dataFieldMap = "+dataFieldMap).append("\n");
		buff.append("dataFieldMatrixMap = "+dataFieldMatrixMap).append("\n");
		buff.append("]");
		return buff.toString();
	}

	public List<DataGroup> getDataGroupList() {
		return dataGroupList;
	}

	public void setDataGroupList(List<DataGroup> dataGroupList) {
		this.dataGroupList = dataGroupList;
	}

	public List<Dialogue> getDialogueList() {
		return dialogueList;
	}

	public void setDialogueList(List<Dialogue> dialogueList) {
		this.dialogueList = dialogueList;
	}
}
