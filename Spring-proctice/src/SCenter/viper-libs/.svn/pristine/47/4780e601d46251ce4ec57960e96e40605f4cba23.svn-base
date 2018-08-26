package com.A.Vdao.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.A.V.beans.entity.RuleStorage;
import com.A.V.beans.entity.RulesSet;

/**
 * Base interface used by Rule Set DAO dao implementations.
 * 
 * @author klyons
 */
public interface RuleSetDao {

	/**
	 * Utility method to find System Properties by name and context.
	 * 
	 * @param name
	 * @param context
	 * @return SystemProperties
	 */
	List<RulesSet> find(final Map<String, String> properties);

	RuleStorage save(Serializable knowledgeBase, Map<String, String> map);

	List<RuleStorage> getRuleFactory();

	void save(RulesSet ruleSet);

	void update(RulesSet ruleSet);

	void delete();
	
	public void deleteStorage();
	
	public void deleteStorage(final Long rulesSetId);

	List<RulesSet> getAll();

	RulesSet findRuleSetById(final Long id);
	
	RulesSet  findRuleSetByName(final String name) ;
}