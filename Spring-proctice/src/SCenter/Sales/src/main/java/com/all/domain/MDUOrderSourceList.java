package com.AL.domain;

import java.util.List;

import com.AL.ui.domain.MDUOrderSource;

public class MDUOrderSourceList {

	private List<MDUOrderSource> mduOrderSourceList;

	public List<MDUOrderSource> getMduOrderSourceList() {
		return mduOrderSourceList;
	}

	public void setMduOrderSourceList(List<MDUOrderSource> mduOrderSourceList) {
		this.mduOrderSourceList = mduOrderSourceList;
	}

	@Override
	public String toString() {
		return "MDUOrderSourceList [mduOrderSourceList=" + mduOrderSourceList
				+ "]";
	}
}
