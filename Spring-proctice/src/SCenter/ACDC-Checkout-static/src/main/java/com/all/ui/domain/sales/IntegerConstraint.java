package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class IntegerConstraint {
    private List<Integer> listOfValues;
    private Integer value;
    private Integer minValue;
    private Integer maxValue;
    private Integer _default;
    private Boolean unlimited;
    private String unit;
	public List<Integer> getListOfValues() {
		if (listOfValues == null) {
			listOfValues = new ArrayList<Integer>();
		}
		return listOfValues;
	}
	public void setListOfValues(List<Integer> listOfValues) {
		this.listOfValues = listOfValues;
	}
	public void addValue(Integer vi) {
		getListOfValues().add(vi);
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getMinValue() {
		return minValue;
	}
	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}
	public Integer getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	public Integer get_default() {
		return _default;
	}
	public void set_default(Integer _default) {
		this._default = _default;
	}
	public Boolean getUnlimited() {
		return unlimited;
	}
	public void setUnlimited(Boolean unlimited) {
		this.unlimited = unlimited;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
