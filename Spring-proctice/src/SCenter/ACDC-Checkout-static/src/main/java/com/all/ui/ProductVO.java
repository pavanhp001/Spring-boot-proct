package com.AL.ui;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.AL.ui.vo.PromotionVO;
import com.AL.xml.di.v4.DataFieldRefType;

public class ProductVO implements Serializable{
	private String productName;
	private String providerID;
	private List<DataDialogueVO> dialogueTypeList;
	private List<PromotionVO> promotionVOList;
	private Map<String,Map<String,List<DataFieldRefType>>> dataFieldMatrixMap;


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProviderID() {
		return providerID;
	}

	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}

	public List<DataDialogueVO> getDialogueTypeList() {
		return dialogueTypeList;
	}

	public void setDialogueTypeList(List<DataDialogueVO> dialogueTypeList) {
		this.dialogueTypeList = dialogueTypeList;
	}

	public List<PromotionVO> getPromotionVOList() {
		return promotionVOList;
	}

	public void setPromotionVOList(List<PromotionVO> promotionVOList) {
		this.promotionVOList = promotionVOList;
	}

	public Map<String, Map<String, List<DataFieldRefType>>> getDataFieldMatrixMap() {
		return dataFieldMatrixMap;
	}

	public void setDataFieldMatrixMap(
			Map<String, Map<String, List<DataFieldRefType>>> dataFieldMatrixMap) {
		this.dataFieldMatrixMap = dataFieldMatrixMap;
	}

	@Override
	public String toString() {
		return "ProductVO [productName=" + productName + ", providerID="
				+ providerID + ", dialogueTypeList=" + dialogueTypeList
				+ ", promotionVOList=" + promotionVOList
				+ ", dataFieldMatrixMap=" + dataFieldMatrixMap + "]";
	}



}
