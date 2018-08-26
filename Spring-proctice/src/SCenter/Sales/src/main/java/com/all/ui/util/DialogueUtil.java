package com.AL.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.AL.xml.di.v4.DataConstraintType;
import com.AL.xml.di.v4.DataFieldDependencyType;
import com.AL.xml.di.v4.DataFieldMatrixType;
import com.AL.xml.di.v4.DataFieldRefType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DependentDataFieldsType;

public enum DialogueUtil {
	
	INSTANCE;

	public Map<String, Map<String, List<String>>> getEnableDependencies(List<DataFieldMatrixType> dataFieldEnableList, HttpServletRequest request) {
		
		Map<String, Map<String, List<String>>> enableDependencyMap = new HashMap<String, Map<String, List<String>>>();
		
		if((dataFieldEnableList != null) && (dataFieldEnableList.size() > 0)) {
			for(DataFieldMatrixType matrix : dataFieldEnableList) {
				for(DataFieldDependencyType dependency : matrix.getDependency()) {

					Map<String, List<String>> enableMap = new HashMap<String, List<String>>();
					for(DependentDataFieldsType dataField : dependency.getEnabledDataFields()) {
						List<String> dataFieldExternalIDList = new ArrayList<String>();
						for(DataFieldRefType dataFieldRef : dataField.getRef()){
							String refId = dataFieldRef.getExternalId();
							if(dataFieldRef.getExternalId().indexOf(' ') >= 0){
								System.out.println("ENABLE DEPENDENCY EXTERNAL ID ::::::::: "+refId);
								refId = dataFieldRef.getExternalId().replace(" ", "");
							}
							dataFieldExternalIDList.add(refId);
						}
						if(dataField.getValue().equals("{allSuppliers}")){
							List<String> supplierList = (List<String>) request.getAttribute("supplier_list");
							for(String suppliers : supplierList){
								enableMap.put(suppliers, dataFieldExternalIDList);
							}
							enableMap.put("{allSuppliers}", dataFieldExternalIDList);
						}else{
							enableMap.put(dataField.getValue(), clearListFromDuplicate(dataFieldExternalIDList));	
						}
					}
					enableDependencyMap.put(dependency.getExternalId(), enableMap);
				}
			}
		}
		return enableDependencyMap;
	}

	public Map<String, Map<String, List<String>>> getDisableDependencies(List<DataFieldType> dataFields,
			Map<String, Map<String, List<String>>> enableDependencyMap, HttpServletRequest request) {
		
		Map<String, Map<String, List<String>>> disableDependencyMapMap = new HashMap<String, Map<String, List<String>>>();

		for(DataFieldType df : dataFields) {

			if(enableDependencyMap != null) {
				Map<String, List<String>> enableMap = enableDependencyMap.get(df.getExternalId());
				DataConstraintType dcType = df.getDataConstraints();
				if(enableMap != null) {
					Map<String, List<String>> disableMap = new HashMap<String, List<String>>();
					List<String> options = new ArrayList<String>();
					if(dcType != null && (dcType.getBooleanConstraint() != null || dcType.getStringConstraint() != null || dcType.getIntegerConstraint() != null)){

						if(dcType.getBooleanConstraint() != null) {
							options.add("Y");
							options.add("N");
						}
						else if(dcType.getStringConstraint() != null) {
							if(dcType.getStringConstraint().getListOfValues() != null) {
								DataConstraintType.StringConstraint.ListOfValues stringConstraint = dcType.getStringConstraint().getListOfValues();
								options.addAll(stringConstraint.getValue());
							} else if(dcType.getStringConstraint().getValue() != null) {
								options.add(dcType.getStringConstraint().getValue());
							}
						}
					}

					for(String option : options) {
						List<String> disables = new ArrayList<String>();
						for(Entry<String, List<String>> entry : enableMap.entrySet()) {
							
							if(!entry.getKey().equalsIgnoreCase(option)) {
								for(String parentExtId:  entry.getValue()){
									if(parentExtId.indexOf(' ') >= 0){
										System.out.println("PARENT EXTERNAL ID ::::::::: "+parentExtId);
										parentExtId = parentExtId.replace(" ", "");
									}
									disables.add(parentExtId);
									disables.addAll(addChildExtIds(parentExtId, enableDependencyMap));
								}
							}
						}
						if(option.equals("{allSuppliers}")){
							List<String> supplierList = (List<String>) request.getAttribute("supplier_list");
							for(String suppliers : supplierList){
								disableMap.put(suppliers, disables);
							}
						}
						else{
							disableMap.put(option, clearListFromDuplicate(disables));
						}
					}
					disableDependencyMapMap.put(df.getExternalId(), disableMap);
				}
			}
		}
		return disableDependencyMapMap;
	}

	private static List<String> addChildExtIds(String parentExtId, Map<String, Map<String, List<String>>> enableDependencyMap) {
		List<String> result = new ArrayList<String>();
		//loop through for childs of parentExtId and add them also to this list
		if(enableDependencyMap.get(parentExtId) != null){
			Map<String, List<String>> curMap = enableDependencyMap.get(parentExtId);
			for(Entry<String, List<String>> curMapEntry : curMap.entrySet()){
				List<String> subChildList = curMapEntry.getValue();
				for(String subChild : subChildList ){
					if(subChild.indexOf(' ') >= 0){
						System.out.println("SUB CHILD EXTERNAL ID ::::::::: "+subChild);
						subChild = subChild.replace(" ", "");
					}
					result.add(subChild);
					result.addAll(addChildExtIds(subChild, enableDependencyMap));
				}
			}
		} 
		return result;
	}
	private List<String> clearListFromDuplicate(List<String> list1) {

		  Map<String, String> cleanMap = new LinkedHashMap<String, String>();
		  for (int i = 0; i < list1.size(); i++) {
		   cleanMap.put(list1.get(i), list1.get(i));
		  }
		  List<String> list = new ArrayList<String>(cleanMap.values());
		  return list;
		 }
}
