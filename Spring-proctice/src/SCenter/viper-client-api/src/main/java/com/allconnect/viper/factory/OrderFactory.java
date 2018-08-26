package com.A.V.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.A.ui.activity.domain.ActivityRetrieveSalesPlan;
import com.A.ui.service.V.impl.CacheService;
import com.A.ui.vo.CartError;
import com.A.ui.vo.ErrorList;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.DialogValueType;
import com.A.xml.v4.FeatureValueType;
import com.A.xml.v4.LineItemAttributeType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProcessingMessage;
import com.A.xml.v4.SalesContextType;
import com.A.xml.v4.SelectedDialogsType;
import com.A.xml.v4.SelectedFeaturesType.FeatureGroup;
import com.A.xml.v4.SelectedFeaturesType.Features;

/**
 * @author Mahesh Nagineni
 *
 */
public enum OrderFactory {

	INSTANCE;
	
	public static final String GUID = "GUID";

	/**
	 * To populate Map with LineItem Attributes
	 * 
	 * @param LineItemType
	 * @return Map<String, Map<String, Object>>
	 */
	public Map<String, Map<String, Object>> getLineItemAttributeMap(LineItemType lit) {

		Map<String, Map<String, Object>> li_attributes = new HashMap<String, Map<String, Object>>();

		LineItemAttributeType liAttributes = lit.getLineItemAttributes();
		if (liAttributes != null) {
			List<AttributeEntityType> attibuteEntityList = liAttributes.getEntity();
			for(int i=0; i<attibuteEntityList.size(); i++){
				Map<String, Object> attr_entities = new HashMap<String, Object>();
				AttributeEntityType attrEntity = attibuteEntityList.get(i);
				List<AttributeDetailType> attrDetailList = attrEntity.getAttribute();
				for(int k=0; k<attrDetailList.size(); k++){
					String name = attrDetailList.get(k).getName();
					String value = attrDetailList.get(k).getValue();
					attr_entities.put(name, value);
				}
				li_attributes.put(attrEntity.getSource(), attr_entities);
			}
		}

		return li_attributes;
	}

	/**
	 * To populate Map with LineItem Active Dialogs
	 * 
	 * @param LineItemType
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getActiveDialogMap(LineItemType lit) {
		Map<String, Object> li_dialogs = new HashMap<String, Object>();

		SelectedDialogsType activeDialogs = lit.getActiveDialogs();

		if (activeDialogs != null && activeDialogs.getDialogs() != null) {
			List<DialogValueType> dialogValueType = activeDialogs.getDialogs().getDialog();

			for (DialogValueType dvt : dialogValueType) {
				li_dialogs.put(dvt.getExternalId(), dvt.getValue().get(0).getValue());
			}
		}

		return li_dialogs;
	}

	/**
	 * To populate Map with LineItem Features
	 * 
	 * @param LineItemType
	 * @return Map<String, Object>
	 */
	@Deprecated
	public Map<String, Object> getFeatureMap(LineItemType lit) {
		return getFeatureMap(lit, "");
	}
	
	/**
	 * To populate Map with LineItem Features
	 * 
	 * @param LineItemType
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getFeatureMap(LineItemType lit, String source) {

		Map<String, Object> li_features = new HashMap<String, Object>();
		if (lit.getSelectedFeatures() != null) {
			Features features = lit.getSelectedFeatures().getFeatures();
			if (features != null) {
				List<FeatureValueType> featureValueList = features.getFeatureValue();
				for (FeatureValueType featureValueType : featureValueList) {
					String id = featureValueType.getExternalId();
					String value = featureValueType.getValue();
					
					//Changes as per AIRS#102598
					if(source != null && source.equals("fulfillment")){
						if(featureValueType.getIncluded() != null){
							if(featureValueType.getType().equals("boolean")){
								value = "Yes";
							} else if(StringUtils.isNotBlank(featureValueType.getValue())){
								value = featureValueType.getValue()+"(Included)";
							} else {
								value = "Included";
							}
						} else if(featureValueType.getType().equals("boolean")){
							value = "Yes";
						}
					}
					li_features.put(id, value);
				}
			}
		}
		return li_features;
	}

	/**
	 * To populate Map with LineItem Feature groups
	 * 
	 * @param LineItemType
	 * @return Map<String, Map<String, Object>>
	 */
	@Deprecated
	public Map<String, Map<String, Object>> getFeatureGroupMap(LineItemType lit) {
		return getFeatureGroupMap(lit, "");
	}
	
	/**
	 * To populate Map with LineItem Feature groups
	 * 
	 * @param LineItemType
	 * @return Map<String, Map<String, Object>>
	 */
	public Map<String, Map<String, Object>> getFeatureGroupMap(LineItemType lit, String source) {
		Map<String, Map<String, Object>> li_feature_groups = new HashMap<String, Map<String, Object>>();
		if (lit.getSelectedFeatures().getFeatureGroup() != null) {
			List<FeatureGroup> featureGroupList = lit.getSelectedFeatures().getFeatureGroup();
			for (FeatureGroup featureGroup : featureGroupList) {

				Map<String, Object> features = new HashMap<String, Object>();
				List<FeatureValueType> featureValueList = featureGroup.getFeatureValue();

				for (FeatureValueType fvt : featureValueList) {
					String id = fvt.getExternalId();
					String value = fvt.getValue();
					
					//Changes as per AIRS#102598
					if(source != null && source.equals("fulfillment")){
						if(fvt.getIncluded() != null){
							if(fvt.getType().equals("boolean")){
								value = "Yes";
							} else if(StringUtils.isNotBlank(fvt.getValue())){
								value = fvt.getValue()+"(Included)";
							} else {
								value = "Included";
							}
						} else if(fvt.getType().equals("boolean")){
							value = "Yes";
						}
					}
					features.put(fvt.getExternalId(), fvt.getValue());
				}
				li_feature_groups.put(featureGroup.getExternalId(), features);
			}
		}
		return li_feature_groups;
	}
	
	/**
	 * To get Map of LineItem ExternalId and ServiceType
	 * 
	 * @param order
	 * @param isAccord
	 * @return
	 */
	public Map<String, String> getServiceTypeMap(OrderType order, boolean isAccord){
		Map<String, String> serviceTypeMap = new HashMap<String, String>();
		
		if(order != null){
			for(LineItemType lit: order.getLineItems().getLineItem()){
				String planId = ActivityRetrieveSalesPlan.INSTANCE.getSalesPlan(lit, isAccord);
				if(StringUtils.isNotBlank(planId)){
					serviceTypeMap.put(String.valueOf(lit.getExternalId()), planId);
				}
			}
		}
		
		return serviceTypeMap;
	}
	
	/**
	 * Method to capture error/fatal messages
	 * 
	 * @param OrderManagementRequestResponse omrr
	 * @param ErrorList errorList
	 */
	public void validateOrder(OrderManagementRequestResponse omrr, ErrorList errorList){
		if(omrr != null && omrr.getStatus() != null  && omrr.getStatus().getStatusMsg() != null)
		{
			String statusMsg = omrr.getStatus().getStatusMsg();
			if(statusMsg.equalsIgnoreCase("ERROR") || statusMsg.equalsIgnoreCase("FATAL"))
			{
				if(omrr.getStatus().getProcessingMessages() != null &&
						omrr.getStatus().getProcessingMessages().getMessage() != null)
				{
					for(ProcessingMessage message : omrr.getStatus().getProcessingMessages().getMessage())
					{
						errorList.add(new CartError(String.valueOf(message.getCode()),message.getText()));
					}
				}
			}
		}
	}

	/**
	 * Method to cache GUID
	 * 
	 * @param orderId
	 * @param salesContext
	 */
	public void cacheGUID(long orderId, SalesContextType salesContext) {
		String guid =  SalesContextFactory.INSTANCE.getAttribute(salesContext, GUID);
		CacheService.INSTANCE.storeGUID("ORDER-GUID-"+orderId, guid);
	}
}
