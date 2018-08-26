package com.AL.ui.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


import com.AL.controller.RESTClient;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.MDUOrderSource;
import com.AL.ui.domain.MDUProperty;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.ui.service.V.impl.MDUCacheService;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.BaseException;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;



public enum SalesUtil {
	
	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(SalesUtil.class);
	public  final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z (Z)");
	 public final String[] metaDataKeyArray = {"CONTRACT_TERM","NUM_CHANNELS","CONN_SPEED","LATINO_PKG_INCL"};
	/**
	 * @param customer
	 * @param key
	 * @return CustAddress
	 */
	public com.AL.xml.v4.CustAddress getAddress(Customer customer, final String key) {
		boolean isRole = Boolean.FALSE;
		if ((customer != null) && (customer.getAddressList() != null)) {
			List<com.AL.xml.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (com.AL.xml.v4.CustAddress custAddress : custAddressList) {
					if ((custAddress != null)
							&& (custAddress.getAddressRoles() != null)) {
						List<com.AL.xml.v4.RoleType> roleTypeList = custAddress.getAddressRoles().getRole();
						for (com.AL.xml.v4.RoleType roleType : roleTypeList) {
							if (key.equals(roleType.name())) {
								isRole = Boolean.TRUE;
								break;
							}
						}
						if (isRole) {
							return custAddress;
						}
					}
				}
			}
		}
		return new com.AL.xml.v4.CustAddress();
	}

	public String parseHtmlTags(String dataFieldText) {
		dataFieldText = dataFieldText.replaceAll("&amp;", "&");
		dataFieldText = dataFieldText.replaceAll("&lt;", "<");
		dataFieldText = dataFieldText.replaceAll("&gt;", ">");
		return dataFieldText;
	}
	
	
  private  Date fromString(String dateString, String formatTo) {
		if ((dateString == null) || (formatTo == null)) {
			return null;
		}
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat(formatTo);
			date = (Date) formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		  }
		return null;
	}
	public  Date fromStringToDate(String dateString, String dateFormat) {
		return fromString(dateString, "MM/dd/yyyy");
	}
	
     public  String fromDate(Date date, String formatTo) {
		if ((date == null) || (formatTo == null)) {
			return null;
		}
		try {
			DateFormat formatter = new SimpleDateFormat(formatTo);
			String s = formatter.format(date);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		  }
		return null;
	}
     
     public static String getDialoguesFormDrupalContent(
 			Map<String, Map<String, String>> contextMap, SalesCenterVO salesCenterVo) {
 		String dialoguesFromDrupal = null;
 		try {
 			dialoguesFromDrupal = DialogServiceUI.INSTANCE.getDialoguesByContextForDrupal(contextMap,salesCenterVo.getValueByName("ordersource.programId"));
 			salesCenterVo.setValueByName("drupalDialogueCacheKey", DialogServiceUI.INSTANCE.generateDialogueCacheKeyForDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId")));
 			if (Utils.isBlank(dialoguesFromDrupal)) {
 				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueCacheKeyForDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
 						, salesCenterVo.getValueByName("drupalContentUrl"));
 			}
 			if (Utils.isBlank(dialoguesFromDrupal)){
 	 			dialoguesFromDrupal = DialogServiceUI.INSTANCE.getDialoguesByContextDefaultUserGroupForDrupal(contextMap);
 	 			if (!Utils.isBlank(dialoguesFromDrupal)){
 	 	 			salesCenterVo.setValueByName("drupalDialogueCacheKey", DialogServiceUI.INSTANCE.generateDialogueBackupDefaultUserGroupDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId")));
 	 	 			logger.info("No drupal content for group reverting to defaultUserGroup=" + salesCenterVo.getValueByName("drupalDialogueCacheKey"));
 	 			} else {
	 	 			dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueBackupDefaultUserGroupDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
							,salesCenterVo.getValueByName("drupalContentUrl"));
					DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupalDefaultUserGroup(contextMap,dialoguesFromDrupal); 	
					logger.info("No drupal content for group reverting to defaultUserGroup=" + salesCenterVo.getValueByName("drupalDialogueCacheKey"));
 	 			}
 			} else {
 				DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupal(contextMap,dialoguesFromDrupal,salesCenterVo.getValueByName("ordersource.programId"));
 			}
 		} catch (BaseException be) {
 			logger.error("error_in_getDialoguesFormDrupalContent",be);
 		} catch (Exception e) {
 			logger.error("error_in_getDialoguesFormDrupalContent",e);
 		}
 		dialoguesFromDrupal = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(dialoguesFromDrupal)));
 		return dialoguesFromDrupal;
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

 			//this is for left tag
 			str = str.replaceAll("&lt;", "<");

 			//this is for right tag
 			str = str.replaceAll("&gt;", ">");
 		}
 		return str;
 	}
     
     public String getDialoguesFormDrupalContentForWTAndTPV(Map<String, Map<String, String>> dynamicFlowContextMap, 
    		 SalesCenterVO salesCenterVo,String providerId, String page) {
  		String dialoguesFromDrupal = null;
  		StringBuilder strBur = new StringBuilder();
  		try {
  			if (dynamicFlowContextMap != null && !dynamicFlowContextMap.isEmpty()) {
  				Map<String, String> dynamicFlow = dynamicFlowContextMap.get("dynamicFlow");

  				strBur.append(dynamicFlow.get("dynamicFlow.flowType"));
  				strBur.append("_");
  				strBur.append(providerId);
  				strBur.append(page);
  				strBur.append("_");
  				strBur.append(dynamicFlow.get("dynamicFlow.userGroup"));
  				strBur.append("_");
  				strBur.append("N");
  				strBur.append("_");
  				strBur.append("N");
  				strBur.append("_");
  				strBur.append("N");
  				strBur.append("_");
  				strBur.append("N");
  			}
  			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(strBur.toString());
  			if(dialogueObject != null)
  			{
  				dialoguesFromDrupal = String.valueOf(dialogueObject);
  			}
  			salesCenterVo.setValueByName("drupalDialogueCacheKey", strBur.toString());
  			if (Utils.isBlank(dialoguesFromDrupal)){
  				//String drupalContentUrl = "http://dpldev001.AL.com/"; 
  				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(strBur.toString(), salesCenterVo.getValueByName("drupalContentUrl"));
  	 			if (Utils.isBlank(dialoguesFromDrupal)){
  	 	 			dialoguesFromDrupal = DialogServiceUI.INSTANCE.getDialoguesByContextDefaultUserGroupForDrupal(dynamicFlowContextMap);
  	 	 			if (!Utils.isBlank(dialoguesFromDrupal)){
  	 	 	 			salesCenterVo.setValueByName("drupalDialogueCacheKey", DialogServiceUI.INSTANCE.generateDialogueBackupDefaultUserGroupDrupal(dynamicFlowContextMap));
  	 	 	 			logger.info("No drupal content for group reverting to defaultUserGroup=" + salesCenterVo.getValueByName("drupalDialogueCacheKey"));
  	 	 			} else {
  		 	 			dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueBackupDefaultUserGroupDrupal(dynamicFlowContextMap)
  								,salesCenterVo.getValueByName("drupalContentUrl"));
  						DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupalDefaultUserGroup(dynamicFlowContextMap,dialoguesFromDrupal); 	
  						logger.info("No drupal content for group reverting to defaultUserGroup=" + salesCenterVo.getValueByName("drupalDialogueCacheKey"));
  	 	 			}
  	 			} else {
  	 				ConcertDialogCacheService.INSTANCE.store(strBur.toString(), dialoguesFromDrupal);
  	  				logger.info("dialogues--"+dialoguesFromDrupal);
  	 			}
  				
  			}
  		} catch (BaseException be) {
  			logger.error("error_in_getDialoguesFormDrupalContentForWTAndTPV",be);
  		} catch (Exception e) {
  			logger.error("error_in_getDialoguesFormDrupalContentForWTAndTPV",e);
  		}
  		dialoguesFromDrupal = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(dialoguesFromDrupal)));
  		return dialoguesFromDrupal;
  	}

 	public void getMduProperties(String mdu_url){
 		try {
 			String allMDUProgramExternalIds = RESTClient.INSTANCE.getAllMDUProgramExternalIds(mdu_url+"getAllMDUProgramExternalIds");
 			logger.info("AllMDUProgramExternalIds="+allMDUProgramExternalIds);
 			JSONArray jsonArray = new JSONArray(allMDUProgramExternalIds);
			Map<Integer,List<MDUOrderSource>> progExtIdMDUPropertiesMap = new HashMap<Integer,List<MDUOrderSource>>();
			Map<Integer,Map<String,List<MDUProperty>>> stateExtIdMDUMap = new HashMap<Integer,Map<String,List<MDUProperty>>>();
 			Map<Integer,Integer> allMDUProgramExternalIdsMap = new HashMap<Integer, Integer>();
 			for(int i=0;i<jsonArray.length();i++){
 				org.json.JSONObject programJson = (org.json.JSONObject) jsonArray.get(i);
 				allMDUProgramExternalIdsMap.put((Integer) programJson.get("programId"),programJson.getInt("programExternalId"));
 			}
 			logger.info("allMDUProgramExternalIdsMap="+allMDUProgramExternalIdsMap);
 			for (Entry<Integer, Integer> entry : allMDUProgramExternalIdsMap.entrySet()) {
				progExtIdMDUPropertiesMap.put(entry.getValue(), RESTClient.INSTANCE.getMDUProperties(mdu_url+"getProperties?",entry.getKey()));
			}
			for(Entry<Integer,List<MDUOrderSource>> progExtPropMap: progExtIdMDUPropertiesMap.entrySet()){
				if(progExtPropMap.getValue() != null && ! progExtPropMap.getValue().isEmpty()){
					Map<String,List<MDUProperty>> stateMDUMap = new HashMap<String,List<MDUProperty>>();
					for(MDUOrderSource mduOrderSource : progExtPropMap.getValue()){
		 				for(MDUProperty mduPropeties : mduOrderSource.getMduProperties()){
		 					if(stateMDUMap.get(mduPropeties.getState()) == null){
		 						List<MDUProperty> mduProList = new ArrayList<MDUProperty>();
		 						mduProList.add(mduPropeties);
		 						stateMDUMap.put(mduPropeties.getState(), mduProList);
		 					}else{
		 						stateMDUMap.get(mduPropeties.getState()).add(mduPropeties);
		 					}
		 				}
					}
					for(Entry<String, List<MDUProperty>> mduMap: stateMDUMap.entrySet()){
						Collections.sort(mduMap.getValue(), new Comparator<MDUProperty>() {
							public int compare(MDUProperty o1, MDUProperty o2) {
								String name1 = null;
								if(o1.getName() != null) {
									name1 = o1.getName();
								}
								String name2 = null;
								if(o2.getName() != null) {
									name2 = o2.getName();
								}
								if(name1 == null && name2 == null) {
									return 0;
								}
								else if(name1 == null) {
									return -1;
								}
								else if (name2 == null){
									return 1;
								}
								else {
									return name1.compareTo(name2);
								}
							}
						});
					}
					stateExtIdMDUMap.put(progExtPropMap.getKey(), stateMDUMap);
				}
			}
			MDUCacheService.INSTANCE.store(stateExtIdMDUMap);
 		} catch (Exception e) {
 			logger.error("Error in Loading MDU properties",e);
 		}
 	}
	public static String getDrupalContentForHints(Map<String, String> dynamicFlowContextMap, SalesCenterVO salesCenterVo) {
 		String dialoguesFromDrupal = null;
 		StringBuilder strBur = new StringBuilder();
 		try {
 			if (dynamicFlowContextMap != null && !dynamicFlowContextMap.isEmpty()) {
 				String dynamicFlow = dynamicFlowContextMap.get("dynamicFlow.flowType");
 				String defaultFlowKey = "salescenter_default_objection_busters";
 				if(!Utils.isBlank(dynamicFlow)){
 					strBur.append(dynamicFlow).append("_").append("objection").append("_").append("busters"); 
 				}
 				logger.info("drupalDialogueCacheKey for Hints"+strBur.toString());
 				Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(strBur.toString());
 				if(dialogueObject != null)
 				{
 					dialoguesFromDrupal = String.valueOf(dialogueObject);
 				}
 				if (Utils.isBlank(dialoguesFromDrupal)){
 					dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(strBur.toString(), salesCenterVo.getValueByName("drupalContentUrl"));
 					if (! Utils.isBlank(dialoguesFromDrupal)){
 						ConcertDialogCacheService.INSTANCE.store(strBur.toString(), dialoguesFromDrupal);
 					}
 					else{
 						dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultFlowKey);
 						if(dialogueObject != null)
 		 				{
 		 					dialoguesFromDrupal = String.valueOf(dialogueObject);
 		 				}
 						if (Utils.isBlank(dialoguesFromDrupal)){
 							dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultFlowKey, salesCenterVo.getValueByName("drupalContentUrl"));
 		 					if (! Utils.isBlank(dialoguesFromDrupal)){
 		 						ConcertDialogCacheService.INSTANCE.store(defaultFlowKey, dialoguesFromDrupal);
 		 					}
 						}
 					}
 				}
 			}
 		} catch (Exception e) {
 			logger.error("error_in_getDialoguesFormDrupalContentForObjectionBusters"+e.getMessage());
 		}
 		return dialoguesFromDrupal;
 	}
 	
 	public static String getDrupalContentForProviderHints(SalesCenterVO salesCenterVo) {
 		String DrupalHints = null;
 		try {
 				String defaultProviderHintsKey = "provider_hints";
 				logger.info("drupalDialogueCacheKey for Hints"+defaultProviderHintsKey);
 				Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultProviderHintsKey);
 				if(dialogueObject != null)
 				{
 					DrupalHints = String.valueOf(dialogueObject);
 				}
 				if (Utils.isBlank(DrupalHints)){
 					DrupalHints = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultProviderHintsKey, salesCenterVo.getValueByName("drupalContentUrl"));
 					if (! Utils.isBlank(DrupalHints)){
 						ConcertDialogCacheService.INSTANCE.store(defaultProviderHintsKey, DrupalHints);
 					}
 				}
 		} catch (Exception e) {
 			logger.error("error_in_getDialoguesFormDrupalContentForProviderHints"+e.getMessage());
 		}
 		return DrupalHints;
 	}
 	public static String getDrupalContentForSalesTips(SalesCenterVO salesCenterVo) {
 		String salesTips = null;
 		try {
 			String defaultSalesTipsKey = "sales_tips";
 			logger.info("drupalDialogueCacheKey_for_Sales_Tips"+defaultSalesTipsKey);
 			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultSalesTipsKey);
 			if(dialogueObject != null)
 			{
 				salesTips = String.valueOf(dialogueObject);
 			}
 			if (Utils.isBlank(salesTips)){
 				salesTips = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultSalesTipsKey, salesCenterVo.getValueByName("drupalContentUrl"));
 				if (! Utils.isBlank(salesTips)){
 					ConcertDialogCacheService.INSTANCE.store(defaultSalesTipsKey, salesTips);
 				}
 			}
 		} catch (Exception e) {
 			logger.error("error_in_getDialoguesFormDrupalContentForProviderHints"+e.getMessage());
 		}
 		if (!Utils.isBlank(salesTips)){
 			Document doc = Jsoup.parse(salesTips);

 			if(doc != null){
 				Element  element = doc.getElementById("SalesTipData");
 				if(element != null){
 					salesTips = element.toString();
 				} 
 			}	 
 		}
 		return salesTips;
 	}
	public static float getTotalPoints(OrderType order, HttpSession session) {
		float totalPoints = 0.00f;
		try {
			ProductResultsManager productResultManager = (ProductResultsManager)session.getAttribute("productResultManager");
			if(productResultManager != null && productResultManager.context.getAllProductList() != null && order.getLineItems()!= null){
				List<LineItemType> lineItems = order.getLineItems().getLineItem();
				if(lineItems != null){
					logger.info("totalPoints="+lineItems.size());
					for(LineItemType lineItem : lineItems){
						if(isProduct(lineItem)){
							if(lineItem != null && lineItem.getLineItemDetail() != null && lineItem.getLineItemDetail().getDetail() != null 
									&& lineItem.getLineItemDetail().getDetail().getProductLineItem() != null && lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId() != null){
								logger.info("lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()="+lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
								for(ProductSummaryVO product : productResultManager.context.getAllProductList()){
									if(product.getExternalId().equalsIgnoreCase(lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId())){
										logger.info("product.getExternalId()="+product.getExternalId());
										logger.info("product.getExternalId()="+product.isSyntheticBundle());
										logger.info("lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()="+lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
										logger.info("product.getPoints()="+product.getPoints());
										if(product != null && !Float.isNaN(product.getPoints())  && product.getPoints() >= 0.0){
											logger.info("totalPoints="+totalPoints);
											totalPoints = totalPoints + product.getPoints();
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}  
		catch (NumberFormatException e) {
			logger.error("Error_while_getting_product_points"+e.getMessage());
		}
		logger.info("totalPoints="+totalPoints);
		session.setAttribute("totalPoints",Float.toString(totalPoints));
		return totalPoints;
	}
	public static boolean isProduct(LineItemType lineItem)
	{

		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				!lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			return true;
		}
		return false;
	}
	public static boolean isProductIncludingRemoved(LineItemType lineItem)
	{

		if(lineItem.getLineItemDetail().getDetailType().equals("product") && (!isOffer(lineItem)))
		{
			return true;
		}
		return false;
	}
	public static boolean isOffer(LineItemType lineItem)
	{

		//ProductType Attribute will be set for Savers Offer & Utility Offer
		String productType = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.PRODUCT_TYPE,"TYPE");

		if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase("product") &&
				!Utils.isBlank(productType))
		{
			return true;
		}
		return false;
	}
	
	public static String getDrupalContentForPivotAssist(SalesCenterVO salesCenterVo) {
 		String pivotAssistData = null;
 		try {
 				String defaultPivotAssistKey = "pivot_assist";
 				logger.info("drupalDialogueCacheKey for PivotAssist"+defaultPivotAssistKey);
 				Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultPivotAssistKey);
 				if(dialogueObject != null)
 				{
 					pivotAssistData = String.valueOf(dialogueObject);
 				}
 				if (Utils.isBlank(pivotAssistData)){
 					pivotAssistData = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultPivotAssistKey, salesCenterVo.getValueByName("drupalContentUrl"));
 					if (! Utils.isBlank(pivotAssistData)){
 						ConcertDialogCacheService.INSTANCE.store(defaultPivotAssistKey, pivotAssistData);
 					}
 				}
 		}
 		catch (Exception e) {
 			logger.error("error_in_getDialoguesFromDrupalContentFordefaultPivotAssistKey"+e.getMessage());
 		}
 		return pivotAssistData;
 	}
	
	public static String getDrupalDialoguesForHomeServe(SalesCenterVO salesCenterVo) {
 		String homeServeGreetingDialogues = null;
 		try {
 			String defaultHomeServeGreetingKey = "homeServe_Greeting";
 			logger.info("drupalDialogueCacheKey_for_homeServe_Greeting"+defaultHomeServeGreetingKey);
 			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultHomeServeGreetingKey);
 			if(dialogueObject != null)
 			{
 				homeServeGreetingDialogues = String.valueOf(dialogueObject);
 			}
 			if (Utils.isBlank(homeServeGreetingDialogues)){
 				homeServeGreetingDialogues = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultHomeServeGreetingKey, salesCenterVo.getValueByName("drupalContentUrl"));
 				if (! Utils.isBlank(homeServeGreetingDialogues)){
 					ConcertDialogCacheService.INSTANCE.store(defaultHomeServeGreetingKey, homeServeGreetingDialogues);
 				}
 			}
 			homeServeGreetingDialogues = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(homeServeGreetingDialogues)));
 		} catch (Exception e) {
 			logger.error("error_in_getDrupalDialoguesForHomeServe"+e.getMessage());
 		}
 		
 		return homeServeGreetingDialogues;
 	}
	
	public static String getDrupalDialoguesForFuseBasicInformation(SalesCenterVO salesCenterVo,boolean isFuseBasicInfo) {
 		String fuseBasicInfoDialogues = null;
 		try {
 			String defaultHomeServeGreetingKey = "Fuse_BasicInformation_"+isFuseBasicInfo;
 			logger.info("drupalDialogueCacheKey_for_fuseBasicInfo"+defaultHomeServeGreetingKey);
 			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultHomeServeGreetingKey);
 			if(dialogueObject != null)
 			{
 				fuseBasicInfoDialogues = String.valueOf(dialogueObject);
 			}
 			if (Utils.isBlank(fuseBasicInfoDialogues)){
 				fuseBasicInfoDialogues = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultHomeServeGreetingKey, salesCenterVo.getValueByName("drupalContentUrl"));
 				if (! Utils.isBlank(fuseBasicInfoDialogues)){
 					ConcertDialogCacheService.INSTANCE.store(defaultHomeServeGreetingKey, fuseBasicInfoDialogues);
 				}
 			}
 			fuseBasicInfoDialogues = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(fuseBasicInfoDialogues)));
 		} catch (Exception e) {
 			logger.error("error_in_getDrupalDialoguesForFuseBasicInfoPage"+e.getMessage());
 		}
 		
 		return fuseBasicInfoDialogues;
 	}
}
