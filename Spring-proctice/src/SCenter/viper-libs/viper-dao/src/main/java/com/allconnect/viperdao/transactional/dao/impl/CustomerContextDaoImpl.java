package com.A.Vdao.transactional.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.CustomerContext;
import com.A.Vdao.dao.CustomerContextDao;

@Component
public class CustomerContextDaoImpl extends BaseTransactionalJpaDao implements CustomerContextDao {

    private static final Logger logger = Logger.getLogger(CustomerContextDaoImpl.class);

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(List<CustomerContext> ctxList) {
	logger.info("Executing customer context save");
	long start = System.currentTimeMillis();
	for (CustomerContext ctx : ctxList) {
	    if (ctx.getId() == 0) {
		getEntityManager().persist(ctx);
	    }
	    else {
		getEntityManager().merge(ctx);
	    }
	}
	logger.info("Customer context save took : " + (System.currentTimeMillis() - start) + "ms");
    }

}
