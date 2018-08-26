package com.AL.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.util.Utils;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.ProviderSourceBaseType;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

/**
 * @author prabhu sekhar tripuraneni
 *
 */
public enum LineItemBuilder {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(LineItemBuilder.class);
	private static final ObjectFactory oFactory = new ObjectFactory();

	/**
	 * iterate over the selected features and featureGroups and adding them to lineItem, 
	 * this is updated while updating LineItem Status
	 * 
	 * @param dataFieldList 
	 * @param monthlyCost
	 * @param parameterMap
	 * @param featureMap
	 * */
	public LineItemType getLineItemBySelectedFeatures(Map<String, String> requestParamMap, 
			Map<String,FeatureType> selectedFeature,
			Map<String, FeatureGroupType> selectedFeatureGroup, LineItemType item, List<DataField> dataFieldList){

		logger.info("inside getLineItemBySelectedFeatures method");

		List<SelectedFeaturesType.FeatureGroup> selFeatureGroupList = new ArrayList<SelectedFeaturesType.FeatureGroup>();		
		String dataFieldExternalID = "";		

		/*
		 * creating product data source object, this object is set to internal and added to line item
		 */
		ProviderSourceType productDataSource = oFactory.createProviderSourceType();
		productDataSource.setDatasource("internal");
		productDataSource.setValue(ProviderSourceBaseType.INTERNAL);
		item.setProductDatasource(productDataSource);

		SelectedFeaturesType featureType = oFactory.createSelectedFeaturesType();

		SelectedFeaturesType.Features selFeatures = oFactory.createSelectedFeaturesTypeFeatures();

		List<FeatureValueType> selectedFeaturesList = new ArrayList<FeatureValueType>();

		SelectedFeaturesType.FeatureGroup selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();

		/*
		 * checking whether the lineItem contains the selectedFeatures and featureGroups that are not included are present, 
		 * add them to lineItem along with the productCusomization selected features and featureGroups
		 */
		if(item != null && item.getSelectedFeatures() != null){

			featureType = item.getSelectedFeatures();
			if(item.getSelectedFeatures().getFeatures() != null){
				for(FeatureValueType features : featureType.getFeatures().getFeatureValue())
				{
					if(features.getIncluded() == null){
						selectedFeaturesList.add(features);
					}
				}

				selFeatures.getFeatureValue().addAll(selectedFeaturesList);
			}
			if(item.getSelectedFeatures().getFeatureGroup() != null){
				selFeatureGroupList = featureType.getFeatureGroup();
			}
		}
		if(selectedFeature != null){

			for(Entry<String, FeatureType> feature : selectedFeature.entrySet()){
				boolean isPresent = false;
				if(dataFieldList != null && dataFieldList.size() > 0){
					for(DataField df : dataFieldList){
						if(df.getFeatureExternalId() != null && df.getFeatureExternalId().equals(feature) && df.getBooleanConstraint() != null){
							if(requestParamMap.get(df.getExternalId()) != null){
								dataFieldExternalID = df.getExternalId();
								isPresent = true;
							}
						}
					}
				}
				if(requestParamMap != null){
					String featureKey = feature.getKey();
					String selectedValue = "";
					if(requestParamMap.get("selectedValues") != null && requestParamMap.get("selectedValues").trim().length()>0){
						selectedValue = requestParamMap.get("selectedValues");
					}
					boolean containsFeature = false;
					if((requestParamMap.get(featureKey) != null && !requestParamMap.get(featureKey).isEmpty()) || isPresent){
						FeatureValueType fVal = oFactory.createFeatureValueType();

						fVal.setExternalId(feature.getValue().getExternalId());
						if(feature.getValue().getDataConstraints().getBooleanConstraint() != null){
							fVal.setType("boolean");
						}

						else if(feature.getValue().getDataConstraints().getStringConstraint() != null){
							fVal.setType("string");
						}

						else if(feature.getValue().getDataConstraints().getIntegerConstraint() != null){
							fVal.setType("integer");
						}
						boolean isGreaterThanZero = true;
						if(requestParamMap.get(featureKey) != null && requestParamMap.get(featureKey).length() > 0){
							if(!(requestParamMap.get(featureKey).indexOf("-") < 0) ){
								String selectedVal = requestParamMap.get(featureKey).substring(0, requestParamMap.get(featureKey).indexOf("-"));
								if(Integer.parseInt(selectedVal) > 0){
									fVal.setValue(String.valueOf(selectedVal));
									for(FeatureValueType featValueType : selectedFeaturesList){
										if(featValueType.getExternalId().equalsIgnoreCase(featureKey) && featValueType.getValue().equals(String.valueOf(selectedVal))){
											containsFeature = true;
											break;
										}
									}
								}
								else{
									isGreaterThanZero = false;
								}
							}
							else{
								String value = "";
								if(isPresent){
									value = requestParamMap.get(dataFieldExternalID);
								}
								else{
									value = requestParamMap.get(featureKey);
								}
								if(!value.equals("N")){
									if(value.equals("Y")){
										fVal.setValue("true");
									}
									else{
										fVal.setValue(value);
									}
								}
							}
						}
						if(isGreaterThanZero){
							if(fVal.getValue() != null && fVal.getValue().trim().length() > 0){
								if(selectedValue != null && selectedValue.trim().length() > 0){
									if(selectedValue.indexOf(featureKey) > 0){
										if(!containsFeature){
											selFeatures.getFeatureValue().add(fVal);
											featureType.setFeatures(selFeatures);
										}
									}
								}
								else{
									if(!containsFeature){
										selFeatures.getFeatureValue().add(fVal);
										featureType.setFeatures(selFeatures);
									}	
								}
							}
						}
					}
				}
			}
		}

		if(selectedFeatureGroup != null){

			boolean featureGroupAlreadyCOntains = false;
			for(Entry<String, FeatureGroupType>featureGroup : selectedFeatureGroup.entrySet()){
				String featureGroupName = featureGroup.getKey();
				if(requestParamMap.get(featureGroupName) != null && requestParamMap.get(featureGroupName).length() > 0){
					if(!(requestParamMap.get(featureGroup.getKey()).indexOf("_ALL") <= 0)){
						selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
						selFeatureGroup.setExternalId(featureGroup.getValue().getExternalId());
						selFeatureGroup.setGroupType(-1);
						featureType.getFeatureGroup().add(selFeatureGroup);
					}
					else if(requestParamMap.get(featureGroupName) != null && 
							(requestParamMap.get(featureGroupName).trim().length() > 0)){

						FeatureValueType fVal = oFactory.createFeatureValueType();
						selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
						selFeatureGroup.setExternalId(featureGroup.getValue().getExternalId());
						selFeatureGroup.setGroupType(1);

						if(featureGroup.getValue().getDataConstraints() != null){
							if(featureGroup.getValue().getDataConstraints().getStringConstraint() != null){
								if(requestParamMap.get(featureGroupName).contains("::")){
									String val = requestParamMap.get(featureGroupName);
									String externalID = val.substring(0,val.indexOf("::"));
									String selVal = "";
									if(val.indexOf("$$") > 0){
										selVal = val.substring(val.indexOf("::")+2, val.indexOf("$$"));
									}
									else{
										selVal = val.substring(val.indexOf("::")+2);
									}

									for(FeatureGroup featGroup : selFeatureGroupList){
										if(externalID.equalsIgnoreCase(featGroup.getFeatureValue().get(0).getExternalId()) && 
												selVal.equalsIgnoreCase(featGroup.getFeatureValue().get(0).getValue())){
											featureGroupAlreadyCOntains = true;
										}
									}

									fVal.setExternalId(externalID);
									fVal.setValue(selVal);
								}
								else{

								}
							}
							else if(featureGroup.getValue().getDataConstraints().getIntegerConstraint() != null){

								fVal.setValue(""+featureGroup.getValue().getDataConstraints().getIntegerConstraint().getValue());
							}
						}
						if(featureGroup.getValue().getDataConstraints().getStringConstraint() != null){
							fVal.setType("string");
						}
						else if(featureGroup.getValue().getDataConstraints().getBooleanConstraint() != null){
							fVal.setType("boolean");
						}
						else if(featureGroup.getValue().getDataConstraints().getIntegerConstraint() != null){
							fVal.setType("integer");
						}

						selFeatureGroup.getFeatureValue().add(fVal);
						if(!selFeatureGroupList.isEmpty()){
							if(!featureGroupAlreadyCOntains){
								featureType.getFeatureGroup().add(selFeatureGroup);	
							}
						}
						else{
							featureType.getFeatureGroup().add(selFeatureGroup);	
						}
					}
				}
			}
		}
		item.setSelectedFeatures(featureType);
		return item;
	}

	/*This method is used to return the list of
	 * selected features from the product_info screen
	 * @param monthlyCost
	 * @param parameterMap
	 * @param featureMap*/
	public LineItemType getLineItemBySelectedDialogues(Map<String,String> reqParamMap, 
			List<DataField> dataFieldList, 
			Map<String, FeatureType> availableFeatureMap, 
			Map<String, FeatureGroupType> availableFeatureGroupMap, LineItemType itemType, Map<String, String> resultsMap){

		SelectedDialogsType selectedDialogueType = oFactory.createSelectedDialogsType();

		SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory.createSelectedDialogsTypeDialogs();

		DialogValueType dialogueValueType = oFactory.createDialogValueType();

		DialogValueType.Value dialogueValueTypeValue = oFactory.createDialogValueTypeValue();
		String selectedValue = "";
		if(reqParamMap.get("selectedValues") != null && reqParamMap.get("selectedValues").trim().length()>0){
			selectedValue = reqParamMap.get("selectedValues");
		}
		if(reqParamMap != null ){
			if(dataFieldList != null && !dataFieldList.isEmpty()){
				String[] selValueArray = null; 
				if(selectedValue != null && selectedValue.trim().length() > 0 && selectedValue.indexOf(",") > 0){
					selValueArray = selectedValue.split(","); 
				}
				for(DataField dataFieldValue : dataFieldList){

					if(dataFieldValue.getFeatureExternalId() == null && selValueArray != null && arrayContainsString(selValueArray, dataFieldValue.getExternalId())){
						String externalId = dataFieldValue.getExternalId();
						if("credit card date".equalsIgnoreCase(dataFieldValue.getValidation())){
							externalId = externalId+"_CCExpDate";
						}

						if(reqParamMap.get(externalId) != null && reqParamMap.get(externalId).trim().length() > 0){

							dialogueValueTypeValue = oFactory.createDialogValueTypeValue();

							dialogueValueType = oFactory.createDialogValueType();

							dialogueValueType.setExternalId(dataFieldValue.getExternalId());

							dialogueValueTypeValue.setSelected(true);

							if(dataFieldValue.getBooleanConstraint()!=null){
								dialogueValueTypeValue.setType("boolean");	 
							}
							else if(dataFieldValue.getStringConstraint() != null){
								dialogueValueTypeValue.setType("string");
							}
							else if(dataFieldValue.getIntegerConstraint() != null){
								dialogueValueTypeValue.setType("integer");
							}
							String dfExtId = dataFieldValue.getExternalId();
							if(reqParamMap.get(dfExtId+"_JSONVAL") != null && reqParamMap.get(dfExtId+"_JSONVAL").trim().length() > 0){
								String res = reqParamMap.get(dfExtId+"_JSONVAL");
								try {
									JSONObject resJSON = new JSONObject(res);
									if(resJSON.getString("enteredValue")!=null && resJSON.getString("enteredValue").trim().length()>0){
										dialogueValueTypeValue.setValue(resJSON.getString("enteredValue"));
										String providerConformationNumber = resJSON.getString("valueTarget");
										logger.info("provider conformation number value target ::::: "+providerConformationNumber);
										//comcast.order.number
										if(!Utils.isBlank(providerConformationNumber) && providerConformationNumber.equalsIgnoreCase("comcast.order.number")){
											logger.info("entered conformation number ::::: "+resJSON.getString("enteredValue"));
											itemType.setProviderConfirmationNumber(resJSON.getString("enteredValue"));
										}
									}else if(reqParamMap.get(externalId)!=null){
										dialogueValueTypeValue.setValue(reqParamMap.get(externalId));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							else{
								dialogueValueTypeValue.setValue(reqParamMap.get(externalId));
							}
							//removing all the dialogues with type null

							dialogueValueType.getValue().add(dialogueValueTypeValue);

							selectedDialogueTypeDialogue.getDialog().add(dialogueValueType);
						}
					}
				}
				selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
				itemType.setActiveDialogs(selectedDialogueType);
			}

			if(availableFeatureMap != null && availableFeatureGroupMap != null){
				itemType = addFeaturesAndFeatureGroupsToLineItem(reqParamMap, itemType);
			}
		}
		return itemType;
	}

	private boolean arrayContainsString(String[] array, String checkVal){
		if(array.length > 0){
			for(String check : array){
				if(check.indexOf(":") > 0){
					String[] checkColonArray = check.split(":");
					check = checkColonArray[0];
				}
				if(check.equalsIgnoreCase(checkVal) || check.equalsIgnoreCase(checkVal+"_CCExpDate")){
					return true;
				}
			}
		}
		return false;
	}

	public LineItemType returnLineItemByLineItemID(OrderType order, long lineItemExternalID){
		LineItemType lineItem = null;
		if(order.getLineItems() != null){
			for(LineItemType liType : order.getLineItems().getLineItem()){
				if(liType.getExternalId() == lineItemExternalID){
					lineItem = liType;
					break;
				}
			}
		}
		return lineItem;
	}

	/**
	 * adds features and feature groups to line item 
	 * 
	 * @param requestParamMap
	 * @param item
	 * @return
	 */
	public LineItemType addFeaturesAndFeatureGroupsToLineItem(Map<String, String> requestParamMap, LineItemType item){

		String selectedFeatureJSON = requestParamMap.get("selectedFeaturesJSONHiddenValue");
		JSONArray jArray = null;
		logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
		if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
			try {
				jArray = new JSONArray (selectedFeatureJSON);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		SelectedFeaturesType featureType = oFactory.createSelectedFeaturesType();

		SelectedFeaturesType.Features selFeatures = oFactory.createSelectedFeaturesTypeFeatures();

		SelectedFeaturesType.FeatureGroup selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
		System.out.println("JARRAY ::::::::: "+jArray);
		if(jArray != null && jArray.length() > 0){
			for(int i = 0; i < jArray.length(); i++){
				try {
					JSONObject jobj = (JSONObject)jArray.get(i);

					logger.info("JARRAY ELEMENTS ::::: "+ i +" :::::::: "+jobj);
					if(jobj != null && jobj.has("type")){
						String customization = (String)jobj.get("type");
						if(customization.equalsIgnoreCase("feature")){
							boolean isQuantityLessThanZero = false;
							FeatureValueType fVal = oFactory.createFeatureValueType();
							fVal.setExternalId((String) jobj.get("featureExternalID"));
							fVal.setType((String) jobj.get("dataConstraint"));
							if(jobj.get("dataConstraint") != null){
								String dataConstraint = (String) jobj.get("dataConstraint");
								if(dataConstraint.equals("integer")){

									String selectedValue = (String)jobj.get("quantity");
									logger.info("SELECTED VALUE :::::::: "+((String)jobj.get("quantity")));
									logger.info(Integer.valueOf(selectedValue));
									if(Integer.valueOf(selectedValue) > 0){
										fVal.setValue((String)jobj.get("quantity"));	
									}
									else{
										isQuantityLessThanZero = true;
									}

								}else if(dataConstraint.equals("boolean")){
									fVal.setValue("true");
								}
							}
							PriceInfoType priceValue = oFactory.createPriceInfoType();
							if(jobj.has("recuringPrice") && jobj.get("recuringPrice") != null){
								priceValue.setBaseRecurringPrice(Double.valueOf((String)jobj.get("recuringPrice")));
							}
							else{
								priceValue.setBaseRecurringPrice(Double.valueOf("0.00"));
							}
							if(jobj.has("nonRecuringPrice") && jobj.get("nonRecuringPrice") != null){
								priceValue.setBaseNonRecurringPrice(Double.valueOf((String)jobj.get("nonRecuringPrice")));
							}
							else{
								priceValue.setBaseNonRecurringPrice(Double.valueOf("0.00"));
							}
							fVal.setPrice(priceValue);
							if(!isQuantityLessThanZero){
								selFeatures.getFeatureValue().add(fVal);
								featureType.setFeatures(selFeatures);
							}
						}
						else if(customization.equalsIgnoreCase("featureGroup")){
							FeatureValueType fVal = oFactory.createFeatureValueType();
							selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
							selFeatureGroup.setExternalId((String)jobj.get("featureGroupExternalID"));
							selFeatureGroup.setGroupType(1);
							fVal.setExternalId((String) jobj.get("featureExternalID"));
							fVal.setDescription((String) jobj.get("description"));
							fVal.setType((String) jobj.get("dataConstraint"));
							fVal.setValue((String)jobj.get("quantity"));
							selFeatureGroup.getFeatureValue().add(fVal);
							featureType.getFeatureGroup().add(selFeatureGroup);	
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			item.setSelectedFeatures(featureType);
		}
		return item;
	}

	public Map<String,String> buildAttributeCollection(String source,String name, String value, String description){
		Map<String,String> attributeMap = new HashMap<String,String>();
		attributeMap.put("source", source);
		attributeMap.put("name", name);
		attributeMap.put("value", value);
		attributeMap.put("description", description);
		return attributeMap;
	}
	public AttributeEntityType getLineItemAttributeSelction(Map<String,String> attributeMap){
		ObjectFactory oFactory = new ObjectFactory();
		AttributeEntityType entity = oFactory.createAttributeEntityType();
		entity.setSource(attributeMap.get("source"));
		AttributeDetailType attr = oFactory.createAttributeDetailType();
		attr.setName(attributeMap.get("name"));
		attr.setDescription(attributeMap.get("description"));
		attr.setValue(attributeMap.get("value"));
		entity.getAttribute().add(attr);
		return entity;
	}
	
	/**
	 * builds a new LineItemObject(contains item number and lineItemExternal ID) from the existing LineItem  
	 * to avoid included features being added
	 * @param lineItem
	 * @return
	 */
	public LineItemType buildNewLineItem(LineItemType lineItem) {
		ObjectFactory oFactory = new ObjectFactory();
		LineItemType newLineItem = oFactory.createLineItemType();
		newLineItem.setLineItemNumber(lineItem.getLineItemNumber());
		newLineItem.setExternalId(lineItem.getExternalId());

		return newLineItem;
	}
}
