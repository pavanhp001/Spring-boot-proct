package com.AL.ui.service.V;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.AL.productResults.util.Utils;
import com.AL.ui.builder.DialogueVOBuilder;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataFieldMatrix;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.mapper.DialogueMapper;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DialogueListType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.di.v4.NameValuePairType;
import com.AL.xml.di.v4.DialogueResponseType.Results;

public enum DialogServiceUI {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(DialogServiceUI.class);

	public SalesDialogueVO getSampleDialoguesByProductId(String productId){
		DialogueListType dialogues = getSTATICSampleDialogues();
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogues);
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO(),productId);
		return dialogueVO;
	}

	public SalesDialogueVO getDialoguesByProductId(String productId) throws BaseException{
		Map<String, Map<String, String>> dataMap = getData(productId);

		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);

		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);

		//DialogCacheService.INSTANCE.store(dialogResponse, productId);

		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO(),productId);

		return dialogueVO;
	}

	/*public SalesDialogueVO getDialoguesByProductIdFromCache(String productId){

		DialogueResponseType dialogResponse = DialogCacheService.INSTANCE.getFromCache(productId);
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO(),productId);

		return dialogueVO;
	}*/

	public DialogueListType getSTATICSampleDialogues(){
		String xmlInput = DialogServiceUI.INSTANCE.readFileToString(new File(".\\src\\main\\resources\\xml\\UverseTripplePlay-Dialogues.xml"));
		Results result = toObject(xmlInput);
		DialogueListType dialogues = result.getDialogueList();

		return dialogues;
	}

	public String readFileToString(File aFileName) {
		StringBuffer sb = new StringBuffer();
		String line = null;

		try {
			FileReader inputFile = new FileReader(aFileName);
			BufferedReader inputBuffer = new BufferedReader(inputFile);

			while ((line = inputBuffer.readLine()) != null)
				sb.append(line);

			inputBuffer.close();
			inputFile.close();

		}
		// Catches any error conditions
		catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public Results toObject(String xmlInput)  {
		Results result = null;
		StringReader sr = null;
		try {
			JAXBContext orderTypeJxbContext = JAXBContext.newInstance(Results.class);
			sr = new StringReader( xmlInput );
			Unmarshaller unmarshaller = orderTypeJxbContext.createUnmarshaller();
			JAXBElement<Results> b = unmarshaller.unmarshal(new StreamSource(sr),	Results.class);
			result = b.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sr.close();
		}
		return result;
	}

	public Map<String, Map<String, String>> getData(String externalId) {
		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();
		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", externalId);

		context.put("salesFlow", salesFlow);
		return context;
	}

	public Map<String, Map<String, String>> getData(String dialogueType, String contextIndicator) {

		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();
		Map<String, String> salesFlow = new HashMap<String, String>();
		if(dialogueType.equals("BasicInformation")){
			if((contextIndicator.equals("00")) || (contextIndicator.equals("05"))){
		        salesFlow.put("salesFlow.dialogueType", dialogueType);
		        salesFlow.put("salesFlow.forceNonConfirm", "true");
			}
			else{
				salesFlow.put("salesFlow.dialogueType", dialogueType);
			}
		}
		else{
			salesFlow.put("salesFlow.dialogueType", dialogueType);
		    salesFlow.put("salesFlow.contextId", contextIndicator);
		}
		context.put("salesFlow", salesFlow);
		return context;
	}

	public SalesDialogueVO getDialoguesByContext(String context,NameValuePairType nvPairObj)throws RecoverableException,UnRecoverableException{
		Map<String, Map<String, String>> dataMap = getData(context);

		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
		salesContext.getEntity().get(0).getAttribute().add(nvPairObj);

		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);

		//DialogCacheService.INSTANCE.store(dialogResponse, productId);

		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());

		dialogueVO.setDialogueList(dialogueList);

		return dialogueVO;
	}

	/*public SalesDialogueVO getDialoguesByContext(String context, String contextIndicator){

		SalesDialogueVO salesDialogueVO = getDialoguesByContext(String context, String contextIndicator);
		salesDialogueVO.


		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);

		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);

		//DialogCacheService.INSTANCE.store(dialogResponse, productId);

		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());

		dialogueVO.setDialogueList(dialogueList);

		return dialogueVO;
	}	*/


	public SalesDialogueVO getDialoguesByContext(String dialogueType, String contextIndicator)throws BaseException{
		Map<String, Map<String, String>> dataMap = getData(dialogueType, contextIndicator);

		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);

		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);

		//DialogCacheService.INSTANCE.store(dialogResponse, productId);

		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());

		dialogueVO.setDialogueList(dialogueList);

		return dialogueVO;
	}

	/**
	 *
	 * Get Dialogues by Map object
	 *
	 * @param contextMap
	 * @return SalesDialogueVO
	 * @throws BaseException
	 *
	 */
	public SalesDialogueVO getDialoguesByContext(Map<String, Map<String, String>> contextMap)throws BaseException {
		StopWatch timer=new StopWatch();
		SalesDialogueVO dialogueVO = null;
		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(contextMap);
		String key = generateDialogueCacheKey(contextMap);
		timer.start();
		Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
		if(dialogueObject != null)
		{
			dialogueVO = (SalesDialogueVO) dialogueObject;
			logger.info("TimetakenforDialougeFromCache="+ timer.getTime());
		}
		else 
		{
			DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);
			List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
			dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());
			logger.info("TimetakenforDialougeServicecall="+timer.getTime());
			dialogueVO.setDialogueList(dialogueList);
			ConcertDialogCacheService.INSTANCE.store(key, dialogueVO);
		}
		timer.stop();
		return dialogueVO;
	}

	//HF
	public SalesDialogueVO getDialoguesByContextNoSale(Map<String, Map<String, String>> contextMap)throws BaseException {
		StopWatch timer=new StopWatch();
		SalesDialogueVO dialogueVO = null;
		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(contextMap);
		timer.start();
		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());
		logger.info("TimetakenforDialougeServicecall="+timer.getTime());
		dialogueVO.setDialogueList(dialogueList);
		timer.stop();
		return dialogueVO;
	}

	
	public String generateDialogueCacheKey(Map<String, Map<String, String>> contextMap) throws BaseException {
		logger.info("Generating dialogue cache key from contextMap="+contextMap.toString());
		
		StringBuilder strBur = new StringBuilder();
		if (contextMap != null && !contextMap.isEmpty()) {
			for (Entry<String, Map<String, String>> entry : contextMap.entrySet()) {
				for (String str :getUniqueKeyElements()){
					if (entry.getValue().get(str) != null){
						strBur.append(entry.getValue().get(str));
						strBur.append("_");
					}
				}
			}
			if (strBur.length() > 0) {
				String key = strBur.substring(0, strBur.length() - 1);
				logger.info("Generated dialogue cache key="+key);
				return key;
			}
		}
		return null;
	}

	public static List<String> getUniqueKeyElements(){
		List<String> keyList = new ArrayList<String>();
		//dynamicFlow
		keyList.add("dynamicFlow.flowType");
		keyList.add("dynamicFlow.page");
		keyList.add("dynamicFlow.userGroup");
		keyList.add("dynamicFlow.enabled");
		keyList.add("dynamicFlow.appVersion");
		
		//salesFlow
		keyList.add("salesFlow.contextId");
		keyList.add("salesFlow.forceNonConfirm");
		keyList.add("salesFlow.requiresCSAT");
		keyList.add("salesFlow.dialogueType");
		//orderSource
		keyList.add("orderSource.referrer");
		return keyList;
	}

	public DialogueResponseType getQualificationDialogue(Map<String, Map<String, String>> contextMap)throws BaseException
	{
		
		StopWatch timer=new StopWatch();
		DialogueResponseType dialogueResponseType = null;
		String key = generateDialogueCacheKey(contextMap);
		timer.start();
		Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
		if(dialogueObject != null)
		{
			dialogueResponseType = (DialogueResponseType) dialogueObject;
			logger.info("TimetakenforDialougeFromCache="+ timer.getTime());
		}
		else{
			dialogueResponseType = SalesDialogueFactory.INSTANCE.getDialoguesByContext(contextMap);
			List<DialogueType> dialogueVOList = new ArrayList<DialogueType>();
			dialogueVOList.addAll(dialogueResponseType.getResults().getDialogueList().getDialogue());
			logger.info("TimetakenforDialougeServicecall="+timer.getTime());
			ConcertDialogCacheService.INSTANCE.store(key, dialogueResponseType);
		}
		return dialogueResponseType;
	}


	/**
	 *
	 * @param contextMap
	 * @param isValid
	 * @param matrixList
	 * @param dfList
	 * @return SalesDialogueVO
	 * @throws BaseException
	 *
	 */
	public SalesDialogueVO getDialoguesByContext(Map<String, Map<String, String>> contextMap,boolean isValid, List<DataFieldMatrix> matrixList, List<DataField> dfList)throws BaseException {
		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(contextMap);
		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		if(dialogueList != null){
			for(Dialogue dialogue : dialogueList){
				for(DataGroup dataGroup : dialogue.getDataGroupList()){
					dfList.addAll(dataGroup.getDataFieldList());
					matrixList.addAll(dataGroup.getDataFieldMatrixList());
				}
			}
		}
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());

		dialogueVO.setDialogueList(dialogueList);

		return dialogueVO;
	}
   
	public String getDialoguesByContextForDrupal(Map<String, Map<String, String>> contextMap,String programID)throws BaseException {
		StopWatch timer=new StopWatch();
		String dialogueVO = null;
		String key = generateDialogueCacheKeyForDrupal(contextMap,programID);
		timer.start();
		Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
		if(dialogueObject != null)
		{
			dialogueVO = String.valueOf(dialogueObject);
			logger.info("TimetakenforDialougeFromCache="+ timer.getTime());
		}
		timer.stop();
		return dialogueVO;
	}
	
	public String getDialoguesByContextDefaultUserGroupForDrupal(Map<String, Map<String, String>> contextMap)throws BaseException {
		StopWatch timer=new StopWatch();
		String dialogueVO = null;
		String key = generateDialogueBackupDefaultUserGroupDrupal(contextMap);
		timer.start();
		Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
		if(dialogueObject != null)
		{
			dialogueVO = String.valueOf(dialogueObject);
			logger.info("TimetakenforDialougeFromCache="+ timer.getTime());
		}
		timer.stop();
		return dialogueVO;
	}	
	public void storeDialoguesByContextForDrupal(Map<String, Map<String, String>> contextMap, String value,String programID)throws BaseException {
		String key = generateDialogueCacheKeyForDrupal(contextMap,programID);
		ConcertDialogCacheService.INSTANCE.store(key, value);
	}
	public void storeDialoguesByContextForDrupalDefaultUserGroup(Map<String, Map<String, String>> contextMap, String value)throws BaseException {
		String key = generateDialogueBackupDefaultUserGroupDrupal(contextMap);
		ConcertDialogCacheService.INSTANCE.store(key, value);
	}	
	
	public static List<String> getUniqueKeyElementsForDrupal(){
		List<String> keyList = new ArrayList<String>();
		//dynamicFlow
		//FlowType_PageName_GroupName_RefId_ContextId_ForceNonConfirm_RequiresCSAT
		keyList.add("dynamicFlow.flowType");
		keyList.add("dynamicFlow.page");
		keyList.add("dynamicFlow.userGroup");

		return keyList;
	}
	
	public String generateDialogueCacheKeyForDrupal(Map<String, Map<String, String>> contextMap) throws BaseException {
		logger.info("Generating dialogue cache key from contextMap="+contextMap.toString());
		
		StringBuilder strBur = new StringBuilder();
		if (contextMap != null && !contextMap.isEmpty()) {
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			Map<String, String> orderSource = contextMap.get("orderSource");
			Map<String, String> salesFlow = contextMap.get("salesFlow");

			String pageName = dynamicFlow.get("dynamicFlow.page");
			String flowType = dynamicFlow.get("dynamicFlow.flowType");

			strBur.append(flowType);
			strBur.append("_");
			strBur.append(pageName);
			strBur.append("_");
			strBur.append(dynamicFlow.get("dynamicFlow.userGroup"));
			strBur.append("_");
			
			if (!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("referrerSpecificConfirm") || flowType.equalsIgnoreCase("referrerSpecificNonConfirm") 
					|| flowType.equalsIgnoreCase("customerLookup"))){
				strBur.append(orderSource.get("orderSource.referrer"));
				strBur.append("_");
			}else if(!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("consumersInteractions") 
					&& (pageName.equalsIgnoreCase("NewScriptedQualification")
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("QualificationMoveInDelta") 
					|| pageName.equalsIgnoreCase("DiscoveryTransfer") 
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("DiscoveryMoveIn")
					|| pageName.equalsIgnoreCase("Recommendations")))){
				strBur.append(dynamicFlow.get("rentown"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && pageName.equalsIgnoreCase("Greeting")){
				strBur.append(salesFlow.get("salesFlow.contextId"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && (pageName.equalsIgnoreCase("BasicInformation") 
					              || pageName.equalsIgnoreCase("NewScriptedQualification")
					                  || pageName.equalsIgnoreCase("QualificationMoveInDelta"))){
				strBur.append(salesFlow.get("salesFlow.forceNonConfirm"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}
			if (!Utils.isBlank(pageName) && pageName.toLowerCase().contains("closecall")){
				if (salesFlow.get("salesFlow.requiresCSAT") != null){
					strBur.append(salesFlow.get("salesFlow.requiresCSAT"));	
				}else{
					strBur.append("false");
				}
			}else{
				strBur.append("N");
			}

			logger.info("Generated dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}
	
	public String generateDialogueCacheKeyForDrupal(Map<String, Map<String, String>> contextMap, String programExternalId) throws BaseException {
		logger.info("Generating dialogue cache key from contextMap="+contextMap.toString());
		
		StringBuilder strBur = new StringBuilder();
		if (contextMap != null && !contextMap.isEmpty()) {
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			Map<String, String> orderSource = contextMap.get("orderSource");
			Map<String, String> salesFlow = contextMap.get("salesFlow");

			String pageName = dynamicFlow.get("dynamicFlow.page");
			String flowType = dynamicFlow.get("dynamicFlow.flowType");

			strBur.append(flowType);
			strBur.append("_");
			strBur.append(pageName);
			strBur.append("_");
			strBur.append(dynamicFlow.get("dynamicFlow.userGroup"));
			strBur.append("_");
			
			if (!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("referrerSpecificConfirm") || flowType.equalsIgnoreCase("referrerSpecificNonConfirm") 
					|| flowType.equalsIgnoreCase("customerLookup"))){
				if(!Utils.isBlank(programExternalId)) {
					strBur.append(programExternalId);
				} else {
					strBur.append(orderSource.get("orderSource.referrer"));
				}
				strBur.append("_");
			}else if(!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("consumersInteractions") 
					&& (pageName.equalsIgnoreCase("NewScriptedQualification")
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("QualificationMoveInDelta") 
					|| pageName.equalsIgnoreCase("DiscoveryTransfer") 
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("DiscoveryMoveIn")
					|| pageName.equalsIgnoreCase("Recommendations")))){
				strBur.append(dynamicFlow.get("rentown"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && pageName.equalsIgnoreCase("Greeting")){
				strBur.append(salesFlow.get("salesFlow.contextId"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && (pageName.equalsIgnoreCase("BasicInformation") 
					              || pageName.equalsIgnoreCase("NewScriptedQualification")
					                  || pageName.equalsIgnoreCase("QualificationMoveInDelta"))){
				strBur.append(salesFlow.get("salesFlow.forceNonConfirm"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}
			if (!Utils.isBlank(pageName) && pageName.toLowerCase().contains("closecall")){
				if (salesFlow.get("salesFlow.requiresCSAT") != null){
					strBur.append(salesFlow.get("salesFlow.requiresCSAT"));	
				}else{
					strBur.append("false");
				}
			}else{
				strBur.append("N");
			}

			logger.info("Generated dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}
	
	public String generateDialogueBackupDefaultUserGroupDrupal(Map<String, Map<String, String>> contextMap) throws BaseException {
		logger.info("Generating BACKUP dialogue cache key from contextMap="+contextMap.toString());
		
		StringBuilder strBur = new StringBuilder();
		if (contextMap != null && !contextMap.isEmpty()) {
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			Map<String, String> orderSource = contextMap.get("orderSource");
			Map<String, String> salesFlow = contextMap.get("salesFlow");

			String pageName = dynamicFlow.get("dynamicFlow.page");
			String flowType = dynamicFlow.get("dynamicFlow.flowType");

			strBur.append(flowType);
			strBur.append("_");
			strBur.append(pageName);
			strBur.append("_");
			strBur.append("defaultUserGroup");
			strBur.append("_");
			
			if (!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("referrerSpecificConfirm") || flowType.equalsIgnoreCase("referrerSpecificNonConfirm") 
					|| flowType.equalsIgnoreCase("customerLookup"))){
					strBur.append(orderSource.get("orderSource.referrer"));
					strBur.append("_");
			}else if(!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("consumersInteractions") 
					&& (pageName.equalsIgnoreCase("NewScriptedQualification")
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("QualificationMoveInDelta") 
					|| pageName.equalsIgnoreCase("DiscoveryTransfer") 
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("DiscoveryMoveIn")
					|| pageName.equalsIgnoreCase("Recommendations")))){
				strBur.append(dynamicFlow.get("rentown"));
				strBur.append("_");
			}
			else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && pageName.equalsIgnoreCase("Greeting")){
				strBur.append(salesFlow.get("salesFlow.contextId"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && (pageName.equalsIgnoreCase("BasicInformation") || pageName.equalsIgnoreCase("NewScriptedQualification"))){
				strBur.append(salesFlow.get("salesFlow.forceNonConfirm"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}
			if (!Utils.isBlank(pageName) && pageName.toLowerCase().contains("closecall")){
				if (salesFlow.get("salesFlow.requiresCSAT") != null){
					strBur.append(salesFlow.get("salesFlow.requiresCSAT"));	
				}else{
					strBur.append("false");
				}
			}else{
				strBur.append("N");
			}

			logger.info("Generated BACKUP dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}		
	
	public String generateDialogueBackupDefaultUserGroupDrupal(Map<String, Map<String, String>> contextMap, String programExternalId) throws BaseException {
		logger.info("Generating BACKUP dialogue cache key from contextMap="+contextMap.toString());
		
		StringBuilder strBur = new StringBuilder();
		if (contextMap != null && !contextMap.isEmpty()) {
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			Map<String, String> orderSource = contextMap.get("orderSource");
			Map<String, String> salesFlow = contextMap.get("salesFlow");

			String pageName = dynamicFlow.get("dynamicFlow.page");
			String flowType = dynamicFlow.get("dynamicFlow.flowType");

			strBur.append(flowType);
			strBur.append("_");
			strBur.append(pageName);
			strBur.append("_");
			strBur.append("defaultUserGroup");
			strBur.append("_");
			
			if (!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("referrerSpecificConfirm") || flowType.equalsIgnoreCase("referrerSpecificNonConfirm") 
					|| flowType.equalsIgnoreCase("customerLookup"))){
				if(!Utils.isBlank(programExternalId)) {
					strBur.append(programExternalId);
				} else {
					strBur.append(orderSource.get("orderSource.referrer"));
				}
				strBur.append("_");
			}else if(!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("consumersInteractions") 
					&& (pageName.equalsIgnoreCase("NewScriptedQualification")
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("QualificationMoveInDelta") 
					|| pageName.equalsIgnoreCase("DiscoveryTransfer") 
					|| pageName.equalsIgnoreCase("DiscoveryNew") 
					|| pageName.equalsIgnoreCase("DiscoveryMoveIn")
					|| pageName.equalsIgnoreCase("Recommendations")))){
				strBur.append(dynamicFlow.get("rentown"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && pageName.equalsIgnoreCase("Greeting")){
				strBur.append(salesFlow.get("salesFlow.contextId"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}

			if (!Utils.isBlank(pageName) && (pageName.equalsIgnoreCase("BasicInformation") || pageName.equalsIgnoreCase("NewScriptedQualification"))){
				strBur.append(salesFlow.get("salesFlow.forceNonConfirm"));
				strBur.append("_");
			}else{
				strBur.append("N");
				strBur.append("_");
			}
			if (!Utils.isBlank(pageName) && pageName.toLowerCase().contains("closecall")){
				if (salesFlow.get("salesFlow.requiresCSAT") != null){
					strBur.append(salesFlow.get("salesFlow.requiresCSAT"));	
				}else{
					strBur.append("false");
				}
			}else{
				strBur.append("N");
			}

			logger.info("Generated BACKUP dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}	
	
	

}
