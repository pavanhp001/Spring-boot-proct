package com.A.Vdao.transactional.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.Vdao.dao.SelectedFeatureValueDao;

@Component
public class SelectedFeatureValueDaoImpl extends BaseTransactionalJpaDao implements SelectedFeatureValueDao {

    private static final Logger logger = Logger.getLogger(SelectedFeatureValueDaoImpl.class);
    public SelectedFeatureValueDaoImpl() {
	super();
    }

    public void persist(final Set<SelectedFeatureValue> featureValueBeanList) {
	logger.info("Executing selected feature save");
	long start = System.currentTimeMillis();
	if (featureValueBeanList != null && !featureValueBeanList.isEmpty()) {
	    for (SelectedFeatureValue bean : featureValueBeanList) {
		if (bean != null) getEntityManager().persist(bean);
	    }
	}
	logger.info("Selected feature save took : " + (System.currentTimeMillis() - start) + "ms");
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
    public void merge(final Set<SelectedFeatureValue> featureValueBeanList) {
	logger.info("Executing selected feature merge");
	long start = System.currentTimeMillis();
	if (featureValueBeanList != null && !featureValueBeanList.isEmpty()) {
	    for (SelectedFeatureValue bean : featureValueBeanList) {
		if (bean != null) getEntityManager().merge(bean);
	    }
	}
	logger.info("Selected feature merge took : " + (System.currentTimeMillis() - start) + "ms");
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
    public void persistAll(final Set<SelectedFeatureValue> featureValueBeanList) {
	logger.info("Executing save all selected feature");
	long start = System.currentTimeMillis();
	if (featureValueBeanList != null && !featureValueBeanList.isEmpty()) {
	    try {
		for (SelectedFeatureValue bean : featureValueBeanList) {
		    if (bean.getId() == 0) {
			getEntityManager().persist(bean);
		    }
		    else {
			getEntityManager().merge(bean);
		    }
		}
		getEntityManager().flush();
	    }
	    catch (Exception e) {
		e.printStackTrace();
	    }
	}
	logger.info("Saving all selected features took : " + (System.currentTimeMillis() - start) + "ms");
    }

    public void remove(Set<SelectedFeatureValue> featureBeanList) {
	try {
	    for (SelectedFeatureValue bean : featureBeanList) {

		getEntityManager().remove(bean);

	    }
	    getEntityManager().flush();
	}
	catch (Exception e) {
	    e.printStackTrace();
	}

    }

}
