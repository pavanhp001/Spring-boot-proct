package com.A.rules.core;

import java.util.Set;

import com.A.task.context.impl.OrchestrationContext;



 
/**
 * @author ebthomas
 * 
 */
public interface FactFactory {
	/**
	 * @param parameters
	 *            input parameters
	 * @return Set
	 */
	Set<Object> getFacts(final Set<Object> facts,
			final OrchestrationContext parameters,
			final OrchestrationContext response);
}
