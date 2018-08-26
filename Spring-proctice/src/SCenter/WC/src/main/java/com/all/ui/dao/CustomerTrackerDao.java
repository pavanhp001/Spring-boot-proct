package com.A.ui.dao;

import java.util.List;
import java.util.Map;

import com.A.ui.domain.CustomerCallsCount;
import com.A.ui.domain.CustomerTracker;
import com.A.ui.domain.CustomerTrackerDetails;

public interface CustomerTrackerDao {
	
	  public void insertCustomerTracker(CustomerTracker customerTracker);
	  
	  public List<CustomerTracker> getCustomerTrackerDataByAgentId(String agentId);
	  
	  public void insertCustomerCallsCount(CustomerCallsCount customerCallsCount);
	  
	  public Integer getMaxCustomerCallNumberId(String agentId);
	  
	  public void updateCustomerTracker(Map<String,Float> updatedActualPointsMap);
	  
	  public void insertCustomerTrackerDetails(CustomerTrackerDetails customerTrackerDetails);
	  
	  public CustomerTrackerDetails getCustomerTrackerDetailsByAgentId(String agentId);
	  
	  public void updateCustomerTrackerDetails(CustomerTrackerDetails customerTrackerDetails);
}
