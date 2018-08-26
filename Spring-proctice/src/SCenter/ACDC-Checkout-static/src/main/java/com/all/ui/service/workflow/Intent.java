package com.AL.ui.service.workflow;

import java.util.HashMap;
import java.util.Map;

import com.AL.ui.service.workflow.stat.StaticIntentSteps;

public class Intent {

	private ActionStatus currentStatus = ActionStatus.success;
	private StaticIntentSteps step;
	private Map<String, Object> extras;

	public Intent(StaticIntentSteps step) {
		this.step = step;
	}

	public StaticIntentSteps getStep() {
		return step;
	}

	public void setStep(StaticIntentSteps step) {
		this.step = step;
	}

	public Map<String, Object> getExtras() {

		if (extras == null) {
			extras = new HashMap<String, Object>();
		}
		return extras;
	}

	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}

	
	public String getAsString(final String key) {
		return (String)this.getExtras().get(key);
	}

	public ActionStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(ActionStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
}
