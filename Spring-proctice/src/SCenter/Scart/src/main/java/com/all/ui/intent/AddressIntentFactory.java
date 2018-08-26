package com.AL.ui.intent;

import javax.servlet.http.HttpServletRequest;

import com.AL.ui.factory.CartAddressFactory;
import com.AL.ui.repository.SessionCache;
import com.AL.xml.cm.v4.AddressType;

public enum AddressIntentFactory {

	INSTANCE;
	
	public Intent create(IntentAction intentStep, String id, String address_id, AddressType address, HttpServletRequest request) {
		
		Intent intent = new Intent(intentStep); 
		
		String agentId = SessionCache.INSTANCE.getAgentId(request.getSession());
		String[] roles = request.getParameterValues("roles");
		String dwellingType = request.getParameter("_dwellingType");
		String addressOwnership = request.getParameter("_addressOwnership");
		String addressUniqueId = request.getParameter("addressUniqueId");
		String status = request.getParameter("status");
 
		intent.getExtras().put("customerId", id);
		intent.getExtras().put("address_id", address_id);
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
	
	public Intent create(IntentAction intentStep, String customerId, String addressId, HttpServletRequest request) {
		
		Intent intent = new Intent(intentStep); 
		
		String agentId = SessionCache.INSTANCE.getAgentId(request.getSession());
		String[] roles = request.getParameterValues("roles");
		if(roles == null){
			roles = new String[1];
			roles[0] = "SERVICE_ADDRESS";
		}
		String dwellingType = request.getParameter("_dwellingType");
		String addressOwnership = request.getParameter("_addressOwnership");
		String addressUniqueId = request.getParameter("addressUniqueId");
		if ((addressUniqueId == null) || (addressUniqueId.length() == 0)) {
			addressUniqueId = "" + System.nanoTime();
		}
		
		String status = request.getParameter("status");
		if(status == null){
			status = "active";
		}
		
		AddressType address = CartAddressFactory.INSTANCE.addAddress(request);
		address.setExternalId(Long.valueOf(addressId).longValue());
 
		intent.getExtras().put("customerId", customerId);
		intent.getExtras().put("addressId", addressId);
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
	
	public boolean validateIntent(Intent intent) {
		return Boolean.TRUE;
	}
}
