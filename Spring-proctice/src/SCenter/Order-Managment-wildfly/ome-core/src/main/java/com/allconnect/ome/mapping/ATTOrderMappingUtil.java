package com.AL.ome.mapping;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ProviderLineItemStatusType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.TransientResponseType;
/**
 * This class will parse V order no specific to ATT-STI
 * @author PPatel
 *
 */
public class ATTOrderMappingUtil {

	private static final Logger logger = Logger.getLogger(ATTOrderMappingUtil.class);

	public static String retrieveVOrderNo(Boolean isHarmonyOrder, LineItemType liType) {
		logger.debug("Retrieving ATT V order no. from lineitem ...");

		String VOrderNo = "";

		if (isHarmonyOrder) {
			//Retrieve harmony provided V order no. for harmony orders

			if(liType != null && liType.getSelectedFeatures() != null){
				SelectedFeaturesType featuresType = liType.getSelectedFeatures();
				if(featuresType !=null && featuresType.getFeatures() != null && featuresType.getFeatures().getFeatureValueList() != null){
					List<FeatureValueType> features = featuresType.getFeatures().getFeatureValueList();
					for(FeatureValueType fvType : features){
						if(fvType.getExternalId().equalsIgnoreCase("VPRORDNO")){
							VOrderNo = fvType.getValue();
							logger.debug("Harmony - ATT-STI V order no. :" + VOrderNo);
							break;
						}
					}
				}

			}

		}else{

			//Retrieve V order no. returned in A7 order submit response
			if (liType != null
					&& liType.getTransientResponseContainer() != null
					&& liType.getTransientResponseContainer()
							.getTransientResponse() != null) {
				TransientResponseType transResType = liType
						.getTransientResponseContainer().getTransientResponse();
				if (transResType.getProviderLineItemStatus() != null) {
					ProviderLineItemStatusType provStatusType = transResType
							.getProviderLineItemStatus();
					if (provStatusType.getProcessingStatusCode().toString()
							.equalsIgnoreCase("INFO")
							&& provStatusType.getLineItemStatusCode()
									.toString()
									.equalsIgnoreCase("order_submitted")) {
						VOrderNo = transResType.getOrderNumber();
						logger.debug("SYP - ATT-STI V order no. :" + VOrderNo);
					}

				}
			}
		}
		return VOrderNo;
	}
}
