package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import com.AL.ui.DataDialogueVO;
import com.AL.ui.DataGroupVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.DataFeildFeatureVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.PriceTierVO;
import com.AL.xml.di.v4.DataFieldDependencyType;
import com.AL.xml.di.v4.DataFieldRefType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DependentDataFieldsType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.PriceTierType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

import edu.emory.mathcs.backport.java.util.Collections;

public enum DialogueDataFeildFactory {
	INSTANCE;

	private static final Logger logger = Logger.getLogger(DialogueDataFeildFactory.class);

	/**
	 * @param dataGroupType
	 * @return
	 * @throws Exception
	 */
	public List<DataFeildFeatureVO> buildDataFeildData(DataGroupType dataGroupType,String providerName,OrderQualVO orderQualVO,String... serviceAddress) throws Exception {
		List<DataFeildFeatureVO> allDataFeildList = new ArrayList<DataFeildFeatureVO>();
		try{
			if( dataGroupType.getDataFieldList() != null && dataGroupType.getDataFieldList().getDataField() != null ){
				for(DataFieldType dataFieldType:dataGroupType.getDataFieldList().getDataField()){
					DataFeildFeatureVO dataFeildFeatureVO =  new DataFeildFeatureVO();
					List<String> constrainList = new ArrayList<String>();
					if(dataFieldType.getDataConstraints()!= null && dataFieldType.getDataConstraints().getBooleanConstraint() != null){
						dataFeildFeatureVO.setDataConstraint(Constants.BOOLEAN_DATA_CONSTRAIN);
						dataFeildFeatureVO.setDispalyInput(Constants.CHECKBOX);
						if(dataFieldType.getDataConstraints().getBooleanConstraint().getValue() != null){
							constrainList.add(dataFieldType.getDataConstraints().getBooleanConstraint().getValue());
						}

					}else if(dataFieldType.getDataConstraints()!= null && dataFieldType.getDataConstraints().getStringConstraint() != null){
						dataFeildFeatureVO.setDataConstraint(Constants.STRING_DATA_CONSTRAIN);
						if(dataFieldType.getDataConstraints().getStringConstraint().getListOfValues() != null){
							constrainList.addAll(dataFieldType.getDataConstraints().getStringConstraint().getListOfValues().getValue());
							if(dataFieldType.getDataConstraints().getStringConstraint().getListOfValues().getValue().contains("NA")){
								dataFeildFeatureVO.setDispalyInput(Constants.TEXT);
							}else{
								dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);
							}
						}else if(dataFieldType.getDataConstraints().getStringConstraint().getValue() != null){
							constrainList.add(dataFieldType.getDataConstraints().getStringConstraint().getValue());
							dataFeildFeatureVO.setDispalyInput(Constants.TEXT);
						}
					}else if(dataFieldType.getDataConstraints()!= null && dataFieldType.getDataConstraints().getIntegerConstraint() != null){
						dataFeildFeatureVO.setDataConstraint(Constants.INTEGER_DATA_CONSTRAIN);
						dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);
						if(dataFieldType.getDataConstraints().getStringConstraint().getValue() != null){
							constrainList.add(String.valueOf(dataFieldType.getDataConstraints().getIntegerConstraint().getValue()));
						}
					}else{
						dataFeildFeatureVO.setDataConstraint(Constants.TEXT);
						dataFeildFeatureVO.setDispalyInput(Constants.TEXT);
					}
					if(dataFieldType.getFeatureExternalId() != null){
						dataFeildFeatureVO.setFeatureExID(dataFieldType.getFeatureExternalId());
					}
					if(dataFieldType.getInfoType() != null
							&& !Utils.isBlank(dataFieldType.getInfoType().value())){
						dataFeildFeatureVO.setDispalyInput(dataFieldType.getInfoType().value());
						dataFeildFeatureVO.setType(dataFieldType.getInfoType().value());
					}
					if(dataFieldType.getValidation() != null){
						dataFeildFeatureVO.setValidation(dataFieldType.getValidation());
					}
					if(dataFieldType.getValueTarget() != null){
						dataFeildFeatureVO.setValueTarget(dataFieldType.getValueTarget());
					}
					dataFeildFeatureVO.setType(Constants.DIALOGUE);
					dataFeildFeatureVO.setDataConstraintValueList(constrainList);
					dataFeildFeatureVO.setDataFieldExID(dataFieldType.getExternalId());
					if(dataFieldType.getText().contains("{businessParty.name}")){
						String text = dataFieldType.getText().replaceAll("\\{businessParty.name\\}", providerName);
						dataFeildFeatureVO.setDataFieldText(text);
					}else{
						dataFeildFeatureVO.setDataFieldText(Jsoup.parse(dataFieldType.getText(), "UTF-8").text());
					}
					if(serviceAddress!=null && serviceAddress.length>0 && serviceAddress[0]!=null){
					if(dataFieldType.getText().trim().equalsIgnoreCase("ServiceAddress")){
						logger.info("serviceAddress From Dialogue"+dataFieldType.getText());
						dataFeildFeatureVO.setDataFieldText(serviceAddress[0]);
						//dataFeildFeatureVO.setDispalyInput(dataFieldType.getInfoType().value());
						logger.info("serviceAddress after change"+dataFieldType.getText());
					}
					}
					dataFeildFeatureVO.setDataFieldName(dataFieldType.getName());
					dataFeildFeatureVO.setEnabled(dataFieldType.isEnabled());
					setPreviousSelectedActiveDialogueType(dataFeildFeatureVO,orderQualVO);
					allDataFeildList.add(dataFeildFeatureVO);
				}
			}
		}catch(Exception e){
			logger.error("error_occured_while_buildDataFeildData",e);
			throw new Exception(e.getMessage());
		}

		return allDataFeildList;
	}


	/**This method is build dialogue type object based on dialogue service response
	 * @param dialogueType
	 * @param featurList
	 * @param featurGroupList
	 * @param titleName
	 * @param qualificationDataFieldMatrixMap
	 * @return
	 * @throws Exception
	 */
	public DataDialogueVO buildFeaturDialogueVO(DialogueType dialogueType,List<FeatureType> featurList, List<FeatureGroupType> featurGroupList, String titleName,Map<String,Map<String,List<DataFieldRefType>>> qualificationDataFieldMatrixMap,OrderQualVO orderQualVO){
		logger.info("start_buildFeaturDialogueVO_"+titleName);
		List<DataGroupVO> dataGroupVOList = new ArrayList<DataGroupVO>();
		DataDialogueVO dialogueVO = new DataDialogueVO();
		if(dialogueType.getDataGroupList()!= null && dialogueType.getDataGroupList().getDataGroup() != null){
			for(DataGroupType dataGroupType:dialogueType.getDataGroupList().getDataGroup()){
				List<DataFeildFeatureVO> featureVOList = buildFeatureDataFeildData(dataGroupType,featurList,featurGroupList,titleName,orderQualVO);
				if(!featureVOList.isEmpty()){
					DataGroupVO dataGroupVO = new DataGroupVO();
					dataGroupVO.setDataFeildList(featureVOList);
					dataGroupVO.setSubTitle(dataGroupType.getDisplayName());
					dataGroupVOList.add(dataGroupVO);
					Map<String,Map<String,List<DataFieldRefType>>>  allDataMap = buildEnableAndDisableDataGroup(dataGroupType);
					qualificationDataFieldMatrixMap.putAll(allDataMap);
				}
			}	
		}
		dialogueVO.setDataGroupList(dataGroupVOList);
		dialogueVO.setTitle(titleName);
		logger.info("end_buildFeaturDialogueVO_"+titleName);
		return dialogueVO;
	}

	/** This method is used to build enable and disable dataGroup based on dialogue service response
	 * @param dataGroupType
	 * @return
	 */
	public Map<String,Map<String,List<DataFieldRefType>>> buildEnableAndDisableDataGroup(DataGroupType dataGroupType) {
		Map<String,Map<String,List<DataFieldRefType>>> dataFieldMatrixMap =  new HashMap<String, Map<String,List<DataFieldRefType>>>();
		if(dataGroupType.getDataFieldMatrix() != null && dataGroupType.getDataFieldMatrix().getDependency() != null){
			for(DataFieldDependencyType dataFieldDependencyType :dataGroupType.getDataFieldMatrix().getDependency()){
				Map<String,List<DataFieldRefType>> enabledDataFieldMap = new HashMap<String, List<DataFieldRefType>>();
				for(DependentDataFieldsType dependentDataFieldsType:dataFieldDependencyType.getEnabledDataFields()){
					if(enabledDataFieldMap.get(dependentDataFieldsType.getValue()) == null){
						List<DataFieldRefType> enabledDataFieldList = new ArrayList<DataFieldRefType>();
						enabledDataFieldList.addAll(dependentDataFieldsType.getRef());
						enabledDataFieldMap.put(dependentDataFieldsType.getValue(), enabledDataFieldList);
					}else{
						enabledDataFieldMap.get(dependentDataFieldsType.getValue()).addAll(dependentDataFieldsType.getRef());
					}
				}
				dataFieldMatrixMap.put(dataFieldDependencyType.getExternalId(), enabledDataFieldMap);
			}
		}
		return dataFieldMatrixMap; 
	}


	/** This method is used to build list DataFeildFeatureVO for list of feature dataFeilds based on dialogue service response
	 * @param dataGroupType
	 * @param featurList
	 * @param featurGroupList
	 * @param titleName
	 * @return
	 */
	private List<DataFeildFeatureVO> buildFeatureDataFeildData(DataGroupType dataGroupType,List<FeatureType>  featurList,List<FeatureGroupType>  featurGroupList,String titleName,OrderQualVO orderQualVO){
		List<DataFeildFeatureVO> allDataFeildList = new ArrayList<DataFeildFeatureVO>();
		try{
			logger.info("start_buildFeatureDataFeildData");
			if( dataGroupType.getDataFieldList() != null && dataGroupType.getDataFieldList().getDataField() != null ){
				for(DataFieldType dataFieldType:dataGroupType.getDataFieldList().getDataField()){
					boolean isdataFieldTypeAdded = false;
					DataFeildFeatureVO dataFeildFeatureVO = new DataFeildFeatureVO();
					if(dataFieldType.getFeatureExternalId()!= null){
						for(FeatureType featureType:featurList){
							if(dataFieldType.getFeatureExternalId().equalsIgnoreCase(featureType.getExternalId())){
								if( featureType.getIncluded() != null  && dataGroupType.getDisplayName().contains("Included"))
								{   
									isdataFieldTypeAdded = true;
									buildFeatureDataFieldJSON(featureType,dataFeildFeatureVO);
									if(featureType.getDataConstraints()!= null 
											&& featureType.getDataConstraints().getIntegerConstraint() != null
											&& featureType.getDataConstraints().getIntegerConstraint().getValue() != null){
										dataFeildFeatureVO.setDataFieldText(dataFieldType.getText()+"-"+featureType.getDataConstraints().getIntegerConstraint().getValue());
										dataFeildFeatureVO.setDescription(dataFieldType.getText());
									}else if(featureType.getDataConstraints()!= null 
											&& featureType.getDataConstraints().getStringConstraint() != null
											&& !Utils.isBlank(featureType.getDataConstraints().getStringConstraint().getValue())){
										dataFeildFeatureVO.setDataFieldText(featureType.getDataConstraints().getStringConstraint().getValue());
										dataFeildFeatureVO.setDescription(featureType.getDataConstraints().getStringConstraint().getValue());
									}else {
										dataFeildFeatureVO.setDataFieldText(dataFieldType.getText());
										dataFeildFeatureVO.setDescription(dataFieldType.getText());
									}
									dataFeildFeatureVO.setDataFieldName(dataFieldType.getName());
									dataFeildFeatureVO.setDispalyInput(Constants.TICK);
									dataFeildFeatureVO.setEnabled(dataFieldType.isEnabled());
									dataFeildFeatureVO.setDataFieldExID(dataFieldType.getExternalId());
									dataFeildFeatureVO.setTagSectionName(titleName);
									allDataFeildList.add(dataFeildFeatureVO);
									break;
								}else if(featureType.isAvailable() && !dataGroupType.getDisplayName().contains("Included")){
									isdataFieldTypeAdded = true;
									buildFeatureDataFieldJSON(featureType,dataFeildFeatureVO);
									dataFeildFeatureVO.setDataFieldText(dataFieldType.getText());
									dataFeildFeatureVO.setDataFieldName(dataFieldType.getName());
									dataFeildFeatureVO.setEnabled(dataFieldType.isEnabled());
									dataFeildFeatureVO.setDataFieldExID(dataFieldType.getExternalId());
									dataFeildFeatureVO.setTagSectionName(titleName);
									dataFeildFeatureVO.setValidation(dataFieldType.getValidation());
									setPreviousSelectedFeatureValueType(dataFeildFeatureVO,orderQualVO);
									allDataFeildList.add(dataFeildFeatureVO);
									break;
								}
							}
						}

						for(FeatureGroupType featureGroupType:featurGroupList){

							if(dataGroupType.getDisplayName().contains("Included") )
							{  
								for(FeatureType featureType:featureGroupType.getFeature()){
									if(dataFieldType.getFeatureExternalId().equalsIgnoreCase(featureType.getExternalId())&& featureType.getIncluded() != null)
									{   
										isdataFieldTypeAdded = true;
										buildFeatureDataFieldJSON(featureType,dataFeildFeatureVO);
										if(featureType.getDataConstraints()!= null 
												&& featureType.getDataConstraints().getIntegerConstraint() != null
												&& featureType.getDataConstraints().getIntegerConstraint().getValue() != null){
											dataFeildFeatureVO.setDataFieldText(dataFieldType.getText()+"-"+featureType.getDataConstraints().getIntegerConstraint().getValue());
											dataFeildFeatureVO.setDescription(dataFieldType.getText());
										}else if(featureType.getDataConstraints()!= null 
												&& featureType.getDataConstraints().getStringConstraint() != null
												&& !Utils.isBlank(featureType.getDataConstraints().getStringConstraint().getValue())){
											dataFeildFeatureVO.setDataFieldText(dataFieldType.getText() +" "+featureType.getDataConstraints().getStringConstraint().getValue());
											dataFeildFeatureVO.setDescription(featureType.getDataConstraints().getStringConstraint().getValue());
										}else {
											dataFeildFeatureVO.setDataFieldText(dataFieldType.getText());
											dataFeildFeatureVO.setDescription(dataFieldType.getText());
										}
										dataFeildFeatureVO.setDataFieldName(dataFieldType.getName());
										dataFeildFeatureVO.setDispalyInput(Constants.TICK);
										dataFeildFeatureVO.setEnabled(dataFieldType.isEnabled());
										dataFeildFeatureVO.setDataFieldExID(dataFieldType.getExternalId());
										dataFeildFeatureVO.setTagSectionName(titleName);
										allDataFeildList.add(dataFeildFeatureVO);
										break;
									}
								}
							} else if(featureGroupType.isAvailable() 
									&& dataFieldType.getFeatureExternalId().equalsIgnoreCase(featureGroupType.getExternalId())){
								isdataFieldTypeAdded = true;
								List<DataFeildFeatureVO> featureDataFieldJSONList = new ArrayList<DataFeildFeatureVO>();
								for(FeatureType featureType:featureGroupType.getFeature()){
									if(featureType.isAvailable()){
										DataFeildFeatureVO dataFeildFeaturesVO = new DataFeildFeatureVO();
										buildFeatureDataFieldJSON(featureType,dataFeildFeaturesVO);
										dataFeildFeaturesVO.setDataFieldText(dataFieldType.getText());
										dataFeildFeaturesVO.setDataFieldName(dataFieldType.getName());
										dataFeildFeaturesVO.setEnabled(dataFieldType.isEnabled());
										dataFeildFeaturesVO.setDataFieldExID(dataFieldType.getExternalId());
										dataFeildFeatureVO.setTagSectionName(titleName);
										dataFeildFeatureVO.setValidation(dataFieldType.getValidation());
										featureDataFieldJSONList.add(dataFeildFeaturesVO);
									}
								}
								if(featureGroupType.getDataConstraints().getBooleanConstraint() != null){
									dataFeildFeatureVO.setDataConstraint(Constants.BOOLEAN_DATA_CONSTRAIN);
									dataFeildFeatureVO.setDispalyInput(Constants.CHECKBOX);
								}else if(featureGroupType.getDataConstraints().getStringConstraint() != null){
									dataFeildFeatureVO.setDataConstraint(Constants.STRING_DATA_CONSTRAIN);
									dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);
								}else if(featureGroupType.getDataConstraints().getIntegerConstraint() != null){
									dataFeildFeatureVO.setDataConstraint(Constants.INTEGER_DATA_CONSTRAIN);
									dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);
								}
								dataFeildFeatureVO.setFeatureExID(featureGroupType.getExternalId());
								dataFeildFeatureVO.setFeatureDescription(featureGroupType.getDescription());
								dataFeildFeatureVO.setDataFieldText(dataFieldType.getText());
								dataFeildFeatureVO.setDataFieldName(dataFieldType.getName());
								dataFeildFeatureVO.setEnabled(dataFieldType.isEnabled());
								dataFeildFeatureVO.setDataFieldExID(dataFieldType.getExternalId());
								dataFeildFeatureVO.setDescription(featureGroupType.getType());
								dataFeildFeatureVO.setType(Constants.FEATURE_GROUP_TYPE);
								dataFeildFeatureVO.setTagSectionName(titleName);
								dataFeildFeatureVO.setFeatureVOList(featureDataFieldJSONList);
								setPreviousSelectedFeatureGroupValueType(dataFeildFeatureVO,orderQualVO);
								allDataFeildList.add(dataFeildFeatureVO);
								break;
							}
						}	
					}

					if(!isdataFieldTypeAdded){
						if(dataFieldType.getInfoType() != null){
							dataFeildFeatureVO.setDataFieldText(dataFieldType.getText());
							dataFeildFeatureVO.setDataFieldName(dataFieldType.getName());
							dataFeildFeatureVO.setDataConstraint(dataFieldType.getType().value());
							dataFeildFeatureVO.setDispalyInput(dataFieldType.getInfoType().value());
							dataFeildFeatureVO.setDataFieldExID(dataFieldType.getExternalId());
							dataFeildFeatureVO.setEnabled(dataFieldType.isEnabled());
							dataFeildFeatureVO.setType(dataFieldType.getInfoType().value());
							dataFeildFeatureVO.setValidation(dataFieldType.getValidation());
							dataFeildFeatureVO.setTagSectionName(titleName);
							allDataFeildList.add(dataFeildFeatureVO);
						}else if(dataFieldType.getFeatureExternalId() == null){
							dataFeildFeatureVO.setDataFieldText(dataFieldType.getText());
							dataFeildFeatureVO.setDataFieldName(dataFieldType.getName());
							if(dataFieldType.getDataConstraints() != null && dataFieldType.getDataConstraints().getBooleanConstraint() != null){
								dataFeildFeatureVO.setDataConstraint(Constants.BOOLEAN_DATA_CONSTRAIN);
								dataFeildFeatureVO.setDispalyInput(Constants.CHECKBOX);
							}else if(dataFieldType.getDataConstraints() != null && dataFieldType.getDataConstraints().getIntegerConstraint() != null){
								dataFeildFeatureVO.setDataConstraint(Constants.INTEGER_DATA_CONSTRAIN);
								dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);
							}else if(dataFieldType.getDataConstraints() != null && dataFieldType.getDataConstraints().getStringConstraint() != null){
								dataFeildFeatureVO.setDataConstraint(Constants.STRING_DATA_CONSTRAIN);
								if("NA".equalsIgnoreCase(dataFieldType.getDataConstraints().getStringConstraint().getValue())){
									dataFeildFeatureVO.setDispalyInput(Constants.TEXT);
								}else{
									dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);
								}
							}else{
								dataFeildFeatureVO.setDataConstraint(Constants.STRING_DATA_CONSTRAIN);
								dataFeildFeatureVO.setDispalyInput(Constants.TEXT);
							}
							dataFeildFeatureVO.setDataFieldExID(dataFieldType.getExternalId());
							dataFeildFeatureVO.setEnabled(dataFieldType.isEnabled());
							dataFeildFeatureVO.setType(Constants.DIALOGUE);
							dataFeildFeatureVO.setTagSectionName(titleName);
							dataFeildFeatureVO.setValidation(dataFieldType.getValidation());
							setPreviousSelectedActiveDialogueType(dataFeildFeatureVO,orderQualVO);
							allDataFeildList.add(dataFeildFeatureVO);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("end_buildFeatureDataFeildData");
		return allDataFeildList;
	}

	/** This method is used to build DataFeildFeatureVO object  for feature dataFeild based on dialogue service response
	 * @param featureType
	 * @param dataFeildFeatureVO
	 * @return
	 * @throws Exception
	 */
	private DataFeildFeatureVO buildFeatureDataFieldJSON(FeatureType featureType, DataFeildFeatureVO dataFeildFeatureVO) throws Exception{
		Map<String,String> displayInputValMap = new HashMap<String,String>();
		List<String> constrainList = new ArrayList<String>();
		boolean isPriceSet = false;
		if(featureType.getDataConstraints().getBooleanConstraint() != null){
			dataFeildFeatureVO.setDataConstraint(Constants.BOOLEAN_DATA_CONSTRAIN);
			dataFeildFeatureVO.setDispalyInput(Constants.CHECKBOX);
			if(featureType.getDataConstraints().getBooleanConstraint().getValue() != null){
				constrainList.add(featureType.getDataConstraints().getBooleanConstraint().getValue());
			}
		}else if(featureType.getDataConstraints().getStringConstraint() != null){
			dataFeildFeatureVO.setDataConstraint(Constants.INTEGER_DATA_CONSTRAIN);
			dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);

			if(featureType.getDataConstraints().getStringConstraint().getListOfValues() != null){
				constrainList.addAll(featureType.getDataConstraints().getStringConstraint().getListOfValues().getValue());
			}else if(featureType.getDataConstraints().getStringConstraint().getValue() != null){
				constrainList.add(featureType.getDataConstraints().getStringConstraint().getValue());
			}
		}else if(featureType.getDataConstraints().getIntegerConstraint() != null){
			dataFeildFeatureVO.setDataConstraint(Constants.INTEGER_DATA_CONSTRAIN);
			dataFeildFeatureVO.setDispalyInput(Constants.DROP_DOWN);
			Map<Integer,PriceTierVO> priceTierMap = new TreeMap<Integer,PriceTierVO>();
			if(featureType.getPriceTierList() != null 
					&& featureType.getPriceTierList().getPriceTier()!= null
					&& featureType.getPriceTierList().getPriceTier().size() > 0){
				for(PriceTierType priceType :featureType.getPriceTierList().getPriceTier()){
					PriceTierVO priceTierVO = new PriceTierVO();
					priceTierVO.setBaseNonRecurringPrice(priceType.getPrice().getBaseNonRecurringPrice());
					priceTierVO.setBaseRecurringPrice(priceType.getPrice().getBaseRecurringPrice());
					priceTierVO.setRangeStart(priceType.getRangeStart());
					priceTierMap.put(priceType.getRangeStart(), priceTierVO);
				}
			}
			if(featureType.getDataConstraints().getIntegerConstraint().getMaxValue() != null
					&& featureType.getDataConstraints().getIntegerConstraint().getMinValue()!= null ){
				int max= featureType.getDataConstraints().getIntegerConstraint().getMaxValue().intValue();
				int min = featureType.getDataConstraints().getIntegerConstraint().getMinValue().intValue();
				Map<Integer,PriceTierVO> allpriceTierMap = null;
				List<Integer> constrainRangeList =  new ArrayList<Integer>();
				while(min <= max){
					constrainRangeList.add(min++);
				}
				Collections.sort(constrainRangeList);
				if(!priceTierMap.isEmpty()){
					allpriceTierMap = buildPriceTierList(constrainRangeList,priceTierMap);
				}else{
					allpriceTierMap = buildPriceList(constrainRangeList,featureType);
				}
				if(allpriceTierMap != null && allpriceTierMap.isEmpty()){
					for(Integer rangeVal:constrainRangeList){
						PriceTierVO priceTierVO = new PriceTierVO();
						priceTierVO.setBaseNonRecurringPrice(0.00);
						priceTierVO.setBaseRecurringPrice(0.00);
						priceTierVO.setRangeStart(rangeVal);
						allpriceTierMap.put(rangeVal,priceTierVO);
					}
				}
				dataFeildFeatureVO.setPriceTierMap(allpriceTierMap);
			}else if(featureType.getDataConstraints().getIntegerConstraint().getValue() != null){
				constrainList.add(String.valueOf(featureType.getDataConstraints().getIntegerConstraint().getValue()));
			}
		}else{
			dataFeildFeatureVO.setDispalyInput(Constants.CHECKBOX);
		}
		if(featureType.getIncluded() != null){
			dataFeildFeatureVO.setIncluded(true);
		}
		if(featureType.getPrice() != null ){
			dataFeildFeatureVO.setBaseNonRecurringPrice(String.format(Constants._2F, featureType.getPrice().getBaseNonRecurringPrice()));
			dataFeildFeatureVO.setBaseRecurringPrice(String.format(Constants._2F, featureType.getPrice().getBaseRecurringPrice()));
			isPriceSet = true;
		}else if(dataFeildFeatureVO.getPriceTierMap() != null && !dataFeildFeatureVO.getPriceTierMap().isEmpty()){
			for(Entry<Integer, PriceTierVO> tierMap: dataFeildFeatureVO.getPriceTierMap().entrySet()){
				if(tierMap.getValue() != null 
						                    && tierMap.getValue().getRangeStart() != null 
						                           && tierMap.getValue().getRangeStart().equals(1)){
					dataFeildFeatureVO.setBaseNonRecurringPrice(String.format(Constants._2F, dataFeildFeatureVO.getPriceTierMap().get(1).getBaseNonRecurringPrice()));
					dataFeildFeatureVO.setBaseRecurringPrice(String.format(Constants._2F, dataFeildFeatureVO.getPriceTierMap().get(1).getBaseRecurringPrice()));
					isPriceSet = true;
					break;
				}
			}
		}
		if(!isPriceSet){
			dataFeildFeatureVO.setBaseNonRecurringPrice("0.00");
			dataFeildFeatureVO.setBaseRecurringPrice("0.00");
		}
		dataFeildFeatureVO.setDataConstraintValueList(constrainList);
		dataFeildFeatureVO.setType(Constants.FEATURE);
		dataFeildFeatureVO.setFeatureExID(featureType.getExternalId());
		dataFeildFeatureVO.setFeatureDescription(featureType.getDescription());
		dataFeildFeatureVO.setDescription(featureType.getType());
		displayInputValMap.put("featureExternalID",dataFeildFeatureVO.getFeatureExID());
		displayInputValMap.put("includedFlag",String.valueOf(dataFeildFeatureVO.isIncluded()));
		displayInputValMap.put("dataConstraint",dataFeildFeatureVO.getDataConstraint());
		displayInputValMap.put("shortDescription",featureType.getType());
		displayInputValMap.put("recuringPrice",dataFeildFeatureVO.getBaseRecurringPrice());
		displayInputValMap.put("type","feature");
		displayInputValMap.put("nonRecuringPrice",dataFeildFeatureVO.getBaseNonRecurringPrice());
		dataFeildFeatureVO.setDispalyInputValue(displayInputValMap);
		return dataFeildFeatureVO;
	}

	/** This method is used to build PriceList for FeatureType
	 * @param constrainRangeList
	 * @param featureType
	 * @return
	 */
	private Map<Integer,PriceTierVO> buildPriceList(List<Integer> constrainRangeList,FeatureType featureType) {
		Map<Integer,PriceTierVO> priceTierMap = new HashMap<Integer, PriceTierVO>();
		//build priceTierMap based on rangeValue 
		if(featureType.getPrice() != null){
			int count = 0;
			Double baseNonRecPrice = featureType.getPrice().getBaseNonRecurringPrice();
			Double baseRecPrice = featureType.getPrice().getBaseRecurringPrice();
			for(Integer rangeValue:constrainRangeList){
				PriceTierVO priceTierVO = new PriceTierVO();
				if(count > 0){
					if(rangeValue > 0){
						baseNonRecPrice =  baseNonRecPrice + featureType.getPrice().getBaseNonRecurringPrice();
						baseRecPrice = baseRecPrice + featureType.getPrice().getBaseRecurringPrice();
					}
				}else if(rangeValue == 0){
					baseNonRecPrice =  0.00;
					baseRecPrice = 0.00;
				}
				priceTierVO.setBaseNonRecurringPrice(baseNonRecPrice);
				priceTierVO.setBaseRecurringPrice(baseRecPrice);
				priceTierVO.setRangeStart(rangeValue);
				priceTierMap.put(rangeValue, priceTierVO);
				++count;
			}
		}
		return priceTierMap;
	}

	/** This method is used to build final priceList for featureType of Max and Min range
	 * @param constrainRangeList
	 * @param priceTierMap
	 * @return
	 */
	private Map<Integer,PriceTierVO>  buildPriceTierList(List<Integer> constrainRangeList, Map<Integer, PriceTierVO> priceTierMap) {
		//build rangeMap for priceTier Calculations
		for(Integer rangeValue:constrainRangeList){
			if(rangeValue > 0 && priceTierMap.get(rangeValue) == null){
				int tempRangeValue =  rangeValue;
				while(tempRangeValue > constrainRangeList.get(0)){
					if(priceTierMap.get(--tempRangeValue) != null){
						PriceTierVO priceTierVO = new PriceTierVO();
						Double baseNonRecprice= priceTierMap.get(tempRangeValue).getBaseNonRecurringPrice();
						Double baseRecprice= priceTierMap.get(tempRangeValue).getBaseRecurringPrice() ;
						priceTierVO.setBaseNonRecurringPrice(baseNonRecprice);
						priceTierVO.setBaseRecurringPrice(baseRecprice);
						priceTierVO.setRangeStart(rangeValue);
						priceTierMap.put(rangeValue, priceTierVO);
						break;
					}
				}
			}else if(rangeValue == 0){
				PriceTierVO priceTierVO = new PriceTierVO();
				priceTierVO.setBaseNonRecurringPrice(0.00);
				priceTierVO.setBaseRecurringPrice(0.00);
				priceTierVO.setRangeStart(rangeValue);
				priceTierMap.put(rangeValue, priceTierVO);
			}
		}
		//Update price range 
		for(Integer rangeValue:constrainRangeList){
			if(priceTierMap.get(rangeValue-1) != null && priceTierMap.get(rangeValue) != null){
				Double baseNonRecprice= priceTierMap.get(rangeValue).getBaseNonRecurringPrice() + priceTierMap.get(rangeValue-1).getBaseNonRecurringPrice();
				Double baseRecprice= priceTierMap.get(rangeValue).getBaseRecurringPrice() + priceTierMap.get(rangeValue-1).getBaseRecurringPrice();
				priceTierMap.get(rangeValue).setBaseRecurringPrice(baseRecprice);
				priceTierMap.get(rangeValue).setBaseNonRecurringPrice(baseNonRecprice);
			}
		}
		
		return priceTierMap;
	}

	/**This method is used to build DataFeildFeatureVOMap key as DataFieldExtrnalID
	 * @param dialogueTypeList
	 * @return
	 */
	public Map<String,DataFeildFeatureVO> buildDataFeildFeatureVOMap(List<DataDialogueVO> dialogueTypeList){
		Map<String,DataFeildFeatureVO> dataFeildMap = new HashMap<String,DataFeildFeatureVO>();
		for(DataDialogueVO dataDialogueVO:dialogueTypeList){
			for(DataGroupVO dataGroupVO:dataDialogueVO.getDataGroupList()){
				for(DataFeildFeatureVO dataFeildFeatureVO:dataGroupVO.getDataFeildList()){
					dataFeildMap.put(dataFeildFeatureVO.getDataFieldExID(),dataFeildFeatureVO);
				}
			}
		}
		return dataFeildMap;
	}


	/** This method is used to build DataFeildFeatureVO list based on custom array 
	 * @param customerCustomeList
	 * @return
	 */
	public List<DataFeildFeatureVO>  buildCustomizeCustomerInformation(String [] customerCustomeList,OrderType orderType,OrderQualVO orderQualVO) {
		List<DataFeildFeatureVO> dataFeildCustomerVOList = new ArrayList<DataFeildFeatureVO>();
		for(String customCustomerName:customerCustomeList){
			DataFeildFeatureVO dataFeildFeatureVO = new DataFeildFeatureVO();
			dataFeildFeatureVO.setDataFieldExID(customCustomerName);
			dataFeildFeatureVO.setDataFieldName(customCustomerName);
			dataFeildFeatureVO.setDataFieldText(customCustomerName);
			dataFeildFeatureVO.setDataConstraint("string");
			dataFeildFeatureVO.setDescription(customCustomerName);
			//dataFeildFeatureVO.setDispalyInput("text");
			dataFeildFeatureVO.setType("dialogue");
			if(orderType.getCustomerInformation() != null && orderType.getCustomerInformation().getCustomer() != null){
				if("emailAddress".equalsIgnoreCase(customCustomerName)){
					dataFeildFeatureVO.setEnteredValue(orderType.getCustomerInformation().getCustomer().getBestEmailContact());
				}else if("firstName".equalsIgnoreCase(customCustomerName)&& !"Default".equalsIgnoreCase(orderType.getCustomerInformation().getCustomer().getFirstName())){
					dataFeildFeatureVO.setEnteredValue(orderType.getCustomerInformation().getCustomer().getFirstName());
				}else if("lastName".equalsIgnoreCase(customCustomerName) && !"Customer".equalsIgnoreCase(orderType.getCustomerInformation().getCustomer().getLastName())){
					dataFeildFeatureVO.setEnteredValue(orderType.getCustomerInformation().getCustomer().getLastName());
				}else if("middleName".equalsIgnoreCase(customCustomerName)){
					dataFeildFeatureVO.setEnteredValue(orderType.getCustomerInformation().getCustomer().getMiddleName());
				}else if("BestContactOnDayOfInstallWeb".equalsIgnoreCase(customCustomerName)){
					setPreviousSelectedActiveDialogueType(dataFeildFeatureVO,orderQualVO);
				}else if("BestContactNum".equalsIgnoreCase(customCustomerName)){
					setPreviousSelectedActiveDialogueType(dataFeildFeatureVO,orderQualVO);
				}else if("DisabledHomePhoneNum".equalsIgnoreCase(customCustomerName)){
					setPreviousSelectedActiveDialogueType(dataFeildFeatureVO,orderQualVO);
				}
			}
			dataFeildCustomerVOList.add(dataFeildFeatureVO);
		}
		return dataFeildCustomerVOList;
	}


	/**
	 * @param dataFeildFeatureVO
	 * @param orderQualVO
	 */
	public void setPreviousSelectedFeatureValueType(DataFeildFeatureVO dataFeildFeatureVO,OrderQualVO orderQualVO){
		if(!Utils.isBlank(dataFeildFeatureVO.getFeatureExID())
				&& orderQualVO.getLineItemType() != null
				&& orderQualVO.getLineItemType().getSelectedFeatures()!= null 
				&& orderQualVO.getLineItemType().getSelectedFeatures().getFeatures()!= null
				&& orderQualVO.getLineItemType().getSelectedFeatures().getFeatures().getFeatureValue() != null
				&& !orderQualVO.getLineItemType().getSelectedFeatures().getFeatures().getFeatureValue().isEmpty()){
			for(FeatureValueType featureValueType:orderQualVO.getLineItemType().getSelectedFeatures().getFeatures().getFeatureValue()){
				if(dataFeildFeatureVO.getFeatureExID().equalsIgnoreCase(featureValueType.getExternalId())){
					if("true".equalsIgnoreCase(featureValueType.getValue())){
						dataFeildFeatureVO.setEnteredValue("on");
					}else{
						dataFeildFeatureVO.setEnteredValue(featureValueType.getValue());
					}
					break;
				}
			}
		}
	}


	/**
	 * @param dataFeildGroupFeatureVO
	 * @param orderQualVO
	 */
	public void setPreviousSelectedFeatureGroupValueType(DataFeildFeatureVO dataFeildGroupFeatureVO,OrderQualVO orderQualVO){
		if(!Utils.isBlank(dataFeildGroupFeatureVO.getFeatureExID())
				&& orderQualVO.getLineItemType() != null
				&& orderQualVO.getLineItemType().getSelectedFeatures()!= null 
				&& orderQualVO.getLineItemType().getSelectedFeatures().getFeatureGroup()!= null
				&& !orderQualVO.getLineItemType().getSelectedFeatures().getFeatureGroup().isEmpty()){
			for(FeatureGroup featureGroupType:orderQualVO.getLineItemType().getSelectedFeatures().getFeatureGroup()){
				if(featureGroupType != null 
						&& dataFeildGroupFeatureVO.getFeatureVOList() != null
						&& !dataFeildGroupFeatureVO.getFeatureVOList().isEmpty()){
					for(FeatureValueType featureValueType:featureGroupType.getFeatureValue()){
						for(DataFeildFeatureVO dataFeildFeatureVO:dataFeildGroupFeatureVO.getFeatureVOList()){
							if(!Utils.isBlank(dataFeildFeatureVO.getFeatureExID()) && dataFeildFeatureVO != null && dataFeildFeatureVO.getFeatureExID().equalsIgnoreCase(featureValueType.getExternalId())){
								if(dataFeildFeatureVO.getDataConstraintValueList() != null && dataFeildFeatureVO.getDataConstraintValueList().size() > 0){
									dataFeildFeatureVO.setEnteredValue(dataFeildFeatureVO.getDataConstraintValueList().get(0));
								}
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param dataFeildFeatureVO
	 * @param orderQualVO
	 */
	public void setPreviousSelectedActiveDialogueType(DataFeildFeatureVO dataFeildFeatureVO,OrderQualVO orderQualVO){
		if(!Utils.isBlank(dataFeildFeatureVO.getDataFieldExID())
				&& orderQualVO.getLineItemType() != null
				&& orderQualVO.getLineItemType().getActiveDialogs()!= null 
				&& orderQualVO.getLineItemType().getActiveDialogs().getDialogs()!= null
				&& orderQualVO.getLineItemType().getActiveDialogs().getDialogs().getDialog() != null
				&& !orderQualVO.getLineItemType().getActiveDialogs().getDialogs().getDialog().isEmpty()){
			for(DialogValueType dialogValueType:orderQualVO.getLineItemType().getActiveDialogs().getDialogs().getDialog()){
				if(dataFeildFeatureVO.getDataFieldExID().equalsIgnoreCase(dialogValueType.getExternalId())){
					if(dialogValueType.getValue() != null && !dialogValueType.getValue().isEmpty()){
						dataFeildFeatureVO.setEnteredValue(dialogValueType.getValue().get(0).getValue());
						break;
					}
				}
			}
		}
	}

	/**
	 * @param combinedDataGroupMap
	 * @param dialogueType
	 * @param sectionName
	 */
	public void combinedDataGroupSection(Map<String,DataDialogueVO> combinedDataGroupMap, DataDialogueVO dialogueType,String sectionName) {
		//DialogueType adding
		if(!Utils.isBlank(sectionName) 
				&& combinedDataGroupMap.get(sectionName) == null
				&& !dialogueType.getDataGroupList().isEmpty()){
			combinedDataGroupMap.put(sectionName, dialogueType);
		}else{
			if(!dialogueType.getDataGroupList().isEmpty()){
				// Iterate DialogueType to add exiting combinedDataGroupMap
				for(DataGroupVO dataGroupType:dialogueType.getDataGroupList()){
					boolean isDataGroupMatched = false;
					logger.info("DataGroupType_DisplayName="+dataGroupType.getSubTitle());
					// Iterate combinedDataGroupMap of DialogueType to check DataGoup DisplayName
					for(DataGroupVO dataGroupTypeMap:combinedDataGroupMap.get(sectionName).getDataGroupList()){
						if(!Utils.isBlank(dataGroupType.getSubTitle()) 
								&& dataGroupType.getSubTitle().equalsIgnoreCase(dataGroupTypeMap.getSubTitle())){
							isDataGroupMatched = true;
							//DataFieldList
							if(!dataGroupType.getDataFeildList().isEmpty()){
								dataGroupTypeMap.getDataFeildList().addAll(dataGroupType.getDataFeildList());
							}
						}
					}
					logger.info("isDataGroupMatched="+isDataGroupMatched);
					//Adding dataGroupType to Exit if not matched
					if(!isDataGroupMatched){
						combinedDataGroupMap.get(sectionName).getDataGroupList().add(dataGroupType);
					}
				}
			}
		}
	}
}
