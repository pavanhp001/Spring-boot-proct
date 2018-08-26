package com.A.V.domain;

import java.util.HashMap;
import java.util.Map;

public enum DayOfWeek {
	any, sun, mon, tues, wed, thurs, fri, sat;
	
	
	public Map<String, String> getInitialMap() {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put(any.name(), null);
		map.put(sun.name(), null);
		map.put(mon.name(), null);
		map.put(tues.name(), null);
		map.put(wed.name(), null);
		map.put(thurs.name(), null);
		map.put(fri.name(), null);
		map.put(sat.name(), null);
		
		
		
		return map;
	}
}
