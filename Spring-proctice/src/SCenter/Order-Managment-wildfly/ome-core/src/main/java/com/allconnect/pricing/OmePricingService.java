package com.AL.pricing;

import com.AL.xml.v4.PricingRequestResponseDocument;

public interface OmePricingService {
	
	public PricingRequestResponseDocument processPricingRequest(
			PricingRequestResponseDocument incomingRequestResponseDocument);

}
