package com.AL.ui.vo;

import java.util.HashMap;
import java.util.Map;

public class StaticCKOVO {
	public Map<String, String> scMap = new HashMap<String, String>();


	/**
	 * @return
	 */
	public Map<String, String> getScMap() {
		return scMap;
	}

	/**
	 * @param scMap
	 */
	public void setScMap(Map<String, String> scMap) {
		this.scMap = scMap;
	}

	/**
	 * @param name
	 * @return
	 */
	public String getValueByName(String name){
		return scMap.get(name);
	}

	/**
	 * @param name
	 * @param value
	 */
	public void setValueByName(String name, String value){
		scMap.put(name, value);
	}

	/**
	 * @param input
	 * @return
	 */
	public String replaceNamesWithValues(String input) {
		//for each name in the scMap
		// replaceAll "{"+name+"}" with value i.e scMap.get(name)
		for(String name: scMap.keySet()) {
			String formatted = name;
			
			input = input.replaceAll(formatted, scMap.get(name));
		}
		input = input.replaceAll("\\{", "").replaceAll("\\}", "");
		return input;
	}
}
