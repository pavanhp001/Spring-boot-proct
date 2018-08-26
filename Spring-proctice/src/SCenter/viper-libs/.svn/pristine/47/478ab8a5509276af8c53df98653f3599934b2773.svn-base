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
import com.A.V.beans.entity.ReasonBean;
import com.A.Vdao.dao.ReasonDao;

@Component
public class ReasonDaoImpl extends BaseTransactionalJpaDao implements ReasonDao {

	private static final Logger logger = Logger.getLogger(ReasonDaoImpl.class);
	private static final String DELETE_REASONS = "DELETE FROM ReasonBean rb WHERE rb.lineItemId = :lineItemId";
	public static final String GET_REASONS= "select r FROM ReasonBean r WHERE r.lineItemId = :lineItemId order by priorityId asc";
	public ReasonDaoImpl() {
		super();
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
	public void persistAll(final Set<ReasonBean> reasonBeanList) {
		logger.info("Executing save all ReasonBeans");
		long start = System.currentTimeMillis();
		if (reasonBeanList != null && !reasonBeanList.isEmpty()) {
			try {
				for (ReasonBean bean : reasonBeanList) {
				    getEntityManager().persist(bean);
				}
				getEntityManager().flush();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Saving all ReasonBeans took : " + (System.currentTimeMillis() - start) + "ms");
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
	public void remove(Long lineItemId) {
		logger.info("Executing remove ReasonBeans for lineItemId= "+lineItemId);
		long start = System.currentTimeMillis();
		try {
			if (getEntityManager() != null) {
				Query deleteReasonQuery = getEntityManager().createQuery(DELETE_REASONS);
				deleteReasonQuery.setParameter("lineItemId", lineItemId);
				deleteReasonQuery.executeUpdate();
				getEntityManager().flush();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Removing all ReasonBeans took : " + (System.currentTimeMillis() - start) + "ms");
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Set<ReasonBean> getReasonBeans(long lineItemId) {
		if (getEntityManager() != null) {
			try{
				Query query = getEntityManager().createQuery(GET_REASONS);
				query.setParameter("lineItemId", lineItemId);
				List<ReasonBean> reasonsList =  query.getResultList();
				if(reasonsList != null){
				    Set<ReasonBean> reasonsSet = new HashSet<ReasonBean>(reasonsList);
				    return reasonsSet;
				}
				
			}catch (NoResultException nre){
				logger.debug(nre.getMessage());
			}
		}
		return null;
	}

}
