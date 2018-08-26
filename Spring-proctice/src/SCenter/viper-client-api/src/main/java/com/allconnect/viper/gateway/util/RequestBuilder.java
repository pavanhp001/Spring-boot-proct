package com.A.V.gateway.util;

import java.util.HashMap;
import java.util.Map;

public enum RequestBuilder {

	INSTANCE; 
	
	public   Map<String, String> createDefaultMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", "");
		map.put("firstname", "");
		map.put("lastname", "");
		map.put("partialSSN", "");
		map.put("state", "");
		map.put("zipcode", "");
		map.put("customer_number", "");
		map.put("confirmationNumber", "");
		map.put("city", "");
		map.put("email", "");
		map.put("streetName", "");
		
		return map;
	}
}
