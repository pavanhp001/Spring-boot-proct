package com.A.vm.util.converter.marshall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlObjectBase;

import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.ProductFeature;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.xml.v4.EmptyElementType;
import com.A.xml.v4.FeatureValueType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.PriceInfoType;
import com.A.xml.v4.SelectedFeaturesType;
import com.A.xml.v4.SelectedFeaturesType.FeatureGroup;
import com.A.xml.v4.SelectedFeaturesType.Features;

public enum MarshallFeatureValue {

	INSTANCE;
	private static final String INTERNAL = "internal";
	public static final String INTEGER_TYPE = "integer";
	public static final String STRING_TYPE = "string";
	public static final String BOOLEAN_TYPE = "boolean";
	public static final String PICK_ONE = "PickOne";
	public static final String PICK_ALL = "PickAll";

	private final static Logger logger = Logger
			.getLogger(MarshallFeatureValue.class);

	public void copySelectedFeatureValue(LineItem src,
			LineItemType lineItemType, Product product) {

		Map<String, FeatureGroup> groups = new HashMap<String, FeatureGroup>();

		// Feature Group container
		SelectedFeaturesType destSelFetType = lineItemType
				.addNewSelectedFeatures();
		Features indFeature = destSelFetType.addNewFeatures();

		FeatureGroup destGroup = null;

		List<SelectedFeatureValue> srcFeatureBeanList = null;
		if (src.getSelectedFeatureValues() == null) {
			srcFeatureBeanList = new ArrayList<SelectedFeatureValue>();
		} else {
			srcFeatureBeanList = new ArrayList<SelectedFeatureValue>(
					src.getSelectedFeatureValues());
		}

		if (srcFeatureBeanList != null) {

			try {
				// FIRST LOAD ALL GROUPS- ASSUME ONLY ONE LAYER DEEP
				for (SelectedFeatureValue srcBean : srcFeatureBeanList) {
					if (srcBean != null) {
						// If parent is null and datatype states is Group
						if ((srcBean.getParentNode() == null)
								&& (srcBean.getDataType() != null)
								&& ("group".equals(srcBean.getDataType()))) {
							destGroup = destSelFetType.addNewFeatureGroup();
							destGroup.setExternalId(srcBean.getExternalId());
							destGroup.setGroupType(srcBean.getFeatureType());
							groups.put(destGroup.getExternalId(), destGroup);

						} else
						// If parent and feature value is null then it is a
						// group
						if ((srcBean.getParentNode() == null)
								&& ((srcBean.getValue() == null))) {
							destGroup = destSelFetType.addNewFeatureGroup();
							destGroup.setExternalId(srcBean.getExternalId());
							destGroup.setGroupType(srcBean.getFeatureType());
							groups.put(destGroup.getExternalId(), destGroup);

						}
					}
				}

				// LOAD ALL FEATURES.... IF IT HAS A GROUP... LOCATE IT IN THE
				// LIST AND NEST IT
				for (SelectedFeatureValue srcBean : srcFeatureBeanList) {
					if (srcBean != null) {

						if (srcBean.getParentNode() != null) {
							// destGroup.getExternalId().equalsIgnoreCase(srcBean.getParentNode().getExternalId()))
							// { // Child feature of
							// the group

							FeatureGroup selectedGroup = groups.get(srcBean
									.getParentNode().getExternalId());
							copyNestedFeatureValue(selectedGroup, srcBean,
									destSelFetType);

						} else if ((srcBean.getParentNode() == null)
								&& ((srcBean.getValue() != null))) {
							// Individual Feature

							copyIndividualFeatureValue(srcBean, indFeature);
						}
					}
				}
			} catch (Exception e) {
				logger.error("Selected feature marshalling error", e);
			}
		}

		// Hack to remove FeatureGroup element if it is empty otherwise pricing
		// will give error
		if (destGroup != null && destGroup.getExternalId() == null) {
			destSelFetType.setFeatureGroupArray(null);
		}

		//Method to populate included features from snapshot product
		populateIncludedFeatures(product, destSelFetType,src.getProductDatasource());

	}

	/**
	 * Helper method to add included features from snapshot product.
	 * @param product
	 * @param destSelFetType
	 * @param productDatasource
	 */
	private void populateIncludedFeatures(Product product,
			SelectedFeaturesType destSelFetType, String productDatasource) {
		logger.info("inside populateIncludedFeatures ");
		// Code to add included features in response.
		// This code retrieves included features from product service and them
		// to response for FA
		List<ProductFeature> includedFeatureList = null;
		if (productDatasource != null && productDatasource.equalsIgnoreCase(INTERNAL)) {
			if (product != null && product.getProductFeatures() != null) {
				includedFeatureList = product.getProductFeatures();
				if (includedFeatureList != null
						&& !includedFeatureList.isEmpty()) {
					Features existingFeaturesList = destSelFetType
							.getFeatures();

					Map<String,String> parentgroupIdMap = new HashMap<String,String>();

					getParentIdMap(includedFeatureList,parentgroupIdMap);

					for (ProductFeature includedFeature : includedFeatureList) {

						logger.trace("inside populateIncludedFeatures "+includedFeature.getProductFeatureBase().getExternalId());

						if (includedFeature.getProductFeatureBase()
								.getParentGroupExtId() != null
								&& parentgroupIdMap.containsKey(includedFeature
										.getProductFeatureBase()
										.getParentGroupExtId())
								&& includedFeature.getProductFeatureBase()
										.getIncluded()
								&& includedFeature.getProductFeatureBase()
										.getAvailable()) {/*

							logger.info("inside populateIncludedFeatures "+includedFeature.getProductFeatureBase().getParentGroupExtId());

							FeatureGroup  featureGroup  =  destSelFetType.addNewFeatureGroup();
							featureGroup.setExternalId(includedFeature.getProductFeatureBase().getParentGroupExtId());

							if (parentgroupIdMap.get(includedFeature
									.getProductFeatureBase()
									.getParentGroupExtId()) != null) {

								String groupType = parentgroupIdMap
										.get(includedFeature
												.getProductFeatureBase()
												.getParentGroupExtId());

								if (groupType != null
										&& groupType
												.equalsIgnoreCase(PICK_ONE)) {
									featureGroup.setGroupType(1);
								} else if (groupType != null
										&& groupType
												.equalsIgnoreCase(PICK_ALL)) {
									featureGroup.setGroupType(-1);
								}
							}
							FeatureValueType featureValueType =  featureGroup.addNewFeatureValue();
							featureValueType.setExternalId(includedFeature.getProductFeatureBase().getExternalId());
							featureValueType.setDescription(includedFeature.getProductFeatureBase().getDescription());
							featureValueType.addNewIncluded();

							if(includedFeature.getProductFeatureBase().getDcValueBean().getDataType() != null){
								String fType = includedFeature.getProductFeatureBase().getDcValueBean().getDataType().toLowerCase();
								logger.debug("Parsing feature type : " + fType);
								if(fType != null && fType.length() > 0){
									featureValueType.setType(featureValueType.getType().forString(fType.trim()));
								}

								setFeatureValue(includedFeature, featureValueType, fType);
							}else{
								logger.warn("Feature type not found for feature : " + includedFeature.getProductFeatureBase().getExternalId());
							}

							PriceInfoType priceType = PriceInfoType.Factory.newInstance();
							priceType.setBaseNonRecurringPrice(includedFeature.getProductFeatureBase().getFeaturePriceInfo().getBaseNonRecurringPrice());
							priceType.setBaseNonRecurringPriceUnits("US");
							priceType.setBaseRecurringPrice(includedFeature.getProductFeatureBase().getFeaturePriceInfo().getBaseRecurringPrice());
							priceType.setBaseRecurringPriceUnits("US");
							featureValueType.setPrice(priceType);

                        */}else if (includedFeature.getProductFeatureBase() != null && includedFeature.getProductFeatureBase()
								.getIncluded() && includedFeature.getProductFeatureBase().getAvailable()) {
							//add onlu included features which are available
							FeatureValueType fvType = existingFeaturesList
									.addNewFeatureValue();
							fvType.setExternalId(includedFeature
									.getProductFeatureBase().getExternalId());
							fvType.setDescription(includedFeature
									.getProductFeatureBase().getDescription());
							fvType.addNewIncluded();

							if(includedFeature.getProductFeatureBase().getDcValueBean().getDataType() != null){
								String fType = includedFeature.getProductFeatureBase().getDcValueBean().getDataType().toLowerCase();
								logger.trace("Parsing feature type : " + fType);
								if(fType != null && fType.length() > 0){
									fvType.setType(fvType.getType().forString(fType.trim()));
								}

								setFeatureValue(includedFeature, fvType, fType);
							}else{
								logger.warn("Feature type not found for feature : " + includedFeature.getProductFeatureBase().getExternalId());
							}

							PriceInfoType priceType = PriceInfoType.Factory.newInstance();
							priceType.setBaseNonRecurringPrice(includedFeature.getProductFeatureBase().getFeaturePriceInfo().getBaseNonRecurringPrice());
							priceType.setBaseNonRecurringPriceUnits("US");
							priceType.setBaseRecurringPrice(includedFeature.getProductFeatureBase().getFeaturePriceInfo().getBaseRecurringPrice());
							priceType.setBaseRecurringPriceUnits("US");
							fvType.setPrice(priceType);
						}
					}
				}
			}
		}
	}

	// method to get feature group external ids
	private void getParentIdMap(List<ProductFeature> includedFeatureList,
			Map<String,String> parentgroupIdMap) {
		// TODO Auto-generated method stub
		for (ProductFeature productFeature : includedFeatureList) {
			if (productFeature.getProductFeatureBase().getFeatureGroup()) {
				parentgroupIdMap.put(productFeature.getProductFeatureBase()
						.getExternalId(),productFeature.getProductFeatureBase().getFeatureGroupType());
			}
		}

		logger.trace("inside getParentIdMap "+parentgroupIdMap);
	}

	private void setFeatureValue(ProductFeature includedFeature,
			FeatureValueType fvType, String fType) {
		if(fType.equalsIgnoreCase("string")){
			fvType.setValue(includedFeature.getProductFeatureBase().getDcValueBean().getStringValue());
		}else if(fType.equalsIgnoreCase("integer")){
			fvType.setValue(String.valueOf(includedFeature.getProductFeatureBase().getDcValueBean().getIntegerValue()));
		}else if(fType.equalsIgnoreCase("boolean")){
			fvType.setValue(String.valueOf(includedFeature.getProductFeatureBase().getDcValueBean().getIntegerValue()));
		}
	}

	private void copyNestedFeatureValue(FeatureGroup destGroup,
			SelectedFeatureValue srcBean, SelectedFeaturesType destSelFetType) {

		FeatureValueType childFeatureType = destGroup.addNewFeatureValue();
		childFeatureType.setExternalId(srcBean.getExternalId());
		childFeatureType.setValue(srcBean.getValue());
		copyFeatureValueDetail(srcBean, childFeatureType);
	}

	private void copyIndividualFeatureValue(SelectedFeatureValue srcBean,
			Features indFeature) {

		FeatureValueType destFeature = indFeature.addNewFeatureValue();
		destFeature.setExternalId(srcBean.getExternalId());

		copyFeatureValueDetail(srcBean, destFeature);
	}

	private void copyFeatureValueDetail(SelectedFeatureValue srcBean,
			FeatureValueType destFeature) {

		if (srcBean.getValue() == null) {
			destFeature.setValue("");
		} else {
			destFeature.setValue(srcBean.getValue());
		}

		if (srcBean.getAvailable() != null) {
			destFeature.setAvailable(srcBean.getAvailable());
		}

		if (srcBean.getDescription() != null) {
			destFeature.setDescription(srcBean.getDescription());
		}

		if (srcBean.getDisplay() != null && srcBean.getDisplay()) {
			destFeature.addNewDoNotDisplay();
		}

		if (srcBean.getIncluded() != null && srcBean.getIncluded()) {
			destFeature.addNewIncluded();
		}

		destFeature.setExternalId(srcBean.getExternalId());

		PriceInfoType priceInfoType = destFeature.addNewPrice();
		MarshallPriceInfo.Builder.copy(srcBean, priceInfoType);

		if (srcBean.getRequired() != null) {
			destFeature.setRequired(srcBean.getRequired());
		}

		setFeatureType(srcBean, destFeature);
	}

	@SuppressWarnings("static-access")
	private void setFeatureType(SelectedFeatureValue srcBean,
			FeatureValueType childFeatureType) {
		if (srcBean != null && srcBean.getDataType() != null) {

			if (srcBean.getDataType().equalsIgnoreCase(INTEGER_TYPE))
				childFeatureType.setType(childFeatureType.getType().forString(
						INTEGER_TYPE));
			else if (srcBean.getDataType().equalsIgnoreCase(STRING_TYPE))
				childFeatureType.setType(childFeatureType.getType().forString(
						STRING_TYPE));
			else if (srcBean.getDataType().equalsIgnoreCase(BOOLEAN_TYPE))
				childFeatureType.setType(childFeatureType.getType().forString(
						BOOLEAN_TYPE));
		}
	}

}
