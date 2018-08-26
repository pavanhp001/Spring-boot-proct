/**
 *
 */
package com.A.vm.service;

import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.ProductPromotion;

/**
 * @author ebthomas
 * 
 */
public interface CatalogProductService
{

	/**
	 * @param id
	 *            catalogProduct
	 * @return Product
	 */
	Product findCatalogProductById( final Long id );

	/**
	 * @param id
	 *            catalogProduct
	 * @return Product
	 */

	Product findCatalogProductById( final String id );

	/**
	 * Find the ProductPromotion based on Product UniqueId
	 * @param id
	 * @return
	 */
	ProductPromotion findProductPromotionById(final Long id, final String promotionExtId);
}

