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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.builder.CartLineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartProductFactory;
import com.AL.ui.factory.CartPromotionFactory;
import com.AL.ui.factory.CartRulesFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.service.V.impl.OrderCacheService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.ProductSummaryVO;

/**
 * @author PreetamMyneni
 *
 */
@Controller
public class CartLineItemUpdateController extends BaseController {


	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartLineItemUpdateController.class);

	/**
	 * Changes the status of lineitem in the cart to SALES_NEW_ORDER 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/scart/addStatusChange")
	public @ResponseBody String addStatusChange(HttpServletRequest request)throws Exception
	{
		logger.info("addStatusChange_begin_in_CartLineItemUpdateController");
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			String orderId = request.getParameter("orderId");
			String lineItemId = request.getParameter("lineItemId");
			HttpSession session = request.getSession();
			String agentId = CartUtil.INSTANCE.getAgentId(session);
	
			/*	
			 * Changing the Cart Rules
			 * 
			 * String error = CartRulesFactory.INSTANCE.cartRulesReAddValidator(orderId, lineItemId, session);			 
			 */
			String error = CartRulesFactory.INSTANCE.cartRulesReAdd(orderId, lineItemId, session);
	
			List<Integer> reasons = new ArrayList<Integer>(); 
			reasons.add(REASON_CODE); 
	
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
			if(error == null)
			{
				com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
	
				List<String> lineItemIdList = new ArrayList<String>();
				lineItemIdList.add(lineItemId);
	
				/*	
				 * ServiceCall for updating LineItemStatus to sales_new_order				 
				 */
				logger.info("Updating_the_LineItemStatus_to_sales_new_order");
				startTimer = timer.getTime();
				OrderType order = LineItemService.INSTANCE.	updateLineItemStatus(agentId, orderId, lineItemIdList,
						LineItemStatusCodesType.SALES_NEW_ORDER.value(), LineItemStatusCodesType.SALES_NEW_ORDER.value(), reasons,salesContext);
				logger.info("TimeTakenforUpdateLineItemStatusServiceCall="+(timer.getTime() - startTimer));
	
				/*	
				 * Handling the Error Response if UpdateLineItemStatus does not work				 
				 */
				if(order == null || order.getExternalId() == 0)
				{
					logger.warn("Handling_error_response_of-updateLineItemStatus(sales_new_order)");
					OrderCacheService.INSTANCE.clear(orderId);
					startTimer = timer.getTime();
					order = OrderService.INSTANCE.getOrderByOrderNumber(orderId,agentId,new HashMap<String, Object>(),"*", Boolean.FALSE, salesContext);
					logger.info("TimeTakenforGetOrderServiceCall="+(timer.getTime() - startTimer));
					OrderCacheService.INSTANCE.store(order, orderId);
				}
	
				/*	
				 * ReAdds the promotion if this Product has a promotion in cancelled_removed states 				 
				 */
				logger.info("ReAdding_the_Promotion");
				order = CartPromotionFactory.INSTANCE.reAddPromtions(order, lineItemIdList, session);
	
				/*	
				 * Builds the response for UpdateLineItem 			 
				 */
				LineItemCollectionType liCollection = CartProductFactory.INSTANCE.getProductDetails(order, request);
	
				/*	
				 * Adds the LineItemAttribute for which the LineItemStatus has been changed.			 
				 */
				for(LineItemType linItem : liCollection.getLineItem())
				{
					if(linItem.getExternalId() == Long.valueOf(lineItemId))
					{
						LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
						lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS, Constants.CKO_READY,
								Constants.DESC_CKO_READY, Constants.CKO));	
						linItem.setLineItemAttributes(lineItemAttributeType);
					}
				}
	
				/*	
				 * ServiceCall for updating LineItemAttributes to CKOReady				 
				 */
				logger.info("Updating_the_LineItemAttributes_and_Price");
				startTimer = timer.getTime();
				order = LineItemService.INSTANCE.updateLineItem(agentId, orderId, VOperation.updateLineItem.toString(), liCollection, salesContext);
				logger.info("TimeTakenforUpdateLineItemServiceCall="+(timer.getTime() - startTimer));
	
				request.getSession().setAttribute("order", order);
				String status = LineItemStatusCodesType.SALES_NEW_ORDER.value(); //order.getLineItems().getLineItem().get(0).getLineItemStatus().getStatusCode().value();//
	
				JSONObject result = new JSONObject();
				result.put("totalPoints", com.AL.ui.util.Utils.getTotalPoints(order, session));
				try
				{
					boolean stat = (order != null) ? true : false;
					result.put("liId", lineItemId);
					result.put("stat", stat);
					result.put("status", status);
					if(order.getLineItems().getLineItem() != null)
					{
						for(LineItemType lineItem:order.getLineItems().getLineItem())
						{
							if(lineItem.getExternalId() == Long.valueOf(lineItemId).longValue())
							{
								result.put("baseRecurringPrice", lineItem.getLineItemPriceInfo().getBaseRecurringPrice());
								result.put("baseNonRecurringPrice", lineItem.getLineItemPriceInfo().getBaseNonRecurringPrice());
								
							}
						}
					}
				}
				catch (JSONException e) 
				{
					logger.info("error_in_CartLineItemUpdateController",e);
					throw new Exception("JSONException: "+e.getMessage());
				}
				return result.toString();
			}
			else
			{
				JSONObject result = new JSONObject();
				try 
				{
					boolean stat =  false;
					result.put("stat", stat);
					result.put("liId", lineItemId);
					result.put("error", error);
				} 
				catch (JSONException e)
				{
					logger.info("error_in_CartLineItemUpdateController",e);
					throw new Exception("JSONException: "+e.getMessage());
				}
				
				return result.toString();
			}
		}finally{
			timer.stop();
		}

	}

	/**
	 * Changes the status of lineitem in the cart to CANCELLED_REMOVED 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/scart/removeProduct")
	public @ResponseBody String removeProduct(HttpServletRequest request)throws Exception
	{
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		String orderId = request.getParameter("orderId");
		String lineItemIds = request.getParameter("jsonArr");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		try{
			com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
	
			List<Integer> reasons = new ArrayList<Integer>(); 
			reasons.add(REASON_CODE); 
	
			JSONArray statusArr = new JSONArray();
			String[] lineItemIdArr = lineItemIds.split(",");
	
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
	
			/*	
			 * Get Order Service Call			 
			 */
			startTimer = timer.getTime();
			OrderType orderType = CartUtil.INSTANCE.getOrder(orderId, agentId, salesContext);
			logger.info("TimeTakenforGetOrderServiceCall="+(timer.getTime() - startTimer));
	
			List<String> lineItemIdList = new ArrayList<String>();
			
			String lineItemId = null;
			/*	
			 * 			 
			 */
			for(int i=0; i< lineItemIdArr.length; i++)
			{
				String[] linItemDetails = lineItemIdArr[i].split("\\|");
				lineItemId = linItemDetails[0];
				//If Dish Product
				if(linItemDetails[1].equals("27010360") || linItemDetails[1].equals("15500201") || linItemDetails[1].equals("24699452")
					|| linItemDetails[1].equals("32416075") || linItemDetails[1].equals("32946482")
					|| linItemDetails[1].equals("32952482")
					|| linItemDetails[1].equals("15499341")
					|| linItemDetails[1].equals("15499381"))
				{
					String status = null;
					//Check the status of the lineItem 
					for(LineItemType lineItem : orderType.getLineItems().getLineItem())
					{
						if(linItemDetails[0].equals(String.valueOf(lineItem.getExternalId())))
						{
							//Fetching the Status
							status = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem, Constants.CKO, Constants.STATUS);
						}
					}
					//If status is CKO_COMPLETE cannot remove the product
					if(status.equalsIgnoreCase(Constants.CKO_COMPLETE))
					{
						try {
							JSONObject obj = new JSONObject();
							obj.put("liId", linItemDetails[0]);
							obj.put("stat", false);
							if(linItemDetails[1].equals("15500201") || linItemDetails[1].equals("24699452")){
								obj.put("ErroMsg", "Error: Cannot remove ATT product after CKO");
							}else if(linItemDetails[1].equals("32946482")){
								obj.put("ErroMsg", "Error: Cannot remove Monitronics product after CKO");
						    }else if(linItemDetails[1].equals("32952482")){
								obj.put("ErroMsg", "Error: Cannot remove GNG product after CKO");
						    }else if(linItemDetails[1].equals("32416075")){
								obj.put("ErroMsg", "Error: Cannot remove CenturyLink product after CKO");
						    }else if(linItemDetails[1].equals("179296")){
							    obj.put("ErroMsg", "Error: Cannot remove Qwest product after CKO");
					        }else if(linItemDetails[1].equals("15499341")){
							    obj.put("ErroMsg", "Error: Cannot remove Cox product after CKO");
					        }else if(linItemDetails[1].equals("15499381")){
							    obj.put("ErroMsg", "Error: Cannot remove Txu product after CKO");
					        }else{
								obj.put("ErroMsg", "Error: Cannot remove Dish product after CKO");
							}
							statusArr.put(obj);
						}
						catch (JSONException e) 
						{
							throw new Exception("JSONException: "+e.getMessage());
						}
					}
					else
					{
						lineItemIdList.add(linItemDetails[0]);
					}
				}
				else
				{
					lineItemIdList.add(linItemDetails[0]);
				}
			}
	
			if(lineItemIdList.size() > 0)
			{		
				/*	
				 * Builds the response for UpdateLineItem 			 
				 */
				LineItemCollectionType liCollection = CartProductFactory.INSTANCE.getProductDetails(orderType, request);
	
				/*	
				 * Gets all the associated promotions LineItem External Id			 
				 */
				CartPromotionFactory.INSTANCE.getLineItemWithPromtions(orderType, lineItemIdList);
	
				/*	
				 * ServiceCall for updating LineItemStatus to sales_new_order				 
				 */
				logger.info("Updating_the_LineItemStatus_to_Cancelled_removed");
				startTimer = timer.getTime();
				OrderType order = LineItemService.INSTANCE.updateLineItemStatus(agentId, orderId, lineItemIdList, 
						LineItemStatusCodesType.CANCELLED_REMOVED.value(), LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, salesContext);
				logger.info("TimeTakenforUpdateLineItemStatusServiceCall="+(timer.getTime() - startTimer));
	
				/*	
				 * Handling the Error Response if UpdateLineItemStatus does not work				 
				 */
				if(order == null || order.getExternalId() == 0)
				{
					logger.warn("Handling_error_response_of_updateLineItemStatus(Cancelled_removed)");
					OrderCacheService.INSTANCE.clear(orderId);
					startTimer = timer.getTime();
					order = OrderService.INSTANCE.getOrderByOrderNumber(orderId,agentId,new  HashMap<String,Object>(),"*",Boolean.FALSE,salesContext);
					logger.info("TimeTakenforGetOrderServiceCall="+(timer.getTime() - startTimer));
					OrderCacheService.INSTANCE.store(order, orderId);
				}
	
				/*	
				 * Adds the LineItemAttribute for which the LineItemStatus has been changed.			 
				 */
				for(LineItemType linItem : liCollection.getLineItem())
				{
					if(lineItemIdList.contains(String.valueOf(linItem.getExternalId())))
					{
						LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
						lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS, Constants.REMOVED,
								Constants.DESC_REMOVED, Constants.CKO));	
						linItem.setLineItemAttributes(lineItemAttributeType);
					}
				}
	
				/*	
				 * ServiceCall for updating LineItemAttributes to Removed				 
				 */
				logger.info("Updating_the_LineItemAttributes_and_Price");
				startTimer = timer.getTime();
				orderType =  LineItemService.INSTANCE.updateLineItem(agentId, orderId, 
						VOperation.updateLineItem.toString(),liCollection,salesContext);
				logger.info("TimeTakenforUpdateLineItemServiceCall="+(timer.getTime() - startTimer));
	
				request.getSession().setAttribute("order", orderType);
				List<LineItemType> lineItemTypes = CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(orderType);
				request.getSession().setAttribute("lineItemList", lineItemTypes);
				
				String status = LineItemStatusCodesType.CANCELLED_REMOVED.value();
				for(LineItemType lineItemType:order.getLineItems().getLineItem())
				{
					if(lineItemIdList.contains(String.valueOf(lineItemType.getExternalId())))
					{
						//CartLineItemUtil.getTotalPoints(lineItemType, session, Constants.SUBTRACT);
						try 
						{
							boolean stat = (order == null) ? false : true;
							JSONObject obj = new JSONObject();
							obj.put("liId", lineItemType.getExternalId());
							obj.put("stat", stat);
							obj.put("status", status);
							obj.put("totalPoints", com.AL.ui.util.Utils.getTotalPoints(orderType, session));
							statusArr.put(obj);
						}
						catch (JSONException e)
						{
							throw new Exception("JSONException: "+e.getMessage());
						}
					}
	
				}
				
			}
			return statusArr.toString();
		}finally
		{
			timer.stop();
		}
	}

	/**
	 * @param request
	 * @return String
	 */
	@RequestMapping(value="/update/lineItemFeatures")
	@Deprecated
	protected @ResponseBody String updateLineItemFeatures(HttpServletRequest request)throws Exception{

		String updateFeaturesJson = request.getParameter("updateFeaturesJson");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		OrderType orderType = oFactory.createOrderType();

		try 
		{
			if(!updateFeaturesJson.isEmpty())
			{

				JSONObject updateFeaturesJsonObject = new JSONObject(updateFeaturesJson);
				JSONObject order = updateFeaturesJsonObject.getJSONObject("order");
				String orderId = order.getString("externalId"); 
				JSONObject lineItemJSON = order.getJSONObject("lineItem");
				String lineItemId = lineItemJSON.getString("externalId");

				LineItemType lineItemType=oFactory.createLineItemType();
				lineItemType = CartLineItemBuilder.INSTANCE.createLineItemFeatures(lineItemType,lineItemJSON);
				lineItemType.setExternalId(Long.valueOf(lineItemId));

				LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
				liCollection.getLineItem().add(lineItemType);

				/*	
				 * Getting SalesContext from the session				 
				 */
				SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
				/*	
				 * ServiceCall for updating LineItemFeatures				 
				 */
				orderType = LineItemService.INSTANCE.updateLineItem(agentId, orderId, VOperation.updateLineItem.toString(), liCollection, salesContext);

				JSONObject result = new JSONObject();

				if(orderType.getExternalId()==0L)
				{
					result.put("liId", lineItemId);
					result.put("stat", false);
					return result.toString();
				}
				else
				{
					for(LineItemType lineItem : orderType.getLineItems().getLineItem())
					{
						if(lineItem.getExternalId() == Long.valueOf(lineItemId))
						{
							result.put("liId", lineItemId);
							result.put("recurringPrice", lineItem.getLineItemPriceInfo().getBaseRecurringPrice());
							result.put("stat", true);
						}
					}
					return result.toString();
				}
			}

		} 
		catch (JSONException e) 
		{
			throw new Exception("JSONException: "+e.getMessage());
		}
		return null;

	}


	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			{
		return new ModelAndView();
			}

}
