


package com.A.ui.dao.impl;


import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.SalesSessionDao;
import com.A.ui.domain.SalesSession;


@Component
public class SalesSessionDaoImpl extends BaseTransactionalJpaDao implements SalesSessionDao {

	private static final Logger logger = Logger.getLogger(SalesSessionDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void insertSalesSession(SalesSession ss) {
		logger.info("Inserting_data_into_SalesSession_table");
		try {
			getEntityManager().persist(ss);
			getEntityManager().flush();
			logger.info("Inserted_data_into_SalesSession_table");
		} catch (Exception e) {
			logger.warn("Error_in_insertSalesSession",e);
		}
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Long getSalesSessionId(Long dispositionId) {
		logger.info("Getting_SalesSessionId_from_SalesSession_table");
		if (getEntityManager() != null) {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("select MAX(u.salesSessionId) FROM SalesSession as u WHERE u.dispositionId = '"
						+ dispositionId + "'");

				Query q = getEntityManager().createQuery(sb.toString());

				List<Long> salesSessionIdList = q.getResultList();

				if (salesSessionIdList != null) {
					Long salesSessionId = salesSessionIdList.get(0);
					return salesSessionId;
				}
			} 
			catch (Exception e) {
				logger.warn("Error_in_getSalesSessionId",e);
                return null;
			}
		}
		throw new IllegalArgumentException("SalesSession code not configured.");
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void updateSalesSession(SalesSession ss) {
		try {
			getEntityManager().merge(ss);
			getEntityManager().flush();
			logger.info("Updated_SalesSession_table");
		} catch (Exception e) {
			logger.warn("Error_in_updateSalesSession",e);
		}
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public SalesSession getSalesSession(Long orderId, Long customerId) {
		logger.info("Getting_SalesSession_data_with_orderId"+ orderId + "_and_customerId"+customerId);
		try {
			if (getEntityManager() != null) {

				StringBuilder sb = new StringBuilder();
				sb.append("select ss FROM SalesSession as ss WHERE ss.orderId = '"
						+ orderId + "'" + "and ss.customerId = '" + customerId + "'");

				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getResultList();

				if (obj != null) {
					@SuppressWarnings("unchecked")
					List<SalesSession> salesSessionList = (List<SalesSession>) obj;
					SalesSession salesSession = salesSessionList.get(0);
					return salesSession;
				}

			} 
		}
		catch (Exception e) {
			logger.warn("Error_in_getSalesSession",e);
			return null;
		}
		return null;
	}
}
