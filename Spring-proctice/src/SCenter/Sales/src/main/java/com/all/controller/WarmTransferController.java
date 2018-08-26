package com.AL.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.builder.SalesDialogueBuilder;
import com.AL.ui.dao.WarmtransferSeqDao;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartSalesContextFactory;
import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.WarmTransferHtmlFactory;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.di.v4.DataFieldMatrixType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedDialogsType;

@Controller("WarmTransferController")
public class WarmTransferController extends BaseController {
	private static final Logger logger = Logger.getLogger(WarmTransferController.class);
	private static final ObjectFactory oFactory = new ObjectFactory();
	private long startTimer=0;
	
	@Autowired
	private WarmtransferSeqDao warmtransferSeqDao;
	
	@RequestMapping(value = "/warmTransfer")
	public  ModelAndView warmTransfer(HttpServletRequest request) throws RecoverableException {
		StopWatch timer = new StopWatch();
		try{
			timer.start();
			logger.info("warmTransfer_begin");
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String orderId = (String) request.getParameter("orderId");
			String agentId = salesCenterVo.getValueByName("agent.id");
			startTimer = timer.getTime();
			OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(orderId,agentId,new HashMap<String, Object>(),"*", Boolean.FALSE, null);
			logger.info("TimeTakenforgetOrderByOrderNumber="+(timer.getTime()-startTimer));
			List<LineItemType> lineItems = CartLineItemFactory.INSTANCE.sortLineItemProducts(order, request);
			String providerId = WarmTransferFactory.INSTANCE.getLastLineItemProvider(lineItems, request);
			if (!Utils.isBlank(providerId) && providerId.equals("15498481")) {
				String rvSeqNumVal = String.valueOf(warmtransferSeqDao.getUpdatedSequenceId());
				logger.info("rvSeqNumVal="+rvSeqNumVal);
				if(rvSeqNumVal!= null){
					request.getSession().setAttribute("rvSeqNumVal", rvSeqNumVal);
				}
				request.getSession().setAttribute("providerId", providerId);
			}
			LineItemType lastLineItem = (LineItemType)request.getSession().getAttribute("lastLineItem");
			com.AL.xml.v4.CustAddress custAddress = SalesUtil.INSTANCE.getAddress(	order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
			salesCenterVo.setValueByName("address.zip",custAddress.getAddress().getPostalCode());
			ModelAndView mav = new ModelAndView("sales.warmTransfer");
			Map<String, Map<String, String>> dynamicFlowContextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, Map<String, String>> context1 = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("WarmTransfer",salesCenterVo,true);
			List<DialogueType> dialogueListType = SalesDialogueBuilder.INSTANCE.getWarmTransferDialogue(providerId,context1);

			List<DataFieldType> dataFieldList = new ArrayList<DataFieldType>();

			List<DataFieldMatrixType> dataFieldEnableList = new ArrayList<DataFieldMatrixType>();
			for (DialogueType dialoge : dialogueListType){
				for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()){
					dataFieldList.addAll(dataGroup.getDataFieldList().getDataField());
					dataFieldEnableList.add(dataGroup.getDataFieldMatrix());
				}
			}
			List<String> allDataFieldList = new ArrayList<String>();
			Map<String, Map<String, List<String>>> enableDependencyMap = DialogueUtil.INSTANCE.getEnableDependencies(dataFieldEnableList, request);
			Map<String, Map<String, List<String>>> disableDependencyMap = DialogueUtil.INSTANCE.getDisableDependencies(dataFieldList, enableDependencyMap, request);

			StringBuilder events = new StringBuilder();
			Map<String, String> dataFieldMap = new HashMap<String, String>();
			List<Fieldset> fieldsetList = WarmTransferHtmlFactory.INSTANCE.dialogueToFieldSet(events, dialogueListType, enableDependencyMap, allDataFieldList, dataFieldMap, request);

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
				element = parseHtmlTags(element);
				events.append(element);
			}
			if(!Utils.isBlank(request.getParameter("addressId"))){
				Long addressId = Long.valueOf(request.getParameter("addressId"));
				mav.addObject("addressId", addressId);
			}
			mav.addObject("address", custAddress);
			mav.addObject("warmTransferLineItemId", lastLineItem.getExternalId());
			mav.addObject("dataField", events.toString());
			mav.addObject("enableDialogueMap", enableDependencyMap);
			mav.addObject("disableDialogueMap", disableDependencyMap);
			mav.addObject("allDataFieldList", allDataFieldList);
			request.getSession().setAttribute("dataFieldMap", dataFieldMap);
			mav.addObject("isClosingCall", true);
			timer.stop();
			logger.info("warmTransfer_end");
			return mav;
		}catch(Exception e){
			timer.stop();
			request.setAttribute("message", e.getMessage());
			request.setAttribute("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			logger.error(e);
			throw new RecoverableException(e.getMessage());
		}
	}

	
	public  void showWarmTransfer(RequestContext requestContext,HttpServletRequest request) throws BaseException {
		try{
			logger.info("start_showWarmTransfer");
			String emailFlag = request.getParameter("emailFlag");
			String emailValue = request.getParameter("bestEmail");
			String csatVal = request.getParameter("CloseCallNoSale_CSAT_Survey_Email");
			ModelAndView mav = warmTransfer(request);
			requestContext.getFlowScope().put("addressId", mav.getModel().get("addressId"));
			requestContext.getFlowScope().put("warmTransferLineItemId", mav.getModel().get("warmTransferLineItemId"));
			requestContext.getFlowScope().put("enableDialogueMap", mav.getModel().get("enableDialogueMap"));
			requestContext.getFlowScope().put("disableDialogueMap", mav.getModel().get("disableDialogueMap"));
			requestContext.getFlowScope().put("allDataFieldList", mav.getModel().get("allDataFieldList"));
			requestContext.getFlowScope().put("dataField",  mav.getModel().get("dataField"));
			requestContext.getFlowScope().put("emailFlag", emailFlag);
			requestContext.getFlowScope().put("bestEmail", emailValue);
			requestContext.getFlowScope().put("CloseCallNoSale_CSAT_Survey_Email", csatVal);
			logger.info("end_showWarmTransfer");
		}catch(Exception e){
			request.setAttribute("message", e.getMessage());
			request.setAttribute("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			logger.error("error_while_showWarmTransfer",e);
			throw new UnRecoverableException(e.toString());
		}
	}
	
	
	
	
	private String parseHtmlTags(String dataFieldText) {
		dataFieldText = dataFieldText.replaceAll("&amp;", "&");
		dataFieldText = dataFieldText.replaceAll("&lt;", "<");
		dataFieldText = dataFieldText.replaceAll("&gt;", ">");
		return dataFieldText;
	}


	/**
	 * Updating order with warm transfer dialogues.
	 * 
	 * @param request
	 * @return
	 */
	public Event saveWarmTransferDetails(HttpServletRequest request) throws Exception
	{
		try{
			Map<String, String> reqParamMap =  dumpRequestScope(request);
			Object dataFieldMapObject = request.getSession().getAttribute("dataFieldMap");
			if(dataFieldMapObject!=null)
			{
				Map<String,String> dataFieldMap = (Map<String,String>)dataFieldMapObject;
				saveActiveDialogs(reqParamMap, dataFieldMap, request);
			}
			return new Event(this, "dispositionSaleEvent");
		}
		catch (Exception e) 
		{
			request.setAttribute("message", e.getMessage());
			request.setAttribute("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			logger.error("Error_Saving_WarmTransferDetails_on_order",e);
			throw new RecoverableException(e.getMessage());
		}
	}



	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/warmTransferSave")
	public ModelAndView saveWarmTransfer(HttpServletRequest request) throws Exception{

		Map<String, String> reqParamMap =  dumpRequestScope(request);
		Object dataFieldMapObject = request.getSession().getAttribute("dataFieldMap");
		if(dataFieldMapObject!=null)
		{
			Map<String,String> dataFieldMap = (Map<String,String>)dataFieldMapObject;
			saveActiveDialogs(reqParamMap, dataFieldMap, request);
		}

		logger.info("Saved_DispositionDetails");
		//SalesUtilsFactory.INSTANCE.clearPreviousSessionInfo(request);
		//String absoluteURL = (String) request.getSession().getAttribute("urlPath");
		String absoluteURL = (String) request.getParameter("urlPath");

		return new ModelAndView("redirect:" + absoluteURL
				+ "/salescenter/login_process");
		//return mav;
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView();
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> dumpRequestScope(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> e = request.getParameterNames();
		String name = null;
		while (e.hasMoreElements()) {
			name = e.nextElement();

			String[] args = request.getParameterValues(name);
			StringBuilder sb = new StringBuilder();
			for (String arg : args) {
				sb.append(arg).append(",");
			}
			sb.setLength(sb.length() - 1);
			map.put(name, sb.toString());
			//TODO:Remove
			logger.info(name + "=" + sb.toString());
		}

		return map;

	}



	/**
	 * 
	 * saving active dialogues on line item.
	 * 
	 * @param reqParamMap
	 * @param dataFieldMap
	 * @param request
	 */
	public void saveActiveDialogs(Map<String,String> reqParamMap,Map<String,String> dataFieldMap, HttpServletRequest request)
	{
		StopWatch timer = new StopWatch();
		timer.start();
		String orderId = request.getParameter("orderId");
		startTimer = timer.getTime();
		OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderId);
		logger.info("TimeTakenforgetOrderManagementRequestResponseByOrderNumber="+(timer.getTime()-startTimer));
		OrderType order = null;
		if(orderManagementRequestResponse.getResponse().getOrderInfo()!=null)
		{
			order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
		}

		if(order!=null)
		{
			LineItemType itemType = null;
			Object itemTypeObject = request.getSession().getAttribute("lastLineItem");
			if(itemTypeObject!=null)
			{
				itemType = (LineItemType)itemTypeObject;
			}

			if(itemType == null)
			{
				String warmTransferLineItemId = request.getParameter("warmTransferLineItemId");
				itemType = getWarmTransferLineItemTypeByLineItemId(order, warmTransferLineItemId);

			}

			SelectedDialogsType selectedDialogueType = oFactory.createSelectedDialogsType();
			SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory.createSelectedDialogsTypeDialogs();

			for(Entry<String, String> entry : dataFieldMap.entrySet()){

				if(!Utils.isBlank(reqParamMap.get(entry.getKey()))){

					DialogValueType.Value dialogueValueTypeValue = oFactory.createDialogValueTypeValue();
					dialogueValueTypeValue.setSelected(true);
					dialogueValueTypeValue.setType(entry.getValue());
					dialogueValueTypeValue.setValue(reqParamMap.get(entry.getKey()));

					DialogValueType dialogueValueType = oFactory.createDialogValueType();
					dialogueValueType.setExternalId(entry.getKey());
					dialogueValueType.getValue().add(dialogueValueTypeValue);
					selectedDialogueTypeDialogue.getDialog().add(dialogueValueType);
				}
			}

			LineItemType lineItem = oFactory.createLineItemType();
			lineItem.setLineItemNumber(itemType.getLineItemNumber());
			lineItem.setExternalId(itemType.getExternalId());
			selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
			lineItem.setActiveDialogs(selectedDialogueType);
			lineItem.setIsEventSelected(true);
			lineItem.setIsEventCompleted(true);
			LineItemCollectionType liCollection = new LineItemCollectionType();
			liCollection.getLineItem().add(lineItem);

			SalesContextType context = orderManagementRequestResponse.getResponse().getSalesContext();

			if (context != null)
			{
				context = CartSalesContextFactory.INSTANCE.updateSalesContextforWarmTransfer(context);
				startTimer = timer.getTime();
				LineItemService.INSTANCE.updateLineItem( order.getAgentId(), String.valueOf(order.getExternalId()),
						VOperation.updateLineItem.toString(), liCollection, context, new ErrorList());
				logger.info("TimeTakenforupdateLineItem="+(timer.getTime()-startTimer));
				timer.stop();
			}

		}

	}
	private LineItemType getWarmTransferLineItemTypeByLineItemId(
			OrderType order, String warmTransferLineItemId)
	{
		if( order.getLineItems()!=null 
				&& order.getLineItems().getLineItem()!=null 
				&& order.getLineItems().getLineItem().isEmpty())
		{
			for(LineItemType lineItemType : order.getLineItems().getLineItem())
			{
				if(warmTransferLineItemId.equals(String.valueOf(lineItemType.getExternalId())))
				{
					return lineItemType;
				}
			}
		}
		return null;
	}
}
