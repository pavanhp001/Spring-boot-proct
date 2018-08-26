package com.A.ui.activity.domain;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.ui.repository.ServiceCategoryRepository;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.ProductCategoryListType;

public enum ActivityRetrieveSalesPlan {

	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(ActivityRetrieveSalesPlan.class);
	
	public String getSalesPlan(final LineItemType targetLineItem, final boolean isAccord) {
		
		String salesPlan = "-1";
		
		if (!isAccord) {
		
			if ((targetLineItem != null) && 
				(targetLineItem.getLineItemDetail() != null) && 
				(targetLineItem.getLineItemDetail().getDetail() != null) && 
				(targetLineItem.getLineItemDetail().getDetail().getProductLineItem() != null) && 
				(targetLineItem.getLineItemDetail().getDetail().getProductLineItem().getProductCategoryList() != null )) {
				
				ProductCategoryListType categoryList = targetLineItem.getLineItemDetail().getDetail().getProductLineItem().getProductCategoryList();
				if(categoryList.getProductCategory() != null && categoryList.getProductCategory().size() > 0){
					String category =  categoryList.getProductCategory().get(0).getValue();
					if(StringUtils.isBlank(category)){
						category =  categoryList.getProductCategory().get(0).getDisplayName();
					}
					
					String bundleIndicator= "|";
					if(StringUtils.contains(category, bundleIndicator)){
						category= "bundles";
					}
					
					salesPlan = ServiceCategoryRepository.INSTANCE.getServicePlan(category);
				}
			}
			 
		
		} else if ((targetLineItem != null) && 
				(targetLineItem.getLineItemDetail() != null) && 
				(targetLineItem.getLineItemDetail().getProductUniqueId() != null)) {
			salesPlan =  String.valueOf(targetLineItem.getLineItemDetail()
					.getProductUniqueId().longValue());
		}
		
		logger.debug("getSalesPlan, Plan: "+salesPlan);
		return salesPlan;
	}
	
	public String getSalesPlanName(final LineItemType targetLineItem, final boolean isAccord) {
		String salesPlan = getSalesPlan(targetLineItem, isAccord);
		return ServiceCategoryRepository.INSTANCE.getServicePlan(salesPlan);
	}
	
	
	
}
