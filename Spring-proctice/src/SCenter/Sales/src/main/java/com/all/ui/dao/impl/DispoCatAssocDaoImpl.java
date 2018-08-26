 


package com.AL.ui.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.AL.ui.dao.DispoCatAssocDao;
import com.AL.ui.domain.sales.DispoCatAssoc;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

@Component
public class DispoCatAssocDaoImpl extends BaseTransactionalJpaDao implements DispoCatAssocDao {

	private static final Logger logger = Logger.getLogger(DispoCatAssocDaoImpl.class);

	private static final long TIME_IN_CACHE = 1000 * 60 * 60 * 8;

    private static final String DISPO_CAT_ASSOC_PREFIX = "DISPO_CAT_ASSOC#";
    
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<DispoCatAssoc> getDispositionCategoryAssociations() {
		List<DispoCatAssoc> cachedList = getDispoCatAssocFromCache();
		if (cachedList != null) {
			return cachedList;
		}
		if (getEntityManager() != null) {
			try {

				StringBuilder sb = new StringBuilder();
				sb.append("select u FROM DispoCatAssoc as u ");
				  
				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getResultList();

				if ((obj != null) && (obj instanceof List )) {
					@SuppressWarnings("unchecked")
					List<DispoCatAssoc> dispoCatAssocList = (List<DispoCatAssoc>) obj;

					storeDispoCatAssoc(dispoCatAssocList);
					return dispoCatAssocList;
				}
			} catch (NoResultException nre) {
				logger.debug(nre.getMessage());

			}
		}

		throw new IllegalArgumentException("reason code not configured.");
	}
	 
	public void storeDispoCatAssoc(final List<DispoCatAssoc> dispoCatAssoc) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(DISPO_CAT_ASSOC_PREFIX, dispoCatAssoc, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearCachedOrder() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(DISPO_CAT_ASSOC_PREFIX);

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<DispoCatAssoc> getDispoCatAssocFromCache() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(DISPO_CAT_ASSOC_PREFIX);

			if ((o != null) && (o instanceof List)) {
				return (List<DispoCatAssoc>) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
