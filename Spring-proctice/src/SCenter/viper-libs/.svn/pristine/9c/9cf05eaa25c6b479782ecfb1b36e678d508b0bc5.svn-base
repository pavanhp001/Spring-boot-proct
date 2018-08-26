package com.A.Vdao.transactional.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.CustomerSurvey;
import com.A.Vdao.dao.CustomerSurveyDao;

@Component("customerSurveyDao")
public class CustomerSurveyDaoImpl extends BaseTransactionalJpaDao implements CustomerSurveyDao {

    private static final Logger logger = Logger.getLogger(CustomerSurveyDaoImpl.class);

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void saveSurveys(Set<CustomerSurvey> custSurveySet) {
	logger.info("Saving customer survey");
	long start = System.currentTimeMillis();
	for (CustomerSurvey survey : custSurveySet) {
	    if (survey.getId() == 0) {
		getEntityManager().persist(survey);
	    }
	    else {
		getEntityManager().merge(survey);
	    }
	    getEntityManager().flush();
	}
	logger.info("Customer survey save took : " + (System.currentTimeMillis() - start) + "ms");
    }

}
