package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;

import com.A.ui.dao.CallflowDao;
import com.A.ui.domain.Callflow;

@Component
public class CallflowDaoImpl extends BaseTransactionalJpaDao implements
CallflowDao {

	private static final Logger logger = Logger
			.getLogger(CallflowDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Callflow get(Long referrerId) {

		Callflow callFlow = null;
		try {
			logger.info("Callflow_search");
			Object obj = getEntityManager().createQuery(
				"select p FROM Callflow as p where referrerId = "
						+ referrerId.longValue()).getSingleResult();

		if ((obj != null)&&(obj instanceof Callflow)) {
			callFlow = (Callflow) obj;
		} 
		} 	catch (NoResultException nre) {
			return null;
		}

		return callFlow;
	}
	

}
