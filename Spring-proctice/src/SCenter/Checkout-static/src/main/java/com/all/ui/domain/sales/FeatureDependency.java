package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class FeatureDependency {
	private List<String> dependencyExternalIdList;

	private String dependency;
	
	public List<String> getDependencyExternalIdList() {
		if (dependencyExternalIdList == null) {
			dependencyExternalIdList = new ArrayList<String>();
		}
		return dependencyExternalIdList;
	}
	
	public void setDependencyExternalIdList(List<String> dependencyExternalId) {
		this.dependencyExternalIdList = dependencyExternalId;
	}
	
	public void addDependency(String id) {
		getDependencyExternalIdList().add(id);
	}
	public String getDependency() {
		return dependency;
	}
	public void setDependency(String dependency) {
		this.dependency = dependency;
	}
}
