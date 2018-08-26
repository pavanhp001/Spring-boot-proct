package com.A.ui.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.SalesCallDao;
import com.A.ui.dao.impl.BaseTransactionalJpaDao;
import com.A.ui.domain.SalesCall;

@Component
public class SalesCallDaoImpl extends BaseTransactionalJpaDao implements SalesCallDao {

	private static final Logger logger = Logger.getLogger(SalesCallDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void put(SalesCall salesCall) {
		try {
			logger.info("Inserting_data_into_SalesCall_table");
			getEntityManager().persist(salesCall);
		} catch (Exception e) {
			logger.warn("Error_in_put",e);
		}
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Long getSalesCallId(Long salesSessionId) {
		logger.info("SalesCall_data_with_salesSessionId : "+salesSessionId);
		if (getEntityManager() != null) {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("select MAX(u.salesCallId) FROM SalesCall as u WHERE u.salesSessionId = '"
						+ salesSessionId + "'");

				Query q = getEntityManager().createQuery(sb.toString());

				Object obj = q.getResultList();

				if (obj != null) {
					@SuppressWarnings("unchecked")
					List<Long> salesCallIdList = (List<Long>) obj;
					Long salesCallId = salesCallIdList.get(0);
					return salesCallId;
				}
			} 
			catch (Exception e) {
				logger.warn("Error_in_getSalesCallIdr",e);

			}
		}
		throw new IllegalArgumentException("disposition code not configured.");
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void updateSalesCallWithAmbDisconnectDateTime(long salesCallId, Calendar disconnectCal){
		logger.info("SalesCall_table_with_salesCallId"+salesCallId);
		if (getEntityManager() != null) {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE SalesCall SET disconnectTime = "+disconnectCal+" WHERE salesCallId= "+salesCallId+" ");
				Query q = getEntityManager().createQuery(sb.toString());
				q.executeUpdate();
			}
			catch (NoResultException nre) {
				logger.warn("Error_in_SalesCallDaoImpl",nre);
			}
		}
		throw new IllegalArgumentException("disposition code not configured.");
	}

}
