package com.A.ui.dao;


import java.util.List;

import com.A.ui.domain.SalesSession;

public interface SalesSessionDao {

	public void insertSalesSession(SalesSession ss);
	
	public Long getSalesSessionId(Long dispositionId);
	
	public void updateSalesSession(SalesSession ss);
	
	public SalesSession getSalesSession(Long orderId, Long customerId);

}
