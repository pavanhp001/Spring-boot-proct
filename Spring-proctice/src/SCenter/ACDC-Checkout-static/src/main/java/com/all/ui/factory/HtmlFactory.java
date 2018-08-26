package com.AL.ui.factory;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AL.html.Div;
import com.AL.html.Fieldset;
import com.AL.html.Img;
import com.AL.html.Input;
import com.AL.html.Label;
import com.AL.html.Legend;
import com.AL.html.ObjectFactory;
import com.AL.html.Option;
import com.AL.html.P;
import com.AL.html.Select;
import com.AL.html.Span;
import com.AL.html.Table;
import com.AL.html.Tbody;
import com.AL.html.Td;
import com.AL.html.Th;
import com.AL.html.Thead;
import com.AL.html.Tr;
import com.AL.ui.builder.JSONBuilder;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.domain.sales.IntegerConstraint;
import com.AL.ui.domain.sales.StringConstraint;
import com.AL.ui.util.PriceDisplayUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.DialogueVO;
import com.AL.ui.vo.PriceDisplayVO;
import com.AL.ui.vo.StaticCKOVO;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.PriceTierListType;
import com.AL.xml.pr.v4.PriceTierType;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.pr.v4.ProviderSourceBaseType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

/**
 * @author prabhu
 *
 */
public enum HtmlFactory {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(HtmlFactory.class);

	private static final String DEFAULT_CSS_CLASS = "styled";
	private static final String CCTYPE_CLASS = "ccType";

	private static final ObjectFactory oFactory = new ObjectFactory();

	private static final String RADIO_LABEL_STYLE = "radioLabel";

	private static String context_Path = "";

	/**
	 * create a display for dialogues for customer qualification page
	 * 
	 * @param events
	 * @param dialogueVO
	 * @param enableDependencies
	 * @param dialogueFeatureMap
	 * @param dialogueFeatureGroupMap
	 * @param viewDetailsFeaturesList
	 * @param viewDetailsFeatureGroupList
	 * @param preSelectedMap
	 * @param contextPath
	 * @param requestParamMap
	 * @param stCkVO
	 * @param priceDisplayVOMap 
	 * @return
	 * @throws Exception
	 */
	public List<Fieldset> dialogueToFieldSet(StringBuilder events,
			DialogueVO dialogueVO,
			Map<String, Map<String, List<String>>> enableDependencies, 
			Map<String, FeatureType> dialogueFeatureMap, Map<String, FeatureGroupType> dialogueFeatureGroupMap, 
			List<String> viewDetailsFeaturesList, List<FeatureGroup> viewDetailsFeatureGroupList, 
			Map<String, String> preSelectedMap, String contextPath, Map<String, String> requestParamMap, 
			StaticCKOVO stCkVO, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception{

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		Fieldset mainFieldSet = null;
		String currentDisplayGroupValue = "";
		String previousDisplayGroupValue = "";
		Fieldset divField = null;
		context_Path = contextPath;

		/*
		 * code to hide the back button when the features and feature groups are not present
		 */
		if(dialogueFeatureMap == null && dialogueFeatureGroupMap == null){
			divField = oFactory.createFieldset();
			Div div = oFactory.createDiv();
			div.setId("nobackArrow");
			divField.getContent().add(div);
		}

		/*
		 * iterate over dialogue names list and build dialogues field sets
		 */
		for (Dialogue dialogue : dialogueVO.getDialogueNameList()) {

			/*
			 * we create a field set for each dialogue, we add all the dialogues under this field set
			 */
			mainFieldSet = oFactory.createFieldset();
			mainFieldSet.setClazz("pc_fldset");

			/*
			 * building dialogue header 
			 */
			Legend dialogueHeader = oFactory.createLegend();
			Div dialogueHeaderDiv = oFactory.createDiv();
			dialogueHeaderDiv.getContent().add(String.valueOf(dialogue.getName()));
			dialogueHeader.getContent().add(dialogueHeaderDiv);
			mainFieldSet.getContent().add(dialogueHeader);
			Div dialogueContentDiv = oFactory.createDiv();
			dialogueContentDiv.getClazz().add("pc_fldset_cont");

			for(DataGroup dGroup : dialogue.getDataGroupList()){
				for(DataField df : dGroup.getDataFieldList()){
					/*
						--------------------------------------------------------------------------------------------------------------------
												Start Of logic to indent the fields under the same display group
						--------------------------------------------------------------------------------------------------------------------
					 */


					/*
					 * some dialogues are related and come under same type, if this type of dialogues occur, they are given same displayGroup tag
					 * eg: creditCardNumber, creditCardType, expirationDate are the fields that comes under credit card type
					 * so, they are given under a display group called credit card
					 * 
					 * if the dialogues with same displayGroup occurs, they are displayed with an indentation
					 */
					Div contentDiv = oFactory.createDiv();

					/*
					 * check whether the current data field comes under the same displayGroup as previous, if current and previous are same, 
					 * they are to be indented
					 */
					if(df.getDisplayGroup() != null){
						currentDisplayGroupValue = df.getDisplayGroup();
					}
					if (!currentDisplayGroupValue.equals(previousDisplayGroupValue)){
						Div displayGroupNameDiv = oFactory.createDiv();
						displayGroupNameDiv.getContent().add(currentDisplayGroupValue);
						displayGroupNameDiv.getClazz().add("display_group_header");
						contentDiv.getContent().add(displayGroupNameDiv);
					}

					/*
						--------------------------------------------------------------------------------------------------------------------
											End Of logic to indent the fields under the same display group
						--------------------------------------------------------------------------------------------------------------------
					 */

					/*
					 * enabled fields are those fields that are to be shown when the page is loaded
					 * if the field is not enabled, they dependend on some other data field
					 */
					if (!df.isEnabled()) {
						contentDiv.setStyle("display:none;");
					}

					/*
					 * external id with '/' is an invalid external id(this poses a problem in js files).
					 * so, replacing the '/' in external id with '_'
					 */
					String mainString = df.getExternalId();
					if(mainString.contains("/")){
						mainString = df.getExternalId().replace("/", "_");
					}

					contentDiv.setId(dGroup.getName()+"_"+ mainString+"_FS");

					/*
					 * checking if the data field is of info type, if the fields are of info type, we display them without any selection type
					 * info types are of 3 types: 
					 * 		--> Disclosure: these type of dialogues are displayed with bold letters and text-color black and 
					 * 			a text 'MANDATORY' is appended at starting of this test 
					 * 		--> Notable: these type of dialogues are displayed with text-color green and an 
					 * 			image 'i' saying that it is an informational dialogue is shown.
					 * 		--> Informational: this is same as notable type dialogue.
					 */
					if(df.getInfoType() != null){
						if(df.getInfoType().equalsIgnoreCase("DISCLOSURE") || df.getInfoType().equalsIgnoreCase("notable") || df.getInfoType().equalsIgnoreCase("informational")){

							contentDiv.getClazz().add("pc_fldset_data_item_cont");

							Div disclosureDiv = oFactory.createDiv();
							disclosureDiv.getClazz().add("label_desc");
							String dialogueLabel = df.getText();
							dialogueLabel = stCkVO.replaceNamesWithValues(dialogueLabel);
							Label disclosureLabel = oFactory.createLabel();

							if(df.getInfoType().equalsIgnoreCase("DISCLOSURE")){
								disclosureLabel.setClazz("disclosure_type_label");

								disclosureLabel.getContent().add(dialogueLabel);
							}else if(df.getInfoType().equalsIgnoreCase("notable") || df.getInfoType().equalsIgnoreCase("informational")){

								Img img = oFactory.createImg();
								img.setSrc(contextPath+"/image/CKO_info_icon.png");
								img.setStyle("vertical-align:middle");
								disclosureDiv.getContent().add(img);
								disclosureLabel.getContent().add(dialogueLabel);
								disclosureLabel.setClazz("info_type_label");
							}
							if(df.getDisplayGroup() != null){
								disclosureDiv.getClazz().add("display_group_element");
							}
							disclosureDiv.getContent().add(disclosureLabel);
							disclosureDiv.setId(df.getExternalId());
							contentDiv.getContent().add(disclosureDiv);
							dialogueContentDiv.getContent().add(contentDiv);
						}
					}
					/*
					 * 	if the promotion is not of InfoType, we need to display it as text type or select box type or radio type 
					 * 	based on the data constraint the data field is having 
					 */
					else{
						Div txtFieldsDiv = oFactory.createDiv();
						txtFieldsDiv.getClazz().add("pc_fldset_subcont_txtflds");

						contentDiv.getClazz().add("pc_fldset_data_item");

						/*
						 * display the text with grey color and add padding if the datafield contains displayGroup
						 */
						Span dialogueLabelSpan = oFactory.createSpan();
						dialogueLabelSpan.getClazz().add("label");
						if(df.getDisplayGroup() != null){
							dialogueLabelSpan.setStyle("padding-left: 100px; !important; width: 400px; !important; color:grey;");
						}
						else{
							dialogueLabelSpan.setStyle("color:grey;");	
						}

						/*
						 * replacing the special text with dynamic data
						 * eg: {businessParty.name} with the name that is obtained on the product
						 */
						String dialogueLabel = df.getText();
						dialogueLabel = stCkVO.replaceNamesWithValues(dialogueLabel);
						Label disclosureLabel = oFactory.createLabel();
						disclosureLabel.getContent().add(dialogueLabel);
						dialogueLabelSpan.getContent().add(disclosureLabel);

						/*
						 * generating the display type based on the type of data constraint present
						 */
						InputTypeEnum inputDisplayType = getDialogueDisplayType(df);

						boolean hasDependency = false;

						/*
						 * checks whether dialogue are dependent on other dialogues, if they are dependent, 
						 * then we show them when corresponding parent is selected 
						 */
						if (enableDependencies != null) {
							hasDependency = enableDependencies.containsKey(dGroup.getName()+"_"+df.getExternalId());
						}

						Span dialogueValueSpan = oFactory.createSpan();
						dialogueValueSpan.getClazz().add("value");

						/*
						 * main logic for building the selection types is handled in this method
						 */
						addDataField(dialogueValueSpan, df, inputDisplayType, hasDependency, dialogueFeatureMap, 
								dialogueFeatureGroupMap, viewDetailsFeaturesList, viewDetailsFeatureGroupList, 
								preSelectedMap, contextPath, dGroup.getName(), requestParamMap, priceDisplayVOMap);

						contentDiv.getContent().add(dialogueLabelSpan);
						contentDiv.getContent().add(dialogueValueSpan);

						if(df.getDescription() != null)
						{
							if(!df.getDescription().equalsIgnoreCase("optional"))
							{
								Img img = oFactory.createImg();
								img.setSrc(contextPath+"/image/red_asterisk.png");
								img.setId(dGroup.getName()+"_"+df.getExternalId()+"_required");

								img.setAlt("<span class='requiredField'>" +
								"<font color='red'>*</font></span>");
								img.setClazz("requiredField error");
								img.setStyle("display:none");
								contentDiv.getContent().add(img);
							}
						}
						else if(!Utils.isBlank(df.getFeatureExternalId()))
						{
							Img img = oFactory.createImg();
					    	img.setSrc(contextPath+"/image/red_asterisk.png");
					    	img.setId(df.getFeatureExternalId()+"_required");
					     	img.setAlt("<span class='requiredField'>" +
					     		"<font color='red'>*</font></span>");
					     	img.setClazz("requiredField error");
					    	img.setStyle("display:none");
					     	contentDiv.getContent().add(img);
					    }
						else
						{
							Img img = oFactory.createImg();
							img.setSrc(contextPath+"/image/red_asterisk.png");
							img.setId(dGroup.getName()+"_"+df.getExternalId()+"_required");

							img.setAlt("<span class='requiredField'>" +
							"<font color='red'>*</font></span>");
							img.setClazz("requiredField error");
							img.setStyle("display:none");
							contentDiv.getContent().add(img);
						}
						txtFieldsDiv.getContent().add(contentDiv);
						dialogueContentDiv.getContent().add(txtFieldsDiv);							
					}
					previousDisplayGroupValue = currentDisplayGroupValue;
					currentDisplayGroupValue = "";
				}
			}
			mainFieldSet.getContent().add(dialogueContentDiv);
			fieldsetList.add(mainFieldSet);
		}

		/*
		 * logic to set the selected features in the feature customization page to hidden value
		 * this JSONObject is iterated and the values are added to the price Flyout
		 */
		String selectedFeatureJSON = requestParamMap.get("selectedFeaturesJSONHiddenValue");
		//<input type="hidden" id="selectedFeatureJSONValue" name="selectedFeatureJSONValue"/>

		String oqSelectedFeaturesHIDDENValue = requestParamMap.get("oqSelectedFeaturesHIDDENValue");

		logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
		if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
			if(selectedFeatureJSON.indexOf("selectedFeatureArray") >= 0){
				JSONObject selectedFeatureJSONObject = new JSONObject(selectedFeatureJSON);
				selectedFeatureJSON = selectedFeatureJSONObject.getString("selectedFeatureArray");
			}
			JSONArray jArray = new JSONArray(selectedFeatureJSON);
			logger.info("JSON ARRAY LENGTH ::::: "+jArray.length());
			JSONObject jObject = new JSONObject();
			jObject.put("selectedFeatureArray", jArray);
			logger.info("Selected Features JSON ::::::::: "+jObject);
			if(jObject != null){
				Input selectedFeatureJSONHiddenInput = oFactory.createInput();
				selectedFeatureJSONHiddenInput.setType("hidden");
				selectedFeatureJSONHiddenInput.setId("selectedFeaturesJSONHiddenValue");
				selectedFeatureJSONHiddenInput.setName("selectedFeaturesJSONHiddenValue");
				selectedFeatureJSONHiddenInput.setValue(jObject.toString());
				divField = oFactory.createFieldset();
				divField.getContent().add(selectedFeatureJSONHiddenInput);
				divField.setStyle("display:none");
				fieldsetList.add(divField);
			}
		}
		else{
			Input selectedFeatureJSONHiddenInput = oFactory.createInput();
			selectedFeatureJSONHiddenInput.setType("hidden");
			selectedFeatureJSONHiddenInput.setId("selectedFeaturesJSONHiddenValue");
			selectedFeatureJSONHiddenInput.setName("selectedFeaturesJSONHiddenValue");
			selectedFeatureJSONHiddenInput.setValue("{}");
			divField = oFactory.createFieldset();
			divField.getContent().add(selectedFeatureJSONHiddenInput);
			divField.setStyle("display:none");
			fieldsetList.add(divField);
		}
		Input selectedFeatureJSONHiddenInput = oFactory.createInput();
		selectedFeatureJSONHiddenInput.setType("hidden");
		selectedFeatureJSONHiddenInput.setId("oqSelectedFeaturesHIDDENValue");
		selectedFeatureJSONHiddenInput.setName("oqSelectedFeaturesHIDDENValue");

		if(oqSelectedFeaturesHIDDENValue != null && oqSelectedFeaturesHIDDENValue.trim().length() > 0){
			JSONArray oqSelectedFeaturesHIDDENValueArray = new JSONArray(oqSelectedFeaturesHIDDENValue);
			JSONObject selectedFeatureJSONObject = new JSONObject();
			selectedFeatureJSONObject.put("oqselfeatures", oqSelectedFeaturesHIDDENValueArray);
			if(selectedFeatureJSONObject != null){
				selectedFeatureJSONHiddenInput.setValue(selectedFeatureJSONObject.toString());
			}
		}

		divField = oFactory.createFieldset();
		divField.getContent().add(selectedFeatureJSONHiddenInput);
		divField.setStyle("display:none");
		fieldsetList.add(divField);

		return fieldsetList;
	}

	/**
	 * main logic to build selection type of dialogues is handled here
	 * @param divFieldset
	 * @param df
	 * @param inputType
	 * @param hasDependency
	 * @param dialogueFeatureMap
	 * @param dialogueFeatureGroupMap
	 * @param viewDetailsFeaturesList
	 * @param viewDetailsFeatureGroupList
	 * @param preSelectedMap
	 * @param contextPath
	 * @param dataGroupName
	 * @param requestParamMap
	 * @param priceDisplayVOMap 
	 * @throws Exception
	 */
	private void addDataField(Span divFieldset, DataField df, InputTypeEnum inputType, boolean hasDependency, 
			Map<String,FeatureType> dialogueFeatureMap, Map<String,FeatureGroupType> dialogueFeatureGroupMap, 
			List<String> viewDetailsFeaturesList, List<FeatureGroup> viewDetailsFeatureGroupList,  
			Map<String, String> preSelectedMap, String contextPath, String dataGroupName, 
			Map<String, String> requestParamMap, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception {

		/*
		 * check whether the data field is of feature type or feature group type, 
		 */
		if(df.getFeatureExternalId() != null && (dialogueFeatureMap != null || dialogueFeatureGroupMap != null)){

			Span displaySpan = oFactory.createSpan();
			if(dialogueFeatureMap != null){

				if(dialogueFeatureMap.get(df.getFeatureExternalId()) != null){
					addDialogueFeatureField(dialogueFeatureMap.get(df.getFeatureExternalId()), 
							viewDetailsFeaturesList, preSelectedMap, df, divFieldset, 
							hasDependency, df.getExternalId(), dataGroupName, priceDisplayVOMap, requestParamMap);
				}
			}
			if(dialogueFeatureGroupMap != null){
				if(dialogueFeatureGroupMap.get(df.getFeatureExternalId()) != null){
					divFieldset.getContent().add(addDialogueFeatureGroupField(dialogueFeatureGroupMap.get(df.getFeatureExternalId()), 
							viewDetailsFeatureGroupList, preSelectedMap, df, hasDependency, 
							df.getExternalId(), dataGroupName, priceDisplayVOMap));
				}
			}

			divFieldset.getContent().add(displaySpan);
		}
		else{

			String mainString = df.getExternalId(); 

			if (inputType == InputTypeEnum.select) {
				buildSelectTypeDialogues(df, hasDependency, preSelectedMap, divFieldset, dataGroupName);
			}

			else if (inputType == InputTypeEnum.text) {
				buildTextTypeDialogues(inputType, mainString, df, preSelectedMap, 
						divFieldset, dataGroupName, requestParamMap);

			} else if (inputType == InputTypeEnum.radio) {
				if (df.getBooleanConstraint() != null) {
					createBooleanFields(df, divFieldset, hasDependency, preSelectedMap, null, dataGroupName, null, null);
				}
			}
		}
	}

	private void createBooleanFields(DataField df, Span fieldset, boolean hasDependency, 
			Map<String, String> preSelectedMap, List<String> viewDetailsSelectedFeatureList, String dataGroupName, 
			Map<String, List<PriceDisplayVO>> priceDisplayVOMap, JSONArray jArray) throws Exception {

		Span rSpan = oFactory.createSpan();

		rSpan.setStyle("display:inline-block;");

		String externalID = df.getExternalId();

		String mainString = externalID;

		if(mainString.contains("/")){
			mainString = mainString.replace("/", "_");
		}
		Span labelY = oFactory.createSpan();


		Input inputY = oFactory.createInput();
		inputY.setType(InputTypeEnum.radio.name());
		inputY.setClazz(DEFAULT_CSS_CLASS+" "+RADIO_LABEL_STYLE);
		if(df.getFeatureExternalId() != null){
			inputY.setName(df.getFeatureExternalId());
			inputY.setClazz(DEFAULT_CSS_CLASS+" "+RADIO_LABEL_STYLE+" Feature"+" FeatureCheck");
		}
		else{
			inputY.setName(externalID);
		}
		inputY.setId("BOOL_Y_" + dataGroupName +"_" + mainString);
		inputY.setValue("Y");
		if(priceDisplayVOMap != null && !priceDisplayVOMap.isEmpty()){
			if(df.getFeatureExternalId() != null && priceDisplayVOMap.get(df.getFeatureExternalId()) != null){
				List<PriceDisplayVO> priceDisplayValue = priceDisplayVOMap.get(df.getFeatureExternalId());
				if(priceDisplayValue != null){
					inputY.setValue(PriceDisplayUtil.INSTANCE.convertToJSON(priceDisplayValue.get(0)).toString());
				}
			}
		}
		if(jArray != null && jArray.length() > 0){
			for(int i = 0; i < jArray.length(); i++){
				JSONObject individualJSONObject = (JSONObject)jArray.get(i);
				if(df.getFeatureExternalId().equals(individualJSONObject.get("featureExternalID"))){
					inputY.setChecked("checked");
				}
			}
		}

		if (hasDependency) {
			inputY.setOnclick("activate(\'" + "BOOL_Y_" + dataGroupName +"_" + mainString + "\')");
		}
		if(viewDetailsSelectedFeatureList != null && viewDetailsSelectedFeatureList.size() > 0){
			for(String viewDetailsSelFeatures : viewDetailsSelectedFeatureList){
				String[] viewDetailsArr = viewDetailsSelFeatures.split("::");
				if(viewDetailsArr[0].equals(df.getFeatureExternalId()) && (viewDetailsArr[1].equals("Y") || viewDetailsArr[1].equalsIgnoreCase("true"))){
					inputY.setChecked("checked");
				}
			}
		}
		rSpan.getContent().add(inputY);
		labelY.getContent().add("Yes");
		rSpan.getContent().add(labelY);

		Span labelN = oFactory.createSpan();

		Input inputN = oFactory.createInput();
		inputN.setType(InputTypeEnum.radio.name());
		inputN.setId("BOOL_N_" + dataGroupName +"_" + mainString);
		inputN.setClazz(DEFAULT_CSS_CLASS+" "+RADIO_LABEL_STYLE);
		if(df.getFeatureExternalId() != null){
			inputN.setName(df.getFeatureExternalId());	
			inputN.setClazz(DEFAULT_CSS_CLASS+" "+RADIO_LABEL_STYLE+" Feature"+" FeatureCheck");
		}
		else{
			inputN.setName(externalID);
		}
		inputN.setValue("N");
		if (hasDependency) {
			inputN.setOnclick("activate(\'" + "BOOL_N_" + dataGroupName +"_" + mainString + "\')");
		}
		if(viewDetailsSelectedFeatureList != null && viewDetailsSelectedFeatureList.size() > 0){
			for(String viewDetailsSelFeatures : viewDetailsSelectedFeatureList){
				String[] viewDetailsArr = viewDetailsSelFeatures.split("::");
				if(viewDetailsArr[0].equals(df.getFeatureExternalId()) && viewDetailsArr[1].equals("N")){
					inputN.setChecked("checked");
				}
			}
		}
		rSpan.getContent().add(inputN);
		labelN.getContent().add("No");
		rSpan.getContent().add(labelN);

		fieldset.getContent().add(rSpan);
	}

	/**
	 * checks the type of dataConstraint present and generates an inputType accordingly
	 * @param df
	 * @return
	 * @throws Exception
	 */
	private InputTypeEnum getDialogueDisplayType(DataField df) throws Exception {
		InputTypeEnum optionDisplayType = null;

		/*
		 * if the dataConstraint is of StringConstraint and contains a value, it is displayed as text box
		 * if the dataConstraint is of StringConstraint and contains a list of values, it is displayed as a select box
		 */
		if (df.getStringConstraint() != null) {
			if (df.getStringConstraint().getValue() != null) {
				optionDisplayType = InputTypeEnum.text;
			} else if (df.getStringConstraint().getListOfValues() != null) {
				optionDisplayType = InputTypeEnum.select;
			}
		} 
		/*
		 * if the dataConstraint is of booleanConstraint, it is displayed as a Radio Type 
		 */
		else if (df.getBooleanConstraint() != null) {
			optionDisplayType = InputTypeEnum.radio;
		}
		/*
		 * if the dataConstraint is of integerConstraint and contains a list of values or 
		 * minValue and maxValue, it is displayed as select box if both of them are not present,
		 * it is displayed as a text box
		 */
		else if (df.getIntegerConstraint() != null) {
			if ((df.getIntegerConstraint().getListOfValues() != null)&& (df.getIntegerConstraint().getListOfValues().size() > 0)) {
				optionDisplayType = InputTypeEnum.select;
			}
			else if(df.getIntegerConstraint().getMinValue() != null && df.getIntegerConstraint().getMaxValue() != null){
				optionDisplayType = InputTypeEnum.select;
			}
			else {
				optionDisplayType = InputTypeEnum.text;
			}
		}
		return optionDisplayType;
	}

	/**
	 * code to build feature and feature group related display
	 * 
	 * @param features
	 * @param featureGroupList
	 * @param prevSelectedFeatures
	 * @param request
	 * @param previousCKOSelFeatureList
	 * @param previouslySelectedFeatureGroups
	 * @param priceDisplayVOMap 
	 * @return
	 * @throws Exception
	 */
	public Fieldset getFeatureFieldSet(List<FeatureType> features, List<FeatureGroupType> featureGroupList, 
			String prevSelectedFeatures,HttpServletRequest request, List<String> previousCKOSelFeatureList, 
			List<FeatureGroup> previouslySelectedFeatureGroups, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception {

		/**
		 * sorting all the features
		 */
		List<FeatureType> sortedFeatures = Utils.sort(features);

		Fieldset fieldSet = oFactory.createFieldset();

		/**
		 * features and feature groups are displayed as table fields
		 */
		Table table = oFactory.createTable();

		/**
		 * table header contains type of feature, price, type of seletion
		 */
		Thead thead = oFactory.createThead();
		Tr tr = oFactory.createTr();
		Th th = oFactory.createTh();

		/**
		 * feature type is the name of the feature this is constant for features and feature groups
		 */
		// header 1      
		th = oFactory.createTh();
		th.setStyle("text-align:center");
		th.getContent().add("Feature Type");
		tr.getTh().add(th);

		/**
		 * 1. price display rules for feature 
		 * 	a. if the feature is included, then we show the price as included
		 * 	b. if the feature is unavailable, we show the price as unavailable
		 * 	c. if the feature is available feature, we show the price as:
		 * 		case 1: if the feature doesnot contains the price tiers
		 * 			--> if the feature contains both recurring price and non recurring price, 
		 * 					we show the price as nonrecurring price/recurringprice.
		 * 			--> if the feature contains only recurring price, ie., if the nonrecurring 
		 * 					price is 0.0, we show only the recurring price.
		 * 			--> if the feature contains only nonrecurring price, ie., if the recurring price
		 * 					is 0.0, we show nonrecurring price/0.0
		 * 			the difference in the display shown above, is only to differentiate between the
		 * 			recurring price and nonrecurring price if one of them is missing.
		 * 		case 2: if the feature's price is given as tier price
		 * 			--> show each range start to range end of current price tier and their corresponding 
		 * 				price values (price display rules same as normal price rules).
		 * 2. if it is a feature group, we show the corresponding drop down 
		 * 		with each feature of the feature group as option
		 */
		th = oFactory.createTh();
		th.getContent().add("Price");
		tr.getTh().add(th);

		/**
		 * 1. selection type for features
		 * 	Selection type is shown based on the data constraint that we get in the features
		 * 		if the feature is available then, we show the following display
		 * 			a. if the dataconstraint is of boolean type, we display check box.
		 * 			b. if the dataconstraint is of text type, we display the value if the 
		 * 				value is present or we show '--'
		 * 			c. if the data constraint is of integer type, 
		 * 				--> if we get list of values or the range start and range end, we display a 
		 * 					selectbox with options as following
		 * 						# if the feature contain recurring price and non recurring price, each option should be
		 * 							1--nonrecurring/recurring price with recurring and non recurring prices 
		 * 							added to cumilatively
		 * 						# if the features contain only recurring price, each option is displayed as
		 * 							1--recurring price with recurring price added cumilatively
		 * 						# if the feature contains only nonrecurring price, each option is displayed as
		 * 							1-- nonrecurring/0.0 with non recurring price added cumilatively.
		 * 						# if both recurring price and non recurring prices are not present, we display the 
		 * 							number of feature that customer can select
		 * 				--> if the value of integer constraint is a single number, we show the number in the options column
		 * 		if the feature is included, we display '--'.
		 * 		if the feature is unavailable feature, we display 'NA'
		 * 2. for feature groups, we display '--' in this column
		 *  
		 */
		th = oFactory.createTh();
		th.getContent().add("Selection Type");
		tr.getTh().add(th);
		thead.getTr().add(tr);

		table.setThead(thead);

		Tbody tbody = oFactory.createTbody();

		/**
		 * building display for features
		 */
		for (FeatureType feature : sortedFeatures) {
			tr = oFactory.createTr();
			addTableDataFeaturesField(feature, tr, previousCKOSelFeatureList, prevSelectedFeatures, request, priceDisplayVOMap);
			tr.setStyle("text-align:center");
			tbody.getTr().add(tr);
		}
		table.setTbody(tbody);

		/**
		 * building display for feature Group
		 */
		if(featureGroupList != null){
			for (FeatureGroupType featureGroup : featureGroupList) {
				tr = oFactory.createTr();
				addTableDataFeatureGroupField(featureGroup, tr, previouslySelectedFeatureGroups, prevSelectedFeatures, request, priceDisplayVOMap);
				tr.setStyle("text-align:center");
				tbody.getTr().add(tr);
			}
		}

		fieldSet.getContent().add(table);

		/*
		 * create a hidden input and setting the previous selected feature values to it
		 */
		Input hiddenInput = oFactory.createInput();
		hiddenInput.setType("hidden");
		hiddenInput.setName("selectedFeaturesJSONHiddenValue");
		hiddenInput.setId("selectedFeaturesJSONHiddenValue");
		String selectedFeatureJSON = request.getParameter("selectedFeaturesJSONHiddenValue");
		logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
		if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
			if(selectedFeatureJSON.indexOf("selectedFeatureArray") >= 0){
				JSONObject selectedFeatureJSONObject = new JSONObject(selectedFeatureJSON);
				selectedFeatureJSON = selectedFeatureJSONObject.getString("selectedFeatureArray");
			}
			hiddenInput.setValue(selectedFeatureJSON);
		}
		fieldSet.getContent().add(hiddenInput);
		return fieldSet;
	}

	private Span addDialogueFeatureField(FeatureType features, List<String> viewDetailsFeaturesList, Map<String, String> preSelectedMap, 
			DataField df, Span fieldset, boolean hasDependency, String externalID, String dataGroupName, 
			Map<String, List<PriceDisplayVO>> priceDisplayVOMap, Map<String, String> requestParamMap)  throws Exception{

		String selectedFeatureJSON = requestParamMap.get("selectedFeaturesJSONHiddenValue");
		JSONArray jArray = null;
		logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
		if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
			if(selectedFeatureJSON.indexOf("selectedFeatureArray") >= 0){
				JSONObject selectedFeatureJSONObject = new JSONObject(selectedFeatureJSON);
				selectedFeatureJSON = selectedFeatureJSONObject.getString("selectedFeatureArray");
			}
			jArray = new JSONArray (selectedFeatureJSON);
		}

		if(features.getDataConstraints().getBooleanConstraint()!= null){
			if(features.isAvailable()){
				Input checkboxHiddenInput = createHiddenInputCheckinBox(features);
				fieldset.getContent().add(checkboxHiddenInput);
				createBooleanFields(df, fieldset, hasDependency, preSelectedMap, 
						viewDetailsFeaturesList, dataGroupName, priceDisplayVOMap, jArray);	
			}
			else{
				fieldset.getContent().add("UNAVAILABLE");
			}
		}
		else if (features.getDataConstraints().getIntegerConstraint() != null ) {
			//checking if the feature is available and performing corresponding operations
			if(!features.isAvailable()){
				fieldset.getContent().add("UNAVAILABLE");
			}
			else if(features.getIncluded() == null && features.isAvailable()){
				if(features.getDataConstraints().getIntegerConstraint().getMinValue() != null || 
						features.getDataConstraints().getIntegerConstraint().getMaxValue() != null){

					Select select =  PriceDisplayUtil.INSTANCE.populateOptionsListForSelect(features, priceDisplayVOMap, null);
					String selectClass = select.getClazz();
					if(! Utils.isBlank(selectClass)){
						select.setClazz(selectClass + " FeatureSelect");
					}
					else{
						select.setClazz("FeatureSelect");	
					}

					defaultTheSelectedField(select, null, null);

					if(jArray != null && jArray.length() > 0){
						for(int i = 0; i < jArray.length(); i++){
							JSONObject individualJSONObject = (JSONObject)jArray.get(i);
							if(features.getExternalId().equals(individualJSONObject.get("featureExternalID"))){
								defaultTheSelectedField(select, individualJSONObject, null);
							}
						}
					}
					if(viewDetailsFeaturesList != null && !viewDetailsFeaturesList.isEmpty()){
						for(String viewDetailsFeatures : viewDetailsFeaturesList){
							if(viewDetailsFeatures != null && viewDetailsFeatures.indexOf("::") > 0){
								String[] viewDetailsExtIDValue =  viewDetailsFeatures.split("::");
								if(features.getExternalId().equalsIgnoreCase(viewDetailsExtIDValue[0])){
									defaultTheSelectedField(select, null, viewDetailsExtIDValue[1]);
								}
							}
						}
					}

					fieldset.getContent().add(select);
				}else if (features.getDataConstraints().getIntegerConstraint().getValue() != null) {
					BigInteger intContraint = features.getDataConstraints().getIntegerConstraint().getValue();
					fieldset.getContent().add(String.valueOf(intContraint));
				}
				else {
					fieldset.getContent().add("NA");
				}
			}
			else if(features.getIncluded() != null ){
				if (features.getDataConstraints().getIntegerConstraint().getValue() != null) {
					BigInteger intContraint = features.getDataConstraints().getIntegerConstraint().getValue();

					fieldset.getContent().add(String.valueOf(intContraint));
				}
				else {
					fieldset.getContent().add("NA");
				}
			}
		}
		else if (features.getDataConstraints().getStringConstraint() != null ) {
			InputTypeEnum inputType = InputTypeEnum.text;
			if(features.isAvailable()){
				buildTextTypeDialogues(inputType, externalID, df, preSelectedMap, fieldset, 
						dataGroupName, null);
			}
			else{
				fieldset.getContent().add("UNAVAILABLE");
			}
		}
		return fieldset;
	}

	/**
	 * default the selected Value if it is selected during previous CKO or during going forward
	 * @param select
	 * @param individualJSONObject
	 * @param selectedQuantity
	 */
	private void defaultTheSelectedField(Select select, JSONObject individualJSONObject, String selectedQuantity){
		List<Object> options = select.getOptionOrOptgroup();
		for(Object opts : options){
			Option opt = (Option)opts;
			String value = opt.getValue();
			if(opt.getContent() != null && opt.getContent().length() > 40){
				select.setStyle("max-width:300px");
			}
			if(!Utils.isBlank(value) && !value.equals("Please Select")){
				try{
					JSONObject jsonObj = new JSONObject(value);
					if(individualJSONObject != null && individualJSONObject.has("quantity")){
						if(jsonObj.get("quantity").equals(individualJSONObject.get("quantity"))){
							opt.setSelected("selected");
						}
					}
					else if(! Utils.isBlank(selectedQuantity) && jsonObj.get("quantity").equals(selectedQuantity)){
						opt.setSelected("selected");
					}
					else if(jsonObj.has("includedFlag") 
							&& jsonObj.getBoolean("includedFlag")){
						opt.setSelected("selected");
					}
				}
				catch(JSONException exception){
					logger.info("Error While parsing the JSON");
				}
			}
		}
	}

	private Span addDialogueFeatureGroupField(FeatureGroupType featureGroup, 
			List<FeatureGroup> viewDetailsFeatureGroupList, Map<String, String> preSelectedMap, DataField df, boolean hasDependency, 
			String externalID, String dataGroupName, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception {

		Span span = oFactory.createSpan();
		//DecimalFormat format = new DecimalFormat("#0.00");
		Span bodySpan = null;

		if(featureGroup.getSelectionType().getPickOne() != null){

			Select select = PriceDisplayUtil.INSTANCE.populateOptionsListForSelect(null, priceDisplayVOMap, featureGroup);
			select.setClazz(DEFAULT_CSS_CLASS+" FeatureGroup");
			select.setId(dataGroupName+"_"+externalID);
			if (hasDependency) {
				select.setOnchange("activate(\'" + dataGroupName+"_"+externalID + "\')");
			}
			/*	boolean isIncludedFeature = false;
			for(FeatureType features : featureGroup.getFeature()){
				if(features.getIncluded() != null){
					isIncludedFeature = true;
				}
			}

			logger.info("IS INCLUDED :::::::: "+isIncludedFeature);
			if(!isIncludedFeature){
				Option baseOpt = null;
				baseOpt = oFactory.createOption();
				baseOpt.setContent("Please Select");
				baseOpt.setValue("");
				select.getOptionOrOptgroup().add(baseOpt);
			}
			Map<String, String> viewDetailsSelectedFeatureGroupMap = new HashMap<String, String>();
			if(viewDetailsFeatureGroupList != null && viewDetailsFeatureGroupList.size() > 0){
				for(FeatureGroup featureGroupType : viewDetailsFeatureGroupList){
					if(featureGroupType.getExternalId().equals(df.getFeatureExternalId())){
						for(FeatureValueType featureValue : featureGroupType.getFeatureValue()){
							viewDetailsSelectedFeatureGroupMap.put(featureValue.getExternalId(), featureValue.getValue());
						}
					}
				}
			}
			for(FeatureType features : featureGroup.getFeature()){

				if(features.getIncluded() != null){
					Option option = oFactory.createOption();
					option.setContent(features.getDataConstraints().getStringConstraint().getValue()+" - "+"INCLUDED");

					String widthValue = features.getDataConstraints().getStringConstraint().getValue()+" - "+"INCLUDED";
					if(widthValue != null && widthValue.length() > 40){
						select.setStyle("max-width:300px");
					}

					String value = features.getExternalId();
					String origValue = value+"::"+features.getDataConstraints().getStringConstraint().getValue();
					option.setValue(origValue);
					if(df.getValueTarget() != null){
						if(preSelectedMap.get(df.getValueTarget()) != null){
							option.setSelected("selected");
						}
					}

					if(viewDetailsFeatureGroupList != null && viewDetailsFeatureGroupList.size() > 0){
						for(FeatureGroup featureGroupType : viewDetailsFeatureGroupList){
							if(featureGroupType.getExternalId().equals(df.getFeatureExternalId())){
								if(viewDetailsSelectedFeatureGroupMap != null && viewDetailsSelectedFeatureGroupMap.get(features.getExternalId()) != null){
									String selValue = viewDetailsSelectedFeatureGroupMap.get(features.getExternalId());
									if(features.getDataConstraints().getStringConstraint().getValue().equals(selValue)){
										option.setSelected("selected");
									}
								}
							}
						}
					}
					select.getOptionOrOptgroup().add(option);
				}
				else if(features.getIncluded() == null && features.isAvailable() == true){

					Option option = oFactory.createOption();

					option.setContent(features.getDataConstraints().getStringConstraint().getValue()+" - $"+format.format(features.getPrice().getBaseNonRecurringPrice())+"/$"+format.format(features.getPrice().getBaseRecurringPrice()));
					String widthValue = features.getDataConstraints().getStringConstraint().getValue()+" - $"+format.format(features.getPrice().getBaseNonRecurringPrice())+"/$"+format.format(features.getPrice().getBaseRecurringPrice());
					if(widthValue != null && widthValue.length() > 40){
						select.setStyle("max-width:300px");
					}
					String origValue = features.getExternalId()+"::"+features.getDataConstraints().getStringConstraint().getValue()+"$$"+
					features.getPrice().getBaseRecurringPrice()+"/"+features.getPrice().getBaseNonRecurringPrice();
					option.setValue(origValue);

					if(viewDetailsFeatureGroupList != null && viewDetailsFeatureGroupList.size() > 0){
						for(FeatureGroup featureGroupType : viewDetailsFeatureGroupList){
							if(featureGroupType.getExternalId().equals(df.getFeatureExternalId())){
								if(viewDetailsSelectedFeatureGroupMap != null && viewDetailsSelectedFeatureGroupMap.get(features.getExternalId()) != null){
									String selValue = viewDetailsSelectedFeatureGroupMap.get(features.getExternalId());
									if(features.getDataConstraints().getStringConstraint().getValue().equals(selValue)){
										option.setSelected("selected");
									}
								}
							}
						}
					}
					select.getOptionOrOptgroup().add(option);
				}
			}*/
			select.setName(featureGroup.getExternalId());
			defaultTheSelectedField(select, null, null);
			if(viewDetailsFeatureGroupList != null && !viewDetailsFeatureGroupList.isEmpty()){
				for(FeatureGroup viewDetailsFeatureGroup : viewDetailsFeatureGroupList){
					if(viewDetailsFeatureGroup.getExternalId().equals(select.getName())){
						FeatureValueType selectedFeatureValueType = viewDetailsFeatureGroup.getFeatureValue().get(0);
						String selectedFeatureValue = selectedFeatureValueType.getValue();
						defaultTheSelectedField(select, null, selectedFeatureValue);
					}
				}
			}
			bodySpan = oFactory.createSpan();
			bodySpan.getClazz().add("cols");
			bodySpan.setStyle("width: 200px;background: #CCC;");
			bodySpan.getContent().add(select);
			span.getContent().add(bodySpan);

		}
		else if(featureGroup.getSelectionType().getPickAll() != null){

			Span pSpan = oFactory.createSpan();
			pSpan.getClazz().add("featureGrpScroll");
			pSpan.setStyle("width: 200px; text-align: left;background: #CCC;");

			Span featureSpan = null;
			if(featureGroup.getFeature() != null){
				for(FeatureType features : featureGroup.getFeature()){
					if(features.getIncluded() != null){
						featureSpan = oFactory.createSpan();
						//featureSpan.getClazz().add("subHtml");
						featureSpan.setStyle("background-color:#ccc;");
						featureSpan.getContent().add(features.getDataConstraints().getStringConstraint().getValue()+" - "+"INCLUDED");
						pSpan.getContent().add(featureSpan);
					}
					else if(features.getIncluded() == null && features.isAvailable() == true){
						featureSpan = oFactory.createSpan();
						//featureSpan.getClazz().add("subHtml");
						featureSpan.setStyle("background-color:#ccc;");
						featureSpan.getContent().add(features.getDataConstraints().getStringConstraint().getValue()+" - "+features.getPrice().getBaseRecurringPrice());
						pSpan.getContent().add(featureSpan);
					}
				}
			}
			else{

			}
			span.getContent().add(pSpan);

			/* Column 3 */
			bodySpan = oFactory.createSpan();
			bodySpan.getClazz().add("cols FeatureGroup");
			bodySpan.setStyle("width: 150px; text-align: center;background: #CCC;");
			Input checkbox = oFactory.createInput();
			checkbox.setType(InputTypeEnum.checkbox.name());
			checkbox.setName(featureGroup.getExternalId());
			checkbox.setId(dataGroupName+"_"+featureGroup.getExternalId());
			checkbox.setValue(featureGroup.getExternalId()+"_ALL");

			bodySpan.getContent().add(checkbox);
			span.getContent().add(bodySpan);
		}
		return span;
	}

	public List<Fieldset> updateDialogueFieldSet(StringBuilder events, DialogueVO dialogueVO) throws Exception {
		List<Fieldset> fieldsetList = new ArrayList<Fieldset> ();

		Fieldset fieldSet = oFactory.createFieldset();
		fieldsetList.add(fieldSet);
		return fieldsetList;
	}	

	private static void buildSelectTypeDialogues(DataField df, boolean hasDependency, Map<String, String>preSelectedMap, 
			Span fieldset, String dataGroupName) throws Exception{

		Select select = oFactory.createSelect();
		select.setClazz(DEFAULT_CSS_CLASS);
		if(df.getValueTarget() != null && df.getValueTarget().equals("consumerFinancialInfo.cardType")){
			select.setClazz(DEFAULT_CSS_CLASS+" "+CCTYPE_CLASS);
		}
		String string = df.getExternalId(); 
		if(string.contains("/")){
			while(string.contains("/")){
				string = string.replace("/", "_");
			}
		}

		select.setId(dataGroupName+"_"+string);
		select.setName(df.getExternalId());
		if (hasDependency) {
			select.setOnchange("activate(\'" + dataGroupName+"_"+string + "\')");
		}

		/*if(df.getExternalId().equalsIgnoreCase("DisabledCreditCardType")){
			Input hiddenJSONInput = oFactory.createInput();
			hiddenJSONInput.setId(dataGroupName+"_"+string+"_ExitOnNo");
			hiddenJSONInput.setType("hidden");
			hiddenJSONInput.setValue("ExitOnNo");
			fieldset.getContent().add(hiddenJSONInput);
		}*/

		Option option = null;
		option = oFactory.createOption();
		option.setContent(" Please Select ");
		option.setValue("");
		select.getOptionOrOptgroup().add(option);

		if (df.getStringConstraint() != null) {

			StringConstraint stringConstraint = df.getStringConstraint();

			List<String> valuesList = stringConstraint.getListOfValues();

			for (String value : valuesList) {

				option = oFactory.createOption();

				select.setStyle("max-width:300px");

				option.setContent(value);

				option.setValue(value);

				if(df.getValueTarget() != null && (preSelectedMap != null && !preSelectedMap.isEmpty())){

					if(preSelectedMap.get("consumer.bestContactNumberType")!=null && preSelectedMap.get("consumer.bestContactNumberType").equalsIgnoreCase(value)){
						option.setSelected("selected");
					}
					else if(preSelectedMap.get(df.getValueTarget()) != null && preSelectedMap.get(df.getValueTarget()).length() > 0){
						if(value.equalsIgnoreCase(preSelectedMap.get(df.getValueTarget()))){
							option.setSelected("selected");	
						}
					}

					select.getOptionOrOptgroup().add(option);
				}
			}
		}
		else if (df.getIntegerConstraint() != null) {

			IntegerConstraint integerConstraint = df.getIntegerConstraint();

			if(integerConstraint.getListOfValues() != null && !integerConstraint.getListOfValues().isEmpty()){

				List<Integer> valuesList = integerConstraint.getListOfValues();

				for (Integer value : valuesList) {

					option = oFactory.createOption();

					option.setContent(String.valueOf(value));
					select.setStyle("max-width:300px");
					option.setValue(String.valueOf(value));

					if(df.getValueTarget() != null && (preSelectedMap != null && !preSelectedMap.isEmpty())){
						if(preSelectedMap.get(df.getValueTarget()) != null){
							if(String.valueOf(value).equalsIgnoreCase(preSelectedMap.get(df.getValueTarget()))){
								option.setSelected("selected");	
							}
						}
					}
					select.getOptionOrOptgroup().add(option);
				}
			}
			else if(integerConstraint.getMinValue() != null && integerConstraint.getMaxValue() != null){
				int minValue = integerConstraint.getMinValue();
				int maxValue = integerConstraint.getMaxValue();
				for(int i = minValue; i <= maxValue; i++){
					option = oFactory.createOption();
					option.setContent(String.valueOf(i));

					option.setValue(String.valueOf(i));

					if(df.getValueTarget() != null  && (preSelectedMap != null && !preSelectedMap.isEmpty())){
						if(preSelectedMap.get(df.getValueTarget()) != null){
							if(String.valueOf(i).equalsIgnoreCase(preSelectedMap.get(df.getValueTarget()))){
								option.setSelected("selected");	
							}
						}
					}
					select.getOptionOrOptgroup().add(option);
				}
			}
		}
		if(df.getValueTarget() != null && 
				(df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate2.time") || 
						df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate.time"))){
			String selectClass = select.getClazz();
			if(df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate.time")){
				if(selectClass != null && selectClass.trim().length() > 0){
					select.setClazz(selectClass+" DesiredStartTime1");
				}
				else{
					select.setClazz("DesiredStartTime1");
				}
			}
			else if(df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate2.time")){
				if(selectClass != null && selectClass.trim().length() > 0){
					select.setClazz(selectClass+" DesiredStartTime2");
				}
				else{
					select.setClazz("DesiredStartTime2");
				}
			}
		}
		fieldset.getContent().add(select);
	}

	/**
	 * Code to build text boxes
	 * @param inputType
	 * @param mainString
	 * @param df
	 * @param resultMap
	 * @param preSelectedMap
	 * @param fieldset
	 * @param dataGroupName
	 * @param requestParamMap
	 * @throws Exception
	 */
	private static void buildTextTypeDialogues(InputTypeEnum inputType, String mainString, DataField df, 
			Map<String, String> preSelectedMap, Span fieldset, String dataGroupName, Map<String, String> requestParamMap) throws Exception{

		JSONObject selectedValueJSONObject = null;

		Input input = oFactory.createInput();
		Select billingAddrSelect = null;
		boolean isSelectType = false;

		Input yearInput = null;
		Input monthInput = null;
		Input hiddenMonthInput = null;
		Input hiddenYearInput = null;
		Img img = null;
		input.setType(inputType.name());
		mainString = df.getExternalId(); 

		if(mainString.contains("/")){
			while(mainString.contains("/")){
				mainString = mainString.replace("/", "_");
			}
		}

		input.setId(dataGroupName + "_" +mainString);
		input.setName(df.getExternalId());
		input.setClazz(DEFAULT_CSS_CLASS);
		Input hiddenInput = oFactory.createInput();
		hiddenInput.setType("hidden");
		hiddenInput.setId(dataGroupName + "_" +mainString+"_JSON");
		hiddenInput.setValue(JSONBuilder.INSTANCE.createHiddenInputJSONObject(df, preSelectedMap));
		hiddenInput.setName(df.getExternalId()+"_JSONVAL");

		if(df.getValidation() != null && df.getValidation().equalsIgnoreCase("credit card date")){

			input.setName(df.getExternalId()+"_CCExpDate");

			yearInput = oFactory.createInput();
			yearInput.setId("YearDD");
			yearInput.setName("YearDD_EXPIRATIONDATE");
			yearInput.setType(InputTypeEnum.text.name());

			img = oFactory.createImg();
			img.setSrc(context_Path+"/image/red_asterisk.png");
			img.setId("YearDD_required");
			img.setAlt("<span class='requiredField'>" +
			"<font color='red'>*</font></span>");
			img.setClazz("requiredField error");
			img.setStyle("display:none");

			hiddenYearInput = oFactory.createInput();
			hiddenYearInput.setType("hidden");
			hiddenYearInput.setId("YearDD_JSON");
			hiddenYearInput.setName("YearDD_EXPIRATIONDATE_JSONVAL");
			JSONObject hiddenYearJSONObject = createMonthAndYearHiddenJSON(df, preSelectedMap, "YearDD");
			hiddenYearInput.setValue(hiddenYearJSONObject.toString());
			fieldset.getContent().add(hiddenYearInput);

			monthInput = oFactory.createInput();
			monthInput.setId("MonthDD");
			monthInput.setName("MonthDD_EXPIRATIONDATE");
			monthInput.setType(InputTypeEnum.text.name());

			hiddenMonthInput = oFactory.createInput();
			hiddenMonthInput.setType("hidden");
			hiddenMonthInput.setId("MonthDD_JSON");
			hiddenMonthInput.setName("MonthDD_EXPIRATIONDATE_JSONVAL");
			JSONObject hiddenMonthJSONObject = createMonthAndYearHiddenJSON(df, preSelectedMap, "MonthDD");
			hiddenMonthInput.setValue(hiddenMonthJSONObject.toString());

			input.setType("hidden");
			input.setId(dataGroupName + "_" +mainString+"_EXPIRATIONDATE");
		}
		String jsonValue = null;
		if(requestParamMap != null && requestParamMap.get(df.getExternalId()+"_JSONVAL") != null){
			jsonValue = requestParamMap.get(df.getExternalId()+"_JSONVAL");
			try {
				selectedValueJSONObject = new JSONObject(jsonValue);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(requestParamMap != null && requestParamMap.get(df.getExternalId()+"_CCExpDate") != null && (requestParamMap.get("YearDD_EXPIRATIONDATE_JSONVAL") != null 
				|| requestParamMap.get("MonthDD_EXPIRATIONDATE_JSONVAL") != null )){
			String expMonthJSONVal = requestParamMap.get("MonthDD_EXPIRATIONDATE_JSONVAL");
			String expYearJSONVal = requestParamMap.get("YearDD_EXPIRATIONDATE_JSONVAL");
			try{
				JSONObject selectedMonthValueJSONObject = new JSONObject(expMonthJSONVal);
				JSONObject selectedYearValueJSONObject = new JSONObject(expYearJSONVal);

				if(selectedMonthValueJSONObject != null && selectedMonthValueJSONObject.getString("maskedValue") != null && selectedMonthValueJSONObject.getString("maskedValue").trim().length() > 0){
					String maskedVal = (String) selectedMonthValueJSONObject.get("maskedValue");
					if(maskedVal != null && maskedVal.trim().length() > 0){
						monthInput.setValue(maskedVal);
						hiddenMonthInput.setValue(selectedMonthValueJSONObject.toString());
					}
				}
				if(selectedYearValueJSONObject != null && selectedYearValueJSONObject.getString("maskedValue") != null && selectedYearValueJSONObject.getString("maskedValue").trim().length() > 0){
					String maskedVal = (String) selectedYearValueJSONObject.get("maskedValue");
					if(maskedVal != null && maskedVal.trim().length() > 0){
						yearInput.setValue(maskedVal);
						hiddenMonthInput.setValue(selectedYearValueJSONObject.toString());
					}
				}
			}
			catch(JSONException exp){
				exp.printStackTrace();
			}
		}
		try {
			if(selectedValueJSONObject != null && selectedValueJSONObject.getString("maskedValue") != null && selectedValueJSONObject.getString("maskedValue").trim().length() > 0){
				String maskedVal = (String) selectedValueJSONObject.get("maskedValue");
				if(maskedVal != null && maskedVal.trim().length() > 0){
					input.setValue(maskedVal);
					hiddenInput.setValue(selectedValueJSONObject.toString());
				}
			}
			if(df.getValueTarget() != null  && (preSelectedMap != null && !preSelectedMap.isEmpty())){
				if(preSelectedMap.get(df.getValueTarget()) != null){
					logger.info("VALUE TARGET ::::::: "+df.getValueTarget());
					logger.info("Is Expiration Date :::::::: "+(df.getValueTarget().equals("consumerFinancialInfo.creditCard.expirationDate")));
					if(df.getValueTarget().equalsIgnoreCase("consumerFinancialInfo.creditCard.expirationDate")){
						String expDate = preSelectedMap.get(df.getValueTarget());
						String[] expFields = expDate.split("-");
						String dayString = null; 
						if(expFields[1].length() > 2){
							dayString = expFields[1].substring(0,2); 
						}
						else{
							dayString = expFields[1];
						}
						monthInput.setValue(dayString);
						yearInput.setValue(expFields[0]);
					}
					else {
						input.setValue(preSelectedMap.get(df.getValueTarget()));
					}	
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if(df.getValueTarget() != null && (df.getValueTarget().equals("consumer.billingAddress.dwelling.line2") || 
				df.getValueTarget().equals("consumer.billingAddress.dwelling.line2info"))){
			String[] addressLine2Arr = null;
			if(preSelectedMap != null && preSelectedMap.get("consumer.billingAddress.dwelling.line2") != null && preSelectedMap.get("consumer.billingAddress.dwelling.line2").trim().length() > 0){
				String addressLine2Info = preSelectedMap.get("consumer.billingAddress.dwelling.line2");
				addressLine2Arr = addressLine2Info.split(" ");
			}

			if(df.getValueTarget().equals("consumer.billingAddress.dwelling.line2")){
				isSelectType = true;
				billingAddrSelect = oFactory.createSelect();
				billingAddrSelect.setId(dataGroupName + "_" +mainString);
				billingAddrSelect.setName(df.getExternalId());
				billingAddrSelect.setClazz(DEFAULT_CSS_CLASS+" billingInfoSelect dwellingLineInfo");

				Option baseOpt = oFactory.createOption();
				baseOpt.setContent("Please Select");
				baseOpt.setStyle("color: black;");
				baseOpt.setValue("");
				billingAddrSelect.getOptionOrOptgroup().add(baseOpt);

				for(UnitTypeEnum unitTypeEnum : HtmlFactory.UnitTypeEnum.values()){

					Option unitOpt = oFactory.createOption();
					unitOpt.setContent(unitTypeEnum.unitTypeValue);
					unitOpt.setStyle("color: black;");
					unitOpt.setValue(unitTypeEnum.unitTypeValue);

					if(addressLine2Arr!=null && addressLine2Arr.length >= 0 && addressLine2Arr[0].equalsIgnoreCase(unitTypeEnum.toString())){
						unitOpt.setSelected("Selected");
					}
					billingAddrSelect.getOptionOrOptgroup().add(unitOpt);
					img = oFactory.createImg();
					img.setSrc(context_Path+"/image/red_asterisk.png");
					img.setId(dataGroupName + "_" +mainString+"_optionalSelect");
					img.setAlt("<span class='requiredField'>" +
					"<font color='red'>*</font></span>");
					img.setClazz("requiredField error optional");
					img.setStyle("display:none");
				}
			}else if(df.getValueTarget().equals("consumer.billingAddress.dwelling.line2info")){
				if(addressLine2Arr!=null && addressLine2Arr.length>1){
					input.setValue(addressLine2Arr[1]);
				}
				img = oFactory.createImg();
				img.setSrc(context_Path+"/image/red_asterisk.png");
				img.setId(dataGroupName + "_" +mainString+"_optionalSelect");
				img.setAlt("<span class='requiredField'>" +
				"<font color='red'>*</font></span>");
				img.setClazz("requiredField error optional");
				img.setStyle("display:none");
			}

			if(!isSelectType){
				String inputClass = input.getClazz();
				if(inputClass != null && inputClass.trim().length() > 0){
					inputClass += " dwellingLineInfo";
				}
				else{
					inputClass = "dwellingLineInfo";
				}
				input.setClazz(inputClass);
			}
		}
		if(df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate.date") || 
				df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate2.date")){
			String selectClass = input.getClazz();
			if(df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate.date")){
				if(selectClass != null && selectClass.trim().length() > 0){
					input.setClazz(selectClass+" DesiredStartDate1");
				}
				else{
					input.setClazz("DesiredStartDate1");
				}
			}
			else if(df.getValueTarget().equals("lineItem.schedulingInfo.desiredStartDate2.date")){
				if(selectClass != null && selectClass.trim().length() > 0){
					input.setClazz(selectClass+" DesiredStartDate2");
				}
				else{
					input.setClazz("DesiredStartDate2");
				}
			}
			//force agent to select from calendar widget
			input.setReadonly("readonly");
		}
		fieldset.getContent().add(hiddenMonthInput);
		fieldset.getContent().add(monthInput);
		fieldset.getContent().add(yearInput);
		fieldset.getContent().add(img);
		fieldset.getContent().add(hiddenInput);
		if(!isSelectType){
			fieldset.getContent().add(input);
		}else{
			fieldset.getContent().add(billingAddrSelect);
		}
	}


	private enum UnitTypeEnum{

		Apartment("Apartment"), Building("Building"), Department("Department"),
		Floor("Floor"), Hangar("Hangar"), Lot("Lot"), Office("Office"), 
		Space("Space"),Suite("Suite"),Trailer("Trailer"),Unit("Unit"),
		Duplex("Duplex"),Mobile_Home("Mobile Home");

		String unitTypeValue;

		private UnitTypeEnum(String value) {
			this.unitTypeValue = value;
		}

	}


	/**
	 * Creates hidden JSONObjects for month and year fields based on the previous selected Data
	 * @param df
	 * @param preSelectedMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static JSONObject createMonthAndYearHiddenJSON(DataField df, Map<String, String> preSelectedMap, String id) throws Exception{

		JSONObject jOb = new JSONObject();
		try {
			jOb.put("toBeMasked", "true");
			jOb.put("maskedValue", "");
			jOb.put("valueTarget", df.getValueTarget());
			jOb.put("enteredValue", "");
			String monthYearVal = "";
			if(preSelectedMap.get(df.getValueTarget()) != null){
				monthYearVal = preSelectedMap.get(df.getValueTarget());
			}
			if(monthYearVal != null && monthYearVal.trim().length() > 0 && monthYearVal.indexOf("-") > 0){
				String[] monthYearArr = monthYearVal.split("-");
				if(id.equals("MonthDD")){
					jOb.put("valueTargetVal", monthYearArr[1]);
					jOb.put("enteredValue", monthYearArr[1]);
				}
				else if(id.equals("YearDD")){
					jOb.put("valueTargetVal", monthYearArr[0]);
					jOb.put("enteredValue", monthYearArr[0]);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jOb;
	}

	/**
	 * Code for Promotion Display Objects
	 * @param promotions
	 * @param productDetailType
	 * @param productDataSource
	 * @param promoJSONObject
	 * @param request
	 * @param priceDisplayVOMap 
	 * @return
	 * @throws Exception
	 */
	public Fieldset getPromotionsFieldSet(List<ProductPromotionType> promotions, String productDetailType, ProviderSourceBaseType productDataSource, 
			JSONObject promoJSONObject, HttpServletRequest request, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception {

		Fieldset fieldSet = oFactory.createFieldset();
		if(!promotions.isEmpty()){
			Table table = oFactory.createTable();
			table.setClazz("tblPromotions");
			table.setId("tblPromotions");
			table.setStyle("background-color:#E0F5C3;");
			Thead thead = oFactory.createThead();
			Tr tr = oFactory.createTr();

			Th th = oFactory.createTh();
			// header 1      
			th = oFactory.createTh();
			th.setStyle("width: 155px");
			th.getContent().add("Promotions");
			tr.getTh().add(th);
			// header 2     
			th = oFactory.createTh();
			th.setStyle("width: 155px");
			th.getContent().add("Select");
			tr.getTh().add(th);
			thead.getTr().add(tr);
			table.setThead(thead);

			Tbody tbody = oFactory.createTbody();

			for (ProductPromotionType promotion : promotions) {
				tr = oFactory.createTr();
				addTableDataPromotionsField(promotion,tr, productDetailType, productDataSource, promoJSONObject, request, priceDisplayVOMap);
				tbody.getTr().add(tr);
			}
			table.setTbody(tbody);
			fieldSet.getContent().add(table);
		}
		return fieldSet;
	}

	private void addTableDataPromotionsField(ProductPromotionType promotion, Tr tr, 
			String productDetailType, ProviderSourceBaseType productDataSource, 
			JSONObject promoJSONObject, HttpServletRequest request, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception {
		Td td = oFactory.createTd();

		P p = oFactory.createP();
		if(promotion.getDescription() != null){
			p = oFactory.createP();
			p.getClazz().add("promo_desc");
			p.getContent().add(promotion.getDescription());
			td.getContent().add(p);
		}
		else{
			p = oFactory.createP();
			p.getClazz().add("promo_desc");
			p.getContent().add(promotion.getShortDescription());
			td.getContent().add(p);			
		}

		p = oFactory.createP();
		p.getClazz().add("promo_desc");
		p.getContent().add(promotion.getQualification());
		td.getContent().add(p);
		p = oFactory.createP();
		p.getContent().add("Terms and Conditions: ");
		p.setStyle("text-decoration:underline;");
		td.getContent().add(p);

		p = oFactory.createP();
		p.getClazz().add("promo_desc");
		p.getContent().add(promotion.getConditions());
		td.getContent().add(p);
		tr.getTd().add(td);

		td = oFactory.createTd();

		td.setStyle("width: 150px;text-align: center;");

		Input input = oFactory.createInput();
		input.setType(InputTypeEnum.checkbox.name());
		input.setName(promotion.getExternalId());
		input.setId(promotion.getExternalId());

		if(priceDisplayVOMap.get(promotion.getExternalId()) != null){
			for(PriceDisplayVO priceDisplayVO : priceDisplayVOMap.get(promotion.getExternalId())){
				input.setValue(PriceDisplayUtil.INSTANCE.convertToJSON(priceDisplayVO).toString());	
			}
		}
		String selectedFeatureJSON = request.getParameter("selectedFeaturesJSONHiddenValue");
		JSONArray jArray = null;
		logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
		if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
			if(selectedFeatureJSON.indexOf("selectedFeatureArray") >= 0){
				JSONObject selectedFeatureJSONObject = new JSONObject(selectedFeatureJSON);
				selectedFeatureJSON = selectedFeatureJSONObject.getString("selectedFeatureArray");
			}
			jArray = new JSONArray(selectedFeatureJSON);
			if(jArray != null && jArray.length() > 0){
				for(int i = 0; i < jArray.length(); i++){
					JSONObject individualJSONObject = (JSONObject)jArray.get(i);
					if(promotion.getExternalId().equals(individualJSONObject.get("featureExternalID"))){
						input.setChecked("checked");
					}
				}
			}
		}
		input.setClazz("promotionClass");
		boolean defaultPrevSelectedValues = JSONBuilder.INSTANCE.defaultPrevouslySelValuesOnBack(promoJSONObject.toString(), promotion.getExternalId());
		if(defaultPrevSelectedValues){
			input.setChecked("checked");
		}

		Input hiddenPromoInput = oFactory.createInput();
		hiddenPromoInput.setId(promotion.getExternalId()+"_hiddenPromo");
		hiddenPromoInput.setName(promotion.getExternalId()+"_hiddenPromo");
		hiddenPromoInput.setValue(JSONBuilder.INSTANCE.createPromotionJSON(promotion, productDetailType, productDataSource));
		hiddenPromoInput.setType("hidden");

		td.getContent().add(input);
		td.getContent().add(hiddenPromoInput);
		tr.getTd().add(td);
	}


	/**
	 * returns a hidden input which is has the value that needs to be added to monthlyPrice and installation price 
	 * @param feature
	 * @return input
	 */
	private Input createHiddenInputCheckinBox(FeatureType feature){

		if(feature.getDataConstraints().getBooleanConstraint() !=  null){
			Input iChkBoxHidden = oFactory.createInput();
			iChkBoxHidden.setType("hidden");
			iChkBoxHidden.setId(feature.getExternalId()+"_Feature_price");
			logger.info("Create Hidden INPUT :::: "+feature.getExternalId());
			if(feature.getIncluded() != null){
				iChkBoxHidden.setValue("0.00");
			}
			else if(feature.isAvailable() && feature.getIncluded() == null){
				String rec_nonRec = "";
				logger.info(feature.getPrice().getBaseNonRecurringPrice());
				if(feature.getPrice().getBaseNonRecurringPrice() != null && feature.getPrice().getBaseNonRecurringPrice().doubleValue() != 0.0){
					rec_nonRec = String.valueOf(feature.getPrice().getBaseRecurringPrice().doubleValue())+"/"+String.valueOf(feature.getPrice().getBaseNonRecurringPrice().doubleValue());
				}
				else{
					rec_nonRec = String.valueOf(feature.getPrice().getBaseRecurringPrice().doubleValue());
				}
				logger.info("rec_nonRec ::::: "+rec_nonRec);
				iChkBoxHidden.setValue(rec_nonRec);
			}
			return iChkBoxHidden;
		}
		return null;
	}

	/**
	 * creates the display for features
	 * @param features
	 * @param tr
	 * @param previousCKOSelFeatureList
	 * @param prevSelectedFeatures
	 * @param request
	 * @param priceDisplayVOMap 
	 * @throws Exception
	 */
	private void addTableDataFeaturesField(FeatureType features, Tr tr, List<String> previousCKOSelFeatureList, 
			String prevSelectedFeatures, HttpServletRequest request, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception {

		/*String checkboxSelect = "";
		String selectBoxSelected = "";
		String from = request.getParameter("from");

		if(from != null && from.equalsIgnoreCase("oqDemoContent")){
			checkboxSelect = (String) request.getParameter("featureCheckboxFields");
			selectBoxSelected = (String) request.getParameter("featureSelectBoxFields");
		}*/

		DecimalFormat format = new DecimalFormat("#0.00");
		logger.info("Creating Feature type values for table");

		/*
		 * creating hidden input objects for storing feature price. used for checkbox 
		 * if the checkbox is checked, if the feature contains recurring price, monthly
		 * price is changed, if the feature contains nonrecurring price, installationprice is changed 
		 */
		Input iChkBoxHidden = createHiddenInputCheckinBox(features);

		/*
		 * creating feature type display
		 */
		Td td = oFactory.createTd();
		if(!Utils.isBlank(features.getDescription())){
			td.getContent().add(features.getDescription());
		}else{
			td.getContent().add(features.getType());
		}

		tr.getTd().add(td);

		/*
		 * creating feature price display
		 */
		td = oFactory.createTd();
		logger.info("Assigining price options");

		/*
		 * if feature contains price, show price related display
		 */
		if(features.getPrice() != null){
			if(features.getPrice().getBaseRecurringPrice() != null){
				if(features.getIncluded() != null){
					td.getContent().add("INCLUDED");
					//iChkBoxHidden.setValue("0.00");
					td.getContent().add(iChkBoxHidden);
				}
				else if(!features.isAvailable()){
					td.getContent().add("UNAVAILABLE");
				}
				else if(features.isAvailable() && features.getIncluded() == null){
					td.getContent().add(" "+"$"+format.format(features.getPrice().getBaseRecurringPrice().doubleValue()));
					//iChkBoxHidden.setValue(String.valueOf(features.getPrice().getBaseRecurringPrice().doubleValue()));
					td.getContent().add(iChkBoxHidden);
				}
			}
		}

		/*
		 * if feature contains price tiers, show pricetier related display
		 */
		else if(features.getPriceTierList() != null){
			int maxValue = 0;
			if(features.getIncluded() != null){
				td.getContent().add("INCLUDED");
				//iChkBoxHidden.setValue("0.00");
				td.getContent().add(iChkBoxHidden);
			}
			else if(!features.isAvailable()){
				td.getContent().add("UNAVAILABLE");
			}
			else if(features.isAvailable() && features.getIncluded() == null){
				if(features.getDataConstraints() != null && features.getDataConstraints().getIntegerConstraint() != null && features.getDataConstraints().getIntegerConstraint().getMaxValue() != null){
					maxValue = features.getDataConstraints().getIntegerConstraint().getMaxValue().intValue();	
				}
				else if(features.getDataConstraints() != null && features.getDataConstraints().getIntegerConstraint() != null && features.getDataConstraints().getIntegerConstraint().getListOfValues() != null){
					maxValue = returnMaxValueFromIntegerList(features.getDataConstraints().getIntegerConstraint().getListOfValues().getValue());
				}
				Table basePriceTierTable = buildPriceTierDisplay(features.getPriceTierList(), maxValue);
				basePriceTierTable.setStyle("border:none");
				td.getContent().add(basePriceTierTable);
			}
		}
		td.setClazz("styled");
		tr.getTd().add(td);

		td = oFactory.createTd();

		if(features.getIncluded() != null){
			td = oFactory.createTd();
			
			if(features.getDataConstraints().getStringConstraint() != null && features.getDataConstraints().getStringConstraint().getValue()!=null){
				String strConstraint = features.getDataConstraints().getStringConstraint().getValue();
				td.getContent().add(strConstraint);
			}
			else if(features.getDataConstraints().getIntegerConstraint()!= null && features.getDataConstraints().getIntegerConstraint().getValue()!=null ){
				BigInteger integerConstraint = features.getDataConstraints().getIntegerConstraint().getValue();
				td.getContent().add(String.valueOf(integerConstraint));
			}
			else {
				td.getContent().add("--");
			}
			tr.getTd().add(td);
		}
		else if(features.getIncluded() == null && features.isAvailable()){
			String selectedFeatureJSON = request.getParameter("selectedFeaturesJSONHiddenValue");
			JSONArray jArray = null;
			logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
			if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
				if(selectedFeatureJSON.indexOf("selectedFeatureArray") >= 0){
					JSONObject selectedFeatureJSONObject = new JSONObject(selectedFeatureJSON);
					selectedFeatureJSON = selectedFeatureJSONObject.getString("selectedFeatureArray");
				}

				jArray = new JSONArray(selectedFeatureJSON);
			}
			/*
			 * for boolean constraint, build a checkbox
			 */
			if(features.getDataConstraints().getBooleanConstraint()!= null){
				Input checkbox = oFactory.createInput();
				checkbox.setName(features.getExternalId());
				checkbox.setType(InputTypeEnum.checkbox.name());
				checkbox.setId(String.valueOf(features.getExternalId()));
				if(priceDisplayVOMap != null && !priceDisplayVOMap.isEmpty()){
					List<PriceDisplayVO> priceDisplayVOList = priceDisplayVOMap.get(features.getExternalId());

					for(PriceDisplayVO priceDisplayVO : priceDisplayVOList){
						JSONObject jsonValue = PriceDisplayUtil.INSTANCE.convertToJSON(priceDisplayVO);
						checkbox.setValue(jsonValue.toString());
					}
				}

				if(jArray != null && jArray.length() > 0){
					for(int i = 0; i < jArray.length(); i++){
						JSONObject individualJSONObject = (JSONObject)jArray.get(i);
						if(features.getExternalId().equals(individualJSONObject.get("featureExternalID"))){
							checkbox.setChecked("checked");
						}
					}
				}

				/*
				 * logic to default if the feature is selected during previous CKO 
				 * or we enter into this CKO by pressing back button in the customer 
				 * qualification page
				 */
				/*if(checkboxSelect != null && checkboxSelect.length() > 0){
					String[] selectedCheck = null; 
					if(checkboxSelect.indexOf(",") > 0){
						selectedCheck = checkboxSelect.split(",");
					}
					for(String str : selectedCheck){
						if(str.equals(features.getExternalId())){
							checkbox.setChecked("checked");
						}
					}
				}
				else*/ 
				if(prevSelectedFeatures != null && prevSelectedFeatures.trim().length() > 0){
					String[] preSelectedValuesArray = prevSelectedFeatures.split(",");
					for(int i = 0; i < preSelectedValuesArray.length; i++){
						if(features.getExternalId().equals(preSelectedValuesArray[i])){
							checkbox.setChecked("checked");
						}
					}
				}
				else if(previousCKOSelFeatureList != null && ! previousCKOSelFeatureList.isEmpty()){
					for(String viewDetailsFeatures : previousCKOSelFeatureList){
						String[] viewDetailsFeaturesArray = viewDetailsFeatures.split("::");
						if(viewDetailsFeaturesArray[0].equals(features.getExternalId())){
							checkbox.setChecked("checked");
						}
					}
				}
				String chkBoxClass = checkbox.getStyle();
				if(chkBoxClass != null && chkBoxClass.trim().length() > 0){
					checkbox.setClazz(chkBoxClass + " FeatureCheck");
				}
				else{
					checkbox.setClazz("FeatureCheck");
				}
				td.getContent().add(checkbox);

				tr.getTd().add(td);
			}
			else if (features.getDataConstraints().getIntegerConstraint() != null ) {

				if(features.getIncluded() == null && features.isAvailable() == true){
					if(features.getDataConstraints().getIntegerConstraint().getMinValue() != null || 
							features.getDataConstraints().getIntegerConstraint().getMaxValue() != null){

						Select select = PriceDisplayUtil.INSTANCE.populateOptionsListForSelect(features, priceDisplayVOMap, null);
						List<Object> options = select.getOptionOrOptgroup();
						for(Object opts : options){
							Option opt = (Option)opts;
							String value = opt.getValue();
							if(jArray != null && jArray.length() > 0){
								for(int i = 0; i < jArray.length(); i++){
									JSONObject individualJSONObject = (JSONObject)jArray.get(i);
									if(features.getExternalId().equals(individualJSONObject.get("featureExternalID"))){
										if(!Utils.isBlank(value) && !value.equals("Please Select")){
											JSONObject jsonObj = new JSONObject(value);
											if(jsonObj.get("quantity").equals(individualJSONObject.get("quantity"))){
												opt.setSelected("selected");
											}
										}
									}
								}
							}
							if(previousCKOSelFeatureList != null && previousCKOSelFeatureList.size() > 0){
								for(String prevCKOSelFeatures : previousCKOSelFeatureList){
									String[] viewDetailsFeaturesArray = prevCKOSelFeatures.split("::");
									if(!Utils.isBlank(value) && !value.equals("Please Select")){
										JSONObject jsonObj = new JSONObject(value);
										if(jsonObj.get("quantity").equals(viewDetailsFeaturesArray[0])){
											opt.setSelected("selected");
										}
									}
								}
							}
						}
						String selectBox = select.getClazz();
						if(selectBox != null && selectBox.trim().length() > 0){
							select.setClazz(selectBox + " FeatureSelect");
						}
						else{
							select.setClazz(" FeatureSelect");
						}
						td.getContent().add(select);
						tr.getTd().add(td);
					}else if (features.getDataConstraints().getIntegerConstraint().getValue() != null) {
						BigInteger intContraint = features.getDataConstraints().getIntegerConstraint().getValue();
						td = oFactory.createTd();
						td.getContent().add(String.valueOf(intContraint));
						tr.getTd().add(td);
					}
					else {
						td = oFactory.createTd();
						td.getContent().add("NA");
						tr.getTd().add(td);
					}
				}
			}
			else if (features.getDataConstraints().getStringConstraint() != null ) {
				if(features.getIncluded() == null && features.isAvailable() == true){
					String strConstraint = features.getDataConstraints().getStringConstraint().getValue();
					td = oFactory.createTd();
					td.getContent().add(String.valueOf(strConstraint));
					tr.getTd().add(td);
				}
			}
		}
		else if(!features.isAvailable()){
			td = oFactory.createTd();
			td.getContent().add("NA");
			tr.getTd().add(td);
		}
	}

	/**
	 * build feature group display 
	 * FeatureGroup selection is of two types
	 * 	1. pick one type feature group where the customer is given a list of features and asked to select one of the feature
	 * 	2. pick all type, if the customer selects this type of feature, he gets all the features that are part of that particular feature group
	 * 
	 * @param featureGroup
	 * @param tr
	 * @param viewDetailsSelectedFeatureGroups
	 * @param prevSelectedFeatures
	 * @param request 
	 * @param priceDisplayVOMap 
	 * @throws Exception
	 */
	private void addTableDataFeatureGroupField(FeatureGroupType featureGroup, Tr tr, List<FeatureGroup> viewDetailsSelectedFeatureGroups, 
			String prevSelectedFeatures, HttpServletRequest request, Map<String, List<PriceDisplayVO>> priceDisplayVOMap) throws Exception {

		Td td = null;

		/*String featureGroupSelected = "";
		String from = request.getParameter("from");

		if(from != null && from.equalsIgnoreCase("oqDemoContent")){
			featureGroupSelected = (String) request.getParameter("featureGroupSelectBoxFields");
		}*/

		/* 
		 * Pick one type feature group is a select box type with options as the related features.
		 * The customer may select one of the feature from this list of features
		 */
		if(featureGroup.getSelectionType().getPickOne() != null){

			td = oFactory.createTd();
			td.getContent().add(featureGroup.getType());
			tr.getTd().add(td);

			/*
			 * building the display for pick one type feature group
			 */
			td = oFactory.createTd();
			Select select = PriceDisplayUtil.INSTANCE.populateOptionsListForSelect(null, priceDisplayVOMap, featureGroup);
			select.setClazz(DEFAULT_CSS_CLASS +" FeatureGroup");

			String selectedFeatureJSON = request.getParameter("selectedFeaturesJSONHiddenValue");
			JSONArray jArray = null;
			logger.info("Selected Features String ::::::::: "+selectedFeatureJSON);
			defaultTheSelectedField(select, null, null);
			if(selectedFeatureJSON != null && selectedFeatureJSON.trim().length() > 0){
				if(selectedFeatureJSON.indexOf("selectedFeatureArray") >= 0){
					JSONObject selectedFeatureJSONObject = new JSONObject(selectedFeatureJSON);
					selectedFeatureJSON = selectedFeatureJSONObject.getString("selectedFeatureArray");
				}
				jArray = new JSONArray(selectedFeatureJSON);
				if(jArray != null && jArray.length() > 0){
					for(int i = 0; i < jArray.length(); i++){
						JSONObject individualJSONObject = (JSONObject)jArray.get(i);
						if(individualJSONObject.has("featureGroupExternalID") && 
								featureGroup.getExternalId().equals(individualJSONObject.get("featureGroupExternalID"))){
							defaultTheSelectedField(select, individualJSONObject, null);
						}
					}
				}
			}
			if(viewDetailsSelectedFeatureGroups != null && !viewDetailsSelectedFeatureGroups.isEmpty()){
				for(FeatureGroup viewDetailsFeatureGroup : viewDetailsSelectedFeatureGroups){
					if(viewDetailsFeatureGroup.getExternalId().equals(select.getName())){
						FeatureValueType selectedFeatureValueType = viewDetailsFeatureGroup.getFeatureValue().get(0);
						String selectedFeatureValue = selectedFeatureValueType.getValue();
						defaultTheSelectedField(select, null, selectedFeatureValue);
					}
				}
			}
			td = oFactory.createTd();
			td.getContent().add(select);
			tr.getTd().add(td);
		}

		/*
		 * Building the pick all feature group display,
		 * we will display a list of options and a checkbox next to it
		 * if the customer opts this check box, he gets all the features that are present in that product
		 */
		else if(featureGroup.getSelectionType().getPickAll() != null){

			td = oFactory.createTd();
			td.getContent().add(featureGroup.getType());
			tr.getTd().add(td);

			/*
			 * displaying the list of options that are present in the feature group
			 */
			Td pSpan = oFactory.createTd();
			if(featureGroup.getFeature() != null){
				for(FeatureType features : featureGroup.getFeature()){
					if(features.getIncluded() != null){
						pSpan = oFactory.createTd();
						pSpan.getContent().add(features.getDataConstraints().getStringConstraint().getValue()+" - "+"INCLUDED");
						tr.getTd().add(pSpan);
					}
					else if(features.getIncluded() == null && features.isAvailable() == true){
						pSpan = oFactory.createTd();
						pSpan.getContent().add(features.getDataConstraints().getStringConstraint().getValue()+" - "+features.getPrice().getBaseRecurringPrice());
						tr.getTd().add(pSpan);
					}
				}
			}

			/*
			 * Building the pick all type of featuregroup
			 */ 
			td = oFactory.createTd();
			Input checkbox = oFactory.createInput();
			checkbox.setType(InputTypeEnum.checkbox.name());
			checkbox.setName(featureGroup.getExternalId()+"_FeatureGroup");
			checkbox.setId(featureGroup.getExternalId());
			checkbox.setValue(featureGroup.getExternalId()+"_ALL");

			td.getContent().add(checkbox);
			tr.getTd().add(td);
		}
	}

	@SuppressWarnings("unused")
	private static Option isFeatureGroupSelected(List<FeatureGroup> viewDetailsSelectedFeatureGroups, FeatureGroupType featureGroup, Option option, 
			FeatureType features, String featureGroupSelected){
		if(featureGroupSelected != null && featureGroupSelected.trim().length() > 0){
			if(featureGroupSelected != null && featureGroupSelected.trim().length() > 0 && featureGroupSelected.indexOf(",") > 0){
				String[] featureGroupSelArray = featureGroupSelected.split(",");
				for(String featureGroupArr : featureGroupSelArray){
					if(featureGroupArr.indexOf("/") > 0){
						String[] featGroupArray = featureGroupArr.split("/");
						if(featureGroup.getExternalId().equalsIgnoreCase(featGroupArray[0])){
							if(features.getExternalId().equals(featGroupArray[1])){
								option.setSelected("selected");
							}
							else if(features.getIncluded() != null){
								option.setSelected("selected");	
							}
						}
					}              
				}
			}

		}
		else{
			for(FeatureGroup fType : viewDetailsSelectedFeatureGroups){
				if(fType.getExternalId().equals(featureGroup.getExternalId()) && fType.getFeatureValue() != null){
					for(FeatureValueType fValType : fType.getFeatureValue()){
						if(fValType.getExternalId().equals(features.getExternalId())){
							option.setSelected("selected");
						}
					}
				}
			}
		}

		return option;
	}

	private Table buildPriceTierDisplay(PriceTierListType priceTierList, int maxValue) throws Exception {
		DecimalFormat format = new DecimalFormat("#0.00");
		String recNonRecPriceDispaly = "";
		String keyStr = "";
		List<Integer> newArrayList = new ArrayList<Integer>();
		Map<Integer, String> rangeAndPriceValuesMap = new TreeMap<Integer, String>();
		Table table = oFactory.createTable();
		Tr tr = null;
		for(PriceTierType priceTiers : priceTierList.getPriceTier()){
			if(priceTiers.getPrice().getBaseNonRecurringPrice() != 0.0){
				recNonRecPriceDispaly = format.format(priceTiers.getPrice().getBaseNonRecurringPrice())+"/"+format.format(priceTiers.getPrice().getBaseRecurringPrice());
			}
			else{
				recNonRecPriceDispaly = format.format(priceTiers.getPrice().getBaseRecurringPrice());
			}
			rangeAndPriceValuesMap.put(priceTiers.getRangeStart(), recNonRecPriceDispaly);
		}
		Set<Integer> keySet = rangeAndPriceValuesMap.keySet();

		newArrayList.addAll(keySet);
		for(int i=0;i<newArrayList.size();i++){
			if(i!=newArrayList.size() - 1){
				if(newArrayList.get(i)==(newArrayList.get(i+1)-1)) {
					keyStr = newArrayList.get(i) + "";
				} else {
					keyStr = newArrayList.get(i)+"-"+(newArrayList.get(i+1)-1);
				}
			} else {
				keyStr = newArrayList.get(i)+"-"+maxValue;
			}
			tr = oFactory.createTr();
			Td td = oFactory.createTd();
			td.setStyle("border:0px; line-height:5px;");
			td.getContent().add(keyStr+" :  "+rangeAndPriceValuesMap.get(newArrayList.get(i)));
			tr.getTd().add(td);
			table.getTr().add(tr);
		}
		return table;
	}

	private int returnMaxValueFromIntegerList(List<BigInteger> value) throws Exception {
		BigInteger maxValue = Collections.max(value);
		return maxValue.intValue();
	}

	@SuppressWarnings("unused")
	private static Select buildPriceDropDown(FeatureType features, boolean fromDialogues, DataField df, String dataGroupName, 
			List<String> viewDetailsFeaturesList, Map<String, String> preSelectedMap, String selectBoxSelected){
		DecimalFormat format = new DecimalFormat("#0.00");
		BigInteger minValue = features.getDataConstraints().getIntegerConstraint().getMinValue();
		BigInteger maxValue = features.getDataConstraints().getIntegerConstraint().getMaxValue();

		Select select = oFactory.createSelect();
		if(fromDialogues){
			select.setClazz(DEFAULT_CSS_CLASS+" Feature"+" FeatureSelect");
		}
		else{
			select.setClazz(DEFAULT_CSS_CLASS+" FeatureSelect");
		}
		if(df != null && !Utils.isBlank(dataGroupName)){
			select.setId(dataGroupName+"_"+df.getExternalId());
		}
		else{
			select.setId(features.getExternalId());
		}

		Option baseOpt = null;
		baseOpt = oFactory.createOption();
		baseOpt.setStyle("text-align: center;");
		baseOpt.setContent("Please Select");
		baseOpt.setValue("");
		select.getOptionOrOptgroup().add(baseOpt);

		Map<Integer, Double> priceTireMap = new TreeMap<Integer, Double>();
		Map<Integer, Double> baseNonPriceTireMap = new TreeMap<Integer, Double>();
		Map<Integer, String> priceTireMapDisplay = new TreeMap<Integer, String>();
		Map<Integer, Double> priceFinalTireMap = new TreeMap<Integer, Double>();
		Map<Integer, Double> baseNonPriceFinalTireMap = new TreeMap<Integer, Double>();

		Map<String, String> prevSelectedFeatureMap = new HashMap<String, String>();
		if(viewDetailsFeaturesList != null && viewDetailsFeaturesList.size() > 0){
			for(String previouslySelectedFeature : viewDetailsFeaturesList){
				String[] extIdValueArray = previouslySelectedFeature.split("::");
				prevSelectedFeatureMap.put(extIdValueArray[0], extIdValueArray[1]);
			}
		}

		if(features.getPriceTierList() != null){
			for(PriceTierType priceTire : features.getPriceTierList().getPriceTier()){
				priceTireMap.put(priceTire.getRangeStart(), priceTire.getPrice().getBaseRecurringPrice());
				baseNonPriceTireMap.put(priceTire.getRangeStart(), priceTire.getPrice().getBaseNonRecurringPrice());
			}

			Double recPrice = priceTireMap.get(Collections.min(priceTireMap.keySet()));
			Double recPriceSum = priceTireMap.get(Collections.min(priceTireMap.keySet()));

			Double nonRecPrice = baseNonPriceTireMap.get(Collections.min(priceTireMap.keySet()));
			Double nonRecPriceSum = baseNonPriceTireMap.get(Collections.min(priceTireMap.keySet()));


			for(int i = minValue.intValue(); i <= maxValue.intValue(); i++){
				if(priceTireMap.get(i) != null && baseNonPriceTireMap.get(i) != null){
					recPrice = priceTireMap.get(i);
					recPriceSum = priceTireMap.get(i);

					nonRecPrice = baseNonPriceTireMap.get(i);
					nonRecPriceSum = baseNonPriceTireMap.get(i);

					double incRecPrice = 0;
					double incNonRecPrice = 0;
					if(i > 0 && priceFinalTireMap.get(i-1) != null && baseNonPriceFinalTireMap.get(i-1) != null){
						incRecPrice = priceFinalTireMap.get(i-1);
						incNonRecPrice = baseNonPriceFinalTireMap.get(i-1);
					}
					recPrice += incRecPrice;
					nonRecPrice += incNonRecPrice;
					priceFinalTireMap.put(i, recPrice);
					baseNonPriceFinalTireMap.put(i, nonRecPrice);
				}
				else{
					recPrice += recPriceSum;
					nonRecPrice += nonRecPriceSum;
					priceFinalTireMap.put(i, recPrice);
					baseNonPriceFinalTireMap.put(i, nonRecPrice);
				}
			}

			if(priceFinalTireMap.size() == baseNonPriceFinalTireMap.size()){
				int i = minValue.intValue();
				for(; i <= maxValue.intValue(); i++){
					String priceString = "";
					if(baseNonPriceFinalTireMap.get(i) != 0.0){
						priceString = "$"+format.format(baseNonPriceFinalTireMap.get(i))+"/$"+format.format(priceFinalTireMap.get(i));
					}
					else{
						priceString = "$"+format.format(priceFinalTireMap.get(i));
					}
					priceTireMapDisplay.put(i, priceString);
				}
			}

			Option option = null;
			for(Entry<Integer, String> entry : priceTireMapDisplay.entrySet()){
				option = oFactory.createOption();
				option.setStyle("text-align: center;");
				option.setId(features.getExternalId());

				//setting content and value as number to corresponding price
				option.setContent(entry.getKey()+" - "+entry.getValue());
				String widthValue = entry.getKey()+" - "+entry.getValue();
				if(widthValue != null && widthValue.length() > 20){
					select.setStyle("width:150px");
				}
				String value = entry.getKey()+"-"+entry.getValue();
				option.setValue(value);
				if(df != null && !preSelectedMap.isEmpty()){
					if(df.getValueTarget() != null){
						if(preSelectedMap.get(df.getValueTarget()) != null){
							option.setSelected("selected");
						}
					}
				}
				if(prevSelectedFeatureMap != null && prevSelectedFeatureMap.size() > 0){
					if(prevSelectedFeatureMap.get(features.getExternalId()) != null && prevSelectedFeatureMap.get(features.getExternalId()).equals(String.valueOf(entry.getKey()))){
						option.setSelected("selected");
					}
				}

				if(selectBoxSelected != null && selectBoxSelected.trim().length() > 0){
					String[] selectBoxArr = null;
					if(selectBoxSelected.indexOf(",") > 0){
						selectBoxArr = selectBoxSelected.split(",");
					}
					for(String str : selectBoxArr){
						if(str.indexOf(":::") > 0){
							String[] splArr = str.split(":::");
							if(splArr[0].equals(features.getExternalId())){
								if(splArr[1].indexOf("-") > 0){
									String[] priceArr = splArr[1].split("-");
									if(Integer.valueOf(entry.getKey()) == Integer.valueOf(priceArr[0])){
										option.setSelected("selected");												
									}
								}
							}
						}
					}
				}

				select.getOptionOrOptgroup().add(option);
			}
		}

		else if(features.getPrice() != null){
			Double baseRecPrice = features.getPrice().getBaseRecurringPrice();
			Double baseRec = features.getPrice().getBaseRecurringPrice();

			Double baseNonRecurr = features.getPrice().getBaseNonRecurringPrice();
			Double baseNonRecurrSum = features.getPrice().getBaseNonRecurringPrice();

			Option option = null;

			int i = minValue.intValue();

			for(; i <= maxValue.intValue(); i++){
				String incrementedStr = "";
				priceTireMap.put(i, baseRecPrice);
				baseNonPriceTireMap.put(i, baseNonRecurr);
				if(i == 0){
					incrementedStr = "$0.00";
					priceTireMapDisplay.put(i, incrementedStr);
				}
				else {
					if(baseNonRecurr != 0.00){
						incrementedStr = "$"+format.format(baseNonRecurr)+"/$"+format.format(baseRecPrice);
						baseNonRecurr += baseNonRecurrSum;
					}
					else{
						incrementedStr = "$"+format.format(baseRecPrice);
					}
					priceTireMapDisplay.put(i, incrementedStr);
					baseRecPrice += baseRec;
				}
			}

			//setting content and value as number to corresponding price
			for(Entry<Integer, String> entry : priceTireMapDisplay.entrySet()){
				option = oFactory.createOption();
				option.setId(features.getExternalId());
				option.setStyle("text-align: center;");
				option.setContent(entry.getKey()+" - "+entry.getValue());
				String widthValue = entry.getKey()+" - "+entry.getValue();
				if(widthValue != null && widthValue.length() > 20){
					select.setStyle("width:150px");
				}
				String value = entry.getKey()+"-"+entry.getValue();
				option.setValue(value);
				if(df != null && !preSelectedMap.isEmpty()){
					if(df.getValueTarget() != null){
						if(preSelectedMap.get(df.getValueTarget()) != null){
							option.setSelected("selected");
						}
					}
				}
				if(prevSelectedFeatureMap != null && prevSelectedFeatureMap.size() > 0){
					if(prevSelectedFeatureMap.get(features.getExternalId()) != null && prevSelectedFeatureMap.get(features.getExternalId()).equals(String.valueOf(entry.getKey()))){
						option.setSelected("selected");
					}
				}

				if(selectBoxSelected != null && selectBoxSelected.trim().length() > 0){
					String[] selectBoxArr = null;
					if(selectBoxSelected.indexOf(",") > 0){
						selectBoxArr = selectBoxSelected.split(",");
					}
					for(String str : selectBoxArr){
						if(str.indexOf(":::") > 0){
							String[] splArr = str.split(":::");
							if(splArr[0].equals(features.getExternalId())){
								if(splArr[1].indexOf("-") > 0){
									String[] priceArr = splArr[1].split("-");
									if(Integer.valueOf(entry.getKey()) == Integer.valueOf(priceArr[0])){
										option.setSelected("selected");												
									}
								}
							}
						}
					}
				}

				select.getOptionOrOptgroup().add(option);
			}
		}
		select.setName(features.getExternalId());
		return select;
	}

}