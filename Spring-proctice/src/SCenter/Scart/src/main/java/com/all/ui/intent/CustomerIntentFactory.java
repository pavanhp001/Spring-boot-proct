package com.AL.ui.intent;

import javax.servlet.http.HttpServletRequest;

import com.AL.ui.repository.SessionCache;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustomerType;

public enum CustomerIntentFactory {

	INSTANCE;
	
	public Intent create(IntentAction intentStep, CustomerType customerType, AddressType address, HttpServletRequest request) {
		Intent intent = new Intent(intentStep); 

		String agentId = SessionCache.INSTANCE.getAgentId(request.getSession());
		String[] roles = request.getParameterValues("roles");
		String checkAllRoles = request.getParameter("checkAllRoles");
		
		intent.getExtras().put("agentId", agentId);
		intent.getExtras().put("roles", roles);
		intent.getExtras().put("checkAllRoles", checkAllRoles);
		intent.getExtras().put("customerType", customerType);
		intent.getExtras().put("address", address);

		//identify the action that will be initiated
		intent.getExtras().put("action", intentStep.name());

		return intent;
	}
}
