package com.A.rules;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.A.rules.core.RuleService;
import com.A.rules.core.V.impl.RulesCache;
import com.A.rules.mo.RuleSetMo;
import com.A.task.context.impl.OrchestrationContext;
import com.A.validation.Message.Type;
import com.A.V.beans.entity.RuleStorage;
import com.A.V.beans.entity.RulesSet;
import com.A.V.beans.entity.SalesOrder;
import com.A.Vdao.dao.RuleSetDao;

/**
 * @author ebthomas
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionalTransactionManager", defaultRollback = false)
public class RulesTwoFilesTest {

	@Autowired
	public RuleSetDao ruleSetDao;

	@Autowired
	public RuleService ruleService;

	@Before
	public void setUp() {
		//System.out.println("Running RuleSet Test");
	}

	@Test
	public void testRules() throws Exception {

		clearRuleSet();

		// Save Ruleset
		RulesSet ruleSet = RuleSetMo.create2();
		ruleSetDao.save(ruleSet);
		Thread.sleep(1000);
//		List<RuleStorage> listRuleStorage = ruleSetDao.getRuleFactory();
//		assertNotNull(listRuleStorage);
//		assertEquals(0, listRuleStorage.size());
//
//		final OrchestrationContext context = OrchestrationContext.Factory
//				.create();
//
//		// nothing in cache before execution of rules
//		assertEquals(0, RulesCache.rulesSessionFactoryCache.size());
//
//		Map<String, String> rules = new HashMap<String, String>();
//		context.add("rulesSelectionProperties", rules);
//
//		Set<Object> facts = new HashSet<Object>();
//
//		facts.add(getSalesOrder(null));
//		context.add("facts", facts);
//		ruleService.execute(context);
//		Thread.sleep(2000);
//		assertEquals(1, context.getValidationReport().getError().size());
//
//		Set<com.A.validation.Message> msgList = context
//				.getValidationReport().getError();
//		for (com.A.validation.Message msg : msgList) {
//			assertEquals(1L, msg.getMessageCode().longValue());
//			assertEquals(
//					"rule.1.invalid.external.id.sales order external id is null",
//					msg.getMessageKey());
//			assertEquals(Type.ERROR, msg.getType().ERROR);
//
//		}
//
//
//
//		//assertEquals(1, context.getValidationReport().getInfo().size());
//
//		 msgList = context
//				.getValidationReport().getError();
//		for (com.A.validation.Message msg : msgList) {
//			assertEquals(1L, msg.getMessageCode().longValue());
//			assertEquals(
//					"rule.1.invalid.external.id.sales order external id is null",
//					msg.getMessageKey());
//			assertEquals(Type.INFO, msg.getType().INFO);
//
//		}
//
//
//
//		// One storage unit should be in storage
//		listRuleStorage = ruleSetDao.getRuleFactory();
//		assertNotNull(listRuleStorage);
//		assertEquals(1, listRuleStorage.size());
//
//		// locate specific ruleset.
//		List<RulesSet> rsList = ruleSetDao.find(new HashMap<String, String>());
//		RulesSet rs = rsList.get(0);
//		assertNotNull(rs);
//
//		// Storage should be there and attached to the ruleset
//		assertNotNull(rs.getRuleStorage());
//
//
//		// Look into Cache. One element in cache
//		assertEquals(1, RulesCache.rulesSessionFactoryCache.size());
//
//
//		clearRuleSet();

	}

	public SalesOrder getSalesOrder(final Long externalId) {
		SalesOrder sob = new SalesOrder();
		sob.setExternalId(null);
		sob.setAAccountNumber("12345");
		sob.setAConfirmNumber("67890");
		sob.setExternalId(externalId);

		return sob;
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
