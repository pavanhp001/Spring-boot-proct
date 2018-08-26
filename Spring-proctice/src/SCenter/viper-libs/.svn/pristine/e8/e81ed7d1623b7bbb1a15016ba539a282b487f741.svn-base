package com.A.vm.vo;

import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

public class AgentUtil {

	public static String getAgentId(Request request) {
		String agentId = "1";

		if ((request == null) || (request.getAgentId() == null)) {
			throw new IllegalArgumentException(
					"invalid request.  missing agent Id or request");
		}

		if (request.getAgentId() != null) {
			agentId = request.getAgentId();
		}

		return agentId;
	}
}
