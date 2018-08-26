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
import com.A.V.beans.entity.SalesOrder;
import com.A.Vdao.dao.OrderManagementDao;
import com.A.Vdao.dao.RuleSetDao;

/**
 * @author ebthomas
 *
 *
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionalTransactionManager", defaultRollback = false)
public class RulesGetByStatusReasonTest {

	@Autowired
	public RuleSetDao ruleSetDao;

	@Autowired
	public RuleService ruleService;

	@Autowired
	public OrderManagementDao orderDao;

	@Before
	public void setUp() {
		//System.out.println("Running RuleSet Test");
	}

	@Test
	public void testGetSalesOrderByStatusReason() {


		List<SalesOrder> orders = orderDao.findOrderByStatusReason("sales_new_order", "0",0,100);
		assertNotNull(orders);
		assertTrue(orders.size()>0);
	}

	@Test
	public void testRulesContextDelta() throws Exception {
		clearRuleSet();

		// Save Ruleset
		// Ruleset applies to all sets with source as source1
		RulesSet ruleSet = RuleSetMo.create();
		ruleSet.setProvider(null);
		ruleSet.setContext("contextMISSING");
		ruleSet.setSource("source1");
		ruleSet.setLineItemDetailExternalId(null);
		ruleSetDao.save(ruleSet);

		Map<String, String> criteria = new HashMap<String, String>();

		criteria.put("context", "context");
		criteria.put("provider", "provider");
		criteria.put("source", "source1");
		List<RulesSet> listOfRuleSet = ruleSetDao.find(criteria);
		assertNotNull(listOfRuleSet);
		assertEquals(0, listOfRuleSet.size());


		criteria.clear();
		criteria.put("source", "source1");
		criteria.put("context", "contextMISSING");
		 listOfRuleSet = ruleSetDao.find(criteria);
		assertNotNull(listOfRuleSet);
		Thread.sleep(5000);
		//assertEquals(1, listOfRuleSet.size());

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
