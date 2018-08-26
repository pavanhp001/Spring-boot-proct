package com.AL.ui.domain.dynamic.dialogue;

import java.util.ArrayList;
import java.util.List;

public class DataFieldDependency {
    private String externalId;
    private List<DependentDataFields> enabledDataFields;
    private String parentDataFieldType; //"text" or "feature"
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public List<DependentDataFields> getEnabledDataFields() {
		if (enabledDataFields == null) {
			enabledDataFields = new ArrayList<DependentDataFields>();
		}
		return enabledDataFields;
	}
	public void setEnabledDataFields(List<DependentDataFields> enabledDataFields) {
		this.enabledDataFields = enabledDataFields;
	}
	public void addEnabledDataField(DependentDataFields arg) {
		getEnabledDataFields().add(arg);
	}
	public String getParentDataFieldType() {
		return parentDataFieldType;
	}
	public void setParentDataFieldType(String parentDataFieldType) {
		this.parentDataFieldType = parentDataFieldType;
	}
}
