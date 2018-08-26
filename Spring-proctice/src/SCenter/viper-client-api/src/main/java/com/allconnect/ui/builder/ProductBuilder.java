package com.A.ui.builder;

import com.A.xml.pr.v4.ProductResponseType;
import com.A.xml.pr.v4.ProviderResults;

public enum ProductBuilder {

	INSTANCE;
	
	public ProviderResults getProviderResults(
			final ProductResponseType productResponse) { 
				return getProviderResults(productResponse);
			}

	public ProviderResults getProviderResults(
			final ProductResponseType productResponse, final int index) {

		ProviderResults pr = null;

		if (productResponse != null) {

			if (productResponse.getProviderResults().size() > index)
				pr = productResponse.getProviderResults().get(index);
		}

		return pr;
	}
}
