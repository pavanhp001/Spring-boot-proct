package com.A.rules.core.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.A.rules.core.RuleAdministrator;
import com.A.ui.domain.RulesSet;
import com.A.ui.dao.RuleSetDao;

@Component
public class RuleAdministratorDefault implements RuleAdministrator {

	@Autowired
	private RuleSetDao ruleSetDao;

	public List<RulesSet> getRuleExecutionSet(
			Map<String, String> rulesSelectionProperties) {
 
		return ruleSetDao.find(rulesSelectionProperties);

	}

	public List<RulesSet> getRuleExecutionSet() {
		 
		throw new UnsupportedOperationException("unsupported");
	}

}
