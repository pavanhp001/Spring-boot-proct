package com.A.rules.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.A.task.context.impl.OrchestrationContext;
import com.A.rules.core.impl.RuleAdministratorDefault;
import com.A.rules.core.impl.RuleServiceBase;
import com.A.rules.core.V.impl.RulesCache;
import com.A.validation.ValidationReport;
import com.A.V.beans.entity.RulesSet;
import com.A.Vdao.dao.RuleSetDao;

@Component("ruleService")
public class RuleServiceDefault extends RuleServiceBase implements RuleService {

	private static final Logger logger = Logger.getLogger(RuleServiceDefault.class);
	@Autowired
	private RuleSetDao ruleDao;

	@Autowired
	private RulesCache ruleSessionFactoryCache;

	@Autowired
	private RuleAdministrator ruleAdministrator;

	public RuleAdministrator getRuleAdministrator() {

		return ruleAdministrator;
	}

	public void removeMemoryCache() {
		
		logger.info("clearing memory cache");
		if (RulesCache.rulesSessionFactoryCache != null) {
			RulesCache.rulesSessionFactoryCache.clear();
		}
	}

	public void removeStorageCache() {
		
		logger.info("deleting storage cache");
		ruleDao.deleteStorage();

		if (RulesCache.rulesSessionFactoryCache != null) {
			RulesCache.rulesSessionFactoryCache.clear();
		}
	}

	public void execute(OrchestrationContext context) {

		RulesLogger.INSTANCE.logger.info("executing rule set");
		RuleAdministrator administrator = getRuleAdministrator();

		if (administrator == null) {
			administrator = new RuleAdministratorDefault();
		}

		// String objFilter = context.getExecutionContextFilter();
		Map<String, String> rulesSelectionProperties = context
				.getRulesSelectionProperties();

		if (rulesSelectionProperties == null) {
			rulesSelectionProperties = new HashMap<String, String>();
		}

		RulesLogger.INSTANCE.logger
				.info("determine rules to execute based on selection criteria");
		List<RulesSet> ruleSet = administrator
				.getRuleExecutionSet(rulesSelectionProperties);

		RulesLogger.INSTANCE.logger
				.info("extract data from context and load to rules engine");
		Set<Object> facts = context.getFacts();

		RulesLogger.INSTANCE.logger.info("begin execute rule set");
		for (RulesSet singleRuleSet : ruleSet) {

			String ruleSetName = singleRuleSet.getName();

			RulesLogger.INSTANCE.logger
					.info("get ruleset from ruleSessionFactoryCache ");
			RulesLogger.INSTANCE.logger
					.info("if not there get from storage and add to ruleSessionFactoryCache ");
			RulesLogger.INSTANCE.logger
					.info("if not in storage create from rule files and add to ruleSessionFactoryCache, storage");
			RuleSessionFactory ruleSessionFactory = ruleSessionFactoryCache
					.getOrBuild(ruleSetName, singleRuleSet);

			if ((!validInit(ruleSessionFactory, context))) {
				RulesLogger.INSTANCE.logger
						.info("unable to process rule set continue to next set");
				continue;
			}

			RulesLogger.INSTANCE.logger.info("executing rules");
			ValidationReport report = executeRules(ruleSessionFactory, facts);

			RulesLogger.INSTANCE.logger.info("adding rules output to context");
			context.getValidationReport().addMessages(report.getMessages());

			RulesLogger.INSTANCE.logger
					.info("ensuring context is updated before response");
			preserveRequestParameters(context);

		}
	}

	public List<String> getRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRuleFactory(RuleSessionFactory decisionInstance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preserveRequestParameters(OrchestrationContext parameters) {
		// TODO Auto-generated method stub

	}

	public void setRuleAdministrator(RuleAdministrator ruleAdministrator) {
		this.ruleAdministrator = ruleAdministrator;
	}

}
