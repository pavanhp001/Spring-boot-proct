package com.A.productResults.vo;

import java.util.ArrayList;
import java.util.List;

import com.A.xml.pr.v4.FeatureType;
import com.A.xml.pr.v4.ProductInfoType;

public class DishRTProductHelper implements RTProductHelper {

	private static final String RTS = "RTS:";


	public List<FeatureType> getFeatures(ProductInfoType prodInfo) {		
		List<FeatureType> allFeatures = prodInfo.getProductDetails().getFeature();
		return getDisplayFeatures(allFeatures);
	}

	private List<FeatureType> getDisplayFeatures(
			List<FeatureType> allFeatures) {
		List<FeatureType> retVal = new ArrayList<FeatureType>();
		for (FeatureType feature : allFeatures) {
			if(!feature.getExternalId().startsWith(RTS)){
				retVal.add(feature);
			}
		}
		return retVal;
	}

}
