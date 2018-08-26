package com.AL.ui.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.AL.xml.di.v4.NameValuePairType;
import com.AL.xml.di.v4.SalesContextEntityType;

public class SalesContextUtil {

	public static Map<String, Map<String, String>> getData() {

		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("channel", "web");

		context.put("orderSource", orderSource);

		return context;
	}

	public static SalesContextEntityType buidlSalesContextEntity(String entityName, Map<String,String> attributeMap) {

		SalesContextEntityType salesContextEntity = new SalesContextEntityType();
		salesContextEntity.setName(entityName);
		List<NameValuePairType> nvPairList = salesContextEntity.getAttribute();
		for(Entry<String, String> attributeEntry :attributeMap.entrySet()){
			NameValuePairType nvPairType = new NameValuePairType();
			nvPairList.add(nvPairType);
			nvPairType.setName(attributeEntry.getKey());
			nvPairType.setValue(attributeEntry.getValue());
		}
		return salesContextEntity;
	}
}
