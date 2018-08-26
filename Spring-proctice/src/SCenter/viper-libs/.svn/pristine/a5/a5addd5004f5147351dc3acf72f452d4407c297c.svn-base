package com.A.util;

import java.util.List;

public enum CollectionUtil {

	INSTANCE;
	
	public String getAsCommaDelimitedString(List<Integer> reasonList) {

		if ((reasonList == null) || (reasonList.size() == 0)) {

			return "";
		}

		StringBuilder result = new StringBuilder();
		for (Integer reason : reasonList) {
			result.append("[").append(reason).append("]");
			result.append(",");
		}

		return result.length() > 0 ? result.substring(0, result.length() - 1)
				: "";

	}
}
