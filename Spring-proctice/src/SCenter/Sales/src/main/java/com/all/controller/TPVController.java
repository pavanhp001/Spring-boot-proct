package com.AL.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.execution.Event;

import com.AL.ui.factory.CartSalesContextFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ErrorList;
import com.AL.V.exception.RecoverableException;
import com.AL.xml.di.v4.DataConstraintType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedDialogsType;

@Controller("TPVController")
public class TPVController extends BaseController {
	private static final Logger logger = Logger.getLogger(TPVController.class);
	private static final ObjectFactory oFactory = new ObjectFactory();



	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tpvSave")
	public ModelAndView saveTPVDialogues(HttpServletRequest request) throws Exception{
		try{
		Map<String, String> reqParamMap =  dumpRequestScope(request);
		saveActiveDialogs(reqParamMap, request);

		logger.info("Saved_TPV_Dialogues");
		//SalesUtilsFactory.INSTANCE.clearPreviousSessionInfo(request);
		String absoluteURL = (String) request.getParameter("urlPath");
		//String absoluteURL = (String) request.getSession().getAttribute("urlPath");
		
		logger.info("saveTPVDialoguesOnFlow_end");
		return new ModelAndView("redirect:" + absoluteURL + "/salescenter/login_process");
		}catch (Exception e) 
		{
			logger.error("error_in_saveTPVDialogues", e);
			throw e;
		}
		
	}
	
	
	/**
	 * Updating order with TPV dialogues.
	 * 
	 * @param request
	 * @return
	 */
	public Event saveTPVDialoguesOnFlow(HttpServletRequest request) throws Exception
	{
		try
		{
			logger.info("saveTPVDialoguesOnFlow_begin");
			Map<String, String> reqParamMap =  dumpRequestScope(request);
			saveActiveDialogs(reqParamMap, request);
			logger.info("saveTPVDialoguesOnFlow_end");
			return new Event(this, "dispositionSaleEvent");
		}
		catch (Exception e) 
		{
			logger.error("error_in_saveTPVDialoguesOnFlow",e);
			throw new RecoverableException(e.getMessage());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
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

	@SuppressWarnings("unchecked")
	public void saveActiveDialogs(Map<String,String> reqParamMap, HttpServletRequest request)throws Exception
	{   long startTimer=0;
		StopWatch timer=new StopWatch();
		timer.start();
		try{
		String orderId = (String) request.getParameter("orderId");
		logger.info("orderId="+orderId);
		startTimer=timer.getTime();
		OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderId);
		logger.info("Time_taken_for_getOrder_call="+(timer.getTime()-startTimer));
		OrderType order = null;
		if(orderManagementRequestResponse.getResponse().getOrderInfo()!=null)
		{
			order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
		}
		
		if(order!=null)
		{
			LineItemType itemType = null;
			if(request.getSession().getAttribute("tpvLineItems")!=null)
			{
				List<LineItemType> lineItems = null;
				if(request.getSession().getAttribute("tpvLineItems") != null){
				    lineItems = (List<LineItemType>)request.getSession().getAttribute("tpvLineItems");
				    itemType = lineItems.get(0);
				}
			}
			
			if(itemType==null)
			{
				String tpvLineItemId = request.getParameter("tpvLineItemId");
				
				if(!Utils.isBlank(tpvLineItemId))
				{
					itemType = getTPVLineItemTypeByLineItemId(order, tpvLineItemId);
				}
			}
			
			if(itemType!=null)
			{
				String extIdString = reqParamMap.get("extIds");
				logger.info("External_ID="+extIdString);
				List<DataFieldType>dataFieldList=(List<DataFieldType>) request.getSession().getAttribute("dataFieldList");
				LineItemType lineItem = oFactory.createLineItemType();
				lineItem.setIsEventSelected(true);
				if( dataFieldList!=null && !dataFieldList.isEmpty() )
				{
					SelectedDialogsType selectedDialogueType = oFactory.createSelectedDialogsType();

					SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory.createSelectedDialogsTypeDialogs();

					DialogValueType dialogueValueType = oFactory.createDialogValueType();

					DialogValueType.Value dialogueValueTypeValue = oFactory.createDialogValueTypeValue();
					
					
					for(DataFieldType entry : dataFieldList){

						if(!Utils.isBlank(reqParamMap.get(entry.getExternalId())) && (extIdString.indexOf(entry.getExternalId()) >= 0)){

							dialogueValueType = oFactory.createDialogValueType();

							dialogueValueTypeValue = oFactory.createDialogValueTypeValue();

							dialogueValueType.setExternalId(entry.getExternalId());

							dialogueValueTypeValue.setSelected(true);
							DataConstraintType constraint = entry.getDataConstraints();
							if (constraint.getBooleanConstraint() != null) {
								dialogueValueTypeValue.setType("boolean");
							} else if (constraint.getStringConstraint() != null) {
								dialogueValueTypeValue.setType("string");
							} else if (constraint.getIntegerConstraint() != null) {
								dialogueValueTypeValue.setType("integer");
							}

							dialogueValueTypeValue.setValue(reqParamMap.get(entry.getExternalId()));
							dialogueValueType.getValue().add(dialogueValueTypeValue);
							selectedDialogueTypeDialogue.getDialog().add(dialogueValueType);
						}
						else if(!Utils.isBlank(entry.getValueTarget()) && entry.getValueTarget().equals("tpv.event.completed") 
								&& Utils.isBlank(reqParamMap.get(entry.getExternalId()))){
							dialogueValueType = oFactory.createDialogValueType();

							dialogueValueTypeValue = oFactory.createDialogValueTypeValue();

							dialogueValueType.setExternalId(entry.getExternalId());

							dialogueValueTypeValue.setSelected(true);
							DataConstraintType constraint = entry.getDataConstraints();
							if (constraint.getBooleanConstraint() != null) {
								dialogueValueTypeValue.setType("boolean");
							} else if (constraint.getStringConstraint() != null) {
								dialogueValueTypeValue.setType("string");
							} else if (constraint.getIntegerConstraint() != null) {
								dialogueValueTypeValue.setType("integer");
							}
							dialogueValueTypeValue.setValue("No");
							dialogueValueType.getValue().add(dialogueValueTypeValue);
							selectedDialogueTypeDialogue.getDialog().add(dialogueValueType);
						}
					}
					
					
					lineItem.setExternalId(itemType.getExternalId());
					selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
					lineItem.setActiveDialogs(selectedDialogueType);
					
					for(DataFieldType dataField : dataFieldList ){

						if(!Utils.isBlank(dataField.getValueTarget()) && dataField.getValueTarget().equals("tpv.event.completed"))
						{
							// check whether the external id is present in the request param map or not,
							// if it is present, 
							if(!Utils.isBlank(reqParamMap.get(dataField.getExternalId())) && reqParamMap.get(dataField.getExternalId()).equalsIgnoreCase("Yes")){
								lineItem.setIsEventCompleted(true);
							}
							else{
								lineItem.setIsEventCompleted(false);
							}
						}
					}
				}

				lineItem.setLineItemNumber(itemType.getLineItemNumber());
				LineItemCollectionType liCollection = new LineItemCollectionType();
				
				request.getSession().setAttribute("dataFieldList", null);
				liCollection.getLineItem().add(lineItem);

				SalesContextType context = orderManagementRequestResponse.getResponse().getSalesContext();
				if (context != null)
				{
					context = CartSalesContextFactory.INSTANCE.updateSalesContextforWarmTransfer(context);
					startTimer=timer.getTime();
					LineItemService.INSTANCE.updateLineItem( order.getAgentId(), String.valueOf(order.getExternalId()),
							VOperation.updateLineItem.toString(), liCollection, context, new ErrorList());
					logger.info("TimetakenforUpdateLineItemcall="+(timer.getTime()-startTimer));
				}
			}
		}
		}finally{
			timer.stop();
		}
		
	}


	private LineItemType getTPVLineItemTypeByLineItemId(OrderType order,
			String tpvLineItemId)
	{
		if(order.getLineItems()!=null 
			&& order.getLineItems().getLineItem()!=null 
			&& !order.getLineItems().getLineItem().isEmpty())
		{
			for(LineItemType lineItemType : order.getLineItems().getLineItem())
			{
				if(tpvLineItemId.equals(String.valueOf(lineItemType.getExternalId())))
				{
					return lineItemType;
				}
			}
		}
		return null;
	}


	
}