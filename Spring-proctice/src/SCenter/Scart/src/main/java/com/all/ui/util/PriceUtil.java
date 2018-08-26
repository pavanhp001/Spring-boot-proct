/**
 * PriceUtil
 * 
 * Version 1.0 (Calculating the Selected Features Price & Promotional Price)
 * 
 * 
 *
 */
package com.AL.ui.util;

import java.util.List;

import com.AL.ui.builder.CartLineItemBuilder;
import com.AL.ui.factory.CartPromotionFactory;
import com.AL.ui.vo.LineItemVo;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;

/**
 * @author Preetam
 *
 */
public enum PriceUtil {

	INSTANCE;


	/**
	 * Adds all the lineItem Selected Features Prices.
	 * 
	 * @param lineItem
	 * @return Double
	 */
	public Double getSelectedFeaturesPrice(LineItemType lineItem) 
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

	/**
	 * Extracts the Promotions and deducts the PromoPrice based on the type
	 * 
	 * @param order
	 * @param baseRecurringPrice
	 * @param baseNonRecurringPrice
	 * @param lineItemNumber
	 * @return
	 */
	public LineItemPriceInfoType calculatePromotionalPrice(OrderType order, Double baseRecurringPrice, 
			Double baseNonRecurringPrice, int lineItemNumber)
	{
		
		/*	
		 * gets all the Promotions			 
		 */
		LineItemVo lineItemvo = CartPromotionFactory.INSTANCE.lineItemPromotions(order);
		/*	
		 * Extracting the Promotions of the selected LineItem			 
		 */
		List<ApplicableType> promoList = lineItemvo.getPromotionMap().get(String.valueOf(lineItemNumber));
		
		for(ApplicableType promo : promoList)
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
		
		return CartLineItemBuilder.INSTANCE.createLineItemPriceInfoType(baseRecurringPrice, baseNonRecurringPrice);
	}

}
