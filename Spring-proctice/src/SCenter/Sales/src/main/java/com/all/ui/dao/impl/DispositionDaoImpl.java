 


package com.AL.ui.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.AL.ui.dao.DispositionDao;
import com.AL.ui.domain.sales.Disposition;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;



@Component
public class DispositionDaoImpl extends BaseTransactionalJpaDao implements DispositionDao {

	private static final Logger logger = Logger.getLogger(DispositionDaoImpl.class);
	private static final long TIME_IN_CACHE = 1000 * 60 * 60 * 8; 

    private static final String DISPOSITION_PREFIX = "DISPOSITION#";
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<Disposition> getDispositions() {
		List<Disposition> cachedList = getDispositionsFromCache();
		if (cachedList != null) {
			return cachedList;
		}
		if (getEntityManager() != null) {
			try {

				StringBuilder sb = new StringBuilder();
				sb.append("select u FROM Disposition as u order by SEQUENCE_ORDER");
				  
				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getResultList();

				if ((obj != null) && (obj instanceof List )) {
					@SuppressWarnings("unchecked")
					List<Disposition> dispositionList = (List<Disposition>) obj;
					storeDispositions(dispositionList);
					return dispositionList;
				}
			} catch (NoResultException nre) {
				logger.debug(nre.getMessage());

			}
		}

		throw new IllegalArgumentException("disposition code not configured.");
	}
	 
	public void storeDispositions(final List<Disposition> dispositions) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(DISPOSITION_PREFIX, dispositions, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearCachedOrder() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(DISPOSITION_PREFIX);

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Disposition> getDispositionsFromCache() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(DISPOSITION_PREFIX);

			if ((o != null) && (o instanceof List)) {
				return (List<Disposition>) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
