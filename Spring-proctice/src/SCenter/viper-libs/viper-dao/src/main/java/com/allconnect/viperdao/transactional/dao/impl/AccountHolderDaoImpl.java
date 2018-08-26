package com.A.Vdao.transactional.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.AccountHolder;
import com.A.Vdao.dao.AccountHolderDao;

@Component
public class AccountHolderDaoImpl extends BaseTransactionalJpaDao implements AccountHolderDao {

	private Logger logger = Logger.getLogger(AccountHolderDaoImpl.class);
	
	private static final String FIND_ACC_HOLDER_BY_ORDER_EXTERNAL_ID = "SELECT acc FROM AccountHolder acc WHERE acc.orderExternalId = :orderExternalId";
	 
	@SuppressWarnings("unchecked")
	public List<AccountHolder> getAllAccountHoldersByOrderExternalId(long orderExternalId) {
		List<AccountHolder> accountHolders = new ArrayList<AccountHolder>();
		long start = System.currentTimeMillis();
		Query q = getEntityManager().createQuery(FIND_ACC_HOLDER_BY_ORDER_EXTERNAL_ID);
		//q.setParameter(1, orderExternalId);
		q.setParameter("orderExternalId", orderExternalId);
		accountHolders = (List<AccountHolder>)q.getResultList();
		logger.info("getAllAccountHoldersByOrderExternalId took: " + (System.currentTimeMillis() - start) + "ms");
		return accountHolders;
	}
	
	@Transactional(value = "transactional")
	public void persist(AccountHolder accountHolder) {
		logger.debug("Persisting accountHolder in DAO");
		long start = System.currentTimeMillis();
		if(accountHolder.getId() == 0) {
			getEntityManager().persist(accountHolder);
		} else {
			getEntityManager().merge(accountHolder);
		}
		getEntityManager().flush();
		logger.info("persist accountHolder took: " + (System.currentTimeMillis() - start) + "ms");
	}
	
	@Transactional(value = "transactional")
	public void persistAll(List<AccountHolder> accountHolders) {
		logger.debug("Persisting all accountHolder in DAO");
		long start = System.currentTimeMillis();
		for(AccountHolder accountHolder : accountHolders) {
			if(accountHolder.getId() == 0) {
				getEntityManager().persist(accountHolder);
			} else {
				getEntityManager().merge(accountHolder);
			}
			getEntityManager().flush();
		}
		logger.info("persist all accountHolder took: " + (System.currentTimeMillis() - start) + "ms");
	}
	
	public AccountHolder getAccountHolder(long id) {
		AccountHolder acctHolder = getEntityManager().find(AccountHolder.class, id);
		return acctHolder;
	}

}
