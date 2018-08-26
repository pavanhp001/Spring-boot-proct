/**
 * 
 */
package com.A.ui.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.A.ui.util.Utils;


/**
 * @author 
 *
 */
public class SalesCenterVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		if(!Utils.isBlank(input)) {
		//for each name in the scMap
		// replaceAll "{"+name+"}" with value i.e scMap.get(name)
		for(String name: scMap.keySet()) {
			String formattedName = "{"+name+"}";
			if(input.contains(formattedName)){
				input = input.replaceAll(name, scMap.get(name));
			}
		}
		input = input.replaceAll("\\{", "").replaceAll("\\}", "");
		return input;
		}
		return "";
	}
	
}
