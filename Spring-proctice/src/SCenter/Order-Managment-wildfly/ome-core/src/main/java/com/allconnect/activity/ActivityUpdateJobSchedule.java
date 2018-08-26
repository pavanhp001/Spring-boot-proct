package com.AL.activity;

import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;

public interface ActivityUpdateJobSchedule {

	 
	
	
	public void updateStatus(String userId, StatusRecordBean status,
			String businessParty, SalesOrder order,
			LineItem lineItem);
}
