package com.A.Vdao.transactional.dao.impl;

import java.util.Calendar;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.ProductBase;
import com.A.V.beans.entity.Product;
import com.A.Vdao.dao.CatalogProductDao;

/**
 * @author ebthomas
 *
 */
@Component
public class CatalogProductDaoImpl extends BaseTransactionalJpaDao implements CatalogProductDao {
    private static final String FIND_PRODUCT_BY_ID = "SELECT mi FROM Product mi WHERE mi.id = :id ";
    private static final String PRODUCT_ID = "id";

    private static final Logger logger = Logger.getLogger(CatalogProductDaoImpl.class);

    /**
     * factory constructor.
     */
    public CatalogProductDaoImpl() {
	super();
    }

    /**
     * @param em
     *            Entity Manager
     * @param id
     *            id
     * @return Market Item Bean
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Product findCatalogProductById(final String id) {
	logger.info("Executing findCatalogProductById");
	long start = System.currentTimeMillis();
	// String db = getDatabaseName();
	if ((id != null) && ("0").equals(id)) {
	    logger.debug("returning STUB Product: " + id);
	    return getStubProduct(id);
	}

	logger.debug("Searching for Product with id : " + id);
	if (getEntityManager() != null) {

	    try {

		Query query = getEntityManager().createQuery(FIND_PRODUCT_BY_ID);
		query.setParameter(PRODUCT_ID, Long.valueOf(id));
		Product product = (Product) query.getSingleResult();
		logger.info("findCatalogProductById took : " + (System.currentTimeMillis() - start) + "ms");
		return product;

	    }
	    catch (NoResultException nre) {
		logger.warn("product catalog not found using stub:");
		return getStubProduct(id);
	    }
	}

	return null;

    }

    public Product getStubProduct(String id) {

	Product product = new Product();
	product.setId(Long.valueOf(id));
	product.setTimestamp(Calendar.getInstance());
	product.setDatasource("internal");

	ProductBase pBase = new ProductBase();
	pBase.setName("");
	pBase.setItemCategoryName("");

	product.setProductBase(pBase);

	return product;

    }

}
