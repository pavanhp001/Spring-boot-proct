package com.A.ui.dao.impl;

import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.ConfigDao;
import com.A.ui.domain.SystemConfig;
import com.A.ui.service.config.ConfigRepo;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

@Component
public class ConfigDaoDefault extends BaseTransactionalJpaDao implements
		ConfigDao {
	private static final String FIND_SYSPROPS_BY_NAME_CTX = "SELECT sysProp FROM SystemConfig sysProp WHERE sysProp.name = :name "
			+ "and sysProp.context = :context";

	private static final String FIND_SYSPROPS_BY_CTX = "SELECT sysProp FROM SystemConfig sysProp WHERE sysProp.context = :context";

	private static final String FETCH_ALL_SYSPROPS = "SELECT sysProp FROM SystemConfig sysProp";

	private static final String NAME = "name";
	private static final String CONTEXT = "context";
	private static final Logger logger = Logger
			.getLogger(ConfigDaoDefault.class);
	private static final long TIME_IN_CACHE = 1000 * 60 * 60 * 8; // 8 hours

	/**
	 * factory constructor.
	 */
	public ConfigDaoDefault() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<SystemConfig> findByContext(final String context) {
		 List<SystemConfig> sysPropsList = getFindByContextValuesFromCache(context);
		if (sysPropsList != null) {
			return sysPropsList;
		}
		if (getEntityManager() != null) {
			try {
				Query query = getEntityManager().createQuery(
						FIND_SYSPROPS_BY_CTX);
				query.setParameter(CONTEXT, context);
				sysPropsList = (List<SystemConfig>) query
						.getResultList();
				storeFindByContextValues(context, sysPropsList);
				return sysPropsList;
			} catch (NoResultException nre) {
				logger.warn("Error_in_findByContext",nre);

			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public SystemConfig findByNameAndContext(final String name,
			final String context) {
		if (getEntityManager() != null) {
			try {
				Query query = getEntityManager().createQuery(
						FIND_SYSPROPS_BY_NAME_CTX);
				query.setParameter(NAME, name);
				query.setParameter(CONTEXT, context);
				SystemConfig sysProperties = (SystemConfig) query
						.getSingleResult();

				return sysProperties;

			} catch (NoResultException nre) {
				logger.warn("Error_in_findByNameAndContext",nre);
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sync() {
		HashMap<String, SystemConfig> propsMap = new HashMap<String, SystemConfig>();

		if (getEntityManager() != null) {
			try {
				Query query = getEntityManager()
						.createQuery(FETCH_ALL_SYSPROPS);
				List<SystemConfig> sysPropsList = (List<SystemConfig>) query
						.getResultList();
				logger.debug("Total properties loaded : " + sysPropsList.size());
				String propertyName = null;
				String propertyContext = null;

				for (SystemConfig systemProperty : sysPropsList) {
					propertyName = systemProperty.getName();
					propertyContext = systemProperty.getContext();

					if (propertyName != null && propertyContext != null) {
						logger.debug(propertyName + " : "
								+ systemProperty.getValue());
						propsMap.put(propertyContext + "." + propertyName,
								systemProperty);
					}
				}

				// Update the In-Memory system properties &
				// make sure the DB actually returned valid records.
				if (propsMap.size() > 0) {
					ConfigRepo.setSystemConfig(propsMap);
				} else {
					String errMsg = "DB returned no system properties on re-sync.";
					logger.info(errMsg);
				}
				return;
			} catch (Exception nre) {
				logger.error(
						"Exception thrown while loading system properties ",
						nre);
			}
		}

		return;
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void update(List<SystemConfig> sysConfigList) {
          for (SystemConfig sysConfig : sysConfigList) {
                try {
                      getEntityManager().merge(sysConfig);
                } catch (Exception e) {
                	logger.warn("Error_in_update",e);
                }
          }
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void update(SystemConfig sysConfig) {
          if (sysConfig == null) {
                return;
          }

          try {
                getEntityManager().merge(sysConfig);
          } catch (Exception e) {
        	  logger.warn("Error_in_update",e);
          }
          
          getEntityManager().flush();
          
    }
    
    public void storeFindByContextValues(final String context, final List<SystemConfig> sysPropsList) {

		// Use the cache manager to create the default cache
		try {
			Cache c = CacheManager.getInstance().getCache();
			c.store(context, sysPropsList, TIME_IN_CACHE);
		} 
		catch (CacheException e) {
			// TODO Auto-generated catch block
			logger.warn("Error_in_storeFindByContextValues",e);
		}
	}

	public List<SystemConfig> getFindByContextValuesFromCache(final String context) {

		try {
			Cache c = CacheManager.getInstance().getCache();

			Object o = c.retrieve(context);
			if ((o != null)) {
				return (List<SystemConfig>) o;
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			logger.warn("Error_in_getFindByContextValuesFromCache",e);
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
