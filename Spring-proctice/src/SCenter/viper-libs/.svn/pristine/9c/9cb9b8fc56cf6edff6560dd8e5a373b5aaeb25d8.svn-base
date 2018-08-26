package com.A.Vdao.transactional.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseTransactionalJpaDao
{
	@PersistenceContext(unitName="transactional_pu")
    public EntityManager entityManager;

	public BaseTransactionalJpaDao(){}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Helper method to find database name from database metadata.
	 *
	 * TODO this code is not portable and hibernate planned to remove this deprecated
	 * connection() method from version 4.x. Find an alternative way to get db metadata info
	 * @return
	 */
//	public String getDatabaseName(){
//		String dbName = "";
//		try {
//			DatabaseMetaData dbmd = ((Session)entityManager.getDelegate()).connection().getMetaData();
//			dbName = dbmd.getDatabaseProductName();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return dbName;
//	}
}
