package com.A.ui.builder;

import java.util.List;

import com.A.util.DateUtil;
import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.ObjectFactory;

public enum LineItemStatusBuilder {

	INSTANCE;
	
	public LineItemStatusType build(final String agentId, final String statusCode,
			final String description, final List<Integer> reasons) {
		ObjectFactory oFactory = new ObjectFactory();
		LineItemStatusType liStatusType = oFactory.createLineItemStatusType();
		liStatusType.setAgentId(agentId);
		liStatusType.setStatusCodeDescription(description);
		liStatusType.setStatusCode(LineItemStatusCodesType
				.fromValue(statusCode));
		liStatusType.setDateTimeStamp(DateUtil.getCurrentXMLDate());

		for (Integer reason : reasons) {
			liStatusType.getReason().add(reason);
		}

		return liStatusType;
	}

}
