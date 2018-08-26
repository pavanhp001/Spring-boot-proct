package com.AL.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.AL.controller.DynamicSaveController;
import com.AL.ui.builder.DialogueVOBuilder;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataFieldDependency;
import com.AL.ui.domain.dynamic.dialogue.DataFieldMatrix;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.DependentDataFields;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.mapper.DialogueMapper;
import com.AL.ui.service.V.DialogService;
import com.AL.ui.service.V.impl.DialogCacheService;
import com.AL.ui.vo.DialogueVO;
import com.AL.V.domain.SalesContext;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;

public class DialogueUtil {
	private static final Logger logger = Logger.getLogger(DialogueUtil.class);
	/**
	 * return a map which contains all the elements that are to be shown when the corresponding parent element is selected
	 * @param dataGroupList
	 * @param availableFeatureGroupMap 
	 * @return
	 */
	public static Map<String, Map<String, List<String>>> buildDataFieldMatrices(List<DataGroup> dataGroupList, Map<String, FeatureGroupType> availableFeatureGroupMap) {
		Map<String, Map<String, List<String>>> enableDependencyMap = new HashMap<String, Map<String, List<String>>>();
		for(DataGroup dtGroup : dataGroupList){
			String dataGroupName = dtGroup.getName();
			Map<String, Map<String, List<String>>> enableDependencies = getEnableDependencies(dtGroup, dataGroupName, enableDependencyMap, availableFeatureGroupMap); 
			if(enableDependencies != null && !enableDependencies.isEmpty()){
				enableDependencyMap.putAll(enableDependencies);	
			}
		}
		return enableDependencyMap;
	}
	
	/**
	 * returns enabled dependencies that are present for current dataGroup
	 * @param dms
	 * @param dataGroupName
	 * @param enableDependencyMap
	 * @param availableFeatureGroupMap 
	 * @return
	 */
	public static Map<String, Map<String, List<String>>> getEnableDependencies(DataGroup dtGroup, 
			String dataGroupName, Map<String, Map<String, List<String>>> enableDependencyMap, Map<String, FeatureGroupType> availableFeatureGroupMap) {
		/*
		 * check whether the dataFieldMatrix is empty or not
		 */
		if(!Utils.isEmpty(dtGroup.getDataFieldMatrixList())) {
			for(DataFieldMatrix matrix : dtGroup.getDataFieldMatrixList()) {
				for(DataFieldDependency dependency : matrix.getDependencyList()) {
					Map<String, List<String>> enableMap = new HashMap<String, List<String>>();
					
					List<String> optionsList = returnOptionsList(dtGroup.getDataFieldList(), availableFeatureGroupMap, dependency.getExternalId());
					
					for(DependentDataFields dataField : dependency.getEnabledDataFields()) {
						List<String> dfExternalID = new ArrayList<String>();
						
						/*
						 * in some cases if same child dialogues are to be displayed to different selections, the externalID's of this type 
						 * of fields separated by ' OR ' that means we need to display same type of dialogues for each of those
						 */
						if(!Utils.isEmpty(optionsList)){
							if(optionsList.indexOf(dataField.getValue()) >= 0){
								if(dataField.getValue().indexOf(" OR ") > 0){
									String[] arr = dataField.getValue().split(" OR ");
									for(String str : arr){
										for(String externalID : dataField.getDataFieldExternalIds()){
											dfExternalID.add(dataGroupName+"_"+externalID);
										}
										enableMap.put(str, dfExternalID);
									}
								}else{
									for(String dataFieldExternalID : dataField.getDataFieldExternalIds()){
										dfExternalID.add(dataGroupName+"_"+dataFieldExternalID);
									}
									enableMap.put(dataField.getValue(), dfExternalID);
								}
								optionsList.remove(dataField.getValue());
							}
							for(String options : optionsList){
								enableMap.put(options, new ArrayList<String>());
							}
						}
						else {
							if(dataField.getValue().indexOf(" OR ") > 0){
								String[] arr = dataField.getValue().split(" OR ");
								for(String str : arr){
									for(String externalID : dataField.getDataFieldExternalIds()){
										dfExternalID.add(dataGroupName+"_"+externalID);
									}
									enableMap.put(str, dfExternalID);
								}
							}else{
								for(String dataFieldExternalID : dataField.getDataFieldExternalIds()){
									dfExternalID.add(dataGroupName+"_"+dataFieldExternalID);
								}
								enableMap.put(dataField.getValue(), dfExternalID);
							}
						}
					}
					
					/*
					 * setting the ID of map as DataGroupExternalID_DataFieldExternalID to have uniqueness
					 */
					enableDependencyMap.put(dataGroupName+"_"+dependency.getExternalId(), enableMap);
				}
			}
			return enableDependencyMap;
		}
		return null;
	}


	private static List<String> returnOptionsList(List<DataField> dataFieldList, Map<String, FeatureGroupType> availableFeatureGroupMap, String externalID){
		List<String> optionsList = new ArrayList<String>();
		for(DataField df : dataFieldList){
			String featureExternalID = null;
			if(df.getType() == "feature" && df.getExternalId().equals(externalID)){
				featureExternalID = df.getFeatureExternalId();
			}
			if(availableFeatureGroupMap != null){
				FeatureGroupType featGroupType = availableFeatureGroupMap.get(featureExternalID);
				logger.info(featGroupType != null);
				if(featGroupType != null && featGroupType.getFeature() != null){
					for(FeatureType featureType : featGroupType.getFeature()){
						if(featureType.isAvailable()){
							if(featureType.getDataConstraints() != null){
								if(featureType.getDataConstraints().getStringConstraint() != null){
									if(featureType.getDataConstraints().getStringConstraint().getListOfValues() != null) {
										optionsList.addAll(featureType.getDataConstraints().getStringConstraint().getListOfValues().getValue());
									} else if(featureType.getDataConstraints().getStringConstraint().getValue() != null) {
										optionsList.add(featureType.getDataConstraints().getStringConstraint().getValue());
									}
								}
							}
						}
					}
				}
			}
		}
		return optionsList;
	}

	/**
	 * iterate over the enableDependencyMap and creates a map exactly opposite of enableDependencyMap
	 * @param dataGroupList
	 * @param enableDependencyMap
	 * @param featureMap
	 * @param featureGroupMap
	 * @return
	 */
	public static Map<String, Map<String, List<String>>> getDisableDialogueDependencies(List<DataGroup> dataGroupList, 
			Map<String, Map<String, List<String>>> enableDependencyMap, Map<String, FeatureType> featureMap, 
			Map<String, FeatureGroupType> featureGroupMap) {

		Map<String, Map<String, List<String>>> disableDependencyMapMap = new HashMap<String, Map<String, List<String>>>();
		
		/*
		 * iterate over dataGroupList and dataFieldList, create unique external Id as DataGroupName_DataFeildExternalID
		 * and check whether this unique external ID is present in the enableDependencyMap, if it is present, 
		 * get corresponding value. this value will be a Map<String, List<String>> i.e, 
		 * a map containing selection and corresponding externalId's to be activated with this selection
		 */
		for(DataGroup dataGroup : dataGroupList){
			for(DataField df : dataGroup.getDataFieldList()){

				if(enableDependencyMap != null) {
					Map<String, List<String>> enableMap = enableDependencyMap.get(dataGroup.getName()+"_"+df.getExternalId());

					if(enableMap != null) {
						Map<String, List<String>> disableMap = new HashMap<String, List<String>>();
						List<String> options = new ArrayList<String>();
						
						/*
						 * building a List<String> with type of selection that can be made for the current data field
						 * later iterating over this list, we build a map with type of selection and corresponding externalID to be hidden
						 */
						
						/*
						 * check if the data field contains dataConstraint, if the data constraint is not present, it is considered as feature type dialogue
						 */
						if((!df.getType().equals("feature")) && (df.getBooleanConstraint() != null || df.getStringConstraint() != null || df.getIntegerConstraint() != null)){

							if(df.getBooleanConstraint() != null) {
								options.add("Y");
								options.add("N");
							}
							else if(df.getStringConstraint() != null) {
								if(df.getStringConstraint().getListOfValues() != null) {
									options.addAll(df.getStringConstraint().getListOfValues());
								} else if(df.getStringConstraint().getValue() != null) {
									options.add(df.getStringConstraint().getValue());
								}
							}
						}
						else{
							if(featureGroupMap != null){
								if(featureGroupMap.get(df.getFeatureExternalId()) != null){
									FeatureGroupType feature = featureGroupMap.get(df.getFeatureExternalId());
									for(FeatureType featureType : feature.getFeature()){
										if(featureType.getDataConstraints() != null){
											if(featureType.getDataConstraints().getStringConstraint() != null){
												if(featureType.getDataConstraints().getStringConstraint().getListOfValues() != null) {
													options.addAll(featureType.getDataConstraints().getStringConstraint().getListOfValues().getValue());
												} else if(featureType.getDataConstraints().getStringConstraint().getValue() != null) {
													options.add(featureType.getDataConstraints().getStringConstraint().getValue());
												}
											}
										}
									}
								}
							}
							if(featureMap != null){
								if(featureMap.get(df.getFeatureExternalId()) != null){
									FeatureType feature = featureMap.get(df.getFeatureExternalId());
									if(feature.getDataConstraints() != null && (feature.getDataConstraints().getBooleanConstraint() != null || 
											feature.getDataConstraints().getIntegerConstraint() != null || feature.getDataConstraints().getStringConstraint() != null)){
										if(feature.getDataConstraints().getBooleanConstraint() != null) {
											options.add("Y");
											options.add("N");
										} else if(feature.getDataConstraints().getStringConstraint() != null) {
											if(feature.getDataConstraints().getStringConstraint().getListOfValues() != null) {
												options.addAll(feature.getDataConstraints().getStringConstraint().getListOfValues().getValue());
											} else if(feature.getDataConstraints().getStringConstraint().getValue() != null) {
												options.add(feature.getDataConstraints().getStringConstraint().getValue());
											}
										}
									}
								}
							}
						}
						
						/*
						 * iterating over options and building a map with option as key and list of values that are to be hidden as value
						 */
						for(String option : options) {
							List<String> disables = new ArrayList<String>();
							for(Map.Entry<String, List<String>> entry : enableMap.entrySet()) {
								if(!entry.getKey().equalsIgnoreCase(option)) {
									for(String parentExtId:  entry.getValue()){
										disables.add(parentExtId);
										disables.addAll(addSubChildExtIds(parentExtId, enableDependencyMap));
									}
								}
							}
							disableMap.put(option, disables);
						}
						disableDependencyMapMap.put(dataGroup.getName()+"_"+df.getExternalId(), disableMap);
					}
				}
			}
		}
		return disableDependencyMapMap;
	}

	private static List<String> addSubChildExtIds(String parentExtId, Map<String, Map<String, List<String>>> enableDependencyMap) {
		List<String> result = new ArrayList<String>();
		//loop through for childs of parentExtId and add them also to this list
		if(enableDependencyMap.get(parentExtId) != null){
			Map<String, List<String>> curMap = enableDependencyMap.get(parentExtId);
			for(Entry<String, List<String>> curMapEntry : curMap.entrySet()){
				List<String> subChildList = curMapEntry.getValue();
				for(String subChild : subChildList ){
					result.add(subChild);
					result.addAll(addSubChildExtIds(subChild, enableDependencyMap));
				}
			}
		} 
		return result;
	}
	
	/**
	 * checks whether the dialogue response is present in cache else it will perform a service call 
	 * @param salesContext
	 * @param productExternalID
	 * @return
	 * @throws Exception
	 */
	public static DialogueResponseType returnDialoguesVoObject(SalesContext salesContext, String productExternalID) throws Exception{
		
		DialogueResponseType dialogResponse = DialogCacheService.INSTANCE.getFromCache(productExternalID);
		if(dialogResponse == null){
			dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);
			DialogCacheService.INSTANCE.store(dialogResponse, productExternalID);
		}
		
		return dialogResponse;
	}
	
	/**
	 * iterate over the dialogues List and generate a new list of externalID of dialogues that are of feature type
	 * @param dialogueVO
	 * @return
	 */
	public static List<String> returnDialogueFeatures(DialogueVO dialogueVO){
		List<String> dialogueFeatureExternalIDList = new ArrayList<String>();
		
		for (Dialogue dialogue : dialogueVO.getDialogueNameList()) {
			String externalId = dialogue.getExternalId();
			Map<String, List<DataField>> dataFieldMap = dialogueVO.getDataFieldMap().get(externalId);
			for(Map.Entry<String, List<DataField>> fieldEntry : dataFieldMap.entrySet()) {
				for(DataField dataField : fieldEntry.getValue()){
					if(dataField.getType().equals("feature")){
						dialogueFeatureExternalIDList.add(dataField.getFeatureExternalId());
					}
				}
			}	
		}
		return dialogueFeatureExternalIDList;
	}
	
	
	/**
	 * converts the dialogueResponseType object to dialogueVO Object
	 * @param dialogueResponseType
	 * @param extId
	 * @return
	 */
	public static DialogueVO buildDialogueVO(DialogueResponseType dialogueResponseType, String extId){
		
		/*
		 * returns a List of Dialogues present on the dialogue response object
		 */
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogueResponseType);

		/*
		 * DialogueVO Object contains Map<dialogueExternalID,<Map<DataGroupExternalID, List<DataField>>>>(DataFieldMap) and
		 * Map<dialogueExternalID,<Map<DataGroupExternalID, List<DataFieldMatrix>>>> (DataFieldMatrixMap)
		 * DataFieldMap is used for creating a display dialogues in the dialogues page
		 * DataFieldMatrixMap is used for creating the dependencies of the dialogues over other dialogue
		 */
		DialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList, new DialogueVO(), extId);

		/*
		 * setting List<Dialogues> to dialogueVO Object
		 */
		dialogueVO.setDialogueNameList(dialogueList);
		
		return dialogueVO;
	}
}
