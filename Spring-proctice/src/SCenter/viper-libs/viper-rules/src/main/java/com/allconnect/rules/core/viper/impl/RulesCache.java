package com.A.rules.core.V.impl;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import com.A.Vdao.dao.RuleSetDao;
import org.apache.commons.collections.map.LRUMap;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.common.DroolsObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.A.rules.core.RuleSessionFactory;
import com.A.V.beans.entity.RuleStorage;
import com.A.V.beans.entity.Rules;
import com.A.V.beans.entity.RulesSet;

@Component
public class RulesCache {

	private static Logger logger = Logger.getLogger(RulesCache.class);
	final static int CACHE_SIZE = 100;
 
	public static LRUMap rulesSessionFactoryCache  = new LRUMap( CACHE_SIZE);

	private static Object rulesCacheKey = new Object();

	@Autowired
	private RuleSetDao ruleSetDao;

	public RuleSessionFactory getOrBuild(final String namespace,
			final RulesSet singleRuleSet) {

		final List<Rules> ruleList = singleRuleSet.getRules();

		logger.debug("ensure shared cache has been created");
		if (rulesSessionFactoryCache == null) {
			synchronized (rulesCacheKey) {
				if (rulesSessionFactoryCache == null) {
					rulesSessionFactoryCache = new LRUMap(CACHE_SIZE);
				}
			}
		}

		logger.debug("locate rule session factory in cache");
		RuleSessionFactory ruleSessionFactory = (RuleSessionFactory)rulesSessionFactoryCache 
				.get(namespace);

		if (ruleSessionFactory == null) {
			logger.debug("rule session factory not found in cache; see if stored in DB Storage");
			ruleSessionFactory = getStoredSessionFactory();

			if (ruleSessionFactory == null) {
				logger.debug("rule session factory not found in cache or DB storage; create from rule files");
				ruleSessionFactory = buildSessionFactoryFromRuleFiles(
						namespace, ruleList);

				// Save RuleSessionFactory to the database
				ruleSessionFactory.setChangeAgent(null);
				ruleSessionFactory.setAgent(null);
				RuleStorage storage = ruleSetDao.save(
						(Serializable) ruleSessionFactory.getKnowledgeBase(),
						new HashMap<String, String>());

				singleRuleSet.setRuleStorage(storage);
				
				 
					ruleSetDao.save(singleRuleSet);
				 
				 
			}
		}

		return ruleSessionFactory;
	}

	public RuleSessionFactory getStoredSessionFactory() {

		RuleSessionFactory ruleSessionFactory = null;

		List<RulesSet> rsList = ruleSetDao.find(new HashMap<String, String>());

		if ((rsList != null) && (rsList.size() > 0)) {
			RulesSet rsf = rsList.get(0);

			RuleStorage rs = rsf.getRuleStorage();

			if (rs != null) {
				byte[] data = rs.getRuleBase();

				try {
					KnowledgeBase knowledgeBase = (KnowledgeBase) deserializeKnowledgeBase(data);

					ruleSessionFactory = new RuleFactoryDefault(knowledgeBase);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return ruleSessionFactory;

	}

	private static KnowledgeBase deserializeKnowledgeBase(
			byte[] serializedKnowledgeBase) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(
				serializedKnowledgeBase);
		DroolsObjectInputStream dois = new DroolsObjectInputStream(bais);
		KnowledgeBase knowledgeBase = (KnowledgeBase) dois.readObject();
		dois.close();
		return knowledgeBase;
	}

	public RuleSessionFactory buildSessionFactoryFromRuleFiles(
			final String namespace, final List<Rules> ruleList) {

		RuleSessionFactory ruleSessionFactory = null;

		Set<String> droolsFileNames = new TreeSet<String>();
		for (Rules rule : ruleList) {

			logger.info("adding...." + rule.getRuleFileName());
			droolsFileNames.add(rule.getRuleFileName());
		}

		String[] drlFileNamesInArray = {};
		drlFileNamesInArray = droolsFileNames.toArray(drlFileNamesInArray);

		ruleSessionFactory = new RuleFactoryDefault(drlFileNamesInArray);
		rulesSessionFactoryCache.put(namespace, ruleSessionFactory);

		return ruleSessionFactory;
	}

	public static RuleSessionFactory getRuleFactory(final String arg) {
		return (RuleSessionFactory)rulesSessionFactoryCache.get(arg);
	}
}
