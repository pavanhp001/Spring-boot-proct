package com.A.ui.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.ui.dao.WebLookupDao;
import com.A.ui.domain.WebLookup;
import com.A.ui.domain.WebLookupCollection;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

@Component
public class WebLookupDaoImpl extends BaseTransactionalJpaDao implements
		WebLookupDao {

	private static final Logger logger = Logger
			.getLogger(WebLookupDaoImpl.class);
	private static final long TIME_IN_CACHE = 1000 * 60 * 60 * 8; // 8 hours
	
	private enum CONTEXT_NAME {
		countries("1"), //
		states("2"), //
		nameprefix("3"), //
		namesuffix("4"), //
		unittype("5"), //
		rentown("6"),//
		serviceaddresstype("7");//

		private String key;

		private CONTEXT_NAME(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection findCountries() {

		return find(CONTEXT_NAME.countries.getKey());
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection findUSStates() {

		return find(CONTEXT_NAME.states.getKey());
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection findNamePrefix() {

		return find(CONTEXT_NAME.nameprefix.getKey());
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection findNameSuffix() {

		return find(CONTEXT_NAME.namesuffix.getKey());
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection findUnitType() {

		return find(CONTEXT_NAME.unittype.getKey());
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection findRentOwn() {

		return find(CONTEXT_NAME.rentown.getKey());
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection findServiceAddressType() {

		return find(CONTEXT_NAME.serviceaddresstype.getKey());
	}

	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public WebLookupCollection find(final String context) {

		boolean foundInCache = false; // TODO: Look into Cache for Lookup Values
		WebLookupCollection listOfLookupValues = new WebLookupCollection();
		listOfLookupValues = getWebLookupValuesFromCache(context);
		if (listOfLookupValues != null) {
			foundInCache = true;
		}
		if (!foundInCache) {
			listOfLookupValues = new WebLookupCollection();
			logger.info("loading_lookup");
			String selectQuery = "select wl FROM WebLookup as wl where context = "+ context+" order by  wl.description ASC";
			/*if(context.equals("6")){
				selectQuery = "select wl FROM WebLookup as wl where context = "+ context+" order by  wl.description ASC";
			}*/
			List<WebLookup> lookupListFromDB = getEntityManager()
					.createQuery(selectQuery).getResultList();

			listOfLookupValues.setLookupList(lookupListFromDB);
			//Place Values Into Cache
			storeWebLookupValues(context, listOfLookupValues);
		}
		return listOfLookupValues;
	}
	
	public void storeWebLookupValues(final String context, final WebLookupCollection listOfLookupValues) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(context, listOfLookupValues, TIME_IN_CACHE);
		} 
		catch (CacheException e) {
			// TODO Auto-generated catch block
			logger.warn("Error_in_storeWebLookupValues",e);
		}
	}

	public WebLookupCollection getWebLookupValuesFromCache(final String context) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(context);

			if ((o != null)) {
				return (WebLookupCollection) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			logger.warn("Error_in_getWebLookupValuesFromCache",e);
		}

		return null;

	}
	
	public void clearCachedOrder(final String context) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.remove(context);

		} catch (CacheException e) {
			// TODO Auto-generated catch block
			logger.warn("Error_in_clearCachedOrder",e);
		}

	}

}