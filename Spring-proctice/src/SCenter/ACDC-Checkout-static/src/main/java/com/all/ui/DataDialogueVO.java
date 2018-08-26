package com.AL.ui;

import java.util.List;

public class DataDialogueVO {

	private String title;
	
	private List<DataGroupVO> dataGroupList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<DataGroupVO> getDataGroupList() {
		return dataGroupList;
	}

	public void setDataGroupList(List<DataGroupVO> dataGroupList) {
		this.dataGroupList = dataGroupList;
	}

	@Override
	public String toString() {
		return "DataDialogueVO [title=" + title + ", dataGroupList="
				+ dataGroupList + "]";
	}
	
	
	
	
}
