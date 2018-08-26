package com.AL.ui.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ScoreBaseTransactionalJpaDao 
{
	@PersistenceContext(unitName="transactional_pu_score")
    public EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
