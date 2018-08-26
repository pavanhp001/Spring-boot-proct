package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.A.V.beans.LineItemPriceInfo;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.xml.v4.FeatureValueType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.SelectedFeaturesType;
import com.A.xml.v4.SelectedFeaturesType.FeatureGroup;
import com.A.xml.v4.SelectedFeaturesType.Features;


public final class UnmarshallSelectedFeatureValue {

    private static final Logger logger = Logger.getLogger(UnmarshallSelectedFeatureValue.class);

	private UnmarshallSelectedFeatureValue(){
		super();
	}

	public static List<SelectedFeatureValue> copySelectedFeatureValues(LineItem destLineItem, LineItemType src){
	    logger.debug("Unmarshlling selected features");
		if(src.getSelectedFeatures() != null){

			UnmarshallPriceInfo unmarshallPriceInfo = new UnmarshallPriceInfo();

			 List<SelectedFeatureValue> featuresBeanList = new ArrayList<SelectedFeatureValue>();
			SelectedFeaturesType srcSelFeatureType = src.getSelectedFeatures();
			List<FeatureGroup> srcFeatureGroupList = srcSelFeatureType.getFeatureGroupList();
			if(srcFeatureGroupList != null){
			    logger.trace("Feature group list size: " + srcFeatureGroupList.size());
				//Creating feature group
				for(FeatureGroup featureGroup : srcFeatureGroupList){
					SelectedFeatureValue featureBean = new SelectedFeatureValue();
					featureBean.setExternalId(featureGroup.getExternalId());
					featureBean.setFeatureType(featureGroup.getGroupType());

					featureBean.setParentNode(null);
					featureBean.setActive(true);
					featureBean.setFeatureDate(Calendar.getInstance());
					featuresBeanList.add(featureBean);

					logger.trace("Unmarshlling feature group : " + featureGroup.toString());
					//creating feature of the group
						List<FeatureValueType> srcFeatureList = featureGroup.getFeatureValueList();
						if(srcFeatureList != null){
						    logger.trace("Features list from group : " + srcFeatureList.size());
							for(FeatureValueType feature : srcFeatureList){
								SelectedFeatureValue childFeatureBean = new SelectedFeatureValue();

								if(feature.getType() != null){
									childFeatureBean.setDataType(feature.getType().toString());
								}
								childFeatureBean.setValue(feature.getValue());
								childFeatureBean.setParentNode(featureBean);
								childFeatureBean.setExternalId(feature.getExternalId());
								childFeatureBean.setAvailable(feature.getAvailable());

								//boolean doNotDisplay = (!XmlUtil.isElementNull(feature.newCursor(), "doNotDisplay"));
								boolean doNotDisplay = feature.getDoNotDisplay() != null ? Boolean.TRUE : Boolean.FALSE;

								childFeatureBean.setDisplay(doNotDisplay);
								childFeatureBean.setDescription(feature.getDescription());

								LineItemPriceInfo price = unmarshallPriceInfo.build(feature.getPrice(), childFeatureBean.getPrice());

								//boolean included = (!XmlUtil.isElementNull(feature.newCursor(), "included"));
								boolean included = feature.getIncluded() != null ? Boolean.TRUE : Boolean.FALSE;


								childFeatureBean.setIncluded(included);
								childFeatureBean.setPrice(price);
								childFeatureBean.setRequired(feature.getRequired());
								childFeatureBean.setActive(true);
								childFeatureBean.setFeatureDate(Calendar.getInstance());

								featuresBeanList.add(childFeatureBean);



							}
						}

				}
			}



			//Creating individual features without group
			Features features = srcSelFeatureType.getFeatures();
			if(features != null){
				List<FeatureValueType> srcFeatureList = features.getFeatureValueList();
				if(srcFeatureList != null){
				    logger.trace("Unmarshalling individual features : " + srcFeatureList.size());
					for(FeatureValueType feature : srcFeatureList){
					    logger.trace("Unmarshalling Ind Feature : " + feature.toString());
						SelectedFeatureValue indFeatureBean = new SelectedFeatureValue();

						if(feature.getType() != null){
							indFeatureBean.setDataType(feature.getType().toString());
						}
						indFeatureBean.setExternalId(feature.getExternalId());
						indFeatureBean.setValue(feature.getValue());
						indFeatureBean.setAvailable(feature.getAvailable());

						boolean doNotDisplay = feature.getDoNotDisplay() != null ? Boolean.TRUE : Boolean.FALSE;

						indFeatureBean.setDisplay(doNotDisplay);
						indFeatureBean.setDescription(feature.getDescription());

						LineItemPriceInfo price = unmarshallPriceInfo.build(feature.getPrice(), indFeatureBean.getPrice());

						/*boolean included = (!XmlUtil.isElementNull(feature
								.newCursor(), "included"));*/

						boolean included = feature.getIncluded() != null ? Boolean.TRUE : Boolean.FALSE;

						indFeatureBean.setIncluded(included);
						indFeatureBean.setPrice(price);
						indFeatureBean.setRequired(feature.getRequired());
						indFeatureBean.setParentNode(null);
						indFeatureBean.setActive(true);
						indFeatureBean.setFeatureDate(Calendar.getInstance());

						featuresBeanList.add(indFeatureBean);

					}
				}
			}
			return featuresBeanList;
		}
		return null;
	}



}
