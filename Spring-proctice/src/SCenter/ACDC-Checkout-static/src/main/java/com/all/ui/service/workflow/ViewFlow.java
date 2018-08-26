package com.AL.ui.service.workflow;

import org.springframework.web.servlet.ModelAndView;

public class ViewFlow {
	
	
	public ModelAndView  modelAndView;
	public ActionStatus actionStatus;
	
	public static ViewFlow create(ModelAndView mv, ActionStatus actionStatus) {
		ViewFlow vf = new ViewFlow(mv);
		vf.setActionStatus(actionStatus);
		
		return vf;
	}
	
	public ViewFlow(ModelAndView mv) {
		this.modelAndView = mv;
	}

	

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public ActionStatus getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(ActionStatus actionStatus) {
		this.actionStatus = actionStatus;
	}
	
	
	
}
