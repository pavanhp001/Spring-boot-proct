/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.bean.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class LocationCodeMapper {
	
	private static final String EMPTY_STRING = "";
	private Map<String, String> locCodeMap;

	public Map<String, String> getLocCodeMap() {
		return locCodeMap;
	}

	public void setLocCodeMap(final Map<String, String> locCodeMap) {
		this.locCodeMap = locCodeMap;
	}
	
	public String getLocationCode(final String mapedKey){		
		return locCodeMap.get(mapedKey) == null ? EMPTY_STRING : locCodeMap.get(mapedKey).toUpperCase();
	}
	
	/**
	 * Returns the list of all the location codes map to a given airport
	 * @param aiportCode - Airport code
	 * @return List of location codes for an airport
	 */
	public List<String> getLocationCodes(final String aiportCode) {
		
		Set<Entry<String, String>> entrySet = locCodeMap.entrySet();
		
		List<String> locationList = new ArrayList<String>();
		
		for(Entry<String, String> entry : entrySet){			
			if(aiportCode.equalsIgnoreCase(entry.getValue())){
				locationList.add(entry.getKey());
			}
		}
		
		return locationList;
	}	

}
