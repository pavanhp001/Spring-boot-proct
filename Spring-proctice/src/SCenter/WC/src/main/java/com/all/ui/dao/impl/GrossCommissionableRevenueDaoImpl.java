package com.A.ui.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.productResults.util.Utils;
import com.A.ui.dao.GrossCommissionableRevenueDao;
import com.A.ui.domain.GrossCommissionableRevenue;



@Component
public class GrossCommissionableRevenueDaoImpl<T> extends BaseTransactionalJpaDao implements GrossCommissionableRevenueDao {
	
	private static final Logger logger = Logger.getLogger(GrossCommissionableRevenueDaoImpl.class);
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public int getRevCommissionBySpIdStable(String spIdStable) {

			int revCommission = 0;

			try {
				logger.info("retrieveGCR");
				Object obj = getEntityManager().createQuery(
						"select gcr.revCommission FROM GrossCommissionableRevenue as gcr where gcr.spIdStable = '"
								+ spIdStable + "'").getSingleResult();

				if (obj != null) {
					revCommission = (Integer) obj;
				}
			} catch (NoResultException nre) {
				return revCommission;
			}catch (Exception e) {
				logger.error(e);
			}

			return revCommission;
		}
	
	
	/**
	 * 
	 * Get all GrossRevenueCommissions from the input of productExternalIds(or spIdStable value).
	 * It returns Map<String, GrossCommissionableRevenue> object. Here key is productExternalIds/spIdStable
	 * and value is GrossCommissionableRevenue object.
	 * 
	 * @param spIdStable
	 * @return revenueCommissions
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Map<String, Float> getRevCommissionsBySpIdStables(List<String> spIdStable) {
		Map<String, Float> revenueCommissionsMap = new HashMap<String, Float>();
		try {
			EntityManager eManager = getEntityManager();
			String queryVal = "select gcr.spIdStable, gcr.points FROM GrossCommissionableRevenue as gcr where gcr.spIdStable in (:spIdStables)";
			Query query =  eManager.createQuery(queryVal);
			query.setParameter("spIdStables", spIdStable);
			
			List<Object[]> commissionableRevenuesList = (List<Object[]>)query.getResultList();
			for(Object[] revenue: commissionableRevenuesList){
				if(revenue[0] != null && revenue[1] != null){
					String productExtId = (String)revenue[0];
					Float firstName = (Float)revenue[1];
					revenueCommissionsMap.put(productExtId, firstName);
				}
			}
			
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return revenueCommissionsMap;
		
	}
	
	/**
	 * 
	 * Get all GrossRevenueCommissions.
	 * It returns Map<String, GrossCommissionableRevenue> object. Here key is productExternalIds/spIdStable
	 * and value is GrossCommissionableRevenue object.
	 * 
	 * @return revenueCommissions
	 */
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Map<String, Float> getAllRevenueCommissions() {

		Map<String, Float> revenueCommissionsMap = new HashMap<String, Float>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select gcr.spIdStable, gcr.points FROM GrossCommissionableRevenue as gcr");
			Query query = getEntityManager().createQuery(sb.toString());
			List<Object[]> commissionableRevenuesList = (List<Object[]>)query.getResultList();
			for(Object[] revenue: commissionableRevenuesList){
				if(revenue[0] != null && revenue[1] != null){
					String productExtId = (String)revenue[0];
					Float firstName = (Float)revenue[1];
					revenueCommissionsMap.put(productExtId, firstName);
				}
			}
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return revenueCommissionsMap;
	}
	
}