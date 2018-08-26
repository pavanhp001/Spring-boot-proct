package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class StringConstraint {
    private Integer length;
    private List<String> listOfValues;
    private String value;
    private String defaultValue;
    private Float comparableValue;
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public List<String> getListOfValues() {
		if (listOfValues == null) {
			listOfValues = new ArrayList<String>();
		}
		return listOfValues;
	}
	public void setListOfValues(List<String> listOfValues) {
		this.listOfValues = listOfValues;
	}
	public void addValue(String arg) {
		getListOfValues().add(arg);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Float getComparableValue() {
		return comparableValue;
	}
	public void setComparableValue(Float comparableValue) {
		this.comparableValue = comparableValue;
	}
}
