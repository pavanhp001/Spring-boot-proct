package com.A.Vdao.transactional.dao.util;

import java.util.List;

public enum CriteriaBuilder {

	INSTANCE;
	
public String buildCriteria(List<String> data, String compareField, String operationType) {
		
		
		
		StringBuilder sb = new StringBuilder("( ");
		int count = 0;
		for (String i:data) {
			count++;
			sb.append(" ("+i+compareField+" )"); 
			
			if (count != data.size())
			sb.append(" "+operationType+" "); 
		}
		
		sb.append(" ) ");
		
		return sb.toString();
	}



}
