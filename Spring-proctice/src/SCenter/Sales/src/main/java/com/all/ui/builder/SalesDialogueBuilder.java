package com.AL.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.service.V.DialogService;
import com.AL.V.domain.SalesContext;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.V.factory.SalesContextFactory;
import com.AL.V.exception.BaseException;
import com.AL.xml.di.v4.DialogueListType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
public enum SalesDialogueBuilder {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(SalesDialogueBuilder.class);

	/**
	 * Getting the Dialogues for Warm Transfer products 
	 * 
	 * 
	 * @param providerId
	 * @return DialogueListType
	 */
	public List<DialogueType>  getWarmTransferDialogue(String providerId,Map<String, Map<String, String>> context)throws BaseException
	{	
		
		StopWatch timer=new StopWatch();
		timer.start();
		long startTimer = timer.getTime();
		
//		logger.info("Getting_Warm_Transfer_Dialogies_for_provider="+providerId);
//		List<DialogueType> dialogueList = new ArrayList<DialogueType>();
//			Map<String, Map<String, String>> dataMap = createWarmTransferContextMap(providerId,context);
//			DialogueResponseType dialogResponse = null;
//				SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
//				dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);	
//			logger.info("TimetakenforWARMTRANSFERDialougeServicecall="+(timer.getTime()-startTimer));
//			timer.stop();
//			dialogueList.addAll(dialogResponse.getResults().getDialogueList().getDialogue());
//			logger.info("Dialogue_Service_for_WarmTransferCompleted- no caching for WarmTransfer");
//			return dialogueList;
		
		List<DialogueType> dialogueList = new ArrayList<DialogueType>();
		Map<String, Map<String, String>> dataMap = createWarmTransferContextMap(providerId,context);
			DialogueResponseType dialogResponse = null;
			String key = generateDialogueCacheKey(dataMap);
			logger.info("Getting_Warm_Transfer_Dialogies_for_provider="+providerId);
			logger.info("WarmTransfer_KEY="+key);
			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
			if(dialogueObject != null){
				dialogResponse = (DialogueResponseType) dialogueObject;
				logger.info("TimetakenforWARMTRANSFERDialougeServicecallFromCache="+(timer.getTime()-startTimer));
				timer.stop();
				dialogueList.addAll(dialogResponse.getResults().getDialogueList().getDialogue());
			}
			else{
				SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
				dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);	
				logger.info("TimetakenforWARMTRANSFERDialougeServicecall="+(timer.getTime()-startTimer));
				timer.stop();
				ConcertDialogCacheService.INSTANCE.store(key, dialogResponse);
				dialogueList.addAll(dialogResponse.getResults().getDialogueList().getDialogue());
			}
			return dialogueList;			
			
	}

	/**
	 * Builds the Map for Dialogue Service request mostly used for WarmTransfer Screens
	 * 
	 * @param externalId
	 * @param GUID
	 * @return
	 */
	public Map<String, Map<String, String>> createWarmTransferContextMap(String providerId,Map<String, Map<String, String>> context) 
	{
		Map<String, String> provisioning = new HashMap<String, String>();
		provisioning.put("provisioning.businessParty", providerId);
		context.put("provisioning", provisioning);
		context.remove("orderSource");

		return context;
	}

	/**
	 * Creates the DialogueListType
	 * 
	 * 
	 * @param dialogueResponseType
	 * @return DialogueListType
	 */
	private DialogueListType buildDataFieldList(
			DialogueResponseType dialogueResponseType) {
		DialogueListType dialogueListType = new DialogueListType();
		if(dialogueResponseType !=null && dialogueResponseType.getResults()!=null){
			if(dialogueResponseType.getResults().getDialogueList() != null){
				dialogueListType = dialogueResponseType.getResults().getDialogueList();
			}
		}
		return dialogueListType;
	}
	
	/**
	 * Getting the Dialogues for Warm Transfer products 
	 * 
	 * 
	 * @param providerId
	 * @return DialogueListType
	 */
	public List<DialogueType>  getTPVDialogue(String providerId, String parentExternalId, String GUID,Map<String, Map<String, String>> context)throws BaseException{
		StopWatch timer=new StopWatch();
		timer.start();
		long startTimer = timer.getTime();		
//		logger.info("Getting_TPV_Dialogies_for_provider="+providerId);
//		List<DialogueType> dialogueList = new ArrayList<DialogueType>(); 
//			Map<String, Map<String, String>> dataMap = createTPVTransferContextMap(providerId, parentExternalId,GUID,context);
//			DialogueResponseType dialogResponse = null;
//			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
//			dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);	
//		timer.stop();
//		logger.info("TimetakenforTPVDialougeServicecall="+(timer.getTime()-startTimer));
//		dialogueList.addAll(dialogResponse.getResults().getDialogueList().getDialogue());
//		logger.info("Dialogue_Service_for_getTPVDialogue complete- no caching for TPV");
//			return dialogueList;
		
		List<DialogueType> dialogueList = new ArrayList<DialogueType>();
		Map<String, Map<String, String>> dataMap = createTPVTransferContextMap(providerId, parentExternalId,GUID,context);
			DialogueResponseType dialogResponse = null;
			String key = generateDialogueCacheKey(dataMap);
			logger.info("Getting_TPV_Dialogies_for_provider="+providerId);
			logger.info("TPV_KEY="+key);
			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
			if(dialogueObject != null){
				dialogResponse = (DialogueResponseType) dialogueObject;
				logger.info("TimetakenforTPVDialougeServicecallFromCache="+(timer.getTime()-startTimer));
				timer.stop();
				dialogueList.addAll(dialogResponse.getResults().getDialogueList().getDialogue());
			}
			else{
				SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
				dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);	
				logger.info("TimetakenforTPVDialougeServicecall="+(timer.getTime()-startTimer));
				timer.stop();
				ConcertDialogCacheService.INSTANCE.store(key, dialogResponse);
				dialogueList.addAll(dialogResponse.getResults().getDialogueList().getDialogue());
			}
			return dialogueList;	
	}

	private String generateDialogueCacheKey(Map<String, Map<String, String>> contextMap) throws BaseException {
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
		//provisioning
		keyList.add("provisioning.businessParty");
		return keyList;
	}
	
	/**
	 * Builds the Map for Dialogue Service request mostly used for WarmTransfer Screens
	 * 
	 * @param externalId
	 * @param GUID
	 * @return
	 */
	public Map<String, Map<String, String>> createTPVTransferContextMap(String providerId, String parentExternalId, 
			String GUID,Map<String, Map<String, String>> context) 
	{
		Map<String, String> provisioning = new HashMap<String, String>();
		provisioning.put("provisioning.businessParty", providerId);
		provisioning.put("provisioning.businessParty.parent", parentExternalId);
		context.put("provisioning", provisioning);
		context.remove("orderSource");

		return context;
	}

}
