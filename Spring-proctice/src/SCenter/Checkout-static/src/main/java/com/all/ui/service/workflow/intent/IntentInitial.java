package com.AL.ui.service.workflow.intent;

import javax.servlet.http.HttpServletRequest;

import com.AL.ui.service.workflow.Intent;
import com.AL.ui.service.workflow.stat.StaticIntentSteps;

public enum IntentInitial {

	INSTANCE;
	
	/**
	 * obtains CKOInput, sessionID from request, set them to intent and returned
	 * @param intentStep
	 * @param request
	 * @return Intent
	 */
	public Intent process(StaticIntentSteps intentStep, HttpServletRequest request) {
		
		final Intent intent = new Intent(intentStep);
		
		String CKOInput = request.getParameter("CKOInput");
		String sessionId = request.getSession().getId();
		
		intent.getExtras().put("CKOInput", CKOInput);
		intent.getExtras().put("sessionId", sessionId);
		
		return intent;
		
	}
	
	public boolean validateIntent(Intent intent) {
		return Boolean.TRUE;
	}
}
