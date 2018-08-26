package com.AL.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.constants.Constants;
import com.AL.ui.dao.ProviderDao;
import com.AL.ui.factory.CartCKOFactory;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartModelAndViewFactory;
import com.AL.ui.factory.CartProductFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.util.CartUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.productResults.util.Utils;

/**
 * @author 
 *
 */
@Controller
public class CartCKOController extends BaseController {

	/**
	 * Auto wiring ProviderDao
	 */
	@Autowired
	ProviderDao providerDao;


	private static final Logger logger = Logger.getLogger(CartCKOController.class);

	/**
	 * Based on the providerId the respective CKO will be initiated.
	 * 
	 * @param customerId(CustomerType externalId)
	 * @param orderId(OrderType externalId)
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/CKO/{customerId}/order/{orderId}")
	public ModelAndView CKOSelected(@PathVariable String customerId,
			@PathVariable String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception
			{

		Double startTime = Double.valueOf(System.currentTimeMillis());

		/**	
		 * Building the ModelandView map for product CKO				 
		 */
		Map<String,Object> modelAndViewMap = CartCKOFactory.INSTANCE.getCKOMavMap(orderId, customerId, request, providerDao);

		/**	
		 * Building the ModelandView Object for product CKO				 
		 */
		ModelAndView mav = CartModelAndViewFactory.INSTANCE.craeteCKOView(modelAndViewMap, CKO_VIEW);
		commonOptions(response);

		logger.info("Completed Building CKO View [orderId: "+orderId+"][Time: "+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");

		return mav;
			}

	/**
	 * Intermediate termination of CKO. Changes the OrderSummary status to CKOIncomplete
	 * 
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/summary/{id}", params ="cancelCKO")
	public ModelAndView cancelCKO(@PathVariable String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Cancel CKO");
		Double startTime = Double.valueOf(System.currentTimeMillis());

		String lineItemId = request.getParameter("linItemInCKO");
		String isUtilityOffer = request.getParameter("isUtilityOffer");
		String isRecommendation = request.getParameter("isRecommendation");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		OrderType order = null;
		if(!(Utils.isBlank(lineItemId))){
			com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
			/**	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

			order = OrderService.INSTANCE.getOrderByOrderNumber(id,agentId,new HashMap<String, Object>(),"*", Boolean.FALSE, salesContext);

			if(isUtilityOffer.equalsIgnoreCase("true"))
			{
				LineItemType lineItemType = oFactory.createLineItemType();
				LineItemAttributeType lineItemAttributeType = oFactory.createLineItemAttributeType();

				for(LineItemType lineItem : order.getLineItems().getLineItem())
				{
					if(Long.valueOf(lineItemId) == lineItem.getExternalId())
					{
						lineItemType.setExternalId(lineItem.getExternalId());
						lineItemType.setLineItemNumber(lineItem.getLineItemNumber());
					}
				}

				//Adding the LineItem Attribute CKO_INCOMPLETE
				lineItemAttributeType.getEntity().add(
						CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS, Constants.CKO_INCOMPLETE,
								"CKO TERMINATED", Constants.CKO));	

				lineItemType.setLineItemAttributes(lineItemAttributeType);
				LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
				liCollection.getLineItem().add(lineItemType);

				//Updating the LineItemAttribute
				order = LineItemService.INSTANCE.updateLineItem(agentId, id, VOperation.updateLineItem.toString(), liCollection, salesContext);
			}
			else
			{
				/**	
				 * Builds the response for UpdateLineItem 			 
				 */
				LineItemCollectionType liCollection = CartProductFactory.INSTANCE.getProductDetails(order, request);

				/**	
				 * Adds the LineItemAttribute for which the LineItemStatus has been changed.			 
				 */
				for(LineItemType linItem : liCollection.getLineItem())
				{
					logger.info("CartCKOController_lineItemId=" + (!Utils.isBlank(lineItemId) ? lineItemId : "null"));
					if(linItem.getExternalId() == Long.valueOf(lineItemId))
					{
						LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
						lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS, Constants.CKO_INCOMPLETE,
								"CKO TERMINATED", Constants.CKO));	
						linItem.setLineItemAttributes(lineItemAttributeType);
					}
				}

				/*	
				 * ServiceCall for updating LineItemAttributes to CKOReady				 
				 */
				order = LineItemService.INSTANCE.updateLineItem(agentId, id, VOperation.updateLineItem.toString(), liCollection, salesContext);
			}
		}
		else{
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
			order = OrderService.INSTANCE.getOrderByOrderNumber(id,agentId,new HashMap<String, Object>(),"*", Boolean.FALSE, salesContext);
		}
		String absoluteURL = (String) request.getSession().getAttribute("urlPath");
		if(isUtilityOffer.equalsIgnoreCase("true") && isRecommendation.equalsIgnoreCase("false"))
		{
			//Redirecting to qualification
			logger.info("Cancel in UtilityOffer redirecting to qualification [orderId: "+id+"][Time: "+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");
			return new ModelAndView("redirect:" + absoluteURL + "/salescenter/qualification");
		}
		else if(isUtilityOffer.equalsIgnoreCase("true") && isRecommendation.equalsIgnoreCase("true"))
		{
			//Redirecting to Recommendations
			logger.info("Cancel in UtilityOffer redirecting to Recommendations [orderId: "+id+"][Time: "+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");
			return new ModelAndView("redirect:" + absoluteURL + "/salescenter/recommendations");
		}
		else
		{
			//Redirecting to OrderSummary
			logger.info("Cancel in CKO redirecting to OrderSummary [orderId: "+id+"][Time: "+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");
			return CartModelAndViewFactory.INSTANCE.createOrderView(agentId, order, ORDER_SUMMARY_VIEW, ORDER_SUMMARY_TITLE, request);
		}
	}

	/**
	 * @param theHttpServletResponse
	 */
	@RequestMapping(method = RequestMethod.OPTIONS)
	public void commonOptions(HttpServletResponse theHttpServletResponse) {
		theHttpServletResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, my-cool-header");
		theHttpServletResponse.addHeader("Access-Control-Max-Age", "60");  
		theHttpServletResponse.addHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
		theHttpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse response) throws Exception 
			{
		commonOptions(response);
		return new ModelAndView();
			}
}
