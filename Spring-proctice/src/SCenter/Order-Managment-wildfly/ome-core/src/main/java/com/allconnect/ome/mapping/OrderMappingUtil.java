package com.AL.ome.mapping;


import org.apache.log4j.Logger;

import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.TransientResponseContainerType;

public class OrderMappingUtil {

	private static final Logger LOG = Logger.getLogger(OrderMappingUtil.class);
	
	public static String retrieveVOrderNo(String provider, LineItemType liType){
		
		LOG.debug("Retrieving "+provider+" link order no. from lineitem ...");
		
		String VOrderNo = "";
		
		if(liType != null && liType.getTransientResponseContainer() != null){
			TransientResponseContainerType trcType = liType.getTransientResponseContainer();
			if(trcType.getTransientResponse() != null){

				if(trcType.getTransientResponse().getProviderLineItemStatus() != null && trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode() != null){
					if(trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode().toString().equalsIgnoreCase("SUCCESS")
							|| trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode().toString().equalsIgnoreCase("INFO")){
						VOrderNo = trcType.getTransientResponse().getOrderNumber();
						LOG.debug("SYP - "+provider+" order no. :" + VOrderNo);
					}
				}
			}
		}
		return VOrderNo;
	}
}
