package com.A.ui.dao;

import java.util.List;
import java.util.Map;

public interface FrontierPricingGridConfigDao {

	Map<String, String> getFrontierPricingGridConfig();
	
	Map<String, List<String>> getFrontierProductDetails();
	
}