package com.AL.ui.domain.dynamic.dialogue;

import java.util.ArrayList;
import java.util.List;

public class DependentDataFields {
    private String value;
    private List<String> dataFieldExternalIds;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<String> getDataFieldExternalIds() {
		if (dataFieldExternalIds == null) {
			dataFieldExternalIds = new ArrayList<String>();
		}
		return dataFieldExternalIds;
	}
	public void setDataFieldExternalIds(List<String> dataFieldExternalIds) {
		this.dataFieldExternalIds = dataFieldExternalIds;
	}
	
	public void addDataFieldExternalId(String id) {
		getDataFieldExternalIds().add(id);
	}
}
