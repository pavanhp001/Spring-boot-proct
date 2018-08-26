package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.AL.productResults.util.Utils;
import com.AL.ui.builder.CartLineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.vo.LineItemVo;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author Preetam
 *
 */
public enum CartPromotionFactory {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(CartPromotionFactory.class);

	/**
	 * Extracts all the Promotions from Order and adds them to the respective LineItem based on the AppliesTo
	 * 
	 * 
	 * @param order
	 * @param mav
	 * @return
	 */
	public  LineItemVo lineItemPromotions(OrderType order)
	{
		LineItemVo vo = new LineItemVo();

		Map<String,List<ApplicableType>> promotionMap = new HashMap<String, List<ApplicableType>>();

		for(LineItemType lineItem:order.getLineItems().getLineItem())
		{
			if(CartLineItemUtil.isPromotion(lineItem))
			{
				for(Integer applies : lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo())
				{
					LineItemType lineItemType = LineItemUtil.getLineItemBasedOnLineitemNumber(order,applies);
					String providerID = null;
					if(lineItemType != null){
						providerID = LineItemUtil.getLineItemAttr(lineItemType,"IMAGE_ID","id");
					}
					String appliesTo = String.valueOf(applies);
					if(promotionMap.get(appliesTo) != null &&
							promotionMap.get(appliesTo).size() !=0)
					{
						ApplicableType applicableType = getApplicableType(lineItem);
						combinePromoDesc(applicableType,vo,providerID);
						promotionMap.get(appliesTo).add(getApplicableType(lineItem));	
					}
					else
					{
						List<ApplicableType> promotionList = new ArrayList<ApplicableType>();
						ApplicableType applicableType = getApplicableType(lineItem);
						promotionList.add(applicableType);
						promotionMap.put(appliesTo, promotionList);
						combinePromoDesc(applicableType,vo,providerID);
					}
				}
			}
		}
		vo.setPromotionMap(promotionMap);
		return vo;
	}

	/**
	 * Sets the values for lineItemPromotion from LineItemAttributes if OME does not send them
	 * 
	 *  
	 * @param lineItemType
	 * @return
	 */
	public ApplicableType getApplicableType(LineItemType lineItemType)
	{

		ApplicableType applicableType = lineItemType.getLineItemDetail().getDetail().getPromotionLineItem();
		for(AttributeEntityType entity : lineItemType.getLineItemAttributes().getEntity())
		{
			if(entity!=null && entity.getSource()!=null
					&& entity.getSource().equalsIgnoreCase(Constants.PROMOTION_DETAILS))
			{
				for(AttributeDetailType attribute : entity.getAttribute())
				{

					if(attribute.getName().equalsIgnoreCase(Constants.CONDITIONS)
							&& !Utils.isBlank(applicableType.getPromotion().getConditions()))
					{
						applicableType.getPromotion().setConditions(attribute.getValue());
					}
					else if(attribute.getName().equalsIgnoreCase(Constants.DESCRIPTION)
							&& !Utils.isBlank(applicableType.getPromotion().getDescription()))
					{
						applicableType.getPromotion().setDescription(attribute.getValue());
					}
					else if(attribute.getName().equalsIgnoreCase(Constants.PRICE))
					{
						applicableType.getPromotion().setPriceValue(Float.parseFloat(attribute.getValue()));
					}
					else if(attribute.getName().equalsIgnoreCase(Constants.PRICETYPE)
							&& !Utils.isBlank(applicableType.getPromotion().getPriceValueType()))
					{
						applicableType.getPromotion().setPriceValueType(attribute.getValue());
					}
					else if(attribute.getName().equalsIgnoreCase(Constants.PROMOCODE)
							&& !Utils.isBlank(applicableType.getPromotion().getPromoCode()))
					{
						applicableType.getPromotion().setPromoCode(attribute.getValue());
					}
					else if(attribute.getName().equalsIgnoreCase(Constants.QUALIFICATION)
							&& !Utils.isBlank(applicableType.getPromotion().getQualification()))
					{
						applicableType.getPromotion().setQualification(attribute.getValue());
					}
					else if(attribute.getName().equalsIgnoreCase(Constants.SHORTDESCRIPTION)
							&& !Utils.isBlank(applicableType.getPromotion().getShortDescription()))
					{
						applicableType.getPromotion().setShortDescription(attribute.getValue());
					}

				}
			}
		}	

		return applicableType;
	}

	/**
	 * Gets all the LineItemids and Promotion extrnalId
	 * 
	 * @param order
	 * @param lineItemIds
	 * @return List<String>(LineItemId)
	 */
	public List<String> getLineItemWithPromtions(OrderType order,List<String> lineItemIds)
	{
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		if(lineItems != null)
		{
			List<Integer> lineItemNumbers = getLineItemNumbers(lineItems,lineItemIds);
			for(LineItemType lineItem : lineItems)
			{
				if(CartLineItemUtil.isPromotion(lineItem))
				{
					for(Integer applies : lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo())
					{
						if(lineItemNumbers.contains(applies) && 
								!lineItemIds.contains(String.valueOf(lineItem.getExternalId())))
						{
							lineItemIds.add(String.valueOf(lineItem.getExternalId()));
						}
					}
				}
			}
		}

		return lineItemIds;
	}

	/**
	 * ReAdds the promotion when lineItemStatus is changed from cancelled_Removed to Sales_new_order
	 * 
	 * @param order
	 * @param lineItemIds
	 * @return List<String>(LineItemId)
	 */
	public OrderType reAddPromtions(OrderType order,List<String> lineItemIds,HttpSession session)
	{
		logger.info("reAddPromtions_begin_in_CartPromotionFactory");
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		/*	
		 * LineItemCollectionType				 
		 */
		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();

		List<String> addedPromtion = new ArrayList<String>();

		if(lineItems != null)
		{
			List<Integer> lineItemNumbers = getLineItemNumbers(lineItems,lineItemIds);
			for(LineItemType lineItem : getUniquePromotions(order))
			{
				String status = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem , Constants.CKO, Constants.STATUS);
				if(!Utils.isBlank(status))
				{
					for(Integer applies : lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo())
					{
						if(lineItemNumbers.contains(applies) && !lineItemIds.contains(String.valueOf(lineItem.getExternalId()))
								&& !addedPromtion.contains(lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getPromotion().getExternalId()))
						{
							addedPromtion.add(lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getPromotion().getExternalId());
							LineItemType promtionLineItem = CartLineItemBuilder.INSTANCE.createPromotion(lineItem);
							liCollection.getLineItem().add(promtionLineItem);

						}
					}
				}
			}
			/*	
			 * ServiceCall for getting SalesContext of the Order				 
			 */
			SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

			/*	
			 * ServiceCall for adding LineItem to Order					 
			 */
			if(liCollection.getLineItem().size() > 0)
			{
				logger.info("reAddPromtions_end_in_CartPromotionFactory");
				StopWatch timer = new StopWatch();
				timer.start();
				long startTimer = timer.getTime();
				order = LineItemService.INSTANCE.addLineItem(agentId, String.valueOf(order.getExternalId()),
						liCollection,updateSalesContext,false);
				logger.info("TimeTakenforAddLineItem="+(timer.getTime()-startTimer));
				timer.stop();
				return order;
			}
		}
		logger.info("reAddPromtions_end_in_CartPromotionFactory");
		return order;
	}

	/**
	 * Gets all the LineItemNumbers for the given LineitemIds
	 * 
	 * @param lineItems
	 * @param lineItemIds
	 * @return List<Integer>(LineItemNumber)
	 */
	public List<Integer> getLineItemNumbers(List<LineItemType> lineItems,
			List<String> lineItemIds) 
			{
		List<Integer> lineItemNumbers = new ArrayList<Integer>();
		for(LineItemType lineItem : lineItems)
		{
			if(lineItemIds.contains(String.valueOf(lineItem.getExternalId())))
			{
				lineItemNumbers.add(Integer.valueOf(lineItem.getLineItemNumber()));
			}
		}

		return lineItemNumbers;
			}

	/**
	 * Validating whether the productPromotion already Exists in the Order or Not
	 * 
	 * 
	 * @param order
	 * @param selectedPromotion
	 * @return
	 */
	public boolean ValidatePromotions(OrderType order,String selectedPromotion)
	{

		List<String> orderPromtions = new ArrayList<String>();
		for(LineItemType lineItem : order.getLineItems().getLineItem())
		{
			if(CartLineItemUtil.isPromotion(lineItem))
			{
				//Adding all the promotions to List
				orderPromtions.add(lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getPromotion().getExternalId()
						+"_"+lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0));
			}
		}

		//Checking whether the selected promotion is in the Order promotions
		if(orderPromtions.contains(selectedPromotion))
		{
			logger.debug("Promotion_Exists");
			return false;
		}
		else
		{
			logger.debug("New_Promotion");
			return true;
		}

	}

	/**
	 * Returns the Unique promotions which are in Canceled removed status.
	 * 
	 * @param order
	 * @return List<LineItemType>
	 */
	public List<LineItemType> getUniquePromotions(OrderType order)
	{

		List<LineItemType> uniquePromotions = new ArrayList<LineItemType>();

		Map<String,LineItemType> promotionExternalIdMap = new HashMap<String, LineItemType>();

		for(LineItemType lineItemType: order.getLineItems().getLineItem())
		{
			//Only Promotions which are in canceled removed status
			if(CartLineItemUtil.isRemovedPromotion(lineItemType))
			{

				String key = lineItemType.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0)+"_"+lineItemType.getLineItemDetail().getDetail().getPromotionLineItem().getPromotion().getExternalId();

				if(promotionExternalIdMap.containsKey(key))
				{
					int mapLineItemNumber = promotionExternalIdMap.get(key).getLineItemNumber();

					if(mapLineItemNumber < lineItemType.getLineItemNumber())
					{
						promotionExternalIdMap.put(key, lineItemType);
					}

				}
				else
				{
					//Unique Promotions
					promotionExternalIdMap.put(key, lineItemType);
				}

			}

		}

		for (Entry<String, LineItemType> entry : promotionExternalIdMap.entrySet()) 
		{
			uniquePromotions.add(entry.getValue());
		}

		return uniquePromotions;
	}

	private void combinePromoDesc(ApplicableType applicableType, LineItemVo vo,String providerID){
		String promoDesc = null;
		if( (!(Constants.ATT1.equals(providerID) 
				|| Constants.ATTV6.equals(providerID)))
				&& applicableType != null 
				&& applicableType.getPromotion() != null
				&& !Utils.isBlank(applicableType.getPromotion().getDescription())){
			promoDesc = applicableType.getPromotion().getDescription();
		}else if(applicableType != null 
				&& applicableType.getPromotion() != null
				&& !Utils.isBlank(applicableType.getPromotion().getShortDescription())){
			promoDesc = applicableType.getPromotion().getShortDescription();
		}
		if(!Utils.isBlank(promoDesc) && !Utils.isBlank(vo.getCombinePromoDesc())){
			vo.setCombinePromoDesc(vo.getCombinePromoDesc()+ " and  " +promoDesc);
		}else if(!Utils.isBlank(promoDesc)) {
			vo.setCombinePromoDesc(promoDesc);
		}
	}

}
