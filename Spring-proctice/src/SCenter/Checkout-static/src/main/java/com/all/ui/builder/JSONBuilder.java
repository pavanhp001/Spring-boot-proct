package com.AL.ui.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AL.html.Input;
import com.AL.html.ObjectFactory;
import com.AL.ui.client.ActionStoreInitialRequest;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.OrderQualVO;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.pr.v4.ProviderSourceBaseType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.SelectedDialogsType;

public enum JSONBuilder {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(ActionStoreInitialRequest.class);
	private static final ObjectFactory oFactory = new ObjectFactory();

	public Long returnLineItem(String CKOInput) throws Exception{

		Long lineItemExternalID = 0L;
		logger.info("Before getting line item external id from Intent");
		//retrieve the lineItemExternalID from initial JSON
		try {
			JSONObject mainJson = new JSONObject(CKOInput);
			JSONObject CKO = mainJson.getJSONObject("CKO");
			JSONObject lineItem = CKO.getJSONObject("lineItems");
			JSONArray externalIds = lineItem.getJSONArray("long");
			lineItemExternalID = externalIds.getLong(0);
		} catch (JSONException e) {
			logger.error("error while getting values from JSON Object");
			e.printStackTrace();
		}
		return lineItemExternalID;
	}

	public String createPromotionJSON(ProductPromotionType promotions, String productDetailType, ProviderSourceBaseType productDataSource ) throws Exception {
		JSONObject promoJson = new JSONObject();
		try {
			promoJson.put("isPromotion", true);
			promoJson.put("productExternalId", promotions.getExternalId());
			promoJson.put("conditions", promotions.getConditions());
			promoJson.put("description", promotions.getShortDescription());
			promoJson.put("priceValue", promotions.getPriceValue());
			promoJson.put("priceValueType", promotions.getPriceValueType());
			promoJson.put("promoCode", promotions.getPromoCode());
			promoJson.put("qualification", promotions.getQualification());
			promoJson.put("type", promotions.getType());
			promoJson.put("shortDescription", promotions.getShortDescription());
			promoJson.put("productDetailType", productDetailType);
			if(productDataSource.value().equalsIgnoreCase("INTERNAL")){
				promoJson.put("productDataSource", "internal");
			}

			StringBuilder promoConflict = new StringBuilder();
			String promoConflictStr = null;
			
			if(promotions.getPreconditions() != null && promotions.getPreconditions().getConflicts() != null)
			{
				for (String promotionConflictExternalID : promotions.getPreconditions().getConflicts().getPromotionExternalId())
				{
					promoConflict.append(promotionConflictExternalID+",");
				}
			}
			if ( promoConflict.toString().endsWith(","))
			{
				promoConflictStr = promoConflict.substring(0, promoConflict.length() - 1);
			}else{
				promoConflictStr = promoConflict.toString();
			}
			
			Input conflictCondition = oFactory.createInput();
			conflictCondition.setHidden("hidden");
			conflictCondition.setName(promotions.getExternalId()+"_Promotions");
			conflictCondition.setValue(promoConflictStr);

			promoJson.put("conflictExternalID", promoConflictStr);
			
			String promoIncludesStr = null;
			if( promotions.getMetaData()!=null && promotions.getMetaData().getMetaData()!=null 
				&& !promotions.getMetaData().getMetaData().isEmpty() )
			{
				StringBuilder promoIncludes = new StringBuilder();
				for(String metaData : promotions.getMetaData().getMetaData())
				{
					if(metaData.contains("INCLUDES="))
					{
						metaData = metaData.replace("INCLUDES=", "");
						promoIncludes.append(metaData+",");
					}
				}
				
				if ( promoIncludes.toString().endsWith(","))
				{
					promoIncludesStr = promoIncludes.substring(0, promoIncludes.length() - 1);
				}else{
					promoIncludesStr = promoIncludes.toString();
				}
			}
			
			if( !Utils.isBlank(promoIncludesStr) )
			{
				promoJson.put("promoIncludesExtId", promoIncludesStr);
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return promoJson.toString();
	}

	public boolean defaultPrevouslySelValuesOnBack(String prevSelectedFeatures, String externalID) throws Exception{

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(prevSelectedFeatures);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		Iterator<? extends String> jsonIterator = jsonObject.keys();
		while(jsonIterator.hasNext()){
			String nextKey = (String)jsonIterator.next();
			String jsonValue = "";
			try {
				jsonValue = jsonObject.getString(nextKey);

				if(externalID.equals(nextKey) && !jsonValue.equalsIgnoreCase("CANCELLED_REMOVED")){
					return true;
				}
//				else if(externalID.equals(nextKey) && jsonValue != null && jsonValue.trim().length() > 0){
//					return true;
//				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String createHiddenInputJSONObject(DataField df, Map<String, String> preSelectedMap) throws Exception{

		JSONObject hiddenInputJSON = new JSONObject();
		String validationType = df.getValidation();
		try {
			if(df.getValidation() != null){
				hiddenInputJSON.put("validation", df.getValidation());
				String dobValueTarget = df.getValueTarget();
				if(validationType.equalsIgnoreCase("SSN") || validationType.equalsIgnoreCase("credit card number") || 
						validationType.equalsIgnoreCase("credit card date") || 
						(dobValueTarget != null && dobValueTarget.equalsIgnoreCase("consumer.dateOfBirth")) || 
						validationType.equalsIgnoreCase("Checking Account Number")){
					// || validationType.equalsIgnoreCase("Routing Number") || validationType.equalsIgnoreCase("Check Number")

					hiddenInputJSON.put("toBeMasked", "true");
				}
				else{
					hiddenInputJSON.put("toBeMasked", "false");
				}
			}
			else{
				hiddenInputJSON.put("validation", "");
			}
			hiddenInputJSON.put("enteredValue", "");
			if(df.getValueTarget() != null){
				hiddenInputJSON.put("valueTarget", df.getValueTarget());
			}
			else{
				hiddenInputJSON.put("valueTarget", "");
			}
			if(df.getValueTarget() != null && preSelectedMap.get(df.getValueTarget())!= null){
				hiddenInputJSON.put("valueTargetVal", preSelectedMap.get(df.getValueTarget()));
				hiddenInputJSON.put("enteredValue", preSelectedMap.get(df.getValueTarget()));
			}
			else{
				hiddenInputJSON.put("valueTargetVal", "");
			}
			// masking should be done for ssn, ccnumber, expiration date, DOB
			hiddenInputJSON.put("maskedValue", "");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return hiddenInputJSON.toString();
	}
	
	/**
	 * takes CKOinput as input and sets the provider_id, line_item_external_id, order_id
	 * to the orderQual Object
	 * 
	 * @param CKOInput
	 * @param request
	 * @return OrderQualVO Object
	 * @throws Exception
	 */
	public OrderQualVO addValuesToOrderQualObject(String CKOInput, HttpServletRequest request) throws Exception{
		OrderQualVO orderQual = new OrderQualVO();
		
		logger.info("Before getting line item external id from Intent");
		//retrieve the lineItemExternalID from initial JSON
		try {
			JSONObject mainJson = new JSONObject(CKOInput);
			JSONObject CKO = mainJson.getJSONObject("CKO");
			JSONObject lineItem = CKO.getJSONObject("lineItems");
			JSONObject params = CKO.getJSONObject("params");
			JSONArray externalIds = lineItem.getJSONArray("long");
			JSONArray paramArray = params.getJSONArray("string");
			
			Long lineItemExternalID = externalIds.getLong(0);			
			String orderID = CKO.getString("orderId");
			String providerArray[] = paramArray.getString(2).split("=");
			String productArray[] = paramArray.getString(4).split("=");
			
			 for (int i=0;i<paramArray.length();i++){ 
				 if(paramArray.getString(i).split("=")[0] != null && paramArray.getString(i).split("=")[0].equalsIgnoreCase("phoneId")){
					 orderQual.setPhoneId(paramArray.getString(i).split("=")[1]);
				 }
				 if(paramArray.getString(i).split("=")[0] != null && paramArray.getString(i).split("=")[0].equalsIgnoreCase("dominionProductExtIds")){
					 orderQual.setDominionProductExtIds(paramArray.getString(i).split("=")[1]);
				 }
				 if(paramArray.getString(i).split("=")[0] != null && paramArray.getString(i).split("=")[0].equalsIgnoreCase("inumVal")){
					 orderQual.setInumVal(paramArray.getString(i).split("=")[1]);
				 }
			} 
			orderQual.setProviderExternalId(providerArray[1]);
			orderQual.setProductExternalId(productArray[1]);
			orderQual.setLineItemExternalId(lineItemExternalID);
			orderQual.setOrderId(orderID);
		} catch (JSONException e) {
			logger.error("error while getting values from JSON Object");
			e.printStackTrace();
		}
		return orderQual;
	}
	
	public void getLineItemExternalIdFromCKOInput(JSONObject CKOInput){
		
		
	}
	
	/**
	 * creates a JSONObject with all the active dialogues and corresponding datatypes this JSONObject is used 
	 * to show the all the values that are selected during the previous CKO
	 * @param prevSelectedDialogueList
	 * @param dialogueNamesList
	 * @return
	 * @throws JSONException
	 */
	public JSONObject buildActiveDialoguesJSON(List<DialogValueType> prevSelectedDialogueList, List<Dialogue> dialogueNamesList) throws JSONException{
		
		JSONObject prevCKOSelDialogues = new JSONObject();
		JSONObject radioButtonJSONArray = new JSONObject();
		JSONObject textBoxJSONArray = new JSONObject();
		JSONObject selectBoxJSONArray = new JSONObject();
		
		/*
		 * iterate over the dialogueNamesList and DataFields from dataGroups, iterates over previously selected Dialogues
		 * creates JSONObject for selected dialogue
		 */
		for(Dialogue dialogue: dialogueNamesList){
			for(DataGroup dGroup : dialogue.getDataGroupList()){
				for(DataField df : dGroup.getDataFieldList()){
					String id = dGroup.getName()+"_"+df.getExternalId();
					for(DialogValueType prevSelectedDialogue : prevSelectedDialogueList){
						if(prevSelectedDialogue.getExternalId().equals(df.getExternalId())){
							if(df.getBooleanConstraint() != null){
								if(prevSelectedDialogue.getValue().get(0).getValue().equalsIgnoreCase("N")){
									id = "BOOL_N_"+id;
								}
								else if(prevSelectedDialogue.getValue().get(0).getValue().equalsIgnoreCase("Y")){
									id = "BOOL_Y_"+id;
								}
								radioButtonJSONArray.put(id, prevSelectedDialogue.getValue().get(0).getValue());
							}
							else if(df.getStringConstraint() != null){
								if(df.getStringConstraint().getValue() != null){
									textBoxJSONArray.put(id, prevSelectedDialogue.getValue().get(0).getValue());
								}
								else if(df.getStringConstraint().getListOfValues() != null){
									selectBoxJSONArray.put(id, prevSelectedDialogue.getValue().get(0).getValue());
								}
							}
							else if(df.getIntegerConstraint() != null){
								if(df.getIntegerConstraint().getListOfValues() != null){
									selectBoxJSONArray.put(id, prevSelectedDialogue.getValue().get(0).getValue());	
								}
								else if(df.getIntegerConstraint().getMinValue() != null && df.getIntegerConstraint().getMaxValue() != null ){
									selectBoxJSONArray.put(id, prevSelectedDialogue.getValue().get(0).getValue());
								}
							}
						}
					}
				}
			}
		}

		if(textBoxJSONArray.length()==0 && radioButtonJSONArray.length()==0 && selectBoxJSONArray.length()==0)
		{
		 return null;	
		}

		prevCKOSelDialogues.put("textBoxes", textBoxJSONArray);
		prevCKOSelDialogues.put("radioButtons", radioButtonJSONArray);
		prevCKOSelDialogues.put("selectBoxes", selectBoxJSONArray);
		//logger.info("PREVIOUS CKO SELECTED VALUES :::::::::: "+prevCKOSelDialogues.toString());
		return prevCKOSelDialogues;
	}
}