package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import com.AL.ui.constants.Constants;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.VOperation;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProviderSourceBaseType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;


public enum CKOLineItemFactory {
	
	INSTANCE;
	
	/**
     * Gets the LineItemAttribute Value Based on Source and Name 
     * 
     * @param lineItem
     * @param entitySrc
     * @param attrName
     * @return
     */
    public String getLineItemAttr(final LineItemType lineItem,
                final String entitySrc,
                final String attrName)

    {
          LineItemAttributeType liAttrib = lineItem.getLineItemAttributes();
          
          if ( liAttrib == null )
          {
                return "";
          }

          List<AttributeEntityType> entities = liAttrib.getEntity();

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

          return "";
    }

    
	public String extractBaseMontlyPrice(LineItemType lineItem) 
	{
		Double selectedFeturesPrice = getSelectedFeaturesPrice(lineItem);
		Double baseRecurringPrice = lineItem.getLineItemPriceInfo().getBaseRecurringPrice();
		baseRecurringPrice = baseRecurringPrice - selectedFeturesPrice;
		return baseRecurringPrice.toString();
	}
	
	
	/**
	 * Adds all the lineItem Selected Features Prices.
	 * 
	 * @param lineItem
	 * @return Double
	 */
	private Double getSelectedFeaturesPrice(LineItemType lineItem) 
	{
		SelectedFeaturesType selectedFeatures = lineItem.getSelectedFeatures();
		Double totalFeaturesPrice = 0.0;
		if(selectedFeatures != null)
		{
			List<FeatureValueType> features = selectedFeatures.getFeatures().getFeatureValue();
			if(features != null )
			{
				for(FeatureValueType feature:features)
				{
					Double featurePrice = feature.getPrice().getBaseRecurringPrice();
					totalFeaturesPrice += featurePrice;
				}
			}

			List<FeatureGroup> featureGroups = selectedFeatures.getFeatureGroup();
			if(featureGroups != null)
			{
				for(FeatureGroup featureGroup:featureGroups)
				{
					features = featureGroup.getFeatureValue();
					if(features != null )
					{
						for(FeatureValueType feature:features)
						{
							Double featurePrice = feature.getPrice().getBaseRecurringPrice();
							totalFeaturesPrice += featurePrice;
						}
					}
				}
			}
		}
		return totalFeaturesPrice;
	}
	
	/**
	 * Adds all the lineItem Selected Features Prices.
	 * 
	 * @param lineItem
	 * @return Double
	 */
	public Double getSelectedNonRecurringFeaturesPrice(LineItemType lineItem) 
	{

		SelectedFeaturesType selectedFeatures = lineItem.getSelectedFeatures();

		Double totalFeaturesPrice = 0.0;

		if(selectedFeatures != null)
		{
			List<FeatureValueType> features = selectedFeatures.getFeatures().getFeatureValue();
			if(features != null )
			{
				for(FeatureValueType feature : features)
				{
					Double featurePrice = feature.getPrice().getBaseNonRecurringPrice();
					totalFeaturesPrice += featurePrice;
				}
			}

			List<FeatureGroup> featureGroups = selectedFeatures.getFeatureGroup();
			if(featureGroups != null)
			{
				for(FeatureGroup featureGroup : featureGroups)
				{
					features = featureGroup.getFeatureValue();
					if(features != null )
					{
						for(FeatureValueType feature:features)
						{
							Double featurePrice = feature.getPrice().getBaseNonRecurringPrice();
							totalFeaturesPrice += featurePrice;
						}
					}
				}
			}
		}

		return totalFeaturesPrice;
	}


	
	public Boolean hasFeatures(LineItemType lineitem) 
	{
		Boolean hasFeatures = Boolean.FALSE;
		if(lineitem.getSelectedFeatures() != null)
		{
			List<FeatureValueType> features = lineitem.getSelectedFeatures().getFeatures().getFeatureValue();
			if(features != null )
			{
				for(FeatureValueType featureValueType : features)
				{
					if(featureValueType.getIncluded() == null)
					{
						hasFeatures = Boolean.TRUE;
						break;
					}
				}
			}
			
			List<FeatureGroup> featureGroups = lineitem.getSelectedFeatures().getFeatureGroup();
			
			if(featureGroups != null)
			{
				for(FeatureGroup featureGroup:featureGroups)
				{
					features = featureGroup.getFeatureValue();
					if(features != null )
					{
						for(FeatureValueType feature:features)
						{
							if(feature.getIncluded() == null)
							{
								hasFeatures = Boolean.TRUE;
								break;
							}
						}
					}
				}
			}	
		}
		return hasFeatures;
	}
	

	public List<ApplicableType> lineItemPromotions( OrderType orderType, LineItemType orderRecapLineItemType ) {
		List<ApplicableType> promotionList = new ArrayList<ApplicableType>();

		for(LineItemType lineItem:orderType.getLineItems().getLineItem())
		{
			if(isPromotion(lineItem))
			{
				for(Integer applies : lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo())
				{
					if( orderRecapLineItemType.getLineItemNumber() == applies.intValue() )
					{
						promotionList.add( getApplicableType(lineItem) );
					}
				}
			}
		}
		return promotionList;
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
					if("conditions".equalsIgnoreCase(attribute.getName())
						&& !StringUtils.isBlank(applicableType.getPromotion().getConditions()))
					{
						applicableType.getPromotion().setConditions(attribute.getValue());
					}
					else if("description".equalsIgnoreCase(attribute.getName())
						&& !StringUtils.isBlank(applicableType.getPromotion().getDescription()))
					{
						applicableType.getPromotion().setDescription(attribute.getValue());
					}
					else if("priceValue".equalsIgnoreCase(attribute.getName()))
					{
						applicableType.getPromotion().setPriceValue(Float.parseFloat(attribute.getValue()));
					}
					else if("priceValueType".equalsIgnoreCase(attribute.getName())
						&& !StringUtils.isBlank(applicableType.getPromotion().getPriceValueType()))
					{
						applicableType.getPromotion().setPriceValueType(attribute.getValue());
					}
					else if("promoCode".equalsIgnoreCase(attribute.getName())
						&& !StringUtils.isBlank(applicableType.getPromotion().getPromoCode()))
					{
						applicableType.getPromotion().setPromoCode(attribute.getValue());
					}
					else if("qualification".equalsIgnoreCase(attribute.getName())
							&& !StringUtils.isBlank(applicableType.getPromotion().getQualification()))
					{
						applicableType.getPromotion().setQualification(attribute.getValue());
					}
					else if("shortDescription".equalsIgnoreCase(attribute.getName())
						&& !StringUtils.isBlank(applicableType.getPromotion().getShortDescription()))
					{
						applicableType.getPromotion().setShortDescription(attribute.getValue());
					}
				}
			}
		}	
		return applicableType;
	}
	
	
	private boolean isPromotion(LineItemType lineItem)
	{
		
		if( "productPromotion".equalsIgnoreCase(lineItem.getLineItemDetail().getDetailType()) 
			&& lineItem.getLineItemStatus().getStatusCode() != null
			&& !lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Temporary check for StaticPrices when showing on
	 * @param order
	 * @param baseNonRecurringPrice 
	 * @param baseRecurringPrice 
	 * @param session
	 * @return
	 */
	public  OrderType validateStaticPrices(OrderType order, LineItemType lineItem,SalesContextType context, Double baseRecurringPrice, Double baseNonRecurringPrice ){
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			//logger.info("validateStaticPrices_begin_in_CartProductFactory");
			LineItemCollectionType liCollection = new LineItemCollectionType();
			String agentId = order.getAgentId();
			String orderId = String.valueOf(order.getExternalId());
			/*	
			 * Getting SalesContext from the session				 
			 */
	
			List<ApplicableType> promotionList = lineItemPromotions(order,lineItem);
	
			LineItemPriceInfoType lineItemPricInfoType  = new LineItemPriceInfoType();
	
			Double selectedBaseRecurringFeaturesPrice = getSelectedFeaturesPrice(lineItem);
			Double selectedBaseNonRecurringFeaturesPrice = getSelectedNonRecurringFeaturesPrice(lineItem);
	
			int lineItemNumber = lineItem.getLineItemNumber();
	
			if( !promotionList.isEmpty() )
			{
				lineItemPricInfoType = calculatePromotionalPrice(order, baseRecurringPrice, baseNonRecurringPrice, lineItemNumber, promotionList);
	
				//if((selectedBaseRecurringFeaturesPrice > 0) || (selectedBaseNonRecurringFeaturesPrice > 0))
				if((selectedBaseRecurringFeaturesPrice > 0) || ((selectedBaseNonRecurringFeaturesPrice > 0) || (lineItemPricInfoType.getBaseNonRecurringPrice() > selectedBaseNonRecurringFeaturesPrice)))
				{
					if((selectedBaseRecurringFeaturesPrice > 0))
					{
						baseRecurringPrice = lineItemPricInfoType.getBaseRecurringPrice();
						baseRecurringPrice += selectedBaseRecurringFeaturesPrice;
						baseRecurringPrice = Math.round(baseRecurringPrice * 100.0) / 100.0;
						lineItemPricInfoType.setBaseRecurringPrice(baseRecurringPrice);
					}
	
					if((selectedBaseNonRecurringFeaturesPrice > 0 || lineItemPricInfoType.getBaseNonRecurringPrice() > selectedBaseNonRecurringFeaturesPrice ))
					{
						baseNonRecurringPrice = lineItemPricInfoType.getBaseNonRecurringPrice();
						baseNonRecurringPrice += selectedBaseNonRecurringFeaturesPrice;
						baseNonRecurringPrice = Math.round(baseNonRecurringPrice * 100.0) / 100.0;
						lineItemPricInfoType.setBaseNonRecurringPrice(baseNonRecurringPrice);
					}
				}
	
				LineItemType lineItemType = new LineItemType();
				lineItemType.setLineItemNumber(lineItemNumber);
				lineItemType.setExternalId(lineItem.getExternalId());
				lineItemType.setLineItemPriceInfo(lineItemPricInfoType);

				liCollection.getLineItem().add(lineItemType);
			}
	
			if(liCollection.getLineItem().size() > 0)
			{
				startTimer = timer.getTime();
				order = LineItemService.INSTANCE.updateLineItem(agentId, orderId, VOperation.updateLineItem.toString(), liCollection, context);
			}
			return order;
		}finally
		{
			timer.stop();
		}
	}
	
	
	/**
	 * Extracts the Promotions and deducts the PromoPrice based on the type
	 * 
	 * @param order
	 * @param baseRecurringPrice
	 * @param baseNonRecurringPrice
	 * @param lineItemNumber
	 * @param promotionList 
	 * @return
	 */
	public LineItemPriceInfoType calculatePromotionalPrice(OrderType order, Double baseRecurringPrice, 
			Double baseNonRecurringPrice, int lineItemNumber, List<ApplicableType> promotionList)
	{
		
		for(ApplicableType promo : promotionList)
		{
			String type = promo.getPromotion().getType();
			String priceValueType = promo.getPromotion().getPriceValueType();
			Float promoPrice = promo.getPromotion().getPriceValue();
			if(type != null)
			{
				if(type.equalsIgnoreCase("baseMonthlyDiscount"))
				{
					/*	
					 *  Its a BaseRecurring Promotion so should be applied to Monthly Price.  
					 */
					if(priceValueType!= null){
						if(priceValueType.equalsIgnoreCase("relative"))
						{
							baseRecurringPrice -= promoPrice;
						}
						else if(priceValueType.equalsIgnoreCase("absolute"))
						{
							baseRecurringPrice = Double.valueOf(promoPrice);
						}
						else
						{
							continue;
						}
					}

				}
				else if(type.equalsIgnoreCase("oneTimeFeeDiscount"))
				{
					/*	
					 *Its a BaseNonRecurring Promotion so should be applied to Installation Price.  
					 */
					if(priceValueType!= null)
					{
						if(priceValueType.equalsIgnoreCase("relative"))
						{
							baseNonRecurringPrice -= promoPrice;
						}
						else if(priceValueType.equalsIgnoreCase("absolute"))
						{
							baseNonRecurringPrice = Double.valueOf(promoPrice);
						}
						else
						{
							continue;
						}
					}
				}
				else
				{
					continue;
				}
			}
		}
		
		baseRecurringPrice = Math.round(baseRecurringPrice * 100.0) / 100.0;
		baseNonRecurringPrice = Math.round(baseNonRecurringPrice * 100.0) / 100.0;
		
		return createLineItemPriceInfoType(baseRecurringPrice, baseNonRecurringPrice);
	}
	
	
	/**
	 * sets the baseRecurringPrice & baseNonRecurringPrice in LineItemPriceInfoType and returns it.
	 * 
	 * 
	 * @param baseRecurringPrice
	 * @param baseNonRecurringPrice
	 * @return LineItemPriceInfoType
	 */
	public LineItemPriceInfoType createLineItemPriceInfoType(Double baseRecurringPrice,Double baseNonRecurringPrice){
		LineItemPriceInfoType lineItemPriceInfoType = new LineItemPriceInfoType();
		
		lineItemPriceInfoType.setBaseRecurringPrice(baseRecurringPrice);
		lineItemPriceInfoType.setBaseRecurringPriceUnits("USD");
		lineItemPriceInfoType.setBaseNonRecurringPrice(baseNonRecurringPrice);
		lineItemPriceInfoType.setBaseNonRecurringPriceUnits("USD");
		return lineItemPriceInfoType;
	}
	
    /**
	 * Checks whether OrderType has UtilityOffer or not. Returns utilityOffLineItem if order has UtilityOffer
	 * 
	 * @param order
	 * @return
	 */
	public  LineItemType getUtilityOfferLineItem(OrderType order,String productExternalId){

		if (order.getLineItems().getLineItem() != null) 
		{
			for ( LineItemType lineItem : order.getLineItems().getLineItem() )
			{
				if ( lineItem.getLineItemDetail().getDetailType().equals("product"))
				{
					String productType = getLineItemAttr(lineItem,"PRODUCT_TYPE", "TYPE");
					if (productType != null) 
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
	 * @param name
	 * @param value
	 * @param description
	 * @param source
	 * @return AttributeEntityType
	 */
	public AttributeEntityType setAttributeEntityType(String name, String value, String description, String source)
	{
		 ObjectFactory oFactory = new ObjectFactory();
		AttributeDetailType attribute = oFactory.createAttributeDetailType();
		attribute.setName(name);
		attribute.setValue(value);
		attribute.setDescription(description);

		AttributeEntityType entity = oFactory.createAttributeEntityType();
		entity.setSource(source);
		entity.getAttribute().add(attribute);

		return entity;
	}
	
	
	/**
	 * @param customer
	 * @param key
	 * @return CustAddress
	 */
	public com.AL.xml.v4.CustAddress getAddress(Customer customer, final String key) {
		boolean isRole = Boolean.FALSE;
		if ((customer != null) && (customer.getAddressList() != null)) {
			List<com.AL.xml.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (com.AL.xml.v4.CustAddress custAddress : custAddressList) {
					if ((custAddress != null)
							&& (custAddress.getAddressRoles() != null)) {
						List<com.AL.xml.v4.RoleType> roleTypeList = custAddress.getAddressRoles().getRole();
						for (com.AL.xml.v4.RoleType roleType : roleTypeList) {
							if (key.equals(roleType.name())) {
								isRole = Boolean.TRUE;
								break;
							}
						}
						if (isRole) {
							return custAddress;
						}
					}
				}
			}
		}
		return new com.AL.xml.v4.CustAddress();
	}
	
	/**
	 * @param value
	 * @return ProviderSourceBaseType
	 */
	public ProviderSourceBaseType getProviderSourceBaseType(String value)
	{
		for (ProviderSourceBaseType providerSourceBaseType: ProviderSourceBaseType.values()) 
		{
			
			if (providerSourceBaseType.value().equals(value)) 
			{
				return providerSourceBaseType;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Maps LineItemAttributes from Java.util.Map<String,String> and returns the AttributeEntityType
	 * 
	 * @param map
	 * @param source
	 * @return AttributeEntityType
	 */
	public AttributeEntityType setAttributeEntityType(Map<String, String> map, String source)
	{
		 ObjectFactory oFactory = new ObjectFactory();
		AttributeEntityType entity =oFactory.createAttributeEntityType();
		entity.setSource(source);
		
		for (Entry<String, String> entry : map.entrySet()) 
		{
			AttributeDetailType attribute = oFactory.createAttributeDetailType();
			attribute.setName(entry.getKey());
			attribute.setValue(entry.getValue());
			entity.getAttribute().add(attribute);
		}

		return entity;
	}


}
