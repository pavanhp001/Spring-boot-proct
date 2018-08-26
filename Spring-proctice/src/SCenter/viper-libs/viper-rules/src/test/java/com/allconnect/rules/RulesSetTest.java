package com.A.rules;

import static org.junit.Assert.*;
import java.util.List;
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
import com.A.task.context.impl.OrchestrationContext;
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
public class RulesSetTest {

	@Autowired
	public RuleSetDao ruleSetDao;

	@Autowired
	public RuleService ruleService;

	 

	@Test
	public void testCreateDelete() {

		assertNotNull(ruleSetDao);
		try {

			ruleSetDao.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<RulesSet> allRulesSets = ruleSetDao.getAll();

		assertNotNull(allRulesSets);
		assertEquals(0, allRulesSets.size());

		RulesSet ruleSet = RuleSetMo.create();
		ruleSetDao.save(ruleSet);

		allRulesSets = ruleSetDao.getAll();

		assertNotNull(allRulesSets);
		assertEquals(1, allRulesSets.size());

		try {

			ruleSetDao.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

		allRulesSets = ruleSetDao.getAll();

		assertNotNull(allRulesSets);
		assertEquals(0, allRulesSets.size());
	}

	
}
