package com.AL.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.util.Utils;
import com.AL.ui.builder.DialogueVOBuilder;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.mapper.DialogueMapper;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.util.DateUtil;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.di.v4.DataFieldMatrixType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.RoleType;


@Controller("EndCallController")
public class EndCallController implements Action {
	private static final Logger logger = Logger.getLogger(EndCallController.class);
	
	
	public Event execute(RequestContext request) throws UnRecoverableException {
		StopWatch timer = new StopWatch();
		timer.start();
        long startTimer = 0;
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();

		try{
			logger.info("begin_closeCallNoSale");
			SalesCenterVO salesCenterVo = (SalesCenterVO)httpRequest.getSession().getAttribute("salescontext");
			httpRequest.getSession().setAttribute("ctiMessage", "");
			OrderType order =(OrderType)httpRequest.getSession().getAttribute("order");
			String isClosingOfferFlow = request.getFlowScope().getString("isClosingOfferFlow");
			logger.info("isClosingOfferFlow="+isClosingOfferFlow);
			if(!Utils.isBlank(isClosingOfferFlow) && "true".equalsIgnoreCase(isClosingOfferFlow)){
				OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(String.valueOf(order.getExternalId()), Boolean.TRUE);
				order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
				httpRequest.getSession().setAttribute("order",order);
			}
			CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(), RoleType.SERVICE_ADDRESS.toString());
			logger.info("Get_Dialog_Service_started_in_show_closingcall");

			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "CloseCallNoSale");

			Map<String, String> salesFlow = new HashMap<String, String>();

			salesFlow.put("salesFlow.requiresCSAT", "false");
			String email = order.getCustomerInformation().getCustomer().getBestEmailContact();
			if(order!=null && !Utils.isBlank(email)){
				if(Utils.isValidEmail(email)){
					salesFlow.put("salesFlow.requiresCSAT", "true");
				}
			}
			httpRequest.getSession().setAttribute("bestEmailContactValue", email);
			httpRequest.getSession().setAttribute("bestPhoneContactValue", order.getCustomerInformation().getCustomer().getBestPhoneContact());	
			contextMap.put("salesFlow", salesFlow);
			if(dynamicFlow.get("dynamicFlow.flowType").contains("confirm")||dynamicFlow.get("dynamicFlow.flowType").contains("nonConfirm")
					||dynamicFlow.get("dynamicFlow.flowType").contains("referrerSpecificConfirm")
					||dynamicFlow.get("dynamicFlow.flowType").contains("referrerSpecificNonConfirm")){
				startTimer=timer.getTime();
				if (salesCenterVo.getValueByName("drupalContentUrl") != null 
						&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
					String dialoguesFromDrupal = SalesUtil.getDialoguesFormDrupalContent(contextMap,salesCenterVo);
					if (Utils.isBlank(dialoguesFromDrupal)){
						generateDialoguesFromService(contextMap,salesCenterVo,request,dynamicFlow);
					}
					else{
						request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));
						request.getFlowScope().put("dialogue" , dialoguesFromDrupal);
					}
				}
				else{
					generateDialoguesFromService(contextMap,salesCenterVo,request,dynamicFlow);
				}
				logger.info("TimeTakenforDialougeServicecall="+(timer.getTime()-startTimer));
				request.getFlowScope().put("address", getAddress(custAdr));
				request.getFlowScope().put("isClosingCall", true);
			}
			else{
				startTimer=timer.getTime();
				if (salesCenterVo.getValueByName("drupalContentUrl") != null 
						&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
					
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
						generateDialoguesFromServiceForWeb(contextMap,salesCenterVo,request,dynamicFlow);
					}
					else{
						ObjectMapper mapper = new ObjectMapper();
						Map<String, Map<String, List<String>>> resultMap = new HashMap<String, Map<String,List<String>>>();
						Map<String, Map<String, List<String>>> resultMap1 = new HashMap<String, Map<String,List<String>>>();
						String displayDialogues = RESTClientForDrupal.INSTANCE.generateSelectedDialogues(dialoguesFromDrupal,"CloseCallNoSale_Data");
						String selectionDisplay = RESTClientForDrupal.INSTANCE.generateSelectedDialogues(dialoguesFromDrupal,"CloseCallNoSale_Display_Bottom");
						String allDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesAll(dialoguesFromDrupal);
						String enableDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesEnable(dialoguesFromDrupal);
						String disableDependencyFields = RESTClientForDrupal.INSTANCE.generateDialoguesDisable(dialoguesFromDrupal);
						if ( (Utils.isBlank(displayDialogues) && Utils.isBlank(selectionDisplay)) 
								&& (Utils.isBlank(allDependencyFields)|| Utils.isBlank(enableDependencyFields)||
								Utils.isBlank(disableDependencyFields)) ){
							generateDialoguesFromServiceForWeb(contextMap,salesCenterVo,request,dynamicFlow);
						}
						else{
							if (!Utils.isBlank(displayDialogues)) {
							displayDialogues = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(displayDialogues)));
							}
							if (!Utils.isBlank(selectionDisplay)) {
							selectionDisplay = StringEscapeUtils.unescapeHtml(salesCenterVo.replaceNamesWithValues(SalesUtil.INSTANCE.escapeSpecialCharacters(selectionDisplay)));	
							}
							List<String> allDataFieldList = new ArrayList<String>();
							if (!Utils.isBlank(allDependencyFields)){
								allDependencyFields = allDependencyFields.replace("[", "");
								allDependencyFields = allDependencyFields.replace("]", "");

								String dataFieldExtIds[] =  allDependencyFields.split(",");
								for(String dataFieldExtId : dataFieldExtIds )
								{
									allDataFieldList.add(dataFieldExtId.trim());
								}
								resultMap = mapper.readValue(enableDependencyFields, new TypeReference<HashMap<String, Map<String, List<String>>>>(){});
								resultMap1 = mapper.readValue(disableDependencyFields, new TypeReference<HashMap<String, Map<String, List<String>>>>(){});
							}

							request.getFlowScope().put("allDataFieldList", allDataFieldList);
							request.getFlowScope().put("enableDialogueMap", resultMap);
							request.getFlowScope().put("disableDialogueMap", resultMap1);

							request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));

							request.getFlowScope().put("cSATDialogue", selectionDisplay);
							request.getFlowScope().put("dialogue" , displayDialogues);
						}
					}
				}
				else{
					generateDialoguesFromServiceForWeb(contextMap,salesCenterVo,request,dynamicFlow);
				}
				logger.info("TimeTakenforDialougeServicecall="+(timer.getTime()-startTimer));
				request.getFlowScope().put("address", getAddress(custAdr));
				request.getFlowScope().put("isClosingCall", true);
			}
			if( order != null )
			{
				request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
				request.getFlowScope().put("isWarmTransferEnabled", WarmTransferFactory.INSTANCE.isWarmTransferEnabled(order, httpRequest));
			}
			//Suppress Sling TV dialogue when we have isSlingTvPurchased true
			if(LineItemUtil.isSlingTVPurchased(order) 
					&& request.getFlowScope().get("dialogue") != null 
					&& !Utils.isBlank(request.getFlowScope().getString("dialogue"))){
				Document doc = Jsoup.parse(request.getFlowScope().getString("dialogue"));
				if(doc != null){
					Element  element = doc.getElementById(Constants.SLING_FS_DATA_FIELD_ID);
					if(element != null){
						element.remove();
						request.getFlowScope().put("dialogue" , doc.toString());
					}	
				}
			}
			httpRequest.getSession().setAttribute("bestEmailContactValue", order.getCustomerInformation().getCustomer().getBestEmailContact());
			httpRequest.getSession().setAttribute("bestPhoneContactValue", order.getCustomerInformation().getCustomer().getBestPhoneContact());
			if(order.getMoveDate() != null 
					&& order.getMoveDate().toGregorianCalendar() != null 
					  && order.getMoveDate().toGregorianCalendar().getTime() != null){
				httpRequest.getSession().setAttribute("moveInDateValue", DateUtil.toDateString(order.getMoveDate()));
			}else{
				httpRequest.getSession().setAttribute("moveInDateValue", "");
			}
			try{
				String dominionProductExtIds = (String)httpRequest.getSession().getAttribute("dominionProductExtIds");
				if (httpRequest.getSession().getAttribute("pauseAndResumeURL")!= null && httpRequest.getSession().getAttribute("phoneId")!= null 
						&& !Utils.isBlank(dominionProductExtIds) && !"NA".equalsIgnoreCase(dominionProductExtIds)){
					RESTClientForDrupal.INSTANCE.resumeCall(String.valueOf(httpRequest.getSession().getAttribute("phoneId")), String.valueOf(httpRequest.getSession().getAttribute("pauseAndResumeURL")));
				}
			}
			catch(Exception e){
				logger.warn("Exception_occurred_in_resume_call"+e.getMessage());
			}
			logger.info("end_closeCallNoSale");
			logger.info("TimeTakenforEnd_closeCall="+timer.getTime());
			return new Event(this, "EndCallEvent");
		}
		catch(Exception e)
		{
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error(e);
			logger.error("Exception_occurred_at_line_number="+e.getStackTrace()[0].getLineNumber()+" in "+e.getStackTrace()[0].getClassName());
			logger.info("end_closeCallNoSale");
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}

	}


	private void generateDialoguesFromServiceForWeb(
			Map<String, Map<String, String>> contextMap,
			SalesCenterVO salesCenterVo, RequestContext request,
			Map<String, String> dynamicFlow) throws UnRecoverableException {
		try {
			DialogueResponseType dialogueResponseType = SalesDialogueFactory.INSTANCE.getDialoguesByContextNoSale(contextMap);
			HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
			List<DialogueType> dialogueVOList = new ArrayList<DialogueType>();
			dialogueVOList.addAll(dialogueResponseType.getResults().getDialogueList().getDialogue());

			List<DataField> dFieldList = getDataFieldFromDialogueResponse(dialogueResponseType);

			List<DataFieldType> dataFieldList = new ArrayList<DataFieldType>();

			List<DataFieldMatrixType> dataFieldEnableList = new ArrayList<DataFieldMatrixType>();

			for (DialogueType dialoge : dialogueVOList){
				for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()){
					dataFieldList.addAll(dataGroup.getDataFieldList().getDataField());
					dataFieldEnableList.add(dataGroup.getDataFieldMatrix());
				}
			}

			List<String> allDataFieldList = new ArrayList<String>();
			Map<String, Map<String, List<String>>> enableDependencyMap = DialogueUtil.INSTANCE.getEnableDependencies(dataFieldEnableList, httpRequest);
			Map<String, Map<String, List<String>>> disableDependencyMap = DialogueUtil.INSTANCE.getDisableDependencies(dataFieldList, enableDependencyMap, httpRequest);

			contextMap.remove("salesFlow");
			StringBuilder events = new StringBuilder();
			List<Fieldset> cSATFieldSetList = new ArrayList<Fieldset>();
			List<Fieldset> fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dFieldList, enableDependencyMap,cSATFieldSetList);

			for(Entry<String, Map<String, List<String>>> enableDependenciesEntry : enableDependencyMap.entrySet()){
				for(Entry<String, List<String>> enableDependenciesList : enableDependenciesEntry.getValue().entrySet()){
					for(String dependedEle : enableDependenciesList.getValue()){
						allDataFieldList.add(dependedEle);
					}
				}
			}
			for (Fieldset fieldset : fieldsetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				element = SalesUtilsFactory.INSTANCE.parseHtmlTags(element);
				events.append(element);
			}

			StringBuilder cSATDialogue = new StringBuilder();
			for (Fieldset fieldset : cSATFieldSetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				element = SalesUtilsFactory.INSTANCE.parseHtmlTags(element);
				cSATDialogue.append(element);
			}
			request.getFlowScope().put("cSATDialogue", cSATDialogue.toString());
			request.getFlowScope().put("enableDialogueMap", enableDependencyMap);
			request.getFlowScope().put("disableDialogueMap", disableDependencyMap);
			request.getFlowScope().put("allDataFieldList", allDataFieldList);
			request.getFlowScope().put("dialogue" , events.toString());
		} 
		catch (BaseException e) {
			logger.error("Exception_in_EndCallController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}

	}


	private void generateDialoguesFromService(
			Map<String, Map<String, String>> contextMap,
			SalesCenterVO salesCenterVo, RequestContext request,Map<String, String> dynamicFlow) throws UnRecoverableException {

		try {
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContextNoSale(contextMap);
			List<Fieldset> fieldsetList = null;
			StringBuilder events = new StringBuilder();
			List<DataGroup> dgList = null;
			List<DataField> dataFields = new ArrayList<DataField>();
			for (Dialogue dialogue : dialogueVO.getDialogueList()){
				logger.debug("dialogue_external_id"+dialogue.getExternalId());
				dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					for(DataField data : dGroup.getDataFieldList()){
						dataFields.add(data);
					}
				}
			}        
			logger.debug("dataFields_size"+dataFields.size());
			fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dataFields, null);
			logger.debug("events="+events);
			for (Fieldset fieldset : fieldsetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = dynamicFlow.get("dynamicFlow.flowType").contains("peco") ?element:salesCenterVo.replaceNamesWithValues(element);
				events.append(element);
			}
			request.getFlowScope().put("dialogue" , events.toString());

		} 
		catch (BaseException e) {
			logger.error("Exception_in_EndCallController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}


	private List<DataField> getDataFieldFromDialogueResponse(
			DialogueResponseType dialogueResponseType) {
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogueResponseType);
		SalesDialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new SalesDialogueVO());

		dialogueVO.setDialogueList(dialogueList);
		List<DataField> dataFields = new ArrayList<DataField>();

		for (Dialogue dialogue : dialogueVO.getDialogueList()){

			logger.debug("dialogue_external_id" + dialogue.getExternalId());
			List<DataGroup> dgList = dialogue.getDataGroupList();
			for(DataGroup dGroup : dgList){
				for(DataField data : dGroup.getDataFieldList()){
					dataFields.add(data);
				}
			}
		}  

		return dataFields;
	}


	public Address getAddress(CustAddress custAdr) {
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