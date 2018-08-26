package com.AL.ui.intent;

import javax.servlet.http.HttpServletRequest;

import com.AL.ui.repository.SessionCache;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustomerType;

public enum OrderIntentFactory {
	
	INSTANCE;

	public Intent create(IntentAction intentStep, CustomerType customerType, AddressType address, HttpServletRequest request) {
		Intent intent = new Intent(intentStep); 

		String agentId = SessionCache.INSTANCE.getAgentId(request.getSession());
		String[] roles = request.getParameterValues("roles");
		String dwellingType = request.getParameter("_dwellingType");
		String addressOwnership = request.getParameter("_addressOwnership");
		String addressUniqueId = request.getParameter("addressUniqueId");
		String status = request.getParameter("status");

		intent.getExtras().put("agentId", agentId);
		intent.getExtras().put("roles", roles);
		intent.getExtras().put("dwellingType", dwellingType);
		intent.getExtras().put("addressOwnership", addressOwnership);
		intent.getExtras().put("addressUniqueId", addressUniqueId);
		intent.getExtras().put("status", status);
		intent.getExtras().put("address", address);

		//identify the action that will be initiated
		intent.getExtras().put("action", intentStep.name());

		return intent;
	}
	public Intent createOrderAndCustomer(IntentAction intentStep,
			CustomerType customerType, AddressType address,
			HttpServletRequest request) {
		Intent intent = new Intent(intentStep); 
		
		String agentId = SessionCache.INSTANCE.getAgentId(request.getSession());
		String[] roles = request.getParameterValues("roles");
		
		intent.getExtras().put("customerType", customerType);
		intent.getExtras().put("address", address);
		intent.getExtras().put("agentId", agentId);
		intent.getExtras().put("roles", roles);
		
		//identify the action that will be initiated
		intent.getExtras().put("action", intentStep.name());
		return intent;
	}
}
