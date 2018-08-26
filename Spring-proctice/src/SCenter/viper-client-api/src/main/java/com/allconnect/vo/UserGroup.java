package com.A.vo;

import java.util.Map;
import java.util.HashMap;


public class UserGroup {

	private Map<String, String> impactedAreas = new HashMap<String, String>();
	private String groupName;


	public Map<String, String> getImpactedAreas() {
		return impactedAreas;
	}

	public void setImpactedAreas(Map<String, String> impactedAreas) {
		this.impactedAreas = impactedAreas;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "UserGroup [name=" + groupName + "impactedareas=" + impactedAreas + "]";
	}
}
