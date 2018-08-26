 


package com.AL.ui.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.AL.ui.dao.DispositionCategoryDao;
import com.AL.ui.domain.sales.DispositionCategory;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

@Component
public class DispositionCategoryDaoImpl extends BaseTransactionalJpaDao implements DispositionCategoryDao {

	private static final Logger logger = Logger.getLogger(DispositionCategoryDaoImpl.class);

	private static final long TIME_IN_CACHE = 1000 * 60 * 60 * 8; 

    private static final String DISPOSITION_CATEGORY_PREFIX = "DISPOSITION_CATEGORY#";
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<DispositionCategory> getDispositionCategories() {
		List<DispositionCategory> cachedList = getDispositionCategoriesFromCache();
		if (cachedList != null) {
			return cachedList;
		}
		if (getEntityManager() != null) {
			try {
				
				StringBuilder sb = new StringBuilder();
				sb.append("select u FROM DispositionCategory as u ");
				  
				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getResultList();

				if ((obj != null) && (obj instanceof List )) {
					@SuppressWarnings("unchecked")
					List<DispositionCategory> dispositionCategoryList = (List<DispositionCategory>) obj;
					storeDispositionCategories(dispositionCategoryList);
					return dispositionCategoryList;
				}
			} catch (NoResultException nre) {
				logger.debug(nre.getMessage());

			}
		}

		throw new IllegalArgumentException("reason code not configured.");
	}
	 
	public void storeDispositionCategories(final List<DispositionCategory> dispositionCategories) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(DISPOSITION_CATEGORY_PREFIX, dispositionCategories, TIME_IN_CACHE);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearCachedOrder() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(DISPOSITION_CATEGORY_PREFIX);

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<DispositionCategory> getDispositionCategoriesFromCache() {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(DISPOSITION_CATEGORY_PREFIX);

			if ((o != null) && (o instanceof List)) {
				return (List<DispositionCategory>) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
