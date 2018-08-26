package com.AL.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;


@Controller("DiscoveryController")
public class DiscoveryController implements Action {
	private static final Logger logger = Logger.getLogger(DiscoveryController.class);

	public Event execute(RequestContext request) throws UnRecoverableException {
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			HttpSession session = httpRequest.getSession();
			logger.info("showDiscovery_begin");
			Long orderId =(Long)session.getAttribute("orderId");
			HashMap<String, String> selectedExistingProviders = null;
			HashMap<String, String> selectedExistingCustomerStatusMap = null;


			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("Agent");
			startTimer=timer.getTime();
			OrderType order =(OrderType)httpRequest.getSession().getAttribute("order");
			if (order == null) {
				order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null,null,false, null);
				logger.info("order from session was null, retrieved from OrderService");
				logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));
			} 			
			String newService = httpRequest.getParameter("newService");
			logger.info("submitvalue_newService= " + newService);

			String existingService = httpRequest.getParameter("existingService");
			logger.info("submitvalue_existingService=" + existingService);

			String moveInDeltaService = httpRequest.getParameter("moveInDeltaService");
			logger.info("submitvalue_moveInDeltaService=" + moveInDeltaService);
			String yesTransferService = httpRequest.getParameter("yesTransferService");
			String noTransferService = httpRequest.getParameter("noTransferService");
			logger.info("Yes_Transfer_Service_is=" + yesTransferService );
			logger.info("No_Transfer_Service_is=" + noTransferService );

			if(httpRequest.getParameter("isBack") != null){
				request.getFlowScope().put("isBack", httpRequest.getParameter("isBack"));
			}
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");	
			
			StringBuilder discoveryProviders = new StringBuilder();
			String selectedExistingProvidersString = httpRequest.getParameter("selectedExistingProviders");
			if(!Utils.isBlank(selectedExistingProvidersString)){
				session.setAttribute("selectedExistingProviders", selectedExistingProvidersString);
			}else{
				session.setAttribute("selectedExistingProviders",null);
			}
			String selectedExistingProvidersAfterAuthenticationString = httpRequest.getParameter("selectedExistingProvidersAfterAuthentication");
			if(!Utils.isBlank(selectedExistingProvidersAfterAuthenticationString)){
				session.setAttribute("selectedExistingProvidersAfterAuthentication", selectedExistingProvidersString);
			}else{
				session.setAttribute("selectedExistingProvidersAfterAuthentication",null);
			}
			logger.info("transferCustomerEnabledProvider="+productResultManager.getTransferCustomerEnabledProviderMap());
			request.getFlowScope().put("moveInDeltaAccepted" , null);
			if( !productResultManager.getTransferCustomerEnabledProviderMap().isEmpty() )
			{
				request.getFlowScope().put("existingCustomerProvidersMap", productResultManager.getTransferCustomerEnabledProviderMap());
				logger.info("selectedExistingCustomerProvidersList="+productResultManager.getSelectedExistingCustomerStatusMap());
				request.getFlowScope().put("selectedExistingCustomerStatusMap", productResultManager.getSelectedExistingCustomerStatusMap());
			}
			Map<String, String> customerDiscovery = new LinkedHashMap<String, String>();
			Map<String, String[]> parameters = httpRequest.getParameterMap();
			Map<String, String> notePadHeaders = new LinkedHashMap<String, String>();
			Map<String, String> discoveryNotepadlinkMap = new LinkedHashMap<String, String>();
			
			String pageTitle = httpRequest.getParameter("pageTitle");
			boolean isFromDiscovery = false;
			String isOtherChecked = httpRequest.getParameter("isOtherChecked");
			if("Qualification".equalsIgnoreCase(pageTitle))
			{
				if(!Utils.isBlank(isOtherChecked))
				{
					request.getFlowScope().put("isOtherChecked", "Yes");
				}
				else
				{
					request.getFlowScope().put("isOtherChecked", "No");
				}
				String discoveryQuestionsOrder = "";
				if( !Utils.isBlank(newService)){
					discoveryQuestionsOrder = httpRequest.getParameter("discoveryQuestionsOrderNew");
				}
				if( !Utils.isBlank(existingService)) {
					discoveryQuestionsOrder = httpRequest.getParameter("discoveryQuestionsOrderTransfer");
				}
				if( !Utils.isBlank(moveInDeltaService) ) {
					discoveryQuestionsOrder = httpRequest.getParameter("discoveryQuestionsOrderMoveIn");
				}

				if( !Utils.isBlank(discoveryQuestionsOrder) )
				{
					String notePadDiscoveryOrder[] = discoveryQuestionsOrder.split(",");
					isFromDiscovery = true;
					for(String discoveryQuestion : notePadDiscoveryOrder)
					{
						/*		if( parameters.get(discoveryQuestion)!=null )
					{*/
						String[] header = discoveryQuestion.split("\\|");
						String[] values = new String[20];
						if(parameters.get(discoveryQuestion)!=null) {
							values = parameters.get(discoveryQuestion);
						}
						if (!Utils.isBlank(values[0])) {
							customerDiscovery.put(header[0], values[0]);
						}
						if (header.length > 0) {
							notePadHeaders.put(header[1], (!Utils.isBlank(values[0]) ? values[0] : ""));
							discoveryNotepadlinkMap.put(header[1], header[0]);
						}
						//					}
					}
				}
				session.setAttribute("notePadMap", notePadHeaders);
				session.setAttribute("discoveryNotepadlinkMap", discoveryNotepadlinkMap);
			}
			if(!Utils.isBlank(isOtherChecked)) {
				discoveryProviders.append("Others");
			}
			if( !Utils.isBlank(selectedExistingProvidersString) )
			{
				JSONObject jsonObject = new JSONObject(selectedExistingProvidersString);
				Iterator<String> iterator = jsonObject.keys();
				while(iterator.hasNext())
				{
					String providerName = iterator.next();
					if(discoveryProviders.length() > 0) {
						discoveryProviders.append(", ");
					}
					discoveryProviders.append(providerName);
					if(selectedExistingProviders!=null)
					{
						selectedExistingProviders.put(providerName, jsonObject.getString(providerName));
						selectedExistingCustomerStatusMap.put(jsonObject.getString(providerName),"enable");
					}
					else
					{
						selectedExistingProviders = new HashMap<String, String>();
						selectedExistingCustomerStatusMap = new HashMap<String, String>();
						selectedExistingProviders.put(providerName, jsonObject.getString(providerName));
						selectedExistingCustomerStatusMap.put(jsonObject.getString(providerName),"enable");
					}
				}
			}
			if( !Utils.isBlank(selectedExistingProvidersAfterAuthenticationString)) {
				JSONObject jsonObject = new JSONObject(selectedExistingProvidersAfterAuthenticationString);
				Iterator<String> iterator = jsonObject.keys();
				while(iterator.hasNext())
				{
					String providerName = iterator.next();
					if(discoveryProviders.length() > 0) {
						discoveryProviders.append(", ");
					}
					discoveryProviders.append(providerName);
				}
			}


			if((session.getAttribute("fromQualification")!= null 
					&& "yes".equalsIgnoreCase(session.getAttribute("fromQualification").toString())) 
					|| (session.getAttribute("fromQualificationMoveInDelta")!= null 
					   &&"yes".equalsIgnoreCase(session.getAttribute("fromQualificationMoveInDelta").toString())))
			{
				if(StringUtils.isNotBlank(yesTransferService) && yesTransferService.trim().equals("Y"))
				{
					session.setAttribute("typeOfcheckBox", "Y");
				} 
				else if(StringUtils.isNotBlank(noTransferService) && noTransferService.trim().equals("N"))
				{
					session.setAttribute("typeOfcheckBox", "N");
				}
				else
				{
					session.setAttribute("typeOfcheckBox", null);
				}

				String serviceSelected = null;
				if( Utils.isBlank(newService) && Utils.isBlank(existingService) && Utils.isBlank(moveInDeltaService)){
					session.setAttribute("isServiceChecked", "No");
					session.setAttribute("typeOfService", "existingService");
					serviceSelected = null;
				}else if( !Utils.isBlank(newService) ){
					serviceSelected = "newService";
				}else if( !Utils.isBlank(existingService) )
				{
					if( !Utils.isBlank(yesTransferService) && yesTransferService.trim().equals("Y") )
					{
						serviceSelected = "existingService";
					}
					else
					{
						serviceSelected = "newService";
					}
				}else if(!Utils.isBlank(moveInDeltaService)){
					serviceSelected = "moveInDeltaService";
				}

				session.setAttribute("typeOfService", serviceSelected);
				logger.info("serviceSelected="+serviceSelected);
			}
			if(selectedExistingProviders!=null )
			{
				productResultManager.setSelectedExistingProvidersMap(selectedExistingProviders);
				productResultManager.setSelectedExistingCustomerStatusMap(selectedExistingCustomerStatusMap);
				request.getFlowScope().put("selectedExistingProviders",productResultManager.getSelectedExistingProvidersMap());
			}
			else
			{
				if( isFromDiscovery )
				{
					if( !productResultManager.getSelectedExistingCustomerStatusMap().isEmpty()
							&& !productResultManager.getSelectedExistingCustomerStatusMap().containsValue("disable") )
					{
						productResultManager.setSelectedExistingProvidersMap(new HashMap<String, String>());
						productResultManager.setSelectedExistingCustomerStatusMap(new HashMap<String, String>());
						request.getFlowScope().put("selectedExistingProviders",productResultManager.getSelectedExistingProvidersMap());
					}

				}
			}


			/*
			if(session.getAttribute("typeOfService")==null && (session.getAttribute("fromQualification")!=null 
					&& "yes".equalsIgnoreCase(session.getAttribute("fromQualification").toString()))){
				if(newService != null){
					Object attribute = session.getAttribute("typeOfService");
					if (attribute != null  && ((String)attribute).equals("existingService")) {
						//session.setAttribute("questionList", null);
					}
					session.setAttribute("typeOfService", newService);
				}
				else {
					if(newService == null && existingService == null){
						session.setAttribute("isServiceChecked", "No");
					}

					Object attribute = session.getAttribute("typeOfService");
					if (attribute != null  && ((String)attribute).equals("newService")) {
						//session.setAttribute("questionList", null);
					}
					session.setAttribute("typeOfService", "existingService");
				}
			}
			 */
			session.setAttribute("fromQualification",null);
			session.setAttribute("fromQualificationMoveInDelta",null);

			String typeOfService = (String)session.getAttribute("typeOfService");
			if(Utils.isBlank(typeOfService))
			{
				typeOfService = "existingService";
			}

			logger.info("typeOfService="+typeOfService);

			ModelAndView mav = new ModelAndView();
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			if( typeOfService.equals("newService")){

				productResultManager.setSelectedExistingProvidersMap(new HashMap<String, String>());
				productResultManager.setSelectedExistingCustomerStatusMap(new HashMap<String, String>());

				request.getFlowScope().put("existingCustomerProvidersMap", productResultManager.getTransferCustomerEnabledProviderMap());
				logger.info("selectedExistingCustomerProvidersList="+productResultManager.getSelectedExistingCustomerStatusMap());
				request.getFlowScope().put("selectedExistingCustomerStatusMap", productResultManager.getSelectedExistingCustomerStatusMap());

				//DiscoveryA Dialogue service code   
				logger.info("Get_Dialog_Service_started_in_show_Discovery_new");
				logger.info("dynamicFlow.flowType=" + dynamicFlow.get("dynamicFlow.flowType"));
				dynamicFlow.put("dynamicFlow.page", "DiscoveryNew");
				dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
				startTimer=timer.getTime();
				if (salesCenterVo.getValueByName("drupalContentUrl") != null 
						&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
					//String dialoguesFromDrupal = SalesUtil.getDialoguesFormDrupalContent(contextMap,salesCenterVo);
					gettingDialoguesFromDrupal(contextMap, salesCenterVo, httpRequest, request);
				}
				else{
					generateDialoguesFromService(contextMap,salesCenterVo,request);
				}
				logger.info("TimeTakenforDialougeServicecall="+(timer.getTime()-startTimer));
				//for order summary page
				boolean hasProducts = false;
				int cnt=0;
				List<LineItemType> lineitemList = order.getLineItems().getLineItem();
				for(LineItemType lit:  lineitemList){
					for(AttributeEntityType entity : lit.getLineItemAttributes().getEntity()){
						if(entity.getSource() != null && entity.getSource().equalsIgnoreCase("provider_feedback")){
							for(AttributeDetailType attribute : entity.getAttribute()){
								if(attribute.getName().equals("Display") && attribute.getValue().equals("false")){
									cnt++;
								}
							}     
						}
					}
				}
				if(cnt != lineitemList.size()){
					hasProducts = true;
				}
				httpRequest.getSession().setAttribute("hasProducts", hasProducts);
				httpRequest.getSession().setAttribute("order", order);
				com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

				request.getFlowScope().put("address",getAddress(custAdr));
				mav.setViewName("sales.discoveryA");
				request.getFlowScope().put("View","sales.discoveryA");
			} 
			if( typeOfService.equals("existingService") )
			{
				//DiscoveryB Dialogue service code   
				logger.info("Get_Dialog_Service_started_in_show_DiscoveryTransfer");
				dynamicFlow.put("dynamicFlow.page", "DiscoveryTransfer");
				dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
				startTimer=timer.getTime();
				if (salesCenterVo.getValueByName("drupalContentUrl") != null 
						&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
					
					gettingDialoguesFromDrupal(contextMap, salesCenterVo, httpRequest, request);
				}
				else{
					generateDialoguesFromExistingService(contextMap,salesCenterVo,request);
				}
				logger.info("TimeTakenforDialougeServicecall="+(timer.getTime()-startTimer));
				//for order summary page
				boolean hasProducts = false;
				int cnt=0;
				List<LineItemType> lineitemList = order.getLineItems().getLineItem();
				for(LineItemType lit:  lineitemList){
					for(AttributeEntityType entity : lit.getLineItemAttributes().getEntity()){
						if(entity.getSource() != null && entity.getSource().equalsIgnoreCase("provider_feedback")){
							for(AttributeDetailType attribute : entity.getAttribute()){
								if(attribute.getName() != null && attribute.getValue() != null
										&& attribute.getName().equals("Display") && attribute.getValue().equals("false")){
									cnt++;
								}
							}     
						}
					}
				}
				if(cnt != lineitemList.size()){
					hasProducts = true;
				}
				com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

				request.getFlowScope().put("address",getAddress(custAdr));
				httpRequest.getSession().setAttribute("hasProducts", hasProducts);
				httpRequest.getSession().setAttribute("order", order);
				mav.setViewName("sales.discoveryB");
				request.getFlowScope().put("View","sales.discoveryB");

			}

			if( "moveInDeltaService".equalsIgnoreCase(typeOfService) )
			{
				dynamicFlow.put("dynamicFlow.page", "DiscoveryMoveIn");
				dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
				startTimer=timer.getTime();
				request.getFlowScope().put("moveInDeltaAccepted" , "Yes");
				if (salesCenterVo.getValueByName("drupalContentUrl") != null 
						&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
					
					gettingDialoguesFromDrupal(contextMap, salesCenterVo, httpRequest, request);
				}
				else{
					generateDialoguesFromExistingService(contextMap,salesCenterVo,request);
				}
				logger.info("TimeTakenforDialougeServicecall="+(timer.getTime()-startTimer));

				//for order summary page
				boolean hasProducts = false;
				int cnt=0;
				List<LineItemType> lineitemList = order.getLineItems().getLineItem();
				for(LineItemType lit:  lineitemList){
					for(AttributeEntityType entity : lit.getLineItemAttributes().getEntity()){
						if(entity.getSource() != null && entity.getSource().equalsIgnoreCase("provider_feedback")){
							for(AttributeDetailType attribute : entity.getAttribute()){
								if(attribute.getName().equals("Display") && attribute.getValue().equals("false")){
									cnt++;
								}
							}     
						}
					}
				}
				if(cnt != lineitemList.size()){
					hasProducts = true;
				}
				httpRequest.getSession().setAttribute("hasProducts", hasProducts);
				httpRequest.getSession().setAttribute("order", order);
				com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

				request.getFlowScope().put("address",getAddress(custAdr));
				mav.setViewName("sales.discoveryC");
				request.getFlowScope().put("View","sales.discoveryC");
			}
			request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
			if(session.getAttribute("customerDiscoveryMap")!=null){
				JSONObject jsonObject = new JSONObject((Map<String, String>)session.getAttribute("customerDiscoveryMap"));
				request.getFlowScope().put("questionListForMAV",jsonObject.toString());
			}
			if(session.getAttribute("notePadMap")!=null){
				request.getFlowScope().put("notePadMap",(Map<String, String>)session.getAttribute("notePadMap"));
			}
			logger.info("timeTakenForShowDiscovery="+timer.getTime());
			logger.info("showDiscovery_end");
			return new Event(this, "ok");
		}catch(Exception e){
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}


	}
	private void gettingDialoguesFromDrupal(
			Map<String, Map<String, String>> contextMap,
			SalesCenterVO salesCenterVo, HttpServletRequest httpRequest,
			RequestContext request) throws UnRecoverableException {


		try {
			String dialoguesFromDrupal = DialogServiceUI.INSTANCE.getDialoguesByContextForDrupal(contextMap,salesCenterVo.getValueByName("ordersource.programId"));
		
			if (Utils.isBlank(dialoguesFromDrupal)){
				dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueCacheKeyForDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
						, salesCenterVo.getValueByName("drupalContentUrl"));
			}
			if (Utils.isBlank(dialoguesFromDrupal)){
					dialoguesFromDrupal = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(DialogServiceUI.INSTANCE.generateDialogueBackupDefaultUserGroupDrupal(contextMap, salesCenterVo.getValueByName("ordersource.programId"))
						, salesCenterVo.getValueByName("drupalContentUrl"));	
					DialogServiceUI.INSTANCE.storeDialoguesByContextForDrupalDefaultUserGroup(contextMap,dialoguesFromDrupal);
				}if(Utils.isBlank(dialoguesFromDrupal)){
					generateDialoguesFromService(contextMap,salesCenterVo,request);
				}
				if(!Utils.isBlank(dialoguesFromDrupal)){
				String displayDialogues = RESTClientForDrupal.INSTANCE.generateSelectedDialogues(dialoguesFromDrupal,"Discovery_Data");
				String selectionDisplay = RESTClientForDrupal.INSTANCE.generateSelectedDialogues(dialoguesFromDrupal,"Discovery_Display_Bottom");
				if (Utils.isBlank(displayDialogues) || Utils.isBlank(selectionDisplay)){
					generateDialoguesFromService(contextMap,salesCenterVo,request);
				}else{
					displayDialogues = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(displayDialogues)));
					selectionDisplay = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(selectionDisplay)));
					request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));	
					request.getFlowScope().put("selection_display",selectionDisplay);
					request.getFlowScope().put("dialogue_display", displayDialogues);
				}
			}
		} catch (BaseException e) {
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
		
	}
	private void generateDialoguesFromExistingService(
			Map<String, Map<String, String>> contextMap,
			SalesCenterVO salesCenterVo, RequestContext request) throws UnRecoverableException {

		try {
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);

			Fieldset fieldsetList = null;
			StringBuilder events = new StringBuilder();
			List<DataGroup> dgList = null;

			List<DataField> dataFields = new ArrayList<DataField>();

			for (Dialogue dialogue : dialogueVO.getDialogueList()){
				logger.debug("dialogue_external_id=" + dialogue.getExternalId());
				dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					for(DataField data : dGroup.getDataFieldList()){
						dataFields.add(data);
					}
				}
			}        

			logger.debug("dataFields_size" + dataFields.size());

			fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSetDiscovery(events, dataFields, null);

			//for (Fieldset fieldset : fieldsetList) {
			String element = HtmlBuilder.INSTANCE.toString(fieldsetList);
			element = salesCenterVo.replaceNamesWithValues(element);
			events.append(element);
			request.getFlowScope().put("dialogueB" , events.toString());
			//}		
		} 
		catch (BaseException e) {
			logger.error("Exception_in_DiscoveryController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}

	}
	private void generateDialoguesFromService(Map<String, Map<String, String>> contextMap, 
			SalesCenterVO salesCenterVo, RequestContext request) throws UnRecoverableException {
		try {
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);

			Fieldset fieldsetList = null;
			StringBuilder events = new StringBuilder();
			List<DataGroup> dgList = null;

			List<DataField> dataFields = new ArrayList<DataField>();

			for (Dialogue dialogue : dialogueVO.getDialogueList()){
				logger.debug("dialogue_external_id=" + dialogue.getExternalId());
				dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					for(DataField data : dGroup.getDataFieldList()){
						dataFields.add(data);
					}
				}
			}        

			logger.debug("dataFields_size "+dataFields.size());

			fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSetDiscovery(events, dataFields, null);

			//for (Fieldset fieldset : fieldsetList) {
			String element = HtmlBuilder.INSTANCE.toString(fieldsetList);
			element = salesCenterVo.replaceNamesWithValues(element);
			events.append(element);
			request.getFlowScope().put("dialogueA" , events.toString());
		} 
		catch (BaseException e) {
			logger.error("Exception_in_DiscoveryController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
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
}
