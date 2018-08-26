package com.A.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.ProductFeature;
import com.A.V.beans.entity.ProductPromotion;
import com.A.Vdao.dao.CatalogProductDao;
import com.A.vm.service.CatalogProductService;

/**
 * @author ebthomas
 * 
 */

@Component("catalogProductService")
public class CatalogProductServiceImpl implements CatalogProductService {

	private static final Logger logger = Logger
			.getLogger(CatalogProductServiceImpl.class);

	@Autowired
	private CatalogProductDao catalogProductDao;

	public CatalogProductServiceImpl() {

		super();

	}

	@Transactional
	public Product findCatalogProductById(Long id) {

		if (id == null) {
			return null;
		}
		Product product = catalogProductDao.findCatalogProductById(String
				.valueOf(id.longValue()));
		return product;
	}

	public Product findCatalogProductById(String id) {
		if (id == null) {
			return null;
		}

		return catalogProductDao.findCatalogProductById(id);
	}

	public CatalogProductDao getCatalogProductDao() {
		return catalogProductDao;
	}

	public void setCatalogProductDao(CatalogProductDao catalogProductDao) {
		this.catalogProductDao = catalogProductDao;
	}

	@Override
	public String toString() {
		return "CatalogProductServiceImpl [catalogProductDao="
				+ catalogProductDao + "]";
	}

	public ProductPromotion findCatalogProductPromotionById(String id) {
		if (id == null) {
			return null;
		}
		return null;
	}

	@Transactional
	public ProductPromotion findProductPromotionById(Long id,
			String promotionExtId) {
		if (id == null) {
			return null;
		}
		Product product = catalogProductDao.findCatalogProductById(String
				.valueOf(id.longValue()));
		List<ProductPromotion> promotionList = product.getPromotions();
		if (promotionList != null && !promotionList.isEmpty()) {
			logger.info("No of promotion found :" + promotionList.size());
			for (ProductPromotion promotion : promotionList) {
				if (promotionExtId.equalsIgnoreCase(promotion.getExternalId())) {
					logger.info("Matching promotion found for product : "
							+ promotion.getId());
					return promotion;
				}

			}
		}
		return null;
	}

}
