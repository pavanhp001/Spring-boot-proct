package com.A.ui.builder;

import java.util.List;

import org.apache.log4j.Logger;

public enum CriteraStringBuilder {
	
	INSTANCE;
	
	public static final Logger logger = Logger.getLogger(CriteraStringBuilder.class);
	public String buildCriteria(List<String> data) {
			
			if ((data == null) || (data.size() == 0)) {
				return "(-1)";
			}
			
			StringBuilder sb = new StringBuilder("( ");
			int count = 0;
			for (String i:data) {
				count++;
				sb.append(i); 
				
				if (count != data.size())
				sb.append(","); 
				else
					sb.append(")");
			}
			logger.debug(sb.toString());
			return sb.toString();
		}

}
