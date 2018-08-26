package com.AL.op.service;

import com.AL.util.OmeSpringUtil;

public enum OpServiceLookupContext {

	INSTANCE;
	
	public Object lookup(String serviceName){
		return OmeSpringUtil.INSTANCE.getBean(serviceName);
	}
}
