package com.AL.activity.impl;

import com.AL.enums.LineItemStatus;
import com.AL.V.beans.entity.LineItem;

public enum ActivityAllowProductPromotionStatusUpdate {

	INSTANCE;

	private static final String PRODUCT_PROMOTION = "ProductPromotion";

	public Boolean process(LineItem li, String status) {

		String productType = getProductType(li);

		if (productType != null) {

			if (!isPromotion(productType)) {
				// product or bundle--> Allow
				return Boolean.TRUE;
			}

			// Beyond this point is promotion
			// allow promotion status delete. disallow all others
			return (isPromotionStatusDelete(productType, status));
		}
		
		//TODO:Allow from Delete to Sales New Order
		//TODO:Allow from Sales New Order to Delete

		// unable to identify product type. Bypass Status Update
		return Boolean.FALSE;
	}

	public String getProductType(final LineItem li) {

		String productType = "";

		if ((li != null) && (li.getLineItemDetail() != null)
				&& (li.getLineItemDetail().getType() != null)) {
			productType = li.getLineItemDetail().getType().trim();
		}

		return productType;
	}

	public Boolean isPromotionStatusDelete(final String productType,
			final String status) {
		return ((isPromotion(productType)) && (status
				.equals(LineItemStatus.cancelled_removed.name())));

	}

	public Boolean isPromotion(final String productType) {
		return (PRODUCT_PROMOTION.equals(productType))
				|| (productType.toUpperCase().indexOf("PROMOTION") != -1);
	}

}
