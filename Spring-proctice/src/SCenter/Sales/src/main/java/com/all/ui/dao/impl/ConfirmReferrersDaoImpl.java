package com.AL.ui.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;

import com.AL.ui.dao.ConfirmReferrersDao;
import com.AL.ui.vo.ConfirmReferrersVO;

@Component
public class ConfirmReferrersDaoImpl extends BaseTransactionalJpaDao implements ConfirmReferrersDao {

	private static final Logger logger = Logger.getLogger(ConfirmReferrersDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public ConfirmReferrersVO getConfirmReferrers(Long referrerId) {

		ConfirmReferrersVO confirmReferrersVO = new ConfirmReferrersVO();

		try {
			logger.info("Webflow_search");
			Object obj = getEntityManager().createQuery(
					"select crv FROM ConfirmReferrersVO as crv where referrerId = "
							+ referrerId).getSingleResult();

			if (obj != null) {
				confirmReferrersVO = (ConfirmReferrersVO) obj;
			}
		} 
		catch (NoResultException nre) {
			return confirmReferrersVO;
		}

		return confirmReferrersVO;
	}

}
