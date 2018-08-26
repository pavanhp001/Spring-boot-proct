package com.A.Vdao.transactional.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.SalesOrderContext;
import com.A.Vdao.dao.SalesOrderContextDao;

@Component
public class SalesOrderContextDaoImpl extends BaseTransactionalJpaDao implements SalesOrderContextDao {

    private static final Logger logger = Logger.getLogger(SalesOrderContextDaoImpl.class);

    /**
     * A method to save SalesOrderContext
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void save(Set<SalesOrderContext> socList) {
	if (socList != null && !socList.isEmpty()) {
	    logger.info("Saving SalesOrderContext");
	    long start = System.currentTimeMillis();
	    for (SalesOrderContext soc : socList) {
		if (soc.getId() > 0) {
		    getEntityManager().merge(soc);
		}
		else {
		    getEntityManager().persist(soc);
		}

	    }
	    getEntityManager().flush();
	    logger.info("Salescontext save took : " + (System.currentTimeMillis() - start) + "ms");
	}

    }

}
