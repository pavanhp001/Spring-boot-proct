package com.AL.ome.mapping;


import org.apache.log4j.Logger;

import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.TransientResponseContainerType;

public class CenturylinkOrderMappingUtil {

	private static final Logger logger = Logger.getLogger(CenturylinkOrderMappingUtil.class);
	
	public static String retrieveVOrderNo(LineItemType liType){
		logger.debug("Retrieving Century link order no. from lineitem ...");
		
		String VOrderNo = "";
		
		if(liType != null && liType.getTransientResponseContainer() != null){
			TransientResponseContainerType trcType = liType.getTransientResponseContainer();
			if(trcType.getTransientResponse() != null){

				if(trcType.getTransientResponse().getProviderLineItemStatus() != null && trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode() != null){
					if(trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode().toString().equalsIgnoreCase("SUCCESS")){
						VOrderNo = trcType.getTransientResponse().getOrderNumber();
						logger.debug("SYP - CenturyLink order no. :" + VOrderNo);
					}
				}
			}
		}
		return VOrderNo;
	}
}
