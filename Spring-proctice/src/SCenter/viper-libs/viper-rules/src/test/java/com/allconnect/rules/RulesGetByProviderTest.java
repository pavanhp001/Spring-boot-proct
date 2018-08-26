package com.A.rules;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.A.rules.core.RuleService;
import com.A.rules.mo.RuleSetMo;
import com.A.V.beans.entity.RulesSet;
import com.A.Vdao.dao.RuleSetDao;

/**
 * @author ebthomas
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionalTransactionManager", defaultRollback = false)
public class RulesGetByProviderTest {

	@Autowired
	public RuleSetDao ruleSetDao;

	@Autowired
	public RuleService ruleService;

	@Before
	public void setUp() {
		//System.out.println("Running RuleSet Test");
	}

	@Test
	public void testRulesmatch() throws Exception {

		clearRuleSet();

		//Save Ruleset
		RulesSet ruleSet = RuleSetMo.create();
		ruleSet.setProvider("provider");
		ruleSet.setContext(null);
		ruleSet.setLineItemDetailExternalId(null);
		ruleSetDao.save(ruleSet);

		Thread.sleep(1000);

		Map<String, String> criteria = new HashMap<String, String>();


		criteria.put("provider", "provider");
		List<RulesSet> listOfRuleSet = ruleSetDao.find(criteria);
		assertNotNull(listOfRuleSet);
		assertEquals(1, listOfRuleSet.size());

		clearRuleSet();
	}

	@Test
	public void testRulesMismatch() {

		clearRuleSet();

		//Save Ruleset
		RulesSet ruleSet = RuleSetMo.create();

		ruleSet.setProvider("provider-miss");
		ruleSet.setContext(null);
		ruleSet.setLineItemDetailExternalId(null);
		ruleSetDao.save(ruleSet);


		Map<String, String> criteria = new HashMap<String, String>();

		//no rules for this provider[provider-miss]
		criteria.put("context", "context");
		criteria.put("provider", "provider");
		List<RulesSet> listOfRuleSet = ruleSetDao.find(criteria);
		assertNotNull(listOfRuleSet);
		assertEquals(0, listOfRuleSet.size());
		clearRuleSet();

	}

	public final void clearRuleSet() {

		assertNotNull(ruleSetDao);
		try {

			ruleSetDao.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RulesSet> allRulesSets = ruleSetDao.getAll();

		assertNotNull(allRulesSets);
		assertEquals(0, allRulesSets.size());
	}


}
