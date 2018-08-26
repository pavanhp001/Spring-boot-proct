package com.A.Vdao.transactional.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.FeatureBean;
import com.A.Vdao.dao.FeatureDao;

@Component
public class FeatureDaoImpl extends BaseTransactionalJpaDao implements FeatureDao {

    private static final Logger logger = Logger.getLogger(FeatureDaoImpl.class);

    public FeatureDaoImpl() {
	super();
    }

    /**
     * Checking existence of the Feature
     */
    public boolean isValidFeature(String externalId) {
	if (getEntityManager() != null) {

	    try {

		Query query = getEntityManager().createQuery("SELECT f FROM FeatureBean f WHERE f.externalId= :externalId");
		query.setParameter("externalId", externalId);
		Object obj = query.getSingleResult();

		if ((obj != null) && (obj instanceof FeatureBean)) {
		    return Boolean.TRUE;
		}

	    }
	    catch (NoResultException nre) {
		logger.warn(nre.getMessage());

	    }
	}
	return Boolean.FALSE;
    }

    /**
     * Checking existence of the feature group
     *
     * @param externalId
     * @return
     */
    public boolean isValidFeatureGroup(String externalId) {
	if (getEntityManager() != null) {

	    try {

		Query query = getEntityManager().createQuery("SELECT fg FROM FeatureGroupBean fg WHERE fg.externalId= :externalId");
		query.setParameter("externalId", externalId);
		Object obj = query.getSingleResult();

		if ((obj != null) && (obj instanceof FeatureBean)) {
		    return Boolean.TRUE;
		}

	    }
	    catch (NoResultException nre) {
		logger.warn(nre.getMessage());

	    }
	}
	return Boolean.FALSE;
    }

}
