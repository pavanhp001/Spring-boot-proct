package com.AL.ome.mapping;

import org.apache.log4j.Logger;

import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ProviderLineItemStatusType;
import com.AL.xml.v4.TransientResponseType;

public class VerizonOrderMappingUtil {
    private static final Logger logger = Logger.getLogger(VerizonOrderMappingUtil.class);

    public static String retrieveVOrderNo(Boolean isHarmonyOrder, LineItemType liType) {
	logger.debug("Retrieving Verizon V order no. from lineitem ...");

	String VOrderNo = "";

	// Retrieve V order no. returned in A7 order submit response
	if (liType != null && liType.getTransientResponseContainer() != null && liType.getTransientResponseContainer().getTransientResponse() != null) {
	    TransientResponseType transResType = liType.getTransientResponseContainer().getTransientResponse();
	    if (transResType.getProviderLineItemStatus() != null) {
		ProviderLineItemStatusType provStatusType = transResType.getProviderLineItemStatus();
		if (provStatusType.getProcessingStatusCode().toString().equalsIgnoreCase("INFO")
			&& provStatusType.getLineItemStatusCode().toString().equalsIgnoreCase("info")) {
		    VOrderNo = transResType.getOrderNumber();
		    logger.debug("SYP - Verizon V order no. :" + VOrderNo);
		}

	    }
	}
	return VOrderNo;
    }
}
