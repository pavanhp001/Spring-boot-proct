package com.AL.ui.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;

import com.AL.ui.dao.DiscoveryExclusionReferrersDao;
import com.AL.ui.vo.DiscoveryExclusionReferrersVO;

@Component
public class DiscoveryExclusionReferrersDaoImpl extends BaseTransactionalJpaDao implements DiscoveryExclusionReferrersDao {

	private static final Logger logger = Logger.getLogger(ConfirmReferrersDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public DiscoveryExclusionReferrersVO getDiscoveryExclusionReferrers(Long referrerId) {

		DiscoveryExclusionReferrersVO discoveryExclusionReferrersVO = new DiscoveryExclusionReferrersVO();

		try {
			logger.info("Webflow_search");
			Object obj = getEntityManager().createQuery(
					"select der FROM DiscoveryExclusionReferrersVO as der where referrerId = "
							+ referrerId).getSingleResult();

			if (obj != null) {
				discoveryExclusionReferrersVO = (DiscoveryExclusionReferrersVO) obj;
			}
		} 
		catch (NoResultException nre) {
			return null;
		}

		return discoveryExclusionReferrersVO;
	}

}
