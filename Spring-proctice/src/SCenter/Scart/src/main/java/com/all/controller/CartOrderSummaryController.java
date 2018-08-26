package com.AL.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.InputType;
import com.AL.ui.factory.CartLineItemUpdateFactory;
import com.AL.ui.factory.CartModelAndViewFactory;
import com.AL.ui.factory.CartProductFactory;
import com.AL.ui.factory.CartRulesFactory;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.MetricsUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author 
 *
 */
@Controller
public class CartOrderSummaryController extends BaseController  {

	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartOrderSummaryController.class);

	/**
	 * Based on the orderId in the URL builds the view for Order summary screen.
	 * 
	 * 
	 * @param id(OrderType externalId)
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/summary/{id}" )
	public ModelAndView orderSummary(@PathVariable String id, HttpServletRequest request) throws Exception
	{
		logger.info("orderSummary_begin_in_CartOrderSummaryController");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);

		Double serviceStartTime = Double.valueOf(System.currentTimeMillis());
		/*	
		 * Service Call for Sales Context 		 
		 */

		SalesContextType context = OrderService.INSTANCE.getSalesContext(id, agentId);
		SalesContextFactory.INSTANCE.setContextInSession(session, context);

		/*	
		 * Gets the Order from Cache 		 
		 */
		OrderType order = CartUtil.INSTANCE.getOrder(id, agentId, context);

		//TODO:Remove this CALL after OME fix(Temporary Fix for Calculating Static Prices)
		order = CartProductFactory.INSTANCE.validateStaticPrices(order, session);
		logger.info("Order_Summary_Service_Calls_Completed[orderId="+id+"][Time="+ CartUtil.INSTANCE.getTimeDiff(serviceStartTime) + "sec]");

		logger.info("Building_Order_Summary_View");
		ModelAndView mav = CartModelAndViewFactory.INSTANCE.createOrderView(agentId, order, ORDER_SUMMARY_VIEW, ORDER_SUMMARY_TITLE, request);
		mav.addObject("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
		logger.info("orderSummary_end_in_CartOrderSummaryController");
		return mav;
	}

	/**
	 * Based on the OrderId and LineItemId Changes its state.
	 * 
	 * 
	 * @param order_id(OrderType externalId)
	 * @param lineitem_id(LineItemType externalId)
	 * @param state
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/order/{order_id}/lineitem/{lineitem_id}/{state}")
	public ModelAndView updateState(@PathVariable String order_id, @PathVariable String lineitem_id, @PathVariable String state,
			HttpServletRequest request)  throws Exception
	{
		logger.info("Changing_the_status_of_"+lineitem_id+"_LineItem_to_"+state);

		String agentId = CartUtil.INSTANCE.getAgentId(request.getSession());
		List<String> lineItemIds = new ArrayList<String>();
		lineItemIds.add(lineitem_id);
		OrderType orderType = null;

		if(state.equals("delete"))
		{
			logger.info("Changing_the_LineItem_status_as_"+LineItemStatusCodesType.CANCELLED_REMOVED.value());
			orderType = CartLineItemUpdateFactory.INSTANCE.removeLineItem(agentId, order_id, lineItemIds, request);
			
		}
		else
		{
			logger.info("Changing_the_LineItem_status_as_"+LineItemStatusCodesType.SALES_NEW_ORDER.value());
			orderType = CartLineItemUpdateFactory.INSTANCE.reAddLineItem(agentId, order_id, lineItemIds, request);
		}

		logger.info("Status_change_completed");
		return CartModelAndViewFactory.INSTANCE.createOrderView(agentId, orderType, ORDER_SUMMARY_VIEW, ORDER_SUMMARY_TITLE, request);
	}

	/**
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/scart/cartRulesValidator")
	public @ResponseBody String cartRulesValidator(HttpServletRequest request)throws Exception
	{

		String orderId = request.getParameter(ORDERID);
		HttpSession session = request.getSession();
		String lineItemId = request.getParameter(LINEITEMID);
		String lineItemNumber = request.getParameter(LINEITEMNUMBER);
		logger.info("Checking_the_Cart_Rules_for_Re-Adding_the_Product");

		/*	
		 * Changing the Cart Rules
		 * 
		 * String error = CartRulesFactory.INSTANCE.cartRulesReAddValidator(orderId, lineItemId, session);	
		 * 		 
		 */

		String error = CartRulesFactory.INSTANCE.cartRulesReAdd(orderId, lineItemId, session);	
		JSONObject result = new JSONObject();
		try {
			if(error == null)
			{

				logger.debug("Product_satisfies_the_Cart_Rules[orderId="+orderId+"][LineItemId="+lineItemId+"]");

				boolean stat =  true;
				result.put("stat", stat);
				result.put(LINEITEMID, lineItemId);
				result.put(LINEITEMNUMBER, lineItemNumber);
			}
			else
			{
				logger.debug("Product_Cannot_be_added[orderId="+orderId+"][LineItemId="+lineItemId+"]");

				boolean stat =  false;
				result.put("stat", stat);
				result.put(LINEITEMID, lineItemId);
				result.put("error", error);
			}
		} 
		catch (JSONException e)
		{
			logger.warn("error_while_validating_cartRules",e);
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception 
			{
		return new ModelAndView();
			}
	
	/**
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/update_dialogues")
	public @ResponseBody void cancelCKODialoguesUpdate(HttpServletRequest request)throws Exception
	{
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		logger.info("cancelCKODialoguesUpdate_begin_in_CartOrderSummaryController");
		String data = request.getParameter("data");
		HttpSession session = request.getSession();
		try {
			logger.info("data_for_cancelCKO"+data);
			
			JSONObject activeDialogueData = new JSONObject(data);
			logger.info("activeDialogueData_for_cancelCKO="+activeDialogueData);
			
			String orderId = null;
			
			logger.info(activeDialogueData.getString("orderId"));
			logger.info(activeDialogueData.getString("lineItems"));
			logger.info(activeDialogueData.getBoolean("utilityOffer"));
			
			boolean isUtilityOffer = activeDialogueData.getBoolean("utilityOffer");
			
			String lineItemId = activeDialogueData.getString("lineItems");
			
			String lineItem = lineItemId.substring(lineItemId.indexOf("[")+1, lineItemId.indexOf("]"));
			if(!activeDialogueData.isNull("orderId")){
				orderId = activeDialogueData.getString("orderId");
			}
			else{
				orderId = (String) session.getAttribute("orderId");
			}
			
			startTimer = timer.getTime();
			OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderId);
			logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));
			
			SalesContextType context = orderManagementRequestResponse.getResponse().getSalesContext();
			SalesContextFactory.INSTANCE.setContextInSession(session, context);

			String productExtID = request.getParameter("productExtIds");
			OrderType  order = CartProductFactory.INSTANCE.getLineItemsFromOrder(orderManagementRequestResponse, activeDialogueData, lineItem, isUtilityOffer, productExtID);
			
			if(order != null)
			{
				session.setAttribute("order",order);				
			}
			logger.info("cancelCKODialoguesUpdate_end_in_CartOrderSummaryController");
		} catch (JSONException e) 
		{
			logger.warn("error_while_updating_cancel_CKO_dialogues_on_order", e);
		}
		finally
		{
			timer.stop();
		}
	}
}
