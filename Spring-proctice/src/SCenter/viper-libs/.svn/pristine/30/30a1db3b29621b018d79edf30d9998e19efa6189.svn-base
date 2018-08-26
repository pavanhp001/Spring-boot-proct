package com.A.Vdao.transactional.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.A.enums.LogLevelEnum;
import com.A.util.LogUtil;
import com.A.V.beans.entity.ProductPromotion;
import com.A.Vdao.dao.PromotionDao;

@Component
public class PromotionDaoImpl extends BaseTransactionalJpaDao implements PromotionDao {

    private static final Logger logger = Logger.getLogger(PromotionDaoImpl.class);

    // @PersistenceContext
    // private EntityManager entityManager;

    private static final String FIND_PROMOTION_ITEM_BY_ID = "SELECT p FROM ProductPromotion p WHERE p.externalId = :externalId";

    public ProductPromotion findPromotionById(final String id) {

	logger.info("Searching for ProductPromotion External ID : " + id);
	long start = System.currentTimeMillis();
	if (entityManager != null) {
	    try {
		Query query = getEntityManager().createQuery(FIND_PROMOTION_ITEM_BY_ID);
		query.setParameter("externalId", id);

		List<ProductPromotion> piList = query.getResultList();

		if ((piList != null) && (piList.size() > 0)) {
		    logger.info("findPromotionById took : " + (System.currentTimeMillis() - start) + "ms");
		    return piList.get(0);
		}
	    }
	    catch (NoResultException nre) {
		logger.error("NoResultException thrown...", nre);
	    }
	}

	return null;
    }

    public EntityManager getEntityManager() {
	return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

}
