package com.AL.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.factory.CartEventFactory;
import com.AL.ui.factory.CartLineItemUpdateFactory;
import com.AL.ui.factory.CartModelAndViewFactory;
import com.AL.ui.factory.CartProductFactory;
import com.AL.ui.factory.CartRulesFactory;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author 
 *
 */
@Controller("CartUpdateStateWebflowController")
public class CartUpdateStateWebflowController implements Action  {

	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartUpdateStateWebflowController.class);

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
	public Event execute(RequestContext req) throws Exception {
		logger.info("execute_begin_in_CartUpdateStateWebflowController");
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		String lineitem_id = request.getParameter("lineitem_id");
		String state = request.getParameter("state");
		String order_id = request.getParameter("orderId");
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

		Event event = CartEventFactory.INSTANCE.createOrderView(agentId, orderType, ControllerConstants.ORDER_SUMMARY_VIEW, ControllerConstants.ORDER_SUMMARY_TITLE, req);
		return event;
	}

}
