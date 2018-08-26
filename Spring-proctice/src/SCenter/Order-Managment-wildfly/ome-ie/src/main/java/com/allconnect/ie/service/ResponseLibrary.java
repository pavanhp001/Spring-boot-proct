package com.AL.ie.service;

import com.AL.ie.service.strategy.ResolveProviderName;
import com.AL.xml.common.RtimError;
import com.AL.xml.common.RtimErrors;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequest;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponse;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponseDocument;
import com.AL.xml.v4.vendorRequestResponse.RtimResponse;

public enum ResponseLibrary {

	INSTANCE;
	
	public RtimRequestResponseDocument getNoResponseTemplate(OrderFulfillmentRequest req) {

		String providerId = "No Response";
		RtimRequestResponseDocument doc = RtimRequestResponseDocument.Factory
				.newInstance();

		RtimRequestResponse reqres = doc.addNewRtimRequestResponse();

		RtimResponse res = reqres.addNewResponse();

		res.addNewContext();

		res.getContext().setCorrelationId(req.getContext().getCorrelationId());
		res.getContext().setAffiliateName(req.getContext().getAffiliateName());
		res.getContext().setChannell(req.getContext().getChannell());
		res.getContext().setOrderNumber(req.getContext().getOrderNumber());
		res.getContext().setProviderId(providerId);
		res.getContext().setRegion(req.getContext().getRegion());
		res.getContext().setRequestId(req.getContext().getCorrelationId());
		res.getContext().setResponseDate(
				String.valueOf(System.currentTimeMillis()));
		res.getContext().setSalesCode(req.getContext().getSalesCode());
		res.getContext().setSessionId(req.getContext().getSessionId());
		res.getContext().setTransactionType(
				req.getContext().getTransactionType());

		RtimErrors errs = res.addNewRtimErrors();
		RtimError err = errs.addNewRtimError();
		err.setErrorCode("3.0");
		err.setErrorMessage("no response from provider");
		err.setProvider(providerId);
		err.setErrorType("NO_RESPONSE_FROM_PROVIDER");



		 return  doc;
	}
}
