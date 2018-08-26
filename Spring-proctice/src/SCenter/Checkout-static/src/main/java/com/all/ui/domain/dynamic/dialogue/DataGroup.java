package com.AL.ui.domain.dynamic.dialogue;

import java.util.ArrayList;
import java.util.List;

public class DataGroup {
	private String name;
	private String displayName;
	private List<DialogueTag> tags;
	private List<DataField> dataFieldList;
    private List<DataFieldMatrix> dataFieldMatrixList;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public List<DialogueTag> getTags() {
		if (tags == null) {
			tags = new ArrayList<DialogueTag>();
		}
		return tags;
	}
	public void setTags(List<DialogueTag> tags) {
		this.tags = tags;
	}
	public void addTag(DialogueTag dt) {
		getTags().add(dt);
	}
	public List<DataField> getDataFieldList() {
		if (dataFieldList == null) {
			dataFieldList = new ArrayList<DataField>();
		}
		return dataFieldList;
	}
	public void setDataFieldList(List<DataField> argDataFieldList) {
		this.dataFieldList = argDataFieldList;
	}
	public void addDataField(DataField arg) {
		getDataFieldList().add(arg);
	}
	public List<DataFieldMatrix> getDataFieldMatrixList() {
		if (dataFieldMatrixList == null) {
			dataFieldMatrixList = new ArrayList<DataFieldMatrix>();
		}
		return dataFieldMatrixList;
	}
	public void setDataFieldMatrixist(List<DataFieldMatrix> argDataFieldMatrixList) {
		this.dataFieldMatrixList = argDataFieldMatrixList;
	}
/*	public void addDataFieldDependency(DataFieldMatrix arg) {
		getDataFieldMatrixList().add(arg);
	}*/
	public void addDataFieldDependency(DataFieldDependency arg) {
		for(DataFieldMatrix dfm: getDataFieldMatrixList()){
			dfm.getDependencyList().add(arg);
		}
		//getDataFieldMatrixList().add(arg);
	}	
	
}
