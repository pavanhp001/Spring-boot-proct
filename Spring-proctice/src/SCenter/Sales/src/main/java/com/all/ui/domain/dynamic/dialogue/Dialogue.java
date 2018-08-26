package com.AL.ui.domain.dynamic.dialogue;

import java.util.ArrayList;
import java.util.List;

public class Dialogue {
    private String externalId;
    private String name;
    private List<DialogueTag> tags;
    private List<DataGroup> dataGroupList;
    
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public void addTag(DialogueTag tag) {
		getTags().add(tag);
	}
	public List<DataGroup> getDataGroupList() {
		if (dataGroupList == null) {
			dataGroupList = new ArrayList<DataGroup>();
		}
		return dataGroupList;
	}
	public void setDataGroupList(List<DataGroup> dataGroupList) {
		this.dataGroupList = dataGroupList;
	}
	public void addDataGroup(DataGroup dg) {
		getDataGroupList().add(dg);
	}
}
