package com.AL.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.vo.PollingStatus;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.builder.DialogueVOBuilder;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.CustomerTracker;
import com.AL.ui.domain.QualificationPopUpRefDetails;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.mapper.DialogueMapper;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.di.v4.DataFieldMatrixType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.v4.OrderType;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.dao.CoxZipcodesDao;
import com.AL.ui.dao.QualificationPopUpRefDetailsDao;
import com.AL.ui.dao.QualificationPopUpZipCodesDao;
import com.AL.ui.service.V.impl.CoxZipCodesDataCache;

import edu.emory.mathcs.backport.java.util.Arrays;

@Controller("QualificationController")
public class QualificationController implements Action {
	private static final Logger logger = Logger.getLogger(QualificationController.class);
	
	@Autowired
	private QualificationPopUpRefDetailsDao qualificationPopUpRefDetailsDao;
	
	
	public Event execute(RequestContext request) throws UnRecoverableException {
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer=0;
		try{
			logger.info("showQualification_begin_in_QualificationController");	
			
			HttpSession session = httpRequest.getSession();
			if(session.getAttribute("isServiceChecked") != null){
				if((String)session.getAttribute("isServiceChecked") != null){
					session.setAttribute("typeOfService", null);
					session.setAttribute("isServiceChecked", null);
				}
			}
			boolean isPopUpEnable = false;
			String popUpMessage = "";
			String isPopupEnabled = ConfigRepo.getString("*.qualification_popup_enable") != null ? ConfigRepo.getString("*.qualification_popup_enable") : "false";
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");	
			logger.info("transferCustomerEnabledProvider="+productResultManager.getTransferCustomerEnabledProviderMap());
			if( !productResultManager.getTransferCustomerEnabledProviderMap().isEmpty() )
			{
				request.getFlowScope().put("existingCustomerProvidersMap", checkATTV6ExitingCustomer(productResultManager));
				logger.info("selectedExistingCustomerProvidersList="+productResultManager.getSelectedExistingCustomerStatusMap());
				request.getFlowScope().put("selectedExistingCustomerStatusMap", productResultManager.getSelectedExistingCustomerStatusMap());
			}
			
			if(httpRequest.getParameter("isBack") != null){
				request.getFlowScope().put("isBack", httpRequest.getParameter("isBack"));
			}
			
			String typeOfService = "";
			if(session.getAttribute("typeOfService")!= null){
				typeOfService = (String)session.getAttribute("typeOfService");
			}

			session.setAttribute("fromQualification","yes");

			Map<String, String> customerDiscovery = new LinkedHashMap<String, String>();
			Map<String, String[]> parameters = httpRequest.getParameterMap();
			Map<String, String> notePadHeaders = new LinkedHashMap<String, String>();
			Map<String, String> discoveryNotepadlinkMap = new LinkedHashMap<String, String>();
			try{
				if(productResultManager != null && productResultManager.getHintProvidersMap() != null && !productResultManager.getHintProvidersMap().isEmpty()){
					String hintProviders = productResultManager.getHintProvidersMap().keySet().toString();
					logger.info("hintProviders="+hintProviders);
					session.setAttribute("hintProviders", hintProviders);
				}
			}
			catch(Exception e)
			{
				logger.error("error_while_retreiving_hintProviders"+e.getMessage());
			}

			String discoveryQuestionsOrder = httpRequest.getParameter("discoveryQuestionsOrder");

			if( !Utils.isBlank(discoveryQuestionsOrder) )
			{
				String notePadDiscoveryOrder[] = discoveryQuestionsOrder.split(",");

				for(String discoveryQuestion : notePadDiscoveryOrder)
				{
					if( parameters.get(discoveryQuestion)!=null )
					{
						String[] header = discoveryQuestion.split("\\|");
						String[] values = parameters.get(discoveryQuestion);
						if (!Utils.isBlank(values[0])) {
							customerDiscovery.put(header[0], values[0]);
						}
						if (header.length > 0) {
							notePadHeaders.put(header[1], (!Utils.isBlank(values[0]) ? values[0] : ""));
							discoveryNotepadlinkMap.put(header[1], header[0]);
						}
					}
				}
				if(session.getAttribute("landlord") != null){
					customerDiscovery.put("Landlord","true");
				}
				session.setAttribute("customerDiscoveryMap", customerDiscovery);
				session.setAttribute("notePadMap", notePadHeaders);
				session.setAttribute("discoveryNotepadlinkMap", discoveryNotepadlinkMap);
			}


			/*
			String header = (String)httpRequest.getHeader("referer");
			if (!Utils.isBlank(typeOfService) && typeOfService.equals("existingService")) { //cleanup
				if (session.getAttribute("questionList") == null || (header !=null && header.endsWith("/discovery"))){
					session.setAttribute("questionList", notePadHeaders); //cleanup
				}
			}

			if (!Utils.isBlank(typeOfService) && typeOfService.equals("newService")) { //cleanup
				if (session.getAttribute("questionList") == null || (header !=null && header.endsWith("/discovery"))){
					session.setAttribute("questionList", notePadHeaders); //cleanup
				}	
			}
			 */

			SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
			com.AL.xml.di.v4.NameValuePairType nvPairObj = new com.AL.xml.di.v4.NameValuePairType();
			nvPairObj.setName("GUID");
			nvPairObj.setValue(salesCenterVo.getValueByName("GUID"));

			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			//		dynamicFlow.put("dynamicFlow.page", "ScriptedQualification");
			dynamicFlow.put("dynamicFlow.page", "NewScriptedQualification");

			dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));

			Map<String, String> saleFlow =  new HashMap<String, String>();

			if(request.getFlowScope().get("salesFlow.forceNonConfirm")!=null){
				if(dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice")){
					String forceNonConfirm = (String)request.getFlowScope().get("salesFlow.forceNonConfirm");
					saleFlow.put("salesFlow.forceNonConfirm", forceNonConfirm);
				}
				else{
					saleFlow.put("salesFlow.forceNonConfirm", "true");
				}
			}
			try{
				if (isPopupEnabled != null && isPopupEnabled.equalsIgnoreCase("true")){
					String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
					Map<String, Map<String, QualificationPopUpRefDetails>> qualPopUpRefDetailsMap = new HashMap<String, Map<String,QualificationPopUpRefDetails>>();
					Map<String, QualificationPopUpRefDetails> qualPopUpDetailsMap = new HashMap<String, QualificationPopUpRefDetails>();
					if (CoxZipCodesDataCache.INSTANCE.get("qualificationPopUpRefDetails") != null){
						qualPopUpRefDetailsMap = (Map<String, Map<String, QualificationPopUpRefDetails>>)CoxZipCodesDataCache.INSTANCE.get("qualificationPopUpRefDetails");
					}
					else{
						qualPopUpRefDetailsMap = qualificationPopUpRefDetailsDao.getAllQualificationPopUpRefDetails();
						if(qualPopUpRefDetailsMap != null && !qualPopUpRefDetailsMap.isEmpty()){
							CoxZipCodesDataCache.INSTANCE.store("qualificationPopUpRefDetails",qualPopUpRefDetailsMap);
						}
					}
					if(qualPopUpRefDetailsMap != null  && !qualPopUpRefDetailsMap.isEmpty()){
						if(qualPopUpRefDetailsMap.get("All")!= null){
							qualPopUpDetailsMap = qualPopUpRefDetailsMap.get("All");
						}
						if(qualPopUpRefDetailsMap.get(referrerId)!= null){
							qualPopUpDetailsMap.putAll(qualPopUpRefDetailsMap.get(referrerId));
						}
					}
					if(qualPopUpDetailsMap != null && !qualPopUpDetailsMap.isEmpty()){
						for(Entry<String, QualificationPopUpRefDetails> popUpDetailsMap: qualPopUpDetailsMap.entrySet()){
							String popupProvider = popUpDetailsMap.getKey();
							logger.info("popupProvider="+popupProvider);
							if(productResultManager.getRealTimeStatusMap().get(popupProvider) != null 
									&& (productResultManager.getRealTimeStatusMap().get(popupProvider).contains(PollingStatus.SUCCESS)||productResultManager.getRealTimeStatusMap().get(popupProvider).contains(PollingStatus.PENDING))){
								List<String> zipCodes = new ArrayList<String>();
								QualificationPopUpRefDetails details = popUpDetailsMap.getValue();
								zipCodes = details.getZipCodesList();
								logger.info("zipCodes size="+zipCodes.size());
								String zipCode = null;
								if (salesCenterVo.getValueByName("correctedZipCode") != null){
									zipCode = salesCenterVo.getValueByName("correctedZipCode");
									if(zipCode.length()>5){
										zipCode = zipCode.substring(0, 5);
									}
								}
								if (zipCode != null && zipCodes != null && zipCodes.contains(zipCode)){
									if(!popUpMessage.isEmpty()){
										popUpMessage = popUpMessage + "" + details.getPopupMessage();
									}
									else{
										popUpMessage = details.getPopupMessage();
									}
									isPopUpEnable = true;
								}
							}
						}
					}
				}
			}
			catch(Exception e){
				logger.warn("Error getting popup messages"+e.getMessage());
			}
			logger.info("showPopUp="+isPopUpEnable);
			logger.info("popUpMessage="+popUpMessage);
			session.setAttribute("showPopUp",isPopUpEnable);	
			session.setAttribute("popUpMessage",popUpMessage);
			contextMap.put("salesFlow", saleFlow);

			OrderType order =(OrderType)httpRequest.getSession().getAttribute("order");

			buildQualicationProducts(request);

			startTimer=timer.getTime();
			if (salesCenterVo.getValueByName("drupalContentUrl") != null 
					&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
				renderDrupalDialogue(request,contextMap,salesCenterVo);
			}
			else{
				generateDialoguesFromService(contextMap,salesCenterVo,request);
			}
			session.setAttribute("showProviderHints",true);
			logger.info("TimeTakenforDialougeServicecall="+(timer.getTime()-startTimer));
			if(session.getAttribute("orderExternalId") ==  null){
				if(httpRequest.getParameter("orderExternalId")!= null){
					session.setAttribute("orderExternalId", httpRequest.getParameter("orderExternalId"));
				}
			}
			if(!Utils.isBlank(httpRequest.getParameter("lineItemExtID"))){
				//Here we setting UtilityOfferExtID in request level for Score Capturing
				request.getFlashScope().put("lineItemExtID", httpRequest.getParameter("lineItemExtID").trim());
			}else{
				//Here we setting SimpleChoiceLineItemExID in request level for Score Capturing
				request.getFlashScope().put("lineItemExtID", request.getFlowScope().get("simpleChoiceLineItemExID"));
				logger.info("SimpleChoiceLineItemExID="+request.getFlowScope().get("simpleChoiceLineItemExID"));
			}
			request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
			com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

			request.getFlowScope().put("address",getAddress(custAdr));
			session.setAttribute("salesTips", (String)SalesUtil.INSTANCE.getDrupalContentForSalesTips(salesCenterVo));
			if(session.getAttribute("callTimeBeforeUtility")!=null){
				session.setAttribute("fromUtility", "yes");
			}
			logger.info("timeTakenForShowQualification="+timer.getTime());
			logger.info("showQualification_end_QualificationController");
			return new Event(this, "confirmationEvent");
		}catch(Exception e){
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error("error_in_QualificationController",e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}

	}


	public Address getAddress(com.AL.xml.v4.CustAddress custAdr){

		Address add = new Address();

		add.setPostfixDirectional(custAdr.getAddress().getPostfixDirectional());
		add.setPrefixDirectional(custAdr.getAddress().getPrefixDirectional());
		add.setStreetName(custAdr.getAddress().getStreetName());
		add.setStreetNumber(custAdr.getAddress().getStreetNumber());
		add.setStreetType(custAdr.getAddress().getStreetType());
		add.setLine2(custAdr.getAddress().getLine2());
		add.setCity(custAdr.getAddress().getCity());
		add.setStateOrProvince(custAdr.getAddress().getStateOrProvince());
		add.setPostalCode(custAdr.getAddress().getPostalCode());

		return add;

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

	public static List<String> removeDuplicates(List<String> providerList){
		if(providerList!=null){
			for (int i = 0 ; i < providerList.size(); i++) {
				for (int j = i + 1; j < providerList.size(); j++) {
					if ( providerList.get(i).toString().equalsIgnoreCase( providerList.get(j).toString() )  ) {
						providerList.remove(j);
						j = j -1; 
					}                                
				}
			}
		}
		return providerList;
	}
	public static List<String> removeHughesNet(List<String> providerList,String provider){
		List<String> newProviderList = new ArrayList<String>();
		if(providerList!=null){
			ListIterator<String> itr =  providerList.listIterator();
			while(itr.hasNext()){
				String providerName = (String) itr.next();
				if(!providerName.equalsIgnoreCase(provider)){
					newProviderList.add(providerName);
				}
			}
		}
		return newProviderList;
	}

	private String getProviderName(ProductSummaryVO product) {
		if(isDirectTVProduct(product)){
			return Constants.ATT_DIRECTV;
		}else if(product.getParentName()!= null) {
			return product.getParentName();
		} else {
			return product.getProviderName();
		}
	}

	private void generateDialoguesFromService(Map<String, Map<String, String>> contextMap, 
			SalesCenterVO salesCenterVo, RequestContext request) throws UnRecoverableException {
		DialogueResponseType dialogueResponseType;
		try {
			dialogueResponseType = DialogServiceUI.INSTANCE.getQualificationDialogue(contextMap);

			List<Fieldset> fieldsetList = null;
			StringBuilder dialogue_events = new StringBuilder();
			StringBuilder selection_events = new StringBuilder();

			List<DataGroup> dgList = null;

			List<DataFieldType> dataFieldList = new ArrayList<DataFieldType>();

			List<DataFieldMatrixType> dataFieldEnableList = new ArrayList<DataFieldMatrixType>();

			for (DialogueType dialoge : dialogueResponseType.getResults().getDialogueList().getDialogue()){
				for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()){
					dataFieldList.addAll(dataGroup.getDataFieldList().getDataField());
					dataFieldEnableList.add(dataGroup.getDataFieldMatrix());
				}
			}

			List<String> allDataFieldList = new ArrayList<String>();
			Map<String, Map<String, List<String>>> enableDependencyMap = DialogueUtil.INSTANCE.getEnableDependencies(dataFieldEnableList, null);
			Map<String, Map<String, List<String>>> disableDependencyMap = DialogueUtil.INSTANCE.getDisableDependencies(dataFieldList, enableDependencyMap, null);

			for(Entry<String, Map<String, List<String>>> enableDependenciesEntry : enableDependencyMap.entrySet()){
				for(Entry<String, List<String>> enableDependenciesList : enableDependenciesEntry.getValue().entrySet()){
					for(String dependedEle : enableDependenciesList.getValue()){
						allDataFieldList.add(dependedEle);
					}
				}
			}

			List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogueResponseType);
			SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());

			dialogueVO.setDialogueList(dialogueList);

			for (Dialogue dialogue : dialogueVO.getDialogueList()){
				dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					if(!dGroup.getName().equals("New or Transfer")){
						fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(dialogue_events, dGroup.getDataFieldList(), null, false);
						for (Fieldset fieldset : fieldsetList) {
							String element = HtmlBuilder.INSTANCE.toString(fieldset);
							element = salesCenterVo.replaceNamesWithValues(element);
							dialogue_events.append(element);
						}
						/*fieldsetList = HtmlFactory.INSTANCE.buildDialogueOnDataGroup(dGroup, enableDependencyMap);
				for (Fieldset fieldset : fieldsetList) {
					String element = HtmlBuilder.INSTANCE.toString(fieldset);
					element = salesCenterVo.replaceNamesWithValues(element);
					dialogue_events.append(element);
				}*/
					}
					else{
						fieldsetList = HtmlFactory.INSTANCE.buildSelectionTypes(dGroup, enableDependencyMap);
						for (Fieldset fieldset : fieldsetList) {
							String element = HtmlBuilder.INSTANCE.toString(fieldset);
							element = salesCenterVo.replaceNamesWithValues(element);
							selection_events.append(element);
						}
					}
				}
			}
			request.getFlowScope().put("dialogue_display",SalesUtilsFactory.INSTANCE.parseHtmlTags(escapeSpecialCharacters(dialogue_events.toString())));
			request.getFlowScope().put("selection_display",SalesUtilsFactory.INSTANCE.parseHtmlTags(escapeSpecialCharacters(selection_events.toString())));
			request.getFlowScope().put("enableDialogueMap", enableDependencyMap);
			request.getFlowScope().put("disableDialogueMap", disableDependencyMap);
			request.getFlowScope().put("allDataFieldList", allDataFieldList);
		} catch (BaseException e) {
			logger.error("Exception_in_QualificationController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}


	public void gotoQualificationMoveInDeltaView(RequestContext request) throws UnRecoverableException {
		logger.info("Qualification_MoveInDeltaView_Begin");
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		HttpSession session = httpRequest.getSession();
		try{
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			if (productResultManager.getProductByIconMap().get("HOMESECURITY") != null &&
					productResultManager.getProductByIconMap().get("HOMESECURITY").size() > 0){
				request.getFlowScope().put("isHomeSecurityAval", "Yes");
			}else{
				request.getFlowScope().put("isHomeSecurityAval", "No");
			}
			session.setAttribute("fromQualificationMoveInDelta","yes");
			SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
			com.AL.xml.di.v4.NameValuePairType nvPairObj = new com.AL.xml.di.v4.NameValuePairType();
			nvPairObj.setName("GUID");
			nvPairObj.setValue(salesCenterVo.getValueByName("GUID"));
			buildQualicationProducts(request);
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "QualificationMoveInDelta");
			dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));

			Map<String, String> saleFlow =  new HashMap<String, String>();

			if(request.getFlowScope().get("salesFlow.forceNonConfirm")!=null){
				if(dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice")){
					String forceNonConfirm = (String)request.getFlowScope().get("salesFlow.forceNonConfirm");
					saleFlow.put("salesFlow.forceNonConfirm", forceNonConfirm);
				}
				else{
					saleFlow.put("salesFlow.forceNonConfirm", "true");
				}
			}

			contextMap.put("salesFlow", saleFlow);

			if (salesCenterVo.getValueByName("drupalContentUrl") != null 
					&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null )
			{
				renderDrupalDialogue(request,contextMap,salesCenterVo);
			}
			else{
				generateDialoguesFromService(contextMap, salesCenterVo,request);
			}

			logger.info("Qualification_MoveInDeltaView_End");
		}catch(Exception e){
			logger.error("Exception_in_QualificationController_isQualificationMoveInDelta",e);
			httpRequest.setAttribute("message", e.getMessage());
			throw new UnRecoverableException(e.getMessage());
		}
	}

	private void renderDrupalDialogue(RequestContext request,Map<String, Map<String, String>> contextMap,SalesCenterVO salesCenterVo) throws Exception {
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		String dialoguesFromDrupal = DialogServiceUI.INSTANCE.getDialoguesByContextForDrupal(contextMap,salesCenterVo.getValueByName("ordersource.programId"));
		if (Utils.isBlank(dialoguesFromDrupal)){
			dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueCacheKeyForDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
					, salesCenterVo.getValueByName("drupalContentUrl"));	
			if (Utils.isBlank(dialoguesFromDrupal)){
				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueBackupDefaultUserGroupDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
						, salesCenterVo.getValueByName("drupalContentUrl"));	
				DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupalDefaultUserGroup(contextMap,dialoguesFromDrupal);
			} else {
				DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupal(contextMap,dialoguesFromDrupal,salesCenterVo.getValueByName("ordersource.programId"));
			}
		}

		if (Utils.isBlank(dialoguesFromDrupal)){
			generateDialoguesFromService(contextMap,salesCenterVo,request);
		}
		else{
			String displayDialogues = RESTClientForDrupal.INSTANCE.generateSelectedDialogues(dialoguesFromDrupal,"Qualification_Data");
			String selectionDisplay = RESTClientForDrupal.INSTANCE.generateSelectedDialogues(dialoguesFromDrupal,"Confirm_Scripted_Qual_Display_Bottom");
			String allDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesAll(dialoguesFromDrupal);
			String enableDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesEnable(dialoguesFromDrupal);
			String disableDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesDisable(dialoguesFromDrupal);

			if (Utils.isBlank(displayDialogues) || Utils.isBlank(selectionDisplay)
					|| Utils.isBlank(allDependencyFields)|| Utils.isBlank(enableDependencyFields)|| Utils.isBlank(disableDependencyFields)){
				generateDialoguesFromService(contextMap,salesCenterVo,request);
			}else{
				request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));

				displayDialogues = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(displayDialogues)));
				selectionDisplay = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(selectionDisplay)));
				ObjectMapper mapper = new ObjectMapper();
				//convert JSON string to Map
				Map<String, Map<String, List<String>>> enableDependencyMapDrupal = mapper.readValue(enableDependencyFields, new TypeReference<HashMap<String, Map<String, List<String>>>>(){});
				//convert JSON string to Map
				Map<String, Map<String, List<String>>> disableDependencyMapDrupal = mapper.readValue(disableDependencyFields, new TypeReference<HashMap<String, Map<String, List<String>>>>(){});

				logger.info("drupal_enableDependencyMap"+enableDependencyMapDrupal);
				logger.info("drupal_disableDependencyMap"+disableDependencyMapDrupal);

				//dialoguesFromDrupal = salesCenterVo.replaceNamesWithValues(dialoguesFromDrupal);
				request.getFlowScope().put("selection_display",selectionDisplay);
				request.getFlowScope().put("dialogue_display", displayDialogues);

				allDependencyFields = allDependencyFields.replace("[", "");
				allDependencyFields = allDependencyFields.replace("]", "");

				List<String> allDataFieldList = new ArrayList<String>();
				String dataFieldExtIds[] =  allDependencyFields.split(",");
				for(String dataFieldExtId : dataFieldExtIds )
				{
					allDataFieldList.add(dataFieldExtId.trim());
				}
				logger.info("allDataFieldList="+allDataFieldList);
				logger.info("enableDependencyFields="+enableDependencyFields);
				logger.info("disableDependencyFields="+disableDependencyFields);
				request.getFlowScope().put("allDataFieldList", allDataFieldList);
				request.getFlowScope().put("enableDialogueMap", enableDependencyMapDrupal);
				request.getFlowScope().put("disableDialogueMap",disableDependencyMapDrupal);
			}
		}


	}

	private void buildQualicationProducts(RequestContext request) {
		logger.info("build_QualicationProducts_begin");
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		HttpSession session = httpRequest.getSession();
		List<String> sqProvidersList = new ArrayList<String>();
		List<String> resultSqProvidersList = new ArrayList<String>();
		StringBuffer strProviders = new StringBuffer();
		ProductSummaryVO tripleDetail = null;
		ProductSummaryVO doubleDetail = null;
		ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
		SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
		//show highest rated bundle (i.e show highest rated triple play; if triple_play is not present show highest rated double_play)		
		if (productResultManager.getProductByIconMap().get("TRIPLE_PLAY") != null &&
				productResultManager.getProductByIconMap().get("TRIPLE_PLAY").size() > 0){
			tripleDetail = (ProductSummaryVO) productResultManager.getProductByIconMap().get("TRIPLE_PLAY").get(0);
		}	
		if (productResultManager.getProductByIconMap().get("DOUBLE_PLAY") != null &&
				productResultManager.getProductByIconMap().get("DOUBLE_PLAY").size() > 0){
			doubleDetail = (ProductSummaryVO) productResultManager.getProductByIconMap().get("DOUBLE_PLAY").get(0);
		}
		//if we have both triple play and double play products, then pick the highest scored bundle
		if (tripleDetail != null && doubleDetail != null){
			if(tripleDetail.getScore() >= doubleDetail.getScore()) {
				sqProvidersList.add(getProviderName(tripleDetail));
			} else {
				sqProvidersList.add(getProviderName(doubleDetail));
			}
		} else if(tripleDetail != null) {
			//if double play products are not found and triple play products are found, pick highest rated triple play
			sqProvidersList.add(getProviderName(tripleDetail));
		} else if(doubleDetail != null) {
			//if triple play products are not found and double play products are found, pick highest rated double play
			sqProvidersList.add(getProviderName(doubleDetail));
		}
		if (productResultManager.getProductByIconMap().get("VIDEO") != null &&
				productResultManager.getProductByIconMap().get("VIDEO").size() > 0){
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("VIDEO");
			for (ProductSummaryVO productVo : list ) {
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					break;
				}
			}
		}
		if (productResultManager.getProductByIconMap().get("INTERNET") != null &&
				productResultManager.getProductByIconMap().get("INTERNET").size() > 0){
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("INTERNET");
			for (ProductSummaryVO productVo : list ) {
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					break;
				}
			}
		}
		if (productResultManager.getProductByIconMap().get("PHONE") != null &&
				productResultManager.getProductByIconMap().get("PHONE").size() > 0){
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("PHONE");
			for (ProductSummaryVO productVo : list ) {
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					break;
				}
			}					
		}
		//if security is available, show in qualification script
		if (productResultManager.getProductByIconMap().get("HOMESECURITY") != null &&
				productResultManager.getProductByIconMap().get("HOMESECURITY").size() > 0){
			int stopAtTwo = 0;
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("HOMESECURITY");
			for (ProductSummaryVO productVo : list ) {					
				String providerName = getProviderName(productVo);
				if(Constants.ADT.equals(productVo.getProviderExternalId())) {
					sqProvidersList.add(providerName);
					stopAtTwo++;
					break;
				}
			}
			for (ProductSummaryVO productVo : list ) {					
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					stopAtTwo++;
				}
				if(stopAtTwo == 2) {
					break;
				}
			}								
		}
		if (sqProvidersList != null && sqProvidersList.size() >= 2) {
			for (int i =0; i<= sqProvidersList.size()-1; i ++){
				String provider = sqProvidersList.get(i);
				if(provider.equalsIgnoreCase("ATTSTI")){
					provider = "AT&T";
				}else if(provider.equalsIgnoreCase("Dish Network")){
					provider  =  "Dish";
				}else if(provider.equalsIgnoreCase("G2B-Comcast")){
					provider = "Comcast";
				}else if(provider.contains("ADT")){
					provider = "ADT";
				}
				else if(provider.contains("ATT_DirecTV")){
					provider = "DirecTV";
				}
				resultSqProvidersList.add(provider);
			}
		}else if (sqProvidersList != null && sqProvidersList.size() > 0 ){
			String provider = sqProvidersList.get(0);
			if(provider.equalsIgnoreCase("ATTSTI")){
				provider = "AT&T";
			}else if(provider.equalsIgnoreCase("Dish Network")){
				provider  =  "Dish";
			}else if(provider.equalsIgnoreCase("G2B-Comcast")){
				provider = "Comcast";
			}else if(provider.contains("ADT")){
				provider = "ADT";
			}else if(provider.contains("ATT_DirecTV")){
				provider = "DirecTV";
			}
			resultSqProvidersList.add(provider);
		}

		logger.info("resultSqProvidersList=" +resultSqProvidersList);
		if(productResultManager.getHideHughesNet() != null && productResultManager.getHideHughesNet().equalsIgnoreCase("true")){
			if(!productResultManager.isHNProductShow()){
				for(String provider : resultSqProvidersList){
					if(provider.equalsIgnoreCase(Constants.HUGHESNET_NAME)){
						resultSqProvidersList = removeHughesNet(resultSqProvidersList,Constants.HUGHESNET_NAME);
					}
				}
			}
		}
		resultSqProvidersList = removeDuplicates(resultSqProvidersList);
		if (resultSqProvidersList != null && resultSqProvidersList.size() >= 2) {
			for (int i =0; i<= resultSqProvidersList.size()-1; i ++){
				String provider = resultSqProvidersList.get(i);
				strProviders.append(provider);
				if(i< resultSqProvidersList.size()-2){
					strProviders.append(", ");
				}
				if(i==resultSqProvidersList.size()-2){
					strProviders.append(" and ");
				}
			}
		}else if(sqProvidersList != null && sqProvidersList.size() == 1){
			strProviders.append(resultSqProvidersList.get(0));
		}
		salesCenterVo.setValueByName("scriptedQualification.providerList", "<b>"+strProviders.toString()+"</b>");
		logger.info("build_QualicationProducts_end");
	}

	/**
	 * @param summaryVO
	 * @return
	 */
	private boolean isDirectTVProduct(ProductSummaryVO summaryVO) {
		if (Constants.ATTV6.equals(summaryVO.getProviderExternalId())
				&& summaryVO.getCapabilityMap() != null
				&& summaryVO.getCapabilityMap().get(Constants.SATELLITE) != null) {
			logger.info("isDirectTVProduct");
			return true;
		}
		return false;
	}

	private Map<String,String> checkATTV6ExitingCustomer(ProductResultsManager productResultManager){
		Map<String,String> map = new ConcurrentHashMap<String,String>(productResultManager.getTransferCustomerEnabledProviderMap());
		if(productResultManager.getRealTimeStatusMap().get(Constants.ATTV6_NAME)!= null && !((productResultManager.getRealTimeStatusMap().get(Constants.ATTV6_NAME).contains(PollingStatus.SUCCESS)
				&& productResultManager.isATTAvailable(productResultManager.context.getAllProductList()))
				|| productResultManager.getRealTimeStatusMap().get(Constants.ATTV6_NAME).contains(PollingStatus.PENDING))){
			for(Entry<String, String> entry :map.entrySet()){
				if(Constants.ATTV6.equals(entry.getValue())){
					map.remove(entry.getKey());
				}
			}
		}
		
		if(productResultManager.getRealTimeStatusMap().get(Constants.ATT_NAME)!= null && !((productResultManager.getRealTimeStatusMap().get(Constants.ATT_NAME).contains(PollingStatus.SUCCESS)
				&& productResultManager.isATTV5Available(productResultManager.context.getAllProductList()))
				|| productResultManager.getRealTimeStatusMap().get(Constants.ATT_NAME).contains(PollingStatus.PENDING))){
			for(Entry<String, String> entry :map.entrySet()){
				if(Constants.ATT.equals(entry.getValue())){
					map.remove(entry.getKey());
				}
			}
		}
		if(productResultManager.getRealTimeStatusMap().get(Constants.COMCAST_NAME)!= null && !((productResultManager.getRealTimeStatusMap().get(Constants.COMCAST_NAME).contains(PollingStatus.SUCCESS)
				&& productResultManager.isComcastAvailable(productResultManager.context.getAllProductList()))
				|| productResultManager.getRealTimeStatusMap().get(Constants.COMCAST_NAME).contains(PollingStatus.PENDING))){
			for(Entry<String, String> entry :map.entrySet()){
				if(Constants.COMCAST.equals(entry.getValue())){
					map.remove(entry.getKey());
				}
			}
		}
		if(productResultManager.getRealTimeStatusMap().get(Constants.CENTURY_LINK_NAME)!= null && !((productResultManager.getRealTimeStatusMap().get(Constants.CENTURY_LINK_NAME).contains(PollingStatus.SUCCESS)
				&& productResultManager.isCenturyLinkAvailable(productResultManager.context.getAllProductList()))
				|| productResultManager.getRealTimeStatusMap().get(Constants.CENTURY_LINK_NAME).contains(PollingStatus.PENDING))){
			for(Entry<String, String> entry :map.entrySet()){
				if(Constants.CENTURY_LINK.equals(entry.getValue())){
					map.remove(entry.getKey());
				}
			}
		}
		return map;	
	}
	
	private Map<String, String> getProviderReferrerMessageData(List<String> popupProvidersMessagesList) {
		Map<String,String> providerReferrerMessagesMap = new HashMap<String,String>();
		for(String providerReferrerMessages: popupProvidersMessagesList){
			if(providerReferrerMessages.indexOf('=') > -1){
				String[] providerReferrerMessage = providerReferrerMessages.split("=");
				String providerId = providerReferrerMessage[0];
				if (providerReferrerMessage.length == 2){
					if(providerReferrerMessage[1].indexOf(':') > -1){
						String[] referrerMessage = providerReferrerMessage[1].split(":");
						String message = referrerMessage[0];
						String referrerIds = referrerMessage[1];
						String[] data = referrerIds.split("\\,");
						for(String refId: data){
							providerReferrerMessagesMap.put(providerId+"-"+refId,message);
						}
					}
				}
				
			}
		}
		logger.info("providerReferrerMessagesMap size="+providerReferrerMessagesMap.size());
		return providerReferrerMessagesMap;
	}
}
