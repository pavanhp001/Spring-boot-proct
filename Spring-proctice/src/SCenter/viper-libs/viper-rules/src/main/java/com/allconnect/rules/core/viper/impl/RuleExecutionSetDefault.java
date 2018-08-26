package com.A.rules.core.V.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.A.V.beans.entity.Rules;
import com.A.V.beans.entity.RulesSet;

public class RuleExecutionSetDefault  {

	public static List<RulesSet> getDefaultRulesSet()
	{
		List<RulesSet> result = new ArrayList<RulesSet>();
		
		RulesSet rSet = new RulesSet();
		rSet.setName("default");
		rSet.setEnabled(true);
		rSet.setProvider("*");
		
		
		
		List<Rules> rules = new ArrayList<Rules>();
		rules.add(createRules("rules_validation_template.drl"));
		//rules.add(createRules("default_bundle.drl"));
		//rules.add(createRules("default_feature.drl"));
		//rules.add(createRules("default_promotion.drl"));
		//rules.add(createRules("default_market_item.drl"));
		
		rSet.setRules(rules);
		
		result.add(rSet);
		
		return result;
	}
	 

	
	public static Rules createRules(final String filename)
	{
		Rules rule = new Rules();
		rule.setDateEffectiveFrom(Calendar.getInstance());
		rule.setDateEffectiveTo(Calendar.getInstance());
		rule.setDescription("default.rule.description");
		rule.setEnabled(true);
		rule.setId(1L);
		rule.setRuleFileName(filename);
		rule.setName(filename);
		
		return rule;
	}


	 

}
