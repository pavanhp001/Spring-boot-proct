package com.AL.ome.mapping;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.TransientResponseContainerType;

public class G2BComcastOrderMappingUtil {

	private static final Logger logger = Logger.getLogger(G2BComcastOrderMappingUtil.class);

	public static String retrieveVOrderNo(Boolean isHarmonyOrder,LineItemType liType) {
		logger.debug("Retrieving G2B-Comcast order no. from lineitem ...");
		String VOrderNo = "";

		if (isHarmonyOrder) {
			//Harmony provided G2B order no as selected features
			//Retrieve harmony provided V order no. for harmony orders

			if(liType != null && liType.getSelectedFeatures() != null){
				SelectedFeaturesType featuresType = liType.getSelectedFeatures();
				if(featuresType !=null && featuresType.getFeatures() != null && featuresType.getFeatures().getFeatureValueList() != null){
					List<FeatureValueType> features = featuresType.getFeatures().getFeatureValueList();
					for(FeatureValueType fvType : features){
						if(fvType.getExternalId().equalsIgnoreCase("VPRORDNO")){
							VOrderNo = fvType.getValue();
							logger.debug("Harmony - G2B-Comcast order id :" + VOrderNo);
							break;
						}
					}
				}

			}

		}else{
			// For SYP, order submit response will have subscriber id in transient response container of g2b-comcast product
			//Retrieve g2b comcast order id from transient response of g2b submit(SYP)
			if(liType != null && liType.getTransientResponseContainer() != null){
				TransientResponseContainerType trcType = liType.getTransientResponseContainer();
				if(trcType.getTransientResponse() != null){

					if(trcType.getTransientResponse().getProviderLineItemStatus() != null && trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode() != null){
						if(trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode().toString().equalsIgnoreCase("INFO")){
							VOrderNo = trcType.getTransientResponse().getOrderNumber();
							logger.debug("SYP - G2B Comcast order no. :" + VOrderNo);
						}
					}


				}
			}
		}
		return VOrderNo;
	}
}
