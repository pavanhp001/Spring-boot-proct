/**
 *
 */
package com.A.rules.core;

import java.util.List;

import com.A.validation.OrchestrationContext;

/**
 * @author ebthomas
 * 
 */
public interface RuleService {
	String DECISION_RULE = "decision";

	/**
	 * @param parameters
	 *            request parameters
	 * @return TaskResponse
	 */
	void execute(OrchestrationContext parameters);

	List<String> getRegistrations();

	/**
	 * @param decisionInstance
	 */
	void setRuleFactory(RuleSessionFactory decisionInstance);

	public void removeMemoryCache();

	public void removeStorageCache();

}
