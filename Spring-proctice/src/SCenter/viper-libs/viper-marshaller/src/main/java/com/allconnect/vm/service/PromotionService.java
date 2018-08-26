package com.A.vm.service;

import com.A.V.beans.entity.ProductPromotion;

public interface PromotionService {

	/**
     * @param id
     *            Promotion ID
     * @return ProductPromotion
     */
	ProductPromotion findPromotionById(final Long id);
	
	/**
     * @param id
     *            Promotion ID
     * @return ProductPromotion
     */
	ProductPromotion findPromotionById(final String id);
	
	 
}
