package com.AL.ui.vo;

import java.io.Serializable;
import java.util.Map;

import com.AL.xml.pr.v4.ProductPromotionType;

public class PromotionVO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductPromotionType promotionObj;
	private Map<String,String> displayInputValue;
	private String displayInputType;
	private boolean isChecked;
	private boolean baseMonthlyPromo;

	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public ProductPromotionType getPromotionObj() {
		return promotionObj;
	}
	public void setPromotionObj(ProductPromotionType promotionObj) {
		this.promotionObj = promotionObj;
	}
	public Map<String, String> getDisplayInputValue() {
		return displayInputValue;
	}
	public void setDisplayInputValue(Map<String, String> displayInputValue) {
		this.displayInputValue = displayInputValue;
	}
	public String getDisplayInputType() {
		return displayInputType;
	}
	public void setDisplayInputType(String displayInputType) {
		this.displayInputType = displayInputType;
	}
	public boolean isBaseMonthlyPromo() {
		return baseMonthlyPromo;
	}
	public void setBaseMonthlyPromo(boolean baseMonthlyPromo) {
		this.baseMonthlyPromo = baseMonthlyPromo;
	}
	@Override
	public String toString() {
		return "PromotionVO [promotionObj=" + promotionObj
		+ ", displayInputValue=" + displayInputValue
		+ ", displayInputType=" + displayInputType + ", isChecked="
		+ isChecked + "]";
	}
}
