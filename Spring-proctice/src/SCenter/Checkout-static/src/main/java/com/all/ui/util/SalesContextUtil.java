package com.AL.ui.util;

import java.util.HashMap;
import java.util.Map;

public class SalesContextUtil {
	
	public static Map<String, Map<String, String>> getData() {

		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("channel", "web");

		context.put("orderSource", orderSource);
	 
		return context;
	}

}
