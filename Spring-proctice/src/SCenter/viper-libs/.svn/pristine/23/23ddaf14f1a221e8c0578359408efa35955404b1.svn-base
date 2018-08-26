package com.A.Vdao.transactional.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.CustomerAttribute;
import com.A.Vdao.dao.CustomerAttributeDao;

@Component("customerAttributeDao")
public class CustomerAttributeDaoImpl extends BaseTransactionalJpaDao implements CustomerAttributeDao {

    private static final Logger logger = Logger.getLogger(CustomerAttributeDaoImpl.class);

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void saveCustomerAttribute(Set<CustomerAttribute> custAttributes) {
	logger.info("Saving customer attributes");
	long start = System.currentTimeMillis();
	for (CustomerAttribute attrib : custAttributes) {
	    if (attrib.getId() == 0) {
		getEntityManager().persist(attrib);
	    }
	    else {
		getEntityManager().merge(attrib);
	    }
	    getEntityManager().flush();
	}
	logger.info("Customer attribute save took : " + (System.currentTimeMillis() - start) + "ms");
    }

}
