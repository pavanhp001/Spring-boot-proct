package com.AL.ui.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.AL.html.Div;
import com.AL.html.Fieldset;
import com.AL.html.Input;
import com.AL.html.Legend;
import com.AL.html.ObjectFactory;
import com.AL.html.Option;
import com.AL.html.Select;
import com.AL.html.Span;
import com.AL.ui.builder.SalesDialogueBuilder;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.xml.di.v4.DataConstraintType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.di.v4.DataConstraintType.IntegerConstraint;
import com.AL.xml.di.v4.DataConstraintType.StringConstraint;
import com.AL.ui.dao.WarmtransferSeqDao;

public enum WarmTransferHtmlFactory {

	INSTANCE;
	
	private static final ObjectFactory oFactory = new ObjectFactory();
	private static final Logger logger = Logger.getLogger(HtmlFactory.class);

	public List<Fieldset> dialogueToFieldSet(StringBuilder events,
			List<DialogueType> dialogueListType,
			Map<String, Map<String, List<String>>> enableDependencyMap,
			List<String> allDataFieldList, Map<String, String> dataFieldMap, HttpServletRequest httpRequest) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		Fieldset mainFieldSet = null;

		mainFieldSet = oFactory.createFieldset();
		mainFieldSet.setClazz("pc_fldset");
		Legend dialogueHeader = oFactory.createLegend();

		Div dialogueContentDiv = oFactory.createDiv();
		dialogueContentDiv.getClazz().add("pc_fldset_cont");
		
		boolean showRVScript = false;
		List<String> rvExtIds = new ArrayList<String>();
		boolean isSupplierTypeDialogue = false;
		String providerId = (String) httpRequest.getSession().getAttribute("providerId");
		String rvSeqNumVal = (String) httpRequest.getSession().getAttribute("rvSeqNumVal");
		if (!Utils.isBlank(providerId) && !Utils.isBlank(rvSeqNumVal) && providerId.equals("15498481")) {
			List<String> rvWarmtransferSeqList  = Utils.isBlank(ConfigRepo.getString("*.rv_warmtransfer_seq")) ? null : Arrays.asList(ConfigRepo.getString("*.rv_warmtransfer_seq").split("\\|"));
			if(!Utils.isBlank(ConfigRepo.getString("*.rv_ext_ids"))){
				rvExtIds.addAll(Arrays.asList(ConfigRepo.getString("*.rv_ext_ids").split("\\|")));
			}
			if (rvWarmtransferSeqList != null) {
				for (String seq : rvWarmtransferSeqList) {
					if (rvSeqNumVal.endsWith(seq)) {
						showRVScript = true;
						logger.info("show_RV_script");
					}
				}
			}
		}
		for (DialogueType dialoge : dialogueListType){
			for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()){
				if(dataGroup.getDisplayName() != null){
					if(dataGroup.getDisplayName().indexOf("Supplier Selection") >= 0){
						isSupplierTypeDialogue = true;
					}
					dialogueHeader.getContent().add(dataGroup.getDisplayName());
				}
				for(DataFieldType df : dataGroup.getDataFieldList().getDataField()){
					if (df.getDataConstraints() != null) {
						DataConstraintType constraint = df.getDataConstraints();
						if (constraint.getBooleanConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "boolean");
						} else if (constraint.getStringConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "string");
						} else if (constraint.getIntegerConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "integer");
						}
					}

					allDataFieldList.add(df.getExternalId());
					Div contentDiv = oFactory.createDiv();

					if (!df.isEnabled()) {
						contentDiv.setStyle("display:none;");
					}
					String mainString = df.getExternalId();
					if(mainString.contains("/")){
						mainString = df.getExternalId().replace("/", "_");
					}

					contentDiv.setId(mainString+"_FS");

					if(df.getInfoType() != null){
						if(df.getInfoType().value().equalsIgnoreCase("DISCLOSURE") || df.getInfoType().value().equalsIgnoreCase("notable") || df.getInfoType().value().equalsIgnoreCase("informational")){
							if(df.getInfoType().value().equalsIgnoreCase("DISCLOSURE")){
								contentDiv.getClazz().add("pc_fldset_data_item_cont");	
							}
							else if(df.getInfoType().value().equalsIgnoreCase("informational")|| df.getInfoType().value().equalsIgnoreCase("notable")){
								contentDiv.getClazz().add("pc_fldset_data_item_cont_info");	
							}
							
							Div disclosureDiv = oFactory.createDiv();
							disclosureDiv.getClazz().add("label_desc");
							String dialogueLabel="";
							if (!Utils.isBlank(providerId) && providerId.equals("15498481")) {
								boolean isRVDataField = false;
								for(String str :rvExtIds){
									if(df.getName().contains(str)){
										isRVDataField = true;
									}
								}
								if (showRVScript){
									if(isRVDataField){
										dialogueLabel = df.getText();
									}
								} else {
									if(!isRVDataField){
										dialogueLabel = df.getText();
									}
								}
							} else {
								dialogueLabel = df.getText();
							}
							dialogueLabel = parseHtmlTags(escapeSpecialCharacters(dialogueLabel));
							
							//code added for the display of the styling
							
							/*if("Mandatory".equalsIgnoreCase(df.getDescription())){
								Span mandatory_span = oFactory.createSpan();
								mandatory_span.getContent().add("MANDATORY");
								mandatory_span.setStyle("color:red; font-weight:bold");
								disclosureDiv.getContent().add(mandatory_span);
								disclosureDiv.getContent().add("\u0020");
							}
							else if("Coaching".equalsIgnoreCase(df.getDescription())){
								disclosureDiv.setStyle("color:green;");
							}
							else if("Suggested".equalsIgnoreCase(df.getDescription())){
								disclosureDiv.setStyle("color: grey;");
							}*/
								
								disclosureDiv.getContent().add(dialogueLabel);
								disclosureDiv.setId(df.getExternalId());
								disclosureDiv.getClazz().add("disclosureDiv");
							    contentDiv.setId(df.getExternalId()+"_FS");
							    contentDiv.getContent().add(disclosureDiv);
							    
							  if(!Utils.isBlank(dialogueLabel)){
								  dialogueContentDiv.getContent().add(contentDiv);
							  }
							  
							if(df.getExternalId().equals("Supplier_Selection_SCFE_Data_9")){
								Div nextElementDiv = oFactory.createDiv();
								nextElementDiv.setStyle("padding-left:576px; display: none");
								nextElementDiv.setId("NextSupplierDiv");
								
								Input roundRobinButton = oFactory.createInput();
								roundRobinButton.setType("button");
								roundRobinButton.setId("NextSupplier");
								roundRobinButton.setName("NextSupplier");
								roundRobinButton.setValue("Next Supplier");
								//roundRobinButton.setStyle("display: none");
								nextElementDiv.getContent().add(roundRobinButton);
								dialogueContentDiv.getContent().add(nextElementDiv);
								
							}
						}
					}
					else{
						Div txtFieldsDiv = oFactory.createDiv();
						txtFieldsDiv.getClazz().add("pc_fldset_subcont_txtflds");

						contentDiv.getClazz().add("pc_fldset_data_item");

						Span dialogueLabelSpan = oFactory.createSpan();
						dialogueLabelSpan.getClazz().add("label");
						String dialogueLabel="";
						if(!Utils.isBlank(providerId) && providerId.equals("15498481")){
							boolean isRVDataField = false;
							for(String str :rvExtIds){
								if(df.getName().contains(str)){
									isRVDataField = true;
								}
							}
							if (showRVScript){
								if(isRVDataField){
									dialogueLabel = df.getText();
								}
							} else {
								if(!isRVDataField){
									dialogueLabel = df.getText();
								}
							}

						}else{
							  dialogueLabel = df.getText();
						  }
							dialogueLabel = parseHtmlTags(escapeSpecialCharacters(dialogueLabel));
							
								dialogueLabelSpan.getContent().add(dialogueLabel);
								dialogueLabelSpan.setId(df.getExternalId()+"_SPAN");
								dialogueLabelSpan.getClazz().add("disclosureDiv");
								contentDiv.getContent().add(dialogueLabelSpan);

						InputTypeEnum inputDisplayType = getDialogueDisplayType(df);

						boolean hasDependency = false;

						if (enableDependencyMap != null) {
							hasDependency = enableDependencyMap.containsKey(df.getExternalId());
						}

						Span dialogueValueSpan = oFactory.createSpan();
						dialogueValueSpan.getClazz().add("value");

						addDataField(dialogueValueSpan, df, inputDisplayType, hasDependency, httpRequest);
						  contentDiv.getContent().add(dialogueValueSpan);

						txtFieldsDiv.getContent().add(contentDiv);
						if(!Utils.isBlank(dialogueLabel)){
							dialogueContentDiv.getContent().add(txtFieldsDiv);
						}
					}
				}
			}
		}
		if(!isSupplierTypeDialogue){
			mainFieldSet.getContent().add(dialogueHeader);
		}
		mainFieldSet.getContent().add(dialogueContentDiv);
		fieldsetList.add(mainFieldSet);
		
		return fieldsetList;
	}

	private void addDataField(Span divFieldset, DataFieldType df,
			InputTypeEnum inputType, boolean hasDependency, HttpServletRequest httpRequest) {

		String mainString = df.getExternalId(); 
		DataConstraintType dcType = df.getDataConstraints(); 
		if (inputType == InputTypeEnum.select) {
			buildSelectTypeDialogues(df, hasDependency, divFieldset, httpRequest);
		}

		else if (inputType == InputTypeEnum.text) {
			buildTextTypeDialogues(inputType, mainString, df, divFieldset);

		} else if (inputType == InputTypeEnum.radio) {
			if (dcType.getBooleanConstraint() != null) {
				createBooleanFields(df, divFieldset, hasDependency);
			}
		}
	}

	private InputTypeEnum getDialogueDisplayType(DataFieldType df) {
		InputTypeEnum optionDisplayType = null;
		DataConstraintType dataCons = df.getDataConstraints();
		if (dataCons!=null && dataCons.getStringConstraint() != null) {
			if (dataCons.getStringConstraint().getValue() != null) {
				optionDisplayType = InputTypeEnum.text;
			} else if (dataCons.getStringConstraint().getListOfValues() != null) {
				optionDisplayType = InputTypeEnum.select;
			}
		} else if (dataCons!=null && dataCons.getBooleanConstraint() != null) {
			optionDisplayType = InputTypeEnum.radio;
		} else if (dataCons!=null &&  dataCons.getIntegerConstraint() != null) {
			if ((dataCons.getIntegerConstraint().getListOfValues() != null)&& (dataCons.getIntegerConstraint().getListOfValues().getValue().size() > 0)) {
				optionDisplayType = InputTypeEnum.select;
			} 
			else if(dataCons.getIntegerConstraint().getMinValue() != null && dataCons.getIntegerConstraint().getMaxValue() != null){
				optionDisplayType = InputTypeEnum.select;
			}
			else {
				optionDisplayType = InputTypeEnum.text;
			}
		}
		return optionDisplayType;
	}

	private static void buildSelectTypeDialogues(DataFieldType df, boolean hasDependency, Span fieldset, HttpServletRequest httpRequest){

		Select select = oFactory.createSelect();
		String string = df.getExternalId(); 
		if(string.contains("/")){
			while(string.contains("/")){
				string = string.replace("/", "_");
			}
		}
		if(string.indexOf(' ') >= 0){
			string = string.replace(" ", "");
		}
		select.setId(string);
		select.setName(df.getExternalId());
		if (hasDependency) {
			select.setOnchange("activate(\'" + df.getExternalId() + "\')");
		}

		Option option = null;
		option = oFactory.createOption();
		option.setContent(" Please Select ");
		option.setValue("");
		select.getOptionOrOptgroup().add(option);
		DataConstraintType dCons = df.getDataConstraints(); 
		if (dCons.getStringConstraint() != null) {
			
			List<String> supplierList = (List<String>) httpRequest.getAttribute("supplier_list");
			StringConstraint stringConstraint = dCons.getStringConstraint();
			List<String> valuesList = stringConstraint.getListOfValues().getValue();
			for (String value : valuesList) {
                if(value.equals("{allSuppliers}") || value.equals("\\{allSuppliers\\}")){
                	for(String suppliers : supplierList){
                		option = oFactory.createOption();
                		option.setContent(suppliers);
        				option.setValue(suppliers);
        				select.getOptionOrOptgroup().add(option);
                	}
                }else{
                	option = oFactory.createOption();
    				option.setContent(value);
    				option.setValue(value);
    				select.getOptionOrOptgroup().add(option);
                }
			}
		}
		else if (dCons.getIntegerConstraint() != null) {

			IntegerConstraint integerConstraint = dCons.getIntegerConstraint();

			if(integerConstraint.getListOfValues() != null && !integerConstraint.getListOfValues().getValue().isEmpty()){

				List<BigInteger> valuesList = integerConstraint.getListOfValues().getValue();

				for (BigInteger value : valuesList) {

					option = oFactory.createOption();

					option.setContent(String.valueOf(value));

					option.setValue(String.valueOf(value));

					select.getOptionOrOptgroup().add(option);
				}
			}
			else if(integerConstraint.getMinValue() != null && integerConstraint.getMaxValue() != null){
				int minValue = integerConstraint.getMinValue().intValue();
				int maxValue = integerConstraint.getMaxValue().intValue();
				for(int i = minValue; i <= maxValue; i++){
					option = oFactory.createOption();
					option.setContent(String.valueOf(i));
					option.setValue(String.valueOf(i));
					select.getOptionOrOptgroup().add(option);
					
				}
			}
		}
		logger.info("df.getValueTarget() TPV="+df.getValueTarget());
		if(!Utils.isBlank(df.getValueTarget()) && (df.getValueTarget()).equalsIgnoreCase("tpv.transfer")){
			String selectClass = select.getClazz()+" tpv_transfer";
			select.setClazz(selectClass);
		}
		
		/*
		 * Check whether the validation tag is 'Required' and add a class to the select object
		 */
		if(!Utils.isBlank(df.getValidation()) && "Required".equalsIgnoreCase(df.getValidation())){
			addClassForValidation(select, "select");
		}
		fieldset.getContent().add(select);
	}

	private static void buildTextTypeDialogues(InputTypeEnum inputType, String mainString, DataFieldType df, Span fieldset){

		Input input = oFactory.createInput();

		input.setType(inputType.name());
		
		mainString = df.getExternalId(); 

		if(mainString.contains("/")){
			while(mainString.contains("/")){
				mainString = mainString.replace("/", "_");
			}
		}
		logger.info("before setting validation for text fields");
		if(df.getValidation() != null && df.getValidation().equalsIgnoreCase("date")){
			mainString = mainString+"_datepicker";
			input.setMaxlength("10");
		}

		else if(df.getExternalId().equalsIgnoreCase("Supplier_Selection_PECO_Acc_Num"))
		{
			  logger.info(" PECO Security Number");
			 input.setOnchange("pecoAccountNumberValidation();");
		}

		input.setTitle(df.getText());
		input.setId(mainString);
		input.setName(df.getExternalId());
		fieldset.getContent().add(input);
	}
	private void createBooleanFields(DataFieldType df, Span fieldset, boolean hasDependency) {

		Span rSpan = oFactory.createSpan();

		rSpan.setStyle("display:inline-block;");
			
		String externalID = df.getExternalId();

		String mainString = externalID;

		if(mainString.contains("/")){
			mainString = mainString.replace("/", "_");
		}
		Span labelY = oFactory.createSpan();
		//labelY.setClazz(DEFAULT_CSS_CLASS + " " + WIDTH_70);

		Input inputY = oFactory.createInput();
		inputY.setType(InputTypeEnum.radio.name());
		inputY.setName(externalID);
		inputY.setId("BOOL_Y_" + mainString);
		inputY.setValue("Y");
		
		if (hasDependency) {
			inputY.setOnclick("activate(\'" + inputY.getId() + "\')");
		}
		
		/*
		 * Check whether the validation tag is 'Required' and add a class to the select object
		 */
		if(!Utils.isBlank(df.getValidation()) && "Required".equalsIgnoreCase(df.getValidation())){
			addClassForValidation(inputY, "radio");
		}
		
		rSpan.getContent().add(inputY);
		labelY.getContent().add("Yes");
		rSpan.getContent().add(labelY);

		Span labelN = oFactory.createSpan();
		//labelN.setClazz(DEFAULT_CSS_CLASS + " " + WIDTH_70);
	

		Input inputN = oFactory.createInput();
		inputN.setType(InputTypeEnum.radio.name());
		inputN.setId("BOOL_N_" + mainString);
		inputN.setName(externalID);
		inputN.setValue("N");
		if (hasDependency) {
			inputN.setOnclick("activate(\'" + inputN.getId() + "\')");
		}
		/*
		 * Check whether the validation tag is 'Required' and add a class to the select object
		 */
		if(!Utils.isBlank(df.getValidation()) && "Required".equalsIgnoreCase(df.getValidation())){
			addClassForValidation(inputN, "radio");
		}
		rSpan.getContent().add(inputN);
		labelN.getContent().add("No");
		rSpan.getContent().add(labelN);
		fieldset.getContent().add(rSpan);

    	if(!Utils.isBlank(df.getValidation()) && "Required".equalsIgnoreCase(df.getValidation())){
			Span red_astrick = new Span();
			red_astrick.setId("red_astrick_" + mainString);
			red_astrick.getContent().add("*");
			red_astrick.setStyle("color: red; font-size: 20px; display:none;");
			fieldset.getContent().add(red_astrick);
    	}

	}
	
	private String parseHtmlTags(String dataFieldText) {
		dataFieldText = dataFieldText.replaceAll("&lt;", "<");
		dataFieldText = dataFieldText.replaceAll("&gt;", ">");
		//dataFieldText = dataFieldText.replaceAll("<br>", "\n");
		return dataFieldText;
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
	
	public List<Fieldset> buildCloseCallDialog(StringBuilder events,
			List<DialogueType> dialogueListType,
			Map<String, Map<String, List<String>>> enableDependencyMap,
			List<String> allDataFieldList, Map<String, String> dataFieldMap, HttpServletRequest httpRequest) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		Fieldset mainFieldSet = null;

		mainFieldSet = oFactory.createFieldset();
		mainFieldSet.setClazz("pc_fldset");
		Legend dialogueHeader = oFactory.createLegend();

		Div dialogueContentDiv = oFactory.createDiv();
		dialogueContentDiv.getClazz().add("pc_fldset_cont");
		
		for (DialogueType dialoge : dialogueListType){
			for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()){
				for(DataFieldType df : dataGroup.getDataFieldList().getDataField()){
					if (df.getDataConstraints() != null) {
						DataConstraintType constraint = df.getDataConstraints();
						if (constraint.getBooleanConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "boolean");
						} else if (constraint.getStringConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "string");
						} else if (constraint.getIntegerConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "integer");
						}
					}

					allDataFieldList.add(df.getExternalId());
					Div contentDiv = oFactory.createDiv();

					if (!df.isEnabled()) {
						contentDiv.setStyle("display:none;");
					}
					String mainString = df.getExternalId();
					if(mainString.contains("/")){
						mainString = df.getExternalId().replace("/", "_");
					}

					contentDiv.setId(mainString+"_FS");

					if(df.getDescription() != null){
						if(df.getDescription().equalsIgnoreCase("Coaching") || df.getDescription().equalsIgnoreCase("Mandatory") || df.getDescription().equalsIgnoreCase("Suggested")){
							if(df.getDescription().equalsIgnoreCase("Coaching")){
								contentDiv.getClazz().add("pc_fldset_data_item_cont_coaching");
							}
							else if(df.getDescription().equalsIgnoreCase("Mandatory")){
								contentDiv.getClazz().add("pc_fldset_data_item_cont_mandatr");
							}
							else if (df.getDescription().equalsIgnoreCase("Suggested")){
								contentDiv.getClazz().add("pc_fldset_data_item_cont_suggested");
							}
							
							Div disclosureDiv = oFactory.createDiv();
							disclosureDiv.getClazz().add("label_desc");
							if("Mandatory".equalsIgnoreCase(df.getDescription())){
								
								Span mandatory_span = oFactory.createSpan();
								mandatory_span.getContent().add("MANDATORY");
								mandatory_span.setStyle("color:red; font-weight:bold");
								disclosureDiv.getContent().add(mandatory_span);
								disclosureDiv.getContent().add("\u0020");
							}

							String dialogueLabel = df.getText();
							dialogueLabel = parseHtmlTags(escapeSpecialCharacters(dialogueLabel));
							
							disclosureDiv.getContent().add(dialogueLabel);
							disclosureDiv.setId(df.getExternalId()+"_FSD");
							disclosureDiv.getClazz().add("disclosureDiv");
							contentDiv.setId(df.getExternalId()+"_FS");
							
							if(df.getDataConstraints() != null){
								Span dialogueValueSpan = oFactory.createSpan();
								//dialogueValueSpan.getClazz().add("value");
								dialogueValueSpan.getClazz().add("disclosure_value");
								InputTypeEnum inputDisplayType = getDialogueDisplayType(df);

								boolean hasDependency = false;

								if (enableDependencyMap != null) {
									hasDependency = enableDependencyMap.containsKey(df.getExternalId());
								}
								Div txtFieldsDiv = oFactory.createDiv();
								txtFieldsDiv.getClazz().add("pc_fldset_subcont_txtflds");
								addDataField(dialogueValueSpan, df, inputDisplayType, hasDependency, httpRequest);
								disclosureDiv.getContent().add(dialogueValueSpan);

								/*txtFieldsDiv.getContent().add(contentDiv);
								dialogueContentDiv.getContent().add(txtFieldsDiv);*/
							}
							contentDiv.getContent().add(disclosureDiv);
							dialogueContentDiv.getContent().add(contentDiv);
							
							if(df.getExternalId().equals("Supplier_Selection_SCFE_Data_9")){
								Div nextElementDiv = oFactory.createDiv();
								nextElementDiv.setStyle("padding-left:576px; display: none");
								nextElementDiv.setId("NextSupplierDiv");
								
								Input roundRobinButton = oFactory.createInput();
								roundRobinButton.setType("button");
								roundRobinButton.setId("NextSupplier");
								roundRobinButton.setName("NextSupplier");
								roundRobinButton.setValue("Next Supplier");
								//roundRobinButton.setStyle("display: none");
								nextElementDiv.getContent().add(roundRobinButton);
								dialogueContentDiv.getContent().add(nextElementDiv);
							}
						}
					}
					else{
						Div txtFieldsDiv = oFactory.createDiv();
						txtFieldsDiv.getClazz().add("pc_fldset_subcont_txtflds");

						contentDiv.getClazz().add("pc_fldset_data_item");

						Span dialogueLabelSpan = oFactory.createSpan();
						dialogueLabelSpan.getClazz().add("sup_label");
						String dialogueLabel = null;
						dialogueLabel = df.getText();
						if(df.getExternalId()!=null&&df.getExternalId().equals("MailingLine2type_disabled")){
							dialogueLabel="Unit Type";
						}
						if(df.getExternalId()!=null&&df.getExternalId().equals("MailingLine2Info_disabled")){
							dialogueLabel="Unit Number";
						}
						
						dialogueLabel = parseHtmlTags(escapeSpecialCharacters(dialogueLabel));

						dialogueLabelSpan.getContent().add(dialogueLabel);
						dialogueLabelSpan.setId(df.getExternalId()+"_SPAN");
						dialogueLabelSpan.getClazz().add("disclosureDiv");
						
						contentDiv.getContent().add(dialogueLabelSpan);

						InputTypeEnum inputDisplayType = getDialogueDisplayType(df);

						boolean hasDependency = false;

						if (enableDependencyMap != null) {
							hasDependency = enableDependencyMap.containsKey(df.getExternalId());
						}

						Span dialogueValueSpan = oFactory.createSpan();
						dialogueValueSpan.getClazz().add("value");

						addDataField(dialogueValueSpan, df, inputDisplayType, hasDependency, httpRequest);
						contentDiv.getContent().add(dialogueValueSpan);

						txtFieldsDiv.getContent().add(contentDiv);
						dialogueContentDiv.getContent().add(txtFieldsDiv);
					}
				}
			}
		}
		mainFieldSet.getContent().add(dialogueContentDiv);
		fieldsetList.add(mainFieldSet);
		
		return fieldsetList;
	}
	
	public List<Fieldset> buildSupplierDialog(StringBuilder events,
			List<DialogueType> dialogueListType,
			Map<String, Map<String, List<String>>> enableDependencyMap,
			List<String> allDataFieldList, Map<String, String> dataFieldMap, HttpServletRequest httpRequest) {

		List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
		Fieldset mainFieldSet = null;

		mainFieldSet = oFactory.createFieldset();
		mainFieldSet.setClazz("pc_fldset");
		
		Div dialogueContentDiv = oFactory.createDiv();
		dialogueContentDiv.getClazz().add("pc_fldset_cont");
		
		Div nextElementDiv = oFactory.createDiv();
		int increment = 0;
		TreeMap<Integer, Map<String, List<Div>>> displayOrderGroupMap = new TreeMap<Integer, Map<String, List<Div>>>();
		
		for (DialogueType dialoge : dialogueListType){
			for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()){
				for(DataFieldType df : dataGroup.getDataFieldList().getDataField()){
					
					if (df.getDataConstraints() != null) {
						DataConstraintType constraint = df.getDataConstraints();
						if (constraint.getBooleanConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "boolean");
						} else if (constraint.getStringConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "string");
						} else if (constraint.getIntegerConstraint() != null) {
							dataFieldMap.put(df.getExternalId(), "integer");
						}
					}

					allDataFieldList.add(df.getExternalId());
					Div contentDiv = oFactory.createDiv();

					if (!df.isEnabled()) {
						contentDiv.setStyle("display:none;");
					}
					String mainString = df.getExternalId();
					if(mainString.contains("/")){
						mainString = df.getExternalId().replace("/", "_");
					}

					contentDiv.setId(mainString+"_FS");

					if(df.getDescription() != null){
						if(df.getDescription().equalsIgnoreCase("Coaching") || df.getDescription().equalsIgnoreCase("Mandatory") || df.getDescription().equalsIgnoreCase("Suggested")){
								
							if( Utils.isBlank(df.getDisplayGroup()) 
								|| !Utils.isBlank(df.getDisplayGroup()) 
								&& !df.getDisplayGroup().equalsIgnoreCase("Program Info")
								&& !df.getDisplayGroup().equalsIgnoreCase("Supplier Selection FAQ") )
							{
								if(df.getDescription().equalsIgnoreCase("Coaching")){
									contentDiv.getClazz().add("displayGroupClass_Coaching");
								}
								else if(df.getDescription().equalsIgnoreCase("Mandatory")){
									contentDiv.getClazz().add("pc_fldset_data_item_cont_mandatr");
								}
								else if (df.getDescription().equalsIgnoreCase("Suggested")){
									contentDiv.getClazz().add("pc_fldset_data_item_cont_suggested");
								}
							}
							
							Div disclosureDiv = oFactory.createDiv();
							disclosureDiv.getClazz().add("label_desc");
							
							if( Utils.isBlank(df.getDisplayGroup()) 
								|| ( !Utils.isBlank(df.getDisplayGroup()) 
								&& !df.getDisplayGroup().equalsIgnoreCase("Program Info")
								&& !df.getDisplayGroup().equalsIgnoreCase("Supplier Selection FAQ")) )
							{
								if("Mandatory".equalsIgnoreCase(df.getDescription()))
								{
									Div contentDiv1 = oFactory.createDiv(); 
									contentDiv1.getContent().add("MANDATORY");
									contentDiv1.setStyle("color:red; font-weight:bold; float:left;");
									disclosureDiv.getContent().add(contentDiv1);
									disclosureDiv.getContent().add("&nbsp;");
								}
							}
							

							String dialogueLabel = df.getText();
							
							if(("Coaching").equalsIgnoreCase(df.getDescription()) && ("Supplier Selection FAQ").equalsIgnoreCase(df.getDisplayGroup())){
								dialogueLabel = "&lt;li>" +" " +dialogueLabel + "&lt;/li>";
								disclosureDiv.setStyle("padding-left: 17px;");
							}
							dialogueLabel = parseHtmlTags(escapeSpecialCharacters(dialogueLabel));
							disclosureDiv.getContent().add(dialogueLabel);
							disclosureDiv.setId(df.getExternalId()+"_FSD");
							disclosureDiv.getClazz().add("disclosureDiv");
							contentDiv.setId(df.getExternalId()+"_FS");
							
							if(df.getDataConstraints() != null){
								Span dialogueValueSpan = oFactory.createSpan();

								dialogueValueSpan.getClazz().add("disclosure_value");
								InputTypeEnum inputDisplayType = getDialogueDisplayType(df);

								boolean hasDependency = false;

								if (enableDependencyMap != null) {
									hasDependency = enableDependencyMap.containsKey(df.getExternalId());
								}
								Div txtFieldsDiv = oFactory.createDiv();
								txtFieldsDiv.getClazz().add("pc_fldset_subcont_txtflds");
								addDataField(dialogueValueSpan, df, inputDisplayType, false, httpRequest);
								disclosureDiv.getContent().add(dialogueValueSpan);
							}
							contentDiv.getContent().add(disclosureDiv);
							if(!Utils.isBlank(df.getValueTarget())){
								String mailingStyle = contentDiv.getStyle();
								if(!Utils.isBlank(mailingStyle)){
									mailingStyle+="padding-left:45px;";
									contentDiv.setStyle(mailingStyle);
								}
								else{
									contentDiv.setStyle("padding-left:45px;");
								}
							}
							if( df.getExternalId().equals("Supplier_Selection_SCFE_Data_9") || df.getExternalId().equals("Supplier_Selection_PECO_9") ){
								
								nextElementDiv.setStyle("display: none; ");
								nextElementDiv.setId("NextSupplierDiv");
								
								Input roundRobinButton = oFactory.createInput();
								roundRobinButton.setType("button");
								roundRobinButton.setId("NextSupplier");
								roundRobinButton.setName("NextSupplier");
								roundRobinButton.setValue("Next Supplier");

								nextElementDiv.getContent().add(roundRobinButton);
								//dialogueContentDiv.getContent().add(nextElementDiv);
							}
						}
					}
					else{
						Div txtFieldsDiv = oFactory.createDiv();
						txtFieldsDiv.getClazz().add("pc_fldset_subcont_txtflds");
					
						contentDiv.getClazz().add("pc_fldset_data_item");

						Span dialogueLabelSpan = oFactory.createSpan();
						dialogueLabelSpan.getClazz().add("sup_label");

						String dialogueLabel = df.getText();
						
						dialogueLabel = parseHtmlTags(escapeSpecialCharacters(dialogueLabel));

						dialogueLabelSpan.getContent().add(dialogueLabel);
						dialogueLabelSpan.setId(df.getExternalId()+"_SPAN");
						dialogueLabelSpan.getClazz().add("disclosureDiv");
						dialogueLabelSpan.getClazz().add("dialogue_label_style");
						dialogueLabelSpan.setStyle("font-size:16px; font-weight:bold;");						
						contentDiv.getContent().add(dialogueLabelSpan);
						
						InputTypeEnum inputDisplayType = getDialogueDisplayType(df);

						boolean hasDependency = false;

						if (enableDependencyMap != null) {
							hasDependency = enableDependencyMap.containsKey(df.getExternalId());
						}

						Span dialogueValueSpan = oFactory.createSpan();
						dialogueValueSpan.getClazz().add("value");

						addDataField(dialogueValueSpan, df, inputDisplayType, false, httpRequest);
						contentDiv.getContent().add(dialogueValueSpan);
						
						if(!Utils.isBlank(df.getValueTarget())){
							String mailingStyle = contentDiv.getStyle();
							if(!Utils.isBlank(mailingStyle)){
								mailingStyle+="padding-left:45px;";
								contentDiv.setStyle(mailingStyle);
							}
							else{
								contentDiv.setStyle("padding-left:45px;");
							}
						}
						
						
						txtFieldsDiv.getContent().add(contentDiv);
						//dialogueContentDiv.getContent().add(txtFieldsDiv);
					}
					
					if(!Utils.isBlank(df.getDisplayGroup()) 
						&& ( df.getDisplayGroup().equalsIgnoreCase("Program Info")
						|| df.getDisplayGroup().equalsIgnoreCase("Supplier Selection FAQ") ) )
					{
						Set<Integer> orderSet = displayOrderGroupMap.keySet();
						boolean isFound = false;
						if(!orderSet.isEmpty()){
							for (Integer key : orderSet) {
								Map<String, List<Div>> displayGroupMap = displayOrderGroupMap.get(key);
								String displayGroupDescVal = null;
								displayGroupDescVal = df.getDisplayGroup().replaceAll(" ", "_");
								if(df.getDescription() != null){
									displayGroupDescVal = df.getDisplayGroup().replaceAll(" ", "_")+"_"+df.getDescription();
								}
								
								for(Entry<String, List<Div>> entry : displayGroupMap.entrySet()){
									if(! Utils.isBlank(entry.getKey()) && entry.getKey().indexOf(df.getDisplayGroup().replaceAll(" ", "_")) >= 0){
										List<Div> displayGroupDivs = new ArrayList<Div>();
										if(!Utils.isBlank(df.getDescription()) && displayGroupMap.get(displayGroupDescVal) != null){
											displayGroupDivs = displayGroupMap.get(displayGroupDescVal);
											displayGroupDivs.add(contentDiv);
											isFound = true;
										}
										else if(displayGroupMap.get(df.getDisplayGroup().replaceAll(" ", "_")) != null){
											displayGroupDivs = displayGroupMap.get(df.getDisplayGroup().replaceAll(" ", "_"));
											displayGroupDivs.add(contentDiv);
											isFound = true;
										}
									}
								}
							}
						}
						if(!isFound){
							List<Div> displayGroupDivs = new ArrayList<Div>();
							displayGroupDivs.add(contentDiv);
							Map<String, List<Div>> displayGroupMap = new HashMap<String, List<Div>>();
							if(df.getDescription() != null){
								displayGroupMap.put(df.getDisplayGroup().replace(" ", "_")+"_"+df.getDescription(),displayGroupDivs);	
							}
							else{
								displayGroupMap.put(df.getDisplayGroup().replace(" ", "_"),displayGroupDivs);
							}
							displayOrderGroupMap.put(increment, displayGroupMap);
							increment++;
						}
					}
					else{
						if(df.getExternalId().equals("Supplier_Selection_SCFE_Data_9") || df.getExternalId().equals("Supplier_Selection_PECO_9") ){
							List<Div> displayGroupDivs = new ArrayList<Div>();
							displayGroupDivs.add(contentDiv);
							
							Map<String, List<Div>> displayGroupMap = new HashMap<String, List<Div>>();
							if(df.getDescription() != null){
								displayGroupMap.put(df.getDisplayGroup()+"_"+df.getDescription(),displayGroupDivs);	
							}
							else{
								displayGroupMap.put(df.getDisplayGroup(),displayGroupDivs);
							}
							displayOrderGroupMap.put(increment, displayGroupMap);
							increment++;
							
							displayGroupDivs = new ArrayList<Div>();
							displayGroupDivs.add(nextElementDiv);
							displayGroupMap = new HashMap<String, List<Div>>();
							if(df.getDescription() != null){
								displayGroupMap.put(df.getDisplayGroup()+"_"+df.getDescription(),displayGroupDivs);	
							}
							else{
								displayGroupMap.put(df.getDisplayGroup(),displayGroupDivs);
							}
							displayOrderGroupMap.put(increment, displayGroupMap);
							increment++;
							
						}
						else{
							List<Div> displayGroupDivs = new ArrayList<Div>();
							displayGroupDivs.add(contentDiv);
							Map<String, List<Div>> displayGroupMap = new HashMap<String, List<Div>>();
							if(df.getDescription() != null){
								displayGroupMap.put(df.getDisplayGroup()+"_"+df.getDescription(),displayGroupDivs);	
							}
							else{
								displayGroupMap.put(df.getDisplayGroup(),displayGroupDivs);
							}
							displayOrderGroupMap.put(increment, displayGroupMap);
							increment++;
						}
					}
				}
			}
		}
		
		logger.debug("displayOrderGroupMap="+displayOrderGroupMap);
		for(Integer key : displayOrderGroupMap.keySet()){
			Map<String, List<Div>> displayGroupSubMap = displayOrderGroupMap.get(key);
			Div div = oFactory.createDiv();
			
			
			for (Entry<String, List<Div>> displayGroupEntry : displayGroupSubMap.entrySet()){
				if(displayGroupEntry.getKey() != null){
					if(displayGroupEntry.getKey().endsWith("Mandatory") && displayGroupEntry.getKey().contains("Program_Info")){
						div.getClazz().add("displayGroupClass_Mandatory");
						Div nextSuppDiv = displayGroupEntry.getValue().get(0);
						if(!Utils.isBlank(nextSuppDiv.getId()) && !nextSuppDiv.getId().equalsIgnoreCase("NextSupplierDiv")){
							div.getContent().add("<div style='color:red;float:left;'>MANDATORY &nbsp;</div>");
						}
					}
					if(displayGroupEntry.getKey().endsWith("Coaching")  && displayGroupEntry.getKey().contains("Supplier_Selection_FAQ")){
						div.getClazz().add("displayGroupClass_Coaching");
					}
					if(displayGroupEntry.getKey().endsWith("Suggested")){
						div.getClazz().add("displayGroupClass_Suggested");
					}
					/*else{
						div.getClazz().add("displayGroupClass_Norm");
					}*/
				}
				int size = 0;
				if(displayGroupEntry.getValue() != null){
					size = displayGroupEntry.getValue().size();	
				}
				
				int hiddenEleCount = 0;
				for(Div divVal : displayGroupEntry.getValue()){
					if(! Utils.isBlank(divVal.getStyle()) && divVal.getStyle().indexOf("display:none;") >= 0){
						hiddenEleCount++;
					}
					div.getContent().add(divVal);
					if(divVal.getId().equals("NextSupplierDiv")){
						div.setStyle("display:none; border:none; background-color: #FFFAFA;");
					}
				}
				
				if(size == hiddenEleCount ){
					div.setStyle("display:none;");
					size = 0;
					hiddenEleCount = 0;
				}
				else{
					size = 0;
					hiddenEleCount = 0;
									}
			}
			dialogueContentDiv.getContent().add(div);
		}
		
		mainFieldSet.getContent().add(dialogueContentDiv);
		fieldsetList.add(mainFieldSet);
		return fieldsetList;
	}
	
	private static void addClassForValidation(Object object, String objectType){
		if ("select".equalsIgnoreCase(objectType)) {
			Select selectObject = (Select) object;
			String selectClass = selectObject.getClazz();
			if(!Utils.isBlank(selectClass)){
				selectClass += "_Required-select";
			}
			else {
				selectClass = "Required-select";
			}
			selectObject.setClazz(selectClass);
		}
		else if ("radio".equalsIgnoreCase(objectType)) {
			Input radioInput = (Input) object;
			String radioClass = radioInput.getClazz(); 
			if(!Utils.isBlank(radioClass)){
				radioClass += "_Required-radio";
			}
			else {
				radioClass = "Required-radio";
			}
			radioInput.setClazz(radioClass);
		}
		else if ("text".equalsIgnoreCase(objectType)) {

		}
	}
	
	public Div buildCustomDataFieldData(String dataFeildStr){
		Div contentDiv = oFactory.createDiv();
		contentDiv.setId("TPV_01_FS");
		contentDiv.getClazz().add("pc_fldset_data_item_cont");	
		Div disclosureDiv = oFactory.createDiv();
		disclosureDiv.setId("TPV_01");
		disclosureDiv.getClazz().add("label_desc");
		disclosureDiv.getClazz().add("disclosureDiv");
		disclosureDiv.getContent().add(customizeTPVDialouge(parseHtmlTags(escapeSpecialCharacters(dataFeildStr))));
		contentDiv.getContent().add(disclosureDiv);
		return contentDiv;
	}
	
	private String customizeTPVDialouge(String tpvScript){
        String splitTpvSript[] = tpvScript.split("\\r?\\n");
        StringBuilder tpvScriptHtml =  new StringBuilder();
        for (String str : splitTpvSript) {
            if (!str.contains("TPV Phone Number")) {
                if (str.contains("This order requires E911 TPV")) {
                    tpvScriptHtml.append(str);
                    tpvScriptHtml.append("</br>");
                } else if (str.contains("TPV ID")) {
                    tpvScriptHtml.append(str);
                    tpvScriptHtml.append("</br>");
                } else {
                    tpvScriptHtml.append(str);
                    tpvScriptHtml.append(" ");
                }
            }
        }
        return tpvScriptHtml.toString();
    }
}
