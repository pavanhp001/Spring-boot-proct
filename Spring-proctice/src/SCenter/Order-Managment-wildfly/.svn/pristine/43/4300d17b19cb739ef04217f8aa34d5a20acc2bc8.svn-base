package com.AL.ome.mapping;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.BillingAttributeType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SubmitOrderResponseInfo;
import com.AL.xml.v4.TransientResponseContainerType;

public class DishOrderMappingUtil {

	private static final Logger logger = Logger.getLogger(DishOrderMappingUtil.class);

	public static String retrieveVOrderNo(Boolean isHarmonyOrder,LineItemType liType) {
		logger.debug("Retrieving dish sucscriber id from lineitem ...");
		String VOrderNo = "";

		if (isHarmonyOrder) {
			//Hamony provided dish subscriber id as selected features
			//Retrieve harmony provided V order no. for harmony orders

			if(liType != null && liType.getSelectedFeatures() != null){
				SelectedFeaturesType featuresType = liType.getSelectedFeatures();
				if(featuresType !=null && featuresType.getFeatures() != null && featuresType.getFeatures().getFeatureValueList() != null){
					List<FeatureValueType> features = featuresType.getFeatures().getFeatureValueList();
					for(FeatureValueType fvType : features){
						if(fvType.getExternalId().equalsIgnoreCase("CONF")){
							VOrderNo = fvType.getValue();
							logger.info("Harmony - dish subscriber id :" + VOrderNo);
							break;
						}
					}
				}

			}

		}else{
			// For SYP, order submit response will have subscriber id in transient response container of dish product
			//Retrieve Dish subscriber id from transient response of dish submit(SYP)
			if(liType != null && liType.getTransientResponseContainer() != null){
				TransientResponseContainerType trcType = liType.getTransientResponseContainer();
				if(trcType.getTransientResponse() != null){

					if(trcType.getTransientResponse().getProviderLineItemStatus() != null && trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode() != null){
						if(trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode().toString().equalsIgnoreCase("INFO")){
							if(trcType.getTransientResponse().getSubmitOrderResponse() != null){
								SubmitOrderResponseInfo sorInfo = trcType.getTransientResponse().getSubmitOrderResponse() ;
								if(sorInfo.getProviderResponseInfo() != null){
									BillingAttributeType prInfo = sorInfo.getProviderResponseInfo();
									if(prInfo.getSource().equalsIgnoreCase("DISH")){
										List<AttributeDetailType> entityList = prInfo.getEntityList();
										for(AttributeDetailType entityType : entityList){
											if(entityType.getName().equalsIgnoreCase("SUBSCRIBERID")){
												VOrderNo = entityType.getValue();
												break;
											}
										}
									}
								}
								logger.debug("SYP - dish subscriber id :" + VOrderNo);
							}

						}
					}


				}
			}
		}
		return VOrderNo;
	}
}
