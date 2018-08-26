package com.A.rules.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.A.V.beans.entity.Rules;
import com.A.V.beans.entity.RulesSet;

public class RuleSetMo {

	public static final RulesSet createMissingFile() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 10);
		
		RulesSet rs = new RulesSet();
		rs.setName("default");
		rs.setContext(null);
		rs.setProvider(null);
		rs.setSource(null);
		rs.setSourceContext(null);
		rs.setLineItemDetailExternalId(null);
		rs.setEnabled(Boolean.TRUE);
		rs.setDateEffectiveFrom(Calendar.getInstance());
		rs.setDateEffectiveTo(cal);

		
		
		
		List<Rules> rules = new ArrayList<Rules>();
		rules.add(createRules("rules_validation_template.drl"));
		rules.add(createRules("rules_validation_template-missing-one.drl"));

		rs.setRules(rules);

		return rs;

	}
	
	public static final RulesSet create2() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 10);
		
		RulesSet rs = new RulesSet();
		rs.setName("default");
		rs.setContext(null);
		rs.setProvider(null);
		rs.setSource(null);
		rs.setSourceContext(null);
		rs.setLineItemDetailExternalId(null);
		rs.setEnabled(Boolean.TRUE);
		rs.setDateEffectiveFrom(Calendar.getInstance());
		rs.setDateEffectiveTo(cal);

		List<Rules> rules = new ArrayList<Rules>();
		rules.add(createRules("rules_validation_template.drl"));
		rules.add(createRules("rules_validation_template-info.drl"));

		rs.setRules(rules);

		return rs;

	}
	
	
	public static final RulesSet create() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 10);
		
		RulesSet rs = new RulesSet();
		rs.setName("default");
		rs.setContext(null);
		rs.setProvider(null);
		rs.setSource(null);
		rs.setSourceContext(null);
		rs.setLineItemDetailExternalId(null);
		rs.setEnabled(Boolean.TRUE);
		rs.setDateEffectiveFrom(Calendar.getInstance());
		rs.setDateEffectiveTo(cal);

		List<Rules> rules = new ArrayList<Rules>();
		rules.add(createRules("rules_validation_template.drl")); 

		rs.setRules(rules);

		return rs;

	}

	public static Rules createRules(final String filename) {
		Rules rule = new Rules();
		rule.setDateEffectiveFrom(Calendar.getInstance());
		rule.setDateEffectiveTo(Calendar.getInstance());
		rule.setDescription(UUID.randomUUID().toString());
		rule.setEnabled(true);
		rule.setRuleFileName(filename);
		rule.setName(filename);

		return rule;
	}
}
