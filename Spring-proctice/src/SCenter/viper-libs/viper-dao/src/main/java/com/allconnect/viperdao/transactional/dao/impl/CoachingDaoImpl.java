package com.A.Vdao.transactional.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.V.beans.entity.CoachingBean;
import com.A.Vdao.dao.CoachingDao;

@Component
public class CoachingDaoImpl extends BaseTransactionalJpaDao implements CoachingDao {

	private static final Logger logger = Logger.getLogger(CoachingDaoImpl.class);
	private static final String DELETE_COACHINGS = "DELETE FROM CoachingBean cb WHERE cb.lineItemId = :lineItemId";
	public static final String GET_COACHINGS= "select c FROM CoachingBean c WHERE c.lineItemId = :lineItemId";
	public CoachingDaoImpl() {
		super();
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
	public void persistAll(final Set<CoachingBean> coachingBeanList) {
		logger.info("Executing save all CoachingBeans");
		long start = System.currentTimeMillis();
		if (coachingBeanList != null && !coachingBeanList.isEmpty()) {
			try {
				for (CoachingBean bean : coachingBeanList) {
					getEntityManager().persist(bean);
				}
				getEntityManager().flush();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Saving all CoachingBeans took : " + (System.currentTimeMillis() - start) + "ms");
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
	public void remove(Long lineItemId) {
		logger.info("Executing remove CoachingBeans for lineItemId= "+lineItemId);
		long start = System.currentTimeMillis();
		try {
			if (getEntityManager() != null) {
				Query deleteCoachingQuery = getEntityManager().createQuery(DELETE_COACHINGS);
				deleteCoachingQuery.setParameter("lineItemId", lineItemId);
				deleteCoachingQuery.executeUpdate();
				getEntityManager().flush();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Removing all CoachingBeans took : " + (System.currentTimeMillis() - start) + "ms");
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Set<CoachingBean> getCoachingBeans(long lineItemId) {
		if (getEntityManager() != null) {
			try{
				Query query = getEntityManager().createQuery(GET_COACHINGS);
				query.setParameter("lineItemId", lineItemId);
				List<CoachingBean> coachingsList =  query.getResultList();
				if(coachingsList != null){
					Set<CoachingBean> coachingsSet = new HashSet<CoachingBean>(coachingsList);
				    return coachingsSet;
				}
				
			}catch (NoResultException nre){
				logger.debug(nre.getMessage());
			}
		}
		return null;
	}

}
