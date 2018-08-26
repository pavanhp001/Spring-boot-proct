package com.A.ui.dao;

import java.util.List;

import com.A.ui.domain.FeatureLookup;

public interface PromotionDao {

	
	public List<String> getFeaturesByPromotionId(String promotionId);
	
}
