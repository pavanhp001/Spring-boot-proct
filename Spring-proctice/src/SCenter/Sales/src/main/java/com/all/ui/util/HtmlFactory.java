package com.AL.ui.util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.AL.html.A;
import com.AL.html.Br;
import com.AL.html.Div;
import com.AL.html.Fieldset;
import com.AL.html.Img;
import com.AL.html.Inline;
import com.AL.html.Input;
import com.AL.html.Label;
import com.AL.html.Legend;
import com.AL.html.Li;
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
import com.AL.html.Ul;
import com.AL.productResults.util.OptionsSort;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.sales.StringConstraint;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.vo.ProductVOJSON;
import com.AL.xml.di.v4.DataConstraintType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.di.v4.DialogueType.DataGroupList;
import com.AL.xml.pr.v4.ChoiceType;
import com.AL.xml.pr.v4.CustomizationType;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.OptionsType;
import com.AL.xml.pr.v4.PriceTierType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.v4.LineItemSelectionType;
import com.AL.xml.v4.LineItemSelectionsType;
import com.AL.xml.v4.ProductInfo;
import com.AL.xml.v4.SelectionChoiceType;

import edu.emory.mathcs.backport.java.util.Arrays;

public enum HtmlFactory {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(HtmlFactory.class);

	private static final String DEFAULT_CSS_CLASS = "styled";
	private static final String WIDTH_70 = "width70Pc";
	private static final String CUSTOM_PADDING = "custom_padding";
	private static final String DEFAULT_CSS_OPTION_STYLE = "styledOption";
	private static final String DEFAULT_CSS_INSTRUCTIONS = "instructions";
	private static final String SELECTION = "Selection";
	private static final String COACHING = "coaching";
	private static final String SUGGESTEDSTYLE = "Suggested";
	private static final String RADIOMIDDLE = "radioMiddle";
	private static final String LIST = "List";
	private static final String TEXT = "Text";
	private static final ObjectFactory oFactory = new ObjectFactory();

	private static final String NESTED_OPTION_CSS = "";
	private static final String OPTION_CSS = " optionClz ";

	public List<Fieldset> featuresToFieldSet(ProductInfoType productInfo) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();

		List<FeatureType> ftList = productInfo.getProductDetails().getFeature();

		for (FeatureType featureType : ftList) {

			// TODO: Implement this using feature Type
			// Fieldset fieldset = toFieldSet(featureType);
			// fieldsetList.add(fieldset);

		}

		return fieldsetList;

	}

	public List<Fieldset> customizationToFieldSet(ProductInfoType productInfo) {

		return customizationToFieldSet(productInfo, null);
	}

	public List<Fieldset> customizationToFieldSet(ProductInfoType productInfo,
			StringBuilder events) {
		return customizationToFieldSet(productInfo, events, null);
	}

	public List<Fieldset> customizationToFieldSet(ProductInfoType productInfo,
			StringBuilder events, LineItemSelectionsType selectedCustomizations) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();

		List<CustomizationType> ctList = productInfo.getProductDetails()
		.getCustomization();

		Map<String, Map<String, String>> selectedCustomizationMap = null;
		if(selectedCustomizations != null) {
			selectedCustomizationMap = createCustomizationMap(selectedCustomizations);
		}
		for (CustomizationType customizationType : ctList) {
			Fieldset fieldset = toFieldSet(customizationType, selectedCustomizationMap);
			fieldsetList.add(fieldset);
		}

		return fieldsetList;

	}

	public List<Fieldset> toFieldSet(List<CustomizationType> customizationTypeList, 
			boolean isNested, Map<String, Map<String, String>> selectedCustomizations) {

		List<Fieldset> fieldList = new ArrayList<Fieldset>();

		for (CustomizationType customizationType : customizationTypeList) {
			Fieldset fs = toFieldSet(customizationType, selectedCustomizations);
			String nestedClazz = isNested ? NESTED_OPTION_CSS : OPTION_CSS;
			fs.setClazz(fs.getClazz() + nestedClazz);
			fieldList.add(fs);
		}

		return fieldList;
	}

	public Fieldset toFieldSet(CustomizationType customizationType, 
			Map<String, Map<String, String>> selectedCustomizations) {

		Fieldset fieldset = oFactory.createFieldset();

		fieldset.setId(customizationType.getExternalId());
		fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
		Legend legend = oFactory.createLegend();
		legend.getContent().add(customizationType.getShortDescription());

		InputTypeEnum optionDisplayType = determineDisplayType(customizationType
				.getOptions());

		if(optionDisplayType != InputTypeEnum.text) {
			addInstructions(fieldset, legend, customizationType.getOptions());
		}	
		addChoices(customizationType.getExternalId(), fieldset,
				customizationType.getOptions(), optionDisplayType, selectedCustomizations);

		return fieldset;
	}

	public InputTypeEnum determineDisplayType(OptionsType optionsType) {

		InputTypeEnum optionDisplayType = InputTypeEnum.text;

		if(LIST.equalsIgnoreCase(optionsType.getType())) {
			optionDisplayType = InputTypeEnum.checkbox;
		} else if(SELECTION.equalsIgnoreCase(optionsType.getType())) {
			optionDisplayType = InputTypeEnum.radio;
		} 
		return optionDisplayType;
	}

	public void addInstructions(Fieldset fieldset, Legend legend,
			OptionsType optionsType) {
		if(!TEXT.equalsIgnoreCase(optionsType.getType())) {
			fieldset.getContent().add(legend);
		}
		// CHECK FOR NULL
		if (optionsType.getMin().longValue() == optionsType.getMax()
				.longValue()) {
			String amount = NumberToWords.processor.getName(optionsType
					.getMin().longValue());

			Span span = oFactory.createSpan();
			span.getClazz().add(DEFAULT_CSS_OPTION_STYLE);
			span.getClazz().add(DEFAULT_CSS_INSTRUCTIONS);
			span.getContent().add(
					"Select " + amount + "[" + optionsType.getMin() + "]");
			legend.getContent().add(span);

		} else {
			Span span = oFactory.createSpan();
			span.getClazz().add(DEFAULT_CSS_OPTION_STYLE);
			span.getClazz().add(DEFAULT_CSS_INSTRUCTIONS);
			span.getContent().add(
					"Select min:" + optionsType.getMin() + " max:"
					+ optionsType.getMax());
			legend.getContent().add(span);
		}

	}

	public void addChoices(String groupId, Fieldset fieldset, OptionsType optionsType, 
			InputTypeEnum optionDisplayType, Map<String, Map<String, String>> selectedCustomizations) {

		List<ChoiceType> choiceTypeList = optionsType.getChoice();

		Collections.sort(choiceTypeList, OptionsSort.OPTIONS_SORT);

		Map<String, String> selectedChoices = null;
		if(selectedCustomizations != null) {
			selectedChoices = selectedCustomizations.get(groupId);
		}	

		for (ChoiceType choice : choiceTypeList) {

			Label label = oFactory.createLabel();

			label.setClazz(DEFAULT_CSS_CLASS + " " + WIDTH_70);
			Input input = oFactory.createInput();
			input.setType(optionDisplayType.name());
			input.setId(choice.getExternalId());

			if(optionDisplayType != InputTypeEnum.text) {
				input.setClazz(DEFAULT_CSS_CLASS);
				input.setValue(choice.getExternalId());
				input.setName(groupId);
				label.getContent().add(
						choice.getShortDescription() + " - ("
						+ PriceBuilder.getPriceLabel(choice) + ")");
				//set the preselected choice
				if(selectedChoices != null) {
					if(selectedChoices.containsKey(input.getId())) {
						input.setChecked("true");
					}
				}

				fieldset.getContent().add(input);
				fieldset.getContent().add(label);
			} else {
				input.setSize("15");
				input.setName(choice.getExternalId());
				label.getContent().add(choice.getShortDescription());
				fieldset.getContent().add(label);
				//set the preselected value
				if(selectedChoices != null) {
					String value = selectedChoices.get(input.getId());
					if(!StringUtils.isEmpty(value)) {
						input.setValue(value);
					}
				}
				fieldset.getContent().add(input);
			}

			if ((choice.getCustomization() != null) && (choice.getCustomization().size() > 0)) {
				fieldset.getContent().addAll(
						toFieldSet(choice.getCustomization(), Boolean.TRUE, selectedCustomizations));
			}

		}

		fieldset.getContent().add(oFactory.createP());
	}

	private Map<String, Map<String, String>> createCustomizationMap(LineItemSelectionsType selectedCustomizations) {
		//Map of all choice externalIds and details
		Map<String, String> selectedChoiceMap = null;
		//Map of all customization externalIds and choices id inside that customization 
		Map<String, Map<String, String>> selectedCustomizationMap = new HashMap<String, Map<String, String>>();	
		if(selectedCustomizations != null) {
			for(LineItemSelectionType itemSelection : selectedCustomizations.getSelection()) {
				selectedChoiceMap = new HashMap<String, String>();
				for(SelectionChoiceType selChoice : itemSelection.getSelectionChoice()) {
					selectedChoiceMap.put(selChoice.getExternalId(), selChoice.getDetail());
				}
				selectedCustomizationMap.put(itemSelection.getExternalId(), selectedChoiceMap);
			}
		}
		return selectedCustomizationMap;
	}

	public List<Fieldset> dialogueToFieldSet(StringBuilder events, List<DataField> dfs, 
			Map<String, Map<String, List<String>>> enableDependencies) {
		return dialogueToFieldSet(events, dfs, enableDependencies, true);		
	}


	public List<Fieldset> dialogueToFieldSet(StringBuilder events, List<DataField> dfs, 
			Map<String, Map<String, List<String>>> enableDependencies, boolean useParagraph) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		for(DataField df : dfs){
			Fieldset fieldset = oFactory.createFieldset();
			if(!df.isEnabled()) {
				//fieldset.setHidden("true");
			}
			fieldset.setId(df.getExternalId()+"_FS");
			if(df.getDescription() != null && df.getDescription().equals("Coaching")){
				fieldset.setClazz(DEFAULT_CSS_CLASS + " "+ COACHING + " " + CUSTOM_PADDING);
			}
			else if(df.getDescription() != null && df.getDescription().equals("Suggested")){
				fieldset.setClazz(DEFAULT_CSS_CLASS + " "+ SUGGESTEDSTYLE + " " + CUSTOM_PADDING);
			}
			else
			{
				fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
			}
			//fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
			InputTypeEnum inputDisplayType = getDialogueDisplayType(df);
			boolean hasDependency = false;
			if(enableDependencies != null) {
				hasDependency = enableDependencies.containsKey(df.getExternalId());
			}
			if(useParagraph) {
				addDataField(fieldset, df, inputDisplayType, hasDependency,false);
			} else {
				//Br br = oFactory.createBr();
				addDataFieldForQual(fieldset, df, inputDisplayType, hasDependency);
				if(df.getDescription() != null && df.getDescription().equals("Coaching")){
					//Changes made to Ticket  86733
					if(df.getExternalId().equals("Scripted_Qual_NonRR_7")||df.getExternalId().equals("Scripted_Qual_NonRR_8")){
						//fieldset.getContent().add(br);
						//fieldset.getContent().add(br);
					}else{
						//fieldset.getContent().add(br);
					}
				}else
				{
					//fieldset.getContent().add(br);
					//fieldset.getContent().add(br);
				}
			}
			fieldsetList.add(fieldset);
		}

		return fieldsetList;		
	}	


	public List<Fieldset> dialogueToFieldSet(StringBuilder events, List<DataField> dfs, 
			Map<String, Map<String, List<String>>> enableDependencies,List<Fieldset> cSATFieldSetList) {
		return dialogueToFieldSet(events, dfs, enableDependencies, true,cSATFieldSetList);		
	}


	public List<Fieldset> dialogueToFieldSet(StringBuilder events, List<DataField> dfs, 
			Map<String, Map<String, List<String>>> enableDependencies, boolean useParagraph,List<Fieldset> cSATFieldSetList) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		for(DataField df : dfs){
			Fieldset fieldset = oFactory.createFieldset();
			if(!df.isEnabled()) {
				fieldset.setHidden("true");
			}
			fieldset.setId(df.getExternalId()+"_FS");
			if(df.getDescription() != null && (df.getDescription().equals("Coaching") || df.getDescription().equals("Hints")))
			{
				fieldset.setClazz(DEFAULT_CSS_CLASS + " "+ COACHING + " " + CUSTOM_PADDING);
			}
			else if(df.getDescription() != null && df.getDescription().equals("Suggested")){
				fieldset.setClazz(DEFAULT_CSS_CLASS + " "+ SUGGESTEDSTYLE + " " + CUSTOM_PADDING);
			}
			else
			{
				fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
			}
			//fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
			InputTypeEnum inputDisplayType = getDialogueDisplayType(df);
			boolean hasDependency = false;
			if(enableDependencies != null) {
				hasDependency = enableDependencies.containsKey(df.getExternalId());
			}

			addDataField(fieldset, df, inputDisplayType, hasDependency,true);

			if("CloseCallNoSale_CSAT_Survey_Email".equalsIgnoreCase(df.getExternalId()))
			{
				cSATFieldSetList.add(fieldset);
			}
			else if("CloseCallNoSale_CSAT_Customer_Email".equalsIgnoreCase(df.getExternalId()))
			{
				cSATFieldSetList.add(fieldset);
			}
			else if("CloseCallNoSale_CSAT_NoProblem".equalsIgnoreCase(df.getExternalId()))
			{
				cSATFieldSetList.add(fieldset);
			}
			else if("CloseCallNoSale_NoProblem".equalsIgnoreCase(df.getExternalId())){
				cSATFieldSetList.add(fieldset);
			}
			else if("CloseCallNoSale_Webflow_Survey_Email".equalsIgnoreCase(df.getExternalId())
					||"CloseCallNoSale_Webflow_Email".equalsIgnoreCase(df.getExternalId())){
				cSATFieldSetList.add(fieldset);
			}
			else
			{
				fieldsetList.add(fieldset);
			}
		}

		return fieldsetList;		
	}	


	public Fieldset dialogueToFieldSetDiscovery(StringBuilder events, List<DataField> dfs, 
			Map<String, Map<String, List<String>>> enableDependencies) {

		Fieldset fieldset = oFactory.createFieldset();
		fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
		StringBuilder values = new StringBuilder();
		String recLoc = "any";
		//for(int i=0;i<dfs.size();i++){
		for(DataField df : dfs){

			values.append(df.getExternalId() + "|" + df.getName()+",");
			//DataField df = dfs.get(i);
			/*if(i==0){
				recLoc = "first";
			}else if(i==dfs.size()-1){
				recLoc = "last";
			}else{
				recLoc = "any";
			}*/
			InputTypeEnum inputDisplayType = getDialogueDisplayType(df);
			boolean hasDependency = false;
			if(enableDependencies != null) {
				hasDependency = enableDependencies.containsKey(df.getExternalId());
			}

			if(df.getIntegerConstraint() != null){
				addIntDataFieldDiscovery(fieldset, df, inputDisplayType, hasDependency, recLoc);
			}else{
				addDataFieldDiscovery(fieldset, df, inputDisplayType, hasDependency, recLoc);

			}
			//fieldsetList.add(fieldset);
		}

		if( values.length()>0  )
		{
			Input input = oFactory.createInput();
			input.setType("hidden");
			input.setId("discoveryQuestionsOrder");
			input.setName("discoveryQuestionsOrder");
			input.setValue(values.substring(0, values.length()-1));
			fieldset.getContent().add(input);
		}
		return fieldset;		
	}

	//This method is  for specific for Confirmation and Offer page
	public List<Fieldset> dialogueToFieldSetConfirm(StringBuilder events, List<DataField> dfs, 
			Map<String, Map<String, List<String>>> enableDependencies) {
		//1. Loop through the fields, get the text and constraints
		logger.info("INSIDE_SALES_HTML_FACTORY");
		logger.info("enableDependencies="+enableDependencies);
		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		for(DataField df : dfs){
			Fieldset fieldset = oFactory.createFieldset();
			if(!df.isEnabled()) {
				fieldset.setHidden("true");
			}
			fieldset.setId(df.getExternalId()+"_FS");
			if(df.getDescription() != null && df.getDescription().equals("Coaching")){
				fieldset.setClazz(DEFAULT_CSS_CLASS + " "+ COACHING + " " + CUSTOM_PADDING);
			}
			else if(df.getDescription() != null && df.getDescription().equals("Suggested")){
				fieldset.setClazz(DEFAULT_CSS_CLASS + " "+ SUGGESTEDSTYLE + " " + CUSTOM_PADDING);
			}
			else
			{
				fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
			}
			//fieldset.setClazz(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
			InputTypeEnum inputDisplayType = getDialogueDisplayType(df);
			boolean hasDependency = false;
			if(enableDependencies != null) {
				hasDependency = enableDependencies.containsKey(df.getExternalId());
			}
			addDataFieldConfirm(fieldset, df, inputDisplayType, hasDependency);
			/*if(cSpan != null){
				contentSetFld.getContent().add(cSpan);
			}*/
			fieldsetList.add(fieldset);
		}
		return fieldsetList;		
	}

	//This method is  for specific for Confirmation and Offer page
	private void addDataFieldConfirm(Fieldset fieldset, DataField df, InputTypeEnum inputType, boolean hasDependency) {

		P p = oFactory.createP();
		p.getContent().add(df.getText());
		p.getContent().add("  ");

		if(df.getDescription() != null && df.getDescription().equals("Suggested")){
			p.getClazz().add("grey");
		}
		else if(df.getDescription() != null && !df.getDescription().equals("Coaching")){
			p.getClazz().add("black");
		}
		//label.setClazz(DEFAULT_CSS_CLASS);
		fieldset.getContent().add(p);

		/*Span cSpan = null;

		if(inputType == InputTypeEnum.select) {
			Select select = oFactory.createSelect();
			select.setClazz(DEFAULT_CSS_CLASS);
			select.setId(df.getExternalId());
			if(hasDependency) {
				select.setOnchange("activate(\'" + df.getExternalId() + "\')");
			}
			StringConstraint stringConstraint = df.getStringConstraint();
			Option option = null;
			if(stringConstraint != null) {
				List<String> valuesList = stringConstraint.getListOfValues();
				for(String value : valuesList) {
					option = oFactory.createOption();
					option.setContent(value);
					option.setValue(value);
					select.getOptionOrOptgroup().add(option);
				}
			}
			select.setName(df.getExternalId());
			cSpan = oFactory.createSpan();
			cSpan.getContent().add(select);
			//contentSetFld.getContent().add(cSpan);
		} else if(inputType == InputTypeEnum.text) {
			Input input = oFactory.createInput();
			input.setType(inputType.name());
			input.setId(df.getExternalId());
			input.setClazz(DEFAULT_CSS_CLASS);
			input.setName(df.getExternalId());
			cSpan = oFactory.createSpan();
			cSpan.getContent().add(input);
			//contentSetFld.getContent().add(cSpan);
		} else if(inputType == InputTypeEnum.radio) {
			if(df.getBooleanConstraint() != null) {
				cSpan = oFactory.createSpan();
				cSpan.getContent().add(createBooleanFields(df.getExternalId(), p, hasDependency));
				//contentSetFld.getContent().add(cSpan);
			}
		}
		return cSpan;*/
	}

	private void addDataFieldDiscovery(Fieldset fieldset, DataField df, InputTypeEnum inputType, boolean hasDependency, String recLoc) {

		P p = oFactory.createP();
		//p.getClazz().add("parenthead");

		Span head = oFactory.createSpan();
		head.getClazz().add("parenthead");

		Span title = oFactory.createSpan();
		if(df.getExternalId()!= null &&("DiscoveryTransfer_Webflow_1".equals(df.getExternalId())
				||"DiscoveryNew_Webflow_1".equals(df.getExternalId()))&&df.getDescription() != null && df.getDescription().equals("Coaching")){
			title.setStyle("color: #009900;"); 
		}
		title.getClazz().add(recLoc);
		title.getContent().add(df.getText());
		head.getContent().add(title);
		//p.getContent().add("  ");
		//label.setClazz(DEFAULT_CSS_CLASS);

		Span fld = oFactory.createSpan();
		fld.getClazz().add("noborder");

		if(inputType == InputTypeEnum.select) {
			Select select = oFactory.createSelect();
			select.setClazz(DEFAULT_CSS_CLASS);
			select.setId(df.getExternalId());
			if(hasDependency) {
				select.setOnchange("activate(\'" + df.getExternalId() + "\')");
			}
			StringConstraint stringConstraint = df.getStringConstraint();
			Option option = null;
			if(stringConstraint != null) {
				List<String> valuesList = stringConstraint.getListOfValues();
				for(String value : valuesList) {
					option = oFactory.createOption();
					option.setContent(value);
					option.setValue(value);
					select.getOptionOrOptgroup().add(option);
				}
			}
			select.setName(df.getExternalId() + "|" + df.getName());
			fld.getContent().add(select);
			head.getContent().add(fld);
		} else if(inputType == InputTypeEnum.text) {
			Input input = oFactory.createInput();
			input.setType(inputType.name());
			input.setId(df.getExternalId());
			input.setClazz(DEFAULT_CSS_CLASS);
			input.setName(df.getExternalId() + "|" + df.getName());
			input.setMaxlength("20");
			fld.getContent().add(input);
			head.getContent().add(fld);
		} else if(inputType == InputTypeEnum.radio) {
			if(df.getBooleanConstraint() != null) {
				fld.getContent().add(createBooleanFields(df.getExternalId(),df.getName(), p, hasDependency));
				head.getContent().add(fld);
			}
		}

		fieldset.getContent().add(head);
	}

	private void addIntDataFieldDiscovery(Fieldset fieldset, DataField df, InputTypeEnum inputType, boolean hasDependency, String recLoc) {

		P p = oFactory.createP();
		//p.getClazz().add("parenthead");

		Span head = oFactory.createSpan();
		head.getClazz().add("parenthead");

		Span title = oFactory.createSpan();
		title.getClazz().add(recLoc);
		title.getContent().add(df.getText());
		head.getContent().add(title);
		//p.getContent().add("  ");
		//label.setClazz(DEFAULT_CSS_CLASS);

		Span fld = oFactory.createSpan();
		fld.getClazz().add("noborder");

		if(inputType == InputTypeEnum.select) {
			Select select = oFactory.createSelect();
			select.setClazz(DEFAULT_CSS_CLASS);
			select.setId(df.getExternalId());
			if(hasDependency) {
				select.setOnchange("activate(\'" + df.getExternalId() + "\')");
			}
			StringConstraint stringConstraint = df.getStringConstraint();
			Option option = null;
			if(stringConstraint != null) {
				List<String> valuesList = stringConstraint.getListOfValues();
				for(String value : valuesList) {
					option = oFactory.createOption();
					option.setContent(value);
					option.setValue(value);
					select.getOptionOrOptgroup().add(option);
				}
			}
			select.setName(df.getExternalId() + "|" + df.getName());
			fld.getContent().add(select);
			head.getContent().add(fld);
		} else if(inputType == InputTypeEnum.text) {
			Input input = oFactory.createInput();
			input.setType(inputType.name());
			input.setId("intFld_"+df.getExternalId());
			input.setClazz(DEFAULT_CSS_CLASS);
			input.setName(df.getExternalId() + "|" + df.getName());
			input.setMaxlength("2");
			fld.getContent().add(input);
			head.getContent().add(fld);
		} else if(inputType == InputTypeEnum.radio) {
			if(df.getBooleanConstraint() != null) {
				fld.getContent().add(createBooleanFields(df.getExternalId(), df.getName(), p, hasDependency));
				head.getContent().add(fld);
			}
		}

		fieldset.getContent().add(head);
	}

	private void addDataField(Fieldset fieldset, DataField df, InputTypeEnum inputType, boolean hasDependency,boolean isCloseCallNoSale) {

		P p = oFactory.createP();
		p.getContent().add(df.getText());
		p.getContent().add("  ");
		if(df.getDescription() != null && df.getDescription().equals("Suggested")){
			p.getClazz().add("grey");
		}
		else if(df.getDescription() != null && (df.getDescription().equals("Coaching") 
				|| df.getDescription().equals("Hints"))){
			p.getClazz().add("green");
		}
		else{
			if( !"CloseCallNoSale_CSAT_Survey_Email".equalsIgnoreCase(df.getExternalId())
					&& !"CloseCallNoSale_CSAT_Customer_Email".equalsIgnoreCase(df.getExternalId())
					&& !"CloseCallNoSale_CSAT_NoProblem".equalsIgnoreCase(df.getExternalId())
					&& !"CloseCallNoSale_NoProblem".equalsIgnoreCase(df.getExternalId()) 
					&& !("CloseCallNoSale_Webflow_Survey_Email".equalsIgnoreCase(df.getExternalId())
							||"CloseCallNoSale_Webflow_Email".equalsIgnoreCase(df.getExternalId())))
			{
				p.getClazz().add("black");
			}
		}
		//label.setClazz(DEFAULT_CSS_CLASS);

		if(inputType == InputTypeEnum.select) {
			Select select = oFactory.createSelect();
			select.setClazz(DEFAULT_CSS_CLASS);
			select.setId(df.getExternalId());
			if(hasDependency) {
				select.setOnchange("activate(\'" + df.getExternalId() + "\')");
			}
			StringConstraint stringConstraint = df.getStringConstraint();
			if(stringConstraint != null) {
				List<String> valuesList = stringConstraint.getListOfValues();
				Option option = oFactory.createOption();
				option.setContent("Please Select");
				option.setValue("");
				select.getOptionOrOptgroup().add(option);
				for(String value : valuesList) {
					Option options = oFactory.createOption();
					options.setContent(value);
					options.setValue(value);
					select.getOptionOrOptgroup().add(options);
				}
			}
			select.setName(df.getExternalId());
			p.getContent().add(select);
			fieldset.getContent().add(p);
		} else if(inputType == InputTypeEnum.text) {
			Input input = oFactory.createInput();
			input.setType(inputType.name());
			input.setId(df.getExternalId());
			input.setClazz(DEFAULT_CSS_CLASS);
			input.setName(df.getExternalId());
			p.getContent().add(input);
			fieldset.getContent().add(p);
		} else if(inputType == InputTypeEnum.radio) {
			if(df.getBooleanConstraint() != null) {
				fieldset.getContent().add(createBooleanFields(df.getExternalId(), p, hasDependency));
			}
		}else{
			if(isCloseCallNoSale && ("Hints".equals(df.getDescription()) || "Coaching".equals(df.getDescription())))
			{
				fieldset.getContent().add(df.getText());
			}
			else
			{
				fieldset.getContent().add(p);
			}
		}			

	}

	private void addDataFieldForQual(Fieldset fieldset, DataField df, InputTypeEnum inputType, boolean hasDependency) {

		P p = oFactory.createP();
		p.getContent().add(df.getText());

		p.getContent().add("  ");
		if(df.getDescription() != null && df.getDescription().equals("Suggested")){
			p.getClazz().add("grey");
		}
		else if(df.getDescription() != null && ( df.getDescription().equals("Coaching") || df.getDescription().equals("Hints"))){
			p.getClazz().add("green");
		}
		else{
			p.getClazz().add("black");
		}
		//label.setClazz(DEFAULT_CSS_CLASS);

		if(inputType == InputTypeEnum.select) {
			Select select = oFactory.createSelect();
			select.setClazz(DEFAULT_CSS_CLASS);
			select.setId(df.getExternalId());
			if(hasDependency) {
				select.setOnchange("activate(\'" + df.getExternalId() + "\')");
			}
			StringConstraint stringConstraint = df.getStringConstraint();
			Option option = null;
			if(stringConstraint != null) {
				List<String> valuesList = stringConstraint.getListOfValues();
				for(String value : valuesList) {
					option = oFactory.createOption();
					option.setContent(value);
					option.setValue(value);
					select.getOptionOrOptgroup().add(option);
				}
			}
			select.setName(df.getExternalId());
			p.getContent().add(select);
			fieldset.getContent().add(p);
		} else if(inputType == InputTypeEnum.text) {
			Input input = oFactory.createInput();
			input.setType(inputType.name());
			input.setId(df.getExternalId());
			input.setClazz(DEFAULT_CSS_CLASS);
			input.setName(df.getExternalId());
			p.getContent().add(input);
			fieldset.getContent().add(p);
		} else if(inputType == InputTypeEnum.radio) {
			if(df.getBooleanConstraint() != null) {
				fieldset.getContent().add(createBooleanFields(df.getExternalId(), p, hasDependency));
			}
		}else{
			fieldset.getContent().add(p);
		}			

	}

	private Inline createBooleanFields(String groupExternalId, String groupName, Inline p, boolean hasDependency) {

		//Br br = oFactory.createBr();
		//fieldset.getContent().add(br);
		//P pgraph = oFactory.createP();
		Span rSpan = oFactory.createSpan();
		rSpan.setStyle("display:inline-block;");

		Span labelY = oFactory.createSpan();
		//labelY.setClazz(DEFAULT_CSS_CLASS);


		Input inputY = oFactory.createInput();
		inputY.setType(InputTypeEnum.radio.name());
		inputY.setClazz(DEFAULT_CSS_CLASS+" "+RADIOMIDDLE);
		inputY.setName(groupExternalId+"|"+groupName);
		inputY.setId("BOOL_Y_" + groupExternalId);
		inputY.setValue("Y");
		if(hasDependency) {
			inputY.setOnclick("activate(\'" + inputY.getId() + "\')");
		}
		rSpan.getContent().add(inputY);
		labelY.getContent().add("Yes");
		rSpan.getContent().add(labelY);

		Span labelN = oFactory.createSpan();
		//labelN.setClazz(DEFAULT_CSS_CLASS);

		Input inputN = oFactory.createInput();
		inputN.setType(InputTypeEnum.radio.name());
		inputN.setId("BOOL_N_" + groupExternalId);
		inputN.setClazz(DEFAULT_CSS_CLASS+" "+RADIOMIDDLE);
		inputN.setName(groupExternalId+"|"+groupName);
		inputN.setValue("N");
		if(hasDependency) {
			inputN.setOnclick("activate(\'" + inputN.getId() + "\')");
		}
		rSpan.getContent().add(inputN);
		labelN.getContent().add("No");
		rSpan.getContent().add(labelN);

		p.getContent().add(rSpan);
		return p;
		//fieldset.getContent().add(pgraph);
	}

	private Inline createBooleanFields(String groupName, Inline p, boolean hasDependency) {

		//Br br = oFactory.createBr();
		//fieldset.getContent().add(br);
		//P pgraph = oFactory.createP();
		Span rSpan = oFactory.createSpan();
		rSpan.setStyle("display:inline-block;");

		Span labelY = oFactory.createSpan();
		//labelY.setClazz(DEFAULT_CSS_CLASS);


		Input inputY = oFactory.createInput();
		inputY.setType(InputTypeEnum.radio.name());
		inputY.setClazz(DEFAULT_CSS_CLASS+" "+RADIOMIDDLE);
		inputY.setName(groupName);
		inputY.setId("BOOL_Y_" + groupName);
		inputY.setValue("Y");
		if(hasDependency) {
			inputY.setOnclick("activate(\'" + inputY.getId() + "\')");
		}
		rSpan.getContent().add(inputY);
		labelY.getContent().add("Yes");
		rSpan.getContent().add(labelY);

		Span labelN = oFactory.createSpan();
		//labelN.setClazz(DEFAULT_CSS_CLASS);

		Input inputN = oFactory.createInput();
		inputN.setType(InputTypeEnum.radio.name());
		inputN.setId("BOOL_N_" + groupName);
		inputN.setClazz(DEFAULT_CSS_CLASS+" "+RADIOMIDDLE);
		inputN.setName(groupName);
		inputN.setValue("N");
		if(hasDependency) {
			inputN.setOnclick("activate(\'" + inputN.getId() + "\')");
		}
		rSpan.getContent().add(inputN);
		labelN.getContent().add("No");
		rSpan.getContent().add(labelN);

		p.getContent().add(rSpan);
		return p;
		//fieldset.getContent().add(pgraph);
	}

	/*private void createBooleanFields(String groupName, Fieldset fieldset, boolean hasDependency) {

		//Br br = oFactory.createBr();
		//fieldset.getContent().add(br);
		P pgraph = oFactory.createP();

		Span labelY = oFactory.createSpan();
		//labelY.setClazz(DEFAULT_CSS_CLASS);
		labelY.getContent().add("Yes");
		pgraph.getContent().add(labelY);

		Input inputY = oFactory.createInput();
		inputY.setType(InputTypeEnum.radio.name());
		inputY.setClazz(DEFAULT_CSS_CLASS);
		inputY.setName(groupName);
		inputY.setId("BOOL_Y_" + groupName);
		inputY.setValue("Y");
		if(hasDependency) {
			inputY.setOnclick("activate(\'" + inputY.getId() + "\')");
		}
		pgraph.getContent().add(inputY);

		Span labelN = oFactory.createSpan();
		//labelN.setClazz(DEFAULT_CSS_CLASS);
		labelN.getContent().add("No");
		pgraph.getContent().add(labelN);

		Input inputN = oFactory.createInput();
		inputN.setType(InputTypeEnum.radio.name());
		inputN.setId("BOOL_N_" + groupName);
		inputN.setClazz(DEFAULT_CSS_CLASS);
		inputN.setName(groupName);
		inputN.setValue("N");
		if(hasDependency) {
			inputN.setOnclick("activate(\'" + inputN.getId() + "\')");
		}
		pgraph.getContent().add(inputN);

		fieldset.getContent().add(pgraph);
	}*/

	private InputTypeEnum getDialogueDisplayType(DataField df) {
		InputTypeEnum optionDisplayType = null;
		if(df.getStringConstraint() != null) {
			if(df.getStringConstraint().getValue() != null) {
				optionDisplayType = InputTypeEnum.text;
			} else if(df.getStringConstraint().getListOfValues() != null) {
				optionDisplayType = InputTypeEnum.select;
			}
		} else if(df.getBooleanConstraint() != null) {
			optionDisplayType = InputTypeEnum.radio;
		} else if(df.getIntegerConstraint() != null) {
			optionDisplayType = InputTypeEnum.text;
		}
		return optionDisplayType;
	}


	// get promotion field set
	public Fieldset getPromotionsFieldSet(List<ProductPromotionType> promotions, String providerExtId, String advisoryPromotion) {

		Fieldset fieldSet = oFactory.createFieldset();

		Table table = oFactory.createTable();
		//table.setClazz("pdet_features_content_cont");
		table.setId("tblPromotions");
		Thead thead = oFactory.createThead();
		Tr tr = oFactory.createTr();
		BigInteger b1 = new BigInteger("2");
		Th th = oFactory.createTh();
		// header 1      
		th = oFactory.createTh();
		//th.setStyle("width: 155px");
		th.getContent().add("Promotions");
		//th.getColspan().add(BigInteger.valueOf(2L));
		th.setColspan(BigInteger.valueOf(2L));
		//th.getColspan().add(b1);
		tr.getTh().add(th);
		thead.getTr().add(tr);
		table.setThead(thead);

		Tbody tbody = oFactory.createTbody();

		if( !Utils.isBlank(advisoryPromotion) )
		{
			tr = oFactory.createTr();
			Td td = oFactory.createTd();
			td.setStyle("color:blue;");
			td.getContent().add(advisoryPromotion);

			tr.getTd().add(td);
			tbody.getTr().add(tr);
		}
		else if(!promotions.isEmpty())
		{
			//fieldSet = oFactory.createFieldset();
			for (ProductPromotionType promotion : promotions) {
				if(("N/A".equals(promotion.getQualification())) && promotion.getQualification()!=null && promotion.getConditions()!=null &&
						("N/A".equals(promotion.getConditions())) && promotion.getDescription()!=null &&("N/A".equals(promotion.getDescription()))){
					tr = oFactory.createTr();
				}else{
					tr = oFactory.createTr();
					addTableDataPromotionsField(promotion,tr,providerExtId);
				}
				tbody.getTr().add(tr);

			}
		}
		table.setTbody(tbody);
		fieldSet.getContent().add(table);
		return fieldSet;
	}

	private void addTableDataPromotionsField(ProductPromotionType promotion, Tr tr, String providerExtId) {
		Td td = oFactory.createTd();

		td = oFactory.createTd(); 

		//Add Input CheckBox
		StringBuffer promoConflict = new StringBuffer();
		String promoConflictStr = "";
		/*
		   <ac:preconditions>
	            <ac:conflicts>
	                <ac:promotionExternalId>10990</ac:promotionExternalId>
	                <ac:promotionExternalId>10991</ac:promotionExternalId>
	            </ac:conflicts>
	        </ac:preconditions>
		 */
		List<String> metaList = new ArrayList<String>();

		if(promotion.getPreconditions() != null && promotion.getPreconditions().getConflicts() != null){
			for (String pct : promotion.getPreconditions().getConflicts().getPromotionExternalId()){
				metaList.add(pct);
			}
		}

		for (String s :metaList){
			promoConflict.append(s).append(",");
		}

		if (promoConflict.toString() != null && promoConflict.toString().endsWith(",")){
			promoConflictStr = promoConflict.substring(0, promoConflict.toString().length() - 1);
		}else{
			promoConflictStr = promoConflict.toString();
		}
		String promotionCheckBoxDisableProviders = ConfigRepo.getString("*.promotion_checkBox_disable_providers") == null ? null : ConfigRepo.getString("*.promotion_checkBox_disable_providers");
		String promotionCheckBoxHideProviders = ConfigRepo.getString("*.promotion_checkBox_hide_providers") == null ? null : ConfigRepo.getString("*.promotion_checkBox_hide_providers");

		List<String> disablePromotionCheckBoxProviders = null;
		List<String> HidePromotionCheckBoxProviders = null;
		
		if(promotionCheckBoxDisableProviders != null){
			disablePromotionCheckBoxProviders = Arrays.asList(promotionCheckBoxDisableProviders.split("\\|"));
		}
		if(promotionCheckBoxHideProviders != null){
			HidePromotionCheckBoxProviders = Arrays.asList(promotionCheckBoxHideProviders.split("\\|"));
		}
		
		logger.info("providersData="+providerExtId);
		if (providerExtId != null && !disablePromotionCheckBoxProviders.contains(providerExtId)) {
			Input input = oFactory.createInput();
			input.setType(InputTypeEnum.checkbox.name());
			input.setName(promotion.getExternalId());
			input.setId(promotion.getExternalId()+"_PROMOTION");
			input.setValue(createPromotionJSON(promotion, promoConflictStr));			
			//input.setDisabled("disabled");
			td.getContent().add(input);
		}
		else if(providerExtId != null && HidePromotionCheckBoxProviders.contains(providerExtId)){
			Input input = oFactory.createInput();
			input.setType("hidden");
			input.setName(promotion.getExternalId());
			input.setId(promotion.getExternalId());
			input.setClazz("Promotions");
			input.setValue(createPromotionJSON(promotion, promoConflictStr));			
			//input.setDisabled("disabled");
			td.getContent().add(input);
		}
		
		tr.getTd().add(td);

		// Add Promotion Data
		td = oFactory.createTd();
		Br br = oFactory.createBr();

		if("N/A".equals(promotion.getDescription()))
		{
			td.getContent().add("");
		}
		else{
			td.getContent().add(promotion.getDescription());
		}
		td.getContent().add(br);

		P p = oFactory.createP();
		if("N/A".equals(promotion.getQualification()))
		{
			p.getContent().add("");
		}
		else{
			p.getContent().add(promotion.getQualification());
		}
		td.getContent().add(p);

		p = oFactory.createP();
		if("N/A".equals(promotion.getConditions()))
		{
			p.getContent().add("");
		}
		else{
			p.setStyle("padding-left:30px");
			p.getContent().add("Terms and Conditions: ");
			p.getContent().add(promotion.getConditions());
		}

		td.getContent().add(p);
		tr.getTd().add(td);
	}

	private String createPromotionJSON(ProductPromotionType promotion, String promoConflict) {
		JSONObject promoJson = new JSONObject();
		try {
			promoJson.put("isPromotion", true);
			promoJson.put("productExternalId", promotion.getExternalId());
			promoJson.put("conditions", promotion.getConditions());
			promoJson.put("description", promotion.getDescription());
			promoJson.put("priceValue", promotion.getPriceValue());
			promoJson.put("priceValueType", promotion.getPriceValueType());
			promoJson.put("promoCode", promotion.getPromoCode());
			promoJson.put("qualification", promotion.getQualification());
			promoJson.put("type", promotion.getType());
			promoJson.put("shortDescription", promotion.getShortDescription());
			promoJson.put("promoConflict", promoConflict);

			if(promotion.getPromotionDuration() != null){
				promoJson.put("promoDuration",promotion.getPromotionDuration().toString() );
			}

			boolean isIncludePromotion = false;
			if (promotion.getMetaData() != null){
				String includePromoValuesStr = "";
				if (promotion.getMetaData().getMetaData() != null &&
						promotion.getMetaData().getMetaData().size() > 0) {
					for (String  strMetaData : promotion.getMetaData().getMetaData()){
						if(strMetaData != null && strMetaData.startsWith("INCLUDES=")) {
							isIncludePromotion = true;
							String strArray[] = strMetaData.split("=");
							if (includePromoValuesStr.isEmpty()) {
								includePromoValuesStr = strArray[1].toString();
							} else {
								includePromoValuesStr += ","+ strArray[1].toString();
							}
						}	
					}
					if (isIncludePromotion){
						promoJson.put("includesPromotions",includePromoValuesStr);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return promoJson.toString();
	}
	public Fieldset getFeatureFieldSet(List<FeatureType> features, List<FeatureGroupType> featureGroupList, String providerExtId, String providerName) {
		return getFeatureFieldSet(features,featureGroupList,providerExtId,providerName,false);
	}
	//this is for getFeatureFieldSet method with two parameters
	public Fieldset getFeatureFieldSet(List<FeatureType> features, List<FeatureGroupType> featureGroupList, String providerExtId, String providerName,boolean isATTDirecTV) {

		List<FeatureType> fets = Utils.sort(features);
		Fieldset fieldSet = oFactory.createFieldset();

		Table table = oFactory.createTable();
		table.setId("tblFeatures");
		Tr tr = oFactory.createTr();

		Tbody tbody = oFactory.createTbody();

		for (FeatureType feature : fets) {
			if(feature.isAvailable() == false){
				//            	addTableDataFeaturesField(feature,tr);	
			}else {
				if (!feature.getExternalId().startsWith("RTS")){
					tr = oFactory.createTr();
					addTableDataFeaturesField(feature, tr, providerExtId, providerName, isATTDirecTV);	
					tbody.getTr().add(tr);
				}
			}
		}
		if(featureGroupList != null){
			for (FeatureGroupType featureGroup : featureGroupList) {
				if (!featureGroup.getExternalId().startsWith("RTS")){
					tr = oFactory.createTr();
					addTableDataFeatureGroupField(featureGroup, tr);
					tbody.getTr().add(tr);
				}
			}
		}

		table.setTbody(tbody);
		fieldSet.getContent().add(table);
		return fieldSet;
	}

	private void addTableDataFeaturesField(FeatureType features, Tr tr, String providerExtId,String providerName,boolean isATTDirecTV) {

		Td td = oFactory.createTd();
		DecimalFormat format = new DecimalFormat("#0.00");
		boolean isChannelLineupProvider = false;
		if(features.getExternalId() != null 
				&& ( features.getExternalId().equalsIgnoreCase("NUM_CHANNEL_AVAIL_STRING") 
						|| features.getExternalId().equalsIgnoreCase("NUM_CHANNEL_AVAIL") 
						|| features.getExternalId().equalsIgnoreCase("DEFAULT_NUM_CHANNEL_AVAIL") ) )
		{
			String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
			logger.info("providersData="+providersData);


			if (!Utils.isBlank(providersData))
			{
				String providersList[] = providersData.split("\\|");
				for (String providerIdWithName : providersList)
				{
					String providerValues[] = providerIdWithName.split("=");
					if (providerValues[0].trim().equalsIgnoreCase(providerExtId))
					{
						if(Utils.isBlank(providerName)){
							providerName = providerValues[1];
						}
						isChannelLineupProvider = true;
						break;
					}
				}
			}

			if( !isChannelLineupProvider && 
					features.getExternalId().equalsIgnoreCase("DEFAULT_NUM_CHANNEL_AVAIL"))
			{
				return;
			}

			if (providerName.equalsIgnoreCase("ATTSTI"))
			{
				providerName = "AT&T";
			}
			else if (providerName.equalsIgnoreCase("DISH Network"))
			{
				providerName = "Dish";
			}
			providerName = escapeSpecialCharacters(providerName);
		}
		if(isChannelLineupProvider)
		{
			logger.info("html factory outer displayChannelLineUpData ::DIRECTV ");
			A a = oFactory.createA();
			if(isATTDirecTV){
				a.setOnclick("displayChannelLineUpData('"+Constants.DIRECTSTAR+"','"+Constants.DIRECTV_NAME+"')");
			}else{
			a.setOnclick("displayChannelLineUpData('"+providerExtId+"','"+providerName+"')");
			}
			a.getContent().add(features.getType());
			a.setHref("#");
			a.setStyle("color:#38ACEC");
			td.getContent().add(a);
		}
		else
		{
			td.getContent().add(features.getType());
		}

		td.setStyle("width:22%;");
		//td.setClazz("labelColumn");
		Br br = oFactory.createBr();
		tr.getTd().add(td);

		td = oFactory.createTd();
		td.setStyle("width:18%");
		Input iChkBoxHidden = oFactory.createInput();
		iChkBoxHidden.setType("hidden");
		iChkBoxHidden.setId(features.getExternalId()+"_Feature_price");

		Input iChkBoxHidden2 = oFactory.createInput();
		iChkBoxHidden2.setType("hidden");
		iChkBoxHidden2.setId(features.getExternalId()+"_Feature_price_nonRecurring");

		if(features.getPrice() != null){
			if(features.getPrice().getBaseRecurringPrice() != null){
				if(features.getIncluded() != null){
					if (features.getDataConstraints().getBooleanConstraint() != null){
						td.getContent().add("");
					}else{
						td.getContent().add("Included");	
					}
					iChkBoxHidden.setValue("0.00");
					td.getContent().add(iChkBoxHidden);
				}
				else if(features.isAvailable() == true && features.getIncluded() == null){
					if (providerExtId.equals("15499381")) {
						td.getContent().add("N/A");
					}else{
						td.getContent().add(" "+"$"+format.format(features.getPrice().getBaseRecurringPrice().doubleValue()));
						iChkBoxHidden.setValue(String.valueOf(features.getPrice().getBaseRecurringPrice().doubleValue()));
						td.getContent().add(iChkBoxHidden);
					}
				}
				/*else(features.isAvailable() == false){

                    }*/
			}
			if(features.getPrice().getBaseNonRecurringPrice() != null){
				iChkBoxHidden2.setValue(String.valueOf(features.getPrice().getBaseNonRecurringPrice().doubleValue()));
				td.getContent().add(iChkBoxHidden2);
			}
		}
		else if(features.getPriceTierList() != null){
			if(features.getPriceTierList().getPriceTier() != null){
				boolean isPriceVaries = false;
				if (features.getPriceTierList().getPriceTier() != null && features.getPriceTierList().getPriceTier().size() > 1){
					isPriceVaries = true;
				}
				for(PriceTierType priceTire : features.getPriceTierList().getPriceTier()){
					if(priceTire.getPrice().getBaseRecurringPrice() == 0.0){
						td.setClazz("priceColumn");
						if (isPriceVaries){
							td.getContent().add("Varies");
						}else{
							td.getContent().add(" "+priceTire.getRangeStart()+" : "+"$0.00");
						}
						td.getContent().add(br);
						iChkBoxHidden.setValue("0.00");
						td.getContent().add(iChkBoxHidden);

					}
					else{
						td.setClazz("priceColumn");
						if (isPriceVaries){
							td.getContent().add("Varies");
						}else{
							if (features.getDataConstraints() != null && features.getDataConstraints().getIntegerConstraint() != null){
								td.getContent().add("  "+priceTire.getRangeStart()+" - "+features.getDataConstraints().getIntegerConstraint().getMaxValue()+" : "+"$"+format.format(priceTire.getPrice().getBaseRecurringPrice().doubleValue()));
							}else{
								td.getContent().add("  "+priceTire.getRangeStart()+" -  --- : "+"$"+format.format(priceTire.getPrice().getBaseRecurringPrice().doubleValue()));
							}
						}
						td.getContent().add(br);
						iChkBoxHidden.setValue("$"+format.format(priceTire.getPrice().getBaseRecurringPrice().doubleValue()));
						td.getContent().add(iChkBoxHidden);	
					}
					iChkBoxHidden2.setValue("$"+format.format(priceTire.getPrice().getBaseNonRecurringPrice().doubleValue()));
					td.getContent().add(iChkBoxHidden2);
					if (isPriceVaries){
						break;
					}
				}
			}
		}
		tr.getTd().add(td);

		//column 3

		if(features.getDataConstraints()!=null && features.getDataConstraints().getBooleanConstraint()!= null){
			if(features.getIncluded() == null && features.isAvailable() == true){
				StringBuilder priceJSON = new StringBuilder();
				td = oFactory.createTd();
				Input checkbox = oFactory.createInput();
				checkbox.setName(features.getExternalId()+"_Feature");
				checkbox.setType(InputTypeEnum.checkbox.name());
				checkbox.setId(features.getExternalId()+"");
				checkbox.setValue("true");
				if(features.getPrice() != null){
					priceJSON.append("{\"baseNonRec\":\"");
					priceJSON.append(features.getPrice().getBaseNonRecurringPrice() != null ? features.getPrice().getBaseNonRecurringPrice(): "0.00");
					priceJSON.append("\",\"baseRec\":\"");
					priceJSON.append(features.getPrice().getBaseRecurringPrice() != null ? features.getPrice().getBaseRecurringPrice(): "0.00");
					priceJSON.append("\"}");	
				}else{
					priceJSON.append("{\"baseNonRec\":\"");
					priceJSON.append("0.00");
					priceJSON.append("\",\"baseRec\":\"");
					priceJSON.append("0.00");
					priceJSON.append("\"}");	
				}
				checkbox.setItem(priceJSON.toString());
				td.getContent().add(checkbox);
				td.setStyle("width:57%");
				tr.getTd().add(td);
			}else if(features.isAvailable() == false) {
				td = oFactory.createTd();
				td.getContent().add("Included");
				td.setStyle("width:57%");
				tr.getTd().add(td);
			}else{
				td = oFactory.createTd();
				td.getContent().add("Included");
				td.setStyle("width:57%");
				tr.getTd().add(td);
			}
		}
		else if ( features.getDataConstraints()!=null && features.getDataConstraints().getIntegerConstraint() != null ) {
			if(features.getIncluded() == null && features.isAvailable() == true){
				if(features.getDataConstraints().getIntegerConstraint().getMinValue() != null && features.getDataConstraints().getIntegerConstraint().getMaxValue() != null){
					BigInteger minValue = features.getDataConstraints().getIntegerConstraint().getMinValue();
					BigInteger maxValue = features.getDataConstraints().getIntegerConstraint().getMaxValue();

					Select select = oFactory.createSelect();
					select.setClazz(DEFAULT_CSS_CLASS);
					//select.setStyle("width:200px");
					select.setId(features.getExternalId());
					select.setName(features.getExternalId()+"_Feature");

					Option baseOpt = null;
					baseOpt = oFactory.createOption();
					baseOpt.setContent("View Options");
					baseOpt.setStyle("color: black;");
					baseOpt.setValue("");

					select.getOptionOrOptgroup().add(baseOpt);

					Map<Integer, Double> priceTireMap = new TreeMap<Integer, Double>();
					Map<Integer, Double> baseNonPriceTireMap = new TreeMap<Integer, Double>();
					Map<Integer, String> priceTireMapDisplay = new TreeMap<Integer, String>();
					Map<Integer, Double> priceFinalTireMap = new TreeMap<Integer, Double>();
					Map<Integer, Double> baseNonPriceFinalTireMap = new TreeMap<Integer, Double>();
					Map<Integer, String> hiddenJSONMap = new HashMap<Integer, String>();
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

							for(int i = 0; i <= baseNonPriceFinalTireMap.size(); i++){
								String priceString = "";
								StringBuilder priceJSON = new StringBuilder();
								if (baseNonPriceFinalTireMap != null && baseNonPriceFinalTireMap.get(i) != null){
									if(baseNonPriceFinalTireMap.get(i) != 0.0){
										priceJSON.append("{\"baseNonRec\":\"");
										priceJSON.append(baseNonPriceFinalTireMap.get(i));
										priceJSON.append("\",\"baseRec\":\"");
										priceJSON.append(priceFinalTireMap.get(i));
										priceJSON.append("\"}");
										priceString = "$"+format.format(baseNonPriceFinalTireMap.get(i))+"/$"+format.format(priceFinalTireMap.get(i));

									}
									else{
										priceJSON.append("{\"baseNonRec\":\"");
										priceJSON.append("0.00");
										priceJSON.append("\",\"baseRec\":\"");
										priceJSON.append(priceFinalTireMap.get(i));
										priceJSON.append("\"}");
										priceString = "$"+format.format(priceFinalTireMap.get(i));
									}
									hiddenJSONMap.put(i, priceJSON.toString());
									priceTireMapDisplay.put(i, priceString);	
								}
							}
						}

						Option option = null;
						for(Entry<Integer, String> entry : priceTireMapDisplay.entrySet()){
							option = oFactory.createOption();
							option.setStyle("text-align: center;");
							option.setId(features.getExternalId());
							//setting content and value as number to corresponding price
							option.setContent(entry.getKey()+" - "+entry.getValue());
							option.setValue(entry.getKey()+"-"+entry.getValue());
							//Pre-Shopping cart price JSON
							if(hiddenJSONMap.get(entry.getKey()) != null && !Utils.isBlank(hiddenJSONMap.get(entry.getKey()))){
								option.setItem(hiddenJSONMap.get(entry.getKey()));								
							}
							select.getOptionOrOptgroup().add(option);
						}
					}
					else if(features.getPrice() != null){
						Double baseRecPrice = features.getPrice().getBaseRecurringPrice();
						Double baseRec = features.getPrice().getBaseRecurringPrice();
						Double baseNonRecurr = features.getPrice().getBaseNonRecurringPrice();
						Double baseNonRec = features.getPrice().getBaseNonRecurringPrice();
						Option option = null;
						int i = minValue.intValue();
						for(; i <= maxValue.intValue(); i++){
							StringBuilder priceJSON = new StringBuilder();
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
									//baseNonRecurr += baseNonRec;
									priceJSON.append("{\"baseNonRec\":\"");
									priceJSON.append(format.format(baseNonRecurr));
									priceJSON.append("\",\"baseRec\":\"");
									priceJSON.append(format.format(baseRecPrice));
									priceJSON.append("\"}");
								}
								else{
									priceJSON.append("{\"baseNonRec\":\"");
									priceJSON.append("0.00");
									priceJSON.append("\",\"baseRec\":\"");
									priceJSON.append(format.format(baseRecPrice));
									priceJSON.append("\"}");
									incrementedStr = "$"+format.format(baseRecPrice);
								}
								hiddenJSONMap.put(i, priceJSON.toString());
								priceTireMapDisplay.put(i, incrementedStr);
								baseRecPrice += baseRec;
								baseNonRecurr += baseNonRec;
							}
						}
						//setting content and value as number to corresponding price
						for(Entry<Integer, String> entry : priceTireMapDisplay.entrySet()){
							option = oFactory.createOption();
							option.setId(features.getExternalId());
							option.setStyle("text-align: center;");
							option.setContent(entry.getKey()+" - "+entry.getValue());
							option.setValue(entry.getKey()+"-"+entry.getValue());
							//Pre-Shopping cart price JSON
							if(hiddenJSONMap.get(entry.getKey()) != null && !Utils.isBlank(hiddenJSONMap.get(entry.getKey()))){
								option.setItem(hiddenJSONMap.get(entry.getKey()));								
							}
							select.getOptionOrOptgroup().add(option);
						}
					}

					//select.setName(features.getExternalId()+"_Feature");
					td = oFactory.createTd();
					td.getContent().add(select);
					td.setStyle("width:51%");
					tr.getTd().add(td);

					//if options column having drop down then the price column value is modified as Varies

					if(select.getOptionOrOptgroup().size()>2){
						if(tr.getTd()!=null && tr.getTd().size()>1){
							Td previousTd = oFactory.createTd();
							previousTd.setClazz("priceColumn");
							previousTd.getContent().add("Varies");
							previousTd.setStyle("width:18%");
							tr.getTd().set(1, previousTd);
						}
					}

				}else if (features.getDataConstraints().getIntegerConstraint().getValue() != null) {
					BigInteger intContraint = features.getDataConstraints().getIntegerConstraint().getValue();
					td = oFactory.createTd();
					td.getContent().add(String.valueOf(intContraint));
					td.setStyle("width:57%");
					tr.getTd().add(td);
				}
				else {
					td = oFactory.createTd();
					td.getContent().add("NA");
					td.setStyle("width:57%");
					tr.getTd().add(td);
				}
			}
			else if(features.getIncluded() != null ){
				if (features.getDataConstraints().getIntegerConstraint().getValue() != null) {
					BigInteger intContraint = features.getDataConstraints().getIntegerConstraint().getValue();

					td = oFactory.createTd();
					td.getContent().add(String.valueOf(intContraint));
					td.setStyle("width:57%");
					tr.getTd().add(td);
				}
				else {
					td = oFactory.createTd();
					td.getContent().add("NA");
					td.setStyle("width:57%");
					tr.getTd().add(td);
				}
			}
			else{
				td = oFactory.createTd();
				td.getContent().add("NA");
				td.setStyle("width:57%");
				tr.getTd().add(td);
			}
		}else if (features.getDataConstraints()!=null && features.getDataConstraints().getStringConstraint() != null ) {

			if(features.getIncluded() == null && features.isAvailable() == true){
				String strConstraint = features.getDataConstraints().getStringConstraint().getValue();
				td = oFactory.createTd();
				td.setStyle("width:57%");
				if(strConstraint.contains("http:")){
					A a = oFactory.createA();
					a.getContent().add("Link");
					a.setHref(strConstraint);
					a.setTarget("_blank");
					a.setStyle("color:#38ACEC");
					a.setClazz("link");
					td.getContent().add(a);
					tr.getTd().add(td);
				}
				
				else{
					td.getContent().add(String.valueOf(strConstraint));
					tr.getTd().add(td);
				}
			}
			else if(features.getIncluded() != null ){
				if (features.getDataConstraints().getStringConstraint().getValue() != null) {
					String strConstraint = features.getDataConstraints().getStringConstraint().getValue();
					td = oFactory.createTd();
					td.setStyle("width:57%");
					//this is to display empty value for price column when option column value is getting 'Included' value
					//Means String constrain value is Included
					if(strConstraint.equals("Included")){
						if(tr.getTd()!=null && tr.getTd().size()>1){
							Td previousTD = oFactory.createTd();
							previousTD.getContent().add("");
							previousTD.setStyle("width:18%");
							tr.getTd().set(1, previousTD);
						}
					}
					td.getContent().add(String.valueOf(strConstraint));
					tr.getTd().add(td);
				}
			}

			/*else{
                    td = oFactory.createTd();
                    td.getContent().add("NA");
                    tr.getTd().add(td);
              }*/
		}
		else
		{
			td = oFactory.createTd();
			td.getContent().add("NA");
			td.setStyle("width:57%");
			tr.getTd().add(td);
		}
	}

	private void addTableDataFeatureGroupField(FeatureGroupType featureGroup, Tr tr){

		DecimalFormat format = new DecimalFormat("#0.00");
		Td td = null;

		if(featureGroup.getSelectionType().getPickOne() != null){
			// Column 1 
			td = oFactory.createTd();
			td.setStyle("width:22%");
			td.getContent().add(featureGroup.getType());
			tr.getTd().add(td);

			// Column 2 
			td = oFactory.createTd();
			td.setStyle("width:18%;");
			//			Span featureSpan = oFactory.createSpan();
			if(featureGroup.getFeature() != null && !featureGroup.getFeature().isEmpty()){
				//Table featureTable = oFactory.createTable();
				Tr featureTr = oFactory.createTr();
				Td featureTd = oFactory.createTd();
				if(featureGroup.getFeature().size() > 1){
					featureTd = oFactory.createTd();
					//featureTr = oFactory.createTr();
					featureTd.getContent().add("Varies");
					featureTd.setStyle("width:18%");
					featureTr.getTd().add(featureTd);
					//featureTable.getTr().add(featureTr);
				}
				else{
					if(featureGroup.getFeature().get(0).getIncluded() != null){
						featureTd = oFactory.createTd();
						//featureTr = oFactory.createTr();
						featureTd.getContent().add("Included");
						featureTd.setStyle("width:18%");
						featureTr.getTd().add(featureTd);
						//featureTable.getTr().add(featureTr);
					}
					else if(featureGroup.getFeature().get(0).getIncluded() == null && featureGroup.getFeature().get(0).isAvailable() == true){
						featureTd = oFactory.createTd();
						//featureTr = oFactory.createTr();
						featureTd.setStyle("width:18%");
						if(featureGroup.getPrice().getBaseRecurringPrice().doubleValue()==0.0){
							featureTd.getContent().add("$0.00");
						}else{
							featureTd.getContent().add("$"+String.valueOf(featureGroup.getPrice().getBaseRecurringPrice()));
						}
						featureTr.getTd().add(featureTd);
						//featureTable.getTr().add(featureTr);
					}
					else{
						featureTd = oFactory.createTd();
						//featureTr = oFactory.createTr();
						featureTd.getContent().add("--");
						featureTd.setStyle("width:18%");
						featureTr.getTd().add(featureTd);
						//featureTable.getTr().add(featureTr);
					}
				}
				/*for(FeatureType features : featureGroup.getFeature()){
					if(features.getIncluded() != null){
						featureTd = oFactory.createTd();
						featureTr = oFactory.createTr();
						featureTd.getContent().add("Included");
						featureTr.getTd().add(featureTd);
						featureTable.getTr().add(featureTr);
					}
					else if(features.getIncluded() == null && features.isAvailable() == true){
						featureTd = oFactory.createTd();
						featureTr = oFactory.createTr();
						featureTd.getContent().add("$ "+String.valueOf(featureGroup.getPrice().getBaseRecurringPrice()));
						featureTr.getTd().add(featureTd);
						featureTable.getTr().add(featureTr);
						featureTr.getTd().add(featureTd);
						featureTable.getTr().add(featureTr);
						td.getContent().add(featureTable);
						featureTd.getContent().add();
						td.getContent().add(featureTd);
						featureTr.getTd().add(td);

//						td.getContent().add(featureSpan);
					}
				}*/
				//				featureTr.getTd().add(featureTd);
				//				featureTable.getTr().add(featureTr);
				//td.getContent().add(featureTable);
				tr.getTd().add(featureTd);
			}else{
				Td featureTd = oFactory.createTd();
				Tr featureTr = oFactory.createTr();
				featureTd = oFactory.createTd();
				//featureTr = oFactory.createTr();
				featureTd.setStyle("width:18%");
				featureTd.getContent().add("--");
				featureTr.getTd().add(featureTd);
				tr.getTd().add(featureTd);
			}

			// Column 3 
			Select select = oFactory.createSelect();
			select.setClazz(DEFAULT_CSS_CLASS);

			select.setId(featureGroup.getExternalId());
			select.setName(featureGroup.getExternalId()+"_FeatureGroup");

			Option baseOpt = null;
			baseOpt = oFactory.createOption();
			baseOpt.setContent("View Options");
			baseOpt.setValue("");
			select.getOptionOrOptgroup().add(baseOpt);
			ArrayList<Integer> arrayList = new ArrayList<Integer>();

			//Input iChkBoxHidden2 = oFactory.createInput();
			//	iChkBoxHidden2.setType("hidden");

			td = oFactory.createTd();
			for(FeatureType features : featureGroup.getFeature()){

				if(features.getIncluded() != null){
					Option option = oFactory.createOption();
					option.setContent(features.getDataConstraints().getStringConstraint().getValue()+" - "+"Included");
					option.setValue(features.getExternalId()+"-"+features.getDataConstraints().getStringConstraint().getValue());
					//option.setDisabled("disabled");
					select.getOptionOrOptgroup().add(option);
					arrayList.add(features.getDataConstraints().getStringConstraint().getValue().length());
					//iChkBoxHidden2.setId(featureGroup.getExternalId()+features.getDataConstraints().getStringConstraint().getValue()+" - "+"Included");
					//iChkBoxHidden2.setValue(format.format(features.getPrice().getBaseNonRecurringPrice()));
					//td.getContent().add(iChkBoxHidden2);
				}
				else if(features.getIncluded() == null && features.isAvailable() == true){
					Option option = oFactory.createOption();
					option.setContent(features.getDataConstraints().getStringConstraint().getValue()+" - "
							+"$"+format.format(features.getPrice().getBaseNonRecurringPrice())+" / "
							+"$"+format.format(features.getPrice().getBaseRecurringPrice()));
					//option.setValue(features.getExternalId());
					String value = features.getExternalId();
					option.setValue(value+"-"+features.getDataConstraints().getStringConstraint().getValue());
					option.setItem("{\"baseNonRec\":\""+format.format(features.getPrice().getBaseNonRecurringPrice())+"\",\"baseRec\":\""+format.format(features.getPrice().getBaseRecurringPrice())+"\"}");
					select.getOptionOrOptgroup().add(option);
					arrayList.add(features.getDataConstraints().getStringConstraint().getValue().length());
					//	iChkBoxHidden2.setId(featureGroup.getExternalId()+features.getDataConstraints().getStringConstraint().getValue()+" - "+"$"+format.format(features.getPrice().getBaseRecurringPrice()));
					//iChkBoxHidden2.setValue(format.format(features.getPrice().getBaseNonRecurringPrice()));
					//	td.getContent().add(iChkBoxHidden2);
				}
			}

			Integer lengthVal = 0;

			if (!Utils.isEmpty(arrayList)){
				lengthVal = Collections.max(arrayList);
			}

			if(lengthVal.intValue()>20){
				select.setStyle("width:230px");
			}
			//select.setName(featureGroup.getExternalId());

			td.getContent().add(select);
			td.setStyle("width:57%");
			tr.getTd().add(td);
			tr.getTd().get(1).getContent().set(0, "Varies");
		}
		else if(featureGroup.getSelectionType().getPickAll() != null){
			// Column 1 
			td = oFactory.createTd();
			td.getContent().add(featureGroup.getType());
			td.setStyle("width:22%;");
			tr.getTd().add(td);

			// Column 2 
			Td pSpan = oFactory.createTd();
			Span featureSpan = oFactory.createSpan();
			featureSpan.setStyle("background-color:#ccc;");
			if(featureGroup.getFeature() != null && !featureGroup.getFeature().isEmpty()){
				for(FeatureType features : featureGroup.getFeature()){
					if(features.getIncluded() != null){
						featureSpan.getContent().add(features.getDataConstraints().getStringConstraint().getValue()+" - "+"Included");
						pSpan.getContent().add(featureSpan);
					}
					else if(features.getIncluded() == null && features.isAvailable() == true){
						featureSpan.getContent().add(features.getDataConstraints().getStringConstraint().getValue()+" - "+"$"+format.format(features.getPrice().getBaseRecurringPrice()));
						pSpan.getContent().add(featureSpan);
					}
				}
			}
			else{
				featureSpan.getContent().add("$ "+String.valueOf(featureGroup.getPrice().getBaseRecurringPrice()));
				pSpan.getContent().add(featureSpan);
			}
			pSpan.setStyle("width:18%");
			tr.getTd().add(pSpan);


			// Column 3 
			td = oFactory.createTd();
			td.setStyle("width:57%");
			Input checkbox = oFactory.createInput();
			checkbox.setType(InputTypeEnum.checkbox.name());
			checkbox.setName(featureGroup.getExternalId()+"_FeatureGroup");
			checkbox.setId(featureGroup.getExternalId());
			checkbox.setValue(featureGroup.getExternalId()+"_ALL");
			td.getContent().add(checkbox);
			tr.getTd().add(td);
		}
	}

	// get promotion field set
	public Fieldset getCompareFieldSet(List<ProductSummaryVO> listOfProducts ,HttpServletRequest httpRequest, HashMap<String, String> selectedExistingProvidersMap) {
		String providersImageLocation = (String)httpRequest.getSession().getAttribute("providersImageLocation");
		DecimalFormat format = new DecimalFormat("#0.00");
		Fieldset fieldSet = oFactory.createFieldset();
		fieldSet.setStyle("width: 1000px;");
		boolean isUsageRateShow = false;
		Float tdWidth = 977f/(listOfProducts.size()+1);

		Table reccomparetablehead = oFactory.createTable();
		reccomparetablehead.setStyle("width: 977px;text-align:center;");

		Table reccomparetablehead1 = oFactory.createTable();
		reccomparetablehead1.setStyle("width: 977px;text-align:center;background-color:#666666; border-spacing:0;border-collapse:collapse;padding: 0;");

		Table table = oFactory.createTable();
		table.setClazz("reccomparetable");
		table.setStyle("width: 977px;");

		Tr imageTr = oFactory.createTr();

		Td td1 = oFactory.createTd();
		td1.getContent().add("");
		td1.setStyle("width:"+tdWidth.intValue()+"px;");
		imageTr.getTd().add(td1);

		Tr productNameTr = oFactory.createTr();
		Td set1 = oFactory.createTd();
		set1.getContent().add("");
		set1.setStyle("width:"+tdWidth.intValue()+"px;background-color:white;");
		productNameTr.getTd().add(set1);

		Tr highLightsTr = oFactory.createTr();
		highLightsTr.setClazz("left");
		Tr priceTr = oFactory.createTr();
		Tr promPriceTr = oFactory.createTr();
		Tr channelsTr = oFactory.createTr();
		Tr internetSpeedTr = oFactory.createTr();
		Tr modemOptionsTr = oFactory.createTr();
		Tr longDistanceTr = oFactory.createTr();
		Tr usageRateTr = oFactory.createTr();



		Th metaTh = oFactory.createTh();
		//metaTh.setStyle("width : 18%;");
		//imageTr.getTh().add(metaTh);

		Td metaName =oFactory.createTd();
		//metaName.setStyle("width : 18%;");
		//productNameTr.getTd().add(metaName);

		Td metaHighlights =oFactory.createTd();
		metaHighlights.getContent().add("Highlights");
		metaHighlights.setStyle("width:"+tdWidth.intValue()+"px;");
		//metaHighlights.setClazz("highlightsclmn");
		//metaHighlights.setStyle("width : 10%;");
		//metaHighlights.setId("metaData");
		highLightsTr.getTd().add(metaHighlights);


		Td regularPrice =oFactory.createTd();
		regularPrice.getContent().add("Regular Price");
		regularPrice.setStyle("width:"+tdWidth.intValue()+"px;");
		//regularPrice.setClazz("highlightsclmn");
		//regularPrice.setStyle("width : 10%;");
		//regularPrice.setId("metaData");
		priceTr.getTd().add(regularPrice);

		Td promotionalPrice =oFactory.createTd();
		promotionalPrice.getContent().add("Promotional Price");
		promotionalPrice.setStyle("width:"+tdWidth.intValue()+"px;");
		//promotionalPrice.setClazz("highlightsclmn");
		//promotionalPrice.setStyle("width : 10%;");
		//promotionalPrice.setId("metaData");
		promPriceTr.getTd().add(promotionalPrice);

		Td channelsTd =oFactory.createTd();
		channelsTd.getContent().add("# Channels");
		channelsTd.setStyle("width:"+tdWidth.intValue()+"px;");
		//channelsTd.setClazz("highlightsclmn");
		//channelsTd.setStyle("width : 10%;");
		//channelsTd.setId("metaData");
		channelsTr.getTd().add(channelsTd);

		Td internetSpeedTd =oFactory.createTd();
		internetSpeedTd.getContent().add("Internet Speed");
		internetSpeedTd.setStyle("width:"+tdWidth.intValue()+"px;");
		//internetSpeedTd.setClazz("highlightsclmn");
		//internetSpeedTd.setStyle("width : 10%;");
		//internetSpeedTd.setId("metaData");
		internetSpeedTr.getTd().add(internetSpeedTd);

		Td modemOptionsTd =oFactory.createTd();
		modemOptionsTd.getContent().add("Modem Options");
		modemOptionsTd.setStyle("width:"+tdWidth.intValue()+"px;");
		//modemOptionsTd.setClazz("highlightsclmn");
		//modemOptionsTd.setStyle("width : 10%;");
		//modemOptionsTd.setId("metaData");
		modemOptionsTr.getTd().add(modemOptionsTd);

		Td longDistanceTd =oFactory.createTd();
		longDistanceTd.getContent().add("Long Distance");
		longDistanceTd.setStyle("width:"+tdWidth.intValue()+"px;");
		//longDistanceTd.setClazz("highlightsclmn");
		//longDistanceTd.setStyle("width : 10%;");
		//longDistanceTd.setId("metaData");
		longDistanceTr.getTd().add(longDistanceTd);

		Td usageRateTd =oFactory.createTd();
		usageRateTd.getContent().add("Usage Rate");
		usageRateTd.setStyle("width:"+tdWidth.intValue()+"px;");
		//usageRateTd.setClazz("highlightsclmn");
		//usageRateTd.setStyle("width : 10%;");
		//usageRateTd.setId("metaData");
		usageRateTr.getTd().add(usageRateTd);

		Td e = oFactory.createTd();
		e.setColspan(new BigInteger(String.valueOf(listOfProducts.size())));
		e.setStyle("width:"+(tdWidth.intValue()*listOfProducts.size())+"px;");

		Td imageTd = oFactory.createTd();
		imageTd.setColspan(new BigInteger(String.valueOf(listOfProducts.size())));
		imageTd.setStyle("width:"+(tdWidth.intValue()*listOfProducts.size())+"px;");

		Table reccomparetablehead11 = oFactory.createTable();
		reccomparetablehead11.setClazz("reccomparetablehead1");
		reccomparetablehead11.setStyle("width:"+(tdWidth.intValue()*listOfProducts.size())+"px;");

		Table reccomparetablehead12 = oFactory.createTable();
		reccomparetablehead12.setClazz("reccomparetablehead");
		reccomparetablehead12.setStyle("width:"+(tdWidth.intValue()*listOfProducts.size())+"px;");

		Tr productNameTr1 = oFactory.createTr();
		Tr imageTr1 = oFactory.createTr();
		int noOfProductsComplted = 0;
		for(ProductSummaryVO product:listOfProducts){
			noOfProductsComplted++;
			if(noOfProductsComplted==listOfProducts.size()){
				tdWidth = (tdWidth.floatValue()*noOfProductsComplted)-(tdWidth.intValue()*(noOfProductsComplted-1));
			}else{
				tdWidth = 977f/(listOfProducts.size()+1);
			}
			// header 1      
			Th th = oFactory.createTh();
			th.setStyle("width:"+tdWidth.intValue()+"px;");
			//Creating Image for Product Icons

			Img pLogo = oFactory.createImg();
			if(product.getParentExternalId() != null){
				pLogo.setSrc(providersImageLocation+product.getParentExternalId()+".jpg");
				String errImg = providersImageLocation+product.getProviderExternalId()+".jpg";
				pLogo.setOnerror("this.src = '"+errImg+"'");
			}else{
				pLogo.setSrc(providersImageLocation+product.getProviderExternalId()+".jpg");
			}

			//pLogo.setStyle("border:none;");
			//pLogo.setClazz("providerLogosComparision");
			pLogo.setTitle1(product.getExternalId());
			pLogo.setTitle("Product External Id : " + product.getExternalId());
			pLogo.setAlt(product.getExternalId());
			th.getContent().add(pLogo);
			//th.setStyle("width :"+90/listOfProducts.size()+"%;");
			imageTr1.getTh().add(th);

			Th set = oFactory.createTh();
			set.setStyle("width:"+tdWidth.intValue()+"px;");
			set.getContent().add(StringEscapeUtils.unescapeHtml(Jsoup.parse(product.getName(), "UTF-8").text()));

			productNameTr1.getTh().add(set);

			//set.setStyle("width :"+90/listOfProducts.size()+"%;padding :4px;text-align:center;");
			//set.setClazz("head");
			Td marketingHighlights = oFactory.createTd();
			marketingHighlights.setStyle("width:"+tdWidth.intValue()+"px;");
			Fieldset ul = oFactory.createFieldset();
			if(product.getMarketingHighlightsList() != null){
				for (String marketing : product.getMarketingHighlightsList()){
					Li list = oFactory.createLi();
					if(!Utils.isBlank(marketing)){
						list.getContent() . add(StringEscapeUtils.escapeHtml(marketing));
						ul.getContent().add(list);
					}

				}
				marketingHighlights.getContent().add(ul);
			}else{
				Div div = oFactory.createDiv();
				div.setStyle("text-align:center;");
				div.getContent().add("NA");
				marketingHighlights.getContent().add(div);
			}
			//marketingHighlights.setStyle("width :"+90/listOfProducts.size()+"%");
			//marketingHighlights.setClazz("highlightshead");
			//marketingHighlights.setId("highlightsTd");
			highLightsTr.getTd().add(marketingHighlights);
			boolean isExistingCustomer = false;
			if(selectedExistingProvidersMap.containsValue(product.getProviderExternalId()))
			{
				isExistingCustomer = true;
			}
			Td priceDetails = oFactory.createTd();
			priceDetails.setStyle("width:"+tdWidth.intValue()+"px;");
			if( !isExistingCustomer )
			{
				priceDetails.getContent().add(String.valueOf((product.getBaseRecurringPrice())==null ? "NA":"$"+format.format(product.getBaseRecurringPrice())+"/month"));
			}
			else
			{
				priceDetails.getContent().add("TBD");
			}
			//priceDetails.setStyle("width :"+90/listOfProducts.size()+"%");
			//priceDetails.setId("priceDetailsTd");
			priceTr.getTd().add(priceDetails);
			Td promotionalPriceDetails = oFactory.createTd();
			promotionalPriceDetails.setStyle("width:"+tdWidth.intValue()+"px;");
			if( !isExistingCustomer )
			{
				Double promotionPrice = null;
				if (product.getPromotionType() != null && product.getPromotionPrice()!=null &&  product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount") && !product.getProviderExternalId().equals("4353598")){
					if (product.getPromotionPriceValueType() != null &&  product.getPromotionPriceValueType().equalsIgnoreCase("absolute")){
						promotionPrice  = Double.parseDouble(product.getPromotionPrice().toString());
					}else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")){
						promotionPrice = product.getBaseRecurringPrice() - product.getPromotionPrice();
					}
					promotionalPriceDetails.getContent().add(String.valueOf("$"+format.format(promotionPrice)+"/month"));
				}else { 
					promotionalPriceDetails.getContent().add(String.valueOf("NA"));
				}
			}
			else
			{
				promotionalPriceDetails.getContent().add("TBD");
			}

			//promotionalPriceDetails.setStyle("width :"+90/listOfProducts.size()+"%");
			//promotionalPriceDetails.setId("promotionalPriceDetailsTd");
			promPriceTr.getTd().add(promotionalPriceDetails);
			Td channels = oFactory.createTd();
			channels.setStyle("width:"+tdWidth.intValue()+"px;");
			channels.getContent().add(product.getNoOfChannels());
			//channels.setStyle("width :"+90/listOfProducts.size()+"%");
			//channels.setId("channelsTd");
			channelsTr.getTd().add(channels);
			Td internetSpeed = oFactory.createTd();
			internetSpeed.setStyle("width:"+tdWidth.intValue()+"px;");
			internetSpeed.getContent().add(product.getInternetSpeed());
			//internetSpeed.setStyle("width :"+90/listOfProducts.size()+"%");
			//internetSpeed.setId("internetSpeedTd");
			internetSpeedTr.getTd().add(internetSpeed);
			Td modemOptions = oFactory.createTd();
			modemOptions.setStyle("width:"+tdWidth.intValue()+"px;");
			modemOptions.getContent().add(product.getModemOptions());
			//modemOptions.setStyle("width :"+90/listOfProducts.size()+"%");
			//modemOptions.setId("modemOptionsTd");
			modemOptionsTr.getTd().add(modemOptions);
			Td longDistance = oFactory.createTd();
			longDistance.setStyle("width:"+tdWidth.intValue()+"px;");
			longDistance.getContent().add(product.getLongDistance());
			//longDistance.setStyle("width :"+90/listOfProducts.size()+"%");
			//longDistance.setId("longDistanceTd");
			longDistanceTr.getTd().add(longDistance);
			String energyRate = "NA";
			Td usageRate = oFactory.createTd();
			if(Constants.ELECTRICITY.equalsIgnoreCase(product.getProductType())
					|| Constants.NATURALGAS.equalsIgnoreCase(product.getProductType())){
				isUsageRateShow = true;
				if(product.getEnergyTierMap() != null && !product.getEnergyTierMap().isEmpty()){
					usageRate.setStyle("width:"+tdWidth.intValue()+"px;");
					if(product.getEnergyTierMap().get(Constants.ENERGY_RATE) != null){
						energyRate = String.valueOf("$"+product.getEnergyTierMap().get(Constants.ENERGY_RATE)+"/"+product.getEnergyUnitName());
					}else{
						for(Entry<String,Double> map : product.getEnergyTierMap().entrySet()){
							energyRate = String.valueOf("$"+map.getValue())+"/"+product.getEnergyUnitName();
							break;
						}
					}
				}
			}
			usageRate.getContent().add(energyRate);
			usageRateTr.getTd().add(usageRate);
		}
		reccomparetablehead12.getTr().add(imageTr1);
		imageTd.getContent().add(reccomparetablehead12);

		reccomparetablehead11.getTr().add(productNameTr1);
		e.getContent().add(reccomparetablehead11);

		productNameTr.getTd().add(e);
		imageTr.getTd().add(imageTd);

		reccomparetablehead.getTr().add(imageTr);
		//thead.getTr().add(imageTr);
		logger.info("productNameTr "+productNameTr);
		reccomparetablehead1.getTr().add(productNameTr);        
		//tbody.getTr().add(productNameTr);

		table.getTr().add(priceTr);
		table.getTr().add(promPriceTr);
		if(isUsageRateShow){
			table.getTr().add(usageRateTr);
		}else{
			table.getTr().add(channelsTr);
			table.getTr().add(internetSpeedTr);
			table.getTr().add(modemOptionsTr);
			table.getTr().add(longDistanceTr);
		}
		table.getTr().add(highLightsTr);
		table.setId("compareTable");

		Div div = oFactory.createDiv();
		div.setStyle("width: 997px;");
		div.getContent().add(reccomparetablehead);
		div.getContent().add(reccomparetablehead1);

		//Creating Image for Product Icons
		Div div1 = oFactory.createDiv();
		div1.setStyle("height: 415px; overflow-y:auto;width: 997px;");
		div1.getContent().add(table);

		fieldSet.getContent().add(div);
		fieldSet.getContent().add(div1);

		return fieldSet;
	}


	// Marketing HighLights
	private Td getMarketingInfo(ProductInfoType  product) {
		Td set = oFactory.createTd();
		if (product.getProductDetails() != null){
			if (product.getProductDetails().getMarketingHighlights() != null){
				for (String m_list : product.getProductDetails().getMarketingHighlights().getMarketingHighlight()) {
					Li list = oFactory.createLi();
					Span span = oFactory.createSpan();
					span.getContent().add(m_list);
					span.getClazz().add("productListSpan");
					list.getContent().add(span);
					set.getContent().add(list);
					set.setClazz("productList");
				}        		
			}
			if (product.getProductDetails().getPromotion() != null && product.getProductDetails().getPromotion().size() > 0){
				int i=0;
				int j=0;
				for ( ProductPromotionType promotion: product.getProductDetails().getPromotion()){
					if (promotion != null){
						if(i == 0) {
							Li list = oFactory.createLi();
							Span span = oFactory.createSpan();
							span.getContent().add(promotion.getShortDescription());
							span.getClazz().add("productListSpan");
							span.setStyle("color:#F00");
							list.getContent().add(span);
							set.getContent().add(list);
							set.setClazz("productList");
						}
					}
					if (promotion.getMetaData() != null){
						if (promotion.getMetaData().getMetaData() != null &&
								promotion.getMetaData().getMetaData().size() > 0) {
							String strMetaData = promotion.getMetaData().getMetaData().get(0);
							if(strMetaData != null && strMetaData.equals("ADVERTISE")) {
								if(j == 0) {
									Li list = oFactory.createLi();
									Span span = oFactory.createSpan();
									span.getContent().add(promotion.getShortDescription());
									span.getClazz().add("productListSpan");
									span.setStyle("color:#F00");
									list.getContent().add(span);
									set.getContent().add(list);
									set.setClazz("productList");
								}
								j++;
							}
						}
					}
					i++;
				}
			}
		}
		return set;


	}
	public Fieldset buildProductIcons(Map<String, Object> recIconMap,HttpServletRequest httpRequest, String fromWebFlowPath){
		String strContextPath = httpRequest.getContextPath();
		Fieldset fieldSet = oFactory.createFieldset();
		fieldSet.setStyle("border: 0px");
		Ul ul = oFactory.createUl();
		ul.setClazz("productsNav");
		String[] productArr = {"MIXEDBUNDLES","TRIPLE_PLAY", "DOUBLE_PLAY", "VIDEO", "INTERNET","PHONE", "HOMESECURITY","ASIS_PLAN", "ELECTRICITY","NATURALGAS", "WATER","WASTEREMOVAL","APPLIANCEPROTECTION"};

		Li pList = oFactory.createLi();
		Img pPLogo = oFactory.createImg();
		Label pLabel = oFactory.createLabel();
		A pA = oFactory.createA();

		pList.setClazz("prodcutsBtn");
		if(!Utils.isBlank(fromWebFlowPath)){
			pA.setHref(fromWebFlowPath+"&_eventId=recommendationsEvent&CategoryName=PP");
		}
		else
		{
			pA.setHref(strContextPath+"/salescenter/recommendations");
		}

		pPLogo.setHeight("42");
		pPLogo.setWidth("50");
		pPLogo.setAlt("Power Pitch");
		pPLogo.setSrc(strContextPath+"/images/images_new/nav/Star.png");
		pLabel.getContent().add("P-Pitch");
		pLabel.setFor("PP");	
		pLabel.setClazz("productLabel");
		pLabel.setId("productLabel");
		pA.getContent().add(pPLogo);
		pA.getContent().add(pLabel);
		pList.getContent().add(pA);

		ul.getContent().add(pList); 

		for(String productCtegory:productArr){
			if(recIconMap.get(productCtegory)!= null){
				if(recIconMap.get(productCtegory).equals("#")){
					Li list = oFactory.createLi();
					Img pLogo = oFactory.createImg();
					Label label = oFactory.createLabel();
					list.setClazz("prodcutsBtn prodcutsBtnDis");
					ul.getContent().add
					(getDisbledImg(pLogo,productCtegory,strContextPath,label,list));
				}else{
					Li list = oFactory.createLi();
					Img pLogo = oFactory.createImg();
					Label label = oFactory.createLabel();
					A a = oFactory.createA();
					list.setClazz("prodcutsBtn");
					String anchorSrc = (String) recIconMap.get(productCtegory);
					a.setHref(anchorSrc);
					ul.getContent().add
					(getEnabledImg(pLogo,productCtegory,strContextPath,label,a,list,fromWebFlowPath));
				}
			}
		}

		fieldSet.getContent().add(ul);

		return fieldSet;
	}

	private Li getEnabledImg(Img pLogo, String productCtegory,
			String strContextPath, Label label, A a, Li list2, String fromWebFlowPath) {

		pLogo.setHeight("42");
		pLogo.setWidth("50");

		label.setClazz("productLabel");
		label.setId("productLabel");
		if(productCtegory.equals("MIXEDBUNDLES")){
			pLogo.setAlt("Bundle$");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/mixed-bundles-icon.png");
			label.getContent().add("Bundle$");
			label.setFor("MIXEDBUNDLES");
		}else if(productCtegory.equals("TRIPLE_PLAY")){
			pLogo.setAlt("Triples");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Triples.png");
			label.getContent().add("Triples");
			label.setFor("TRIPLE_PLAY");
		}else if(productCtegory.equals("DOUBLE_PLAY")){
			pLogo.setAlt("Doubles");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Doubles.png");
			label.getContent().add("Doubles");
			label.setFor("DOUBLE_PLAY");

			a.getContent().add(pLogo);
			a.getContent().add(label);
			list2.getContent().add(a);
			list2.getContent().add(createDoublePlayCetgory(strContextPath,fromWebFlowPath));
			return list2;

		}else if(productCtegory.equals("VIDEO")){
			pLogo.setAlt("Video");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/TV.png");
			label.getContent().add("Video");
			label.setFor("VIDEO");
		}else if(productCtegory.equals("PHONE")){
			pLogo.setAlt("Phone");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Phone.png");
			label.getContent().add("Phone");
			label.setFor("PHONE");
		}else if(productCtegory.equals("INTERNET")){
			pLogo.setAlt("Internet");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Internet.png");
			label.getContent().add("Internet");
			label.setFor("INTERNET");
		}else if(productCtegory.equals("HOMESECURITY")){
			pLogo.setAlt("Security");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Security.png");
			label.getContent().add("Security");
			label.setFor("HOMESECURITY");
		}else if(productCtegory.equals("ELECTRICITY")){
			pLogo.setAlt("Electric");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Electric.png");
			label.getContent().add("Electric");
			label.setFor("ELECTRICITY");
		}else if(productCtegory.equals("WATER")){
			pLogo.setAlt("Water");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Water.png");
			label.getContent().add("Water");
			label.setFor("WATER");
		}else if(productCtegory.equals("NATURALGAS")){
			pLogo.setAlt("Gas");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Gas.png");
			label.getContent().add("Gas");
			label.setFor("NATURALGAS");
		}else if(productCtegory.equals("WASTEREMOVAL")){
			list2.setClazz("prodcutsBtn");
			pLogo.setAlt("Waste");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Waste.png");
			label.getContent().add("Waste");
			label.setFor("WASTEREMOVAL");
		}else if(productCtegory.equals("ASIS_PLAN")){
			pLogo.setAlt("Asis");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Asis.png");
			label.getContent().add("As Is");
			label.setFor("ASIS_PLAN");
		}else if(productCtegory.equals("APPLIANCEPROTECTION")){
			pLogo.setAlt("ApplianceProtection");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/ApplianceProtection.png");
			label.getContent().add("Utility");
			label.setFor("APPLIANCEPROTECTION");
		}
		a.getContent().add(pLogo);
		a.getContent().add(label);
		list2.getContent().add(a);
		return list2;
	}

	public Ul createDoublePlayCetgory(String contextPath, String fromWebFlowPath){
		Ul ul = oFactory.createUl();

		Li list1 = oFactory.createLi();
		A a1 = oFactory.createA();
		if(!Utils.isBlank(fromWebFlowPath))
		{
			a1.setHref(fromWebFlowPath+"&_eventId=recommendationsByCategoryEvent&CategoryName=DOUBLE_PLAY_VI");
		}
		else
		{
			a1.setHref(contextPath+"/salescenter/recommendationsbyCategory/DOUBLE_PLAY_VI");
		}
		a1.setId("DOUBLE_PLAY_VI");
		a1.setClazz("doubleplay_sub");
		a1.getContent().add("VIDEO-INTERNET");
		list1.getContent().add(a1);

		Li list2 = oFactory.createLi();
		A a2 = oFactory.createA();
		if(!Utils.isBlank(fromWebFlowPath))
		{
			a2.setHref(fromWebFlowPath+"&_eventId=recommendationsByCategoryEvent&CategoryName=DOUBLE_PLAY_PV");
		}
		else
		{
			a2.setHref(contextPath+"/salescenter/recommendationsbyCategory/DOUBLE_PLAY_PV");
		}
		a2.setClazz("doubleplay_sub");
		a2.setId("DOUBLE_PLAY_PV"); 
		a2.getContent().add("PHONE-VIDEO");
		list2.getContent().add(a2);
		Li list3 = oFactory.createLi();
		A a3 = oFactory.createA();
		if(!Utils.isBlank(fromWebFlowPath))
		{
			a3.setHref(fromWebFlowPath+"&_eventId=recommendationsByCategoryEvent&CategoryName=DOUBLE_PLAY_PI");
		}
		else
		{
			a3.setHref(contextPath+"/salescenter/recommendationsbyCategory/DOUBLE_PLAY_PI");
		}
		a3.setClazz("doubleplay_sub");
		a3.setId("DOUBLE_PLAY_PI");
		a3.getContent().add("PHONE-INTERNET");
		list3.getContent().add(a3);

		ul.getContent().add(list1);
		ul.getContent().add(list2);
		ul.getContent().add(list3);

		return ul;
	}
	private Li getDisbledImg(Img pLogo, String productCtegory, String strContextPath, Label label, Li list2) {

		pLogo.setHeight("42");
		pLogo.setWidth("50");
		pLogo.setStyle("border:0px");

		label.setClazz("productLabel");
		label.setId("productLabel");
		if(productCtegory.equals("MIXEDBUNDLES")){
			pLogo.setAlt("Bundle$");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/mixed-bundles-icon_d.png");
			label.getContent().add("Bundle$");
			label.setFor("Bundle$");
		}else if(productCtegory.equals("TRIPLE_PLAY")){
			pLogo.setAlt("Triples");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Triples_d.png");
			label.getContent().add("Triples");
			label.setFor("Triples");
		}else if(productCtegory.equals("DOUBLE_PLAY")){
			pLogo.setAlt("Doubles");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Doubles_d.png");
			label.getContent().add("Doubles");
			label.setFor("Doubles");
		}else if(productCtegory.equals("VIDEO")){
			pLogo.setAlt("Video");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/TV_d.png");
			label.getContent().add("Video");
			label.setFor("Video");
		}else if(productCtegory.equals("PHONE")){
			pLogo.setAlt("Phone");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Phone_d.png");
			label.getContent().add("Phone");
			label.setFor("");
		}else if(productCtegory.equals("INTERNET")){
			pLogo.setAlt("Internet");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Internet_d.png");
			label.getContent().add("Internet");
			label.setFor("Internet");
		}else if(productCtegory.equals("HOMESECURITY")){
			pLogo.setAlt("Security");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Security_d.png");
			label.getContent().add("Security");
			label.setFor("Security");
		}else if(productCtegory.equals("ELECTRICITY")){
			pLogo.setAlt("Electric");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Electric_d.png");
			label.getContent().add("Electric");
			label.setFor("Electric");
		}else if(productCtegory.equals("WATER")){
			pLogo.setAlt("Water");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Water_d.png");
			label.getContent().add("Water");
			label.setFor("Water");
		}else if(productCtegory.equals("NATURALGAS")){
			pLogo.setAlt("Gas");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Gas_d.png");
			label.getContent().add("Gas");
			label.setFor("Gas");
		}else if(productCtegory.equals("WASTEREMOVAL")){
			list2.setClazz("prodcutsBtn prodcutsBtnDis");
			pLogo.setAlt("Waste");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Waste_d.png");
			label.getContent().add("Waste");
			label.setFor("Waste");
		}else if(productCtegory.equals("ASIS_PLAN")){
			pLogo.setAlt("Asis");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/Asis_d.png");
			label.getContent().add("As Is");
			label.setFor("Asis");
		}else if(productCtegory.equals("APPLIANCEPROTECTION")){
			pLogo.setAlt("ApplianceProtection");
			pLogo.setSrc(strContextPath+"/images/images_new/nav/ApplianceProtection_d.png");
			label.getContent().add("Utility");
			label.setFor("ApplianceProtection");
		}

		list2.getContent().add(pLogo);
		list2.getContent().add(label);

		return list2;
	}
	public Fieldset getWarmTransferUI(DataGroupList dataGroupList){
		Fieldset fieldSet = oFactory.createFieldset();
		for(DataGroupType dateGroup : dataGroupList.getDataGroup()){
			Fieldset dateGroupFieldSet = oFactory.createFieldset();
			String displayName = dateGroup.getDisplayName();
			if(displayName != null){
				Div dataGroupNameDiv = oFactory.createDiv();
				dataGroupNameDiv.getContent().add(displayName);
				dataGroupNameDiv.getClazz().add("displayName");
				dateGroupFieldSet.getContent().add(dataGroupNameDiv);
			}
			DataFieldList dataFieldList = dateGroup.getDataFieldList();
			if(dataFieldList != null){
				Div dataFieldListDiv = oFactory.createDiv();
				dataFieldListDiv.getClazz().add("content");
				for(DataFieldType data: dataFieldList.getDataField()){
					Div dataFieldDiv = oFactory.createDiv();
					dataFieldDiv.getClazz().add("contentItem");
					if(isBooleanConstraint(data)){
						Span labelSpan = oFactory.createSpan();
						labelSpan.getContent().add(data.getText());
						labelSpan.getClazz().add("label");

						Span valueSpan = oFactory.createSpan();
						valueSpan.getContent().add(buildBooleanUi(data));
						valueSpan.getClazz().add("value");

						dataFieldDiv.getContent().add(labelSpan);
						dataFieldDiv.getContent().add(valueSpan);
					}else{
						Span labelSpan = oFactory.createSpan();
						labelSpan.getContent().add(data.getText());
						labelSpan.getClazz().add("label");

						dataFieldDiv.getContent().add(labelSpan);
					}
					dataFieldListDiv.getContent().add(dataFieldDiv);
				}
				dateGroupFieldSet.getContent().add(dataFieldListDiv);
			}
			fieldSet.getContent().add(dateGroupFieldSet);
		}
		return fieldSet;
	}

	private Span buildBooleanUi(DataFieldType data) {

		Span span = oFactory.createSpan();
		span.setStyle("display:inline-block");

		Span yesSpan = oFactory.createSpan();
		yesSpan.getContent().add("Yes");
		Input yesRadiobutton = oFactory.createInput();
		yesRadiobutton.setType("radio");
		yesRadiobutton.setId(data.getExternalId());
		yesRadiobutton.setValue("Y");

		Span noSpan = oFactory.createSpan();
		noSpan.getContent().add("No");
		Input noRadiobutton = oFactory.createInput();
		noRadiobutton.setType("radio");
		noRadiobutton.setId(data.getExternalId());
		noRadiobutton.setValue("N");

		span.getContent().add(yesRadiobutton);
		span.getContent().add(yesSpan);
		span.getContent().add(noRadiobutton);
		span.getContent().add(noSpan);

		return span;
	}

	private boolean isBooleanConstraint(DataFieldType data) {
		DataConstraintType  dataConstraintType= data.getDataConstraints();
		if(dataConstraintType != null){
			if(dataConstraintType.getBooleanConstraint() != null){
				return true;
			}
		}
		return false;
	}

	public List<Fieldset> buildDialogueOnDataGroup(DataGroup dGroup, Map<String, Map<String, List<String>>> enableDependencyMap) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		List<DataField> dfs = dGroup.getDataFieldList();
		Fieldset fieldset = oFactory.createFieldset();
		for(DataField df : dfs){
			Div div = oFactory.createDiv();
			div.setId(df.getExternalId()+"_FS");
			if(df.getDescription() != null && df.getDescription().equals("Coaching")){
				div.getClazz().add(DEFAULT_CSS_CLASS + " "+ COACHING + " " + CUSTOM_PADDING);
			}
			else if(df.getDescription() != null && df.getDescription().equals("Suggested")){
				div.getClazz().add(DEFAULT_CSS_CLASS + " "+ SUGGESTEDSTYLE + " " + CUSTOM_PADDING);
			}
			else
			{
				div.getClazz().add(DEFAULT_CSS_CLASS + " " + CUSTOM_PADDING);
			}
			div.getContent().add(df.getText());
			fieldset.getContent().add(div);
		}
		fieldsetList.add(fieldset);
		return fieldsetList;
	}

	public List<Fieldset> buildSelectionTypes(DataGroup dGroup, Map<String, Map<String, List<String>>> enableDependencyMap) {
		logger.info("buildSelectionTypes_begin");
		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		List<DataField> dfs = dGroup.getDataFieldList();
		Fieldset fieldset = oFactory.createFieldset();
		for(DataField df : dfs){
			Div contentDiv = oFactory.createDiv();
			InputTypeEnum inputDisplayType = getDialogueDisplayType(df);

			boolean hasDependency = false;
			if(enableDependencyMap != null) {
				hasDependency = enableDependencyMap.containsKey(df.getExternalId());
			}
			if (!df.isEnabled()) {
				contentDiv.setStyle("display:none;");
			}
			buildInputTypesForQual(contentDiv, df, inputDisplayType, hasDependency);
			fieldset.getContent().add(contentDiv);
		}
		fieldsetList.add(fieldset);
		logger.info("buildSelectionTypes_end");
		return fieldsetList;
	}

	private void buildInputTypesForQual(Div contentDiv, DataField df, InputTypeEnum inputDisplayType, boolean hasDependency) {
		logger.info("buildInputTypesForQual_begin");
		contentDiv.setId(df.getExternalId()+"_FS");
		P p = oFactory.createP();
		p.getContent().add(df.getText());
		p.getContent().add("  ");
		if(df.getDescription() != null && df.getDescription().equals("Suggested")){
			p.getClazz().add("grey");
		}
		else if(df.getDescription() != null && ( df.getDescription().equals("Coaching") || df.getDescription().equals("Hints"))){
			p.getClazz().add("green");
		}
		else{
			p.getClazz().add("black");
		}
		contentDiv.getContent().add(p);

		logger.info("Building_Qualification_page_selections");

		if(inputDisplayType == InputTypeEnum.select) 
		{
			StringConstraint stringConstraint = df.getStringConstraint();

			Span rSpan = oFactory.createSpan();
			rSpan.setStyle("display:inline-block;");

			if(stringConstraint != null) {
				List<String> valuesList = stringConstraint.getListOfValues();
				for(String value : valuesList) {

					Span label = oFactory.createSpan();

					Input input = oFactory.createInput();
					input.setType(InputTypeEnum.radio.name());
					input.setClazz(DEFAULT_CSS_CLASS+" "+RADIOMIDDLE);
					input.setName(df.getExternalId());
					input.setId("BOOL_"+value+"_" + df.getExternalId());
					input.setValue(value);

					if(hasDependency) {
						input.setOnclick("activate(\'" + input.getId() + "\',\'" + df.getExternalId() + "\')");
					}

					label.getContent().add(value);
					rSpan.getContent().add(input);
					rSpan.getContent().add(label);
				}
			}
			contentDiv.getContent().add(rSpan);

			Span red_astrick = new Span();
			red_astrick.setId("red_astrick_" + df.getExternalId());
			red_astrick.getContent().add("*");
			red_astrick.setStyle("color: red; font-size: 20px; display:none;");
			contentDiv.getContent().add(red_astrick);

		}
		else if(inputDisplayType == InputTypeEnum.text) 
		{
			Input input = oFactory.createInput();
			input.setType(inputDisplayType.name());
			input.setId(df.getExternalId());
			input.setClazz(DEFAULT_CSS_CLASS);
			input.setName(df.getExternalId());
			contentDiv.getContent().add(input);
		}
		else if(inputDisplayType == InputTypeEnum.radio) 
		{
			if(df.getBooleanConstraint() != null) {

				Span rSpan = oFactory.createSpan();
				rSpan.setStyle("display:inline-block;");

				Span labelY = oFactory.createSpan();

				Input inputY = oFactory.createInput();
				inputY.setType(InputTypeEnum.radio.name());
				inputY.setClazz(DEFAULT_CSS_CLASS+" "+RADIOMIDDLE);
				inputY.setName(df.getExternalId());
				inputY.setId("BOOL_Y_" + df.getExternalId());
				inputY.setValue("Y");
				if(hasDependency) {
					inputY.setOnclick("activate(\'" + inputY.getId() + "\',\'" + df.getExternalId() + "\')");
				}
				rSpan.getContent().add(inputY);
				labelY.getContent().add("Yes");
				rSpan.getContent().add(labelY);

				Span labelN = oFactory.createSpan();

				Input inputN = oFactory.createInput();
				inputN.setType(InputTypeEnum.radio.name());
				inputN.setId("BOOL_N_" + df.getExternalId());
				inputN.setClazz(DEFAULT_CSS_CLASS+" "+RADIOMIDDLE);
				inputN.setName(df.getExternalId());
				inputN.setValue("N");
				if(hasDependency) {
					inputN.setOnclick("activate(\'" + inputN.getId() + "\',\'" + df.getExternalId() + "\')");
				}
				rSpan.getContent().add(inputN);
				labelN.getContent().add("No");
				rSpan.getContent().add(labelN);

				contentDiv.getContent().add(rSpan);

				Span red_astrick = new Span();
				red_astrick.setId("red_astrick_" + df.getExternalId());
				red_astrick.getContent().add("*");
				red_astrick.setStyle("color: red; font-size: 20px; display:none;");
				contentDiv.getContent().add(red_astrick);
			}
		}
		logger.info("buildInputTypesForQual_end");
	}


	public String escapeSpecialCharacters(String str){
		if(str!=null){
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");

			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");

			//this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			//this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");

			//this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			//this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			//this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			//this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");

			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
		}
		return str;
	}
} 