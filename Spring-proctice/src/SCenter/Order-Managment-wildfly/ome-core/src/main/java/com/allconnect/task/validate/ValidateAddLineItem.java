package com.AL.task.validate;

import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.SalesOrder;

public enum ValidateAddLineItem {

	INSTANCE;
	
	
	public void validate(final OrchestrationContext params) {
		
		SalesOrder salesOrder = (SalesOrder) params
		.get(TaskContextParamEnum.salesOrder.name());
		
	}
	
	private void promotionToPromotion(final SalesOrder salesOrder) {
		
	}
	
	
	 
	
	
}
