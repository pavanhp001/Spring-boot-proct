package com.AL.ie.activity.impl;

import com.AL.xml.v4.LineItemType;

public enum ActivityGetProvider {

	INSTANCE;

	public String getProvider(LineItemType lineItem) {

		String providerExternalId = "-1";
		if ((lineItem != null)
				&& (lineItem.getLineItemDetail() != null)
				&& (lineItem.getLineItemDetail().getDetail() != null)
				&& (lineItem.getLineItemDetail().getDetail()
						.getProductLineItem() != null)
				&& (lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider() != null)
				&& (lineItem.getLineItemDetail().getDetail()
						.getProductLineItem().getProvider().getExternalId() != null)) {

			providerExternalId = lineItem.getLineItemDetail().getDetail()
					.getProductLineItem().getProvider().getExternalId();

		}

		if ((lineItem != null)
				&& (lineItem.getLineItemDetail() != null)
				&& (lineItem.getLineItemDetail().getDetail() != null)
				&& (lineItem.getLineItemDetail().getDetail()
						.getProductBundleLineItem() != null)
				&& (lineItem.getLineItemDetail().getDetail()
						.getProductBundleLineItem().getProviderId() != null)) {

			providerExternalId = lineItem.getLineItemDetail().getDetail()
					.getProductLineItem().getProvider().getExternalId();

		}

		return providerExternalId;

	}
}
