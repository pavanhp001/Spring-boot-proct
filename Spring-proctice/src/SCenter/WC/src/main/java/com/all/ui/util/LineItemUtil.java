package com.A.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.A.productResults.util.Utils;
import com.A.ui.constants.Constants;
import com.A.ui.factory.CartLineItemFactory;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.LineItemAttributeType;
import com.A.xml.v4.LineItemDetailType;
import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProviderSourceBaseType;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

import com.A.ui.service.config.ConfigRepo;
import com.A.xml.v4.LineItemStatusType;

/**
 * @author Preetam
 *
 */
public class LineItemUtil
{

	/*
	 * Logger Initialization
	 */
	private static final Logger logger = Logger.getLogger(LineItemUtil.class);
	/**
	 * Checks whether the lineItem is a product with status excluding CANCELLED_REMOVED
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isProduct(LineItemType lineItem)
	{

		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				!lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a promotion with status excluding CANCELLED_REMOVED
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isPromotion(LineItemType lineItem)
	{

		if(isPromotionIncludingRemoved(lineItem) &&
				lineItem.getLineItemStatus().getStatusCode() != null &&
				!lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a product with status as CANCELLED_REMOVED
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isRemovedProduct(LineItemType lineItem)
	{

		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a promotion with status as CANCELLED_REMOVED
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isRemovedPromotion(LineItemType lineItem)
	{

		if(isPromotionIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a product or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isProductIncludingRemoved(LineItemType lineItem)
	{

		if(lineItem.getLineItemDetail().getDetailType().equals(Constants.PRODUCT) && (!isOffer(lineItem)))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a promotion or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isPromotionIncludingRemoved(LineItemType lineItem)
	{

		if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase(Constants.PROMOTION))
		{
			return true;
		}
		return false;
	}

	/**
	 *Checks whether the lineItem is a product with status as SALES_NEW_ORDER
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isProductInSalesNewOrder(LineItemType lineItem)
	{

		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.SALES_NEW_ORDER))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a Offer or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isOffer(LineItemType lineItem)
	{

		//ProductType Attribute will be set for Savers Offer & Utility Offer
		String productType = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.PRODUCT_TYPE,Constants.TYPE);

		if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase(Constants.PRODUCT) &&
				!Utils.isBlank(productType))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a isClosingOffer or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isClosingOfferLineItem(LineItemType lineItem)
	{
		if(lineItem.getLineItemDetail() != null 
				&& lineItem.getLineItemDetail().getDetail() != null
				&& lineItem.getLineItemDetail().getDetail().getProductLineItem()!= null
				&& lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider() != null
				&& ((Constants.RENTERS_INSURANCE.equals(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId()) 
						|| Constants.SUNRUN.equals(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId()))))  
		{
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Checks whether the lineItem is an Savers Offer or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isSaversOffer(LineItemType lineItem)
	{

		//ProductType Attribute will be set for Savers Offer & Utility Offer
		String productType = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.PRODUCT_TYPE,Constants.TYPE);

		if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase(Constants.PRODUCT) &&
				lineItem.getLineItemStatus().getStatusCode() != null &&
				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.SALES_NEW_ORDER)&&
				!Utils.isBlank(productType) && productType.equalsIgnoreCase("SaversOffer"))
		{
			return true;
		}
		return false;
	}


	/**
	 * Checks whether the lineItem is an Utility Offer or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isUtilityOfferIncomplete(LineItemType lineItem)
	{
		boolean isUtilityOfferIncomplete = false;
		String productType = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.PRODUCT_TYPE,Constants.TYPE);

		if(Constants.PRODUCT.equalsIgnoreCase(lineItem.getLineItemDetail().getDetailType()) &&
				!Utils.isBlank(productType) && "UtilityOffer".equalsIgnoreCase(productType)){
			if(lineItem.getLineItemAttributes()!=null){
				List<AttributeEntityType> entityTypes = lineItem.getLineItemAttributes().getEntity();
				if(entityTypes!=null){
					for(AttributeEntityType attributeEntityType : entityTypes){
						if(Constants.CKO.equalsIgnoreCase(attributeEntityType.getSource())){
							List<AttributeDetailType> detailTypes = attributeEntityType.getAttribute();
							if(detailTypes!=null){
								for(AttributeDetailType detailType : detailTypes){
									if(Constants.STATUS.equalsIgnoreCase(detailType.getName()) && Constants.CKO_INCOMPLETE.equalsIgnoreCase(detailType.getValue()) ){
										isUtilityOfferIncomplete = true;
										break;
									}
								}
							}
						}
						if(isUtilityOfferIncomplete){
							break;
						}
					}
				}
			}
		}
		return isUtilityOfferIncomplete;
	}


	/**
	 * Checks whether the lineItem is an Utility Offer or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isContainsSupplierSelection(LineItemType lineItem)
	{
		//ProductType Attribute will be set for Savers Offer & Utility Offer
		String productType = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem,Constants.PRODUCT_TYPE,Constants.TYPE);

		if(lineItem.getLineItemDetail().getDetailType().equalsIgnoreCase(Constants.PRODUCT) &&
				lineItem.getLineItemStatus().getStatusCode() != null &&
				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.SALES_NEW_ORDER)&&
				!Utils.isBlank(productType) && productType.equalsIgnoreCase("SimpleChoice"))
		{
			return true;
		}
		return false;
	}
	/**
	 * Checks whether the product is real-time product or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isRealtimeProduct(LineItemType lineItem)
	{
		if(lineItem.getProductDatasource() != null &&
				lineItem.getProductDatasource().getValue() != null &&
				lineItem.getProductDatasource().getValue().equals(ProviderSourceBaseType.REALTIME))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the product is static product or not
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isStaticProduct(LineItemType lineItem)
	{
		if(lineItem.getProductDatasource() != null &&
				lineItem.getProductDatasource().getValue() != null &&
				lineItem.getProductDatasource().getValue().equals(ProviderSourceBaseType.INTERNAL))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a product with status as provision_ready
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isSubmittedProduct(LineItemType lineItem)
	{

		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null)
		{
			if(lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.PROVISION_READY)) {
				return true;
			}
			String providerId = ConfigRepo.getString("*.closecallsale_hist_status_providers");
			if(lineItem.getPartnerExternalId() != null && providerId != null && providerId.contains(lineItem.getPartnerExternalId().toLowerCase())) {
				List<LineItemStatusType> history = lineItem.getLineItemStatusHistory().getPreviousStatus();			
				if (history != null) {
					for(LineItemStatusType obj:history){
						if (obj.getStatusCode().equals(LineItemStatusCodesType.PROVISION_READY)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}


	/**
	 * Checks whether the lineItem is a product with status as provision_ready
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isPlaceOrderedProduct(LineItemType lineItem)
	{

		if(isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null)
		{
			if(lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.PROVISION_READY)) 
			{
				return true;
			}

			List<LineItemStatusType> history = lineItem.getLineItemStatusHistory().getPreviousStatus();			
			if (history != null && !history.isEmpty()) 
			{
				for(LineItemStatusType obj:history){
					if (obj.getStatusCode().equals(LineItemStatusCodesType.PROVISION_READY))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the lineItem is a product with status as SALES_NEW_ORDER
	 * 
	 * @param lineItem
	 * @return boolean
	 */
	public static boolean isProductReadyToPlaceOrder(LineItemType lineItem)
	{

		if( ( isProductIncludingRemoved(lineItem) && 
				lineItem.getLineItemStatus().getStatusCode() != null &&
				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.SALES_NEW_ORDER) ) ||
				(isSaversOffer(lineItem)))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the Order has savers Offers or Not
	 * 
	 * @param order
	 * @return
	 */
	public static List<String> containsSaversOffers(OrderType order)
	{
		List<String> saversLineItemIds = new ArrayList<String>();
		for(LineItemType lineItem : order.getLineItems().getLineItem()){
			if (isSaversOffer(lineItem)) 
			{
				saversLineItemIds.add(String.valueOf(lineItem.getExternalId()));
			}
		}

		return saversLineItemIds;
	}

	/**
	 * Checks whether the Order has savers Offers or Not
	 * 
	 * @param order
	 * @return
	 */
	public static List<String> containsUtilityOffers(OrderType order)
	{
		List<String> lineItemlist = new ArrayList<String>();
		for(LineItemType lineItem : order.getLineItems().getLineItem()){
			if (isUtilityOfferIncomplete(lineItem)) 
			{
				lineItemlist.add(String.valueOf(lineItem.getExternalId()));
			}
		}
		return lineItemlist;
	}

	public static List<String> containsSupplierSelection(OrderType order) {
		List<String> lineItemlist = new ArrayList<String>();
		for(LineItemType lineItem : order.getLineItems().getLineItem()){
			if (isContainsSupplierSelection(lineItem)) 
			{
				lineItemlist.add(String.valueOf(lineItem.getExternalId()));
			}
		}
		return lineItemlist;
	}



	public static String prepareCKOCompletedLinItemsListFromOrder(OrderType orderType)
	{
		JSONObject lineItems = new JSONObject();
		try
		{
			lineItems.put("orderId", orderType.getExternalId());
			lineItems.put("hasLineItemsTOSubmit", "No");
			lineItems.put("hasSubmittedRTProduct", "No");
			if(orderType.getLineItems() != null 
					&& orderType.getLineItems().getLineItem()!=null
					&& !orderType.getLineItems().getLineItem().isEmpty())
			{
				JSONArray lineItemList = new JSONArray();
				for (LineItemType lineItemType : orderType.getLineItems().getLineItem()) 
				{
					if( isSubmittedProduct(lineItemType) && 
							(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("27010360")
									|| lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("15500201")
									|| lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("24699452")
									|| lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("32416075")
									|| lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("32946482")
									|| lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("32952482")
									|| lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("15499341")
									|| lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("15499381")))
					{
						lineItems.put("hasSubmittedRTProduct", "Yes");
					}
					else
					{
						if( isProductIncludingRemoved(lineItemType) && lineItemType.getLineItemStatus().getStatusCode() != null 
								&& lineItemType.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.SALES_NEW_ORDER) )
						{
							entityTypeFor : for(AttributeEntityType entity : lineItemType.getLineItemAttributes().getEntity())
							{
								if(entity != null && entity.getSource()!= null)
								{
									if(entity.getSource().equalsIgnoreCase(Constants.CKO))
									{
										attributeTypeFor : for(AttributeDetailType attribute : entity.getAttribute())
										{
											if(attribute.getName().equals(Constants.STATUS))
											{
												String status = attribute.getValue();
												if(status.equals(Constants.CKO_COMPLETE))
												{
													lineItems.put("hasLineItemsTOSubmit", "Yes");
													lineItemList.put(lineItemType.getExternalId());
												}
												break attributeTypeFor;
											}
										}
									break entityTypeFor;
									}
								}
							}
						}
					}
				}
				lineItems.put("lineItemList", lineItemList);
			}
		}
		catch (Exception e) 
		{
			logger.warn("Error_while_preparing_completed_lineItem_list",e);
		}
		logger.info("lineItemJson_For_Submit="+lineItems);
		return lineItems.toString();
	}


	/**
	 * Checks whether OrderType has UtilityOffer or not. Returns utilityOffLineItem if order has UtilityOffer
	 * 
	 * @param order
	 * @return
	 */
	public static LineItemType getUtilityOfferLineItem(OrderType order,String productExternalId){

		if (order.getLineItems().getLineItem() != null) 
		{
			for ( LineItemType lineItem : order.getLineItems().getLineItem() )
			{
				if ( lineItem.getLineItemDetail().getDetailType().equals("product"))
				{
					String productType = getLineItemAttr(lineItem,"PRODUCT_TYPE", "TYPE");
					if (productType != null && productExternalId != null) 
					{
						if (productType.equalsIgnoreCase("UtilityOffer"))
						{
							LineItemDetailType lineItemDetialType  = lineItem.getLineItemDetail();
							if(lineItemDetialType != null 
									&& lineItemDetialType.getDetail()!= null
									&& lineItemDetialType.getDetail().getProductLineItem()!= null
									&& productExternalId.equals(lineItemDetialType.getDetail().getProductLineItem().getExternalId())){

								return lineItem;
							}
						}
					}
				}
			}
		}

		return null;
	}


	/**
	 * Gets the LineItemAttribute Value Based on Source and Name 
	 * 
	 * @param lineItem
	 * @param entitySrc
	 * @param attrName
	 * @return
	 */
	public static String  getLineItemAttr(final LineItemType lineItem,
			final String entitySrc,
			final String attrName)

	{
		LineItemAttributeType liAttrib = lineItem.getLineItemAttributes();

		if ( liAttrib == null )
		{
			return "";
		}

		List<AttributeEntityType> entities = liAttrib.getEntity();
		if(entities != null)
		{
			for(AttributeEntityType entity : entities)
			{
				if (entity.getSource() != null
						&& entity.getSource().equalsIgnoreCase(entitySrc))
				{
					for(AttributeDetailType attr : entity.getAttribute())
					{
						if (attr.getName() != null && attr.getName().equalsIgnoreCase(attrName))
						{
							return attr.getValue();
						}
					}
				}
			}
		}
		return "";
	}




	/**
	 * This method will be return LineItemType based on selectedProductType of lineItem from given OrderType.Otherwise return null
	 * @param order
	 * @param productType
	 * @return LineItemType
	 */
	public static LineItemType getLineItemBasedOnProductType(OrderType order , String productType){

		if (order != null && order.getLineItems() != null && order.getLineItems().getLineItem() != null) 
		{
			for ( LineItemType lineItem : order.getLineItems().getLineItem() )
			{
				if ( lineItem.getLineItemDetail().getDetailType().equals("product"))
				{
					String lineItemnProdType = getLineItemAttr(lineItem,"PRODUCT_TYPE", "TYPE");
					if (!Utils.isBlank(productType)) 
					{
						if (productType.equalsIgnoreCase(lineItemnProdType))
						{
							return lineItem;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Check whether contextEntity exit or not if exits return true other wise
	 * false
	 * 
	 * @param salesContextType
	 * @param contextEntity
	 * @return boolean
	 */
	public static boolean isSalesContextEntityExit(SalesContextType salesContextType, String entityName)
	{
		boolean isEntityExit = false;
		if (salesContextType != null && salesContextType.getEntity() != null
				&& salesContextType.getEntity().size() > 0) 
		{
			for (SalesContextEntityType contextEntityType : salesContextType.getEntity())
			{
				if (!Utils.isBlank(entityName) && entityName.equalsIgnoreCase(contextEntityType.getName()))
				{
					isEntityExit = true;
				}
			}
		}
		logger.info("isEntityExit="+isEntityExit);
		return isEntityExit;
	}

	/** Use this method get all lineitems based on providerID
	 * @param order
	 * @param providerID
	 * @return
	 */
	public static List<LineItemType> getLineItemsBasedOnProvider(OrderType order, String providerID){
		List<LineItemType> lineItemList = new ArrayList<LineItemType>();
		if (order.getLineItems()!= null 
				&& order.getLineItems().getLineItem() != null
				&& !order.getLineItems().getLineItem().isEmpty()){
			for (LineItemType lineItem : order.getLineItems().getLineItem()){
				if(isProductIncludingRemoved(lineItem)){
					if (providerID.equals(getLineItemAttr(lineItem,"IMAGE_ID", "id"))){
						lineItemList.add(lineItem);
					}
				}
			}
		}
		return lineItemList;
	}

	/** This method use to check sling TV purchased or not
	 * @param order
	 * @return
	 */
	public static boolean isSlingTVPurchased(OrderType order){
		if (order.getLineItems()!= null 
				&& order.getLineItems().getLineItem() != null
				&& !order.getLineItems().getLineItem().isEmpty()){
			for (LineItemType lineItem : order.getLineItems().getLineItem()){
				if(lineItem.getLineItemStatus()!= null 
						&& lineItem.getLineItemStatus().getStatusCode() != null      
						&& LineItemStatusCodesType.PROVISION_READY.equals(lineItem.getLineItemStatus().getStatusCode()) 
						&& lineItem.getLineItemDetail() != null 
						&& lineItem.getLineItemDetail().getDetail() != null
						&& lineItem.getLineItemDetail().getDetail().getProductLineItem()!= null
						&& lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider() != null
						&& Constants.SLINGVIDEO_PROVIDER_ID.equals(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId()))
				{
					return true;
				}
			}
		}
		return false;
	}	

	/** This method use to get lineitem from order based on lineitem externalID
	 * @param order
	 * @param lineItemExID
	 * @return LineItemType
	 */
	public static LineItemType getLineItemBasedOnLineItemExID(OrderType order , long linexternalId)
	{
		if (order != null 
				&& order.getLineItems() != null 
				&& order.getLineItems().getLineItem() != null){
			for ( LineItemType lineItem : order.getLineItems().getLineItem() ){
				if(lineItem.getExternalId() == linexternalId){
					return lineItem;
				}
			}
		}
		return null;
	}

	public static LineItemType getLineItemBasedOnLineitemNumber(OrderType order , Integer lineItemNumber){
		if (order != null 
				&& order.getLineItems() != null 
				&& order.getLineItems().getLineItem() != null){
			for ( LineItemType lineItem : order.getLineItems().getLineItem() ){
				if(lineItem.getLineItemNumber() == lineItemNumber.intValue()){
					return lineItem;
				}
			}
		}
		return null;
	}
	/**
	 * This method will be return LineItemType based on selectedProductType of lineItem from given OrderType.Otherwise return null
	 * @param order
	 * @param productType
	 * @return LineItemType
	 */
	public static String getLineItemDesireDate(OrderType order){
		StringBuilder date = new StringBuilder();
		if (order != null && order.getLineItems() != null && order.getLineItems().getLineItem() != null) 
		{
			for ( LineItemType lineItem : order.getLineItems().getLineItem() )
			{
				if ( lineItem.getLineItemDetail().getDetailType().equals("product") 
						&& lineItem.getSchedulingInfo()  != null
						&& lineItem.getSchedulingInfo().getDesiredStartDate() != null
						&& lineItem.getSchedulingInfo().getDesiredStartDate().getDate() != null)
				{
					date.append(lineItem.getSchedulingInfo().getDesiredStartDate().getDate().getMonth());
					date.append("/");
					date.append(lineItem.getSchedulingInfo().getDesiredStartDate().getDate().getDay());
					date.append("/");
					date.append(lineItem.getSchedulingInfo().getDesiredStartDate().getDate().getYear());
					if(lineItem.getSchedulingInfo().getDesiredStartDate().getStartTime() != null){
						date.append("   ");
						date.append(lineItem.getSchedulingInfo().getDesiredStartDate().getStartTime().getHour());
						date.append(":");
						date.append(lineItem.getSchedulingInfo().getDesiredStartDate().getStartTime().getMinute());
					}
					if(lineItem.getSchedulingInfo().getDesiredStartDate().getEndTime() != null){
						date.append("   ");
						date.append(lineItem.getSchedulingInfo().getDesiredStartDate().getEndTime().getHour());
						date.append(":");
						date.append(lineItem.getSchedulingInfo().getDesiredStartDate().getEndTime().getMinute());
					}
				}
			}
		}
		return date.toString();
	}
	
	public static Boolean isVZLineItemNotInProvisionReady(OrderType order){
		boolean isSmartCartApplicable = false;
		boolean isVerizonSubmitted = false;
		boolean isVerizonLineItem = false;
		if (order != null && order.getLineItems() != null && order.getLineItems().getLineItem() != null) 
		{
			for ( LineItemType lineItem : order.getLineItems().getLineItem() )
			{
				if (!Utils.isBlank(lineItem.getPartnerExternalId())
						&& lineItem.getLineItemDetail() != null
						&& lineItem.getLineItemDetail().getDetailType() != null
						&& lineItem.getLineItemDetail().getDetailType().toString().equals("product")
						&& (lineItem.getPartnerExternalId().equals(Constants.VERIZON2))) {
					isVerizonLineItem = true;
					if (lineItem.getLineItemStatus().getStatusCode().value().equalsIgnoreCase("provision_ready")) {
						isVerizonSubmitted = true;
					}
				}
			}
			if(!isVerizonSubmitted && isVerizonLineItem){
				isSmartCartApplicable = true;
			}
		}
		return isSmartCartApplicable;
	}


}
