package com.A.ui.repository;

import java.util.HashMap;
import java.util.Map;

public enum ServiceCategoryRepository {
	
	INSTANCE;
	
	public static Map<String, String> data = new HashMap<String, String>();
	
	static{
		data.put("bundles", "10311");
		data.put("electricity", "24");
		data.put("localPhone", "23");
		data.put("longDistancePhone", "26");
		data.put("homeWireProtection", "10553");
		data.put("wirelessPhone", "10531");
		data.put("cableTV", "27");
		data.put("satelliteTV", "10574");
		data.put("highSpeedInternet", "29");
		data.put("dialUpInternet", "10575");
		data.put("localNewspaper", "10232");
		data.put("nationalNewspaper", "10576");
		data.put("personalChecks", "10351");
		data.put("washerDryerRental", "32");
		data.put("warranty", "10432");
		data.put("homeSecurity", "33");
		data.put("wasteRemoval", "10271");
		data.put("naturalGas", "25");
		data.put("energyConservation", "10552");
		data.put("offers", "11001");
		data.put("secondCall", "11023");
		data.put("water", "35");
		data.put("applianceProtection", "10371");
		data.put("techSupport", "11084");
		
		data.put("23", "Local Phone");
		data.put("24", "Electricity");
		data.put("25", "Natural Gas");
		data.put("26", "Long Distance Phone");
		data.put("27", "CableTV");
		data.put("29", "HighSpeed Internet");
		data.put("32", "Washer Dryer Rental");
		data.put("33", "Home Security");
		data.put("35", "Water");
		data.put("10232", "Local Newspaper");
		data.put("10271", "Waste Removal");
		data.put("10311", "Bundles");
		data.put("10351", "Personal Checks");
		data.put("10371", "Appliance Protection");
		data.put("10432", "Warranty");
		data.put("10531", "Wireless Phone");
		data.put("10552", "Energy Conservation");
		data.put("10553", "Home Wire Protection");
		data.put("10574", "Satellite TV");
		data.put("10575", "DialUpInternet");
		data.put("10576", "National Newspaper");
		data.put("11001", "Offers");
		data.put("11023", "SecondCall");
		data.put("11084", "Tech Support");
	}
	
	public String getServicePlan(final String category) {
		return data.get(category);
	}
	
	public static Map<String, String> getData() {
		return data;
	}

	public static void setData(Map<String, String> data) {
		ServiceCategoryRepository.data = data;
	}

}
