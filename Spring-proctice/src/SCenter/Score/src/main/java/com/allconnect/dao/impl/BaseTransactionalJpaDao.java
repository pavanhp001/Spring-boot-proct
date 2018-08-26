package com.AL.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseTransactionalJpaDao
{
	@PersistenceContext(unitName="transactional_pu")
    public EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
