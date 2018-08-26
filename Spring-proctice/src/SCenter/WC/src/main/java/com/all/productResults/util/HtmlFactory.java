package com.A.productResults.util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.A.html.Br;
import com.A.html.Fieldset;
import com.A.html.Inline;
import com.A.html.Input;
import com.A.html.Label;
import com.A.html.Legend;
import com.A.html.ObjectFactory;
import com.A.html.Option;
import com.A.html.Select;
import com.A.html.Span;
import com.A.html.Table;
import com.A.html.Tbody;
import com.A.html.Td;
import com.A.html.Th;
import com.A.html.Thead;
import com.A.html.Tr;

//import com.A.ui.sort.OptionsSort;
//import com.A.ui.util.NumberToWords;
import com.A.xml.pr.v4.ChoiceType;
import com.A.xml.pr.v4.CustomizationType;
import com.A.xml.pr.v4.FeatureGroupType;
import com.A.xml.pr.v4.FeatureType;
import com.A.xml.pr.v4.OptionsType;
import com.A.xml.pr.v4.PriceTierType;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductPromotionType;
import com.A.xml.v4.LineItemSelectionType;
import com.A.xml.v4.LineItemSelectionsType;
import com.A.xml.v4.SelectionChoiceType;

import java.util.Collections;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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

	private Inline createBooleanFields(String groupName, Inline p, boolean hasDependency) {
		
		//Br br = oFactory.createBr();
		//fieldset.getContent().add(br);
		//P pgraph = oFactory.createP();
		Span rSpan = oFactory.createSpan();
		rSpan.setStyle("display:inline-block;");
		
		Span labelY = oFactory.createSpan();
		//labelY.setClazz(DEFAULT_CSS_CLASS);
		labelY.getContent().add("Yes");
		rSpan.getContent().add(labelY);
		
		Input inputY = oFactory.createInput();
		inputY.setType(InputTypeEnum.radio.name());
		inputY.setClazz("styled radioMiddle");
		inputY.setName(groupName);
		inputY.setId("BOOL_Y_" + groupName);
		inputY.setValue("Y");
		if(hasDependency) {
			inputY.setOnclick("activate(\'" + inputY.getId() + "\')");
		}
		rSpan.getContent().add(inputY);
		
		Span labelN = oFactory.createSpan();
		//labelN.setClazz(DEFAULT_CSS_CLASS);
		labelN.getContent().add("No");
		rSpan.getContent().add(labelN);
		
		Input inputN = oFactory.createInput();
		inputN.setType(InputTypeEnum.radio.name());
		inputN.setId("BOOL_N_" + groupName);
		inputN.setClazz("styled radioMiddle");
		inputN.setName(groupName);
		inputN.setValue("N");
		if(hasDependency) {
			inputN.setOnclick("activate(\'" + inputN.getId() + "\')");
		}
		rSpan.getContent().add(inputN);
		
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
	}
	
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
	}*/
	

	// get promotion field set
	public Fieldset getPromotionsFieldSet(List<ProductPromotionType> promotions) {

     Fieldset fieldSet = oFactory.createFieldset();
     if(!promotions.isEmpty()){
        Table table = oFactory.createTable();
        table.setClazz("tblPromotions");
        table.setId("tblPromotions");
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

       //fieldSet = oFactory.createFieldset();
        for (ProductPromotionType promotion : promotions) {
            tr = oFactory.createTr();
            addTableDataPromotionsField(promotion,tr);
            tbody.getTr().add(tr);
        }
        table.setTbody(tbody);
        fieldSet.getContent().add(table);
     }
        return fieldSet;
    }
	
    private void addTableDataPromotionsField(ProductPromotionType feature, Tr tr) {
        Td td = oFactory.createTd();

        String data = feature.getShortDescription();
        logger.info("promotion_field_length"+data.length());
        td.getContent().add(feature.getShortDescription());
        tr.getTd().add(td);

        td = oFactory.createTd();
        td.setStyle("width: 150px;text-align: center;");
        
        Input input = oFactory.createInput();
        input.setType(InputTypeEnum.radio.name());
        input.setName("productPromotions");
        input.setId(feature.getShortDescription());
        input.setValue(feature.getExternalId());
        
        td.getContent().add(input);
        tr.getTd().add(td);
    }
    
	//added table for product details
	public Fieldset getFeatureFieldSet(List<FeatureType> features) {

        List<FeatureType> fets = Utils.sort(features);

        Fieldset fieldSet = oFactory.createFieldset();

        Table table = oFactory.createTable();
        table.setClazz("tblFeatures");
        table.setId("tblFeatures");
        Thead thead = oFactory.createThead();
        Tr tr = oFactory.createTr();

        Th th = oFactory.createTh();
        // header 1      
        th = oFactory.createTh();
        //th.setStyle("width: 155px");
        th.getContent().add("Feature Type");
        tr.getTh().add(th);
        // header 2     
        th = oFactory.createTh();
        th.getContent().add("Price");
        tr.getTh().add(th);
        //header 3
        th = oFactory.createTh();
        th.getContent().add("Selection Type");
        tr.getTh().add(th);
        
        thead.getTr().add(tr);
        table.setThead(thead);
        Tbody tbody = oFactory.createTbody();

        int i =0;
        //fieldSet = oFactory.createFieldset();
         for (FeatureType feature : fets) {
        	 i++;
        	 logger.debug("isAvailable="+feature.isAvailable());
        	 logger.debug("getIncluded="+feature.getIncluded());
        	 if(feature.isAvailable() == true){
             tr = oFactory.createTr();
             addTableDataFeaturesField(feature,tr);
             tbody.getTr().add(tr);
        	 }
         }
         table.setTbody(tbody);
        
        table.setThead(thead);
        fieldSet.getContent().add(table);
        return fieldSet;
    }

	//this is for getFeatureFieldSet method with two parameters
	public Fieldset getFeatureFieldSet(List<FeatureType> features, List<FeatureGroupType> featureGroupList) {

	   	List<FeatureType> fets = Utils.sort(features);
        Fieldset fieldSet = oFactory.createFieldset();

        Table table = oFactory.createTable();
        table.setClazz("tblFeatures");
        table.setId("tblFeatures");
        Thead thead = oFactory.createThead();
        Tr tr = oFactory.createTr();

        Th th = oFactory.createTh();
        // header 1      
        th = oFactory.createTh();
        //th.setStyle("width: 155px");
        th.getContent().add("Feature Type");
        tr.getTh().add(th);
        // header 2     
        th = oFactory.createTh();
        th.getContent().add("Price");
        tr.getTh().add(th);
        //header 3
        th = oFactory.createTh();
        th.getContent().add("Selection Type");
        tr.getTh().add(th);
        thead.getTr().add(tr);
        
        table.setThead(thead);
        Tbody tbody = oFactory.createTbody();
        
        // fieldSet = oFactory.createFieldset();
        for (FeatureType feature : fets) {
            
            
            if(feature.isAvailable() == false){
//            	addTableDataFeaturesField(feature,tr);	
            }else {
            	tr = oFactory.createTr();
            	addTableDataFeaturesField(feature,tr);
            	tbody.getTr().add(tr);
            }
        }
        if(featureGroupList != null){
          for (FeatureGroupType featureGroup : featureGroupList) {
        	  tr = oFactory.createTr();
        	  addTableDataFeatureGroupField(featureGroup, tr);
        	  tbody.getTr().add(tr);
          }
        }
    
        table.setTbody(tbody);
        fieldSet.getContent().add(table);
        return fieldSet;
  }
	
    private void addTableDataFeaturesField(FeatureType features, Tr tr) {

        Td td = oFactory.createTd();
        DecimalFormat format = new DecimalFormat("#0.00");
        td.getContent().add(features.getType());
        td.setClazz("labelColumn");
        Br br = oFactory.createBr();
        tr.getTd().add(td);

        td = oFactory.createTd();
        
        Input iChkBoxHidden = oFactory.createInput();
		iChkBoxHidden.setType("hidden");
		iChkBoxHidden.setId(features.getExternalId()+"_Feature_price");

        if(features.getPrice() != null){
              if(features.getPrice().getBaseRecurringPrice() != null){
                    if(features.getIncluded() != null){
                          td.getContent().add("INCLUDED");
                          iChkBoxHidden.setValue("0.00");
                  		  td.getContent().add(iChkBoxHidden);
                    }
                   else if(features.isAvailable() == true && features.getIncluded() == null){
                        td.getContent().add(" "+"$"+format.format(features.getPrice().getBaseRecurringPrice().doubleValue()));
                        iChkBoxHidden.setValue(String.valueOf(features.getPrice().getBaseRecurringPrice().doubleValue()));
                		  td.getContent().add(iChkBoxHidden);
                    }
                    /*else(features.isAvailable() == false){
                    
                    }*/
                    
              }
        }
        else if(features.getPriceTierList() != null){
              if(features.getPriceTierList().getPriceTier() != null){
                    for(PriceTierType priceTire : features.getPriceTierList().getPriceTier()){
                          if(priceTire.getPrice().getBaseRecurringPrice() == 0.0){
                        	  	td.setClazz("priceColumn");
                                td.getContent().add(" "+priceTire.getRangeStart()+" : "+"$0.00");
                                td.getContent().add(br);
                                iChkBoxHidden.setValue("0.00");
                        		  td.getContent().add(iChkBoxHidden);

                          }
                          else{
                        	  td.setClazz("priceColumn");
                                td.getContent().add("  "+priceTire.getRangeStart()+" - "+features.getDataConstraints().getIntegerConstraint().getMaxValue()+" : "+"$"+format.format(priceTire.getPrice().getBaseRecurringPrice().doubleValue()));
                                td.getContent().add(br);
                                iChkBoxHidden.setValue("$"+format.format(priceTire.getPrice().getBaseRecurringPrice().doubleValue()));
                                td.getContent().add(iChkBoxHidden);
                          }
                    }
              }
        }
        tr.getTd().add(td);

        td = oFactory.createTd();
        if(features.getDataConstraints().getBooleanConstraint()!= null){
              if(features.getIncluded() == null && features.isAvailable() == true){

                    Input checkbox = oFactory.createInput();
                    checkbox.setName(features.getExternalId()+"_Feature");
                    checkbox.setType(InputTypeEnum.checkbox.name());
                    checkbox.setId(features.getExternalId()+"");
                    checkbox.setValue("true");


                    td.getContent().add(checkbox);
                    tr.getTd().add(td);
              }
              else if(features.isAvailable() == false) {
                    
              }
              else{
            	  td = oFactory.createTd();
                  td.getContent().add("NA");
                  tr.getTd().add(td);
              }
        }
        else if (features.getDataConstraints().getIntegerConstraint() != null ) {
              if(features.getIncluded() == null && features.isAvailable() == true){
                    if(features.getDataConstraints().getIntegerConstraint().getMinValue() != null || features.getDataConstraints().getIntegerConstraint().getMaxValue() != null){
                          BigInteger minValue = features.getDataConstraints().getIntegerConstraint().getMinValue();
                          BigInteger maxValue = features.getDataConstraints().getIntegerConstraint().getMaxValue();

                          Select select = oFactory.createSelect();
                          select.setClazz(DEFAULT_CSS_CLASS);

                          select.setId(features.getExternalId());
                          select.setName(features.getExternalId()+"_Feature");

                          Option baseOpt = null;
                          baseOpt = oFactory.createOption();
                          baseOpt.setContent("Please Select");
                          baseOpt.setStyle("color: black;");
                          baseOpt.setValue("");
                          select.getOptionOrOptgroup().add(baseOpt);

                          Map<Integer, Double> priceTireMap = new TreeMap<Integer, Double>();
                          if(features.getPriceTierList() != null){
                                for(PriceTierType priceTire : features.getPriceTierList().getPriceTier()){
                                      priceTireMap.put(priceTire.getRangeStart(), priceTire.getPrice().getBaseRecurringPrice());
                                }
                                int maxRange = Collections.max(priceTireMap.keySet());
                                if(maxValue.intValue()>(maxRange)){
                                      Double baseRecPrice = priceTireMap.get(maxRange);
                                      Double baseRec = priceTireMap.get(maxRange);
                                      for(int i=maxRange; i <= maxValue.intValue(); i++){
                                            priceTireMap.put(i, baseRecPrice);
                                            baseRecPrice += baseRec;
                                            baseRecPrice = Double.valueOf(format.format(baseRecPrice));
                                      }
                                }
                                Option option = null;
                                for(Entry<Integer, Double> entry : priceTireMap.entrySet()){
                                      option = oFactory.createOption();
                                      option.setId(features.getExternalId());
                                      option.setContent(entry.getKey()+" -  "+"$"+format.format(entry.getValue().doubleValue()));
                                      option.setValue(String.valueOf(entry.getKey()));
                                      select.getOptionOrOptgroup().add(option);
                                }
                                //select.setOnchange("increaseMonthlyAmnt(this.id"+option.getValue()+")");
                          }
                          else if(features.getPrice() != null){
                                Double baseRecPrice = features.getPrice().getBaseRecurringPrice();
                                Double baseRec = features.getPrice().getBaseRecurringPrice();
                                Option option = null;
                                for(int i = (minValue.intValue()+1); i <= maxValue.intValue(); i++){

                                      priceTireMap.put(i, baseRecPrice);
                                      baseRecPrice += baseRec;
                                      baseRecPrice = Double.valueOf(format.format(baseRecPrice));
                                }
                                for(Entry<Integer, Double> entry : priceTireMap.entrySet()){
                                      option = oFactory.createOption();
                                      option.setId(features.getExternalId());
                                      option.setContent(entry.getKey()+" -  "+"$"+format.format(entry.getValue()));
                                      option.setValue(String.valueOf(entry.getKey()));
                                      select.getOptionOrOptgroup().add(option);
                                }
                                //  select.setOnchange("increaseMonthlyAmnt("+option.getId()+")");
                          }
                         //select.setName(features.getExternalId()+"_Feature");
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
              else if(features.getIncluded() != null ){
                    if (features.getDataConstraints().getIntegerConstraint().getValue() != null) {
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
              else{
                    td = oFactory.createTd();
                    td.getContent().add("NA");
                    tr.getTd().add(td);
              }
        }else if (features.getDataConstraints().getStringConstraint() != null ) {
              if(features.getIncluded() == null && features.isAvailable() == true){
                    String strConstraint = features.getDataConstraints().getStringConstraint().getValue();
                    td = oFactory.createTd();
                    td.getContent().add(String.valueOf(strConstraint));
                    tr.getTd().add(td);
              }
              else if(features.getIncluded() != null ){
                    if (features.getDataConstraints().getStringConstraint().getValue() != null) {
                          String strConstraint = features.getDataConstraints().getStringConstraint().getValue();
                          td = oFactory.createTd();
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
    }

    private void addTableDataFeatureGroupField(FeatureGroupType featureGroup, Tr tr){

    	DecimalFormat format = new DecimalFormat("#0.00");
		Td td = null;

		if(featureGroup.getSelectionType().getPickOne() != null){
			// Column 1 
			td = oFactory.createTd();
			td.getContent().add(featureGroup.getType());
			tr.getTd().add(td);

			// Column 2 
			td = oFactory.createTd();
//			Span featureSpan = oFactory.createSpan();
			if(featureGroup.getFeature() != null && !featureGroup.getFeature().isEmpty()){
				Table featureTable = oFactory.createTable();
				Tr featureTr = oFactory.createTr();
				Td featureTd = oFactory.createTd();
				for(FeatureType features : featureGroup.getFeature()){
					if(features.getIncluded() != null){
						featureTd = oFactory.createTd();
						featureTr = oFactory.createTr();
						featureTd.getContent().add("INCLUDED");
						featureTr.getTd().add(featureTd);
						featureTable.getTr().add(featureTr);
					}
					else if(features.getIncluded() == null && features.isAvailable() == true){
						featureTd = oFactory.createTd();
						featureTr = oFactory.createTr();
						featureTd.getContent().add("$ "+String.valueOf(featureGroup.getPrice().getBaseRecurringPrice()));
						featureTr.getTd().add(featureTd);
						featureTable.getTr().add(featureTr);
/*						featureTr.getTd().add(featureTd);
						featureTable.getTr().add(featureTr);
						td.getContent().add(featureTable);*/
/*						featureTd.getContent().add();
						td.getContent().add(featureTd);
						featureTr.getTd().add(td);*/
						
//						td.getContent().add(featureSpan);
					}
				}
//				featureTr.getTd().add(featureTd);
//				featureTable.getTr().add(featureTr);
				td.getContent().add(featureTable);
				tr.getTd().add(td);
			}
			
			

			// Column 3 
			Select select = oFactory.createSelect();
			select.setClazz(DEFAULT_CSS_CLASS);
			select.setId(featureGroup.getExternalId());
			select.setName(featureGroup.getExternalId()+"_FeatureGroup");

			Option baseOpt = null;
			baseOpt = oFactory.createOption();
			baseOpt.setContent("Please Select");
			baseOpt.setValue("");
			select.getOptionOrOptgroup().add(baseOpt);

			for(FeatureType features : featureGroup.getFeature()){
				if(features.getIncluded() != null){
					Option option = oFactory.createOption();
					option.setContent(features.getDataConstraints().getStringConstraint().getValue()+" - "+"INCLUDED");
					option.setValue(features.getExternalId()+"-"+features.getDataConstraints().getStringConstraint().getValue());
					//option.setDisabled("disabled");
					select.getOptionOrOptgroup().add(option);
				}
				else if(features.getIncluded() == null && features.isAvailable() == true){
					Option option = oFactory.createOption();
					option.setContent(features.getDataConstraints().getStringConstraint().getValue()+" - "+"$"+format.format(features.getPrice().getBaseRecurringPrice()));
					//option.setValue(features.getExternalId());
					String value = features.getExternalId();
					option.setValue(value+"-"+features.getDataConstraints().getStringConstraint().getValue());
					select.getOptionOrOptgroup().add(option);
				}
			}
			//select.setName(featureGroup.getExternalId());
			td = oFactory.createTd();
			td.getContent().add(select);
			tr.getTd().add(td);
		}
		else if(featureGroup.getSelectionType().getPickAll() != null){
			// Column 1 
			td = oFactory.createTd();
			td.getContent().add(featureGroup.getType());
			tr.getTd().add(td);

			// Column 2 
			Td pSpan = oFactory.createTd();

			Span featureSpan = oFactory.createSpan();
			featureSpan.setStyle("background-color:#ccc;");
			if(featureGroup.getFeature() != null && !featureGroup.getFeature().isEmpty()){
				for(FeatureType features : featureGroup.getFeature()){
					if(features.getIncluded() != null){
						featureSpan.getContent().add(features.getDataConstraints().getStringConstraint().getValue()+" - "+"INCLUDED");
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
			tr.getTd().add(pSpan);

			// Column 3 
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
}
