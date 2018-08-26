package com.AL.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.AL.V.beans.entity.LineItemDetail;
import com.AL.V.beans.entity.Product;
import com.AL.V.beans.entity.ProductFeature;
import com.AL.V.beans.entity.ProductPromotion;
import com.AL.Vdao.dao.CatalogProductDao;
import com.AL.vm.service.CatalogProductService;

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
	    logger.info("Executing findCatalogProductById");
	    long start = System.currentTimeMillis();
		if (id == null) {
			return null;
		}
		logger.info("Product unique id : " + id.longValue());
		Product product = catalogProductDao.findCatalogProductById(String
				.valueOf(id.longValue()));
		//Hack to load prod features to include them in OME res for FA
		if(product != null && product.getProductFeatures() != null){
			for(ProductFeature pf : product.getProductFeatures()){
				pf.getId();
				pf.getProductFeatureBase();
			}
		}
		logger.info("findCatalogProductById took : " + (System.currentTimeMillis() - start) + "ms");
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
	    logger.info("Executing findProductPromotionById");
	    long start = System.currentTimeMillis();
		if (id == null) {
			return null;
		}
		logger.info("ProductUniqueId="+id+"  PromotionId="+promotionExtId);
		Product product = catalogProductDao.findCatalogProductById(String
				.valueOf(id.longValue()));
		List<ProductPromotion> promotionList = product.getPromotions();
		if (promotionList != null && !promotionList.isEmpty()) {
			logger.info("No. of promotion found :" + promotionList.size());
			for (ProductPromotion promotion : promotionList) {
				if (promotionExtId.equalsIgnoreCase(promotion.getExternalId())) {
					logger.info("Matching promotion found for product. Promotion Id : "
							+ promotion.getId());
					logger.info("findProductPromotionById : " + (System.currentTimeMillis() - start) + "ms");
					return promotion;
				}

			}
		}
		logger.warn("No matching promotion found in product catalog for promotion id = " + promotionExtId);
		logger.info("findProductPromotionById : " + (System.currentTimeMillis() - start) + "ms");
		return null;
	}

	 @Transactional
	    public ProductPromotion findPromotion(Product product, LineItemDetail VLineItemDetail) {
	     logger.info("Executing findPromotion");
	     long start = System.currentTimeMillis();
		List<ProductPromotion> promotionList = product.getPromotions();
		if (promotionList != null && !promotionList.isEmpty()) {
			logger.info("No. of promotion found :" + promotionList.size());
			for (ProductPromotion promotion : promotionList) {
				if (VLineItemDetail.getLineItemDetailExternalId().equalsIgnoreCase(promotion.getExternalId())) {
					logger.info("Matching promotion found for product : "
							+ promotion.getId());
					logger.info("findPromotion took : " + (System.currentTimeMillis() - start) + "ms");
					return promotion;
				}

			}
		}
		logger.info("findPromotion took : " + (System.currentTimeMillis() - start) + "ms");
		return null;
	    }

}
