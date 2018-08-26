package com.A.ui.dao;

import java.util.List;
import java.util.Map;

import com.A.ui.domain.GrossCommissionableRevenue;


public interface GrossCommissionableRevenueDao {

	int getRevCommissionBySpIdStable(String spIdStable);
	
	Map<String, Float> getRevCommissionsBySpIdStables(List<String> spIdStable);
	
	Map<String, Float> getAllRevenueCommissions();
	
}