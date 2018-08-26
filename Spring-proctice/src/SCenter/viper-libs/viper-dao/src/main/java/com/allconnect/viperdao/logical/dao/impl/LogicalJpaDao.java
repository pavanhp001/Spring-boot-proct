package com.A.Vdao.logical.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class LogicalJpaDao {
	@PersistenceContext(unitName="logical_pu")
    public EntityManager entityManager;

	public LogicalJpaDao(){
		
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
