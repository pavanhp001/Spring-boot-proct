package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.AL.ui.service.V.DialogService;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DialogueResponseType;

public enum SalesDialogueFactory {

	INSTANCE;
	private static final Logger logger = Logger.getLogger(SalesDialogueFactory.class);
	/**
	 * @param context
	 * @return
	 * @throws UnRecoverableException 
	 * @throws RecoverableException 
	 */
	public DialogueResponseType getDialoguesByContext( Map<String, Map<String, String>> dataMap) throws BaseException{
		StopWatch timer=new StopWatch();
		timer.start();
		
		DialogueResponseType dialogResponse = null;
		String key = generateDialogueCacheKey(dataMap);
		logger.info("KEY --> "+key);
		long startTimer = timer.getTime();
		Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
		if(dialogueObject != null){
			dialogResponse = (DialogueResponseType) dialogueObject;
			logger.info("TimetakenforDialougeFromCache="+(timer.getTime()-startTimer));
			timer.stop();
			return dialogResponse;
		}
		else{
			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
			dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);	
			logger.info("TimetakenforDialougeServicecall="+(timer.getTime()-startTimer));
			timer.stop();
			ConcertDialogCacheService.INSTANCE.store(key, dialogResponse);
			return dialogResponse;
		}
	}
	
	//HF
	public DialogueResponseType getDialoguesByContextNoSale( Map<String, Map<String, String>> dataMap) throws BaseException{
		StopWatch timer=new StopWatch();
		timer.start();
		DialogueResponseType dialogResponse = null;
		long startTimer = timer.getTime();
		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
		dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);	
		logger.info("TimetakenforDialougeServicecall="+(timer.getTime()-startTimer));
		timer.stop();
		return dialogResponse;
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
		return keyList;
	}
	
	/**
	 * @param context
	 * @param salesCenterVo
	 * @return
	 */
	public Map<String, Map<String, String>> updateContextMapWithReferrerAndCallType(
			String dialogueType, SalesCenterVO salesCenterVo,boolean isGUIDAvail) {
		
		 Map<String, Map<String, String>> context = new HashMap<String, Map<String,String>>();
		
		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.referrer.callType", salesCenterVo.getValueByName("referrer.callType"));
		salesFlow.put("salesFlow.dialogueType",dialogueType);
		if(isGUIDAvail){
			salesFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
		}
		context.put("salesFlow", salesFlow);
		
		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.referrer", salesCenterVo.getValueByName("referrer.businessParty.referrerId"));
		
		context.put("orderSource", orderSource);
		
		return context;
	}

}
