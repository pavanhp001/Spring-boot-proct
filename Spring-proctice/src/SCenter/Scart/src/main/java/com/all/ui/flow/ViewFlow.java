package com.AL.ui.flow;

import org.springframework.web.servlet.ModelAndView;

public class ViewFlow {
	
	public ModelAndView  modelAndView;
	
	public ViewFlow(ModelAndView mv) {
		this.modelAndView = mv;
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}
	
}
