package com.A.V.mo.dialog;

import java.util.HashMap;
import java.util.Map;

 

public enum ProductMotherObject {

	INSTANCE;
	
	public Map<String, Map<String, String>> getData() {

		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("channel", "web");

	 

		context.put("orderSource", orderSource);
	 
		return context;
	}
}


//<ac:entity name="orderSource">
//<ac:attribute name="channel" value="web"/>
//</ac:entity>