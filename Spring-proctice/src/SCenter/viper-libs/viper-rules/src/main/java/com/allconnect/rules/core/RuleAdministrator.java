package com.A.rules.core;

import java.util.List;
import java.util.Map;
import com.A.V.beans.entity.RulesSet;

public interface RuleAdministrator {

	List<RulesSet> getRuleExecutionSet(  final Map<String, String> properties);
	
	List<RulesSet> getRuleExecutionSet();
}
