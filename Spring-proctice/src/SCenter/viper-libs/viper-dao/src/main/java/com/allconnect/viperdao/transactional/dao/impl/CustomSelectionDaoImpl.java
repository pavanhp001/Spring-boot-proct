package com.A.Vdao.transactional.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.CustomSelection;
import com.A.Vdao.dao.CustomSelectionDao;

@Component
public class CustomSelectionDaoImpl extends BaseTransactionalJpaDao implements CustomSelectionDao {

    private static final Logger logger = Logger.getLogger(CustomSelectionDaoImpl.class);

    @Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
    public void persist(Set<CustomSelection> selectionSet) {
	logger.info("Persisting Custom selection");
	long start = System.currentTimeMillis();
	if (selectionSet != null) {
	    for (CustomSelection selection : selectionSet) {
		if (selection.getId() == 0) {
		    getEntityManager().persist(selection);
		}
		else {
		    getEntityManager().merge(selection);
		}
	    }
	    getEntityManager().flush();
	}
	logger.info("Custom selection save took : " + (System.currentTimeMillis() - start) + "ms");
    }

}
