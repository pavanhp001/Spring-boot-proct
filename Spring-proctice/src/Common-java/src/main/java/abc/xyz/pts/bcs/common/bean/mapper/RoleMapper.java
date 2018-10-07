/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2012
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.bean.mapper;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RoleMapper {
	
	private Map<String, String> roleMap;

	public Map<String, String> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(final Map<String, String> roleMap) {
		this.roleMap = roleMap;
	}
	
	public String getLdapRole(final String mapedKey){
		return roleMap.get(mapedKey) == null ? null : roleMap.get(mapedKey).toUpperCase();
	}

	public String getADRole(final String role) {
		Set<Entry<String, String>> entrySet = roleMap.entrySet();
		
		for(Entry<String, String> entry : entrySet){			
			if(role.equalsIgnoreCase(entry.getValue())){
				return entry.getKey();
			}
		}
		
		return null;
	}

}
