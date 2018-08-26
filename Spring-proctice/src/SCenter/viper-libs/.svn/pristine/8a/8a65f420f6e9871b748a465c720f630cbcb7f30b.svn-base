package com.A.Vdao.transactional.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.common.DroolsObjectInputStream;
import org.drools.common.DroolsObjectOutputStream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.V.beans.entity.RuleStorage;
import com.A.V.beans.entity.Rules;
import com.A.V.beans.entity.RulesSet;
import com.A.Vdao.dao.RuleSetDao;
import com.A.Vdao.util.RulesSetSelectionBuilder;

/**
 * @author klyons
 * 
 */
@Component
public class RuleSetDaoImpl extends BaseTransactionalJpaDao implements
		RuleSetDao {

	private static final Logger logger = Logger
			.getLogger(SystemPropertiesDaoImpl.class);

	/**
	 * factory constructor.
	 */
	public RuleSetDaoImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<RulesSet> find(Map<String, String> rulesSelectionProps) {

		List<RulesSet> ruleSetList = null;
		
		if (getEntityManager() != null) {
			try {

				String where = RulesSetSelectionBuilder.INSTANCE
						.getWhere(rulesSelectionProps);

				Query q = getEntityManager().createQuery(
						"select rs FROM RulesSet as rs WHERE " + where);
 

				 ruleSetList = q.getResultList();

				touch(ruleSetList);

				
			} catch (NoResultException nre) {
				logger.debug(nre.getMessage());
				 
			}
		}

		return ruleSetList;
	}

	private void touch(RulesSet ruleset) {

		for (Rules rule : ruleset.getRules())
			rule.getName();

		ruleset.getRuleStorage();

	}

	private void touch(List<RulesSet> ruleSetList) {

		for (RulesSet ruleset : ruleSetList) {
			for (Rules rule : ruleset.getRules())
				rule.getName();

			ruleset.getRuleStorage();
		}
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void deleteStorage(final Long rulesSetId) {

		if (rulesSetId == null) {
			return;
		}

		@SuppressWarnings("unchecked")
		List<RulesSet> sysPropsList = getEntityManager().createQuery(
				"SELECT rs FROM RulesSet AS rs WHERE rs.id = "
						+ rulesSetId.longValue()).getResultList();

		for (RulesSet ruleset : sysPropsList) {

			if (ruleset.getRuleStorage() != null)
				getEntityManager().remove(ruleset.getRuleStorage());

			ruleset.setRuleStorage(null);
			getEntityManager().merge(ruleset);
		}
		getEntityManager().flush();

	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void deleteStorage() {

		@SuppressWarnings("unchecked")
		List<RulesSet> sysPropsList = getEntityManager().createQuery(
				"SELECT rs FROM RulesSet as rs ").getResultList();

		for (RulesSet ruleset : sysPropsList) {

			if (ruleset.getRuleStorage() != null)
				getEntityManager().remove(ruleset.getRuleStorage());

			ruleset.setRuleStorage(null);
			getEntityManager().merge(ruleset);
		}
		getEntityManager().flush();

	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void delete() {

		@SuppressWarnings("unchecked")
		List<RulesSet> sysPropsList = getEntityManager().createQuery(
				"select rs FROM RulesSet as rs ").getResultList();

		for (RulesSet ruleset : sysPropsList) {
			getEntityManager().remove(ruleset);

			if (ruleset.getRules() != null)
				for (Rules rules : ruleset.getRules()) {
					getEntityManager().remove(rules);
				}

			if (ruleset.getRuleStorage() != null)
				getEntityManager().remove(ruleset.getRuleStorage());
			
			getEntityManager().remove(ruleset);
		}
		
		
		List<RuleStorage> rStorageList = getRuleFactory();
		for (RuleStorage storage:rStorageList) {
			getEntityManager().remove(storage);
		}
		
		getEntityManager().flush();

	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public RulesSet findRuleSetById(final Long id) {
		RulesSet ruleSetList = (RulesSet) getEntityManager()
				.createQuery(
						"select rs FROM RulesSet as rs where rs.id = "
								+ id.longValue()).getSingleResult();

		touch(ruleSetList);

		return ruleSetList;
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public RulesSet findRuleSetByName(final String name) {
		RulesSet ruleSets = (RulesSet) getEntityManager().createQuery(
				"select rs FROM RulesSet as rs where rs.name = " + name);

		touch(ruleSets);

		return ruleSets;
	}

	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<RulesSet> getAll() {
		List<RulesSet> ruleSets = getEntityManager().createQuery(
				"select rs FROM RulesSet as rs ").getResultList();

		touch(ruleSets);

		return ruleSets;
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void update(RulesSet ruleSet) {

		if (ruleSet == null) {
			return;
		}

		// update any added rules..and clear storage
		if ((ruleSet.getRules() != null) && (ruleSet.getRules().size() > 0)) {
			logger.debug("iterate thru rules");
			for (Rules rule : ruleSet.getRules()) {
				logger.debug("save rule files");
				if (rule.getId() == 0) {
					logger.debug("save new rules");
					getEntityManager().persist(rule);
					logger.debug("remove old storage from db cache");
					ruleSet.setRuleStorage(null);
				}
			}
		}

		try {

			validate(ruleSet);
			getEntityManager().merge(ruleSet);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void save(RulesSet ruleSet) {

		if (ruleSet == null) {
			return;
		}

		validate(ruleSet);

		try {

			if ((ruleSet.getRules() != null) && (ruleSet.getRules().size() > 0)) {
				for (Rules rule : ruleSet.getRules()) {
					getEntityManager().merge(rule);
				}
			}

			if (ruleSet.getRuleStorage() != null) {
				getEntityManager().merge(ruleSet.getRuleStorage());
				getEntityManager().flush();
			}

			getEntityManager().merge(ruleSet);
			getEntityManager().flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void validate(RulesSet ruleSet) {
		if (ruleSet.getProvider() == null) {
			ruleSet.setProvider("*");
		}

		if (ruleSet.getContext() == null) {
			ruleSet.setContext("*");
		}

		if (ruleSet.getSource() == null) {
			ruleSet.setSource("*");
		}

		if (ruleSet.getLineItemDetailExternalId() == null) {
			ruleSet.setLineItemDetailExternalId("*");
		}

		if (ruleSet.getSourceContext() == null) {
			ruleSet.setSourceContext("*");
		}
	}

	@SuppressWarnings("unused")
	private static KnowledgeBase deserializeKnowledgeBase(
			byte[] serializedKnowledgeBase) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(
				serializedKnowledgeBase);
		DroolsObjectInputStream dois = new DroolsObjectInputStream(bais);
		KnowledgeBase knowledgeBase = (KnowledgeBase) dois.readObject();
		dois.close();
		return knowledgeBase;
	}

	private static byte[] serializeKnowledgeBase(KnowledgeBase knowledgeBase)
			throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DroolsObjectOutputStream doos = new DroolsObjectOutputStream(baos);
		doos.writeObject(knowledgeBase);
		doos.flush();
		doos.close();

		byte[] serializedKnowledgeBase = baos.toByteArray();
		return serializedKnowledgeBase;
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public RuleStorage save(Serializable knowledgeBase, Map<String, String> map) {

		RuleStorage ruleStorage = new RuleStorage();

		try {
			byte[] bytes = serializeKnowledgeBase((KnowledgeBase) knowledgeBase);

			ruleStorage.setRuleBase(bytes);
			ruleStorage.setCreatedOn(Calendar.getInstance());
			ruleStorage.setDescription(map.get("description"));
			ruleStorage.setEnabled(Boolean.TRUE);
			ruleStorage.setUpdatedOn(Calendar.getInstance());
			getEntityManager().persist(ruleStorage);
			getEntityManager().flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ruleStorage;

	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<RuleStorage> getRuleFactory() {
		@SuppressWarnings("unchecked")
		List<RuleStorage> ruleStorageList = getEntityManager().createQuery(
				"select rs FROM RuleStorage as rs ").getResultList();

		return ruleStorageList;
	}

}
