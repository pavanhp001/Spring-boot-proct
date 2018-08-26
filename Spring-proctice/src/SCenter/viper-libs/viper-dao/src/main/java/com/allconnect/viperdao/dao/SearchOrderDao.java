package com.A.Vdao.dao;

import java.util.Map;

import javax.persistence.EntityManager;

import com.A.Vdao.dao.impl.SearchCriteria;

public interface SearchOrderDao {
	EntityManager getEntityManager();

	void setEntityManager(EntityManager entityManager);

	public Map<String,Object> searchOrder(final SearchCriteria criteria,final int offset,final int totalRows);
}
