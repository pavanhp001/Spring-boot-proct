package com.AL.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.AL.productResults.util.Utils;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.ui.service.V.impl.OrderCacheService;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.BaseException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

public enum CartUtil {

	INSTANCE;
	
	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartUtil.class);	

	/**
	 * @param orderId
	 * @return
	 */
	public OrderType getOrder(String orderId,String agentId,SalesContextType salesContext) 
	{
		
		OrderType order = OrderCacheService.INSTANCE.get(orderId);
		boolean isFound = Boolean.FALSE;
		retry: for (int i = 0; i < 3; i++) 
		{
			if (order == null || order.getExternalId() != 0) 
			{
				order = OrderService.INSTANCE.getOrderByOrderNumber(orderId,agentId,
						new HashMap<String, Object>(),"*", Boolean.FALSE, salesContext);
				isFound = (order != null);
				if (isFound) 
				{
					OrderCacheService.INSTANCE.store(order, orderId);
					break retry;
				}
			}
		}
		return order;
	}

	/**
	 * Validating GUID in the SalesContext .If it is Empty setting the value from Session
	 * @param agentId 
	 * @param orderId 
	 * 
	 * 
	 * @param updateSalesContext
	 * @param session
	 * @return SalesContextType
	 */
	public SalesContextType setGUIDInSalesContext(
			String orderId, String agentId, SalesContextType updateSalesContext, HttpSession session) 
	{
		String GUID = (String) session.getAttribute("GUID");
		if(!Utils.isBlank(GUID)){
			for(SalesContextEntityType entity : updateSalesContext.getEntity())
			{
				if(entity != null && entity.getName() != null && entity.getName().equalsIgnoreCase("CKO"))
				{
					for(NameValuePairType attribute : entity.getAttribute())
					{
						if(attribute.getName() != null && attribute.getName().equalsIgnoreCase("GUID")
								&& Utils.isBlank(attribute.getValue()))
						{
								attribute.setValue(GUID);
								OrderService.INSTANCE.updateSalesContext(agentId, orderId, updateSalesContext, GUID);
								break;
						}
					}
				}
			}
		}
		
		return updateSalesContext;
	}
	
	/**
	 * Gets the agentId from SalesCenterVo
	 * 
	 * @return String
	 */
	public String getAgentId(HttpSession session)
	{
		SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
		String agentId = salesCenterVo.getValueByName("agent.id");
		//TODO:Get the agentId from SessionCache
		//String agentId = SessionCache.INSTANCE.getAgentId(request.getSession());
		if(agentId == null)
		{
			agentId = "default";	
		}
		logger.info("agentId="+agentId);
		return agentId;
	}
	
	 /**
     * Returns the Time Difference
     * 
     * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double getTimeDiff(Double startTime)
	{
		Double endTime = Double.valueOf(System.currentTimeMillis());
		return (endTime - startTime);
	}
	
	/**
	 * Returns GUID 
	 * 
	 * 
	 * @param salesContext
	 * @return returns GUID from SalesContext
	 */
	public String getGuid(SalesContextType salesContext)
	{
		
		String GUID = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		
		if(Utils.isBlank(GUID))
		{
			GUID = UUID.randomUUID().toString();
		}
		
		return GUID;
	}
	

	public static String generateDialogueCacheKey(Map<String, Map<String, String>> contextMap, String providerId, String programExternalId) throws BaseException {
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
			if (!Utils.isBlank(providerId)){
				strBur.append(providerId);
			}
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

			logger.info("Generated dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}	
	
	public static String generateDialogueCacheKey(Map<String, Map<String, String>> contextMap, String providerId) throws BaseException {
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
			if (!Utils.isBlank(providerId)){
				strBur.append(providerId);
			}
			strBur.append(pageName);
			strBur.append("_");
			strBur.append(dynamicFlow.get("dynamicFlow.userGroup"));
			strBur.append("_");
			
			if (!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("referrerSpecificConfirm") || flowType.equalsIgnoreCase("referrerSpecificNonConfirm") 
					|| flowType.equalsIgnoreCase("customerLookup"))){
				strBur.append(orderSource.get("orderSource.referrer"));
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

			logger.info("Generated dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}
	
	
	public static String generateDialogueBackupDefaultUserGroupDrupal(Map<String, Map<String, String>> contextMap, String providerId) throws BaseException {
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
			if (!Utils.isBlank(providerId)){
				strBur.append(providerId);
			}
			strBur.append(pageName);
			strBur.append("_");
			strBur.append("defaultUserGroup");
			strBur.append("_");
			
			if (!Utils.isBlank(flowType) && (flowType.equalsIgnoreCase("referrerSpecificConfirm") || flowType.equalsIgnoreCase("referrerSpecificNonConfirm") 
					|| flowType.equalsIgnoreCase("customerLookup"))){
				strBur.append(orderSource.get("orderSource.referrer"));
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

			logger.info("Generated dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}
	
	public static String generateDialogueBackupDefaultUserGroupDrupal(Map<String, Map<String, String>> contextMap, String providerId, String programExternalId) throws BaseException {
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
			if (!Utils.isBlank(providerId)){
				strBur.append(providerId);
			}
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

			logger.info("Generated dialogue cache key="+strBur.toString());
			return strBur.toString();
		}
		return null;
	}

	
	public static String escapeSpecialCharacters(String str){
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

 			//this is for left tag
 			str = str.replaceAll("&lt;", "<");

 			//this is for right tag
 			str = str.replaceAll("&gt;", ">");
 		}
 		return str;
 	}

	public static String dialoguesByContextForDrupal(Map<String, Map<String, String>> contextMap, String providerId)throws BaseException {
		StopWatch timer=new StopWatch();
		String dialogueVO = null;
		String key = generateDialogueCacheKey(contextMap,providerId);
		timer.start();
		Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(key);
		if(dialogueObject != null)
		{
			dialogueVO = (String) dialogueObject;
			logger.info("TimetakenforDialougeFromCache="+ timer.getTime());
		}
		timer.stop();
		return dialogueVO;
	}
	
	public String getDialoguesByContextDefaultUserGroupForDrupal(Map<String, Map<String, String>> contextMap, String providerId)throws BaseException {
		StopWatch timer=new StopWatch();
		String dialogueVO = null;
		String key = generateDialogueBackupDefaultUserGroupDrupal(contextMap,providerId);
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
	
	public static void storeDialoguesByContextForDrupalDefaultUserGroup(Map<String, Map<String, String>> contextMap, String value, String providerId)throws BaseException {
		String key = generateDialogueBackupDefaultUserGroupDrupal(contextMap,providerId);
		ConcertDialogCacheService.INSTANCE.store(key, value);
	}	
	public static void storeDialoguesByContextForDrupal(Map<String, Map<String, String>> contextMap, String value, String providerId)throws BaseException {
		String key = generateDialogueCacheKey(contextMap,providerId);
		ConcertDialogCacheService.INSTANCE.store(key, value);
	}
}
