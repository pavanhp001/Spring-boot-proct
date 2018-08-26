package com.A.productResults.vo;

import java.util.List;

import com.A.xml.pr.v4.FeatureType;
import com.A.xml.pr.v4.ProductInfoType;

public interface RTProductHelper {

	
	List<FeatureType> getFeatures(ProductInfoType prodInfo);
}
