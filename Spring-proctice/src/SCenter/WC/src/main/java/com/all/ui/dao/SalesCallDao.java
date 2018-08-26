package com.A.ui.dao;

import com.A.ui.domain.SalesCall;
import com.A.ui.domain.SalesSession;
import java.util.Calendar;

public interface SalesCallDao {

	
	  void put(SalesCall salesCall) ;
	  
	  public Long getSalesCallId(Long salesSessionId);
	  
	  public void updateSalesCallWithAmbDisconnectDateTime(long salesCallId, Calendar disconnectCal);
}
