package com.A.Vdao.transactional.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.ProductPromotion;
import com.A.Vdao.dao.CatalogPromotionDao;

@Component
public class CatalogPromotionDaoImpl extends BaseTransactionalJpaDao implements CatalogPromotionDao {

    private static final Logger logger = Logger.getLogger(CatalogPromotionDaoImpl.class);
    private static final String FIND_PROMOTION_BY_ID = "SELECT pp FROM ProductPromotion pp WHERE pp.externalId =? ORDER BY pp.id DESC";

    /**
	 *
	 */
    public ProductPromotion findCatalogPromotionById(String id) {
	logger.info("Executing findCatalogPromotionById : " + id);
	long start = System.currentTimeMillis();
	if (getEntityManager() != null) {

	    try {

		Query query = getEntityManager().createQuery(FIND_PROMOTION_BY_ID);
		query.setParameter(1, id);
		List<ProductPromotion> promotionList = (List<ProductPromotion>) query.getResultList();
		if (promotionList != null && promotionList.size() > 0) {
		    logger.info("findCatalogPromotionById took : " + (System.currentTimeMillis() - start) + "ms");
		    return promotionList.get(0);
		}

	    }
	    catch (NoResultException nre) {
		logger.error("Exception thrown while executing query :", nre);

	    }
	}

	return null;
    }

}
