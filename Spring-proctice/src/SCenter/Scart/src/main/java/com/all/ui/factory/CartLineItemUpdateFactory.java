package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.service.V.impl.OrderCacheService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.Utils;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

public enum CartLineItemUpdateFactory {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(CartLineItemUpdateFactory.class);

	private ObjectFactory oFactory = new ObjectFactory();

	public OrderType removeLineItem(String agentId, String order_id, List<String> lineItemIds, 
			HttpServletRequest request)
	{
		long startTimer=0;
		StopWatch timer=new StopWatch();
		timer.start();
		try{
			HttpSession session = request.getSession();
	
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
	
			List<Integer> reasons = new ArrayList<Integer>(); 
			reasons.add(ControllerConstants.REASON_CODE);
	
			/*	
			 * Get Order Service Call			 
			 */
			startTimer=timer.getTime();
			OrderType orderType = CartUtil.INSTANCE.getOrder(order_id, agentId, salesContext);
			logger.info("TimetakenforOrderSeviceCall="+(timer.getTime()-startTimer));
			
			CartPromotionFactory.INSTANCE.getLineItemWithPromtions(orderType, lineItemIds);
	
			logger.info("Updating_the_LineItemStatus_to_Cancelled_removed");
			startTimer=timer.getTime();
			orderType =  LineItemService.INSTANCE.updateLineItemStatus(agentId, order_id, lineItemIds,
					LineItemStatusCodesType.CANCELLED_REMOVED.value(), LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, salesContext);
			logger.info("TimetakenforUpdateLineItemStatusSeviceCall="+(timer.getTime()-startTimer));
			//Error response Handling
			if(orderType == null || orderType.getExternalId() == 0)
			{
				logger.warn("Handling_error_response_of_updateLineItemStatus(Cancelled_removed)");
				OrderCacheService.INSTANCE.clear(order_id);
				startTimer=timer.getTime();
				orderType = OrderService.INSTANCE.getOrderByOrderNumber(order_id, agentId, new HashMap<String,Object>(), "*", Boolean.FALSE, salesContext);
				logger.info("TimetakenforOrderSeviceCall="+(timer.getTime()-startTimer));
				OrderCacheService.INSTANCE.store(orderType, order_id);
			}
			/*	
			 * Builds the response for UpdateLineItem 			 
			 */
			LineItemCollectionType liCollection = CartProductFactory.INSTANCE.getProductDetails(orderType, request);
			/*	
			 * Adds the LineItemAttribute for which the LineItemStatus has been changed.			 
			 */
			for(LineItemType linItem : liCollection.getLineItem())
			{
				if(lineItemIds.contains(String.valueOf(linItem.getExternalId())))
				{
					LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
					lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS, Constants.REMOVED,
							Constants.DESC_REMOVED, Constants.CKO));
					linItem.setLineItemAttributes(lineItemAttributeType);
				}
			}
			logger.info("Updating_the_LineItemAttributes_and_Price");
			startTimer=timer.getTime();
			orderType = LineItemService.INSTANCE.updateLineItem(agentId, order_id, VOperation.updateLineItem.toString(), liCollection,salesContext);
			/*for(LineItemType lineItemType:orderType.getLineItems().getLineItem())
			{
				if(lineItemIds.contains(String.valueOf(lineItemType.getExternalId())))
				{
					CartLineItemUtil.getTotalPoints(orderType, session);
				}
			}*/
			Utils.getTotalPoints(orderType, session);
			logger.info("TimetakenforUpdateLineItemSeviceCall="+(timer.getTime()-startTimer));
			
			return orderType;
		}finally
		{
			timer.stop();
		}
	}

	public OrderType reAddLineItem(String agentId, String order_id, List<String> lineItemIds,
			HttpServletRequest request)
	{
		long startTimer = 0;
		StopWatch timer=new StopWatch();
		timer.start();
		HttpSession session = request.getSession();
		try{
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
	
			List<Integer> reasons = new ArrayList<Integer>(); 
			reasons.add(ControllerConstants.REASON_CODE);
	
			/*	
			 * Get Order Service Call			 
			 */
			startTimer=timer.getTime();
			OrderType orderType = CartUtil.INSTANCE.getOrder(order_id, agentId, salesContext);
			logger.info("TimetakenforGetOrderSeviceCall="+(timer.getTime()-startTimer));
			
			logger.info("Updating_the_LineItemStatus_to_sales_new_order");
			startTimer=timer.getTime();
			orderType =  LineItemService.INSTANCE.	updateLineItemStatus(agentId, order_id, lineItemIds,
					LineItemStatusCodesType.SALES_NEW_ORDER.value(), LineItemStatusCodesType.SALES_NEW_ORDER.value(), reasons, salesContext);
			logger.info("TimetakenforUpdateLineItemSeviceCall="+(timer.getTime()-startTimer));
			//Error response Handling
			if(orderType == null || orderType.getExternalId() == 0)
			{
				logger.warn("Handling_error_response_of_updateLineItemStatus(sales_new_order");
				OrderCacheService.INSTANCE.clear(order_id);
				startTimer=timer.getTime();
				orderType = OrderService.INSTANCE.getOrderByOrderNumber(order_id, agentId, new HashMap<String,Object>(), "*", Boolean.FALSE, salesContext);
				logger.info("TimetakenforGetOrderSeviceCall="+(timer.getTime()-startTimer));
				OrderCacheService.INSTANCE.store(orderType, order_id);
			}
	
			/*	
			 * ReAdds the promotion if this Product has a promotion in cancelled_removed states 				 
			 */
			logger.info("ReAdding_the_Promotion");
			startTimer=timer.getTime();
			orderType = CartPromotionFactory.INSTANCE.reAddPromtions(orderType, lineItemIds, session);
			logger.info("TimeTakenforAddLineItem="+(timer.getTime()-startTimer));
			/*	
			 * Builds the response for UpdateLineItem 			 
			 */
			LineItemCollectionType liCollection = CartProductFactory.INSTANCE.getProductDetails(orderType, request);
	
			/*	
			 * Adds the LineItemAttribute for which the LineItemStatus has been changed.			 
			 */
			for(LineItemType linItem : liCollection.getLineItem())
			{
				if(lineItemIds.contains(String.valueOf(linItem.getExternalId())))
				{
					LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
					lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS, Constants.CKO_READY,
							Constants.DESC_CKO_READY, Constants.CKO));	
					linItem.setLineItemAttributes(lineItemAttributeType);
				}
			}
			
			logger.info("Updating_the_LineItemAttributes_and_Price");
			startTimer=timer.getTime();
			orderType = LineItemService.INSTANCE.updateLineItem(agentId, order_id, VOperation.updateLineItem.toString(), liCollection,salesContext);
			/*for(LineItemType lineItemType:orderType.getLineItems().getLineItem())
			{
				if(lineItemIds.contains(String.valueOf(lineItemType.getExternalId())))
				{
					CartLineItemUtil.getTotalPoints(orderType, session);
				}
			}*/
			;
			session.setAttribute("totalPoints", Utils.getTotalPoints(orderType, session));
			logger.info("TimetakenforUpdateLineItemSeviceCall="+(timer.getTime()-startTimer));
			
			return orderType;
		}finally
		{
			timer.stop();
		}
	}

}
